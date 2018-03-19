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

class ConceptData
{
  private final byte    _query;
  private final byte    _nColumns;
  private final byte    _role;
  private final int    _concept;
  private int          _conceptLength;
  private final int    _proximity;
  private final double _penalty;
  private ConceptData _next;
  
  private static final int ProxPerTerm = 100;
  
  public ConceptData(int id, int role, double score, int query, int nColumns)
  {
    _query = (byte)query;
    _nColumns = (byte)nColumns;
    _concept = id;
    _proximity = nColumns * ProxPerTerm;
    _role = (byte)role;
    _penalty = score;
    _next = null;
  }
  
  public int getConcept() {
    return _concept;
  }
  
  public double getPenalty() {
    return _penalty;
  }
  
  public int getConceptLength() {
    return _conceptLength;
  }
  
  public byte getRole() {
    return _role;
  }

  public byte getQuery() {
    return _query;
  }

  public byte getNColumns() {
    return _nColumns;
  }

  public double getScore() {
    return _penalty;
  }
  
  public ConceptData getNext() {
    return _next;
  }
  
  public int getQueryMask() {
    return (_next != null ? _next.getQueryMask() : 0) | (1 << _query);
  }

  public void setConceptLength(int length)
  {
    _conceptLength = length;
    if (_next != null)
      _next.setConceptLength(length);
  }
  
  public void setNext(ConceptData next) {
    _next = next;
  }
  
  boolean cEquals(ConceptData other) {
    return _concept == other._concept;
  }

  boolean crEquals(ConceptData other) {
    return _concept == other._concept && _role == other._role;
  }
  
  boolean crqEquals(ConceptData other) {
    return _concept == other._concept && _role == other._role &&
      _query == other._query;
  }
  
  void addLast(ConceptData other)
  {
    if (_next != null)
      _next.addLast(other);
    else
      _next = other;
  }

  boolean compareWith(ConceptData other) {
    return _concept < other._concept
      || cEquals(other)  && _role < other._role
      || crEquals(other) && _penalty < other._penalty;
  }

  public void runBy(Query[] queries)
  {
    ConceptData cd = this;
    do
      queries[cd._query].updateEstimate(cd._role, cd._penalty);
    while ((cd = cd._next) != null);
  }

  public void generateFillers(RoleFiller[] array, int pos)
  {
    if (array[_query] != RoleFiller.STOP) // 'prohibited'
      (new RoleFiller(_nColumns, this, _role, pos, pos + _proximity))
	.use(array, _query);
    if (_next != null)
      _next.generateFillers(array, pos);
  }
}
