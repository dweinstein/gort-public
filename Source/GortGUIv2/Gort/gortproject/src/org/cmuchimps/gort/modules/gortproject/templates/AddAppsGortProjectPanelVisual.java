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
package org.cmuchimps.gort.modules.gortproject.templates;

import java.io.File;
import java.util.LinkedHashMap;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.cmuchimps.gort.modules.tablewidgets.LinkedHashMapTableModel;
import org.openide.WizardDescriptor;

/**
 *
 * @author shahriyar
 */
public class AddAppsGortProjectPanelVisual extends JPanel implements DocumentListener {

    public static final String KEY_APK_FILES = "appfiles";
    
    private AddAppsGortProjectWizardPanel panel;
    
    private Object lock = new Object();
    
    /**
     * Creates new form AddAppsGortProjectPanelVisual
     */
    public AddAppsGortProjectPanelVisual(AddAppsGortProjectWizardPanel panel) {
        initComponents();
        this.panel = panel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        instructionLabel = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        apkFileTable = new org.cmuchimps.gort.modules.tablewidgets.TableScrollPane(new LinkedHashMapTableModel("Applications"), new JTable());
        removeButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(instructionLabel, org.openide.util.NbBundle.getMessage(AddAppsGortProjectPanelVisual.class, "AddAppsGortProjectPanelVisual.instructionLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(addButton, org.openide.util.NbBundle.getMessage(AddAppsGortProjectPanelVisual.class, "AddAppsGortProjectPanelVisual.addButton.text")); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(removeButton, org.openide.util.NbBundle.getMessage(AddAppsGortProjectPanelVisual.class, "AddAppsGortProjectPanelVisual.removeButton.text")); // NOI18N
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(apkFileTable, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .add(instructionLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(removeButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(instructionLabel)
                    .add(addButton)
                    .add(removeButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(apkFileTable, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        fc.setDialogTitle("Select Application APK Files");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // Add an apk file filter
        FileFilter apkFilter = new FileNameExtensionFilter("Android application package (.apk)","apk");
        fc.addChoosableFileFilter(apkFilter);
        // Clear "All files" from dropdown filter box
        fc.setAcceptAllFileFilterUsed(false);
        // Set the default file filter selected
        fc.setFileFilter(apkFilter);

        //System.out.println("Presenting apk file chooser");

        if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(this)) {
            File[] selectedFiles = fc.getSelectedFiles();

            if (selectedFiles == null || selectedFiles.length <= 0) {
                return;
            }

            //System.out.println("Adding apk files to table panel");

            // Add the files to the files table
            LinkedHashMapTableModel tm = (LinkedHashMapTableModel) apkFileTable.getModel();

            for (File f : selectedFiles) {
                System.out.println(f.getAbsoluteFile());
                tm.checkAndPut(f.getAbsolutePath(), f);
            }

            //System.out.println("Firing table data change event");
            tm.fireTableDataChanged();

            panel.fireChangeEvent();
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        JTable table = apkFileTable.getTable();
        LinkedHashMapTableModel tm = (LinkedHashMapTableModel) apkFileTable.getModel();
        
        if (table == null || tm == null) {
            return;
        }
        
        int row = table.getSelectedRow();
        
        tm.remove(row);
        tm.fireTableRowsDeleted(row, row);
    }//GEN-LAST:event_removeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private org.cmuchimps.gort.modules.tablewidgets.TableScrollPane apkFileTable;
    private javax.swing.JLabel instructionLabel;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void insertUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean isValid(WizardDescriptor wizardDescriptor) {
        // Check the table files to make sure they are accessible and readible
        LinkedHashMapTableModel model = (LinkedHashMapTableModel) apkFileTable.getModel();
        
        if (model == null) {
            return true;
        }
        
        LinkedHashMap map = model.getMap();
        
        if (map == null || map.values() == null) {
            return true;
        }
        
        for (Object o : map.values()) {
            if (o == null) {
                continue;
            }
            
            if (o instanceof File) {
                File f = (File) o;
                
                if (!f.exists()) {
                    wizardDescriptor.putProperty("WizardPanel_errorMessage",
                    f.getAbsolutePath() + " does not exist.");
                    return false;
                }
                
                if (!f.canRead()) {
                    wizardDescriptor.putProperty("WizardPanel_errorMessage",
                    f.getAbsolutePath() + " is not readable.");
                    return false;
                }
                
            }
            
        }
        
        return true;
    }
    
    public void read(WizardDescriptor wizardDescriptor) {
        LinkedHashMapTableModel model = (LinkedHashMapTableModel) apkFileTable.getModel();
        
        if (model == null) {
            synchronized (lock) {
                model.setMap((LinkedHashMap) wizardDescriptor.getProperty(KEY_APK_FILES));
            }

            model.fireTableDataChanged();
        }
    }
    
    public void store(WizardDescriptor d) {
        LinkedHashMapTableModel model = (LinkedHashMapTableModel) apkFileTable.getModel();
        
        if (model == null) {
            return;
        }
        
        synchronized (lock) {
            d.putProperty(KEY_APK_FILES, model.getMap());
        }
    }
}