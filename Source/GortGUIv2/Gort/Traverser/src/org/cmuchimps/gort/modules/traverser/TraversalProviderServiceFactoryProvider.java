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
package org.cmuchimps.gort.modules.traverser;

import org.cmuchimps.gort.api.gort.TraversalProviderService;
import org.cmuchimps.gort.api.gort.TraversalProviderServiceFactory;
import org.netbeans.api.project.Project;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author shahriyar
 */
@ServiceProvider(service=TraversalProviderServiceFactory.class)
public class TraversalProviderServiceFactoryProvider extends TraversalProviderServiceFactory {

    @Override
    public TraversalProviderService getInstance(Project project) {
        return new TraversalProviderServiceProvider(project);
    }
    
}
