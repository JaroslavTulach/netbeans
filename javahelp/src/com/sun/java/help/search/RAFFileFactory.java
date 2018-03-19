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
import java.net.*;
import java.lang.reflect.*;

/**
 * A factory for files that can be accessed as Random Access file from a URL. 
 * RAFFiles can either be an acutall RAF file or a memory version of the
 * RAF file. When a URL is it is opened as a RAF file, otherwise it is moved
 * to a temporary file if possible or into a memory resident version.
 * 
 *
 * @author Roger D. Brinkley
 * @version	1.27	10/30/06
 */
class RAFFileFactory {

    /**
     * Cannot create these, need to go through the factory method
     */
    private RAFFileFactory() {
    }

    static RAFFileFactory theFactory;

    public static synchronized RAFFileFactory create() {
	if (theFactory == null) {
	    theFactory = new RAFFileFactory();
	}
	return theFactory;
    }

    /* For this connection, when to cache in memory and when to disk */
    private int memoryCacheLimit = 10000;

    private boolean isFileURL(URL url) {
	return url.getProtocol().equalsIgnoreCase("file");
    }

    public int getMemoryCacheLimit() {
	return memoryCacheLimit;
    }

    public void setMemoryCacheLimit(int limit) {
	this.memoryCacheLimit = limit;
    }

    public synchronized RAFFile get(URL url, boolean update) 
	throws IOException {

	RAFFile result = null;

	if (isFileURL(url)) {
	    try {
        File f = new File(url.toURI());

		// refactor so it runs with verification on...

		// Object o = new FilePermission(f, update ? "write":"read");
		// here -- check if AccessController.checkPermission(p);

		result =  new RAFFile(f, update);
		debug ("Opened Dict file with file protocol:" +  f);
	    } catch (SecurityException e) {
		// cannot do "it" -- code is not yet in place
	    } catch (URISyntaxException x) {
            throw (IOException) new IOException(x.toString()).initCause(x);
        }
	}
	if (result == null) {
	    result = createLocalRAFFile(url);
	}
	if (result == null) {
	    throw new FileNotFoundException(url.toString());
	}
	return result;
    }


    /** 
     * Given a URL, retrieves a DICT file and creates a cached DICT
     * file object. If the file is larger than the size limit,
     * and if temp files are supported by the Java virtual machine,
     * the DICT file is it is cached to disk. Otherwise the DICT file 
     * is cached in memory.
     */
    private static RAFFile createLocalRAFFile(URL url) throws IOException {

	RAFFile result = null;
	URLConnection connection = url.openConnection();

	// We should be able to just do a catch on missing method but
	// IE4.0 does not like this
	//
	//	try {
	//	    Object foo = connection.getPermission();
	//
	//	    result = RAFFileFactoryOn12.get(connection);
	//
	//	} catch (NoSuchMethodError ex) {
	//	    // on 1.1 all we can do is create a memory file
	//	    result = new MemoryRAFFile(connection);
	//	    debug ("Opening a Dict file in Memory");
	//	}
	//

	try {
	    Class types[] = {};
	    Method m = URLConnection.class.getMethod("getPermission", types);
	    result = RAFFileFactoryOn12.get(connection);
	} catch (NoSuchMethodError ex) {
	    // as in JDK1.1
	} catch (NoSuchMethodException ex) {
	    // as in JDK1.1
	}

	if (result == null) {
	    // on 1.1 all we can do is create a memory file
	    result = new MemoryRAFFile(connection);
	    debug ("Opening a Dict file in Memory");
	}
	return result;
    }

    /**
     * Debug code
     */

    private static final boolean debug = false;
    private static void debug(String msg) {
        if (debug) {
            System.err.println("RAFFileFactory: "+msg);
        }
    }
}
