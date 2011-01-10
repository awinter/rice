/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kns.ui.field;

import java.util.List;

import org.kuali.rice.kns.ui.Component;

/**
 * This is a description of what this class does - jkneal don't forget to fill
 * this in.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class LabelField extends FieldBase {
	private String labelText;
	private String labelForComponentId;

	private boolean renderColon;

	private MessageField requiredMessageField;

	public LabelField() {
		renderColon = true;
	}
	
	/**
	 * @see org.kuali.rice.kns.ui.ComponentBase#getNestedComponents()
	 */
	@Override
	public List<Component> getNestedComponents() {
		List<Component> components = super.getNestedComponents();

		components.add(requiredMessageField);

		return components;
	}

	public String getLabelForComponentId() {
		return this.labelForComponentId;
	}

	public void setLabelForComponentId(String labelForComponentId) {
		this.labelForComponentId = labelForComponentId;
	}

	public String getLabelText() {
		return this.labelText;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	public boolean isRenderColon() {
		return this.renderColon;
	}

	public void setRenderColon(boolean renderColon) {
		this.renderColon = renderColon;
	}

	public MessageField getRequiredMessageField() {
		return this.requiredMessageField;
	}

	public void setRequiredMessageField(MessageField requiredMessageField) {
		this.requiredMessageField = requiredMessageField;
	}

}
