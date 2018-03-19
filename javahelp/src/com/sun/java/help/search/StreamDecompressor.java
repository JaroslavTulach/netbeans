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

class StreamDecompressor extends Decompressor
{
  private InputStream _input;
  
  public StreamDecompressor(InputStream input) {
    initReading(input);
  }

  public void initReading(InputStream input)
  {
    _input = input;
    initReading();
  }
  
  public int getNextByte() throws java.io.IOException {
    return _input.read();
  }

  public static void main(String[] args)
  {
    try {
      FileInputStream file = new FileInputStream(args[0]);
      try {
	int k1 = file.read();
	long start = System.currentTimeMillis();
	System.out.println("k1 = " + k1);
	IntegerArray concepts = new IntegerArray();
	StreamDecompressor documents = new StreamDecompressor(file);
	try {
	  documents.ascDecode(k1, concepts);
	}
	catch (Exception e) {
	  System.err.println(e);
	}
	System.out.println("index1 = " + concepts.cardinality());
	int k2 = file.read();
	System.out.println("k2 = " + k2);
	IntegerArray offs = new IntegerArray(concepts.cardinality());
	StreamDecompressor offsets = new StreamDecompressor(file);
	try {
	  offsets.decode(k2, offs);
	}
	catch (Exception e) {
	  System.err.println(e);
	}
	System.out.println("index2 = " + offs.cardinality());
	System.out.println((System.currentTimeMillis() - start) + " msec");
	file.close();
      }
      catch (IOException e) {
	System.err.println(e);
      }
    }
    catch (FileNotFoundException e) {
      System.err.println(e);
    }
  }
}
