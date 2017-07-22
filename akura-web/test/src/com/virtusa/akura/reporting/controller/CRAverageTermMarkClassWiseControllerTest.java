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

import com.virtusa.akura.api.dto.CRAverageTestMarkTemplate;
import com.virtusa.akura.api.dto.ReportAverageTestMark;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.reporting.validator.CRAverageTermMarkClassWiseValidator;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * @author Virtusa Corporation
 */

public class CRAverageTermMarkClassWiseControllerTest extends BaseWebTest {

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Holds the value for sourceJrxmlPath. */
    private static final String SOURCE_JRXML_PATH = "sourceJrxmlPath";

    /**
     * systemConfig property file.
     */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /**
     * Represents an variable of Grade Id and initialized.
     */
    private static final int GRADE_ID = 12;

    /**
     * initialized an variable of AVERAGE.
     */
    private static final double AVERAGE = 35.45;

    /**
     * initialized an instance of REPORT.
     */
    public static final String REPORT = "com.virtusa.sms.util.reporting.report";

    /**
     * Represents an instance of CRAverageTermMarkClassWiseController.
     */
    private CRAverageTermMarkClassWiseController cRAverageTermMarkClassWiseController;

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
     * Represents an instance of CRAverageTestMarkTemplate.
     */
    private CRAverageTestMarkTemplate cRAverageTestMarkTemplate;

    /**
     * Represents an instance of Term.
     */
    private Term term;

    /**
     * Represents an instance of CRAverageTermMarkClassWiseValidator.
     */
    @Autowired
    private CRAverageTermMarkClassWiseValidator cRAverageTermMarkClassWiseValidator;

    /**
     * Represents an instance of ReportAverageTestMark.
     */
    private ReportAverageTestMark reportAverageTestMark;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);

        cRAverageTermMarkClassWiseController = new CRAverageTermMarkClassWiseController();
        cRAverageTestMarkTemplate = new CRAverageTestMarkTemplate();

        List<String> subList = new ArrayList<String>();
        subList.add("Subject 1");

        term = new Term();
        term.setDescription("term 123");

        List<Term> terList = new ArrayList<Term>();
        terList.add(term);

        cRAverageTestMarkTemplate.setClassDescription("Class 1");
        cRAverageTestMarkTemplate.setGradeDescription("Grade 1");
        cRAverageTestMarkTemplate.setGradeId(GRADE_ID);
        cRAverageTestMarkTemplate.setSubjectDescription("Subject 2");
        // cRAverageTestMarkTemplate.setSubjectList(subList);
        cRAverageTestMarkTemplate.setTermDescription("Term 3");
        cRAverageTestMarkTemplate.setTermList(terList);

        reportAverageTestMark = new ReportAverageTestMark();
        reportAverageTestMark.setAverage(AVERAGE);
        reportAverageTestMark.setClassDescription("Class 1");
        reportAverageTestMark.setGradeDescription("Grade 1");
        reportAverageTestMark.setSubjectDescription("Subject 2");
        reportAverageTestMark.setTermDescription("Term 3");

        model = new ModelMap();
        model.addAttribute(cRAverageTestMarkTemplate);

        // cRAverageTermMarkClassWiseController.setCommonReportingService(commonReportingService);
    }

/**
     * Test method for {@link com.virtusa.sms.reporting.controller.CRAverageTermMarkClassWiseController.
     * showReportForm(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String view = cRAverageTermMarkClassWiseController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/averageTermTestMarkClassWise", view);
    }

/**
     * Test method for {@link com.virtusa.sms.reporting.controller.CRAverageTermMarkClassWiseController.
     * onSubmit(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     * @throws JRException when an error has occurred during Jasper processing
     */
    @Test
    public void testOnSubmit() throws AkuraAppException, JRException {

        cRAverageTermMarkClassWiseController
                .setcRAverageTermMarkClassWiseValidator(cRAverageTermMarkClassWiseValidator);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        // String view =
        // cRAverageTermMarkClassWiseController.onSubmit(request, response, cRAverageTestMarkTemplate,
        // mockBindingResult);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        // Check that we returned back to the original form:
        // assertEquals("reporting/AverageTermTestMarkClassWise", view);
        assertTrue(model.size() > 0);

        Map<String, String> params = new HashMap<String, String>();

        params.put("AverageTermMarkClassWiseTitleText", "Average Term Mark Report");
        params.put("subjectLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "AverageTermMarkClassWise_subjectLableText"));
        params.put("subjectText", cRAverageTestMarkTemplate.getSubjectDescription());
        params.put("gradeLableText",
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, "AverageTermMarkClassWise_gradeLableText"));
        params.put("gradeText", cRAverageTestMarkTemplate.getGradeDescription());
        params.put("classLableText",
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, "AverageTermMarkClassWise_classLableText"));
        params.put("classText", cRAverageTestMarkTemplate.getClassDescription());
        params.put("logoPath", ReportUtil.getReportLogo());
        params.put("averageLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "AverageTermMarkClassWise_averageLableText"));
        params.put("subjectDescriptionLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "AverageTermMarkClassWise_subjectDescriptionLableText"));
        params.put("termDescriptionLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "AverageTermMarkClassWise_termDescriptionLableText"));

        List<ReportAverageTestMark> list = new ArrayList<ReportAverageTestMark>();
        list.add(reportAverageTestMark);

        String path =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH)
                        + "CRAverageTermMarkClassWise" + ".jrxml";
        String pdfPath =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH)
                        + "CRAverageTermMarkClassWise" + ".pdf";
        JasperDesign subReportDesign2 = JRXmlLoader.load(path);
        JasperReport subCompiled2 = JasperCompileManager.compileReport(subReportDesign2);
        JRBeanCollectionDataSource ds2 = new JRBeanCollectionDataSource(list);
        JasperPrint printSubreport2 = JasperFillManager.fillReport(subCompiled2, params, ds2);
        JasperExportManager.exportReportToPdfFile(printSubreport2, pdfPath);
    }

}
