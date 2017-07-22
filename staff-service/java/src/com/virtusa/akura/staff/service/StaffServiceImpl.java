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

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClassTeacher;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.SectionHead;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffClubSociety;
import com.virtusa.akura.api.dto.StaffEducation;
import com.virtusa.akura.api.dto.StaffExternalActivity;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.dto.StaffLeaveStatus;
import com.virtusa.akura.api.dto.StaffLeaveType;
import com.virtusa.akura.api.dto.StaffPastService;
import com.virtusa.akura.api.dto.StaffProfessional;
import com.virtusa.akura.api.dto.StaffProfileReportTemplate;
import com.virtusa.akura.api.dto.StaffSeminar;
import com.virtusa.akura.api.dto.StaffSport;
import com.virtusa.akura.api.dto.SubjectTeacher;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.enums.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.DayTypeLeaveDayException;
import com.virtusa.akura.api.exception.ExistStaffLeaveException;
import com.virtusa.akura.api.exception.InvalidRejoinDateException;
import com.virtusa.akura.api.exception.LeaveApplyBeforeJoinDateException;
import com.virtusa.akura.api.exception.LeaveDayHolidayException;
import com.virtusa.akura.api.exception.NotAvailableLeavesForLeaveTypeException;
import com.virtusa.akura.common.dao.HolidayDao;
import com.virtusa.akura.common.dao.StaffLeaveTypeDao;
import com.virtusa.akura.common.dao.UserLoginDao;
import com.virtusa.akura.staff.dao.ClassTeacherDao;
import com.virtusa.akura.staff.dao.SectionHeadDao;
import com.virtusa.akura.staff.dao.StaffClubSocietyDao;
import com.virtusa.akura.staff.dao.StaffDao;
import com.virtusa.akura.staff.dao.StaffEducationDao;
import com.virtusa.akura.staff.dao.StaffExternalActivityDao;
import com.virtusa.akura.staff.dao.StaffLeaveDao;
import com.virtusa.akura.staff.dao.StaffLeaveStatusDao;
import com.virtusa.akura.staff.dao.StaffPastServiceDao;
import com.virtusa.akura.staff.dao.StaffProfessionalDoa;
import com.virtusa.akura.staff.dao.StaffProfileReportDao;
import com.virtusa.akura.staff.dao.StaffSeminarDao;
import com.virtusa.akura.staff.dao.StaffSportDao;
import com.virtusa.akura.staff.dao.SubjectTeacherDao;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.HolidayUtil;
import com.virtusa.akura.util.PropertyReader;

/**
 * @author Virtusa Corporation
 */
public class StaffServiceImpl implements StaffService {
    
    /** Holds instance of a StaffDao. */
    private StaffDao staffDao;
    
    /** Holds instance of a SectionHeadDao. */
    private SectionHeadDao sectionHeadDao;
    
    /** Holds instance of a ClassTeacherDao. */
    private ClassTeacherDao classTeacherDao;
    
    /** Holds instance of a SubjectTeacherDao. */
    private SubjectTeacherDao subjectTeacherDao;
    
    /** Holds instance of a StaffEducationDao. */
    private StaffEducationDao staffEducationDao;
    
    /** Holds instance of a StaffProfessionalDoa. */
    private StaffProfessionalDoa staffProfessionalDoa;
    
    /** Holds instance of a StaffLeaveDao. */
    private StaffLeaveDao staffLeaveDao;
    
    /** Holds instance of a StaffClubSocietyDao. */
    private StaffClubSocietyDao staffClubSocietyDao;
    
    /** Holds instance of a StaffSportDao. */
    private StaffSportDao staffSportDao;
    
    /** Holds instance of a StaffExternalActivityDao. */
    private StaffExternalActivityDao staffExternalActivityDao;
    
    /** Holds instance of a StaffSeminarDao. */
    private StaffSeminarDao staffSeminarDao;
    
    /** Holds instance of a StaffProfileReportDao. */
    private StaffProfileReportDao staffProfileReportDao;
    
    /** Holds instance of a UserLoginDao. */
    private UserLoginDao userLoginDao;
    
    /** Holds instance of a StaffLeaveStatusDao. */
    private StaffLeaveStatusDao staffLeaveStatusDao;
    
    /** Holds instance of a HolidayDao. */
    private HolidayDao holidayDao;
    
    /** Holds instance of a StaffLeaveTypeDao. */
    private StaffLeaveTypeDao staffLeaveTypeDao;
    
    /** Holds the error message key. */
    private static final String ERROR_MESSAGE_STAFF_REJOIN = "STAFF.REJOIN.JOINDATE.ERROR";
    
    /** Holds the AkuraErrorMsg property file name. */
    private static final String AKURA_ERROR_MSG_PROPERTY = "AkuraErrorMsg";
    
    /** Holds instance of a StaffPastServiceDao. */
    private StaffPastServiceDao staffPastServiceDao;
    
    /** Holds the error message key. */
    private static final String ERROR_WHILE_SETTING_THE_PHOTO = "Error while setting the photo";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_LEAVE_JOIN_DATE = "STAFF.LEAVE.LEAVEDAY.JOINDATE";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_LEAVE_EXIST = "STAFF.LEAVE.LEAVEDAY.EXIST";
    
    /** key to hold string of year end date. */
    private static final String END_DATE = "-12-31";
    
    /** key to hold string of year start date. */
    private static final String START_DATE = "-01-01";
    
    /** float attribute for holding HALF_DAY_VALUE. */
    private static final float HALF_DAY_VALUE = 0.5f;
    
    /** String attribute for holding HALF_DAY. */
    private static final String HALF_DAY = "Half Day";
    
    /** String attribute for holding ERROR_MESSAGE_LEAVEDAY_DAYTYPE. */
    private static final String ERROR_MESSAGE_LEAVEDAY_DAYTYPE = "STAFF.LEAVE.LEAVEDAY.DAYTYPE";
    
    /** String attribute for holding ERROR_MESSAGE_LEAVEDAY_HOLIDAY. */
    private static final String ERROR_MESSAGE_LEAVEDAY_HOLIDAY = "STAFF.LEAVE.LEAVEDAY.HOLIDAY";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_STAFF_LEAVE_TYPE_NOT_AVAILABLE = "STAFF.LEAVE.TYPE.NOT.AVAILABLE";
    
    /** String attribute for holding STAFF_LEAVE_STATUS_INPROGRESS_ID. */
    private static final int STAFF_LEAVE_STATUS_INPROGRESS_ID = 3;
    
    /** request value for Year. */
    private static final String REQ_SELECTED_YEAR = "SelectedYear";
    
    /** Represent error message when parsing the date. */
    private static final String DATE_CONVERSION_ERROR = "Date Conversion Error";
    
    /**
     * Set Holiday Dao.
     * 
     * @param holidayDaoRef - holidayDao
     */
    public void setHolidayDao(HolidayDao holidayDaoRef) {
        
        this.holidayDao = holidayDaoRef;
    }

    /**
     * Set Staff Leave Type.
     * 
     * @param staffLeaveTypeDaoRef - staffLeaveTypeDao
     */
    public void setStaffLeaveTypeDao(StaffLeaveTypeDao staffLeaveTypeDaoRef) {
    
        this.staffLeaveTypeDao = staffLeaveTypeDaoRef;
    }
    /**
     * Set StaffProfileReportDao object.
     * 
     * @param staffProfileReportDaoRef staffProfileReportDao
     */
    public void setStaffProfileReportDao(StaffProfileReportDao staffProfileReportDaoRef) {
    
        this.staffProfileReportDao = staffProfileReportDaoRef;
    }
    
    /**
     * Set StaffLeaveDao object.
     * 
     * @param staffLeaveDaoRef staffLeaveDao to be set.
     */
    public void setStaffLeaveDao(StaffLeaveDao staffLeaveDaoRef) {
    
        this.staffLeaveDao = staffLeaveDaoRef;
    }
    
    /**
     * Set StaffEducationDao object.
     * 
     * @param objStaffEducationDao staffEducationDao to be set.
     */
    public void setStaffEducationDao(StaffEducationDao objStaffEducationDao) {
    
        this.staffEducationDao = objStaffEducationDao;
    }
    
    /**
     * Set StaffProfessionalDoa object.
     * 
     * @param objStaffProfessionalDoa staffProfessionalDoa to be set.
     */
    public void setStaffProfessionalDoa(StaffProfessionalDoa objStaffProfessionalDoa) {
    
        this.staffProfessionalDoa = objStaffProfessionalDoa;
    }
    
    /**
     * Set staff Dao object.
     * 
     * @param daoStaffDao StaffDao to be set.
     */
    public void setStaffDao(StaffDao daoStaffDao) {
    
        this.staffDao = daoStaffDao;
    }
    
    /**
     * Set SectionHead Dao object.
     * 
     * @param daoSectionHeadDao SectionHeadDao to be set.
     */
    public void setSectionHeadDao(SectionHeadDao daoSectionHeadDao) {
    
        this.sectionHeadDao = daoSectionHeadDao;
    }
    
    /**
     * Set staff Dao object.
     * 
     * @param daoClassTeacherDao ClassTeacherDao to be set.
     */
    public void setClassTeacherDao(ClassTeacherDao daoClassTeacherDao) {
    
        this.classTeacherDao = daoClassTeacherDao;
    }
    
    /**
     * Set staff Dao object.
     * 
     * @param daoSubjectTeacherDao SubjectTeacherDao to be set.
     */
    public void setSubjectTeacherDao(SubjectTeacherDao daoSubjectTeacherDao) {
    
        this.subjectTeacherDao = daoSubjectTeacherDao;
    }
    
    /**
     * Registers the staffClubSocietyDao to inject with staff service.
     * 
     * @param daoStaffClubSocietyDao the staffClubSocietyDao to set
     */
    public void setStaffClubSocietyDao(StaffClubSocietyDao daoStaffClubSocietyDao) {
    
        this.staffClubSocietyDao = daoStaffClubSocietyDao;
    }
    
    /**
     * Registers the staffSportDao to inject with staff service.
     * 
     * @param daoStaffSportDao the staffSportDao to set
     */
    public void setStaffSportDao(StaffSportDao daoStaffSportDao) {
    
        this.staffSportDao = daoStaffSportDao;
    }
    
    /**
     * Registers the staffExternalActivityDao to inject with staff service.
     * 
     * @param daoStaffExternalActivityDao the staffExternalActivityDao to set
     */
    public void setStaffExternalActivityDao(StaffExternalActivityDao daoStaffExternalActivityDao) {
    
        this.staffExternalActivityDao = daoStaffExternalActivityDao;
    }
    
    /**
     * Registers the staffSeminarDao to inject with staff service.
     * 
     * @param daoStaffSeminarDao the staffSeminarDao to set
     */
    public void setStaffSeminarDao(StaffSeminarDao daoStaffSeminarDao) {
    
        this.staffSeminarDao = daoStaffSeminarDao;
    }
    
    /**
     * Registers the userLoginDao to inject with staff service.
     * 
     * @param userLoginDaoObj the UserLoginDao to set
     */
    public void setUserLoginDao(UserLoginDao userLoginDaoObj) {
    
        this.userLoginDao = userLoginDaoObj;
    }
    
    /**
     * Registers the staffPastServiceDao to inject with staff service.
     * 
     * @param daoStaffPastServiceDao the staffPastServiceDao to set
     */
    public void setStaffPastServiceDao(StaffPastServiceDao daoStaffPastServiceDao) {
    
        this.staffPastServiceDao = daoStaffPastServiceDao;
    }
    
    /**
     * Registers the staffLeaveStatusDao to inject with staff service.
     * 
     * @param staffLeaveStatusDaoVal the staffLeaveStatusDao to set
     */
    public void setStaffLeaveStatusDao(StaffLeaveStatusDao staffLeaveStatusDaoVal) {
    
        this.staffLeaveStatusDao = staffLeaveStatusDaoVal;
    }
    
    /**
     * Service to add a staff object.
     * 
     * @param staff Staff object to set
     * @return check add staff is successful.
     * @throws AkuraAppException throw SMS exception.
     */
    
    public Staff addStaff(Staff staff) throws AkuraAppException {
    
        return this.staffDao.save(staff);
    }
    
    /**
     * Service to delete a staff object.
     * 
     * @param objSraff Staff object to be deleted.
     * @throws AkuraAppException throw SMS exception.
     */
    public void deleteStaff(Staff objSraff) throws AkuraAppException {
    
        this.staffDao.delete(objSraff);
    }
    
    /**
     * Service to view a staff object.
     * 
     * @param staffID id of the Staff
     * @return Staff object.
     * @throws AkuraAppException throw SMS exception.
     */
    public Object viewStaff(int staffID) throws AkuraAppException {
    
        return this.staffDao.findById(Staff.class, staffID);
    }
    
    /**
     * Service to get a SectionHead object.
     * 
     * @param sectionHeadID of the SectionHead.
     * @return SectionHead object.
     * @throws AkuraAppException throw SMS exception.
     */
    public SectionHead getSectionHead(int sectionHeadID) throws AkuraAppException {
    
        return (SectionHead) this.sectionHeadDao.findById(SectionHead.class, sectionHeadID);
    }
    
    /**
     * Service to modify a staff object.
     * 
     * @param staff Staff object to modify.
     * @throws AkuraAppException throw SMS exception.
     * @throws AkuraAppException
     */
    public void modifyStaff(Staff staff) throws AkuraAppException {
    
        this.staffDao.update(staff);
    }
    
    /**
     * Service to view a Collection of staff objects.
     * 
     * @return Collection of staff objects.
     * @throws AkuraAppException throw SMS exception.
     */
    public Collection<Staff> viewAllStaff() throws AkuraAppException {
    
        return this.staffDao.findAll(Staff.class);
    }
    
    /**
     * Service to assign a SectionHead object.
     * 
     * @param head SectionHead object to add.
     * @return check assign SectionHead is successful.
     * @throws AkuraAppException throw SMS exception.
     */
    public SectionHead assignSectionHead(SectionHead head) throws AkuraAppException {
    
        return this.sectionHeadDao.save(head);
    }
    
    /**
     * Service to update a SectionHead object.
     * 
     * @param head SectionHead object to update.
     * @throws AkuraAppException throw SMS exception.
     */
    public void updateSectionHead(SectionHead head) throws AkuraAppException {
    
        this.sectionHeadDao.update(head);
    }
    
    /**
     * Service to delete a SectionHead object.
     * 
     * @param head SectionHead object to be deleted.
     * @throws AkuraAppException throw SMS exception.
     */
    public void deleteSectionHead(SectionHead head) throws AkuraAppException {
    
        this.sectionHeadDao.delete(head);
    }
    
    /**
     * Service to assign a ClassTeacher object.
     * 
     * @param teacher ClassTeacher object to add.
     * @return check assign ClassTeacher is successful.
     * @throws AkuraAppException throw SMS exception.
     */
    public ClassTeacher assignClassTeacher(ClassTeacher teacher) throws AkuraAppException {
    
        return this.classTeacherDao.save(teacher);
    }
    
    /**
     * Service to update a ClassTeacher object.
     * 
     * @param teacher ClassTeacher object to update.
     * @throws AkuraAppException throw SMS exception.
     */
    public void modifyClassTeacher(ClassTeacher teacher) throws AkuraAppException {
    
        this.classTeacherDao.update(teacher);
    }
    
    /**
     * Service to delete a ClassTeacher object.
     * 
     * @param teacher ClassTeacher object to be deleted.
     * @throws AkuraAppException throw SMS exception.
     */
    public void deleteClassTeacher(ClassTeacher teacher) throws AkuraAppException {
    
        this.classTeacherDao.delete(teacher);
    }
    
    /**
     * Service to assign a SubjectTeacher object.
     * 
     * @param teacher SubjectTeacher object to add.
     * @return check assign SubjectTeacher is successful.
     * @throws AkuraAppException throw SMS exception.
     */
    public SubjectTeacher assignSubjectTeacher(SubjectTeacher teacher) throws AkuraAppException {
    
        return this.subjectTeacherDao.save(teacher);
    }
    
    /**
     * Service to update a SubjectTeacher object.
     * 
     * @param teacher SubjectTeacher object to update.
     * @throws AkuraAppException throw SMS exception.
     */
    public void modifySubjectTeacher(SubjectTeacher teacher) throws AkuraAppException {
    
        this.subjectTeacherDao.update(teacher);
    }
    
    /**
     * Service to delete a SubjectTeacher object.
     * 
     * @param staffId staff id.
     * @param gradeSubjectId gradesubject Id
     * @param year check the year.
     * @throws AkuraAppException throw SMS exception.
     */
    public void deleteSubjectTeacher(int staffId, int gradeSubjectId, String year) throws AkuraAppException {
    
        this.subjectTeacherDao.deleteSubTeacher(staffId, gradeSubjectId, year);
    }
    
    /**
     * Service method is to search staff.
     * 
     * @param staff - Staff object to be search.
     * @return List(Staff).
     * @throws AkuraAppException SMS Exceptions
     */
    public List<Staff> searchStaff(Staff staff) throws AkuraAppException {
    
        return staffDao.staffSearch(staff);
    }
    
    /**
     * Service method is to delete student.
     * 
     * @param staffId - id of deleting staff member.
     * @throws AkuraAppException AkuraAppException
     */
    public void deleteStaff(int staffId) throws AkuraAppException {
    
        Staff staff = (Staff) staffDao.findById(Staff.class, staffId);
        staffDao.delete(staff);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<ClassTeacher> searchClassTeacher(String description, String lName, String searchYear)
            throws AkuraAppException {
    
        return classTeacherDao.searchClassTeacher(description, lName, searchYear);
    }
    
    /**
     * {@inheritDoc}
     */
    public Staff findStaff(int staffId) throws AkuraAppException {
    
        return (Staff) staffDao.findById(Staff.class, staffId);
    }
    
    /**
     * {@inheritDoc}
     */
    public ClassTeacher addClassTeacher(ClassTeacher classTeacher) throws AkuraAppException {
    
        return classTeacherDao.save(classTeacher);
    }
    
    /**
     * {@inheritDoc}
     */
    public void editClassTeacher(ClassTeacher classTeacher) throws AkuraAppException {
    
        classTeacherDao.update(classTeacher);
    }
    
    /**
     * {@inheritDoc}
     */
    public ClassTeacher findClassTeacher(int id) throws AkuraAppException {
    
        return (ClassTeacher) classTeacherDao.findById(ClassTeacher.class, id);
    }
    
    /**
     * Returns a list of ClassTeachers by year.
     * 
     * @param year - the year.
     * @return - a list of ClassTeachers
     * @throws AkuraAppException - The exception details that occurred when retrieving all ClassTeachers
     */
    public List<ClassTeacher> getClassTeacherListByYear(Date year) throws AkuraAppException {
    
        return classTeacherDao.findClassTeacherListByYear(year);
    }
    
    /**
     * check whether the registration no is a valid one.
     * 
     * @param registrationNo - registration no of the staff member
     * @throws AkuraAppException AkuraAppException
     * @return flag indicates whether admissionNO exist in Student or Staff table.
     */
    public boolean isValidRegistrationNo(String registrationNo) throws AkuraAppException {
    
        int staffId = 0;
        boolean validity = false;
        if (registrationNo != null && !registrationNo.isEmpty()) {
            staffId = staffDao.findStaffIdForRegistrationNo(registrationNo);
        }
        
        if (staffId != 0) {
            validity = true;
        }
        
        return validity;
    }
    
    /**
     * check whether the user has departure date or not.
     * 
     * @param staffId - staffId no of the staff member
     * @throws AkuraAppException AkuraAppException
     * @return flag indicates whether user has a departure date or not.
     */
    public boolean hasDepartureDate(String staffId) throws AkuraAppException {
    
        boolean result = false;
        Staff staff = findStaff(Integer.parseInt(staffId));
        if (staff != null) {
            result = staff.getDateOfDeparture() == null ? false : true;
        }
        
        return result;
    }
    
    /**
     * service method is to get staffId for the registrationNo.
     * 
     * @param registrationNo - registrationNo.
     * @return flag states whther admission no is exist.
     * @throws AkuraAppException AkuraAppException
     */
    public int findStaffIdForRegistrationNo(String registrationNo) throws AkuraAppException {
    
        return staffDao.findStaffIdForRegistrationNo(registrationNo);
    }
    
    /**
     * Service method is to search staff.
     * 
     * @param staff - Staff object to be search.
     * @param year - year
     * @return List(Staff).
     * @throws AkuraAppException SMS Exceptions
     */
    public List<SectionHead> searchSectionHead(Staff staff, String year) throws AkuraAppException {
    
        return sectionHeadDao.sectionHeadSearch(staff, year);
    }
    
    /**
     * Service method is to search staff.
     * 
     * @return List(SectionHead).
     * @throws AkuraAppException SMS Exceptions
     */
    
    public List<SectionHead> getSectionHeadList() throws AkuraAppException {
    
        return sectionHeadDao.findAll(SectionHead.class);
    }
    
    /**
     * Service method is to get list of subject teachers by giving last name and registration number.
     * 
     * @param lname - last name.
     * @param regNo - registrationNo.
     * @return list of subject teachers.
     * @throws AkuraAppException AkuraAppException
     */
    public List<SubjectTeacher> getSubjectTeachers(String lname, String regNo) throws AkuraAppException {
    
        return this.subjectTeacherDao.getSubjectTeacherList(lname, regNo);
        
    }
    
    /**
     * Service method is to get list of academic staff.
     * 
     * @return list of staff.
     * @throws AkuraAppException AkuraAppException
     */
    public List<Staff> getAcedemicStaff() throws AkuraAppException {
    
        return this.staffDao.getacademicStaff();
    }
    
    /**
     * Service method is to get list of staff by type.
     * 
     * @param staffType - boolean
     * @param selectedDate - Date
     * @return list of staff.
     * @throws AkuraAppException AkuraAppException
     */
    public List<Staff> getStaffByType(boolean staffType, Date selectedDate) throws AkuraAppException {
    
        return this.staffDao.getStaffByType(staffType, selectedDate);
    }
    
    /**
     * Service method is to get list of classes for a particular staff for a particular grade subject.
     * 
     * @param staffId - staff id.
     * @param gradeSubjectId - grade subject ID.
     * @param year - assigned year.
     * @return list of classes for a particular staff for a particular grade subject.
     * @throws AkuraAppException AkuraAppException
     */
    public List<Integer> getteacherClassesfofgradesubject(int staffId, int gradeSubjectId, String year)
            throws AkuraAppException {
    
        return this.subjectTeacherDao.getClassList(staffId, gradeSubjectId, year);
    }
    
    /**
     * Service method is to delete Subject Teacher.
     * 
     * @param subTeacher - subject Teacher
     * @throws AkuraAppException AkuraAppException
     */
    public void deleteSubjectTeacher(SubjectTeacher subTeacher) throws AkuraAppException {
    
        this.subjectTeacherDao.deleteSubTeacher(subTeacher);
        
    }
    
    /**
     * Service to add a StaffEducation object.
     * 
     * @param staffEducation StaffEducation object to set
     * @return check add StaffEducation is successful.
     * @throws AkuraAppException throw SMS exception.
     */
    public StaffEducation addStaffEducation(StaffEducation staffEducation) throws AkuraAppException {
    
        return staffEducationDao.save(staffEducation);
    }
    
    /**
     * Service method is to delete StaffEducation.
     * 
     * @param objStaffEducation - StaffEducation
     * @throws AkuraAppException AkuraAppException
     */
    public void deleteStaffEducation(StaffEducation objStaffEducation) throws AkuraAppException {
    
        staffEducationDao.delete(objStaffEducation);
    }
    
    /**
     * Service to view a StaffEducation object.
     * 
     * @param staffEducationId id of the Staff
     * @return StaffEducation object.
     * @throws AkuraAppException throw SMS exception.
     */
    public Object viewStaffEducation(int staffEducationId) throws AkuraAppException {
    
        return staffEducationDao.findById(StaffEducation.class, staffEducationId);
    }
    
    /**
     * Service to update a StaffEducation object.
     * 
     * @param objStaffEducation StaffEducation object to update.
     * @throws AkuraAppException throw SMS exception.
     */
    public void updateStaffEducation(StaffEducation objStaffEducation) throws AkuraAppException {
    
        staffEducationDao.update(objStaffEducation);
        
    }
    
    /**
     * service to get the list of StaffEducation of a given staff.
     * 
     * @param staffId the id of the Staff.
     * @return List of StaffEducation
     * @throws AkuraAppException - The exception details that occurred when retrieve a list of StaffEducation
     *         instances.
     */
    public List<StaffEducation> getStaffEducationListForStaff(int staffId) throws AkuraAppException {
    
        return staffEducationDao.getStaffEducationListForStaff(staffId);
    }
    
    /**
     * Service to add a StaffProfessional object.
     * 
     * @param staffProfessional StaffProfessional object to set
     * @return check add StaffProfessional is successful.
     * @throws AkuraAppException throw SMS exception.
     */
    public StaffProfessional addStaffProfessional(StaffProfessional staffProfessional) throws AkuraAppException {
    
        return staffProfessionalDoa.save(staffProfessional);
    }
    
    /**
     * Service method is to delete StaffProfessional.
     * 
     * @param objStaffProfessional - StaffProfessional
     * @throws AkuraAppException AkuraAppException
     */
    public void deleteStaffProfessional(StaffProfessional objStaffProfessional) throws AkuraAppException {
    
        staffProfessionalDoa.delete(objStaffProfessional);
    }
    
    /**
     * Service to view a StaffProfessional object.
     * 
     * @param staffProfessionalId id of the StaffProfessional
     * @return StaffProfessional object.
     * @throws AkuraAppException throw SMS exception.
     */
    public Object viewStaffProfessional(int staffProfessionalId) throws AkuraAppException {
    
        return staffProfessionalDoa.findById(StaffProfessional.class, staffProfessionalId);
    }
    
    /**
     * Service to update a StaffProfessional object.
     * 
     * @param objStaffProfessional StaffProfessional object to update.
     * @throws AkuraAppException throw SMS exception.
     */
    public void updateStaffProfessional(StaffProfessional objStaffProfessional) throws AkuraAppException {
    
        staffProfessionalDoa.update(objStaffProfessional);
        
    }
    
    /**
     * service to get the list of StaffProfessional of a given staff.
     * 
     * @param staffId the id of the Staff.
     * @return List of StaffProfessional
     * @throws AkuraAppException - The exception details that occurred when retrieve a list of
     *         StaffProfessional instances.
     */
    public List<StaffProfessional> getStaffProfessionalListForStaff(int staffId) throws AkuraAppException {
    
        return staffProfessionalDoa.getStaffProfessionalListForStaff(staffId);
    }
    
    /**
     * {@inheritDoc}
     */
    public int getMaxStaffId() throws AkuraAppException {
    
        List<Integer> staffId = staffDao.getMaxStaffId();
        return staffId.get(0) != null ? staffId.get(0) : 0;
    }
    
    /**
     * Creates a cub&society for a staff member.
     * 
     * @see StaffService#addStaffClubSociety(StaffClubSociety)
     * @param staffClubSociety a DTO holds all the properties to add a club&society for a staff
     * @return a staff club society object.
     * @throws AkuraAppException throws a detailed exception when add a club&society for a staff member
     */
    public StaffClubSociety addStaffClubSociety(StaffClubSociety staffClubSociety) throws AkuraAppException {
    
        return staffClubSocietyDao.save(staffClubSociety);
    }
    
    /**
     * Service to add a StaffLeave object.
     * 
     * @param staffLeave StaffLeave object to set
     * @return StaffLeave object.
     * @throws AkuraAppException throw SMS exception.
     */
    /*public StaffLeave addStaffLeave(StaffLeave staffLeave) throws AkuraAppException {
    
        return staffLeaveDao.save(staffLeave);
    }*/
    
    /**
     * Service to update a StaffLeave object.
     * 
     * @param staffLeave StaffLeave object to set
     * @throws AkuraAppException throw SMS exception.
     */
    public void updateStaffLeave(StaffLeave staffLeave) throws AkuraAppException {
    
        staffLeaveDao.update(staffLeave);
    }
    
    /**
     * Service to delete a StaffLeave object.
     * 
     * @param staffLeave StaffLeave object to set
     * @throws AkuraAppException throw SMS exception.
     */
    public void deleteStaffLeave(StaffLeave staffLeave) throws AkuraAppException {
    
        staffLeaveDao.delete(staffLeave);
    }
    
    /**
     * Service to find a StaffLeave object.
     * 
     * @return StaffLeave object.
     * @param staffId StaffLeaveId
     * @throws AkuraAppException throw SMS exception.
     */
    public StaffLeave findStaffLeaveById(int staffId) throws AkuraAppException {
    
        return (StaffLeave) staffLeaveDao.findById(StaffLeave.class, staffId);
    }
    
    /**
     * Service to find a StaffLeave objects.
     * 
     * @return StaffLeave List.
     * @param staffId StaffLeaveId
     * @throws AkuraAppException throw SMS exception.
     */
    public List<StaffLeave> getStaffLeaveListByStaffId(int staffId) throws AkuraAppException {
    
        return staffLeaveDao.findStaffLeaveByStaffId(staffId);
    }
    
    /**
     * Service to find a StaffLeave objects.
     * 
     * @return StaffLeave List.
     * @param fromDate Date from
     * @param toDate Date to
     * @param staffId StaffLeaveId
     * @throws AkuraAppException throw SMS exception.
     */
    public List<StaffLeave> getStaffLeaveListByDatePeriodAndStaffId(Date fromDate, Date toDate, int staffId)
            throws AkuraAppException {
    
        return staffLeaveDao.findStaffLeaveByDatePeriodAndStaffId(fromDate, toDate, staffId);
    }
    
    /**
     * Updates a cub&society of staff member.
     * 
     * @see StaffService#modifyStaffClubSociety(StaffClubSociety)
     * @param staffClubSociety a DTO holds all the properties to add a club&society for a staff
     * @throws AkuraAppException throws a detailed exception when modify a club&society for a staff member
     */
    public void modifyStaffClubSociety(StaffClubSociety staffClubSociety) throws AkuraAppException {
    
        staffClubSocietyDao.update(staffClubSociety);
    }
    
    /**
     * Deletes a cub&society for a staff member.
     * 
     * @see StaffService#deleteStaffClubSociety(StaffClubSociety)
     * @param staffClubSociety a DTO holds all the properties to delete a club&society for a staff
     * @throws AkuraAppException throws a detailed exception when add a club&society for a staff member
     */
    public void deleteStaffClubSociety(StaffClubSociety staffClubSociety) throws AkuraAppException {
    
        staffClubSocietyDao.delete(staffClubSociety);
        
    }
    
    /**
     * Retrieves a cub&society for a staff member.
     * 
     * @see StaffService#getStaffClubSociety(int)
     * @param staffClubSocietyId is a unique identification key for a StaffClubSociety object.
     * @return a staff club society object.
     * @throws AkuraAppException throws a detailed exception when retrieve a club&society for a staff member
     */
    public StaffClubSociety getStaffClubSociety(int staffClubSocietyId) throws AkuraAppException {
    
        return (StaffClubSociety) staffClubSocietyDao.findById(StaffClubSociety.class, staffClubSocietyId);
    }
    
    /**
     * Retrieves list of cub&society for a staff member.
     * 
     * @see StaffService#getStaffClubSocietyList()
     * @param staffId - holds integer type
     * @param dateSelectedYear - holds date type
     * @return list of cub&society object.
     * @throws AkuraAppException throws a detailed exception when retrieve a club&society for a staff member
     */
    public List<?> getStaffClubSocietyList(int staffId, Date dateSelectedYear) throws AkuraAppException {
    
        return staffClubSocietyDao.getStaffClubSocietyByYearList(staffId, dateSelectedYear);
    }
    
    /**
     * Retrieves list of cub&society for a staff member.
     * 
     * @see StaffService#getStaffClubSociety()
     * @param staffId - holds integer type
     * @param dateSelectedYear - holds date type
     * @param clubSocietyId - holds integer type
     * @return list of cub&society object.
     * @throws AkuraAppException throws a detailed exception when retrieve a club&society for a staff member
     */
    public List<StaffClubSociety> getStaffClubSociety(int staffId, Date dateSelectedYear, int clubSocietyId)
            throws AkuraAppException {
    
        return staffClubSocietyDao.getStaffClubSociety(staffId, dateSelectedYear, clubSocietyId);
    }
    
    /**
     * Creates a StaffExternalActivity for a staff member.
     * 
     * @see StaffService#addStaffExternalActivity(StaffExternalActivity)
     * @param staffExternalActivity - holds object
     * @return list of StaffExternalActivity object.
     * @throws AkuraAppException throws a detailed exception when add a StaffExternalActivity for a staff
     *         member
     */
    public StaffExternalActivity addStaffExternalActivity(StaffExternalActivity staffExternalActivity)
            throws AkuraAppException {
    
        return staffExternalActivityDao.save(staffExternalActivity);
    }
    
    /**
     * Modifies StaffExternalActivity for a staff member.
     * 
     * @param staffExternalActivity - holds object
     * @throws AkuraAppException throws a detailed exception when modify a StaffExternalActivity for a staff
     *         member
     */
    public void modifyStaffExternalActivity(StaffExternalActivity staffExternalActivity) throws AkuraAppException {
    
        staffExternalActivityDao.update(staffExternalActivity);
        
    }
    
    /**
     * Retrieves a StaffExternalActivity for a staff member.
     * 
     * @param staffExternalActivityId is a unique identification key for a StaffExternalActivity object.
     * @return a StaffExternalActivity object.
     * @throws AkuraAppException throws a detailed exception when retrieve a StaffExternalActivity for a staff
     *         member
     */
    public StaffExternalActivity getStaffExternalActivity(int staffExternalActivityId) throws AkuraAppException {
    
        return (StaffExternalActivity) staffExternalActivityDao.findById(StaffExternalActivity.class,
                staffExternalActivityId);
    }
    
    /**
     * Retrieves list of StaffExternalActivity for a staff member.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - hold date type
     * @return list of StaffExternalActivity object.
     * @throws AkuraAppException throws a detailed exception when retrieve list of StaffExternalActivity for a
     *         staff member
     */
    @SuppressWarnings("unchecked")
    public List<StaffExternalActivity> getStaffExternalActivityList(int staffId, Date dateSelectedYear)
            throws AkuraAppException {
    
        return (List<StaffExternalActivity>) staffExternalActivityDao.getStaffExternalActivityByYearList(staffId,
                dateSelectedYear);
    }
    
    /**
     * Deletes a StaffExternalActivity for a staff member.
     * 
     * @param staffExternalActivity a DTO holds all the properties to add a external activity for a staff
     * @throws AkuraAppException throws a detailed exception when delete a StaffExternalActivity for a staff
     *         member
     */
    public void deleteStaffExternalActivity(StaffExternalActivity staffExternalActivity) throws AkuraAppException {
    
        staffExternalActivityDao.delete(staffExternalActivity);
        
    }
    
    /**
     * Creates a seminar for a staff member.
     * 
     * @param staffSeminar a DTO holds all the properties to add a StaffSeminar for a staff
     * @return a StaffSeminar object.
     * @throws AkuraAppException throws a detailed exception when add a StaffSeminar for a staff member
     */
    public StaffSeminar addStaffSeminar(StaffSeminar staffSeminar) throws AkuraAppException {
    
        return staffSeminarDao.save(staffSeminar);
    }
    
    /**
     * Updates a StaffSeminar of staff member.
     * 
     * @param staffSeminar a DTO holds all the properties to modify a StaffSeminar for a staff
     * @throws AkuraAppException throws a detailed exception when modify a StaffSeminar for a staff member
     */
    public void modifyStaffSeminar(StaffSeminar staffSeminar) throws AkuraAppException {
    
        staffSeminarDao.update(staffSeminar);
        
    }
    
    /**
     * Deletes a StaffSeminar of a staff member.
     * 
     * @param staffSeminar a DTO holds all the properties to delete a StaffSeminar of a staff
     * @throws AkuraAppException throws a detailed exception when delete a StaffSeminar for a staff member
     */
    public void deleteStaffSeminar(StaffSeminar staffSeminar) throws AkuraAppException {
    
        staffSeminarDao.delete(staffSeminar);
        
    }
    
    /**
     * Retrieves a seminar for a staff member.
     * 
     * @param staffSeminarId is a unique identification key for a StaffSeminar object.
     * @return a staff seminar object.
     * @throws AkuraAppException throws a detailed exception when retrieve a seminar for a staff member
     */
    public StaffSeminar getStaffSeminar(int staffSeminarId) throws AkuraAppException {
    
        return (StaffSeminar) staffSeminarDao.findById(StaffSeminar.class, staffSeminarId);
    }
    
    /**
     * Retrieves list of seminars for a staff member.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - hold date type
     * @return list of StaffSeminar object.
     * @throws AkuraAppException throws a detailed exception when retrieve a seminar for a staff member
     */
    public List<?> getStaffSeminarList(int staffId, Date dateSelectedYear) throws AkuraAppException {
    
        return staffSeminarDao.getStaffSeminarByYearList(staffId, dateSelectedYear);
    }
    
    /**
     * Creates a Sport for a staff member.
     * 
     * @param staffSport a DTO holds all the properties to add a staffSport for a staff
     * @return a staff club society object.
     * @throws AkuraAppException throws a detailed exception when add a sport for a staff member
     */
    public StaffSport addStaffSport(StaffSport staffSport) throws AkuraAppException {
    
        return staffSportDao.save(staffSport);
    }
    
    /**
     * Updates a sports of a staff member.
     * 
     * @param staffSport a DTO holds all the properties of StaffSport
     * @throws AkuraAppException throws a detailed exception when modify a StaffSport for a staff member
     */
    public void modifyStaffSport(StaffSport staffSport) throws AkuraAppException {
    
        staffSportDao.update(staffSport);
        
    }
    
    /**
     * Deletes a sport of a staff member.
     * 
     * @param staffSport a DTO holds all the properties of StaffSport
     * @throws AkuraAppException throws a detailed exception when delete a sport of a staff member
     */
    public void deleteStaffSport(StaffSport staffSport) throws AkuraAppException {
    
        staffSportDao.delete(staffSport);
        
    }
    
    /**
     * Retrieves a staff sport using a staff member sport id.
     * 
     * @param staffSportId is a unique identification key for a StaffSport object.
     * @return a staff sport object.
     * @throws AkuraAppException throws a detailed exception when add a sport for a staff member
     */
    public StaffSport getStaffSport(int staffSportId) throws AkuraAppException {
    
        return (StaffSport) staffSportDao.findById(StaffSport.class, staffSportId);
    }
    
    /**
     * Retrieves list of StaffExternalActivity for a staff member.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - hold date type
     * @param activity - holds string type
     * @param achievement - holds String type
     * @return list of StaffExternalActivity object.
     * @throws AkuraAppException throws a detailed exception when add a external activity for a staff member
     */
    public List<StaffExternalActivity> getStaffExternalActivity(int staffId, Date dateSelectedYear, String activity,
            String achievement) throws AkuraAppException {
    
        return staffExternalActivityDao.getStaffExternalActivity(staffId, dateSelectedYear, activity, achievement);
    }
    
    /**
     * Retrieve the list of available Staff members.
     * 
     * @return list of Staff members in the category.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    public List<Staff> getAllAvailableStaff() throws AkuraAppException {
    
        return staffDao.findAllAvailableStaff();
    }
    
    /**
     * Retrieve the list of available Staff members.
     * 
     * @return list of Staff members in the category.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    public List<Staff> getAllAvailableStaffWithOutDeparture() throws AkuraAppException {
    
        return staffDao.findAllAvailableStaffWithOutDeparture();
    }
    
    /**
     * Retrieve the list of available Staff members.
     * 
     * @return list of Staff members in the category.
     * @param date - date.
     * @param hDate - hDate.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    public List<Staff> getAllAvailableStaffWithOutDepartureDate(Date date, Date hDate) throws AkuraAppException {
    
        return staffDao.findAllAvailableStaffWithOutDepartureDate(date, hDate);
    }
    
    /**
     * Retrieve the list of Staff members by category.
     * 
     * @param staffCategoryId - Staff type id.
     * @return list of Staff members in the category.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    public List<Staff> getStaffListByCategory(int staffCategoryId) throws AkuraAppException {
    
        return staffDao.getStaffByCategory(staffCategoryId);
    }
    
    /**
     * Retrieve the list of Staff members by category.
     * 
     * @param staffCategoryId - Staff type id.
     * @return list of Staff members in the category.
     * @param date - date.
     * @param hDate - hDate.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    public List<Staff> getStaffListByCategoryByDate(int staffCategoryId, Date date, Date hDate)
            throws AkuraAppException {
    
        return staffDao.getStaffByCategoryByDate(staffCategoryId, date, hDate);
    }
    
    /**
     * Get staff leave list of, staff in mentioned type that, status of the leave for mentioned date either
     * Approved or In progress.
     * 
     * @param isAcademic boolean Considering staff type (Academic / Non Academic)
     * @param date Date Considering date
     * @return staffList List of staff Matching staff list
     * @throws AkuraAppException throws when retrieving the list of staff fails
     */
    public List<StaffLeave> getAllowableStaffLeaveListByStaffTypeAndDate(boolean isAcademic, Date date)
            throws AkuraAppException {
    
        return staffLeaveDao.getAllowableStaffLeaveListByStaffTypeAndDate(isAcademic, date);
    }
    
    /**
     * Retrieve the list of All Staff members by category.
     * 
     * @param staffCategoryId - Staff type id.
     * @return list of All Staff members in the category.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    public List<Staff> getAllStaffListByCategory(int staffCategoryId) throws AkuraAppException {
    
        return staffDao.getAllStaffByCategory(staffCategoryId);
    }
    
    /**
     * Retrieve the list of Departure Staff members by category.
     * 
     * @param staffCategoryId the staff type to retrieve.
     * @return list of Staff members.
     * @throws AkuraAppException throws a detailed exception when retrieving staff members.
     */
    public List<Staff> getDepatureStaffListByCategory(int staffCategoryId) throws AkuraAppException {
    
        return staffDao.getDepatureStaffByCategory(staffCategoryId);
    }
    
    /**
     * Get a list of class teacher by Grade.
     * 
     * @param gradeId - the Grade id.
     * @return a list of class teachers in this grade.
     * @throws AkuraAppException throws when fails to load class teacher list.
     */
    public List<ClassTeacher> getClassTeacherListByGrade(int gradeId) throws AkuraAppException {
    
        return classTeacherDao.getClassTeacherByGrade(gradeId);
    }
    
    /**
     * Retrieves list of sports for a staff member.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - hold date type
     * @return list of StaffSport object.
     * @throws AkuraAppException throws a detailed exception when retrieve a sports for a staff member
     */
    public List<StaffSport> getStaffSportsList(int staffId, Date dateSelectedYear) throws AkuraAppException {
    
        return staffSportDao.getStaffSportListByYear(staffId, dateSelectedYear);
    }
    
    /**
     * Gets the staff sport.
     * 
     * @see com.virtusa.sms.api.service.StaffService#getStaffSports(int, java.util.Date, int, int)
     * @param staffId the staff id
     * @param selectedYear the selected year
     * @param sportCategoryId the sport category id
     * @return the staff sport
     * @throws AkuraAppException the sMS app exception
     */
    public List<StaffSport> getStaffSports(int staffId, Date selectedYear, int sportCategoryId)
            throws AkuraAppException {
    
        return staffSportDao.getStaffSports(staffId, selectedYear, sportCategoryId);
    }
    
    /**
     * Get the current Section Heads list.
     * 
     * @param today The current date.
     * @return List - The list of current sectional heads.
     * @throws AkuraAppException - Throws when fails to get Sectional Heads list.
     */
    public List<SectionHead> getCurrentSectionHeadList(Date today) throws AkuraAppException {
    
        return sectionHeadDao.getCurrentSectionalHeadList(today);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<SubjectTeacher> getSubjectTeacherList(int staffId, int gradeSubjectId, String year)
            throws AkuraAppException {
    
        return subjectTeacherDao.getSubjectTeachersList(staffId, gradeSubjectId, year);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<SubjectTeacher> getSubjectTeachers(ClassGrade classGrade, int year) throws AkuraAppException {
    
        return subjectTeacherDao.getSubjectTeachers(classGrade, year);
    }
    
    /**
     * {@inheritDoc}
     */
    public ClassTeacher getClassTeacher(ClassGrade classGrade, int year) throws AkuraAppException {
    
        return classTeacherDao.getClassTeacher(classGrade, year);
    }
    
    /**
     * {@inheritDoc}
     */
    public SubjectTeacher findsubjectTeacherById(int subjectTeacherId) throws AkuraAppException {
    
        return (SubjectTeacher) subjectTeacherDao.findById(SubjectTeacher.class, subjectTeacherId);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<SchoolClass> getSchoolClassList(int staffId, int subjectGradeId, String year) throws AkuraAppException {
    
        return subjectTeacherDao.getSchooClassObjectList(staffId, subjectGradeId, year);
    }
    
    /**
     * Get Staff Profile Template by staff id.
     * 
     * @param staffId - staffId
     * @return List - StaffProfileReportTemplate
     * @throws AkuraAppException AkuraAppException
     */
    public List<StaffProfileReportTemplate> getStaffProfileTemplateByStaffId(int staffId) throws AkuraAppException {
    
        return staffProfileReportDao.getStaffProfileInfoByStaffId(staffId);
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
    public int getStaffLeaveCountByPreviousYear(Date fromDate, Date toDate, int staffId) throws AkuraAppException {
    
        return staffLeaveDao.findStaffLeaveCountByPreviousYear(fromDate, toDate, staffId);
    }
    
    /**
     * Get Subjects taught at present by staff id and current year.
     * 
     * @param staffId staff id
     * @param year current year
     * @return list of subjects
     * @throws AkuraAppException AkuraAppException
     */
    public List<String> getCurrentSubjectsByStaffId(int staffId, String year) throws AkuraAppException {
    
        return subjectTeacherDao.getCurrentSubjectsByStaffId(staffId, year);
    }
    
    /**
     * Retrieves cub&societies for a staff member.
     * 
     * @param staffId - holds integer type
     * @return list of club societies for a staff.
     * @throws AkuraAppException - throw detailed exception when retrieve list of staff club societies
     */
    public List<Object[]> getStaffClubSocietyByStaffId(int staffId) throws AkuraAppException {
    
        return staffClubSocietyDao.getStaffClubSocietyByStaffId(staffId);
    }
    
    /**
     * Get staff sports by staff id.
     * 
     * @param staffId staffId
     * @return list of staff sports.
     * @throws AkuraAppException AkuraAppException
     */
    public List<Object[]> getStaffSportByStaffId(int staffId) throws AkuraAppException {
    
        return staffSportDao.getStaffSportByStaffId(staffId);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean checkEmailAvailability(String email) throws AkuraAppException {
    
        boolean emailExist = false;
        
        if (!staffDao.checkEmailAvailability(email).isEmpty()) {
            emailExist = true;
        }
        return emailExist;
    }
    
    /** {@inheritDoc} */
    public List<String> getStaffEmails(List<Integer> staffIdList) throws AkuraAppException {
    
        return staffDao.getStaffEmails(staffIdList);
    }
    
    /**
     * Gets the section head list if any, for given staff id and departure date.
     * 
     * @param staffId the id of the staff member
     * @param dateOfDeparture the date
     * @return section head list
     * @throws AkuraAppException when exception occurs.
     */
    public List<SectionHead> getSectionalHeadsById(int staffId, Date dateOfDeparture) throws AkuraAppException {
    
        return sectionHeadDao.getSectionalHeadsById(staffId, dateOfDeparture);
    }
    
    /**
     * Update the section head list with correct end date.
     * 
     * @param updatedSectionHeadList selection head list
     * @throws AkuraAppException when exception occurs.
     */
    public void updateSectionHeadList(List<SectionHead> updatedSectionHeadList) throws AkuraAppException {
    
        sectionHeadDao.updateList(updatedSectionHeadList);
    }
    
    /**
     * Delete the section head lists after the departure date.
     * 
     * @param deletedSectionHeadList selection head list
     * @throws AkuraAppException when exception occurs.
     */
    public void deleteSectionHeadList(List<SectionHead> deletedSectionHeadList) throws AkuraAppException {
    
        sectionHeadDao.deleteList(deletedSectionHeadList);
    }
    
    /** {@inheritDoc} */
    @Transactional
    public boolean rejoinStaffMemberService(int staffId, Date rejoinDate) throws InvalidRejoinDateException,
            AkuraAppException {
    
        // Get the staff by staffId.
        Staff staff = (Staff) staffDao.findById(Staff.class, staffId);
        
        // A variable to hold the staff rejoin status
        boolean rejoinedStatus = false;
        
        // If depature date is older than rejoin date, rejoin the staff member.Otherwise throw
        // InvalidRejoinDateException.
        if (!staff.getDateOfDeparture().after(rejoinDate)) {
            
            // reset staff join date.
            staff.setDateOfHire(rejoinDate);
            
            // Remove depature date.
            staff.setDateOfDepature(null);
            
            if (staff.getMultipartFile() != null) {
                MultipartFile multipartFile = staff.getMultipartFile();
                if (multipartFile.getSize() > 0) {
                    try {
                        staff.setPhoto(multipartFile.getBytes());
                    } catch (IOException e) {
                        
                        throw new AkuraAppException(ERROR_WHILE_SETTING_THE_PHOTO + e.toString(), e);
                    }
                } else {
                    if (staff.getPhoto() != null && staff.getPhoto().length > 0) {
                        staff.setPhoto(staff.getPhoto());
                    }
                }
                
            }
            
            // Update staff member.
            staffDao.update(staff);
            
            //Update staff member teacher subject allocations.
            subjectTeacherDao.removeAsDeletedAllSubjectTeacherRecords(staffId);
            
          //Update staff member teacher class allocations.
            classTeacherDao.removeAsDeletedAllClassTeacherRecords(staffId);
            
            UserLogin userAccountOfStaffMember = null;
            
            List<UserLogin> userLoginList =
                    userLoginDao.getUserLoginOfAnyStatusByRegistrationNo(staff.getRegistrationNo());
            
            if (!userLoginList.isEmpty()) {
                
                // Get the staff members user account.
                userAccountOfStaffMember = userLoginList.get(0);
            }
            
            if (userAccountOfStaffMember != null) {
                
                // reset depature status of the StaffMember
                userAccountOfStaffMember.setDeleted(false);
                
                // reset user account status of the StaffMember
                userAccountOfStaffMember.setStatus(true);
                
                // update rejoined staff members user account.
                userLoginDao.update(userAccountOfStaffMember);
                
            }
            rejoinedStatus = true;
            
        } else {
            
            throw new InvalidRejoinDateException(PropertyReader.getPropertyValue(AKURA_ERROR_MSG_PROPERTY,
                    ERROR_MESSAGE_STAFF_REJOIN));
        }
        
        return rejoinedStatus;
    }
    
    /**
     * terminates a staff member.
     * 
     * @param staffPastService - StaffPastService object of the particular staff member
     * @throws AkuraAppException when exception occurs.
     */
    @Transactional()
    public void terminateStaff(StaffPastService staffPastService) throws AkuraAppException {
    
        Staff staff = findStaff(staffPastService.getStaff().getStaffId());
        int staffId = staff.getStaffId();
        Date departureDate = staffPastService.getDateOfDepature();
        staffPastService.setStaff(staff);
        staffPastService.setDateOfJoin(staff.getDateOfHire());
        
        /* set the departure date of the staff member. */
        staff.setDateOfDeparture(departureDate);
        staffDao.update(staff);
        
        /* disable and mark as deleted the userLogin belongs to staff member. */
        disableAndDeleteStaffLogin(staff);
        
        /* if there are any attendance data after the departure date, it should be cleared . */
        staffDao.deletedStaffAttendanceRecordsByDepartureDate(staffId, departureDate);
        
        /* If there are any sections head allocations it will be altered to have departure date. */
        updateEndDateForDepartureStaff(staffId, departureDate);
        
        /*
         * Class teacher allocations, subject teacher allocations allocations will be marked as deleted.
         */
        subjectTeacherDao.markAsDeletedAllSubjectTeacherRecords(staffId);
        classTeacherDao.markAsDeletedAllClassTeacherRecords(staffId);
        
        /*
         * Staff Leave data will be deleted from database if there are any record after departure date,
         * duration leave end date should be set to departure date.
         */
        staffLeaveDao.deletedLeaveRecordsByDepartureDate(staffId, departureDate);
        staffLeaveDao.updateToDateWithDepartureDate(staffId, departureDate);
        
        /*
         * Class Teacher further allocations will be deleted from database if there are any record after
         * departure date,
         */
        classTeacherDao.deletedClassTeacherRecordsByDepartureDate(staffId, departureDate);
        /*
         * Subject Teacher future allocations will be deleted from database if there are any record after
         * departure date,
         */
        subjectTeacherDao.deletedSubjectTeacherRecordsByDepartureDate(staffId, departureDate);
        
        /* Old leave data will be marked as deleted. */
        staffLeaveDao.markAsDeletedAllStaffLeaveRecords(staffId);
        
        /* The service details will be moved to Staff_Past_Service. */
        staffPastServiceDao.save(staffPastService);
    }
    
    /**
     * Disable User login details related to staff member.
     * 
     * @param staff - staff object of the particular staff member
     * @throws AkuraAppException AkuraAppException
     */
    private void disableAndDeleteStaffLogin(Staff staff) throws AkuraAppException {
    
        UserLogin userLogin =
                userLoginDao.getUserLoginByUserRoleAndIdetificationNo(UserRole.ROLE_TEACHER.getUserRoleId(),
                        staff.getStaffId() + "");
        
        userLogin =
                (userLogin == null) ? userLoginDao.getUserLoginByUserRoleAndIdetificationNo(
                        UserRole.ROLE_CLERICALSTAFF.getUserRoleId(), staff.getStaffId() + "") : userLogin;
        
        if (userLogin != null) {
            userLogin.setStatus(false);
            userLogin.setDeleted(false);
            userLoginDao.update(userLogin);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StaffPastService> getPastStaffServiceByStaffId(int staffId) throws AkuraAppException {
    
        return staffPastServiceDao.getPastStaffServiceList(staffId);
    }
    
    /**
     * Update the end date in section head.
     * 
     * @param staffId the id of staff
     * @param dateOfDeparture the date
     * @throws AkuraAppException AkuraAppException.
     */
    private void updateEndDateForDepartureStaff(int staffId, Date dateOfDeparture) throws AkuraAppException {
    
        // Getting section head list by the staff id.
        List<SectionHead> sectionHeadList = getSectionalHeadsById(staffId, dateOfDeparture);
        
        if (!sectionHeadList.isEmpty()) {
            List<SectionHead> updatedSectionHeadList = new ArrayList<SectionHead>();
            List<SectionHead> deletedSectionHeadList = new ArrayList<SectionHead>();
            
            // Change departure date to section end date.
            for (SectionHead sectionHead : sectionHeadList) {
                // Check the start date with departure date.
                if (sectionHead.getStartDate().after(dateOfDeparture)) {
                    deletedSectionHeadList.add(sectionHead);
                } else {
                    sectionHead.setEndDate(dateOfDeparture);
                    updatedSectionHeadList.add(sectionHead);
                }
            }
            // Update section head list with departure date.
            if (!updatedSectionHeadList.isEmpty()) {
                updateSectionHeadList(updatedSectionHeadList);
            }
            
            // Delete section heads after the departure date.
            if (!deletedSectionHeadList.isEmpty()) {
                deleteSectionHeadList(deletedSectionHeadList);
            }
            
        }
        
    }
    
    /** {@inheritDoc} */
    public int getKeyByIdentificationNo(String identification) throws AkuraAppException {
    
        List<Integer> staffIdList = staffDao.getStaffId(identification);
        return staffIdList.isEmpty() ? 0 : staffIdList.get(0);
    }
    
    /**
     * Get applied staff leave type list with info.
     * 
     * @param staffId - staffId
     * @param fromDate - selected year
     * @return list of Object array
     * @throws AkuraAppException AkuraAppException
     */
    public List<Object[]> findStaffLeaveTypeInfo(int staffId, Date fromDate) throws AkuraAppException {
    
        List<Object[]> appliedStaffLeaveTypeList = staffLeaveDao.findAppliedStaffLeaveTypes(staffId, fromDate);
        List<Object[]> notAppliedStaffLeaveTypeList = staffLeaveDao.findNotAppliedStaffLeaveTypeIds(staffId, fromDate);
        List<Object[]> staffLeaveTypeInfoList = new ArrayList<Object[]>();
        
        staffLeaveTypeInfoList.addAll(appliedStaffLeaveTypeList);
        staffLeaveTypeInfoList.addAll(notAppliedStaffLeaveTypeList);
        
        return staffLeaveTypeInfoList;
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
    public List<Object[]> findStaffLeaveForStaffLeaveType(int staffId, Date fromDate, int staffLeaveTypeId)
            throws AkuraAppException {
    
        return staffLeaveDao.findStaffLeaveForStaffLeaveType(staffId, fromDate, staffLeaveTypeId);
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
    public List<StaffLeave> findAlreadyExistStaffLeave(int staffId, Date dateFrom, Date dateTo)
            throws AkuraAppException {
    
        return staffLeaveDao.findAlreadyExistStaffLeave(staffId, dateFrom, dateTo);
    }
    
    /**
     * Get applied staff leave type list with info for join year.
     * 
     * @param staffId - staffId
     * @param fromDate - selected year
     * @param toDate - todate
     * @return list of Object array
     * @throws AkuraAppException AkuraAppException
     */
    public List<Object[]> findStaffLeaveTypeInfoForJoinYear(int staffId, Date fromDate, Date toDate)
            throws AkuraAppException {
    
        List<Object[]> appliedStaffLeaveTypeList =
                staffLeaveDao.findAppliedStaffLeaveTypesForJoinYear(staffId, fromDate, toDate);
        List<Object[]> notAppliedStaffLeaveTypeList =
                staffLeaveDao.findNotAppliedStaffLeaveTypeIdsForJoinYear(staffId, fromDate, toDate);
        List<Object[]> staffLeaveTypeInfoList = new ArrayList<Object[]>();
        
        staffLeaveTypeInfoList.addAll(appliedStaffLeaveTypeList);
        staffLeaveTypeInfoList.addAll(notAppliedStaffLeaveTypeList);
        
        return staffLeaveTypeInfoList;
    }
    
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
    public List<Object[]> findStaffLeaveForStaffLeaveTypeForJoinYear(int staffId, Date joinDate, Date toDate,
            int staffLeaveTypeId) throws AkuraAppException {
    
        return staffLeaveDao.findStaffLeaveForStaffLeaveTypeForJoinYear(staffId, joinDate, toDate, staffLeaveTypeId);
    }
    
    /**
     * Returns all the Staff Leave status.
     * 
     * @return - a list of all Leave status.
     * @throws AkuraAppException when exception occurs.
     */
    public List<StaffLeaveStatus> getLeaveStatusList() throws AkuraAppException {
    
        return staffLeaveStatusDao.findAll(StaffLeaveStatus.class);
    }
    
    /**
     * Returns all the Staff Leave list by status id.
     * 
     * @param statusId - statusId
     * @return - a list of staff Leave by status id .
     * @throws AkuraAppException when exception occurs.
     */
    public List<Object[]> getStaffLeaveListByStatusId(int statusId) throws AkuraAppException {
    
        return staffLeaveDao.getStaffLeaveListByStatusId(statusId);
    }
    
    /**
     * Returns Staff Leave Status by status id.
     * 
     * @param statusId - statusId
     * @return - a staff Leave status by status id .
     * @throws AkuraAppException when exception occurs.
     */
    public StaffLeaveStatus findStaffStatusById(int statusId) throws AkuraAppException {
    
        return (StaffLeaveStatus) staffLeaveStatusDao.findById(StaffLeaveStatus.class, statusId);
        
    }
    /**
     * Check whether these is any available leaves for the selected leave type. 
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
    private boolean tempAvilableLeaves(StaffLeave staffLeave, int staffId, HttpServletRequest request)
            throws AkuraAppException, LeaveApplyBeforeJoinDateException, ExistStaffLeaveException,
            DayTypeLeaveDayException, LeaveDayHolidayException, NotAvailableLeavesForLeaveTypeException {
    
        // Get property values from the model.
        Date dateFrom = staffLeave.getFromDate();
        Date dateTo = staffLeave.getToDate();
        String dayType = staffLeave.getDateType();
        //String reason = staffLeave.getReason();
        int staffLeaveTypeId = staffLeave.getStaffLeaveTypeId();
        Date joinDate = ((Staff) staffDao.findById(Staff.class, staffId)).getDateOfHire();
        
        // Check whether the from date of the leave is before the join date.
        if (dateFrom.before(joinDate)) {
            
            throw new LeaveApplyBeforeJoinDateException(PropertyReader.getPropertyValue(AKURA_ERROR_MSG_PROPERTY,
                    ERROR_MSG_LEAVE_JOIN_DATE));
        }
        // Get exist staff leave and validate exist leave.
        StaffLeave existStaffLeave = getExistStaffLeave(dateFrom, dateTo, staffId);
        if ((existStaffLeave != null && staffLeave.getStaffLeaveId() == 0)
                || (existStaffLeave != null && existStaffLeave.getStaffLeaveId() != staffLeave.getStaffLeaveId())) {
            
            throw new ExistStaffLeaveException(PropertyReader.getPropertyValue(AKURA_ERROR_MSG_PROPERTY,
                    ERROR_MSG_LEAVE_EXIST));
            
        }
        // Get day count for the given time period.
        float daysCount = getDaysCount(dateFrom, dateTo, dayType);
        // Check whether the half day or short leave exist on one day.
        if (daysCount > 1 && dayType.equals(HALF_DAY)) {
            
            throw new DayTypeLeaveDayException(PropertyReader.getPropertyValue(AKURA_ERROR_MSG_PROPERTY,
                    ERROR_MESSAGE_LEAVEDAY_DAYTYPE));
        }
        // Check whether the applied days are holidays.
        if (daysCount == 0) {
            
            throw new LeaveDayHolidayException(PropertyReader.getPropertyValue(AKURA_ERROR_MSG_PROPERTY,
                    ERROR_MESSAGE_LEAVEDAY_HOLIDAY));
        }
        // Get available leaves for the staff leave type.
        float availableLeaves =
                findAvailableLevesForStaffLeaveType(request, dateFrom, staffLeaveTypeId, staffId, joinDate);
        
        // Get no of days property value from the model.
        float tempCount = staffLeave.getNoOfDays();
        if (existStaffLeave != null) {
            if (!existStaffLeave.getStaffLeaveTypeId().equals(staffLeave.getStaffLeaveTypeId())) {
                tempCount = 0;
            }
        }
        
        // Check whether are there any available leaves for the  selected staff leave type. 
        if (availableLeaves >= (daysCount - tempCount)) {
            staffLeave.setNoOfDays(daysCount);
            
            return true;
        } else {
            throw new NotAvailableLeavesForLeaveTypeException(PropertyReader.getPropertyValue(AKURA_ERROR_MSG_PROPERTY,
                    ERROR_MSG_STAFF_LEAVE_TYPE_NOT_AVAILABLE));
        }
    }
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
    public StaffLeave addStaffLeave(StaffLeave staffLeave, int staffId, HttpServletRequest request) 
            throws AkuraAppException, LeaveApplyBeforeJoinDateException, ExistStaffLeaveException,
            DayTypeLeaveDayException, LeaveDayHolidayException, NotAvailableLeavesForLeaveTypeException {
        
        if (tempAvilableLeaves(staffLeave, staffId, request)) {
            // Set values for the staff leave object.
            staffLeave.setStaffId(staffId);
            staffLeave.setStaffLeaveStatusId(STAFF_LEAVE_STATUS_INPROGRESS_ID);
            staffLeave.setAppliedDate(new Date());
            staffLeave.setApprovedRejectedDate(null);
            
            return staffLeaveDao.save(staffLeave);
        } else {
            return null;
        }
    }
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
    public void updateStaffLeave (StaffLeave staffLeave, int staffId, HttpServletRequest request) 
            throws AkuraAppException, LeaveApplyBeforeJoinDateException, ExistStaffLeaveException,
            DayTypeLeaveDayException, LeaveDayHolidayException, NotAvailableLeavesForLeaveTypeException {
        
        if (tempAvilableLeaves(staffLeave, staffId, request)) {
            staffLeaveDao.update(staffLeave);
        }
        
    }
    /**
     * Get exist staff leave object.
     * 
     * @param dateFrom - from date
     * @param dateTo - to date
     * @param staffId - staffId
     * @return exist staff leave
     * @throws AkuraAppException - AkuraAppException
     */
    private StaffLeave getExistStaffLeave(Date dateFrom, Date dateTo, int staffId) throws AkuraAppException {
    
        StaffLeave existStaffLeave = null;
        List<StaffLeave> getExistStaffLeaves = this.findAlreadyExistStaffLeave(staffId, dateFrom, dateTo);
        if (!getExistStaffLeaves.isEmpty()) {
            
            existStaffLeave = getExistStaffLeaves.get(0);
        }
        return existStaffLeave;
    }
    
    /**
     * Get days count for given time period.
     * 
     * @param dateFrom - from date
     * @param dateTo - to date
     * @param dayType - day type
     * @return DaysCount
     * @throws AkuraAppException - AkuraAppException
     */
    private float getDaysCount(Date dateFrom, Date dateTo, String dayType) throws AkuraAppException {
    
        Calendar fromDate = Calendar.getInstance();
        fromDate.setTime(dateFrom);
        Calendar toDate = Calendar.getInstance();
        toDate.setTime(dateTo);
        
        List<Holiday> holidayList = getHolidayList(dateFrom, dateTo);
        
        int holidayCount = HolidayUtil.countHolidays(fromDate, toDate, holidayList);
        float daysCount = HolidayUtil.daysBetween(dateFrom, dateTo) - holidayCount;
        if (daysCount == 1 && dayType.equals(HALF_DAY)) {
            daysCount = daysCount - HALF_DAY_VALUE;
        }
        return daysCount;
    }
    
    /**
     * Get the Holiday List for the given time period.
     * 
     * @param startDate - start date of the report.
     * @param endDate - end date of the report.
     * @return ListHoliday - a list containing holiday objects.
     * @throws AkuraAppException - AkuraAppException
     */
    private List<Holiday> getHolidayList(Date startDate, Date endDate) throws AkuraAppException {
    
        String strYr = DateUtil.getStringYear(startDate);
        String strStartDate = strYr + START_DATE;
        String strEndDate = strYr + END_DATE;
        
        Date startDateToSearch = DateUtil.getParseDate(strStartDate);
        Date endDateToSearch = DateUtil.getParseDate(strEndDate);
        
        return holidayDao.getHolidayListByYear(startDateToSearch, endDateToSearch); 
    }
    
    /**
     * Find available leaves for given staff leave type.
     * 
     * @param request - request
     * @param dateFrom - from date
     * @param staffLeaveTypeId - staff leave type id
     * @param staffId - staff id
     * @param joinDate - join date
     * @return available leaves
     * @throws AkuraAppException - AkuraAppException
     */
    private float findAvailableLevesForStaffLeaveType(HttpServletRequest request, Date dateFrom, int staffLeaveTypeId,
            int staffId, Date joinDate) throws AkuraAppException {
    
        String joinYear = DateUtil.getStringYear(joinDate);
        String selectYear = request.getParameter(REQ_SELECTED_YEAR).toString();
        List<Object[]> getSelectedLeaveTypeInfo = null;
        boolean joinYearFlag = false;
        String stringToDate = "";
        
        try {
            if (selectYear.equals(joinYear)) {
                
                stringToDate = joinYear + END_DATE;
                getSelectedLeaveTypeInfo =
                        this.findStaffLeaveForStaffLeaveTypeForJoinYear(staffId, joinDate,
                                DateUtil.convertStringToDate(stringToDate), staffLeaveTypeId);
                joinYearFlag = true;
            } else {
                getSelectedLeaveTypeInfo = this.findStaffLeaveForStaffLeaveType(staffId, dateFrom, staffLeaveTypeId);
            }
            
            float availableLeaves = 0;
            if (getSelectedLeaveTypeInfo.isEmpty()) {
                if (joinYearFlag) {
                    List<Object[]> leaveInfoList =
                            this.findStaffLeaveTypeInfoForJoinYear(staffId, joinDate,
                                    DateUtil.convertStringToDate(stringToDate));
                    Iterator<Object[]> iterator = leaveInfoList.iterator();
                    while (iterator.hasNext()) {
                        int i = 2;
                        int j = 0;
                        Object[] object = iterator.next();
                        if (((Integer) object[j]).intValue() == staffLeaveTypeId) {
                            availableLeaves = ((Integer) object[i]).intValue();
                            break;
                        }
                    }   
                } else {
                    availableLeaves =
                            ((StaffLeaveType) staffLeaveTypeDao.findById(StaffLeaveType.class, staffLeaveTypeId))
                                    .getMaxStaffLeaves();
                }
            } else {
                Iterator<Object[]> iterator = getSelectedLeaveTypeInfo.iterator();
                while (iterator.hasNext()) {
                    int i = 2;
                    Object[] object = iterator.next();
                    availableLeaves = ((Double) object[i]).floatValue();
                }
            }
            return availableLeaves;
            
        } catch (ParseException e) {
            throw new AkuraAppException(DATE_CONVERSION_ERROR, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getNumberOfStaffInSchool() throws AkuraAppException {

        return staffDao.getNumberOfStaffMembersInSchool();
    }
    
    /**
     * Get the ClassGrade List for the given ClassTeacher.
     * @param staffId - staffId of the ClassTeacher.
     * @return ClassGrade - a list containing ClassGrade objects.
     * @throws AkuraAppException - AkuraAppException
     */
	@Override
	public List<ClassGrade> getClassListOfaClassTeacherInCurrentYrear(
			int staffId) throws AkuraAppException {
		
			 List<ClassGrade> list=classTeacherDao.getClassTeacherRecordsByStaffIdAndYear(staffId);
			if (!list.isEmpty()) {
		           
				return classTeacherDao.getClassTeacherRecordsByStaffIdAndYear(staffId);
		        }
			return null;
	}
	
	 /**
     * Check whether the logged use is a classTeacher. 
     * 
     * @param staffId - staff id
     * @return boolean value whether the logged use is a ClassTeacher or not. 
     * @throws AkuraAppException - AkuraAppException
      */
	@Override
	public boolean isClassTeacher(int staffId) throws AkuraAppException {
		 List<ClassGrade> list=classTeacherDao.getClassTeacherRecordsByStaffIdAndYear(staffId);
		 if(!list.isEmpty()){
			 return true;
						 }
					
		return false;
	}
    
    
}
