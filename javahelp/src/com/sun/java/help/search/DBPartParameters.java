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

import java.util.Hashtable;
import java.net.URL;

public class DBPartParameters
{
  protected Schema _schema;
  private String _partName;
  private Hashtable _parameters;

  protected DBPartParameters() {}

  public DBPartParameters(Schema schema, String partName)
  {
    _schema = schema;
    _partName = partName;
    _parameters = schema.parameters(partName);
  }
  
  protected boolean parametersKnown() {
    return _parameters != null;
  }
  
  protected void updateSchema(String parameters) {
    _schema.update(_partName, parameters);
  }
  
  public int integerParameter(String name) {
    return Integer.parseInt(((String)_parameters.get(name)));
  }

  public URL getURL() throws Exception {
    return _schema.getURL(_partName);
  }
}
