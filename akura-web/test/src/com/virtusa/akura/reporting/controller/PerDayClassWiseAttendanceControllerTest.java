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

import net.sf.jasperreports.engine.JRException;

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
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.validator.PerDayClassWiseAttendanceValidator;
import com.virtusa.akura.student.service.StudentService;


/**
 * @author Virtusa Corporation
 *
 */

public class PerDayClassWiseAttendanceControllerTest extends BaseWebTest{

    /**
     * initialized an instance of REPORT.
     */
    public static final String REPORT = "com.virtusa.sms.util.reporting.report";

    /**
     * Represents an instance of PerDayClassWiseAttendanceController.
     */
    private PerDayClassWiseAttendanceController perDayClassWiseAttendanceController;

    /**
     * attendanceReportingService To invoke service methods.
     */
    @Autowired
    private DailyAttendanceService dailyAttendanceService;

    /** CommonService attribute for holding commonService. */
    @Autowired
    private CommonService commonService;

    /**
     * studentService To invoke service methods.
     */
    @Autowired
    private StudentService studentService;

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
     * Represents an instance of PerDayAttendanceTemplate.
     */
    private PerDayAttendanceTemplate perDayAttendanceTemplate;

    /**
     * Represents an instance of PerDayClassWiseAttendanceValidator.
     */
    @Autowired
    private PerDayClassWiseAttendanceValidator perDayClassWiseAttendanceValidator;



    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {
        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);

        perDayClassWiseAttendanceController = new PerDayClassWiseAttendanceController();
        perDayAttendanceTemplate=new PerDayAttendanceTemplate();
        perDayAttendanceTemplate.setClassDescription("32");
        perDayAttendanceTemplate.setDateConsidered("2011-06-14");
        perDayAttendanceTemplate.setReportType(2);

        model = new ModelMap();
        model.addAttribute(perDayAttendanceTemplate);

        perDayClassWiseAttendanceController.setDailyAttendanceService(dailyAttendanceService);
        perDayClassWiseAttendanceController.setPerDayClassWiseAttendanceValidator(perDayClassWiseAttendanceValidator);
        perDayClassWiseAttendanceController.setCommonService(commonService);
        perDayClassWiseAttendanceController.setStudentService(studentService);
    }

    /**
     * Test method for {@link com.virtusa.sms.reporting.controller.CRExamAbsentListController.
     * showReportForm(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowReportForm() throws AkuraAppException{

        String view = perDayClassWiseAttendanceController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/perDayClassWiseAttendance", view);
    }

    /**
     * Test method for {@link com.virtusa.sms.reporting.controller.CRExamAbsentListController.
     * onSubmit(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     * @throws JRException when an error has occurred during Jasper processing
     */
    @Test
    public void testOnSubmit() throws AkuraAppException, JRException {


        request=new MockHttpServletRequest();
        response=new MockHttpServletResponse();

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        String view = perDayClassWiseAttendanceController.onSubmit
                (request, response, perDayAttendanceTemplate, mockBindingResult, model);
        assertEquals("reporting/perDayClassWiseAttendance", view);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        //Check that we returned back to the original form:
        String newView = perDayClassWiseAttendanceController.onSubmit
                (request, response, perDayAttendanceTemplate, mockBindingResult, model);
        assertEquals(null, newView);

    }

}
