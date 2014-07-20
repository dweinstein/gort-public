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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.swing.JTable;
import org.cmuchimps.gort.api.gort.GestureCollection;
import org.cmuchimps.gort.api.gort.ProjectUtility;
import org.cmuchimps.gort.api.gort.TraversalProcessorService;
import org.cmuchimps.gort.api.gort.TraversalProcessorService.TraversalProcessedChangeEvent;
import org.cmuchimps.gort.api.gort.TraversalProviderService;
import org.cmuchimps.gort.modules.dataobject.GortEntityManager;
import org.cmuchimps.gort.modules.dataobject.Server;
import org.cmuchimps.gort.modules.dataobject.Traversal;
import org.cmuchimps.gort.modules.helper.DataHelper;
import org.cmuchimps.gort.modules.tablewidgets.StringTableModel;
import org.netbeans.api.project.Project;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.WeakListeners;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.cmuchimps.gort.modules.appview//Servers//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "ServersTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = false)
@ActionID(category = "Window", id = "org.cmuchimps.gort.modules.appview.ServersTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ServersAction",
        preferredID = "ServersTopComponent")
@Messages({
    "CTL_ServersAction=Servers",
    "CTL_ServersTopComponent=Servers",
    "HINT_ServersTopComponent=This is a Servers window"
})
public final class ServersTopComponent extends TopComponent implements LookupListener, PropertyChangeListener {

    private static final String DEFAULT_MODE = "output";
    
    private static final String[] HEADERS = {"IP", "Hostname", "Registrant",
        "Registrant Address",
        "Registrant City",
        "Registrant State/Providence",
        "Registrant Country",
        "Registrant Postal Code",
        "Registrant Phone",
        "Registrant Email",
        };
    
    private Lookup.Result<TraversalProcessorService.TraversalProcessedChangeEvent> lookupResult;
    
    private StringTableModel model;
    private JTable table;
    
    public ServersTopComponent() {
        initComponents();
        setName(Bundle.CTL_ServersTopComponent());
        setToolTipText(Bundle.HINT_ServersTopComponent());

        model = new StringTableModel(HEADERS);
        serversTableScrollPane.setModel(model);
        
        table = new JTable();
        serversTableScrollPane.setTable(table);
        
        // Add a listener to this so that we can update
        TopComponent.Registry reg = TopComponent.getRegistry();
        reg.addPropertyChangeListener(WeakListeners.propertyChange(this, reg));
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serversTableScrollPane = new org.cmuchimps.gort.modules.tablewidgets.TableScrollPane();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(serversTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(serversTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.cmuchimps.gort.modules.tablewidgets.TableScrollPane serversTableScrollPane;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        GestureCollection.getInstance().topComponentOpened(this.getClass());
        super.componentOpened();
    }

    @Override
    public void componentClosed() {
        GestureCollection.getInstance().topComponentClosed(this.getClass());
        super.componentClosed();
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
    public void resultChanged(LookupEvent le) {
        if (le == null) {
            return;
        }
        
        Lookup.Result r = (Lookup.Result) le.getSource();
        Collection c = r.allInstances();
        
        if (c == null || c.isEmpty()) {
            return;
        }
        
        Object o = c.iterator().next();
        
        if (o == null) {
            return;
        }

        if (o instanceof TraversalProcessorService.TraversalProcessedChangeEvent) {
            TraversalProcessedChangeEvent e = (TraversalProcessedChangeEvent) o;
            resultChanged(e);
        }
    }
    
    private void resultChanged(TraversalProcessedChangeEvent e) {
        if (e == null) {
            return;
        }
        
        FileObject fo = e.getTraversal();
        
        if (fo == null) {
            return;
        }
        
        // only update the heuristics panel for the current viewable file
        TopComponent activated = TopComponent.getRegistry().getActivated();
        
        if (activated == null || !(activated instanceof AppViewCloneableTopComponent)) {
            return;
        }
        
        AppViewCloneableTopComponent appView = (AppViewCloneableTopComponent) activated;
        
        FileObject viewableFO = appView.getFileObject();
        
        if (viewableFO == null) {
            return;
        }
        
        if (fo.getNameExt().startsWith(viewableFO.getNameExt())) {
            update();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt == null) {
            return;
        }
        
        if (!TopComponent.Registry.PROP_ACTIVATED.equals(evt.getPropertyName())) {
            return;
        }
        
        update();
    }
    
    private void update() {
        TopComponent activated = TopComponent.getRegistry().getActivated();
        
        if (activated == null) {
            return;
        }
        
        if (activated == this) {
            // find if there are any associated AppViews
            activated = AppViewCloneableTopComponent.getShowingAppView();
            if (activated == null) {
                return;
            }
        } else if (!(activated instanceof AppViewCloneableTopComponent)) {
            return;
        }
        
        System.out.println("Updating appview heuristics");
        
        AppViewCloneableTopComponent appView = (AppViewCloneableTopComponent) activated;
        
        Project project = appView.getProject();
        FileObject fo = appView.getFileObject();
        
        if (project == null || fo == null) {
            return;
        }
        
        // TODO: check if this may add multiple instances of listeners or just adds one
        TraversalProcessorService processor = project.getLookup().lookup(TraversalProcessorService.class);
        lookupResult = processor.getLookup().lookupResult(TraversalProcessorService.TraversalProcessedChangeEvent.class);
        lookupResult.addLookupListener(this);
        
        TraversalProviderService provider = project.getLookup().lookup(TraversalProviderService.class);
        
        if (provider == null) {
            return;
        }
        
        clear();
        
        FileObject traversalDir = provider.getMainTraversal(fo);
        
        if (traversalDir == null) {
            return;
        }
        
        GortEntityManager gem = ProjectUtility.getGortEntityManager(project);
        
        if (gem == null) {
            return;
        }
        
        EntityManager em = gem.getEntityManager();
        
        try {
            Traversal traversal = gem.selectTraversal(em, traversalDir.getNameExt());
            
            if (traversal == null) {
                return;
            }
            
            List<Server> servers = traversal.getServers();
            updateModel(servers);
        } finally {
            GortEntityManager.closeEntityManager(em);
        }
    }
    
    private void updateModel(List<Server> servers) {
        if (servers == null || servers.isEmpty()) {
            return;
        }
        
        List<String[]> data = new LinkedList<String[]>();
        
        Set<Server> dataAdded = new HashSet<Server>();
        
        for (Server s : servers) {
            if (s == null) {
                continue;
            }
            
            String ip = s.getIp();
            
            if (ip == null || ip.isEmpty()) {
                continue;
            }
            
            // Do not repeat servers
            if (dataAdded.contains(s)) {
                continue;
            } else {
                dataAdded.add(s);
            }
            
            String[] row = new String[model.getColumnCount()];
            
            row[0] = ip;
            row[1] = s.getHostname();
            row[2] = s.getName();
            row[3] = s.getAddress();
            row[4] = s.getCity();
            row[5] = s.getStateprov();
            row[6] = s.getCountry();
            row[7] = s.getPostalCode();
            row[8] = s.getPhone();
            row[9] = s.getEmail();
            
            data.add(row);
        }
        
        if (data.size() <= 0) {
            return;
        }
        
        model.setData(DataHelper.to2DArray(data));
        model.fireTableDataChanged();
    }
    
    private void clear() {
        if (model != null) {
            model.setNoData();
            model.fireTableDataChanged();
        }
    }
}
