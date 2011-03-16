/*
 * Copyright 2006-2011 The Kuali Foundation
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





package org.kuali.rice.krms.api

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import junit.framework.Assert
import org.junit.Test
import org.kuali.rice.krms.api.repository.Type;
import org.kuali.rice.krms.api.repository.TypeContract;

/**
 * Exercises the immutable Country class, including XML (un)marshalling
 */
class TypeTest {

  private final shouldFail = new GroovyTestCase().&shouldFail

    @Test
    void test_create_only_required() {
        Type.Builder.create(Type.Builder.create("1", "Default", "KRMS_TEST", null)).build();
    }

  @Test
  public void testTypeBuilderPassedInParams() {
    //No assertions, just test whether the Builder gives us a KRMS Type object
    Type myType = Type.Builder.create("1", "Default", "KRMS_TEST", "").build()
  }

  @Test
  public void testTypeBuilderPassedInContract() {
    //No assertions, just test whether the Builder gives us a KRMS Type object
    Type type = Type.Builder.create(new TypeContract() {
      String getId() {"1"}
      String getName() { "Student" }
	  String getNamespace() {"KRMS_TEST" }
	  String getServiceName() {"TypeServiceImpl"}
      boolean isActive() { true }
    }).build()
  }

  public void testTypeBuilderNullTypeId() {
    shouldFail(IllegalArgumentException.class) {
      Type.Builder.create(null, "United States", "KRMS_TEST", null)
    }
  }

  public void testTypeBuilderEmptyTypeId() {
    shouldFail(IllegalArgumentException.class) {
      Type.Builder.create("  ", "United States", "KRMS_TEST", null)
    }
  }

  @Test
  public void testXmlMarshaling() {
    JAXBContext jc = JAXBContext.newInstance(Type.class)
    Marshaller marshaller = jc.createMarshaller()
    StringWriter sw = new StringWriter()

    Type myType = Type.Builder.create("2", "United States", "KRMS_TEST", null).build()
    marshaller.marshal(myType, sw)
    String xml = sw.toString()

    String expectedTypeElementXml = """
    <KRMSType xmlns="http://rice.kuali.org/schema/krms">
      <id>2</id>
      <name>United States</name>
      <namespace>KRMS_TEST</namespace>
      <active>true</active>
    </KRMSType>
    """

    Unmarshaller unmarshaller = jc.createUnmarshaller();
    Object actual = unmarshaller.unmarshal(new StringReader(xml))
    Object expected = unmarshaller.unmarshal(new StringReader(expectedTypeElementXml))
    Assert.assertEquals(expected, actual)
  }

  @Test
  public void testXmlUnmarshal() {
    String rawXml = """
    <KRMSType xmlns="http://rice.kuali.org/schema/krms">
      <id>3</id>
      <name>Student</name>
      <namespace>KRMS_TEST</namespace>
      <active>true</active>
    </KRMSType>
    """

    JAXBContext jc = JAXBContext.newInstance(Type.class)
    Unmarshaller unmarshaller = jc.createUnmarshaller();
    Type myType = (Type) unmarshaller.unmarshal(new StringReader(rawXml))
    Assert.assertEquals("3",myType.id)
    Assert.assertEquals("Student",myType.name)
	Assert.assertEquals("KRMS_TEST", myType.namespace)
	Assert.assertEquals (true, myType.active)
		

  }
}