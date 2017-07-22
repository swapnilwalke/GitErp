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

package com.virtusa.akura.student.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.dto.StudentTemporaryLeave;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.api.exception.InvalidRejoinDateException;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.EmailService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.student.validator.StudentTemporaryLeaveValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.HolidayUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.email.EmailUtil;

/**
 * This class , manages student temporary leave - rejoin.
 *
 * @author Virtusa Corporation
 */

@Controller
@SessionAttributes("tempLeaveListCommand")
public class StudentTemporaryLeaveController {

    /** key to hold model attribute of selectedClassGrade. */
    private static final String MODEL_ATT_SELECTED_CLASS_GRADE = "selectedClassGrade";

    /** key to hold model attribute of selectedYear. */
    private static final String MODEL_ATT_SELECTED_YEAR = "selectedYear";

    /** key to hold string of ERROR MESSAGE. */
    private static final String MARKING_COMPLETED_ASSIGNED_TO_NEXT_YEAR =
            "STUDENT.TEMPEPORY.LEAVE.MARKING.COMPLETED.ASSIGN.NEXT.YEAR.ERROR.MESSAGE";

    /** key to hold string of ERROR MESSAGE. */
    private static final String STUDENT_SHOULD_BE_ASSIGNED_TO_CURENT_YEAR =
            "STUDENT.TEMPEPORY.LEAVE.ASSIGN.CURRENT.YEAR.ERROR.MESSAGE";

    /** key to hold string of SUCCESS MESSAGE. */
    private static final String STUDENT_IS_ACTIVATED_SUCCESSFULLY = "STUDENT.TEMPEPORY.LEAVE.ACTIVATE.SUCCESS.MESSAGE";

    /** key to hold string of ERROR MESSAGE. */
    private static final String MARKING_COMPLETED_ASSIGNED_TO_SAME_PREVIOUS_GRADE =
            "STUDENT.TEMPEPORY.LEAVE.MARKING.COMPLETED.ASSIGN.PREVIOUS.GRADE.ERROR.MESSAGE";

    /** key to hold string of ERROR MESSAGE. */
    private static final String ASSIGNED_TO_CLASS_OF_SAME_PREVIOUS_GRADE =
            "STUDENT.TEMPEPORY.LEAVE.ASSIGN.PREVIOUS.GRADE.ERROR.MESSAGE";

    /** key to hold string of index value. */
    private static final int VAL_THREE = 3;

    /** key to hold string of model attribute. */
    private static final String MODEL_ATT_SUCMESSAGE = "Sucmessage";

    /** key to hold string of request attribute student current year. */
    private static final String REQ_ATT_STUDENT_YEAR = "studentYear";

    /** key to hold string of model attribute of current year. */
    private static final String MODEL_ATT_CURRENT_YEAR = "currentYear";

    /** key to hold string of year index. */
    private static final int YEAR_INDEX = 4;

    /** key to hold string of active index. */
    private static final int ACTIVE_INDEX = 4;

    /** key to hold string of model attribute after success activated.. */
    private static final String MODEL_ATT_SUCCESS_ACTIVE_MSG = "successActiveMsg";

    /** key to hold string of model attribute success message for extended successfully. */
    private static final String EXTENDED_MSG = "extendedMsg";

    /** key to hold string of success message for extended successfully. */
    private static final String THE_TEMPERATURE_LEAVE_IS_EXTENDED_SUCCESSFULLY =
            "STUDENT.REJOIN.EXTENDED.LEAVE.MESSAGE";
    
    /** key to hold string of success message for curtailed successfully. */
    private static final String THE_TEMPERORY_LEAVE_IS_CURTAILED_SUCCESSFULLY =
            "STUDENT.REJOIN.CURTAILED.LEAVE.MESSAGE";
    

    /** key to hold string of skip values for assign class to future. */
    private static final int SKIP_YEAR_VALUE = 3;

    /** key to hold string of model attribute of error messages. */
    private static final String MODEL_ATT_ERROR_MESSAGES = "errorMessages";

    /** key to hold string of error message for no assign subjects. */
    private static final String STUDENT_TEMPEPORY_LEAVE_SUBJECT_ASSIGN_ERROR_MESSAGE =
            "STUDENT.TEMPEPORY.LEAVE.SUBJECT.ASSIGN.GRADE.ERROR.MESSAGE";

    /** key to hold string of model map for current class name. */
    private static final String MODEL_MAP_CURRENT_CLASS_NAME = "currentClassName";

    /** key to hold string of model map for year list. */
    private static final String MODEL_ATTR_YEAR_LIST = "yearList";

    /** key to hold string of request year. */
    private static final String REQ_YEAR = "year";

    /** key to hold string of error message for to date in extended. */
    private static final String STUDENT_TEMPEPORY_LEAVE_EXTEND_TO_DATE_ERROR_MESSAGE =
            "STUDENT.TEMPEPORY.LEAVE.EXTEND.TO.DATE.ERROR.MESSAGE";

    /** key to hold string of request of currently selected student class. */
    private static final String REQ_STUDENT_CLASS = "studentClass";

    /** key to hold string of request of success for re join and extended. */
    private static final String REQUEST_SUCCESS_MSG = "successMsg";

    /** key to hold string of email subject. */
    private static final String EMAIL_SUBJECT_TEMPORARY_LEAVE_EXTENDED = "STUDENT.REJOIN.EXTENDED.SUCCESS.MESSAGE";

    /** Represents the model value for successful addition. */
    private static final String SUCESS_EMAIL = "REF.UI.STUDENT.SUCESS_EMAIL";

    /** Represents the model value for successful addition. */
    private static final String FAILURE_EMAIL_PARENT_EMAIL = "REF.UI.STUDENT.FAILURE_EMAIL_PARENT_EMAIL";

    /** Represents the model value for successful addition. */
    private static final String FAILURE_EMAIL_PARENT_NOT_EXIST = "REF.UI.STUDENT.FAILURE_EMAIL_PARENT_NOTEXIST";

    /** Represents the model value for successful addition. */
    private static final String FAILURE_EMAIL_PARENT_EXIXTS = "REF.UI.STUDENT.FAILURE_FAILURE_EMAIL_PARENT_EXIXTS";

    /** Represents the model value for successful addition. */
    private static final String FAILURE_EMAIL_SERVER_ERROR = "REF.UI.STUDENT.FAILURE_SERVER_ERROR";

    /** key to hold string of content for email. */
    private static final String CONTENT2 = "content";

    /** key to hold string of logged user name for email. */
    private static final String STF_NAME = "stfName";

    /** key to hold string of first name for email. */
    private static final String F_NAME = "fName";

    /** key to hold string of email template. */
    private static final String EMAIL_TEMPLATE_STUDENT_TEMP_LEAVE_DETAILS = "email.template.studentTempLeaveDetails";

    /** key to hold string of email. */
    private static final String EMAIL = "email";

    /** String attribute for holding MAP_TODATE . */
    private static final String MAP_TODATE = "toDate";

    /** String attribute for holding MAIL_SUBJECT. */
    private static final String MAP_FROMDATE = "fromDate";

    /** String attribute for holding MAP_REASON. */
    private static final String MAP_REASON = "reason";

    /** key to hold string of content for mail. */
    private static final String EXTEND_TEMPORARY_LEAVE = "Extend Temporary Leave";

    /** String attribute for holding MESSAGE_TOPIC. */
    private static final String MESSAGE_TOPIC = "Extend Temporary Leave";

    /** key to hold string of session user. */
    private static final String SESSION_USER = "user";

    /** key to hold string of date field error message. */
    private static final String REF_UI_DATE_FIELD_INCORRECT = "REF.UI.DATE.FIELD.INCORRECT";

    /** key to hold string of model message. */
    private static final String MODEL_MESSAGE = "message";

    /** key to hold string of request map for save extend student. */
    private static final String REQUEST_MAP_SAVE_EXTENDED_STUDENT_TEMP_HTM = "saveExtendedStudentTemp.htm";

    /** key to hold string of request success. */
    private static final String REQUEST_SUCCESS = "success";

    /** key to hold string of request state. */
    private static final String REQUEST_STATE = "state";

    /** key to hold string of request map extended. */
    private static final String REQUEST_MAP_EXTEND_STUDENT_TEMP_HTM = "extendStudentTemp.htm";

    /** key to hold string of error message for activated. */
    private static final String STUDENT_REJOIN_ACTIVATE_ERROR_MESSAGE = "STUDENT.REJOIN.ACTIVATE.ERROR.MESSAGE";

    /** key to hold string of model attribute. */
    private static final String MESSAGES = "messages";

    /** key to hold string of activated message. */
    private static final String STUDENT_REJOIN_ACTIVATE_SUCCESS_MESSAGE = "STUDENT.REJOIN.ACTIVATE.SUCCESS.MESSAGE";

    /** key to hold string of request of activated date. */
    private static final String REQUEST_ACTIVATE_DATE = "activateDate";

    /** key to hold string of request error state. */
    private static final String REQUEST_ERRSTATE = "Errstate";

    /** key to hold string of request map for activate temporary student. */
    private static final String REQUEST_MAP_ACTIVATE_STUDENT_TEMP_HTM = "activateStudentTemp.htm";

    /** key to hold string of model attribute class grade list. */
    private static final String MODEL_ATTI_CLASS_GRADE_LIST = "classGradeList";

    /** key to hold string of model attribute student id. */
    private static final String MODEL_ATTI_STUDENT_ID = "studentId";

    /** key to hold string of request attribute student id. */
    private static final String REQUEST_SELECTED_STUDENT_ID = "selectedStudentId";

    /** key to hold string of request map for leave list. */
    private static final String REQUEST_MAP_TEMP_LEAVE_LIST = "tempLeaveList";

    /** key to hold string of view page. */
    private static final String VIEW_PAGE_STUDENT_ACTIVE_STUDENT_TEMPORARY_LEAVE =
            "student/activeStudentTemporaryLeave";

    /** key to hold string of request map manage activate temp leave. */
    private static final String REQUEST_MAP_MANAGE_ACTIVE_EXTEND_TEMP_STUDENT_LEAVE_HTM =
            "/manageActiveExtendTempStudentLeave.htm";

    /** key to hold string of model attribute. */
    private static final String MODEL_TEMP_LEAVE_LIST_COMMAND = "tempLeaveListCommand";

    /** key to hold string of year month date. */
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    /** key to hold string of year end date. */
    private static final String END_DATE = "-12-31";

    /** key to hold string of year start date. */
    private static final String START_DATE = "-01-01";

    /** Represents an instance of StudentService. */
    private StudentService studentService;

    /** Represents an instance of CommonService. */
    private CommonService commonService;

    /** emailService attribute for holding EmailService. */
    private EmailService emailService;

    /** String attribute for holding SESSION_USER. */
    private UserService userService;

    /**
     * StudentTemporaryLeaveValidator attribute for holding StudentTemporaryLeaveValidator.
     */
    private StudentTemporaryLeaveValidator temporaryLeaveValidator;

    /**
     * Sets the StudentTemporaryLeaveValidator object.
     *
     * @param temporaryLeaveValidatorObj set StudentTemporaryLeaveValidator object.
     */
    public void setTemporaryLeaveValidator(StudentTemporaryLeaveValidator temporaryLeaveValidatorObj) {

        this.temporaryLeaveValidator = temporaryLeaveValidatorObj;
    }

    /**
     * Set userService.
     *
     * @param userServiceVal the userService to set
     */
    public void setUserService(UserService userServiceVal) {

        this.userService = userServiceVal;
    }

    /**
     * Injects an instance of StudentService.
     *
     * @param objStudentService - an instance of StudentService.
     */
    public void setStudentService(StudentService objStudentService) {

        this.studentService = objStudentService;
    }

    /**
     * Injects an instance of CommonService.
     *
     * @param objCommonService - an instance of CommonService.
     */
    public void setCommonService(final CommonService objCommonService) {

        this.commonService = objCommonService;
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
     * intiBinder method is to bind date class with the date format.
     *
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder) {

        binder.getBindingResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    }

    /**
     * Initializes the reference data to which are related to the student temporary leave, that is to be
     * previewed on the UI.
     *
     * @param model - a ModelMap that contains information of the student temporary leave.
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(REQUEST_MAP_MANAGE_ACTIVE_EXTEND_TEMP_STUDENT_LEAVE_HTM)
    public final String addStudentTempLeave(final ModelMap model) throws AkuraAppException {

        StudentTemporaryLeave studentLeave = new StudentTemporaryLeave();
        model.addAttribute(MODEL_TEMP_LEAVE_LIST_COMMAND, studentLeave);
        model.addAttribute(MODEL_ATT_SUCCESS_ACTIVE_MSG, Boolean.TRUE);

        return VIEW_PAGE_STUDENT_ACTIVE_STUDENT_TEMPORARY_LEAVE;
    }

    /**
     * @param model - a ModelMap that contains information of the student object.
     * @param request - request of type HttpServletRequest.
     * @return the student Leave List.
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(REQUEST_MAP_TEMP_LEAVE_LIST)
    public List<StudentTemporaryLeave> populateStudentTempLeaveList(final ModelMap model, HttpServletRequest request)
            throws AkuraAppException {

        // get current student grade class description
        String currClass = request.getParameter(REQ_STUDENT_CLASS);

        // get year of that current class
        String currYear = request.getParameter(REQ_ATT_STUDENT_YEAR);

        // get student registration number
        String registrationNo = request.getParameter(REQUEST_SELECTED_STUDENT_ID);

        // get particular student id
        int studentId = Integer.parseInt(registrationNo);

        // get student object
        Student objStudent = (Student) studentService.viewStudent(studentId);

        String currYearOnly = AkuraWebConstant.EMPTY_STRING;

        // get current year only, example : 2012
        if (currYear != AkuraWebConstant.EMPTY_STRING) {
            currYearOnly = currYear.substring(0, YEAR_INDEX);
        }

        // set student object, it's class, selected year
        model.addAttribute(MODEL_ATTI_STUDENT_ID, objStudent);
        model.addAttribute(MODEL_MAP_CURRENT_CLASS_NAME, currClass);
        model.addAttribute(MODEL_ATT_CURRENT_YEAR, currYearOnly);

        // get the student temporary leave list for view.
        return SortUtil.sorttemporaryLeaveStudentListByDate
        		(studentService.getStudentTempLeaveListByStudentId(studentId));
    }

    /**
     * @return the student class grade List.
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(MODEL_ATTI_CLASS_GRADE_LIST)
    public List<ClassGrade> populateClassList() throws AkuraAppException {

        return SortUtil.sortClassGradeList(commonService.getClassGradeList());
    }

    /**
     * @return the year List.
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(MODEL_ATTR_YEAR_LIST)
    public List<String> populateYearList() throws AkuraAppException {

        return DateUtil.getFutureYears(2);
    }

    /**
     * re Join Student, when he is on temporary leave.
     *
     * @param model - a ModelMap that contains information of the re join Student
     * @param studentTemporaryLeave - an instance of StudentTemporaryLeave
     * @param result - containing list of errors and studentTemporaryLeave instance to which data was bound
     * @param request - request of type HttpServletRequest.
     * @param session - session of user.
     * @return the student temporary leave view.
     * @throws AkuraAppException - The exception details that occurred when re join temporary leave student
     *         instance.
     * @throws ParseException - ParseException.
     * @throws IOException - IOException
     */
    @RequestMapping(value = REQUEST_MAP_ACTIVATE_STUDENT_TEMP_HTM)
    public final String reJoinStudent(final HttpServletRequest request, final ModelMap model, HttpSession session,
            @ModelAttribute(MODEL_TEMP_LEAVE_LIST_COMMAND) final StudentTemporaryLeave studentTemporaryLeave,
            BindingResult result) throws AkuraAppException, ParseException, IOException {

        String message = AkuraWebConstant.EMPTY_STRING;
        String errorMessage = AkuraWebConstant.EMPTY_STRING;

        String resultPage = VIEW_PAGE_STUDENT_ACTIVE_STUDENT_TEMPORARY_LEAVE;

        // get selectedYear from request
        String reqSelectedYear = request.getParameter(REQ_YEAR);
        int selectedYear = Integer.parseInt(reqSelectedYear);

        // get selected class grade from drop down list
        String description = studentTemporaryLeave.getClassGradeDescription();

        studentTemporaryLeave.setYear(selectedYear);

        // validate fields from UI
        temporaryLeaveValidator.validate(studentTemporaryLeave, result);
        model.addAttribute(MODEL_ATT_SUCCESS_ACTIVE_MSG, Boolean.TRUE);

        if (result.hasErrors()) {
            setModelAttribute(model, selectedYear, description);
            request.setAttribute(REQUEST_ERRSTATE, Boolean.TRUE);
        } else {
            List<ClassGrade> selectedClassGradeObjList = commonService.getClassGradeByName(description);
            ClassGrade selectedGradeClassObj = selectedClassGradeObjList.get(0);

            List<GradeSubject> gradeSubjectList =
                    commonService.getGradeCoreSubjectIdListByGrade(selectedGradeClassObj.getGrade().getGradeId());

            if (gradeSubjectList.isEmpty()) {
                setModelAttribute(model, selectedYear, description);
                errorMessage =
                        new ErrorMsgLoader().getErrorMessage(STUDENT_TEMPEPORY_LEAVE_SUBJECT_ASSIGN_ERROR_MESSAGE);
                model.addAttribute(MODEL_ATT_ERROR_MESSAGES, errorMessage);

            } else {

                // get student id from request
                String reqStudentId = request.getParameter(REQUEST_SELECTED_STUDENT_ID);

                // convert request into integer
                int studentId = Integer.parseInt(reqStudentId);

                // get student object
                Student student = studentService.findStudent(studentId);

                // get selected date from UI
                String date = request.getParameter(REQUEST_ACTIVATE_DATE);

                // Returns the date as this format '2012-01-01'.
                Date reJoinDateYear = DateUtil.getDate(selectedYear);

                // get current student grade class description
                String currClass = request.getParameter(REQ_STUDENT_CLASS);

                // get student current grade class object
                List<ClassGrade> currGradeClassObjList = commonService.getClassGradeByName(currClass);

                ClassGrade currGradeClassObj = null;

                if (!currGradeClassObjList.isEmpty()) {
                    // this object exists , because already has grade classes.
                    currGradeClassObj = currGradeClassObjList.get(0);
                }

                try {
                    // convert selected date(activate date) into date format
                    Date convertedDate = DateUtil.convertStringToDate(date);

                    // student is assigned into class and core subjects
                    int assignGradeSubject =
                            assignCoreSubjects(studentId, reJoinDateYear, selectedGradeClassObj, student,
                                    currGradeClassObj, selectedYear, gradeSubjectList);

                    switch (assignGradeSubject) {
                        case 0:
                            errorMessage =
                                    new ErrorMsgLoader().getErrorMessage(ASSIGNED_TO_CLASS_OF_SAME_PREVIOUS_GRADE);
                            break;
                        case 1:
                            errorMessage =
                                    new ErrorMsgLoader()
                                            .getErrorMessage(MARKING_COMPLETED_ASSIGNED_TO_SAME_PREVIOUS_GRADE);
                            break;
                        case 2:

                            // check he can be rejoined or not
                            boolean joinStatus = studentService.rejoinStudentMemberService(studentId, convertedDate);

                            if (joinStatus) {
                                message = new ErrorMsgLoader().getErrorMessage(STUDENT_IS_ACTIVATED_SUCCESSFULLY);
                                message = new ErrorMsgLoader().getErrorMessage(STUDENT_REJOIN_ACTIVATE_SUCCESS_MESSAGE);
                                model.addAttribute(MODEL_ATT_SUCCESS_ACTIVE_MSG, Boolean.FALSE);
                            }

                            break;
                        case SKIP_YEAR_VALUE:
                            errorMessage =
                                    new ErrorMsgLoader().getErrorMessage(STUDENT_SHOULD_BE_ASSIGNED_TO_CURENT_YEAR);

                            break;

                        case ACTIVE_INDEX:
                            errorMessage =
                                    new ErrorMsgLoader().getErrorMessage(MARKING_COMPLETED_ASSIGNED_TO_NEXT_YEAR);
                            break;

                        default:

                    }

                    model.addAttribute(MESSAGES, message);
                    request.setAttribute(REQUEST_SUCCESS_MSG, Boolean.TRUE);
                    model.addAttribute(MODEL_ATT_ERROR_MESSAGES, errorMessage);

                    List<StudentTemporaryLeave> studentLeaveList =
                            studentService.getStudentTempLeaveListByStudentId(studentId);
                    model.addAttribute(REQUEST_MAP_TEMP_LEAVE_LIST, studentLeaveList);

                } catch (InvalidRejoinDateException e) {

                    errorMessage = new ErrorMsgLoader().getErrorMessage(STUDENT_REJOIN_ACTIVATE_ERROR_MESSAGE);
                    model.addAttribute(MODEL_ATT_ERROR_MESSAGES, errorMessage);
                }

            }
        }
        return resultPage;
    }

    /**
     * method to assign model attribute.
     *
     * @param model - model
     * @param description - description
     * @param selectedYear - selectedYear.
     */
    private void setModelAttribute(final ModelMap model, int selectedYear, String description) {

        model.addAttribute(MODEL_ATT_SELECTED_YEAR, selectedYear);
        model.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE, description);
    }

    /**
     * method to assign into new class and Core Subjects or updated for student.
     *
     * @param studentId - studentId
     * @param rejoinedDate - rejoinedDate.
     * @param slectedClassGradeObj - slectedClassGradeObj.
     * @param student - student.
     * @param currGradeClassObj - currGradeClassObj of ClassGrade Object
     * @param selectedYear - selectedYear.
     * @param gradeSubjectList - gradeSubjectList.
     * @return flag - flag.
     * @throws AkuraAppException SMS Exceptions
     */
    private int assignCoreSubjects(int studentId, Date rejoinedDate, ClassGrade slectedClassGradeObj, Student student,
            ClassGrade currGradeClassObj, int selectedYear, List<GradeSubject> gradeSubjectList)
            throws AkuraAppException {

        int currentYear = DateUtil.currentYearOnly();
        Date curYear = DateUtil.getDate(currentYear);
        int futureYear = currentYear + 1;

        int currGradeId = 0;
        int currentClassGradeId = 0;
        String currentGradeDescription = AkuraWebConstant.EMPTY_STRING;
        int selectedGradeId = slectedClassGradeObj.getGrade().getGradeId();
        int selectedClassGradeId = slectedClassGradeObj.getClassGradeId();

        if (currGradeClassObj != null) {
            currGradeId = currGradeClassObj.getGrade().getGradeId();
            currentClassGradeId = currGradeClassObj.getClassGradeId();
            currentGradeDescription = currGradeClassObj.getDescription();
        }

        int currStudentClassInfoId = getCurrentStudentClassList(studentId, curYear, currentClassGradeId);

        List<Term> termList = commonService.getTermList();
        List<StudentClassInfo> exsistStudentClassInfoObjList =
                getExsistStudentClassList(studentId, curYear, currentClassGradeId);
        List<StudentClassInfo> nowExsistStudentClassInfoObjList = null;

        int indexVal = 0;
        int x = 0;
        boolean assignNewClass = false;
        boolean isSeleStudentTermExist = false;
        boolean isCurrStudentTermExist = false;
        boolean completedFlag = false;

        for (Term term : termList) {
            int termId = term.getTermId();
            isSeleStudentTermExist =
                    studentService.isMarkingCompletedForTerm(selectedClassGradeId, termId, currentYear);
            isCurrStudentTermExist = studentService.isMarkingCompletedForTerm(currentClassGradeId, termId, currentYear);
            if (isSeleStudentTermExist) {
                x++;
            }

            if (isCurrStudentTermExist && currentClassGradeId != 0) {
                completedFlag = true;
            }
        }

        for (Term term : termList) {
            int termId = term.getTermId();
            isSeleStudentTermExist =
                    studentService.isMarkingCompletedForTerm(selectedClassGradeId, termId, currentYear);

            for (GradeSubject gradeSubject : gradeSubjectList) {

                int gradeSubjectId = gradeSubject.getGradeSubjectId();
                nowExsistStudentClassInfoObjList =
                        studentService.getStudentClassInfoOfStudentClassByYear(studentId, selectedClassGradeId,
                                rejoinedDate);

                if ((currentGradeDescription.equalsIgnoreCase(slectedClassGradeObj.getDescription()))
                        && selectedYear != futureYear) {
                    if (x == VAL_THREE) {
                        indexVal = ACTIVE_INDEX;
                        break;
                    } else {
                        if (!isSeleStudentTermExist) {
                            // assign subject term marks as default 0.0
                            indexVal = 2;
                            assignClassSubjectTermMarks(rejoinedDate, slectedClassGradeObj, student,
                                    exsistStudentClassInfoObjList, assignNewClass, termId, gradeSubjectId);
                        }
                    }
                } else {
                    // if selected student ,not having assigned to a class
                    if (currStudentClassInfoId == 0) {
                        if (selectedYear != futureYear) {
                            if (x == VAL_THREE) {
                                indexVal = ACTIVE_INDEX;
                                break;
                            } else if (isSeleStudentTermExist) {
                                break;
                            } else {
                                indexVal = 2;
                                if (nowExsistStudentClassInfoObjList.isEmpty()) {
                                    assignNewClass =
                                            assignNewGradeClassCoreSubjects(rejoinedDate, student,
                                                    slectedClassGradeObj, termId, gradeSubjectId);

                                } else if (assignNewClass) {
                                    assignClassSubjectTermMarks(rejoinedDate, slectedClassGradeObj, student,
                                            nowExsistStudentClassInfoObjList, assignNewClass, termId, gradeSubjectId);
                                }
                            }
                        } else {
                            if (x == VAL_THREE) {

                                indexVal = 2;
                                // future year grade class assignment
                                assignFutureYearGradeClass(rejoinedDate, student, slectedClassGradeObj,
                                        nowExsistStudentClassInfoObjList);

                            } else {
                                indexVal = SKIP_YEAR_VALUE;
                                break;
                            }
                        }
                    } else {
                        if (selectedYear == futureYear) {
                            indexVal = 2;

                            // future year grade class assignment
                            assignFutureYearGradeClass(rejoinedDate, student, slectedClassGradeObj,
                                    nowExsistStudentClassInfoObjList);

                        } else {
                            if ((currGradeId == selectedGradeId) && (x != VAL_THREE) && (!completedFlag)) {
                                indexVal = 2;
                                assignClassSubjectTermMarks(rejoinedDate, slectedClassGradeObj, student,
                                        exsistStudentClassInfoObjList, assignNewClass, termId, gradeSubjectId);
                            } else {
                                // if student is going to assign same grade ,but marking is completed.
                                if ((currGradeId == selectedGradeId) && (completedFlag)) {
                                    indexVal = 1;
                                    break;
                                }
                                // not having in same grade
                                if (currGradeId != selectedGradeId) {
                                    indexVal = 0;
                                    break;
                                }
                                if (assignNewClass) {
                                    // save core subject with default marks, example: marks 0.0
                                    indexVal = 2;
                                    assignClassSubjectTermMarks(rejoinedDate, slectedClassGradeObj, student,
                                            exsistStudentClassInfoObjList, assignNewClass, termId, gradeSubjectId);
                                }
                            }
                        }
                    }
                }
            } // end of subjects loop
        }// end of term loop
        return indexVal;
    }

    /**
     * method to assign student class grade subject and term marks.
     *
     * @param rejoinedDate - rejoinedDate.
     * @param slectedClassGradeObj - slectedClassGradeObj.
     * @param student - student.
     * @param nowExsistStudentClassInfoObjList - nowExsistStudentClassInfoObjList.
     * @param assignNewClass - assignNewClass.
     * @param termId - termId.
     * @param gradeSubjectId - gradeSubjectId.
     * @throws AkuraAppException SMS Exceptions
     */
    private void assignClassSubjectTermMarks(Date rejoinedDate, ClassGrade slectedClassGradeObj, Student student,
            List<StudentClassInfo> nowExsistStudentClassInfoObjList, boolean assignNewClass, int termId,
            int gradeSubjectId) throws AkuraAppException {

        assignGradeClassCoreSubjects(rejoinedDate, student, slectedClassGradeObj, termId, gradeSubjectId,
                nowExsistStudentClassInfoObjList, assignNewClass);

        saveStudentTermMarks(termId, gradeSubjectId, nowExsistStudentClassInfoObjList, rejoinedDate);
    }

    /**
     * method to get current student class information list and get id.
     *
     * @param studentId - studentId.
     * @param curYear - curYear.
     * @param currentClassGradeId - currentClassGradeId.
     * @return exsistStudentClassInfoObjList .
     * @throws AkuraAppException SMS Exceptions
     */
    private List<StudentClassInfo> getExsistStudentClassList(int studentId, Date curYear, int currentClassGradeId)
            throws AkuraAppException {

        return studentService.getStudentClassInfoOfStudentClassByYear(studentId, currentClassGradeId, curYear);
    }

    /**
     * method to get current student class information list and get id.
     *
     * @param studentId - studentId.
     * @param curYear - curYear.
     * @param currentClassGradeId - currentClassGradeId.
     * @return currStudentClassInfoId .
     * @throws AkuraAppException SMS Exceptions
     */
    private int getCurrentStudentClassList(int studentId, Date curYear, int currentClassGradeId)
            throws AkuraAppException {

        List<StudentClassInfo> currStudentClassInfoObjList =
                studentService.getStudentClassInfoOfStudentClassByYear(studentId, currentClassGradeId, curYear);

        int currStudentClassInfoId = 0;
        if (!currStudentClassInfoObjList.isEmpty()) {
            currStudentClassInfoId = currStudentClassInfoObjList.get(0).getStudentClassInfoId();
        }
        return currStudentClassInfoId;
    }

    /**
     * method to assign into new class and Core Subjects for student.
     *
     * @param student - student.
     * @param rejoinedDate - rejoinedDate.
     * @param existStudentClassInfoList - existStudentClassInfoList.
     * @param slectedClassGradeObj - slectedClassGradeObj.
     * @throws AkuraAppException SMS Exceptions
     */
    private void assignFutureYearGradeClass(Date rejoinedDate, Student student, ClassGrade slectedClassGradeObj,
            List<StudentClassInfo> existStudentClassInfoList) throws AkuraAppException {

        if (existStudentClassInfoList.isEmpty()) {
            StudentClassInfo studentClassInfo = new StudentClassInfo();

            studentClassInfo.setClassGrade(slectedClassGradeObj);
            studentClassInfo.setStudent(student);
            studentClassInfo.setYear(rejoinedDate);
            studentClassInfo.setCheckMonitor(false);

            // assign student into new class
            studentService.saveStudentClassInfoObj(studentClassInfo);
        } else {

            List<StudentClassInfo> studentClassNewList = new ArrayList<StudentClassInfo>();

            for (StudentClassInfo st : existStudentClassInfoList) {
                st.setStudent(student);
                st.setClassGrade(slectedClassGradeObj);
                st.setYear(rejoinedDate);

                // add current student class object into list
                studentClassNewList.add(st);
            }

            if (!studentClassNewList.isEmpty()) {
                // update exist student class object
                studentService.updateStudentClassInfoObjects(studentClassNewList);
            }

        }
    }

    /**
     * method to assign into new class and Core Subjects for student.
     *
     * @param student - student.
     * @param rejoinedDate - rejoinedDate.
     * @param termId - termId.
     * @param gradeSubjectId - gradeSubjectId.
     * @param slectedClassGradeObj - slectedClassGradeObj.
     * @return assignNewClass .
     * @throws AkuraAppException SMS Exceptions
     */
    private boolean assignNewGradeClassCoreSubjects(Date rejoinedDate, Student student,
            ClassGrade slectedClassGradeObj, int termId, int gradeSubjectId) throws AkuraAppException {

        StudentClassInfo studentClassInfo = new StudentClassInfo();

        studentClassInfo.setClassGrade(slectedClassGradeObj);
        studentClassInfo.setStudent(student);
        studentClassInfo.setYear(rejoinedDate);
        studentClassInfo.setCheckMonitor(false);

        // assign student into new class
        studentService.saveStudentClassInfoObj(studentClassInfo);

        StudentTermMark studentTermMark = new StudentTermMark();
        // set new class grade info id
        studentTermMark.setStudentClassInfoId(studentClassInfo.getStudentClassInfoId());
        studentTermMark.setGradeSubjectId(gradeSubjectId);
        studentTermMark.setTermId(termId);

        // save student term mark object
        studentService.setSubjectMark(studentTermMark);
        return true;
    }

    /**
     * method to update into new class and Core Subjects for student.
     *
     * @param student - student.
     * @param rejoinedDate - rejoinedDate.
     * @param termId - termId.
     * @param gradeSubjectId - gradeSubjectId.
     * @param exsistClassInfoObjList - exsistClassInfoObjList
     * @param slectedClassGradeObj - slectedClassGradeObj.
     * @param assignNewClass - assignNewClass.
     * @throws AkuraAppException SMS Exceptions
     */
    private void assignGradeClassCoreSubjects(Date rejoinedDate, Student student, ClassGrade slectedClassGradeObj,
            int termId, int gradeSubjectId, List<StudentClassInfo> exsistClassInfoObjList, boolean assignNewClass)
            throws AkuraAppException {

        List<StudentClassInfo> studentClassNewList = new ArrayList<StudentClassInfo>();

        // check current year has record
        if (!exsistClassInfoObjList.isEmpty() && !assignNewClass) {

            for (StudentClassInfo st : exsistClassInfoObjList) {
                st.setStudent(student);
                st.setClassGrade(slectedClassGradeObj);
                st.setYear(rejoinedDate);

                // add current student class object into list
                studentClassNewList.add(st);
            }

            if (!studentClassNewList.isEmpty()) {

                // update exists student class object
                studentService.updateStudentClassInfoObjects(studentClassNewList);
            }

        }

    }

    /**
     * method to update into student term marks for student.
     *
     * @param termId - termId.
     * @param gradeSubjectId - gradeSubjectId.
     * @param exsistClassInfoObjList - exsistClassInfoObjList
     * @param rejoinedDate - rejoinedDate.
     * @throws AkuraAppException SMS Exceptions
     */
    private void saveStudentTermMarks(int termId, int gradeSubjectId, List<StudentClassInfo> exsistClassInfoObjList,
            Date rejoinedDate) throws AkuraAppException {

        int currentClassGradeId;
        int currentYear = DateUtil.currentYearOnly();
        if (exsistClassInfoObjList.isEmpty()) {
            currentClassGradeId = 0;
        } else {
            currentClassGradeId = exsistClassInfoObjList.get(0).getStudentClassInfoId();
        }

        boolean isCurrStudentTermExist =
                studentService.isMarkingCompletedForTerm(currentClassGradeId, termId, currentYear);

        // assigned core subjects , and term
        if (!exsistClassInfoObjList.isEmpty() && !isCurrStudentTermExist) {

            StudentTermMark studentTermMark = new StudentTermMark();
            // set new class grade info id
            studentTermMark.setStudentClassInfoId(currentClassGradeId);
            studentTermMark.setGradeSubjectId(gradeSubjectId);
            studentTermMark.setTermId(termId);

            // save student term mark object
            studentService.setSubjectMark(studentTermMark);

        }
    }

    /**
     * Extend temporary leave, when he is on temporary leave.
     *
     * @param modelMap - a ModelMap that contains information of the re join - extend Student leave.
     * @param request - request of type HttpServletRequest.
     * @return the student temporary leave view.
     * @throws AkuraAppException - The exception details that occurred when re join temporary leave student
     *         instance.
     */
    @RequestMapping(value = REQUEST_MAP_EXTEND_STUDENT_TEMP_HTM)
    public String extendTemporaryStudent(final ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {

        StudentTemporaryLeave studentTemplate = new StudentTemporaryLeave();
        StudentTemporaryLeave studentTemporaryLeave = null;

        String registrationNo = request.getParameter(REQUEST_SELECTED_STUDENT_ID);

        int studentId = Integer.parseInt(registrationNo);

        // get student temporary leave list
        List<StudentTemporaryLeave> studentTemporaryLeavelist = 
        		SortUtil.sorttemporaryLeaveStudentListByDate(studentService
						.getStudentTempLeaveListByStudentId(studentId));

        if (!studentTemporaryLeavelist.isEmpty()) {
            // get last updated leave object
            studentTemporaryLeave = studentTemporaryLeavelist.get(0);
        }

        if (studentTemporaryLeave != null) {
            // set from , to date and reason to the template
            studentTemplate.setFromDate(studentTemporaryLeave.getFromDate());
            studentTemplate.setToDate(studentTemporaryLeave.getToDate());
            studentTemplate.setReason(studentTemporaryLeave.getReason());
        }

        modelMap.addAttribute(MODEL_TEMP_LEAVE_LIST_COMMAND, studentTemplate);
        request.setAttribute(REQUEST_STATE, Boolean.TRUE);
        request.setAttribute(REQUEST_SUCCESS, Boolean.TRUE);
        modelMap.addAttribute(MODEL_ATT_SUCCESS_ACTIVE_MSG, Boolean.TRUE);
        return VIEW_PAGE_STUDENT_ACTIVE_STUDENT_TEMPORARY_LEAVE;
    }

    /**
     * save extended temporary leave student , when he is on temporary leave.
     *
     * @param studentTemporaryLeave - containing studentTemporaryLeave attribute to which data was bound
     * @param request - request of type HttpServletRequest
     * @param model - model.
     * @param session - session of user.
     * @return viewPage - viewPage of jsp.
     * @throws AkuraAppException - The exception details that occurred when re join temporary leave student
     *         instance.
     */
    @RequestMapping(value = REQUEST_MAP_SAVE_EXTENDED_STUDENT_TEMP_HTM)
    public String saveExtendedTemporaryStudent(HttpServletRequest request, final ModelMap model,
            @ModelAttribute(MODEL_TEMP_LEAVE_LIST_COMMAND) final StudentTemporaryLeave studentTemporaryLeave,
            final HttpSession session) throws AkuraAppException {

        String viewPage = VIEW_PAGE_STUDENT_ACTIVE_STUDENT_TEMPORARY_LEAVE;

        Date getToDate = null;
        Date getFromDate = null;

        if (studentTemporaryLeave != null) {
            getToDate = studentTemporaryLeave.getToDate();
            getFromDate = studentTemporaryLeave.getFromDate();
        }

        if (getToDate == null) {
            String dateError =
                    new ErrorMsgLoader().getErrorMessage(STUDENT_TEMPEPORY_LEAVE_EXTEND_TO_DATE_ERROR_MESSAGE);
            model.addAttribute(MODEL_MESSAGE, dateError);
            request.setAttribute(REQUEST_STATE, Boolean.TRUE);
            model.addAttribute(MODEL_ATT_SUCCESS_ACTIVE_MSG, Boolean.TRUE);

        } else {

            Date dateToDate = DateUtil.getParseDate(DateUtil.getFormatDate(getToDate));
            Date dateFromdate = DateUtil.getParseDate(DateUtil.getFormatDate(getFromDate));

            if (dateToDate.before(dateFromdate)) {
                String message = new ErrorMsgLoader().getErrorMessage(REF_UI_DATE_FIELD_INCORRECT);
                model.addAttribute(MODEL_MESSAGE, message);
                request.setAttribute(REQUEST_STATE, Boolean.TRUE);
                model.addAttribute(MODEL_ATT_SUCCESS_ACTIVE_MSG, Boolean.TRUE);

            } else {

                
                int studentId = Integer.parseInt(request.getParameter(REQUEST_SELECTED_STUDENT_ID));

                Student student = studentService.findStudent(studentId);
                List<StudentTemporaryLeave> studentTemporaryLeavelist = 
                		SortUtil.sorttemporaryLeaveStudentListByDate(studentService
        						.getStudentTempLeaveListByStudentId(studentId));
                                                        
                Date extendToDate = studentTemporaryLeavelist.get(0).getToDate();
                
                Calendar fromDate = Calendar.getInstance();
                fromDate.setTime(getFromDate);

                Calendar toDate = Calendar.getInstance();
                toDate.setTime(getToDate);

                // get holiday list
                List<Holiday> holidayList = getHolidayList(getFromDate, getToDate);

                // get number of working days
                int workingDays = HolidayUtil.countWorkingDays(fromDate, toDate, holidayList);

                // save extended student temporary lave object
                studentService.saveExtendedTemporaryStudent(studentId, workingDays, getToDate);

                List<StudentTemporaryLeave> studentTempLeaveList =
                        studentService.getStudentTempLeaveListByStudentId(studentId);
                model.addAttribute(REQUEST_MAP_TEMP_LEAVE_LIST, studentTempLeaveList);

                // student leave object
                StudentLeave studentLeave = null;

                // get student leave list
                List<StudentLeave> studentLeaveList = studentService.getStudentLeaveListByStudentId(studentId);

                if (!studentLeaveList.isEmpty()) {
                    // take the last record of student leave - to be updated this record
                    studentLeave = studentLeaveList.get(studentLeaveList.size() - 1);
                }

                // update student leave object
                if (studentLeave != null) {
                    studentLeave.setToDate(getToDate);
                    studentLeave.setNoOfDays(workingDays);

                    // update student leave object with to date ,number of working days.
                    studentService.updateStudentLeave(studentLeave);
                    
                }
                                    
                if(dateToDate.before(extendToDate)){                	
                    model.addAttribute(EXTENDED_MSG, new ErrorMsgLoader()
                    .getErrorMessage(THE_TEMPERORY_LEAVE_IS_CURTAILED_SUCCESSFULLY));  
                    // send a mail to the student's parent by the logged user - Administrator.
                    this.sendConfirmationMail(studentId, model, session, student, EXTEND_TEMPORARY_LEAVE,
                            studentTemporaryLeave); 
                } 
                
                if(dateToDate.after(extendToDate)){                	
                    model.addAttribute(EXTENDED_MSG, new ErrorMsgLoader()
                    .getErrorMessage(THE_TEMPERATURE_LEAVE_IS_EXTENDED_SUCCESSFULLY));
                    // send a mail to the student's parent by the logged user - Administrator.
                    this.sendConfirmationMail(studentId, model, session, student, EXTEND_TEMPORARY_LEAVE,
                            studentTemporaryLeave);
                }
                
                request.setAttribute(REQUEST_STATE, Boolean.TRUE);
                request.setAttribute(REQUEST_SUCCESS_MSG, Boolean.TRUE);
                model.addAttribute(MODEL_ATT_SUCCESS_ACTIVE_MSG, Boolean.FALSE);
                request.setAttribute(REQUEST_SUCCESS, Boolean.TRUE);
            }
        }
        return viewPage;
    }

    /**
     * get number of working days.
     *
     * @param startDate - startDate.
     * @param endDate - endDate.
     * @return holidayList - holidayList list of holiday.
     * @throws AkuraAppException - The exception details that occurred when re join temporary leave student
     *         instance.
     */
    private List<Holiday> getHolidayList(Date startDate, Date endDate) throws AkuraAppException {

        String strYr = DateUtil.getStringYear(startDate);

        String strStartDate = strYr + START_DATE;
        String strEndDate = strYr + END_DATE;

        Date startDateToSearch = DateUtil.getParseDate(strStartDate);
        Date endDateToSearch = DateUtil.getParseDate(strEndDate);

        return commonService.findHolidayByYear(startDateToSearch, endDateToSearch);
    }

    /**
     * Send the confirmation mail.
     *
     * @param studentIdVal - Student Id
     * @param model - model
     * @param session - a session to pass values.
     * @param student - Student object
     * @param content - Date Starting date.
     * @param studentTemporaryLeave - studentTemporaryLeave.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    private void sendConfirmationMail(int studentIdVal, ModelMap model, final HttpSession session, Student student,
            String content, StudentTemporaryLeave studentTemporaryLeave) throws AkuraAppException {

        String str = null;
        // String strFromAddress = AkuraWebConstant.EMPTY_STRING;

        try {
            // get student's parent list
            List<StudentParent> studentParent = studentService.getParentId(studentIdVal);

            if (studentParent.size() > 0) {
                // get student's parent object
                StudentParent studentParentnew = studentParent.get(0);

                // get parent object
                Parent parent = studentParentnew.getParent();

                // if having parent or not
                if (!(parent == null)) {

                    // get parent mail address for sending
                    String strToemail = parent.getEmail();

                    if (!(strToemail.equals(AkuraConstant.EMPTY_STRING))) {

                        UserLogin userLogin = null;

                        // get logged user details with his email address
                        if (session.getAttribute(SESSION_USER) != null) {
                            UserInfo userInfo = (UserInfo) session.getAttribute(SESSION_USER);
                            String logginUserName = userInfo.getUsername();
                            userLogin = userService.getUserByName(logginUserName);

                        }

                        if (userLogin != null) {
                            String strFromAddress = userLogin.getEmail();

                            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
                            String toDateWithoutTime = sdf.format(studentTemporaryLeave.getToDate());
                            String fromDateWithoutTime = sdf.format(studentTemporaryLeave.getFromDate());
                            String strReason = studentTemporaryLeave.getReason();

                            String templateName =
                                    PropertyReader.getPropertyValue(EMAIL, EMAIL_TEMPLATE_STUDENT_TEMP_LEAVE_DETAILS);

                            Map<String, Object> mapObjectMap = new HashMap<String, Object>();

                            // assign email parameters for email template
                            mapObjectMap.put(MAP_TODATE, toDateWithoutTime);
                            mapObjectMap.put(MAP_FROMDATE, fromDateWithoutTime);
                            mapObjectMap.put(MAP_REASON, strReason);

                            mapObjectMap.put(MESSAGE_TOPIC, MESSAGE_TOPIC);
                            mapObjectMap.put(F_NAME, parent.getFullName());
                            mapObjectMap.put(STF_NAME, student.getFullName());
                            mapObjectMap.put(CONTENT2, content);
                            // Include email header information
                            EmailUtil.addHeaderForEmail(mapObjectMap);

                            // call to be set e mail properties and get mail bean object
                            CommonEmailBean commonEmailBean =
                                    studentService.setEmailProperties(strFromAddress, strToemail, templateName,
                                            mapObjectMap, new ErrorMsgLoader()
                                                    .getErrorMessage(EMAIL_SUBJECT_TEMPORARY_LEAVE_EXTENDED));

                            // pass mail bean object to sent mail to student's parent.
                            emailService.sendMail(commonEmailBean);

                            str = new ErrorMsgLoader().getErrorMessage(SUCESS_EMAIL);

                            model.addAttribute(MODEL_ATT_SUCMESSAGE, str);
                        }
                    } else {
                        str = new ErrorMsgLoader().getErrorMessage(FAILURE_EMAIL_PARENT_EMAIL);
                        model.addAttribute(MODEL_MESSAGE, str);
                    }
                } else {
                    str = new ErrorMsgLoader().getErrorMessage(FAILURE_EMAIL_PARENT_NOT_EXIST);
                    model.addAttribute(MODEL_MESSAGE, str);
                }
            } else {
                str = new ErrorMsgLoader().getErrorMessage(FAILURE_EMAIL_PARENT_EXIXTS);
                model.addAttribute(MODEL_MESSAGE, str);
            }
        } catch (MailException e) {

            str = new ErrorMsgLoader().getErrorMessage(FAILURE_EMAIL_SERVER_ERROR);
            model.addAttribute(MODEL_MESSAGE, str);

        }

    }

}
