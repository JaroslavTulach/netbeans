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

import java.awt.*;
import java.awt.event.*;
import javax.help.*;
import javax.help.event.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Stack;
import javax.swing.*;

/**
 *
 * @author Stepan Marek
 * @version	1.4	10/30/06
 */
public class ForwardAction extends AbstractHelpAction implements MouseListener, HelpHistoryModelListener {

    private static final String NAME = "ForwardAction";
    
    private static final int DELAY = 500;    
    private Timer timer;
    private HelpHistoryModel historyModel;
    
    /** Creates new ForwardAction */
    public ForwardAction(Object control) {
        super(control, NAME);
        if (control instanceof JHelp) {
            JHelp help = (JHelp)control;            
            historyModel = help.getHistoryModel();
            historyModel.addHelpHistoryModelListener(this);
            
            setEnabled(historyModel.getIndex() > 0);
            
            putValue("icon", UIManager.getIcon(NAME + ".icon"));
            
	    Locale locale = null;
	    try {
		locale = help.getModel().getHelpSet().getLocale();
	    } catch (NullPointerException npe) {
		locale = Locale.getDefault();
	    }
            putValue("tooltip", HelpUtilities.getString(locale, "tooltip." + NAME));
            putValue("access", HelpUtilities.getString(locale, "access." + NAME));            
        }
    }

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(MouseEvent e) {
    }    
    
    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e) {
        if (timer != null) {
            timer.stop();
        }
    }
    
    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(MouseEvent e) {
        timer = new Timer(DELAY, new TimeListener(e));
        timer.start();
    }
    
    /**
     * Invoked when the mouse has been clicked on a component.
     */
    public void mouseClicked(MouseEvent e) {
        if ((historyModel != null) && isEnabled()) {
            historyModel.goForward();
        }
    }
    
    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered(MouseEvent e) {
    }

    private class TimeListener implements ActionListener {
        
        private MouseEvent e;
        
        public TimeListener(MouseEvent e) {
            this.e = e;
        }
        
        public void actionPerformed(ActionEvent evt){
            timer.stop();
            if (ForwardAction.this.isEnabled()) {
                ForwardAction.this.showForwardHistory(e);
            }
        }
    }

    private class HistoryActionListener implements ActionListener {
        
        private int index;
        
        public HistoryActionListener(int index) {
            this.index = index;          
        }
        
        public void actionPerformed(java.awt.event.ActionEvent event) {
            if(historyModel != null) {
                historyModel.setHistoryEntry(index);
            }
        }        
    }

    /**
     * Shows popup with forward history entries
     */
    private void showForwardHistory(MouseEvent e) {
        
        JPopupMenu forwardMenu = new JPopupMenu("Forward History");
        
        if (historyModel == null) {
            return;
        }
        
        Locale locale = ((JHelp)getControl()).getModel().getHelpSet().getLocale();
        Enumeration items = historyModel.getForwardHistory().elements();
        JMenuItem mi = null;
        int index = historyModel.getIndex() + 1;
        //while(items.hasMoreElements()){
        for(int i = 0; items.hasMoreElements(); i++) {
            HelpModelEvent item = (HelpModelEvent) items.nextElement();
            if(item != null) {
                String title = item.getHistoryName();
                if (title == null) {                    
                    title = HelpUtilities.getString(locale, "history.unknownTitle");
                }
                mi = new JMenuItem(title);
                //mi.setToolTipText(item.getURL().getPath());
                mi.addActionListener(new HistoryActionListener(i + index));
                forwardMenu.add(mi);
            }
        }        
       // if(e.isPopupTrigger())
        forwardMenu.show(e.getComponent(),e.getX(),e.getY());
        
    }

    /**
     * Tells the listener that the history has changed.
     * Will enable/disable the Action depending on the events previous flag
     *
     * @param e The HelpHistoryModelEvent
     */
    public void historyChanged(HelpHistoryModelEvent e) {
        setEnabled(e.isNext());
    }
    
}
