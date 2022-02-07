/*
 * Copyright (c) 2002-2022, City of Paris
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

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.mylutece.business.LuteceUserRoleDescription;
import fr.paris.lutece.plugins.mylutece.service.IMyLuteceExternalRolesProvider;
import fr.paris.lutece.portal.business.role.Role;
import fr.paris.lutece.portal.business.role.RoleHome;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.security.LuteceUser;



/**
 * The Class AutomaticRoleProvider.
 */
public class AutomaticRoleProvider implements IMyLuteceExternalRolesProvider 
{
    
    /** The list automatic role configuration. */
    private List<AutomaticRoleConfiguration> _listAutomaticRoleConfiguration;
    // Properties for page titles
    private static final String PROPERTY_PAGE_ROLE_DESCRIPTION_MESSAGE = "automaticroleprovider.automaticRoleProvider.roleDescriptionMessage";
    private static final String PROPERTY_PAGE_ROLE_DESCRIPTION_ERROR = "automaticroleprovider.automaticRoleProvider.roleDescriptionError";
     
   
    
	
	/**
	 * Instantiates a new automatic role provider.
	 *
	 * @param listAutomaticRoleConfiguration the list automatic role configuration
	 */
	public AutomaticRoleProvider(List<AutomaticRoleConfiguration>  listAutomaticRoleConfiguration) {
		super();
		this._listAutomaticRoleConfiguration = listAutomaticRoleConfiguration;
	
	}

	/**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> providesRoles( LuteceUser user ) 
    {
       
        
      List<String> listRoles=new ArrayList<String>();
      
      if(this._listAutomaticRoleConfiguration!=null)
      {
			this._listAutomaticRoleConfiguration.forEach(x -> {

				if (!StringUtils.isEmpty(user.getUserInfo(x.getLuteceUserAttributeKey()))
						&& x.getLuteceUserAttributeValue().toUpperCase()
								.equals(user.getUserInfo(x.getLuteceUserAttributeKey()).toUpperCase())) {
					listRoles.add(x.getRole());

				}

			});
      }
      
      return listRoles;
    	
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<LuteceUserRoleDescription> getLuteceUserRolesProvided(Locale locale)
    {
    	
    	List<LuteceUserRoleDescription> lisDescriptions=new ArrayList<LuteceUserRoleDescription>();
    	if(this._listAutomaticRoleConfiguration!=null)
        {
			this._listAutomaticRoleConfiguration.forEach(x -> {
				Role role = RoleHome.findByPrimaryKey(x.getRole());
				if (role != null) {
					lisDescriptions.add(
							new LuteceUserRoleDescription(role, LuteceUserRoleDescription.TYPE_CONDITIONAL_ASSIGNMENT,
									I18nService.getLocalizedString(PROPERTY_PAGE_ROLE_DESCRIPTION_MESSAGE, new Object[]{x.getLuteceUserAttributeKey(), x.getLuteceUserAttributeValue()},locale)));
				}
				else
				{
					Role roleEmty=new Role();
					roleEmty.setRole(x.getRole());
					lisDescriptions.add(
							new LuteceUserRoleDescription(roleEmty, LuteceUserRoleDescription.TYPE_CONDITIONAL_ASSIGNMENT,
									I18nService.getLocalizedString(PROPERTY_PAGE_ROLE_DESCRIPTION_ERROR,locale)));
	
					
				}

			}

			);
        }
    	
    	
    	return lisDescriptions;
    	
    }
    
       
    
    
}
