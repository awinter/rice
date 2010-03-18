/*
 * Copyright 2005-2008 The Kuali Foundation
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
package org.kuali.rice.kns.bo;

import javax.persistence.Column;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * This Compound Primary Class has been generated by the rice ojb2jpa Groovy script.  Please
 * note that there are no setter methods, only getters.  This is done purposefully as cpk classes
 * can not change after they have been created.  Also note they require a public no-arg constructor.
 * TODO: Implement the equals() and hashCode() methods. 
 */
public class ParameterId extends CompositePrimaryKeyBase {

    private static final long serialVersionUID = -8210189691273413060L;
    
	@Column(name="NMSPC_CD")
    private String parameterNamespaceCode;
    @Column(name="PARM_DTL_TYP_CD")
    private String parameterDetailTypeCode;
    @Column(name="PARM_NM")
    private String parameterName;
    @Column(name="APPL_NMSPC_CD")
    private String parameterApplicationNamespaceCode;

    public ParameterId() {}
    
    public ParameterId(String parameterNamespaceCode, String parameterDetailTypeCode, String parameterName, String parameterApplicationNamespaceCode) {
    	this.parameterNamespaceCode = parameterNamespaceCode;
    	this.parameterDetailTypeCode = parameterDetailTypeCode;
    	this.parameterName = parameterName;
    	this.parameterApplicationNamespaceCode = parameterApplicationNamespaceCode;
    }

    public String getParameterNamespaceCode() { return parameterNamespaceCode; }

    public String getParameterDetailTypeCode() { return parameterDetailTypeCode; }

    public String getParameterName() { return parameterName; }
    
    public String getParameterApplicationNamespaceCode() { return parameterApplicationNamespaceCode; }
}

