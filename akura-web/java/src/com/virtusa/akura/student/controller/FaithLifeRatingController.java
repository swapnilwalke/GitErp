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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.virtusa.akura.api.dto.FaithLifeCategory;
import com.virtusa.akura.api.dto.FaithLifeComment;
import com.virtusa.akura.api.dto.FaithLifeGrading;
import com.virtusa.akura.api.dto.FaithLifeRating;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.delegate.LoginDelegate;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentProgressBarService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.student.validator.FaithLifeRatingValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * FaithLifeRatingController is to handle Add/Edit/Delete/View operations for FaithLife module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class FaithLifeRatingController {
    
    /**
     * session key to hold string studentGrade .
     */
    private static final String SESSION_ATT_STUDENT_GRADE = "studentGrade";
    
    /**
     * key to hold string selectedGrade.
     */
    private static final String SELECTED_GRADE = "selectedGrade";
    
    /**
     * key to hold string faithLifeCommentId.
     */
    private static final String FAITH_LIFE_COMMENT_ID = "faithLifeCommentId";
    
    /**
     * key to hold string faithLifeCommentDes.
     */
    private static final String FAITH_LIFE_COMMENT_DES = "faithLifeCommentDes";
    
    /** model attribute of studentGrade list. */
    private static final String MODEL_ATT_STUDENT_GRADE_LIST = "studentGradeList";
    
    /**
     * key to hold error message code when faithLife already exists.
     */
    private static final String REF_UI_FAITHLIFE_EXIST = "REF.UI.FAITHLIFE.EXIST";
    
    /**
     * key to hold error message to log.
     */
    private static final String ERROR_OCCURED_WHILE_DELETING_FAITH_LIFE_RATING_OBJECT =
            "FaithLifeRatingController - error occured while deleting FaithLifeRating object-->";
    
    /**
     * key to hold string "none".
     */
    private static final String NONE = "none";
    
    /**
     * key to hold string "EnableAddEditDelete".
     */
    private static final String ENABLE_ADD_EDIT_DELETE = "EnableAddEditDelete";
    
    /**
     * key to hold string averageAcademicLifeRating.
     */
    private static final String AVERAGE_ACADEMIC_LIFE_RATING = "averageAcademicLifeRating";
    
    /**
     * key to hold string averageFaithLifeRating.
     */
    private static final String AVERAGE_FAITH_LIFE_RATING = "averageFaithLifeRating";
    
    /** key to define the averageAttendanceRating. */
    private static final String AVERAGE_ATTENDANCE_RATING = "averageAttendanceRating";
    
    /** key to define the attendanceRating. */
    private static final String MODEL_ATT_ATTENDANCE_RATING = "attendanceRating";
    
    /**
     * key to hold string studentId.
     */
    private static final String STUDENT_ID = "studentId";
    
    /**
     * key to hold model attribute StudentClassId.
     */
    private static final String MODEL_ATT_SELECTED_ID = "selectedStudClassId";
    
    /**
     * key to hold string selectedValue.
     */
    private static final String SELECTED_VALUE = "selectedValue";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(FaithLifeRatingController.class);
    
    /** view get method create faithLifeRating. */
    private static final String VIEW_GET_FAITH_LIFE = "student/faithLife";
    
    /** request mapping value for manage save or update faithLife. */
    private static final String SAVE_OR_UPDATE_FAITHLIFE_HTM = "/saveOrUpdateFaithLifeRating.htm";
    
    /** request mapping value for delete faithLife. */
    private static final String DELETE_FAITHLIFE_HTM = "/deleteFaithLifeRating.htm";
    
    /** request mapping value for populate faithLife data. */
    private static final String POPULATE_FAITHLIFE_HTM = "/populateFaithLifeData.htm";
    
    /** request mapping value for populate faithLife comment. */
    private static final String POPULATE_FAITHLIFE_COMMENT_HTM = "/populateFaithLifeComment.htm";
    
    /** model attribute of faithLifeRating list. */
    private static final String MODEL_ATT_FAITH_LIFE_LIST = "faithLifeList";
    
    /** model attribute of faithLifeRating list. */
    private static final String MODEL_ATT_FAITH_LIFE_CATEGORY_LIST = "faithLifeCategoryList";
    
    /** model attribute of grading list. */
    private static final String MODEL_ATT_GRADING_LIST = "gradingList";
    
    /** model attribute of faithLifeRating object. */
    private static final String MODEL_ATT_FAITH_LIFE = "faithLifeRating";
    
    /** model attribute of massage. */
    private static final String MODEL_ATT_MESSAGE = "message";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_FAITH_LIFE_OBJECT = "selectedObj";
    
    /** model attribute of student faithLife average value. */
    private static final String MODEL_ATT_STUDENT_FAITH_LIFE = "averageFaithLife";
    
    /** model attribute of student academic life average value. */
    private static final String MODEL_ATT_STUDENT_ACADEMIC_LIFE = "averageAcademicLife";
    
    /** attribute for holding string. */
    
    /** Represents an instance of StudentLoginService. */
    private LoginDelegate studentLoginDelegate;
    
    /** StudentService attribute for holding studentService. */
    private StudentService studentService;
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** StudentService attribute for holding studentProgressBarService. */
    private StudentProgressBarService studentProgressBarService;
    
    /** FaithLifeRatingValidator attribute for holding faithLifeRatingValidator. */
    private FaithLifeRatingValidator faithLifeRatingValidator;
    
    /**
     * Sets an instance of StudentLoginService.
     * 
     * @param studentLoginDelegateVal - an instance of StudentLoginService.
     */
    public void setStudentLoginDelegate(LoginDelegate studentLoginDelegateVal) {
    
        this.studentLoginDelegate = studentLoginDelegateVal;
    }
    
    /**
     * sets the FaithLifeRatingValidator object.
     * 
     * @param faithLifeRatingValidatorObj the faithLifeRatingValidator to set
     */
    public void setFaithLifeRatingValidator(FaithLifeRatingValidator faithLifeRatingValidatorObj) {
    
        this.faithLifeRatingValidator = faithLifeRatingValidatorObj;
    }
    
    /**
     * sets the StudentProgressBarService object.
     * 
     * @param studentProgressBarServiceObj the studentProgressBarService to set
     */
    public void setStudentProgressBarService(StudentProgressBarService studentProgressBarServiceObj) {
    
        this.studentProgressBarService = studentProgressBarServiceObj;
    }
    
    /**
     * sets the StudentService object.
     * 
     * @param studentServiceObj the studentService to set
     */
    public void setStudentService(StudentService studentServiceObj) {
    
        this.studentService = studentServiceObj;
    }
    
    /**
     * sets the CommonService object.
     * 
     * @param commonServiceObj the commonService to set
     */
    public void setCommonService(CommonService commonServiceObj) {
    
        this.commonService = commonServiceObj;
    }
    
    /**
     * constructor to FaithLifeRatingController.
     */
    public FaithLifeRatingController() {
    
    }
    
    /**
     * handle GET requests for FaithLifeRating_details view.
     * 
     * @param model - ModelMap
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @return the name of the view.
     * @throws AkuraAppException - Detailed exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showFaithLifeRating(ModelMap model, HttpServletRequest request, HttpSession session)
            throws AkuraAppException {
    
        FaithLifeRating faithLifeRating = (FaithLifeRating) model.get(MODEL_ATT_FAITH_LIFE);
        
        if (faithLifeRating == null) {
            faithLifeRating = new FaithLifeRating();
        }
        model.addAttribute(MODEL_ATT_FAITH_LIFE, faithLifeRating);
        
        return populateData(request, session, model);
    }
    
    /**
     * Create or update FaithLifeRating details.
     * 
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @param faithLifeRating - FaithLifeRating object
     * @param bindingResult - BindingResult
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when save or update a FaithLifeRating instance.
     */
    @RequestMapping(value = SAVE_OR_UPDATE_FAITHLIFE_HTM, method = RequestMethod.POST)
    public String saveOrUpdateFaithLifeRating(@ModelAttribute(MODEL_ATT_FAITH_LIFE) FaithLifeRating faithLifeRating,
            BindingResult bindingResult, HttpServletRequest request, HttpSession session, ModelMap model)
            throws AkuraAppException {
    
        String strMessage = null;
        FaithLifeRating selectedObject = null;
        String returnPage;
        String strYr = null;
        int intCommentId = 0;
        String commentDes = null;
        int studentClassInfoId = 0;
        // get data
        int intFaithLifeId = faithLifeRating.getFaithLifeRatingId();
        int studentId = (Integer) session.getAttribute(STUDENT_ID);
        
        if (request.getParameter(SELECTED_GRADE) != null) {
            
            studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
            
            StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
            
            strYr = DateUtil.getStringYear(studentClassInfo.getYear());
        } else {
            Date currentDate = new Date();
            strYr = DateUtil.getStringYear(currentDate);
        }
        Date date = DateUtil.getDateTypeYearValue(strYr);
        
        if (request.getParameter(FAITH_LIFE_COMMENT_ID) != null && request.getParameter(FAITH_LIFE_COMMENT_ID) != "") {
            intCommentId = Integer.parseInt(request.getParameter(FAITH_LIFE_COMMENT_ID));
        }
        if (request.getParameter(FAITH_LIFE_COMMENT_DES) != null 
                && !request.getParameter(FAITH_LIFE_COMMENT_DES).trim().equals("")) {
            commentDes = request.getParameter(FAITH_LIFE_COMMENT_DES);
        }
        
        // validations
        faithLifeRatingValidator.validate(faithLifeRating, bindingResult);
        if (bindingResult.hasErrors()) {
            returnPage = populateData(request, session, model);
        } else {
            // validate faithLife comment
            if (intCommentId == 0 && commentDes == null) {
                strMessage = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                return populateData(request, session, model);
            }
            // check existence of faithLife
            boolean exists = false;
            if (intCommentId > 0) {
                exists = isExistsFaithLife(studentId, date, intCommentId);
            } else {
                exists = isExistsFaithLifeAdditional(studentId, date, commentDes, intFaithLifeId);
            }
            if (exists) {
                if (intFaithLifeId != 0) {
                    
                    selectedObject = studentService.findFaithLifeRatingObjById(intFaithLifeId);
                    // editing exist object with only grading
                    if ((selectedObject.getFaithLifeGrading().getGradingId() != faithLifeRating.getFaithLifeGrading()
                            .getGradingId())) {
                        FaithLifeGrading gradeObj =
                                commonService.findFaithLifeGradingById(faithLifeRating.getFaithLifeGrading()
                                        .getGradingId());
                        selectedObject.setFaithLifeGrading(gradeObj);
                        studentService.editFaithLifeRating(selectedObject);
                        returnPage = populateData(request, session, model);
                    } else {
                        model.addAttribute(MODEL_ATT_SELECTED_FAITH_LIFE_OBJECT, faithLifeRating);
                        strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_FAITHLIFE_EXIST);
                        model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                        returnPage = populateData(request, session, model);
                    }
                    
                } else {
                    strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_FAITHLIFE_EXIST);
                    model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                    returnPage = populateData(request, session, model);
                }
                
            } else {
                Student studentObj = studentService.findStudent(studentId);
                faithLifeRating.setStudent(studentObj);
                faithLifeRating.setYear(date);
                if (intCommentId > 0) {
                    FaithLifeComment faithLifeCommentObj = commonService.findFaithLifeCommentById(intCommentId);
                    faithLifeRating.setFaithLifeComment(faithLifeCommentObj);
                }
                if (commentDes != null) {
                    faithLifeRating.setFaithLifeComment(null);
                    faithLifeRating.setFaithLifeDescription(commentDes);
                }
                if (intFaithLifeId == 0) {
                    studentService.addFaithLifeRating(faithLifeRating);
                } else {
                    studentService.editFaithLifeRating(faithLifeRating);
                }
                returnPage = populateData(request, session, model);
            }
            
        }
        request.setAttribute(FAITH_LIFE_COMMENT_ID, request.getParameter(FAITH_LIFE_COMMENT_ID));
        return returnPage;
    }
    
    /**
     * Delete FaithLifeRating details.
     * 
     * @param faithLifeRating - FaithLifeRating object
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when deleting a FaithLifeRating instance.
     */
    @RequestMapping(value = DELETE_FAITHLIFE_HTM, method = RequestMethod.POST)
    public String deleteFaithLifeRating(@ModelAttribute(MODEL_ATT_FAITH_LIFE) FaithLifeRating faithLifeRating,
            HttpServletRequest request, HttpSession session, ModelMap model) throws AkuraAppException {
    
        try {
            studentService.deleteFaithLifeRating(faithLifeRating.getFaithLifeRatingId());
        } catch (DataAccessException ex) {
            LOG.error(ERROR_OCCURED_WHILE_DELETING_FAITH_LIFE_RATING_OBJECT + ex.toString());
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        } catch (AkuraAppException ex) {
            LOG.error(ERROR_OCCURED_WHILE_DELETING_FAITH_LIFE_RATING_OBJECT + ex.toString());
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        }
        
        return populateData(request, session, model);
    }
    
    /**
     * Check whether FaithLife is already added.
     * 
     * @param studentId - int
     * @param commentId -int
     * @param year - Date
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    private boolean isExistsFaithLife(int studentId, Date year, int commentId) throws AkuraAppException {
    
        boolean isExists = false;
        List<FaithLifeRating> faithLifeRating = studentService.getFaithLifeRatingsListForStudent(studentId, year);
        
        for (FaithLifeRating flr : faithLifeRating) {
            if (flr.getFaithLifeComment() != null) {
                if (flr.getFaithLifeComment().getFaithLifeCommentId() == commentId) {
                    isExists = true;
                }
            }
        }
        return isExists;
    }
    
    /**
     * Check whether other category FaithLife comment is already added.
     * 
     * @param studentId - int
     * @param commDes -String
     * @param intFaithLifeRatingId - int
     * @param year - Date
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    private boolean isExistsFaithLifeAdditional(int studentId, Date year, String commDes, int intFaithLifeRatingId)
            throws AkuraAppException {
    
        boolean isExists = false;
        List<FaithLifeRating> faithLifeRating = studentService.getFaithLifeRatingsListForStudent(studentId, year);
        
        String descript = commDes.replaceAll(" ", "");
        for (FaithLifeRating flr : faithLifeRating) {
            if (flr.getFaithLifeDescription() != null) {
                boolean check =
                        (intFaithLifeRatingId == 0) ? flr.getFaithLifeDescription().replaceAll(" ", "")
                                .equalsIgnoreCase(descript) : flr.getFaithLifeDescription().replaceAll(" ", "")
                                .equals(descript);
                if (check) {
                    isExists = check;
                    break;
                }
            }
        }
        
        return isExists;
    }
    
    /**
     * populate data for faith life page for relevant student for a selected grade.
     * 
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when deleting a Achievement instance.
     */
    @RequestMapping(value = POPULATE_FAITHLIFE_HTM, method = RequestMethod.POST)
    public String populateData(HttpServletRequest request, HttpSession session, ModelMap model)
            throws AkuraAppException {
    
        int studentId = 0;
        Date date = null;
        String strYr = null;
        double averFaithLife = 0.0;
        double academicLifeAverage = 0.0;
        double attendanceAverage = 0.0;
        String selectedStudClassId = null;
        if (session.getAttribute(STUDENT_ID) != null) {
            studentId = (Integer) session.getAttribute(STUDENT_ID);
        }
        
        int studentClassInfoId = 0;
        
        if (request.getParameter(SELECTED_GRADE) != null) {
            studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
            
            StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
            
            strYr = studentClassInfo.getYear().toString();
            selectedStudClassId = studentClassInfo.getClassGrade().getGrade().getDescription();
            
            if (DateUtil.getStringYear(studentClassInfo.getYear()).equals(DateUtil.getStringYear(new Date()))) {
                
                model.addAttribute(ENABLE_ADD_EDIT_DELETE, NONE);
                
            }
            
        } else {
            String studentGrade = (String) session.getAttribute(SESSION_ATT_STUDENT_GRADE);
            selectedStudClassId = studentGrade;
            strYr = Integer.toString(DateUtil.currentYearOnly());
            model.addAttribute(ENABLE_ADD_EDIT_DELETE, NONE);
        }
        
        date = DateUtil.getDateTypeYearValue(strYr);
        
        List<FaithLifeRating> faithLifeRatingList = studentService.getFaithLifeRatingsListForStudent(studentId, date);
        
        if (date.equals(DateUtil.getDateTypeYearValue(Integer.toString(DateUtil.currentYearOnly())))) {
            
            if (session.getAttribute(AVERAGE_ACADEMIC_LIFE_RATING) != null) {
                academicLifeAverage = (Double) session.getAttribute(AVERAGE_ACADEMIC_LIFE_RATING);
            }
            if (session.getAttribute(AVERAGE_ATTENDANCE_RATING) != null) {
                attendanceAverage = (Double) session.getAttribute(AVERAGE_ATTENDANCE_RATING);
                model.addAttribute(MODEL_ATT_ATTENDANCE_RATING, (int) Math.round(attendanceAverage));
            }
            averFaithLife = this.calFaithLifeValue(studentId, date);
            session.setAttribute(AVERAGE_FAITH_LIFE_RATING, averFaithLife);
        } else {
            Map<String, Double> averageMap = studentLoginDelegate.populateStudentProgressBar(studentId, date);
            
            averFaithLife = averageMap.get(AVERAGE_FAITH_LIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_FAITH_LIFE, (int) Math.round(averFaithLife));
            
            academicLifeAverage = averageMap.get(AVERAGE_ACADEMIC_LIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_ACADEMIC_LIFE, (int) Math.round(academicLifeAverage));
            
            attendanceAverage = averageMap.get(AVERAGE_ATTENDANCE_RATING);
            model.addAttribute(MODEL_ATT_ATTENDANCE_RATING, (int) Math.round(attendanceAverage));
        }
        
        FaithLifeRating newFaithLifeRating = (FaithLifeRating) model.get(MODEL_ATT_FAITH_LIFE);
        if (newFaithLifeRating == null) {
            newFaithLifeRating = new FaithLifeRating();
        }
        
        model.addAttribute(MODEL_ATT_FAITH_LIFE, newFaithLifeRating);
        
        model.addAttribute(MODEL_ATT_FAITH_LIFE_LIST, faithLifeRatingList);
        model.addAttribute(MODEL_ATT_SELECTED_ID, selectedStudClassId);
        model.addAttribute(MODEL_ATT_STUDENT_FAITH_LIFE, (int) Math.round(averFaithLife));
        model.addAttribute(MODEL_ATT_STUDENT_ACADEMIC_LIFE, (int) Math.round(academicLifeAverage));
        
        return VIEW_GET_FAITH_LIFE;
    }
    
    /**
     * Method is to return FaithLifeComment list.
     * 
     * @param request - HttpServletRequest
     * @param modelMap - ModelMap attribute.
     * @return list of FaithLifeComment
     * @throws AkuraAppException - Detailed exception
     */
    @ResponseBody
    @RequestMapping(value = POPULATE_FAITHLIFE_COMMENT_HTM)
    public String populateFaithLifeComment(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {
    
        StringBuilder allFaithLifeComment = new StringBuilder();
        int categoryId = Integer.parseInt(request.getParameter(SELECTED_VALUE));
        List<FaithLifeComment> commentList =
                SortUtil.sortFaithLifeComment(commonService.findFaithLifeCommentsByCategory(categoryId));
        boolean isFirst = true;
        StringBuilder comments = new StringBuilder();
        
        for (FaithLifeComment comment : commentList) {
            comments.append(comment.getDescription());
            comments.append("_");
            comments.append(comment.getFaithLifeCommentId());
            
            if (isFirst) {
                allFaithLifeComment.append(comments.toString()); // no comma
                isFirst = false;
            } else {
                allFaithLifeComment.append(","); // comma
                allFaithLifeComment.append(comments.toString());
            }
            comments.delete(0, comments.length());
        }
        return allFaithLifeComment.toString();
    }
    
    /**
     * Method is to return StudentClassInfo list.
     * 
     * @param session - HttpSession session
     * @return list of StudentClassInfo
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_STUDENT_GRADE_LIST)
    public List<StudentClassInfo> populateStudentGradeList(HttpSession session) throws AkuraAppException {
    
        int studentId = 0;
        if (session.getAttribute(STUDENT_ID) != null) {
            studentId = (Integer) session.getAttribute(STUDENT_ID);
        }
        
        List<StudentClassInfo> gredeList = studentService.getStudentClassInfoByStudentId2(studentId);
        List<StudentClassInfo> selectedGradeList = new ArrayList<StudentClassInfo>();
        
        for(StudentClassInfo classInfo : gredeList){
            if(!classInfo.getYear().after(DateUtil.currentYear())){
                selectedGradeList.add(classInfo);
            }
            
        }
        
        return selectedGradeList;
    }
    
    /**
     * returns FaithLifeCategory list.
     * 
     * @return list of FaithLifeCategory
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_FAITH_LIFE_CATEGORY_LIST)
    public List<FaithLifeCategory> populateCategoryList() throws AkuraAppException {
    
        return SortUtil.sortFaithLifeCategoryList(commonService.findFaithLifeCategories());
        
    }
    
    /**
     * Method to retrieve all the grading data.
     * 
     * @return list of grading instances.
     * @throws AkuraAppException throws when exception occurs
     */
    @ModelAttribute(MODEL_ATT_GRADING_LIST)
    public List<FaithLifeGrading> gradingList() throws AkuraAppException {
    
        return commonService.getFaithLifeGradingList();
    }
    
    /**
     * method to calculate faithLifeRating average.
     * 
     * @param studentId - integer
     * @param year - Date
     * @return average in double.
     * @throws AkuraAppException when exception occurs
     */
    private double calFaithLifeValue(int studentId, Date year) throws AkuraAppException {
    
        return studentProgressBarService.calculateFaithLifeRatingAverage(studentId, year);
        
    }
    
}
