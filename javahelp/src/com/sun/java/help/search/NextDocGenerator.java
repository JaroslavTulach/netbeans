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

class NextDocGenerator
{
  private int                   _document;
  private final int              _concept;
  private final int              _queryMask;
  private final ConceptData      _terms;
  private final NonnegativeIntegerGenerator  _iterator;

  public NextDocGenerator(ConceptData cd, SearchEnvironment env)
  {
    _document = 0;
    _concept = cd.getConcept();
    _queryMask = cd.getQueryMask();
    _terms = cd;
    _iterator = env.getDocumentIterator(_concept);
  }
  
  public int first() throws Exception {
    return _document =
      _iterator != null ? _iterator.first() : NonnegativeIntegerGenerator.END;
  }
  
  public int next() throws Exception {
    return _document = _iterator.next();
  }
  
  public int getDocument() {
    return _document;
  }
  
  public int getConcept() {
    return _concept;
  }
  
  public ConceptData getTerms() {
    return _terms;
  }
  
  public int getQueryMask() {
    return _queryMask;
  }
  
  // for sorting
  public boolean compareWith(NextDocGenerator other)
  {
    return _document > other._document  
      || _document == other._document && _concept > other._concept;
  }

  public boolean smallerThan(NextDocGenerator other)
  {
    return _document < other._document  
      || _document == other._document && _concept < other._concept;
  }
}
