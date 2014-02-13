/**
 * Copyright 2005-2014 The Kuali Foundation
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
package org.kuali.rice.krad.uif.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.datadictionary.parse.BeanTagAttribute;
import org.kuali.rice.krad.datadictionary.validator.ValidationTrace;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifPropertyPaths;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.component.ComponentBase;
import org.kuali.rice.krad.uif.component.DelayedCopy;
import org.kuali.rice.krad.uif.element.Header;
import org.kuali.rice.krad.uif.element.Message;
import org.kuali.rice.krad.uif.element.ValidationMessages;
import org.kuali.rice.krad.uif.field.FieldBase;
import org.kuali.rice.krad.uif.layout.LayoutManager;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycleRestriction;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.util.ExpressionUtils;
import org.kuali.rice.krad.uif.util.LifecycleElement;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.ExpressionEvaluator;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.uif.widget.Help;
import org.kuali.rice.krad.uif.widget.Tooltip;
import org.kuali.rice.krad.util.KRADUtils;

/**
 * Base <code>Container</code> implementation which container implementations
 * can extend
 *
 * <p>
 * Provides properties for the basic <code>Container</code> functionality in
 * addition to default implementation of the lifecycle methods including some
 * setup of the header, items list, and layout manager
 * </p>
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public abstract class ContainerBase extends ComponentBase implements Container {
	private static final long serialVersionUID = -4182226230601746657L;

	private int defaultItemPosition;

	private Help help;
	
	private LayoutManager layoutManager;

	private Header header;
	private Group footer;

	private String instructionalText;
	private Message instructionalMessage;

    @DelayedCopy
    private ValidationMessages validationMessages;

	/**
	 * Default Constructor
	 */
	public ContainerBase() {
		defaultItemPosition = 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isProcessRemoteFieldHolders() {
	    return true;
	}

	/**
	 * The following initialization is performed:
	 *
	 * <ul>
	 * <li>Sorts the containers list of components</li>
     * <li>Initializes the instructional field if necessary</li>
	 * <li>Initializes LayoutManager</li>
	 * </ul>
	 *
	 * {@inheritDoc}
	 */
	@SuppressWarnings("deprecation")
    @Override
	public void performInitialization(Object model) {
		super.performInitialization(model);

        if ((StringUtils.isNotBlank(instructionalText) || (getPropertyExpression("instructionalText") != null)) && (
                instructionalMessage == null)) {
            instructionalMessage = ComponentFactory.getInstructionalMessage();
        }

		if (layoutManager != null && !this.getItems().isEmpty()) {
			layoutManager.performInitialization(model);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("deprecation")
    @Override
	public void performApplyModel(Object model, LifecycleElement parent) {
		super.performApplyModel(model, parent);

		// setup summary message field if necessary
		if (instructionalMessage != null && StringUtils.isBlank(instructionalMessage.getMessageText())) {
			instructionalMessage.setMessageText(instructionalText);
		}

        if (layoutManager != null && !this.getItems().isEmpty()) {
            layoutManager.performApplyModel(model, this);
        }
	}

	/**
	 * The following finalization is performed:
	 *
	 * <ul>
	 * <li>Sets the headerText of the header Group if it is blank</li>
	 * <li>Set the messageText of the summary Message if it is blank</li>
	 * <li>Finalizes LayoutManager</li>
	 * </ul>
	 *
	 * {@inheritDoc}
	 */
	@SuppressWarnings("deprecation")
    @Override
	public void performFinalize(Object model, LifecycleElement parent) {
		super.performFinalize(model, parent);

        if(header != null){
            header.addDataAttribute(UifConstants.DataAttributes.HEADER_FOR, this.getId());
        }

        if (layoutManager != null && !this.getItems().isEmpty()) {
            layoutManager.performFinalize(model, this);
        }

        // Generate validation messages
        if (validationMessages != null) {
            validationMessages.generateMessages(ViewLifecycle.getView(), model, this);
        }
 	}

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAdditionalTemplates() {
        List<String> additionalTemplates = super.getAdditionalTemplates();

        if (layoutManager != null) {
            if (additionalTemplates.isEmpty()) {
                additionalTemplates = new ArrayList<String>();
            }
            additionalTemplates.add(layoutManager.getTemplate());
        }
        
        return additionalTemplates;
    }

    /**
     * Performs sorting of the container items based on the order property
     */
    @Override
    public void sortItems() {
        // sort items list by the order property
        List<? extends Component> sortedItems = ComponentUtils.sort(getItems(), defaultItemPosition);
        setItems(sortedItems);
    }

	/**
	 * @see org.kuali.rice.krad.uif.container.Container#getValidationMessages()
	 */
	@Override
    @BeanTagAttribute(name="validationMessages",type= BeanTagAttribute.AttributeType.SINGLEBEAN)
	@ViewLifecycleRestriction
	public ValidationMessages getValidationMessages() {
		return this.validationMessages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValidationMessages(ValidationMessages validationMessages) {
		this.validationMessages = validationMessages;
	}

	/**
	 * @see org.kuali.rice.krad.uif.widget.Helpable#getHelp()
	 */
	@Override
    @BeanTagAttribute(name="help",type= BeanTagAttribute.AttributeType.SINGLEBEAN)
	public Help getHelp() {
		return this.help;
	}

	/**
	 * @see org.kuali.rice.krad.uif.widget.Helpable#setHelp(org.kuali.rice.krad.uif.widget.Help)
	 */
	@Override
	public void setHelp(Help help) {
		this.help = help;
	}

    /**
     * For containers the help tooltip is placed on the header.
     *
     * @see org.kuali.rice.krad.uif.widget.Helpable#setTooltipOfComponent(org.kuali.rice.krad.uif.widget.Tooltip)
     */
    @Override
    public void setTooltipOfComponent(Tooltip tooltip) {
        getHeader().setToolTip(tooltip);
    }

    /**
     * Return the container header text for the help title
     *
     * @return container title
     *
     * @see org.kuali.rice.krad.uif.widget.Helpable#setTooltipOfComponent(org.kuali.rice.krad.uif.widget.Tooltip)
     */
    @Override
    public String getHelpTitle() {
        return this.getHeader().getHeaderText();
    }

    /**
	 * {@inheritDoc}
	 */
	@Override
    @BeanTagAttribute(name="items",type= BeanTagAttribute.AttributeType.LISTBEAN)
	public abstract List<? extends Component> getItems();

	/**
	 * Setter for the containers list of components
	 *
	 * @param items
	 */
	public abstract void setItems(List<? extends Component> items);

	/**
	 * For <code>Component</code> instances in the container's items list that
	 * do not have an order set, a default order number will be assigned using
	 * this property. The first component found in the list without an order
	 * will be assigned the configured initial value, and incremented by one for
	 * each component (without an order) found afterwards
	 *
	 * @return int order sequence
	 */
    @BeanTagAttribute(name="defaultItemPosition")
	public int getDefaultItemPosition() {
		return this.defaultItemPosition;
	}

	/**
	 * Setter for the container's item ordering sequence number (initial value)
	 *
	 * @param defaultItemPosition
	 */
	public void setDefaultItemPosition(int defaultItemPosition) {
		this.defaultItemPosition = defaultItemPosition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
    @BeanTagAttribute(name="layoutManager",type= BeanTagAttribute.AttributeType.SINGLEBEAN)
	public LayoutManager getLayoutManager() {
		return this.layoutManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLayoutManager(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
    @BeanTagAttribute(name="header",type= BeanTagAttribute.AttributeType.SINGLEBEAN)
	public Header getHeader() {
		return this.header;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setHeader(Header header) {
		this.header = header;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
    @BeanTagAttribute(name="footer",type= BeanTagAttribute.AttributeType.SINGLEBEAN)
	public Group getFooter() {
		return this.footer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFooter(Group footer) {
		this.footer = footer;
	}

	/**
	 * Convenience setter for configuration to turn rendering of the header
	 * on/off
	 *
	 * <p>
	 * For nested groups (like Field Groups) it is often necessary to only show
	 * the container body (the contained components). This method allows the
	 * header to not be displayed
	 * </p>
	 *
	 * @param renderHeader
	 */
	public void setRenderHeader(boolean renderHeader) {
		if (header != null) {
			header.setRender(renderHeader);
		}
	}

    /**
     * Convenience getter for the header text
     *
     * @return The text that should be displayed on the header
     */
    @BeanTagAttribute(name="headertext")
    public String getHeaderText () {
        if (header != null && header.getHeaderText() != null) {
            return header.getHeaderText();
        } else {
            return "";
        }
    }

    /**
     * Convenience setter for configuration to set the header text
     *
     * @param headerText the text that should be displayed on the header.
     */
    public void setHeaderText (String headerText) {
        if (header != null) {
            header.setHeaderText(headerText);
        }
    }

	/**
	 * Convenience setter for configuration to turn rendering of the footer
	 * on/off
	 *
	 * <p>
	 * For nested groups it is often necessary to only show the container body
	 * (the contained components). This method allows the footer to not be
	 * displayed
	 * </p>
	 *
	 * @param renderFooter
	 */
	public void setRenderFooter(boolean renderFooter) {
		if (footer != null) {
			footer.setRender(renderFooter);
		}
	}

    /**
     * Text explaining how complete the group inputs, including things like what values should be selected
     * in certain cases, what fields should be completed and so on (instructions)
     *
     * @return instructional message
     */
    @BeanTagAttribute(name="instructionalText")
	public String getInstructionalText() {
		return this.instructionalText;
	}

    /**
     * Setter for the instructional message
     *
     * @param instructionalText
     */
	public void setInstructionalText(String instructionalText) {
		this.instructionalText = instructionalText;
	}

    /**
     * Message field that displays instructional text
     *
     * <p>
     * This message field can be configured to for adjusting how the instructional text will display. Generally
     * the styleClasses property will be of most interest
     * </p>
     *
     * @return instructional message field
     */
    @BeanTagAttribute(name="instructionalMessage",type= BeanTagAttribute.AttributeType.SINGLEBEAN)
	public Message getInstructionalMessage() {
		return this.instructionalMessage;
	}

    /**
     * Setter for the instructional text message field
     *
     * <p>
     * Note this is the setter for the field that will render the instructional text. The actual text can be
     * set on the field but can also be set using {@link #setInstructionalText(String)}
     * </p>
     *
     * @param instructionalMessage
     */
	public void setInstructionalMessage(Message instructionalMessage) {
		this.instructionalMessage = instructionalMessage;
	}

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> void copyProperties(T component) {
        super.copyProperties(component);

        ContainerBase containerBaseCopy = (ContainerBase) component;

        containerBaseCopy.setDefaultItemPosition(this.defaultItemPosition);

        if (this.footer != null) {
            containerBaseCopy.setFooter((Group) this.footer.copy());
        }

        if (this.header != null) {
            containerBaseCopy.setHeader((Header) this.header.copy());
        }

        if (this.help != null) {
            containerBaseCopy.setHelp((Help) this.help.copy());
        }

        if (this.instructionalMessage != null) {
            containerBaseCopy.setInstructionalMessage((Message) this.instructionalMessage.copy());
        }

        containerBaseCopy.setInstructionalText(this.instructionalText);

        if (this.layoutManager != null) {
            containerBaseCopy.setLayoutManager((LayoutManager) this.layoutManager.copy());
        }

        if (getItems() != null) {
            List<Component> itemsCopy = ComponentUtils.copy(new ArrayList<Component>(getItems()));
            containerBaseCopy.setItems(itemsCopy);
        }

        if (this.validationMessages != null) {
            containerBaseCopy.setValidationMessages((ValidationMessages) this.validationMessages.copy());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void completeValidation(ValidationTrace tracer){
        tracer.addBean(this);

        // Checks for over writing of the instructional text or message
        if(getInstructionalText()!=null && getInstructionalMessage()!=null){
            String currentValues [] = {"instructionalMessage.text = "+getInstructionalMessage().getMessageText(),"instructionalText = "+getInstructionalText()};
            tracer.createWarning("InstructionalMessage will override instructioanlText",currentValues);
        }

        super.completeValidation(tracer.getCopy());
    }

}
