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

package com.virtusa.akura.common.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.HolidayValidator;

/**
 * This test class, tests all the presentation related functionality for the Holiday domain object.
 *
 * @author Virtusa Corporation
 */
public class ManageHolidayControllerTest extends BaseWebTest {

    /**
     * Represents an instance of ManageHolidayController.
     */
    private ManageHolidayController controller;

    /**
     * Represents an instance of CommonService.
     */
    @Autowired
    private CommonService commonService;

    /** holidayValidator attribute for holding HolidayValidator. */
    @Autowired
    private HolidayValidator holidayValidator;

    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;

    /**
     * Represents an instance of MockHttpServletRequest.
     */
    private MockHttpServletRequest request;

    /** Represents an instance of MockHttpSession. */
    private MockHttpSession session;

    /**
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * Represents an instance of Holiday.
     */
    private Holiday holiday;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();

        holiday = new Holiday();
        holiday.setDescription("testHoliday111");
        holiday.setStartDate(new Date());
        holiday.setEndDate(new Date());

        model.addAttribute("holiday", holiday);
        controller = new ManageHolidayController();
        controller.setCommonService(commonService);
    }

    /**
     * Test method for ShowHolidayDetail of ManageHolidayController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowHolidayDetail() throws AkuraAppException {

        holiday = commonService.saveHoliday(holiday);
        assertNotNull("Holiday should not be null " + holiday);

        request.setAttribute("selectedYear", "2012");
        String result = controller.showHolidayDetail(model, session, request);
        assertNotNull("ModelAndView should not be null", result);

    }

    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.ManageHolidayController# saveOrUpdateHoliday
     * (org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.Holiday, org.springframework.validation.BindingResult)}
     * .
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateHoliday() throws AkuraAppException {

        try {
            controller.setHolidayValidator(holidayValidator);

            DateFormat formatter = new SimpleDateFormat("yyyy");
            String date = formatter.format(new Date());
            request.addParameter("selectedYear", date);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view = controller.saveOrUpdateHoliday(holiday, mockBindingResult, request, session, model);

            assertEquals("reference/manageHoliday", view);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView = controller.saveOrUpdateHoliday(holiday, mockBindingResult, request, session, model);

            assertEquals("reference/manageHoliday", savedView);
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

        try {
            String result = controller.deleteHoliday(holiday, request, session, model);
            assertNotNull("Holiday should not be null " + result);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

}
