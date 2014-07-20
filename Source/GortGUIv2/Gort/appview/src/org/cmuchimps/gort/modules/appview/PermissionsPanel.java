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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.swing.JTable;
import org.cmuchimps.gort.api.gort.ProjectUtility;
import org.cmuchimps.gort.modules.dataobject.App;
import org.cmuchimps.gort.modules.dataobject.GortEntityManager;
import org.cmuchimps.gort.modules.dataobject.Permission;
import org.cmuchimps.gort.modules.dataobject.TaintLog;
import org.cmuchimps.gort.modules.helper.AndroidPermissions;
import org.cmuchimps.gort.modules.helper.DataHelper;
import org.cmuchimps.gort.modules.helper.TaintHelper;
import org.cmuchimps.gort.modules.tablewidgets.StringTableModel;

/**
 *
 * @author shahriyar
 */
public class PermissionsPanel extends javax.swing.JPanel {

    private static final String[] HEADERS = {"Permission", "Description"};
    
    AppViewCloneableTopComponent parent;
    
    private StringTableModel model;
    
    /**
     * Creates new form PermissionsPanel
     */
    public PermissionsPanel(AppViewCloneableTopComponent parent) {
        initComponents();
        
        this.parent = parent;
        
        model = new StringTableModel(HEADERS);
        
        tableScrollPane.setModel(model);
        tableScrollPane.setTable(new JTable());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableScrollPane = new org.cmuchimps.gort.modules.tablewidgets.TableScrollPane();

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tableScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tableScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.cmuchimps.gort.modules.tablewidgets.TableScrollPane tableScrollPane;
    // End of variables declaration//GEN-END:variables

    public int numCols() {
        return HEADERS.length;
    }

    public StringTableModel getModel() {
        return model;
    }
    
    public void clear() {
        model.setData(new String[0][0]);
        model.fireTableDataChanged();
    }
    
    public void setData(List<TaintLog> taintLogs) {
        if (taintLogs == null || taintLogs.isEmpty()) {
            System.out.println("Taintlog list is null or invalid.");
            return;
        }
        
        int combinedTag = 0x00;
        
        // uses internet
        boolean hasTransmission = false;
        
        // get the taint tags for all the taintlogs;
        for (TaintLog t: taintLogs) {
            if (t == null || t.getTainttag() == null) {
                continue;
            }
            
            if (!hasTransmission && t.isTransmission()) {
                hasTransmission = true;
            }
            
            Integer taintTag = t.getTainttag();
            
            combinedTag |= taintTag.intValue();
        }
        
        System.out.println("Combined taint tag: " + combinedTag);
        
        List<String> permissions = new LinkedList<String>();
        
        if (hasTransmission) {
            permissions.add(AndroidPermissions.INTERNET);
        }
        
        permissions.addAll(new ArrayList(Arrays.asList(TaintHelper.permissionsFromTag(combinedTag))));
        
        // get all the permissions that that app has
        GortEntityManager gem = ProjectUtility.getGortEntityManager(parent.getProject());
        
        if (gem == null) {
            return;
        }
        
        EntityManager em = null;
        
        try {
            em = gem.getEntityManager();
            App app = gem.selectApp(em, parent.getDataObject().getPrimaryFile().getNameExt());
            if (app == null) {
                return;
            }
            
            List<Permission> appPermissions = app.getPermissions();
            
            if (appPermissions == null || appPermissions.isEmpty()) {
                return;
            }
            
            Map<String, Permission> permissionMap = Permission.getPermissionMap(appPermissions);
            
            if (permissionMap == null) {
                return;
            }
            
            // for each of the permissions see if the app has the permission
            // then add it to the list to be displayed
            List<String[]> data = new LinkedList<String[]>();
            
            for (String permission : permissions) {
                Permission p = permissionMap.get(permission);
                if (p == null) {
                    continue;
                }
                
                data.add(new String[]{p.getName(), p.getDescription()});
            }
            
            if (data.size() < 0) {
                return;
            }
            
            model.setData(DataHelper.to2DArray(data));
            model.fireTableDataChanged();
            
        } finally {
            GortEntityManager.closeEntityManager(em);
        }
    }
}
