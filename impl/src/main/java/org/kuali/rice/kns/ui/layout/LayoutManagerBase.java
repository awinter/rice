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
package org.kuali.rice.kns.ui.layout;

import java.util.Map;

/**
 * This is a description of what this class does - jkneal don't forget to fill
 * this in.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public abstract class LayoutManagerBase implements LayoutManager {
	private String id;
	private String template;
	private String style;
	private String styleClass;

	private String labelPlacement;

	public LayoutManagerBase() {

	}

	/**
	 * Default initialize implementation
	 * 
	 * @see org.kuali.rice.kns.ui.layout.LayoutManager#initialize(java.util.Map)
	 */
	@Override
	public void initialize(Map<String, String> options) {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyleClass() {
		return this.styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getLabelPlacement() {
		return this.labelPlacement;
	}

	public void setLabelPlacement(String labelPlacement) {
		this.labelPlacement = labelPlacement;
	}

}
