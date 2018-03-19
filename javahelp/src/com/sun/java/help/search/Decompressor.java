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

abstract class Decompressor
{
  private static final int BitsInByte = 8;
  private static final int NBits = 32;
  
  private int _readByte;
  private int _toRead = 0;
  private int _path = 0;

  abstract protected int getNextByte() throws Exception;

  protected void initReading() {
    _toRead = 0;
  }
    
  private int countZeroes() throws Exception
  {
    for (int count = 0;; _readByte = getNextByte(), _toRead = BitsInByte)
      while (_toRead-- > 0)
	if ((_readByte & (1 << _toRead)) != 0)
	  return count;
	else
	  ++count;
  }
  
  // reads 1 bit; returns non-0 for bit "1"
  private int read() throws Exception
  {
    if (_toRead-- > 0)
      return _readByte & (1 << _toRead);
    else
      {  // get next word
	_toRead = BitsInByte - 1;
	return (_readByte = getNextByte()) & 0x80;
      }
  }
  
  public int read(int kBits) throws Exception
  {
    int shift = BitsInByte - _toRead;
    if (kBits <= _toRead)
      return ((_readByte<<shift) & 0xFF) >>> (shift + (_toRead-=kBits));
    else
      {
	int result = _toRead > 0
	  ? ((_readByte << shift) & 0xFF) >>> shift
	  : 0;
	for (kBits -= _toRead; kBits >= BitsInByte; kBits -= BitsInByte)
	  result = (result << BitsInByte) | getNextByte();
	if (kBits > 0)
	  return (result << kBits)
	    | ((_readByte = getNextByte()) >>> (_toRead = BitsInByte - kBits));
	else
	  {
	    _toRead = 0;
	    return result;
	  }
      }
  }
  
  public void beginIteration() {
    _path = 0;
  }

  public boolean readNext(int k, CompressorIterator it) throws Exception
  {
    if (read() != 0)
      {
	it.value(_path | read(k));
	return true;
      }
    else
      for (int count = 1;; _readByte = getNextByte(), _toRead = BitsInByte)
	while (_toRead-- > 0)
	  if ((_readByte & (1 << _toRead)) != 0)
	    {
	      int saved = _path;
	      _path = ((_path >>> (k + count) << count) | read(count)) << k;
	      if (_path != saved)
		{
		  it.value(_path | read(k));
		  return true;
		}
	      else
		return false;
	    }
	  else
	    ++count;
  }
  
  public void decode(int k, IntegerArray array) throws Exception
  {
    for (int path = 0;;)
      if (read() != 0)
	array.add(path | read(k));
      else
	{
	  int count = countZeroes() + 1;
	  int saved = path;
	  path = ((path >>> (k + count) << count) | read(count)) << k;
	  if (path != saved)	// convention for end
	    array.add(path | read(k));
	  else
	    break;
	}
  }

  public void ascDecode(int k, IntegerArray array) throws Exception
  {
    for (int path = 0, start = 0;;)
      if (read() != 0)
	array.add(start += path | read(k));
      else
	{
	  int count = countZeroes() + 1;
	  int saved = path;
	  path = ((path >>> (k + count) << count) | read(count)) << k;
	  if (path != saved)	// convention for end
	    array.add(start += path | read(k));
	  else
	    break;
	}
  }
  
  public int ascendingDecode(int k, int start, int[] array) throws Exception
  {
    int path = 0, index = 0;
  LOOP:
    while (true)
      if (read() != 0)
	array[index++] = (start += path | read(k));
      else
	for (int cnt = 0;; _readByte = getNextByte(), _toRead = BitsInByte)
	  while (_toRead-- > 0)
	    if ((_readByte & (1 << _toRead)) != 0)
	      {
		++cnt;
		int Path = ((path >>> (k + cnt) << cnt) | read(cnt)) << k;
		if (Path != path)
		  {
		    array[index++] = (start += (path = Path) | read(k));
		    continue LOOP;
		  }
		else
		  return index;
	      }
	    else
	      ++cnt;
  }
}
