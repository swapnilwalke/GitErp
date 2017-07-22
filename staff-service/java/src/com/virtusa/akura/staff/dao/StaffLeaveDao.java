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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the Class StaffLeave object.
 * 
 * @author Virtusa Corporation
 */

public interface StaffLeaveDao extends BaseDao<StaffLeave> {
    
    /**
     * Retrieve all the available StaffLeave information given by staff Id.
     * 
     * @param staffId specifies the staff id, defined by an integer
     * @return List of StaffLeave
     * @throws AkuraAppException - Throw Exception
     */
    List<StaffLeave> findStaffLeaveByStaffId(int staffId) throws AkuraAppException;
    
    /**
     * Retrieve all the available StaffLeave information given by staff Id and Date period.
     * 
     * @param staffId specifies the staff id, defined by an integer
     * @param fromDate specifies Date from
     * @param toDate specifies Date to
     * @return List of StaffLeave
     * @throws AkuraAppException - Throw Exception
     */
    List<StaffLeave> findStaffLeaveByDatePeriodAndStaffId(Date fromDate, Date toDate, int staffId)
            throws AkuraAppException;
    
    /**
     * Get the total no of staff leaves by staff Id and previous year period.
     * 
     * @param staffId specifies the staff id, defined by an integer
     * @param fromDate specifies Date from
     * @param toDate specifies Date to
     * @return total no of days
     * @throws AkuraAppException - Throw Exception
     */
    int findStaffLeaveCountByPreviousYear(Date fromDate, Date toDate, int staffId) throws AkuraAppException;
    
    /**
     * Get applied staff leave type list with info.
     * 
     * @param staffId - staffId
     * @param fromDate - selected year
     * @return list of Object array
     * @throws AkuraAppException AkuraAppException
     */
    List<Object[]> findAppliedStaffLeaveTypes(int staffId, Date fromDate) throws AkuraAppException;
    
    /**
     * Get not applied Staff Leave list with info.
     * 
     * @param staffId - staffId
     * @param fromDate - selected year
     * @return list of Object array
     * @throws AkuraAppException AkuraAppException
     */
    List<Object[]> findNotAppliedStaffLeaveTypeIds(int staffId, Date fromDate) throws AkuraAppException;
    
    /**
     * Get Staff Leave Type info for given Staff Leave Type Id.
     * 
     * @param staffId - staffId
     * @param fromDate - selected year
     * @param staffLeaveTypeId - selected staffLeaveTypeId
     * @return List of object arrays
     * @throws AkuraAppException AkuraAppException
     */
    List<Object[]> findStaffLeaveForStaffLeaveType(int staffId, Date fromDate, int staffLeaveTypeId)
            throws AkuraAppException;
       
    /**
     * Delete all the leave records that have start date after the departureDate.
     * 
     * @param staffId staff id.
     * @param departureDate departure date.
     * @throws AkuraAppException - The exception details that occurred when deleting StaffLeave records with given
     *         staffId.
     */
    void deletedLeaveRecordsByDepartureDate(int staffId, Date departureDate) throws AkuraAppException;

    /**
     * update `toDate` with departure date.
     * 
     * @param staffId staff id.
     * @param departureDate departure date.
     * @throws AkuraAppException - The exception details that occurred when updating  StaffLeave records with given
     *         staffId.
     */
    void updateToDateWithDepartureDate(int staffId, Date departureDate) throws AkuraAppException;
    
    /**
     * Mark as delete all the staff leave records.
     * 
     * @param staffId staff id.
     * @throws AkuraAppException - The exception details that occurred when updating staff leave records with given
     *         staffId.
     */
    void markAsDeletedAllStaffLeaveRecords(int staffId) throws AkuraAppException;
    
    /**
     * Finds the already leaves for the given staff and the given date range.
     *
     * @param staffId - staff id
     * @param dateFrom - the start date of the leave
     * @param dateTo - the end date of the leave
     * @return - a list of staff leaves
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StaffLeave> findAlreadyExistStaffLeave(int staffId, Date dateFrom, Date dateTo) throws AkuraAppException;
    
    /**
     * Get applied staff leave type list with info for join year.
     * 
     * @param staffId - staffId
     * @param fromDate - selected year
     * @param toDate - to date
     * @return list of Object array
     * @throws AkuraAppException AkuraAppException
     */
    List<Object[]> findAppliedStaffLeaveTypesForJoinYear(int staffId, Date fromDate, Date toDate) 
            throws AkuraAppException;
    
    /**
     * Get not applied Staff Leave list with info for join year.
     * 
     * @param staffId - staffId
     * @param fromDate - from date year
     * @param toDate - to date
     * @return list of Object array
     * @throws AkuraAppException AkuraAppException
     */
    List<Object[]> findNotAppliedStaffLeaveTypeIdsForJoinYear(int staffId, Date fromDate, Date toDate) 
            throws AkuraAppException;
    
    /**
     * Get Staff Leave Type info for given Staff Leave Type Id.
     * 
     * @param staffId - staffId
     * @param joinDate - join date
     * @param toDate - to date
     * @param staffLeaveTypeId - selected staffLeaveTypeId
     * @return List of object arrays
     * @throws AkuraAppException AkuraAppException
     */
    List<Object[]> findStaffLeaveForStaffLeaveTypeForJoinYear(int staffId, Date joinDate, Date toDate, 
            int staffLeaveTypeId) throws AkuraAppException;
    
    /**
     * Get staff leave list of, staff in mentioned type that, status of
     * the leave for mentioned date either Approved or In progress.
     * 
     * @param isAcademic boolean Considering staff type (Academic / Non Academic)
     * @param date 		 Date    Considering date
     * @return staffList List of staff  Matching staff list
     * @throws AkuraAppException throws when retrieving the list of staff fails
     */
    List<StaffLeave> getAllowableStaffLeaveListByStaffTypeAndDate(boolean isAcademic, Date date)
    		throws AkuraAppException;
    
    /**
     * Get All Staff Leaves by status id.
     * 
     * @param statusId - statusId
     * @return List of StaffLeave
     * @throws AkuraAppException AkuraAppException
     */
	List<Object[]> getStaffLeaveListByStatusId(int statusId)
			throws AkuraAppException;
}
