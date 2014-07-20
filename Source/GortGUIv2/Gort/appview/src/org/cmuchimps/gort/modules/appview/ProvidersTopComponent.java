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
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JTable;
import org.cmuchimps.gort.api.gort.GestureCollection;
import org.cmuchimps.gort.api.gort.GortDatabaseService;
import org.cmuchimps.gort.modules.dataobject.App;
import org.cmuchimps.gort.modules.dataobject.GortEntityManager;
import org.cmuchimps.gort.modules.dataobject.Provider;
import org.cmuchimps.gort.modules.tablewidgets.StringTableModel;
import org.netbeans.api.project.Project;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.filesystems.FileObject;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.WeakListeners;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.cmuchimps.gort.modules.appview//Providers//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "ProvidersTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = false)
@ActionID(category = "Window", id = "org.cmuchimps.gort.modules.appview.ProvidersTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ProvidersAction",
        preferredID = "ProvidersTopComponent")
@Messages({
    "CTL_ProvidersAction=Providers",
    "CTL_ProvidersTopComponent=Providers",
    "HINT_ProvidersTopComponent=This is a Providers window"
})
public final class ProvidersTopComponent extends TopComponent implements
        PropertyChangeListener {

    private static final String[] HEADERS = {"Name"};
    private StringTableModel model;
    
    public ProvidersTopComponent() {
        initComponents();
        setName(Bundle.CTL_ProvidersTopComponent());
        setToolTipText(Bundle.HINT_ProvidersTopComponent());

        model = new StringTableModel(HEADERS);
        providersTableScrollPane.setModel(model);
        providersTableScrollPane.setTable(new JTable());
        
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

        providersTableScrollPane = new org.cmuchimps.gort.modules.tablewidgets.TableScrollPane();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(providersTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(providersTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.cmuchimps.gort.modules.tablewidgets.TableScrollPane providersTableScrollPane;
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
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt == null) {
            return;
        }
        
        if (!TopComponent.Registry.PROP_ACTIVATED.equals(evt.getPropertyName())) {
            return;
        }
        
        update();
    }
    
    private void clear() {
        if (model != null) {
            model.setNoData();
            model.fireTableDataChanged();
        }
    }
    
    private void update() {
        TopComponent activated = TopComponent.getRegistry().getActivated();
        
        if (activated == null || !(activated instanceof AppViewCloneableTopComponent)) {
            return;
        }
        
        System.out.println("Updating app providers");
        
        AppViewCloneableTopComponent appView = (AppViewCloneableTopComponent) activated;
        
        Project project = appView.getProject();
        FileObject fo = appView.getFileObject();
        
        clear();
        
        if (project == null || fo == null) {
            return;
        }
        
        GortDatabaseService gds = project.getLookup().lookup(GortDatabaseService.class);
        
        if (gds == null) {
            return;
        }
        
        GortEntityManager gem = gds.getGortEntityManager();
        
        if (gem == null) {
            return;
        }
        
        EntityManager em = gem.getEntityManager();
        
        try {
            App app = gem.selectApp(em, fo.getNameExt());
            List<Provider> providers = app.getProviders();
            String[][] data = new String[providers.size()][1];
        
            if (providers != null) {
                if (providers.size() > 0) {
                int index = 0;

                for (Provider p : providers) {
                    data[index][0] = p.getName();
                    index++;
                }

                model.setData(data);
                model.fireTableDataChanged();
                } else {
                    System.out.println("App has no associated providers: " + app.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            GortEntityManager.closeEntityManager(em);
        }
        
    }
}
