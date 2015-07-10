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
import java.util.zip.*;


/**
 * Useful methods to manage compressed files.
 * 
 * @author andynaz
 * @version 2014/04/29
 */
public class UtilsZIP {
	// hides the contructors
	private UtilsZIP(){}
	
	/**
	 * Compresses a file.
	 * 
	 * The compressed file has the same name of the original file, with the
	 * {@code .zip} extension added. The file is create di the same posizion of
	 * the original file.
	 * 
	 * @param file file to be compressed
	 * @return the compressed file
	 * 
	 * @throws NullPointerException if {@code file==null}
	 * @throws FileNotFoundException if the file does not exist
	 * @throws IOException if I/O errors occour
	 * 
	 * @see <a href="http://www.bits4beats.it/java/creare-un-file-zip-in-java/"
	 *    target="_blank">bits4beats</a>
	 */
	public static File createZip(File file) throws IOException {
		if( file==null )
			throw new NullPointerException("null file");
			
		File zipFile = null;
		FileInputStream fileIS = null;
		FileOutputStream fileOS = null;
		ZipOutputStream zipOS = null;

		byte data[] = new byte[1024];

		try {
			fileIS = new FileInputStream(file);
			
			zipFile = new File(file.getParent(), file.getName()+".zip");
			fileOS = new  FileOutputStream(zipFile);
			zipOS = new ZipOutputStream(new BufferedOutputStream(fileOS));
			
			/* add zip entry */
			ZipEntry entry = new ZipEntry(file.getName());
			zipOS.putNextEntry(entry);

			int count;
			while( (count=fileIS.read(data, 0, 1024))!=-1 ){
				zipOS.write(data, 0, count);
			}
			zipOS.closeEntry();
		} finally {
			if (zipOS!=null) zipOS.close();
			if (fileIS!=null) fileIS.close();
			return zipFile;
		}
	}

}
