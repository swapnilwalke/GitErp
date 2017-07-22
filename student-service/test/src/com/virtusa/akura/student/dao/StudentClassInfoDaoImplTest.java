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

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.student.BaseStudentTest;


/**
 * This class contains a test suit to test dao operation over Student Class Info
 * entity.
 */

public class StudentClassInfoDaoImplTest extends BaseStudentTest {
	
	/**
	 * This instance will be dependency injected by type.
	 */
	@Autowired
	private StudentClassInfoDao studentClassInfoDao;
	
	/** Date attribute for holding currentDate. */
	private Date currentDate = new Date();
	
	/** Defines Student type object. */
	private Student student;
	
	/**
	 * This instance will be dependency injected by type.
	 */

	private StudentClassInfo studentClassInfo;
	
	/**
	 * This instance will be dependency injected by type.
	 */

	private ClassGrade classGrade;
	
	
	
	/**
	 * setting up resources.
	 */
	@Before
	public void setUp(){

		student = new Student();
		student.setStudentId(1);
		
		classGrade= new ClassGrade();
		classGrade.setClassGradeId(32);
		
		studentClassInfo= new StudentClassInfo();
		studentClassInfo.setCheckMonitor(true);
		studentClassInfo.setClassGrade(classGrade);
		studentClassInfo.setModifiedTime(currentDate);
		studentClassInfo.setStudent(student);
		studentClassInfo.setYear(currentDate);
		
	}
	
	/**
	 * Test method to check if a student class info object was
	 * successfully added to the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */

	@Test
	public void testAddStudentClassInfo() throws AkuraAppException {
		try {

			StudentClassInfo newStudentClassInfo = this.studentClassInfoDao
					.save(studentClassInfo);
			assertNotNull("newStudentLeave should not be null "
					+ newStudentClassInfo);

		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Test method to check if a StudentClassInfo object was successfully
	 * updated to the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */

	@Test
	public void testUpdateStudentScholarship() throws AkuraAppException {

		try {
			StudentClassInfo newStudentClassInfo = this.studentClassInfoDao
					.save(studentClassInfo);
			assertNotNull("newStudentLeave should not be null "
					+ newStudentClassInfo);
			newStudentClassInfo.setCheckMonitor(false);
			this.studentClassInfoDao.update(newStudentClassInfo);

			StudentClassInfo newUpdateStudentClassInfo = (StudentClassInfo) this.studentClassInfoDao
					.findById(StudentClassInfo.class,
							newStudentClassInfo.getStudentClassInfoId());
			assertEquals(false, newUpdateStudentClassInfo.isCheckMonitor());

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method to check load all the StudentClassInfo records from the
	 * db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testViewAllStudentScholarship() throws AkuraAppException {

		try {
			StudentClassInfo newStudentClassInfo = this.studentClassInfoDao
					.save(studentClassInfo);
			assertNotNull("newStudentLeave should not be null "
					+ newStudentClassInfo);
			List<StudentClassInfo> newStudentClassInfoList = this.studentClassInfoDao
					.findAll(StudentClassInfo.class);
			assertNotNull(newStudentClassInfoList.size());
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
			studentClassInfoDao.delete(studentClassInfo);
		} catch (AkuraAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
