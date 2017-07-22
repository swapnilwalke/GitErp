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

import java.text.ParseException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.staff.dao.StaffDao;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.staff.validator.StaffLeaveValidator;

/**
 * @author Virtusa Corporation
 */

public class StaffLeaveControllerTest extends BaseWebTest {

    /** Represents an instance of StaffLeaveController. */
    private StaffLeaveController staffLeaveController;

    /** Represents an instance of StaffLeaveValidator. */
    @Autowired
    private StaffLeaveValidator staffLeaveValidator;

    /** Holds StaffService instance. */
    @Autowired
    private StaffService staffService;

    /** Holds CommonService instance. */
    @Autowired
    private CommonService commonService;

    /** Represents an instance of MockHttpSession. */
    private MockHttpSession session;

    /** Represents an instance of MockHttpServletRequest. */
    private MockHttpServletRequest request;

    /** Represents an instance of ModelMap. */
    private ModelMap model;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;

    /** Represents an instance of StaffLeave. */
    private StaffLeave staffLeave;

    /** Date attribute for holding currentDate. */
    private Date currentDate = new Date();

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StaffDao staffDao;

    /** Holds StaffCategoryDao instance. */
    @Autowired
    private StaffCategoryDao staffCategoryDao;

    /** Holds CityDao instance. */
    @Autowired
    private CityDao cityDao;

    /** Holds DistrictDao instance. */
    @Autowired
    private DistrictDao districtDao;

    /** Holds ProvinceDao instance. */
    @Autowired
    private ProvinceDao provinceDao;

    /**
     * Represents an instance of Staff.
     */
    private Staff staff;

    /** Holds StaffCategory instance. */
    private StaffCategory staffCategory;

    /** Holds City instance. */
    private City city;

    /** Holds District instance. */
    private District district;

    /** Holds District instance. */
    private Province province;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        // Instantiate a Province object
        province = new Province();
        province.setDescription("Province Description--");
        province.setModifiedTime(currentDate);
        province = provinceDao.save(province);
        assertNotNull(" Province saved to db " + province);

        // Instantiate a District object
        district = new District();
        district.setDescription("District Descrition--");
        district.setProvince(province);
        district = districtDao.save(district);
        district.setModifiedTime(currentDate);
        assertNotNull(" District saved to db " + district);

        // Instantiate a city
        city = new City();
        city.setDescription("City Description--");
        city.setDistrict(district);
        city.setModifiedTime(currentDate);
        city = cityDao.save(city);
        assertNotNull(" City saved to db " + city);

        // Instantiate a staffCategory object.
        staffCategory = new StaffCategory();
        staffCategory.setAcademic(true);
        staffCategory.setDescription("StaffCategory Description--");
        staffCategory.setModifiedTime(currentDate);
        staffCategory = staffCategoryDao.save(staffCategory);
        assertNotNull(" StaffCategory saved to db " + staffCategory);

        // Instantiate a staff object.
        staff = new Staff();
        staff.setStaffCategory(staffCategory);
        staff.setRegistrationNo("Staff RegNo--");
        staff.setDateOfHire(currentDate);
        staff.setFullName("Staff FullName");
        staff.setLastName("Staff LastName");
        staff.setNationalID("0123456789--");
        staff.setDateOfBirth(currentDate);
        staff.setAddress("Staff Address");
        staff.setGender('M');
        staff.setCityId(city.getCityId());
        staff.setModifiedTime(currentDate);
        staff = staffDao.save(staff);
        assertNotNull(" Staff saved to db " + staff);

        // Instantiate a staffLeave object.
        staffLeave = new StaffLeave();
        staffLeave.setStaffId(staff.getStaffId());
        staffLeave.setFromDate(currentDate);
        staffLeave.setToDate(currentDate);
        staffLeave.setReason("reason");
        staffLeave.setModifiedTime(currentDate);
        staffLeave.setNoOfDays(1.0f);

        staffLeaveController = new StaffLeaveController();
        //staffLeaveController.setCommonService(commonService);
        staffLeaveController.setStaffService(staffService);

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        model.addAttribute("staffLeave", staffLeave);
    }

    /**
     * Test method for SaveOrUpdateStaffLeave of StaffLeaveController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     * @throws ParseException - ParseException
     */
    @Test
    public void testSaveOrUpdateStaffLeave() throws AkuraAppException, ParseException {

        staffLeaveController.setStaffLeaveValidator(staffLeaveValidator);
        session.setAttribute("staffId", staff.getStaffId());

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(Boolean.TRUE);
        String view =
                staffLeaveController.saveOrUpdateStaffLeave(staffLeave, mockBindingResult, request, model, session);
        assertEquals("staff/staffLeave", view);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(Boolean.FALSE);
        String savedView =
                staffLeaveController.saveOrUpdateStaffLeave(staffLeave, mockBindingResult, request, model, session);
        assertEquals("redirect:staffLeave.htm", savedView);
        assertTrue(model.size() > 0);

        request.addParameter("staffLeaveId", "" + staffLeave.getStaffLeaveId());

    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        staffLeaveController.deleteStaffLeave(request, model, session);

        staffDao.delete(staff);
        staffCategoryDao.delete(staffCategory);
        cityDao.delete(city);
        districtDao.delete(district);
        provinceDao.delete(province);
    }
}
