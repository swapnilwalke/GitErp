/*
 * < ï¿½KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.springframework.transaction.annotation.Transactional;

import com.virtusa.akura.api.dto.Achievement;
import com.virtusa.akura.api.dto.AssignmentMarkView;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClassWiseStudentsSubjectsDTO;
import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.dto.ExamAdmission;
import com.virtusa.akura.api.dto.ExamMark;
import com.virtusa.akura.api.dto.ExamResults;
import com.virtusa.akura.api.dto.FaithLifeRating;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.MarkingFlag;
import com.virtusa.akura.api.dto.MediumWiseClassSubjectAverageView;
import com.virtusa.akura.api.dto.PastStudent;
import com.virtusa.akura.api.dto.Seminar;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentAnnualGradeRank;
import com.virtusa.akura.api.dto.StudentAssignmentMark;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentClubSociety;
import com.virtusa.akura.api.dto.StudentDisability;
import com.virtusa.akura.api.dto.StudentDiscipline;
import com.virtusa.akura.api.dto.StudentGradeSubjectRankView;
import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.dto.StudentPrefect;
import com.virtusa.akura.api.dto.StudentScholarship;
import com.virtusa.akura.api.dto.StudentSearchCritiria;
import com.virtusa.akura.api.dto.StudentSeminar;
import com.virtusa.akura.api.dto.StudentSpecialEventParticipationView;
import com.virtusa.akura.api.dto.StudentSport;
import com.virtusa.akura.api.dto.StudentSubTermMark;
import com.virtusa.akura.api.dto.StudentSubTermMarkDTO;
import com.virtusa.akura.api.dto.StudentSubjectAverageViewDTO;
import com.virtusa.akura.api.dto.StudentTemporaryLeave;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.dto.StudentTermMarkPerformance;
import com.virtusa.akura.api.dto.StudentTermMarksRank;
import com.virtusa.akura.api.dto.StudentsGradeRankView;
import com.virtusa.akura.api.dto.SubjectAverageTermMarks;
import com.virtusa.akura.api.dto.SuspendStudent;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.enums.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.AlreadyOnLeaveException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.api.exception.InvalidRejoinDateException;
import com.virtusa.akura.api.exception.StudentClubAndSocietyAchievementDeleteException;
import com.virtusa.akura.api.exception.SuspendRejoinReasonValidationException;
import com.virtusa.akura.common.dao.ClassGradeDao;
import com.virtusa.akura.common.dao.HolidayDao;
import com.virtusa.akura.common.dao.MarkingFlagDao;
import com.virtusa.akura.common.dao.SeminarDao;
import com.virtusa.akura.common.dao.TermDao;
import com.virtusa.akura.common.dao.UserDao;
import com.virtusa.akura.common.dao.UserLoginDao;
import com.virtusa.akura.student.dao.AchievementDao;
import com.virtusa.akura.student.dao.AssignmentMarkViewDao;
import com.virtusa.akura.student.dao.ClassWiseStudentsSubjectsViewDao;
import com.virtusa.akura.student.dao.ExamAdmissionDao;
import com.virtusa.akura.student.dao.ExamMarksDao;
import com.virtusa.akura.student.dao.ExamResultsDao;
import com.virtusa.akura.student.dao.FaithLifeRatingDao;
import com.virtusa.akura.student.dao.MediumWiseClassSubjectAverageViewDao;
import com.virtusa.akura.student.dao.PastStudentDao;
import com.virtusa.akura.student.dao.StudentAnnualGradeRankDao;
import com.virtusa.akura.student.dao.StudentAssignmentMarkDao;
import com.virtusa.akura.student.dao.StudentClassInfoDao;
import com.virtusa.akura.student.dao.StudentClubSocietyDao;
import com.virtusa.akura.student.dao.StudentDao;
import com.virtusa.akura.student.dao.StudentDisabilityDao;
import com.virtusa.akura.student.dao.StudentDisciplineDao;
import com.virtusa.akura.student.dao.StudentGradeRankViewDao;
import com.virtusa.akura.student.dao.StudentGradeSubjectRankViewDao;
import com.virtusa.akura.student.dao.StudentLeaveDao;
import com.virtusa.akura.student.dao.StudentParentDao;
import com.virtusa.akura.student.dao.StudentPrefectDao;
import com.virtusa.akura.student.dao.StudentScholarshipDao;
import com.virtusa.akura.student.dao.StudentSeminarDao;
import com.virtusa.akura.student.dao.StudentSpecialEventParticipationViewDao;
import com.virtusa.akura.student.dao.StudentSportDao;
import com.virtusa.akura.student.dao.StudentSubTermMarkDao;
import com.virtusa.akura.student.dao.StudentSubTermMarkViewDao;
import com.virtusa.akura.student.dao.StudentSubjectAverageViewDao;
import com.virtusa.akura.student.dao.StudentSuspendDao;
import com.virtusa.akura.student.dao.StudentTemporaryLeaveDao;
import com.virtusa.akura.student.dao.StudentTermMarkDao;
import com.virtusa.akura.student.dao.StudentTermMarkPerformanceDao;
import com.virtusa.akura.student.dao.StudentTermMarkRankViewDao;
import com.virtusa.akura.student.dao.StudentTermMarkViewDao;
import com.virtusa.akura.student.dao.SubjectAverageTermMarksDao;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.HolidayUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;

/**
 * Student Service Implementation.
 */
public class StudentServiceImpl implements StudentService {
    
    /** Holds the temporary leave state. */
    private static final int TEMPORARY_STATE = 4;
    
    /** Holds the error message key. */
    private static final String ERROR_MESSAGE_EPMTY_FEILD = "STUDENT.REJOIN.REASON.EMPTY.MESSAGE";
    
    /** Holds the error message key. */
    private static final String ERROR_MESSAGE_STUDENT_REJOIN = "STUDENT.REJOIN.JOINDATE.ERROR";
    
    /** Holds the error message key. */
    private static final String ERROR_MESSAGE_STUDENT_REJOIN_SAMEDAY = "STUDENT.REJOIN.SAMEDATE.ERROR";
    
    /** Holds the AkuraErrorMsg property file name. */
    private static final String AKURA_ERROR_MSG_PROPERTY = "AkuraErrorMsg";
    
    /** Holds the error message key. */
    private static final String ERROR_MESSAGE_ALREADY_ONLEAVE = "STUDENT.SUSPEND.ALREADY.ON.LEAVE";
    
    /** attribute for holding string. */
    private static final String START_YEAR = "-01-01";
    
    /** attribute for holding string. */
    private static final String TEMP_LEAVE = "Temporary leave";
    
    /** attribute for holding string. */
    private static final String SUSPEND = "REF.UI.SUSPEND.REASON";
    
    /** attribute for holding error key "STU.UI.STUDENT.CLUBANDSOCIETY.ACHIEVEMENT.DELETE.ERROR". */
    private static final String CLUB_AND_SOCIETY_DELETE_ERROR =
            "STU.UI.STUDENT.CLUBANDSOCIETY.ACHIEVEMENT.DELETE.ERROR";
    
    /** studentDao attribute for holding schoolDao. */
    private StudentDao studentDao;
    
    /** studentDisciplineDaoTarget attribute for studentDisciplineDao. */
    private StudentDisciplineDao studentDisciplineDaoTarget;
    
    /** studentSportDAO attribute for studentSportDAO. */
    private StudentSportDao studentSportDaoTarget;
    
    /** studentClubSocietyDAO attribute for studentClubSocietyDAO. */
    private StudentClubSocietyDao studentClubSocietyDaoTarget;
    
    /** studentClassInfoDAO attribute for holding StudentClassInfoDao. */
    private StudentClassInfoDao studentClassInfoDAO;
    
    /** This instance will be dependency injected by type. */
    private StudentTermMarkDao studentTermMarkDao;
    
    /** This instance will be dependency injected by type. */
    private StudentTermMarkViewDao studentTermMarkViewDao;
    
    /** StudentSeminarDao attribute for access the StudentSeminar table. */
    private StudentSeminarDao studentSeminarDao;
    
    /** SeminarDao attribute for access the Seminar table. */
    private SeminarDao seminarDao;
    
    /**
     * studentSpecialEventParticipationViewDao attribute for holding StudentSpecialEventParticipationViewDao.
     */
    private StudentSpecialEventParticipationViewDao studentSpecialEventParticipationViewDao;
    
    /** The student sub term mark view dao. */
    private StudentSubTermMarkViewDao studentSubTermMarkViewDao;
    
    /** studentTermMarkRankViewDao attribute for StudentTermMarkRankViewDao. */
    private StudentTermMarkRankViewDao studentTermMarkRankViewDao;
    
    /**
     * subjectAverageTermMarksDao attribute for holding SubjectAverageTermMarksDao object.
     */
    private SubjectAverageTermMarksDao subjectAverageTermMarksDao;
    
    /**
     * studentGradeSubjectRankViewDao attribute for holding StudentGradeSubjectRankViewDao.
     */
    private StudentGradeSubjectRankViewDao studentGradeSubjectRankViewDao;
    
    /** The student assignment mark view dao. */
    private ClassWiseStudentsSubjectsViewDao classWiseStudentsSubjectsViewDao;
    
    /** Represents an instance of ExamMarksDao. */
    private ExamMarksDao examMarksDao;
    
    /** Represents an instance of StudentAssignmentMarkDao. */
    private StudentAssignmentMarkDao studentAssignmentMarkDao;
    
    /** Represents an instance of type ExamAdmissionDao. */
    private ExamAdmissionDao examAdmissionDao;
    
    /** Represents an instance of type ExamResultsDao. */
    private ExamResultsDao examResultsDao;
    
    /** Represents an instance of type AssignmentMarkViewDao. */
    private AssignmentMarkViewDao assignmentMarkViewDao;
    
    /** markingFlagDao attribute for holding MarkingFlagDao. */
    private MarkingFlagDao markingFlagDao;
    
    /** termDao attribute for holding TermDao. */
    private TermDao termDao;
    
    /** classGradeDao attribute for holding ClassGradeDao. */
    private ClassGradeDao classGradeDao;
    
    /**
     * studentTermMarkPerformanceDao attribute for holding StudentTermMarkPerformanceDao.
     */
    private StudentTermMarkPerformanceDao studentTermMarkPerformanceDao;
    
    /** studentTempLeaveDao attribute for holding StudentTempLeaveDao. */
    private StudentTemporaryLeaveDao studentTempLeaveDao;
    
    /** Holds instance of a UserLoginDao. */
    private UserLoginDao userLoginDao;
    
    /** Holds instance of a StudentSuspendDao. */
    private StudentSuspendDao studentSuspendDao;
    
    /** Holds instance of a PastStudentDao. */
    private PastStudentDao pastStudentDao;
    
    /** Holds instance of a StudentParentDao. */
    private StudentParentDao studentParentDaoTarget;
    
    /** Holds instance of a StudentSuspendDao. */
    private StudentDisciplineDao studentDisciplineDao;
    
    /** studentTempLeaveDao attribute for holding StudentTempLeaveDao. */
    private HolidayDao holidayDao;
    
    /**
     * Sets an instance of HolidayDao.
     * 
     * @param holidayDaoObj the holidayDao to set
     */
    public void setHolidayDao(HolidayDao holidayDaoObj) {

        this.holidayDao = holidayDaoObj;
    }
    
    /**
     * Sets an instance of StudentDisciplineDao.
     * 
     * @param studentDisciplineDaoObj the studentDisciplineDao to set
     */
    public void setStudentDisciplineDao(StudentDisciplineDao studentDisciplineDaoObj) {

        this.studentDisciplineDao = studentDisciplineDaoObj;
    }
    
    /** key to hold string of year end date. */
    private static final String END_DATE = "-12-31";
    
    /** key to hold string of year start date. */
    private static final String START_DATE = "-01-01";
    
    /**
     * Registers the studentSuspendDao to inject with student service.
     * 
     * @param studentSuspendDaoObj the UserLoginDao to set
     */
    public void setStudentSuspendDao(StudentSuspendDao studentSuspendDaoObj) {

        this.studentSuspendDao = studentSuspendDaoObj;
    }
    
    /**
     * Sets an instance of PastStudentDao.
     * 
     * @param pastStudentDaoObj the pastStudentDao to set
     */
    public void setPastStudentDao(PastStudentDao pastStudentDaoObj) {

        this.pastStudentDao = pastStudentDaoObj;
    }
    
    /**
     * Sets an instance of studentParentDaoTarget.
     * 
     * @param studentParentDaoTargetObj the studentParentDao to set
     */
    public void setStudentParentDao(StudentParentDao studentParentDaoTargetObj) {

        this.studentParentDaoTarget = studentParentDaoTargetObj;
    }
    
    /**
     * /** Registers the userLoginDao to inject with staff service.
     * 
     * @param userLoginDaoObj the UserLoginDao to set
     */
    public void setUserLoginDao(UserLoginDao userLoginDaoObj) {

        this.userLoginDao = userLoginDaoObj;
    }
    
    /**
     * Sets an instance of StudentTempLeaveDao.
     * 
     * @param studentTempLeaveDaoObj the StudentTemporaryLeaveDao to set
     */
    public void setStudentTempLeaveDao(StudentTemporaryLeaveDao studentTempLeaveDaoObj) {

        this.studentTempLeaveDao = studentTempLeaveDaoObj;
    }
    
    /**
     * Sets an instance of StudentTermMarkPerformanceDao.
     * 
     * @param studentTermMarkPerformanceDaoObj the studentTermMarkPerformanceDao to set
     */
    public void setStudentTermMarkPerformanceDao(StudentTermMarkPerformanceDao studentTermMarkPerformanceDaoObj) {

        this.studentTermMarkPerformanceDao = studentTermMarkPerformanceDaoObj;
    }
    
    /**
     * Sets an instance of AssignmentMarkViewDao.
     * 
     * @param assignmentMarkViewDaoObj the assignmentMarkViewDao to set
     */
    public void setAssignmentMarkViewDao(AssignmentMarkViewDao assignmentMarkViewDaoObj) {

        this.assignmentMarkViewDao = assignmentMarkViewDaoObj;
    }
    
    /**
     * Injects an instance of ExamResultsDao.
     * 
     * @param examResultsDaoRef - an instance of ExamResultsDao.
     */
    public void setExamResultsDao(ExamResultsDao examResultsDaoRef) {

        this.examResultsDao = examResultsDaoRef;
    }
    
    /**
     * Injects an instance of ExamAdmissionDao.
     * 
     * @param objExamAdmissionDao - an instance of ExamAdmissionDao.
     */
    public void setExamAdmissionDao(ExamAdmissionDao objExamAdmissionDao) {

        this.examAdmissionDao = objExamAdmissionDao;
    }
    
    /**
     * Injects an intance of ExamMarksDao.
     * 
     * @param objExamMarksDao - an intance of ExamMarksDao.
     */
    public void setExamMarksDao(final ExamMarksDao objExamMarksDao) {

        this.examMarksDao = objExamMarksDao;
    }
    
    /**
     * Sets the studentSeminarDao dao.
     * 
     * @param studentSeminarDaoArg StudentSeminarDao.
     */
    public void setStudentSeminarDao(StudentSeminarDao studentSeminarDaoArg) {

        this.studentSeminarDao = studentSeminarDaoArg;
    }
    
    /**
     * Sets the SeminarDao dao.
     * 
     * @param seminarDaoArg SeminarDao.
     */
    public void setSeminarDao(SeminarDao seminarDaoArg) {

        seminarDao = seminarDaoArg;
    }
    
    /**
     * Sets the Student term mark view dao.
     * 
     * @param dao studentTermMarkViewDao.
     */
    public void setStudentTermMarkViewDao(StudentTermMarkViewDao dao) {

        this.studentTermMarkViewDao = dao;
    }
    
    /** StudentDisabilityDao attribute for holding studentDisabilityDao. */
    private StudentDisabilityDao studentDisabilityDao;
    
    /** studentTermMarkList to hold returned results list. */
    private List<StudentTermMark> studentTermMarkList;
    
    /** average to hold average of a single student per term. */
    private float average;
    
    /** StudentSubTermMarkDao attribute. */
    private StudentSubTermMarkDao studentSubTermMarkDao;
    
    /** AchievementDao attribute for holding AchievementDaoTarget. */
    private AchievementDao achievementDaoTarget;
    
    /** StudentLeaveDao attribute for holding studentLeaveDao. */
    private StudentLeaveDao studentLeaveDao;
    
    /** StudentPrefectDao to hold a single instance of student prefects. */
    private StudentPrefectDao studentPrefectDao;
    
    /** StudentScholarshipDao to hold a single instance of student scholarships. */
    private StudentScholarshipDao studentScholarshipDao;
    
    /**
     * faithLifeRatingDaoTarget to hold a single instance of student FaithLifeRating.
     */
    private FaithLifeRatingDao faithLifeRatingDaoTarget;
    
    /** Represents an instance of type StudentSubjectAverageViewDao. */
    private StudentSubjectAverageViewDao studentSubjectAverageViewDao;
    
    /** Represents an instance of type StudentGradeRankViewDao. */
    private StudentGradeRankViewDao studentGradeRankViewDao;
    
    /**
     * studentAnnualGradeRankDao attribute for holding StudentAnnualGradeRankDao.
     */
    private StudentAnnualGradeRankDao studentAnnualGradeRankDao;
    
    /** holds user dao. */
    private UserDao userDao;
    
    /**
     * mediumWiseClassSubjectAverageViewDao attribute for holding MediumWiseClassSubjectAverageViewDao.
     */
    private MediumWiseClassSubjectAverageViewDao mediumWiseClassSubjectAverageViewDao;
    
    /**
     * Sets the student assignment mark dao.
     * 
     * @param objStudentAssignmentMarkDao the studentAssignmentMarkDao to set
     */
    public void setStudentAssignmentMarkDao(StudentAssignmentMarkDao objStudentAssignmentMarkDao) {

        this.studentAssignmentMarkDao = objStudentAssignmentMarkDao;
    }
    
    /**
     * Sets the class wise students subjects view dao.
     * 
     * @param objClassWiseStudentsSubjViewDao the objClassWiseStudentsSubjViewDao to set
     */
    public void setClassWiseStudentsSubjectsViewDao(ClassWiseStudentsSubjectsViewDao objClassWiseStudentsSubjViewDao) {

        this.classWiseStudentsSubjectsViewDao = objClassWiseStudentsSubjViewDao;
    }
    
    /**
     * Sets an instance of StudentSubjectAverageViewDao.
     * 
     * @param studentSubjectAverageViewDaoVal - an instance of StudentSubjectAverageViewDao..
     */
    public void setStudentSubjectAverageViewDao(StudentSubjectAverageViewDao studentSubjectAverageViewDaoVal) {

        this.studentSubjectAverageViewDao = studentSubjectAverageViewDaoVal;
    }
    
    /**
     * @param faithLifeRatingDaoTargetObj the faithLifeRatingDaoTarget to set
     */
    public void setFaithLifeRatingDaoTarget(FaithLifeRatingDao faithLifeRatingDaoTargetObj) {

        this.faithLifeRatingDaoTarget = faithLifeRatingDaoTargetObj;
    }
    
    /**
     * Set the studentLeaveDao instance to inject the dao.
     * 
     * @param studentLeaveDaoValue the studentLeaveDao to set
     */
    public void setStudentLeaveDao(StudentLeaveDao studentLeaveDaoValue) {

        this.studentLeaveDao = studentLeaveDaoValue;
    }
    
    /**
     * @param studentPrefectDaoVal the StudentPrefectDao to set
     */
    public void setStudentPrefectDao(StudentPrefectDao studentPrefectDaoVal) {

        this.studentPrefectDao = studentPrefectDaoVal;
    }
    
    /**
     * @param studentScholarshipDaoVal the StudentScholarshipDao to set
     */
    public void setStudentScholarshipDao(StudentScholarshipDao studentScholarshipDaoVal) {

        this.studentScholarshipDao = studentScholarshipDaoVal;
    }
    
    /**
     * @param achievementDaoTargetObj the achievementDaoTarget to set
     */
    public void setAchievementDaoTarget(AchievementDao achievementDaoTargetObj) {

        this.achievementDaoTarget = achievementDaoTargetObj;
    }
    
    /**
     * @param studentSportDaoVal the studentSportDAO to set
     */
    public void setStudentSportDaoTarget(StudentSportDao studentSportDaoVal) {

        this.studentSportDaoTarget = studentSportDaoVal;
    }
    
    /**
     * @param clubSociety the studentClubSocietyDAO to set
     */
    public void setStudentClubSocietyDaoTarget(StudentClubSocietyDao clubSociety) {

        this.studentClubSocietyDaoTarget = clubSociety;
    }
    
    /**
     * @param daoStudentClassInfoDAO the studentClassInfoDAO to set
     */
    public void setStudentClassInfoDAO(StudentClassInfoDao daoStudentClassInfoDAO) {

        this.studentClassInfoDAO = daoStudentClassInfoDAO;
    }
    
    /**
     * @param studentSubTermMarkDaoVal the StudentSubTermMarkDao to set
     */
    public void setStudentSubTermMarkDao(StudentSubTermMarkDao studentSubTermMarkDaoVal) {

        this.studentSubTermMarkDao = studentSubTermMarkDaoVal;
    }
    
    /**
     * Setter method for studentSpecialEventParticipationViewDao.
     * 
     * @param vStudentSpecialEventParticipationViewDao object
     */
    public void setStudentSpecialEventParticipationViewDao(
            StudentSpecialEventParticipationViewDao vStudentSpecialEventParticipationViewDao) {

        this.studentSpecialEventParticipationViewDao = vStudentSpecialEventParticipationViewDao;
    }
    
    /**
     * Setter method for studentTermMarkRankViewDao.
     * 
     * @param vStudentTermMarkRankViewDao student term mark view dao
     */
    public void setStudentTermMarkRankViewDao(StudentTermMarkRankViewDao vStudentTermMarkRankViewDao) {

        this.studentTermMarkRankViewDao = vStudentTermMarkRankViewDao;
    }
    
    /**
     * Setter method for subjectAverageTermMarksDao.
     * 
     * @param vSubjectAverageTermMarksDao object
     */
    public void setSubjectAverageTermMarksDao(SubjectAverageTermMarksDao vSubjectAverageTermMarksDao) {

        this.subjectAverageTermMarksDao = vSubjectAverageTermMarksDao;
    }
    
    /**
     * Setter method for studentGradeRankViewDao.
     * 
     * @param vStudentGradeRankViewDao object
     */
    public void setStudentGradeRankViewDao(StudentGradeRankViewDao vStudentGradeRankViewDao) {

        this.studentGradeRankViewDao = vStudentGradeRankViewDao;
    }
    
    /**
     * Setter method for studentAnnualGradeRankDao.
     * 
     * @param vStudentAnnualGradeRankDao object
     */
    public void setStudentAnnualGradeRankDao(StudentAnnualGradeRankDao vStudentAnnualGradeRankDao) {

        this.studentAnnualGradeRankDao = vStudentAnnualGradeRankDao;
    }
    
    /**
     * Setter method to set MarkingFlagDao object.
     * 
     * @param vMarkingFlagDao object
     */
    public void setMarkingFlagDao(MarkingFlagDao vMarkingFlagDao) {

        this.markingFlagDao = vMarkingFlagDao;
    }
    
    /**
     * Setter method to set TermDao object.
     * 
     * @param vTermDao object;
     */
    public void setTermDao(TermDao vTermDao) {

        this.termDao = vTermDao;
    }
    
    /**
     * Setter method to set ClassSubjectAverageViewDao object.
     * 
     * @param vMediumWiseClassSubjectAverageViewDao object
     */
    public void setMediumWiseClassSubjectAverageViewDao(
            MediumWiseClassSubjectAverageViewDao vMediumWiseClassSubjectAverageViewDao) {

        this.mediumWiseClassSubjectAverageViewDao = vMediumWiseClassSubjectAverageViewDao;
    }
    
    /**
     * set the userDao.
     * 
     * @param userDaoVal the user dao to set
     */
    public void setUserDao(UserDao userDaoVal) {

        this.userDao = userDaoVal;
    }
    
    /**
     * Service to modify a student object.
     * 
     * @param student student object to modify.
     * @throws AkuraAppException throw SMS exception.
     * @throws AkuraAppException
     */
    public void modifyStudent(Student student) throws AkuraAppException {

        this.studentDao.update(student);
    }
    
    /**
     * Service method to retrive student.
     * 
     * @param studentId - studentId
     * @return student obj for given studentId.
     * @throws AkuraAppException SMS Exceptions
     */
    @Override
    public Student findStudent(int studentId) throws AkuraAppException {

        return (Student) studentDao.findById(Student.class, studentId);
        
    }
    
    /**
     * Service method is to search student.
     * 
     * @param critiria - StudentSearchCritiria object to be search.
     * @return List(Student).
     * @throws AkuraAppException SMS Exceptions
     */
    @Override
    public List<Object> studentSearch(StudentSearchCritiria critiria) throws AkuraAppException {

        return studentDao.studentSearch(critiria);
    }
    
    /**
     * Service method for student advance search.
     * 
     * @param critiria - StudentSearchCritiria object to be search.
     * @return List(Student).
     * @throws AkuraAppException SMS Exceptions
     */
    @Override
    public List<Object> studentAdvanceSearch(StudentSearchCritiria critiria) throws AkuraAppException {

        return studentDao.studentAdvanceSearch(critiria);
    }
    
    /**
     * Setter method for Student Dao.
     * 
     * @param studentDaoVal - StudentDao
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public void setStudentDao(StudentDao studentDaoVal) throws AkuraAppException {

        studentDao = studentDaoVal;
    }
    
    // --------Student Character related methods------------//
    /**
     * Setter method for studentDisciplineDaoTarget.
     * 
     * @param studentDisDaoTarget - StudentDisciplineDao type object
     * @throws AkuraAppException SMS Exception.
     */
    public void setstudentDisciplineDaoTarget(StudentDisciplineDao studentDisDaoTarget) throws AkuraAppException {

        studentDisciplineDaoTarget = studentDisDaoTarget;
    }
    
    /**
     * Adding student discipline information.
     * 
     * @param sd specifies the student discipline object
     * @return StudentDiscipline type object
     * @throws AkuraAppException SMS Exception.
     */
    @Override
    public StudentDiscipline addStudentDisciplineInfo(StudentDiscipline sd) throws AkuraAppException {

        return studentDisciplineDaoTarget.save(sd);
    }
    
    /**
     * Editing student discipline information.
     * 
     * @param sd specifies the student discipline object
     * @throws AkuraAppException SMS Exception.
     */
    @Override
    public void editStudentDisciplineInfo(StudentDiscipline sd) throws AkuraAppException {

        studentDisciplineDaoTarget.update(sd);
    }
    
    /**
     * Deleting student discipline information.
     * 
     * @param sd specifies the student discipline object
     * @throws AkuraAppException SMS Exception.
     */
    @Override
    public void deleteStudentDisciplineInfo(StudentDiscipline sd) throws AkuraAppException {

        studentDisciplineDaoTarget.delete(sd);
    }
    
    /**
     * Viewing student discipline information for a given student.
     * 
     * @param studDisId specifies the student discipline id, defined by an integer
     * @return List of StudentDiscipline type objects
     * @throws AkuraAppException SMS Exception.
     */
    @Override
    public StudentDiscipline viewStudentDisciplineInfoById(int studDisId) throws AkuraAppException {

        return (StudentDiscipline) studentDisciplineDaoTarget.findById(StudentDiscipline.class, studDisId);
    }
    
    /**
     * Viewing student discipline information for a given student.
     * 
     * @param studentId specifies the student discipline id, defined by an integer
     * @return List of StudentDiscipline type objects
     * @throws AkuraAppException SMS Exception.
     */
    @Override
    public List<StudentDiscipline> viewStudentDisciplineInfo(int studentId) throws AkuraAppException {

        return studentDisciplineDaoTarget.viewStudentDisciplineByStudentId(studentId);
        
    }

    
    
    /**
     * Viewing student discipline information for a given student.
     *
     * @param studentId specifies the student discipline id, defined by an integer
     * @return List of StudentDiscipline type objects
     * @throws AkuraAppException SMS Exception.
     */
    /*    @Override
  public List<Object> viewStudentDisciplineDetailsInfo(int studentId) throws AkuraAppException {
	
        return studentDisciplineDaoTarget.viewStudentDisciplineDetailsByStudentId(studentId);

    }

*/
    /**
     * Viewing student discipline information for a given student.
     * 
     * @param studentId specifies the student discipline id, defined by an integer
     * @param userLoginId specifies the user login id, defined by an integer
     * @return List of StudentDiscipline type objects
     * @throws AkuraAppException SMS Exception.
     */
    @Override
    public List<StudentDiscipline> viewStudentDisciplineInfoByUserLoginId(int studentId, int userLoginId)
            throws AkuraAppException {

        return studentDisciplineDaoTarget.viewStudentDisciplineByStudentIdAndUserLoginId(studentId, userLoginId);
        
    }
    
    /**
     * Display all Student Discipline information.
     * 
     * @return List of StudentDisciplineCategory type objects
     * @throws AkuraAppException SMS Exception.
     */
    @Override
    public List<StudentDiscipline> viewAllStudentDisciplineInfo() throws AkuraAppException {

        return studentDisciplineDaoTarget.findAll(StudentDiscipline.class);
    }
    
    // ----------End of Student Character related methods--------//
    
    /**
     * service to add a sport Activity to student.
     * 
     * @param studentSport the StudentSport object to set.
     * @throws AkuraAppException - The exception details that occurred when adding a list of StudentSport
     *         instances.
     */
    @Override
    public void addStudentSport(StudentSport studentSport) throws AkuraAppException {

        studentSportDaoTarget.save(studentSport);
        
    }
    
    /**
     * service to edit a sport Activity of a student.
     * 
     * @param studentSport the StudentSport object to edit.
     * @throws AkuraAppException - The exception details that occurred when editing a list of StudentSport
     *         instances.
     */
    @Override
    public void editStudentSport(StudentSport studentSport) throws AkuraAppException {

        studentSportDaoTarget.update(studentSport);
        
    }
    
    /**
     * service to delete a sport Activity of a student.
     * 
     * @param studentSportId the id of the StudentSport object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a list of StudentSport
     *         instances.
     */
    @Override
    public void deleteStudentSport(int studentSportId) throws AkuraAppException {

        StudentSport studSport = (StudentSport) studentSportDaoTarget.findById(StudentSport.class, studentSportId);
        studentSportDaoTarget.delete(studSport);
        
    }
    
    /**
     * service to get the list of sportActivities of a given student.
     * 
     * @param studentId the id of the Student.
     * @param year the Date of the StudentSport activity.
     * @return List {@link StudentSport}
     * @throws AkuraAppException - The exception details that occurred when retrieve a list of StudentSport
     *         instances.
     */
    @Override
    public List<StudentSport> getSportsListForStudent(int studentId, Date year) throws AkuraAppException {

        return studentSportDaoTarget.getSportsListForStudent(studentId, year);
    }
    
    /**
     * service to find a studentSport by studentSportId.
     * 
     * @param studSportId the id of the StudentSport activity.
     * @return StudentSport {@link StudentSport}
     * @throws AkuraAppException - The exception details that occurred when retrieve StudentSport instances.
     */
    @Override
    public StudentSport findStudentSportObjById(int studSportId) throws AkuraAppException {

        return (StudentSport) studentSportDaoTarget.findById(StudentSport.class, studSportId);
    }
    
    /**
     * service to find a studentSport.
     * 
     * @param studentId the id of the StudentSport activity.
     * @param sportCategoryId the id of the sportCategory instance.
     * @param year the date of the StudentSport activity.
     * @return StudentSport {@link StudentSport}
     * @throws AkuraAppException - The exception details that occurred when retrieve StudentSport instances.
     */
    @Override
    public List<StudentSport> findStudentSportObj(int studentId, int sportCategoryId, Date year)
            throws AkuraAppException {

        return studentSportDaoTarget.findStudentSport(studentId, sportCategoryId, year);
    }
    
    /**
     * service to add ClubSociety to the student.
     * 
     * @param studentClubSociety the StudentClubSociety to add
     * @throws AkuraAppException AkuraAppException
     */
    @Override
    public void addStudentClubSociety(StudentClubSociety studentClubSociety) throws AkuraAppException {

        studentClubSocietyDaoTarget.save(studentClubSociety);
        
    }
    
    /**
     * service to edit ClubSociety Detail of a student.
     * 
     * @param studentClubSociety the StudentClubSociety object to edit
     * @throws AkuraAppException SMS Exception
     */
    @Override
    public void editStudentClubSocietyDetail(StudentClubSociety studentClubSociety) throws AkuraAppException {

        studentClubSocietyDaoTarget.update(studentClubSociety);
        
    }
    
    /**
     * service to delete a clubSociety of a student.
     * 
     * @param studentClubSocietyId the id of the studentClubsociety object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete studentClubsociety
     *         instances.
     */
    @Override
    public void deleteStudentClubSocietyDetail(int studentClubSocietyId) throws AkuraAppException {

        StudentClubSociety clubSociety =
                (StudentClubSociety) studentClubSocietyDaoTarget.findById(StudentClubSociety.class,
                        studentClubSocietyId);
        
        List<Achievement> achievementList =
                achievementDaoTarget.findStudentClubAchievement(clubSociety.getStudent().getStudentId(), "",
                        clubSociety.getClubSociety().getClubSocietyId(), clubSociety.getYear());
        
        // Check if this club/society has achievements.If so can't delete.
        if (!achievementList.isEmpty()) {
            
            throw new StudentClubAndSocietyAchievementDeleteException(PropertyReader.getPropertyValue(
                    AKURA_ERROR_MSG_PROPERTY, CLUB_AND_SOCIETY_DELETE_ERROR));
            
        } else {
            
            studentClubSocietyDaoTarget.delete(clubSociety);
        }
        
    }
    
    /**
     * service to get list of ClubSociety of a given student.
     * 
     * @param studentId the id of the student
     * @return List {@link StudentClubSociety}
     * @throws AkuraAppException AkuraAppException
     */
    @Override
    public List<StudentClubSociety> getClubSocietiesListForStudent(int studentId) throws AkuraAppException {

        return studentClubSocietyDaoTarget.getClubSocietiesListForStudent(studentId);
    }
    
    /**
     * service to find a studentClubSociety.
     * 
     * @param studentId the id of the StudentSport activity.
     * @param clubSocietyId the id of the clubSociety instance.
     * @param year the date of the clubSociety activity.
     * @return StudentClubSociety {@link StudentClubSociety}
     * @throws AkuraAppException - The exception details that occurred when retrieve StudentClubSociety
     *         instances.
     */
    @Override
    public List<StudentClubSociety> findStudentClubSocietyObj(int studentId, int clubSocietyId, Date year)
            throws AkuraAppException {

        return studentClubSocietyDaoTarget.findStudentClubSociety(studentId, clubSocietyId, year);
    }
    
    /**
     * Finds the StudentClubSociety that relevant to the StudentClubSociety id.
     * 
     * @param studClubSocietyId - the id of StudentClubSociety.
     * @return - StudentClubSociety instance.
     * @throws AkuraAppException - The exception details that occurred when finding a StudentClubSociety
     *         instance.
     */
    @Override
    public StudentClubSociety findStudentClubSocietyObjById(int studClubSocietyId) throws AkuraAppException {

        return (StudentClubSociety) studentClubSocietyDaoTarget.findById(StudentClubSociety.class, studClubSocietyId);
    }
    
    /**
     * service to get list of ClubSociety of a given student in a given year.
     * 
     * @param studentId the id of the student
     * @param year the Date of the studentSport activity.
     * @return List {@link StudentClubSociety}
     * @throws AkuraAppException AkuraAppException
     */
    @Override
    public List<StudentClubSociety> getClubSocietyListForStudent(int studentId, Date year) throws AkuraAppException {

        return SortUtil.sortStudentClubSocietyListByName(studentClubSocietyDaoTarget.getClubSocietyListForStudent(
                studentId, year));
    }
    
    /**
     * Service method is to save student.
     * 
     * @param student - student object to be saved.
     * @return Student.
     * @throws AkuraAppException AkuraAppException
     */
    @Override
    public Student saveStudent(Student student) throws AkuraAppException {

        studentDao.save(student);
        return student;
    }
    
    /**
     * Service method is to update student.
     * 
     * @param student - student object to be saved.
     * @return Student.
     * @throws AkuraAppException AkuraAppException
     */
    @Override
    public Student updateStudent(Student student) throws AkuraAppException {

        studentDao.update(student);
        return student;
    }
    
    /**
     * Returns a list of students in a specific class grade.
     * 
     * @param classGradeId classgrade id.
     * @param year the Year.
     * @return a list of StudentClassInfo.
     * @throws AkuraAppException throws if exception occurs.
     */
    @Override
    public List<StudentClassInfo> getClassStudentList(int classGradeId, int year) throws AkuraAppException {

        return this.studentClassInfoDAO.getClassStudentList(classGradeId, year);
    }
    
    /** {@inheritDoc} */
    public List<StudentClassInfo> getPresentClassStudentList(int classGradeId, int year, Date date)
            throws AkuraAppException {

        return this.studentClassInfoDAO.getPresentClassStudentList(classGradeId, year, date);
    }
    
    /**
     * Returns a list of studentClassInfo by student and year.
     * 
     * @param studentId student id.
     * @param year the Year.
     * @return a list of StudentClassInfo.
     * @throws AkuraAppException throws if exception occurs.
     */
    public List<StudentClassInfo> getStudentClassInfoByStudent(int studentId, int year) throws AkuraAppException{
        
        return this.studentClassInfoDAO.getStudentClassInfoByStudentId(studentId, year);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentSport> getStudentListforSportsCategory(int sportCategoryId, Date year) throws AkuraAppException {

        return this.studentSportDaoTarget.getStudentListforSportsCategory(sportCategoryId, year);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentClubSociety> getStudentListforClubSociety(int clubSocId, Date year) throws AkuraAppException {

        return this.studentClubSocietyDaoTarget.getStudentListforClubSociety(clubSocId, year);
    }
    
    /**
     * Service method is to delete student.
     * 
     * @param studentId - id of deleting student.
     * @throws AkuraAppException AkuraAppException
     */
    @Override
    public void deleteStudent(int studentId) throws AkuraAppException {

        Student student = (Student) studentDao.findById(Student.class, studentId);
        studentDao.delete(student);
    }
    
    /* StudentTermMarkService related methods */

    /**
     * @return average marks.
     */
    @Override
    public float getAverage() {

        return this.average;
    }
    
    /**
     * @param fltAverage to set average of marks.
     */
    private void setAverage(final float fltAverage) {

        this.average = fltAverage;
    }
    
    /**
     * Calculate average for given student depending on class and term and sets it to average variable.
     * 
     * @param termId to hold term Id.
     * @param classInfoId to hold student class info Id.
     */
    @Override
    public void calculateAverage(int termId, int classInfoId) {

        float total = 0;
        int i = 0;
        while (studentTermMarkList.iterator().next() != null) {
            
            if (studentTermMarkList.get(i).getTermId() == termId
                    && studentTermMarkList.get(i).getStudentClassInfoId() == classInfoId) {
                
                total += studentTermMarkList.get(i).getMarks();
            }
            i++;
        }
        this.setAverage(total / i);
    }
    
    /**
     * @return List of subject marks for all students.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    @Override
    public List<StudentTermMark> getAllSubjectMarks() throws AkuraAppException {

        return studentTermMarkDao.findAll(StudentTermMark.class);
    }
    
    /**
     * @param termId to hold term Id.
     * @return List of subject marks for all students matching search criteria.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    @Override
    public List<StudentTermMark> getSelectedSubjectMarks(int termId) throws AkuraAppException {

        return studentTermMarkDao.getSelectedSubjectMarks(termId);
    }
    
    /**
     * @param termId to hold term Id.
     * @param classGradeId to class grade Id.
     * @return List of subject marks for all students matching search criteria.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    @Override
    public List<StudentTermMark> getSelectedSubjectMarksByGrade(int termId, int classGradeId) throws AkuraAppException {

        return studentTermMarkDao.getSelectedSubjectMarksByGrade(termId, classGradeId);
    }
    
    /**
     * @param allSubjectMarks to hold subject marks for multiple students.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public void setAllSubjectMarks(final List<StudentTermMark> allSubjectMarks) throws AkuraAppException {

        studentTermMarkDao.saveOrUpdateAll(allSubjectMarks);
        
    }
    
    /**
     * @param objStudentTermMark to hold subject mark for one student for one term subject.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    @Override
    public void setSubjectMark(StudentTermMark objStudentTermMark) throws AkuraAppException {

        studentTermMarkDao.save(objStudentTermMark);
        
    }
    
    /**
     * @param objStudentTermMark to hold the object to be deleted.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    @Override
    public void deleteSubjectMarks(final StudentTermMark objStudentTermMark) throws AkuraAppException {

        studentTermMarkDao.delete(objStudentTermMark);
        
    }
    
    /**
     * @param objStudentTermMark to hold object to be updated
     * @throws AkuraAppException -
     */
    @Override
    public void editSubjectMarks(final StudentTermMark objStudentTermMark) throws AkuraAppException {

        studentTermMarkDao.update(objStudentTermMark);
        
    }
    
    /**
     * @param objStudentTermMarkDao the studentTermMarkDao to set
     */
    public void setStudentTermMarkDao(StudentTermMarkDao objStudentTermMarkDao) {

        this.studentTermMarkDao = objStudentTermMarkDao;
    }
    
    /* End of StudentTermMarkService related methods */

    /**
     * Sets the student sub term mark view dao.
     * 
     * @param objStudentSubTermMarkViewDao the studentSubTermMarkViewDao to set
     */
    public void setStudentSubTermMarkViewDao(StudentSubTermMarkViewDao objStudentSubTermMarkViewDao) {

        this.studentSubTermMarkViewDao = objStudentSubTermMarkViewDao;
    }
    
    /**
     * Setter method for studentGradeSubjectRankViewDao.
     * 
     * @param vStudentGradeSubjectRankViewDao object
     */
    public void setStudentGradeSubjectRankViewDao(StudentGradeSubjectRankViewDao vStudentGradeSubjectRankViewDao) {

        this.studentGradeSubjectRankViewDao = vStudentGradeSubjectRankViewDao;
    }
    
    /**
     * Method is to save or update list of StudentTermMark objects.
     * 
     * @param objstudentTermMark - TermMark type object list.
     * @return studentTermMarkList of StudentTermMark objects that updated.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public List<StudentTermMark> saveOrUpdateStudentSubjectList(List<StudentTermMark> objstudentTermMark)
            throws AkuraAppException {

        studentTermMarkList = this.studentTermMarkDao.getSavedStudentSubjectList(objstudentTermMark);
        
        return studentTermMarkList;
        
    }
    
    /**
     * Method is to get list of StudentTermMark objects of a perticular grade class.
     * 
     * @param classGrade - ClassGrade type object.
     * @return studentTermMarkList of StudentTermMark objects that deleted.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public List<StudentTermMark> viewStudentTermMarkInfoOfClassGrade(ClassGrade classGrade) throws AkuraAppException {

        studentTermMarkList = studentTermMarkDao.getStudentTermMarkInfoOfClassSubjects(classGrade);
        
        return studentTermMarkList;
    }
    
    /**
     * Method is to delete list of StudentTermMark objects.
     * 
     * @param deletedTermMarkIdList - StudentTermMarkId s to delete.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public void deleteStudentTermMark(List<Integer> deletedTermMarkIdList) throws AkuraAppException {

        studentTermMarkList = studentTermMarkDao.deleteStudentTermMarkInfoOfClassSubjects(deletedTermMarkIdList);
        if (studentTermMarkList.size() > 0) {
            studentSubTermMarkDao.deleteSubTrmMarks(studentTermMarkList);
        }
        
    }
    
    /**
     * Method is to generate list of StudentSubTermMark records.
     * 
     * @param changedTermMarkList - Student term mark objects.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public void generateSubtermMarkRecords(List<StudentSubTermMark> changedTermMarkList) throws AkuraAppException {

        studentSubTermMarkDao.saveAll(changedTermMarkList);
    }
    
    /**
     * Method is to return list of StudentTermMark objects.
     * 
     * @param studentId - integer type object.
     * @param year - Date type object.
     * @return list of StudentTermMark objects.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public List<StudentTermMarkDTO> getStudentMarksSubjectTermByStudentIdYear(int studentId, int year)
            throws AkuraAppException {

        return studentTermMarkViewDao.getTermMarksForStudent(studentId, year);
    }
    
    /**
     * Method is to view list of StudentSubTermMark objects given by student term mark id.
     * 
     * @param termMarkId - Student term mark id specified by an integer type variable
     * @throws AkuraAppException when exception occurs
     * @return list of StudentSubTermMark objects
     */
    @Override
    public List<StudentSubTermMark> getSubTermInfoByTermMarkId(int termMarkId) throws AkuraAppException {

        return studentSubTermMarkDao.viewSubTermInfoByTermMarkId(termMarkId);
    }
    
    /**
     * Service method is to save studentDisability.
     * 
     * @param studentDisability - studentDisability object to be saved.
     * @return studentDisability.
     * @throws AkuraAppException AkuraAppException
     */
    @Override
    public StudentDisability saveStudentDisability(StudentDisability studentDisability) throws AkuraAppException {

        studentDisabilityDao.save(studentDisability);
        return studentDisability;
    }
    
    /**
     * Service method is to update studentDisability.
     * 
     * @param studentDisability - studentDisability object to be saved.
     * @return studentDisability.
     * @throws AkuraAppException AkuraAppException
     */
    @Override
    public StudentDisability updateStudentDisability(StudentDisability studentDisability) throws AkuraAppException {

        studentDisabilityDao.update(studentDisability);
        return studentDisability;
    }
    
    /**
     * Service method is to update studentDisability.
     * 
     * @param studentId - related student id to find student disability object.
     * @return studentDisability.
     * @throws AkuraAppException AkuraAppException
     */
    @Override
    public StudentDisability findStudentDisability(int studentId) throws AkuraAppException {

        return (StudentDisability) studentDisabilityDao.findById(StudentDisability.class, studentId);
    }
    
    /**
     * Setter method for studentDisabilityDao.
     * 
     * @param studentDisabilityDaoVal the studentDisabilityDao to set
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public void setStudentDisabilityDao(StudentDisabilityDao studentDisabilityDaoVal) throws AkuraAppException {

        this.studentDisabilityDao = studentDisabilityDaoVal;
    }
    
    /**
     * Setter method to set ClassGradeDao object.
     * 
     * @param classGradeDaoObj object
     */
    public void setClassGradeDao(ClassGradeDao classGradeDaoObj) {

        this.classGradeDao = classGradeDaoObj;
    }
    
    /**
     * Service method is to chech whther admission no is exist.
     * 
     * @param admissionNo - admissionNo.
     * @return flag states whther admission no is exist.
     * @throws AkuraAppException AkuraAppException
     */
    @Override
    public boolean isAdmissionNoExist(String admissionNo) throws AkuraAppException {

        int studentId = 0;
        boolean validity = false;
        if (admissionNo != null && !admissionNo.isEmpty()) {
            studentId = studentDao.findStudentIdForAdmissionNo(admissionNo);
        }
        
        if (studentId != 0) {
            validity = true;
        }
        return validity;
    }
    
    /**
     * Method is to view list of StudentClassInfo objects of a particular grade and a year.
     * 
     * @param gradeId GradeID to set.
     * @param selectedyear Year to set.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    @Override
    public List<StudentClassInfo> getStudentSearchByGradeYear(int gradeId, int selectedyear) throws AkuraAppException {

        return studentClassInfoDAO.getStudentSearchByGradeYear(gradeId, selectedyear);
    }
    
    /**
     * Get a List of StudentClassInfo s of a particular grade and a year.
     * 
     * @param studentId Student Id to set.
     * @param year year of the class.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    @Override
    public List<StudentClassInfo> getStudentClassInfoByStudentId(int studentId, int year) throws AkuraAppException {

        return studentClassInfoDAO.getStudentClassInfoByStudentId(studentId, year);
    }
    
    /**
     * Method is to update list of StudentClassInfo objects.
     * 
     * @param updatedStudentClassInfoObjList - StudentClassInfo Objects to update.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public void updateStudentClassInfoObjects(List<StudentClassInfo> updatedStudentClassInfoObjList)
            throws AkuraAppException {

        studentClassInfoDAO.saveOrUpdateAll(updatedStudentClassInfoObjList);
    }
    
    /**
     * Method is to find StudentTermMark when provide the studentTermMarkId.
     * 
     * @param studentTermMarkId - int studentTermMarkId.
     * @return StudentTermMark return a student term mark object.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public StudentTermMark findStudentTermMark(int studentTermMarkId) throws AkuraAppException {

        return (StudentTermMark) this.studentTermMarkDao.findById(StudentTermMark.class, studentTermMarkId);
    }
    
    /**
     * Method is to update StudentTermMark List.
     * 
     * @param stuTermMarkList - studentTermMark list want to update.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public void updateStudentSubjectList(List<StudentTermMark> stuTermMarkList) throws AkuraAppException {

        this.studentTermMarkDao.updateStudentTermMark(stuTermMarkList);
        
    }
    
    /**
     * Method is to get sub term mark objects by proving a term mark id list.
     * 
     * @param termMarkIdList - studentTermMarkID list .
     * @return List of StudentSubTermMark- studentSubTermMark list returns.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public List<StudentSubTermMark> getSelectedSubTermMarksByGrade(List<Integer> termMarkIdList)
            throws AkuraAppException {

        return this.studentSubTermMarkDao.getSelectedSubTermMarksByGrade(termMarkIdList);
    }
    
    /**
     * Method is to get sub term mark object by proving a sub term mark id.
     * 
     * @param studentSubTermMarkId - studentSubTermMarkId .
     * @return StudentSubTermMark StudentSubTermMark object returns.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public StudentSubTermMark findStudentSubTermMark(int studentSubTermMarkId) throws AkuraAppException {

        return (StudentSubTermMark) this.studentSubTermMarkDao.findById(StudentSubTermMark.class, studentSubTermMarkId);
    }
    
    /**
     * Method is to update StudentSubTermMark List.
     * 
     * @param studentSubTermMarkList - studentSubTermMark list want to update.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public void updateStudentSubTermMarkList(List<StudentSubTermMark> studentSubTermMarkList) throws AkuraAppException {

        this.studentSubTermMarkDao.updateStudentSubTermMark(studentSubTermMarkList);
        
    }
    
    /**
     * Method is to save StudentClassInfo object.
     * 
     * @param sciObjTosave - StudentClassInfo object want to save.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public void saveStudentClassInfoObj(StudentClassInfo sciObjTosave) throws AkuraAppException {

        studentClassInfoDAO.save(sciObjTosave);
    }
    
    /**
     * Method is to view all Student objects.
     * 
     * @return List of Student objects
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public List<Student> getStudentList() throws AkuraAppException {

        return studentDao.findAll(Student.class);
        
    }
    
    /**
     * Method is to view all StudentClassInfo objects.
     * 
     * @return List of StudentClassInfo objects
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public List<StudentClassInfo> getAllStudentClassInfoObjList() throws AkuraAppException {

        return studentClassInfoDAO.findAll(StudentClassInfo.class);
    }
    
    /**
     * Service to add Achievement instance.
     * 
     * @param achievement Achievement instance.
     * @throws AkuraAppException throws if exception occurs when saving a Achievement instance.
     */
    @Override
    public void addAchievement(Achievement achievement) throws AkuraAppException {

        achievementDaoTarget.save(achievement);
        
    }
    
    /**
     * Updates the relevant Achievement object.
     * 
     * @param achievement - Achievement instance.
     * @throws AkuraAppException - if error occurs when updating a Achievement instance.
     */
    @Override
    public void editAchievement(Achievement achievement) throws AkuraAppException {

        achievementDaoTarget.update(achievement);
        
    }
    
    /**
     * Deletes the relevant Achievement object.
     * 
     * @param achievementId - id of Achievement.
     * @throws AkuraAppException - if error occurs when deleting a Achievement instance.
     */
    @Override
    public void deleteAchievement(int achievementId) throws AkuraAppException {

        Achievement achievement = this.findAchievementById(achievementId);
        achievementDaoTarget.delete(achievement);
        
    }
    
    /**
     * Returns a list of Achievement instances.
     * 
     * @param studentId - student id.
     * @param date - Date.
     * @return a list of Achievement instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Achievement instances.
     */
    @Override
    public List<Achievement> getAchievementsListForStudent(int studentId, Date date) throws AkuraAppException {

        return achievementDaoTarget.getAchievementsListForStudent(studentId, date);
        
    }
    
    /**
     * Finds the Achievement that relevant to the Achievement id.
     * 
     * @param achievementId - Achievement id.
     * @return a Achievement instance.
     * @throws AkuraAppException throws if exception occurs when finding a Achievement instance.
     */
    @Override
    public Achievement findAchievementById(int achievementId) throws AkuraAppException {

        return (Achievement) achievementDaoTarget.findById(Achievement.class, achievementId);
    }
    
    /**
     * service to find a studentAchievement.
     * 
     * @param studentId the id of the Student.
     * @param strDescription the Achievement.
     * @param strActivity the activity.
     * @param year the date of the achievement.
     * @return Achievement {@link Achievement}
     * @throws AkuraAppException - The exception details that occurred when retrieve Achievement instances.
     */
    @Override
    public List<Achievement> findStudentAchievementObj(int studentId, String strDescription, String strActivity,
            Date year) throws AkuraAppException {

        return achievementDaoTarget.findStudentAchievement(studentId, strDescription, strActivity, year);
    }
    
    /**
     * service to find a studentAchievement.
     * 
     * @param studentId the id of the Student.
     * @param strDescription the Achievement.
     * @param clubSocietyId the id of the club or society.
     * @param year the date of the achievement.
     * @return Achievement {@link Achievement}
     * @throws AkuraAppException - The exception details that occurred when retrieve Achievement instances.
     */
    @Override
    public List<Achievement> findStudentClubAchievementObj(int studentId, String strDescription, int clubSocietyId,
            Date year) throws AkuraAppException {

        return achievementDaoTarget.findStudentClubAchievement(studentId, strDescription, clubSocietyId, year);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    @Override
    public StudentLeave createStudentLeave(StudentLeave studentLeave) throws AkuraAppException {

        return studentLeaveDao.save(studentLeave);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    @Override
    public void updateStudentLeave(StudentLeave studentLeave) throws AkuraAppException {

        studentLeaveDao.update(studentLeave);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    @Override
    public StudentLeave findStudentLeaveById(int studentLeaveId) throws AkuraAppException {

        return (StudentLeave) studentLeaveDao.findById(StudentLeave.class, studentLeaveId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    @Override
    public void deleteStudentLeave(StudentLeave studentLeave) throws AkuraAppException {

        studentLeaveDao.delete(studentLeave);
    }
    
    /**
     * Method is to view all StudentPrefect objects given by StudentId.
     * 
     * @param studentId Student Id to set.
     * @param year specifies the year which holds the prefect details given by integer type object.
     * @return List of StudentPrefect objects
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public List<StudentPrefect> getStudentPrefectDetailsByStudentId(int studentId, int year) throws AkuraAppException {

        return studentPrefectDao.getStudentPrefectDetailsByStudentId(studentId, year);
    }
    
    /**
     * Method is to view all StudentScholarship objects given by StudentId.
     * 
     * @param studentId Student Id to set.
     * @param year specifies the year which holds the scholarship details given by integer type object.
     * @return List of StudentScholarship objects
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public List<StudentScholarship> getStudentScholarshipDetailsByStudentId(int studentId, int year)
            throws AkuraAppException {

        return studentScholarshipDao.getStudentScholarshipDetailsByStudentId(studentId, year);
    }
    
    /**
     * Method is to get all StudentClassInfo by ClassGradeId.
     * 
     * @param classGradeIds - classGradeIds to set.
     * @return List of StudentClassInfo objects
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public List<StudentClassInfo> getStudentClassInfoByClassGradeIdList(List<Integer> classGradeIds)
            throws AkuraAppException {

        return studentClassInfoDAO.findStudentClassInfoByClassGradeId(classGradeIds);
    }
    
    /**
     * service to add a FaithLifeRating to student.
     * 
     * @param faithLifeRating the FaithLifeRating object to add.
     * @return the faith life rating
     * @throws AkuraAppException - The exception details that occurred when adding a FaithLifeRating
     *         instances.
     */
    @Override
    public FaithLifeRating addFaithLifeRating(FaithLifeRating faithLifeRating) throws AkuraAppException {

        return faithLifeRatingDaoTarget.save(faithLifeRating);
    }
    
    /**
     * service to edit a FaithLifeRating of a student.
     * 
     * @param faithLifeRating the FaithLifeRating object to edit.
     * @throws AkuraAppException - The exception details that occurred when editing a FaithLifeRating
     *         instances.
     */
    @Override
    public void editFaithLifeRating(FaithLifeRating faithLifeRating) throws AkuraAppException {

        faithLifeRatingDaoTarget.update(faithLifeRating);
    }
    
    /**
     * service to delete a FaithLifeRating of a student.
     * 
     * @param faithLifeRatingId the id of the FaithLifeRating object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a FaithLifeRating
     *         instances.
     */
    @Override
    public void deleteFaithLifeRating(int faithLifeRatingId) throws AkuraAppException {

        FaithLifeRating objFaithLifeRating =
                (FaithLifeRating) faithLifeRatingDaoTarget.findById(FaithLifeRating.class, faithLifeRatingId);
        faithLifeRatingDaoTarget.delete(objFaithLifeRating);
    }
    
    /**
     * service to get the list of FaithLifeRating of a given student.
     * 
     * @param studentId the id of the Student.
     * @param year the Date
     * @return List {@link FaithLifeRating}
     * @throws AkuraAppException - The exception details that occurred when retrieve a list of FaithLifeRating
     *         instances.
     */
    @Override
    public List<FaithLifeRating> getFaithLifeRatingsListForStudent(int studentId, Date year) throws AkuraAppException {

        return faithLifeRatingDaoTarget.getFaithLifeRatingsListForStudent(studentId, year);
    }
    
    
    /**
     * service to get the list of FaithLifeRating of a given student.
     *
     * @param studentId the id of the Student.
     * @param year the Date
     * @return List {@link FaithLifeRating}
     * @throws AkuraAppException - The exception details that occurred when retrieve a list of FaithLifeRating
     *         instances.
     */
    @Override
    public List<Object> getFaithLifeRateListForStudent(int studentId, Date year ) throws AkuraAppException {

        return faithLifeRatingDaoTarget.getFaithLifeRateListForStudent(studentId,year);
    }

    /**
     * service to find a FaithLifeRating by faithLifeRatingId.
     * 
     * @param faithLifeRatingId the id of the FaithLifeRating.
     * @return FaithLifeRating {@link FaithLifeRating}
     * @throws AkuraAppException - The exception details that occurred when retrieve FaithLifeRating
     *         instances.
     */
    @Override
    public FaithLifeRating findFaithLifeRatingObjById(int faithLifeRatingId) throws AkuraAppException {

        return (FaithLifeRating) faithLifeRatingDaoTarget.findById(FaithLifeRating.class, faithLifeRatingId);
    }
    
    /**
     * service to find a FaithLifeRating.
     * 
     * @param studentId the id Student.
     * @param description the description of the faithLifeRating.
     * @param year the Date
     * @return FaithLifeRating {@link FaithLifeRating}
     * @throws AkuraAppException - The exception details that occurred when retrieve FaithLifeRating
     *         instances.
     */
    @Override
    public List<FaithLifeRating> findFaithLifeRatingObj(int studentId, String description, Date year)
            throws AkuraAppException {

        return null;
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    @Override
    public List<StudentLeave> getStudentLeaveList() throws AkuraAppException {

        return studentLeaveDao.findAll(StudentLeave.class);
    }
    
    /**
     * Dao method is to get StudentId for the admissionNo.
     * 
     * @param admissionNo - admissionNo.
     * @return student id for the registrationNo.
     * @throws AkuraAppException AkuraAppException
     */
    @Override
    public int findStudentIdForAdmissionNo(String admissionNo) throws AkuraAppException {

        return studentDao.findStudentIdForAdmissionNo(admissionNo);
    }
    
    /**
     * Method is to save StudentPrefect object.
     * 
     * @param studentPrefectObj student prefect object to set.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public void saveStudentPrefectDetails(StudentPrefect studentPrefectObj) throws AkuraAppException {

        studentPrefectDao.save(studentPrefectObj);
    }
    
    /**
     * Method is to update StudentPrefect object.
     * 
     * @param studentPrefectObj student prefect object to set.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public void updateStudentPrefectDetails(StudentPrefect studentPrefectObj) throws AkuraAppException {

        studentPrefectDao.update(studentPrefectObj);
    }
    
    /**
     * Method is to find StudentPrefect object given by Id.
     * 
     * @param studentPrefectId PrefectType Id to set.
     * @return StudentPrefect object
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public StudentPrefect findStudentPrefectDetailsById(int studentPrefectId) throws AkuraAppException {

        return (StudentPrefect) studentPrefectDao.findById(StudentPrefect.class, studentPrefectId);
    }
    
    /**
     * Method is to delete StudentPrefect object given by Id.
     * 
     * @param studentPrefectObj student prefect object to set.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public void deleteStudentPrefectDetails(StudentPrefect studentPrefectObj) throws AkuraAppException {

        studentPrefectDao.delete(studentPrefectObj);
    }
    
    /**
     * Method is to get all StudentPrefect objects.
     * 
     * @return StudentPrefect object
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public List<StudentPrefect> getAllStudentPrefectDetails() throws AkuraAppException {

        return studentPrefectDao.findAll(StudentPrefect.class);
    }
    
    /**
     * Method is to save StudentScholarship object.
     * 
     * @param studentScholarship student scholarship object to set.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public void saveStudentScholarshipDetails(StudentScholarship studentScholarship) throws AkuraAppException {

        studentScholarshipDao.save(studentScholarship);
    }
    
    /**
     * Method is to update StudentScholarship object.
     * 
     * @param studentScholarship StudentScholarship object to set.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public void updateStudentScholarshipDetails(StudentScholarship studentScholarship) throws AkuraAppException {

        studentScholarshipDao.update(studentScholarship);
    }
    
    /**
     * Method is to find StudentScholarship object given by Id.
     * 
     * @param studentScholarshipId PrefectType Id to set.
     * @return StudentScholarship object
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public StudentScholarship findStudentScholarshipDetailsById(int studentScholarshipId) throws AkuraAppException {

        return (StudentScholarship) studentScholarshipDao.findById(StudentScholarship.class, studentScholarshipId);
    }
    
    /**
     * Method is to delete StudentScholarship object given by Id.
     * 
     * @param studentScholarship StudentScholarship object to set.
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public void deleteStudentScholarshipDetails(StudentScholarship studentScholarship) throws AkuraAppException {

        studentScholarshipDao.delete(studentScholarship);
    }
    
    /**
     * Method is to get all StudentPrefect objects.
     * 
     * @return StudentPrefect object
     * @throws AkuraAppException when exception occurs.
     */
    @Override
    public List<StudentScholarship> getAllStudentScholarshipDetails() throws AkuraAppException {

        return studentScholarshipDao.findAll(StudentScholarship.class);
    }
    
    /**
     * Retrieve list of StudentLeave for a particular student.
     * 
     * @param studentId - type int
     * @return list of StudentLeave
     * @throws AkuraAppException throws if exception occurs.
     */
    @Override
    public List<StudentLeave> getStudentLeaveListByStudentId(int studentId) throws AkuraAppException {

        return studentLeaveDao.findStudentLeaveByStudentId(studentId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentTemporaryLeave> getStudentTempLeaveListByStudentId(int studentId) throws AkuraAppException {

        return studentTempLeaveDao.findStudentTempLeaveByStudentId(studentId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentSubTermMark> getSubTermMarks(int subTermId, int termId) throws AkuraAppException {

        return studentSubTermMarkDao.getSubTermMarks(subTermId, termId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getSubTerms(int studentTermMarkId) throws AkuraAppException {

        return studentSubTermMarkDao.getSubTerms(studentTermMarkId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getRating(String faithLifeValue) throws AkuraAppException {

        int value = 0;
        final int valueV = 3;
        if (faithLifeValue.equals(new ErrorMsgLoader().getErrorMessage("REF.SERVICE.RATING.VERYGOOD"))) {
            value = valueV;
        } else if (faithLifeValue.equals(new ErrorMsgLoader().getErrorMessage("REF.SERVICE.RATING.GOOD"))) {
            value = 2;
        } else {
            value = 1;
        }
        return value;
    }
    
    /**
     * Gets the student personal details by admission no.
     * 
     * @see com.virtusa.sms.api.service.StudentService#getStudentPersonalDetailsByAdmissionNo(java.lang.String)
     * @param admissionNumber the student admission number
     * @return the student personal details by admission no
     * @throws AkuraAppException the sMS app exception
     */
    public List<Student> getStudentPersonalDetailsByAdmissionNo(String admissionNumber) throws AkuraAppException {

        return studentDao.getStudentPersonalDetailsByAdmissionNo(admissionNumber);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getNewStudentForYear(int year) throws AkuraAppException {

        return studentDao.getNewStudentForYear(year);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Float getStuTermMarks(final int classInfoId, final int gradeSubjectId, final int parseInt)
            throws AkuraAppException {

        final List<Float> marks = studentTermMarkDao.getStuTermMarks(classInfoId, gradeSubjectId, parseInt);
        return marks != null && marks.size() > 0 ? marks.get(0) : 0F;
    }
    
    /**
     * Display all Student Discipline information for given year.
     * 
     * @param startDate - start date of year.
     * @param endDate - end date of year.
     * @return List of StudentDiscipline type objects
     * @throws AkuraAppException SMS Exception.
     */
    @Override
    public List<StudentDiscipline> viewAllStudentDisciplineInfoByYear(Date startDate, Date endDate)
            throws AkuraAppException {

        return studentDisciplineDaoTarget.viewAllStudentDisciplineInfoByYear(startDate, endDate);
    }
    
    /**
     * Display all StudentClassInfo objects for given class and given year.
     * 
     * @param classGradeId - classId.
     * @param year - calendar year.
     * @return List of StudentClassInfo type objects
     * @throws AkuraAppException Akura Exception.
     */
    @Override
    public List<StudentClassInfo> getStudentClassInfoOfClassByYear(int classGradeId, Date year)
            throws AkuraAppException {

        return studentClassInfoDAO.getStudentClassInfoOfClassByYear(classGradeId, year);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentClassInfo> getStudentClassInfoOfStudentClassByYear(int studentId, int classGradeId, Date year)
            throws AkuraAppException {

        return studentClassInfoDAO.getStudentClassInfoOfStudentClassByYear(studentId, classGradeId, year);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentLeave> getStudentLeaveListByStudentId(int studentId, int year) throws AkuraAppException {

        return studentLeaveDao.getStudentLeaveListByStudentId(studentId, year);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentClassInfo> getStudentClassInfoByStudentId2(int studentId) throws AkuraAppException {

        return studentClassInfoDAO.getStudentClassInfoByStudentId2(studentId);
    }
    
    /** {@inheritDoc} */
    @Override
    public StudentClassInfo findStudentClassInfoById(int classGradeIds) throws AkuraAppException {

        return (StudentClassInfo) this.studentClassInfoDAO.findById(StudentClassInfo.class, classGradeIds);
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentSpecialEventParticipationView> getAllocatedStudentListForEvent(int participantId, Date date)
            throws AkuraAppException {

        String year = DateUtil.getStringYear(date) + START_YEAR;
        Date dateYear = DateUtil.getParseDate(year);
        
        return studentSpecialEventParticipationViewDao.getSpecialEventParticipationStudentInfo(participantId, dateYear);
        
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentSeminar> getAllStudentSeminars(int studentId, Date dateSelectedYear) throws AkuraAppException {

        return studentSeminarDao.getAllStudentSeminars(studentId, dateSelectedYear);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Seminar> getAllSeminars() throws AkuraAppException {

        return seminarDao.findAll(Seminar.class);
    }
    
    /** {@inheritDoc} */
    @Override
    public void saveStudetnSeminar(StudentSeminar studentSem) throws AkuraAppException {

        studentSeminarDao.save(studentSem);
    }
    
    /** {@inheritDoc} */
    @Override
    public void updateStudentSeminar(StudentSeminar studentSem) throws AkuraAppException {

        studentSeminarDao.update(studentSem);
    }
    
    /** {@inheritDoc} */
    @Override
    public void deleteStudentSeminar(int studentSeminarId) throws AkuraAppException {

        StudentSeminar delObj = (StudentSeminar) studentSeminarDao.findById(StudentSeminar.class, studentSeminarId);
        
        studentSeminarDao.delete(delObj);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isStudentTermMarkExist(int studentClassInfoId, int gradeSubjectId, int termId)
            throws AkuraAppException {

        List<Integer> termMarkIdList =
                studentTermMarkDao.isStudentTermMarkExist(studentClassInfoId, gradeSubjectId, termId);
        return !termMarkIdList.isEmpty() ? (termMarkIdList.get(0) > 0 ? true : false) : false;
    }
    
    /**
     * Gets the student sub term marks.
     * 
     * @param studentId the student id
     * @param year the year
     * @return the student sub term marks
     * @throws AkuraAppException the akura app exception
     */
    @Override
    public List<StudentSubTermMarkDTO> getStudentSubTermMarks(int studentId, int year) throws AkuraAppException {

        return studentSubTermMarkViewDao.getStudentSubTermMarks(studentId, year);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentTermMarkDTO> getTermMarksByTermGradeYear(int classGradeId, int termid, String year)
            throws AkuraAppException {

        return studentTermMarkViewDao.getTermMarksByTermGradeYear(classGradeId, termid, Integer.parseInt(year));
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public StudentTermMarksRank getStudentTermMarksRank(int studentClassInfo, int termId) throws AkuraAppException {

        return studentTermMarkRankViewDao.getStudentTermMarksRank(studentClassInfo, termId);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentSubjectAverageViewDTO> getStudentSubjectAverage(int studentId, int year)
            throws AkuraAppException {

        return studentSubjectAverageViewDao.getStudentSubjectAverage(studentId, year);
    }
    
    /**
     * Gets the student sub term marks by subject.
     * 
     * @param studentId the student id
     * @param year the year
     * @param subject the subject
     * @return the student sub term marks by subject
     * @throws AkuraAppException the akura app exception
     */
    @Override
    public List<StudentSubTermMarkDTO> getStudentSubTermMarksBySubject(int studentId, Date year, String subject)
            throws AkuraAppException {

        return studentSubTermMarkViewDao.getStudentSubTermMarksBySubject(studentId, year, subject);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SubjectAverageTermMarks getSubjectAverageTermMarksById(String classDescription, int year,
            int gradeSubjectId, String term) throws AkuraAppException {

        return subjectAverageTermMarksDao.getSubjectAverageTermMarksById(classDescription, year, gradeSubjectId, term);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentTermMarksRank> getStudentRank(int studentClassInfoId) throws AkuraAppException {

        return studentTermMarkRankViewDao.getStudentRank(studentClassInfoId);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Object> populateStudentAverageTerm(int year, int studentId) throws AkuraAppException {

        return studentTermMarkViewDao.populateStudentAverageTerm(year, studentId);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentGradeSubjectRankView> getStudentGradeSubjectRankList(int gradeSubjectId, int year, int noOfPrize)
            throws AkuraAppException {

        return studentGradeSubjectRankViewDao.getStudentGradeSubjectRank(gradeSubjectId, year, noOfPrize);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentTermMarkDTO> findTermMarksByClassInfoIdAndGradeSubject(int classInfoId, int gradeSubjectId)
            throws AkuraAppException {

        return studentTermMarkViewDao.getTermMarksByClassInfoIdAndGradeSubject(classInfoId, gradeSubjectId);
        
    }
    
    /**
     * Gets the student assignment marks.
     * 
     * @param classGradeId the class grade id
     * @param gradeSubjectId the grade subject id
     * @param year the year
     * @return the student assignment marks
     * @throws AkuraAppException the akura app exception
     * @see com.virtusa.akura.student.service.StudentService#getStudentAssignmentMarks(int, int, int,
     *      java.util.Date)
     */
    @Override
    public List<ClassWiseStudentsSubjectsDTO> getClassWiseStudentListBySubject(int classGradeId, int gradeSubjectId,
            Date year) throws AkuraAppException {

        return classWiseStudentsSubjectsViewDao.getClassWiseStudentListBySubject(classGradeId, gradeSubjectId, year);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentsGradeRankView> findTermWiseStudentTotalMarks(int studentClassInfoId) throws AkuraAppException {

        return studentGradeRankViewDao.getTermWiseStudentTotalMarks(studentClassInfoId);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentAnnualGradeRank> findStudentAnnualGradeRank(int gradeId, int year, int noOfPrizes)
            throws AkuraAppException {

        return studentAnnualGradeRankDao.getStudentAnnualGradeRank(gradeId, year, noOfPrizes);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getAllExamMarksList(final int year, final int examId) throws AkuraAppException {

        BigInteger val = examMarksDao.getAllExamMarksList(year, examId).get(0);
        return val.intValue();
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List searchExamMarks(int examMarksListSize, int year, String gradeId, int examId) throws AkuraAppException {

        return examMarksDao.searchExamMarks(examMarksListSize, year, gradeId, examId);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getStudentIdByName(final String studentName) throws AkuraAppException {

        List<Integer> studentNameList = studentDao.getStudentIdByName(studentName);
        return studentNameList != null ? studentNameList.get(0) : 0;
    }
    
    /** {@inheritDoc} */
    public void saveExamMarks(final ExamMark examMark) throws AkuraAppException {

        examMarksDao.save(examMark);
        
    }
    
    /** {@inheritDoc} */
    @Override
    public ExamMark findExamMarkById(final int examSubjectKey) throws AkuraAppException {

        return (ExamMark) examMarksDao.findById(ExamMark.class, examSubjectKey);
    }
    
    /** {@inheritDoc} */
    @Override
    public void updateExamMarks(ExamMark examMarks) throws AkuraAppException {

        examMarksDao.update(examMarks);
    }
    
    /**
     * Save student assignment marks list.
     * 
     * @param studentAssignmentMarksList the student assignment marks list
     * @throws AkuraAppException the akura app exception
     */
    @Override
    public void saveStudentAssignmentMarksList(List<StudentAssignmentMark> studentAssignmentMarksList)
            throws AkuraAppException {

        studentAssignmentMarkDao.saveOrUpdateAll(studentAssignmentMarksList);
    }
    
    /**
     * Gets the student assignment marks list.
     * 
     * @param gradeSubjectId the grade subject id
     * @param assignId the assignment id
     * @param year the year
     * @param classGradeId the class grade id
     * @return the student assignment marks list
     * @throws AkuraAppException the akura app exception
     */
    @Transactional
    public List<Object[]> getStudentAssignmentMarksList(int gradeSubjectId, int assignId, int year, int classGradeId)
            throws AkuraAppException {

        return studentAssignmentMarkDao.getStudentAssignmentMarksList(gradeSubjectId, assignId, year, classGradeId);
    }
    
    /** {@inheritDoc} */
    public List<ExamAdmission> getExamAdmissionList() throws AkuraAppException {

        return examAdmissionDao.findAll(ExamAdmission.class);
    }
    
    /** {@inheritDoc} */
    public ExamAdmission findExamAdmissionByAdmission(final String admissionNo, int year, final int examId)
            throws AkuraAppException {

        List<ExamAdmission> examAdmissionList =
                examAdmissionDao.findExamAdmissionByAdmission(admissionNo, year, examId);
        return !examAdmissionList.isEmpty() ? examAdmissionList.get(0) : null;
    }
    
    /** {@inheritDoc} */
    @Override
    public ExamAdmission saveExamAdmission(final ExamAdmission examAdmission) throws AkuraAppException {

        return examAdmissionDao.save(examAdmission);
    }
    
    /** {@inheritDoc} */
    @Override
    public void updateExamAdmission(final ExamAdmission examAdmission) throws AkuraAppException {

        examAdmissionDao.update(examAdmission);
    }
    
    /** {@inheritDoc} */
    @Override
    public ExamAdmission findExamAdmissionById(final int examAdmissionId) throws AkuraAppException {

        return (ExamAdmission) examAdmissionDao.findById(ExamAdmission.class, examAdmissionId);
    }
    
    /** {@inheritDoc} */
    public void saveOrUpdateExamMarks(final List<ExamMark> examMarkList) throws AkuraAppException {

        examMarksDao.saveOrUpdateAll(examMarkList);
    }
    
    /**
     * Gets the student assignment mark by id.
     * 
     * @param studentAssignmentMarksId the student assignment marks id
     * @return the student assignment mark by id
     * @throws AkuraAppException the akura app exception
     */
    @Override
    public StudentAssignmentMark getStudentAssignmentMarkById(int studentAssignmentMarksId) throws AkuraAppException {

        return (StudentAssignmentMark) studentAssignmentMarkDao.findById(StudentAssignmentMark.class,
                studentAssignmentMarksId);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Integer> getStudentClassInfoIdsNotInAssignmentMarks(int year) throws AkuraAppException {

        return studentAssignmentMarkDao.getStudentClassInfoIdsNotInAssignmentMarks(year);
    }
    
    /** {@inheritDoc} */
    @Override
    public void updateStudentAssignmentMark(StudentAssignmentMark studentAssignmentMark) throws AkuraAppException {

        studentAssignmentMarkDao.update(studentAssignmentMark);
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExistAssignmentMarks(int assignmentId) throws AkuraAppException {

        List<StudentAssignmentMark> assignmentMarksList = studentAssignmentMarkDao.isExistAssignmentMarks(assignmentId);
        return assignmentMarksList.isEmpty() ? false : true;
        
    }
    
    /** {@inheritDoc} */
    @Override
    public ExamAdmission findExamAdmissionByStudentId(String admission, int year, int studentId, int examId)
            throws AkuraAppException {

        List<ExamAdmission> examAdmissionList =
                examAdmissionDao.findExamAdmissionByStudentId(admission, year, studentId, examId);
        return !examAdmissionList.isEmpty() ? examAdmissionList.get(0) : null;
    }
    
    /**
     * Gets the student assignment marks by student id.
     * 
     * @param studentId the student id
     * @return the student assignment marks by student id
     * @throws AkuraAppException the akura app exception
     */
    @Override
    public List<Object[]> getStudentAssignmentMarksByStudentId(int studentId) throws AkuraAppException {

        return studentAssignmentMarkDao.getStudentAssignmentMarksByStudentId(studentId);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<ExamResults> getExamResultsByStudentId(int studentId) throws AkuraAppException {

        return examResultsDao.getExamResultsByStudentId(studentId);
    }
    
    /**
     * Retrieves Exam Results by Exam Admission No.
     * 
     * @param examAdmissionNo - examAdmissionNo
     * @param examId - examId
     * @return list of exam results objects
     * @throws AkuraAppException - AkuraAppException
     */
    @Override
    public List<ExamResults> findExamResultsByExamAdmissionNo(String examAdmissionNo, int examId)
            throws AkuraAppException {

        List<ExamResults> examResultsList = examResultsDao.getExamResultsByExamAdmissionNo(examAdmissionNo, examId);
        return examResultsList;
    }
    
    /**
     * Gets the student assignment marks by subject.
     * 
     * @param studentId the student id
     * @param year the year
     * @param subject the subject
     * @return the student assignment marks by subject
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Override
    public List<AssignmentMarkView> getStudentAssignmentMarksBySubject(int studentId, Date year, String subject)
            throws AkuraAppException {

        return assignmentMarkViewDao.getStudentAssignmentMarksBySubject(studentId, year, subject);
    }
    
    /** {@inheritDoc} */
    @Override
    public void saveOrUpdateExamAdmissions(List<ExamAdmission> newExamAdmissionList) throws AkuraAppException {

        examAdmissionDao.saveOrUpdateAll(newExamAdmissionList);
    }
    
    /**
     * Iterates over the new subjects and checks the existance. {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List searchExamMarksWithArray(final int year, final String gradeId, final int examId, final List<Integer> o)
            throws AkuraAppException {

        Integer[] array = new Integer[o.size()];
        List examSubjectsList = new ArrayList();
        int index = 0;
        for (Integer list : o) {
            array[index] = list;
            List newExamSubjectsList = examMarksDao.searchExamMarksWithArray(year, gradeId, examId, list);
            examSubjectsList = ListUtils.union(examSubjectsList, newExamSubjectsList);
            index++;
        }
        return examSubjectsList;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setMarkingFlag(boolean markingCompleted, int gradeId, int classGradeId, int termId, Date year)
            throws AkuraAppException {

        MarkingFlag markingFlag = markingFlagDao.findMarkingFlag(classGradeId, termId, year);
        
        if (markingFlag == null) {
            
            markingFlag = new MarkingFlag();
            markingFlag.setMarkingCompleted(markingCompleted);
            markingFlag.setGradeId(gradeId);
            markingFlag.setClassGradeId(classGradeId);
            markingFlag.setTermId(termId);
            markingFlag.setYear(year);
            markingFlagDao.save(markingFlag);
        } else {
            
            markingFlag.setMarkingCompleted(markingCompleted);
            markingFlagDao.update(markingFlag);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAnnualMarkingDoneForGrade(int gradeId, int year) throws AkuraAppException {

        boolean markingCompleted = true;
        boolean loopingFinish = false;
        List<Term> termList = termDao.findAll(Term.class);
        List<ClassGrade> classgradeList = classGradeDao.getClassGradeListByGradeId(gradeId);
        
        for (ClassGrade classGrade : classgradeList) {
            if (loopingFinish) {
                break;
            }
            for (Term term : termList) {
                markingCompleted =
                        markingFlagDao.isMarkingCompletedForTerm(classGrade.getClassGradeId(), term.getTermId(), year);
                
                if (!markingCompleted) {
                    markingCompleted = false;
                    loopingFinish = true;
                    break;
                }
            }
        }
        
        return markingCompleted;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMarkingCompletedForTerm(int classGradeId, int termId, int year) throws AkuraAppException {

        return markingFlagDao.isMarkingCompletedForTerm(classGradeId, termId, year);
    }
    
    /**
     * Service method is to check whether exam admission no is exist.
     * 
     * @param examAdmissionNo - examAdmissionNo.
     * @return flag states whether exam admission no is exist.
     * @throws AkuraAppException AkuraAppException
     */
    @Override
    public boolean isExamAdmissionNoExist(String examAdmissionNo) throws AkuraAppException {

        String tempExamAdmissionNo = "";
        boolean validity = false;
        if (examAdmissionNo != null && !examAdmissionNo.isEmpty()) {
            tempExamAdmissionNo = examAdmissionDao.getExamAdmissionNo(examAdmissionNo);
        }
        
        if (!tempExamAdmissionNo.isEmpty()) {
            validity = true;
        }
        return validity;
    }
    
    /** {@inheritDoc} */
    public List<AssignmentMarkView> getStudentAssignmentMarks(int studentId, int year) throws AkuraAppException {

        return assignmentMarkViewDao.getStudentAssignmentMarks(studentId, year);
        
    }
    
    /**
     * Gets the student admission nos by class grade id.
     * 
     * @param classGradeId the class grade id
     * @return the student admission nos by class grade id
     * @throws AkuraAppException the akura app exception
     */
    public List<String> getStudentAdmissionNosByClassGradeId(int classGradeId) throws AkuraAppException {

        return studentDao.getStudentAdmissionNosByClassGradeId(classGradeId);
    }
    
    /** {@inheritDoc} */
    public List<StudentLeave> findAlreadyExistLeave(int studentId, Date dFrom, Date dateTo) throws AkuraAppException {

        return studentLeaveDao.findAlreadyExistLeave(studentId, dFrom, dateTo);
    }
    
    /** {@inheritDoc} */
    public List<ExamResults> getExamResults(int studentId, Date date) throws AkuraAppException {

        return examResultsDao.getExamResults(studentId, date);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentTermMarkPerformance> getTermMarksByGradeYear(String grade, int term, String year, float less,
            float grater) throws AkuraAppException {

        return studentTermMarkPerformanceDao.getTermMarksByGradeYear(grade, term, Integer.parseInt(year), less, grater);
        
    }
    
    /**
     * Gets the student term marks by student id year subject.
     * 
     * @param studentId the student id
     * @param year the year
     * @param gradeSubjectId the grade subject id
     * @return the student term marks by student id year subject
     * @throws AkuraAppException the akura app exception
     */
    public List<StudentTermMarkDTO> getStudentTermMarksByStudentIdYearSubject(int studentId, int year,
            int gradeSubjectId) throws AkuraAppException {

        return studentTermMarkViewDao.getStudentTermMarksByStudentIdYearSubject(studentId, year, gradeSubjectId);
    }
    
    /** {@inheritDoc} */
    @Override
    public MediumWiseClassSubjectAverageView getMediumWiseClassSubjectAverage(int classGradeid, int year,
            int gradeSubjectId, String term, int languageId) throws AkuraAppException {

        return mediumWiseClassSubjectAverageViewDao.findMediumWiseClassSubjectAverage(classGradeid, year,
                gradeSubjectId, term, languageId);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Integer> getStudyMediumsInClass(int classGradeid, int year, String term) throws AkuraAppException {

        return mediumWiseClassSubjectAverageViewDao.findStudyMediumsInClass(classGradeid, year, term);
        
    }
    
    /** {@inheritDoc} */
    public Double getBestStudentAverage(int classGradeId, String term, Date year) throws AkuraAppException {

        return studentTermMarkRankViewDao.getBestStudentAverage(classGradeId, term, year);
    }
    
    /** {@inheritDoc} */
    public Object[] getClassAverage(int classGradeId, String term, Date year) throws AkuraAppException {

        return studentTermMarkRankViewDao.getClassAverage(classGradeId, term, year);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentSubTermMarkDTO> getSubTermMarksForStudent(int studentId, int year) throws AkuraAppException {

        return studentSubTermMarkViewDao.getSubTermMarksForStudent(studentId, year);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentTermMarkDTO> getStudentTermMarksByStudentIdYearClassGrade(int studentId, int year,
            int classGradeId) throws AkuraAppException {

        return studentTermMarkViewDao.findStudentTermMarksByStudentIdYearClassGrade(studentId, year, classGradeId);
        
    }
    
    /** {@inheritDoc} */
    public int getKeyByIdentificationNo(final String identification) throws AkuraAppException {

        return studentDao.findStudentIdForAdmissionNo(identification);
    }
    
    /**
     * Check whether StudentDiscipline already exist.
     * 
     * @param studentDisciplineName - StudentDiscipline
     * @return true if the StudentDiscipline exist else false.
     * @throws AkuraAppException - Throw detailed exception.
     */
    public boolean isExistStudentDiscipline(StudentDiscipline studentDisciplineName) throws AkuraAppException {

        String comment1 = studentDisciplineName.getComment().trim();
        Boolean informedtoParent = studentDisciplineName.isInformedtoParent();
        Integer warningLevelId = studentDisciplineName.getWarningLevelId();
        Date date = studentDisciplineName.getDate();
        
        StudentDiscipline studentDiscipline =
                studentDisciplineDaoTarget.getStudentDisciplineByName(comment1, informedtoParent, warningLevelId, date);
        
        boolean isExists = false;
        
        if (studentDiscipline != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentTermMark> getStudentTermMarkListByStudentIdAndYear(int studentId, Date year)
            throws AkuraAppException {

        return studentTermMarkDao.getTermMarkByStudentIdAndYear(studentId, year);
    }
    
    /** {@inheritDoc} */
    @Transactional
    public boolean rejoinStudentMemberService(int studentId, Date rejoinDate) throws InvalidRejoinDateException,
            IOException, AkuraAppException {

        // Change the rejoin date to yesterday.
        Calendar dayBeforeRejoin = Calendar.getInstance();
        dayBeforeRejoin.setTime(rejoinDate);
        dayBeforeRejoin.add(Calendar.DATE, -1);
        rejoinDate = dayBeforeRejoin.getTime();
        
        Student student = (Student) studentDao.findById(Student.class, studentId);
        
        UserLogin userAccountOfStudentMember = null;
        
        StudentTemporaryLeave studentTemporaryLeave = null;
        
        List<UserLogin> userLoginList =
                userLoginDao.getUserLoginOfAnyStatusByRegistrationNo(String.valueOf(student.getStudentId()));
        
        List<StudentTemporaryLeave> studentTemporaryLeavelist =
                studentTempLeaveDao.findStudentTempLeaveByStudentId(studentId);
        
        if (!studentTemporaryLeavelist.isEmpty()) {
            studentTemporaryLeave = studentTemporaryLeavelist.get(0);
        }
        
        if (!userLoginList.isEmpty()) {
            
            userAccountOfStudentMember = userLoginList.get(0);
        }
        
        // student leave object
        StudentLeave studentLeave = null;
        int daysCount = 0;
        
        // get student leave list
        List<StudentLeave> studentLeaveList = studentLeaveDao.findStudentLeaveByStudentId(studentId);
        
        if (!studentLeaveList.isEmpty()) {
            // take the last record of student leave - to be updated this record
            studentLeave = studentLeaveList.get(studentLeaveList.size() - 1);
        }
        
        if (studentLeave != null) {
            // convert date from & date to into calendar type.
            Calendar fromDate = Calendar.getInstance();
            fromDate.setTime(studentLeave.getFromDate());
            Calendar toDate = Calendar.getInstance();
            toDate.setTime(rejoinDate);
            
            // get holiday list between date from and date to
            List<Holiday> holidayList = getHolidayList(studentLeave.getFromDate(), rejoinDate);
            
            // count holiday
            int holidayCount = HolidayUtil.countHolidays(fromDate, toDate, holidayList);
            
            // count number of days
            daysCount = HolidayUtil.daysBetween(studentLeave.getFromDate(), rejoinDate) - holidayCount;
            studentLeave.setNoOfDays(daysCount);
        }
        
        boolean rejoinedStatus = false;
        
        if (student.getStatusId() == TEMPORARY_STATE) {
            
            // update student leave object
            if (studentLeave != null) {
                studentLeave.setToDate(rejoinDate);
                
                studentLeave.setNoOfDays(daysCount);
                
                studentLeaveDao.update(studentLeave);
            }
            
            // student departure date is null , when it is active state.
            student.setDateOfDeparture(null);
            
            // set student status to current status.
            student.setStatusId(1);
            
            // update student status
            this.modifyStudent(student);
            
            if (studentTemporaryLeave != null) {
                studentTemporaryLeave.setActivateDate(rejoinDate);
                studentTemporaryLeave.setToDate(rejoinDate);
                studentTemporaryLeave.setNoOfDays(daysCount);
                
                // update student temporary leave
                studentTempLeaveDao.update(studentTemporaryLeave);
            }
            
            rejoinedStatus = true;
            
            if (userAccountOfStudentMember != null) {
                
                userAccountOfStudentMember.setDeleted(false);
                
                userAccountOfStudentMember.setStatus(true);
                
                // update user login
                userLoginDao.update(userAccountOfStudentMember);
            }
            
        } else {
            
            throw new InvalidRejoinDateException(PropertyReader.getPropertyValue(AKURA_ERROR_MSG_PROPERTY,
                    ERROR_MESSAGE_STUDENT_REJOIN));
        }
        
        return rejoinedStatus;
    }
    
    /**
     * Get the student discipline in current year.
     * 
     * @param studentIdVal - the id of the student.
     * @param year - Current year.
     * @return - a list of StudentDiscipline.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Override
    public List<StudentDiscipline> viewCurrentYearStudentDisciplineInfo(int studentIdVal, int year)
            throws AkuraAppException {

        return studentDisciplineDaoTarget.viewCurrentYearStudentDisciplineByStudentId(studentIdVal, year);
        
    }
    
    /**
     * Get the student details.
     * 
     * @param studentId - the id of the student.
     * @return - a list of Student Objects.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    public Object viewStudent(int studentId) throws AkuraAppException {

        return this.studentDao.findById(Student.class, studentId);
    }
    
    /**
     * Suspend the students.
     * 
     * @param suspendStudent - SuspendStudent object.
     * @return - boolean value.
     * @throws AkuraAppException - The exception details that occurred when processing.
     * @throws AlreadyOnLeaveException Newly created Exception
     */
    @Transactional
    public boolean suspendStudent(SuspendStudent suspendStudent) throws AkuraAppException, AlreadyOnLeaveException {

        boolean activityStatus = true;
        
        Student student = suspendStudent.getStudent();
        
        int studentId = student.getStudentId();
        suspendStudent.setStudent(student);
        
        // Get the suspended duration.
        Calendar fromDate = Calendar.getInstance();
        fromDate.setTime(suspendStudent.getFromDate());
        Calendar toDate = Calendar.getInstance();
        toDate.setTime(suspendStudent.getToDate());
        
        List<Holiday> holidayList = getHolidayList(suspendStudent.getFromDate(), suspendStudent.getToDate());
        
        int holidayCount = HolidayUtil.countHolidays(fromDate, toDate, holidayList);
        int daysCount =
                HolidayUtil.daysBetween(suspendStudent.getFromDate(), suspendStudent.getToDate()) - holidayCount;
        suspendStudent.setNoOfDays(daysCount);
        
        // Check whether student is on leave.
        List<StudentLeave> existLeave =
                this.findAlreadyExistLeave(studentId, suspendStudent.getFromDate(), suspendStudent.getToDate());
        
        if (existLeave.isEmpty()) {
            
            // Disable the user login of the suspended student.
            disableAndDeleteStudentLogin(student);
            
            // Make student leave as suspended.
            StudentLeave studentLeave = new StudentLeave();
            studentLeave.setStudentId(studentId);
            studentLeave.setFromDate(suspendStudent.getFromDate());
            studentLeave.setToDate(suspendStudent.getToDate());
            studentLeave.setReason(PropertyReader.getPropertyValue(AKURA_ERROR_MSG_PROPERTY, SUSPEND));
            studentLeave.setNoOfDays(suspendStudent.getNoOfDays());
            studentLeave.setCheckAutoGenerated(true);
            studentLeaveDao.save(studentLeave);
            
            // Update the student status as suspended.
            studentDao.update(student);
            
            /* The service details will be moved to Suspend student details. */
            studentSuspendDao.save(suspendStudent);
            
        } else {
            throw new AlreadyOnLeaveException(PropertyReader.getPropertyValue(AKURA_ERROR_MSG_PROPERTY,
                    ERROR_MESSAGE_ALREADY_ONLEAVE));
        }
        
        return activityStatus;
    }
    
    /**
     * Disable User login details related to staff member.
     * 
     * @param student - student object of the particular student member
     * @throws AkuraAppException AkuraAppException
     */
    private void disableAndDeleteStudentLogin(Student student) throws AkuraAppException {

        UserLogin userLogin =
                userLoginDao.getUserLoginByUserRoleAndIdetificationNo(UserRole.ROLE_STUDENT.getUserRoleId(),
                        student.getStudentId() + AkuraConstant.EMPTY_STRING);
        
        if (userLogin != null) {
            userLogin.setStatus(false);
            userLogin.setDeleted(true);
            userLoginDao.update(userLogin);
        }
    }
    
    /**
     * Terminate as past student.
     * 
     * @param pastStudent - PastStudent object.
     * @return - boolean value.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Transactional
    public boolean terminatePastStudent(PastStudent pastStudent) throws AkuraAppException {

        boolean activityStatus = true;
        Student student = pastStudent.getStudent();
        int studentId = student.getStudentId();
        Date departureDate = pastStudent.getDateOfDeparture();
        
        /* set the departure date of the student. */

        student.setDateOfDeparture(departureDate);
        
        studentDao.update(student);
        
        /* disable and mark as deleted the userLogin belongs to student. */
        disableAndDeleteStudentLogin(student);
        
        /*
         * Student data will be deleted from database if the student departures
         */
        studentParentDaoTarget.deleteStudentByDepartureDate(studentId);
        
        /* if there are any attendance data after the departure date, it should be cleared . */
        studentDao.deletedStudentAttendanceRecordsByDepartureDate(studentId, departureDate);
        
        /*
         * update the ToDate if leave record is
         */
        updateStudentLeaveRecord(studentId, pastStudent);
        
        /* The service details will be moved to pastStudent. */
        pastStudentDao.save(pastStudent);
        
        return activityStatus;
        
    }
    
    /**
     * {@inheritDoc}
     */
    public List<PastStudent> getPastStudentByStudentId(int studentId) throws AkuraAppException {

        return pastStudentDao.getPastStudentList(studentId);
    }
    
    /**
     * terminates a student.
     * 
     * @param studentTemLeave - studentTemporaryLeave object of the particular student - terminateCriteriaVal
     * @throws AkuraAppException when exception occurs.
     * @return true
     */
    @Transactional
    public boolean terminateTemporaryLeaveStudent(StudentTemporaryLeave studentTemLeave) throws AkuraAppException {

        Student student = studentTemLeave.getStudent();
        int studentId = student.getStudentId();
        
        // setting the no of days in the studentTemporaryLeave
        Calendar fromDate = Calendar.getInstance();
        fromDate.setTime(studentTemLeave.getFromDate());
        Calendar toDate = Calendar.getInstance();
        toDate.setTime(studentTemLeave.getToDate());
        
        List<Holiday> holidayList = getHolidayList(studentTemLeave.getFromDate(), studentTemLeave.getToDate());
        
        int holidayCount = HolidayUtil.countHolidays(fromDate, toDate, holidayList);
        int daysCount =
                HolidayUtil.daysBetween(studentTemLeave.getFromDate(), studentTemLeave.getToDate()) - holidayCount;
        
        studentTemLeave.setNoOfDays(daysCount);
        
        // Disable the user login of the TemporaryLeft student.
        disableAndDeleteStudentLogin(student);
        
        // update the leave record in student Leave
        StudentLeave studentLeave = new StudentLeave();
        studentLeave.setStudentId(studentId);
        studentLeave.setFromDate(studentTemLeave.getFromDate());
        studentLeave.setToDate(studentTemLeave.getToDate());
        studentLeave.setReason(TEMP_LEAVE);
        studentLeave.setNoOfDays(studentTemLeave.getNoOfDays());
        studentLeave.setCheckAutoGenerated(true);
        studentLeaveDao.save(studentLeave);
        
        /* The service details will be moved to studentTemporaryLeave. */
        studentTempLeaveDao.save(studentTemLeave);
        
        // set the status of the student to temporary leave
        studentDao.update(student);
        
        return true;
    }
    
    /**
     * Check whether MarkEntriesCompleted.
     * 
     * @param studentId - student Id
     * @return true if the MarkEntriesCompleted else false.
     * @throws AkuraAppException - Throw detailed exception.
     */
    public boolean checkMarkEntriesCompleted(int studentId) throws AkuraAppException {

        boolean completed = true;
        List<Term> terms = termDao.findAll(Term.class);
        int noOfTerms = terms.size();
        List<StudentClassInfo> studentClassInfos =
                studentClassInfoDAO.getStudentClassInfoByStudentId(studentId, DateUtil.currentYearOnly());
        int i = 1;
        while (i <= noOfTerms) {
            for (int j = 0; j < studentClassInfos.size(); j++) {
                
                ClassGrade classGrade = (ClassGrade) studentClassInfos.get(j).getClassGrade();
                int classGradeId = classGrade.getClassGradeId();
                completed = markingFlagDao.isMarkingCompletedForTerm(classGradeId, i, DateUtil.currentYearOnly());
            }
            if (!completed) {
                break;
            } else {
                i++;
            }
        }
        return completed;
        
    }
    
    /**
     * Delete term marks.
     * 
     * @param studentId - student Id
     * @throws AkuraAppException - Throw detailed exception.
     */
    
    public void deleteStudentTermmarks(int studentId) throws AkuraAppException {

        List<StudentClassInfo> studentClassInfos =
                studentClassInfoDAO.getStudentClassInfoByStudentId(studentId, DateUtil.currentYearOnly());
        
        List<Term> terms = termDao.findAll(Term.class);
        
        int i = 1;
        boolean completed = true;
        int noOfTerms = terms.size();
        while (i < (noOfTerms + 1)) {
            for (int j = 0; j < studentClassInfos.size(); j++) {
                ClassGrade classGrade = new ClassGrade();
                classGrade = (ClassGrade) studentClassInfos.get(j).getClassGrade();
                int classGradeId = classGrade.getClassGradeId();
                completed = markingFlagDao.isMarkingCompletedForTerm(classGradeId, i, DateUtil.currentYearOnly());
            }
            if (!completed) {
                
                studentTermMarkDao.deleteTrmMarks(studentId, i);
                i++;
            } else {
                i++;
            }
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    public void saveExtendedTemporaryStudent(int studentId, int workingDays, Date date) throws AkuraAppException {

        StudentTemporaryLeave studentTemporary = new StudentTemporaryLeave();
        List<StudentTemporaryLeave> studentTemporaryLeavelist =
                studentTempLeaveDao.findStudentTempLeaveByStudentId(studentId);
        
        studentTemporary = studentTemporaryLeavelist.get(0);
        
        studentTemporary.setNoOfDays(workingDays);
        studentTemporary.setToDate(date);
        
        if (studentTemporary != null) {
            studentTempLeaveDao.update(studentTemporary);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Transactional
    public boolean rejoinSuspendedStudentMemberService(int studentId, Date rejoinDate, String reason)
            throws AkuraAppException, SuspendRejoinReasonValidationException {

        boolean rejoinedStatus = false;
        
        SuspendStudent suspendStudent = null;
        StudentLeave studentLeave = null;
        
        // Get suspend Details List by student Id
        List<SuspendStudent> suspendedSudentDetailList = studentSuspendDao.getSuspendedSudentDetailList(studentId);
        
        // take first value of suspend Details List
        if (!suspendedSudentDetailList.isEmpty()) {
            suspendStudent = suspendedSudentDetailList.get(0);
        }
        
        // validate reason
        if (!rejoinDate.equals(suspendStudent.getToDate())) {
            if (reason == null || reason.trim().isEmpty()) {
                
                throw new SuspendRejoinReasonValidationException(PropertyReader.getPropertyValue(
                        AKURA_ERROR_MSG_PROPERTY, ERROR_MESSAGE_EPMTY_FEILD));
            }
        }
        
        // validate same day activation
        if (rejoinDate.equals(suspendStudent.getFromDate())) {
            throw new SuspendRejoinReasonValidationException(PropertyReader.getPropertyValue(AKURA_ERROR_MSG_PROPERTY,
                    ERROR_MESSAGE_STUDENT_REJOIN_SAMEDAY));
            
        }
        
        // Get student object by student Id
        Student student = (Student) studentDao.findById(Student.class, studentId);
        
        // Get user login object by UserRole And Identification number
        UserLogin userLogin =
                userLoginDao.getUserLoginByUserRoleAndIdetificationNo(UserRole.ROLE_STUDENT.getUserRoleId(),
                        student.getStudentId() + "");
        
        // Get Student Leave List by student Id
        List<StudentLeave> studentLeaveList = studentLeaveDao.findStudentLeaveByStudentId(studentId);
        
        // take last value of Student Leave List
        if (!studentLeaveList.isEmpty()) {
            studentLeave = studentLeaveList.get(studentLeaveList.size() - 1);
        }
        
        // Get the real suspended duration.
        Calendar fromDate = Calendar.getInstance();
        fromDate.setTime(suspendStudent.getFromDate());
        Calendar toDate = Calendar.getInstance();
        toDate.setTime(rejoinDate);
        
        // reduce one day
        toDate.add(Calendar.DATE, -1);
        Date yesterday = toDate.getTime();
        
        List<Holiday> holidayList = getHolidayList(suspendStudent.getFromDate(), yesterday);
        
        int holidayCount = HolidayUtil.countHolidays(fromDate, toDate, holidayList);
        int daysCount = HolidayUtil.daysBetween(suspendStudent.getFromDate(), yesterday) - holidayCount;
        suspendStudent.setNoOfDays(daysCount);
        
        // Update student
        if (student != null) {
            student.setStatusId(1);
            studentDao.update(student);
            
        }
        
        // Update Student Leave object
        if (studentLeave != null) {
            studentLeave.setToDate(yesterday);
            studentLeave.setNoOfDays(daysCount);
            
            studentLeaveDao.update(studentLeave);
        }
        
        // Update Suspend Student Object
        if (suspendStudent != null) {
            suspendStudent.setActivatedDate(yesterday);
            suspendStudent.setCurtailedOrExtendedReason(reason);
            suspendStudent.setNoOfDays(daysCount);
            studentSuspendDao.update(suspendStudent);
        }
        
        // Update User login Object
        if (userLogin != null) {
            userLogin.setStatus(true);
            userLogin.setDeleted(false);
            userLoginDao.update(userLogin);
            this.modifyStudent(student);
        }
        
        rejoinedStatus = true;
        
        return rejoinedStatus;
    }
    
    /**
     * Method is to view calculate number of days leave.
     * 
     * @param startDate - Date Starting date.
     * @param endDate - Date Ending date.
     * @return list of HolidayList.
     * @throws AkuraAppException - throw this
     */
    private List<Holiday> getHolidayList(Date startDate, Date endDate) throws AkuraAppException {

        String strYr = DateUtil.getStringYear(startDate);
        
        String strStartDate = strYr + START_DATE;
        String strEndDate = strYr + END_DATE;
        
        Date startDateToSearch = DateUtil.getParseDate(strStartDate);
        Date endDateToSearch = DateUtil.getParseDate(strEndDate);
        
        return holidayDao.getHolidayListByYear(startDateToSearch, endDateToSearch);
        
    }
    
    /**
     * Method is to view StudentDisciplineInfo.
     * 
     * @return list of StudentDiscipline.
     * @throws AkuraAppException - throw this
     */
    
    public List<StudentDiscipline> viewStudentDisciplineInfo() throws AkuraAppException {

        return studentDisciplineDao.findAll(StudentDiscipline.class);
        
    }
    
    /**
     * {@inheritDoc}
     */
    public List<SuspendStudent> getSuspendedStudentByStudentId(int studentId) throws AkuraAppException {

        return studentSuspendDao.getSuspendedSudentDetailList(studentId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws
     */
    public List<StudentParent> getParentId(int studentIdVal) throws AkuraAppException {

        return studentParentDaoTarget.getStudentParentsByStudentId(studentIdVal);
        
    }
    
    /**
     * {@inheritDoc}
     */
    public String getLoggedUser(String user) throws AkuraAppException {

        UserLogin userLogin = userDao.getUserLoginByName(user);
        String strFromAddress = userLogin.getEmail();
        return strFromAddress;
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudentTemporaryLeave> getLatestStudentTempLeaveByStudentId(int studentId) throws AkuraAppException {

        return studentTempLeaveDao.getLatestStudentTempLeaveByStudentId(studentId);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudentClassInfo> getNonCurrentStudentClassInfoList(int classGradeId, int year, Date date)
            throws AkuraAppException {

        return studentClassInfoDAO.getNonCurrentClassInfoList(classGradeId, year, date);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<SuspendStudent> getLatestStudentSuspendRecordByStudentId(int studentId) throws AkuraAppException {

        return studentSuspendDao.getLatestStudentSuspendRecordByStudentId(studentId);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudentSport> getNonCurrentStudentListforSportsCategory(int sportCategoryId, Date year, Date date)
            throws AkuraAppException {

        return studentSportDaoTarget.getNonCurrentStudentListforSportsCategory(sportCategoryId, year, date);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudentClubSociety> getNonCurrentStudentListByClubSociety(int clubOrSocietyId, Date year, Date date)
            throws AkuraAppException {

        return studentClubSocietyDaoTarget.getNonCurrentStudentListByClubSociety(clubOrSocietyId, year, date);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudentTermMark> getStudentTermMarkObject(int studentClassInfoId, int gradeSubjectId, int termId)
            throws AkuraAppException {

        return (List<StudentTermMark>) studentTermMarkDao.getStudentTermMarkObject(studentClassInfoId, gradeSubjectId,
                termId);
    }
    
    /**
     * Remove all students that are past, suspended or temporary leaved considered to the date passed.
     * 
     * @param date - day of interest.
     * @param studentClassesToBeRemoved - list of StudentClassInfo to be removed.
     * @param studentClass - StudentClassInfo to be considered for removal.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    public void checkStudentStatusForRemoval(Date date, List<StudentClassInfo> studentClassesToBeRemoved,
            StudentClassInfo studentClass) throws AkuraAppException {

        Student student = findStudent(studentClass.getStudent().getStudentId());
        
        // get the temporary leaved students.
        if (student.getStatusId() == AkuraConstant.PARAM_INDEX_FOUR) {
            
            List<StudentTemporaryLeave> tempLeaveList = getLatestStudentTempLeaveByStudentId(student.getStudentId());
            
            if (!tempLeaveList.isEmpty() && date.after(tempLeaveList.get(0).getFromDate())
                    && date.before(tempLeaveList.get(0).getToDate())) {
                studentClassesToBeRemoved.add(studentClass);
            }
            
            // get the suspended student.
        } else if (student.getStatusId() == AkuraConstant.PARAM_INDEX_THREE) {
            
            List<SuspendStudent> suspendedRecord = getLatestStudentSuspendRecordByStudentId(student.getStudentId());
            
            if (!suspendedRecord.isEmpty() && date.after(suspendedRecord.get(0).getFromDate())
                    && date.before(suspendedRecord.get(0).getToDate())) {
                studentClassesToBeRemoved.add(studentClass);
            }
            
            // get the past students.
        } else if (student.getStatusId() == AkuraConstant.PARAM_INDEX_TWO) {
            
            if (student.getDateOfDeparture().before(date)) {
                studentClassesToBeRemoved.add(studentClass);
            }
            
        }
    }
    
    /**
     * Update the To date StudentLeaveRecord.
     * 
     * @param studentId the id of student
     * @param pastStudent the PastStudent object
     * @throws AkuraAppException AkuraAppException.
     */
    private void updateStudentLeaveRecord(int studentId, PastStudent pastStudent) throws AkuraAppException {

        StudentLeave studentLeave = null;
        Date departureDate = pastStudent.getDateOfDeparture();
        
        // get student leave list
        List<StudentLeave> studentLeaveList = studentLeaveDao.findStudentLeaveByStudentId(studentId);
        
        if (!studentLeaveList.isEmpty()) {
            // take the last record of student leave - to be updated this record
            studentLeave = studentLeaveList.get(studentLeaveList.size() - 1);
        }
        
        if (studentLeave != null) {
            Calendar fromDate = Calendar.getInstance();
            fromDate.setTime(studentLeave.getFromDate());
            
            Calendar departureDateVal = Calendar.getInstance();
            departureDateVal.setTime(pastStudent.getDateOfDeparture());
            List<Holiday> holidayList = getHolidayList(studentLeave.getFromDate(), departureDate);
            
            int holidayCount = HolidayUtil.countHolidays(fromDate, departureDateVal, holidayList);
            int daysCount = HolidayUtil.daysBetween(studentLeave.getFromDate(), departureDate) - holidayCount;
            
            if ((studentLeave.getToDate()).after(departureDate)) {
                studentLeave.setToDate(departureDate);
                studentLeave.setNoOfDays(daysCount);
                
                studentLeaveDao.update(studentLeave);
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudentClassInfo> getStudentClassList(int studentId, int classGradeId) throws AkuraAppException {

        return this.studentClassInfoDAO.getStudentClassList(studentId, classGradeId);
    }
    
    /** {@inheritDoc} */
    public List<Integer> getStudentClassInfoIdByYear(int classGradeId, Date currentYear) throws AkuraAppException {

        return studentClassInfoDAO.getStudentClassInfoIdByYear(classGradeId, currentYear);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudentLeave> checkTodayIsWithinLeavePeriod(int studentId) throws AkuraAppException {

        return studentLeaveDao.checkTodayIsWithinLeavePeriod(studentId);
    }
    
    /** {@inheritDoc} */
    public boolean isMarkingCompleted(final int clsGradeId, final String year, boolean markCompletionStatus)
            throws AkuraAppException {

        Date currentYear = DateUtil.getDate(Integer.parseInt(year));
        List<Boolean> markCompletion = markingFlagDao.isMarkingCompleted(clsGradeId, currentYear, markCompletionStatus);
        return markCompletion.isEmpty() ? false : true;
    }
    
    /** {@inheritDoc} */
    public boolean findMarkingFlag(int classGradeId, String term, Date currentYear) throws AkuraAppException {

        MarkingFlag markingFlag = markingFlagDao.findMarkingFlag(classGradeId, Integer.parseInt(term), currentYear);
        return markingFlag != null && markingFlag.isMarkingCompleted() ? true : false;
    }
    
    /** {@inheritDoc} */
    public boolean findMarkingStatusForGrade(int gradeId, String term, Date currentYear) throws AkuraAppException {

        List<Boolean> markingFlag =
                markingFlagDao.findMarkingStatusForGrade(gradeId, Integer.parseInt(term), currentYear);
        return !markingFlag.isEmpty() ? false : true;
    }
    
    /** {@inheritDoc} */
    public Date getStudentStartedDate(int studentId) throws AkuraAppException {

        List<Date> dateList = studentDao.getStudentStartedDate(studentId);
        return dateList.isEmpty() ? null : dateList.get(0);
    }
    
    /** {@inheritDoc} */
    public List<Boolean> isExistMarkingCompleted(int clsGradeId, String year) throws AkuraAppException {

        Date currentYear = DateUtil.getDate(Integer.parseInt(year));
        return markingFlagDao.isExistMarkingCompleted(clsGradeId, currentYear);
    }
    
    /**
     * Get the selected disciplinary action for student.
     * 
     * @param disciplinaryActionId - Id of the selected disciplinary action for student.
     * @return ListStudentDiscipline disciplinary action.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    public List<StudentDiscipline> getSelectedDisciplinaryActionByStudent(int disciplinaryActionId)
            throws AkuraAppException {

        return studentDisciplineDaoTarget.getSelectedDisciplinaryActionByStudent(disciplinaryActionId);
        
    }
    
    /**
     * {@inheritDoc}
     */
    public StudentClassInfo getStudentClassInfoByStudentClassInfoId(int studentClassInfoId) throws AkuraAppException {

        return (StudentClassInfo) studentClassInfoDAO.findById(StudentClassInfo.class, studentClassInfoId);
    }
    
    /** {@inheritDoc} */
    public List<Object[]> getStudentAssignmentMarksByStudentIdForReport(int studentId) throws AkuraAppException {

        return studentAssignmentMarkDao.getStudentAssignmentMarksByStudentIdForReport(studentId);
        
    }
    
    /** {@inheritDoc} */
    public List<StudentClassInfo> getPresentClassStudentList(int classGradeId, int year) throws AkuraAppException {

        return this.studentClassInfoDAO.getPresentClassStudentList(classGradeId, year);
    }
    
    /** {@inheritDoc} */
    public int getNoOfTerms() throws AkuraAppException {

        List<Long> noOfTerms = termDao.getNoOfTerms();
        return noOfTerms.isEmpty() ? 0 : noOfTerms.get(0).intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Integer> getStudentStatusListByStudentIdsList(List<Integer> studentIdsList) throws AkuraAppException {

        return studentDao.getStudentStatusListByStudentIdsList(studentIdsList);
    }
    
    /**
     * {@inheritDoc}
     */
    public int getStudentStatusId(int studentId) throws AkuraAppException {

        return studentDao.getStudentStatusId(studentId);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudentClassInfo> getSuspendedAndTemporaryLeavedStudentsToBeActive(int clGradeId, int year, Date date)
            throws AkuraAppException {

        List<StudentClassInfo> listOfSuspendedAndTemporaryLeavedStudents = new ArrayList<StudentClassInfo>();
        
        listOfSuspendedAndTemporaryLeavedStudents.addAll(studentClassInfoDAO.getSuspendedLeaveStudents(clGradeId, year,
                date));
        listOfSuspendedAndTemporaryLeavedStudents.addAll(studentClassInfoDAO.getTemporaryLeavedClassStudentList(
                clGradeId, year, date));
        return listOfSuspendedAndTemporaryLeavedStudents;
    }
    
    /**
     * service to find a StudentDiscipline.
     * 
     * @param studentId the id Student.
     * @param year the Date
     * @return StudentDiscipline {@link StudentDiscipline}
     * @throws AkuraAppException - The exception details that occurred when retrieve StudentDiscipline
     *         instances.
     */
    public List<StudentDiscipline> findStudentDisciplineListForStudent(int studentId, Date year)
            throws AkuraAppException {

        return studentDisciplineDaoTarget.getStudentDisciplineListForStudent(studentId, year);
        
    }
    
    /**
     * {@inheritDoc}
     */
    public CommonEmailBean setEmailProperties(String strFromAddress, String strToemail, String templateName,
            Map<String, Object> mapObjectMap, String subject) throws AkuraAppException {

        CommonEmailBean commonEmailBean = new CommonEmailBean();
        
        commonEmailBean.setFromAddress(strFromAddress);
        commonEmailBean.setToAddress(strToemail);
        commonEmailBean.setMailTemplate(templateName);
        commonEmailBean.setObjectMap(mapObjectMap);
        commonEmailBean.setSubject(subject);
        
        return commonEmailBean;
        
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean checkStudentIsOnLeave(int studentId, Date date) throws AkuraAppException {

        boolean studentLeaveExist = false;
        
        List<StudentLeave> studentLeaves = studentLeaveDao.checkSelectedDateIsWithinLeavePeriod(studentId, date);
        
        if (studentLeaves != null && !studentLeaves.isEmpty()) {
            studentLeaveExist = true;
        }
        return studentLeaveExist;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isPresentDay(int studentId, Date fromDate, Date toDate) throws AkuraAppException {

        boolean isPresentDay = false;
        
        List<Date> presentDaysList = studentLeaveDao.getStudentPresentDaysList(studentId, fromDate, toDate);
        
        if (presentDaysList != null && !presentDaysList.isEmpty()) {
            isPresentDay = true;
        }
        return isPresentDay;
    }

    
    /** {@inheritDoc} */
    public List<String> getScholarshipNameByStudentId(final int studentKey, final int year) throws AkuraAppException {

        return studentScholarshipDao.getScholarshipNameByStudentId(studentKey, DateUtil.getDate(year));
    }
    
    /** {@inheritDoc} */
    public List<String> getPrefectTypeByStudentId(int studentKey, int currentYear) throws AkuraAppException {

        return studentPrefectDao.getPrefectTypeByStudentId(studentKey, DateUtil.getDate(currentYear));
    }
    
    /** {@inheritDoc} */
    public List<List<String>> viewClubAndSocieties(int studentKey, int currentYear) throws AkuraAppException {

        return studentClubSocietyDaoTarget.viewClubAndSocieties(studentKey, DateUtil.getDate(currentYear));
    }
    
    /** {@inheritDoc} */
    public List<List<String>> viewStudentSeminars(int studentKey, int currentDate) throws AkuraAppException {

        return studentSeminarDao.viewStudentSeminars(studentKey, DateUtil.getDate(currentDate));
    }
    
    /** {@inheritDoc} */
    public List<String> viewAchievements(int studentKey, int currentYearOnly, boolean acheivementStatus)
            throws AkuraAppException {

        return achievementDaoTarget.viewAchievements(studentKey, DateUtil.getDate(currentYearOnly), acheivementStatus);
    }

    
    /**
     * {@inheritDoc}
     */
    public int getNumberOfStudntsInSchool() throws AkuraAppException {

        return studentDao.getNumberOfStudentsInSchool();
    }
    

}
