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

import static java.lang.System.out;
import java.util.*;
import it.andynaz.log.Logger;

/**
 * Generic useful methods.
 * 
 * @author andynaz
 * @version 2014/01/24
 */
public abstract class Utils{
	// hides the constructor
	private Utils(){}

	/**
	 * Check if a string is empty.
	 *
	 * A String is considered empty if it is {@code null} or its length is 0.
	 * 
	 * @param s the string to check
	 * @return {@code true} if the string is empty, {@code false} otherwise
	 */
	public static boolean isEmpty(String s){
		return (s==null || s.length()==0);
	}
	
	
	/**
	 * Write the log of an exception/error on the standard output, preceed by a
	 * custom message.
	 *
	 * <p>Print (in this order):
	 * <ol>
	 *   <li>the custom message</li>
	 *   <li>the exception/error's message</li>
	 *   <li>the exception/error stack trace</li>
	 * </ol>
	 * Every element is printed only if it is "present", ie it is not {@code
	 * null} or empty.</p>
	 * 
	 * <p><b>Note:</b> if more flexibility is needed, the {@link Logger andyLog
	 * Project} can be used.</p>
	 *
	 * @param message the custom message
	 * @param t exception or error to be logged
	 */
	public static void printException(String message, Throwable t){
		if (!isEmpty(message)) out.println(message);
		if (!isEmpty(t.getMessage())){
			out.println(t.getMessage());
			if (t.getStackTrace()!=null)
				t.printStackTrace();
		}
	}
	
	
	/**
	 * Get an {@link Integer} from a string.
	 * 
	 * If it is not possible to convert the string, {@code null} is returned.
	 * 
	 * <p>Note: this method does not throw any {@link NumberFormatException}.</p>
	 * 
	 * @param s a string representing a number
	 * @return the Integer corresponding to the string, or {@code null} if it
	 * does not represent an Integer
	 */
	public static Integer parseInt(String s){
		Integer i = null;
		try{
			i = new Integer(s);
		} catch(Exception e){}
		return i;
	}
	
	
	/**
	 * Get a {@link Long} from a string.
	 *
	 * If it is not possible to convert the string, {@code null} is returned.
	 *
	 * <p>Note: this method does not throw any {@link NumberFormatException}.</p>
	 *
	 * @param s a string representing a number
	 * @return the Long corresponding to the string, or {@code null} if it does
	 * not represent a Long
	 */
	public static Long parseLong(String s){
		Long l = null;
		try{
			l = new Long(s);
		} catch(Exception e){}
		return l;
	}
	
	
	/**
	 * Pad the string {@code s} on the left with the pattern {@code p}, till the
	 * length of (at least) {@code l}.
	 * 
	 * <p><b>Note:</b> the returned string can be longer than {@code l}.</p>
	 *
	 * @param s a string to pad
	 * @param p pattern to use to pad the string
	 * @param l minimun length of the returned string
	 * 
	 * @throws NullPointerException if at least one of {@code s} or {@code p} is
	 * {@code null}.
	 * @throws IllegalArgumentException il {@code l}&lt;0 or the pattern {@code
	 * p} is empty
	 */
	public static String pad(String s, String p, int l){
		if (s==null) throw new NullPointerException("null string");
		if (p==null) throw new NullPointerException("null pattern");
		if (p.length()==0) throw new IllegalArgumentException("empty pattern");
		if (l<0) throw new IllegalArgumentException("negative length: l="+l);
	
		StringBuilder sb = new StringBuilder(l);
		sb.append(s);
		while (sb.length()<l)
			sb.insert(0, p);
		return sb.toString();
	}
	

	/**
	 * Concat the elements in an array {@code s}, using the string {@code sep}.
	 * 
	 * If the list is empty, this method returns an empty string (not {@code
	 * null}). If {@code sep} is empty, it concats the elements with no
	 * separator.
	 * 
	 * @param s strings to be concated
	 * @param separator separator
	 * 
	 * @throws NullPointerException if {@code s} is {@code null}
	 */
	public static String concat(String[] s, String separator){
		if (s==null) throw new NullPointerException("null string array");
		
		String sep = isEmpty(separator) ? "" : separator;
		
		StringBuilder sb = new StringBuilder(s.length*10);
		for (int i=0; i<s.length; i++) {
			if (i>0) sb.append(sep);
			sb.append(s[i]);
		}
		return sb.toString();
	}
	

	/**
	 * Concat the elements in a collection {@code c}, using the string {@code
	 * sep} as separator.
	 * 
	 * If the list is empty, this method returns an empty string (not {@code
	 * null}). If {@code sep} is empty, it concats the elements with no
	 * separator.
	 * 
	 * <p><b>Note:</b> the method accepts a non-generic list because the access
	 * to the elements is in read-only mode.</p>
	 * 
	 * @param c a collection of elements
	 * @param separator a separator
	 * 
	 * @throws NullPointerException if {@code l} is {@code null}
	 */
	public static String concat(Collection c, String separator){
		if (c==null) throw new NullPointerException("null list");
		
		String sep = isEmpty(separator) ? "" : separator;
		
		StringBuilder sb = new StringBuilder(c.size()*10);
		Iterator i = c.iterator();
		int count=0;
		while (i.hasNext()) {
			if (count>0) sb.append(sep);
			sb.append(String.valueOf(i.next()));
			count++;
		}
		return sb.toString();
	}

	/**
	 * Returns a paramenter taken from a string array (for instance the one
	 * passed to a 'main' method).
	 * 
	 * The parameter value is considered to be immediatly following the parameter
	 * name, as <tt>-<i>name</i>&nbsp;<i>value</i></tt>.
	 *
	 * <p>The parameter in the array has to be preceed by the character '-'. If
	 * there is no match on the parameter name, the method returns {@code
	 * null}.</p>
	 * 
	 * <p>If the parameter name is repeated, only the first occourrence is
	 * considered. If the paramenter name is the last element of the array, it is
	 * not considered (and {@code null} is returned).</p>
	 *
	 * <p>As an example, in the strings sequence
	 * <blockquote>ab c -or de f</blockquote>
	 * invoking the method as <tt>getInitParam("or", args)</tt>, "de" is
	 * returned.</p>
	 *
	 * @param paramName the name of the parameter
	 * @param args the array
	 * @return the value of the parameter, or {@code null} if there is no match
	 */
	public static String getInitParam(String paramName, String[] args){
		for (int c=0; c<args.length-1; c++) {
			if (("-"+paramName).equals(args[c]))
				return args[++c];
		}
		return null;
	}
	
	/**
	 * Remove the extension from a file name.
	 * 
	 * @param fileName the file name
	 * @return the file name without extension, if any, otherwhise the file name
	 */
	public static String removeFileExt(String fileName){
		int ndx = fileName.lastIndexOf('.');
		return (ndx==-1 ? fileName : fileName.substring(0, ndx));
	}
	
	/**
	 * Get the file extension from a file name.
	 * 
	 * @param fileName the file name
	 * @return the extension
	 */
	public static String getFileExt(String fileName){
		int ndx = fileName.lastIndexOf('.');
		return fileName.substring(++ndx);
	}
	
}
