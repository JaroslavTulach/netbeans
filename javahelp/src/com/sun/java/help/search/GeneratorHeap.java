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

class GeneratorHeap
{
  private static final int        InitSize = 128;
  private int                     _heapSize = 0;
  private ConceptGroupGenerator[] _heap;
  private int                     _size = InitSize;
  private int                     _free = 0;
  
  public GeneratorHeap() {
    _heap = new ConceptGroupGenerator[InitSize];
  }
  
  public void reset() {
    _free = 0;
  }
  
  public void addGenerator(ConceptGroupGenerator gen)
  {
    if (_free == _size)
      {
	ConceptGroupGenerator[] newArray =
	  new ConceptGroupGenerator[_size *= 2];
	System.arraycopy(_heap, 0, newArray, 0, _free);
	_heap = newArray;
      }
    _heap[_free++] = gen;
  }

  private void buildHeap()
  {
    for (int i = _heapSize/2; i >= 0; i--)
      heapify(i);
  }

  private void heapify(int i)
  {
    int r = (i + 1) << 1, l = r - 1;
    int smallest = l<_heapSize&&_heap[l].position()<_heap[i].position()?l:i;
  
    if (r < _heapSize && _heap[r].position() < _heap[smallest].position())
      smallest = r;
    if (smallest != i)
      {
	ConceptGroupGenerator temp = _heap[smallest];
	_heap[smallest] = _heap[i];
	_heap[i] = temp;
	heapify(smallest);
      }
  }

  public boolean start(RoleFiller[] array) throws Exception
  {
    if ((_heapSize = _free) > 0)
      {
	for (int i = 0; i < _free; i++)
	  _heap[i].next();
	buildHeap();
	_heap[0].generateFillers(array);
	return true;
      }
    else
      return false;
  }

  public boolean next(RoleFiller[] array) throws Exception
  {
    if (_heapSize > 0)
      {
	if (!_heap[0].next()) // no more
	  if (_heapSize > 1)
	    _heap[0] = _heap[--_heapSize];
	  else
	    {
	      _heapSize = 0;
	      return false;
	    }
	heapify(0);
	_heap[0].generateFillers(array);
	return true;
      }
    else
      return false;
  }
}
