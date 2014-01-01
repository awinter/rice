/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krad.uif.lifecycle.model;

import org.kuali.rice.krad.uif.lifecycle.ViewLifecycleTaskBase;
import org.kuali.rice.krad.uif.lifecycle.ApplyModelComponentPhase;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecyclePhase;

/**
 * Perform default apply model behavior defined for the component.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class ComponentDefaultApplyModelTask extends ViewLifecycleTaskBase {

    /**
     * Create a task to assign component IDs during the apply model phase.
     * 
     * @param phase The apply model phase for the component.
     */
    public ComponentDefaultApplyModelTask(ViewLifecyclePhase phase) {
        super(phase);
    }

    /**
     * @see org.kuali.rice.krad.uif.lifecycle.ViewLifecycleTaskBase#getPhase()
     */
    @Override
    public ApplyModelComponentPhase getPhase() {
        return (ApplyModelComponentPhase) super.getPhase();
    }

    /**
     * @see org.kuali.rice.krad.uif.lifecycle.ViewLifecycleTaskBase#performLifecycleTask()
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void performLifecycleTask() {
        ApplyModelComponentPhase phase = getPhase();
        phase.getElement().performApplyModel(phase.getModel(), phase.getParent());
    }

}