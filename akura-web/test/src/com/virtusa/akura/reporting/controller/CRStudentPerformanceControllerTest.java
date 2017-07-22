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

import com.virtusa.akura.api.dto.CRStudentPerformanceTemplate;
import com.virtusa.akura.api.dto.ReportAbsenteeList;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.reporting.service.CommonReportingService;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * @author Virtusa Corporation
 */

public class CRStudentPerformanceControllerTest extends BaseWebTest {

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Holds the value for sourceJrxmlPath. */
    private static final String SOURCE_JRXML_PATH = "sourceJrxmlPath";

    /**
     * systemConfig property file.
     */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /**
     * initialized an variable of LESSTHAN_VALUE.
     */
    private static final float LESSTHAN_VALUE = 35;

    /**
     * initialized an variable of CONST_VALUE.
     */
    private static final int CONST_VALUE = 50;

    /**
     * initialized an variable of GRATERTHAN_VALUE.
     */
    private static final float GRATERTHAN_VALUE = 50;

    /**
     * initialized an variable of INBETWEEN_GRATER.
     */
    private static final float INBETWEEN_GRATER = 15;

    /**
     * initialized an variable of LESSTHAN_VALUE.
     */
    private static final float INBETWEEN_LESS = 70;

    /**
     * initialized an variable of MARKS.
     */
    private static final float MARKS1 = 30;

    /**
     * initialized an variable of MARKS.
     */
    private static final float MARKS3 = 70;

    /**
     * initialized an variable of MARKS.
     */
    private static final float MARKS4 = 90;

    /**
     * Represents an instance of CRStudentPerformanceController.
     */
    private CRStudentPerformanceController cRStudentPerformanceController;

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
     * Represents an instance of CRStudentPerformanceTemplate.
     */
    private CRStudentPerformanceTemplate cRStudentPerformanceTemplate;

    /**
     * Represents an instance of ReportAbsenteeList.
     */
    private ReportAbsenteeList reportAbsenteeList;

    /**
     * Represents an instance of ReportAbsenteeList.
     */
    private ReportAbsenteeList reportAbsenteeList2;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);

        cRStudentPerformanceController = new CRStudentPerformanceController();
        cRStudentPerformanceTemplate = new CRStudentPerformanceTemplate();

        cRStudentPerformanceTemplate.setClassDescription("Class A");
        cRStudentPerformanceTemplate.setClassDescriptionGreater("Class A-Grater");
        cRStudentPerformanceTemplate.setClassDescriptionLess("Class A-Less");
        cRStudentPerformanceTemplate.setGradeDescription("Grade 1");
        cRStudentPerformanceTemplate.setGradeDescriptionGreater("Grade A-Grater");
        cRStudentPerformanceTemplate.setGradeDescriptionLess("Grade A-Less");
        cRStudentPerformanceTemplate.setGreaterThan(GRATERTHAN_VALUE);
        cRStudentPerformanceTemplate.setInBetweenGreaterValue(INBETWEEN_GRATER);
        cRStudentPerformanceTemplate.setInBetweenLessValue(INBETWEEN_LESS);
        cRStudentPerformanceTemplate.setLessThan(LESSTHAN_VALUE);

        if (CONST_VALUE > MARKS1) {
            reportAbsenteeList = new ReportAbsenteeList();
            reportAbsenteeList.setAbsent(true);
            reportAbsenteeList.setAdmissionNumber("013110");
            reportAbsenteeList.setMarks(MARKS1);
            reportAbsenteeList.setClassDescription("Class A");
            reportAbsenteeList.setGradeDescription("Grade 1");
            reportAbsenteeList.setTermDescription("Term 1");
            reportAbsenteeList.setFullName("Harsha Pathum");
            reportAbsenteeList.setSubjectDescription("English");

        }
        if (CONST_VALUE < MARKS4) {
            reportAbsenteeList2 = new ReportAbsenteeList();
            reportAbsenteeList2.setAbsent(true);
            reportAbsenteeList2.setAdmissionNumber("090346");
            reportAbsenteeList2.setMarks(MARKS3);
            reportAbsenteeList2.setClassDescription("Class B");
            reportAbsenteeList2.setGradeDescription("Grade 2");
            reportAbsenteeList2.setTermDescription("Term 2");
            reportAbsenteeList2.setFullName("Srinath Perera");
            reportAbsenteeList2.setSubjectDescription("English");
        }
        model = new ModelMap();
        model.addAttribute(cRStudentPerformanceTemplate);

        cRStudentPerformanceController.setCommonReportingService(commonReportingService);
    }

/**
      * Test method for {@link com.virtusa.sms.reporting.controller.CRStudentPerformanceController.
      * showReportForm(org.springframework.ui.ModelMap)
      *
      * @throws AkuraAppException when an error has occurred during processing.
      */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String view = cRStudentPerformanceController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/studentPerformance", view);
    }

/**
      * Test method for {@link com.virtusa.sms.reporting.controller.CRStudentPerformanceController.
      * onSubmit(org.springframework.ui.ModelMap)
      *
      * @throws AkuraAppException when an error has occurred during processing.
      * @throws JRException when an error has occurred during Jasper processing
      */
    @Test
    public void testOnSubmit() throws AkuraAppException, JRException {

        assertTrue(model.size() > 0);

        Map<String, String> params = new HashMap<String, String>();

        params.put("logoPath", ReportUtil.getReportLogo());
        params.put("StudentPerformanceGeneralTitleText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuPerformance_titleText"));
        params.put("gradeClassLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuPerformance_gradeClassLableText"));

        params.put("classLabelText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuPerformance_classLabelText"));
        params.put("classLabelText2", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuPerformance_classLabelText2"));
        params.put("admissionNumberLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuPerformance_admissionNumberLableText"));
        params.put("fullNameLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuPerformance_fullNameLableText"));
        params.put("termDescriptionLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuPerformance_termDescriptionLableText"));
        params.put("subjectDescriptionLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuPerformance_subjectDescriptionLableText"));
        params.put("MarksLabelText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuPerformance_marksLableText"));

        List<ReportAbsenteeList> list = new ArrayList<ReportAbsenteeList>();
        list.add(reportAbsenteeList);
        list.add(reportAbsenteeList2);

        String path =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH)
                        + "CRStudentPerformanceGeneral" + ".jrxml";

        String pdfPath =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH)
                        + "CRStudentPerformanceGeneral" + ".pdf";

        JasperDesign subReportDesign2 = JRXmlLoader.load(path);
        JasperReport subCompiled2 = JasperCompileManager.compileReport(subReportDesign2);
        JRBeanCollectionDataSource ds2 = new JRBeanCollectionDataSource(list);
        JasperPrint printSubreport2 = JasperFillManager.fillReport(subCompiled2, params, ds2);
        JasperExportManager.exportReportToPdfFile(printSubreport2, pdfPath);
    }

}
