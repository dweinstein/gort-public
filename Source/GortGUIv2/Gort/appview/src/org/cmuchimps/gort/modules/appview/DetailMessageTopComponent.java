/*
   Copyright 2014 Shahriyar Amini

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.cmuchimps.gort.modules.appview;

import java.util.Collection;
import javax.swing.text.DefaultCaret;
import org.cmuchimps.gort.api.gort.DetailMessageService;
import org.cmuchimps.gort.api.gort.GestureCollection;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.cmuchimps.gort.modules.appview//DetailMessage//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "DetailMessageTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "navigator", openAtStartup = false)
@ActionID(category = "Window", id = "org.cmuchimps.gort.modules.appview.DetailMessageTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_DetailMessageAction",
        preferredID = "DetailMessageTopComponent")
@Messages({
    "CTL_DetailMessageAction=Details",
    "CTL_DetailMessageTopComponent=Details",
    "HINT_DetailMessageTopComponent=This is a Details window"
})
public final class DetailMessageTopComponent extends TopComponent implements LookupListener {
    
    private static final String DEFAULT_MODE = "navigator";
    
    private DetailMessageService dms;
    private Result<String> result;
    
    public DetailMessageTopComponent() {
        initComponents();
        setName(Bundle.CTL_DetailMessageTopComponent());
        setToolTipText(Bundle.HINT_DetailMessageTopComponent());

        // stop the text area from auto scrolling to bottom
        DefaultCaret caret = (DefaultCaret) messageTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        
        messageTextArea.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        messageScrollPane = new javax.swing.JScrollPane();
        messageTextArea = new javax.swing.JTextArea();

        messageScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        messageScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        messageTextArea.setColumns(20);
        messageTextArea.setLineWrap(true);
        messageTextArea.setRows(5);
        messageTextArea.setWrapStyleWord(true);
        messageScrollPane.setViewportView(messageTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane messageScrollPane;
    private javax.swing.JTextArea messageTextArea;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        GestureCollection.getInstance().topComponentOpened(this.getClass());
        dms = DetailMessageService.getDefault();
        
        if (dms == null) {
            return;
        }
        
        result = dms.getLookup().lookupResult(String.class);
        result.addLookupListener(this);
    }

    @Override
    public void componentClosed() {
        GestureCollection.getInstance().topComponentClosed(this.getClass());
        if (result != null) {
            result.removeLookupListener(this);
        }
    }
    
    @Override
    protected void componentShowing() {
        GestureCollection.getInstance().topComponentShowing(this.getClass());
        super.componentShowing();
    }

    @Override
    protected void componentHidden() {
        GestureCollection.getInstance().topComponentHidden(this.getClass());
        super.componentHidden();
    }

    @Override
    protected void componentActivated() {
        GestureCollection.getInstance().topComponentActivated(this.getClass());
        super.componentActivated(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void componentDeactivated() {
        GestureCollection.getInstance().topComponentDeactivated(this.getClass());
        super.componentDeactivated(); //To change body of generated methods, choose Tools | Templates.
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public void open() {
        Mode mode = WindowManager.getDefault().findMode(DEFAULT_MODE);
        if (mode != null) {
            mode.dockInto(this);
        }
        super.open();
    }

    @Override
    public void resultChanged(LookupEvent le) {
        System.out.println("DetailMessage lookup event...");
        
        if (le == null) {
            return;
        }
        
        Lookup.Result result = (Lookup.Result) le.getSource();
        
        if (result == null) {
            return;
        }
        
        Collection c = result.allInstances();
        
        if (c == null || c.isEmpty()) {
            return;
        }
        
        Object o = c.iterator().next();
        
        if (o instanceof String) {
            System.out.println("Setting messageTextArea text");
            String value = (String) o;
            messageTextArea.setText(value);
        }
    }
    
}
