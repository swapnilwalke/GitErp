
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
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.AttendeesWrapperTemplate;
import com.virtusa.akura.api.dto.TeacherAttendance;
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

public class GenerateTeacherEarlyLateAttendiesReportControllerTest extends BaseWebTest {

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Holds the value for sourceJrxmlPath. */
    private static final String SOURCE_JRXML_PATH = "sourceJrxmlPath";

    /**
     * systemConfig property file.
     */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /**
     * initialized an variable of AVERAGE HOURS.
     */
    private static final double NUMBER_HOURS = 10;

    /**
     * Represents an instance of GenerateTeacherEarlyLateAttendiesReportController.
     */
    private GenerateTeacherEarlyLateAttendiesReportController generateTeacherEarlyLateAttendiesReportController;

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
     * Represents an instance of TeacherAttendance.
     */
    private TeacherAttendance teacherAttendance;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);

        generateTeacherEarlyLateAttendiesReportController = new GenerateTeacherEarlyLateAttendiesReportController();
        attendeesWrapperTemplate = new AttendeesWrapperTemplate();

        teacherEarlyComersTemplate = new TeacherEarlyComersTemplate();
        teacherEarlyComersTemplate.setDateFrom("1999-10-10");
        teacherEarlyComersTemplate.setDateTo("1999-10-18");
        teacherEarlyComersTemplate.setTimeInFrom("7.30");
        teacherEarlyComersTemplate.setTimeInTo("7.45");

        teacherLateAttendiesTemplate = new TeacherLateAttendiesTemplate();
        teacherLateAttendiesTemplate.setDateFrom("1999-10-10");
        teacherLateAttendiesTemplate.setDateTo("1999-10-17");
        teacherLateAttendiesTemplate.setTimeInFrom("7.45");
        teacherLateAttendiesTemplate.setTimeInTo("6.15");

        attendeesWrapperTemplate.setTeacherEarlyComersTemplate(teacherEarlyComersTemplate);
        attendeesWrapperTemplate.setTeacherLateAttendiesTemplate(teacherLateAttendiesTemplate);

        teacherAttendance = new TeacherAttendance();
        teacherAttendance.setDate("1999-10-10");
        teacherAttendance.setEmployeeID("093546");
        teacherAttendance.setEmployeeName("Gayan");
        teacherAttendance.setNumOfHours(NUMBER_HOURS);
        teacherAttendance.setTimeIn("2.30");
        teacherAttendance.setTimeOut("5.40");

        model = new ModelMap();
        model.addAttribute(attendeesWrapperTemplate);

        generateTeacherEarlyLateAttendiesReportController.setAttendanceReportingService(attendanceReportingService);
    }

/**
     * Test method for {@link com.virtusa.sms.reporting.controller.
     * GenerateTeacherEarlyLateAttendiesReportController.
     * showReportForm(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String view = generateTeacherEarlyLateAttendiesReportController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/teacherEarlyLateAttendees", view);
    }

    /**
     * Test method for GenerateTeacherEarlyLateAttendiesReportController.
     * onSubmit(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     * @throws JRException when an error has occurred during Jasper processing
     */
    @Test
    public void testOnSubmit() throws AkuraAppException, JRException {

        assertTrue(model.size() > 0);

        Map<String, String> params = new HashMap<String, String>();

        params.put("title", "Teacher Late Attendees Report");
        params.put("teacherid", "Teacher ID");
        params.put("teachername", "Teacher Name");
        params.put("date", "Date Considered");
        params.put("timein", "Time In");
        params.put("timeout", "Time Out");
        params.put("datefrom", attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateFrom());
        params.put("dateto", attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateTo());
        params.put("timeinfrom", attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getTimeInFrom() + " Hr");
        params.put("timeinto", attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getTimeInTo() + " Hr");
        params.put("logoPath", ReportUtil.getReportLogo());

        final List<TeacherAttendance> list = new ArrayList<TeacherAttendance>();
        list.add(teacherAttendance);

        String path =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH) + "TeacherEarlyComers"
                        + ".jrxml";
        String pdfPath =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH) + "TeacherEarlyComers"
                        + ".pdf";
        JasperDesign subReportDesign2 = JRXmlLoader.load(path);
        JasperReport subCompiled2 = JasperCompileManager.compileReport(subReportDesign2);
        JRBeanCollectionDataSource ds2 = new JRBeanCollectionDataSource(list);
        JasperPrint printSubreport2 = JasperFillManager.fillReport(subCompiled2, params, ds2);
        JasperExportManager.exportReportToPdfFile(printSubreport2, pdfPath);
    }

}
