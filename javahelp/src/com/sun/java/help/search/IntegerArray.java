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

class IntegerArray
{
  private int[] _array;
  private int _size;
  private int _free = 0;
  private static final int InitialSize = 128;
  
  public IntegerArray() {
    _array = new int[_size = InitialSize];
  }
  
  public IntegerArray(int size) {
    _array = new int[_size = size];
  }

  public void clear() {
    _free = 0;
  }

  public int at(int index) {
    return _array[index];
  }

  public void add(int value)
  {
    if (_free == _size)
      growArray(_size * 2);
    _array[_free++] = value;
  }
  
  private void growArray(int size)
  {
    int[] newArray = new int[_size = size];
    System.arraycopy(_array, 0, newArray, 0, _free);
    _array = newArray;
  }

  public int popLast() {
    return _array[--_free];
  }
  
  public int cardinality() {
    return _free;
  }
  
  public void toDifferences(IntegerArray result)
  {
    if (result._size < _size)
      result.growArray(_size);
    if ((result._free = _free) > 0)
      {
	result._array[0] = _array[0];
	for (int i = 1; i < _free; i++)
	  result._array[i] = _array[i] - _array[i - 1];
      }
  }
  
  public int indexOf(int value)
  {
    int i = 0, j = _free, k;
    while (i <= j)
      if (_array[k = (i + j)/2] < value)
	i = k + 1;
      else if (value < _array[k])
	j = k - 1;
      else
	return k;
    return -1;
  }
  
  public void print(java.io.PrintStream out)
  {
    for (int i = 0; i < _free - 1; i++)
      {
	out.print(_array[i]);
	out.print(' ');
      }
    out.println(_array[_free - 1]);
  }
}
