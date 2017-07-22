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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.CRAverageTestMarkTemplate;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.ReportAverageTestMark;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.validator.CRAverageTermMarkClassWiseValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * Generate average term test marks for a given subject(s) for a grade (class wise).
 *
 * @author Virtusa Corporation
 */
@Controller
@SessionAttributes("cRAverageTestMarkTemplate")
public class CRAverageTermMarkClassWiseController {

    /** The Constant model attribute. */
    private static final String MODEL_ATT_SELECTED_GRADE_ID = "selectedGradeId";

    /** Logger to log the exceptions. */
    private static final Logger LOG = Logger.getLogger(CRAverageTermMarkClassWiseController.class);

    /** Represents the error message for marking is not completed. */
    private static final String TERM_MARK_ENTRY_COMPLETION = "REPORT.PRIZE.MARKING.NOT.COMPLETED";

    /** string attribute for holding undefined message. */
    private static final String UNDEFINED = "undefined";

    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REPORT.UI.NO.DATA";

    /** request mapping value for save or update date. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/AverageTermMarkClassWise.htm";

    /** model attribute for template. */
    private static final String MODEL_ATT_AVERAGE_TEST_MARK_TEMPLATE = "cRAverageTestMarkTemplate";

    /** key to hold error message when mandatory field null. */
    private static final String MANDATORY_FIELD_CAN_T_BE_NULL = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** key to hold string selectedValue. */
    private static final String SELECTED_VALUE = "selectedValue";

    /** request mapping value for view class list of grade. */
    private static final String GRADE_CLASS_HTM = "/populateGradeSubjectList.htm";

    /** attribute for holding message. */
    public static final String MESSAGE = "message";

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

    /** String to hold parameter selectClasses. */
    private static final String SELECT_SUBJECTS = "selectSubjects";

    /** Represents the path of the default image. */
    private static final String LOGO_PATH = "logoPath";

    /** Represents the key for the grade. */
    private static final String GRADE_TEXT = "gradeText";

    /** Represents the value of the grade. */
    private static final String GRADE_LABLE = "AverageTermMarkClassWise_gradeLableText";

    /** Represents the key for the label of the grade. */
    private static final String MODEL_GRADE = "gradeLableText";

    /** Represents the value of the title. */
    private static final String TITLE = "AverageTermMarkClassWise_titleText";

    /** Represents the label of the title. */
    private static final String MODEL_TITLE = "AverageTermMarkClassWiseTitleText";

    /** Represents the default image path. */
    private static final String DEFAULT_PATH = "/resources/images/logo_large.jpg";

    /** Represents the name of the ModelAndView. */
    private static final String MODEL_NAME = "averageTermMarks";

    /** Represents the name of the no date label. */
    private static final String NODATA = "AverageTermMarkClassWise_noDataLableText";

    /** Represents the name of the no date report parameter. */
    private static final String NODATALABEL = "noData";

    /** Represents the key for the data source. */
    private static final String DATASOURCE = "datasource";

    /** Represents an instance of ServletContext. */
    private ServletContext context;

    /** CommonService attribute for holding commonService. */
    @Autowired
    private CommonService commonService;

    /** StudentService attribute for holding studentService. */
    @Autowired
    private StudentService studentService;

    /**
     * Gets the student Service.
     *
     * @return the student service.
     */
    public StudentService getStudentService() {

        return studentService;
    }

    /**
     * Sets the student service.
     *
     * @param service the student service.
     */
    public void setStudentService(StudentService service) {

        this.studentService = service;
    }

    /**
     * CRAverageTermMarkClassWiseValidator attribute for holding cRAverageTermMarkClassWiseValidator.
     */
    private CRAverageTermMarkClassWiseValidator cRAverageTermMarkClassWiseValidator;

    /** model attribute of gradeList. */
    private static final String MODEL_ATT_GRADELIST = "gradeList";

    /** model attribute of classList. */
    private static final String MODEL_ATT_CLASSLIST = "classList";

    /** model attribute of subjectList. */
    private static final String MODEL_ATT_SUBJECTLIST = "subjectList";

    /** model attribute of termList. */
    private static final String MODEL_ATT_TERMLIST = "termList";

    /** Represents the key for the term description. */
    private static final String TERM_KEY = "term";

    /** Represents the value for the term label key. */
    private static final String TERM_VALUE = "AverageTermMarkClassWise_termDescriptionLableText";

    /** Represents the key for the term label. */
    private static final String TERM_LABLE = "termLable";

    /** Represents the key for the path of the style template of the sub reports. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** Represents the key for the style path. */
    private static final String STYLE_PATH = "style.path";

    /**
     * A constant serves as a key for storing path to email properties file.
     */
    public static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** VIEW_PAGE for holding return jsp page. */
    private static final String VIEW_PAGE = "reporting/averageTermTestMarkClassWise";

    /**
     * Sets the CRAverageTermMarkClassWiseValidator object.
     *
     * @param cRAverageTermMarkClassWiseValidatorRef set CRAverageTermMarkClassWiseValidator object.
     */
    public void setcRAverageTermMarkClassWiseValidator(
            CRAverageTermMarkClassWiseValidator cRAverageTermMarkClassWiseValidatorRef) {

        this.cRAverageTermMarkClassWiseValidator = cRAverageTermMarkClassWiseValidatorRef;
    }

    /**
     * Set CommonService object.
     *
     * @param objCommonService set common service object.
     */
    public void setCommonService(CommonService objCommonService) {

        this.commonService = objCommonService;
    }

    /**
     * Used to collect user the input data to generate the report.
     *
     * @param modelMap of type ModelMap
     * @return java.lang.String
     * @throws AkuraAppException throw exception if occur.
     */

    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(ModelMap modelMap) throws AkuraAppException {

        LOG.info("Calling AverageTermTestMarkClassWise.jsp to colect input data");
        CRAverageTestMarkTemplate cRAverageTestMarkTemplate = new CRAverageTestMarkTemplate();
        modelMap.addAttribute(MODEL_ATT_AVERAGE_TEST_MARK_TEMPLATE, cRAverageTestMarkTemplate);

        return VIEW_PAGE;
    }

    /**
     * @return the grade list.
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(MODEL_ATT_GRADELIST)
    public List<Grade> populateGradeList() throws AkuraAppException {

        return SortUtil.sortGradeList(commonService.getGradeList());
    }

    /**
     * @return the class list
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(MODEL_ATT_CLASSLIST)
    public List<SchoolClass> populateClassList() throws AkuraAppException {

        return commonService.getClassList();
    }

    /**
     * @return the subject list
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(MODEL_ATT_SUBJECTLIST)
    public List<GradeSubject> populateSubjectList() throws AkuraAppException {

        return commonService.getGradeSubjectList();
    }

    /**
     * @return the term list
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(MODEL_ATT_TERMLIST)
    public List<Term> populateTermList() throws AkuraAppException {

        return commonService.getTermList();
    }

    /**
     * @return the grade subject list
     * @param request of type HttpServletRequest
     * @throws AkuraAppException AkuraAppException
     */
    @RequestMapping(value = GRADE_CLASS_HTM)
    @ResponseBody
    public String populateGradeSubject(HttpServletRequest request) throws AkuraAppException {

        StringBuilder allGradeSubject = new StringBuilder();
        int gradeId = Integer.parseInt(request.getParameter(SELECTED_VALUE));

        List<GradeSubject> subjectGrades = commonService.getGradeSubjectIdListByGrade(gradeId);

        boolean isFirst = true;

        StringBuilder subjects = new StringBuilder();

        for (GradeSubject subjectGrade : subjectGrades) {
            subjects.append(subjectGrade.getSubject().getDescription());
            subjects.append(AkuraWebConstant.UNDERSCORE_STRING);
            subjects.append(subjectGrade.getGradeSubjectId());

            if (isFirst) {
                allGradeSubject.append(subjects.toString()); // no comma
                isFirst = false;
            } else {
                allGradeSubject.append(AkuraWebConstant.STRING_COMMA); // comma
                allGradeSubject.append(subjects.toString());
            }
            subjects.delete(0, subjects.length());
        }
        return allGradeSubject.toString();
    }

    /**
     * Generate Student Class Wise Exam Absentee List Report .
     *
     * @param cRAverageTestMarkTemplate dto to transfer user input data to generate the report.
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param errors errors
     * @param model - a HashMap contains the student marks.
     * @return java.lang.String
     * @throws AkuraAppException - The exception details that occurred when processing.
     */

    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(MODEL_ATT_AVERAGE_TEST_MARK_TEMPLATE) CRAverageTestMarkTemplate cRAverageTestMarkTemplate,
            BindingResult errors, final ModelMap model) throws AkuraAppException {

        LOG.info("Start processing user data for Average Term Test Mark Class Wise Report");
        cRAverageTermMarkClassWiseValidator.validate(cRAverageTestMarkTemplate, errors);
        ModelAndView view = null;
        if (errors.hasErrors()) {
            return new ModelAndView(VIEW_PAGE);
        } else {

            String[] toList = null;

            if (request.getParameter(SELECT_SUBJECTS) == null
                    || request.getParameter(SELECT_SUBJECTS).equals(UNDEFINED)) {

                String message = new ErrorMsgLoader().getErrorMessage(MANDATORY_FIELD_CAN_T_BE_NULL);
                model.addAttribute(MODEL_ATT_SELECTED_GRADE_ID, cRAverageTestMarkTemplate.getGradeDescription());
                model.addAttribute(MESSAGE, message);
                return new ModelAndView(VIEW_PAGE);
            }

            Map<String, Object> params = new HashMap<String, Object>();
            context = request.getSession().getServletContext();

            int gradeId = Integer.parseInt(cRAverageTestMarkTemplate.getGradeDescription());

            if (request.getParameterValues(SELECT_SUBJECTS) != null) {
                toList = request.getParameterValues(SELECT_SUBJECTS);
            }

            final List<Integer> gradeSubjectList = new ArrayList<Integer>();
            if (toList != null && toList.length > 0) {
                for (String z : toList) {
                    int id = Integer.parseInt(z);
                    gradeSubjectList.add(id);
                }
            }
            String term = cRAverageTestMarkTemplate.getTermDescription();

            Term termObj = commonService.findTermById(Integer.parseInt(term));
            String termDes = termObj.getDescription();

            final List<ReportAverageTestMark> averageTermMarksList = new ArrayList<ReportAverageTestMark>();
            Grade grade = commonService.findGradeById(gradeId);

            List<ClassGrade> list = new ArrayList<ClassGrade>(grade.getClassGrades());
            List<ClassGrade> classGradeList = SortUtil.sortClassGradeList(list);

            boolean markCompletion = getResults(term, gradeSubjectList, averageTermMarksList, classGradeList);

            // if edit mark status is not checked for the relevant grade then does not display the report.
            if (!markCompletion) {
                String message = new ErrorMsgLoader().getErrorMessage(TERM_MARK_ENTRY_COMPLETION);
                view = setDataWhenError(cRAverageTestMarkTemplate, model, message);
            } else {

                int increment = 0;

                for (ReportAverageTestMark avgMarks : averageTermMarksList) {
                    if (avgMarks.getAverage() == 0.0) {
                        increment++;
                    }
                }

                if (increment == averageTermMarksList.size()) {
                    String message = new ErrorMsgLoader().getErrorMessage(NO_DATA_ERROR_MESSAGE);
                    view = setDataWhenError(cRAverageTestMarkTemplate, model, message);
                } else {
                    JRDataSource datasource = new JRBeanCollectionDataSource(averageTermMarksList);

                    String logo = getImagePath();
                    getDataSource(cRAverageTestMarkTemplate, params, logo, datasource, termDes, grade.getDescription());
                    view = new ModelAndView(MODEL_NAME, params);
                }

            }
            return view;
        }
    }

    /**
     * Returns the model and view object.
     *
     * @param cRAverageTestMarkTemplate - an instance of CRAverageTestMarkTemplate.
     * @param model - model object.
     * @param message - error message.
     * @return model and view.
     */
    private ModelAndView setDataWhenError(CRAverageTestMarkTemplate cRAverageTestMarkTemplate, final ModelMap model,
            String message) {

        model.addAttribute(MODEL_ATT_SELECTED_GRADE_ID, cRAverageTestMarkTemplate.getGradeDescription());
        model.addAttribute(MESSAGE, message);
        return new ModelAndView(VIEW_PAGE);
    }

    /**
     * Returns the data source.
     *
     * @param cRAverageTestMarkTemplate - an instance of CRAverageTestMarkTemplate.
     * @param params - a HashMap contains the data of the source.
     * @param logo - the path of the default image.
     * @param datasource - the name of the data source.
     * @param term - term of the year.
     * @param gradeDes - gradeDes of the grade.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void getDataSource(final CRAverageTestMarkTemplate cRAverageTestMarkTemplate,
            final Map<String, Object> params, final String logo, final JRDataSource datasource, String term,
            String gradeDes) throws AkuraAppException {

        params.put(TERM_LABLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, TERM_VALUE));
        params.put(TERM_KEY, term);
        params.put(MODEL_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, TITLE));
        params.put(MODEL_GRADE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, GRADE_LABLE));
        params.put(GRADE_TEXT, gradeDes);
        params.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, STYLE_PATH));
        params.put(DATASOURCE, datasource);
        params.put(LOGO_PATH, logo);
        params.put(NODATALABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, NODATA));
        params.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_GENERATED_ON_TEXT));
        params.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        params.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        params.put(GPL, AkuraWebConstant.REPORT_GPL);
    }

    /**
     * Retrieves the result set for the grade.
     *
     * @param term - description of the term.
     * @param gradeSubjectList - the list of the GradeSubject.
     * @param averageTermMarksList - the list of the ReportAverageTestMark.
     * @param classGradeList - the list of ClassGrades.
     * @return - the status of the edit mark completion.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private boolean getResults(final String term, final List<Integer> gradeSubjectList,
            final List<ReportAverageTestMark> averageTermMarksList, List<ClassGrade> classGradeList)
            throws AkuraAppException {

        boolean markingStatus = true;

        if (classGradeList != null) {
            classGradeList: for (ClassGrade classGrade : classGradeList) {

                Date currentYearOnly = DateUtil.currentYear();

                // access the student class info keys.
                List<Integer> studentClassInfoIdList =
                        studentService.getStudentClassInfoIdByYear(classGrade.getClassGradeId(), currentYearOnly);

                // finds the edit mark status for the relevant class info keys.
                markingStatus =
                        studentService.findMarkingStatusForGrade(classGrade.getGrade().getGradeId(), term,
                                currentYearOnly);

                if (markingStatus) {

                    // gets the grade subjects related to the class.
                    for (Integer gradeSubjectId : gradeSubjectList) {
                        ReportAverageTestMark averageMarks = new ReportAverageTestMark();

                        int studentCount = 0; // the number of student of the class
                        // for the subject.
                        float totalMarks = 0F; // the total marks of the subject.
                        // gets the name of the class.
                        averageMarks.setClassDescription(classGrade.getSchoolClass().getDescription());
                        GradeSubject gradeSubject = commonService.findGradeSubject(gradeSubjectId);
                        averageMarks.setSubjectDescription(gradeSubject.getSubject().getDescription());

                        // accesses the student class information for the class for
                        // a given grade.
                        for (Integer studentClassInfoId : studentClassInfoIdList) {
                            float marks =
                                    studentService.getStuTermMarks(studentClassInfoId,
                                            gradeSubject.getGradeSubjectId(), Integer.parseInt(term));

                            totalMarks = totalMarks + marks;
                            // count students.
                            if (marks != 0F) {
                                studentCount = studentCount + 1;
                            }

                        }
                        double average = totalMarks / studentCount;

                        // check NnN for average value (totalMarks / studentCount --> infinit )
                        if (totalMarks == 0.0 && studentCount == 0) {
                            average = 0.0;
                        }
                        averageMarks.setAverage(average);
                        averageTermMarksList.add(averageMarks);
                    }
                } else {
                    markingStatus = false;
                    break classGradeList;
                }
            }
        }
        return markingStatus;
    }

    /**
     * Returns the absolute path of the logo.
     *
     * @return - the absolute path of the image
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    public String getImagePath() throws AkuraAppException {

        return context.getRealPath(DEFAULT_PATH);
    }
}
