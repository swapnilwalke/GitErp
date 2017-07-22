/*
 * � 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

 * PROPRIETARY INFORMATION The information contained herein (the

 * 'Proprietary Information') is highly confidential and proprietary to and

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published,

 * communicated, disclosed or divulged to any person, firm, corporation or

 * other legal entity, directly or indirectly, without the prior written

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.reporting.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.AttendeesWrapperTemplate;
import com.virtusa.akura.api.dto.StudentAttendance;
import com.virtusa.akura.api.dto.TeacherEarlyComersTemplate;
import com.virtusa.akura.api.dto.TeacherLateAttendiesTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.reporting.service.AttendanceReportingService;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * @author Virtusa Corporation
 */

public class GenerateEarlyLateAttendeesStudentReportControllerTest extends BaseWebTest {

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Holds the value for sourceJrxmlPath. */
    private static final String SOURCE_JRXML_PATH = "sourceJrxmlPath";

    /**
     * systemConfig property file.
     */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /**
     * initialized an variable of NUMBER_OF_HOURS.
     */
    private static final double NUMBER_OF_HOURS = 12.45;

    /**
     * Represents an instance of GenerateEarlyLateAttendeesStudentReportController.
     */
    private GenerateEarlyLateAttendeesStudentReportController generateEarlyLateAttendeesStudentReportController;

    /**
     * Represents an instance of AttendanceReportingService.
     */
    @Autowired
    private AttendanceReportingService attendanceReportingService;

    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;

    /**
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * Represents an instance of AttendeesWrapperTemplate.
     */
    private AttendeesWrapperTemplate attendeesWrapperTemplate;

    /**
     * Represents an instance of TeacherEarlyComersTemplate.
     */
    private TeacherEarlyComersTemplate teacherEarlyComersTemplate;

    /**
     * Represents an instance of TeacherLateAttendiesTemplate.
     */
    private TeacherLateAttendiesTemplate teacherLateAttendiesTemplate;

    /**
     * Represents an instance of StudentAttendance.
     */
    private StudentAttendance studentAttendance;

    /**
     * Represents an another instance of StudentAttendance.
     */
    private StudentAttendance studentAttendance2;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);

        generateEarlyLateAttendeesStudentReportController = new GenerateEarlyLateAttendeesStudentReportController();
        attendeesWrapperTemplate = new AttendeesWrapperTemplate();

        teacherEarlyComersTemplate = new TeacherEarlyComersTemplate();
        teacherEarlyComersTemplate.setDateFrom("2011-01-10");
        teacherEarlyComersTemplate.setDateTo("2011-11-10");
        teacherEarlyComersTemplate.setTimeInFrom("7.30");
        teacherEarlyComersTemplate.setTimeInTo("8.30");

        teacherLateAttendiesTemplate = new TeacherLateAttendiesTemplate();
        teacherLateAttendiesTemplate.setDateFrom("2011-11-10");
        teacherLateAttendiesTemplate.setDateTo("2011-12-10");
        teacherLateAttendiesTemplate.setTimeInFrom("7.30");
        teacherLateAttendiesTemplate.setTimeInTo("10.30");

        studentAttendance = new StudentAttendance();
        studentAttendance.setDate("2011-01-10");
        studentAttendance.setDayname("Monday");
        studentAttendance.setNumOfHours(NUMBER_OF_HOURS);
        studentAttendance.setStudentID("013110");
        studentAttendance.setStudentName("Gayan");
        studentAttendance.setTimeIn("7.30");
        studentAttendance.setTimeOut("1.30");

        studentAttendance2 = new StudentAttendance();
        studentAttendance2.setDate("2011-12-10");
        studentAttendance2.setDayname("Monday");
        studentAttendance2.setNumOfHours(NUMBER_OF_HOURS);
        studentAttendance2.setStudentID("013345");
        studentAttendance2.setStudentName("Kasun");
        studentAttendance2.setTimeIn("8.30");
        studentAttendance2.setTimeOut("11.30");

        attendeesWrapperTemplate.setTeacherEarlyComersTemplate(teacherEarlyComersTemplate);
        attendeesWrapperTemplate.setTeacherLateAttendiesTemplate(teacherLateAttendiesTemplate);

        model = new ModelMap();
        model.addAttribute(attendeesWrapperTemplate);

        generateEarlyLateAttendeesStudentReportController.setAttendanceReportingService(attendanceReportingService);
    }

/**
     * Test method for {@link com.virtusa.sms.reporting.controller.GenerateEarlyLateAttendeesStudentReportController.
     * showReportForm(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String view = generateEarlyLateAttendeesStudentReportController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/studentEarlyLateAttendees", view);
    }

/**
     * Test method for {@link com.virtusa.sms.reporting.controller.GenerateEarlyLateAttendeesStudentReportController.
     * onSubmit(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     * @throws JRException when an error has occurred during Jasper processing
     */
    @Test
    public void testOnSubmit() throws AkuraAppException, JRException {

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        assertTrue(model.size() > 0);

        Map<String, String> params = new HashMap<String, String>();

        params.put("title", "Student Late Attendees Report");
        params.put("studentid", "Student ID");
        params.put("studentname", "Student Name");
        params.put("date", "Date Considered");
        params.put("timein", "Time In");
        params.put("timeout", "Time Out");
        params.put("datefrom", attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateFrom());
        params.put("dateto", attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateTo());
        params.put("timeinfrom", attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getTimeInFrom() + " Hr");
        params.put("timeinto", attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getTimeInTo() + " Hr");
        params.put("logoPath", ReportUtil.getReportLogo());

        final List<StudentAttendance> list = new ArrayList<StudentAttendance>();
        list.add(studentAttendance);

        JRBeanCollectionDataSource jrBeanDataSource =
                attendanceReportingService.getStudentLateAttendeesSummary(attendeesWrapperTemplate
                        .getTeacherLateAttendiesTemplate());

        assertNotNull("Not Null JR Source" + jrBeanDataSource);

        String path =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH) + "StudentEarlyComers"
                        + ".jrxml";
        String pdfPath =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH) + "StudentEarlyComers"
                        + ".pdf";
        JasperDesign subReportDesign2 = JRXmlLoader.load(path);
        JasperReport subCompiled2 = JasperCompileManager.compileReport(subReportDesign2);
        JRBeanCollectionDataSource ds2 = new JRBeanCollectionDataSource(list);
        assertNotNull(list);
        JasperPrint printSubreport2 = JasperFillManager.fillReport(subCompiled2, params, ds2);
        JasperExportManager.exportReportToPdfFile(printSubreport2, pdfPath);
    }

}
