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

package org.kuali.rice.krms.impl.repository

import groovy.mock.interceptor.MockFor
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.kuali.rice.kns.service.BusinessObjectService
import org.kuali.rice.kns.util.KNSPropertyConstants
import org.kuali.rice.krms.api.repository.Type;
import org.kuali.rice.krms.api.repository.TypeService;
import org.kuali.rice.krms.impl.repository.TypeBo;
import org.kuali.rice.krms.impl.repository.TypeServiceImpl;

class TypeServiceImplTest {

  private final shouldFail = new GroovyTestCase().&shouldFail

  static Map<String, TypeBo> sampleTypes = new HashMap<String, TypeBo>()
  static Map<String, TypeBo> sampleTypesKeyedByName = new HashMap<String, TypeBo>()
  def mockBusinessObjectService

  @BeforeClass
  static void createSampleTypeBOs() {
    TypeBo defaultBo = new TypeBo(active: true, id: "1", name: "DEFAULT", namespace: "KRMS_TEST", serviceName: "TypeServiceImpl")
    TypeBo studentBo = new TypeBo(active: true, id: "2", name: "Student", namespace: "KRMS_TEST", serviceName: "TypeServiceImpl")
    TypeBo ifopalBo = new TypeBo(active: true, id: "3", name: "IFOPAL", namespace: "KC_TEST", serviceName: null)
    for (bo in [defaultBo, studentBo, ifopalBo]) {
      sampleTypes.put(bo.id, bo)
      sampleTypesKeyedByName.put(bo.name, bo)
    }
  }

  @Before
  void setupBoServiceMockContext() {
    mockBusinessObjectService = new MockFor(BusinessObjectService.class)
  }


  @Test
  public void test_getType() {
    mockBusinessObjectService.demand.findByPrimaryKey(1..1) {
      Class clazz, Map map -> return sampleTypes.get(map.get(Type.Elements.ID))
    }

    BusinessObjectService bos = mockBusinessObjectService.proxyDelegateInstance()

    TypeService service = new TypeServiceImpl()
    service.setBusinessObjectService(bos)
    Type myType = service.getTypeById("1")

    Assert.assertEquals(TypeBo.to(sampleTypes.get("1")), myType)
    mockBusinessObjectService.verify(bos)
  }

  @Test
  public void testGetByPrimaryIdEmptyTypeId() {
      shouldFail(IllegalArgumentException.class) {
        new TypeServiceImpl().getTypeById("")
      }
  }

  @Test
  public void testGetByPrimaryIdNullTypeId() {
      shouldFail(IllegalArgumentException.class) {
        new TypeServiceImpl().getTypeById(null)
      }
  }

  @Test
  public void testGetByNameAndNamespace() {
    mockBusinessObjectService.demand.findMatching(1..2) {
      Class clazz, Map map ->
      [sampleTypesKeyedByName.get(
              map.get(KNSPropertyConstants.ALTERNATE_POSTAL_COUNTRY_CODE))]
    }
    BusinessObjectService bos = mockBusinessObjectService.proxyDelegateInstance()

    TypeService service = new TypeServiceImpl()
    service.setBusinessObjectService(bos)
    Type myType = service.getTypeByNameAndNamespace("Student","KRMS_TEST")

    Assert.assertEquals(TypeBo.to(sampleTypesKeyedByName.get("Student")), myType)
    mockBusinessObjectService.verify(bos)
  }

//  @Test
//  public void testGetByIdWhenNoneFound() {
//    mockBusinessObjectService.demand.findMatching(1..1) {Class clazz, Map map -> []}
//    BusinessObjectService bos = mockBusinessObjectService.proxyDelegateInstance()
//
//    CountryService service = new CountryServiceImpl()
//    service.setBusinessObjectService(bos)
//    Country country = service.getCountryByAlternateCode("ZZ")
//
//    Assert.assertNull(country)
//    mockBusinessObjectService.verify(bos)
//
//  }
//
//  @Test
//  public void testGetByAlternatePostalCountryCodeWhenMultipleFound() {
//    mockBusinessObjectService.demand.findMatching(1..1) {
//      Class clazz, Map map -> [sampleCountries.get("US"), sampleCountries.get("AU")]
//    }
//    BusinessObjectService bos = mockBusinessObjectService.proxyDelegateInstance()
//
//    CountryService service = new CountryServiceImpl()
//    service.setBusinessObjectService(bos)
//
//    shouldFail(IllegalStateException.class) {
//      service.getCountryByAlternateCode("US")
//    }
//
//    mockBusinessObjectService.verify(bos)
//  }
//
//  @Test
//  public void testGetByAlternatePostalCountryCodeWithEmptyCode() {
//      shouldFail(IllegalArgumentException.class) {
//        new CountryServiceImpl().getCountryByAlternateCode(" ")
//      }
//  }
//
//  @Test
//  public void testGetByAlternatePostalCountryCodeWithNullCode() {
//      shouldFail(IllegalArgumentException.class) {
//        new CountryServiceImpl().getCountryByAlternateCode(null)
//      }
//  }

  @Test
  public void findAllTypesByNamespace() {
     mockBusinessObjectService.demand.findMatching(1..1) {
      Class clazz, Map map -> [sampleTypes.get("1"), sampleTypes.get("2")]
    }
    BusinessObjectService bos = mockBusinessObjectService.proxyDelegateInstance()
    TypeService service = new TypeServiceImpl()
    service.setBusinessObjectService(bos)
    service.findAllTypesByNamespace()

    mockBusinessObjectService.verify(bos)
  }

  @Test
  public void testFindAllTypes() {
    mockBusinessObjectService.demand.findMatching(1..1) {
      Class clazz, Map map -> [sampleTypes.get("1"), sampleTypes.get("2"), sampleTypes.get("3")]
    }
    BusinessObjectService bos = mockBusinessObjectService.proxyDelegateInstance()

    TypeService service = new TypeServiceImpl()
    service.setBusinessObjectService(bos)
    service.findAllTypes()

    mockBusinessObjectService.verify(bos)
  }
}
