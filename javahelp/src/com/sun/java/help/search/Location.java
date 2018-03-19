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

class Location
{
  private final int _docID;
  private final int _begin;
  private final int _end;
  
  public Location(int doc, int begin, int end)
  {
    _docID = doc;
    _begin = begin;
    _end = end;
  }
  
  public int getDocument() {
    return _docID;
  }

  public int getBegin() {
    return _begin;
  }

  public int getEnd() {
    return _end;
  }
}
