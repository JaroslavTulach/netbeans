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
 * Append merge type
 *
 * @author Richard Gregor
 * @version	1.4	10/30/06
 */
public class AppendMerge extends Merge{

    /**
     * Constructs AppendMerge
     *
     * @param master The master NavigatorView
     * @param slave The slave NavigatorView
     */ 
    public AppendMerge(NavigatorView master, NavigatorView slave){
        super(master,slave);

    }
    
    /**
     * Processes append merge
     *
     * @param node The master node
     * @return Merged master node
     */
    public TreeNode processMerge(TreeNode node){
       debug("start merge");

       mergeNodes(node, slaveTopNode);
       return node;
    }


    /**
     * Merge Nodes. Merge two nodes according to the Append merging rules 
     *
     * @param masterNode The master node to merge with 
     * @param slaveNode The node to merge into the master
     */
    public static void mergeNodes(TreeNode master, TreeNode slave) {
       DefaultMutableTreeNode masterNode = (DefaultMutableTreeNode)master;
       DefaultMutableTreeNode slaveNode = (DefaultMutableTreeNode)slave;
       debug("mergeNodes master=" + MergeHelpUtilities.getNodeName(masterNode) + 
	     " slave=" + MergeHelpUtilities.getNodeName(slaveNode));       
       while (slaveNode.getChildCount() > 0) {
	   DefaultMutableTreeNode slaveNodeChild = 
	       (DefaultMutableTreeNode)slaveNode.getFirstChild();
	   masterNode.add(slaveNodeChild);
	   MergeHelpUtilities.mergeNodeChildren("javax.help.AppendMerge", 
						slaveNodeChild);
       }
    }
    
    /**
     * Merge Node Children. Merge the children of a node according to the
     * Append merging.
     *
     * @param node The parent node from which the children are merged
     */
    public static void mergeNodeChildren(TreeNode node) {
	DefaultMutableTreeNode masterNode = (DefaultMutableTreeNode)node;
	debug("mergeNodes master=" + MergeHelpUtilities.getNodeName(masterNode));
	
	// The rules are there are no rules. Nothing else needs to be done
	// except for merging through the children
	for (int i=0; i < masterNode.getChildCount(); i++) {
	    DefaultMutableTreeNode child = 
		(DefaultMutableTreeNode)masterNode.getChildAt(i);
	    if (!child.isLeaf()) {
		MergeHelpUtilities.mergeNodeChildren("javax.help.AppendMerge",
						     child);
	    }
	}
    }

    private static boolean debug = false;
    private static void debug(String msg){
        if (debug) {
            System.out.println("AppendMerge :"+msg);
	}
    }
}
    
