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


/**
 * The Class AutomaticRoleConfiguration.
 */
public class AutomaticRoleConfiguration {

	
	/**
	 * Instantiates a new automatic role configuration.
	 *
	 * @param strLuteceUserAttributeKey the str lutece user attribute key
	 * @param strLuteceUserAttributeValue the str lutece user attribute value
	 * @param strRole the str role
	 */
	public AutomaticRoleConfiguration(String strLuteceUserAttributeKey, String strLuteceUserAttributeValue,
			String strRole) {
		super();
		this._strLuteceUserAttributeKey = strLuteceUserAttributeKey;
		this._strLuteceUserAttributeValue = strLuteceUserAttributeValue;
		this._strRole = strRole;
	}
	
	/** The str lutece user attribute key. */
	private String _strLuteceUserAttributeKey;
	
	/** The str lutece user attribute value. */
	private String _strLuteceUserAttributeValue;
	
	/** The str role. */
	private String _strRole;
	
	
	/**
	 * Gets the lutece user attribute key.
	 *
	 * @return the lutece user attribute key
	 */
	public String getLuteceUserAttributeKey() {
		return _strLuteceUserAttributeKey;
	}
	
	/**
	 * Sets the lutece user attribute key.
	 *
	 * @param _strLuteceUserAttributeKey the new lutece user attribute key
	 */
	public void setLuteceUserAttributeKey(String _strLuteceUserAttributeKey) {
		this._strLuteceUserAttributeKey = _strLuteceUserAttributeKey;
	}
	
	/**
	 * Gets the lutece user attribute value.
	 *
	 * @return the lutece user attribute value
	 */
	public String getLuteceUserAttributeValue() {
		return _strLuteceUserAttributeValue;
	}
	
	/**
	 * Sets the lutece user attribute value.
	 *
	 * @param _strLuteceUserAttributeValue the new lutece user attribute value
	 */
	public void setLuteceUserAttributeValue(String _strLuteceUserAttributeValue) {
		this._strLuteceUserAttributeValue = _strLuteceUserAttributeValue;
	}
	
	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return _strRole;
	}
	
	/**
	 * Sets the role.
	 *
	 * @param _strRole the new role
	 */
	public void setRole(String _strRole) {
		this._strRole = _strRole;
	}
	
	
	

}
