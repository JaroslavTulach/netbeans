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
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;

public class Schema
{
  private String _dirName;
  private URL _hsBase = null;
  private boolean _update;
  private Vector _lines = new Vector();

  public static void main(String[] args)
  {
    try {
      Schema sch = new Schema(null, "/files/resources/lexicon/LIF_ONT", false);
      debug(sch.parametersAsString("EDGE"));
      debug(sch.parametersAsString("TMAP"));
      debug(sch.parameters("EDGE").get("rt").toString());
      debug(sch.parameters("EDGE").get("vl").toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Schema(URL base, String dirName, boolean update) throws Exception
  {
    _hsBase = base;
    _dirName = dirName;
    _update = update;
    try {
      read();
    }
    catch (java.io.FileNotFoundException e) {
      debug("error creating SCHEMA");
    }
    for (int i = 0; i < _lines.size(); i++)
      debug(_lines.elementAt(i).toString());
  }
  
  public void update(String partName, String parameters)
  {
    for (int i = 0; i < _lines.size(); i++)
      if (((String)_lines.elementAt(i)).startsWith(partName))
	_lines.removeElementAt(i);
    _lines.addElement(partName + " " + parameters);
  }

  public String parametersAsString(String name)
  {
    for (int i = 0; i < _lines.size(); i++)
      if (((String)_lines.elementAt(i)).startsWith(name))
	return ((String)_lines.elementAt(i)).substring(name.length() + 1);
    return null;
  }

  public Hashtable parameters(String name)
  {
    for (int i = 0; i < _lines.size(); i++)
      if (((String)_lines.elementAt(i)).startsWith(name))
	{
	  Hashtable result = new Hashtable();
	  StringTokenizer tokens =
	    new StringTokenizer((String)_lines.elementAt(i), " =");
	  tokens.nextToken();		// skip name
	  while (tokens.hasMoreTokens())
	    result.put(tokens.nextToken(), tokens.nextToken());
	  return result;
	}
    return null;
  }

  public URL getURL(String name) throws Exception
  {
    URL baseURL=null, url, tmapURL=null;
    URLConnection connect;
    File file;
    
    debug("getURL " + name);
    debug("dirName=" + _dirName + " hsBase= " + _hsBase);

    if (_hsBase == null) {
	file = new File(_dirName);
	if (file.exists()) {
	    // On Win32 we need to convert all "\" to "/"
	    if (File.separatorChar != '/') {
		_dirName = _dirName.replace(File.separatorChar, '/');
	    }
	    // Make sure the last character is a file separator
	    if (_dirName.lastIndexOf('/')
		!= _dirName.length() - 1) {
		_dirName = _dirName.concat("/");
	    }
	    debug ("file:" + _dirName);
	    // Use a file protocol
	    baseURL = new URL("file", "", _dirName);
	} else {
	// Assume that some protocol was specified and try it
	baseURL = new URL(_dirName);
      }
    }
      
    // Read the SCHEMA data
    if (_hsBase != null) {
      return new URL(_hsBase, _dirName + "/" + name);
    } else {
      return new URL(baseURL, name);
    }
  }

  public void save()
  {
    if (_update) {
      try {
	FileWriter out = new FileWriter(_dirName + "/SCHEMA");
	out.write("JavaSearch 1.0\n");
	for (int i = 0; i < _lines.size(); i++)
	  {
	    out.write((String)_lines.elementAt(i));
	    out.write('\n');
	  }
	out.close();
      }
      catch (IOException e) {
	System.err.println("SCHEMA save failed " + e);
      }
    }
  }
  
  private void read() throws Exception
  {
    URL url;
    URLConnection connect;
    BufferedReader in;

    url = getURL("SCHEMA");
    connect = url.openConnection();
    in = new BufferedReader
      (new InputStreamReader(connect.getInputStream()));

    // This needs to be replaced with our XML Parser
    String line;
    while ((line = in.readLine()) != null)
      _lines.addElement(line);
    in.close();
  }
  
  /**
   * For printf debugging.
   */
  private static boolean debugFlag = false;
  private static void debug(String str) {
    if( debugFlag ) {
      System.out.println("Schema: " + str);
    }
  }
}
