/*
 * Copyright 2013-2015 Andrea "andynaz" Agnesse
 *
 * This file is part of "andyLib".
 *
 * "andyLib" is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * "andyLib" is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * "andyLib".  If not, see <http://www.gnu.org/licenses/>.
 *
 * author e-mail: andynaz@gmail.com
 * author home page: http://andynaz.altervista.org
 * program official page: http://andynaz.altervista.org/Progetti/andyLib.html
 */

package it.andynaz.utils;

import javax.servlet.http.HttpServletRequest;


/**
 * Useful methods for web applications.
 * 
 * @author andynaz
 * @version 2015/07/10
 */
public abstract class UtilsWEB{
	// hides the contructor
	private UtilsWEB(){}
	
	/**
	 * Returns a numeric parameter from an HTTP request.
	 * 
	 * If it is not possible (either because the parameter does not exist or the
	 * value is not an Integer) returns {@code null}.
	 *
	 * @param req HTTP request
	 * @param name parameter's name
	 * 
	 * @return an {@link Integer} object with the value of the {@code name}
	 * parameter, or {@code null}
	 * 
	 * @throws NullPointerException if req or name is {@code null}
	 * @throws IllegalArgumentException if name has length==0
	 */
	public static Integer getIntegerParameter(HttpServletRequest req, String name){
		if (req==null) throw new NullPointerException("null HTTP request");
		if (name==null) throw new NullPointerException("null parameter name");
		if (name.length()==0) throw new IllegalArgumentException("empty parameter name");
		
		return Utils.parseInt(req.getParameter(name));
	}
	
	/**
	 * Returns a numeric parameter from an HTTP request.
	 * 
	 * If it is not possible (either because the parameter does not exist or the
	 * value is not a Long) returns {@code null}.
	 *
	 * @param req HTTP request
	 * @param name parameter's name
	 * 
	 * @return a {@link Long} object with the value of the {@code name}
	 * parameter, or {@code null}
	 * 
	 * @throws NullPointerException if req or name is {@code null}
	 * @throws IllegalArgumentException if name has length==0
	 */
	public static Long getLongParameter(HttpServletRequest req, String name){
		if (req==null) throw new NullPointerException("null HTTP request");
		if (name==null) throw new NullPointerException("null parameter name");
		if (name.length()==0) throw new IllegalArgumentException("empty parameter name");
		
		return Utils.parseLong(req.getParameter(name));
	}
	
}
