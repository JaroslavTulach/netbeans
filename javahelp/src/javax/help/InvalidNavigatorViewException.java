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

import java.util.Hashtable;
import java.util.Locale;

/**
 * JHelpNavigator cannot deal with given NavigatorView.
 *
 * @author Eduardo Pelegri-Llopart
 * @version	1.14	10/30/06
 */

public class InvalidNavigatorViewException extends Exception 
{
    /**
     * Create an exception. All parameters accept null values.
     * 
     * @param msg The message. If msg is null it is the same as if
     * no detailed message was specified.
     */
    public InvalidNavigatorViewException(String msg,
					 HelpSet hs,
					 String name,
					 String label,
					 Locale locale,
					 String className,
					 Hashtable params) {
	super(msg);
	this.hs = hs;
	this.name = name;
	this.label = label;
	this.locale = locale;
	this.className = className;
	this.params = params;
    }

    /**
     * @return The helpset
     */
    public HelpSet getHelpSet() {
	return hs;
    }

    /**
     * @return The name
     */
    public String getName() {
	return name;
    }

    /**
     * @return The label
     */
    public String getLabel() {
	return label;
    }

    /**
     * @return The locale
     */
    public Locale getLocale() {
	return locale;
    }

    /**
     * @return The className
     */
    public String getClassName() {
	return className;
    }

    /**
     * @return The parameters
     */
    public Hashtable getParams() {
	return params;
    }

    private HelpSet hs;
    private String name;
    private String label;
    private Locale locale;
    private String className;
    private Hashtable params;
}
