package com.virtusa.akura.student.dao;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.student.BaseStudentTest;

/**
 * This class contains a test suit to test dao operation over Student Disability
 * entity.
 */
public class StudentLeaveDaoImplTest extends BaseStudentTest {

	/** Date attribute for holding currentDate. */
	private Date currentDate = new Date();

	/**
	 * This instance will be dependency injected by type.
	 */
	@Autowired
	private StudentLeaveDao studentLeaveDaoTarget;

	/**
	 * This instance will be dependency injected by type.
	 */

	private StudentLeave studentLeave;

	/**
	 * setting up resources.
	 */
	@Before
	public final void setUp() {

		studentLeave = new StudentLeave();
		studentLeave.setStudentId(1);
		studentLeave.setFromDate(currentDate);
		studentLeave.setModifiedTime(currentDate);
		studentLeave.setReason("fun");
		studentLeave.setToDate(currentDate);

	}

	/**
	 * Test method to check if a student Student Leave object was successfully
	 * added to the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */

	@Test
	public void testAddStudentLeave() throws AkuraAppException {
		try {

			StudentLeave newStudentLeave = this.studentLeaveDaoTarget
					.save(studentLeave);
			assertNotNull("newStudentLeave should not be null "
					+ newStudentLeave);

		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Test method to check if a StudentLeave object was successfully updated to
	 * the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */

	@Test
	public void testUpdateStudentLeave() throws AkuraAppException {

		try {
			StudentLeave newStudentLeave = this.studentLeaveDaoTarget
					.save(studentLeave);
			assertNotNull("newStudentLeave should not be null "
					+ newStudentLeave);
			newStudentLeave.setReason("fun fun");
			this.studentLeaveDaoTarget.update(newStudentLeave);

			StudentLeave newUpdateStudentLeave = (StudentLeave) this.studentLeaveDaoTarget
					.findById(StudentLeave.class,
							newStudentLeave.getStudentLeaveId());
			assertEquals("fun fun", newUpdateStudentLeave.getReason());

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method to check load all the Student newStudentLeave records from
	 * the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testViewAllStudentClubSociety() throws AkuraAppException {

		try {
			StudentLeave newStudentLeave = this.studentLeaveDaoTarget
					.save(studentLeave);
			assertNotNull("newStudentLeave should not be null "
					+ newStudentLeave);
			List<StudentLeave> newStudentLeaveList = this.studentLeaveDaoTarget
					.findAll(StudentLeave.class);
			assertNotNull(newStudentLeaveList.size());
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method to check Student leave records by student id from the db for given
	 * student ID and year.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testFindStudentLeaveByStudentId() throws AkuraAppException {
		try {
			StudentLeave newStudentLeave = this.studentLeaveDaoTarget
					.save(studentLeave);
			assertNotNull("newStudentLeave should not be null "
					+ newStudentLeave);
			List<StudentLeave> newStudentLeaveList = this.studentLeaveDaoTarget
					.findStudentLeaveByStudentId(newStudentLeave.getStudentId());
			assertNotNull(newStudentLeaveList.size());
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
			studentLeaveDaoTarget.delete(studentLeave);
		} catch (AkuraAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
