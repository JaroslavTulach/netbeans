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
import javax.swing.tree.TreeNode;
import javax.swing.tree.DefaultMutableTreeNode;
import java.lang.reflect.Constructor;

/**
 * Common superclass for all merge types
 *
 * @author  Richard Gregor
 * @version	1.6	10/30/06 
 */
public abstract class Merge {

    /**
     * Slave node
     */
    protected DefaultMutableTreeNode slaveTopNode;

    /**
     * HelpSet's locale which is used in sorting
     */
    protected Locale locale;
    
    /**
     * Constructs Merge for master and slave NavigatorViews
     *
     * @param master The master NavigatorView
     * @param slave The slave NavigatorView
     */
    protected Merge(NavigatorView master, NavigatorView slave){
        
        try {
            Class clss = Class.forName("javax.help.TOCView");
            if (clss.isInstance(slave)) {
                this.slaveTopNode = ((TOCView)slave).getDataAsTree();
	    }
            clss = Class.forName("javax.help.IndexView");
            if (clss.isInstance(slave)) {
                this.slaveTopNode = ((IndexView)slave).getDataAsTree();
	    }
        } catch(ClassNotFoundException exp) {
            System.err.println(exp);
        }
                       
        locale = master.getHelpSet().getLocale();
        if(locale == null)
            locale = Locale.getDefault();
    }
    
    /**
     * Processes merge. Changes master node according merge rules using slave node.
     *
     * @param node The master node 
     * @return The changed master node
     */
    public abstract TreeNode processMerge(TreeNode node);

    /**
     * Merge Nodes. Merge two nodes according to the merging rules of the
     * masterNode. Each Subclass should override this implementation.
     *
     * @param master The master node to merge with 
     * @param slave The node to merge into the master
     */
    public static void mergeNodes(TreeNode master, TreeNode slave) {
	// Doesn't do anything
    }

    /**
     * Merge Node Children. Merge the children of a node according to the
     * merging rules of the parent. Each subclass must implement this method
     *
     * @param node The parent node from which the children are merged
     */
    public static void mergeNodeChildren(TreeNode node) {
	// Doesn't do anything
    }
    
    /**
     * Default Merge factory which creates concrete Merge objects
     */ 
    public static class DefaultMergeFactory {

        /**
         * Returns suitable Merge object
         *
         * @param masterView The master NavigatorView
         * @param slaveView The slave NavigatorView
         * @return The Merge object
         */
        public static Merge getMerge(NavigatorView masterView, NavigatorView slaveView) {
            
            Merge mergeObject = null;
	    // throw an NPE early
	    if (masterView == null || slaveView == null) {
		throw new NullPointerException("masterView and/or slaveView are null");

	    }
            String mergeType = masterView.getMergeType();
	    HelpSet hs = masterView.getHelpSet();
            Locale locale = hs.getLocale();
	    ClassLoader loader = hs.getLoader();
	    Class klass;
	    Constructor konstructor;
            
	    if (mergeType != null) {
		try {
		    Class types[] = { NavigatorView.class, 
				      NavigatorView.class };
		    Object args[] = { masterView, slaveView };
		    if (loader == null) {
			klass = Class.forName(mergeType);
		    } else {
			klass = loader.loadClass(mergeType);
		    }
		    konstructor = klass.getConstructor(types);
		    mergeObject = (Merge) konstructor.newInstance(args);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    throw new RuntimeException("Could not create Merge type " +
					       mergeType);
		}
	    } else {
		mergeObject = new AppendMerge(masterView, slaveView);
	    }
            return mergeObject;
        }
    }
}
