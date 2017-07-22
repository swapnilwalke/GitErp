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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.DisciplinaryActionClassWise;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.service.CommonReportingService;
import com.virtusa.akura.reporting.validator.DisciplinaryActionClassWiseValidator;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * ClassWiseStudentDisciplinaryActionController is to handle View operations for StudentClassWise Disciplinary
 * Report.
 *
 * @author Virtusa Corporation
 */
@Controller
public class ClassWiseStudentDisciplinaryActionController {

    /** Logger to log the exceptions. */
    private static final Logger LOG = Logger.getLogger(ClassWiseStudentDisciplinaryActionController.class);

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

    /** string to hold key value of InformedToParent. */
    private static final String STU_DISCIPLINE_ACT_TRENDS_PARENT_INFORMED_STATUS_LABEL_TEXT =
            "StuDisciplineActTrends_parentInformedStatusLabelText";

    /** string to hold key value of Warning. */
    private static final String STU_DISCIPLINE_ACT_TRENDS_TECHER_LABEL_TEXT =
            "StuDisciplineActTrends_teacherNameLabelText";

    /** string to hold key value of Warning. */
    private static final String STU_DISCIPLINE_ACT_TRENDS_WARNING_LABEL_TEXT =
            "StuDisciplineActTrends_warningLabelText";

    /** string to hold key value of WarningComment. */
    private static final String STU_DISCIPLINE_ACT_TRENDS_WARN_COMMENT_LABEL_TEXT =
            "StuDisciplineActTrends_warnCommentLabelText";

    /** string to hold key value of Date. */
    private static final String STU_DISCIPLINE_ACT_TRENDS_WARN_DATE_LABEL_TEXT =
            "StuDisciplineActTrends_warnDateLabelText";

    /** string to hold key value of Full Name. */
    private static final String STU_DISCIPLINE_ACT_TRENDS_FULL_NAME_LABLE_TEXT =
            "StuDisciplineActTrends_fullNameLableText";

    /** string to hold key value of AdmissionNo. */
    private static final String STU_DISCIPLINE_ACT_TRENDS_ADMISSION_NUMBER_LABLE_TEXT =
            "StuDisciplineActTrends_admissionNumberLableText";

    /** string to hold key value of Year. */
    private static final String STU_DISCIPLINE_ACT_TRENDS_YEAR_LABEL_TEXT = "StuDisciplineActTrends_yearLabelText";

    /** string to hold key value of Class. */
    private static final String STU_DISCIPLINE_ACT_TRENDS_CLASS_LABEL_TEXT = "StuDisciplineActTrends_classLabelText";

    /** string to hold key value of Class Wise Student Disciplinary Action Report. */
    private static final String STU_DISCIPLINE_ACT_TRENDS_TITLE_TEXT = "StuDisciplineActTrends_titleText";

    /** String to hold parameter parentInformedStatusLabelText. */
    private static final String PARENT_INFORMED_STATUS_LABEL_TEXT = "parentInformedStatusLabelText";

    /** String to hold parameter warningLabelText. */
    private static final String WARNING_LABEL_TEXT = "warningLabelText";

    /** String to hold parameter warningCommentLabelText. */
    private static final String WARNING_COMMENT_LABEL_TEXT = "warningCommentLabelText";

    /** String to hold parameter warnDateLabelText. */
    private static final String WARN_DATE_LABEL_TEXT = "warnDateLabelText";

    /** String to hold parameter fullNameLabelText. */
    private static final String FULL_NAME_LABEL_TEXT = "fullNameLabelText";

    /** String to hold parameter admissionNoLableText. */
    private static final String ADMISSION_NO_LABLE_TEXT = "admissionNoLableText";

    /** String to hold parameter teacherNameLableText. */
    private static final String TEACHER_NAME_LABLE_TEXT = "teacherNameLableText";

    /** String to hold parameter yearLableText. */
    private static final String YEAR_LABLE_TEXT = "yearLableText";

    /** String to hold parameter classLableText. */
    private static final String CLASS_LABLE_TEXT = "classLableText";

    /** String to hold parameter StudentDisciplinaryActionTrendsTitleText. */
    private static final String STUDENT_DISCIPLINARY_ACTION_TRENDS_TITLE_TEXT =
            "StudentDisciplinaryActionTrendsTitleText";

    /** String to hold parameter logoPath. */
    private static final String LOGO_PATH = "logoPath";

    /** key to hold message to log. */
    private static final String LOG_MESSAGE_PROCESSING_DISCIPLINARY_ACTION_CLASS_WISE_REPORT =
            "Start processing user data for Disciplinary Action Class Wise Report";

    /** key to hold string selectedValue. */
    private static final String SELECTED_VALUE = "selectedValue";

    /** key to hold string disciplinaryActionClassWise. */
    private static final String DISCIPLINARY_ACTION_CLASS_WISE = "disciplinaryActionClassWise";

    /** key to hold log message. */

    private static final String LOG_MESSAGE_CALLING_DISCIPLINARY_ACTION_CLASS_WISE_JSP =
            "Calling DisciplinaryActionClassWise.jsp to colect input data";

    /** VIEW_PAGE for holding return jsp page. */
    private static final String VIEW_PAGE = "reporting/disciplinaryActionClassWise";

    /** model attribute of gradeList. */
    private static final String MODEL_ATT_GRADCLASSELIST = "gradeList";

    /** request mapping value for view student Disciplinary action. */
    private static final String STUDENT_DISCIPLINARY_ACTION_HTM = "/StudentDisciplinaryActions.htm";

    /** request mapping value for view class list of grade. */
    private static final String GRADE_CLASS_HTM = "/populateGradeClassList.htm";

    /** model attribute of massage. */
    private static final String MODEL_ATT_MESSAGE = "message";

    /** key to hold error message when mandatory field null. */
    private static final String MANDATORY_FIELD_CAN_T_BE_NULL = "mandatory field can't be null";

    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA";

    /** attribute for holding message. */
    public static final String MESSAGE = "message";

    /** report name of jrxml report logo. */
    private static final String REPORT_NAME_STUDENT_DISCIPLINART_ACTION = "CRStudentDisciplinaryActionClassWise";

    /** key to hold string style.path. */
    private static final String STYLE_PATH = "style.path";

    /** key to hold string appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** key to hold string systemConfig. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** key to hold string styleTemplate. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** key to hold string userLogin. */
    private static final String SESSION_USER_LOGIN = "userLogin";

    /** CommonService attribute for holding commonService. */
    @Autowired
    private CommonService commonService;

    /** StaffService attribute for holding StaffService. */
    @Autowired
    private StaffService staffService;

    /** DisciplinaryActionClassWiseValidator attribute for holding disciplinaryActionClassWiseValidator. */
    @Autowired
    private DisciplinaryActionClassWiseValidator disciplinaryActionClassWiseValidator;

    /** CommonReportingService attribute for holding commonReportingService. */
    @Autowired
    private CommonReportingService commonReportingService;

    /**
     * Sets CommonReportingService object.
     *
     * @param objCommonReportingService set common service object.
     */
    public void setCommonReportingService(CommonReportingService objCommonReportingService) {

        this.commonReportingService = objCommonReportingService;
    }

    /**
     * Sets CommonService object.
     *
     * @param objCommonService set common service object.
     */
    public void setCommonService(CommonService objCommonService) {

        this.commonService = objCommonService;
    }

    /**
     * sets StaffService object.
     *
     * @param staffServiceref set staff service object.
     */

    public void setStaffService(StaffService staffServiceref) {

        this.staffService = staffServiceref;
    }

    /**
     * Sets DisciplinaryActionClassWiseValidator object.
     *
     * @param disciplinaryActionClassWiseValidatorObj the disciplinaryActionClassWiseValidator to set
     */
    public void setDisciplinaryActionClassWiseValidator(
            DisciplinaryActionClassWiseValidator disciplinaryActionClassWiseValidatorObj) {

        this.disciplinaryActionClassWiseValidator = disciplinaryActionClassWiseValidatorObj;
    }

    /**
     * Used to collect user the input data to generate the report.
     *
     * @param modelMap of type ModelMap
     * @return java.lang.String
     * @throws AkuraAppException throw exception if occur.
     */

    @RequestMapping(method = RequestMethod.GET)
    public String showDisciplinaryActionReportForm(ModelMap modelMap) throws AkuraAppException {

        LOG.info(LOG_MESSAGE_CALLING_DISCIPLINARY_ACTION_CLASS_WISE_JSP);
        DisciplinaryActionClassWise disciplinaryActionClassWise = new DisciplinaryActionClassWise();
        modelMap.addAttribute(DISCIPLINARY_ACTION_CLASS_WISE, disciplinaryActionClassWise);

        return VIEW_PAGE;
    }

    /**
     * @return the grade list.
     * @param session of type HttpSession
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(MODEL_ATT_GRADCLASSELIST)
    public List<ClassGrade> populateGradeList(HttpSession session) throws AkuraAppException {

        UserLogin userLogin = (UserLogin) session.getAttribute(SESSION_USER_LOGIN);

        if (userLogin.getUserIdentificationNo() != null && userLogin.getUserRoleId() == 2) {
            int staffId = Integer.parseInt(userLogin.getUserIdentificationNo());

            return SortUtil.sortClassGradeList(staffService.getClassListOfaClassTeacherInCurrentYrear(staffId));
        }

        return SortUtil.sortClassGradeList(commonService.getClassGradeList());
    }

    /**
     * Method is to return GradeClass list.
     *
     * @param request - HttpServletRequest
     * @param modelMap - ModelMap attribute.
     * @return list of classGrade
     * @throws AkuraAppException - Detailed exception
     */

    @RequestMapping(value = GRADE_CLASS_HTM)
    @ResponseBody
    public String populateGradeClass(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {

        StringBuilder allGradeClass = new StringBuilder();
        int gradeId = Integer.parseInt(request.getParameter(SELECTED_VALUE));
        Grade grade = commonService.findGradeById(gradeId);
        List<ClassGrade> classGrades = SortUtil.sortClassGradeList(commonService.getClassGradeListByGrade(grade));
        boolean isFirst = true;
        StringBuilder classes = new StringBuilder();

        for (ClassGrade classGrade : classGrades) {
            classes.append(classGrade.getDescription());
            classes.append("_");
            classes.append(classGrade.getClassGradeId());

            if (isFirst) {
                allGradeClass.append(classes.toString()); // no comma
                isFirst = false;
            } else {
                allGradeClass.append(","); // comma
                allGradeClass.append(classes.toString());
            }
            classes.delete(0, classes.length());
        }
        return allGradeClass.toString();
    }

    /**
     * Generate Student Class Wise Exam Absentee List Report .
     *
     * @param disciplinaryActionClassWise dto to transfer user input data to generate the report.
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param errors errors
     * @param model - a HashMap contains the student marks.
     * @return java.lang.String
     * @throws AkuraAppException - The exception details that occurred when processing.
     */

    @RequestMapping(value = STUDENT_DISCIPLINARY_ACTION_HTM, method = RequestMethod.POST)
    public String onSubmit(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(DISCIPLINARY_ACTION_CLASS_WISE) DisciplinaryActionClassWise disciplinaryActionClassWise,
            BindingResult errors, final ModelMap model) throws AkuraAppException {

        LOG.info(LOG_MESSAGE_PROCESSING_DISCIPLINARY_ACTION_CLASS_WISE_REPORT);

        // validations
        disciplinaryActionClassWiseValidator.validate(disciplinaryActionClassWise, errors);

        if (errors.hasErrors()) {
            return VIEW_PAGE;
        } else {
            if (disciplinaryActionClassWise.getGradeDescription().equals("0")) {
                model.addAttribute(MODEL_ATT_MESSAGE, MANDATORY_FIELD_CAN_T_BE_NULL);
                return VIEW_PAGE;
            }

            int classGradeId = Integer.parseInt(disciplinaryActionClassWise.getGradeDescription());
            ClassGrade classGradeObj = commonService.findClassGrade(classGradeId);
            String classGradeDescription = classGradeObj.getDescription();

            Map<String, Object> params = new HashMap<String, Object>();
            params.put(LOGO_PATH, ReportUtil.getReportLogo());
            params.put(STUDENT_DISCIPLINARY_ACTION_TRENDS_TITLE_TEXT, PropertyReader.getPropertyValue(
                    ReportUtil.REPORT_TEMPLATE, STU_DISCIPLINE_ACT_TRENDS_TITLE_TEXT));
            params.put(CLASS_LABLE_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STU_DISCIPLINE_ACT_TRENDS_CLASS_LABEL_TEXT));
            params.put(YEAR_LABLE_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STU_DISCIPLINE_ACT_TRENDS_YEAR_LABEL_TEXT));
            params.put(ADMISSION_NO_LABLE_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STU_DISCIPLINE_ACT_TRENDS_ADMISSION_NUMBER_LABLE_TEXT));
            params.put(FULL_NAME_LABEL_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STU_DISCIPLINE_ACT_TRENDS_FULL_NAME_LABLE_TEXT));
            params.put(WARN_DATE_LABEL_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STU_DISCIPLINE_ACT_TRENDS_WARN_DATE_LABEL_TEXT));
            params.put(WARNING_COMMENT_LABEL_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STU_DISCIPLINE_ACT_TRENDS_WARN_COMMENT_LABEL_TEXT));
            params.put(WARNING_LABEL_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STU_DISCIPLINE_ACT_TRENDS_WARNING_LABEL_TEXT));
            params.put(TEACHER_NAME_LABLE_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STU_DISCIPLINE_ACT_TRENDS_TECHER_LABEL_TEXT));
            params.put(PARENT_INFORMED_STATUS_LABEL_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    STU_DISCIPLINE_ACT_TRENDS_PARENT_INFORMED_STATUS_LABEL_TEXT));

            params.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                    + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
            params.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    REPORT_GENERATED_ON_TEXT));
            params.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                    ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
            params.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
            params.put(GPL, AkuraWebConstant.REPORT_GPL);

            JRBeanCollectionDataSource studentDisciplinaryActionSummary =
                    commonReportingService.getStudentDisciplinaryActionsTrends(classGradeDescription);

            if (studentDisciplinaryActionSummary.getRecordCount() != 0) {
                ReportUtil.displayReportInPdfForm(response, studentDisciplinaryActionSummary, params,
                        REPORT_NAME_STUDENT_DISCIPLINART_ACTION);
                return null;
            } else {
                setErrorMessage(model);
                return VIEW_PAGE;
            }
        }
    }

    /**
     * set the error messages when returning to the jsp.
     *
     * @param model - ModelMap
     */
    private void setErrorMessage(ModelMap model) {

        String message = new ErrorMsgLoader().getErrorMessage(NO_DATA_ERROR_MESSAGE);
        model.addAttribute(MESSAGE, message);
    }
}
