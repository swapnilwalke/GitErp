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

package com.virtusa.akura.staff.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClassTeacher;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.SectionHead;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffClubSociety;
import com.virtusa.akura.api.dto.StaffEducation;
import com.virtusa.akura.api.dto.StaffExternalActivity;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.dto.StaffLeaveStatus;
import com.virtusa.akura.api.dto.StaffPastService;
import com.virtusa.akura.api.dto.StaffProfessional;
import com.virtusa.akura.api.dto.StaffProfileReportTemplate;
import com.virtusa.akura.api.dto.StaffSeminar;
import com.virtusa.akura.api.dto.StaffSport;
import com.virtusa.akura.api.dto.SubjectTeacher;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.api.exception.DayTypeLeaveDayException;
import com.virtusa.akura.api.exception.ExistStaffLeaveException;
import com.virtusa.akura.api.exception.InvalidRejoinDateException;
import com.virtusa.akura.api.exception.LeaveApplyBeforeJoinDateException;
import com.virtusa.akura.api.exception.LeaveDayHolidayException;
import com.virtusa.akura.api.exception.NotAvailableLeavesForLeaveTypeException;

/**
 * @author Virtusa Corporation
 */
public interface StaffService {
    
    /**
     * Service to add a staff object.
     * 
     * @param staff Staff object to set
     * @return check add staff is successful.
     * @throws AkuraAppException throw SMS exception.
     */
    Staff addStaff(Staff staff) throws AkuraAppException;
    
    /**
     * Service to delete a staff object.
     * 
     * @param objSraff Staff object to be deleted.
     * @throws AkuraAppException throw SMS exception.
     */
    void deleteStaff(Staff objSraff) throws AkuraAppException;
    
    /**
     * Service to view a staff object.
     * 
     * @param staffID id of the Staff
     * @return Staff object.
     * @throws AkuraAppException throw SMS exception.
     */
    Object viewStaff(int staffID) throws AkuraAppException;
    
    /**
     * Service to get a SectionHead object.
     * 
     * @param sectionHeadID of the SectionHead.
     * @return SectionHead object.
     * @throws AkuraAppException throw SMS exception.
     */
    SectionHead getSectionHead(int sectionHeadID) throws AkuraAppException;
    
    /**
     * Service to modify a staff object.
     * 
     * @param staff Staff object to modify.
     * @throws AkuraAppException throw SMS exception.
     */
    void modifyStaff(Staff staff) throws AkuraAppException;
    
    /**
     * Service to view a Collection of staff objects.
     * 
     * @return Collection of staff objects.
     * @throws AkuraAppException throw SMS exception.
     */
    Collection<Staff> viewAllStaff() throws AkuraAppException;
    
    /**
     * Service to assign a SectionHead object.
     * 
     * @param head SectionHead object to add.
     * @return check assign SectionHead is successful.
     * @throws AkuraAppException throw SMS exception.
     */
    SectionHead assignSectionHead(SectionHead head) throws AkuraAppException;
    
    /**
     * Service to update a SectionHead object.
     * 
     * @param head SectionHead object to update.
     * @throws AkuraAppException throw SMS exception.
     */
    void updateSectionHead(SectionHead head) throws AkuraAppException;
    
    /**
     * method to get section head list.
     * 
     * @return section head list
     * @throws AkuraAppException throw SMS exception.
     */
    List<SectionHead> getSectionHeadList() throws AkuraAppException;
    
    /**
     * Service to assign a ClassTeacher object.
     * 
     * @param teacher ClassTeacher object to add.
     * @return check assign ClassTeacher is successful.
     * @throws AkuraAppException throw SMS exception.
     */
    ClassTeacher assignClassTeacher(ClassTeacher teacher) throws AkuraAppException;
    
    /**
     * Service to update a ClassTeacher object.
     * 
     * @param teacher ClassTeacher object to update.
     * @throws AkuraAppException throw SMS exception.
     */
    void modifyClassTeacher(ClassTeacher teacher) throws AkuraAppException;
    
    /**
     * Service to assign a SubjectTeacher object.
     * 
     * @param teacher SubjectTeacher object to add.
     * @return check assign SubjectTeacher is successful.
     * @throws AkuraAppException throw SMS exception.
     */
    SubjectTeacher assignSubjectTeacher(SubjectTeacher teacher) throws AkuraAppException;
    
    /**
     * Service to update a SubjectTeacher object.
     * 
     * @param teacher SubjectTeacher object to update.
     * @throws AkuraAppException throw SMS exception.
     */
    void modifySubjectTeacher(SubjectTeacher teacher) throws AkuraAppException;
    
    /**
     * Service to delete a SectionHead object.
     * 
     * @param head SectionHead object to be deleted.
     * @throws AkuraAppException throw SMS exception.
     */
    void deleteSectionHead(SectionHead head) throws AkuraAppException;
    
    /**
     * Service to delete a ClassTeacher object.
     * 
     * @param teacher ClassTeacher object to be deleted.
     * @throws AkuraAppException throw SMS exception.
     */
    void deleteClassTeacher(ClassTeacher teacher) throws AkuraAppException;
    
    /**
     * Service to delete a SubjectTeacher object.
     * 
     * @param staffId staff id.
     * @param gradeSubjectId gradesubject Id
     * @param year check year.
     * @throws AkuraAppException throw SMS exception.
     */
    void deleteSubjectTeacher(int staffId, int gradeSubjectId, String year) throws AkuraAppException;
    
    /**
     * Service method is to search staff.
     * 
     * @param staff - Staff object to be searched.
     * @return List of Staff members.
     * @throws AkuraAppException SMS Exception.
     */
    List<Staff> searchStaff(Staff staff) throws AkuraAppException;
    
    /**
     * Service method is to search Section Head.
     * 
     * @param staff - Staff object to be searched.
     * @param year of type string
     * @return List of Section Head members.
     * @throws AkuraAppException SMS Exception.
     */
    List<SectionHead> searchSectionHead(Staff staff, String year) throws AkuraAppException;
    
    /**
     * Service to delete a staff member.
     * 
     * @param staffId - id of the staff member.
     * @throws AkuraAppException throws if exception occurs.
     */
    void deleteStaff(int staffId) throws AkuraAppException;
    
    /**
     * Searches results for the given year, last name of the staff and the class grade criteria.
     * 
     * @param description - description of the ClassGrade
     * @param lName - last name of the Staff
     * @param searchYear - year
     * @return - a list of ClassTeachers
     * @throws AkuraAppException - The exception details that occurred when searching ClassTeachers for a
     *         given criteria.
     */
    List<ClassTeacher> searchClassTeacher(String description, String lName, String searchYear) throws AkuraAppException;
    
    /**
     * Finds the instance of Staff for a given id.
     * 
     * @param staffId - id of the staff
     * @return - the instance of Staff
     * @throws AkuraAppException - The exception details that occurred when finding the relevant Staff
     */
    Staff findStaff(int staffId) throws AkuraAppException;
    
    /**
     * Adds the relevant classTeacher.
     * 
     * @param classTeacher - the instance of classTeacher
     * @return - the newly saved instance of ClassTeacher
     * @throws AkuraAppException - The exception details that occurred when saving the relevant ClassTeacher
     */
    ClassTeacher addClassTeacher(ClassTeacher classTeacher) throws AkuraAppException;
    
    /**
     * Updates the relevant instance of ClassTeacher.
     * 
     * @param classTeacher - the instance of ClassTeacher
     * @throws AkuraAppException - The exception details that occurred when updating the relevant ClassTeacher
     */
    void editClassTeacher(ClassTeacher classTeacher) throws AkuraAppException;
    
    /**
     * Finds the classTeacher for a given classTeacher id.
     * 
     * @param id - the id of the ClassTeacher
     * @return - the relevant instance of ClassTeacher
     * @throws AkuraAppException - The exception details that occurred when finding the relevant ClassTeacher
     */
    ClassTeacher findClassTeacher(int id) throws AkuraAppException;
    
    /**
     * Returns a list of ClassTeachers by year.
     * 
     * @param year - the year.
     * @return - a list of ClassTeachers
     * @throws AkuraAppException - The exception details that occurred when retrieving all ClassTeachers
     */
	List<ClassTeacher> getClassTeacherListByYear(Date year)
			throws AkuraAppException;
    
    /**
     * get Class teacher for given class grade and year.
     * 
     * @param classGrade ClassGrade combine object
     * @param year year
     * @return ClassTeacher object
     * @throws AkuraAppException The exception details that occurred when retrieving
     */
    ClassTeacher getClassTeacher(ClassGrade classGrade, int year) throws AkuraAppException;
    
    /**
     * check whether the registration no is a valid one.
     * 
     * @param registrationNo - registration no of the staff member
     * @throws AkuraAppException AkuraAppException
     * @return flag indicates whether admissionNO exist in Student or Staff table.
     */
    boolean isValidRegistrationNo(String registrationNo) throws AkuraAppException;
    
    /**
     * Service method is to get staffId for the registrationNo.
     * 
     * @param registrationNo - registrationNo.
     * @return flag states whther admission no is exist.
     * @throws AkuraAppException AkuraAppException
     */
    int findStaffIdForRegistrationNo(String registrationNo) throws AkuraAppException;
    
    /**
     * Service method is to get list of subject teachers by giving last name and registration number.
     * 
     * @param lname - last name.
     * @param regNo - registrationNo.
     * @return list of subject teachers.
     * @throws AkuraAppException AkuraAppException
     */
    List<SubjectTeacher> getSubjectTeachers(String lname, String regNo) throws AkuraAppException;
    
    /**
     * gets the subject teacher for given class grade and year.
     * 
     * @param classGrade class and grade combined object
     * @param year year as int
     * @return list of SubjectTeacher
     * @throws AkuraAppException exception occurs
     */
    List<SubjectTeacher> getSubjectTeachers(ClassGrade classGrade, int year) throws AkuraAppException;
    
    /**
     * Service method is to get list of academic staff.
     * 
     * @return list of staff.
     * @throws AkuraAppException AkuraAppException
     */
    List<Staff> getAcedemicStaff() throws AkuraAppException;
    
    /**
     * Service method is to get list of staff by type.
     * 
     * @param staffType - boolean.
     * @param selectedDate - date.
     * @return list of staff.
     * @throws AkuraAppException AkuraAppException
     */
    List<Staff> getStaffByType(boolean staffType, Date selectedDate) throws AkuraAppException;
    
    /**
     * Service method is to get list of classes for a particular staff for a particular grade subject.
     * 
     * @param staffId - staff id.
     * @param gradeSubjectId - grade subject ID.
     * @param year - assigned year.
     * @return list of classes for a particular staff for a particular grade subject.
     * @throws AkuraAppException AkuraAppException
     */
    List<Integer> getteacherClassesfofgradesubject(int staffId, int gradeSubjectId, String year)
            throws AkuraAppException;
    
    /**
     * Service method is to delete Subject Teacher.
     * 
     * @param subTeacher - subject Teacher
     * @throws AkuraAppException AkuraAppException
     */
    void deleteSubjectTeacher(SubjectTeacher subTeacher) throws AkuraAppException;
    
    /**
     * Service to add a staffEducation object.
     * 
     * @param staffEducation staffEducation object to set
     * @return check add staffEducation is successful.
     * @throws AkuraAppException throw SMS exception.
     */
    StaffEducation addStaffEducation(StaffEducation staffEducation) throws AkuraAppException;
    
    /**
     * Service to delete a StaffEducation object.
     * 
     * @param objStaffEducation StaffEducation object to be deleted.
     * @throws AkuraAppException throw SMS exception.
     */
    void deleteStaffEducation(StaffEducation objStaffEducation) throws AkuraAppException;
    
    /**
     * Service to view a StaffEducation object.
     * 
     * @param staffEducationId id of the Staff
     * @return StaffEducation object.
     * @throws AkuraAppException throw SMS exception.
     */
    Object viewStaffEducation(int staffEducationId) throws AkuraAppException;
    
    /**
     * Service to update a StaffEducation object.
     * 
     * @param objStaffEducation StaffEducation object to update.
     * @throws AkuraAppException throw SMS exception.
     */
    void updateStaffEducation(StaffEducation objStaffEducation) throws AkuraAppException;
    
    /**
     * service to get the list of StaffEducation of a given staff.
     * 
     * @param staffId the id of the Staff.
     * @return List of StaffEducation
     * @throws AkuraAppException - The exception details that occurred when retrieve a list of StaffEducation
     *         instances.
     */
    List<StaffEducation> getStaffEducationListForStaff(int staffId) throws AkuraAppException;
    
    /**
     * Service to add a StaffProfessional object.
     * 
     * @param staffProfessional StaffProfessional object to set
     * @return check add StaffProfessional is successful.
     * @throws AkuraAppException throw SMS exception.
     */
    StaffProfessional addStaffProfessional(StaffProfessional staffProfessional) throws AkuraAppException;
    
    /**
     * Service to delete a StaffProfessional object.
     * 
     * @param objStaffProfessional StaffProfessional object to be deleted.
     * @throws AkuraAppException throw SMS exception.
     */
    void deleteStaffProfessional(StaffProfessional objStaffProfessional) throws AkuraAppException;
    
    /**
     * Service to view a StaffProfessional object.
     * 
     * @param staffProfessionalId id of the Staff
     * @return StaffProfessional object.
     * @throws AkuraAppException throw SMS exception.
     */
    Object viewStaffProfessional(int staffProfessionalId) throws AkuraAppException;
    
    /**
     * Service to update a StaffProfessional object.
     * 
     * @param objStaffProfessional StaffProfessional object to update.
     * @throws AkuraAppException throw SMS exception.
     */
    void updateStaffProfessional(StaffProfessional objStaffProfessional) throws AkuraAppException;
    
    /**
     * service to get the list of StaffProfessional of a given staff.
     * 
     * @param staffId the id of the Staff.
     * @return List of StaffProfessional
     * @throws AkuraAppException - The exception details that occurred when retrieve a list of
     *         StaffProfessional instances.
     */
    List<StaffProfessional> getStaffProfessionalListForStaff(int staffId) throws AkuraAppException;
    
    /**
     * Returns the max staffId.
     * 
     * @return - the max staffId
     * @throws AkuraAppException - The exception details that occurred when retrieving the max staffId
     */
    int getMaxStaffId() throws AkuraAppException;
    
    /**
     * Add a StaffLeave.
     * 
     * @param staffLeaveObj {@link StaffLeave}
     * @return {@link StaffLeave}
     * @throws AkuraAppException throws if exception occurs when adding a StaffLeave instance.
     */
    // StaffLeave addStaffLeave(StaffLeave staffLeaveObj) throws AkuraAppException;
    
    /**
     * Update a StaffLeave.
     * 
     * @param staffLeaveObj - {@link StaffLeave} .
     * @throws AkuraAppException throws if exception occurs when update a StaffLeave instance.
     */
    void updateStaffLeave(StaffLeave staffLeaveObj) throws AkuraAppException;
    
    /**
     * Delete a StaffLeave.
     * 
     * @param staffLeaveObj - {@link StaffLeave} .
     * @throws AkuraAppException throws if exception occurs when deleting a StaffLeaveinstance.
     */
    void deleteStaffLeave(StaffLeave staffLeaveObj) throws AkuraAppException;
    
    /**
     * Find staffLeave by id.
     * 
     * @param staffLeaveId - int
     * @return staffLeaveObj - {@link StaffLeave} .
     * @throws AkuraAppException throws if exception occurs when deleting a StaffLeaveinstance.
     */
    StaffLeave findStaffLeaveById(int staffLeaveId) throws AkuraAppException;
    
    /**
     * Retrieve all the available StaffLeave.
     * 
     * @param staffId - int
     * @return list of StaffLeave
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StaffLeave> getStaffLeaveListByStaffId(int staffId) throws AkuraAppException;
    
    /**
     * Retrieve all the available StaffLeave information given by staff Id and Date period.
     * 
     * @param fromDate Date from
     * @param toDate Date to
     * @param staffId staff id
     * @return list of StaffLeave
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StaffLeave> getStaffLeaveListByDatePeriodAndStaffId(Date fromDate, Date toDate, int staffId)
            throws AkuraAppException;
    
    /**
     * Creates a cub&society for a staff member.
     * 
     * @param staffClubSociety a DTO holds all the properties to add a club&society for a staff
     * @return a staff club society object.
     * @throws AkuraAppException throws a detailed exception when add a club&society for a staff member
     */
    StaffClubSociety addStaffClubSociety(StaffClubSociety staffClubSociety) throws AkuraAppException;
    
    /**
     * Updates a cub&society of staff member.
     * 
     * @param staffClubSociety a DTO holds all the properties to add a club&society for a staff
     * @throws AkuraAppException throws a detailed exception when modify a club&society for a staff member
     */
    void modifyStaffClubSociety(StaffClubSociety staffClubSociety) throws AkuraAppException;
    
    /**
     * Deletes a cub&society for a staff member.
     * 
     * @param staffClubSociety a DTO holds all the properties to add a club&society for a staff
     * @throws AkuraAppException throws a detailed exception when add a club&society for a staff member
     */
    void deleteStaffClubSociety(StaffClubSociety staffClubSociety) throws AkuraAppException;
    
    /**
     * Retrieves a cub&society for a staff member.
     * 
     * @param staffClubSocietyId is a unique identification key for a StaffClubSociety object.
     * @return a staff club society object.
     * @throws AkuraAppException throws a detailed exception when add a club&society for a staff member
     */
    StaffClubSociety getStaffClubSociety(int staffClubSocietyId) throws AkuraAppException;
    
    /**
     * Retrieves list of cub&society for a staff member.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - hold date type
     * @return list of cub&society object.
     * @throws AkuraAppException throws a detailed exception when add a club&society for a staff member
     */
    List<?> getStaffClubSocietyList(int staffId, Date dateSelectedYear) throws AkuraAppException;
    
    /**
     * Retrieves list of cub&society for a staff member.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - hold date type
     * @param clubSocietyId - holds integer type
     * @return list of cub&society object.
     * @throws AkuraAppException throws a detailed exception when add a club&society for a staff member
     */
    List<StaffClubSociety> getStaffClubSociety(int staffId, Date dateSelectedYear, int clubSocietyId)
            throws AkuraAppException;
    
    /**
     * Creates a StaffExternalActivity for a staff member.
     * 
     * @param staffExternalActivity a DTO holds all the properties to add a StaffExternalActivity for a staff
     * @return a StaffExternalActivity object.
     * @throws AkuraAppException throws a detailed exception when add a StaffExternalActivity for a staff
     *         member
     */
    StaffExternalActivity addStaffExternalActivity(StaffExternalActivity staffExternalActivity)
            throws AkuraAppException;
    
    /**
     * Updates a StaffExternalActivity of staff member.
     * 
     * @param staffExternalActivity a DTO holds all the properties to modify a StaffExternalActivity for a
     *        staff
     * @throws AkuraAppException throws a detailed exception when modify a StaffExternalActivity for a staff
     *         member
     */
    void modifyStaffExternalActivity(StaffExternalActivity staffExternalActivity) throws AkuraAppException;
    
    /**
     * Retrieves a StaffExternalActivity for a staff member.
     * 
     * @param staffExternalActivityId is a unique identification key for a StaffExternalActivity object.
     * @return a StaffExternalActivity object.
     * @throws AkuraAppException throws a detailed exception when retrieve a StaffExternalActivity for a staff
     *         member
     */
    StaffExternalActivity getStaffExternalActivity(int staffExternalActivityId) throws AkuraAppException;
    
    /**
     * Retrieves list of StaffExternalActivity for a staff member.
     * 
     * @param sessionStaffId - holds integer type
     * @param dateSelectedYear - hold date type
     * @return list of StaffExternalActivity object.
     * @throws AkuraAppException throws a detailed exception when retrieve list of StaffExternalActivity for a
     *         staff member
     */
    List<StaffExternalActivity> getStaffExternalActivityList(int sessionStaffId, Date dateSelectedYear)
            throws AkuraAppException;
    
    /**
     * Deletes a StaffExternalActivity for a staff member.
     * 
     * @param staffExternalActivity a DTO holds all the properties to add a external activity for a staff
     * @throws AkuraAppException throws a detailed exception when delete a StaffExternalActivity for a staff
     *         member
     */
    void deleteStaffExternalActivity(StaffExternalActivity staffExternalActivity) throws AkuraAppException;
    
    /**
     * Retrieves list of external activities for a staff member based on the selected year.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - hold date type
     * @param activity - holds string type
     * @param achievement - holds String type
     * @return list of StaffExternalActivity object.
     * @throws AkuraAppException throws a detailed exception when retrieve an external activity for a staff
     *         member
     */
    List<StaffExternalActivity> getStaffExternalActivity(int staffId, Date dateSelectedYear, String activity,
            String achievement) throws AkuraAppException;
    
    /**
     * Creates a seminar for a staff member.
     * 
     * @param staffSeminar a DTO holds all the properties to add a StaffSeminar for a staff
     * @return a StaffSeminar object.
     * @throws AkuraAppException throws a detailed exception when add a StaffSeminar for a staff member
     */
    StaffSeminar addStaffSeminar(StaffSeminar staffSeminar) throws AkuraAppException;
    
    /**
     * Updates a StaffSeminar of staff member.
     * 
     * @param staffSeminar a DTO holds all the properties to modify a StaffSeminar for a staff
     * @throws AkuraAppException throws a detailed exception when modify a StaffSeminar for a staff member
     */
    void modifyStaffSeminar(StaffSeminar staffSeminar) throws AkuraAppException;
    
    /**
     * Deletes a StaffSeminar of a staff member.
     * 
     * @param staffSeminar a DTO holds all the properties to delete a StaffSeminar of a staff
     * @throws AkuraAppException throws a detailed exception when delete a StaffSeminar for a staff member
     */
    void deleteStaffSeminar(StaffSeminar staffSeminar) throws AkuraAppException;
    
    /**
     * Retrieves a seminar for a staff member.
     * 
     * @param staffSeminarId is a unique identification key for a StaffSeminar object.
     * @return a staff seminar object.
     * @throws AkuraAppException throws a detailed exception when retrieve a seminar for a staff member
     */
    StaffSeminar getStaffSeminar(int staffSeminarId) throws AkuraAppException;
    
    /**
     * Retrieves list of seminars for a staff member.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - hold date type
     * @return list of StaffSeminar object.
     * @throws AkuraAppException throws a detailed exception when retrieve a seminar for a staff member
     */
    List<?> getStaffSeminarList(int staffId, Date dateSelectedYear) throws AkuraAppException;
    
    /**
     * Creates a Sport for a staff member.
     * 
     * @param staffSport a DTO holds all the properties to add a staffSport for a staff
     * @return a staff club society object.
     * @throws AkuraAppException throws a detailed exception when add a sport for a staff member
     */
    StaffSport addStaffSport(StaffSport staffSport) throws AkuraAppException;
    
    /**
     * Updates a sports of a staff member.
     * 
     * @param staffSport a DTO holds all the properties of StaffSport
     * @throws AkuraAppException throws a detailed exception when modify a StaffSport for a staff member
     */
    void modifyStaffSport(StaffSport staffSport) throws AkuraAppException;
    

    
    /**
     * Retrieves list of class grades for a staff member who is a class teacher.
     * 
     * @param staffId - holds integer type
     * @return list of ClassGrade object.
     * @throws AkuraAppException throws a detailed exception when retrieve a class grades  of a  staff member
     */
     List<ClassGrade> getClassListOfaClassTeacherInCurrentYrear(int staffId)throws AkuraAppException;
    
    /**
     * Deletes a sport of a staff member.
     * 
     * @param staffSport a DTO holds all the properties of StaffSport
     * @throws AkuraAppException throws a detailed exception when delete a sport of a staff member
     */
    void deleteStaffSport(StaffSport staffSport) throws AkuraAppException;
    
    /**
     * Retrieves a staff sport using a staff member sport id.
     * 
     * @param staffSportId is a unique identification key for a StaffSport object.
     * @return a staff sport object.
     * @throws AkuraAppException throws a detailed exception when add a sport for a staff member
     */
    StaffSport getStaffSport(int staffSportId) throws AkuraAppException;
    
    /**
     * Retrieve the list of Staff members by category.
     * 
     * @return list of Staff members.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    List<Staff> getAllAvailableStaff() throws AkuraAppException;
    
    /**
     * Retrieve the list of Staff members by category.
     * 
     * @return list of Staff members except departure.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    List<Staff> getAllAvailableStaffWithOutDeparture() throws AkuraAppException;
    
    /**
     * Retrieve the list of Staff members by category.
     * 
     * @return list of Staff members except departure.
     * @param date - date.
     * @param hDate - hDate.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    List<Staff> getAllAvailableStaffWithOutDepartureDate(Date date, Date hDate) throws AkuraAppException;
    
    /**
     * Retrieve the list of Staff members by category.
     * 
     * @param staffCategoryId the staff type to retrieve.
     * @return list of Staff members.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    List<Staff> getStaffListByCategory(int staffCategoryId) throws AkuraAppException;
    
    /**
     * Retrieve the list of Staff members by category.
     * 
     * @param staffCategoryId the staff type to retrieve.
     * @return list of Staff members.
     * @param date Date.
     * @param hDate Date.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    List<Staff> getStaffListByCategoryByDate(int staffCategoryId, Date date, Date hDate) throws AkuraAppException;
    
    /**
     * Get staff leave list of, staff in mentioned type that, status of
     * the leave for mentioned date either Approved or In progress.
     * 
     * @param isAcademic boolean Considering staff type (Academic / Non Academic)
     * @param date Date Considering date
     * @return staffList List of staff Matching staff list
     * @throws AkuraAppException throws when retrieving the list of staff fails
     */
    List<StaffLeave> getAllowableStaffLeaveListByStaffTypeAndDate(boolean isAcademic, Date date)
            throws AkuraAppException;
    
    /**
     * Retrieve the list of All Staff members by category.
     * 
     * @param staffCategoryId the staff type to retrieve.
     * @return list of All Staff members.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    List<Staff> getAllStaffListByCategory(int staffCategoryId) throws AkuraAppException;
    
    /**
     * Method is to get list of ClassTeachers by Grade.
     * 
     * @param gradeId Id to be searched.
     * @return ClassTeacher list.
     * @throws AkuraAppException - throw this if fails.
     */
    List<ClassTeacher> getClassTeacherListByGrade(int gradeId) throws AkuraAppException;
    
    /**
     * Gets the staff sports list.
     * 
     * @param staffId the staff id
     * @param dateSelectedYear the date selected year
     * @return the staff sports list
     * @throws AkuraAppException the sMS app exception
     */
    List<StaffSport> getStaffSportsList(int staffId, Date dateSelectedYear) throws AkuraAppException;
    
    /**
     * Gets the staff sport.
     * 
     * @param staffId the staff id
     * @param selectedYear the selected year
     * @param sportCategoryId the sport category id
     * @return the staff sport
     * @throws AkuraAppException the sMS app exception
     */
    List<StaffSport> getStaffSports(int staffId, Date selectedYear, int sportCategoryId) throws AkuraAppException;
    
    /**
     * Get the current Section Heads list.
     * 
     * @param today The current date.
     * @return List - The list of current sectional heads.
     * @throws AkuraAppException - Throws when fails to get Sectional Heads list.
     */
    List<SectionHead> getCurrentSectionHeadList(Date today) throws AkuraAppException;
    
    /**
     * Get the Subject Teacher list.
     * 
     * @param staffId the id of the staff member.
     * @param gradeSubjectId the grade subject id.
     * @param year - The subject assigned year.
     * @return List - The list of Subject Teachers.
     * @throws AkuraAppException - Throws when fails to get the subject teacher list.
     */
    List<SubjectTeacher> getSubjectTeacherList(int staffId, int gradeSubjectId, String year) throws AkuraAppException;
    
    /**
     * Get Staff Profile Template by staff id.
     * 
     * @param staffId - staffId
     * @return List - StaffProfileReportTemplate
     * @throws AkuraAppException AkuraAppException
     */
    List<StaffProfileReportTemplate> getStaffProfileTemplateByStaffId(int staffId) throws AkuraAppException;
    
    /**
     * Get the total no of staff leaves by staff Id and previous year period.
     * 
     * @param staffId specifies the staff id, defined by an integer
     * @param fromDate specifies Date from
     * @param toDate specifies Date to
     * @return total no of days
     * @throws AkuraAppException - Throw Exception
     */
    int getStaffLeaveCountByPreviousYear(Date fromDate, Date toDate, int staffId) throws AkuraAppException;
    
    /**
     * Get Subjects taught at present by staff id and current year.
     * 
     * @param staffId staff id
     * @param year current year
     * @return list of subjects
     * @throws AkuraAppException AkuraAppException
     */
    List<String> getCurrentSubjectsByStaffId(int staffId, String year) throws AkuraAppException;
    
    /**
     * Retrieves cub&societies for a staff member.
     * 
     * @param staffId - holds integer type
     * @return list of club societies for a staff.
     * @throws AkuraAppException - throw detailed exception when retrieve list of staff club societies
     */
    List<Object[]> getStaffClubSocietyByStaffId(int staffId) throws AkuraAppException;
    
    /**
     * Get staff sports by staff id.
     * 
     * @param staffId staffId
     * @return list of staff sports.
     * @throws AkuraAppException AkuraAppException
     */
    List<Object[]> getStaffSportByStaffId(int staffId) throws AkuraAppException;
    
    /**
     * Get the Subject Teacher by Id.
     * 
     * @param subjectTeacherId - subject teacher id.
     * @return SubjectTeacher.
     * @throws AkuraAppException - Throws when fails to get the subject teacher list.
     */
    SubjectTeacher findsubjectTeacherById(int subjectTeacherId) throws AkuraAppException;
    
    /**
     * Get the School class list.
     * 
     * @param staffId the id of the staff member.
     * @param gradeSubjectId the grade subject id.
     * @param year - The subject assigned year.
     * @return List - The list of Subject Teachers.
     * @throws AkuraAppException - Throws when fails to get the subject teacher list.
     */
    List<SchoolClass> getSchoolClassList(int staffId, int gradeSubjectId, String year) throws AkuraAppException;
    
    /**
     * Check the email availability.
     * 
     * @param email to be checked.
     * @return true if email already exist.
     * @throws AkuraAppException - Throws when fails to get the subject teacher list.
     */
    boolean checkEmailAvailability(String email) throws AkuraAppException;
    
    /**
     * gets the email addresses if any, for given staff id list.
     * 
     * @param staffIdList staff id list
     * @return email address list
     * @throws AkuraAppException when exception occurs.
     */
    List<String> getStaffEmails(List<Integer> staffIdList) throws AkuraAppException;
    
    /**
     * Gets the section head list if any, for given staff id and departure date.
     * 
     * @param staffId the id of the staff member
     * @param dateOfDeparture the date
     * @return section head list
     * @throws AkuraAppException when exception occurs.
     */
    List<SectionHead> getSectionalHeadsById(int staffId, Date dateOfDeparture) throws AkuraAppException;
    
    /**
     * Update the section head list with correct end date.
     * 
     * @param updatedSectionHeadList selection head list
     * @throws AkuraAppException when exception occurs.
     */
    void updateSectionHeadList(List<SectionHead> updatedSectionHeadList) throws AkuraAppException;
    
    /**
     * Get applied staff leave type list with info.
     * 
     * @param staffId - staffId
     * @param fromDate - selected year
     * @return list of Object array
     * @throws AkuraAppException AkuraAppException
     */
    List<Object[]> findStaffLeaveTypeInfo(int staffId, Date fromDate) throws AkuraAppException;
    
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
     * check whether the user has departure date or not.
     * 
     * @param registrationNo - registration no of the staff member
     * @throws AkuraAppException AkuraAppException
     * @return flag indicates whether user has a departure date or not.
     */
    boolean hasDepartureDate(String registrationNo) throws AkuraAppException;
    
    /**
     * Retrieve the list of Departure Staff members by category.
     * 
     * @param staffCategoryId the staff type to retrieve.
     * @return list of Staff members.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    List<Staff> getDepatureStaffListByCategory(int staffCategoryId) throws AkuraAppException;
    
    /**
     * This method handles the staff member rejoin functionality.
     * 
     * @param staffId - the id of the staff member to be rejoined.
     * @param rejoinDate - the rejoin date of the staff member.
     * @return true if staff member is rejoined successfully.
     * @throws InvalidRejoinDateException - Throws when user enters a date lower than the departure date for
     *         rejoin.
     * @throws AkuraException - Throws detailed exception when fails find staff member by Id.
     */
    boolean rejoinStaffMemberService(int staffId, Date rejoinDate) throws InvalidRejoinDateException,
            AkuraException;
    
    /**
     * Returns all the staff past services for the given staff id.
     * 
     * @param staffId the id of the staff member.
     * @return - a list of staff past services for the given staff id.
     * @throws AkuraAppException when exception occurs.
     */
    List<StaffPastService> getPastStaffServiceByStaffId(int staffId) throws AkuraAppException;
    
    /**
     * terminates a staff member.
     * 
     * @param staffPastService - StaffPastService object of the particular staff member
     * @throws AkuraAppException when exception occurs.
     */
    void terminateStaff(StaffPastService staffPastService) throws AkuraAppException;
    
    /**
     * Returns the key of the staff or the student by the identification number.
     * 
     * @param identification - the identification number.
     * @return - the key for the relevant identification number.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    int getKeyByIdentificationNo(String identification) throws AkuraAppException;
    
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
     * @param toDate - toDate
     * @return list of Object array
     * @throws AkuraAppException AkuraAppException
     */
    List<Object[]> findStaffLeaveTypeInfoForJoinYear(int staffId, Date fromDate, Date toDate) throws AkuraAppException;
    
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
     * Returns all the Staff Leave status.
     * 
     * @return - a list of all Leave status.
     * @throws AkuraAppException when exception occurs.
     */
    List<StaffLeaveStatus> getLeaveStatusList() throws AkuraAppException;
    
    /**
     * Returns all the Staff Leave list by status id.
     * 
     * @param statusId - statusId
     * @return - a list of staff Leave by status id .
     * @throws AkuraAppException when exception occurs.
     */
	List<Object[]> getStaffLeaveListByStatusId(int statusId)
			throws AkuraAppException;
    
    /**
     * Returns Staff Leave Status by status id.
     * 
     * @param statusId - statusId
     * @return - a staff Leave status by status id .
     * @throws AkuraAppException when exception occurs.
     */
    StaffLeaveStatus findStaffStatusById(int statusId) throws AkuraAppException;
    
    /**
     * Service to add a StaffLeave object.
     * 
     * @param staffLeave - model object
     * @param staffId - staff id
     * @param request - HttpServletRequest
     * @return boolean value whether the leaves available or not.
     * @throws AkuraAppException - AkuraAppException
     * @throws LeaveApplyBeforeJoinDateException - LeaveApplyBeforeJoinDateException
     * @throws ExistStaffLeaveException - ExistStaffLeaveException
     * @throws DayTypeLeaveDayException - DayTypeLeaveDayException
     * @throws LeaveDayHolidayException - LeaveDayHolidayException
     * @throws NotAvailableLeavesForLeaveTypeException - NotAvailableLeavesForLeaveTypeException
     */
	StaffLeave addStaffLeave(StaffLeave staffLeave, int staffId, HttpServletRequest request) 
            throws AkuraAppException, LeaveApplyBeforeJoinDateException, ExistStaffLeaveException,
            DayTypeLeaveDayException, LeaveDayHolidayException, NotAvailableLeavesForLeaveTypeException;
    
    /**
     * Service to update a StaffLeave object.
     * 
     * @param staffLeave - model object
     * @param staffId - staff id
     * @param request - HttpServletRequest
     * @throws AkuraAppException - AkuraAppException
     * @throws LeaveApplyBeforeJoinDateException - LeaveApplyBeforeJoinDateException
     * @throws ExistStaffLeaveException - ExistStaffLeaveException
     * @throws DayTypeLeaveDayException - DayTypeLeaveDayException
     * @throws LeaveDayHolidayException - LeaveDayHolidayException
     * @throws NotAvailableLeavesForLeaveTypeException - NotAvailableLeavesForLeaveTypeException
     */
    void updateStaffLeave(StaffLeave staffLeave, int staffId, HttpServletRequest request) throws AkuraAppException,
            LeaveApplyBeforeJoinDateException, ExistStaffLeaveException, DayTypeLeaveDayException,
            LeaveDayHolidayException, NotAvailableLeavesForLeaveTypeException;
    
    /**
     * Get the number of staff members in the school.
     * 
     * @return the number of staff members in the school.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    int getNumberOfStaffInSchool() throws AkuraAppException;
    
   
    /**
     * check whether the  staff members is a class teacher or not.
     * 
     * @return true if staff member is a class teacher,false if staff member is not a class teacher.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
     boolean isClassTeacher(int staffId) throws AkuraAppException;
}
