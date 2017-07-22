/*
 * < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
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

package com.virtusa.akura.staff.controller;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.staff.dao.StaffDao;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.staff.validator.StaffDetailsValidator;

/**
 * @author Virtusa Corporation
 */
public class StaffDetailsControllerTest extends BaseWebTest {

    /** Represents an instance of the StaffDetailsController. */
    private StaffDetailsController staffDetailsController;

    /** Represents an instance of the Staff. */
    private Staff staff;

    /** Holds StaffCategory instance. */
    private StaffCategory staffCategory;

    /** Represents an instance of the StaffDetailsValidator. */
    @Autowired
    private StaffDetailsValidator staffDetailsValidator;

    /** Represents an instance of the commonService. */
    @Autowired
    private CommonService commonService;

    /** Holds StaffDao instance. */
    @Autowired
    private StaffDao staffDao;

    /** Holds CityDao instance. */
    @Autowired
    private CityDao cityDao;

    /** Holds DistrictDao instance. */
    @Autowired
    private DistrictDao districtDao;

    /** Holds ProvinceDao instance. */
    @Autowired
    private ProvinceDao provinceDao;

    /** Holds StaffCategoryDao instance. */
    @Autowired
    private StaffCategoryDao staffCategoryDao;

    /** Represents an instance of the staffService. */
    @Autowired
    private StaffService staffService;

    /** Represents an instance of MockHttpServletRequest. */
    private MockHttpServletRequest request;

    /** Represents an instance of ModelMap. */
    private ModelMap model;

    /** Holds City instance. */
    private City city;

    /** Holds District instance. */
    private District district;

    /** Holds District instance. */
    private Province province;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        model = new ModelMap();
        request = new MockHttpServletRequest();

        MockitoAnnotations.initMocks(this);

        staffDetailsController = new StaffDetailsController();
        staffDetailsController.setCommonService(commonService);
        staffDetailsController.setStaffService(staffService);

        // Instantiate a Province object
        Province newProvince = new Province();
        newProvince.setDescription("StaffServiceImplTest Description test");
        newProvince.setModifiedTime(new Date());
        province = provinceDao.save(newProvince);
        assertNotNull(" Province saved to db " + province);

        // Instantiate a District object
        District newDistrict = new District();
        newDistrict.setDescription("StaffServiceImplTest Descrition test");
        newDistrict.setProvince(province);
        newDistrict.setModifiedTime(new Date());
        district = districtDao.save(newDistrict);
        assertNotNull(" District saved to db " + district);

        // Instantiate a city
        City newCity = new City();
        newCity.setDescription("StaffServiceImplTest Description test");
        newCity.setDistrict(district);
        newCity.setModifiedTime(new Date());
        city = cityDao.save(newCity);
        assertNotNull(" City saved to db " + city);

        // Instantiate a staffCategory object.
        StaffCategory newStaffCategory = new StaffCategory();
        newStaffCategory.setAcademic(false);
        newStaffCategory.setDescription("StaffServiceImplTest Description test");
        newStaffCategory.setModifiedTime(new Date());
        staffCategory = staffCategoryDao.save(newStaffCategory);
        assertNotNull(" StaffCategory saved to db " + staffCategory);

        // Instantiate a staff object.
        staff = new Staff();
        staff.setStaffCategory(staffCategory);
        staff.setRegistrationNo("");
        staff.setDateOfHire(new Date());
        staff.setFullName("StaffServiceImplTest FullName test");
        staff.setLastName("StaffServiceImplTest LastName test");
        staff.setNationalID("StaffServiceImplTest ID");
        staff.setNameWithIntials("Test");
        staff.setDateOfBirth(new Date());
        staff.setAddress("StaffServiceImplTest Description");
        staff.setGender('M');
        staff.setCityId(city.getCityId());
        staff.setModifiedTime(new Date());
        staff.setEmail("test@test.com");

        MockMultipartFile mockMultipartFile = new MockMultipartFile("name", new byte[0]);

        staff.setMultipartFile(mockMultipartFile);

    }

    /**
     * Test method for showStaffDetailsForm of StaffDetailsController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowStaffDetailsForm() throws AkuraAppException {

        String view = staffDetailsController.showStaffDetailsForm(request, model);
        assertEquals("staff/staffMemberDetails", view);

        assertTrue(model.containsAttribute("staffIdCheck"));
        assertTrue(model.containsAttribute("staff"));
        assertTrue(model.containsAttribute("defaultImage"));
    }

    /**
     * Test method for SaveStaffDetails of StaffDetailsController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveStaffDetails() throws AkuraAppException {

        staffDetailsController.setStaffDetailsValidator(staffDetailsValidator);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(Boolean.TRUE);

        String view = staffDetailsController.saveStaffDetails(staff, mockBindingResult, model, request);

        assertEquals("staff/staffMemberDetails", view);

    }

    /**
     * Tear down method for each test case.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {

        staffDao.delete(staff);
        staffCategoryDao.delete(staffCategory);

        cityDao.delete(city);
        districtDao.delete(district);
        provinceDao.delete(province);

    }
}
