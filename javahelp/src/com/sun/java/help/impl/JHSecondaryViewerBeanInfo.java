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
import java.beans.*;

/**
 * This class provides information about getter/setter methods within 
 * JHSecondaryWindow. It is usefull for reflection.
 * @see JHSecondaryViewer
 *
 * @author Roger D. Brinkley
 * @version	1.7	10/30/06
 */
public class JHSecondaryViewerBeanInfo extends SimpleBeanInfo {

    public JHSecondaryViewerBeanInfo() {
    }
    
    public PropertyDescriptor[] getPropertyDescriptors() {
	PropertyDescriptor back[] = new PropertyDescriptor[15];
	try {
	    back[0] = new PropertyDescriptor("content", JHSecondaryViewer.class);
	    back[1] = new PropertyDescriptor("id", JHSecondaryViewer.class);
	    back[2] = new PropertyDescriptor("viewerName", JHSecondaryViewer.class);
	    back[3] = new PropertyDescriptor("viewerActivator", JHSecondaryViewer.class);
	    back[4] = new PropertyDescriptor("viewerStyle", JHSecondaryViewer.class);
	    back[5] = new PropertyDescriptor("viewerLocation", JHSecondaryViewer.class);
	    back[6] = new PropertyDescriptor("viewerSize", JHSecondaryViewer.class);
	    back[7] = new PropertyDescriptor("iconByName", JHSecondaryViewer.class);
	    back[8] = new PropertyDescriptor("iconByID", JHSecondaryViewer.class);
	    back[9] = new PropertyDescriptor("text", JHSecondaryViewer.class);
	    back[10] = new PropertyDescriptor("textFontFamily", JHSecondaryViewer.class);
	    back[11] = new PropertyDescriptor("textFontSize", JHSecondaryViewer.class);
	    back[12] = new PropertyDescriptor("textFontWeight", JHSecondaryViewer.class);
	    back[13] = new PropertyDescriptor("textFontStyle", JHSecondaryViewer.class);
	    back[14] = new PropertyDescriptor("textColor", JHSecondaryViewer.class);
	    return back;
	} catch (Exception ex) {
	    return null;
	}
    }
}
