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
 * @version	1.14	10/30/06
 */

import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;

public class RAFFile {
    private RandomAccessFile raf;

    protected RAFFile() {
    }

    public RAFFile(File file, boolean update) throws IOException {
	debug("RAFFile " + file);
	raf = new RandomAccessFile(file, update?"rw":"r");
    }

    public long length() throws IOException { 
	return raf.length();
    }

    public long getFilePointer() throws IOException {
	return raf.getFilePointer();
    }

    public void close() throws IOException {
	raf.close();
    }

    public void seek(long pos) throws IOException {
	raf.seek(pos);
    }

    public int readInt() throws IOException {
	return raf.readInt();
    }

    public int read() throws IOException {
	return raf.read();
    }

    public void readFully (byte b[]) throws IOException {
	raf.readFully(b);
    }

    public int read(byte[] b, int off, int len) throws IOException {
	return raf.read(b, off, len);
    }
  
    public void writeInt(int v) throws IOException {
	raf.writeInt(v);
    }

    public void write(byte b[]) throws IOException {
	raf.write(b);
    }

    /**
     * Debug code
     */

    private static final boolean debug = false;
    private static void debug(String msg) {
        if (debug) {
            System.err.println("RAFFile: "+msg);
        }
    }
}
