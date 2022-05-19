package fr.paris.lutece.plugins.automaticroleprovider.service;

import java.util.function.BiPredicate;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.portal.service.security.LuteceUser;

public class EqualsPredicate implements ConfigurationPredicate{

	BiPredicate<LuteceUser,AutomaticRoleConfiguration>  _biPredicate;
	
	public EqualsPredicate() {
		 
		_biPredicate = (aUser, roleConfiguration) -> {
	    	  return (roleConfiguration.isAutomatic()!=null && roleConfiguration.isAutomatic()) ||( !StringUtils.isEmpty(aUser.getUserInfo(roleConfiguration.getLuteceUserAttributeKey()))
						&& roleConfiguration.getLuteceUserAttributeValue().toUpperCase()
								.equals(aUser.getUserInfo(roleConfiguration.getLuteceUserAttributeKey()).toUpperCase()));
	      };
	}

	@Override
	public BiPredicate<LuteceUser, AutomaticRoleConfiguration> getPredicate() {

		return _biPredicate;
	}

}
