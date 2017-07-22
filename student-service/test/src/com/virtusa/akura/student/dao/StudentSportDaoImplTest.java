/*
 * © 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND 

 * PROPRIETARY INFORMATION The information contained herein (the 

 * 'Proprietary Information') is highly confidential and proprietary to and 

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published, 

 * communicated, disclosed or divulged to any person, firm, corporation or 

 * other legal entity, directly or indirectly, without the prior written 

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.student.dao;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.ParticipantCategory;
import com.virtusa.akura.api.dto.Position;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.dto.SportCategory;
import com.virtusa.akura.api.dto.SportSub;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClubSociety;
import com.virtusa.akura.api.dto.StudentSport;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.BloodGroupDao;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.HouseDao;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.dao.MethodOfTravelDao;
import com.virtusa.akura.common.dao.NationalityDao;
import com.virtusa.akura.common.dao.ParticipantCategoryDao;
import com.virtusa.akura.common.dao.PositionDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.ReligionDao;
import com.virtusa.akura.common.dao.SpecialEventsDao;
import com.virtusa.akura.common.dao.SpecialEventsParticipationDao;
import com.virtusa.akura.common.dao.SportCategoryDao;
import com.virtusa.akura.common.dao.SportDao;
import com.virtusa.akura.common.dao.SportSubDao;
import com.virtusa.akura.student.BaseStudentTest;

/**
 * This test class, tests all the persistence related functionality for the StudentSport domain object.
 * 
 * @author Virtusa Corporation
 */
public class StudentSportDaoImplTest extends BaseStudentTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StudentSportDao studentSportDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StudentDao studentDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SportCategoryDao sportCategoryDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SportDao sportDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SportSubDao sportSubDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private PositionDao positionDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ProvinceDao provinceDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private DistrictDao districtDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private CityDao cityDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private MethodOfTravelDao methodOfTravelDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private HouseDao houseDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ReligionDao religionDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private BloodGroupDao bloodGroupDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private NationalityDao nationalityDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private LanguageDao languageDaoTarget;
    
    /**
     * Instance of a StudentSport entity.
     */
    private StudentSport studentSport;
    
    /**
     * Instance of a Student entity.
     */
    private Student student;
    
    /**
     * Instance of a SportCategory entity.
     */
    private SportCategory sportCategory;
    
    /**
     * Instance of a Position entity.
     */
    private Position position;
    
    /**
     * Instance of a Sport entity.
     */
    private Sport sport;
    
    /**
     * Instance of a SportSub entity.
     */
    private SportSub sportSub;
    
    /**
     * language {@link Language}.
     */
    private Language language;
    
    /**
     * Defines nationality.
     */
    private Nationality nationality;
    
    /**
     * Defines BloodGroup.
     */
    private BloodGroup bloodGroup;
    
    /**
     * Defines a Religion object.
     */
    private Religion religion;
    
    /**
     * Defines a House object.
     */
    private House house;
    
    /**
     * methodOfTravel {@link MethodOfTravel}.
     */
    private MethodOfTravel methodOfTravel;
    
    /**
     * Defines a City object.
     */
    private Province province;
    
    /**
     * Defines a City object.
     */
    private District district;
    
    /**
     * Defines a City object.
     */
    private City city;
    
    /** Represents an instance of SpecialEventsParticipation. */
    private SpecialEventsParticipation specialEventsParticipation;
    
    /** Injects an instance of StudentDao. */
    @Autowired
    private SpecialEventsParticipationDao specialEventsParticipationDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ParticipantCategoryDao participantCategoryDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SpecialEventsDao specialEventsDao;
    
    /**
     * Represents an instance of participantCategory.
     */
    private ParticipantCategory participantCategory;
    
    /**
     * Represents an instance of SpecialEvents.
     */
    private SpecialEvents specialEvents;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws AkuraAppException SMS Exception.
     */
    @Before
    public void setUp() throws AkuraAppException {
    
        // Create a Sport object.
        sport = new Sport();
        sport.setDescription("KKarate111");
        sport.setModifiedTime(new Date());
        
        // Create a SportSub object.
        sportSub = new SportSub();
        sportSub.setDescription("KFirst 12-Team111");
        sportSub.setModifiedTime(new Date());
        
        // Create a Position object.
        position = new Position();
        position.setDescription("KPosDescription111");
        position.setModifiedTime(new Date());
        
        // Instantiates a Province object.
        province = new Province();
        province.setDescription("KWestern1");
        province.setModifiedTime(new Date());
        
        // Create a methodOfTravel object.
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("KBy School Transport1");
        methodOfTravel.setModifiedTime(new Date());
        
        // Create a house object.
        house = new House();
        house.setColour("KRed1");
        house.setDescription("KRed House1");
        house.setName("KPonchu1");
        house.setModifiedTime(new Date());
        
        // Create a religion object.
        religion = new Religion();
        religion.setDescription("KBuddhism1");
        religion.setModifiedTime(new Date());
        
        // Instantiates a BloodGroup object.
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("KA1");
        bloodGroup.setModifiedTime(new Date());
        
        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("KSri Lankan1");
        nationality.setModifiedTime(new Date());
        
        // Instantiates a Language object
        language = new Language();
        language.setLanguage("KEnglish1");
        language.setModifiedTime(new Date());
        
        // first add the sport
        Sport newSport = sportDaoTarget.save(sport);
        assertNotNull("new Sport should not be null " + newSport);
        
        // next add the sport sub category - example - Under-18
        SportSub newSportSub = sportSubDaoTarget.save(sportSub);
        assertNotNull("new Sport Sub should not be null " + newSportSub);
        // adding SportCategory object
        sportCategory = new SportCategory(newSport, newSportSub);
        sportCategory.setModifiedTime(new Date());
        SportCategory newSportCategory = sportCategoryDaoTarget.save(sportCategory);
        assertNotNull("new Sport Category should not be null " + newSportCategory);
        
        // adding Position object
        position = positionDaoTarget.save(position);
        assertNotNull("new Position should not be null" + position);
        
        // adding Province object
        Province newProvince = provinceDaoTarget.save(province);
        assertNotNull("new Province should not be null" + newProvince);
        
        // adding a District object.
        district = new District();
        district.setDescription("KColombo1");
        district.setProvince(newProvince);
        district.setModifiedTime(new Date());
        
        District newDistrict = districtDaoTarget.save(district);
        assertNotNull("new district should not be null" + newDistrict);
        
        // adding a City object.
        city = new City();
        city.setDescription("KDematagoda1");
        city.setModifiedTime(new Date());
        city.setDistrict(newDistrict);
        
        City newCity = cityDaoTarget.save(city);
        assertNotNull("new city should not be null" + newCity);
        
        // Adding a methodOfTravel object.
        MethodOfTravel newMethodOfTravel = methodOfTravelDaoTarget.save(methodOfTravel);
        assertNotNull("new MethodOfTravel should not be null" + newMethodOfTravel);
        
        // Adding a house object.
        House newHouse = houseDaoTarget.save(house);
        assertNotNull("new house should not be null" + newHouse);
        
        // // Adding a religion object.
        Religion newReligion = religionDaoTarget.save(religion);
        assertNotNull("new Religion should not be null" + newReligion);
        
        // // Adding a BloodGroup object.
        BloodGroup newBloodGroup = bloodGroupDaoTarget.save(bloodGroup);
        assertNotNull("new BloodGroup should not be null" + newBloodGroup);
        
        // // Adding a Nationality object.
        Nationality newNationality = nationalityDaoTarget.save(nationality);
        assertNotNull("new Nationality should not be null" + newNationality);
        
        // // Adding a Language object
        Language newLanguage = languageDaoTarget.save(language);
        assertNotNull("new Language should not be null" + newLanguage);
        
        // Adding a Student
        
        student = new Student();
        student.setAddress("sms");
        student.setAdmissionDate(new Date());
        student.setAdmissionNo("Ksms");
        
        student.setCityId(newCity.getCityId());
        student.setTravelId(newMethodOfTravel.getTravelId());
        student.setFullName("Ksms");
        student.setGender('F');
        student.setHouseId(newHouse.getHouseId());
        student.setReligionId(newReligion.getReligionId());
        student.setNameWtInitials("Ksms");
        student.setBloodGroupId(newBloodGroup.getBloodGroupId());
        student.setNationalityId(newNationality.getNationalityId());
        student.setLastName("Ksms");
        student.setModifiedTime(new Date());
        student.setLanguageId(newLanguage.getLanguageId());
        student.setDateOfBirth(new Date());
        student.setFirstSchoolDay(new Date());
        
        student.setPreviousSchoolFromDate(new Date());
        student.setPreviousSchoolName("Bandaranayake College Gampaha");
        student.setPreviousSchoolPassedGrade("2");
        student.setPreviousSchoolReasonForLeave("pricipal sucks");
        student.setPreviousSchoolStudiedGrade("2");
        
        student.setPreviousSchoolToDate(new Date());
        
        student.setEmergencyContactEmailAddress("bla@bla.com");
        student.setEmergencyContactFullName("EFullName");
        student.setEmergencyContactLastName("ELastName");
        student.setEmergencyContactMobileNo("1234");
        student.setEmergencyContactNameWtInitials("EC");
        student.setEmergencyContactOfficeNo("adcc");
        student.setEmergencyContactRelationship("Parent");
        student.setEmergencyContactResidenceNo("12364");
        
        Student newStudent = studentDaoTarget.save(student);
        assertNotNull("new Student should not be null " + newStudent);
        
        participantCategory = new ParticipantCategory();
        participantCategory.setDescription("desc4rnRef");
        participantCategory.setModifiedTime(new Date());
        participantCategory = participantCategoryDao.save(participantCategory);
        assertNotNull(participantCategory);
        
        specialEvents = new SpecialEvents();
        specialEvents.setDate(new Date());
        specialEvents.setDescription("des4cref");
        specialEvents.setModifiedTime(new Date());
        specialEvents.setName("nae4ef");
        specialEvents.setParticipantCategory(participantCategory);
        specialEvents = specialEventsDao.save(specialEvents);
        assertNotNull(specialEvents);
        
        specialEventsParticipation = new SpecialEventsParticipation();
        specialEventsParticipation.setSportCategory(sportCategory);
        specialEventsParticipation.setModifiedTime(new Date());
        specialEventsParticipation.setSpecialEvents(specialEvents);
        specialEventsParticipation = specialEventsParticipationDao.save(specialEventsParticipation);
        assertNotNull(specialEventsParticipation);
        
        // Adding a StudentSport object.
        
        studentSport = new StudentSport();
        studentSport.setStudent(newStudent);
        studentSport.setSportCategory(newSportCategory);
        
        studentSport.setPosition(position);
        studentSport.setStatus(null);
        studentSport.setYear(new Date());
        // studentSport.setSportColour(1);
        
    }
    
    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl# save(com.virtusa.sms.api.dto.BaseDomain)}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveStudentSport() throws AkuraAppException {
    
        try {
            
            StudentSport newStudentSport = studentSportDaoTarget.save(studentSport);
            assertNotNull("new StudentSport should not be null " + newStudentSport);
            
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a StudentSport object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateStudentSport() throws AkuraAppException {
    
        try {
            
            StudentSport newSport = studentSportDaoTarget.save(studentSport);
            newSport.setPosition(position);
            studentSportDaoTarget.update(studentSport);
            StudentSport updateSport =
                    (StudentSport) studentSportDaoTarget.findById(StudentSport.class, studentSport.getStudentSportId());
            assertEquals(studentSport.getStudentSportId(), updateSport.getStudentSportId());
            
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test method for
     * {@link com.virtusa.sms.student.dao.StudentCoCurricularActivityDao#getSportsListForStudent(StudentSport)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testViewAllStudentSports() throws AkuraAppException {
    
        try {
            int studentId = 1;
            Date year = null;
            List<StudentSport> lst = this.studentSportDaoTarget.getSportsListForStudent(studentId, year);
            assertNotNull("StudentSports List not null", lst);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * @Test public void testSaveOrUpdateStudentSport() throws AkuraAppException { try { List<StudentSport>
     * studentSportList = new ArrayList<StudentSport>(); studentSportList.add(studentSport);
     * studentSportDaoTarget.saveOrUpdateAll(studentSportList); List<StudentSport> newStudentSportsList =
     * studentSportDaoTarget.findAll(StudentSport.class); assertTrue(newStudentSportsList.size() > 0); } catch
     * (AkuraAppException e) { e.printStackTrace(); } }
     */
    /**
     * Test method to check find the newStudentSport records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindStudentSport() throws AkuraAppException {
    
        try {
            
            StudentSport newStudentSport = studentSportDaoTarget.save(studentSport);
            assertNotNull("New StudentParent should not be null " + newStudentSport);
            
            StudentSport findStudentSport =
                    (StudentSport) studentSportDaoTarget.findById(StudentSport.class,
                            newStudentSport.getStudentSportId());
            assertNotNull("Find StudentParent should not be null ", findStudentSport);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for getStudentListforSportsCategory in studentService.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetStudentListforSportsCategory() throws AkuraAppException {
    
        try {
            StudentSport newStudentSport = studentSportDaoTarget.save(studentSport);
            assertNotNull("newStudentSport should not be null " + newStudentSport);
            List<StudentSport> newStudentSportList =
                    studentSportDaoTarget.getStudentListforSportsCategory(specialEventsParticipation.getSportCategory()
                            .getSportCategoryId(), specialEventsParticipation.getSpecialEvents().getDate());
            
            assertNotNull(newStudentSportList.size());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * @Test public void testFindAllStudentSport() throws AkuraAppException { try { StudentSport
     * newStudentSport = studentSportDaoTarget.save(studentSport);
     * assertNotNull("New StudentParent should not be null " + newStudentSport); List<StudentSport>
     * studentSportList = studentSportDaoTarget.findAll(StudentSport.class);
     * assertNotNull(studentSportList.size()); } catch (AkuraAppException e) { e.printStackTrace(); } }
     */
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        specialEventsParticipationDao.delete(specialEventsParticipation);
        specialEventsDao.delete(specialEvents);
        participantCategoryDao.delete(participantCategory);
        studentSportDaoTarget.delete(studentSport);
        sportCategoryDaoTarget.delete(sportCategory);
        sportSubDaoTarget.delete(sportSub);
        sportDaoTarget.delete(sport);
        
        studentDaoTarget.delete(student);
        
        cityDaoTarget.delete(city);
        districtDaoTarget.delete(district);
        provinceDaoTarget.delete(province);
        
        positionDaoTarget.delete(position);
        methodOfTravelDaoTarget.delete(methodOfTravel);
        houseDaoTarget.delete(house);
        religionDaoTarget.delete(religion);
        bloodGroupDaoTarget.delete(bloodGroup);
        nationalityDaoTarget.delete(nationality);
        languageDaoTarget.delete(language);
        
    }
    
}
