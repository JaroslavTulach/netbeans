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

package javax.help;

/**
 * Stores search information for individual Search hits.
 *
 * @author Roger D. Brinkley
 * @version   1.4     10/30/06
 */

public class SearchHit {

    private double confidence;
    private int begin;
    private int end;

    public SearchHit(double confidence, int begin, int end) {
	this.confidence = confidence;
	this.begin = begin;
	this.end = end;
    }

    public double getConfidence() {
	return confidence;
    }

    public int getBegin() {
	return begin;
    }

    public int getEnd() {
	return end;
    }
}

