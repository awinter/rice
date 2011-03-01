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
package org.kuali.rice.kns.uif;

import java.io.Serializable;
import java.util.List;

import org.kuali.rice.kns.uif.container.View;
import org.kuali.rice.kns.uif.decorator.ComponentDecorator;
import org.kuali.rice.kns.uif.decorator.DecoratorChain;
import org.kuali.rice.kns.uif.modifier.ComponentModifier;
import org.kuali.rice.kns.uif.service.ViewHelperService;

/**
 * All classes of the UIF that are used as a rendering element implement the
 * component interface. This interface defines basic properties and methods that
 * all such classes much implement. All components within the framework have the
 * following structure:
 * <ul>
 * <li>Dictionary Configuration/Composition</li>
 * <li>Java Class (the Component implementation</li>
 * <li>>JSP Template Renderer</li>
 * </ul>
 * 
 * There are three basic types of components:
 * <ul>
 * <li>Container Components: <code>View</code>, <code>Group</code></li>
 * <li>Field Components: <code>Field</code></li>
 * <li>Widget Components: <code>Widget</code></li>
 * </ul>
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 * 
 * @see org.kuali.rice.kns.uif.container.Container
 * @see org.kuali.rice.kns.uif.field.Field
 * @see org.kuali.rice.kns.uif.widget.Widget
 */
public interface Component extends Serializable {

	/**
	 * The unique id (within a given tree) for the component
	 * 
	 * <p>
	 * The id will be used by renderers to set the HTML element id. This gives a
	 * way to find various elements for scripting. If the id is not given, a
	 * default will be generated by the framework
	 * </p>
	 * 
	 * @return String id
	 */
	public String getId();

	/**
	 * Sets the unique id (within a given tree) for the component
	 * 
	 * @param id
	 *            - string to set as the component id
	 */
	public void setId(String id);

	/**
	 * The name for the component type
	 * 
	 * <p>
	 * This is used within the rendering layer to pass the component instance
	 * into the template. The component instance is exported under the name
	 * given by this method.
	 * </p>
	 * 
	 * @return String type name
	 */
	public String getComponentTypeName();

	/**
	 * The path to the JSP file that should be called to render the component
	 * 
	 * <p>
	 * The path should be relative to the web root. An attribute will be
	 * available to the component to use under the name given by the method
	 * <code>getComponentTypeName</code>. Based on the component type,
	 * additional attributes could be available for use. See the component
	 * documentation for more information on such attributes.
	 * </p>
	 * 
	 * <p>
	 * e.g. '/krad/WEB-INF/jsp/tiles/component.jsp'
	 * </p>
	 * 
	 * @return String representing the template path
	 */
	public String getTemplate();

	/**
	 * Setter for the components template
	 * 
	 * @param template
	 */
	public void setTemplate(String template);

	/**
	 * A title for the component. Depending on the component can be used in
	 * various ways. For example with a Container component the title is used to
	 * set the header text. For components like controls other other components
	 * that render an HTML element it is used to set the HTML title attribute
	 * 
	 * @return String title for component
	 */
	public String getTitle();

	/**
	 * Setter for the components title
	 * 
	 * @param title
	 */
	public void setTitle(String title);

	/**
	 * Should be called to initialize the component
	 * 
	 * <p>
	 * Where components can set defaults and setup other necessary state. The
	 * initialize method should only be called once per component lifecycle and
	 * is invoked within the initialize phase of the view lifecylce.
	 * </p>
	 * 
	 * @param view
	 *            - view instance in which the component belongs
	 * @see ViewHelperService#initializeComponent
	 */
	public void performInitialization(View view);

	/**
	 * Called after the initialize phase to perform conditional logic based on
	 * the model data
	 * 
	 * <p>
	 * Where components can perform conditional logic such as dynamically
	 * generating new fields or setting field state based on the given data
	 * </p>
	 * 
	 * @param view
	 *            - view instance to which the component belongs
	 * @param model
	 *            - Top level object containing the data (could be the form or a
	 *            top level business object, dto)
	 */
	public void performApplyModel(View view, Object model);

	/**
	 * The last phase before the view is rendered. Here final preparations can
	 * be made based on the updated view state
	 * 
	 * 
	 * @param view
	 *            - view instance that should be finalized for rendering
	 * @param model
	 *            - top level object containing the data
	 */
	public void performFinalize(View view, Object model);

	/**
	 * List of components that are contained within the component
	 * 
	 * <p>
	 * Used by <code>ViewHelperService</code> for the various lifecycle
	 * callbacks
	 * </p>
	 * 
	 * @return List<Component> child components
	 */
	public List<Component> getNestedComponents();

	/**
	 * <code>ComponentModifier</code> instances that should be invoked to
	 * initialize the component
	 * 
	 * <p>
	 * These provide dynamic initialization behavior for the component and are
	 * configured through the components definition. Each initializer will get
	 * invoked by the initialize method.
	 * </p>
	 * 
	 * @return List of component initializers
	 * @see ViewHelperService#initializeComponent
	 */
	public List<ComponentModifier> getComponentInitializers();

	/**
	 * Setter for the components List of <code>ComponentModifier</code>
	 * instances
	 * 
	 * @param componentModifiers
	 */
	public void setComponentInitializers(List<ComponentModifier> componentModifiers);

	/**
	 * Indicates whether the component should be rendered in the UI
	 * 
	 * <p>
	 * If set to false, the corresponding component template will not be invoked
	 * (therefore nothing will be rendered to the UI).
	 * </p>
	 * 
	 * @return boolean true if the component should be rendered, false if it
	 *         should not be
	 */
	public boolean isRender();

	/**
	 * Setter for the components render indicator
	 * 
	 * @param render
	 */
	public void setRender(boolean render);

	/**
	 * Indicates whether the component should be hidden in the UI
	 * 
	 * <p>
	 * How the hidden data is maintained depends on the views persistence mode.
	 * If the mode is request, the corresponding data will be rendered to the UI
	 * but not visible. If the mode is session, the data will not be rendered to
	 * the UI but maintained server side.
	 * </p>
	 * 
	 * <p>
	 * For a <code>Container</code> component, the hidden setting will apply to
	 * all contained components (making a section hidden makes all fields within
	 * the section hidden)
	 * </p>
	 * 
	 * @return boolean true if the component should be hidden, false if it
	 *         should be visible
	 */
	public boolean isHidden();

	/**
	 * Setter for the hidden indicator
	 * 
	 * @param hidden
	 */
	public void setHidden(boolean hidden);

	/**
	 * Indicates whether the component can be edited
	 * 
	 * <p>
	 * When readOnly the controls and widgets of <code>Field</code> components
	 * will not be rendered. If the Field has an underlying value it will be
	 * displayed readOnly to the user.
	 * </p>
	 * 
	 * <p>
	 * For a <code>Container</code> component, the readOnly setting will apply
	 * to all contained components (making a section readOnly makes all fields
	 * within the section readOnly)
	 * </p>
	 * </p>
	 * 
	 * @return boolean true if the component should be readOnly, false if is
	 *         allows editing
	 */
	public boolean isReadOnly();

	/**
	 * Setter for the read only indicator
	 * 
	 * @param readOnly
	 */
	public void setReadOnly(boolean readOnly);

	/**
	 * Indicates whether the component is required
	 * 
	 * <p>
	 * At the general component level required means there is some action the
	 * user needs to take within the component. For example, within a section it
	 * might mean the fields within the section should be completed. At a field
	 * level, it means the field should be completed. This provides the ability
	 * for the renderers to indicate the required action.
	 * </p>
	 * 
	 * @return boolean true if the component is required, false if it is not
	 *         required
	 */
	public Boolean getRequired();

	/**
	 * Setter for the required indicator
	 * 
	 * @param required
	 */
	public void setRequired(Boolean required);

	/**
	 * CSS style string to be applied to the component
	 * 
	 * <p>
	 * Any style override or additions can be specified with this attribute.
	 * This is used by the renderer to set the style attribute on the
	 * corresponding element.
	 * </p>
	 * 
	 * <p>
	 * e.g. 'color: #000000;text-decoration: underline;'
	 * </p>
	 * 
	 * @return String css style string
	 */
	public String getStyle();

	/**
	 * Setter for the components style
	 * 
	 * @param style
	 */
	public void setStyle(String style);

	/**
	 * CSS style class(s) to be applied to the component
	 * 
	 * <p>
	 * Declares style classes for the component. Multiple classes are specified
	 * with a space delimiter. This is used by the renderer to set the class
	 * attribute on the corresponding element. The class(s) declared must be
	 * available in the common style sheets or the style sheets specified for
	 * the view
	 * </p>
	 * 
	 * <p>
	 * e.g. 'header left'
	 * </p>
	 * 
	 * @return List<String> css style classes to apply
	 */
	public List<String> getStyleClasses();

	/**
	 * Setter for the components style classes
	 * 
	 * @param styleClass
	 */
	public void setStyleClasses(List<String> styleClasses);

	/**
	 * Number of places the component should take up horizontally in the
	 * container
	 * 
	 * <p>
	 * All components belong to a <code>Container</code> and are placed using a
	 * <code>LayoutManager</code>. This property specifies how many places
	 * horizontally the component should take up within the container. This is
	 * only applicable for table based layout managers. Default is 1
	 * </p>
	 * 
	 * TODO: this should not be on component interface since it only applies if
	 * the layout manager supports it, need some sort of layoutOptions map for
	 * field level options that depend on the manager
	 * 
	 * @return int number of columns to span
	 */
	public int getColSpan();

	/**
	 * Setter for the components column span
	 * 
	 * @param colSpan
	 */
	public void setColSpan(int colSpan);

	/**
	 * Number of places the component should take up vertically in the container
	 * 
	 * <p>
	 * All components belong to a <code>Container</code> and are placed using a
	 * <code>LayoutManager</code>. This property specifies how many places
	 * vertically the component should take up within the container. This is
	 * only applicable for table based layout managers. Default is 1
	 * </p>
	 * 
	 * TODO: this should not be on component interface since it only applies if
	 * the layout manager supports it, need some sort of layoutOptions map for
	 * field level options that depend on the manager
	 * 
	 * @return int number of rows to span
	 */
	public int getRowSpan();

	/**
	 * Setter for the component row span
	 * 
	 * @param rowSpan
	 */
	public void setRowSpan(int rowSpan);

	/**
	 * <code>ComponentDecorator</code> instance for the component
	 * 
	 * <p>
	 * Decorators can be used to wrap the given component with content
	 * (providing content before and after the component output). Multiple
	 * decorators can be applied by continually setting the decorator property
	 * (decorator for decorator). A <code>DecoratorChain</code> will be built up
	 * to render the decorators in the correct order
	 * </p>
	 * 
	 * @return ComponentDecorator instance
	 * @see org.kuali.rice.kns.uif.decorator.ComponentDecorator
	 */
	public ComponentDecorator getDecorator();

	/**
	 * Setter for the components decorator
	 * 
	 * @param decorator
	 */
	public void setDecorator(ComponentDecorator decorator);

	/**
	 * Returns the <code>DecoratorChain</code> instance that will return the
	 * <code>ComponentDecorator</code> instances for the component in the
	 * correct order for rendering
	 * 
	 * @return DecoratorChain instance
	 */
	public DecoratorChain getDecoratorChain();

	/**
	 * Can be used to order a component within a List of other components, lower
	 * numbers are placed higher up in the list, while higher numbers are placed
	 * lower in the list
	 * 
	 * @return int ordering number
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	public int getOrder();

	/**
	 * Setter for the component's order
	 * 
	 * @param order
	 */
	public void setOrder(int order);

}
