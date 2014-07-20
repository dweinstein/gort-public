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
package org.cmuchimps.gort.modules.progressstatus;

import org.cmuchimps.gort.api.gort.ProgressStatusService;
import org.cmuchimps.gort.api.gort.ProgressStatusServiceFactory;
import org.netbeans.api.project.Project;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author shahriyar
 */
@ServiceProvider(service=ProgressStatusServiceFactory.class)
public class ProgressStatusServiceFactoryProvider extends ProgressStatusServiceFactory {

    @Override
    public ProgressStatusService getInstance(Project project) {
        return new ProgressStatusServiceProvider(project);
    }
    
}