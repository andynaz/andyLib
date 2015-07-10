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

/**
 * Levels for logs.
 * 
 * Each level has a numeric value; the log is written if the log level is high
 * enough (compared with the configuration level). Each level has also a code,
 * that can be retrived with the {@link #getCode()} method.
 * 
 * <p>Available levels (in ascending order) are:
 * <ul>
 *   <li>FINE</li>
 *   <li>DEBUG</li>
 *   <li>INFO</li>
 *   <li>WARNING</li>
 *   <li>ERROR</li>
 *   <li>FATAL</li>
 * </ul>
 * </p>
 * 
 * @author andynaz
 * @version 2014/04/29
 */
public enum Level{
	
	/**
	 * Lower level.
	 * 
	 * A configuration with this level let you log every event.
	 */
	FINE(0, "F"),
	
	/**
	 * Debug level.
	 *
	 * Used to check for problems during development.
	 */
	DEBUG(2, "D"),
	
	/** 
	 * Defaut level.
	 */
	INFO(4, "I"),
	
	/**
	 * Warning level.
	 *
	 * Should be user for captured exceptions or other warnings.
	 */
	WARNING(6, "W"),
	
	/**
	 * Highest level.
	 *
	 * Used for serious problems (like an exit condition for the program).
	 */
	ERROR(8, "E"),
	
	/**
	 * Highest level.
	 *
	 * Used for serious problems (like an exit condition for the program).
	 */
	FATAL(10, "X");
	

	/**
	 * Numeric value of the level.
	 */
	private int level;

	/**
	 * Level code.
	 */
	private String code;
	
	
	Level(int level, String code){
		this.level = level;
		this.code = code;
	}

	/**
	 * Returns the code of this level.
	 *
	 * @return the code of this level
	 */
	public String getCode(){
		return this.code;
	}
	
	/**
	 * Test if a log has to be written.
	 * 
	 * A log is written is the log level is higher than the configuration level.
	 * 
	 * <p>This method should be invoked from the configuration level, having the
	 * log level as argument.</p>
	 * 
	 * @param logLevel event level
	 * @return {@code true} if the log has to e logger, {@code false} otherwise
	 */
	public boolean hasToLog(Level logLevel){
		return this.level<=logLevel.level;
	}
}
