/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.CRExamAbsentTemplate;
import com.virtusa.akura.api.dto.ReportAbsenteeList;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.reporting.service.CommonReportingService;
import com.virtusa.akura.reporting.validator.CRExamAbsentListValidator;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * @author Virtusa Corporation
 */

public class CRExamAbsentListControllerTest extends BaseWebTest {

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Holds the value for sourceJrxmlPath. */
    private static final String SOURCE_JRXML_PATH = "sourceJrxmlPath";

    /**
     * systemConfig property file.
     */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /**
     * initialized an instance of REPORT.
     */
    public static final String REPORT = "report";

    /**
     * Represents an instance of CRExamAbsentListController.
     */
    private CRExamAbsentListController cRExamAbsentListController;

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
     * Represents an instance of CRExamAbsentTemplate.
     */
    private CRExamAbsentTemplate cRExamAbsentTemplate;

    /**
     * Represents an instance of CRExamAbsentListValidator.
     */
    @Autowired
    private CRExamAbsentListValidator cRExamAbsentListValidator;

    /**
     * Represents an instance of ReportAbsenteeList.
     */
    private ReportAbsenteeList reportAbsenteeList;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);

        cRExamAbsentListController = new CRExamAbsentListController();
        cRExamAbsentTemplate = new CRExamAbsentTemplate();
        cRExamAbsentTemplate.setAbsent(true);
        cRExamAbsentTemplate.setClassDescription("Class A");
        cRExamAbsentTemplate.setGradeDescription("Grade 1");
        cRExamAbsentTemplate.setSubjectDescription("Sinhala");
        cRExamAbsentTemplate.setTermDescription("Term 2");

        reportAbsenteeList = new ReportAbsenteeList();
        reportAbsenteeList.setAbsent(true);
        reportAbsenteeList.setClassDescription("Class A");
        reportAbsenteeList.setGradeDescription("Grade 1");
        reportAbsenteeList.setTermDescription("Term 1");
        reportAbsenteeList.setFullName("Harsha Pathum");
        reportAbsenteeList.setSubjectDescription("English");

        model = new ModelMap();
        model.addAttribute(cRExamAbsentTemplate);

        cRExamAbsentListController.setCommonReportingService(commonReportingService);
    }

/**
     * Test method for {@link com.virtusa.sms.reporting.controller.CRExamAbsentListController.
     * showReportForm(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String view = cRExamAbsentListController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/examAbsentClassWise", view);
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

        cRExamAbsentListController.setcRExamAbsentListValidator(cRExamAbsentListValidator);

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        String view =
                cRExamAbsentListController.onSubmit(request, response, cRExamAbsentTemplate, mockBindingResult, model);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        // Check that we returned back to the original form:
        assertEquals("reporting/examAbsentClassWise", view);
        assertTrue(model.size() > 0);

        Map<String, String> params = new HashMap<String, String>();

        params.put("ExamAbsenteeListClassWiseTitleText",
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, "ExamAbsentClassWise_titleText"));
        params.put("gradeLableText",
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, "ExamAbsentClassWise_gradeLableText"));
        params.put("gradeText", cRExamAbsentTemplate.getGradeDescription());
        params.put("ClassLabelText",
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, "ExamAbsentClassWise_classLabelText"));
        params.put("ClassText", cRExamAbsentTemplate.getClassDescription());
        params.put("ADMISSION_NO_TEXT",
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, "ExamAbsentClassWise_admissionNumberText"));
        params.put("fullNameLableText",
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, "ExamAbsentClassWise_fullNameLableText"));
        params.put("termDescriptionLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "ExamAbsentClassWise_termDescriptionLableText"));
        params.put("subjectDescriptionLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "ExamAbsentClassWise_subjectDescriptionLableText"));
        params.put("logoPath", ReportUtil.getReportLogo());

        List<ReportAbsenteeList> list = new ArrayList<ReportAbsenteeList>();
        list.add(reportAbsenteeList);

        JRBeanCollectionDataSource jrBeanDataSource =
                commonReportingService.getStudentClassWiseExamAbsenteeList(cRExamAbsentTemplate);

        assertNotNull("Not Null JR Source" + jrBeanDataSource);

        String path =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH)
                        + "CRExamAbsenteeListClassWise" + ".jrxml";
        String pdfPath =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH)
                        + "CRExamAbsenteeListClassWise" + ".pdf";
        JasperDesign subReportDesign2 = JRXmlLoader.load(path);
        JasperReport subCompiled2 = JasperCompileManager.compileReport(subReportDesign2);
        JRBeanCollectionDataSource ds2 = new JRBeanCollectionDataSource(list);
        JasperPrint printSubreport2 = JasperFillManager.fillReport(subCompiled2, params, ds2);
        JasperExportManager.exportReportToPdfFile(printSubreport2, pdfPath);
    }

}
