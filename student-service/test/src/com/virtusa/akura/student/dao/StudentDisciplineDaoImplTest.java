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
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentDiscipline;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.WarningLevel;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.BloodGroupDao;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.HouseDao;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.dao.MethodOfTravelDao;
import com.virtusa.akura.common.dao.NationalityDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.ReligionDao;
import com.virtusa.akura.common.dao.UserLoginDao;
import com.virtusa.akura.common.dao.WarningLevelDao;
import com.virtusa.akura.student.BaseStudentTest;

/**
 * This test class tests all the functionalities of the StudentDisciplineDaoImpl
 * class.
 * 
 * @author Virtusa Corporation
 */

public class StudentDisciplineDaoImplTest extends BaseStudentTest {

	/**
	 * This instance will be dependency injected by type.
	 */
	@Autowired
	private StudentDisciplineDao studentDisciplineDaoTarget;

	/** Defines the StudentDiscipline type object. */
	private StudentDiscipline studentDis;

	/** Defines Student type object. */
	private Student student;

	/** Defines UserLogin type object. */
	private UserLogin userLogin;

	/** Defines WarningLevel type object. */
	private WarningLevel warningLevel;

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

	/** Holds StaffDao instance. */
	@Autowired
	private UserLoginDao userLoginDao;

	/** Holds StaffDao instance. */
	@Autowired
	private WarningLevelDao warningLevelDao;

	/**
	 * Instantiate the setup method.
	 * 
	 * @throws AkuraAppException
	 *             when an error has occurred during processing.
	 */
	@Before
	public void setUp() throws AkuraAppException {

	    String randomString=String.valueOf(Math.round(Math.random()*1000));
		// Instantiate a Province object.
		province = new Province();
		province.setDescription("Westernna44"+randomString);
		province.setModifiedTime(new Date());

		province = provinceDao.save(province);
		assertNotNull(" province saved to db " + province);

		// Instantiates a District object.
		district = new District();
		district.setDescription("Gampahaaa344"+randomString);
		district.setProvince(province);
		district.setModifiedTime(new Date());

		district = districtDao.save(district);
		assertNotNull(" district saved to db " + district);

		// Instantiates a City object.
		city = new City();
		city.setDescription("Dematagodaaa44"+randomString);
		city.setDistrict(district);
		city.setModifiedTime(new Date());

		city = cityDao.save(city);
		assertNotNull(" city saved to db " + city);

		// Instantiates a Religion object.
		religion = new Religion();
		religion.setDescription("Religiona44"+randomString);
		religion.setModifiedTime(new Date());

		religion = religionDao.save(religion);
		assertNotNull(" religion saved to db " + religion);

		// Instantiates a BloodGroup object.
		bloodGroup = new BloodGroup();
		bloodGroup.setDescription("AAAAa44"+randomString);
		bloodGroup.setModifiedTime(new Date());

		bloodGroup = bloodGroupDao.save(bloodGroup);
		assertNotNull(" bloodGroup saved to db " + bloodGroup);

		// Instantiates a Nationality object.
		nationality = new Nationality();
		nationality.setDescription("Sri Lankanaa44"+randomString);
		nationality.setModifiedTime(new Date());

		nationality = nationalityDao.save(nationality);
		assertNotNull(" nationality saved to db " + nationality);

		// Instantiates a House object.
		house = new House();
		house.setName("Housea44"+randomString);
		house.setModifiedTime(new Date());

		house = houseDao.save(house);
		assertNotNull(" house saved to db " + house);

		// Instantiates a Language object.
		language = new Language();
		language.setLanguage("Sinhala1a44"+randomString);
		language.setModifiedTime(new Date());

		language = languageDao.save(language);
		assertNotNull(" language saved to db " + language);

		// Instantiates a Method of travel object.
		methodOfTravel = new MethodOfTravel();
		methodOfTravel.setTravelMethod("Buas44"+randomString);
		methodOfTravel.setModifiedTime(new Date());

		methodOfTravel = methodOfTravelDao.save(methodOfTravel);
		assertNotNull(" methodOftravel saved to db " + methodOfTravel);

		student = new Student();
		student.setAdmissionNo("A112a334"+randomString);
		student.setNameWtInitials("I Laa34");
		student.setLastName("Lolratnee ");
		student.setAddress("Gampahae");
		student.setFullName("Lol Lolrathnee");
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

		warningLevel = new WarningLevel();
		warningLevel.setDescription("desca3");
		warningLevel.setColor("strcolor");
		warningLevel.setModifiedTime(new Date());
		warningLevel = warningLevelDao.save(warningLevel);
		assertNotNull(" warningLevel saved to db " + warningLevel);
	      
		// Instantiate a UserLogin object.
        userLogin = new UserLogin();
        userLogin.setUserRoleId(1);
        userLogin.setFirstName("FirstName");
        userLogin.setLastName("LastName");
        userLogin.setEmail("ss@email.com");
        userLogin.setUsername("UserName");
        userLogin.setPassword("qqq");
        userLogin.setStatus(false);
        userLogin.setModifiedTime(new Date());
        userLogin = userLoginDao.save(userLogin);
        assertNotNull(" userLogin saved to db " + userLogin);
		
        // Instantiate a StudentDiscipline object.
		studentDis = new StudentDiscipline();
		studentDis.setUserLoginId(userLogin.getUserLoginId());
		studentDis.setStudentId(student.getStudentId());
		studentDis.setWarningLevelId(warningLevel.getWarningLevelId());
		studentDis.setComment("Comment2a3");
		studentDis.setInformedtoParent(true);
		studentDis.setDate(new Date());
		studentDis.setModifiedTime(new Date());

		
	}

	/**
	 * Test method to check if a student discipline object was successfully
	 * added to the db.
	 * 
	 * @throws AkuraAppException - the exception that occurred.
	 */
	@Test
	public void testAddStudentDiscipline() throws AkuraAppException {

		try {
			StudentDiscipline newStudentDis = this.studentDisciplineDaoTarget
					.save(studentDis);
			assertNotNull("New studentDiscipline should not be null "
					+ newStudentDis);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Test method to check if a student discipline object was successfully
	 * updated to the db.
	 * 
	 * @throws AkuraAppException - the exception that occurred.
	 */
	@Test
	public void testEditStudentDiscipline() throws AkuraAppException {

		try {
			StudentDiscipline newStudentDis = this.studentDisciplineDaoTarget
					.save(studentDis);
			assertNotNull("New student discipline can not be null "
					+ newStudentDis);
			newStudentDis.setComment("NewComment");
			this.studentDisciplineDaoTarget.update(newStudentDis);

			StudentDiscipline newStudent = (StudentDiscipline) studentDisciplineDaoTarget
					.findById(StudentDiscipline.class,
							newStudentDis.getStudentDisciplineId());
			assertEquals("NewComment", newStudent.getComment());
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method to check load all the student discipline records from the db.
	 * 
	 * @throws AkuraAppException - the exception that occurred.
	 */
	@Test
	public final void testViewAllStudentDiscipline() throws AkuraAppException {

		try {
			StudentDiscipline newStudentDis = studentDisciplineDaoTarget
					.save(studentDis);
			assertNotNull("New student discipline can not be null "
					+ newStudentDis);

			List<StudentDiscipline> studentDisList = studentDisciplineDaoTarget
					.findAll(StudentDiscipline.class);
			assertNotNull(studentDisList.size());

		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method to check load the student discipline records from the db
	 * given by student id.
	 * 
	 * @throws AkuraAppException - the exception that occurred.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public final void testViewStudentDisciplineInfoByStudent()
			throws AkuraAppException {

		try {
			StudentDiscipline newStudentDis = studentDisciplineDaoTarget
					.save(studentDis);
			assertNotNull("New student discipline can not be null "
					+ newStudentDis);

			List<StudentDiscipline> studentDislist = studentDisciplineDaoTarget
					.viewStudentDisciplineByStudentId(newStudentDis
							.getStudentId());
			Iterator itr = studentDislist.iterator();
			while (itr.hasNext()) {
				StudentDiscipline studentDisObj = (StudentDiscipline) itr
						.next();
				assertNotNull(
						"Student discipline object retrieved by student id can not be null",
						studentDisObj);
			}

		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Test method to check load the student discipline records from the db
     * given by student id and userLoginId.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testViewStudentDisciplineInfoByStudentIdAndUserId()
            throws AkuraAppException {

        try {
            StudentDiscipline newStudentDis = studentDisciplineDaoTarget
                    .save(studentDis);
            assertNotNull("New student discipline can not be null "
                    + newStudentDis);

            List<StudentDiscipline> studentDislist = 
                    studentDisciplineDaoTarget.viewStudentDisciplineByStudentIdAndUserLoginId(newStudentDis
                            .getStudentId(), userLogin.getUserLoginId());
            assertNotNull(studentDislist);
            

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Tear down method for each test case.
	 * 
	 * @throws AkuraAppException - the exception.
	 */
	@After
	public final void teardown() throws AkuraAppException {

		try {
			studentDisciplineDaoTarget.delete(studentDis);
			warningLevelDao.delete(warningLevel);
			userLoginDao.delete(userLogin);
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
			e.printStackTrace();
		}
	}
}
