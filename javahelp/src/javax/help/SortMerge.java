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

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.*;
import java.text.*;

/**
 * Sort merge type
 *
 * @author Richard Gregor
 * @version	1.12	10/30/06
 */
public class SortMerge extends Merge{

    /**
     * Constructs SortMerge
     *
     * @param master The master NavigatorView
     * @param slave The slave NavigatorView
     */
    public SortMerge(NavigatorView master, NavigatorView slave) {
        super(master,slave);
    }
    
    /**
     * Processes sort merge 
     *
     * @param node The master node (This node must be sorted)
     * @return Merged master node
     */
    public TreeNode processMerge(TreeNode node) {
	debug("processMerge started");
        DefaultMutableTreeNode masterNode = (DefaultMutableTreeNode) node;
        
        //if master and slave are the same object return the
	// masterNode 
        if (masterNode.equals(slaveTopNode)) {
            return masterNode;
        }
        
	// If there are no children in slaveTopNode return the
	// masterNode
        if (slaveTopNode.getChildCount() == 0) { 
            return masterNode;
        }
        
	// If there are no children in the masterNode process the slaveTopNode
	// and return the slaveTopNode
	if (masterNode.getChildCount() == 0) {
	    MergeHelpUtilities.mergeNodeChildren("javax.help.SortMerge",
						 slaveTopNode);
	    // It would be better to return the slaveNode but that causes 
	    // problems in the UIs so return the masterNode after adding
	    // all the slavNodes
	    while (slaveTopNode.getChildCount() > 0) {
		masterNode.add((DefaultMutableTreeNode) slaveTopNode.getFirstChild());
	    }
	    return masterNode;
	}

	mergeNodes(masterNode, slaveTopNode);
	debug("process merge ended");
        return masterNode;
    }

    /**
     * Merge Nodes. Merge two nodes according to the Sort merging rules 
     *
     * @param masterNode The master node to merge with 
     * @param slaveNode The node to merge into the master
     */
    public static void mergeNodes(TreeNode master, TreeNode slave) {
	debug("mergeNodes started");
	DefaultMutableTreeNode masterNode = (DefaultMutableTreeNode)master;
	DefaultMutableTreeNode slaveNode = (DefaultMutableTreeNode)slave;

	// It is important that the masterNode already be sorted
	sortNode(slaveNode, MergeHelpUtilities.getLocale(slaveNode));
	int masterCnt = masterNode.getChildCount();
	int m = 0;
	DefaultMutableTreeNode masterAtM=null;
	
	if (masterCnt > 0) {
	    masterAtM = (DefaultMutableTreeNode)masterNode.getChildAt(m);
	}

	DefaultMutableTreeNode slaveNodeChild = null;
	
	//Loop through the slaves
	while (slaveNode.getChildCount() > 0 && masterAtM != null) {
	    slaveNodeChild = (DefaultMutableTreeNode)slaveNode.getFirstChild();
	    
	    // compare this slaveChild to the masterChild
	    int compareVal = MergeHelpUtilities.compareNames(masterAtM,
							     slaveNodeChild);
	    
	    // if < 0 get the next master child
	    if (compareVal < 0 ) {
		++m;
		// break out if we've done all we can do.
		if (m >= masterCnt) {
		    break;
		}
		masterAtM = (DefaultMutableTreeNode)masterNode.getChildAt(m);
		continue;
	    } else if (compareVal > 0) {
		// slaveNodeChild is lexically procedes the masterNodeChild
		// add the slaveNodeChild and merge in the node children
		// and then continue. You automatically get the next 
		// slaveNodeChild
		masterNode.add(slaveNodeChild);
		MergeHelpUtilities.mergeNodeChildren("javax.help.SortMerge",
						     slaveNodeChild);
		continue;
	    } else {
		// The masterChild and slaveChild are equal
		if (MergeHelpUtilities.haveEqualID(masterAtM, slaveNodeChild)) {

		    // Merge the masterchild and slaveNodeChild together
		    // based on the mergetype of the masterChild
		    MergeHelpUtilities.mergeNodes("javax.help.SortMerge",
						  masterAtM, slaveNodeChild);
		    
		    // Need to remove the slaveNodeChild from the list
		    slaveNodeChild.removeFromParent();
		    slaveNodeChild = null;
		} else {
		    // Names are the same but the ID are not
		    // Mark the nodes and add the slaveChild
		    MergeHelpUtilities.markNodes(masterAtM, slaveNodeChild);
		    masterNode.add(slaveNodeChild);
		    MergeHelpUtilities.mergeNodeChildren("javax.help.SortMerge",
							 slaveNodeChild);
		}
	    }
	}

	// Add the remainong slave node children
	while (slaveNode.getChildCount() > 0) {
	    slaveNodeChild = (DefaultMutableTreeNode)slaveNode.getFirstChild();
	    masterNode.add(slaveNodeChild);
	    MergeHelpUtilities.mergeNodeChildren("javax.help.SortMerge",
						 slaveNodeChild);
	}

	// ok make sure the masterNode is sorted
	mergeNodeChildren(masterNode);
	debug ("mergeNode ended");
    }


    /**
     * Merge Node Children. Merge the children of a node according to the
     * Sort merging rules.
     *
     * @param node The parent node from which the children are merged
     */
    public static void mergeNodeChildren(TreeNode node) {
	DefaultMutableTreeNode masterNode = (DefaultMutableTreeNode)node;
	debug("mergeNodeChildren master=" + MergeHelpUtilities.getNodeName(masterNode));

	// Sort the children
	sortNode(masterNode, MergeHelpUtilities.getLocale(masterNode));

	// merge the children's children
	for (int i=0; i < masterNode.getChildCount(); i++) {
	    DefaultMutableTreeNode child = 
		(DefaultMutableTreeNode)masterNode.getChildAt(i);
	    if (!child.isLeaf()) {
		MergeHelpUtilities.mergeNodeChildren("javax.help.SortMerge",
						     child);
	    }
	}
    }

    /**
     * Sorts children of node using Array.sort 
     *
     * @param node The node to sort
     * @param locale The locale
     * @return Sorted node
     */ 
    public static void sortNode(DefaultMutableTreeNode node, Locale locale) {
	debug ("sortNode");
        if (locale == null) {
            locale = Locale.getDefault();
	}
        
	//Locale locale = MergeHelpUtilities.getLocale(node);         
	CollationKey temp;
	int size = node.getChildCount();
	DefaultMutableTreeNode sortedNode = new DefaultMutableTreeNode();
	
	Collator collator = Collator.getInstance(locale);
	CollationKey[] keys = new CollationKey[size];
	
	for (int i = 0; i < size; i++) {
	    String string = MergeHelpUtilities.getNodeName((DefaultMutableTreeNode)node.getChildAt(i));
	    debug("String , i:"+string+" , "+i);
	    keys[i] = collator.getCollationKey(string);
	}
	
	Arrays.sort(keys);
	
	for (int i = 0; i < size; i++) {
	    DefaultMutableTreeNode child = MergeHelpUtilities.getChildWithName(node,keys[i].getSourceString()); 
	    if (child != null) {
		sortedNode.add(child);
	    }
	}
            
	while (sortedNode.getChildCount() > 0) {
	    node.add((DefaultMutableTreeNode) sortedNode.getFirstChild());
	}
        
	debug ("end sortNode");
    }
    
  
    private static boolean debug = false;
    private static void debug(String msg) {
        if (debug) {
            System.out.println("SortMerge :"+msg);
	}
    }
}
