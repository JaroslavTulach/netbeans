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

import java.io.InputStream;

class ConceptGroupGenerator implements CompressorIterator
{
  private static final int NConceptsInGroup =
  DocumentCompressor.NConceptsInGroup;
  private static final int BitsInLabel =
  DocumentCompressor.BitsInLabel;
  
  private int           _last;
  private ConceptData[] _table;
  private Decompressor    _bits;
  private int          _k1;
  private final int     _k2 = BitsInLabel;
  private ConceptData  _cData;

  public ConceptGroupGenerator()
  {
    _k1 = 0;
    _table = new ConceptData[NConceptsInGroup];
    _last = 0;
    _bits = null;
  }
    
  public ConceptGroupGenerator(byte[] bytes, int index, int k)
  {
    _k1 = k;
    _table = new ConceptData[NConceptsInGroup];
    _last = 0;
    _bits = new ByteArrayDecompressor(bytes, index);
  }

  public void init(byte[] bytes, int index, int k)
  {
    _k1 = k;
    _bits = new ByteArrayDecompressor(bytes, index);
    _last = 0;
    for (int i = 0; i < NConceptsInGroup; i++)
      _table[i] = null;
  }

  public void addTerms(int index, ConceptData terms) {
    _table[index] = terms;
  }

  public int decodeConcepts(int k, int shift, int[] concepts) throws Exception {
    return _bits.ascendingDecode(k, shift, concepts);
  }

  public int position() {
    return _last;
  }
  
  public void value(int value) {
    _last += value;
  }
  
  boolean next() throws Exception
  {
    try {
      while (_bits.readNext(_k1, this))
	if ((_cData = _table[_bits.read(_k2)]) != null)
	  return true;
      return false;
    }
    catch (Exception e) {
      e.printStackTrace();
      System.err.println(_bits);
      System.err.println(_table);
      throw e;
    }
  }
  
  public void generateFillers(RoleFiller[] array) {
    _cData.generateFillers(array, _last);
  }
}
