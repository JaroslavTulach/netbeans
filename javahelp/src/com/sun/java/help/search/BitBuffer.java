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

class BitBuffer
{
  private static final int InitSize   = 256;
  private static final int NBits      =  32;
  private static final int BitsInByte =   8;
  private static final int BytesInInt =   4;
  
  private int   _avail = NBits;
  private int   _word = 0;
  private int   _free = 0;
  private int   _size = InitSize;
  private int[] _array = new int[InitSize];

  public void close()
  {
    if (_avail < NBits)
      store(_word << _avail);
    else
      _avail = 0;
  }
  
  public void write(DataOutput out) throws IOException
  {
    for (int i = 0; i < _free - 1; i++)
      out.writeInt(_array[i]);
    int word = _array[_free - 1];
    int bytes = BytesInInt - _avail/BitsInByte;
    int shift = NBits;
    while (bytes-- > 0)
      out.writeByte((word >>> (shift -= BitsInByte)) & 0xFF);
  }

  public void clear()
  {
    _word   = 0;
    _avail  = NBits;
    _free   = 0;
  }
  
  public int byteCount() {
    return _free*BytesInInt - _avail/BitsInByte;
  }
  
  public int bitCount() {
    return NBits*_free - _avail;
  }

  public void setFrom(BitBuffer rhs)
  {
    _word  = rhs._word;
    _avail = rhs._avail;
    if ((_free = rhs._free) > _size)
      _array = new int[_size = rhs._free];
    System.arraycopy(rhs._array, 0, _array, 0, _free);
  }
  
  private void growArray(int newSize)
  {
    int[] newArray = new int[_size = newSize];
    System.arraycopy(_array, 0, newArray, 0, _free);
    _array = newArray;
  }

  private void store(int value)
  {
    if (_free == _size)
      growArray(_size * 2);
    _array[_free++] = value;
  }

  public void append(int bit)
  {
    _word = (_word << 1) | bit;
    if (--_avail == 0)
      {
	store(_word);
	_word = 0;
	_avail = NBits;
      }
  }
  
  public void append(int source, int kBits)
  {
    if (kBits < _avail)
      {
	_word = (_word << kBits) | source;
	_avail -= kBits;
      }
    else if (kBits > _avail)
      {
	int leftover = kBits - _avail;
	store((_word << _avail) | (source >>> leftover));
	_word = source;
	_avail = NBits - leftover;
      }
    else
      {
	store((_word << kBits) | source);
	_word = 0;
	_avail = NBits;
      }
  }
  
  public void concatenate(BitBuffer bb)
  {
    if (NBits*(_size - _free) + _avail < bb.bitCount())
      growArray(_free + bb._free + 1);

    if (_avail == 0)
      {
	System.arraycopy(bb._array, 0, _array, _free, bb._free);
	_avail = bb._avail;
	_free += bb._free;
      }
    else
      {
	int tp = _free - 1;	// target
	int sp = 0;		// source
	do {
	  _array[tp++] |= bb._array[sp] >>> (NBits - _avail);
	  _array[tp] = bb._array[sp++] << _avail;
	}
	while (sp < bb._free);
	_free += bb._free;
	if ((_avail += bb._avail) >= NBits)
	  {
	    _avail -= NBits;
	    _free--;
	  }
      }
  }
}
