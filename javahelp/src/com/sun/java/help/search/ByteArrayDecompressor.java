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

class ByteArrayDecompressor extends Decompressor
{
  private byte[] _array;
  private int    _index;
  private int    _index0;
  
  public ByteArrayDecompressor(byte[] array, int index) {
    initReading(array, index);
  }

  public void initReading(byte[] array, int index)
  {
    _array = array;
    _index = _index0 = index;
    initReading();
  }

  public int bytesRead() {
    return _index - _index0;
  }

  protected int getNextByte() throws Exception {
    return _array[_index++] & 0xFF;
  }
}
