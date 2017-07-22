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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.ClassTeacher;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.SchoolTeacherAndSectionHeadList;
import com.virtusa.akura.api.dto.SectionHead;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.reporting.validator.CustomStaffReportControllerValidator;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * This controller class handles the request and generates Class Teacher List and Section Head List reports.
 *
 * @author VIRTUSA Corporation
 */
@Controller
public class CustomStaffReportController {

    /** The Constant to hold All. */
    private static final String ALL = "All";

    /** The Constant to hold All. */
    private static final String STAFF_CATEGORY_TYPE = "staffCategoryType";

    /** The Constant to hold string. */
    private static final String START_DATE2 = "startDate";

    /** The Constant to hold string. */
    private static final String END_DATE2 = "endDate";

    /** The Constant to hold string. */
    private static final String SECTION2 = "section";

    /** The Constant to hold string. */
    private static final String YEAR = "year";

    /** The Constant to hold string. */
    private static final String STAFF_REPORT = "CustomStaffReport_Title_Staff";

    /** variable for holding string "generatedDate". */
    private static final String GENERATED_DATE = "generatedDate";

    /** model attribute for report generated date. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";

    /** The Constant REPORT_PAGE. */
    private static final String REPORT_PAGE = "Report_page";

    /** The Constant PAGE. */
    private static final String PAGE = "page";

    /** The Constant GPL. */
    private static final String GPL = "GPLComment";

    /** The Constant REPORT_GENERATED_ON. */
    private static final String REPORT_GENERATED_ON = "reportGeneratedOn";

    /** The Constant REPORT_GENERATED_ON_TEXT. */
    private static final String REPORT_GENERATED_ON_TEXT = "Report_reportGeneratedOn";

    /** attribute for holding style path. */
    private static final String STYLE_PATH = "style.path";

    /** attribute for holding app home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** attribute for holding system config. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** attribute for holding style template. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** The constant for String "SectionHeadsReport". */
    private static final String SECTION_HEADS_REPORT = "SectionHeadsReport";

    /** The constant for String "StaffReports". */
    private static final String STAFF_REPORTS = "StaffReports";

    /** The constant for String "grade". */
    private static final String GRADE_LOW = "grade";

    /** The constant for String "class". */
    private static final String CLASS = "class";

    /** The constant for String "class title". */
    private static final String CLASS_TITLE = "CustomStaffReport_Class";

    /** The constant for String "Class Teachers". */
    private static final String CLASS_TEACHERS = "CustomStaffReport_Class_Teachers";

    /** The constant for String "Class Grade". */
    private static final String CLASS_GRADE_TITLE = "CustomStaffReport_Class_Grade";

    /** The constant for String "classGrade". */
    private static final String CLASS_GRADE = "classGrade";

    /** The constant for String "staffCategoryList". */
    private static final String STAFF_CATEGORY_LIST = "staffCategoryList";

    /** The constant for String "gradeList". */
    private static final String GRADE_LIST = "gradeList";

    /** The constant for String "classTeacherList". */
    private static final String CLASS_TEACHER_LIST = "ClassTeacherList";

    /** The constant for String "fullName". */
    private static final String FULL_NAME_LOW = "fName";

    /** The constant for String "fullName". */
    private static final String START_DATE = "CustomStaffReport_StartDate";

    /** The constant for String "fullName". */
    private static final String END_DATE = "CustomStaffReport_EndDate";

    /** The constant for String "fullName". */
    private static final String SECTION = "CustomStaffReport_Section";

    /** The constant for String "FULL NAME". */
    private static final String FULL_NAME = "CustomStaffReport_Full_Name";

    /** The constant for String "registrationNo". */
    private static final String REGISTRATION_NO_LOW = "registrationNum";

    /** The constant for String "Registration No". */
    private static final String REGISTRATION_NO = "CustomStaffReport_Registration_No";

    /** The constant for String "title". */
    private static final String TITLE = "title";

    /** The constant for String "Section Heads". */
    private static final String SECTION_HEADS = "CustomStaffReport_Title_SectionHeads";

    /** The constant for String "SchoolTeacherAndSectionHeadList". */
    private static final String SCHOOL_TEACHER_AND_SECTION_HEAD_LIST = "SchoolTeacherAndSectionHeadList";

    /** The constant for String "School Teacher List Report request processing.". */
    private static final String SCHOOL_TEACHER_LIST_LOG = "School Teacher List Report request processing.";

    /** The constant for String "/customizedStaffGeneralReports.htm". */
    private static final String LIST_REPORTS = "/customizedStaffGeneralReports.htm";

    /** The constant for String "Calling the School Teacher List and Section Head List Reports. ". */
    private static final String SCHOOL_TEACHER_LIST_AND_SECTION_HEAD_LIST_REPORTS_LOG =
            "Calling the School Teacher List and Section Head List Reports. ";

    /** The constant for String "reporting/schoolTeacherListAndSectionHeadList". */
    private static final String REPORTING_SCHOOL_TEACHER_LIST_AND_SECTION_HEAD_LIST =
            "reporting/schoolTeacherListAndSectionHeadList";

    /** The constant for String "Year". */
    private static final String YEAR_STRING = "CustomStaffReport_Year";

    /** attribute for holding string. */
    private static final String LOGO_PATH = "logoPath";

    /** attribute for holding string. */
    private static final String DATASOURCE = "datasource";

    /** attribute for holding string. */
    private static final String LIST_TYPE = "listType";

    /** attribute for holding string "Sectional heads are not assigned.". */
    private static final String SECTIONAL_HEADS_ARE_NOT_ASSIGNED = "Sectional heads are not assigned.";

    /** attribute for holding string "Teachers are not assigned to the selected class.". */
    private static final String TEACHERS_ARE_NOT_ASSIGNED_TO_THE_SELECTED_CLASS =
            "Teachers are not assigned to the selected class.";

    /** attribute for holding string "Members are not assigned to the selected staff category.". */
    private static final String MEMBERS_ARE_NOT_ASSIGNED_ERROR_CATEGORY =
            "Members are not assigned to the selected staff category.";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(CustomStaffReportController.class);

    /**
     * staffService To invoke service methods.
     */
    private StaffService staffService;

    /**
     * CommonService To invoke service methods.
     */
    private CommonService commonService;

    /**
     * StaffCommonService To invoke service methods.
     */
    private StaffCommonService staffCommonService;

    /**
     * schoolTeacherListSectionHeadListValidator To validate.
     */
    private CustomStaffReportControllerValidator schoolTeacherListSectionHeadListValidator;

    /**
     * Inject a StaffService object using the setter.
     *
     * @param staffServices The staff Service object to be injected.
     */
    public void setStaffService(StaffService staffServices) {

        this.staffService = staffServices;
    }

    /**
     * Inject a CustomStaffReportControllerValidator object using the setter.
     *
     * @param schoolTeacherListSectionHeadListValidators to be injected.
     */
    public void setSchoolTeacherListSectionHeadListValidator(
            CustomStaffReportControllerValidator schoolTeacherListSectionHeadListValidators) {

        this.schoolTeacherListSectionHeadListValidator = schoolTeacherListSectionHeadListValidators;
    }

    /**
     * Sets the common service.
     *
     * @param objCommonService the commonService to set
     */
    public void setCommonService(CommonService objCommonService) {

        this.commonService = objCommonService;
    }

    /**
     * Sets the common service.
     *
     * @param objStaffCommonService the staffCommonService to set
     */
    public void setStaffCommonService(StaffCommonService objStaffCommonService) {

        this.staffCommonService = objStaffCommonService;
    }

    /**
     * Load the list of Staff categories to load on report.
     *
     * @return a list of available staff categories.
     * @throws AkuraAppException - throws when fails.
     */
    @ModelAttribute(STAFF_CATEGORY_LIST)
    public List<StaffCategory> populateListOfStaffCategories() throws AkuraAppException {

        return SortUtil.sortStaffCategoryList(staffCommonService.getAllStaffCategories());

    }

    /**
     * Load the list of Grades to load on jsp.
     *
     * @return a list of available Grades.
     * @throws AkuraAppException - throws when fails.
     */
    @ModelAttribute(GRADE_LIST)
    public List<Grade> populateClassGradeList() throws AkuraAppException {

        return SortUtil.sortGradeList(commonService.getGradeList());
    }

    /**
     * Get the user request and show School Teacher List Report request link and Section Head List Report
     * request.
     *
     * @param modelMap of type ModelMap.
     * @return The view name of the request handler.
     * @throws AkuraAppException throw exception if request processing fails.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(ModelMap modelMap) throws AkuraAppException {

        LOG.info(SCHOOL_TEACHER_LIST_AND_SECTION_HEAD_LIST_REPORTS_LOG);
        SchoolTeacherAndSectionHeadList schoolTeacherSectionHeadList = new SchoolTeacherAndSectionHeadList();
        schoolTeacherSectionHeadList.setListType(2);
        modelMap.addAttribute(SCHOOL_TEACHER_AND_SECTION_HEAD_LIST, schoolTeacherSectionHeadList);
        return REPORTING_SCHOOL_TEACHER_LIST_AND_SECTION_HEAD_LIST;
    }

    /**
     * Generate School Teacher List Report and School Section Head List Report request processing method.
     *
     * @param schoolStaffHeadList a model to bind values.
     * @param errors To bind errors.
     * @param response the servlet response.
     * @return The view name of the request handler.
     * @throws AkuraAppException throw exception if request processing fails.
     */
    @RequestMapping(value = LIST_REPORTS, method = RequestMethod.POST)
    public String showTeacherListReport(
            @ModelAttribute(SCHOOL_TEACHER_AND_SECTION_HEAD_LIST) SchoolTeacherAndSectionHeadList schoolStaffHeadList,
            BindingResult errors, HttpServletResponse response) throws AkuraAppException {

        LOG.info(SCHOOL_TEACHER_LIST_LOG);

        JRBeanCollectionDataSource datasource;

        schoolTeacherListSectionHeadListValidator.validate(schoolStaffHeadList, errors);

        if (errors.hasErrors()) {

            return REPORTING_SCHOOL_TEACHER_LIST_AND_SECTION_HEAD_LIST;

        }

        Map<String, Object> parameterMap = new HashMap<String, Object>();

        // if the list type is true then generate the Teacher List Report.
        if (schoolStaffHeadList.getListType() == AkuraConstant.PARAM_INDEX_ONE) {

            List<Staff> staffByCategoryList = null;
            if (schoolStaffHeadList.getCatogaryID() == -1) {
                staffByCategoryList = staffService.getAllAvailableStaffWithOutDeparture();

                parameterMap.put(STAFF_CATEGORY_TYPE, ALL);

            } else {
                staffByCategoryList = staffService.getStaffListByCategory(schoolStaffHeadList.getCatogaryID());

                parameterMap.put(STAFF_CATEGORY_TYPE, staffCommonService.getStaffCategory(
                        schoolStaffHeadList.getCatogaryID()).getDescription());

            }

            if (staffByCategoryList.isEmpty()) {

                errors.addError(new FieldError(SCHOOL_TEACHER_AND_SECTION_HEAD_LIST, LIST_TYPE,
                        MEMBERS_ARE_NOT_ASSIGNED_ERROR_CATEGORY));
                return REPORTING_SCHOOL_TEACHER_LIST_AND_SECTION_HEAD_LIST;
            }

            // Sort the staff List and Set to data source.
            datasource = new JRBeanCollectionDataSource(SortUtil.sortStaffList(staffByCategoryList));
            parameterMap.put(TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STAFF_REPORT));
            parameterMap.put(YEAR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, YEAR_STRING));
            parameterMap = populateParameterMap(parameterMap, datasource);

            ReportUtil.displayReportInPdfForm(response, datasource, parameterMap, STAFF_REPORTS);

        } else if (schoolStaffHeadList.getListType() == AkuraConstant.PARAM_INDEX_THREE) {

            List<ClassTeacher> classTeacherList = getClassTeacherListByGrade(schoolStaffHeadList.getGradeId());

            if (classTeacherList.isEmpty()) {

                errors.addError(new FieldError(SCHOOL_TEACHER_AND_SECTION_HEAD_LIST, LIST_TYPE,
                        TEACHERS_ARE_NOT_ASSIGNED_TO_THE_SELECTED_CLASS));
                return REPORTING_SCHOOL_TEACHER_LIST_AND_SECTION_HEAD_LIST;
            }
            datasource = new JRBeanCollectionDataSource(SortUtil.sortClassTeacherList(classTeacherList));

            parameterMap = populateParameterMap(parameterMap, datasource);
            parameterMap.put(CLASS_GRADE, PropertyReader
                    .getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASS_GRADE_TITLE));
            parameterMap.put(TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASS_TEACHERS));
            parameterMap.put(GRADE_LOW, schoolStaffHeadList.getGradeDescription());
            parameterMap.put(YEAR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, YEAR_STRING));
            parameterMap.put(CLASS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASS_TITLE));

            ReportUtil.displayReportInPdfForm(response, datasource, parameterMap, CLASS_TEACHER_LIST);

        } else {

            List<SectionHead> sectionHeadList =
                    getAllCurrrentSectionHeads(staffService.getCurrentSectionHeadList(new Date()));

            if (sectionHeadList.isEmpty()) {

                errors.addError(new FieldError(SCHOOL_TEACHER_AND_SECTION_HEAD_LIST, LIST_TYPE,
                        SECTIONAL_HEADS_ARE_NOT_ASSIGNED));
                return REPORTING_SCHOOL_TEACHER_LIST_AND_SECTION_HEAD_LIST;
            }

            datasource = new JRBeanCollectionDataSource(SortUtil.sortSectionHeadList(sectionHeadList));
            parameterMap.put(TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SECTION_HEADS));
            parameterMap.put(START_DATE2, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, START_DATE));
            parameterMap.put(END_DATE2, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, END_DATE));
            parameterMap.put(SECTION2, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SECTION));

            parameterMap = populateParameterMap(parameterMap, datasource);

            ReportUtil.displayReportInPdfForm(response, datasource, parameterMap, SECTION_HEADS_REPORT);
        }

        return null;
    }

    /**
     * Set common parameters to print on report.
     *
     * @param parameterMap a map of parameters.
     * @param datasource a collection of data to print.
     * @return map full of parameters.
     * @throws AkuraAppException throw exception if request processing fails.
     */
    private Map<String, Object> populateParameterMap(Map<String, Object> parameterMap,
            JRBeanCollectionDataSource datasource) throws AkuraAppException {

        parameterMap.put(DATASOURCE, datasource);
        parameterMap.put(LOGO_PATH, ReportUtil.getReportLogo());
        parameterMap.put(REGISTRATION_NO_LOW, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REGISTRATION_NO));
        parameterMap.put(FULL_NAME_LOW, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, FULL_NAME));
        parameterMap.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        parameterMap.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_GENERATED_ON_TEXT));
        parameterMap.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        parameterMap.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        parameterMap.put(GPL, AkuraWebConstant.REPORT_GPL);

        return parameterMap;

    }

    /**
     * Get the class Teacher list by grade.
     *
     * @param gradeId - The grade id.
     * @return a list of class teachers in the given grade.
     * @throws AkuraAppException throw exception if request processing fails.
     */
    public List<ClassTeacher> getClassTeacherListByGrade(int gradeId) throws AkuraAppException {

        return staffService.getClassTeacherListByGrade(gradeId);

    }

    /**
     * Get the list of section heads who are still in duty as a section head.
     *
     * @param allSectionHeads - list of all section heads from the db.
     * @return list of current section heads.
     * @throws AkuraAppException throw exception if request processing fails.
     */
    private List<SectionHead> getAllCurrrentSectionHeads(List<SectionHead> allSectionHeads) throws AkuraAppException {

        List<SectionHead> listOfCurrentSectionHeads = new ArrayList<SectionHead>();

        if (allSectionHeads.size() > 0) {

            for (SectionHead sHead : allSectionHeads) {

                if (sHead.getGradeId() != 0) {

                    sHead.setSection(commonService.findGradeById(sHead.getGradeId()).getDescription());
                    listOfCurrentSectionHeads.add(sHead);

                } else {
                    sHead.setSection(staffCommonService.findSection(sHead.getSectionId()).getDescription());
                    listOfCurrentSectionHeads.add(sHead);
                }
            }

        }

        return listOfCurrentSectionHeads;

    }
}
