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

import java.io.*;
import java.net.URL;
import java.util.*;
import it.andynaz.log.Level;
import it.andynaz.log.Logger;
import it.andynaz.log.LoggerMgr;

/**
 * Useful method to manage a program data that uses the andyLib project.
 * 
 * These methods rely on files stored in path {@code it/andynaz/resources}.
 *
 * @author andynaz
 * @version 2015/07/10
 */
public class UtilsProgram {
	private static final Logger l = LoggerMgr.getLogger("UtilsProgram");
	
	// hides the constructor
	private UtilsProgram(){}
	
	/**
	 * Print a program header.
	 * 
	 * The header is read from the file {@code it/andynaz/resources/header.txt};
	 * this file has to be stored somewhere in the classpath,<br>
	 * Note that this method prints the first 'header.txt' file it founds.
	 * 
	 * <p>To be printed "well" on most terminal, each line should have a maximum
	 * length of 80 characters.</p>
	 */
	public static void printHeader() {
		InputStream in = Object.class.getResourceAsStream("/it/andynaz/resources/header.txt"); //Thead.currentThread().getContextClassLoader().getResource("foo");
		if (in==null) return;
		UtilsIO.print(in);
	}
	
	
	/**
	 * Print a program help.
	 * 
	 * The header is read from the file {@code it/andynaz/resources/help.txt};
	 * this file has to be stored somewhere in the classpath,<br>
	 * Note that this method prints the first 'header.txt' file it founds.
	 * 
	 * <p>To be printed "well" on most terminal, each line should have a maximum
	 * length of 80 characters.</p>
	 */
	public static void printHelp() throws Exception{
		InputStream in = Object.class.getResourceAsStream("/it/andynaz/resources/help.txt");
		if (in==null) return;
		UtilsIO.print(in);
	}
	
	/**
	 * Keeps informations about the program versions.
	 * 
	 * The reading is done only once on first load of this class.
	 * 
	 * @see <a href="http://stackoverflow.com/questions/3369794/how-to-a-read-file-from-jar-in-java">stackoverflow</a>
	 */
	private static Properties versionData = new Properties();
	static {
		try {
			l.log("reading version file", Level.FINE);
			String resource = "it/andynaz/resources/version.properties";
			ClassLoader classLoader = UtilsProgram.class.getClassLoader();
			Enumeration<URL> urls = classLoader.getResources(resource);
			while (urls.hasMoreElements()) {
				l.log("version file found", Level.FINE);
                final URL url = urls.nextElement();
				Reader reader = new InputStreamReader(url.openStream(), "utf-8");
				versionData.load(reader);
			}
		} catch (IOException ex) {
			l.log(ex, Level.WARNING);
		}
	}
	
	/**
	 * Prints all the version and build date data of the project on the {@link
	 * System#out}.
	 * 
	 * All the entries are printed in alphabetical order.
	 * 
	 * @throws IOException 
	 */
	public static void printProgamData() throws IOException {
		Set<String> keys = versionData.stringPropertyNames();
		List<String> orderedKeys = new ArrayList<String>(keys);
		Collections.sort(orderedKeys);
		for (String key : orderedKeys) {
			System.out.println(String.format("%s=%s", key, versionData.getProperty(key)));
		}
	}
	
	/**
	 * Returns all the version and build date data used in the project
	 * 
	 * @return the Properties with all the program data; the entries are not
	 * sorted
	 */
	public static Properties getProgamData() {
		return versionData;
	}
	
	/**
	 * Returns the version of the project.
	 * 
	 * In particular returns the '<em>projectCode</em>.version' value from
	 * version file.
	 * 
	 * @param projectCode project code
	 * @return the version of the project
	 */
	public static String getVersion(String projectCode) throws IOException{
		return versionData!=null ? versionData.getProperty(projectCode+".version") : null;
	}
	
	/**
	 * Returns the build date of the project.
	 * 
	 * In particular returns the '<em>projectCode</em>.date' value from version
	 * file.
	 * 
	 * @param projectCode project code
	 * @return the build date of the project
	 */
	public static String getBuildDate(String projectCode) throws IOException{
		return versionData!=null ? versionData.getProperty(projectCode+".date") : null;
	}
	
}
