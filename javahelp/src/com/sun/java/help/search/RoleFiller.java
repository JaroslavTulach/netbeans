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

import java.io.PrintStream;

class RoleFiller
{
  static int Threshold = 300;

  private ConceptData _conceptData;
  private byte        _fixedRole;
  private short        _filled;
  private int        _begin;
  private int        _end;
  private int        _limit;
  private RoleFiller       _next;
  private RoleFiller[]     _fillers;
  
  public static final RoleFiller STOP = new RoleFiller();
  
  private RoleFiller() {}

  public RoleFiller(int nColumns, ConceptData first, int role,
		    int pos, int limit)
  {
    _conceptData = first;
    _fixedRole = (byte)role; // primary/constitutive concept/role
    _filled = (short)(1 << _fixedRole);
    _begin = pos;		// offset in file
    _end = _begin + first.getConceptLength();
    _limit = limit;
    _next = null;
    _fillers = new RoleFiller[nColumns];
    _fillers[role] = this;
  }

  public void print(PrintStream out) {
    out.println(_begin + ", " + _end);
  }
  
  void makeQueryHit(Query q, int nColumns, int doc, double penalty)
  {
    if (q.goodEnough(penalty))
      {
	int[] array =
	  q.getConceptArrayOfNewHit(penalty,
				    new Location(doc, _begin, _end));
	for (int i = 0; i < nColumns; i++)
	  array[i] = (_filled & 1 << i) != 0 ? _fillers[i].getConcept() : 0;
      }
  }

  boolean isHit() {
    return _filled > (1 << _fixedRole);
  }
  
  double getScore() {
    return _conceptData.getScore();
  }
  
  int getConcept() {
    return _conceptData.getConcept();
  }
  
  RoleFiller next() {
    return _next;
  }
  
  void use(RoleFiller[] place, int index)
  {
    if (place[index] != null)
      {
	RoleFiller rf = place[index];
	place[index] = this;
	_next = rf;
	while (rf._limit >= _begin)
	  {
	    // check if we can grow/improve a hit
	    // we don't ever replace filler's fixed role
	    if (_fixedRole != rf._fixedRole)
	      {
		if ((rf._filled & (1 << _fixedRole)) == 0) // not filled yet
		  {
		    rf._filled |= 1 << _fixedRole;
		    rf._fillers[_fixedRole] = this;
		    rf._end = _end;
		  }
		else
		  rf.considerReplacementWith(this);
	      }
	      
	    if (rf._next != null)
	      rf = rf._next;
	    else
	      return;
	  }
      }
    else
      place[index] = this;
  }
  
  private void considerReplacementWith(RoleFiller replacement)
  {
    // !!! simplistic for now
    // needs gap and out of order
    int role = replacement._fixedRole;
    if (replacement.getScore() > _fillers[role].getScore())
      _fillers[role] = replacement;
  }

  private double penalty(Query query, int nColumns)
  {
    int length = _end - _begin + 1;
    double penalty = query.lookupPenalty(_filled);
    // !!! here is a chance to check against query if hit worth scoring further
    // might not be if query already has lots of good hits
    for (int i = 0; i < nColumns; i++)
      if ((_filled & (1 << i)) != 0)
	{
	  penalty += _fillers[i]._conceptData.getPenalty();
	  length -= _fillers[i]._conceptData.getConceptLength() + 1;
	  if ((_filled >> (i + 1)) != 0)
	    for (int j = i + 1; j < nColumns; j++)
	      if ((_filled & 1 << j) != 0 && _fillers[j]._begin < _begin)
		penalty += query.getOutOufOrderPenalty();
	}
    return penalty + length*query.getGapPenalty();
  }
  
  public void scoreList(Query query, int document)
  {
    int nColumns = query.getNColumns();
    RoleFiller candidateHit = this; // function called for the head of list
    RoleFiller next;		// lookahead: if overlap, if so, is it better

    // 'candidateHit' always points at the current candidate to be converted to a QueryHit
    // 'penalty' is its penalty
    // 'next' is used to explore earlier overlapping fillers
    // the decision to emit a QueryHit is made when either there's no next
    // or next doesn't overlap the current candidate
    // the loop's logic makes sure that at emit time there's no better/earlier filler
    // to overlap with the candidate

    double penalty = candidateHit.penalty(query, nColumns);
  
    for (next = candidateHit._next; next != null; next = next._next)
      if (next._end < candidateHit._begin) // no overlap
	{
	  candidateHit.makeQueryHit(query, nColumns, document, penalty);
	  candidateHit = next;
	  penalty = candidateHit.penalty(query, nColumns);
	}
      else
	{
	  // !!! can be computed in two steps
	  double penalty2 = next.penalty(query, nColumns);
	  if (penalty2 <= penalty)	// prefer next, disregard candidateHit
	    {
	      penalty = penalty2;
	      candidateHit = next;
	    }
	}
    candidateHit.makeQueryHit(query, nColumns, document, penalty);
  }

}
