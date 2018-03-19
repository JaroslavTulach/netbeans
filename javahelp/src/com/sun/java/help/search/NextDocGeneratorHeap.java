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

class NextDocGeneratorHeap
{
  private static final int InitSize = 1024;
  
  private int                _heapSize = 0;
  private int                _size = InitSize;
  private NextDocGenerator[] _heap = new NextDocGenerator[InitSize];
  private int                _free = 0;
  private boolean             _nonEmpty = false;
  
  public boolean isNonEmpty() {
    return _nonEmpty;
  }
  
  public void addGenerator(NextDocGenerator gen)
  {
    if (_free == _size)
      {
	NextDocGenerator[] newArray = new NextDocGenerator[_size *= 2];
	System.arraycopy(_heap, 0, newArray, 0, _free);
	_heap = newArray;
      }
    _heap[_free++] = gen;
  }
  
  public void start()
  {
    if ((_heapSize = _free) > 0)
      {
	// build heap
	for (int i = _heapSize/2; i >= 0; i--)
	  heapify(i);
	_nonEmpty = true;
      }
    else
      _nonEmpty = false;
  }
  
  public void step() throws Exception
  {
    if (_heap[0].next() != NonnegativeIntegerGenerator.END)
      heapify(0);
    else if (_heapSize > 1)
      {
	_heap[0] = _heap[--_heapSize];
	heapify(0);
      }
    else
      _nonEmpty = false;
  }
  
  public int getDocument() {
    return _heap[0].getDocument();
  }
  
  public int getConcept() {
    return _heap[0].getConcept();
  }
  
  public ConceptData getTerms() {
    return _heap[0].getTerms();
  }
  
  public int getQueryMask() {
    return _heap[0].getQueryMask();
  }
  
  public void reset()
  {
    _nonEmpty = false;
    _free = 0;
  }
  
  public boolean atDocument(int document) {
    return _nonEmpty && _heap[0].getDocument() == document;
  }
  
  private void heapify(int i)
  {
    int r = (i + 1) << 1, l = r - 1;
    int smallest = l < _heapSize && _heap[l].smallerThan(_heap[i]) ? l : i;
    if (r < _heapSize && _heap[r].smallerThan(_heap[smallest]))
      smallest = r;
    if (smallest != i)
      {
	NextDocGenerator temp = _heap[smallest];
	_heap[smallest] = _heap[i];
	_heap[i] = temp;
	heapify(smallest);
      }
  }
}
