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

import java.io.*;
import java.util.Properties;
import it.andynaz.utils.*;
import it.andynaz.config.ConfigMgr;

/**
 * File Logger.
 * 
 * Writes the logs on a text file.
 * 
 * <p>Each constructor calls the corresponding constructor of the {@link Logger}
 * class, so the FileLogger object inherits all the properties the Logger class
 * has (and uses all of its options).</p>
 * 
 * @author andynaz
 * @version 2014/04/29
 */
public class FileLogger extends Logger{

	/**
	 * File in which write the logs.
	 */
	protected File logFile = new File("andyLog.log");
	
	
	/**
	 * Creates a FileLogger with a name and a configuration.
	 * 
	 * The path of the log file is taken from the property "{@code
	 * log.fileLogger.file}".
	 * 
	 * @param name name of the Logger
	 * @param props configuration of the Logger
	 * 
	 * @see Logger#Logger(String, Properties) for all the other properties see
	 * the Logger constructor
	 */
	public FileLogger(String name, Properties props){
		super(name, props);

		// --- file
		if (props.getProperty("log.fileLogger.file")!=null)
			try{
				this.logFile = new File(props.getProperty("log.fileLogger.file"));
			} catch(Exception e){ /*out.println("err: data"); throw e;*/ }
	}
	
	/**
	 * Creates a FileLogger with a name.
	 *
	 * It uses the configuration of the application (taken from {@link
	 * ConfigMgr#getConfigParams()} method).
	 *
	 * @param name name of the Logger
	 * 
	 * @see #FileLogger(String, Properties)
	 */
	public FileLogger(String name){
		this(name, ConfigMgr.getConfigParams());
	}
	
	/**
	 * Creates a FileLogger with a configuration.
	 *
	 * @param props configuration of the Logger
	 * 
	 * @see #FileLogger(String, Properties)
	 */
	public FileLogger(Properties props){
		this(null, props);
	}
	
	/**
	 * Creates a FileLogger.
	 *
	 * It uses the configuration of the application.
	 * 
	 * @see #FileLogger(String, Properties)
	 */
	public FileLogger(){
		this(null, ConfigMgr.getConfigParams());
	}
	
	
	/**
	 * Writes the log.
	 * 
	 * If it is not possible, invokes the {@link Logger#log(String, Level)}
	 * method.
	 * 
	 * @param log log to be written
	 * @param level log level
	 */
	@Override
	public void log(String log, Level level){
		if (this.level.hasToLog(level))
			try{
				Writer w = new FileWriter(logFile, true);
				BufferedWriter bw = new BufferedWriter(w);
				bw.write(getPrefix(level) + log);
				bw.newLine();
				bw.flush();
				bw.close();
			} catch(IOException e){
				Utils.printException("error while writing the log", e);
				super.log(log, level);
			}
	}
	
	/**
	 * Writes the log of an exception with a specificied level.
	 * 
	 * It is written the exception type (with the message of the exception, if
	 * present) and the stack trace. All al this elements are written all
	 * togheter.
	 * 
	 * <p>If it is not possible, invokes the {@link Logger#log(Exception, Level)}
	 * method.</p>
	 *
	 * @param e exception to be logged
	 * @param level log level
	 */
	@Override
	public void log(Exception e, Level level){
		if (this.level.hasToLog(level))
			try{
				Writer w = new FileWriter(logFile, true);
				BufferedWriter bw = new BufferedWriter(w);
				String prefix = getPrefix(level);
				
				// print exception message
				log(e.toString(), level);
				// print stack trace
				for (StackTraceElement ste : e.getStackTrace()){
					bw.write(prefix + "    "+ste.toString());
					bw.newLine();
				}
				// print an eventual cause exception
				Throwable t = e.getCause();
				if (t!=null && t instanceof Exception) {
					bw.write(prefix + "  caused by:");
					bw.newLine();
					log((Exception)t, level);
				}
				
				bw.flush();
				bw.close();
			} catch(IOException ex){
				Utils.printException("error while writing the log", ex);
				super.log(ex, level);
			}
	}
	
}
