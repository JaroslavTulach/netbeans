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

package com.sun.java.help.impl;

import javax.swing.text.*;

/**
 * Interface that a Component should support if it wants to play in the View
 * hierachy.
 *
 * WARNING!! This is an experimental feature of the JavaHelp reference
 * implemenation and may change in future versions of the implementation.
 *
 * @author Eduardo Pelegri-Llopart
 * @version 1.5 10/30/06 */


public interface ViewAwareComponent {
    /**
     * Set the View that corresponds to this object
     * This gives access to a wealth of information.
     */
    public void setViewData(View v);

    /**
    * May need something to react to changes (in my view?)
    */
}
