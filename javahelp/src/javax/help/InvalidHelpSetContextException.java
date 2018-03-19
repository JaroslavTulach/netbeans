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
 * The HelpSet is not a (transitive) sub-HelpSet of some context HelpSet.
 *
 * For example, setting an ID to a HelpModel.
 *
 * @author Eduardo Pelegri-Llopart
 * @version	1.3	01/29/99
 */
public class InvalidHelpSetContextException extends Exception {
    private HelpSet context;
    private HelpSet hs;

    /**
     * Create the exception. All parameters accept null values.
     * 
     * @param msg The message. If msg is null it is the same as if
     * no detailed message was specified.
     */
    public InvalidHelpSetContextException(String msg,
					  HelpSet context,
					  HelpSet hs) {
	super(msg);
	this.context = context;
	this.hs = hs;
    }

    /**
     * Get the context HelpSet
     */
    public HelpSet getContext() {
	return context;
    }

    /**
     * Get the offending HelpSet
     */
    public HelpSet getHelpSet() {
	return hs;
    }
}
