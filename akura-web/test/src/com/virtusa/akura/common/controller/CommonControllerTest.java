/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
 *
 * Copyright (C) 2012 Virtusa Corporation.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.virtusa.akura.common.controller;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Stream;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.CityValidator;
import com.virtusa.akura.common.validator.CountryValidator;
import com.virtusa.akura.common.validator.DistrictValidator;
import com.virtusa.akura.common.validator.ProvinceValidator;
import com.virtusa.akura.common.validator.StreamValidator;
import com.virtusa.akura.common.validator.SubjectValidator;

/**
 * This test class, tests all the presentation related functionality for the Province domain object.
 * 
 * @author Virtusa Corporation
 */
public class CommonControllerTest extends BaseWebTest {
    
    /**
     * Represents an instance of CommonController.
     */
    private CommonController controller;
    
    /**
     * Represents an instance of CommonService.
     */
    @Autowired
    private CommonService commonService;
    
    /**
     * Represents an instance of MockHttpServletRequest.
     */
    private MockHttpServletRequest request;
    
    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;
    
    /**
     * Represents an instance of Province.
     */
    private Province province;
    
    /**
     * Represents an instance of District.
     */
    private District district;
    
    /**
     * Represents an instance of City.
     */
    private City city;
    
    /**
     * Represents an instance of Country.
     */
    private Country country;
    
    /** Represents an instance of the Subject. */
    private Subject subject;
    
    /** Represents an instance of the Stream. */
    private Stream stream;
    
    /**
     * Represents an instance of ProvinceValidator.
     */
    @Autowired
    private ProvinceValidator provinceValidator;
    
    /**
     * Represents an instance of DistrictValidator.
     */
    @Autowired
    private DistrictValidator districtValidator;
    
    /**
     * Represents an instance of CityValidator.
     */
    @Autowired
    private CityValidator cityValidator;
    
    /**
     * Represents an instance of CiountryValidator.
     */
    @Autowired
    private CountryValidator countryValidator;
    
    /** Represents an instance of the SubjectValidator. */
    @Autowired
    private SubjectValidator subjectValidator;
    
    /** Represents an instance of the StreamValidator. */
    @Autowired
    private StreamValidator streamValidator;
    
    /**
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;
    
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        province = new Province();
        district = new District();
        city = new City();
        country = new Country();
        model = new ModelMap();
        controller = new CommonController();
        province.setDescription("testProvinceDDD");
        district.setDescription("testDistrictDDD");
        city.setDescription("testCity123");
        country.setCountryName("testCountry123");
        controller.setCommonService(commonService);
        
        stream = new Stream();
        stream.setDescription("StreamDescriptionDDD");
        stream.setModifiedTime(new Date());
        
        subject = new Subject();
        subject.setDescription("SubjectDescriptionDDD");
        subject.setModifiedTime(new Date());
        
        model = new ModelMap();
        request = new MockHttpServletRequest();
        
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.CommonController# searchProvince(javax.servlet.http.HttpServletRequest, org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.Province)}
     * .
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSearchProvince() throws AkuraAppException {

        String message = "message";
        // you can set request method type and the url.
        request = new MockHttpServletRequest();
        Province saveProvince = commonService.addProvince(province);
        assertNotNull(saveProvince);
        Province newProvince = commonService.findProvince(province.getProvinceId());
        String searchDescription = newProvince.getDescription();
        request.setParameter("searchDescription", searchDescription);
        String result = controller.searchProvince(request, model, province);
        assertNotSame(message, model.containsKey("message"));
        assertNotNull("ModelAndView should not be null", result);
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.CommonController# saveOrUpdateProvince(org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.Province, org.springframework.validation.BindingResult)}
     * .
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateProvince() throws AkuraAppException {

        controller.setProvinceValidator(provinceValidator);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        String view = controller.saveOrUpdateProvince(model, province, mockBindingResult);
        // Check that we returned back to the original form:
        assertEquals("reference/manageProvince", view);
        // While the default boolean return value for a mock is 'false',
        // it's good to be explicit anyway:
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        String savedView = controller.saveOrUpdateProvince(model, province, mockBindingResult);
        assertEquals("redirect:manageProvince.htm", savedView);
        assertTrue(model.size() > 0);
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.CommonController#saveOrUpdateDistrict(org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.District, org.springframework.validation.BindingResult)}
     * .
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateDistrict() throws AkuraAppException {

        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull(newProvince);
            district.setProvince(newProvince);
            controller.setDistrictValidator(districtValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view = controller.saveOrUpdateDistrict(model, district, mockBindingResult);
            assertEquals("reference/manageDistrict", view);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView = controller.saveOrUpdateDistrict(model, district, mockBindingResult);
            assertEquals("redirect:manageDistrict.htm", savedView);
            assertTrue(model.size() > 0);
        } catch (AkuraAppException e) {
            throw new AkuraAppException();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.CommonController #searchDistrict(javax.servlet.http.HttpServletRequest, org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.District)}
     * .
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSearchDistrict() throws AkuraAppException {

        try {
            String message = "message";
            // you can set request method type and the url.
            request = new MockHttpServletRequest();
            Province newProvince = commonService.addProvince(province);
            assertNotNull(newProvince);
            district.setProvince(province);
            District newDistrict = commonService.addDistrict(district);
            assertNotNull(newDistrict);
            District findDistrict = commonService.findDistrict(newDistrict.getDistrictId());
            String searchDescription = findDistrict.getDescription();
            request.setParameter("searchDescription", searchDescription);
            String result = controller.searchDistrict(request, model, findDistrict);
            assertNotSame(message, model.containsKey("message"));
            assertNotNull("ModelAndView should not be null", result);
        } catch (AkuraAppException e) {
            throw new AkuraAppException();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.CommonController# searchCity(javax.servlet.http.HttpServletRequest, org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.City)}
     * .
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSearchCity() throws AkuraAppException {

        try {
            String message = "message";
            request = new MockHttpServletRequest();
            Province newProvince = commonService.addProvince(province);
            assertNotNull(newProvince);
            district.setProvince(province);
            District newDistrict = commonService.addDistrict(district);
            assertNotNull(newDistrict);
            city.setDistrict(newDistrict);
            City newCity = commonService.addCity(city);
            City findCity = commonService.findCity(newCity.getCityId());
            String searchDescription = findCity.getDescription();
            request.setParameter("searchDescription", searchDescription);
            String result = controller.searchCity(request, model, findCity);
            assertNotSame(message, model.containsKey("message"));
            assertNotNull("ModelAndView should not be null", result);
        } catch (AkuraAppException e) {
            throw new AkuraAppException();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.CommonController# searchCountry(javax.servlet.http.HttpServletRequest, org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.Country)}
     * .
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSearchCountry() throws AkuraAppException {

        try {
            String message = "message";
            request = new MockHttpServletRequest();
            Country newCountry = commonService.addCountry(country);
            Country findCountry = commonService.findCountry(newCountry.getCountryId());
            String searchDescription = findCountry.getCountryName();
            request.setParameter("searchDescription", searchDescription);
            String result = controller.searchCountry(request, model, findCountry);
            assertNotSame(message, model.containsKey("message"));
            assertNotNull("ModelAndView should not be null", result);
        } catch (AkuraAppException e) {
            throw new AkuraAppException();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.CommonController# saveOrUpdateCity
     * (org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.City, org.springframework.validation.BindingResult)}
     * .
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateCity() throws AkuraAppException {

        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull(newProvince);
            district.setProvince(province);
            District newDistrict = commonService.addDistrict(district);
            assertNotNull(newDistrict);
            city.setDistrict(newDistrict);
            controller.setCityValidator(cityValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view = controller.saveOrUpdateCity(model, city, mockBindingResult);
            assertEquals("reference/manageCity", view);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView = controller.saveOrUpdateCity(model, city, mockBindingResult);
            assertEquals("redirect:manageCity.htm", savedView);
            assertTrue(model.size() > 0);
        } catch (AkuraAppException e) {
            throw new AkuraAppException();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.CommonController# saveOrUpdateCountry(org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.Country, org.springframework.validation.BindingResult)}
     * .
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateCountry() throws AkuraAppException {

        try {
            controller.setCountryValidator(countryValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view = controller.saveOrUpdateCountry(request, model, country, mockBindingResult);
            assertEquals("reference/manageCountry", view);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView = controller.saveOrUpdateCountry(request, model, country, mockBindingResult);
            assertEquals("redirect:manageCountry.htm", savedView);
            assertTrue(model.size() > 0);
        } catch (AkuraAppException e) {
            throw new AkuraAppException();
        }
    }
    
    /**
     * Test method for SearchSubject of controller.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSearchSubject() throws AkuraAppException {

        try {
            stream = commonService.addStream(stream);
            assertNotNull("Stream type should not be null " + stream);
            
            subject.setStream(stream);
            subject = commonService.addSubject(subject);
            assertNotNull("Subject type should not be null " + subject);
            
            Subject newSubject = commonService.findSubject(subject.getSubjectId());
            assertNotNull("Subject type should not be null " + newSubject);
            String searchDescription = newSubject.getDescription();
            request.setParameter("searchDescription", searchDescription);
            
            String result = controller.searchSubject(request, model, newSubject);
            assertNotNull("ModelAndView should not be null", result);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for SearchStream of controller.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSearchStream() throws AkuraAppException {

        try {
            stream = commonService.addStream(stream);
            assertNotNull("Stream type should not be null " + stream);
            
            Stream newStream = commonService.findStream(stream.getStreamId());
            assertNotNull("Stream type should not be null " + newStream);
            String searchDescription = newStream.getDescription();
            request.setParameter("searchDescription", searchDescription);
            
            String result = controller.searchStream(request, model, newStream);
            assertNotNull("ModelAndView should not be null", result);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for SaveOrUpdateSubject of controller.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateSubject() throws AkuraAppException {

        try {
            stream = commonService.addStream(stream);
            assertNotNull("Stream type should not be null " + stream);
            subject.setStream(stream);
            
            controller.setSubjectValidator(subjectValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view = controller.saveOrUpdateSubject(model, subject, mockBindingResult);
            
            assertEquals("reference/manageSubjects", view);
            
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView = controller.saveOrUpdateSubject(model, subject, mockBindingResult);
            assertEquals("redirect:manageSubjects.htm", savedView);
            assertTrue(model.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for SaveOrUpdateStream of controller.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateStream() throws AkuraAppException {

        try {
            controller.setStreamValidator(streamValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view = controller.saveOrUpdateStream(model, stream, mockBindingResult);
            
            assertEquals("reference/manageStreams", view);
            
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView = controller.saveOrUpdateStream(model, stream, mockBindingResult);
            assertEquals("redirect:manageStreams.htm", savedView);
            assertTrue(model.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        if (city.getCityId() > 0) {
            controller.deleteCity(city, model);
        }
        if (country.getCountryId() > 0) {
            controller.deleteCountry(country, model, request);
        }
        if (district.getDistrictId() > 0) {
            controller.deleteDistrict(district, model);
        }
        if (province.getProvinceId() > 0) {
            controller.deleteProvince(province, model);
        }
        
        if (subject.getSubjectId() != null && subject.getSubjectId() > 0) {
            controller.deleteSubject(subject, model);
        }
        if (stream.getStreamId() > 0) {
            controller.deleteStream(request, stream, model);
        }
    }
}
