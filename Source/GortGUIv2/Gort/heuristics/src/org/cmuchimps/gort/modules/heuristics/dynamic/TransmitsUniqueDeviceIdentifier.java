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
package org.cmuchimps.gort.modules.heuristics.dynamic;

import java.util.LinkedHashSet;
import org.cmuchimps.gort.api.gort.heuristic.AbstractDynamicHeuristic;
import org.cmuchimps.gort.modules.helper.AndroidPermissions;
import org.cmuchimps.gort.modules.dataobject.History;
import org.cmuchimps.gort.modules.dataobject.State;
import org.cmuchimps.gort.modules.dataobject.TaintLog;
import org.cmuchimps.gort.modules.dataobject.Traversal;
import org.cmuchimps.gort.modules.helper.TaintHelper;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;

/**
 *
 * @author shahriyar
 */
public class TransmitsUniqueDeviceIdentifier extends AbstractDynamicHeuristic {
    public static final String NAME = "Transmits Device Unique Device Identifier";
    public static final String SUMMARY = "App transmits device's unique device identifier (e.g., IMEI, IMSI, ICCID).";
    
    private boolean output = false;
    
    public TransmitsUniqueDeviceIdentifier() {
        super(NAME, SUMMARY);
    }

    public TransmitsUniqueDeviceIdentifier(Project project, FileObject apk, Traversal traversal) {
        this();
        this.project = project;
        this.apk = apk;
        this.traversal = traversal;
    }
    
    @Override
    public AbstractDynamicHeuristic getInstance(Project project, FileObject apk, Traversal traversal) {
        return new TransmitsUniqueDeviceIdentifier(project, apk, traversal);
    }

    @Override
    public void init() {
        if (!hasAllPermissions(new String[]{AndroidPermissions.INTERNET, AndroidPermissions.READ_PHONE_STATE})) {
            setComputed(true);
        }
    }

    @Override
    public void onActivityChange(History h) {
        // do nothing
    }

    @Override
    public void onBack(History h) {
        // do nothing
    }

    @Override
    public void onClick(History h) {
        // do nothing
    }

    @Override
    public void onDialog(History h) {
        // do nothing
    }

    @Override
    public void onKeyboard(History h) {
        // do nothing
    }

    @Override
    public void onStateChange(History h) {
        // do nothing
    }

    @Override
    public void onTransmissionTaintLog(TaintLog t) {
        if (isComputed()) {
            return;
        }
        
        if (t == null) {
            return;
        }
        
        Integer taintTag = t.getTainttag();
        
        if (taintTag == null) {
            return;
        }
        
        if (TaintHelper.checkTag(taintTag, TaintHelper.TAINT_IMEI) ||
                TaintHelper.checkTag(taintTag, TaintHelper.TAINT_IMSI) ||
                TaintHelper.checkTag(taintTag, TaintHelper.TAINT_ICCID)) {
            output = true;
            
            State state = t.getState();
            
            if (state != null) {
                getStateIds().add(state.getId());
            }
        }
    }

    @Override
    public void onNonTransmissionTaintLog(TaintLog t) {
        // do nothing
    }

    @Override
    public void onTraversalStart(History h) {
        // do nothing
    }
    
    @Override
    public Boolean output() {
        super.output();
        return hasPermission(AndroidPermissions.INTERNET) && output;
    }
    
    @Override
    public LinkedHashSet<Integer> getAssociatedStateIds() {
        if (output()) {
            return getStateIds();
        }
        
        return null;
    }
}
