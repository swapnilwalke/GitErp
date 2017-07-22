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

package com.virtusa.akura.student.service;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Donation;
import com.virtusa.akura.api.dto.DonationType;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.dto.ParentDonation;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.dto.WorkingSegment;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.BaseStudentTest;
import com.virtusa.akura.student.dao.StudentParentDao;

/**
 * This test class, tests all the persistence related functionality for the parentServiceImpl domain object.
 * 
 * @author Virtusa Corporation
 */
public class ParentServiceImplTest extends BaseStudentTest {
    
    /** Holds ParentService instance. */
    @Autowired
    private ParentService parentService;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StudentService studentService;
    
    /** Holds CommonService instance. */
    @Autowired
    private CommonService commonService;
    
    /** Holds StudentParentDao instance. */
    @Autowired
    private StudentParentDao studentParentDao;
    
    /** Holds Parent instance. */
    private Parent parent;
    
    /** Holds Student instance. */
    private Student student;
    
    /** Holds ParentDonation instance. */
    private ParentDonation parentDonation;
    
    /** Holds donation instance. */
    private Donation donation;
    
    /** Holds donationType instance. */
    private DonationType donationType;
    
    /** Holds workingSegment instance. */
    private WorkingSegment workingSegment;
    
    /** Holds Religion instance. */
    private Religion religion;
    
    /** Holds province instance. */
    private Province province;
    
    /** Holds city instance. */
    private City city;
    
    /** Holds district instance. */
    private District district;
    
    /** Holds bloodGroup instance. */
    private BloodGroup bloodGroup;
    
    /** Holds nationality instance. */
    private Nationality nationality;
    
    /** Holds house instance. */
    private House house;
    
    /** Holds language instance. */
    private Language language;
    
    /** Holds methodOfTravel instance. */
    private MethodOfTravel methodOfTravel;
    
    /** Holds instance of LanguageDao. */
    @Autowired
    private LanguageDao languageDao;
    
    /** randonm string to . */
    private static final String RANDOM_STRING = String.valueOf(Math.round(Math.random() * 1000));
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {
    
        // Instantiate a Province object.
        Province newProvince = new Province();
        newProvince.setDescription("North2" + RANDOM_STRING);
        // newProvince.setModifiedTime(new Date());
        
        // Instantiates a District object.
        District newDistrict = new District();
        newDistrict.setDescription("Gampaha1" + RANDOM_STRING);
        // newDistrict.setModifiedTime(new Date());
        
        // Instantiates a City object.
        City newCity = new City();
        newCity.setDescription("Dematagoda1" + RANDOM_STRING);
        // newCity.setModifiedTime(new Date());
        
        // Instantiates a Religion object.
        Religion newReligion = new Religion();
        newReligion.setDescription("Religion" + RANDOM_STRING);
        
        // Instantiates a newWorkingSegment object.
        WorkingSegment newWorkingSegment = new WorkingSegment();
        newWorkingSegment.setDescription("Engineer");
        // newWorkingSegment.setModifiedTime(new Date());
        
        province = commonService.addProvince(newProvince);
        assertNotNull(" province saved to db " + province);
        
        newDistrict.setProvince(newProvince);
        district = commonService.addDistrict(newDistrict);
        assertNotNull(" district saved to db " + district);
        
        newCity.setDistrict(newDistrict);
        city = commonService.addCity(newCity);
        assertNotNull(" district saved to db " + city);
        
        workingSegment = new WorkingSegment();
        workingSegment.setDescription("unitTest" + RANDOM_STRING);
        workingSegment = commonService.createWorkingSegment(workingSegment);
        assertNotNull(" working segment saved to db " + workingSegment);
        
        religion = commonService.createReligion(newReligion);
        assertNotNull(" religion saved to db " + religion);
        
        // Instantiates a newParent object.
        parent = new Parent();
        parent.setDesignation("designation");
        parent.setTeacher(Boolean.FALSE);
        parent.setPastPupil(Boolean.FALSE);
        parent.setFullName("fullName");
        parent.setNameWithInitials("nameWithInitials");
        parent.setLastName("lastName");
        parent.setNationalIdNo(RANDOM_STRING + "111050V");
        parent.setAddress("address");
        parent.setMobileNo("0123456789");
        parent.setResidenceNo("0123456789");
        parent.setOfficeNo("0123456789");
        parent.setRelationship("Mother");
        parent.setEmail("emal");
        // parent.setModifiedTime(new Date());
        parent.setCityId(city.getCityId());
        parent.setTempCityId(city.getCityId());
        parent.setOfficeCityId(city.getCityId());
        parent.setWorkingSegmentId(workingSegment.getWorkingSegmentId());
        parent.setReligionId(religion.getReligionId());
        
        // Instantiates a donation type object.
        donationType = new DonationType();
        donationType.setDescription("Description" + RANDOM_STRING);
        donationType.setModifiedTime(new Date());
        
        // Instantiates a donation type object.
        donation = new Donation();
        donation.setPurpose("test");
        donation.setAmount("");
        donation.setDate(new Date());
        donation.setModifiedTime(new Date());
        
        // Instantiates a parentDonation object.
        parentDonation = new ParentDonation();
        parentDonation.setModifiedTime(new Date());
        
        // Instantiates a BloodGroup object.
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("AAAA" + RANDOM_STRING);
        bloodGroup.setModifiedTime(new Date());
        
        bloodGroup = commonService.addBloodGroup(bloodGroup);
        assertNotNull(" bloodGroup saved to db " + bloodGroup);
        
        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("Sri Lankan1111" + RANDOM_STRING);
        nationality.setModifiedTime(new Date());
        
        nationality = commonService.addNationality(nationality);
        assertNotNull(" nationality saved to db " + nationality);
        
        // Instantiates a House object.
        house = new House();
        house.setName("House" + RANDOM_STRING);
        house.setModifiedTime(new Date());
        
        house = commonService.createHouse(house);
        assertNotNull(" house saved to db " + house);
        
        // Instantiates a Language object.
        language = new Language();
        language.setLanguage("Sinhala111" + RANDOM_STRING);
        language.setModifiedTime(new Date());
        
        language = languageDao.save(language);
        assertNotNull(" language saved to db " + language);
        
        // Instantiates a Method of travel object.
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("Bus" + RANDOM_STRING);
        methodOfTravel.setModifiedTime(new Date());
        
        methodOfTravel = commonService.createMethodOfTravel(methodOfTravel);
        assertNotNull(" methodOftravel saved to db " + methodOfTravel);
        
        // Instantiates a student object.
        student = new Student();
        student.setAdmissionNo("A1123" + RANDOM_STRING);
        student.setNameWtInitials("I L");
        student.setLastName("Lolratne ");
        student.setAddress("Gampaha");
        student.setFullName("Lol TestClass");
        student.setModifiedTime(new Date());
        
        student.setLanguageId(language.getLanguageId());
        student.setReligionId(religion.getReligionId());
        student.setTravelId(methodOfTravel.getTravelId());
        student.setBloodGroupId(bloodGroup.getBloodGroupId());
        student.setHouseId(house.getHouseId());
        
        student.setCityId(city.getCityId());
        student.setNationalityId(nationality.getNationalityId());
        student.setGender('M');
        student.setIsOldBoy(false);
        student.setDateOfBirth(new Date());
        student.setAdmissionDate(new Date());
        student.setFirstSchoolDay(new Date());
        
        student = studentService.saveStudent(student);
    }
    
    /**
     * Test method to check if a parent object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddParent() throws AkuraAppException {
    
        try {
            
            Parent addParent = this.parentService.addParent(parent);
            assertNotNull("Parent object should not be null ", addParent);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            parentService.deleteParent(parent);
        }
    }
    
    /**
     * Test method to check if a donation type object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddDonationType() throws AkuraAppException {
    
        try {
            
            DonationType addDonationType = commonService.addDonationType(donationType);
            assertNotNull("Donation Type object should not be null ", addDonationType);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            commonService.deleteDonationType(donationType);
        }
    }
    
    /**
     * Test method to check if a donation object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddDonation() throws AkuraAppException {
    
        try {
            
            DonationType addDonationType = commonService.addDonationType(donationType);
            assertNotNull("Donation Type object should not be null ", addDonationType);
            donation.setDonationType(addDonationType);
            
            Donation addDonation = this.parentService.addDonation(donation);
            assertNotNull("Donation object should not be null ", addDonation);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            parentService.deleteDonation(donation);
            commonService.deleteDonationType(donationType);
        }
        
    }
    
    /**
     * Test method to check if a newParent object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddParentDonation() throws AkuraAppException {
    
        try {
            
            Parent addParent = this.parentService.addParent(parent);
            assertNotNull("Parent object should not be null ", addParent);
            
            DonationType addDonationType = commonService.addDonationType(donationType);
            assertNotNull("Donation Type object should not be null ", addDonationType);
            donation.setDonationType(addDonationType);
            
            Donation addDonation = this.parentService.addDonation(donation);
            assertNotNull("Donation object should not be null ", addDonation);
            parentDonation.setParent(addParent);
            parentDonation.setDonation(addDonation);
            
            ParentDonation addParentDonation = this.parentService.addParentDonation(parentDonation);
            assertNotNull("Parent Donation object should not be null ", addParentDonation);
            assertEquals("Returned object should equals to adding object.", addParentDonation, parentDonation);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            parentService.deleteParentDonation(parentDonation);
            parentService.deleteParent(parent);
            parentService.deleteDonation(donation);
            commonService.deleteDonationType(donationType);
        }
    }
    
    /**
     * Test method to check find the donation records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindDonation() throws AkuraAppException {
    
        try {
            
            DonationType addDonationType = commonService.addDonationType(donationType);
            assertNotNull("Donation Type object should not be null ", addDonationType);
            donation.setDonationType(addDonationType);
            
            Donation addDonation = this.parentService.addDonation(donation);
            assertNotNull("Donation object should not be null ", addDonation);
            
            Donation findDonation = (Donation) parentService.findDonation(donation.getDonationId());
            assertNotNull("Dontion object should not null ", findDonation);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            parentService.deleteDonation(donation);
            commonService.deleteDonationType(donationType);
        }
    }
    
    /**
     * Test method to check if a donation object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testModifyDonation() throws AkuraAppException {
    
        try {
            
            DonationType addDonationType = commonService.addDonationType(donationType);
            assertNotNull("Donation Type object should not be null ", addDonationType);
            donation.setDonationType(addDonationType);
            
            Donation addDonation = this.parentService.addDonation(donation);
            assertNotNull("Donation object should not be null ", addDonation);
            donation.setPurpose("Updated Purpose");
            
            parentService.modifyDonation(donation);
            Donation updateDonation = this.parentService.findDonation(donation.getDonationId());
            assertEquals(donation.getPurpose(), updateDonation.getPurpose());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            parentService.deleteDonation(donation);
            commonService.deleteDonationType(donationType);
        }
    }
    
    /**
     * Test method to check if a donation type object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testModifyDonationType() throws AkuraAppException {
    
        try {
            
            DonationType addDonationType = commonService.addDonationType(donationType);
            assertNotNull("Donation Type object should not be null ", addDonationType);
            donationType.setDescription("Updated Description");
            commonService.modifyDonationType(addDonationType);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            commonService.deleteDonationType(donationType);
        }
    }
    
    /**
     * Test method to check if a newParent donation object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testModifyParentDonation() throws AkuraAppException {
    
        try {
            
            Parent addParent = this.parentService.addParent(parent);
            assertNotNull("Parent object should not be null ", addParent);
            
            DonationType addDonationType = commonService.addDonationType(donationType);
            assertNotNull("Donation Type object should not be null ", addDonationType);
            donation.setDonationType(addDonationType);
            
            Donation addDonation = this.parentService.addDonation(donation);
            assertNotNull("Donation object should not be null ", addDonation);
            parentDonation.setParent(addParent);
            parentDonation.setDonation(addDonation);
            
            ParentDonation addParentDonation = this.parentService.addParentDonation(parentDonation);
            assertNotNull("Parent Donation object should not be null ", addParentDonation);
            parentService.modifyParentDonation(parentDonation);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            parentService.deleteParentDonation(parentDonation);
            parentService.deleteParent(parent);
            parentService.deleteDonation(donation);
            commonService.deleteDonationType(donationType);
        }
    }
    
    /**
     * Test method to check if a parent object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testModifyParent() throws AkuraAppException {
    
        try {
            
            Parent addParent = this.parentService.addParent(parent);
            assertNotNull("Parent object should not be null ", addParent);
            parent.setFullName("updated");
            parentService.modifyParent(parent);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            parentService.deleteParent(parent);
        }
    }
    
    /**
     * Test method to check load all the donation records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testViewAllDonation() throws AkuraAppException {
    
        try {
            
            DonationType addDonationType = commonService.addDonationType(donationType);
            assertNotNull("Donation Type object should not be null ", addDonationType);
            donation.setDonationType(addDonationType);
            
            Donation addDonation = this.parentService.addDonation(donation);
            assertNotNull("Donation object should not be null ", addDonation);
            
            List<Donation> donationList = parentService.viewAllDonation();
            assertNotNull(donationList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            parentService.deleteDonation(donation);
            commonService.deleteDonationType(donationType);
        }
    }
    
    /**
     * Test method to check load all the donation type records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testViewAllDonationType() throws AkuraAppException {
    
        try {
            
            DonationType addDonationType = commonService.addDonationType(donationType);
            assertNotNull("Donation Type object should not be null ", addDonationType);
            
            List<DonationType> donationTypeList = commonService.viewAllDonationType();
            assertNotNull(donationTypeList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            commonService.deleteDonationType(donationType);
        }
    }
    
    /**
     * Test method to check load all the parent records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testViewAllParent() throws AkuraAppException {
    
        try {
            
            Parent addParent = this.parentService.addParent(parent);
            assertNotNull("Parent object should not be null ", addParent);
            
            List<Parent> parentList = parentService.viewAllParents();
            assertNotNull(parentList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            parentService.deleteParent(parent);
        }
    }
    
    /**
     * Test method to check find the Parent records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindParent() throws AkuraAppException {
    
        try {
            
            Parent addParent = this.parentService.addParent(parent);
            assertNotNull("Parent object should not be null ", addParent);
            
            Parent findParent = this.parentService.findParent(parent.getParentId());
            assertNotNull("Parent object should not be null ", findParent);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            parentService.deleteParent(parent);
        }
    }
    
    /**
     * Test method to check load all the Parent Donation records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testViewAllParentDonation() throws AkuraAppException {
    
        try {
            
            DonationType addDonationType = commonService.addDonationType(donationType);
            assertNotNull("Donation Type object should not be null ", addDonationType);
            donation.setDonationType(addDonationType);
            
            Donation addDonation = this.parentService.addDonation(donation);
            assertNotNull("Donation object should not be null ", addDonation);
            
            Parent addParent = this.parentService.addParent(parent);
            assertNotNull("Parent object should not be null ", addParent);
            
            parentDonation.setParent(addParent);
            parentDonation.setDonation(addDonation);
            
            List<ParentDonation> parentDonationList = parentService.viewAllParentDonation();
            assertNotNull(parentDonationList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            parentService.deleteDonation(donation);
            parentService.deleteParent(parent);
            parentService.deleteParentDonation(parentDonation);
            commonService.deleteDonationType(donationType);
        }
    }
    
    /**
     * Test method to check find the donation type from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindDonationType() throws AkuraAppException {
    
        try {
            
            DonationType addDonationType = commonService.addDonationType(donationType);
            assertNotNull("Donation Type object should not be null ", addDonationType);
            donation.setDonationType(addDonationType);
            
            DonationType findDonationType =
                    (DonationType) commonService.findDonationType(donationType.getDonationTypeId());
            assertNotNull("Dontion object should not null ", findDonationType);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            commonService.deleteDonationType(donationType);
        }
    }
    
    /**
     * Test method to check load all the working segment types from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetWorkingSegmentList() throws AkuraAppException {
    
        try {
            List<WorkingSegment> segmentList = commonService.getWorkingSegmentList();
            assertNotNull(segmentList.size());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a StudentParent object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddStudentParent() throws AkuraAppException {
    
        try {
            Parent addParent = parentService.addParent(parent);
            assertNotNull("Parent object should not be null ", addParent);
            
            StudentParent studentParent = new StudentParent();
            studentParent.setParent(addParent);
            studentParent.setStudent(student);
            studentParent.setRelationship('F');
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } finally {
            parentService.deleteParent(parent);
        }
    }
    
    /**
     * Test method to check load all the StudentParent types from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetStudentParentListByStudentID() throws AkuraAppException {
    
        try {
            Parent addParent = parentService.addParent(parent);
            assertNotNull("Parent object should not be null ", addParent);
            
            StudentParent studentParent = new StudentParent();
            studentParent.setParent(addParent);
            studentParent.setStudent(student);
            studentParent.setRelationship('F');
            
            List<StudentParent> stuParList = parentService.getStudentParentListByStudentId(student.getStudentId());
            assertNotNull(stuParList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } finally {
            parentService.deleteParent(parent);
        }
    }
    
    /**
     * Test method to check load all the Donations by the student id from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetDonationListByStudentId() throws AkuraAppException {
    
        try {
            
            DonationType addDonationType = commonService.addDonationType(donationType);
            assertNotNull("Donation Type object should not be null ", addDonationType);
            donation.setDonationType(addDonationType);
            
            Donation addDonation = this.parentService.addDonation(donation);
            assertNotNull("Donation object should not be null ", addDonation);
            
            Parent addParent = parentService.addParent(parent);
            assertNotNull("Parent object should not be null ", addParent);
            
            StudentParent studentParent = new StudentParent();
            studentParent.setParent(addParent);
            studentParent.setStudent(student);
            studentParent.setRelationship('F');
            
            parentDonation.setParent(addParent);
            parentDonation.setDonation(addDonation);
            
            List<Donation> donationList = parentService.getDonationListByStudentId(student.getStudentId());
            assertNotNull(donationList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        } finally {
            parentService.deleteDonation(donation);
            parentService.deleteParent(parent);
            parentService.deleteParentDonation(parentDonation);
            commonService.deleteDonationType(donationType);
        }
    }
    
    /**
     * Test method to check load the Parent object by the Parent NIC no from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetParentByNIC() throws AkuraAppException {
    
        try {
            Parent addParent = parentService.addParent(parent);
            assertNotNull("Parent object should not be null ", addParent);
            
            Parent getParent = parentService.getParentByNIC(parent.getNationalIdNo());
            assertNotNull("Parent object should not be null ", getParent);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } finally {
            parentService.deleteParent(parent);
        }
    }
    
    /**
     * Test getAllChilds(String) method in parentService.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void getAllChildsTest() throws AkuraAppException {
    
        StudentParent studentParent = null;
        try {
            parent = parentService.addParent(parent);
            studentParent = new StudentParent();
            studentParent.setParent(parent);
            studentParent.setStudent(student);
            studentParent.setRelationship('F');
            studentParentDao.save(studentParent);
            
            // testing
            List<Student> children = parentService.getAllChilds(parent.getNationalIdNo());
            assertNotNull("Parent object should not be null ", children);
            assertTrue("children cannot be empty", !children.isEmpty());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } finally {
            studentParentDao.delete(studentParent);
            parentService.deleteParent(parent);
        }
        
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {// parent already deleted
            parentService.deleteParent(parent);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        try {
            
            studentService.deleteStudent(student.getStudentId());
            
            commonService.deleteWorkingSegment(workingSegment.getWorkingSegmentId());
            commonService.deleteCity(city);
            commonService.deleteDistrict(district);
            commonService.deleteProvince(province);
            commonService.deleteReligion(religion.getReligionId());
            
            commonService.deleteMethodOfTravel(methodOfTravel.getTravelId());
            languageDao.delete(language);
            commonService.deleteHouse(house.getHouseId());
            commonService.deleteNationality(nationality);
            commonService.deleteBloodGroup(bloodGroup);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
    
}
