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

import it.andynaz.log.Logger;
import it.andynaz.log.LoggerMgr;
import java.io.*;


/**
 * Useful methods for an elementary file management.
 * 
 * @author andynaz
 * @version 2013/11/12
 */
public class UtilsIO{
	// hides the constructor
	private UtilsIO(){}
	
	/**
	 * Reads the content of a text file.
	 * 
	 * The file content is returned as a {@link StringBuilder} for efficency.
	 * 
	 * @param file file to be read
	 * @return a StringBuilder with the content of the file
	 * 
	 * @throws FileNotFoundException if the file does not exist
	 * @throws IOException if I/O errors occour
	 */	
	public static StringBuilder readFromFile(File file) throws FileNotFoundException, IOException{
		if( !file.exists() )
			throw new FileNotFoundException("file "+file+" not found");
		StringBuilder content = new StringBuilder(500);
		String line = null;
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		while( (line=br.readLine())!=null ){
			content.append(line);
			content.append('\n');
		}
		return content;
	}
	
	
	/**
	 * Writes a string in a text file.
	 * 
	 * The method does not throw any exception. A boolean is returned with the
	 * result of the operation.
	 * 
	 * <p>In case of error, the writing is interrupted and a log is produced on
	 * the standard output.</p>
	 * 
	 * @param file file to be written
	 * @param text text to write
	 * @return <tt>true</tt> if the writing has been done without errors,
	 * <tt>false</tt> otherwise
	 */
	public static boolean writeToFile(File file, String text) {
		try{
			StringReader reader = new StringReader(text);
			FileWriter writer = new FileWriter(file);
			char[] buff = new char[512];
			int len = buff.length;
			while( (len=reader.read(buff, 0, len))!=-1 ){
				writer.write(buff, 0, len);
				writer.flush();
			}
			writer.close();
			reader.close();
			return true;
		} catch(IOException e){
			Logger l = LoggerMgr.getLogger("andyUtils-IO");
			l.log("error while writing to the file");
			l.log(e);
			return false;
		}
	}

	//TODO writeTo[Print]Stream
	
	
	/**
	 * Copy the content of an {@link InputStream} in an {@link OutputStream}.
	 *
	 * At the end of the copy, the streams are <b>not</b> closed.
	 * 
	 * @param in stream to read
	 * @param out stream to write
	 *
	 * @throws IOException if I/O errors occour
	 */
	public static void cp(InputStream in, OutputStream out) throws IOException{
		byte[] buff = new byte[512];
		int len;
		while( (len=in.read(buff))!=-1 ){
			out.write(buff, 0, len);
		}
		out.flush();
		out.close();
		in.close();
	}

	
	/**
	 * Copy a file.
	 * 
	 * The method does not any check if the destination file exist.
	 * 
	 * @param inFile file to be copied
	 * @param outFile file in which to copy
	 * 
	 * @throws FileNotFoundException if <tt>inFile</tt> does not exist
	 * @throws IOException if I/O errors occour
	 */
	public static void cp(File inFile, File outFile) throws IOException{
		FileInputStream fileIS = new FileInputStream(inFile);
		FileOutputStream fileOS = new FileOutputStream(outFile);
		cp(fileIS, fileOS);
	}
	
	/**
	 * Write the content of the input stream on the {@link System#out}.
	 * 
	 * This method is suitable if the input stream is a character stream.
	 * 
	 * @param in an InputStream to read
	 */
	public static void print(InputStream in){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line=br.readLine())!=null)
				System.out.println(line);
		} catch(IOException e){
			Logger l = LoggerMgr.getLogger("andyUtils-IO");
			l.log("error while reading the InputStream");
			l.log(e);
		}
	}
	
	
	/**
	 * Moves a file.
	 * 
	 * At first the file is moved using the {@link File#renameTo(java.io.File)};
	 * if this fail, a copy is made and the original file is deleted.
	 * 
	 * <p>Note: if the original file can not be deleted, the method actually
	 * makes a copy.</p>
	 * 
	 * @param from file to move
	 * @param to new file position
	 * @return <tt>true</tt> if the move is completed correctly, <tt>false</tt>
	 * otherwise
	 * 
	 * @throws FileNotFoundException if file <tt>from</tt> does not exist
	 * @throws IOException if I/O errors occour
	 */
	public static boolean mv(File from, File to) throws IOException{
		if( from.renameTo(to) ){
			return true;
		}
		cp(from, to);	
		return from.delete();
	}
	
	
	/**
	 * Convert a byte in an unsigned int.
	 *
	 * The convertion is based on the bits of the byte (they are considered as a
	 * base-2 number).
	 *
	 * @param b a byte
	 * @return the int corresponding to the bits in the byte as a base-2 number
	 */
	public static int getUnsignedInt(byte b){
		return b>=0 ? b : 256+b;
	}
}
