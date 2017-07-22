
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
import com.virtusa.akura.api.dto.ClubSociety;
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
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClubSociety;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.BloodGroupDao;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.ClubSocietyDao;
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
import com.virtusa.akura.student.BaseStudentTest;

/**
 * This class contains a test suit to test dao opeartion over Club Society enitity.
 */
public class StudentClubSocietyDaoImplTest extends BaseStudentTest {
    
    /** Date attribute for holding currentDate. */
    private Date currentDate = new Date();
    
    /**
     * studentClubSociety object is to test studentClubSocietyDao functionalities.
     */
    private StudentClubSociety studentClubSociety;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StudentClubSocietyDao studentClubSocietyDaoTarget;
    
    /**
     * Instance of a Student entity.
     */
    private Student student;
    
    /**
     * Instance of a ClubSociety entity.
     */
    private ClubSociety clubSociety;
    
    /**
     * Instance of a Position entity.
     */
    private Position position;
    
    /** Holds Religion instance. */
    private Religion religion;
    
    /** Holds province instance. */
    private Province province;
    
    /** Holds city instance. */
    private City city;
    
    /** Holds district instance. */
    private District district;
    
    /** Represents an instance of BloodGroup. */
    private BloodGroup bloodGroup;
    
    /** Represents an instance of Nationality. */
    private Nationality nationality;
    
    /** Holds house instance of {@link House}. */
    private House house;
    
    /** Holds language instance of {@link Language}. */
    private Language language;
    
    /** Holds methodOfTravel instance of {@link MethodOfTravel}. */
    private MethodOfTravel methodOfTravel;
    
    /** Holds Province instance. */
    @Autowired
    private ProvinceDao provinceDao;
    
    /** Holds District instance. */
    @Autowired
    private DistrictDao districtDao;
    
    /** Holds City instance. */
    @Autowired
    private CityDao cityDao;
    
    /** Holds Religion instance. */
    @Autowired
    private ReligionDao religionDao;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private BloodGroupDao bloodGroupDao;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private NationalityDao nationalityDao;
    
    /** Holds houseDAO instance of {@link HouseDao}. */
    @Autowired
    private HouseDao houseDao;
    
    /** Holds languageDao instance of {@link LanguageDao}. */
    @Autowired
    private LanguageDao languageDao;
    
    /** Holds methodOfTravelDAO instance of {@link MethodOfTravelDao}. */
    @Autowired
    private MethodOfTravelDao methodOfTravelDao;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private StudentDao studentDao;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private ClubSocietyDao clubSocietyDao;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private PositionDao positionDao;
    
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
     * setting up resources.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {
    
        String randomString = String.valueOf(Math.round(Math.random() * 1000));
        
        clubSociety = new ClubSociety();
        clubSociety.setName("VstrName");
        clubSociety.setDescription("VstrDescript" + randomString);
        clubSociety.setModifiedTime(currentDate);
        
        clubSociety = clubSocietyDao.save(clubSociety);
        assertNotNull(" clubSociety saved to db " + clubSociety);
        
        position = new Position();
        position.setModifiedTime(currentDate);
        position.setDescription("VstrDescription" + randomString);
        position = positionDao.save(position);
        assertNotNull(" position saved to db " + position);
        
        // Instantiate a Province object.
        province = new Province();
        province.setDescription("VWesternna4" + randomString);
        province.setModifiedTime(new Date());
        
        province = provinceDao.save(province);
        assertNotNull(" province saved to db " + province);
        
        // Instantiates a District object.
        district = new District();
        district.setDescription("VGampahaaa34" + randomString);
        district.setProvince(province);
        district.setModifiedTime(new Date());
        
        district = districtDao.save(district);
        assertNotNull(" district saved to db " + district);
        
        // Instantiates a City object.
        city = new City();
        city.setDescription("VDematagodaaa4" + randomString);
        city.setDistrict(district);
        city.setModifiedTime(new Date());
        
        city = cityDao.save(city);
        assertNotNull(" city saved to db " + city);
        
        // Instantiates a Religion object.
        religion = new Religion();
        religion.setDescription("VReligiona4" + randomString);
        religion.setModifiedTime(new Date());
        
        religion = religionDao.save(religion);
        assertNotNull(" religion saved to db " + religion);
        
        // Instantiates a BloodGroup object.
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("VAAAAa4" + randomString);
        bloodGroup.setModifiedTime(new Date());
        
        bloodGroup = bloodGroupDao.save(bloodGroup);
        assertNotNull(" bloodGroup saved to db " + bloodGroup);
        
        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("VSri Lankanaa4" + randomString);
        nationality.setModifiedTime(new Date());
        
        nationality = nationalityDao.save(nationality);
        assertNotNull(" nationality saved to db " + nationality);
        
        // Instantiates a House object.
        house = new House();
        house.setName("VHousea4" + randomString);
        house.setModifiedTime(new Date());
        
        house = houseDao.save(house);
        assertNotNull(" house saved to db " + house);
        
        // Instantiates a Language object.
        language = new Language();
        language.setLanguage("VSinhala1a4" + randomString);
        language.setModifiedTime(new Date());
        
        language = languageDao.save(language);
        assertNotNull(" language saved to db " + language);
        
        // Instantiates a Method of travel object.
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("VBuas4" + randomString);
        methodOfTravel.setModifiedTime(new Date());
        
        methodOfTravel = methodOfTravelDao.save(methodOfTravel);
        assertNotNull(" methodOftravel saved to db " + methodOfTravel);
        
        student = new Student();
        student.setAdmissionNo("A112a33");
        student.setNameWtInitials("I Laa3");
        student.setLastName("Lolratne ");
        student.setAddress("Gampaha");
        student.setFullName("Lol Lolrathne");
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
        
        student = studentDao.save(student);
        assertNotNull("New Student should not be null " + student);
        
        Byte status = new Byte("1");
        studentClubSociety = new StudentClubSociety();
        studentClubSociety.setStudent(student);
        studentClubSociety.setClubSociety(clubSociety);
        studentClubSociety.setMembershipNo("5");
        studentClubSociety.setModifiedTime(currentDate);
        studentClubSociety.setPosition(position);
        studentClubSociety.setYear(currentDate);
        studentClubSociety.setStatus(status);
        
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
        specialEventsParticipation.setClubSociety(clubSociety);
        specialEventsParticipation.setModifiedTime(new Date());
        specialEventsParticipation.setSpecialEvents(specialEvents);
        specialEventsParticipation = specialEventsParticipationDao.save(specialEventsParticipation);
        assertNotNull(specialEventsParticipation);
        
    }
    
    /**
     * Test method to check if a student Club Society object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    
    @Test
    public void testAddStudentClubSociety() throws AkuraAppException {
    
        try {
            
            StudentClubSociety newStudentClubSociety = studentClubSocietyDaoTarget.save(studentClubSociety);
            assertNotNull("newStudentClubSociety should not be null " + newStudentClubSociety);
            
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check if a StudentClubSociety object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    
    @Test
    public void testEditStudentClubSociety() throws AkuraAppException {
    
        try {
            
            StudentClubSociety newStudentClubSociety = studentClubSocietyDaoTarget.save(studentClubSociety);
            assertNotNull("newStudentClubSociety should not be null " + newStudentClubSociety);
            newStudentClubSociety.setMembershipNo("3");
            this.studentClubSocietyDaoTarget.update(newStudentClubSociety);
            
            StudentClubSociety newUpdateStudentClubSociety =
                    (StudentClubSociety) this.studentClubSocietyDaoTarget.findById(StudentClubSociety.class,
                            newStudentClubSociety.getStudentClubSocietyId());
            assertEquals("3", newUpdateStudentClubSociety.getMembershipNo());
            
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the Student Club Society records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testViewAllStudentClubSociety() throws AkuraAppException {
    
        try {
            StudentClubSociety newStudentClubSociety = studentClubSocietyDaoTarget.save(studentClubSociety);
            assertNotNull("newStudentClubSociety should not be null " + newStudentClubSociety);
            List<StudentClubSociety> newStudentClubSocietyList =
                    this.studentClubSocietyDaoTarget.findAll(StudentClubSociety.class);
            assertNotNull(newStudentClubSocietyList.size());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to get Student Club Society records from the db for given student ID and year.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetClubSocietyListForStudent() throws AkuraAppException {
    
        try {
            StudentClubSociety newStudentClubSociety = studentClubSocietyDaoTarget.save(studentClubSociety);
            assertNotNull("newStudentClubSociety should not be null " + newStudentClubSociety);
            List<StudentClubSociety> newStudentClubSocietyList =
                    this.studentClubSocietyDaoTarget.getClubSocietyListForStudent(studentClubSociety.getStudent()
                            .getStudentId(), currentDate);
            assertNotNull(newStudentClubSocietyList.size());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to get Student Club Society records from the db for given student ID.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetClubSocietiesListForStudent() throws AkuraAppException {
    
        try {
            StudentClubSociety newStudentClubSociety = studentClubSocietyDaoTarget.save(studentClubSociety);
            assertNotNull("newStudentClubSociety should not be null " + newStudentClubSociety);
            List<StudentClubSociety> newStudentClubSocietyList =
                    this.studentClubSocietyDaoTarget.getClubSocietiesListForStudent(studentClubSociety.getStudent()
                            .getStudentId());
            assertNotNull(newStudentClubSocietyList.size());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to get Student Club Society records from the db for given student ID.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindStudentClubSociety() throws AkuraAppException {
    
        try {
            StudentClubSociety newStudentClubSociety = studentClubSocietyDaoTarget.save(studentClubSociety);
            assertNotNull("newStudentClubSociety should not be null " + newStudentClubSociety);
            List<StudentClubSociety> newStudentClubSocietyList =
                    this.studentClubSocietyDaoTarget.findStudentClubSociety(studentClubSociety.getStudent()
                            .getStudentId(), studentClubSociety.getClubSociety().getClubSocietyId(), currentDate);
            assertNotNull(newStudentClubSocietyList.size());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for getStudentListforClubSociety in studentService.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetStudentListforClubSociety() throws AkuraAppException {
    
        try {
            StudentClubSociety newStudentClubSociety = studentClubSocietyDaoTarget.save(studentClubSociety);
            assertNotNull("newStudentClubSociety should not be null " + newStudentClubSociety);
            List<StudentClubSociety> newStudentClubSocietyList =
                    studentClubSocietyDaoTarget.getStudentListforClubSociety(specialEventsParticipation
                            .getClubSociety().getClubSocietyId(), specialEventsParticipation.getSpecialEvents()
                            .getDate());
            
            assertNotNull(newStudentClubSocietyList.size());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    
    @After
    public final void teardown() throws AkuraAppException {
    
        try {
            specialEventsParticipationDao.delete(specialEventsParticipation);
            specialEventsDao.delete(specialEvents);
            participantCategoryDao.delete(participantCategory);
            studentClubSocietyDaoTarget.delete(studentClubSociety);
            clubSocietyDao.delete(clubSociety);
            positionDao.delete(position);
            studentDao.delete(student);
            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);
            bloodGroupDao.delete(bloodGroup);
            nationalityDao.delete(nationality);
            religionDao.delete(religion);
            houseDao.delete(house);
            methodOfTravelDao.delete(methodOfTravel);
            languageDao.delete(language);
        } catch (AkuraAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
