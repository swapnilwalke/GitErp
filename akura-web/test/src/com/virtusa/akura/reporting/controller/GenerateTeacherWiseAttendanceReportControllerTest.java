
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

import com.virtusa.akura.api.dto.TeacherAttendance;
import com.virtusa.akura.api.dto.TeacherWiseAttendanceTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.reporting.service.AttendanceReportingService;
import com.virtusa.akura.reporting.validator.TeacherWiseAttendanceValidator;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * @author Virtusa Corporation
 */

public class GenerateTeacherWiseAttendanceReportControllerTest extends BaseWebTest {

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
     * Represents an instance of GenerateTeacherWiseAttendanceReportController.
     */
    private GenerateTeacherWiseAttendanceReportController generateTeacherWiseAttendanceReportController;

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
     * Represents an instance of TeacherWiseAttendanceValidator.
     */
    @Autowired
    private TeacherWiseAttendanceValidator teacherWiseAttendanceValidator;

    /**
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * Represents an instance of TeacherWiseAttendanceTemplate.
     */
    private TeacherWiseAttendanceTemplate teacherWiseAttendanceTemplate;

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

        generateTeacherWiseAttendanceReportController = new GenerateTeacherWiseAttendanceReportController();
        teacherWiseAttendanceTemplate = new TeacherWiseAttendanceTemplate();

        teacherWiseAttendanceTemplate.setDateFrom("2011-10-10");
        teacherWiseAttendanceTemplate.setDateTo("2012-10-10");
        // teacherWiseAttendanceTemplate.setTeacherName("Nimal Perera");

        teacherAttendance = new TeacherAttendance();
        teacherAttendance.setDate("1999-10-10");
        teacherAttendance.setEmployeeID("093546");
        teacherAttendance.setEmployeeName("Gayan");
        teacherAttendance.setNumOfHours(NUMBER_HOURS);
        teacherAttendance.setTimeIn("2.30");
        teacherAttendance.setTimeOut("5.40");

        model = new ModelMap();
        model.addAttribute(teacherWiseAttendanceTemplate);

        generateTeacherWiseAttendanceReportController.setAttendanceReportingService(attendanceReportingService);
    }

/**
     * Test method for GenerateTeacherWiseAttendanceReportController.
     * showReportForm(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String view = generateTeacherWiseAttendanceReportController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/teacherWiseAttendance", view);
    }

/**Test method for {@link com.virtusa.sms.reporting.controller.
     *  GenerateStudentWiseAttendanceReportController.
     * onSubmit(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     * @throws JRException when an error has occurred during Jasper processing
     */
    @Test
    public void testOnSubmit() throws AkuraAppException, JRException {

        generateTeacherWiseAttendanceReportController.setTeacherWiseAttendanceValidator(teacherWiseAttendanceValidator);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        // Check that we returned back to the original form:
        assertTrue(model.size() > 0);

        Map<String, String> params = new HashMap<String, String>();

        params.put("title", "Teacher Wise Attendance Report");
        params.put("teacherid", "Teacher ID");
        params.put("teachername", "Teacher Name");
        params.put("date", "Date Considered");
        params.put("timein", "Time In");
        params.put("timeout", "Time Out");
        // params.put("name", teacherWiseAttendanceTemplate.getTeacherName());
        params.put("datefrom", teacherWiseAttendanceTemplate.getDateFrom());
        params.put("dateto", teacherWiseAttendanceTemplate.getDateTo());
        params.put("logoPath", ReportUtil.getReportLogo());

        final List<TeacherAttendance> list = new ArrayList<TeacherAttendance>();
        list.add(teacherAttendance);

        String path =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH) + "TeacherWiseAttendance"
                        + ".jrxml";
        String pdfPath =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH) + "TeacherWiseAttendance"
                        + ".pdf";
        JasperDesign subReportDesign2 = JRXmlLoader.load(path);
        JasperReport subCompiled2 = JasperCompileManager.compileReport(subReportDesign2);
        JRBeanCollectionDataSource ds2 = new JRBeanCollectionDataSource(list);
        JasperPrint printSubreport2 = JasperFillManager.fillReport(subCompiled2, params, ds2);
        JasperExportManager.exportReportToPdfFile(printSubreport2, pdfPath);
    }
}
