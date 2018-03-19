/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.sun.java.help.search;

import java.io.*;
import java.net.URLConnection;
import java.security.Permission;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.security.PrivilegedActionException;

/**
 * This Factory assumes we are on JDK 1.2
 */

final public class RAFFileFactoryOn12 {
    /**
     * Creata a RAFFile from a URLConnection.  Try to use a temporary file
     * if possible.  This is a static public method
     *
     * @param url The URL with the data to the file
     * @return the RAFFile for this data
     * @exception IOException if there is problem reading from the file
     */
    public static RAFFile get(final URLConnection connection) throws IOException {
	RAFFile topBack = null;

	debug("get on "+connection);
	final Permission permission = connection.getPermission();
	
	/* get the permission after the connection is established */
	int dictLength = connection.getContentLength();
	
	try { 
	    topBack = (RAFFile)
		AccessController.doPrivileged(new PrivilegedExceptionAction() {
		    
		    RAFFile back = null;
		    public Object run() throws IOException {
			InputStream in = null;
			OutputStream out = null;
			try {
			    File tmpFile = File.createTempFile("dict_cache", null);
			    tmpFile.deleteOnExit();

			    if (tmpFile != null) {
				in = connection.getInputStream();
				out  = new FileOutputStream(tmpFile);
				int read = 0;
				byte[] buf = new byte[BUF_SIZE];
				while ((read = in.read(buf)) != -1) {
				    out.write(buf, 0, read);
				}
				back = new TemporaryRAFFile(tmpFile, permission);
			    }  else {
				back = new MemoryRAFFile(connection);
			    }
			} finally {
			    if (in != null) {
				in.close();
			    }
			    if (out != null) {
				out.close();
			    }
			}
			return back;
		    }
		});
	} catch (PrivilegedActionException pae) {
	    topBack = new MemoryRAFFile(connection);
	} catch (SecurityException se) {
	    topBack = new MemoryRAFFile(connection);
	}
	return topBack;
    }

    // The size of the buffer
    private static int BUF_SIZE = 2048;

    /**
     * Debug code
     */

    private static final boolean debug = false;
    private static void debug(String msg) {
        if (debug) {
            System.err.println("RAFFileFactoryOn12: "+msg);
        }
    }
}

