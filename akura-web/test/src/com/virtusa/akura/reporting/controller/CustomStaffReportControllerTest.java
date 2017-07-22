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

package com.virtusa.akura.reporting.controller;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.SchoolTeacherAndSectionHeadList;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.validator.CustomStaffReportControllerValidator;
import com.virtusa.akura.staff.dao.StaffDao;
import com.virtusa.akura.staff.service.StaffService;

/**
 * CustomStaffReportControllerTest class test the school teacher list and section head list.
 *
 * @author VIRTUSA Corporation
 */
public class CustomStaffReportControllerTest extends BaseWebTest {

    /**
     * Represents an instance of CustomStaffReportController.
     */
    private CustomStaffReportController customStaffReportController;

    /**
     * Represents an instance of CustomStaffReportControllerValidator.
     */
    @Autowired
    private CustomStaffReportControllerValidator schoolTeacherListSectionHeadListValidator;

    /**
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * staffService To invoke service methods.
     */
    @Autowired
    private StaffService staffService;

    /**
     * CommonService To invoke service methods.
     */
    @Autowired
    private CommonService commonService;

    /**
     * Represents an instance of SchoolTeacherAndSectionHeadList.
     */
    private SchoolTeacherAndSectionHeadList schoolTeacherAndSectionHeadList1;

    /** Represents an instance of the Staff. */
    private Staff staff;

    /** Holds StaffCategory instance. */
    private StaffCategory staffCategory;

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

    /** Holds City instance. */
    private City city;

    /** Holds District instance. */
    private District district;

    /** Holds District instance. */
    private Province province;

    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;

    /**
     * Represents an instance of HttpSession.
     */
    @Mock
    private MockHttpSession session;

    /**
     * Represents an instance of HttpServletRequest.
     */
    @Mock
    private MockHttpServletRequest request;

    /**
     * Represents an instance of HttpServletResponse.
     */
    @Mock
    private MockHttpServletResponse response;

    /**
     * Execute this method before each test method.
     *
     * @throws AkuraAppException when fails to set up.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        assertNotNull(session);
        assertNotNull(request);
        assertNotNull(response);

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
        newStaffCategory.setAcademic(true);
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

        schoolTeacherAndSectionHeadList1 = new SchoolTeacherAndSectionHeadList();
        schoolTeacherAndSectionHeadList1.setListType(AkuraConstant.PARAM_INDEX_ONE);
        schoolTeacherAndSectionHeadList1.setCatogaryID(staffCategory.getCatogaryID());
        schoolTeacherAndSectionHeadList1.setCategory(staffCategory.getDescription());

        model = new ModelMap();

        customStaffReportController = new CustomStaffReportController();
        customStaffReportController.setCommonService(commonService);
        customStaffReportController.setStaffService(staffService);
        customStaffReportController
                .setSchoolTeacherListSectionHeadListValidator(schoolTeacherListSectionHeadListValidator);

    }

    /**
     * This method tests the showReportForm method.
     *
     * @throws AkuraAppException throws when fails.
     */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String reportView =
                customStaffReportController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/schoolTeacherListAndSectionHeadList", reportView);

    }

    /**
     * This method test the showTeacherListReport method.
     *
     * @throws AkuraAppException when fails to generate report.
     */
    @Test
    public void testShowTeacherListReport() throws AkuraAppException {

        assertNotNull(customStaffReportController.showTeacherListReport(
                schoolTeacherAndSectionHeadList1, mockBindingResult, response));

    }

    /**
     * This method executes after every test method.
     *
     * @throws AkuraAppException throws when fails to clean up.
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
