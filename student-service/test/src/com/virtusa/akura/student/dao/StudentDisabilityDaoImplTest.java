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
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentDisability;
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
import com.virtusa.akura.student.BaseStudentTest;

/**
 * This class contains a test suit to test dao operation over Student Disability
 * entity.
 */
public class StudentDisabilityDaoImplTest extends BaseStudentTest {

	/** Date attribute for holding currentDate. */
	private Date currentDate = new Date();

	/** student attribute for holding Student object. */
	private Student student;
	/**
	 * This instance will be dependency injected by type.
	 */
	@Autowired
	private StudentDisabilityDao studentDisabilityDaoTarget;

	/** studentDisability attribute for holding StudentDisability object. */
	private StudentDisability studentDisability;

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

	/**
	 * setting up resources.
	 * 
	 * @throws AkuraAppException
	 *             when an error has occurred during processing.
	 */
	@Before
	public final void setUp() throws AkuraAppException {	    
	    String randomString=String.valueOf(Math.round(Math.random()*1000));

		// Instantiate a Province object.
		province = new Province();
		province.setDescription("Westernna4"+randomString);
		province.setModifiedTime(new Date());

		province = provinceDao.save(province);
		assertNotNull(" province saved to db " + province);

		// Instantiates a District object.
		district = new District();
		district.setDescription("Gampahaaa34"+randomString);
		district.setProvince(province);
		district.setModifiedTime(new Date());

		district = districtDao.save(district);
		assertNotNull(" district saved to db " + district);

		// Instantiates a City object.
		city = new City();
		city.setDescription("Dematagodaaa4"+randomString);
		city.setDistrict(district);
		city.setModifiedTime(new Date());

		city = cityDao.save(city);
		assertNotNull(" city saved to db " + city);

		// Instantiates a Religion object.
		religion = new Religion();
		religion.setDescription("Religiona4"+randomString);
		religion.setModifiedTime(new Date());

		religion = religionDao.save(religion);
		assertNotNull(" religion saved to db " + religion);

		// Instantiates a BloodGroup object.
		bloodGroup = new BloodGroup();
		bloodGroup.setDescription("AAAAa4"+randomString);
		bloodGroup.setModifiedTime(new Date());

		bloodGroup = bloodGroupDao.save(bloodGroup);
		assertNotNull(" bloodGroup saved to db " + bloodGroup);

		// Instantiates a Nationality object.
		nationality = new Nationality();
		nationality.setDescription("Sri Lankanaa4"+randomString);
		nationality.setModifiedTime(new Date());

		nationality = nationalityDao.save(nationality);
		assertNotNull(" nationality saved to db " + nationality);

		// Instantiates a House object.
		house = new House();
		house.setName("Housea4"+randomString);
		house.setModifiedTime(new Date());

		house = houseDao.save(house);
		assertNotNull(" house saved to db " + house);

		// Instantiates a Language object.
		language = new Language();
		language.setLanguage("Sinhala1a4"+randomString);
		language.setModifiedTime(new Date());

		language = languageDao.save(language);
		assertNotNull(" language saved to db " + language);

		// Instantiates a Method of travel object.
		methodOfTravel = new MethodOfTravel();
		methodOfTravel.setTravelMethod("Buas4"+randomString);
		methodOfTravel.setModifiedTime(new Date());

		methodOfTravel = methodOfTravelDao.save(methodOfTravel);
		assertNotNull(" methodOftravel saved to db " + methodOfTravel);

		student = new Student();
		student.setAdmissionNo("A112a33"+randomString);
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

		studentDisability = new StudentDisability();
		studentDisability.setStudentId(student.getStudentId());
		studentDisability.setAllergiesMedicalNotes("ok");
		studentDisability.setBehaviourDifficulties("ok");
		studentDisability.setDisabilityInfo("no");
		studentDisability.setDyslexia("no");
		studentDisability.setFits("fit");
		studentDisability.setHearingImpairment("no");
		studentDisability.setModifiedTime(currentDate);
		studentDisability.setOtherInfo("no");
		studentDisability.setPhysicalDisabilities("no");
		studentDisability.setSpeechDifficulties("no");
		studentDisability.setVisualImpairment("ok");

	}

	/**
	 * Test method to check if a Student Disability object was successfully
	 * added to the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */

	@Test
	public void testAddStudentDissability() throws AkuraAppException {
		try {

			StudentDisability newStudentDisability = this.studentDisabilityDaoTarget
					.save(studentDisability);
			assertNotNull("Student Dissability should not be null "
					+ newStudentDisability);

		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Test method to check if a Student Disability object was successfully
	 * updated to the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */

	@Test
	public void testEditStudentDisability() throws AkuraAppException {

		try {

			StudentDisability newStudentDisability = this.studentDisabilityDaoTarget
					.save(studentDisability);
			assertNotNull("Student Dissability should not be null "
					+ newStudentDisability);
			newStudentDisability.setBehaviourDifficulties("not good");
			this.studentDisabilityDaoTarget.update(newStudentDisability);
			StudentDisability newUpdateStudentDisability = (StudentDisability) this.studentDisabilityDaoTarget
					.findById(StudentDisability.class,
							newStudentDisability.getStudentId());
			assertEquals("not good",
					newUpdateStudentDisability.getBehaviourDifficulties());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method to check load all the Student Disability records from the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testViewAllStudentDisability() throws AkuraAppException {

		try {
			StudentDisability newStudentDisability = this.studentDisabilityDaoTarget
					.save(studentDisability);
			assertNotNull("Student Dissability should not be null "
					+ newStudentDisability);
			List<StudentDisability> newStudentDisabilityList = this.studentDisabilityDaoTarget
					.findAll(StudentDisability.class);
			assertNotNull(newStudentDisabilityList.size());
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tear down method for each test case.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */

	@After
	public final void teardown() throws AkuraAppException {

		try {

			studentDisabilityDaoTarget.delete(studentDisability);
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
		} catch (AkuraAppException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
