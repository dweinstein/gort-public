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

import java.util.List;
import org.cmuchimps.gort.modules.appview.components.NetworkRequestsTable;
import org.cmuchimps.gort.modules.dataobject.TaintLog;
import org.cmuchimps.gort.modules.tablewidgets.StringTableModel;

/**
 *
 * @author shahriyar
 */
public class NetworkRequestsPanel extends javax.swing.JPanel {

    private NetworkRequestsTable nrt;
    
    /**
     * Creates new form NetworkRequestsPanel
     */
    public NetworkRequestsPanel() {
        initComponents();
        
        nrt = new NetworkRequestsTable();
        
        tableScrollPane.setModel(nrt.getModel());
        tableScrollPane.setTable(nrt.getTable());
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

    public StringTableModel getModel() {
        return nrt.getModel();
    }
    
    public void clear() {
        nrt.clear();
    }
    
    public void setData(List<TaintLog> taintLogs) {
        nrt.setData(taintLogs);
    }
}
