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

package com.virtusa.akura.common.service;

import java.util.List;

import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.dto.AppointmentNature;
import com.virtusa.akura.api.dto.CivilStatus;
import com.virtusa.akura.api.dto.EducationalQualification;
import com.virtusa.akura.api.dto.ProfessionalQualification;
import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.StaffLeaveType;
import com.virtusa.akura.api.dto.StaffServiceCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.AppointmentClassificationDao;
import com.virtusa.akura.common.dao.AppointmentNatureDao;
import com.virtusa.akura.common.dao.CivilStatusDao;
import com.virtusa.akura.common.dao.EducationalQualificationDao;
import com.virtusa.akura.common.dao.ProfessionalQualificationDao;
import com.virtusa.akura.common.dao.SectionDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;
import com.virtusa.akura.common.dao.StaffLeaveTypeDao;
import com.virtusa.akura.common.dao.StaffServiceCategoryDao;

/**
 * @author Virtusa Corporation
 */

public class StaffCommonServiceImpl implements StaffCommonService {
    
    /** {@link EducationalQualificationDao}. */
    private EducationalQualificationDao educationalQualificationDao;
    
    /**
     * {@link ProfessionalQualificationDao}.
     */
    private ProfessionalQualificationDao professionalQualificationDao;
    
    /** staffCategoryDao object. */
    private StaffCategoryDao staffCategoryDao;
    
    /** Represents an instance of SectionDao. */
    private SectionDao sectionDao;
    
    /** Represents an instance of CivilStatusDao. */
    private CivilStatusDao civilStatusDao;
    
    /** Represents an instance of AppointmentNatureDao. */
    private AppointmentNatureDao appointmentNatureDao;
    
    /** Represents an instance of AppointmentClassificationDao. */
    private AppointmentClassificationDao appointmentClassificationDao;
    
    /** Represents an instance of StaffServiceDao. */
    private StaffServiceCategoryDao staffServiceCategoryDaoTarget;
    
    /** StaffLeaveTypeDao attribute for holding StaffLeaveTypeDao. */
    
    private StaffLeaveTypeDao staffLeaveTypeDao;
    
    /**
     * Default constructor.
     */
    public StaffCommonServiceImpl() {
    
    }
    
    /**
     * @param educationalQualificationDaoValue the educationalQualificationDao to set
     */
    public void setEducationalQualificationDao(EducationalQualificationDao educationalQualificationDaoValue) {
    
        this.educationalQualificationDao = educationalQualificationDaoValue;
    }
    
    /**
     * @param professionalQualificationDaoVal the professionalQualificationDao to set
     */
    public void setProfessionalQualificationDao(ProfessionalQualificationDao professionalQualificationDaoVal) {
    
        this.professionalQualificationDao = professionalQualificationDaoVal;
    }
    
    
    /**
     * Sets the staff category dao.
     * 
     * @param objStaffCategoryDao the staffCategoryDao to set
     */
    public void setStaffCategoryDao(StaffCategoryDao objStaffCategoryDao) {
    
        this.staffCategoryDao = objStaffCategoryDao;
    }
    
    /**
     * Set the staffServiceDao.
     * 
     * @param staffServiceDaoObj the staffServiceDao to set
     */
    public void setStaffServiceCategoryDao(StaffServiceCategoryDao staffServiceDaoObj) {
    
        this.staffServiceCategoryDaoTarget = staffServiceDaoObj;
    }
    
    /**
     * Set the appointmentClassificationDao.
     * 
     * @param appointmentClassificationDaoObj the appointmentClassificationDao to set
     */
    public final void setAppointmentClassificationDao(final AppointmentClassificationDao appointmentClassificationDaoObj) {
    
        this.appointmentClassificationDao = appointmentClassificationDaoObj;
    }
    
    /**
     * Set the appointmentNatureDao.
     * 
     * @param appointmentNatureDaoObj the appointmentNatureDao to set
     */
    
    public final void setAppointmentNatureDao(final AppointmentNatureDao appointmentNatureDaoObj) {
    
        this.appointmentNatureDao = appointmentNatureDaoObj;
    }
    
    /**
     * Set the civilStatusDao.
     * 
     * @param civilStatusDaoObj the civilStatusDao to set
     */
    
    public final void setCivilStatusDao(final CivilStatusDao civilStatusDaoObj) {
    
        this.civilStatusDao = civilStatusDaoObj;
    }
    
    /**
     * Set the sectionDao.
     * 
     * @param sectionDaoObj the sectionDao to set
     */
    public final void setSectionDao(final SectionDao sectionDaoObj) {
    
        this.sectionDao = sectionDaoObj;
    }
    
    /**
     * Set the Staff Leave type dao.
     * 
     * @param staffLeaveTypeDaoValue the staffLeaveTypeDao to set
     */
    public void setStaffLeaveTypeDao(StaffLeaveTypeDao staffLeaveTypeDaoValue) {
    
        this.staffLeaveTypeDao = staffLeaveTypeDaoValue;
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public EducationalQualification createEducationalQualification(EducationalQualification educationalQualification)
            throws AkuraAppException {
    
        return educationalQualificationDao.save(educationalQualification);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateEducationalQualification(EducationalQualification educationalQualification)
            throws AkuraAppException {
    
        educationalQualificationDao.update(educationalQualification);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public EducationalQualification findEducationalQualificationById(int educationalQualificationId)
            throws AkuraAppException {
    
        return (EducationalQualification) educationalQualificationDao.findById(EducationalQualification.class,
                educationalQualificationId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteEducationalQualification(int educationalQualificationId) throws AkuraAppException {
    
        EducationalQualification educationalQualification =
                findEducationalQualificationById(educationalQualificationId);
        educationalQualificationDao.delete(educationalQualification);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<EducationalQualification> getEducationalQualifications() throws AkuraAppException {
    
        return educationalQualificationDao.findAll(EducationalQualification.class);
    }
    
    /**
     * Check whether EducationalQualification is already added.
     * 
     * @param strEducationalQualification - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsEducationalQualification(String strEducationalQualification) throws AkuraAppException {
    
        EducationalQualification educationalQualificationDes =
                educationalQualificationDao.getAnyEducationalQualificationByDes(strEducationalQualification);
        boolean isExists = false;
        
        if (educationalQualificationDes != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * @see com.virtusa.sms.api.service.CommonService#addProfessionalQualification
     *      (com.virtusa.sms.api.dto.ProfessionalQualification)
     * @param professionalQualification {@link ProfessionalQualification}
     * @return {@link ProfessionalQualification}
     * @throws AkuraAppException - Detailed exception.
     */
    public ProfessionalQualification createProfessionalQualification(ProfessionalQualification professionalQualification)
            throws AkuraAppException {
    
        return professionalQualificationDao.save(professionalQualification);
    }
    
    /**
     * @see com.virtusa.sms.api.service.CommonService#modifyProfessionalQualification
     *      (com.virtusa.sms.api.dto.ProfessionalQualification)
     * @param professionalQualification {@link ProfessionalQualification}
     * @throws AkuraAppException - Detailed exception.
     */
    public void updateProfessionalQualification(ProfessionalQualification professionalQualification)
            throws AkuraAppException {
    
        professionalQualificationDao.update(professionalQualification);
    }
    
    /**
     * @see com.virtusa.sms.api.service.CommonService#deleteProfessionalQualification
     *      (com.virtusa.sms.api.dto.ProfessionalQualification)
     * @param professionalQualificationId {@link ProfessionalQualification}
     * @throws AkuraAppException - Detailed exception.
     */
    public void deleteProfessionalQualification(int professionalQualificationId) throws AkuraAppException {
    
        ProfessionalQualification professionalQualification =
                findProfessionalQualificationById(professionalQualificationId);
        professionalQualificationDao.delete(professionalQualification);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public ProfessionalQualification findProfessionalQualificationById(int professionalQualificationId)
            throws AkuraAppException {
    
        return (ProfessionalQualification) professionalQualificationDao.findById(ProfessionalQualification.class,
                professionalQualificationId);
    }
    
    /**
     * @see com.virtusa.sms.api.service.CommonService#getProfessionalQualifications()
     * @return Collection of ProfessionalQualification
     * @throws AkuraAppException - Detailed exception.
     */
    public List<ProfessionalQualification> getProfessionalQualifications() throws AkuraAppException {
    
        return professionalQualificationDao.findAll(ProfessionalQualification.class);
    }
    
    /**
     * Retrieve the available professionalQualificationDao object.
     * 
     * @param description - professionalQualificationDao's description.
     * @return ProfessionalQualificationDao object.
     * @throws AkuraAppException - throw detailed exception.
     */
    public ProfessionalQualification getProfessionalQualificationByName(String description) throws AkuraAppException {
    
        return professionalQualificationDao.getProfessionalQualificationByName(description);
    }
    
    /**
     * Check whether professionalQualification is already added.
     * 
     * @param description - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsProfessionalQualification(String description) throws AkuraAppException {
    
        ProfessionalQualification professionalQualification = getProfessionalQualificationByName(description);
        boolean isExists = false;
        
        if (professionalQualification != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * service to add a StaffLeaveType to student.
     * 
     * @param staffLeaveType the StaffLeaveType object to add.
     * @return a staffLeaveType instance.
     * @throws AkuraAppException - The exception details that occurred when adding a StaffLeaveType instances.
     */
    public StaffLeaveType addStaffLeaveType(StaffLeaveType staffLeaveType) throws AkuraAppException {
    
        return staffLeaveTypeDao.save(staffLeaveType);
    }
    
    /**
     * service to delete a StaffLeaveType of a student.
     * 
     * @param staffLeaveTypeId the id of the StaffLeaveType object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a StaffLeaveType instances.
     */
    public void deleteStaffLeaveType(int staffLeaveTypeId) throws AkuraAppException {
    
        StaffLeaveType staffLeaveType =
                (StaffLeaveType) staffLeaveTypeDao.findById(StaffLeaveType.class, staffLeaveTypeId);
        staffLeaveTypeDao.delete(staffLeaveType);
    }
    
    /**
     * Finds the StaffLeaveType that relevant to the StaffLeaveType id.
     * 
     * @param staffLeaveTypeId - staffLeaveType id.
     * @return a staffLeaveType instance.
     * @throws AkuraAppException throws if exception occurs when finding a StaffLeaveType instance.
     */
    public final StaffLeaveType findStaffLeaveType(final Integer staffLeaveTypeId) throws AkuraAppException {
    
        return (StaffLeaveType) staffLeaveTypeDao.findById(StaffLeaveType.class, staffLeaveTypeId);
    }
    
    /**
     * {@inheritDoc}
     */
    public void updateStaffLeaveType(StaffLeaveType staffLeaveType) throws AkuraAppException {
    
        staffLeaveTypeDao.update(staffLeaveType);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StaffLeaveType> getStaffLeaveTypeList() throws AkuraAppException {
    
        return staffLeaveTypeDao.findAll(StaffLeaveType.class);
    }
    
    /**
     * Get StaffLeaveType object by StaffLeaveTypeId.
     * 
     * @param staffLeaveTypeId staffLeave type id defined by integer type object
     * @return a staffLeaveType object.
     * @throws AkuraAppException throws if exception occurs when
     */
    public StaffLeaveType findStaffLeaveTypeById(int staffLeaveTypeId) throws AkuraAppException {
    
        return (StaffLeaveType) staffLeaveTypeDao.findById(StaffLeaveType.class, staffLeaveTypeId);
    }
    
    /**
     * Check whether StaffLeaveType is already added.
     * 
     * @param staffLeaveTypeName - String
     * @param staffLeaveTypeId - int
     * @param maxstaffLeaves - int
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    
    public boolean isExistsStaffLeaveType(String staffLeaveTypeName, int staffLeaveTypeId, int maxstaffLeaves)
            throws AkuraAppException {
    
        StaffLeaveType staffLeaveType = staffLeaveTypeDao.getStaffLeaveTypeByDes(staffLeaveTypeName);
        
        boolean isExists = false;
        if (staffLeaveType != null) {
            if (staffLeaveTypeId == 0) {
                isExists = true;
            }
            if (staffLeaveTypeId != 0) {
                if (!staffLeaveTypeName.equalsIgnoreCase(findStaffLeaveTypeById(staffLeaveTypeId).getDescription())) {
                    isExists = true;
                } else {
                    isExists = false;
                    
                }
            }
        }
        return isExists;
    }
    
    /**
     * Check whether staff leave type is already allocated.
     * 
     * @param staffLeaveTypeId - int
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    
    public boolean isAlreadyAllocated(int staffLeaveTypeId) throws AkuraAppException {
    
        int sl = staffLeaveTypeDao.findStaffLeaveIdByStaffLeaveTypeId(staffLeaveTypeId);
        
        boolean isExists = false;
        if (sl != 0) {
            
            isExists = true;
        } else {
            isExists = false;
            
        }
        
        return isExists;
        
    }
    
    /**
     * Returns a list of Section instances.
     * 
     * @return a list of Section instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Section instances.
     */
    public List<Section> getSectionsList() throws AkuraAppException {
    
        return sectionDao.findAll(Section.class);
    }
    
    /**
     * Method is to Section reference data.
     * 
     * @return section list.
     * @throws AkuraAppException - Detailed exception.
     */
    @Override
    public List<Section> getSectionList() throws AkuraAppException {
    
        return sectionDao.findAll(Section.class);
    }
    
    /**
     * method to find a Section.
     * 
     * @param sectionId the id of the Section object to find.
     * @return Section object
     * @throws AkuraAppException - The exception details that occurred when find a Section instances.
     */
    @Override
    public Section findSection(int sectionId) throws AkuraAppException {
    
        return (Section) sectionDao.findById(Section.class, sectionId);
    }
    
    /**
     * @see com.virtusa.sms.api.service.SchoolService#createSection(Section)
     * @param section {@link Section}
     * @return {@link Section}.
     * @throws AkuraAppException - throws exception
     */
    @Override
    public Section createSection(Section section) throws AkuraAppException {
    
        return sectionDao.save(section);
    }
    
    /**
     * method to delete a Section.
     * 
     * @param section the id of the Section object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a Section instances.
     */
    @Override
    public void deleteSection(Section section) throws AkuraAppException {
    
        sectionDao.delete(section);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateSection(Section section) throws AkuraAppException {
    
        sectionDao.update(section);
    }
    
    /**
     * Retrieve the available Section object.
     * 
     * @param description - Section's description.
     * @return Section object.
     * @throws AkuraAppException - throw detailed exception.
     */
    public Section getSectionByName(String description) throws AkuraAppException {
    
        return sectionDao.getSectionByName(description);
    }
    
    /**
     * Check whether section is already added.
     * 
     * @param secId - integer
     * @param description - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsSection(String description, int secId) throws AkuraAppException {
    
        Section section = getSectionByName(description);
        
        boolean isExists = false;
        
        if (section != null) {
            
            isExists = true;
            
        }
        
        return isExists;
    }
    
    /**
     * Service to find a CivilStatus object.
     * 
     * @param civilStatusId CivilStatus object to find
     * @return donationType object
     * @throws AkuraAppException - throw this
     */
    public CivilStatus findCivilStatus(final int civilStatusId) throws AkuraAppException {
    
        return (CivilStatus) this.civilStatusDao.findById(CivilStatus.class, civilStatusId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    
    public void updateCivilStatus(CivilStatus civilStatus) throws AkuraAppException {
    
        civilStatusDao.update(civilStatus);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    
    public CivilStatus addCivilStatus(CivilStatus civilStatus) throws AkuraAppException {
    
        return civilStatusDao.save(civilStatus);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteCivilStatus(CivilStatus civilStatus) throws AkuraAppException {
    
        civilStatusDao.delete(civilStatus);
    }
    
    /**
     * Returns a list of CivilStatus instances.
     * 
     * @return a list of CivilStatus instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of CivilStatus instances.
     */
    public List<CivilStatus> getCivilStatusList() throws AkuraAppException {
    
        return civilStatusDao.findAll(CivilStatus.class);
    }
    
    /**
     * Retrieve the available CivilStatus object.
     * 
     * @param civilStatusName - civilStatusName.
     * @return UserLogin object.
     * @throws AkuraAppException - throw detailed exception.
     */
    public CivilStatus getAnyCivilStatus(String civilStatusName) throws AkuraAppException {
    
        return civilStatusDao.getAnyCivilStatusByName(civilStatusName);
    }
    
    /**
     * Check whether civilstaus is already added.
     * 
     * @param civilStatusName - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsCivilStatus(String civilStatusName) throws AkuraAppException {
    
        CivilStatus civilStatus = getAnyCivilStatus(civilStatusName);
        boolean isExists = false;
        
        if (civilStatus != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Service to get a Staff Category object.
     * 
     * @param categoryID StaffCategoryID to be get.
     * @return StaffCategory staff category object.
     * @throws AkuraAppException throw SMS exception.
     */
    public StaffCategory getStaffCategory(int categoryID) throws AkuraAppException {
    
        return (StaffCategory) this.staffCategoryDao.findById(StaffCategory.class, categoryID);
    }
    
    /**
     * Service to add a Staff Category.
     * 
     * @param category StaffCategory object to be saved.
     * @return StaffCategory staff category object.
     * @throws AkuraAppException throw SMS exception.
     */
    public StaffCategory addStaffCategory(StaffCategory category) throws AkuraAppException {
    
        return this.staffCategoryDao.save(category);
    }
    
    /**
     * Service to update a Staff Category.
     * 
     * @param category StaffCategory object to be updated.
     * @throws AkuraAppException throw SMS exception.
     */
    public void updateStaffCategory(StaffCategory category) throws AkuraAppException {
    
        this.staffCategoryDao.update(category);
    }
    
    /**
     * Service to update a Staff Category.
     * 
     * @param category StaffCategory object to be delete.
     * @throws AkuraAppException throw SMS exception.
     */
    public void deleteStaffCategory(StaffCategory category) throws AkuraAppException {
    
        this.staffCategoryDao.delete(category);
        
    }
    
    /**
     * Service to get all Staff Categories.
     * 
     * @return List of Staff Categories.
     * @throws AkuraAppException throw SMS exception.
     */
    public List<StaffCategory> getAllStaffCategories() throws AkuraAppException {
    
        return this.staffCategoryDao.findAll(StaffCategory.class);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAcademicStaffCategory(int categoryId) throws AkuraAppException {
    
        return staffCategoryDao.isAcademicStaffCategory(categoryId);
    }
    
    /**
     * service to add a Appointment Nature to staff.
     * 
     * @param appointmentNature the AppointmentNature object to add.
     * @return a appointmentNature instance.
     * @throws AkuraAppException - The exception details that occurred when adding a AppointmentNature
     *         instances.
     */
    @Override
    public AppointmentNature addAppointmentNature(AppointmentNature appointmentNature) throws AkuraAppException {
    
        return appointmentNatureDao.save(appointmentNature);
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAppointmentNature(AppointmentNature appointmentNature) throws AkuraAppException {
    
        appointmentNatureDao.update(appointmentNature);
        
    }
    
    /**
     * service to delete a AppointmentNature of a staff.
     * 
     * @param appointmentNature the id of the AppointmentNature object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a AppointmentNature
     *         instances.
     */
    @Override
    public void deleteAppointmentNature(AppointmentNature appointmentNature) throws AkuraAppException {
    
        appointmentNatureDao.delete(appointmentNature);
        
    }
    
    /**
     * Returns a list of AppointmentNature instances.
     * 
     * @return a list of AppointmentNature instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of AppointmentNature instances.
     */
    public List<AppointmentNature> getAppointmentNatureList() throws AkuraAppException {
    
        return appointmentNatureDao.findAll(AppointmentNature.class);
    }
    
    /**
     * Get AppointmentNature object by AppointmentNatureId.
     * 
     * @param appointmentNatureId defined by integer type object
     * @return a AppointmentNature object.
     * @throws AkuraAppException throws if exception occurs when
     */
    @Override
    public AppointmentNature getAppointmentNatureById(int appointmentNatureId) throws AkuraAppException {
    
        return (AppointmentNature) appointmentNatureDao.findById(AppointmentNature.class, appointmentNatureId);
    }
    
    /**
     * Retrieve the available appointmentNatureDao object.
     * 
     * @param description - String.
     * @return AppointmentNature object.
     * @throws AkuraAppException - throw detailed exception.
     */
    @Override
    public AppointmentNature getAnyAppointmentNature(String description) throws AkuraAppException {
    
        return appointmentNatureDao.getAppointmentNatureByDescription(description);
    }
    
    /**
     * Check whether appointment nature is already added.
     * 
     * @param description - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsAppointmentNature(String description) throws AkuraAppException {
    
        AppointmentNature appointmentNature = getAnyAppointmentNature(description);
        boolean isExists = false;
        
        if (appointmentNature != null) {
            isExists = true;
        }
        
        return isExists;
        
    }
    
    /**
     * Returns a list of AppointmentClassification instances.
     * 
     * @return a list of AppointmentClassification instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of AppointmentClassification
     *         instances.
     */
    public List<AppointmentClassification> getAppointmentClassificationList() throws AkuraAppException {
    
        return appointmentClassificationDao.findAll(AppointmentClassification.class);
    }
    
    /**
     * service to add a AppointmentClassification to staff.
     * 
     * @param appointmentClassification the AppointmentClassification object to add.
     * @return a AppointmentClassification instance.
     * @throws AkuraAppException - The exception details that occurred when adding a AppointmentClassification
     *         instances.
     */
    @Override
    public AppointmentClassification addAppointmentClassification(AppointmentClassification appointmentClassification)
            throws AkuraAppException {
    
        return appointmentClassificationDao.save(appointmentClassification);
    }
    
    /**
     * Finds the AppointmentClassification that relevant to the AppointmentClassification id.
     * 
     * @param classificationId type Integer
     * @return a appointmentClassification instance.
     * @throws AkuraAppException throws if exception occurs when finding a AppointmentClassification instance.
     */
    @Override
    public AppointmentClassification findAppointmentClassification(final Integer classificationId)
            throws AkuraAppException {
    
        return (AppointmentClassification) appointmentClassificationDao.findById(AppointmentClassification.class,
                classificationId);
    }
    
    /**
     * {@inheritDoc}
     */
    public void updateAppointmentClassification(AppointmentClassification appointmentClassification)
            throws AkuraAppException {
    
        appointmentClassificationDao.update(appointmentClassification);
    }
    
    /**
     * service to delete a AppointmentClassification of a staff.
     * 
     * @param appointmentClassification the id of the AppointmentClassification object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a AppointmentClassification
     *         instances.
     */
    public void deleteAppointmentClassification(AppointmentClassification appointmentClassification)
            throws AkuraAppException {
    
        appointmentClassificationDao.delete(appointmentClassification);
    }
    
    /**
     * Retrieve the available AppointmentClassification object.
     * 
     * @param appointmentClassification - String.
     * @return AppointmentClassification object.
     * @throws AkuraAppException - throw detailed exception.
     */
    public AppointmentClassification getAnyAppointmentClassification(String appointmentClassification)
            throws AkuraAppException {
    
        return appointmentClassificationDao.getAnyAppointmentClassificationByName(appointmentClassification);
    }
    
    /**
     * Check whether user name already exist.
     * 
     * @param name - String
     * @return true if the user name exist else false.
     * @throws AkuraAppException - Throw detailed exception.
     */
    public boolean isExistsAppointmentClassification(String name) throws AkuraAppException {
    
        String classification = name.trim();
        AppointmentClassification appointmentClassification = getAnyAppointmentClassification(classification);
        boolean isExists = false;
        
        if (appointmentClassification != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Method to add staffServiceCategory reference data.
     * 
     * @param staffServiceCategory - StaffServiceCategory type object.
     * @return - the saved StaffServiceCategory object.
     * @throws AkuraAppException - Detailed exception.
     */
    @Override
    public StaffServiceCategory addStaffServiceCategory(StaffServiceCategory staffServiceCategory)
            throws AkuraAppException {
    
        return staffServiceCategoryDaoTarget.save(staffServiceCategory);
    }
    
    /**
     * Method to edit staffServiceCategory reference data.
     * 
     * @param staffServiceCategory - StaffServiceCategory type object.
     * @throws AkuraAppException - Detailed exception.
     */
    @Override
    public void editStaffServiceCategory(StaffServiceCategory staffServiceCategory) throws AkuraAppException {
    
        staffServiceCategoryDaoTarget.update(staffServiceCategory);
        
    }
    
    /**
     * Method to delete staffServiceCategory reference data.
     * 
     * @param staffServiceCategoryId - id of StaffServiceCategory
     * @throws AkuraAppException - Detailed exception
     */
    @Override
    public void deleteStaffServiceCategory(int staffServiceCategoryId) throws AkuraAppException {
    
        staffServiceCategoryDaoTarget.delete(findStaffServiceCategoryById(staffServiceCategoryId));
        
    }
    
    /**
     * Method to finds the StaffServiceCategory which relevant to StaffServiceCategory Id.
     * 
     * @param staffServiceCategoryId -id of Staff Service Category
     * @return StaffServiceCategory type object
     * @throws AkuraAppException - Detailed Exception
     */
    @Override
    public StaffServiceCategory findStaffServiceCategoryById(int staffServiceCategoryId) throws AkuraAppException {
    
        return (StaffServiceCategory) staffServiceCategoryDaoTarget.findById(StaffServiceCategory.class,
                staffServiceCategoryId);
    }
    
    /**
     * Returns a list of Staff Service Category instances.
     * 
     * @return a list of StaffServiceCategory instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of StaffServiceCategory instances.
     */
    public List<StaffServiceCategory> getStaffServiceCategoryList() throws AkuraAppException {
    
        return staffServiceCategoryDaoTarget.findAll(StaffServiceCategory.class);
        
    }
    
    /**
     * Check whether StaffServiceCategory is already exists.
     * 
     * @param staffServiceCategoryName - String.
     * @return true if already exist else false.
     * @throws AkuraAppException - Detailed Exception
     */
    public boolean isExistsStaffServiceCategory(String staffServiceCategoryName) throws AkuraAppException {
    
        StaffServiceCategory staffServiceCategory =
                staffServiceCategoryDaoTarget.getStaffServiceCategoryByName(staffServiceCategoryName);
        boolean isExists = false;
        
        if (staffServiceCategory != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
}
