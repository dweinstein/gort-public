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
package org.cmuchimps.gort.modules.crowdanalysis.tasks;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import javax.persistence.EntityManager;
import org.cmuchimps.gort.modules.crowdanalysis.Installer;
import org.cmuchimps.gort.modules.dataobject.CrowdTask;
import org.cmuchimps.gort.modules.dataobject.GortEntityManager;
import org.cmuchimps.gort.modules.dataobject.HIT;
import org.openide.filesystems.FileObject;

/**
 *
 * @author shahriyar
 */
public class AppTaskExtraction extends AbstractCrowdTask {

    private static final String HEADER = "appname\tdescription\tscreenshot0\tscreenshot1\tscreenshot2\tscreenshot3\ttraversal\thistory\tstartstate\tendstate\tstartscreenshot\tendscreenshot\tsingle\n";
    
    public AppTaskExtraction(FileObject resultsFO, FileObject mTurkPropertiesFO,
            FileObject questionFO, FileObject propertiesFO, FileObject inputFO,
            List<CrowdTask> crowdTasks, GortEntityManager gem, EntityManager em) {
        super(resultsFO, mTurkPropertiesFO, questionFO, propertiesFO, inputFO, crowdTasks, gem, em);
    }
    
    public AppTaskExtraction(FileObject resultsFO, FileObject mTurkPropertiesFO,
            FileObject inputFO, List<CrowdTask> crowdTasks, GortEntityManager gem, EntityManager em) {
        this(resultsFO, mTurkPropertiesFO,
                //Installer.getCrowdTaskFile("Tasks/apptaskextraction.question"),
                Installer.getCrowdTaskFile("Tasks/apptaskextraction.xml"),
                Installer.getCrowdTaskFile("Tasks/task.properties"),
                inputFO, crowdTasks, gem, em);
    }
    
    public AppTaskExtraction(FileObject resultsFO, FileObject mTurkPropertiesFO,
            List<CrowdTask> crowdTasks, GortEntityManager gem, EntityManager em) {
        this(resultsFO, mTurkPropertiesFO,
                Installer.getCrowdTaskFile("Tasks/apptaskextraction.xml"),
                Installer.getCrowdTaskFile("Tasks/task.properties"),
                Installer.getCrowdTaskFile("Tasks/apptaskextraction.input"),
                crowdTasks, gem, em);
    }

    public static void writeHeader(Writer writer) throws IOException {
        if (writer == null) {
            return;
        }
        
        writer.write(HEADER);
        writer.flush();
    }

    public static void writeInput(Writer writer, String name,
            String description, String appScreenshot0, String appScreenshot1,
            String stateScreenshot0, String stateScreenshot1, int traversalId,
            int historyId, int startStateId, int endStateId, int startScreenshotId,
            int endScreenshotId, boolean single) throws IOException {
        if (writer == null) {
            return;
        }
        
        writer.write(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%d\t%d\t%d\t%d\t%d\t%d\t%s\n", name, description,
                appScreenshot0, appScreenshot1, stateScreenshot0, stateScreenshot1,
                traversalId, historyId, startStateId, endStateId, startScreenshotId,
                endScreenshotId, (single) ? "true" : "false"));
        writer.flush();
    }
    
    @Override
    public String getType() {
        return HIT.TYPE_TASK_EXTRACTION;
    }
    
}
