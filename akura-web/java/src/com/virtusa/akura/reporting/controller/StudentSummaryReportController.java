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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.Achievement;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ExamResults;
import com.virtusa.akura.api.dto.FaithLifeRating;
import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentAchievementTemplate;
import com.virtusa.akura.api.dto.StudentAssignmentMarkTemplate;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentClubSociety;
import com.virtusa.akura.api.dto.StudentFaithLifeTemplate;
import com.virtusa.akura.api.dto.StudentMarksTemplate;
import com.virtusa.akura.api.dto.StudentPrefect;
import com.virtusa.akura.api.dto.StudentScholarship;
import com.virtusa.akura.api.dto.StudentSport;
import com.virtusa.akura.api.dto.StudentSubTermMarkDTO;
import com.virtusa.akura.api.dto.StudentSummaryReportTemplate;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.validator.StudentSummaryReportTemplateValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.StaticDataUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * StudentSummaryReportController is to generate student summary report including Student Personal Details,
 * Academic Life, Co curricular details and Religious Activities.
 *
 * @author Virtusa Corporation
 */

@Controller
public class StudentSummaryReportController {

    /** The String of Array attribute. */
    private String[] toListStr = new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" };

    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_ADDMISSION_NO = "selectedAddmissionNo";

    /** Represents the array that selected to the 'FromList'. */
    private static final String REMOVED_FROM_ARRAY = "removedFromArray";

    /** Represents the array that selected to the 'ToList'. */
    private static final String SELECTED_ARRAY = "selectedArray";

    /** The Sting variable for sub term title. */
    private static final String SUB_TERM_TITLE = "sub_term_title";

    /** The Sting variable for STUDENT_SUMMARY_SUB_TERM_MARKS_TITLE. */
    private static final String STUDENT_SUMMARY_SUB_TERM_MARKS_TITLE = "StudentSummary_sub_term_marks_title";

    /** The Sting variable for SUB_TERM_MARKS. */
    private static final String SUB_TERM_MARKS = "sub_term_marks";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_SUB_TERM_MARKS = "StudentSummary_sub_term_marks";

    /** The Sting variable for report constant. */
    private static final String SUB_TERM_GRADING = "sub_term_grading";

    /** The Sting variable for report constant. */
    private static final String TERM_MARKS_TITLE = "term_marks_title";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_TERM_MARKS_TITLE = "StudentSummary_term_marks_title";

    /** The Sting variable for report constant. */
    private static final String TERM_MARKS_TERM = "term_marks_term";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_TERM_MARKS_TERM = "StudentSummary_term_marks_term";

    /** The Sting variable for report constant. */
    private static final String TERM_MARKS_SUBJECT = "term_marks_subject";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_TERM_MARKS_SUBJECT = "StudentSummary_term_marks_subject";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_TERM_MARKS_MARKS = "StudentSummary_term_marks_marks";

    /** The Sting variable for report constant. */
    private static final String TERM_MARKS_MARKS = "term_marks_marks";

    /** The Sting variable for report constant. */
    private static final String STUDENT_PERSONAL_INFO_TITLE2 = "studentPersonalInfoTitle";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_PREFECT_TYPE = "StudentSummary_prefect_type";

    /** The Sting variable for report constant. */
    private static final String PREFECT_TYPE = "prefect_type";

    /** The Sting variable for report constant. */
    private static final String PREFECT_YEAR = "prefect_year";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_PREFECT_TITLE = "StudentSummary_prefect_title";

    /** The Sting variable for report constant. */
    private static final String PREFECT_TITLE = "prefect_title";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_SCHORLASHIP = "StudentSummary_schorlaship";

    /** The Sting variable for report constant. */
    private static final String SCHORLASHIP = "schorlaship";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_SCHORLASHIP_TITLE = "StudentSummary_schorlaship_title";

    /** The Sting variable for report constant. */
    private static final String SCHORLASHIP_TITLE = "schorlaship_title";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_ACHIEVEMENT_CLUBSOCIETY = "StudentSummary_achievement_clubsociety";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_ACHIEVEMENT_CATEGORY = "StudentSummary_achievement_category";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_ACHIEVEMENT = "StudentSummary_achievement";

    /** The Sting variable for report constant. */
    private static final String ACHIEVEMENT = "achievement";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_ACHIEVEMENT_TITLE = "StudentSummary_achievement_title";

    /** The Sting variable for report constant. */
    private static final String ACHIEVEMENT_TITLE = "achievement_title";

    /** The Sting variable for report constant. */
    private static final String POSITION = "position";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_CLUB_SOCIETY = "StudentSummary_club_society";

    /** The Sting variable for report constant. */
    private static final String CLUB_SOCIETY = "club_society";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_CLUB_SOCIETY_TITLE = "StudentSummary_club_society_title";

    /** The Sting variable for report constant. */
    private static final String CLUB_SOCIETY_TITLE = "club_society_title";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_SPORT_POSITION = "StudentSummary_sport_position";

    /** The Sting variable for report constant. */
    private static final String SPORT_POSITION = "sport_position";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_TEAM = "StudentSummary_sport_team";

    /** The Sting variable for report constant. */
    private static final String SPORT_TEAM = "sport_team";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_SPORT = "StudentSummary_sport_sport";

    /** The Sting variable for report constant. */
    private static final String SPORT = "sport";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_SPORT_TITLE = "StudentSummary_sport_title";

    /** The Sting variable for report constant. */
    private static final String SPORT_TITLE = "sport_title";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_FAITH_LIFE_GRADING = "StudentSummary_faith_life_grading";

    /** The Sting variable for report constant. */
    private static final String GRADING = "grading";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_FAITH_LIFE_COMMENTS = "StudentSummary_faith_life_comments";

    /** The Sting variable for report constant. */
    private static final String COMMENTS = "comments";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_FAITH_LIFE_CATEGORY = "StudentSummary_faith_life_category";

    /** The Sting variable for report constant. */
    private static final String CATEGORY = "category";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_TERM_MARKS_YEAR = "StudentSummary_term_marks_year";

    /** The Sting variable for report constant. */
    private static final String TERM_MARKS_YEAR = "term_marks_year";

    /** The Sting variable for report constant. */
    private static final String STUDENT_SUMMARY_FAITH_LIFE_TITLE = "StudentSummary_faith_life_title";

    /** The Sting variable for report constant. */
    private static final String FAITH_LIFE_TITLE = "faith_life_title";

    /** The Sting variable for report constant. */
    private static final String REQ_FLAG = "flag";

    /** The Sting variable for report constant. */
    private static final String SHOW_STUDENT_SUMMARY_REPORT_FORM_HTM = "/showStudentSummaryReportForm.htm";

    /** Represents the array that selected toList. */
    private static final String SELECTED_TO_LIST = "selectedToList";

    /** The Constant request attribute. */
    private static final String REQ_PARA_SELECTED_CLASS = "selectedClass";

    /** The Constant model attribute. */
    private static final String MODEL_ATT_SELECTED_CLASS_GRADE_ID = "selectedClassGradeId";

    /** The Constant CATEGORY_STUDENT_STUDENT_EXAM_MARK. */
    private static final String CATEGORY_STUDENT_STUDENT_EXAM_MARK = "CATEGORY.STUDENT.STUDENT.EXAM.MARK";

    /** The Constant CATEGORY_STUDENT_STUDENT_ASSIGNMENT_MARK. */
    private static final String CATEGORY_STUDENT_STUDENT_ASSIGNMENT_MARK = "CATEGORY.STUDENT.STUDENT.ASSIGNMENT.MARK";

    /** The Constant CATEGORY_STUDENT_STUDENT_RELIGIOUS_ACTIVITIES. */
    private static final String CATEGORY_STUDENT_STUDENT_RELIGIOUS_ACTIVITIES =
            "CATEGORY.STUDENT.STUDENT.RELIGIOUS.ACTIVITIES";

    /** The Constant CATEGORY_STUDENT_STUDENT_SPORT_DETAILS. */
    private static final String CATEGORY_STUDENT_STUDENT_SPORT_DETAILS = "CATEGORY.STUDENT.STUDENT.SPORT.DETAILS";

    /** The Constant CATEGORY_STUDENT_STUDENT_CLUB_SOCIETY. */
    private static final String CATEGORY_STUDENT_STUDENT_CLUB_SOCIETY = "CATEGORY.STUDENT.STUDENT.CLUB.SOCIETY";

    /** The Constant ATEGORY_STUDENT_STUDENT_EXAM_MARK. */
    private static final String CATEGORY_STUDENT_STUDENT_ARCHIEVEMENTS = "CATEGORY.STUDENT.STUDENT.ARCHIEVEMENTS";

    /** The Constant CATEGORY_STUDENT_STUDENT_ARCHIEVEMENTS. */
    private static final String CATEGORY_STUDENT_SCHOLARSHIP_DETAILS = "CATEGORY.STUDENT.SCHOLARSHIP.DETAILS";

    /** The Constant CATEGORY_STUDENT_PREFECT_DETAILS. */
    private static final String CATEGORY_STUDENT_PREFECT_DETAILS = "CATEGORY.STUDENT.PREFECT.DETAILS";

    /** The Constant CATEGORY_STUDENT_SUB_TERM_MARK. */
    private static final String CATEGORY_STUDENT_SUB_TERM_MARK = "CATEGORY.STUDENT.SUB.TERM.MARK";

    /** The Constant CATEGORY_STUDENT_TERM_MARK. */
    private static final String CATEGORY_STUDENT_TERM_MARK = "CATEGORY.STUDENT.TERM.MARK";

    /** The Constant POPULATE_ADDMISSION_NOS_BY_CLASS_HTM. */
    private static final String POPULATE_ADDMISSION_NOS_BY_CLASS_HTM = "/populateAddmissionNosByClass1.htm";

    /** The Constant CLASS_GRADE_ID. */
    private static final String CLASS_GRADE_ID = "classGradeId";

    /** The Constant CLASS_GRADE_LIST. */
    private static final String CLASS_GRADE_LIST = "classGradeList";

    /** The Constant SELECTED_ADDMISSION. */
    private static final String SELECTED_ADDMISSION = "selectedAddmission";

    /** attribute for holding message. */
    public static final String MESSAGE = "message";

    /** reportTemplate Property file key value. */
    private static final String MARK_GRADE_LABEL_KEY = "StudentSummary_mark_grade_label";

    /** reportTemplate Property file key value. */
    private static final String INDEX_NO_LABEL_KEY = "StudentSummary_index_no_label";

    /** reportTemplate Property file key value. */
    private static final String YEAR_LABEL_KEY = "StudentSummary_year_label";

    /** reportTemplate Property file key value. */
    private static final String EXAM_NAME_LABEL_KEY = "StudentSummary_exam_name_label";

    /** reportTemplate Property file key value. */
    private static final String EXAM_SUB_LABEL_KEY = "StudentSummary_exam_subject_label";

    /** reportTemplate Property file key value. */
    private static final String EXAM_MARKS_TITLE_KEY = "StudentSummary_exam_marks_title";

    /** Report property Name. */
    private static final String MARK_GRADE_LABEL = "mark_grade_label";

    /** Report property Name. */
    private static final String INDEX_NO_LABEL = "index_no_label";

    /** Report property Name. */
    private static final String YEAR_LABEL = "year_label";

    /** Report property Name. */
    private static final String NOTE_LABEL = "note";
    
    /** Report property Name. */
    private static final String EXAM_NAME_LABEL = "exam_name_label";

    /** Report property Name. */
    private static final String EXAM_SUBJECT_LABEL = "exam_subject_label";

    /** Report property Name. */
    private static final String EXAM_MARKS_TITLE = "exam_marks_title";

    /** Report property Name for data source 10. */
    private static final String DATASOURCE10 = "datasource10";

    /** variable for holding string "generatedDate". */
    private static final String GENERATED_DATE = "generatedDate";

    /** model attribute for report generated date. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";

    /** model attribute for report note. */
    private static final String REPORT_MAXMARK_NOTE = "REPORT.MAXMARK.NOTE";
    
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

    /** The Constant PERSONAL_INFO_SUB. */
    private static final String PERSONAL_INFO_SUB = "personal_sub";

    /** The Constant STUDENT_PERSONAL_INFORMATION. */
    private static final String STUDENT_PERSONAL_INFO_TITLE = "StudentSummary_personal_info_title";

    /** The Constant EXTERNAL_ACHIEVEMENT. */
    private static final String EXTERNAL_ACHIEVEMENT = "External Achievement";

    /** The Constant CLUB_SOCIETY_ACHIEVEMENT. */
    private static final String CLUB_SOCIETY_ACHIEVEMENT = "Club&Society Achievement";

    /** The Constant ACADEMIC_ACHIEVEMENT. */
    private static final String ACADEMIC_ACHIEVEMENT = "Academic Achievement";

    /** The Constant FAITHLIFE_DATASOURCE. */
    private static final String FAITHLIFE_DATASOURCE = "datasource8";

    /** The Constant SPORTS_DATASOURCE. */
    private static final String SPORTS_DATASOURCE = "datasource7";

    /** The Constant COCURRICULAR_DATASOURCE. */
    private static final String COCURRICULAR_DATASOURCE = "datasource6";

    /** The Constant ACHIEVEMENT_DATASOURCE. */
    private static final String ACHIEVEMENT_DATASOURCE = "datasource5";

    /** The Constant SCHOLARSHIP_DATASOURCE. */
    private static final String SCHOLARSHIP_DATASOURCE = "datasource4";

    /** The Constant PREFECT_DATASOURCE. */
    private static final String PREFECT_DATASOURCE = "datasource3";

    /** The Constant SUBTERMMARK_DATASOURCE. */
    private static final String SUBTERMMARK_DATASOURCE = "datasource2";

    /** The Constant TERMMARK_DATASOURCE. */
    private static final String TERMMARK_DATASOURCE = "datasource1";

    /** The Constant STUDENT_SUMMARY_REPORT. */
    private static final String STUDENT_SUMMARY_REPORT = "studentSummaryReport";

    /** The Constant REF_UI_STUDENT_ADMISSIONNO_VIOLATEERROR. */
    private static final String REF_UI_STUDENT_ADMISSIONNO_VIOLATEERROR = "REF.UI.STUDENT.ADMISSIONNO.VIOLATEERROR";

    /** The Constant PHOTO. */
    private static final String PHOTO = "photo";

    /** The Constant REPORT_ADMINSSION_DATE. */
    private static final String REPORT_ADMINSSION_DATE = "StudentSummary_admissionDateText";

    /** The Constant REPORT_ADDRESS. */
    private static final String REPORT_ADDRESS = "StudentSummary_addressText";

    /** The Constant REPORT_DATE_OF_BIRTH. */
    private static final String REPORT_DATE_OF_BIRTH = "StudentSummary_dateOfBirthText";

    /** The Constant REPORT_FULL_NAME. */
    private static final String REPORT_FULL_NAME = "StudentSummary_fullNameText";

    /** The Constant REPORT_ADMISSION_NO. */
    private static final String REPORT_ADMISSION_NO = "StudentSummary_admissionNoText";

    /** The Constant REPORT_STUDENT_DETAILS_SUMMARY. */
    private static final String REPORT_STUDENT_DETAILS_SUMMARY = "StudentSummary_titleText";

    /** The Constant REPORT_STUDENT_DETAILS_SUMMARY. */
    private static final String REPORT_STUDENT_DETAILS_SUMMARY2 = "StudentSummary_titleText2";

    /** Constant for style.path. */
    private static final String STYLE_PATH = "style.path";

    /** The Constant ADMISSION_DATE. */
    private static final String ADMISSION_DATE = "admissionDate";

    /** The Constant ADDRESS. */
    private static final String ADDRESS = "address";

    /** The Constant DATE_OF_BIRTH. */
    private static final String DATE_OF_BIRTH = "dateOfBirth";

    /** The Constant FULL_NAME. */
    private static final String FULL_NAME = "fullName";

    /** The Constant ADMISSION_NO. */
    private static final String ADMISSION_NO = "admissionNo";

    /** The Constant TITLE. */
    private static final String TITLE = "title";

    /** The Constant DATASOURCE_KEY. */
    private static final String MAINREPORT_DATASOURCE = "datasource";

    /** The Constant STUDENT_SUMMARY_TEMPLATE. */
    private static final String STUDENT_SUMMARY_TEMPLATE = "StudentSummaryTemplate";

    /** The Constant GENERATE_STUDENT_SUMMARY_REPORT_HTM. */
    private static final String GENERATE_STUDENT_SUMMARY_REPORT_HTM = "/studentSummaryReport.htm";

    /** The Constant REPORTING_STUDENT_SUMMARY_REPORT_VIEW. */
    private static final String REPORTING_STUDENT_SUMMARY_REPORT_VIEW = "reporting/studentSummaryReport";

    /** property file name. */
    private static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";

    /** key to define the image folder path. */
    private static final String IMAGE_FOLDER_PATH = "imageFolder.path";

    /** key to define the image file format. */
    private static final String FILE_EXT = ".jpg";

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Constant for styleTemplate. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** The Constant LOGO. */
    private static final String LOGO = "logo";

    /** The Constant EMAIL_PROPERTIES. */
    public static final String EMAIL_PROPERTIES = "email";

    /** The Constant STUDENT_SUMMARY_MARKS. */
    private static final String STUDENT_SUMMARY_MARKS = "StudentSummary_marks";

    /** The Constant STUDENT_SUMMARY_YEAR. */
    private static final String STUDENT_SUMMARY_YEAR = "StudentSummary_year";

    /** The Constant ASSIGNMENT_MARKS. */
    private static final String ASSIGNMENT_MARKS = "assignment_marks";

    /** The Constant STUDENT_SUMMARY_ASSIGNMENT_NAME. */
    private static final String STUDENT_SUMMARY_ASSIGNMENT_NAME = "StudentSummary_assignment_name";

    /** The Constant ASSIGNMENT_NAME. */
    private static final String ASSIGNMENT_NAME = "assignment_name";

    /** The Constant STUDENT_SUMMARY_ASSIGNMENT_SUBJECT. */
    private static final String STUDENT_SUMMARY_ASSIGNMENT_SUBJECT = "StudentSummary_assignment_subject";

    /** The Constant ASSIGNMENT_SUBJECT. */
    private static final String ASSIGNMENT_SUBJECT = "assignment_subject";

    /** The Constant ASSIGNMENT_YEAR. */
    private static final String ASSIGNMENT_YEAR = "assignment_year";

    /** The Constant STUDENT_SUMMARY_ASSIGNMENT_TITLE. */
    private static final String STUDENT_SUMMARY_ASSIGNMENT_TITLE = "StudentSummary_assignment_title";

    /** The Constant ASSIGNMENT_TITLE. */
    private static final String ASSIGNMENT_TITLE = "assignment_title";

    /** The Constant DATA_SOURCE_STUDENT_ASSIGNMENT_MARKS. */
    private static final String DATA_SOURCE_STUDENT_ASSIGNMENT_MARKS = "dataSourceStudentAssignmentMarks";

    /** The Constant DEPARTURE_DATE. */
    private static final String DEPARTURE_DATE = "dateOfDeparture";

    /** The Constant REPORT_STUDENT_DETAILS_SUMMARY. */
    private static final String REPORT_STUDENT_DATE_DEPARTURE = "StaffPresentAndAbsentDays_Report_DateTodeparture";

    /** The Constant AB. */
    private static final String AB = "AB";

    /** The Constant INDEX_ELEVEN. */
    private static final int INDEX_ELEVEN = 11;

    /** The Constant INDEX_TEN. */
    private static final int INDEX_TEN = 10;

    /** The Constant INDEX_NINE. */
    private static final int INDEX_NINE = 9;

    /** The Constant INDEX_EIGHT. */
    private static final int INDEX_EIGHT = 8;

    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_SEVEN = 7;

    /** The Constant INDEX_SIX. */
    private static final int INDEX_SIX = 6;

    /** The Constant INDEX_FIVE. */
    private static final int INDEX_FIVE = 5;

    /** The Constant INDEX_FOUR. */
    private static final int INDEX_FOUR = 4;

    /** The Constant INDEX_THREE. */
    private static final int INDEX_THREE = 3;

    /** model attribute of LastList object. */
    private static final String MODEL_CATEGORY_LIST = "categoryList";

    /** request attribute of `ToList` value. */
    private static final String REQUEST_TO_LIST = "ToList";

    /** The student summary template validator. */
    private StudentSummaryReportTemplateValidator studentSummaryReportTemplateValidator;

    /** The student service. */
    private StudentService studentService;

    /** The common service. */
    private CommonService commonService;

    /**
     * Sets the student summary report template validator.
     *
     * @param objStudentSummaryReportTemplateValidator the studentSummaryReportTemplateValidator to set
     */
    public final void setStudentSummaryReportTemplateValidator(
            StudentSummaryReportTemplateValidator objStudentSummaryReportTemplateValidator) {

        this.studentSummaryReportTemplateValidator = objStudentSummaryReportTemplateValidator;
    }

    /**
     * Sets the student service.
     *
     * @param objStudentService the studentService to set
     */
    public final void setStudentService(final StudentService objStudentService) {

        this.studentService = objStudentService;
    }

    /**
     * Sets the common service.
     *
     * @param objCommonService the commonService to set
     */
    public final void setCommonService(CommonService objCommonService) {

        this.commonService = objCommonService;
    }

    /**
     * Method Handle view page of student summary report form.
     *
     * @param modelMap of type ModelMap
     * @return java.lang.String
     */
    @RequestMapping(value = SHOW_STUDENT_SUMMARY_REPORT_FORM_HTM, method = RequestMethod.GET)
    public final String showStudentSummaryReportForm(final ModelMap modelMap) {

        if (modelMap.get(STUDENT_SUMMARY_TEMPLATE) == null) {
            modelMap.addAttribute(STUDENT_SUMMARY_TEMPLATE, new StudentSummaryReportTemplate());
        }

        Map<Integer, String> categoryList = new HashMap<Integer, String>();
        categoryList.put(2, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CATEGORY_STUDENT_TERM_MARK));
        categoryList.put(INDEX_THREE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CATEGORY_STUDENT_SUB_TERM_MARK));
        categoryList.put(INDEX_FOUR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CATEGORY_STUDENT_PREFECT_DETAILS));
        categoryList.put(INDEX_FIVE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CATEGORY_STUDENT_SCHOLARSHIP_DETAILS));
        categoryList.put(INDEX_SIX, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CATEGORY_STUDENT_STUDENT_ARCHIEVEMENTS));
        categoryList.put(INDEX_SEVEN, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CATEGORY_STUDENT_STUDENT_CLUB_SOCIETY));
        categoryList.put(INDEX_EIGHT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CATEGORY_STUDENT_STUDENT_SPORT_DETAILS));
        categoryList.put(INDEX_NINE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CATEGORY_STUDENT_STUDENT_RELIGIOUS_ACTIVITIES));
        categoryList.put(INDEX_TEN, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CATEGORY_STUDENT_STUDENT_ASSIGNMENT_MARK));
        categoryList.put(INDEX_ELEVEN, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CATEGORY_STUDENT_STUDENT_EXAM_MARK));
        modelMap.addAttribute(MODEL_CATEGORY_LIST, categoryList);
        return REPORTING_STUDENT_SUMMARY_REPORT_VIEW;
        
        
    }

    /**
     * Generate student summary report which includes student academic life, co curricular and religious
     * activities.
     *
     * @param studentSummaryTemplate the student summary template
     * @param bindingResult the binding result
     * @param modelView the ModelAndView type
     * @param modelMap the ModelMap type
     * @param request the request
     * @return the model and view
     * @throws AkuraAppException the sMS app exception
     * @throws ParseException the parse exception
     */
    @RequestMapping(value = GENERATE_STUDENT_SUMMARY_REPORT_HTM)
    public ModelAndView generateStudentSummaryReport(
            @ModelAttribute(STUDENT_SUMMARY_TEMPLATE) StudentSummaryReportTemplate studentSummaryTemplate,
            BindingResult bindingResult, ModelAndView modelView, ModelMap modelMap, HttpServletRequest request)
            throws AkuraAppException, ParseException {

        String redirectURL = STUDENT_SUMMARY_REPORT;
        Map<String, Object> map = new HashMap<String, Object>();

        String selectedClass = request.getParameter(REQ_PARA_SELECTED_CLASS);
        String selectedNameandAndAdmission=request.getParameter(SELECTED_ADDMISSION);
       
        String selectedAddmission=null;
        
        if(selectedNameandAndAdmission!=null){
        	 String[]  parts = selectedNameandAndAdmission.split("-");
        	  selectedAddmission = parts[0];
        }
       
               
        
        String selectedList = null;

        String flag = request.getParameter(REQ_FLAG);
        boolean booleanFlag = Boolean.parseBoolean(flag);

        if (!booleanFlag) {
            toListStr = request.getParameterValues(REQUEST_TO_LIST);

            selectedList = (String) request.getParameter(SELECTED_ARRAY);
            modelMap.addAttribute(REMOVED_FROM_ARRAY, request.getParameter(REMOVED_FROM_ARRAY));

            if (toListStr == null) {
                modelMap.addAttribute(MODEL_ATT_SELECTED_ADDMISSION_NO, selectedAddmission);
                modelMap.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, selectedClass);
                modelMap.addAttribute(SELECTED_TO_LIST, selectedList);
                String message = new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_ADMISSIONNO_VIOLATEERROR);
                modelMap.addAttribute(MESSAGE, message);
                String url = showStudentSummaryReportForm(modelMap);
                return new ModelAndView(url, map);
            }
        }

        if (selectedAddmission != null) {
            studentSummaryTemplate.setStudentAdmissionNo(selectedAddmission.trim());
        } else {
            String message = new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_ADMISSIONNO_VIOLATEERROR);
            modelMap.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, selectedClass);
            modelMap.addAttribute(MESSAGE, message);
            String url = showStudentSummaryReportForm(modelMap);
            return new ModelAndView(url, map);
        }

        studentSummaryReportTemplateValidator.validate(studentSummaryTemplate, bindingResult);

        Student student = null;

        if (bindingResult.hasErrors()) {
            redirectURL = REPORTING_STUDENT_SUMMARY_REPORT_VIEW;
            modelMap.addAttribute(SELECTED_TO_LIST, selectedList);
            return new ModelAndView(redirectURL, map);
        } else {

            if (selectedAddmission.equals(AkuraWebConstant.STRING_ZERO)) {

                String message = new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_ADMISSIONNO_VIOLATEERROR);
                modelMap.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, selectedClass);
                modelMap.addAttribute(SELECTED_TO_LIST, selectedList);
                modelMap.addAttribute(MESSAGE, message);
                String url = showStudentSummaryReportForm(modelMap);
                return new ModelAndView(url, map);
            }

            // Get Student Personal Details by using student admission number
            List<Student> studentPersonalDetailsList =
                    studentService.getStudentPersonalDetailsByAdmissionNo(studentSummaryTemplate
                            .getStudentAdmissionNo());

            initializingReport(map);
            if (studentPersonalDetailsList != null) {
                student = studentPersonalDetailsList.get(0);

                if (toListStr == null) {
                    populateStudentPersonalDetails(map, student, studentPersonalDetailsList);
                } else {
                    int[] toList = new int[toListStr.length];
                    for (int i = 0; i < toListStr.length; i++) {
                        toList[i] = Integer.parseInt(toListStr[i]);

                    }

                    populateStudentPersonalDetails(map, student, studentPersonalDetailsList);
                    for (int i = 0; i < toList.length; i++) {

                        switch (toList[i]) {
                            case 2:
                                populateStudentTermMarks(map, student);
                                break;
                            case INDEX_THREE:
                                populateStudentSubTermMarks(map, student);
                                break;
                            case INDEX_FOUR:
                                populateStudentPrefectDetails(map, student);
                                break;
                            case INDEX_FIVE:
                                populateStudentScholarshipDetails(map, student);
                                break;
                            case INDEX_SIX:
                                populateStudentAchievements(map, student);
                                break;
                            case INDEX_SEVEN:
                                populateStudentClubSocietyDetails(map, student);
                                break;
                            case INDEX_EIGHT:
                                populateStudentSportsDetails(map, student);
                                break;
                            case INDEX_NINE:
                                populateStudentReligiousActivities(map, student);
                                break;
                            case INDEX_TEN:
                                populateStudentAssignmentMarks(map, student);
                                break;
                            case INDEX_ELEVEN:
                                populateExamMarks(map, student);
                                break;
                            default:
                                break;
                        }

                    }
                }
            }

        }
        return new ModelAndView(redirectURL, map);
    }

    /**
     * Populate student assignment marks.
     *
     * @param map the map
     * @param student the student
     * @throws AkuraAppException the akura app exception
     * @throws ParseException the parse exception
     */
    private void populateStudentAssignmentMarks(Map<String, Object> map, Student student) throws AkuraAppException,
            ParseException {

        int indexZero = 0;
        int indexOne = 1;
        int indexTwo = 2;
        int indexFour = INDEX_FOUR;
        int indexFive = INDEX_FIVE;
        int indexThree = INDEX_THREE;

        List<Object[]> studentAssignmentMarks =
                studentService.getStudentAssignmentMarksByStudentIdForReport(student.getStudentId());
        List<StudentAssignmentMarkTemplate> assignmentMarkTemplates = new ArrayList<StudentAssignmentMarkTemplate>();

        for (Object[] object : studentAssignmentMarks) {
            StudentAssignmentMarkTemplate assignmentMarkTemplate = new StudentAssignmentMarkTemplate();
            assignmentMarkTemplate.setYear(DateUtil.convertStringToDate(object[indexZero].toString()));
            assignmentMarkTemplate.setSubject(object[indexOne].toString());
            assignmentMarkTemplate.setAssignment(object[indexTwo].toString());
            String gradingAcronym = AkuraWebConstant.EMPTY_STRING;

            if (object[indexThree] != null) {
                Grading grading = commonService.findGradingById(Integer.parseInt(object[indexThree].toString()));
                gradingAcronym = grading.getGradeAcronym();
            } else {
                if ((Boolean) object[indexFive]) {
                    gradingAcronym = AB;
                } else {
                    if (object[indexFour] != null) {
                        float marks = Float.parseFloat(object[indexFour].toString());
                        assignmentMarkTemplate.setMarks(marks);
                    }
                }
            }
            assignmentMarkTemplate.setGradingAcronym(gradingAcronym);
            assignmentMarkTemplates.add(assignmentMarkTemplate);
        }

        SortUtil.sortStudentAssignmentMarkTemplates(assignmentMarkTemplates);
        assignmentMarksDataSource(map, assignmentMarkTemplates);
    }

    /**
     * Assignment marks data source.
     *
     * @param map the map
     * @param studentAssignmentMarks the student assignment marks
     */
    private void assignmentMarksDataSource(Map<String, Object> map,
            List<StudentAssignmentMarkTemplate> studentAssignmentMarks) {

        JRDataSource dataSourceStudentAssignmentMarks = new JRBeanCollectionDataSource(studentAssignmentMarks);
        map.put(DATA_SOURCE_STUDENT_ASSIGNMENT_MARKS, dataSourceStudentAssignmentMarks);
        map.put(ASSIGNMENT_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_ASSIGNMENT_TITLE));
        map.put(ASSIGNMENT_YEAR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_SUMMARY_YEAR));
        map.put(ASSIGNMENT_SUBJECT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_ASSIGNMENT_SUBJECT));
        map.put(ASSIGNMENT_NAME, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_ASSIGNMENT_NAME));
        map.put(ASSIGNMENT_MARKS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_SUMMARY_MARKS));
 	   map.put(NOTE_LABEL,PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
               REPORT_MAXMARK_NOTE));
    }

    /**
     * Populate student religious activities.
     *
     * @param map the map
     * @param student the student
     * @throws AkuraAppException the sMS app exception
     */
    private void populateStudentReligiousActivities(Map<String, Object> map, Student student) throws AkuraAppException {

        List<FaithLifeRating> faithLifeRatings =
                studentService.getFaithLifeRatingsListForStudent(student.getStudentId(), null);
        if (faithLifeRatings != null) {
            List<StudentFaithLifeTemplate> studentFaithLifeTemplateList = new ArrayList<StudentFaithLifeTemplate>();
            for (FaithLifeRating faithLifeRating : faithLifeRatings) {
                StudentFaithLifeTemplate faithLifeTemplate = new StudentFaithLifeTemplate();

                if (faithLifeRating.getFaithLifeComment() != null) {
                    faithLifeTemplate.setCategory(faithLifeRating.getFaithLifeComment().getFaithLifeCategory()
                            .getDescription());
                    faithLifeTemplate.setComment(faithLifeRating.getFaithLifeComment().getDescription());
                } else if (faithLifeRating.getFaithLifeDescription() != null) {
                    faithLifeTemplate.setComment(faithLifeRating.getFaithLifeDescription());
                }
                faithLifeTemplate.setGradingValue(faithLifeRating.getFaithLifeGrading().getDescription());
                faithLifeTemplate.setYear(faithLifeRating.getYear());
                studentFaithLifeTemplateList.add(faithLifeTemplate);
            }
            SortUtil.sortStudentFaithLifeTemplateList(studentFaithLifeTemplateList);
            JRDataSource datasource8 = new JRBeanCollectionDataSource(studentFaithLifeTemplateList);
            map.put(FAITHLIFE_DATASOURCE, datasource8);
            map.put(FAITH_LIFE_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_FAITH_LIFE_TITLE));
            map.put(TERM_MARKS_YEAR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_TERM_MARKS_YEAR));
            map.put(CATEGORY, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_FAITH_LIFE_CATEGORY));
            map.put(COMMENTS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_FAITH_LIFE_COMMENTS));
            map.put(GRADING, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_FAITH_LIFE_GRADING));
        }
    }

    /**
     * Populate student sports details.
     *
     * @param map the map
     * @param student the student
     * @throws AkuraAppException the sMS app exception
     */
    private void populateStudentSportsDetails(Map<String, Object> map, Student student) throws AkuraAppException {

        List<StudentSport> studentSportsList = studentService.getSportsListForStudent(student.getStudentId(), null);
        if (studentSportsList != null) {
            SortUtil.sortStudentSportList(studentSportsList);
            JRDataSource datasource7 = new JRBeanCollectionDataSource(studentSportsList);
            map.put(SPORTS_DATASOURCE, datasource7);
            map.put(SPORT_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_SPORT_TITLE));
            map.put(TERM_MARKS_YEAR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_TERM_MARKS_YEAR));
            map.put(SPORT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_SUMMARY_SPORT));
            map.put(SPORT_TEAM, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_SUMMARY_TEAM));
            map.put(SPORT_POSITION, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_SPORT_POSITION));
        }
    }

    /**
     * Populate student club society details.
     *
     * @param map the map
     * @param student the student
     * @throws AkuraAppException the sMS app exception
     */
    private void populateStudentClubSocietyDetails(Map<String, Object> map, Student student) throws AkuraAppException {

        List<StudentClubSociety> studentClubSocietieList =
                studentService.getClubSocietiesListForStudent(student.getStudentId());
        if (studentClubSocietieList != null) {
            SortUtil.sortStudentClubSocietyList(studentClubSocietieList);
            JRDataSource datasource6 = new JRBeanCollectionDataSource(studentClubSocietieList);
            map.put(COCURRICULAR_DATASOURCE, datasource6);
            map.put(CLUB_SOCIETY_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_CLUB_SOCIETY_TITLE));
            map.put(TERM_MARKS_YEAR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_TERM_MARKS_YEAR));
            map.put(CLUB_SOCIETY, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_CLUB_SOCIETY));
            map.put(POSITION, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_SPORT_POSITION));

        }
    }

    /**
     * Initializing the report.
     *
     * @param map the map
     * @throws AkuraAppException the sMS app exception
     */
    private void initializingReport(Map<String, Object> map) throws AkuraAppException {

        List<StudentTermMarkDTO> studentTermMarksList = new ArrayList<StudentTermMarkDTO>();
        JRDataSource datasource1 = new JRBeanCollectionDataSource(studentTermMarksList);
        map.put(TERMMARK_DATASOURCE, datasource1);

        List<StudentMarksTemplate> studentSubMarksList = new ArrayList<StudentMarksTemplate>();
        JRDataSource datasource2 = new JRBeanCollectionDataSource(studentSubMarksList);
        map.put(SUBTERMMARK_DATASOURCE, datasource2);

        List<StudentPrefect> studentPrefectList = new ArrayList<StudentPrefect>();
        JRDataSource datasource3 = new JRBeanCollectionDataSource(studentPrefectList);
        map.put(PREFECT_DATASOURCE, datasource3);

        List<StudentScholarship> studentScholarshipList = new ArrayList<StudentScholarship>();
        JRDataSource datasource4 = new JRBeanCollectionDataSource(studentScholarshipList);
        map.put(SCHOLARSHIP_DATASOURCE, datasource4);

        List<StudentAchievementTemplate> studentAchievementTemplates = new ArrayList<StudentAchievementTemplate>();
        JRDataSource datasource5 = new JRBeanCollectionDataSource(studentAchievementTemplates);
        map.put(ACHIEVEMENT_DATASOURCE, datasource5);

        List<StudentClubSociety> studentClubSocietieList = new ArrayList<StudentClubSociety>();
        JRDataSource datasource6 = new JRBeanCollectionDataSource(studentClubSocietieList);
        map.put(COCURRICULAR_DATASOURCE, datasource6);

        List<StudentSport> studentSportsList = new ArrayList<StudentSport>();
        JRDataSource datasource7 = new JRBeanCollectionDataSource(studentSportsList);
        map.put(SPORTS_DATASOURCE, datasource7);

        List<StudentFaithLifeTemplate> studentFaithLifeTemplateList = new ArrayList<StudentFaithLifeTemplate>();
        JRDataSource datasource8 = new JRBeanCollectionDataSource(studentFaithLifeTemplateList);
        map.put(FAITHLIFE_DATASOURCE, datasource8);

        List<StudentAssignmentMarkTemplate> assignmentMarkTemplates = new ArrayList<StudentAssignmentMarkTemplate>();
        JRDataSource dataSourceStudentAssignmentMarks = new JRBeanCollectionDataSource(assignmentMarkTemplates);
        map.put(DATA_SOURCE_STUDENT_ASSIGNMENT_MARKS, dataSourceStudentAssignmentMarks);

        List<ExamResults> examResultsList = new ArrayList<ExamResults>();
        JRDataSource datasource10 = new JRBeanCollectionDataSource(examResultsList);
        map.put(DATASOURCE10, datasource10);
        
      

    }

    /**
     * Populate student achievements.
     *
     * @param map the map
     * @param student the student
     * @throws AkuraAppException the sMS app exception
     */
    private void populateStudentAchievements(Map<String, Object> map, Student student) throws AkuraAppException {

        List<Achievement> academicAchievementsList =
                studentService.getAchievementsListForStudent(student.getStudentId(), null);
        if (academicAchievementsList != null) {
            List<StudentAchievementTemplate> studentAchievementTemplates = new ArrayList<StudentAchievementTemplate>();
            for (Achievement achievement : academicAchievementsList) {
                StudentAchievementTemplate achievementTemplate = new StudentAchievementTemplate();
                achievementTemplate.setYear(achievement.getYear());
                achievementTemplate.setAchievement(achievement.getDescription());

                String achievementCategory = AkuraWebConstant.EMPTY_STRING;
                String type = AkuraWebConstant.EMPTY_STRING;
                if (achievement.isAcademic()) {
                    achievementCategory = ACADEMIC_ACHIEVEMENT;
                } else if (achievement.getClubSociety() != null) {
                    achievementCategory = CLUB_SOCIETY_ACHIEVEMENT;
                    type = achievement.getClubSociety().getName();
                } else if (achievement.getActivity() != null) {
                    achievementCategory = EXTERNAL_ACHIEVEMENT;
                    type = achievement.getActivity();
                }
                achievementTemplate.setAchievementCategory(achievementCategory);
                achievementTemplate.setType(type);
                studentAchievementTemplates.add(achievementTemplate);
            }
            SortUtil.sortAchievementList(studentAchievementTemplates);
            JRDataSource datasource5 = new JRBeanCollectionDataSource(studentAchievementTemplates);
            map.put(ACHIEVEMENT_DATASOURCE, datasource5);
            map.put(ACHIEVEMENT_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_ACHIEVEMENT_TITLE));
            map.put(TERM_MARKS_YEAR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_TERM_MARKS_YEAR));
            map.put(ACHIEVEMENT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_ACHIEVEMENT));
            map.put(CATEGORY, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_ACHIEVEMENT_CATEGORY));
            map.put(CLUB_SOCIETY, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_ACHIEVEMENT_CLUBSOCIETY));
        }
    }

    /**
     * Populate student scholarship details.
     *
     * @param map the map
     * @param student the student
     * @throws AkuraAppException the sMS app exception
     */
    private void populateStudentScholarshipDetails(Map<String, Object> map, Student student) throws AkuraAppException {

        List<StudentScholarship> studentScholarshipList =
                studentService.getStudentScholarshipDetailsByStudentId(student.getStudentId(), 0);
        if (studentScholarshipList != null) {
            SortUtil.sortStudentScholarshipList(studentScholarshipList);
            JRDataSource datasource4 = new JRBeanCollectionDataSource(studentScholarshipList);
            map.put(SCHOLARSHIP_DATASOURCE, datasource4);
            map.put(SCHORLASHIP_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_SCHORLASHIP_TITLE));
            map.put(TERM_MARKS_YEAR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_TERM_MARKS_YEAR));
            map.put(SCHORLASHIP, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_SCHORLASHIP));

        }
    }

    /**
     * Populate student prefect details.
     *
     * @param map the map
     * @param student the student
     * @throws AkuraAppException the sMS app exception
     */
    private void populateStudentPrefectDetails(Map<String, Object> map, Student student) throws AkuraAppException {

        List<StudentPrefect> studentPrefectList =
                studentService.getStudentPrefectDetailsByStudentId(student.getStudentId(), 0);
        if (studentPrefectList != null) {
            SortUtil.sortStudentPrefectList(studentPrefectList);
            JRDataSource datasource3 = new JRBeanCollectionDataSource(studentPrefectList);
            map.put(PREFECT_DATASOURCE, datasource3);
            map.put(PREFECT_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_PREFECT_TITLE));
            map.put(PREFECT_YEAR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_SUMMARY_YEAR));
            map.put(PREFECT_TYPE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STUDENT_SUMMARY_PREFECT_TYPE));
        }
    }

    /**
     * Populate student sub term marks.
     *
     * @param map the map
     * @param student the student
     * @throws AkuraAppException the sMS app exception
     */
    private void populateStudentSubTermMarks(Map<String, Object> map, Student student) throws AkuraAppException {

        List<StudentSubTermMarkDTO> studentSubTermMarkList =
                studentService.getStudentSubTermMarks(student.getStudentId(), 0);
        List<StudentMarksTemplate> studentSubMarksList = new ArrayList<StudentMarksTemplate>();

        for (StudentSubTermMarkDTO studentSubTermMark : studentSubTermMarkList) {
            StudentMarksTemplate subMarksTemplate = new StudentMarksTemplate();
            String gradingValue = AkuraWebConstant.EMPTY_STRING;

            if (studentSubTermMark.getGradingId() != null && studentSubTermMark.getGradingId() > 0) {
                Grading grading = commonService.findGradingById(studentSubTermMark.getGradingId());
                gradingValue = grading.getGradeAcronym();
            } else {
                if (studentSubTermMark.isAbsent()) {
                    gradingValue = AB;
                } else {
                    if (studentSubTermMark.getMarks() != null) {
                        subMarksTemplate.setMarks(studentSubTermMark.getMarks());
                    }
                }
            }
            subMarksTemplate.setGradeValue(gradingValue);
            subMarksTemplate.setSubjectName(studentSubTermMark.getSubject());
            subMarksTemplate.setTerm(studentSubTermMark.getTerm());
            subMarksTemplate.setSubTerm(studentSubTermMark.getSubTerm());
            subMarksTemplate.setYear(studentSubTermMark.getYear());
            studentSubMarksList.add(subMarksTemplate);
        }

        SortUtil.sortStudentSubTermMarksTemplateList(studentSubMarksList);
        JRDataSource datasource2 = new JRBeanCollectionDataSource(studentSubMarksList);
        map.put(SUBTERMMARK_DATASOURCE, datasource2);
        map.put(SUB_TERM_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_SUB_TERM_MARKS_TITLE));
        map.put(TERM_MARKS_YEAR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_TERM_MARKS_YEAR));
        map.put(TERM_MARKS_TERM, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_TERM_MARKS_TERM));
        map.put(SUB_TERM_MARKS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_SUB_TERM_MARKS));
        map.put(TERM_MARKS_SUBJECT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_TERM_MARKS_SUBJECT));
        map.put(SUB_TERM_GRADING, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_SUMMARY_MARKS));
        map.put(NOTE_LABEL,PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_MAXMARK_NOTE));
    }

    /**
     * Populate student term marks.
     *
     * @param map the map
     * @param student the student
     * @throws AkuraAppException the sMS app exception
     */
    private void populateStudentTermMarks(Map<String, Object> map, Student student) throws AkuraAppException {

        List<StudentTermMarkDTO> studentTermMarksList =
                studentService.getStudentMarksSubjectTermByStudentIdYear(student.getStudentId(), 0);
        SortUtil.sortStudentTermMarkDTOList(studentTermMarksList);
        JRDataSource datasource1 = new JRBeanCollectionDataSource(studentTermMarksList);
        map.put(TERMMARK_DATASOURCE, datasource1);
        map.put(TERM_MARKS_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_TERM_MARKS_TITLE));
        map.put(TERM_MARKS_YEAR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_TERM_MARKS_YEAR));
        map.put(TERM_MARKS_TERM, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_TERM_MARKS_TERM));
        map.put(TERM_MARKS_SUBJECT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_TERM_MARKS_SUBJECT));
        map.put(TERM_MARKS_MARKS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SUMMARY_TERM_MARKS_MARKS));
        map.put(NOTE_LABEL,PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_MAXMARK_NOTE));
    }

    /**
     * Populate student personal details.
     *
     * @param map the map
     * @param student the student
     * @param studentPersonalDetailsList the student personal details list
     * @throws AkuraAppException the sMS app exception
     */
    private void populateStudentPersonalDetails(Map<String, Object> map, Student student,
            List<Student> studentPersonalDetailsList) throws AkuraAppException {

        Date studentDepartureDate = student.getDateOfDeparture();
        if (studentDepartureDate != null) {
            map.put(DEPARTURE_DATE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    REPORT_STUDENT_DATE_DEPARTURE));
        } else {
            map.put(DEPARTURE_DATE, AkuraWebConstant.EMPTY_STRING);
        }
        JRDataSource datasource = new JRBeanCollectionDataSource(studentPersonalDetailsList);
        map.put(MAINREPORT_DATASOURCE, datasource);
        map.put(TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_STUDENT_DETAILS_SUMMARY));
        map.put(STUDENT_PERSONAL_INFO_TITLE2, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_STUDENT_DETAILS_SUMMARY2));
        map.put(LOGO, ReportUtil.getReportLogo());
        map.put(ADMISSION_NO, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_ADMISSION_NO));
        map.put(FULL_NAME, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_FULL_NAME));
        map.put(DATE_OF_BIRTH, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_DATE_OF_BIRTH));
        map.put(ADDRESS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_ADDRESS));
        map.put(ADMISSION_DATE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_ADMINSSION_DATE));
        map.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, STYLE_PATH));

        map.put(PERSONAL_INFO_SUB, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_PERSONAL_INFO_TITLE));
        map.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_GENERATED_ON_TEXT));
        map.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        map.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        map.put(GPL, AkuraWebConstant.REPORT_GPL);

        if (student.getPhoto() != null && student.getPhoto().length > 0) {
            byte[] image = student.getPhoto();
            String imageLoadPath =
                    PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, IMAGE_FOLDER_PATH);
            imageLoadPath = imageLoadPath + student.getAdmissionNo() + FILE_EXT;
            StaticDataUtil.previewProfile(imageLoadPath, image);
            map.put(PHOTO, imageLoadPath);
        }
    }

    /**
     * Populate student Exam Marks .
     *
     * @param map the map
     * @param student the student
     * @throws AkuraAppException the sMS app exception
     */
    private void populateExamMarks(Map<String, Object> map, Student student) throws AkuraAppException {

        List<ExamResults> examResultsList = studentService.getExamResultsByStudentId(student.getStudentId());

        JRDataSource datasource10 = new JRBeanCollectionDataSource(examResultsList);
        map.put(DATASOURCE10, datasource10);

        map.put(EXAM_MARKS_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, EXAM_MARKS_TITLE_KEY));
        map.put(EXAM_SUBJECT_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, EXAM_SUB_LABEL_KEY));
        map.put(EXAM_NAME_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, EXAM_NAME_LABEL_KEY));
        map.put(YEAR_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, YEAR_LABEL_KEY));
        map.put(INDEX_NO_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, INDEX_NO_LABEL_KEY));
        map.put(MARK_GRADE_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, MARK_GRADE_LABEL_KEY));
        map.put(NOTE_LABEL,PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_MAXMARK_NOTE));
    }

    /**
     * Populate addmission nos by class.
     *
     * @param request the request
     * @return the list
     * @throws AkuraAppException the akura app exception
     */
    @RequestMapping(value = POPULATE_ADDMISSION_NOS_BY_CLASS_HTM)
    @ResponseBody
    public String populateAddmissionNosByClass(HttpServletRequest request) throws AkuraAppException {

        String classGradeId = request.getParameter(CLASS_GRADE_ID);

        List<String> addmissionList = new ArrayList<String>();

        List<StudentClassInfo> studentList =
                studentService.getStudentClassInfoOfClassByYear(Integer.parseInt(classGradeId), DateUtil.currentYear());
        
           

        SortUtil.sortStudentListByAdmissionNo(studentList);

        if (!studentList.isEmpty()) {
            for (StudentClassInfo s : studentList) {
            	String addmissionWithNameWithInitials =String.format(
            			s.getStudent().getAdmissionNo()+" "+"-"+" "+s.getStudent().getNameWtInitials()
            		           			
            			);
                addmissionList.add(addmissionWithNameWithInitials);
            }
        }

        StringBuilder addmissionBuilder = new StringBuilder();

        if (!addmissionList.isEmpty()) {

            for (String admission : addmissionList) {
                addmissionBuilder.append(admission);
                addmissionBuilder.append(AkuraWebConstant.STRING_COMMA);
            }
        }

        return addmissionBuilder.toString();
    }

    /**
     * Populate class grade list.
     *
     * @param request the request
     * @return the list
     * @throws AkuraAppException the akura app exception
     */
    @ModelAttribute(CLASS_GRADE_LIST)
    public List<ClassGrade> populateClassGradeList(HttpServletRequest request) throws AkuraAppException {

        return SortUtil.sortClassGradeList(commonService.getClassGradeList());

    }

}
