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
 * This class represents a Block of information in DictBlock
 *
 * @author Jacek Ambroziak
 * @author Roger D. Brinkley
 * @version	1.3	03/20/98
 */

class Block
{
  public static final int HEADERLEN = 8;
  public static final int IDLEN = 4;
  public int number;
  public boolean isLeaf = true;
  public int free = 0;
  public byte[] data = null;
  
  public Block(int blocksize) {
    data = new byte[blocksize - HEADERLEN];
  }

  public void setBlockNumber(int n) {
    number = n;
  }

  public void setFree(int free) {
    free = free;
  }
  
  public int integerAt(int i) {
    return ((((((data[i++]&0xFF)<<8)
	       |data[i++]&0xFF)<<8)
	     |data[i++]&0xFF)<<8)
      |data[i]&0xFF;
  }
  
  public void setIntegerAt(int i, int value)
  {
    for (int j = i + 3; j >= i; j--, value >>>= 8)
      data[j] = (byte)(value & 0xFF);
  }

  public static Block readIn(RAFFile in, Block block)
    throws IOException
  {
    debug("readIn");
    block.number = in.readInt();
    int twoFields = in.readInt();
    block.isLeaf = (twoFields & 0x80000000) != 0;
    block.free = twoFields & 0x7FFFFFFF;
    in.readFully(block.data);
    return block;
  }
  
  public void writeOut(RAFFile out) throws IOException
  {
    out.writeInt(number);
    out.writeInt(free | (isLeaf ? 0x80000000 : 0));
    out.write(data);
  }

  /**
   * Debug code
   */

  private static boolean debug=false;
  private static void debug(String msg) {
    if (debug) {
      System.err.println("Block: "+msg);
    }
  }

}

