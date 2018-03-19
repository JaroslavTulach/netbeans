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

class ConceptLocation
{
  private int _concept;
  private int _begin;
  private int _end;
  
  private abstract class ConceptLocationSorter
  {
    abstract public boolean smallerThan(ConceptLocation a, ConceptLocation b);
    // part of quicksearch
    private int partition(ConceptLocation[] array, int p, int r)
    {
      ConceptLocation x = array[p];
      int i = p - 1, j = r + 1;
      while (true)
	{
	  while (smallerThan(x, array[--j]))
	    ;
	  while (smallerThan(array[++i], x))
	    ;
	  if (i < j)
	    {
	      ConceptLocation t = array[i];
	      array[i] = array[j];
	      array[j] = t;
	    }
	  else
	    return j;
	}
    }

    public void quicksort(ConceptLocation[] array, int p, int r)
    {
      if (p < r)
	{
	  int q = partition(array, p, r);
	  quicksort(array, p, q);
	  quicksort(array, q + 1, r);
	}
    }
  }

  private class ConceptSorter extends ConceptLocationSorter
  {
    public boolean smallerThan(ConceptLocation a, ConceptLocation b) {
      return a._concept < b._concept;
    }
  }
  
  private class PositionSorter extends ConceptLocationSorter
  {
    public boolean smallerThan(ConceptLocation a, ConceptLocation b) {
      return a._begin < b._begin || a._begin == b._begin && a._end < b._end;
    }
  }
  // sorter by concept ID
  private static ConceptLocationSorter _cComp;
  // sorter by position
  private static ConceptLocationSorter _pComp;

  private ConceptLocation()
  {
    _cComp = new ConceptSorter();
    _pComp = new PositionSorter();
  }

  static { new ConceptLocation(); }

  public ConceptLocation(int conceptID, int begin, int end)
  {
    _concept = conceptID;
    _begin = begin;
    _end = end;
  }
  
  public boolean equals(ConceptLocation other) {
    return _concept==other._concept&&_begin==other._begin&&_end==other._end;
  }

  public void setConcept(int concept) {
    _concept = concept;
  }
  
  public int getConcept() {
    return _concept;
  }
  
  public int getBegin() {
    return _begin;
  }
  
  public int getEnd() {
    return _end;
  }
  
  public int getLength() {
    return _end - _begin;
  }
  
  public static void sortByConcept(ConceptLocation[] array, int i1, int i2) {
    _cComp.quicksort(array, i1, i2 - 1);
  }
  
  public static void sortByPosition(ConceptLocation[] array, int i1, int i2) {
    _pComp.quicksort(array, i1, i2 - 1);
  }
  
  public void print() {
    System.out.println(_concept+"\t"+_begin+"\t"+_end);
  }

  public static void main(String[] args)
  {
    int limit = 30, b;
    ConceptLocation[] array = new ConceptLocation[limit];
    for (int i = 0; i < limit; i++)
      array[i] = new ConceptLocation((int)(Math.random()*1000),
				     b = (int)(Math.random()*1000),
				     b + (int)(Math.random()*10));
    for (int i = 0; i < limit; i++)
      array[i].print();
    ConceptLocation.sortByConcept(array, 0, limit);
    System.out.println("----------------------------------");
    for (int i = 0; i < limit; i++)
      array[i].print();
    ConceptLocation.sortByPosition(array, 0, limit);
    System.out.println("----------------------------------");
    for (int i = 0; i < limit; i++)
      array[i].print();
  }
}

