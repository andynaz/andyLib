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

package it.andynaz.config;

import it.andynaz.log.Level;
import it.andynaz.log.LoggerMgr;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import java.util.Properties;
import java.util.Set;

/**
 * Class to centralize the configuration of a program as a set of parameters.
 * 
 * Parameters can be stored (for instance, at the beginning of the execution)
 * and then retrived later.
 * 
 * <p>The parameters can be stored in the class using one (or more) of the
 * {@code init} methods provided or the {@link #setConfigParam(String, String)}
 * method (to store them manually). When storing a value, only the last one is
 * saved (so different invocation of one of the {@code init} methods override
 * old values of the same parameter - this can be used also to choose the
 * priority of different source of config parameters).<br/>
 * Note that the {@code init} methods consider only the parameter which name
 * begin with he suffix "it.andynaz.".</p>
 * 
 * <p>Parameters can be retrived using the {@link #getConfigParam(String)}
 * method.</p>
 * 
 * <p>All of its methods are static, as there have to be only one instance of
 * the configuration. However, the class can return a copy of all the params
 * stored as a {@link Properties} object with the {@link #getConfigParams()}
 * method.</p>
 *
 * @author andynaz
 * @version 2014/04/29
 */
public class ConfigMgr {
	
	private static final String prefix = "it.andynaz.";
	private static final Properties p = new Properties();
	
	/**
	 * Loads the parameters stored in {@link System#getProperties()}.
	 * 
	 * This method consider only the properties beginning with the prefix
	 * "{@code it.andynaz.}".
	 */
	public static void init(){
		init(System.getProperties());
	}
	
	/**
	 * Loads the parameters stored in a Properties file.
	 * 
	 * This method consider only the properties beginning with the prefix
	 * "{@code it.andynaz.}".
	 * 
	 * @param filePath path to the Properties file
	 * 
	 * @throws NullPointerException if props is {@code null}
	 */
	public static void init(String filePath){
		try {
			Properties p = new Properties();
			p.load(new FileReader(filePath));
			init(p);
		} catch (FileNotFoundException ex) {
			LoggerMgr.getLogger("ConfigMrg").log(ex, Level.WARNING);
		} catch (IOException ex) {
			LoggerMgr.getLogger("ConfigMrg").log(ex, Level.WARNING);
		}
	}
	
	/**
	 * Loads the parameters stored in the {@link Properties} object.
	 * 
	 * This method consider only the properties beginning with the prefix
	 * "{@code it.andynaz.}".
	 * 
	 * @param props 
	 * 
	 * @throws NullPointerException if props is {@code null}
	 */
	public static void init(Properties props){
		Set<String> keys = props.stringPropertyNames();
		for (String key : keys)
			if (key.startsWith(prefix))
				p.setProperty(key, props.getProperty(key));
	}
	
	/**
	 * Return the config parameter associated with the name.
	 * 
	 * @return the config parameter associated with the name, or {@code null} if
	 * the parameter does not exists
	 */
	public static String getConfigParam(String propName){
		return p.getProperty(propName);
	}
	
	/**
	 * Returns a Properties object with all the properties stored.
	 * 
	 * @return a copy of the internal Properties object
	 */
	public static Properties getConfigParams(){
		return (Properties)p.clone();
	}
	
	/**
	 * Set a parameter value.
	 * 
	 * The value is set only of it is not null.
	 * 
	 * <p>If the parameter already exists, its value is overwritten.</p>
	 * 
	 * @param name parameter's name
	 * @param value parameter's value
	 * 
	 * @throws NullPointerException if {@code name} is {@code null}
	 */
	public static void setConfigParam(String name, String value){
		if (value!=null)
			p.setProperty(name, value);
	}
	
	/**
	 * Remove a parameter value.
	 * 
	 * @param name parameter's name
	 */
	public static void removeConfigParam(String name){
		p.remove(name);
	}
	
	/**
	 * List all the parameters currently stored.
	 * 
	 * The parameters are printed on the standard output. Used for debug.
	 */
	public static void list(){
		p.list(out);
	}
}
