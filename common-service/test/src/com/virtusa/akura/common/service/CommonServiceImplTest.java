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

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.dto.AppointmentNature;
import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.EducationalQualification;
import com.virtusa.akura.api.dto.EmploymentStatus;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.ParticipantCategory;
import com.virtusa.akura.api.dto.ProfessionalQualification;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Race;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Scholarship;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.dto.SportSub;
import com.virtusa.akura.api.dto.StaffServiceCategory;
import com.virtusa.akura.api.dto.Stream;
import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.api.dto.SubTerm;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.common.dao.EmploymentStatusDao;
import com.virtusa.akura.common.dao.StaffServiceCategoryDao;
import com.virtusa.akura.common.dao.StudyMediumDao;
import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.dto.StaffLeaveType;
import com.virtusa.akura.api.dto.CivilStatus;

/**
 * This test class, tests all the persistence related functionality for the CommonServiceImpl domain object.
 * 
 * @author Virtusa Corporation
 */
public class CommonServiceImplTest extends BaseCommonTest {
    
    /** Holds ParentService instance. */
    @Autowired
    private CommonService commonService;
    
    /** Holds ParentService instance. */
    @Autowired
    private StaffCommonService staffCommonService;
    
    /** Holds StudyMediumDao instance. */
    @Autowired
    private StudyMediumDao studyMediumDao;
    
    /** Holds StaffServiceCategorydao instance. */
    @Autowired
    private StaffServiceCategoryDao staffServicecategoryDao;
    
    /** Holds EducationalQualification instance. */
    private EducationalQualification educationalQualification;
    
    /** Holds ProfessionalQualification instance. */
    private ProfessionalQualification professionalQualification;
    
    /** Holds EmploymentStatusDao instance. */
    @Autowired
    private EmploymentStatusDao employmentStatusDao;
    
    /** Holds City instance. */
    private City city;
    
    /** Holds Country instance. */
    private Country country;
    
    /** Holds District instance. */
    private District district;
    
    /** Holds Province instance. */
    private Province province;
    
    /** Holds Term instance. */
    private Term term;
    
    /** Holds SubTerm instance. */
    private SubTerm subTerm;
    
    /** Holds Religion instance. */
    private Religion religion;
    
    /** Holds BloodGroup instance. */
    private BloodGroup bloodGroup;
    
    /** Holds Nationality instance. */
    private Nationality nationality;
    
    /** Holds Race instance. */
    private Race race;
    
    /** Holds Nationality instance. */
    private House house;
    
    /** Holds Nationality instance. */
    private ClubSociety clubSociety;
    
    /** Holds StaffService Category instance. */
    private StaffServiceCategory staffServiceCategory;
    
    /** Holds Sport instance. */
    private Sport sport;
    
    /** Holds SportSub instance. */
    private SportSub sportSub;
    
    /** Holds Scholarship instance. */
    private Scholarship scholarship;
    
    /** Holds Scholarship instance. */
    private Subject subject;
    
    /** Holds Scholarship instance. */
    private MethodOfTravel methodOfTravel;
    
    /** /** Holds StudyMedium instance. */
    private StudyMedium studyMedium;
    
    /** Holds Civil Status instance. */
    private CivilStatus civilStatus;
    
    /**
     * Represents an instance of Stream.
     */
    private Stream stream;
    
    /**
     * Represents an instance of SchoolClass.
     */
    private SchoolClass classInstace;
    
    /** Represents an instance of SpecialEvents. */
    private SpecialEvents specialEvents;
    
    /** Represents an instance of ParticipantCategory. */
    private ParticipantCategory participantCategory;
    
    /** Represents an instance of ParticipantCategory. */
    private SpecialEventsParticipation specialEventsParticipation;
    
    /** Holds AppointmentClassification instance. */
    private AppointmentClassification appointmentClassification;
    
    /** Holds AppointmentNature instance. */
    private AppointmentNature appointmentNature;
    
    /** Holds Section instance. */
    private Section section;
    
    /** Holds StaffLeaveType instance. */
    private StaffLeaveType staffLeaveType;
    
    /** Holds EmploymentStatus instance. */
    private EmploymentStatus employmentStatus;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {

        // Instantiates EducationalQualification object.
        educationalQualification = new EducationalQualification();
        educationalQualification.setDescription("EducationalQualification Description test123");
        
        // Instantiates ProfessionalQualification object.
        professionalQualification = new ProfessionalQualification();
        professionalQualification.setDescription("ProfessionalQualification Description test123");
        
        // Instantiates Province object.
        province = new Province();
        province.setDescription("Province test123");
        province.setModifiedTime(new Date());
        
        // Instantiates District object.
        district = new District();
        district.setDescription("District Description test123");
        district.setModifiedTime(new Date());
        
        // Instantiates City object.
        city = new City();
        city.setDescription("City Description test123");
        city.setModifiedTime(new Date());
        
        // Instantiates Country object.
        country = new Country();
        country.setCountryName("Country Description test123");
        country.setModifiedTime(new Date());
        
        // Instantiates Term object.
        term = new Term();
        term.setDescription("Term test123");
        term.setFromMonth(new Date());
        term.setToMonth(new Date());
        term.setModifiedTime(new Date());
        
        // Instantiates SubTerm object.
        subTerm = new SubTerm();
        subTerm.setDescription("SubTerm test123");
        subTerm.setFromMonth(new Date());
        subTerm.setToMonth(new Date());
        subTerm.setModifiedTime(new Date());
        
        // Instantiates a Religion object.
        religion = new Religion();
        religion.setDescription("Religion test123");
        religion.setModifiedTime(new Date());
        
        // Instantiates a BloodGroup object.
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("Blood group test123");
        bloodGroup.setModifiedTime(new Date());
        
        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("nationality test123");
        nationality.setModifiedTime(new Date());
        
        // Instantiates a Race object.
        race = new Race();
        race.setDescription("race test123");
        race.setModifiedTime(new Date());
        
        // Instantiates a House object.
        house = new House();
        house.setName("House test1123");
        house.setModifiedTime(new Date());
        
        // Instantiates a ClubSociety object.
        clubSociety = new ClubSociety();
        clubSociety.setName("Club Society test123");
        clubSociety.setModifiedTime(new Date());
        
        // Instantiates a StaffServiceCategory object
        staffServiceCategory = new StaffServiceCategory();
        staffServiceCategory.setDescription("Staff Service Category test123");
        staffServiceCategory.setModifiedTime(new Date());
        
        // Instantiates a ClubSociety object.
        sport = new Sport();
        sport.setDescription("Sport test123");
        sport.setModifiedTime(new Date());
        
        // Instantiates a ClubSociety object.
        sportSub = new SportSub();
        sportSub.setDescription("Sports Sub test123");
        sportSub.setModifiedTime(new Date());
        
        // Instantiates a Scholarship object.
        scholarship = new Scholarship();
        scholarship.setName("Scolarship test1123");
        scholarship.setModifiedTime(new Date());
        
        // Instantiates a Subject object.
        subject = new Subject();
        subject.setSubjectId(0);
        subject.setDescription("New Subject123");
        subject.setModifiedTime(new Date());
        
        // Instantiates a Method of travel object.
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("Method of travel test123");
        methodOfTravel.setModifiedTime(new Date());
        
        // instantiates SchoolClass object.
        classInstace = new SchoolClass();
        classInstace.setDescription("A123");
        classInstace.setModifiedTime(new Date());
        
        // create a subject object.
        subject = new Subject();
        subject.setSubjectId(0);
        subject.setDescription("Biology test123");
        subject.setModifiedTime(new Date());
        
        // Instantiates Stream object.
        stream = new Stream();
        stream.setDescription("Science test123");
        stream.setModifiedTime(new Date());
        
        // Instantiates a StudyMedium object.
        studyMedium = new StudyMedium();
        studyMedium.setStudyMediumId(0);
        studyMedium.setStudyMediumName("Medium test1123");
        
        // Instantiates a AppointmentClassification object.
        appointmentClassification = new AppointmentClassification();
        appointmentClassification.setDescription("AppointmentClassification test123");
        appointmentClassification.setModifiedTime(new Date());
        
        // Instantiates a AppointmentNature object.
        appointmentNature = new AppointmentNature();
        appointmentNature.setDescription("Appointment Nature test123");
        appointmentNature.setModifiedTime(new Date());
        
        participantCategory = new ParticipantCategory();
        participantCategory.setParticipantCategoryId(0);
        participantCategory.setDescription("CLUB AND SOCIETY WISE");
        
        specialEvents = new SpecialEvents();
        specialEvents.setParticipantCategory(participantCategory);
        specialEvents.setName("Event Name Test");
        specialEvents.setDescription("event description test");
        specialEvents.setDate(new Date());
        
        specialEventsParticipation = new SpecialEventsParticipation();
        specialEventsParticipation.setSpecialEvents(specialEvents);
        specialEventsParticipation.setClubSociety(clubSociety);
        
        // Instantiates a Section object.
        section = new Section();
        section.setDescription("Section test123");
        section.setModifiedTime(new Date());
        
        // Instantiates a StaffLeaveType object.
        staffLeaveType = new StaffLeaveType();
        staffLeaveType.setStaffLeaveTypeId(0);
        staffLeaveType.setDescription("StaffLeaveType test1123");
        staffLeaveType.setMaxStaffLeaves(0);
        
        // Instantiates a Civil Status object.
        civilStatus = new CivilStatus();
        civilStatus.setDescription("civilStatus test123");
        civilStatus.setModifiedTime(new Date());
        
        // Instantiates a Employment Status object.
        employmentStatus = new EmploymentStatus();
        employmentStatus.setDescription("EmploymentStatus test 123");
        employmentStatus.setModifiedTime(new Date());
        
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {

        if (specialEventsParticipation.getSpecialEventsParticipationId() > 0) {
            commonService.deleteSpecialEventsParticipation(specialEventsParticipation);
        }
        if (specialEvents.getSpecialEventsId() > 0) {
            commonService.deleteSpecialEvents(specialEvents.getSpecialEventsId());
        }
        if (educationalQualification.getEducationalQualificationId() > 0) {
            staffCommonService.deleteEducationalQualification(educationalQualification.getEducationalQualificationId());
        }
        if (professionalQualification.getProfessionalQualificationId() > 0) {
            staffCommonService.deleteProfessionalQualification(professionalQualification.getProfessionalQualificationId());
        }
        if (city.getCityId() > 0) {
            commonService.deleteCity(city);
        }
        if (country.getCountryId() > 0) {
            commonService.deleteCountry(country);
        }
        if (district.getDistrictId() > 0) {
            commonService.deleteDistrict(district);
        }
        if (province.getProvinceId() > 0) {
            commonService.deleteProvince(province);
        }
        if (clubSociety.getClubSocietyId() > 0) {
            commonService.deleteClubSociety(clubSociety.getClubSocietyId());
        }
        
        if (staffServiceCategory.getServiceId() > 0) {
            staffCommonService.deleteStaffServiceCategory(staffServiceCategory.getServiceId());
        }
        
        if (subTerm.getSubTermId() > 0) {
            commonService.deleteSubTerm(subTerm.getSubTermId());
        }
        if (term.getTermId() > 0) {
            commonService.deleteTerm(term.getTermId());
        }
        if (sport.getSportId() > 0) {
            commonService.deleteSport(sport.getSportId());
        }
        if (sportSub.getSportSubId() > 0) {
            commonService.deleteSportSub(sportSub.getSportSubId());
        }
        if (subject.getSubjectId() > 0) {
            commonService.deleteSubject(subject);
        }
        if (scholarship.getScholarshipId() > 0) {
            commonService.deleteScholarship(scholarship.getScholarshipId());
        }
        if (religion.getReligionId() > 0) {
            commonService.deleteReligion(religion.getReligionId());
        }
        if (house.getHouseId() > 0) {
            commonService.deleteHouse(house.getHouseId());
        }
        if (methodOfTravel.getTravelId() > 0) {
            commonService.deleteMethodOfTravel(methodOfTravel.getTravelId());
        }
        if (bloodGroup.getBloodGroupId() > 0) {
            commonService.deleteBloodGroup(bloodGroup);
        }
        if (nationality.getNationalityId() > 0) {
            commonService.deleteNationality(nationality);
        }
        if (race.getRaceId() > 0) {
            commonService.deleteRace(race);
        }
        
        if (studyMedium.getStudyMediumId() > 0) {
            studyMediumDao.delete(studyMedium);
        }
        
        if (stream.getStreamId() > 0) {
            commonService.deleteStream(stream);
        }
        if (classInstace.getClassId() > 0) {
            commonService.deleteClass(classInstace);
        }
        if (appointmentClassification.getClassificationId() > 0) {
            staffCommonService.deleteAppointmentClassification(appointmentClassification);
        }
        if (appointmentNature.getNatureId() > 0) {
            staffCommonService.deleteAppointmentNature(appointmentNature);
        }
        if (section.getSectionId() > 0) {
            staffCommonService.deleteSection(section);
        }
        
        if (staffLeaveType.getStaffLeaveTypeId() > 0) {
            staffCommonService.deleteStaffLeaveType(staffLeaveType.getStaffLeaveTypeId());
        }
        
        if (civilStatus.getCivilStatusId() > 0) {
            staffCommonService.deleteCivilStatus(civilStatus);
        }
        
        if (employmentStatus.getEmploymentStatusId() > 0) {
            commonService.deleteEmploymentStatus(employmentStatus);
        }
    }
    
    /**
     * Test method to check if a EducationalQualification object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testCreateEducationalQualification() throws AkuraAppException {

        try {
            EducationalQualification newEducationalQualification =
                    staffCommonService.createEducationalQualification(educationalQualification);
            assertNotNull("EducationalQualification Type object should not be null ", newEducationalQualification);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a ProfessionalQualification object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testCreateProfessionalQualification() throws AkuraAppException {

        try {
            ProfessionalQualification newProfessionalQualification =
                    staffCommonService.createProfessionalQualification(professionalQualification);
            assertNotNull("ProfessionalQualification Type object should not be null ", newProfessionalQualification);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a City object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testAddCity() throws AkuraAppException {

        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", newProvince);
            
            district.setProvince(newProvince);
            District newDistrict = commonService.addDistrict(district);
            assertNotNull("District Type object should not be null ", newDistrict);
            
            city.setDistrict(newDistrict);
            City newCity = commonService.addCity(city);
            assertNotNull("City Type object should not be null ", newCity);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a Country object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testAddCountry() throws AkuraAppException {

        try {
            Country newCountry = commonService.addCountry(country);
            assertNotNull("Country Type object should not be null ", newCountry);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a District object was successfully added to the database.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testAddDistrict() throws AkuraAppException {

        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", newProvince);
            
            district.setProvince(newProvince);
            District newDistrict = commonService.addDistrict(district);
            assertNotNull("District Type object should not be null ", newDistrict);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a Province object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testAddProvince() throws AkuraAppException {

        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", newProvince);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a ClubSociety object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    /*
     * @Test public final void testAddClubSociety() throws AkuraAppException { try {
     * commonService.addClubSociety(clubSociety); // assertNotNull(findClubSociety); } catch
     * (AkuraAppException e) { e.printStackTrace(); } }
     */
    /**
     * Test method to check if a Sport object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    /*
     * @Test public final void testAddSport() throws AkuraAppException { try { commonService.addSport(sport);
     * // assertNotNull(findClubSociety); } catch (AkuraAppException e) { e.printStackTrace(); } }
     */
    /**
     * Test method to check if a SportSub object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    /*
     * @Test public final void testAddSportSub() throws AkuraAppException { try {
     * commonService.addSportSub(sportSub); // assertNotNull(findClubSociety); } catch (AkuraAppException e) {
     * e.printStackTrace(); } }
     */
    /**
     * Test method to check if a EducationalQualification object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdateEducationalQualification() throws AkuraAppException {

        String newDescription = "New EducationalQualification Description";
        try {
            EducationalQualification oldEducationalQualification =
                    staffCommonService.createEducationalQualification(educationalQualification);
            assertNotNull("EducationalQualification Type object should not be null ", oldEducationalQualification);
            
            oldEducationalQualification.setDescription(newDescription);
            staffCommonService.updateEducationalQualification(oldEducationalQualification);
            
            EducationalQualification newEducationalQualification =
                    (EducationalQualification) staffCommonService.findEducationalQualificationById(educationalQualification
                            .getEducationalQualificationId());
            assertEquals(oldEducationalQualification.getDescription(), newEducationalQualification.getDescription());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a ProfessionalQualification object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdateProfessionalQualification() throws AkuraAppException {

        String newDescription = "New ProfessionalQualification Description";
        try {
            ProfessionalQualification oldProfessionalQualification =
                    staffCommonService.createProfessionalQualification(professionalQualification);
            assertNotNull("ProfessionalQualification Type object should not be null ", oldProfessionalQualification);
            
            oldProfessionalQualification.setDescription(newDescription);
            staffCommonService.updateProfessionalQualification(oldProfessionalQualification);
            
            ProfessionalQualification newProfessionalQualification =
                    (ProfessionalQualification) staffCommonService
                            .findProfessionalQualificationById(professionalQualification
                                    .getProfessionalQualificationId());
            assertEquals(oldProfessionalQualification.getDescription(), newProfessionalQualification.getDescription());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a City object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdateCity() throws AkuraAppException {

        String newDescription = "New City Description";
        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", newProvince);
            
            district.setProvince(newProvince);
            District newDistrict = commonService.addDistrict(district);
            assertNotNull("District Type object should not be null ", newDistrict);
            
            city.setDistrict(newDistrict);
            City oldCity = commonService.addCity(city);
            assertNotNull("City Type object should not be null ", oldCity);
            
            oldCity.setDescription(newDescription);
            commonService.updateCity(oldCity);
            
            City newCity = (City) commonService.findCity(city.getCityId());
            assertEquals(city.getDescription(), newCity.getDescription());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a Country object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdateCountry() throws AkuraAppException {

        String newDescription = "New Country Description";
        try {
            Country oldCountry = commonService.addCountry(country);
            assertNotNull("Country Type object should not be null ", oldCountry);
            
            oldCountry.setCountryName(newDescription);
            commonService.updateCountry(oldCountry);
            
            Country newCountry = (Country) commonService.findCountry(country.getCountryId());
            assertEquals(country.getCountryName(), newCountry.getCountryName());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a District object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdateDistrict() throws AkuraAppException {

        String newDescription = "New District Description";
        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", newProvince);
            
            district.setProvince(newProvince);
            District oldDistrict = commonService.addDistrict(district);
            assertNotNull("District Type object should not be null ", oldDistrict);
            
            oldDistrict.setDescription(newDescription);
            commonService.updateDistrict(oldDistrict);
            
            District newDistrict = (District) commonService.findDistrict(district.getDistrictId());
            assertEquals(district.getDescription(), newDistrict.getDescription());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a Province object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdateProvince() throws AkuraAppException {

        String newDescription = "New Province Description";
        try {
            Province oldProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", oldProvince);
            
            oldProvince.setDescription(newDescription);
            commonService.updateProvince(oldProvince);
            
            Province newProvince = (Province) commonService.findProvince(province.getProvinceId());
            assertEquals(province.getDescription(), newProvince.getDescription());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a ClubSociety object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    /*
     * @Test public final void testEditClubSociety() throws AkuraAppException { try {
     * clubSociety.setDescription("Updated Club Society"); commonService.editClubSociety(clubSociety); //
     * assertNotNull(findClubSociety); } catch (AkuraAppException e) { e.printStackTrace(); } }
     */
    /**
     * Test method to check if a Sport object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    /*
     * @Test public final void testEditSport() throws AkuraAppException { try {
     * sport.setDescription("Updated Sport"); commonService.editSport(sport); //
     * assertNotNull(findClubSociety); } catch (AkuraAppException e) { e.printStackTrace(); } }
     */
    /**
     * Test method to check if a SportSub object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    /*
     * @Test public final void testEditSportSub() throws AkuraAppException { try {
     * sportSub.setDescription("Updated SportSub"); commonService.editSportSub(sportSub); //
     * assertNotNull(findClubSociety); } catch (AkuraAppException e) { e.printStackTrace(); } }
     */
    /**
     * Test method to find EducationalQualification records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindEducationalQualificationById() throws AkuraAppException {

        try {
            EducationalQualification oldEducationalQualification =
                    staffCommonService.createEducationalQualification(educationalQualification);
            assertNotNull("EducationalQualification Type object should not be null ", oldEducationalQualification);
            
            EducationalQualification newEducationalQualification =
                    (EducationalQualification) staffCommonService
                            .findEducationalQualificationById(oldEducationalQualification
                                    .getEducationalQualificationId());
            assertNotNull("EducationalQualification Type object should not be null ", newEducationalQualification);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to find ProfessionalQualification records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindProfessionalQualificationById() throws AkuraAppException {

        try {
            ProfessionalQualification oldProfessionalQualification =
                    staffCommonService.createProfessionalQualification(professionalQualification);
            assertNotNull("ProfessionalQualification Type object should not be null ", oldProfessionalQualification);
            
            ProfessionalQualification newProfessionalQualification =
                    (ProfessionalQualification) staffCommonService
                            .findProfessionalQualificationById(oldProfessionalQualification
                                    .getProfessionalQualificationId());
            assertNotNull("ProfessionalQualification Type object should not be null ", newProfessionalQualification);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to find City records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindCity() throws AkuraAppException {

        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", newProvince);
            
            district.setProvince(newProvince);
            District newDistrict = commonService.addDistrict(district);
            assertNotNull("District Type object should not be null ", newDistrict);
            
            city.setDistrict(newDistrict);
            City oldCity = commonService.addCity(city);
            assertNotNull("City Type object should not be null ", oldCity);
            
            City newCity = (City) commonService.findCity(oldCity.getCityId());
            assertNotNull("City Type object should not be null ", newCity);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to find Country records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindCountry() throws AkuraAppException {

        try {
            Country oldCountry = commonService.addCountry(country);
            assertNotNull("Country Type object should not be null ", oldCountry);
            
            Country newCountry = (Country) commonService.findCountry(oldCountry.getCountryId());
            assertNotNull("Country Type object should not be null ", newCountry);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to find District records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindDistrict() throws AkuraAppException {

        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", newProvince);
            
            district.setProvince(newProvince);
            District oldDistrict = commonService.addDistrict(district);
            assertNotNull("District Type object should not be null ", oldDistrict);
            
            District newDistrict = (District) commonService.findDistrict(oldDistrict.getDistrictId());
            assertNotNull("District Type object should not be null ", newDistrict);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to find Province records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindProvince() throws AkuraAppException {

        try {
            Province oldProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", oldProvince);
            
            Province newProvince = (Province) commonService.findProvince(oldProvince.getProvinceId());
            assertNotNull("Province Type object should not be null ", newProvince);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to find Scholarship records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindSubjectById() throws AkuraAppException {

        try {
            Subject oldSubject = commonService.addSubject(subject);
            assertNotNull("Subject Type object should not be null ", oldSubject);
            
            Subject newSubject = (Subject) commonService.findSubjectById(oldSubject.getSubjectId());
            assertNotNull(newSubject);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to find Scholarship records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindScholarshipById() throws AkuraAppException {

        try {
            Scholarship oldScholarship = commonService.createScholarship(scholarship);
            assertNotNull("Scholarship Type object should not be null ", oldScholarship);
            
            Scholarship newScholarship =
                    (Scholarship) commonService.findScholarshipById(oldScholarship.getScholarshipId());
            assertNotNull("Scholarship Type object should not be null ", newScholarship);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the EducationalQualifications records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetEducationalQualifications() throws AkuraAppException {

        try {
            EducationalQualification newEducationalQualification =
                    staffCommonService.createEducationalQualification(educationalQualification);
            assertNotNull("EducationalQualification Type object should not be null ", newEducationalQualification);
            
            List<EducationalQualification> eduQualificationsList = staffCommonService.getEducationalQualifications();
            assertNotNull(eduQualificationsList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the EducationalQualifications records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetProfessionalQualifications() throws AkuraAppException {

        try {
            ProfessionalQualification newProfessionalQualification =
                    staffCommonService.createProfessionalQualification(professionalQualification);
            assertNotNull("ProfessionalQualification Type object should not be null ", newProfessionalQualification);
            
            List<ProfessionalQualification> proQualificationsList = staffCommonService.getProfessionalQualifications();
            assertNotNull(proQualificationsList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the House records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetHouseList() throws AkuraAppException {

        try {
            House newHouse = commonService.createHouse(house);
            assertNotNull("House Type object should not be null ", newHouse);
            
            List<House> houseList = commonService.getHouseList();
            assertNotNull(houseList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the Religion records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetReligionList() throws AkuraAppException {

        try {
            Religion newReligion = commonService.createReligion(religion);
            assertNotNull("Religion Type object should not be null ", newReligion);
            
            List<Religion> religionList = commonService.getReligionList();
            assertNotNull(religionList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the Language records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetLanguageList() throws AkuraAppException {

        try {
            // Language newLanguage = schoolService.
            
            assertNotNull(commonService.getLanguageList());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the City records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetCityList() throws AkuraAppException {

        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", newProvince);
            
            district.setProvince(newProvince);
            District newDistrict = commonService.addDistrict(district);
            assertNotNull("District Type object should not be null ", newDistrict);
            
            city.setDistrict(newDistrict);
            City newCity = commonService.addCity(city);
            assertNotNull("City Type object should not be null ", newCity);
            
            List<City> cityList = commonService.getCityList();
            assertNotNull(cityList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the Country records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetCountryList() throws AkuraAppException {

        try {
            Country newCountry = commonService.addCountry(country);
            assertNotNull("Country Type object should not be null ", newCountry);
            
            List<Country> countryList = commonService.getCountryList();
            assertNotNull(countryList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the District records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetDistrictList() throws AkuraAppException {

        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", newProvince);
            
            district.setProvince(newProvince);
            District newDistrict = commonService.addDistrict(district);
            assertNotNull("District Type object should not be null ", newDistrict);
            
            List<District> districtList = commonService.getDistrictList();
            assertNotNull(districtList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the Province records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetProvinceList() throws AkuraAppException {

        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", newProvince);
            
            List<Province> provinceList = commonService.getProvinceList();
            assertNotNull(provinceList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the MethodOfTravel records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetMethodOfTravelList() throws AkuraAppException {

        try {
            MethodOfTravel newMethodOfTravel = commonService.createMethodOfTravel(methodOfTravel);
            assertNotNull("MethodOfTravel Type object should not be null ", newMethodOfTravel);
            
            List<MethodOfTravel> methodOfTravelList = commonService.getMethodOfTravelList();
            assertNotNull(methodOfTravelList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the Term records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetTermList() throws AkuraAppException {

        try {
            Term newTerm = commonService.createTerm(term);
            assertNotNull("Term Type object should not be null ", newTerm);
            
            List<Term> termList = commonService.getTermList();
            assertNotNull(termList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the SubTerm records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetSubTermList() throws AkuraAppException {

        try {
            Term newTerm = commonService.createTerm(term);
            assertNotNull("Term Type object should not be null ", newTerm);
            
            subTerm.setTermId(newTerm.getTermId());
            SubTerm newSubTerm = commonService.createSubTerm(subTerm);
            assertNotNull("SubTerm Type object should not be null ", newSubTerm);
            
            List<SubTerm> subTermList = commonService.getSubTermList();
            assertNotNull(subTermList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to GetSubTermsOfATerm from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetSubTermsOfATerm() throws AkuraAppException {

        try {
            Term newTerm = commonService.createTerm(term);
            assertNotNull("Term Type object should not be null ", newTerm);
            
            List<SubTerm> subTermList = commonService.getSubTermsOfATerm(newTerm.getTermId());
            assertNotNull(subTermList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the BloodGroup records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetBloodGroupList() throws AkuraAppException {

        try {
            BloodGroup newBloodGroup = commonService.addBloodGroup(bloodGroup);
            assertNotNull("BloodGroup Type object should not be null ", newBloodGroup);
            
            List<BloodGroup> bloodGroupList = commonService.getBloodGroupList();
            assertNotNull(bloodGroupList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the Nationality records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetNationalityList() throws AkuraAppException {

        try {
            Nationality newNationality = commonService.addNationality(nationality);
            assertNotNull("Nationality Type object should not be null ", newNationality);
            
            List<Nationality> nationalityList = commonService.getNationalityList();
            assertNotNull(nationalityList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the Race records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetRaceList() throws AkuraAppException {

        try {
            Race newRace = commonService.addRace(race);
            assertNotNull("Race Type object should not be null ", newRace);
            
            List<Race> raceList = commonService.getRaceList();
            assertNotNull(raceList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the ClubSociety records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    /*
     * @Test public final void testGetClubsSocietiesList() throws AkuraAppException { try {
     * commonService.addClubSociety(clubSociety); List<ClubSociety> clubSocietieList =
     * commonService.getClubsSocietiesList(); assertNotNull(clubSocietieList.size()); } catch
     * (AkuraAppException e) { e.printStackTrace(); } }
     */
    /**
     * Test method to check load all the Sports records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    /*
     * @Test public final void testGetSportsList() throws AkuraAppException { try {
     * commonService.addSport(sport); List<Sport> sportList = commonService.getSportsList();
     * assertNotNull(sportList.size()); } catch (AkuraAppException e) { e.printStackTrace(); } }
     */
    /**
     * Test method to check load all the Sports records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    /*
     * @Test public final void testGetSportSubsList() throws AkuraAppException { try {
     * commonService.addSportSub(sportSub); List<SportSub> sportSubList = commonService.getSportSubsList();
     * assertNotNull(sportSubList.size()); } catch (AkuraAppException e) { e.printStackTrace(); } }
     */
    /**
     * Test method to check load all the PrefectTypes records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetPrefectTypeList() throws AkuraAppException {

        try {
            assertNotNull(commonService.getPrefectTypeList());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to search City recored from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSearchCity() throws AkuraAppException {

        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", newProvince);
            
            district.setProvince(newProvince);
            District newDistrict = commonService.addDistrict(district);
            assertNotNull("District Type object should not be null ", newDistrict);
            
            city.setDistrict(newDistrict);
            City oldCity = commonService.addCity(city);
            assertNotNull("City Type object should not be null ", oldCity);
            
            List<City> listCity = commonService.searchCity(oldCity.getDescription());
            assertNotNull(listCity);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to search Country recored from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSearchCountry() throws AkuraAppException {

        try {
            
            Country oldCountry = commonService.addCountry(country);
            assertNotNull("Country Type object should not be null ", oldCountry);
            
            List<Country> listCountry = commonService.searchCountry(oldCountry.getCountryName());
            assertNotNull(listCountry);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to search Province recored from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSearchProvince() throws AkuraAppException {

        try {
            Province oldProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", oldProvince);
            
            List<Province> provinceList = commonService.searchProvince(oldProvince.getDescription());
            assertNotNull(provinceList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to search District recored from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSearchDistrict() throws AkuraAppException {

        try {
            Province newProvince = commonService.addProvince(province);
            assertNotNull("Province Type object should not be null ", newProvince);
            
            district.setProvince(newProvince);
            District oldDistrict = commonService.addDistrict(district);
            assertNotNull("District Type object should not be null ", oldDistrict);
            
            List<District> districtList = commonService.searchDistrict(oldDistrict.getDescription());
            assertNotNull(districtList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.common.dao.schoolClassServiceImpl# addClass(com.virtusa.akura.api.dto.SchoolClass)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testAddClass() throws AkuraAppException {

        try {
            SchoolClass newClassInstace = commonService.addClass(classInstace);
            assertNotNull("SchoolClass Type object should not null ", newClassInstace);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.common.dao.schoolClassServiceImpl# updateClass(com.virtusa.akura.api.dto.SchoolClass)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdateClass() throws AkuraAppException {

        try {
            SchoolClass newClassInstace = commonService.addClass(classInstace);
            assertNotNull(newClassInstace);
            newClassInstace.setDescription("B");
            commonService.updateClass(newClassInstace);
            SchoolClass findClass = commonService.findClass(newClassInstace.getClassId());
            assertEquals(newClassInstace.getDescription(), findClass.getDescription());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.sms.common.dao.schoolClassServiceImpl# findClass(java.lang.Integer)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindClass() throws AkuraAppException {

        try {
            SchoolClass newClassInstace = commonService.addClass(classInstace);
            assertNotNull(newClassInstace);
            SchoolClass findClass = commonService.findClass(newClassInstace.getClassId());
            assertNotNull("SchoolClass Type object should not null ", findClass);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.sms.common.dao.schoolClassServiceImpl# getClassList()}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetClassList() throws AkuraAppException {

        try {
            SchoolClass newClassInstace = commonService.addClass(classInstace);
            assertNotNull(newClassInstace);
            List<SchoolClass> classList = commonService.getClassList();
            assertTrue(classList.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.school.service.SchoolServiceImpl# addSubject(com.virtusa.akura.api.dto.Subject)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testAddSubject() throws AkuraAppException {

        try {
            Subject newSubject = commonService.addSubject(subject);
            assertNotNull("Subject Type object should not null ", newSubject);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.school.service.SchoolServiceImpl# updateSubject(com.virtusa.akura.api.dto.Subject)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdateSubject() throws AkuraAppException {

        try {
            Subject newSubject = commonService.addSubject(subject);
            assertNotNull(newSubject);
            newSubject.setDescription("Mathematics");
            commonService.updateSubject(newSubject);
            Subject findSubject = commonService.findSubject(newSubject.getSubjectId());
            assertEquals(newSubject.getDescription(), findSubject.getDescription());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.sms.school.service.SchoolServiceImpl# getSubjectList()}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetSubjectList() throws AkuraAppException {

        try {
            Subject newSubject = commonService.addSubject(subject);
            assertNotNull(newSubject);
            List<Subject> subjectList = commonService.getSubjectList();
            assertTrue(subjectList.size() > 0);
        } catch (AkuraAppException e) {
            // delete the object.
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.sms.school.service.SchoolServiceImpl# findSubject(java.lang.Integer)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindSubject() throws AkuraAppException {

        try {
            Subject newSubject = commonService.addSubject(subject);
            assertNotNull(newSubject);
            Subject findSubject = commonService.findSubject(newSubject.getSubjectId());
            assertNotNull("Subject Type object should not null ", findSubject);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.school.service. SchoolServiceImpl#addStream(com.virtusa.akura.api.dto.Stream)}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testAddStream() throws AkuraAppException {

        try {
            Stream newStream = commonService.addStream(stream);
            assertNotNull("Stream Type Object should not null ", newStream);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.school.service. SchoolServiceImpl# updateStream(com.virtusa.akura.api.dto.Stream)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdateStream() throws AkuraAppException {

        try {
            Stream newStream = commonService.addStream(stream);
            assertNotNull(newStream);
            newStream.setDescription("Science");
            commonService.updateStream(newStream);
            Stream findStream = commonService.findStream(newStream.getStreamId());
            assertEquals(newStream.getDescription(), findStream.getDescription());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.sms.school.service.SchoolServiceImpl# findStream(java.lang.Integer)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindStream() throws AkuraAppException {

        try {
            Stream newStream = commonService.addStream(stream);
            assertNotNull(newStream);
            Stream findStream = commonService.findStream(newStream.getStreamId());
            assertNotNull("Stream Type Object should not null ", findStream);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.sms.common.dao.streamDAOImpl# getStreamList()}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetStreamList() throws AkuraAppException {

        try {
            Stream newStream = commonService.addStream(stream);
            assertNotNull(newStream);
            List<Stream> streamList = commonService.getStreamList();
            assertTrue(streamList.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method getStudyMediumList in commonServiceImpl.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    public void testGetStudyMediumList() throws AkuraAppException {

        StudyMedium newStudyMedium = studyMediumDao.save(studyMedium);
        assertNotNull("StudyMedium Type object should not be null ", newStudyMedium);
        
        List<StudyMedium> studyMediumList = commonService.getStudyMediumList();
        assertNotNull(studyMediumList);
    }
    
    /**
     * Test method getSpecialEventById in commonServiceImpl.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetSpecialEventById() throws AkuraAppException {

        commonService.addSpecialEvents(specialEvents);
        SpecialEvents persitedSpecialEvent = commonService.getSpecialEventById(specialEvents.getSpecialEventsId());
        assertNotNull(persitedSpecialEvent);
        
    }
    
    /**
     * Test method getParticipantListBySpecialEvent in commonServiceImpl.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testGetParticipantListBySpecialEvent() throws AkuraAppException {

        commonService.addSpecialEvents(specialEvents);
        List<SpecialEventsParticipation> list = commonService.getParticipantListBySpecialEvent(specialEvents);
        assertNotNull(list);
    }
    
    /**
     * Test method getSpecialEventsParticipation in commonServiceImpl.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    public void testGetSpecialEventsParticipation() throws AkuraAppException {

        commonService.addSpecialEvents(specialEvents);
        commonService.addClubSociety(clubSociety);
        commonService.addSpecialEventsParticipation(specialEventsParticipation);
        
        SpecialEventsParticipation persistedObj =
                commonService.getSpecialEventsParticipation(specialEventsParticipation
                        .getSpecialEventsParticipationId());
        assertNotNull(persistedObj);
    }
    
    /**
     * Test method getSpecialEventList in commonServiceImpl.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetSpecialEventList() throws AkuraAppException {

        commonService.addSpecialEvents(specialEvents);
        List<SpecialEvents> persitedSpecialEvent = commonService.getSpecialEventList();
        assertTrue(persitedSpecialEvent.size() > 0);
        
    }
    
    /**
     * Test method getSpecialEventParticipationListById in commonServiceImpl.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testGetSpecialEventParticipationListById() throws AkuraAppException {

        commonService.addSpecialEvents(specialEvents);
        commonService.addClubSociety(clubSociety);
        commonService.addSpecialEventsParticipation(specialEventsParticipation);
        
        List<SpecialEventsParticipation> persistedObj =
                commonService.getSpecialEventParticipationListById(specialEvents.getSpecialEventsId());
        assertTrue(persistedObj.size() > 0);
    }
    
    /**
     * Test method to check if a staffServiceCategory object was successfully added to the database.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testAddStaffServiceCategory() throws AkuraAppException {

        try {
            StaffServiceCategory newStaffServiceCategory = staffCommonService.addStaffServiceCategory(staffServiceCategory);
            assertNotNull("StaffServiceCategory Type object should not be null ", newStaffServiceCategory);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a StaffServiceCategory object was successfully updated to the database.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testEditStaffServiceCategory() throws AkuraAppException {

        String newDescription = "New Staff Serevice Category";
        
        try {
            StaffServiceCategory oldStaffServiceCategory = staffCommonService.addStaffServiceCategory(staffServiceCategory);
            assertNotNull("StaffServiceCategory Type object should not be null ", oldStaffServiceCategory);
            
            oldStaffServiceCategory.setDescription(newDescription);
            staffCommonService.editStaffServiceCategory(oldStaffServiceCategory);
            
            StaffServiceCategory newStaffServiceCategory =
                    (StaffServiceCategory) staffCommonService.findStaffServiceCategoryById(staffServiceCategory
                            .getServiceId());
            
            assertEquals(oldStaffServiceCategory.getDescription(), newStaffServiceCategory.getDescription());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to find StaffServiceCategory records from the database.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindStaffServiceCategoryById() throws AkuraAppException {

        try {
            StaffServiceCategory oldStaffServiceCategory = staffCommonService.addStaffServiceCategory(staffServiceCategory);
            assertNotNull("StaffServiceCategory Type object should not be null ", oldStaffServiceCategory);
            
            StaffServiceCategory newStaffServiceCategory =
                    (StaffServiceCategory) staffCommonService.findStaffServiceCategoryById(oldStaffServiceCategory
                            .getServiceId());
            assertNotNull("StaffStaffServiceCategory Type object should not be null ", newStaffServiceCategory);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method getStaffServiceCategoryList from the database.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    public void testGetStaffServiceCategoryList() throws AkuraAppException {

        StaffServiceCategory newStaffServiceCategory = staffServicecategoryDao.save(staffServiceCategory);
        assertNotNull("StaffServiceCategory Type object should not be null ", newStaffServiceCategory);
        
        List<StaffServiceCategory> staffServiceCategoryList = staffCommonService.getStaffServiceCategoryList();
        assertNotNull(staffServiceCategoryList);
    }
    
    /**
     * Test method to check load all the AppointmentClassification records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetAppointmentClassificationList() throws AkuraAppException {

        try {
            AppointmentClassification newAppointmentClassification =
                    staffCommonService.addAppointmentClassification(appointmentClassification);
            assertNotNull("AppointmentClassification Type object should not be null ", newAppointmentClassification);
            
            List<AppointmentClassification> appointmentClassificationList =
                    staffCommonService.getAppointmentClassificationList();
            assertNotNull(appointmentClassificationList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the AppointmentNature records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetAppointmentNatureList() throws AkuraAppException {

        try {
            AppointmentNature getAppointmentNature = staffCommonService.addAppointmentNature(appointmentNature);
            assertNotNull("AppointmentNature Type object should not be null ", getAppointmentNature);
            
            List<AppointmentNature> appointmentNatureList = staffCommonService.getAppointmentNatureList();
            assertNotNull(appointmentNatureList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the StaffLeaveType records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetStaffLeaveTypeList() throws AkuraAppException {

        try {
            StaffLeaveType newStaffLeaveType = staffCommonService.addStaffLeaveType(staffLeaveType);
            assertNotNull("StaffLeave Type object should not be null ", newStaffLeaveType);
            
            List<StaffLeaveType> staffLeaveTypeList = staffCommonService.getStaffLeaveTypeList();
            assertNotNull(staffLeaveTypeList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the Civil Status records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetCivilStatusList() throws AkuraAppException {

        try {
            CivilStatus newCivilStatus = staffCommonService.addCivilStatus(civilStatus);
            assertNotNull("CivilStatus Type object should not be null ", newCivilStatus);
            
            List<CivilStatus> civilStatusList = staffCommonService.getCivilStatusList();
            assertNotNull(civilStatusList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a staffServiceCategory object was successfully added to the database.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testAddEmploymentStatus() throws AkuraAppException {

        try {
            EmploymentStatus newEmploymentStatus = commonService.addEmploymentStatus(employmentStatus);
            assertNotNull("EmploymentStatus Type object should not be null ", newEmploymentStatus);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a EmploymentStatus object was successfully updated to the database.
     * 
     * @throws AkuraAppException - when exception has occurred.
     */
    @Test
    public final void testupdateEmploymentStatus() throws AkuraAppException {

        String newEmploymentDesctiption = "New Employment Status Description";
        
        try {
            EmploymentStatus oldEmploymentStatus = commonService.addEmploymentStatus(employmentStatus);
            assertNotNull("EmploymentStatus Type Object should not be null.", oldEmploymentStatus);
            
            oldEmploymentStatus.setDescription(newEmploymentDesctiption);
            commonService.updateEmploymentStatus(oldEmploymentStatus);
            
            EmploymentStatus newEmploymentStatus =
                    (EmploymentStatus) commonService.findEmploymentStatusById(employmentStatus.getEmploymentStatusId());
            assertEquals(oldEmploymentStatus.getDescription(), newEmploymentStatus.getDescription());
            
        } catch (AkuraAppException ex) {
            ex.printStackTrace();
        }
        
    }
    
    /**
     * Test method to find StaffServiceCategory records from the database.
     * 
     * @throws AkuraAppException  - the exception that occurred.
     */
    @Test
    public final void testfindEmploymentStatusById() throws AkuraAppException{
        
        try {
            
            EmploymentStatus oldEmploymentStatus = commonService.addEmploymentStatus(employmentStatus);
            assertNotNull("EmploymentStatus Type Object should not be null.", oldEmploymentStatus);
            
            EmploymentStatus newEmploymentStatus =
                (EmploymentStatus) commonService.findEmploymentStatusById(oldEmploymentStatus.getEmploymentStatusId());
            assertNotNull("EmploymentStatus Type Object should not be null.", newEmploymentStatus);
            
        } catch (AkuraAppException ex) {
            ex.printStackTrace();
        }
        
    }
    
    /**
     * Test method getStaffServiceCategoryList from the database.
     * 
     * @throws AkuraAppException - throw this
     */
    public final void testfindAllEmploymentStatus() throws AkuraAppException {
        
        EmploymentStatus newEmploymentStatus = employmentStatusDao.save(employmentStatus);
        assertNotNull("EmploymentStatus Type Object should not be null.",newEmploymentStatus);
        
        List<EmploymentStatus> employmentStatusList = commonService.findAllEmploymentStatus();
        assertNotNull(employmentStatusList);
        
    }
}
