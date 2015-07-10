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

import static java.lang.System.out;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import it.andynaz.utils.Utils;
import it.andynaz.config.ConfigMgr;

/**
 * Base logger.
 * 
 * A logger is used to log events. This class is the root of all the logger
 * jerarchy, and it logs on the {@link System#out System.out} channel.
 * 
 * <p>Every log is written with a prefix, which is returned by the {@link
 * #getPrefix(Level) getPrefix} method.</p>
 * 
 * <p>Each logger has a set of attributes related to how it logs the events (for
 * instance, {@link FileLogger} has a File object where to write logs), which
 * are set by the contructors (see the documentation to know which parameter it
 * takes).<br/>
 * A logger should have also methods related to this set of attributes.<br/>
 * In addition to this members, each logger has also a name, used in the log
 * prefix (useful to distinguish which logger do a particular log).</p>
 * 
 * <p>Logs are written using the various {@code log()} methods: every method,
 * before writing the log, check if the log level if enough to write the
 * log.</p>
 *
 * <p>Sub-classes can change the way a log is written (for instance, in a file
 * or on a database), only changing the {@link #log(String, Level)} method. For
 * other reasons, like efficiency, other methods can be overridden.</p>
 * 
 * @author andynaz
 * @version 2014/04/29
 */
public class Logger{
	
	/**
	 * Name of the logger.
	 * 
	 * If present, it is used in the prefix of the log (useful ti know by which
	 * component of the program the log has been done).
	 */
	protected String name;

	/**
	 * Indicates if the date has to be used in the prefix of the log.
	 *
	 * Dafaule value is 'false'.
	 *
	 * <p>If the value is 'true', the prefix starts with "date+separator"
	 */
	protected boolean useDate = false;

	/**
	 * Indicates the format of the date.
	 *
	 * The format is the one used by {@link SimpleDateFormat}. Dafaule value is
	 * "yy-MM-dd HH:mm:ss".
	 */
	protected SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

	/**
	 * Indicates the level of the logging.
	 *
	 * Dafaule value is {@link Level#INFO}.
	 *
	 * <p>Only the logs with a level compatible (according to the {@link
	 * Level#hasToLog(Level)} method) with this value will be logged.</p>
	 */
	protected Level level = Level.INFO;

	/**
	 * Indicates if the level code has to be used in the prefix of the log.
	 *
	 * Dafaule value is 'false'.
	 */
	protected boolean useLevel = false;

	/**
	 * Indicates the separator to use in the prefix of the logs.
	 *
	 * Dafaule value is "::".
	 */
	protected String separator = "::";
	
	
	/**
	 * Creates a Logger with a name and a specified configuration.
	 *
	 * From the configuration, this method takes from the Properties object only
	 * the propertyes used. If a property is not found in the configuration,
	 * default value is used.
	 *
	 * <p>Properties are used to construct the attributes of the logger. If error
	 * occour (for instance, the format for the date is not correct), no
	 * exception is thrown and the default value is used.</p>
	 *
	 * <p>The properties taken from the configuration (the Properties object
	 * passed as an argument) are:
	 * <dl>
	 *   <dt>log.useDate</dt>    <dd>a boolean to indicates if the date has to be used in the log prefix</dd>
	 *   <dt>log.dateFormat</dt> <dd>format for the date (suitable for a {@link SimpleDateFormat} object)</dd>
	 *   <dt>log.level</dt>      <dd>log level: has to be one of the {@link Level} enum constants</dd>
	 *   <dt>log.useLevel</dt>   <dd>a boolean to indicates if the level code has to be used in the log prefix</dd>
	 *   <dt>log.separator</dt>  <dd>a string indicating the separator of the variuos section of the log prefix</dd>
	 * </dl></p>
	 * 
	 * @param name name of the logger
	 * @param props logger configuration
	 */
	public Logger(String name, Properties props){
		if (!Utils.isEmpty(name))
			this.name = name;
		
		if (props==null) return;
		
		// --- formato data
		if (props.getProperty("log.dateFormat")!=null)
			try{
				this.dateFormat = new SimpleDateFormat(props.getProperty("log.dateFormat"));
			} catch(Exception e){ /*out.println("err: data"); throw e;*/ }

		// --- livello
		if (props.getProperty("log.level")!=null)
			try{
				this.level = Level.valueOf(props.getProperty("log.level"));
			} catch(Exception e){ /*out.println("err: livello"); throw e;*/ }

		// --- log con data
		if (props.getProperty("log.useDate")!=null)
			this.useDate = Boolean.parseBoolean(props.getProperty("log.useDate"));

		// --- log con sigla livello
		if (props.getProperty("log.useLevel")!=null)
			this.useLevel = Boolean.parseBoolean(props.getProperty("log.useLevel"));

		// --- formato data
		if (props.getProperty("log.separator")!=null)
			this.separator = props.getProperty("log.separator");
	}
	
	/**
	 * Creates a logger with a name.
	 *
	 * It uses the configuration of the application (taken from {@link
	 * ConfigMgr#getConfigParams()} method).
	 * 
	 * @param name name of the logger
	 */
	public Logger(String name){
		this(name, ConfigMgr.getConfigParams());
	}
	
	/**
	 * Creates a logger with a configuration.
	 * 
	 * @param props configuration of the logger
	 * 
	 * @see #Logger(String, Properties)
	 */
	public Logger(Properties props){
		this(null, props);
	}
	
	/**
	 * Creates a logger.
	 *
	 * It uses the configuration of the application (taken from {@link
	 * ConfigMgr#getConfigParams()} method).
	 * 
	 * @see #Logger(String, Properties)
	 */
	public Logger(){
		this(null, ConfigMgr.getConfigParams());
	}
	
	
	/**
	 * Writes a log with a specified level.
	 * 
	 * Depending on the log configuration, this method include the level code and
	 * a separator before the log.
	 * 
	 * @param log log to be written
	 * @param level log level
	 */
	public void log(String log, Level level){
		if (this.level.hasToLog(level)) {
			out.println(getPrefix(level) + log);
		}
	}

	/**
	 * Writes a log.
	 * 
	 * The level used is {@link Level#INFO INFO}.
	 *
	 * @param log log to be written
	 */
	public void log(String log){
		log(log, Level.INFO);
	}
	
	/**
	 * Writes the log of an exception.
	 *
	 * The level used is {@link Level#INFO INFO}.
	 *
	 * @param e exception to be logged
	 */
	public void log(Exception e){
		log(e, Level.INFO);
	}
	
	/**
	 * Writes the log of an exception with a specificied level.
	 * 
	 * <p>It is written the exception type (with the message of the exception, if
	 * present) and the stack trace.</p>
	 *
	 * @param e exception to be logged
	 * @param level log level
	 */
	public void log(Exception e, Level level){
		log(e.toString(), level);
		for (StackTraceElement ste : e.getStackTrace())
			log("    "+ste.toString(), level);
		Throwable t = e.getCause();
		if (t!=null && t instanceof Exception) {
			log("  caused by:", level);
			log((Exception)t, level);
		}
	}
	
	/**
	 * Returns the prefix to be written before the log.
	 *
	 * <p>The prefix is
	 * <pre>[date+sep][level+sep][name+sep]</pre>
	 * where each part is optional (depends on the logger configuration).</p>
	 * 
	 * @param level the level from which take the code
	 * @return a prefix in the form {@code [date+sep][level+sep]}[name+sep] (it
	 * can be an empty string)
	 */
	protected String getPrefix(Level level){
		StringBuilder logPrefix = new StringBuilder();
		// date
		if (this.useDate) {
			logPrefix.append(this.getDate());
			logPrefix.append(this.separator);
		}
		// level
		if (this.useLevel && level!=null) {
			logPrefix.append(level.getCode());
			logPrefix.append(this.separator);
		}
		// date
		if (!Utils.isEmpty(this.name)) {
			logPrefix.append(this.name);
			logPrefix.append(this.separator);
		}
		return logPrefix.toString();
	}

	/**
	 * Returns a string with the date.
	 * 
	 * The format of the string is the one specified by the {@link #dateFormat}
	 * value.
	 *
	 * @return a string with the date
	 */
	protected String getDate(){
		return this.dateFormat.format(new Date());
	}
	
}
