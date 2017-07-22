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
import java.util.Date;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.CRExtraCurricularActivitiesTemplate;
import com.virtusa.akura.api.dto.ReportStudentClubSociety;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.reporting.service.CommonReportingService;
import com.virtusa.akura.reporting.validator.CRStudentExtraCurricularValidator;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * @author Virtusa Corporation
 */

public class CRStudentExtraCurricularControllerTest extends BaseWebTest {

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Holds the value for sourceJrxmlPath. */
    private static final String SOURCE_JRXML_PATH = "sourceJrxmlPath";

    /**
     * systemConfig property file.
     */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /**
     * Represents an variable of STATE and initialized.
     */
    private static final byte STATE = 1;

    /**
     * Represents an variable of CLUBE_SOCIETY_ID and initialized.
     */
    private static final int CLUBE_SOCIETY_ID = 1;

    /**
     * Represents an variable of STUDENT_ID and initialized.
     */
    private static final int STUDENT_ID = 1;

    /**
     * initialized an instance of REPORT.
     */
    public static final String REPORT = "com.virtusa.sms.util.reporting.report";

    /**
     * Represents an instance of CRStudentExtraCurricularController.
     */
    private CRStudentExtraCurricularController cRStudentExtraCurricularController;

    /**
     * Represents an instance of CommonReportingService.
     */
    @Autowired
    private CommonReportingService commonReportingService;

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
     * Represents an instance of CRStudentExtraCurricularValidator.
     */
    @Autowired
    private CRStudentExtraCurricularValidator cRStudentExtraCurricularValidator;

    /**
     * Represents an instance of CRExtraCurricularActivitiesTemplate.
     */
    private CRExtraCurricularActivitiesTemplate cRExtraCurricularActivitiesTemplate;

    /**
     * Represents an instance of ReportStudentClubSociety.
     */
    private ReportStudentClubSociety reportStudentClubSociety;

    /**
     * Represents an instance of MockHttpServletRequest.
     */
    private MockHttpServletRequest request;

    /**
     * Represents an instance of MockHttpServletResponse.
     */
    private MockHttpServletResponse response;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);

        cRStudentExtraCurricularController = new CRStudentExtraCurricularController();
        cRExtraCurricularActivitiesTemplate = new CRExtraCurricularActivitiesTemplate();

        cRExtraCurricularActivitiesTemplate.setAdmissionNo("ABC1234");
        cRExtraCurricularActivitiesTemplate.setClubSocietyName("Society 1");
        cRExtraCurricularActivitiesTemplate.setFullName("Gayan Chandana");
        cRExtraCurricularActivitiesTemplate.setMembershipNo("ABC12345");
        cRExtraCurricularActivitiesTemplate.setPosition("P1");
        cRExtraCurricularActivitiesTemplate.setStatus(STATE);
        cRExtraCurricularActivitiesTemplate.setStudentclubSocietyId(CLUBE_SOCIETY_ID);
        cRExtraCurricularActivitiesTemplate.setStudentId(STUDENT_ID);
        cRExtraCurricularActivitiesTemplate.setYear(new Date());

        reportStudentClubSociety = new ReportStudentClubSociety();
        reportStudentClubSociety.setAdmissionNo("ABC1234");
        reportStudentClubSociety.setClubSocietyName("Society 1");
        reportStudentClubSociety.setFullName("Gayan Chandana");
        reportStudentClubSociety.setMembershipNo("ABC12345");
        reportStudentClubSociety.setPosition("Good");
        // reportStudentClubSociety.setYear(new Date());

        model = new ModelMap();
        model.addAttribute(cRExtraCurricularActivitiesTemplate);

        cRStudentExtraCurricularController.setCommonReportingService(commonReportingService);
    }

/**
     * Test method for {@link com.virtusa.sms.reporting.controller.CRStudentExtraCurricularController.
     * showReportForm(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String view = cRStudentExtraCurricularController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/studentExtraCurricularActivities", view);
    }

/**
     * Test method for {@link com.virtusa.sms.reporting.controller.CRStudentExtraCurricularController.
     * onSubmit(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     * @throws JRException when an error has occurred during Jasper processing
     */
    @Test
    public void testOnSubmit() throws AkuraAppException, JRException {

        cRStudentExtraCurricularController.setcRStudentExtraCurricularValidator(cRStudentExtraCurricularValidator);

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        String view =
                cRStudentExtraCurricularController.onSubmit(request, response, cRExtraCurricularActivitiesTemplate,
                        mockBindingResult);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        // Check that we returned back to the original form:
        assertEquals("reporting/StudentExtraCurricularActivities", view);
        assertTrue(model.size() > 0);

        Map<String, String> params = new HashMap<String, String>();

        params.put("StudentExtraCurricularTitleText", "Student Extra Curricular Activities Report ");
        params.put("admissionNumberLableText",
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                		"StuExCurricular_admissionNumberLableText"));
        params.put("fullNameLableText",
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, "StuExCurricular_fullNameLableText"));
        params.put("logoPath", ReportUtil.getReportLogo());
        // columns
        params.put("yearLableText",
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, "StuExCurricular_yearLableText"));
        params.put("clubNameLableText",
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, "StuExCurricular_clubNameLableText"));
        params.put("positionLableText",
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, "StuExCurricular_positionLableText"));

        List<ReportStudentClubSociety> list = new ArrayList<ReportStudentClubSociety>();
        list.add(reportStudentClubSociety);

        String path =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH)
                        + "CRStudentExtraCurricular" + ".jrxml";
        String pdfPath =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH)
                        + "CRStudentExtraCurricular" + ".pdf";
        JasperDesign subReportDesign2 = JRXmlLoader.load(path);
        JasperReport subCompiled2 = JasperCompileManager.compileReport(subReportDesign2);
        JRBeanCollectionDataSource ds2 = new JRBeanCollectionDataSource(list);
        JasperPrint printSubreport2 = JasperFillManager.fillReport(subCompiled2, params, ds2);
        JasperExportManager.exportReportToPdfFile(printSubreport2, pdfPath);
    }

}
