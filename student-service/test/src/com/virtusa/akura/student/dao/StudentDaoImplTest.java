package com.virtusa.akura.student.dao;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.student.BaseStudentTest;

/**
 * This class contains a test suit to test dao opeartion over Student enitity.
 */
public class StudentDaoImplTest extends BaseStudentTest {

	/** Date attribute for holding currentDate. */
	private Date currentDate = new Date();

	/**
	 * This instance will be dependency injected by type.
	 */
	@Autowired
	private StudentDao studentDao;

	/** student object is to test studentDao functionalities. */
	private Student student;

	/** admission no constant used to test update on student. */
	private static final String ADMISSION_NO_FOR_UPDATE = "1111132";

	/** admission no constant used to test update on student. */
	private static final String ADMISSION_NO = "11111"+String.valueOf(Math.round(Math.random()*1000));

	/**
	 * setting up resources.
	 */
	@Before
	public final void setUp() {

		student = new Student();
		student.setAdmissionNo(ADMISSION_NO);
		student.setNameWtInitials("I L");
		student.setLastName("Lolratne ");
		student.setAddress("Gampaha");
		student.setFullName("Lol Lolrathne");
		student.setModifiedTime(currentDate);

		student.setLanguageId(1);
		student.setReligionId(2);
		student.setTravelId(1);
		student.setBloodGroupId(2);
		student.setHouseId(1);

		student.setCityId(1);
		student.setNationalityId(1);
		student.setGender('M');
		student.setIsOldBoy(false);
		student.setDateOfBirth(currentDate);
		student.setAdmissionDate(currentDate);
		student.setFirstSchoolDay(currentDate);

		student.setNationalIdNo("123564");
		student.setTemporaryAddress("abcdef");
		student.setMobileNo("14566");
		student.setResidenceNo("1458");
		student.setEmail("hhh@bbb.com");

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

	}

	/**
	 * test student save update retrive and delete dao functionalities as a
	 * flow.
	 */
	@Test
	public void testStudentCRUDFunctionalities() {

		Student studentObj = null;

		try {
			studentObj = studentDao.save(student);
			assertNotNull("save method return null value", studentObj);

		} catch (AkuraAppException ex) {
			assertFalse("exception occured in student save", true);
		}

		try {
			studentObj.setAdmissionNo(ADMISSION_NO_FOR_UPDATE);
			studentDao.update(studentObj);

		} catch (AkuraAppException ex) {
			assertFalse("exception occured in student update", true);
		}

		try {

			Student retrivedStudent = (Student) studentDao.findById(
					Student.class, studentObj.getStudentId());
			assertNotNull("student find by id is failed", retrivedStudent);
			assertEquals("student updation failed", ADMISSION_NO_FOR_UPDATE,
					retrivedStudent.getAdmissionNo());

		} catch (AkuraAppException ex) {
			assertFalse("exception occured in student retrival", true);
		}

		try {
			studentDao.delete(student);

			Student retrivedStudent = (Student) studentDao.findById(
					Student.class, studentObj.getStudentId());
			assertNull("Student delete failed", retrivedStudent);

		} catch (AkuraAppException ex) {
			assertFalse("exception occured in student delete", true);
		}

	}

	/**
	 * test student save update retrive and delete dao functionalities as a
	 * flow.
	 * 
	 * @throws AkuraAppException
	 *             - AkuraAppException.
	 */
	@Test
	public void testCheckAdmissionNoIsExist() throws AkuraAppException {

		Student studentObj = null;
		try {
			studentObj = studentDao.save(student);
			assertNotNull("save method return null value", studentObj);

			assertEquals("check Admission No failed", student.getStudentId(),
					studentDao.findStudentIdForAdmissionNo(ADMISSION_NO));
			studentDao.delete(studentObj);

			assertEquals("check Admission No failed", 0,
					studentDao.findStudentIdForAdmissionNo(ADMISSION_NO));

		} catch (AkuraAppException ex) {
			assertFalse("exception occured in testCheckAdmissionNo", true);
		}

	}
}
