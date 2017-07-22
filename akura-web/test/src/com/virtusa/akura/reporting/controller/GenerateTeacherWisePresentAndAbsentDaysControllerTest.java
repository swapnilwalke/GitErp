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
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.TeacherWisePresentAbsentTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.reporting.service.AttendanceReportingService;
import com.virtusa.akura.reporting.validator.TeacherWisePresentAndAbsentDaysValidator;

/**
 * @author Virtusa Corporation
 */

public class GenerateTeacherWisePresentAndAbsentDaysControllerTest extends BaseWebTest {

    /**
     * initialized an instance of REPORT.
     */
    public static final String REPORT = "com.virtusa.sms.util.reporting.report";

    /**
     * Represents an instance of GenerateTeacherWiseAttendanceReportController.
     */
    private GenarateTeacherWisePresentAndAbsentDaysReportController presentAndAbsentDaysReportController;

    /**
     * Represents an instance of AttendanceReportingService.
     */
    @Autowired
    private AttendanceReportingService attendanceReportingService;

    /**
     * Represents an instance of MockHttpServletRequest.
     */
    private MockHttpServletRequest request;

    /**
     * Represents an instance of MockHttpServletResponse.
     */
    private MockHttpServletResponse response;

    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;

    /**
     * Represents an instance of TeacherWiseAttendanceValidator.
     */
    @Autowired
    private TeacherWisePresentAndAbsentDaysValidator presentAndAbsentDaysValidator;

    /**
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * Represents an instance of TeacherWiseAttendanceTemplate.
     */
    private TeacherWisePresentAbsentTemplate presentAbsentTemplate;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);

        presentAbsentTemplate = new TeacherWisePresentAbsentTemplate();
        presentAbsentTemplate.setTeacherRegNo("010118");
        presentAbsentTemplate.setDateFrom("2011-06-01");
        presentAbsentTemplate.setDateTo("2011-06-30");

        model = new ModelMap();
        model.addAttribute(presentAbsentTemplate);

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        presentAndAbsentDaysReportController = new GenarateTeacherWisePresentAndAbsentDaysReportController();
        presentAndAbsentDaysReportController.setAttendanceReportingService(attendanceReportingService);

    }

    /**
     * Test method for showReportForm.
     *
     * @throws AkuraAppException - The Exception that can occurred.
     */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String view = presentAndAbsentDaysReportController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/teacherWisePresentAndAbsentDays", view);
    }

    /**
     * Test method for onSubmit report details.
     *
     * @throws AkuraAppException - The Exception that can occurred.
     * @throws ParseException - The Exception that can occurred.
     */

    @Test
    public void testOnSubmit() throws AkuraAppException, ParseException {

        presentAndAbsentDaysReportController.setPresentAndAbsentDaysValidator(presentAndAbsentDaysValidator);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        ModelAndView view =
                presentAndAbsentDaysReportController.onSubmit(request, response, presentAbsentTemplate,
                        mockBindingResult, model);
        assertEquals("reporting/teacherWisePresentAndAbsentDays", view.getViewName());

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        ModelAndView viewFalse =
                presentAndAbsentDaysReportController.onSubmit(request, response, presentAbsentTemplate,
                        mockBindingResult, model);
        assertNull(viewFalse);
    }
}
