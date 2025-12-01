/*
 * Copyright (c) 2002-2025, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.automaticroleprovider.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.eclipse.microprofile.config.Config;

import fr.paris.lutece.plugins.mylutece.business.LuteceUserRoleDescription;
import fr.paris.lutece.plugins.mylutece.service.IMyLuteceExternalRolesProvider;
import fr.paris.lutece.portal.business.role.Role;
import fr.paris.lutece.portal.business.role.RoleHome;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * The Class AutomaticRoleProvider.
 */
@ApplicationScoped
@Named( "automaticroleprovider.automaticRoleProvider" )
public class AutomaticRoleProvider implements IMyLuteceExternalRolesProvider 
{

    private static final String CONFIGURATIONS_KEY = "automaticroleprovider.configurations";
    private static final String CONFIGURATION_ROLE_SUFFIX = ".role";
    private static final String CONFIGURATION_PREDICATE_SUFFIX = ".predicate";
    private static final String CONFIGURATION_AUTOMATIC_SUFFIX = ".automatic";
    private static final String CONFIGURATION_USER_ATTRIBUTE_KEY_SUFFIX = ".luteceUserAttributeKey";
    private static final String CONFIGURATION_USER_ATTRIBUTE_VALUE_SUFFIX = ".luteceUserAttributeValue";

    // Properties for page titles
    private static final String PROPERTY_PAGE_ROLE_DESCRIPTION_MESSAGE = "automaticroleprovider.automaticRoleProvider.roleDescriptionMessage";
    private static final String PROPERTY_PAGE_ROLE_DESCRIPTION_MESSAGE_AUTOMATIC = "automaticroleprovider.automaticRoleProvider.roleDescriptionMessageAutomatic";
    private static final String PROPERTY_PAGE_ROLE_DESCRIPTION_ERROR = "automaticroleprovider.automaticRoleProvider.roleDescriptionError";

    /** The list automatic role configuration. */
    private List<AutomaticRoleConfiguration> _listAutomaticRoleConfiguration;
    
    @Inject
    private Config _config;
    
    @PostConstruct
    private void postConstruct( )
    {
        List<String> configurations = _config.getOptionalValues( CONFIGURATIONS_KEY, String.class ).orElse( new ArrayList<String>( ) );
        _listAutomaticRoleConfiguration  = new ArrayList<AutomaticRoleConfiguration>( );
        for ( String configuration : configurations )
        {
            addConfiguration( configuration);
        }
    }
    
    private void addConfiguration(String strKey)
    {
        String strRole = _config.getValue( strKey + CONFIGURATION_ROLE_SUFFIX, String.class );
        String strPredicate = _config.getValue( strKey + CONFIGURATION_PREDICATE_SUFFIX, String.class );
        ConfigurationPredicate configurationPredicate = CDI.current( ).select( ConfigurationPredicate.class, NamedLiteral.of( strPredicate ) ).get( );
        boolean bAutomatic = _config.getOptionalValue( strKey + CONFIGURATION_AUTOMATIC_SUFFIX, Boolean.class ).orElse( false );
        String strUserAttributeKey = _config.getOptionalValue( strKey + CONFIGURATION_USER_ATTRIBUTE_KEY_SUFFIX, String.class ).orElse( null );
        String strUserAttributeValue = _config.getOptionalValue( strKey + CONFIGURATION_USER_ATTRIBUTE_VALUE_SUFFIX, String.class ).orElse( null );
        _listAutomaticRoleConfiguration.add( new AutomaticRoleConfiguration( strUserAttributeKey, strUserAttributeValue, strRole, configurationPredicate, bAutomatic ) ); 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> providesRoles( LuteceUser user )
    {
        List<String> listRoles = new ArrayList<String>( );

        if ( this._listAutomaticRoleConfiguration != null )
        {
            this._listAutomaticRoleConfiguration.stream( ).filter( x -> x.getConfigurationPredicate( ).getPredicate( ).test( user, x ) )
                    .forEach( x -> listRoles.add( x.getRole( ) ) );
        }

        return listRoles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LuteceUserRoleDescription> getLuteceUserRolesProvided( Locale locale )
    {
        List<LuteceUserRoleDescription> lisDescriptions = new ArrayList<LuteceUserRoleDescription>( );
        if ( this._listAutomaticRoleConfiguration != null )
        {
            this._listAutomaticRoleConfiguration.forEach( x -> {
                Role role = RoleHome.findByPrimaryKey( x.getRole( ) );
                if ( role != null )
                {
                    if ( x.isAutomatic( ) != null && x.isAutomatic( ) )
                    {
                        // Automatic assignment
                        lisDescriptions.add( new LuteceUserRoleDescription( role, LuteceUserRoleDescription.TYPE_AUTOMATIC_ASSIGNMENT,
                                I18nService.getLocalizedString( PROPERTY_PAGE_ROLE_DESCRIPTION_MESSAGE_AUTOMATIC, locale ) ) );

                    }
                    else
                    {
                        // conditional assignment
                        lisDescriptions.add( new LuteceUserRoleDescription( role, LuteceUserRoleDescription.TYPE_CONDITIONAL_ASSIGNMENT,
                                I18nService.getLocalizedString( PROPERTY_PAGE_ROLE_DESCRIPTION_MESSAGE, new Object [ ] {
                                        x.getLuteceUserAttributeKey( ), x.getLuteceUserAttributeValue( )
                        }, locale ) ) );
                    }
                }
                else
                {
                    Role roleEmty = new Role( );
                    roleEmty.setRole( x.getRole( ) );
                    lisDescriptions.add( new LuteceUserRoleDescription( roleEmty, LuteceUserRoleDescription.TYPE_CONDITIONAL_ASSIGNMENT,
                            I18nService.getLocalizedString( PROPERTY_PAGE_ROLE_DESCRIPTION_ERROR, locale ) ) );

                }
            }
            );
        }
        return lisDescriptions;
    }

    public List<AutomaticRoleConfiguration> getListAutomaticRoleConfiguration( )
    {
        return _listAutomaticRoleConfiguration;
    }    
}
