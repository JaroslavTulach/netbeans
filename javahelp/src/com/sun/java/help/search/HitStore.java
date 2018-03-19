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

import java.util.Vector;

class HitStoreNode
{
  private int           _free = 0;
  private int           _size;
  private QueryHit[]    _array;
  private int           _hitCount = 0;
  private double         _divider = 0.0;
  private double         _min = 10000000.0;
  private double         _max = 0.0;
  private HitStoreNode[] _children = new HitStoreNode[2]; // left & right
  private int           _index = 0;

  public HitStoreNode(int size) {
    _array = new QueryHit[_size = size];
  }
  
  public QueryHit getNextHit() {
    return _index < _free ? _array[_index++] : null;
  }
  
  private void fastAdd(QueryHit hit)
  {
    _hitCount++;
    _divider += hit.getScore();
    if (hit.getScore() > _max)
      _max = hit.getScore();
    if (hit.getScore() < _min)
      _min = hit.getScore();
    _array[_free++] = hit;
  }

  public boolean add(QueryHit hit)
  {
    if (_array != null)
      {
	if (_free < _size)
	  {
	    fastAdd(hit);
	    return false;
	  }
	else if (_min != _max)
	  {
	    split();
	    _hitCount++;
	    _children[hit.getScore() > _divider ? 1 : 0].fastAdd(hit);
	    return true;
	  }
	else
	  {
	    QueryHit[] newArray = new QueryHit[_size *= 2];
	    System.arraycopy(_array, 0, newArray, 0, _free);
	    _array = newArray;
	    fastAdd(hit);
	    return true;
	  }
      }
    else
      {
	_hitCount++;
	return _children[hit.getScore() > _divider ? 1 : 0].add(hit);
      }
  }

  private void split()
  {
    _children[0] = new HitStoreNode(_size);
    _children[1] = new HitStoreNode(_size);
    _divider /= _hitCount;	// becomes average
    for (int i = 0; i < _free; i++)
      _children[_array[i].getScore() > _divider ? 1 : 0].fastAdd(_array[i]);
    _array = null;			// becomes internal
  }
  public int getCount() {
    return _hitCount;
  }
  public double getDivider() {
    return _divider;
  }
  public HitStoreNode getLChild() {
    return _children[0];
  }
  public HitStoreNode getRChild() {
    return _children[1];
  }
  public void setLChild(HitStoreNode node) {
    _children[0] = node;
  }
  public void setRChild(HitStoreNode node) {
    _children[1] = node;
  }
  public void decrementCount(int delta) {
    _hitCount -= delta;
  }
  public boolean isLeaf() {
    return _array != null;
  }
  
  public void sort() {
    quicksort(0, _free - 1);
  }

  public void gatherLeaves(Vector vector)
  {
    if (isLeaf())
      vector.addElement(this);
    else
      {
	getLChild().gatherLeaves(vector);
	getRChild().gatherLeaves(vector);
      }
  }
  
  // part of quicksearch
  private int partition(int p, int r)
  {
    QueryHit x = _array[p];
    int i = p - 1, j = r + 1;
    while (true)
      {
	while (x.betterThan(_array[--j]))
	  ;
	while (_array[++i].betterThan(x))
	  ;
	if (i < j)
	  {
	    QueryHit t = _array[i];
	    _array[i] = _array[j];
	    _array[j] = t;
	  }
	else
	  return j;
      }
  }

  private void quicksort(int p, int r)
  {
    if (p < r)
      {
	int q = partition(p, r);
	quicksort(p, q);
	quicksort(q + 1, r);
      }
  }
}


class HitStore
{
  private static final int ArraySize = 128;
  private static final int DefaultLimit = 300;
  
  private HitStoreNode _root = new HitStoreNode(ArraySize);
  private int           _limit;
  private double        _standard;
  private Vector       _leaves = null;
  private HitStoreNode _current = null;

  public HitStore(double initialStandard) {
    this(initialStandard, DefaultLimit);
  }
  
  public HitStore(double initialStandard, int limit)
  {
    _limit = limit;
    _standard = initialStandard;
  }
  
  public void addQueryHit(QueryHit hit) {
    if(_root.add(hit))
      adapt();
  }
  
  private HitStoreNode getNextNode()
  {
    if (_leaves.size() > 0)
      {
	HitStoreNode node = (HitStoreNode)_leaves.firstElement();
	_leaves.removeElementAt(0);
	node.sort();
	return node;
      }
    else
      return null;
  }

  public QueryHit firstBestQueryHit()
  {
    _leaves = new Vector();
    _root.gatherLeaves(_leaves);
    _current = getNextNode();
    return _current.getNextHit();
  }
  
  public QueryHit nextBestQueryHit()
  {
    QueryHit result = _current.getNextHit();
    if (result != null)
      return result;
    else if ((_current = getNextNode()) != null)
      return _current.getNextHit();
    else
      return null;
  }
  
  double getCurrentStandard() {
    return _standard;
  }
  
  private void adapt()
  {
    if (_root.getCount() > _limit)
      if (!_root.isLeaf())
	{
	  HitStoreNode ptr = _root;
	  // find rightmost internal
	  while (!ptr.getRChild().isLeaf())
	    ptr = ptr.getRChild();
      
	  _standard = ptr.getDivider();
      
	  if (ptr == _root)
	    _root = ptr.getLChild();
	  else
	    {
	      int count = ptr.getRChild().getCount();
	      HitStoreNode ptr2 = _root;
	      while (ptr2.getRChild() != ptr)
		{
		  ptr2.decrementCount(count);
		  ptr2 = ptr2.getRChild();
		}
	      ptr2.setRChild(ptr.getLChild());
	    }
	  ptr.setLChild(null);
	}
  }
}
