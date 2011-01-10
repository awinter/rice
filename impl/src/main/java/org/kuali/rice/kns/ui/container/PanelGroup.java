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
package org.kuali.rice.kns.ui.container;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.ui.Component;
import org.kuali.rice.kns.ui.field.HeaderField;

/**
 * This is a description of what this class does - jkneal don't forget to fill
 * this in.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class PanelGroup extends Group {
	private String wrappedGroupTemplate;

	private boolean defaultOpen;

	private HeaderField panelHeader;

	public PanelGroup() {
		defaultOpen = true;
	}

	/**
	 * @see org.kuali.rice.kns.ui.container.ContainerBase#initialize(java.util.Map)
	 */
	@Override
	public void initialize(Map<String, String> options) {
		super.initialize(options);

		// if panel title not given, use the section title on the panel
		if (StringUtils.isBlank(panelHeader.getHeaderText())) {
			panelHeader.setHeaderText(this.getTitle());
		}
	}
	
	/**
	 * @see org.kuali.rice.kns.ui.ComponentBase#getNestedComponents()
	 */
	@Override
	public List<Component> getNestedComponents() {
		List<Component> components = super.getNestedComponents();

		components.add(panelHeader);

		return components;
	}

	public boolean isDefaultOpen() {
		return this.defaultOpen;
	}

	public void setDefaultOpen(boolean defaultOpen) {
		this.defaultOpen = defaultOpen;
	}

	public HeaderField getPanelHeader() {
		return this.panelHeader;
	}

	public void setPanelHeader(HeaderField panelHeader) {
		this.panelHeader = panelHeader;
	}

	public String getWrappedGroupTemplate() {
		return this.wrappedGroupTemplate;
	}

	public void setWrappedGroupTemplate(String wrappedGroupTemplate) {
		this.wrappedGroupTemplate = wrappedGroupTemplate;
	}

}
