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

import java.util.Locale;

/**
 * This exeception reports generic failures in HelpSet.
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @version	1.12	10/30/06
 */

public class HelpSetException extends Exception {
    /**
     * Constructs a HelpSetException with a specified detailed message.
     * A detail message is a String that describes this particular exception.
     * @params s The detailed message. If s is null it is the same as if
     * no detailed message was specified.
     */
    public HelpSetException(String s) {
	super(s);
    }
}
