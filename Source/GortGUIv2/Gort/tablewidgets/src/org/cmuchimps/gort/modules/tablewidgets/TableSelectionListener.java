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
package org.cmuchimps.gort.modules.tablewidgets;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author shahriyar
 */
public class TableSelectionListener implements ListSelectionListener {

    JTable table;
    AbstractTableModel model;
    
    TableSelectionListener() {
        super();
        
        this.table = table;
        this.model = model;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        
        if (table == null) {
            return;
        }
        
        if (e == null) {
            return;
        }
        
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
    }
    
}