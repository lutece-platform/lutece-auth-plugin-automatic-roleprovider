package fr.paris.lutece.plugins.automaticroleprovider.service;

import java.util.function.BiPredicate;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.portal.service.security.LuteceUser;

public class NotEmptyPredicate implements ConfigurationPredicate{

	BiPredicate<LuteceUser,AutomaticRoleConfiguration>  _biPredicate;
	
	public NotEmptyPredicate() {
		 
		_biPredicate = (aUser, roleConfiguration) -> {
	    	  return (roleConfiguration.isAutomatic()!=null && roleConfiguration.isAutomatic()) ||( !StringUtils.isEmpty(aUser.getUserInfo(roleConfiguration.getLuteceUserAttributeKey())));
	      };
	}

	@Override
	public BiPredicate<LuteceUser, AutomaticRoleConfiguration> getPredicate() {

		return _biPredicate;
	}

}
