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

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.PrefectType;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentPrefect;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.BloodGroupDao;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.HouseDao;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.dao.MethodOfTravelDao;
import com.virtusa.akura.common.dao.NationalityDao;
import com.virtusa.akura.common.dao.PrefectTypeDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.ReligionDao;
import com.virtusa.akura.student.BaseStudentTest;

/**
 * This test class tests all the functionalities of the WarningLevelDaoImpl
 * class.
 * 
 * @author Virtusa Corporation
 */

public class StudentPrefectDaoImplTest extends BaseStudentTest {

	/**
	 * This instance will be dependency injected by type.
	 */
	@Autowired
	private StudentPrefectDao studentPrefectDao;

	/** studentPrefect attribute for holding StudentPrefect object. */
	private StudentPrefect studentPrefect;

	/** prefectType attribute for holding PrefectType object. */

	private PrefectType prefectType;

	/** prefectType attribute for holding PrefectType object. */

	private PrefectType newPrefectType;

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

	/** Defines Student type object. */
	private Student student;

	/** Date attribute for holding currentDate. */
	private Date currentDate = new Date();

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
	private PrefectTypeDao prefectTypeDao;
	
	/** attribute for holding year. */
	private static final int YEAR = 2011;

	/**
	 * Instantiate the setup method.
	 * 
	 * @throws Exception
	 *             when an error has occurred during processing.
	 */
	@Before
	public void setUp() throws Exception {
	    String randomString=String.valueOf(Math.round(Math.random()*1000));
	    
		// Instantiate a Province object.
		province = new Province();
		province.setDescription("Westernn1111"+randomString);
		province.setModifiedTime(new Date());

		province = provinceDao.save(province);
		assertNotNull(" province saved to db " + province);

		// Instantiates a District object.
		district = new District();
		district.setDescription("Gampaha1111"+randomString);
		district.setProvince(province);
		district.setModifiedTime(new Date());

		district = districtDao.save(district);
		assertNotNull(" district saved to db " + district);

		// Instantiates a City object.
		city = new City();
		city.setDescription("Dematagoda1111"+randomString);
		city.setDistrict(district);
		city.setModifiedTime(new Date());

		city = cityDao.save(city);
		assertNotNull(" city saved to db " + city);

		// Instantiates a Religion object.
		religion = new Religion();
		religion.setDescription("Religion1111"+randomString);
		religion.setModifiedTime(new Date());

		religion = religionDao.save(religion);
		assertNotNull(" religion saved to db " + religion);

		// Instantiates a BloodGroup object.
		bloodGroup = new BloodGroup();
		bloodGroup.setDescription("AAAA"+randomString);
		bloodGroup.setModifiedTime(new Date());

		bloodGroup = bloodGroupDao.save(bloodGroup);
		assertNotNull(" bloodGroup saved to db " + bloodGroup);

		// Instantiates a Nationality object.
		nationality = new Nationality();
		nationality.setDescription("Sri Lankan111"+randomString);
		nationality.setModifiedTime(new Date());

		nationality = nationalityDao.save(nationality);
		assertNotNull(" nationality saved to db " + nationality);

		// Instantiates a House object.
		house = new House();
		house.setName("House"+randomString);
		house.setModifiedTime(new Date());

		house = houseDao.save(house);
		assertNotNull(" house saved to db " + house);

		// Instantiates a Language object.
		language = new Language();
		language.setLanguage("Sinhala111"+randomString);
		language.setModifiedTime(new Date());

		language = languageDao.save(language);
		assertNotNull(" language saved to db " + language);

		// Instantiates a Method of travel object.
		methodOfTravel = new MethodOfTravel();
		methodOfTravel.setTravelMethod("Bus"+randomString);
		methodOfTravel.setModifiedTime(new Date());

		methodOfTravel = methodOfTravelDao.save(methodOfTravel);
		assertNotNull(" methodOftravel saved to db " + methodOfTravel);

		// Instantiates student object.
		student = new Student();
		student.setAdmissionNo("A1123"+randomString);
		student.setNameWtInitials("I L");
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

		prefectType = new PrefectType();
		prefectType.setDescription("strDescription"+randomString);
		prefectType.setModifiedTime(new Date());

		prefectType = prefectTypeDao.save(prefectType);
		assertNotNull("Prefect Type should not be null " + prefectType);

		newPrefectType = new PrefectType();
		newPrefectType.setDescription("strDescriptionNew"+randomString);
		newPrefectType.setModifiedTime(new Date());

		newPrefectType = prefectTypeDao.save(newPrefectType);
		assertNotNull("Prefect Type should not be null " + newPrefectType);

		studentPrefect = new StudentPrefect();
		studentPrefect.setStudent(student);
		studentPrefect.setPrefectType(prefectType);
		studentPrefect.setYear(currentDate);
		studentPrefect.setModifiedTime(currentDate);

	}

	/**
	 * Test method to check if a Add Student Perfect object was successfully
	 * added to the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public void testAddStudentPrefect() throws AkuraAppException {

		try {

			StudentPrefect newStudentPrefect = this.studentPrefectDao
					.save(studentPrefect);
			assertNotNull("New StudentPrefect object can not be null"
					+ newStudentPrefect);
		} catch (AkuraAppException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Test method to check if Student Perfect object was successfully updated
	 * to the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */

	@Test
	public void testEditStudentPrefect() throws AkuraAppException {

		try {
			StudentPrefect newStudentPrefect = this.studentPrefectDao
					.save(studentPrefect);
			assertNotNull("New StudentPrefect object can not be null"
					+ newStudentPrefect);
			newStudentPrefect.setPrefectType(newPrefectType);
			this.studentPrefectDao.update(newStudentPrefect);
			StudentPrefect newUpdateStudentPrefect = (StudentPrefect) studentPrefectDao
					.findById(StudentPrefect.class,
							newStudentPrefect.getStudentPrefectId());
			assertEquals(newPrefectType.getPrefectTypeId(),
					newUpdateStudentPrefect.getPrefectType().getPrefectTypeId());

		} catch (AkuraAppException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Test method to check load all the Student Prefect records from the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testViewAllStudentPrefect() throws AkuraAppException {

		try {

			StudentPrefect newStudentPrefect = studentPrefectDao
					.save(studentPrefect);
			assertNotNull("New StudentPrefect object can not be null"
					+ newStudentPrefect);

			List<StudentPrefect> studentPrefectList = studentPrefectDao
					.findAll(StudentPrefect.class);
			assertNotNull(studentPrefectList.size());

		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method to check Prefect records from the db for given
	 * student ID and year.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testGetStudentPrefectDetailsByStudentId() throws AkuraAppException{
		try {
			StudentPrefect newStudentPrefect = studentPrefectDao
					.save(studentPrefect);
			assertNotNull("New StudentPrefect object can not be null"
					+ newStudentPrefect);

			List<StudentPrefect> studentPrefectList = studentPrefectDao
					.getStudentPrefectDetailsByStudentId(newStudentPrefect
							.getStudent().getStudentId(), YEAR);
			assertNotNull(studentPrefectList.size());

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
			studentPrefectDao.delete(studentPrefect);
			prefectTypeDao.delete(prefectType);
			prefectTypeDao.delete(newPrefectType);
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
