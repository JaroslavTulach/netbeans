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
 * A DICT (Dictionary) file cached in memory.
 *
 * @author Roger D. Brinkley
 * @version	1.12	10/30/06
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;

public class MemoryRAFFile extends RAFFile {

    private URL url;
    private byte[] data;
    private int size;
    private int filePointer;

    public MemoryRAFFile(URLConnection connection) throws IOException {
	this.url = connection.getURL();

	InputStream in = new BufferedInputStream(connection.getInputStream());
	ByteArrayOutputStream data = new ByteArrayOutputStream();

	byte[] buf = new byte[512];
	int i = 0;
	while((i = in.read(buf)) != -1) {
	    data.write(buf, 0, i);
	}
	this.data = data.toByteArray();
	size = data.size();
	filePointer = 0;
    }

    public long length() { 
	return size;
    }

    public void close() throws IOException {
	filePointer = 0;
	data = null;
	size = 0;
    }

    public long getFilePointer() throws IOException {
	return filePointer;
    }

    public void seek(long pos) throws IOException {
	if (pos > size) {
	    throw new IOException();
	}
	filePointer = (int)pos;
    }

    public int read() throws IOException {
	if (filePointer >= size) {
	    return -1;
	}
	filePointer += 1;
	return data[filePointer - 1] & 0xFF;
    }

    private int readBytes(byte b[], int off, int len) throws IOException {
	debug ("readBytes");
	if (filePointer + off + len > size) {
	    throw new IOException();
	}
	filePointer += off;
	System.arraycopy(data, filePointer, b, 0, (int)len);
	filePointer += len;
	return len;
    }

    public int read(byte b[], int off, int len) throws IOException {
	return readBytes(b, off, len);
    }

    public int readInt() throws IOException {
	debug ("readInt");
	int ch1 = this.read();
	int ch2 = this.read();
	int ch3 = this.read();
	int ch4 = this.read();
	if ((ch1 | ch2 | ch3 | ch4) < 0)
	    throw new EOFException();
	return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }

    public final void readFully(byte b[]) throws IOException {
	readFully(b, 0, b.length);
    }

    private void readFully (byte b[], int off, int len) 
	throws IOException {
        int n = 0;
	do {
	    int count = this.read(b, off + n, len - n);
	    if (count < 0)
		throw new EOFException();
	    n += count;
	} while (n < len);
    }

    public void writeInt(int v) throws IOException {
	throw new IOException("Unsupported Operation");
    }

    public void write(byte b[]) throws IOException {
	throw new IOException("Unsupported Operation");
    }
    /**
     * For printf debugging.
     */
    private static boolean debugFlag = false;
    private static void debug(String str) {
        if( debugFlag ) {
            System.out.println("MemoryRAFFile: " + str);
        }
    }
}
