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

class Entry
{
  public byte[] key;
  public int    id;
  public int    block = -1;
  
  public Entry(byte[] key, int length, int id)
  {
    this.key = new byte[length + 1];
    System.arraycopy(key, 0, this.key, 0, length);
    this.key[length] = 0;
    this.id = id;
  }
  
  public byte[] getKey() {
    return key;
  }

  public int getID() {
    return id;
  }

  public boolean smallerThan(Entry other)
  {
    for (int i = 0; i < Math.min(key.length, other.key.length); i++)
      if (key[i] != other.key[i])
	return (key[i]&0xFF) - (other.key[i]&0xFF) < 0;
    return false;
  }
}
