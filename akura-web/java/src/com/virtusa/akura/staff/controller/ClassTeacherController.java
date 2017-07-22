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

package com.virtusa.akura.staff.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClassTeacher;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * This controller where controls class Teacher reference data.
 *
 * @author Virtusa Corporation
 */
@Controller
public class ClassTeacherController {

    /** Constant for error message for exists. */
    private static final String REF_UI_CLASSTEACHER_EXIST = "REF.UI.CLASSTEACHER.EXIST";

    /** Constant for model attribute selected object. */
    private static final String SELECTED_OBJ = "selectedObj";

    /** Constant for string error message. */
    private static final String CLASSTEACHER_SEARCH_NO_RESULT = "CLASSTEACHER.SEARCH.NO.RESULT";

    /** Constant for request parameter selected edit/delete. */
    private static final String REQUEST_PARA_SELECT_VAL = "selectVal";

    /** Constant for model attribute staff id. */
    private static final String MODEL_ATT_SELECTED_STAFF_ID_VAL = "selectedStaffIdVal";

    /** Constant for model attribute class grade id. */
    private static final String MODEL_ATT_SELECTED_CLASS_GRADE_VAL = "selectedClassGradeVal";

    /** Constant for model attribute year. */
    private static final String MODEL_ATT_SELECTED_YEAR_VAL = "selectedYearVal";

    /** key to hold string of request state. */
    private static final String REQUEST_STATE = "state";

    /** Constant for String 2. */
    private static final String STRING_2 = "2";

    /** Constant for String 1. */
    private static final String STRING_1 = "1";

    /** This query string will be added after Adding new class teacher. */
    private static final String SUCCESS_1 = "?success=1";

    /** This query string will be added after updating class teacher. */
    private static final String SUCCESS_2 = "?success=2";

    /** Represents the log message for the parse exception. */
    private static final String PARSE_EXCEPTION = "Parse Exception";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(ClassTeacherController.class);

    /** attribute for year + 01-01 . */
    private static final String YEAR_01_01 = "-01-01";

    /** attribute for year zero . */
    private static final String YEAR_ZERO = "0";

    /** view post method class teacher allocation. */
    private static final String VIEW_POST_CLASS_TEACHER_ALLOCATION = "redirect:classTeacherAllocation.htm";

    /** attribute of error message for already exists field. */
    private static final String ERROR_MESSAGE_ALREADY_ASSIGN = "REF.UI.CLASSTEACHER.ALREADY.ASSIGN";

    /** attribute of error message for delete class teacher field. */
    private static final String ERROR_MESSAGE_CLASS_TEACHER_DELETE = "REF.UI.CLASSTEACHER.DELETE";

    /** attribute of message for delete class teacher field. */
    private static final String ERROR_MESSAGE_CLASS_TEACHER_ADDED = "REF.UI.CLASSTEACHER.ADDED";

    /** attribute of error message for delete class teacher field. */
    private static final String ERROR_MESSAGE_CLASS_TEACHER_UPDATED = "REF.UI.CLASSTEACHER.UPDATED";

    /** model attribute of assign message. */
    private static final String MODEL_ATT_ASSGIN_MESSAGE = "assginMessage";

    /** get parameter attribute of class teacher year. */
    private static final String GET_PARA_CLASS_TEACHER_YEAR = "classTeacherYear";

    /** get parameter attribute of last name. */
    private static final String GET_PARA_LAST_NAME = "lName";

    /** model attribute of search class teacher. */
    private static final String MODEL_ATT_SEARCH_CLASS_TEACHER = "searchClassTeacher";

    /** model attribute of show edit. */
    private static final String MODEL_ATT_SHOW_EDIT = "showEdit";

    /** model attribute of search message. */
    private static final String MODEL_ATT_SEARCH_MESSAGE = "searchMessage";

    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message";

    /** attribute for holding year. */
    private static final String YEAR_FORMAT = "yyyy";

    /** get parameter attribute of class grade. */
    private static final String GET_PARA_CLASS_GRADE = "classGrade";

    /** get parameter attribute of year. */
    private static final String GET_PARA_YEAR = "year";

    /** view get method class teacher allocation. */
    private static final String VIEW_GET_CLASS_TEACHER_ALLOCATION = "staff/classTeacherAllocation";

    /** attribute of error message for mandatory field. */
    private static final String ERROR_MESSAGE_REQUIRED_FIELD = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** model attribute of Year List. */
    private static final String MODEL_ATT_EDIT_YEAR_LIST = "editYearList";

    /** model attribute of class teacher. */
    private static final String MODEL_ATT_CLASS_TEACHER = "classTeacher";

    /** model attribute of year list. */
    private static final String MODEL_ATT_YEAR_LIST = "yearList";

    /** model attribute of staff list. */
    private static final String MODEL_ATT_STAFF_LIST = "staffList";

    /** model attribute of current year. */
    private static final String MODEL_ATT_CURRENT_YEAR = "currentYear";

    /** model attribute of class grade list. */
    private static final String MODEL_ATT_CLASS_GRADE_LIST = "classGradeList";

    /** request mapping value for manage staff class teacher. */
    private static final String REQ_MAP_STAFF_CLASS_TEACHER = "/staffSearchClassTeacher.htm";

    /** request mapping value for manage class teacher. */
    private static final String REQ_MAP_CLASS_TEACHER = "/classTeacherAllocation.htm";

    /** request mapping value for manage delete class teacher. */
    private static final String REQ_MAP_DELETE_CLASS_TEACHER = "/staffDeleteClassTeacher.htm";

    /** request mapping value for save or update class teacher. */
    private static final String REQ_MAP_SAVE_UPDATE_CLASS_TEACHER = "/staffSaveOrUpdateClassTeacher.htm";

    /** model attribute of current year. */
    private static final String MODEL_ATT_THIS_YEAR = "thisYear";

    /** model attribute of current year. */
    private static final String MODEL_ATT_CLASS_GRADE = "selectedClass";

    /** attribute for holding year-month-day. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Represents an instance of StaffService.
     */
    private StaffService staffService;

    /**
     * Represents an instance of SchoolService.
     */
    private CommonService commonService;

    /**
     * Sets an instance of StaffService.
     *
     * @param staffServiceVal - the relevant instance of StaffService
     */
    public void setStaffService(StaffService staffServiceVal) {

        staffService = staffServiceVal;
    }

    /**
     * Sets an instance of CommonService.
     *
     * @param commonServiceVal - the relevant instance of CommonService
     */
    public void setCommonService(CommonService commonServiceVal) {

        commonService = commonServiceVal;
    }

    /**
     * Registers the given custom property editor for all properties of the Date type.
     *
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.getBindingResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * Returns a list of years.
     *
     * @return gradeClassList - a list of years.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_YEAR_LIST)
    public List<String> populateYearList() throws AkuraAppException {

        List<String> yearList = new ArrayList<String>();
        final int maxYear = 5;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        yearList.add(String.valueOf(year + 1));
        yearList.add(String.valueOf(year));
        for (int index = 1; index < maxYear; index++) {
            int newYear = year - index;
            yearList.add(String.valueOf(newYear));
        }
        return yearList;
    }

    /**
     * Returns a list of Staff.
     *
     * @return gradeList - a list of Staff.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_STAFF_LIST)
    public List<Staff> populateAcademicStaffList() throws AkuraAppException {

        Collection<Staff> academicStaff = staffService.viewAllStaff();
        List<Staff> staffList = new ArrayList<Staff>();
        for (Staff staff : academicStaff) {
            StaffCategory c = staff.getStaffCategory();

            if (c.isAcademic() && staff.getDateOfDeparture() == null) {
                staffList.add(staff);
            }
        }
        return SortUtil.sortStaffList(staffList);
    }

    /**
     * get current year.
     *
     * @return Date object
     * @throws AkuraAppException - throw this
     */
    @ModelAttribute(MODEL_ATT_CURRENT_YEAR)
    public List<String> currentYear() throws AkuraAppException {

        return DateUtil.getFutureYears(2);
    }

    /**
     * Returns a list of classGrades.
     *
     * @return gradeClassList - a list of classGrades.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_CLASS_GRADE_LIST)
    public List<ClassGrade> populateGradeClassList() throws AkuraAppException {

        return SortUtil.sortClassGradeList(commonService.getClassGradeList());
    }

    /**
     * Initializes the reference data that is to be previewed on the view.
     *
     * @param model - a HashMap that contains information of the ClassTeacher
     * @param success - This parameter set only after happened class teacher add or update
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(REQ_MAP_CLASS_TEACHER)
    public String manageClassTeacher(ModelMap model, @RequestParam(required = false) String success)
            throws AkuraAppException {

        if (STRING_1.equals(success)) {
            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_CLASS_TEACHER_ADDED);
            model.addAttribute(MODEL_ATT_ASSGIN_MESSAGE, message);

        } else if (STRING_2.equals(success)) {
            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_CLASS_TEACHER_UPDATED);
            model.addAttribute(MODEL_ATT_ASSGIN_MESSAGE, message);
        }
        ClassTeacher classTeacher = new ClassTeacher();

        int thisYear = DateUtil.currentYearOnly();

        model.addAttribute(MODEL_ATT_THIS_YEAR, thisYear);
        model.addAttribute(MODEL_ATT_CLASS_TEACHER, classTeacher);
        return VIEW_GET_CLASS_TEACHER_ALLOCATION;
    }

    /**
     * Searches results for the given year, last name of the staff and the class grade criteria.
     *
     * @param request - an object of HttpServletRequest
     * @param model - a HashMap that contains information of the ClassTeacher
     * @param result - containing list of errors and ClassTeacher instance to which data was bound
     * @param classTeacher - an instance of ClassTeacher
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception detailthat occurred when searching ClassTeacher instances.
     */
    @RequestMapping(REQ_MAP_STAFF_CLASS_TEACHER)
    public String searchClassTeacher(HttpServletRequest request,
            @ModelAttribute(MODEL_ATT_CLASS_TEACHER) final ClassTeacher classTeacher, BindingResult result,
            ModelMap model) throws AkuraAppException {

        String message;
        String classGrade = request.getParameter(GET_PARA_CLASS_GRADE);
        String lName = request.getParameter(GET_PARA_LAST_NAME);
        String year = request.getParameter(GET_PARA_YEAR);
        int thisYear = DateUtil.currentYearOnly();
        int intYear = (year != null) ? Integer.parseInt(year) : thisYear;
        boolean showEdit = false;

        if (thisYear <= intYear) {
            showEdit = true;
        }
        List<ClassTeacher> classTeacherList = staffService.searchClassTeacher(classGrade, lName, year);

        if (classTeacherList.isEmpty()) {
            message = new ErrorMsgLoader().getErrorMessage(CLASSTEACHER_SEARCH_NO_RESULT);
            model.addAttribute(MODEL_ATT_SEARCH_MESSAGE, message);
        } else {
            Map<Integer, String> editYearList = new LinkedHashMap<Integer, String>();
            for (ClassTeacher c : classTeacherList) {
                SimpleDateFormat dateformat = new SimpleDateFormat(YEAR_FORMAT);
                String date = dateformat.format(c.getYear());
                editYearList.put(c.getClassTeacherId(), date);
            }
            model.addAttribute(MODEL_ATT_SEARCH_CLASS_TEACHER, SortUtil.sortClassTeacherList(classTeacherList));
            model.addAttribute(MODEL_ATT_EDIT_YEAR_LIST, editYearList);
        }
        model.addAttribute(MODEL_ATT_CLASS_GRADE, classGrade);
        model.addAttribute(MODEL_ATT_THIS_YEAR, year);
        model.addAttribute(MODEL_ATT_SHOW_EDIT, showEdit);

        return VIEW_GET_CLASS_TEACHER_ALLOCATION;
    }

    /**
     * Saves or updates the relevant instance of ClassTeacher.
     *
     * @param request - an object of HttpServletRequest
     * @param result - containing list of errors and ClassTeacher instance to which data was bound
     * @param model - a HashMap that contains information of the ClassTeacher
     * @param classTeacher - an instance of ClassTeacher
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when saving or updating ClassTeacher
     *         instances.
     */
    @RequestMapping(REQ_MAP_SAVE_UPDATE_CLASS_TEACHER)
    public String saveOrUpdateClassTeacher(@ModelAttribute(MODEL_ATT_CLASS_TEACHER) ClassTeacher classTeacher,
            BindingResult result, HttpServletRequest request, ModelMap model) throws AkuraAppException {

        String message;
        String year =
                (request.getParameter(GET_PARA_CLASS_TEACHER_YEAR) != null) ? request
                        .getParameter(GET_PARA_CLASS_TEACHER_YEAR) : AkuraWebConstant.STRING_ZERO;

        model.addAttribute(MODEL_ATT_THIS_YEAR, request.getParameter(GET_PARA_YEAR));
        String reqClassGrade = request.getParameter(GET_PARA_CLASS_GRADE);

        ClassTeacher classTeacherObj = staffService.findClassTeacher(classTeacher.getClassTeacherId());

        if (year.equals(YEAR_ZERO) || classTeacher.getStaff().getStaffId() == 0
                || classTeacher.getClassGrade().getClassGradeId() == 0) {
            model.addAttribute(MODEL_ATT_CLASS_GRADE, reqClassGrade);
            message = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_REQUIRED_FIELD);
            model.addAttribute(MODEL_ATT_THIS_YEAR, request.getParameter(GET_PARA_YEAR));
            model.addAttribute(MODEL_ATT_SELECTED_YEAR_VAL, year);
            model.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_VAL, classTeacher.getClassGrade().getClassGradeId());
            model.addAttribute(MODEL_ATT_SELECTED_STAFF_ID_VAL, classTeacher.getStaff().getStaffId());
            request.setAttribute(REQUEST_STATE, Boolean.TRUE);
            model.addAttribute(MODEL_ATT_MESSAGE, message);

            model.addAttribute(MODEL_ATT_SEARCH_CLASS_TEACHER, SortUtil
                    .sortClassTeacherList(getClassTeacherList(request)));

            if (Boolean.parseBoolean(request.getParameter(REQUEST_PARA_SELECT_VAL))) {
                model.addAttribute(MODEL_ATT_SHOW_EDIT, Boolean.TRUE);
                model.addAttribute(SELECTED_OBJ, classTeacherObj);
            }

            return VIEW_GET_CLASS_TEACHER_ALLOCATION;
        } else {

            try {
                year = year + YEAR_01_01;
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                Date date = null;

                try {
                    date = (Date) formatter.parse(year);
                } catch (ParseException e) {
                    LOG.debug(PARSE_EXCEPTION);
                }
                classTeacher.setYear(date);
                int classGradeId = classTeacher.getClassGrade().getClassGradeId();
                int staffId = classTeacher.getStaff().getStaffId();
                ClassGrade classGrade = commonService.findClassGrade(classGradeId);
                Staff staff = staffService.findStaff(staffId);
                classTeacher.setClassGrade(classGrade);
                classTeacher.setStaff(staff);
                List<ClassTeacher> classTeachers = staffService.getClassTeacherListByYear(date);
                if (classTeacher.getClassTeacherId() > 0) {
                    return updateClassTeacher(classTeacher, model, date, classGrade, classTeachers);
                } else {
                    // This if,else statement was written in order to validate the departure date
                    // of the current Teacher and to assign a new class teacher for that
                    // Particular class as a new teacher
                    ClassTeacher currentClassTeacher =
                            staffService.getClassTeacher(classGrade, Integer.parseInt(request.getParameter(
                                    GET_PARA_CLASS_TEACHER_YEAR).toString()));
                    if (currentClassTeacher == null) {
                        return createNewClassTeacher(classTeacher, model, date, classTeachers);
                    } else {
                        int staffIdForGetCurrentStaffMember = currentClassTeacher.getStaff().getStaffId();
                        Staff currentStaffObject = staffService.findStaff(staffIdForGetCurrentStaffMember);
                        if (currentStaffObject.getDateOfDeparture() != null) {
                            return createNewClassTeacher(classTeacher, model, date, classTeachers);
                        } else {

                            int currentYear = DateUtil.currentYearOnly();
                            if (Integer.parseInt(request.getParameter(GET_PARA_YEAR)) == currentYear) {
                                model.addAttribute(MODEL_ATT_SHOW_EDIT, Boolean.TRUE);
                                request.setAttribute(REQUEST_STATE, Boolean.TRUE);
                            }

                            model.addAttribute(MODEL_ATT_CLASS_GRADE, reqClassGrade);
                            model.addAttribute(MODEL_ATT_THIS_YEAR, request.getParameter(GET_PARA_YEAR));
                            model.addAttribute(MODEL_ATT_SELECTED_YEAR_VAL, request
                                    .getParameter(GET_PARA_CLASS_TEACHER_YEAR));
                            model.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_VAL, classTeacher.getClassGrade()
                                    .getClassGradeId());
                            model.addAttribute(MODEL_ATT_SELECTED_STAFF_ID_VAL, classTeacher.getStaff().getStaffId());
                            model.addAttribute(MODEL_ATT_SEARCH_CLASS_TEACHER, SortUtil
                                    .sortClassTeacherList(getClassTeacherList(request)));
                            return updateClassTeacher(classTeacher, model, date, classGrade, classTeachers);
                        }
                    }
                }
            } catch (AkuraAppException e) {
                if (e.getCause() instanceof DataIntegrityViolationException) {
                    message = new ErrorMsgLoader().getErrorMessage(REF_UI_CLASSTEACHER_EXIST);
                    ClassTeacher newClassTeacher = new ClassTeacher();
                    model.addAttribute(MODEL_ATT_CLASS_TEACHER, newClassTeacher);
                    model.addAttribute(MODEL_ATT_MESSAGE, message);
                    return VIEW_GET_CLASS_TEACHER_ALLOCATION;
                } else {
                    throw e;
                }
            }
        }
    }

    /**
     * get class teacher list.
     *
     * @param request - request.
     * @return the return ClassTeacher List.
     * @throws AkuraAppException AkuraAppException.
     */
    private List<ClassTeacher> getClassTeacherList(HttpServletRequest request) throws AkuraAppException {

        String reqClassGrade = request.getParameter(GET_PARA_CLASS_GRADE);
        String reqLName = request.getParameter(GET_PARA_LAST_NAME);
        String reqYear = request.getParameter(GET_PARA_YEAR);

        return staffService.searchClassTeacher(reqClassGrade, reqLName, reqYear);
    }

    /**
     * Updates the class teacher.
     *
     * @param classTeacher the class teacher.
     * @param model the model.
     * @param date the date.
     * @param classGrade the class grade.
     * @param classTeachers the class teachers.
     * @return the retrun path.
     * @throws AkuraAppException AkuraAppException.
     */
    private String updateClassTeacher(ClassTeacher classTeacher, ModelMap model, Date date, ClassGrade classGrade,
            List<ClassTeacher> classTeachers) throws AkuraAppException {

        for (ClassTeacher addclassTeacher : classTeachers) {
            if (addclassTeacher.getClassTeacherId() != classTeacher.getClassTeacherId()) {
                if (addclassTeacher.getClassGrade().getClassGradeId() == classTeacher.getClassGrade().getClassGradeId()
                        && addclassTeacher.getStaff().getStaffId() == classTeacher.getStaff().getStaffId()
                        && addclassTeacher.getYear().equals(date)) {
                    checkAlreadyAssignClassGradeYear(model);
                    return VIEW_GET_CLASS_TEACHER_ALLOCATION;
                }
            }
            if (addclassTeacher.getClassTeacherId() != classTeacher.getClassTeacherId()) {
                if (classTeacher.getStaff().getNameWithIntials()
                        .equals(addclassTeacher.getStaff().getNameWithIntials())) {
                    if (classTeacher.getYear().equals(date)) {
                        if (addclassTeacher.getClassGrade().getDescription().equals(classGrade.getDescription())) {
                            checkAlreadyAssignClassGradeYear(model);
                            return VIEW_GET_CLASS_TEACHER_ALLOCATION;
                        }
                    }
                } else {
                    if (addclassTeacher.getStaff().getDateOfDeparture() == null) {
                        if (classTeacher.getYear().equals(date)) {
                            if (addclassTeacher.getClassGrade().getDescription().equals(classGrade.getDescription())) {
                                checkAlreadyAssignClassGradeYear(model);
                                return VIEW_GET_CLASS_TEACHER_ALLOCATION;
                            }
                        }
                    }
                }
            }
        }
        staffService.editClassTeacher(classTeacher);
        return VIEW_POST_CLASS_TEACHER_ALLOCATION + SUCCESS_2;
    }

    /**
     * Creates new class teacher.
     *
     * @param classTeacher the class teacher.
     * @param model the model.
     * @param date the date.
     * @param classTeachers the class teachers.
     * @return the return path.
     * @throws AkuraAppException AkuraAppException.
     */
    private String createNewClassTeacher(ClassTeacher classTeacher, ModelMap model, Date date,
            List<ClassTeacher> classTeachers) throws AkuraAppException {

        for (ClassTeacher addclassTeacher : classTeachers) {
            if (addclassTeacher.getClassGrade().getClassGradeId() == classTeacher.getClassGrade().getClassGradeId()
                    && addclassTeacher.getStaff().getStaffId() == classTeacher.getStaff().getStaffId()
                    && addclassTeacher.getYear().equals(date)) {
                checkAlreadyAssignClassGradeYear(model);
                return VIEW_GET_CLASS_TEACHER_ALLOCATION;
            }
            if (addclassTeacher.getStaff().getStaffId() == classTeacher.getStaff().getStaffId()
                    && addclassTeacher.getYear().equals(date)
                    && addclassTeacher.getClassGrade().getClassGradeId() != classTeacher.getClassGrade()
                            .getClassGradeId()) {
                checkAlreadyAssignClassGradeYear(model);
                return VIEW_GET_CLASS_TEACHER_ALLOCATION;
            }
            if (addclassTeacher.getStaff().getStaffId() != classTeacher.getStaff().getStaffId()
                    && addclassTeacher.getYear().equals(date)
                    && addclassTeacher.getClassGrade().getClassGradeId() == classTeacher.getClassGrade()
                            .getClassGradeId() && (classTeacher.getStaff().getDateOfDeparture() != null)) {
                checkAlreadyAssignClassGradeYear(model);
                return VIEW_GET_CLASS_TEACHER_ALLOCATION;
            }
        }

        staffService.addClassTeacher(classTeacher);
        return VIEW_POST_CLASS_TEACHER_ALLOCATION + SUCCESS_1;
    }

    /**
     * check already assign to the relevant instance of ClassTeacher.
     *
     * @param model - a HashMap that contains information of the ClassTeacher
     */
    private void checkAlreadyAssignClassGradeYear(ModelMap model) {

        String message;
        message = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_ALREADY_ASSIGN);
        ClassTeacher newClassTeacher = new ClassTeacher();
        model.addAttribute(MODEL_ATT_CLASS_TEACHER, newClassTeacher);
        model.addAttribute(MODEL_ATT_MESSAGE, message);
    }

    /**
     * Deletes the relevant instance of ClassTeacher.
     *
     * @param result - containing list of errors and ClassTeacher instance to which data was bound
     * @param model - a HashMap that contains information of the ClassTeacher
     * @param classTeacher - an instance of ClassTeacher
     * @param request - request.
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting ClassTeacher instances.
     */
    @RequestMapping(REQ_MAP_DELETE_CLASS_TEACHER)
    public String deleteClassTeacher(@ModelAttribute(MODEL_ATT_CLASS_TEACHER) ClassTeacher classTeacher,
            BindingResult result, ModelMap model, HttpServletRequest request) throws AkuraAppException {

        String message;
        int id = classTeacher.getClassTeacherId();
        ClassTeacher deleteClassTeacher = staffService.findClassTeacher(id);

        String page = AkuraWebConstant.EMPTY_STRING;

        try {
            model.addAttribute(MODEL_ATT_SHOW_EDIT, Boolean.TRUE);
            staffService.deleteClassTeacher(deleteClassTeacher);
            page = VIEW_GET_CLASS_TEACHER_ALLOCATION;
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                message = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_CLASS_TEACHER_DELETE);
                ClassTeacher newClassTeacher = new ClassTeacher();
                model.addAttribute(MODEL_ATT_CLASS_TEACHER, newClassTeacher);
                model.addAttribute(MODEL_ATT_MESSAGE, message);

                page = VIEW_GET_CLASS_TEACHER_ALLOCATION;
            } else {
                throw e;
            }
        }
        model.addAttribute(MODEL_ATT_THIS_YEAR, request.getParameter(GET_PARA_YEAR));
        model.addAttribute(MODEL_ATT_SEARCH_CLASS_TEACHER, SortUtil.sortClassTeacherList(getClassTeacherList(request)));
        model.addAttribute(SELECTED_OBJ, deleteClassTeacher);

        return page;
    }
}
