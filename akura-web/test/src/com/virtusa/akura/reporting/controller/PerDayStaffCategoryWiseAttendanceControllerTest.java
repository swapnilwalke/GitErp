/*
 * © 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

 * PROPRIETARY INFORMATION The information contained herein (the

 * 'Proprietary Information') is highly confidential and proprietary to and

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published,

 * communicated, disclosed or divulged to any person, firm, corporation or

 * other legal entity, directly or indirectly, without the prior written

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.reporting.controller;

import java.text.ParseException;

import net.sf.jasperreports.engine.JRException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.PerDayAttendanceTemplate;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.reporting.validator.PerDayStaffCategoryWiseAttendanceValidator;

/**
 * this is JUnit for staff category wise attendance report.
 *
 * @author Virtusa Corporation
 */

public class PerDayStaffCategoryWiseAttendanceControllerTest extends BaseWebTest {

    /**
     * initialized an instance of REPORT.
     */
    public static final String REPORT = "com.virtusa.sms.util.reporting.report";

    /**
     * Represents an instance of PerDayStaffCategoryWiseAttendanceController.
     */
    private PerDayStaffCategoryWiseAttendanceController perDayStaffCategoryWiseAttendanceController;

    /**
     * attendanceReportingService To invoke service methods.
     */
    @Autowired
    private DailyAttendanceService dailyAttendanceService;

    /** staffCommonService attribute for holding StaffCommonService. */
    @Autowired
    private StaffCommonService staffCommonService;

    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;

    /**
     * Represents an instance of MockHttpServletRequest.
     */
    private MockHttpServletRequest request;

    /**
     * Represents an instance of MockHttpServletResponse.
     */
    private MockHttpServletResponse response;

    /**
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * Represents an instance of staff category.
     */
    @Mock
    private StaffCategory staffCategory;

    /**
     * Represents an instance of PerDayAttendanceTemplate.
     */
    private PerDayAttendanceTemplate perDayAttendanceTemplate;

    /**
     * Represents an instance of PerDayStaffCategoryWiseAttendanceValidator.
     */
    @Autowired
    private PerDayStaffCategoryWiseAttendanceValidator perDayStaffCategoryWiseAttendanceValidator;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
       
        staffCategory = new StaffCategory();
        staffCategory.setAcademic(false);
        //staffCategory.setCatogaryID(10);
        staffCategory.setDescription("staff category for non academic");

        staffCommonService.addStaffCategory(staffCategory);

        perDayStaffCategoryWiseAttendanceController = new PerDayStaffCategoryWiseAttendanceController();
        perDayAttendanceTemplate = new PerDayAttendanceTemplate();
        perDayAttendanceTemplate.setCatogaryID(staffCategory.getCatogaryID());
        perDayAttendanceTemplate.setDateConsidered("2011-06-14");
        perDayAttendanceTemplate.setReportType(2);

        model = new ModelMap();
        model.addAttribute(perDayAttendanceTemplate);

        perDayStaffCategoryWiseAttendanceController.setDailyAttendanceService(dailyAttendanceService);
        perDayStaffCategoryWiseAttendanceController
                .setPerDayStaffCategoryWiseAttendanceValidator(perDayStaffCategoryWiseAttendanceValidator);
        perDayStaffCategoryWiseAttendanceController.setStaffCommonService(staffCommonService);

    }

/**
     * Test method for {@link com.virtusa.sms.reporting.controller.PerDayStaffCategoryWiseAttendanceController.
     * showReportForm(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String view = perDayStaffCategoryWiseAttendanceController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/perDayStaffCategoryWiseAttendance", view);
    }

/**
     * Test method for {@link com.virtusa.sms.reporting.controller.PerDayStaffCategoryWiseAttendanceController.
     * onSubmit(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     * @throws JRException when an error has occurred during Jasper processing
     * @throws ParseException when an error occurred 
     */
    @Test
    public void testOnSubmit() throws AkuraAppException, JRException , ParseException {

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        String view =
                perDayStaffCategoryWiseAttendanceController.onSubmit(request, response, perDayAttendanceTemplate,
                        mockBindingResult, model);
        assertEquals("reporting/perDayStaffCategoryWiseAttendance", view);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        // Check that we returned back to the original form:
        String newView =
                perDayStaffCategoryWiseAttendanceController.onSubmit(request, response, perDayAttendanceTemplate,
                        mockBindingResult, model);
        assertEquals(null, newView);

    }

    /**
     * Tear down method for each test case.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {

        try {
            staffCommonService.deleteStaffCategory(staffCategory);

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }

    }

}
