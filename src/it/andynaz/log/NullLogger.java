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

package it.andynaz.log;

import java.util.Properties;

/**
 * This logger does nothing.
 * 
 * Used to suppress all logs in a program that uses the <i>andyLog</i> project.
 *
 * @author andynaz
 * @version 2013/12/18
 */
public class NullLogger extends Logger {
	
	/**
	 * Creates an instance.
	 */
	public NullLogger(){ }
	
	/**
	 * Creates an instance.
	 * 
	 * @param name name of the logger (will not be used)
	 */
	public NullLogger(String name){ }
	
	/**
	 * Creates an instance.
	 * 
	 * @param props configuration (will not be used)
	 */
	public NullLogger(Properties props){ }
	
	/**
	 * Creates an instance with a name.
	 * 
	 * @param name logger's name (will not be used)
	 * @param props configuration (will not be used)
	 */
	public NullLogger(String name, Properties props){ }
	
	/**
	 * Does nothing.
	 * 
	 * @param log log (will not be used)
	 * @param level level of the log (will not be used)
	 */
	@Override
	public void log(String log, Level level){ }
	
	/**
	 * Does nothing.
	 * 
	 * @param log log (will not be used)
	 */
	@Override
	public void log(String log){ }
	
	/**
	 * Does nothing.
	 * 
	 * @param e exception to log (will not be used)
	 */
	@Override
	public void log(Exception e){ }
	
	/**
	 * Does nothing.
	 * 
	 * @param e exception to log (will not be used)
	 * @param level level of the log (will not be used)
	 */
	@Override
	public void log(Exception e, Level level){ }
	
}
