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

package com.virtusa.akura.staff.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the Class StaffLeave object.
 * 
 * @author Virtusa Corporation
 */

public class StaffLeaveDaoImpl extends BaseDaoImpl<StaffLeave> implements StaffLeaveDao {
    
    /**
     * key to hold the named query findStaffLeaveForStaffLeaveTypeForJoinYear.
     */   
    private static final String FIND_STAFF_LEAVE_FOR_STAFF_LEAVE_TYPE_FOR_JOIN_YEAR = 
            "findStaffLeaveForStaffLeaveTypeForJoinYear";

    /**
     * key to hold the named query findNotAppliedStaffLeaveTypesForJoinYear.
     */
    private static final String FIND_NOT_APPLIED_STAFF_LEAVE_TYPES_FOR_JOIN_YEAR = 
            "findNotAppliedStaffLeaveTypesForJoinYear";

    /**
     * key to hold the named query findStaffLeaveForStaffLeaveType.
     */
    private static final String FIND_APPLIED_STAFF_LEAVE_TYPES_FOR_JOIN_YEAR = "findAppliedStaffLeaveTypesForJoinYear";

    /**
     * key to hold the named query findStaffLeaveForStaffLeaveType.
     */
    private static final String FIND_EXIST_STAFF_LEAVE = "findExistStaffLeave";

    /**
     * key to hold the named query findStaffLeaveForStaffLeaveType.
     */
    private static final String FIND_STAFF_LEAVE_FOR_STAFF_LEAVE_TYPE = "findStaffLeaveForStaffLeaveType";
    
    /**
     * key to hold the named query findNotAppliedStaffLeaveTypes.
     */
    private static final String FIND_NOT_APPLIED_STAFF_LEAVE_TYPES = "findNotAppliedStaffLeaveTypes";
    
    /**
     * key to hold the named query findAppliedStaffLeaveTypes.
     */
    private static final String FIND_APPLIED_STAFF_LEAVE_TYPES = "findAppliedStaffLeaveTypes";
    
    /**
     * key to hold the named query getStaffLeaveCountByPreviousYear.
     */
    private static final String GET_STAFF_LEAVE_COUNT_BY_PREVIOUS_YEAR = "getStaffLeaveCountByPreviousYear";
    
    /**
     * key to hold the named query deleteLeaveRocordsByDepartureDate.
     */
    private static final String DELETE_LEAVE_ROCORDS_BY_DEPARTURE_DATE = "deleteLeaveRocordsByDepartureDate";
    
    /**
     * key to hold the named query updateToDateWithDepartureDate.
     */
    private static final String UPDATE_TODATE_WITH_DEPARTURE_DATE = "updateToDateWithDepartureDate";
    
    /**
     * key to hold the named query markAsDeletedAllStaffLeaveByStaffId.
     */
    private static final String MARK_AS_DELETED_ALL_BY_STAFF_ID = "markAsDeletedAllStaffLeaveByStaffId";
    
    /**
     * key to hold the named query viewStudentDisciplineByStaffId.
     */
    private static final String GET_STAFF_LEAVE_BY_STAFF_ID = "getStaffLeaveByStaffId";
    
    /**
     * key to hold the named query viewStudentDisciplineByStaffIdAndTimePeriod.
     */
    private static final String GET_STAFF_LEAVE_BY_DATE_PERIOD_AND_STAFF_ID = "getStaffLeaveByDatePeriodAndStaffId";
    
    /**
     * key to hold the named query getStaffLEaveListByStatusId.
     */
    private static final String GET_STAFF_LEAVE_LIST_BY_STATUS_ID = "getStaffLeaveListByStatusId";
    
    /**
     * key to hold the named query getCurrentStaffListWithApprovedOrInprogressLeaveByDateAndType.
     */
    private static final String GET_ALLOWABLE_STAFF_LEAVE_LIST_BY_STAFF_TYPE_AND_DATE =
    		"getStaffLeaveListWithApprovedOrInprogressLeaveByStaffTypeAndDate";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StaffLeaveDaoImpl.class);
    
    /**
     * String attribute for error staffLeave info.
     */
    private static final String ERROR_STAFFLEAVE_INFO = "Error while retrieving staff leave info ---> ";
    
    /**
     * Retrieve all the available StaffLeave information given by staff Id.
     * 
     * @param staffId specifies the staff id, defined by an integer
     * @return List of StaffLeave
     * @throws AkuraAppException - Throw Exception
     */
    @SuppressWarnings("unchecked")
    public List<StaffLeave> findStaffLeaveByStaffId(int staffId) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_LEAVE_BY_STAFF_ID, staffId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Retrieve all the available StaffLeave information given by staff Id.
     * 
     * @param staffId specifies the staff id, defined by an integer
     * @param fromDate specifies Date from
     * @param toDate specifies Date to
     * @return List of StaffLeave
     * @throws AkuraAppException - Throw Exception
     */
    @SuppressWarnings("unchecked")
    public List<StaffLeave> findStaffLeaveByDatePeriodAndStaffId(Date fromDate, Date toDate, int staffId)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_LEAVE_BY_DATE_PERIOD_AND_STAFF_ID, fromDate,
                    toDate, staffId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Get the total no of staff leaves by staff Id and previous year period.
     * 
     * @param staffId specifies the staff id, defined by an integer
     * @param fromDate specifies Date from
     * @param toDate specifies Date to
     * @return total no of days
     * @throws AkuraAppException - Throw Exception
     */
    @SuppressWarnings("unchecked")
    public int findStaffLeaveCountByPreviousYear(Date fromDate, Date toDate, int staffId) throws AkuraAppException {
    
        int noOfDays = 0;
        try {
            /*List<Long> checkList =
                    getHibernateTemplate().findByNamedQuery(GET_STAFF_LEAVE_COUNT_BY_PREVIOUS_YEAR, fromDate, toDate,
                            staffId);*/
            
            List<Double> checkList =
                    getHibernateTemplate().findByNamedQuery(GET_STAFF_LEAVE_COUNT_BY_PREVIOUS_YEAR, fromDate, toDate,
                            staffId);
            if (checkList.size() > 0) {
                noOfDays = checkList.get(0).intValue();
            }
            
        } catch (DataAccessException e) {
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return noOfDays;
    }
    
    /**
     * Get applied staff leave type list with info.
     * 
     * @param staffId - staffId
     * @param fromDate - selected year
     * @return list of Object array
     * @throws AkuraAppException AkuraAppException
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> findAppliedStaffLeaveTypes(int staffId, Date fromDate) throws AkuraAppException {
    
        List<Object[]> appliedList = null;
        try {
            appliedList = getHibernateTemplate().findByNamedQuery(FIND_APPLIED_STAFF_LEAVE_TYPES, staffId, 
                    fromDate, staffId);
            
        } catch (DataAccessException e) {
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return appliedList;
    }
    
    /**
     * Get not applied Staff Leave list with info.
     * 
     * @param staffId - staffId
     * @param fromDate - selected year
     * @return list of Object array
     * @throws AkuraAppException AkuraAppException
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> findNotAppliedStaffLeaveTypeIds(int staffId, Date fromDate) throws AkuraAppException {
    
        List<Object[]> notAppliedList = null;
        try {
            notAppliedList =
                    getHibernateTemplate().findByNamedQuery(FIND_NOT_APPLIED_STAFF_LEAVE_TYPES, staffId, 
                            fromDate, staffId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return notAppliedList;
    }
    
    /**
     * Get Staff Leave Type info for given Staff Leave Type Id.
     * 
     * @param staffId - staffId
     * @param fromDate - selected year
     * @param staffLeaveTypeId - selected staffLeaveTypeId
     * @return List of object arrays
     * @throws AkuraAppException AkuraAppException
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> findStaffLeaveForStaffLeaveType(int staffId, Date fromDate, int staffLeaveTypeId)
            throws AkuraAppException {
    
        List<Object[]> appliedList = null;
        try {
            appliedList =
                    getHibernateTemplate().findByNamedQuery(FIND_STAFF_LEAVE_FOR_STAFF_LEAVE_TYPE, staffId, fromDate,
                            staffLeaveTypeId);
            
        } catch (DataAccessException e) {
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return appliedList;
    }
        
    /**
     * {@inheritDoc}
     */
    public void deletedLeaveRecordsByDepartureDate(int staffId, Date departureDate) throws AkuraAppException {
    
        Session session = null;
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
            Query query = session.getNamedQuery(DELETE_LEAVE_ROCORDS_BY_DEPARTURE_DATE);
            getHibernateTemplate().bulkUpdate(query.getQueryString(), staffId, departureDate);
        } catch (DataAccessException e) {
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void updateToDateWithDepartureDate(int staffId, Date departureDate) throws AkuraAppException {
    
        Session session = null;
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
            Query query = session.getNamedQuery(UPDATE_TODATE_WITH_DEPARTURE_DATE);
            getHibernateTemplate().bulkUpdate(query.getQueryString(), departureDate, staffId, departureDate);
        } catch (DataAccessException e) {
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void markAsDeletedAllStaffLeaveRecords(int staffId) throws AkuraAppException {
    
        Session session = null;
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
            Query query = session.getNamedQuery(MARK_AS_DELETED_ALL_BY_STAFF_ID);
            getHibernateTemplate().bulkUpdate(query.getQueryString(), staffId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
    }
    
    /**
     * Finds the already leaves for the given staff and the given date range.
     * 
     * @param staffId - staff id
     * @param dateFrom - the start date of the leave
     * @param dateTo - the end date of the leave
     * @return - a list of staff leaves
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @SuppressWarnings("unchecked")
    public List<StaffLeave> findAlreadyExistStaffLeave(int staffId, Date dateFrom, Date dateTo)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_EXIST_STAFF_LEAVE, staffId, dateFrom, dateTo, dateFrom,
                    dateTo, dateFrom, dateTo);
        } catch (DataAccessException e) {
            
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Get applied staff leave type list with info.
     * 
     * @param staffId - staffId
     * @param fromDate - selected year
     * @param toDate - to date
     * @return list of Object array
     * @throws AkuraAppException AkuraAppException
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> findAppliedStaffLeaveTypesForJoinYear(int staffId, Date fromDate, Date toDate) 
            throws AkuraAppException {
    
        List<Object[]> appliedList = null;
        try {
            appliedList =
                    getHibernateTemplate().findByNamedQuery(FIND_APPLIED_STAFF_LEAVE_TYPES_FOR_JOIN_YEAR, staffId, 
                            fromDate, toDate, staffId);
            
        } catch (DataAccessException e) {
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return appliedList;
    }
    
    /**
     * Get not applied Staff Leave list with info.
     * 
     * @param staffId - staffId
     * @param fromDate - selected year
     * @param toDate - to date
     * @return list of Object array
     * @throws AkuraAppException AkuraAppException
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> findNotAppliedStaffLeaveTypeIdsForJoinYear(int staffId, Date fromDate, Date toDate)
            throws AkuraAppException {
    
        List<Object[]> notAppliedList = null;
        try {
            notAppliedList =
                    getHibernateTemplate().findByNamedQuery(FIND_NOT_APPLIED_STAFF_LEAVE_TYPES_FOR_JOIN_YEAR, staffId, 
                            staffId, staffId, fromDate, toDate, staffId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return notAppliedList;
    }
    
    /**
     * Get Staff Leave Type info for given Staff Leave Type Id.
     * 
     * @param staffId - staffId
     * @param joinDate - join date
     * @param toDate - todate
     * @param staffLeaveTypeId - selected staffLeaveTypeId
     * @return List of object arrays
     * @throws AkuraAppException AkuraAppException
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> findStaffLeaveForStaffLeaveTypeForJoinYear(int staffId, Date joinDate, Date toDate, 
            int staffLeaveTypeId) throws AkuraAppException {
    
        List<Object[]> appliedList = null;
        try {
            appliedList =
                    getHibernateTemplate().findByNamedQuery(FIND_STAFF_LEAVE_FOR_STAFF_LEAVE_TYPE_FOR_JOIN_YEAR, 
                            staffId, staffId, staffId, joinDate, toDate, staffLeaveTypeId);
            
        } catch (DataAccessException e) {
            LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return appliedList;
    }
    
    /**
     * Get staff leave list of, staff in mentioned type that, status of
     * the leave for mentioned date either Approved or In progress.
     * 
     * @param isAcademic boolean Considering staff type (Academic / Non Academic)
     * @param date 		 Date    Considering date
     * @return staffList List of staff  Matching staff list
     * @throws AkuraAppException throws when retrieving the list of staff fails
     */
    @SuppressWarnings("unchecked")
    public List<StaffLeave> getAllowableStaffLeaveListByStaffTypeAndDate(boolean isAcademic, Date date)
    		throws AkuraAppException {
    	
    	List<StaffLeave> staffLeaveList = null;
    	
    	try {
    		
    		staffLeaveList = getHibernateTemplate().findByNamedQuery(
				GET_ALLOWABLE_STAFF_LEAVE_LIST_BY_STAFF_TYPE_AND_DATE,
				isAcademic, date, date
    		);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    	
    	return staffLeaveList;
    	
    }
    
    /**
     * Get All Staff Leaves by status id.
     * 
     * @param statusID - statusID
     * @return List of StaffLeave
     * @throws AkuraAppException AkuraAppException
     */
	@SuppressWarnings("unchecked")
	public List<Object[]> getStaffLeaveListByStatusId(int statusID)
			throws AkuraAppException {

		List<Object[]> staffLeaveList = null;
		try {
			staffLeaveList = getHibernateTemplate().findByNamedQuery(
					GET_STAFF_LEAVE_LIST_BY_STATUS_ID, statusID);

		} catch (DataAccessException e) {
			LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
			throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
		}
		return staffLeaveList;
	}
    
}
