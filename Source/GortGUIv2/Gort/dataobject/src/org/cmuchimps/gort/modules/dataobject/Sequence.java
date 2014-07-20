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
package org.cmuchimps.gort.modules.dataobject;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author shahriyar
 */
@Entity
public class Sequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    // a sequence is visited if even a part of it is traversed. it may malfunction
    // midway which marks the sequence as non-deterministic but still visited.
    private Boolean visited;
    private Boolean deterministic;
    
    // Ranking is a string of the ids of the interactions for which the
    // sequence should be performed.
    private String rank;
    
    @ManyToOne
    @JoinColumn(name="traversal_fk")
    private Traversal traversal;
    
    @ManyToOne
    @JoinColumn(name="state_fk")
    private State state;
    
    @ManyToMany
    private Set<Interaction> interactions;
}