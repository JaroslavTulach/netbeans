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

import javax.swing.tree.*;
import java.util.*;
import java.text.*;

/**
 * No merge type
 *
 * @author Roger Brinkley
 * @version	1.3	10/30/06
 */
public class NoMerge extends Merge{

    /**
     * Constructs NoMerge
     *
     * @param master The master NavigatorView
     * @param slave The slave NavigatorView
     */ 
    public NoMerge(NavigatorView master, NavigatorView slave){
        super(master,slave);

    }
    
    /**
     * Processes no merge
     *
     * @param node The master node
     * @return the master node
     */
    public TreeNode processMerge(TreeNode node){
       debug("start merge");
       DefaultMutableTreeNode masterNode = (DefaultMutableTreeNode)node;
 
       // There is no merging here but we will make sure
       // the master children are handled correctly
       MergeHelpUtilities.mergeNodeChildren("javax.help.NoMerge",
					    masterNode);      
       return masterNode;
    }
    
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
    
    private static boolean debug = false;
    private static void debug(String msg){
        if (debug) {
            System.out.println("NoMerge :"+msg);
	}
    }
}
    
