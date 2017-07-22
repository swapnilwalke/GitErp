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

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * StudentParentDaoImpl implementation of StudentParentDao interface.
 * 
 * @author Virtusa Corporation
 */
public class StudentParentDaoImpl extends BaseDaoImpl<StudentParent> implements StudentParentDao {
    
    /** query name isParentUsedBySameStudent. */
    private static final String IS_PARENT_USED_BY_SAME_STUDENT = "isParentUsedBySameStudent";
    
    /** query name getSPRrelationByStudent. */
    private static final String IS_PARENT_USED_BY_OTHER_STUDENT = "isParentUsedByOtherStudent";
    
    /** query name getSPRrelationByParent. */
    private static final String GET_SP_RRELATION_BY_STUDENT = "getSPRrelationByStudent";
    
    /** query name getSPRrelationByParent. */
    private static final String GET_SP_RRELATION_BY_PARENT = "getSPRrelationByParent";
    
    /** String attribute for query name getChildrenByParnetId. */
    private static final String GET_CHILDREN_BY_PARNET_ID = "getChildrenByParnetId";
    
    /**
     * String attribute for query name getParentsByStudentId.
     */
    private static final String GET_PARENTS_BY_STUDENT_ID = "getParentsByStudentId";
    
    /**
     * key to hold the named query deleteStudentByDepartureDate.
     */
    private static final String DELETE_STUDENT_BY_DEPARTURE_DATE = "deleteStudentByDepartureDate";
    
    /**
     * String attribute for error student info.
     */
    private static final String ERROR_STUDENT_INFO = "Error while retrieving student info ---> ";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentParentDaoImpl.class);
    
    /**
     * Retrieve all the available Student Parent information given by student Id.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @return List of StudentParent
     * @throws AkuraAppException SMS Exception
     */
    @SuppressWarnings("unchecked")
    public final List<StudentParent> getStudentParentsByStudentId(final int studentId) throws AkuraAppException {

        try {
            // List<StudentParent> studentParentList =
            return (List<StudentParent>) getHibernateTemplate().findByNamedQuery(GET_PARENTS_BY_STUDENT_ID, studentId);
            
        } catch (DataAccessException e) {
            LOG.error("error occured while getting StudentParentsByStudentId  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Retrieve all the available Student Parent information given by parent Id.
     * 
     * @param parentId specifies the parent Id, defined by an integer
     * @return List of StudentParent
     * @throws AkuraAppException SMS Exception
     */
    @SuppressWarnings("unchecked")
    public List<Student> getStudentByParnetId(int parentId) throws AkuraAppException {

        try {
            
            return (List<Student>) getHibernateTemplate().findByNamedQuery(GET_CHILDREN_BY_PARNET_ID, parentId);
            
        } catch (DataAccessException e) {
            LOG.error("error occured while getting getChildrenByParnetId  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /** {@inheritDoc} */
    public List<Student> getStudentListBy(String nic, Character relationship) throws AkuraAppException {

        try {
            
            return (List<Student>) getHibernateTemplate().findByNamedQuery(GET_SP_RRELATION_BY_PARENT, nic,
                    relationship);
            
        } catch (DataAccessException e) {
            LOG.error("error occured while getting the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /** {@inheritDoc} */
    public List<StudentParent> getParentListBy(int studentId, Character relationship) throws AkuraAppException {

        try {
            
            return (List<StudentParent>) getHibernateTemplate().findByNamedQuery(GET_SP_RRELATION_BY_STUDENT,
                    studentId, relationship);
            
        } catch (DataAccessException e) {
            LOG.error("error occured while getting the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /** {@inheritDoc} */
    public boolean isUsedInOtherStudent(String nic, int studentID) throws AkuraAppException {

        try {
            
            List<Object> idList =
                    (List<Object>) getHibernateTemplate().findByNamedQuery(IS_PARENT_USED_BY_OTHER_STUDENT, nic,
                            studentID);
            if (idList != null && !idList.isEmpty()) {
                return true;
            }
            
        } catch (DataAccessException e) {
            LOG.error("error occured while getting the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return false;
    }
    
    /** {@inheritDoc} */
    public boolean isUsedInSameStudent(String nic, int studentID) throws AkuraAppException {

        try {
            
            List<Object> idList =
                    (List<Object>) getHibernateTemplate().findByNamedQuery(IS_PARENT_USED_BY_SAME_STUDENT, nic,
                            studentID);
            if (idList != null && !idList.isEmpty()) {
                return true;
            }
            
        } catch (DataAccessException e) {
            LOG.error("error occured while getting the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    public void deleteStudentByDepartureDate(int studentId) throws AkuraAppException {

        Session session = null;
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
            Query query = session.getNamedQuery(DELETE_STUDENT_BY_DEPARTURE_DATE);
            getHibernateTemplate().bulkUpdate(query.getQueryString(), studentId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_STUDENT_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
    }
}
