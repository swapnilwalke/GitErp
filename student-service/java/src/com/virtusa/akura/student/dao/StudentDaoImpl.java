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

package com.virtusa.akura.student.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentSearchCritiria;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * Implementation of the Student Dao functionalities.
 * 
 * @author Virtusa Corporation
 */
public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao {
    
    /** Logger to log the exceptions. */
    private static final Logger LOG = Logger.getLogger(StudentDaoImpl.class);
    
    /** Represents the query name for retrieving first date at school. */
    private static final String GET_STUDENT_STARTED_DATE = "getStudentStartedDate";
    
    /** key to hold the named query "studentSearch". */
    private static final String STUDENT_SEARCH = "studentSearch";
    
    /** key to hold the named query findNewStudents. */
    private static final String FIND_NEW_STUDENTS = "findNewStudents";
    
    /** key to hold the named query getStudentIdByAdmissionNo. */
    private static final String GET_STUDENT_ID_BY_ADMISSION_NO = "getStudentIdByAdmissionNo";
    
    /** key to hold string new state for new students. */
    private static final String NEW_STATE = "New";
    
    /** key to hold string empty match. */
    private static final String STRING_EMPTY = "";
    
    /** The Constant ERROR_WHILE_RETRIEVE_STUDENT_PERSONAL_DETAILS. */
    private static final String ERROR_WHILE_RETRIEVE_STUDENT_PERSONAL_DETAILS =
            "Error while retrieve student personal details-->";
    
    /** The Constant GET_STUDENT_PERSONAL_DETAILS_BY_ADMISSION_NO. */
    private static final String GET_STUDENT_PERSONAL_DETAILS_BY_ADMISSION_NO = "getStudentPersonalDetailsByAdmissionNo";
    
    /** key to hold string pattern match. */
    private static final String PATTERN_MATCH = "%";
    
    /** The Constant FIND_STUDENT_ADMISSION_NOS_BY_CLASS_GRADE_ID. */
    private static final String FIND_STUDENT_ADMISSION_NOS_BY_CLASS_GRADE_ID = "findStudentAdmissionNosByClassGradeId";
    
    /** key to hold detailed error while advance student search. */
    private static final String ERROR_WHILE_STUDENT_ADVANCE_SEARCH = "Error while Student advance search ---> ";
    
    /** key to hold detailed error while student search. */
    private static final String ERROR_WHILE_SEARCHING_STUDENTS = "Error while searching Students ---> ";
    
    /** key to hold the named query checkAdmissionNoIsExist. */
    private static final String CHECK_ADMISSION_NO_IS_EXIST = "checkAdmissionNoIsExist";
    
    /** key to hold the named query callStudentAdvancedSearch. */
    private static final String CALL_STUDENT_ADVANCED_SEARCH = "callStudentAdvancedSearch";
    
    /** Represents the query name for retrieving student key by the name. */
    private static final String GET_STUDENT_ID_BY_NAME = "getStudentIdByName";
    
    /** The constant for String "deleteStudentAttendanceByDepartureDate". */
    private static final String DELETE_STUDENT_ATTENDANCE_BY_DEPARTURE_DATE = "deleteStudentAttendanceByDepartureDate";
    
    /** The constant for String "studentIdsList". */
    private static final String STUDENT_IDS_LIST = "studentIdsList";
    
    /** The constant for String query name "getStudentStatusByStudentIdsList". */
    private static final String GET_STUDENT_STATUS_BY_STUDENT_IDS_LIST = "getStudentStatusByStudentIdsList";
    
    /** The constant for String query name "getStudentStatusId". */
    private static final String GET_STUDENT_STATUS_ID = "getStudentStatusId";
    
    /** The constant for String query name "getStudentCount". */
    private static final String GET_STUDENT_COUNT = "getStudentCount";
    
    /**
     * Get the list of {@link Student} records that are matching the given criteria.
     * 
     * @param searchCritiria
     *            - student search criteria
     * @exception AkuraAppException
     *                -AkuraAppException
     * @return list of students
     */
    @SuppressWarnings("unchecked")
    public List<Object> studentSearch(StudentSearchCritiria searchCritiria)
            throws AkuraAppException {

        try {
            String lastName = PATTERN_MATCH + searchCritiria.getLastName()
                    + PATTERN_MATCH;
            String admissionNo = searchCritiria.getAdmissionNumber();
            int studentStatusId = searchCritiria.getStudentStatusId();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            String classGrade = searchCritiria.getGrade();
            int startFrom = searchCritiria.getStartFrom();
            List<Object> searchReults = null;
            
            if (searchCritiria.getGrade() != STRING_EMPTY) {
                
                if (searchCritiria.getGrade().equals(NEW_STATE)) {
                    
                    searchReults = getHibernateTemplate().findByNamedQuery(STUDENT_SEARCH, year,
                            lastName,lastName,
                            admissionNo,admissionNo,
                            studentStatusId,studentStatusId,
                            AkuraConstant.EMPTY_STRING,AkuraConstant.EMPTY_STRING,NEW_STATE,
                            lastName,lastName,
                            admissionNo,admissionNo,
                            studentStatusId,studentStatusId,
                            AkuraConstant.EMPTY_STRING,AkuraConstant.EMPTY_STRING,NEW_STATE,year,
                            startFrom);
                    
                } else {
                    
                    searchReults = getHibernateTemplate().findByNamedQuery(STUDENT_SEARCH, year,
                            lastName,lastName,
                            admissionNo,admissionNo,
                            studentStatusId,studentStatusId,
                            classGrade,classGrade,AkuraConstant.EMPTY_STRING,
                            lastName,lastName,
                            admissionNo,admissionNo,
                            studentStatusId,studentStatusId,
                            classGrade,classGrade,NEW_STATE,year,
                            startFrom);
                    
                }
            } else {
                
                    searchReults = getHibernateTemplate().findByNamedQuery(STUDENT_SEARCH,year,
                            lastName,lastName,
                            admissionNo,admissionNo,
                            studentStatusId,studentStatusId,
                            classGrade,classGrade,AkuraConstant.EMPTY_STRING,
                            lastName,lastName,
                            admissionNo,admissionNo,
                            studentStatusId,studentStatusId,
                            classGrade,classGrade,AkuraConstant.EMPTY_STRING,year,
                            startFrom);
            }
            
            return searchReults;
            
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_SEARCHING_STUDENTS + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * Get student list for the given criteria.
     * 
	 * @param searchCritiria
	 *            - student search criteria
	 * @exception AkuraAppException
	 *                -AkuraAppException
     * @return list of students
     */
    @SuppressWarnings({ "unchecked" })
	public List<Object> studentAdvanceSearch(
			StudentSearchCritiria searchCritiria) throws AkuraAppException {

        try {
			return getHibernateTemplate().findByNamedQuery(
					CALL_STUDENT_ADVANCED_SEARCH, searchCritiria.getClazzId(),
					searchCritiria.getSportId(), searchCritiria.getSubjectId(),
					searchCritiria.getPrefectTypeId(),
					searchCritiria.getWorkingSegmentId(),
					searchCritiria.getStartFrom(),
					searchCritiria.getYear());
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_STUDENT_ADVANCE_SEARCH + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Dao method is to get StudentId for the admissionNo.
     * 
	 * @param admissionNo
	 *            - admissionNo.
     * @return student id for the registrationNo.
	 * @throws AkuraAppException
	 *             AkuraAppException
     */
    @SuppressWarnings("unchecked")
    @Override
	public int findStudentIdForAdmissionNo(String admissionNo)
			throws AkuraAppException {

        int studentId = 0;
        try {
            
			List<Object> checkList = getHibernateTemplate().findByNamedQuery(
					CHECK_ADMISSION_NO_IS_EXIST, admissionNo);
            
            if (checkList.size() > 0) {
                studentId = (Integer) checkList.get(0);
            }
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return studentId;
    }
    
    /**
     * Gets the student personal details by admission no.
     * 
     * @see com.virtusa.sms.student.dao.StudentDao#getStudentPersonalDetailsByAdmissionNo(java.lang.String)
	 * @param admissionNo
	 *            the admission no
     * @return the student personal details by admission no
	 * @throws AkuraAppException
	 *             the sMS app exception
     */
    @SuppressWarnings("unchecked")
	public List<Student> getStudentPersonalDetailsByAdmissionNo(
			String admissionNo) throws AkuraAppException {

        try {
			return getHibernateTemplate().findByNamedQuery(
					GET_STUDENT_PERSONAL_DETAILS_BY_ADMISSION_NO, admissionNo);
        } catch (DataAccessException e) {
			LOG.error(ERROR_WHILE_RETRIEVE_STUDENT_PERSONAL_DETAILS
					+ e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see com.virtusa.sms.student.dao.StudentDao#getNewStudentForYear(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object> getNewStudentForYear(int year) throws AkuraAppException {

        try {
			return getHibernateTemplate().findByNamedQuery(FIND_NEW_STUDENTS,
					year);
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
	public List<Integer> getStudentId(String addmissionNo)
			throws AkuraAppException {

        try {
			return getHibernateTemplate().findByNamedQuery(
					GET_STUDENT_ID_BY_ADMISSION_NO, addmissionNo);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_WHILE_PROCESSING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
	public List<Integer> getStudentIdByName(final String studentName)
			throws AkuraAppException {

        try {
			return getHibernateTemplate().findByNamedQuery(
					GET_STUDENT_ID_BY_NAME, studentName);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_WHILE_PROCESSING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Gets the student admission nos by class grade id.
     * 
	 * @param classGradeId
	 *            the class grade id
     * @return the student admission nos by class grade id
	 * @throws AkuraAppException
	 *             the akura app exception
     */
    @SuppressWarnings("unchecked")
	public List<String> getStudentAdmissionNosByClassGradeId(int classGradeId)
			throws AkuraAppException {

        try {
			return getHibernateTemplate().findByNamedQuery(
					FIND_STUDENT_ADMISSION_NOS_BY_CLASS_GRADE_ID, classGradeId,
                    DateUtil.currentYear());
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
	public void deletedStudentAttendanceRecordsByDepartureDate(int studentId,
			Date departureDate) throws AkuraAppException {

        Session session = null;
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(DELETE_STUDENT_ATTENDANCE_BY_DEPARTURE_DATE);
			getHibernateTemplate().bulkUpdate(query.getQueryString(),
					studentId, departureDate);
        } catch (DataAccessException e) {
			LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_DELETING_THE_OBJECT
					+ e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
	public List<Date> getStudentStartedDate(int studentId)
			throws AkuraAppException {

        try {
			return getHibernateTemplate().findByNamedQuery(
					GET_STUDENT_STARTED_DATE, studentId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	public List<Integer> getStudentStatusListByStudentIdsList(
			List<Integer> studentIdsList) throws AkuraAppException {

        try {
            
			return getHibernateTemplate().findByNamedQueryAndNamedParam(
					GET_STUDENT_STATUS_BY_STUDENT_IDS_LIST,
					new String[] { STUDENT_IDS_LIST },
					new Object[] { studentIdsList });
        } catch (DataAccessException e) {
			LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_RETRIEVING_THE_OBJECT
					+ e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public int getStudentStatusId(int studentId) throws AkuraAppException {

        try {
			List<Integer> statusList= getHibernateTemplate().findByNamedQuery(
					GET_STUDENT_STATUS_ID,studentId);
            Integer studentStatusId = statusList.get(0);
            return studentStatusId;
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_RETRIEVING_THE_OBJECT + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    public int getNumberOfStudentsInSchool() throws AkuraAppException {

        List<?> studentList;
        
        try {
            studentList = getHibernateTemplate().findByNamedQuery(GET_STUDENT_COUNT);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_RETRIEVING_THE_OBJECT + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return studentList != null ? ((Long) studentList.get(0)).intValue() : 0;
        
    }
    
}
