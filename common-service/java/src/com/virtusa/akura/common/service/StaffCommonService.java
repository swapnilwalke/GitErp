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


/**
 * @author Virtusa Corporation
 *
 */

public interface StaffCommonService {
    
    /**
     * Create a EducationalQualification.
     * 
     * @param educationalQualification {@link EducationalQualification}
     * @return {@link EducationalQualification}
     * @throws AkuraAppException throws if exception occurs when adding a EducationalQualification instance.
     */
    EducationalQualification createEducationalQualification(EducationalQualification educationalQualification)
            throws AkuraAppException;
    
    /**
     * Edit a EducationalQualification.
     * 
     * @param educationalQualification - {@link EducationalQualification} .
     * @throws AkuraAppException throws if exception occurs when update a EducationalQualification instance.
     */
    void updateEducationalQualification(EducationalQualification educationalQualification) throws AkuraAppException;
    
    /**
     * Get the EducationalQualification by id.
     * 
     * @param educationalQualificationId - int .
     * @return {@link EducationalQualification}
     * @throws AkuraAppException throws if exception occurs when get a EducationalQualification instance.
     */
    EducationalQualification findEducationalQualificationById(int educationalQualificationId) throws AkuraAppException;
    
    /**
     * Delete a EducationalQualification.
     * 
     * @param educationalQualificationId - int .
     * @throws AkuraAppException throws if exception occurs when deleting a EducationalQualification instance.
     */
    void deleteEducationalQualification(int educationalQualificationId) throws AkuraAppException;
    
    /**
     * View all the available educational qualifications.
     * 
     * @return collection of educational qualifications {@link EducationalQualification}
     * @throws AkuraAppException - Detailed exception.
     */
    List<EducationalQualification> getEducationalQualifications() throws AkuraAppException;
    
    /**
     * Check whether the EducationalQualification is exists.
     * 
     * @param strEducationalQualification - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the EducationalQualification object.
     */
    boolean isExistsEducationalQualification(String strEducationalQualification) throws AkuraAppException;
    
    /**
     * Add professional qualification to system.
     * 
     * @param professionalQualification {@link ProfessionalQualification}
     * @return Object of {@link ProfessionalQualification}
     * @throws AkuraAppException - Detailed exception.
     */
    ProfessionalQualification createProfessionalQualification(ProfessionalQualification professionalQualification)
            throws AkuraAppException;
    
    /**
     * Modify professional qualification in the system.
     * 
     * @param professionalQualification {@link ProfessionalQualification}
     * @throws AkuraAppException - Detailed exception.
     */
    void updateProfessionalQualification(ProfessionalQualification professionalQualification) throws AkuraAppException;
    
    /**
     * Delete professional qualification in the system.
     * 
     * @param professionalQualificationId type int
     * @throws AkuraAppException - Detailed exception.
     */
    void deleteProfessionalQualification(int professionalQualificationId) throws AkuraAppException;
    
    /**
     * Get the ProfessionalQualification by id.
     * 
     * @param professionalQualificationId - int .
     * @return {@link ProfessionalQualification}
     * @throws AkuraAppException throws if exception occurs when get a ProfessionalQualification instance.
     */
    ProfessionalQualification findProfessionalQualificationById(int professionalQualificationId)
            throws AkuraAppException;
    
    /**
     * View all the available professional qualifications.
     * 
     * @return collection of professional qualifications {@link ProfessionalQualification}
     * @throws AkuraAppException - Detailed exception.
     */
    List<ProfessionalQualification> getProfessionalQualifications() throws AkuraAppException;
    
    /**
     * Retrieve the available ProfessionalQualification object.
     * 
     * @param description - ProfessionalQualification's description.
     * @return ProfessionalQualification object.
     * @throws AkuraAppException - throw detailed exception.
     */
    ProfessionalQualification getProfessionalQualificationByName(String description) throws AkuraAppException;
    
    /**
     * Check whether professionalQualification is already added.
     * 
     * @param description - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isExistsProfessionalQualification(String description) throws AkuraAppException;
    
    /**
     * Service to add StaffLeaveType instance.
     * 
     * @param staffLeaveType instance.
     * @return - the saved StaffLeaveType object.
     * @throws AkuraAppException throws if exception occurs when saving a StaffLeaveType instance.
     */
    StaffLeaveType addStaffLeaveType(StaffLeaveType staffLeaveType) throws AkuraAppException;
    
    /**
     * Finds the StaffLeaveType instance that relates to the StaffLeaveType id.
     * 
     * @param staffLeaveTypeId - the id of the StaffLeaveType.
     * @return - a StaffLeaveType instance.
     * @throws AkuraAppException - The exception details that occurred when finding a StaffLeaveType instance.
     */
    StaffLeaveType findStaffLeaveType(Integer staffLeaveTypeId) throws AkuraAppException;
    
    /**
     * Updates the relevant StaffLeaveType object.
     * 
     * @param staffLeaveType - an instance of StaffLeaveType.
     * @throws AkuraAppException - The exception details that occurred when updating a StaffLeaveType
     *         instance.
     */
    void updateStaffLeaveType(StaffLeaveType staffLeaveType) throws AkuraAppException;
    
    /**
     * Deletes the relevant staffLeaveType object.
     * 
     * @param staffLeaveTypeId - an instance of StaffLeaveType
     * @throws AkuraAppException - The exception details that occurred when deleting a StaffLeaveType
     *         instance.
     */
    void deleteStaffLeaveType(int staffLeaveTypeId) throws AkuraAppException;
    
    /**
     * Get list of StaffLeaveType.
     * 
     * @return a list of StaffLeaveType.
     * @throws AkuraAppException throws if exception occurs when getting a StaffLeaveType List.
     */
    List<StaffLeaveType> getStaffLeaveTypeList() throws AkuraAppException;
    
    /**
     * Get StaffLeaveType object by StaffLeaveTypeId.
     * 
     * @param staffLeaveTypeId integer type object.
     * @return a StaffLeaveType object.
     * @throws AkuraAppException throws if exception occurs when finding StaffLeaveType by StaffLeaveTypeId.
     */
    StaffLeaveType findStaffLeaveTypeById(int staffLeaveTypeId) throws AkuraAppException;
    
    /**
     * Retrieve the available StaffLeaveType object.
     * 
     * @param staffLeaveTypeName - strDescription.
     * @param staffLeaveTypeId - int.
     * @param maxstaffLeaves - int.
     * @return StaffLeaveType object.
     * @throws AkuraAppException - throw detailed exception.
     */
    
    boolean isExistsStaffLeaveType(String staffLeaveTypeName, int staffLeaveTypeId, int maxstaffLeaves)
            throws AkuraAppException;
    
    /**
     * Check whether staff leave type is already allocated.
     * 
     * @param staffLeaveTypeId - int
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isAlreadyAllocated(int staffLeaveTypeId) throws AkuraAppException;
    
    /**
     * Returns a list of Section instances.
     * 
     * @return a list of Section instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Section instances.
     */
    List<Section> getSectionsList() throws AkuraAppException;
    
    /**
     * Method is to get Section reference data.
     * 
     * @return section list.
     * @throws AkuraAppException - Detailed exception.
     */
    List<Section> getSectionList() throws AkuraAppException;
    
    /**
     * Method is to find Section reference data.
     * 
     * @param id of type integer
     * @return section list.
     * @throws AkuraAppException .
     */
    Section findSection(int id) throws AkuraAppException;
    
    /**
     * Method is to create Section reference data.
     * 
     * @param section - of type Section
     * @return section object.
     * @throws AkuraAppException The exception details that occurred when creating a Section instance.
     */
    Section createSection(Section section) throws AkuraAppException;
    
    /**
     * Method is to update Section reference data.
     * 
     * @param section - of type Section
     * @throws AkuraAppException The exception details that occurred when updating a Section instance.
     */
    void updateSection(Section section) throws AkuraAppException;
    
    /**
     * Deletes the relevant Section object.
     * 
     * @param section - an instance of Section.
     * @throws AkuraAppException - The exception details that occurred when deleting a Section instance.
     */
    void deleteSection(Section section) throws AkuraAppException;
    
    /**
     * Get the any Section object by passing the section's description.
     * 
     * @param description - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the Section object.
     */
    Section getSectionByName(String description) throws AkuraAppException;
    
    /**
     * Check whether section is already added.
     * 
     * @param secId - Integer
     * @param description - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isExistsSection(String description, int secId) throws AkuraAppException;
    
    /**
     * Service to find the donationType object.
     * 
     * @param civilStatusId to find CivilStatusId
     * @return donationType object
     * @throws AkuraAppException - throw this
     */
    CivilStatus findCivilStatus(int civilStatusId) throws AkuraAppException;
    
    /**
     * Service to modify CivilStatus object.
     * 
     * @param civilStatus CivilStatus object to modify
     * @throws AkuraAppException - throw this
     */
    void updateCivilStatus(CivilStatus civilStatus) throws AkuraAppException;
    
    /**
     * Service to add CivilStatus object.
     * 
     * @param civilStatus CivilStatus object to add
     * @return civilStatus object
     * @throws AkuraAppException - throw this
     */
    CivilStatus addCivilStatus(CivilStatus civilStatus) throws AkuraAppException;
    
    /**
     * Service to delete CivilStatus object.
     * 
     * @param civilStatus CivilStatus object to delete
     * @throws AkuraAppException - throw this
     */
    void deleteCivilStatus(CivilStatus civilStatus) throws AkuraAppException;
    
    /**
     * Returns a list of CivilStatus instances.
     * 
     * @return a list of CivilStatus instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of CivilStatus instances.
     */
    List<CivilStatus> getCivilStatusList() throws AkuraAppException;
    
    /**
     * Get the any CivilStatus object by passing the civilStusName.
     * 
     * @param civilStusName - String
     * @throws AkuraAppException AkuraAppException.
     * @return returns the CivilStatus object.
     */
    
    CivilStatus getAnyCivilStatus(String civilStusName) throws AkuraAppException;
    
    /**
     * Returns the status of civil status which exists on the table.
     * 
     * @param civilStatusName - the description of the civil status.
     * @return - a list of exist civil status
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    
    boolean isExistsCivilStatus(String civilStatusName) throws AkuraAppException;
    
    /**
     * Service to get a Staff Category object.
     * 
     * @param categoryID StaffCategory ID to be get.
     * @return StaffCategory staff category object.
     * @throws AkuraAppException throw SMS exception.
     */
    StaffCategory getStaffCategory(int categoryID) throws AkuraAppException;
    
    /**
     * Service to add a Staff Category.
     * 
     * @param category StaffCategory object to be saved.
     * @return StaffCategory staff category object.
     * @throws AkuraAppException throw SMS exception.
     */
    StaffCategory addStaffCategory(StaffCategory category) throws AkuraAppException;
    
    /**
     * Service to update a Staff Category.
     * 
     * @param category StaffCategory object to be updated.
     * @throws AkuraAppException throw SMS exception.
     */
    void updateStaffCategory(StaffCategory category) throws AkuraAppException;
    
    /**
     * Service to update a Staff Category.
     * 
     * @param category StaffCategory object to be delete.
     * @throws AkuraAppException throw SMS exception.
     */
    void deleteStaffCategory(StaffCategory category) throws AkuraAppException;
    
    /**
     * Service to get all Staff Categories.
     * 
     * @return List of Staff Categories.
     * @throws AkuraAppException throw SMS exception.
     */
    List<StaffCategory> getAllStaffCategories() throws AkuraAppException;
    
    /**
     * Check category id is belong to the academic.
     * 
     * @param categoryId id of the category.
     * @return boolean
     * @throws AkuraAppException the akura app exception
     */
    boolean isAcademicStaffCategory(int categoryId) throws AkuraAppException;
    
    /**
     * Service to add AppointmentNature instance.
     * 
     * @param appointmentNature AppointmentNature instance.
     * @return - the saved AppointmentNature object.
     * @throws AkuraAppException throws if exception occurs when saving a AppointmentNature instance.
     */
    AppointmentNature addAppointmentNature(AppointmentNature appointmentNature) throws AkuraAppException;
    
    /**
     * Updates the relevant AppointmentNature object.
     * 
     * @param appointmentNature - an instance of AppointmentNature.
     * @throws AkuraAppException - The exception details that occurred when updating a AppointmentNature
     *         instance.
     */
    void updateAppointmentNature(AppointmentNature appointmentNature) throws AkuraAppException;
    
    /**
     * Deletes the relevant AppointmentNature object.
     * 
     * @param appointmentNature - an instance of AppointmentNature.
     * @throws AkuraAppException - The exception details that occurred when deleting a AppointmentNature
     *         instance.
     */
    void deleteAppointmentNature(AppointmentNature appointmentNature) throws AkuraAppException;
    
    /**
     * Returns a list of AppointmentNature instances.
     * 
     * @return a list of AppointmentNature instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of AppointmentNature instances.
     */
    List<AppointmentNature> getAppointmentNatureList() throws AkuraAppException;
    
    /**
     * Get AppointmentNature object by AppointmentNatureId.
     * 
     * @param appointmentNatureId integer type object.
     * @return a AppointmentNature object.
     * @throws AkuraAppException throws if exception occurs when
     */
    AppointmentNature getAppointmentNatureById(int appointmentNatureId) throws AkuraAppException;
    
    /**
     * Get the any AppointmentNature object by passing the appointment nature description.
     * 
     * @param description - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the AppointmentNature object.
     */
    AppointmentNature getAnyAppointmentNature(String description) throws AkuraAppException;
    
    /**
     * Check whether appointment nature is already added.
     * 
     * @param description - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isExistsAppointmentNature(String description) throws AkuraAppException;   
    
    
    /**
     * Returns a list of AppointmentClassification instances.
     * 
     * @return a list of AppointmentClassification instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of AppointmentClassification
     *         instances.
     */
    List<AppointmentClassification> getAppointmentClassificationList() throws AkuraAppException;
    
    /**
     * Service to add AppointmentClassification instance.
     * 
     * @param appointmentClassification AppointmentClassification instance.
     * @return - the saved AppointmentClassification object.
     * @throws AkuraAppException throws if exception occurs when saving a AppointmentClassification instance.
     */
    AppointmentClassification addAppointmentClassification(AppointmentClassification appointmentClassification)
            throws AkuraAppException;
    
    /**
     * Get AppointmentClassification object by classificationId.
     * 
     * @param classificationId integer type object.
     * @return a AppointmentClassification object.
     * @throws AkuraAppException throws if exception occurs when
     */
    AppointmentClassification findAppointmentClassification(final Integer classificationId) throws AkuraAppException;
    
    /**
     * Updates the relevant AppointmentClassification object.
     * 
     * @param appointmentClassification - an instance of AppointmentClassification.
     * @throws AkuraAppException - The exception details that occurred when updating a
     *         AppointmentClassification instance.
     */
    void updateAppointmentClassification(AppointmentClassification appointmentClassification) throws AkuraAppException;
    
    /**
     * Deletes the relevant appointmentClassification object.
     * 
     * @param appointmentClassificatonId - an instance of AppointmentClassification
     * @throws AkuraAppException - The exception details that occurred when deleting a
     *         AppointmentClassification instance.
     */
    void deleteAppointmentClassification(AppointmentClassification appointmentClassificatonId) throws AkuraAppException;
    
    /**
     * Retrieve the available AppointmentClassification object.
     * 
     * @param appointmentClassification - String.
     * @return AppointmentClassification object.
     * @throws AkuraAppException - throw detailed exception.
     */
    AppointmentClassification getAnyAppointmentClassification(String appointmentClassification)
            throws AkuraAppException;
    
    /**
     * Check whether user name already exist.
     * 
     * @param name - String
     * @return true if the user name exist else false.
     * @throws AkuraAppException - Throw detailed exception.
     */
    boolean isExistsAppointmentClassification(String name) throws AkuraAppException;
    
    /**
     * Returns a StaffServiceCategory for the successful saving of a StaffServiceCategory instance.
     * 
     * @param staffServiceCategory StaffServiceCategory instance.
     * @return - the saved StaffServiceCategory object.
     * @throws AkuraAppException throws if exception occurs when saving a ClubSociety instance.
     */
    StaffServiceCategory addStaffServiceCategory(StaffServiceCategory staffServiceCategory) throws AkuraAppException;
    
    /**
     * Updates the relevant StaffServiceCategory object.
     * 
     * @param staffServiceCategory - StaffServiceCategory instance.
     * @throws AkuraAppException - if error occurs when updating a StaffServiceCategory instance.
     */
    void editStaffServiceCategory(StaffServiceCategory staffServiceCategory) throws AkuraAppException;
    
    /**
     * Deletes the relevant StaffServiceCategory object.
     * 
     * @param staffServiceCategoryId - id of staffServiceCategoryId.
     * @throws AkuraAppException - if error occurs when deleting a staffServiceCategory instance.
     */
    void deleteStaffServiceCategory(int staffServiceCategoryId) throws AkuraAppException;
    
    /**
     * Returns a list of Staff Service Category instances.
     * 
     * @return a list of StaffServiceCategory instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of StaffServiceCategory instances.
     */
    List<StaffServiceCategory> getStaffServiceCategoryList() throws AkuraAppException;
    
    /**
     * Finds the StaffServiceCategory that relevant to the StaffServiceCategory id.
     * 
     * @param staffServiceCategoryId - StaffServiceCategory id.
     * @return a StaffServiceCategory instance.
     * @throws AkuraAppException throws if exception occurs when finding a StaffServiceCategory instance.
     */
    StaffServiceCategory findStaffServiceCategoryById(int staffServiceCategoryId) throws AkuraAppException;
    
    /**
     * Check whether StaffServiceCategory is already exists.
     * 
     * @param staffServiceCategoryName - String.
     * @return true if already exist else false.
     * @throws AkuraAppException - Detailed Exception
     */
    boolean isExistsStaffServiceCategory(String staffServiceCategoryName) throws AkuraAppException;
    
   
    
    
    
}
