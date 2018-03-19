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

class QueryHit
{
  private int _doc;
  private int _begin;
  private int _end;
  private double _score;
  private final int[] _concepts;

  public QueryHit(Location loc, double penalty, int nColumns)
  {
    _score = penalty;
    _doc = loc.getDocument();
    _begin = loc.getBegin();
    _end = loc.getEnd();
    _concepts = new int[nColumns];
  }
  
  public String toString() {
    return "[doc = "+_doc+", "+_begin+", "+_end+", "+_score+"]";
  }

  public int getDocument() {
    return _doc;
  }

  public int getBegin() {
    return _begin;
  }

  public int getEnd() {
    return _end;
  }

  public double getScore() {
    return _score;
  }

  public int[] getArray() {
    return _concepts;
  }

  public boolean betterThan(QueryHit x)
  {
    if (_score < x._score) return true;
    if (_score > x._score) return false;
    if (_begin < x._begin) return true;
    if (_begin > x._begin) return false;
    if (_end < x._end) return true;
    if (_end > x._end) return false;
    return false;
  }
}
