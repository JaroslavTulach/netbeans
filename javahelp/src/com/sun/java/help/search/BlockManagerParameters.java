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

import java.net.URL;

/**
 * This class contains the parameters necessary for Dict BlockManager
 *
 * @date   2/3/98
 * @author Jacek R. Ambroziak
 * @author Roger D. Brinkley
 */

class BlockManagerParameters extends DBPartParameters
{
  private URL url;

  private int blockSize;
  protected int root;

  public BlockManagerParameters(Schema schema, String partName)
    throws Exception
  {
    super(schema, partName);
    url = schema.getURL(partName);
    debug(url.toString());
  }
  
  public boolean readState()
  {
    if (parametersKnown())
      {
	blockSize = integerParameter("bs");
	//	System.err.println("blockSize " + blockSize);
	root = integerParameter("rt");
	return true;
      }
    else
      return false;
  }

  public void updateSchema(String params) {
    super.updateSchema("bs="+blockSize+" rt="+root+" fl=-1 " + params);
  }

  public BlockManagerParameters(URL url, int blockSize, int root)
  {
    this.url = url;
    this.blockSize = blockSize;
    this.root = root;
  }

  public URL getURL() {
    return url;
  }

  public int getBlockSize() {
    return blockSize;
  }

  public void setBlockSize(int size) {
    blockSize = size;
  }

  public int getRootPosition() {
    return root;
  }

  public void setRoot(int root) {
    this.root = root;
  }
  /**
   * Debug code
   */

  private boolean debug=false;
  private void debug(String msg) {
    if (debug) {
      System.err.println("Block Manager Parameters: "+msg);
    }
  }
}
