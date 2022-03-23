package fr.paris.lutece.plugins.automaticroleprovider.service;

import java.util.function.BiPredicate;

import fr.paris.lutece.portal.service.security.LuteceUser;

public interface ConfigurationPredicate {

	
	 public BiPredicate<LuteceUser,AutomaticRoleConfiguration> getPredicate();

}
