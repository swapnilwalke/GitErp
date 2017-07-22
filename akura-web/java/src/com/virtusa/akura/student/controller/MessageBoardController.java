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

package com.virtusa.akura.student.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.virtusa.akura.api.dto.AssignmentMarkView;
import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.dto.FaithLifeRating;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.dto.StudentMarksTemplate;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.dto.StudentSubTermMarkDTO;
import com.virtusa.akura.api.dto.StudentSubjectAverageViewDTO;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.dto.StudentTermMarksRank;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.EmailService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.student.service.ParentService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.StaticDataUtil;
import com.virtusa.akura.util.email.EmailUtil;

/**
 * The MessageBoardController is to manage student Message Board tab functionalities.
 *
 * @author Virtusa Corporation
 */
@Controller
public class MessageBoardController {

    /** Logger to log the exceptions. */
    private static final Logger LOG = Logger.getLogger(MessageBoardController.class);

    /** Represents the text for the subjectAverageMarks. */
    private static final String MARKS_COMPARISON_TEXT = "Subject Marks Comparison With Class.";

    /** Represents the name for the page. */
    private static final String PAGE = "Page ";

    /** Represents the label for subject average title. */
    private static final String SUBJECT_A_TITLE_MAIN = "subjectAverageTitle";

    /** Represents the text for subject. */
    private static final String SUBJECT_TEXT = "Subject";

    /** Represents the label for subject. */
    private static final String SUBJECT_LABLE_MAIN = "subject";

    /** Represents the text for label 'academicReport'. */
    private static final String ACADEMIC_PROGRESS_TEXT = "Academic progress report";

    /** Represents the label for 'academicReport'. */
    private static final String ACADEMIC_LABLE_MAIN = "academicReport";

    /** Represents the text for label, 'gradeAverage'. */
    private static final String GRADE_AVERAGE_TEXT = "Grade Average";

    /** Represents the label for 'gradeAverage'. */
    private static final String GRADE_AVERAGE_LABLE_MAIN = "gradeAverage";

    /** Represents the text for 'classAverage'. */
    private static final String CLASS_AVERAGE_TEXT = "Class Average";

    /** Represents the text for 'classAverage'. */
    private static final String CLASS_AVERAGE_LABLE_MAIN = "classAverage";

    /** Represents the text for 'classAverage'. */
    private static final String MARKS_TEXT = "Marks/Grade";

    /** Represents the text for 'classAverage'. */
    private static final String MARKS_LABLE_MAIN = "marks";

    /** Represents the decimal number. */
    private static final int DECIMAL = 10;

    /** The Constant COM_SMS_PATH_IMAGE. */
    private static final String COM_SMS_PATH_IMAGE = "large.logo.image.path";

    /** attribute for holding `JasperCustomSubReportDatasource3`. */
    private static final String JASPER_CUSTOM_SUB_REPORT_DATASOURCE3 = "JasperCustomSubReportDatasource3";

    /** attribute for holding `JasperCustomSubReportDatasource1`. */
    private static final String JASPER_CUSTOM_SUB_REPORT_DATASOURCE1 = "JasperCustomSubReportDatasource1";

    /** attribute for holding `JasperCustomSubReportDatasource4`. */
    private static final String JASPER_CUSTOM_SUB_REPORT_DATASOURCE4 = "JasperCustomSubReportDatasource4";

    /** Represents the log message for the date conversion error. */
    private static final String ERROR_DATE_CONVERSION = "Error Date Conversion";

    /** Represents the key for the path of the style template. */
    private static final String STYLE = "styleTemplate";

    /** Represents the key for the path of the style template of the sub reports. */
    private static final String STYLE_TEMPLATE = "STYLE_TEMPLATE";

    /** Represents the key for the style path. */
    private static final String STYLE_PATH = "style.path";

    /** Represents the log message for the request. */
    private static final String RECEIVED_REQUEST_LOG =
            "Received request to get a summary" + "for the progress of the student";

    /** attribute for holding `studentName`. */
    private static final String STUDENT_NAME = "studentName";

    /** attribute for holding `comment`. */
    private static final String COMMENT = "comment";

    /** attribute for holding `generator`. */
    private static final String GENERATOR = "generator";

    /** attribute for holding `logo`. */
    private static final String LOGO = "logo";

    /** Represents the key for the class. */
    private static final String CLASS = "class";

    /** Represents the value for year. */
    private static final String YEAR = "year";

    /** Represents the default format of the date. */
    private static final String DEFAULT_DATE = "yyyy-MM-dd";

    /** Represents the date. */
    private static final String DATE = "-01-01";

    /** attribute for holding `data source`. */
    private static final String DATASOURCE = "datasource";

    /** attribute for holding request value `studentProgressPdf.htm`. */
    private static final String REQUEST_STUDENT_PROGRESS_PDF = "/studentProgressPdf.htm";

    /** attribute for holding request value `EmailProgressSummary.htm`. */
    private static final String REQUEST_EMAIL_PROGRESS_SUMMARY = "/EmailProgressSummary.htm";

    /** constant for holding log massage `File Not Found error`. */
    private static final String LOG_FILE_NOT_FOUND_ERROR = "File Not Found error";

    /** constant for holding log massage `report generation error`. */
    private static final String LOG_JASPERREPORT_GENERATION_ERROR = "Jasperreport generation error";

    /** constant for holding StudentProgressReport.jrxml resource location. */
    private static final String RESOURCES_JRXML_STUDENT_PROGRESS_REPORT_JRXML =
            "/resources/jrxml/StudentProgressReport.jrxml";

    /** constant for holding SubTermMarksReport.jrxml resource location. */
    private static final String RESOURCES_JRXML_SUB_TERM_MARKS_REPORT_JRXML =
            "/resources/jrxml/SubTermMarksReport.jrxml";

    /** constant for holding StudentAttendance.jrxml resource location. */
    private static final String RESOURCES_JRXML_ATTENDANCE_REPORT_JRXML = "/resources/jrxml/StudentAttendance.jrxml";

    /** constant for holding FaithLifeRating.jrxml resource location. */
    private static final String RESOURCES_JRXML_FAITH_LIFE_RATING_JRXML = "/resources/jrxml/FaithLifeRating.jrxml";

    /** attribute for holding view name `pdfReport`. */
    private static final String PDF_REPORT = "pdfReport";

    /** attribute for holding view name `emptyReport`. */
    private static final String EMPTY_REPORT = "student/emptyReport";

    /** request attribute of comments. */
    private static final String COMMENTS = "comments";

    /** constant for holding `/FaithLifeRating.pdf`. */
    private static final String FAITH_LIFE_RATING_PDF = "/FaithLifeRating.pdf";

    /** Represents the name of the pdf for `/Attendance.pdf`. */
    private static final String ATTENDANCE_PDF = "/Attendance.pdf";

    /** constant for holding `/studentSubMarksList.pdf`. */
    private static final String STUDENT_SUB_MARKS_LIST_PDF = "/studentSubMarksList.pdf";

    /** constant for holding `/mainReport.pdf`. */
    private static final String MAIN_REPORT_PDF = "/mainReport.pdf";

    /** constant for holding `Progress Report is attached.`. */
    private static final String PROGRESS_REPORT_IS_ATTACHED = "Progress Report is attached.";

    /** constant for holding `ProgressReport.pdf`. */
    private static final String PROGRESS_REPORT_PDF = "ProgressReport.pdf";

    /** constant for holding `Student Progress Report`. */
    private static final String STUDENT_PROGRESS_REPORT = "Student Progress Report";

    /** attribute for holding error key REF.EMAIL.REPORT.PATH. */
    private static final String REF_EMAIL_REPORT_PATH = "REF.EMAIL.REPORT.PATH";

    /** attribute for holding error key PAR_UI_USER_EMAIL_REQUIRED. */
    private static final String PAR_UI_USER_EMAIL_REQUIRED = "PAR.UI.USER.EMAIL.REQUIRED";

    /** attribute for holding error key. */
    private static final String EMAIL_SEND_ERROR = "EMAIL.SEND.ERROR";

    /** model attribute of user. */
    private static final String USER = "user";

    /** key to define the image folder path. */
    private static final String IMAGE_FOLDER_PATH = "imageFolder.path";

    /** key to define the image file format. */
    private static final String FILE_EXT = ".jpg";

    /** The Constant AB. */
    private static final String AB = "AB";

    /** The Constant PHOTO. */
    private static final String PHOTO = "photo";

    /** attribute for holding error key PAR.UI.PARENT.EMAIL.REQUIRED. */
    private static final String PAR_UI_PARENT_EMAIL_REQUIRED = "PAR.UI.PARENT.EMAIL.REQUIRED";

    /** model attribute of studentId. */
    private static final String STUDENT_ID = "studentId";

    /** view get method Studen_progress_report_success. */
    private static final String STUDENT_STUDEN_PROGRESS_REPORT_SUCCESS = "student/studenProgressSuccess";

    /** view get method studentProgressReport. */
    private static final String STUDENT_STUDENT_PROGRESS_REPORT = "student/studentProgressReport";

    /** model attribute of message. */
    private static final String MESSAGE = "message";

    /** attribute for holding error key PAR.UI.GENERATE.REPORT.INCOMPLETE. */
    private static final String PAR_UI_GENERATE_REPORT_INCOMPLETE = "PAR.UI.GENERATE.REPORT.INCOMPLETE";

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /**
     * A constant serves as a key for storing Template path for email templates.
     */
    public static final String TEMPLATE_PATH = "email.templates.path";

    /** A constant serves as a key for storing path to email properties file. */
    public static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";

    /** A constant serves as a key for storing path to email properties file. */
    public static final String EMAIL_PROPERTIES = "email";

    /** The constant for hold view attendance velocity file name. */
    public static final String VELOCITY_ATTENDANCE_TO_PARENT = "email.template.attendancetoparent";

    /** Represents the data source name for the average for the term. */
    private static final String JASPER_CUSTOM_SUB_REPORT_DATASOURCE5 = "JasperCustomSubReportDatasource5";

    /** Represents the data source name for the subject average for the term. */
    private static final String JASPER_CUSTOM_SUB_REPORT_DATASOURCE6 = "JasperCustomSubReportDatasource6";

    /** Represents the data source name for the average for the term. */
    private static final String STUDENT_TERM_AVERAGE = "/StudentTermAverage.pdf";

    /** Represents the path for the AverageTermMarksReport.jrxml . */
    private static final String RESOURCES_JRXML_SUB_TERM_AVERAGE_JRXML =
            "/resources/jrxml/AverageTermMarksReport.jrxml";

    /** constant for holding `SubjectAverage.pdf`. */
    private static final String STUDENT_SUBJECT_AVERAGE = "/SubjectAverage.pdf";

    /** Represents the data source name for the rank for the terms. */
    private static final String JASPER_CUSTOM_SUB_REPORT_DATASOURCE7 = "JasperCustomSubReportDatasource7";

    /** Represents the data source name for the Assignment marks. */
    private static final String JASPER_CUSTOM_SUB_REPORT_DATASOURCE8 = "JasperCustomSubReportDatasource8";

    /** constant for holding `StudentTermRank.pdf`. */
    private static final String STUDENT_TERM_RANK = "/StudentTermRank.pdf";

    /** constant for holding 'StudentAssignmentMarks.pdf'. */
    private static final String STUDENT_ASSIGNMENT_MARKS = "/StudentAssignmentMarks.pdf";

    /** Represents the mapping for the sutdentProgressReport. */
    private static final String STUDENT_PROGRESS_MAPPING = "/studentProgressReport.htm";

    /** Represents the path for the StudentTermRank.jrxml . */
    private static final String RESOURCES_JRXML_MARKS_RANK_JRXML = "/resources/jrxml/StudentTermRank.jrxml";

    /** Represents the path for the StudentAssignmentMarks.jrxml . */
    private static final String RESOURCES_JRXML_STUDENT_ASSIGNMENT_MARKS =
            "/resources/jrxml/AssignmentMarksReport.jrxml";

    /** Represents the path for the SubjectAverageComparison.jrxml . */
    private static final String RESOURCES_JRXML_SUBJECT_AVERAGE_JRXML =
            "/resources/jrxml/SubjectAverageComparison.jrxml";

    /** emailService attribute for holding EmailService. */
    private EmailService emailService;

    /** Represents the generateReport complete. */
    private boolean generateReport = false;

    /** The Constant GPL. */
    private static final String GPL = "GPLComment";

    /** parentService attribute for holding ParentService. */
    private ParentService parentService;

    /** Represents the To Address. */
    private String strToAddress;

    /** Represents the From Address. */
    private String strFromAddress;

    /** userService attribute for holding UserService. */
    private UserService userService;

    /**
     * Represents an instance of ServletContext.
     */
    private ServletContext context;

    /**
     * Represents an instance of StaffCategoryValidator.
     */
    private StudentService studentService;

    /**
     * Represents an instance of commonService.
     */
    private CommonService commonService;

    /**
     * Set the service instance to inject the service.
     *
     * @param userServiceRef the UserService to set
     */

    public void setUserService(UserService userServiceRef) {

        this.userService = userServiceRef;
    }

    /**
     * Set the service instance to inject the service.
     *
     * @param parentServiceRef the ParentService to set
     */
    public void setParentService(ParentService parentServiceRef) {

        this.parentService = parentServiceRef;
    }

    /**
     * Set the service instance to inject the service.
     *
     * @param emailServiceValue the EmailService to set
     */
    public void setEmailService(EmailService emailServiceValue) {

        this.emailService = emailServiceValue;
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
     * Sets an instance of StudentService.
     *
     * @param studentServiceVal - the relevant instance of StudentService
     */
    public void setStudentService(StudentService studentServiceVal) {

        studentService = studentServiceVal;
    }

    /**
     * Handle GET requests for Send Email view.
     *
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @param modelAndView - the name of the view
     * @return the name of the view
     * @throws AkuraAppException - The exception details that occurred when processing
     */

    @RequestMapping(REQUEST_EMAIL_PROGRESS_SUMMARY)
    public ModelAndView setEmailReport(HttpServletRequest request, HttpSession session, ModelMap model,
            ModelAndView modelAndView) throws AkuraAppException {

        if (!generateReport) {
            return populateErrorMessage(model, PAR_UI_GENERATE_REPORT_INCOMPLETE, request);
        }

        Student student = null;
        boolean gotParentEmail = false;

        if (session.getAttribute(STUDENT_ID) != null) {
            int studentId = Integer.parseInt(session.getAttribute(STUDENT_ID).toString());
            student = studentService.findStudent(studentId);

            gotParentEmail = getStudentParentEmailAddress(gotParentEmail, studentId);

            if (!gotParentEmail) {
                return populateErrorMessage(model, PAR_UI_PARENT_EMAIL_REQUIRED, request);
            }
        }

        if (session.getAttribute(USER) != null) {

            getLoginUserEmailAddress(session);

            if (strFromAddress == null) {
                return populateErrorMessage(model, PAR_UI_USER_EMAIL_REQUIRED, request);
            }
        }

        String path =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, new ErrorMsgLoader()
                                .getErrorMessage(REF_EMAIL_REPORT_PATH));
        try {
            path = setPropertyValuesToCommonEmailBean(student);
        } catch (AkuraAppException e) {
            return populateErrorMessage(model, EMAIL_SEND_ERROR, request);
        } finally {
            // delete files
            deleteFiles(student, path);
        }

        return new ModelAndView(STUDENT_STUDEN_PROGRESS_REPORT_SUCCESS, model);
    }

    /**
     * Delete MessageBoard Related files.
     *
     * @param student - {@link Student}
     * @param path - holds string
     */
    private void deleteFiles(Student student, String path) {

        String fileName = path + MAIN_REPORT_PDF;
        String fileName1 = path + "/" + student.getNameWtInitials() + PROGRESS_REPORT_PDF;
        String fileName2 = path + STUDENT_SUB_MARKS_LIST_PDF;
        String fileName4 = path + FAITH_LIFE_RATING_PDF;
        String fileName3 = path + ATTENDANCE_PDF;
        String fileName7 = path + STUDENT_SUBJECT_AVERAGE;
        String fileName5 = path + STUDENT_ASSIGNMENT_MARKS;
        String fileName6 = path + STUDENT_TERM_AVERAGE;
        File f1 = new File(fileName);
        File f2 = new File(fileName1);
        File f3 = new File(fileName2);
        File f4 = new File(fileName4);
        File f5 = new File(fileName3);
        File f6 = new File(fileName5);
        File f7 = new File(fileName6);
        File f8 = new File(fileName7);
        deleteFile(f1);
        deleteFile(f2);
        deleteFile(f3);
        deleteFile(f4);
        deleteFile(f5);
        deleteFile(f6);
        deleteFile(f7);
        deleteFile(f8);
    }

    /**
     * Deletes the pdf files after sending the e-mail.
     *
     * @param file - the relevant file
     */
    private void deleteFile(final File file) {

        if (file.exists()) {
            LOG.debug(file.delete());
        }
    }

    /**
     * Sets Property Values To CommonEmailBean.
     *
     * @param student - {@link Student}
     * @return string path
     * @throws AkuraAppException - throw detailed exception
     */
    private String setPropertyValuesToCommonEmailBean(Student student) throws AkuraAppException {

        try {

            String path =
                    PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, new ErrorMsgLoader()
                                    .getErrorMessage(REF_EMAIL_REPORT_PATH));
            CommonEmailBean commonEmailBean = new CommonEmailBean();
            String templateName = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, VELOCITY_ATTENDANCE_TO_PARENT);
            String strSubject = STUDENT_PROGRESS_REPORT;
            Map<String, Object> mapObjectMap = new HashMap<String, Object>();
            mapObjectMap.put(MESSAGE, PROGRESS_REPORT_IS_ATTACHED);
            EmailUtil.addHeaderForEmail(mapObjectMap);
            commonEmailBean.setFromAddress(strFromAddress);
            commonEmailBean.setToAddress(strToAddress);
            commonEmailBean.setAttachementPath(path + "/" + student.getNameWtInitials() + PROGRESS_REPORT_PDF);
            commonEmailBean.setMailTemplate(templateName);
            commonEmailBean.setObjectMap(mapObjectMap);
            commonEmailBean.setSubject(strSubject);
            commonEmailBean.setAttachMail(true);
            emailService.sendMail(commonEmailBean);
            commonEmailBean.setAttachementPath(null);
            return path;
        } catch (ResourceNotFoundException e) {
            throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e);
        }
    }

    /**
     * Retrieve Logged in user's email address.
     *
     * @param session - {@link HttpSession}
     * @throws AkuraAppException - throw detailed exception
     */
    private void getLoginUserEmailAddress(HttpSession session) throws AkuraAppException {

        UserInfo userInfo = (UserInfo) session.getAttribute(USER);
        String userName = userInfo.getUsername();
        UserLogin userLogin = userService.getUser(userName);
        strFromAddress = userLogin.getEmail();
    }

    /**
     * Retrieve student parent email address.
     *
     * @param gotParentEmail - holds boolean type
     * @param studentId - holds integer type
     * @return boolean status
     * @throws AkuraAppException - throw detailed exception
     */
    private boolean getStudentParentEmailAddress(boolean gotParentEmail, int studentId) throws AkuraAppException {

        List<StudentParent> studentParentList = parentService.getStudentParentListByStudentId(studentId);
        Iterator<StudentParent> iterator = studentParentList.iterator();
        while (iterator.hasNext()) {
            StudentParent studentParent = iterator.next();
            if (studentParent.getParent().getEmail() != null) {
                strToAddress = studentParent.getParent().getEmail().toString();
                gotParentEmail = true;
                break;
            }
        }
        return gotParentEmail;
    }

    /**
     * Generate error messages.
     *
     * @param model - {@link ModelMap}
     * @param messageKey - holds string type
     * @param request - holds request object
     * @return {@link ModelAndView}
     */
    private ModelAndView populateErrorMessage(ModelMap model, String messageKey, HttpServletRequest request) {

        String message = new ErrorMsgLoader().getErrorMessage(messageKey);
        model.addAttribute(MESSAGE, message);

        String comments = request.getParameter(COMMENTS);
        request.setAttribute(COMMENTS, comments);

        return new ModelAndView(STUDENT_STUDENT_PROGRESS_REPORT, model);
    }

    /**
     * Redirects to the relevant page.
     *
     * @return - name of the view which is redirected to
     */
    @RequestMapping(STUDENT_PROGRESS_MAPPING)
    public String studentProgressReport() {

        generateReport = false;
        return STUDENT_STUDENT_PROGRESS_REPORT;
    }

    /**
     * Generates a summary report of the student's progress.
     *
     * @param modelAndView - the name of the view
     * @param request - an instance of request scope
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    @RequestMapping(value = REQUEST_STUDENT_PROGRESS_PDF, method = RequestMethod.POST)
    public ModelAndView studentProgressReportPDF(ModelAndView modelAndView, HttpServletRequest request)
            throws AkuraAppException {

        LOG.debug(RECEIVED_REQUEST_LOG);
        String comments = request.getParameter(COMMENTS);
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(USER);
        List<Object> studentMarksTemplateList = new ArrayList<Object>();
        List<StudentMarksTemplate> studentSubMarksList = new ArrayList<StudentMarksTemplate>();
        int studentId = (Integer) request.getSession().getAttribute(STUDENT_ID);
        Student student = studentService.findStudent(studentId);
        List<StudentTermMarksRank> termMarksRankList = null;
        Set<StudentClassInfo> studentClassInfoList = student.getStudentClassInfos();
        if (studentClassInfoList == null || studentClassInfoList.isEmpty()) {
            return new ModelAndView(EMPTY_REPORT);
        }
        String classGrade = null;
        for (StudentClassInfo studentClassInfo : studentClassInfoList) {

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            String currentYear = year + DATE;
            int infoYear = DateUtil.getYearFromDate(studentClassInfo.getYear());
            Date convertedDate = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE);
            try {
                convertedDate = dateFormat.parse(currentYear);
            } catch (ParseException e) {
                LOG.debug(ERROR_DATE_CONVERSION, e);
            }
            if (infoYear == (DateUtil.getYearFromDate(convertedDate))) {
                classGrade = studentClassInfo.getClassGrade().getDescription();
                Set<StudentTermMark> studentTermMarks = studentClassInfo.getStudentTermMarks();
                if (studentTermMarks.isEmpty()) {
                    return new ModelAndView(EMPTY_REPORT);
                }
                populateStudentTermMarks(studentMarksTemplateList, studentTermMarks);
                populateStudentSubTermMarks(student, year);
                termMarksRankList = studentService.getStudentRank(studentClassInfo.getStudentClassInfoId());
            }
        }

        int year = Calendar.getInstance().get(Calendar.YEAR);
        List<StudentLeave> attendanceList = getStudentAttendance(studentId, student, year);
        List<FaithLifeRating> faithLifeRatingList = getStudentFaithLife(studentId);
        List<Object> termMarksForTerm = studentService.populateStudentAverageTerm(year, student.getStudentId());

        // gets a list of average of the student marks.
        List<StudentSubjectAverageViewDTO> studentSubjecAverageList =
                studentService.getStudentSubjectAverage(studentId, year);

        context = request.getSession().getServletContext();

        SortUtil.sortStudentMarksTemplateList(studentMarksTemplateList);

        Map<String, Object> parameterMap =
                populateParameterMap(comments, userInfo, studentMarksTemplateList, studentSubMarksList, student,
                        faithLifeRatingList, classGrade);
        JRDataSource datasource4 = new JRBeanCollectionDataSource(attendanceList);

        List<StudentTermMarkDTO> studentTermMarkDTOList = getStudentTermAverage(termMarksForTerm, parameterMap);
        JRDataSource studentSubjectAverage = new JRBeanCollectionDataSource(studentSubjecAverageList);

        JRDataSource termMarksRank = new JRBeanCollectionDataSource(termMarksRankList);

        List<AssignmentMarkView> assignmentMarkList = studentService.getStudentAssignmentMarks(studentId, year);
        JRDataSource assignmentMark = new JRBeanCollectionDataSource(assignmentMarkList);

        // populates the hash map contains the data.
        populateDataMap(parameterMap, datasource4, studentSubjectAverage, termMarksRank, assignmentMark);

        parameterMap = generateImage(student, parameterMap);
        // Jasper sub reports preparation
        try {

            String path =
                    PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, new ErrorMsgLoader()
                                    .getErrorMessage(REF_EMAIL_REPORT_PATH));

            // if pdf files are in the export path delete all.
            deleteFiles(student, path);
            generateSubReport(faithLifeRatingList, parameterMap, path, RESOURCES_JRXML_FAITH_LIFE_RATING_JRXML,
                    FAITH_LIFE_RATING_PDF);
            // generates sub term marks sub report.
            generateSubReport(studentSubMarksList, parameterMap, path, RESOURCES_JRXML_SUB_TERM_MARKS_REPORT_JRXML,
                    STUDENT_SUB_MARKS_LIST_PDF);

            // generates attendance sub report.
            generateSubReport(attendanceList, parameterMap, path, RESOURCES_JRXML_ATTENDANCE_REPORT_JRXML,
                    ATTENDANCE_PDF);

            generateSubReport(studentTermMarkDTOList, parameterMap, path, RESOURCES_JRXML_SUB_TERM_AVERAGE_JRXML,
                    STUDENT_TERM_AVERAGE);

            // generates student subject average sub report.
            generateSubReport(studentSubjecAverageList, parameterMap, path, RESOURCES_JRXML_SUBJECT_AVERAGE_JRXML,
                    STUDENT_SUBJECT_AVERAGE);

            // generates term marks rank sub report.
            generateSubReport(termMarksRankList, parameterMap, path, RESOURCES_JRXML_MARKS_RANK_JRXML,
                    STUDENT_TERM_RANK);

            // generates assignment marks sub report.
            generateSubReport(assignmentMarkList, parameterMap, path, RESOURCES_JRXML_STUDENT_ASSIGNMENT_MARKS,
                    STUDENT_ASSIGNMENT_MARKS);

            // Prepare main report
            List<InputStream> pdfs = createMainReport(studentMarksTemplateList, parameterMap, path);

            addToPdf(studentSubMarksList, STUDENT_SUB_MARKS_LIST_PDF, path, pdfs);
            addToPdf(studentTermMarkDTOList, STUDENT_TERM_AVERAGE, path, pdfs);
            addToPdf(studentSubjecAverageList, STUDENT_SUBJECT_AVERAGE, path, pdfs);
            if (termMarksRankList != null && termMarksRankList.size() > 0) {
                pdfs.add(new FileInputStream(path + STUDENT_TERM_RANK));
            }

            addToPdf(faithLifeRatingList, FAITH_LIFE_RATING_PDF, path, pdfs);
            addToPdf(attendanceList, ATTENDANCE_PDF, path, pdfs);
            addToPdf(assignmentMarkList, STUDENT_ASSIGNMENT_MARKS, path, pdfs);

            OutputStream output = new FileOutputStream(path + "/" + student.getNameWtInitials() + PROGRESS_REPORT_PDF);
            MessageBoardController.concatPDFs(pdfs, output, true);
        } catch (JRException e) {
            LOG.debug(LOG_JASPERREPORT_GENERATION_ERROR);
        } catch (FileNotFoundException e) {
            LOG.debug(LOG_FILE_NOT_FOUND_ERROR);
        }
        modelAndView = new ModelAndView(PDF_REPORT, parameterMap);
        generateReport = true;
        return modelAndView;
    }

    /**
     * Populates the hash map.
     *
     * @param parameterMap - parameter map.
     * @param datasource4 - the name of the data source
     * @param studentSubjectAverage - data source for the student subject average.
     * @param termMarksRank - data source for the student assignment.
     * @param assignmentMark - data source for the student term rank.
     */
    private void populateDataMap(Map<String, Object> parameterMap, JRDataSource datasource4,
            JRDataSource studentSubjectAverage, JRDataSource termMarksRank, JRDataSource assignmentMark) {

        parameterMap.put(JASPER_CUSTOM_SUB_REPORT_DATASOURCE8, assignmentMark);
        parameterMap.put(JASPER_CUSTOM_SUB_REPORT_DATASOURCE6, studentSubjectAverage);
        parameterMap.put(JASPER_CUSTOM_SUB_REPORT_DATASOURCE4, datasource4);
        parameterMap.put(JASPER_CUSTOM_SUB_REPORT_DATASOURCE7, termMarksRank);
    }

    /**
     * Merges the sub reports on to one report.
     *
     * @param list - the relevant type of list.
     * @param pdfName - the name of the sub report
     * @param path - the path to be exported.
     * @param pdfs - the list of input streams
     * @throws FileNotFoundException - The exception details that occurred when file is not found.
     */
    private void addToPdf(List<?> list, String pdfName, String path, List<InputStream> pdfs)
            throws FileNotFoundException {

        if (list.size() > 0) {
            pdfs.add(new FileInputStream(path + pdfName));
        }
    }

    /**
     * Returns a list of StudentTermMarkDTO.
     *
     * @param termMarksForTerm - a list of properties of StudentTermMarkDTO.
     * @param parameterMap - a map containing the data.
     * @return - a list of StudentTermMarkDTO.
     */
    private List<StudentTermMarkDTO> getStudentTermAverage(List<Object> termMarksForTerm,
            Map<String, Object> parameterMap) {

        Iterator<?> iteratorList = termMarksForTerm.iterator();
        List<StudentTermMarkDTO> studentTermMarkDTOList = new LinkedList<StudentTermMarkDTO>();

        while (iteratorList.hasNext()) {
            Object[] object = (Object[]) iteratorList.next();
            StudentTermMarkDTO termMarkDto = new StudentTermMarkDTO();
            int index = 1;
            termMarkDto.setTerm((String) object[index]);
            Double marks = (Double) object[0];
            Float floatMarks = marks.floatValue();
            floatMarks = roundFloat(floatMarks, 2);
            termMarkDto.setMarks(floatMarks);
            studentTermMarkDTOList.add(termMarkDto);
        }

        JRDataSource averageTermMarks = new JRBeanCollectionDataSource(studentTermMarkDTOList);

        parameterMap.put(JASPER_CUSTOM_SUB_REPORT_DATASOURCE5, averageTermMarks);
        return studentTermMarkDTOList;
    }

    /**
     * Populate student sub term marks.
     *
     * @param year - the relevant year
     * @param student - the instance of relevant student
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    private void populateStudentSubTermMarks(Student student, int year) throws AkuraAppException {

        List<StudentSubTermMarkDTO> studentSubTermMarkList =
                studentService.getSubTermMarksForStudent(student.getStudentId(), year);
        List<StudentMarksTemplate> studentSubMarksList = new ArrayList<StudentMarksTemplate>();

        for (StudentSubTermMarkDTO studentSubTermMark : studentSubTermMarkList) {
            StudentMarksTemplate subMarksTemplate = new StudentMarksTemplate();
            String gradingValue = "";

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
    }

    /**
     * Generates the photo of the student.
     *
     * @param student - the instance of the student.
     * @param parameterMap - the map contains the data.
     * @return - the map
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public Map<String, Object> generateImage(final Student student, Map<String, Object> parameterMap)
            throws AkuraAppException {

        if (student.getPhoto() != null && student.getPhoto().length > 0) {
            byte[] image = student.getPhoto();
            String imageLoadPath =
                    PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, IMAGE_FOLDER_PATH);
            imageLoadPath = imageLoadPath + student.getAdmissionNo() + FILE_EXT;
            StaticDataUtil.previewProfile(imageLoadPath, image);
            parameterMap.put(PHOTO, imageLoadPath);
        }
        return parameterMap;
    }

    /**
     * Generates the sub reports.
     *
     * @param list - the relevant type of object list.
     * @param parameterMap - holds a map
     * @param path - holds String type
     * @param reportPath - the path of the report to be exported.
     * @param reportName - the name of the sub report.
     * @throws JRException - throw detail exception when generate sub reports
     */
    private void generateSubReport(List<?> list, Map<String, Object> parameterMap, String path, String reportPath,
            String reportName) throws JRException {

        if (list != null && list.size() > 0) {
            JasperDesign subReportDesign = JRXmlLoader.load(context.getRealPath(reportPath));
            JasperReport subCompiled = JasperCompileManager.compileReport(subReportDesign);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
            Map<String, Object> subReportMap = parameterMap;
            subReportMap.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                    + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, STYLE_PATH));
            JasperPrint printSubreport = JasperFillManager.fillReport(subCompiled, subReportMap, ds);
            JasperExportManager.exportReportToPdfFile(printSubreport, path + reportName);
        }
    }

    /**
     * Rounding off a float number into two decimal places.
     *
     * @param floatMarks - the float number.
     * @param i - the number of decimal places.
     * @return - the float number that rounded into two decimal places.
     */
    private Float roundFloat(Float floatMarks, final int i) {

        float p = (float) Math.pow(DECIMAL, i);
        floatMarks = floatMarks * p;
        float tmp = Math.round(floatMarks);
        return (float) tmp / p;
    }

    /**
     * Retrieves the student attendance results.
     *
     * @param studentId - primary key of the student.
     * @param student - an instance of student.
     * @param year - the current year.
     * @return - a list of attendance of the student for the current year.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    private List<StudentLeave> getStudentAttendance(int studentId, Student student, int year) throws AkuraAppException {

        List<StudentLeave> studentLeaves = studentService.getStudentLeaveListByStudentId(studentId, year);
        return SortUtil.sortStudentAttendance(studentLeaves);
    }

    /**
     * Populate student term marks.
     *
     * @param studentMarksTemplateList - list of object
     * @param studentTermMarks - sets of StudentTermMark
     * @throws AkuraAppException - throw detailed exception
     */
    private void populateStudentTermMarks(List<Object> studentMarksTemplateList, Set<StudentTermMark> studentTermMarks)
            throws AkuraAppException {

        for (StudentTermMark termMark : studentTermMarks) {
            StudentMarksTemplate studentMarksTemplate = new StudentMarksTemplate();
            Term term = commonService.findTermById(termMark.getTermId());

            setValuesToStudentTermMarkTemplate(studentMarksTemplateList, termMark, studentMarksTemplate, term);
        }
    }

    /**
     * Generate Main report.
     *
     * @param studentMarksTemplateList - holds list of Object
     * @param parameterMap - holds a map
     * @param path - holds a string type
     * @return list of InputStream
     * @throws JRException - throw this
     * @throws FileNotFoundException - throw this
     */
    private List<InputStream> createMainReport(List<Object> studentMarksTemplateList, Map<String, Object> parameterMap,
            String path) throws JRException, FileNotFoundException {

        JasperDesign mainDesign = JRXmlLoader.load(context.getRealPath(RESOURCES_JRXML_STUDENT_PROGRESS_REPORT_JRXML));
        JasperReport mainCompiled = JasperCompileManager.compileReport(mainDesign);
        JRBeanCollectionDataSource mainDS = new JRBeanCollectionDataSource(studentMarksTemplateList);
        JasperPrint printMainreport = JasperFillManager.fillReport(mainCompiled, parameterMap, mainDS);
        JasperExportManager.exportReportToPdfFile(printMainreport, path + MAIN_REPORT_PDF);
        List<InputStream> pdfs = new ArrayList<InputStream>();
        pdfs.add(new FileInputStream(path + MAIN_REPORT_PDF));
        return pdfs;
    }

    /**
     * Populate parameter map.
     *
     * @param comments - holds String type
     * @param userInfo - holds {@link UserInfo}
     * @param studentMarksTemplateList - holds list of objects
     * @param studentSubMarksList - holds list of StudentMarksTemplate
     * @param student - {@link Student}
     * @param faithLifeRatingList - holds list of FaithLifeTemplate
     * @param classGrade - class grade of the student.
     * @return Map with String key and Object value
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    private Map<String, Object> populateParameterMap(String comments, UserInfo userInfo,
            List<Object> studentMarksTemplateList, List<StudentMarksTemplate> studentSubMarksList, Student student,
            List<FaithLifeRating> faithLifeRatingList, String classGrade) throws AkuraAppException {

        JRDataSource datasource = new JRBeanCollectionDataSource(studentMarksTemplateList);
        JRDataSource datasource1 = new JRBeanCollectionDataSource(faithLifeRatingList);
        JRDataSource datasource3 = new JRBeanCollectionDataSource(studentSubMarksList);
        UserLogin userLogin = userService.getAnyUser(userInfo.getUsername());
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put(DATASOURCE, datasource);
        parameterMap.put(YEAR, DateUtil.currentYear());
        parameterMap.put(MARKS_LABLE_MAIN, MARKS_TEXT);
        parameterMap.put(CLASS_AVERAGE_LABLE_MAIN, CLASS_AVERAGE_TEXT);
        parameterMap.put(GRADE_AVERAGE_LABLE_MAIN, GRADE_AVERAGE_TEXT);
        parameterMap.put(ACADEMIC_LABLE_MAIN, ACADEMIC_PROGRESS_TEXT);
        parameterMap.put(SUBJECT_LABLE_MAIN, SUBJECT_TEXT);
        parameterMap.put(SUBJECT_A_TITLE_MAIN, MARKS_COMPARISON_TEXT);
        parameterMap.put(CLASS, classGrade);
        parameterMap.put(LOGO, PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, COM_SMS_PATH_IMAGE));
        parameterMap.put(GENERATOR, userLogin.getFirstName() + " " + userLogin.getLastName());
        parameterMap.put(COMMENT, comments);
        parameterMap.put(STUDENT_NAME, student.getFullName());
        parameterMap.put(STYLE, PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, STYLE_PATH));
        parameterMap.put(JASPER_CUSTOM_SUB_REPORT_DATASOURCE1, datasource1);
        parameterMap.put(JASPER_CUSTOM_SUB_REPORT_DATASOURCE3, datasource3);
        parameterMap.put(GPL, AkuraWebConstant.REPORT_GPL);
        return parameterMap;
    }

    /**
     * Retrieves student faith life rating.
     *
     * @param studentId - primary key of the student.
     * @return list of FaithLifeTemplate
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    private List<FaithLifeRating> getStudentFaithLife(Integer studentId) throws AkuraAppException {

        return studentService.getFaithLifeRatingsListForStudent(studentId, DateUtil.currentYear());
    }

    /**
     * Set values to student term marks template.
     *
     * @param studentMarksTemplateList - holds list of objects
     * @param termMark - {@link StudentTermMark}
     * @param studentMarksTemplate - {@link StudentMarksTemplate}
     * @param term - {@link Term}
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    private void setValuesToStudentTermMarkTemplate(List<Object> studentMarksTemplateList, StudentTermMark termMark,
            StudentMarksTemplate studentMarksTemplate, Term term) throws AkuraAppException {

        GradeSubject gradeSubject = commonService.findGradeSubject(termMark.getGradeSubjectId());
        studentMarksTemplate.setMarks(termMark.getMarks());
        studentMarksTemplate.setSubjectName(gradeSubject.getSubject().getDescription());
        studentMarksTemplate.setTerm(term.getDescription());
        studentMarksTemplateList.add(studentMarksTemplate);
    }

    /**
     * Merge many pdf files into one file.
     *
     * @param streamOfPDFFiles - a list of inputStreams
     * @param outputStream - an instance of outputStream
     * @param paginate - a boolean
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public static void concatPDFs(List<InputStream> streamOfPDFFiles, OutputStream outputStream, boolean paginate)
            throws AkuraAppException {

        final int fontSize = 8, leftRightAlignment = 8;
        final int min = 0;
        final int max = 5;
        final int size = 300;
        final int xAxis = -150;
        final int pageHeight = 550;
        final int pageHieghtfromBottom = 16;
        final int pageRecHeight = 580;
        final int recHeightFromBottom = 14;
        Document document = new Document(PageSize.A4);
        try {
            List<InputStream> pdfs = streamOfPDFFiles;
            List<PdfReader> readers = new ArrayList<PdfReader>();
            int totalPages = 0;
            Iterator<InputStream> iteratorPDFs = pdfs.iterator();

            // Create Readers for the pdfs.
            while (iteratorPDFs.hasNext()) {
                InputStream pdf = iteratorPDFs.next();
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
            // Create a writer for the output stream
            PdfWriter writer = null;
            BaseFont bf = null;
            try {
                writer = PdfWriter.getInstance(document, outputStream);

                document.open();

                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF data
            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();

            // Loop through the PDF files and add to the output.
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();

                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    if (currentPageNumber != 2) {
                        document.newPage();
                    }
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
                    if (currentPageNumber == 1) {
                        cb.addTemplate(page, 0, xAxis);
                    } else if (currentPageNumber != 2) {
                        cb.addTemplate(page, 0, 0);
                    }

                    // Code for pagination.
                    if (paginate) {
                        cb.beginText();
                        cb.setFontAndSize(bf, fontSize);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, " " + AkuraWebConstant.REPORT_GPL, size, max,
                                min);
                        cb.newlineText();
                        cb.endText();

                        if (currentPageNumber != 2) {
                            int pageNo = currentPageNumber;
                            if (currentPageNumber != 1) {
                                pageNo = currentPageNumber - 1;
                            }

                            // write the page number inside a rectangle.
                            cb.fillStroke();
                            cb.rectangle(leftRightAlignment, recHeightFromBottom, pageRecHeight, leftRightAlignment);
                            cb.beginText();
                            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, PAGE + pageNo, pageHeight,
                                    pageHieghtfromBottom, 0);
                            cb.endText();
                            cb.stroke();
                        }
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }

            outputStream.flush();

            document.close();

            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
