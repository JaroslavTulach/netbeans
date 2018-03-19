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

import java.io.IOException;

/**
 * This class can be used to read/write the contents of a RAF type files
 * (i.e. DICT (Dictionary) & POSITIONS (Positions))
 * file as part of JavaHelp Search Database. It uses RandamAccessFile for 
 * quick access to dictionary blocks (DictBlock). 
 * </p>
 * Extension of this class serve include memory resident  or unwriteable  
 * RAFFile.
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @version	1.4	10/30/06
 */

import java.net.URL;
import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.security.Permission;

final public class TemporaryRAFFile extends RAFFile {

    private RandomAccessFile raf;
    private Permission permission;

    // provides only read access.
    public TemporaryRAFFile(File file, Permission permission) throws IOException{
	debug("TemporaryRAFFile " + file);
	raf = new RandomAccessFile(file, "r");
	this.permission = permission;
    }

    public long length() throws IOException { 
	// check permission
	return raf.length();
    }

    public long getFilePointer() throws IOException {
	// check permission
	return raf.getFilePointer();
    }

    public void close() throws IOException {
	// check permission
	raf.close();
    }

    public void seek(long pos) throws IOException {
	// check permission
	raf.seek(pos);
    }

    public int readInt() throws IOException {
	// check permission
	return raf.readInt();
    }

    public int read() throws IOException {
	// check permission
	return raf.read();
    }

    public void readFully (byte b[]) throws IOException {
	// check permission
	raf.readFully(b);
    }

    public int read(byte[] b, int off, int len) throws IOException {
	// check permission
	return raf.read(b, off, len);
    }
  
    public void writeInt(int v) throws IOException {
	// check permission
	raf.writeInt(v);
    }

    public void write(byte b[]) throws IOException {
	// check permission
	raf.write(b);
    }

    /**
     * Debug code
     */

    private static final boolean debug = false;
    private static void debug(String msg) {
        if (debug) {
            System.err.println("TemporaryRAFFile: "+msg);
        }
    }
}
