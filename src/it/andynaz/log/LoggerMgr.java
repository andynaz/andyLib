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

import java.lang.reflect.Constructor;
import it.andynaz.config.ConfigMgr;

/**
 * This class let you create a "default" logger.
 *
 * The default logger is the one specified by tha value "{@code
 * log.defaultLogger}" in the {@link ConfigMgr} class. The logger used should
 * have two constructors, the one with no arguments and another one with takes a
 * string.
 *
 * <p>The logger is created using the configuration stored in the {@link
 * ConfigMgr} class. If the class specified by the value "{@code defaultLogger}"
 * does not exist or it is not a subclass of {@link Logger}, a {@link Logger} is
 * created (the one that uses the standard output).</p>
 *
 * @author andynaz
 * @version 2014/04/29
 */
public class LoggerMgr{
	// hides the constructor
	private LoggerMgr(){ }

	/**
	 * Returns a "default" logger without a name.
	 * 
	 * The logger built has to have a contructor with no arguments.
	 *
	 * @return a default logger with no name, or a {@link Logger} in caso of
	 * errors
	 */
	public static Logger getLogger(){
		try {
			Class<Logger> logger = getLoggerClass();
			Logger loggerInst = logger.newInstance();
			if (loggerInst==null)
				throw new Exception();
			return loggerInst;
		} catch (Exception ex) {
			return new Logger();
		}
		
	}

	/**
	 * Returnes a "default" logger with a name.
	 * 
	 * The logger built has to have a contructor which takes a string (the
	 * logger's name).
	 *
	 * @param name the name of the logger
	 * @return a default logger (or a {@link Logger} in case of errors) with a
	 * name
	 */
	public static Logger getLogger(String name){
		try {
			Class<Logger> logger = getLoggerClass();
			Constructor<Logger> c = logger.getConstructor(String.class);
			Logger loggerInst = c.newInstance(name);
			if (loggerInst==null)
				throw new Exception();
			return loggerInst;
		} catch (Exception ex) {
			return new Logger(name);
		}
	}

	/**
	 * Returns the class used to create the logger.
	 * 
	 * The class name is taken from the "{@code defaultLogger}" property.
	 *
	 * @return the class used to create the logger
	 */
	private static Class<Logger> getLoggerClass(){
		Class<Logger> logger = Logger.class;
		// try the one from the configuration
		try {
			Class<Logger> l = (Class<Logger>) Class.forName(ConfigMgr.getConfigParam("log.defaultLogger"));
			logger = l;
		} catch (Exception ex) { }
		return logger;
	}

}
