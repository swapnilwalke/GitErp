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

package com.virtusa.akura.student.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MediumWiseClassSubjectAverageView;
import com.virtusa.akura.api.dto.MeritAward;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.ParticipantCategory;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Seminar;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentAnnualGradeRank;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentDiscipline;
import com.virtusa.akura.api.dto.StudentGradeSubjectRankView;
import com.virtusa.akura.api.dto.StudentSearchCritiria;
import com.virtusa.akura.api.dto.StudentSeminar;
import com.virtusa.akura.api.dto.StudentSpecialEventParticipationView;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.dto.StudentTermMarksRank;
import com.virtusa.akura.api.dto.StudentsGradeRankView;
import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.SubjectAverageTermMarks;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.WarningLevel;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.BloodGroupDao;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.ClassGradeDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.dao.GradeSubjectDao;
import com.virtusa.akura.common.dao.HouseDao;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.dao.MethodOfTravelDao;
import com.virtusa.akura.common.dao.NationalityDao;
import com.virtusa.akura.common.dao.ParticipantCategoryDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.ReligionDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.common.dao.SpecialEventsDao;
import com.virtusa.akura.common.dao.SpecialEventsParticipationDao;
import com.virtusa.akura.common.dao.StudyMediumDao;
import com.virtusa.akura.common.dao.SubjectDao;
import com.virtusa.akura.common.dao.TermDao;
import com.virtusa.akura.common.dao.UserLoginDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.BaseStudentTest;
import com.virtusa.akura.student.dao.StudentClassInfoDao;
import com.virtusa.akura.student.dao.StudentDao;
import com.virtusa.akura.student.dao.StudentTermMarkDao;
import com.virtusa.akura.util.DateUtil;

/**
 * Description: StudentServiceImplTest test class for StudentServiceImp.
 * 
 * @author Virtusa Corporation.
 */
public class StudentServiceImplTest extends BaseStudentTest {
    
    /** attribute for holding string. */
    private static final String START_YEAR = "-01-01";
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StudentService studentService;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private CommonService commonService;
    
    /** Holds instance of TermDao. */
    @Autowired
    private TermDao termDAO;
    
    /** Holds instance of ProvinceDao. */
    @Autowired
    private ProvinceDao provinceDao;
    
    /** Holds instance of DistrictDao. */
    @Autowired
    private DistrictDao districtDao;
    
    /** Holds instance of ReligionDao. */
    @Autowired
    private ReligionDao religionDao;
    
    /** Holds instance of BloodGroupDao. */
    @Autowired
    private BloodGroupDao bloodGroupDao;
    
    /** Holds instance of NationalityDao. */
    @Autowired
    private NationalityDao nationalityDao;
    
    /** Holds instance of HouseDao. */
    @Autowired
    private HouseDao houseDao;
    
    /** Holds instance of CityDao. */
    @Autowired
    private CityDao cityDao;
    
    /** Holds instance of StudyMediumDao. */
    @Autowired
    private StudyMediumDao studyMediumDao;
    
    /** Holds instance of MethodOfTravel. */
    @Autowired
    private MethodOfTravelDao methodOfTravelDao;
    
    /** Holds instance of StudentDao. */
    @Autowired
    private StudentDao studentDao;
    
    /** Holds instance of SchoolClassDao. */
    @Autowired
    private SchoolClassDao schoolClassDao;
    
    /** Holds instance of GradeDao. */
    @Autowired
    private GradeDao gradeDao;
    
    /** Holds instance of ClassGradeDao. */
    @Autowired
    private ClassGradeDao classGradeDao;
    
    /** Holds instance of StudentClassInfoDao. */
    @Autowired
    private StudentClassInfoDao studentClassInfoDao;
    
    /** Holds instance of SubjectDao. */
    @Autowired
    private SubjectDao subjectDao;
    
    /** Holds instance of GradeSubjectDao. */
    @Autowired
    private GradeSubjectDao gradeSubjectDao;
    
    /** Holds instance of StudentTermMarkDao. */
    @Autowired
    private StudentTermMarkDao studentTermMarkDaoRef;
    
    /** Holds instance of SpecialEventsDao. */
    @Autowired
    private SpecialEventsDao specialEventsDao;
    
    /** Holds instance of ParticipantCategoryDao. */
    @Autowired
    private ParticipantCategoryDao participantCategoryDao;
    
    /** Holds instance of SpecialEventsParticipationDao. */
    @Autowired
    private SpecialEventsParticipationDao specialEventsParticipationDao;
    
    /** Defines the StudentDiscipline type object. */
    private StudentDiscipline studentDis = new StudentDiscipline();
    
    /** Defines the WarningLevel type object. */
    private WarningLevel warnLevel = new WarningLevel();
    
    /** Defines a Date type object and initialized into current date. */
    private java.sql.Date dt = new java.sql.Date(System.currentTimeMillis());
    
    /** Defines Student type object. */
    private Student std;
    
    /** Defines WarningLevel type object. */
    private WarningLevel wl;
    
    /**
     * Instantiate StudentSearchCritiria.
     */
    private StudentSearchCritiria critiria;
    
    /**
     * Represents an instance of BloodGroup.
     */
    private BloodGroup bloodGroup;
    
    /**
     * Represents an instance of Nationality.
     */
    private Nationality nationality;
    
    /**
     * Represents an instance of StudentClassInfo.
     */
    private StudentClassInfo studentClassInfo;
    
    /**
     * Represents an instance of UserLogin.
     */
    private UserLogin userLogin;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private UserLoginDao userLoginDao;
    
    /**
     * studentTermMarkList to hold returned results list.
     */
    private List<StudentTermMark> studentTermMarkList;
    
    /**
     * average to hold average of a single student per term.
     */
    private float average;
    
    /**
     * studentTermMark to hold single student term mark instance.
     */
    private StudentTermMark studentTermMark;
    
    /**
     * Defines GradeSubject type object.
     */
    private GradeSubject gradeSubject;
    
    /**
     * Defines MeritAward type object.
     */
    private MeritAward meritAward;
    
    /** Holds the instance of Student. */
    private Student student;
    
    /** Defines SQL Date type object with current date and time. */
    private java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
    
    /** Defines Term type object. */
    private Term term;
    
    /** Holds the instance of Province. */
    private Province province;
    
    /** Holds the instance of District. */
    private District district;
    
    /** Holds the instance of City. */
    private City city;
    
    /** Holds the instance of Religion. */
    private Religion religion;
    
    /** Holds the instance of BloodGroup. */
    private BloodGroup bloodGroup1;
    
    /** Holds the instance of Nationality. */
    private Nationality nationality1;
    
    /** Holds the instance of House. */
    private House house;
    
    /** Holds the instance of studyMedium. */
    private StudyMedium studyMedium;
    
    /** Holds the instance of MethodOfTravel. */
    private MethodOfTravel methodOfTravel;
    
    /** Holds the instance of SchoolClass. */
    private SchoolClass schoolClass;
    
    /** Holds the instance of Grade. */
    private Grade grade;
    
    /** Holds the instance of ClassGrade. */
    private ClassGrade classGrade;
    
    /** Holds the instance of Subject. */
    private Subject subject;
    
    /** Holds the instance of SpecialEvents. */
    private SpecialEvents specialEvents;
    
    /** Holds the instance of ParticipantCategory. */
    private ParticipantCategory participantCategory;
    
    /** Holds the instance of ParticipantCategory. */
    private SpecialEventsParticipation specialEventsParticipation;
    
    /**
     * @throws Exception java.lang.Exception
     */
    @Before
    public final void setUp() throws Exception {
    
        final int maxNumber = 50;
        critiria = new StudentSearchCritiria();
        critiria.setLastName("Ranasinghe");
        critiria.setGrade("1");
        critiria.setAdmissionNumber("1991-3200");
        critiria.setMaxNumber(maxNumber);
        critiria.setStartFrom(0);
        
        std = new Student(1);
        wl = new WarningLevel(1);
        
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
        
        studentDis.setStudentId(std.getStudentId());
        studentDis.setWarningLevelId(wl.getWarningLevelId());
        studentDis.setComment("TestingService");
        studentDis.setInformedtoParent(true);
        studentDis.setUserLoginId(userLogin.getUserLoginId());
        studentDis.setModifiedTime(new Date());
        studentDis.setDate(new Date());
        
        warnLevel.setDescription("Bad d");
        warnLevel.setColor("Red d ");
        warnLevel.setModifiedTime(dt);
        
        // Instantiates a BloodGroup object.
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("A test SSI123556");
        bloodGroup.setModifiedTime(new Date());
        
        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("Sri Lankan test SSI123556");
        nationality.setModifiedTime(new Date());
        
        // ///////////////
        term = new Term();
        term.setDescription("Term description test SSI123556");
        term.setFromMonth(new Date());
        term.setToMonth(new Date());
        
        termDAO.save(term);
        
        // Instantiate a Province object.
        province = new Province();
        province.setDescription("Western Province Test SSI12556");
        province.setModifiedTime(new Date());
        
        province = provinceDao.save(province);
        
        // Instantiates a District object.
        district = new District();
        district.setDescription("District test SSI12556");
        district.setProvince(province);
        district.setModifiedTime(new Date());
        
        district = districtDao.save(district);
        
        // Instantiates a City object.
        city = new City();
        city.setDescription("City test SSI12556");
        city.setDistrict(district);
        city.setModifiedTime(new Date());
        
        city = cityDao.save(city);
        
        // Instantiates a Religion object.
        religion = new Religion();
        religion.setDescription("Religion test SSI12556");
        religion.setModifiedTime(new Date());
        
        religion = religionDao.save(religion);
        
        // Instantiates a BloodGroup object.
        bloodGroup1 = new BloodGroup();
        bloodGroup1.setDescription("AAAA test SSI12556");
        bloodGroup1.setModifiedTime(new Date());
        
        bloodGroup1 = bloodGroupDao.save(bloodGroup1);
        
        // Instantiates a Nationality object.
        nationality1 = new Nationality();
        nationality1.setDescription("Nationality test SSI12556");
        nationality1.setModifiedTime(new Date());
        
        nationality1 = nationalityDao.save(nationality1);
        
        // Instantiates a House object.
        house = new House();
        house.setName("House test SSI12556");
        house.setModifiedTime(new Date());
        
        house = houseDao.save(house);
        
        // Instantiates a Language object.
        studyMedium = new StudyMedium();
        studyMedium.setStudyMediumName("language test SSI12556");
        studyMedium.setModifiedTime(new Date());
        
        studyMedium = studyMediumDao.save(studyMedium);
        
        // Instantiates a Method of travel object.
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("method of travel test SSI12556");
        methodOfTravel.setModifiedTime(new Date());
        
        methodOfTravel = methodOfTravelDao.save(methodOfTravel);
        
        // Instantiates ScoolClass object.
        schoolClass = new SchoolClass();
        schoolClass.setDescription("ScoolClass test SSI12556");
        schoolClass.setModifiedTime(new Date());
        
        schoolClass = schoolClassDao.save(schoolClass);
        
        // Instantiates Grade object.
        grade = new Grade();
        grade.setDescription("Grade test SSI12556");
        grade.setModifiedTime(new Date());
        
        grade = gradeDao.save(grade);
        
        // Instantiates student object.
        student = new Student();
        student.setAdmissionNo("student test SSI1255666*");
        student.setNameWtInitials("student Name with initials test");
        student.setLastName("student last name test");
        student.setAddress("adress test");
        student.setFullName("student full name test");
        student.setModifiedTime(new Date());
        student.setLanguageId(studyMedium.getStudyMediumId());
        student.setReligionId(religion.getReligionId());
        student.setTravelId(methodOfTravel.getTravelId());
        student.setBloodGroupId(bloodGroup1.getBloodGroupId());
        student.setHouseId(house.getHouseId());
        student.setCityId(city.getCityId());
        student.setNationalityId(nationality1.getNationalityId());
        student.setGender('M');
        student.setIsOldBoy(false);
        student.setDateOfBirth(new Date());
        student.setAdmissionDate(new Date());
        student.setFirstSchoolDay(new Date());
        student = studentDao.save(student);
        
        // Instantiates classGrade object.
        classGrade = new ClassGrade();
        classGrade.setSchoolClass(schoolClass);
        classGrade.setGrade(grade);
        classGrade.setDescription("ClassGrade SSI12556");
        classGrade.setModifiedTime(new Date());
        
        classGrade = classGradeDao.save(classGrade);
        
        String year = DateUtil.getStringYear(new Date()) + START_YEAR;
        Date dateYear = DateUtil.getParseDate(year);
        // Instantiates studentClassInfo object.
        studentClassInfo = new StudentClassInfo();
        studentClassInfo.setStudent(student);
        studentClassInfo.setClassGrade(classGrade);
        studentClassInfo.setModifiedTime(new Date());
        studentClassInfo.setYear(dateYear);
        studentClassInfo.setCheckMonitor(false);
        
        studentClassInfo = studentClassInfoDao.save(studentClassInfo);
        
        // Instantiates ScoolClass object.
        subject = new Subject();
        subject.setDescription("Subject test SSI12556");
        subject.setModifiedTime(new Date());
        
        subject = subjectDao.save(subject);
        assertNotNull("New Subject should not be null " + subject);
        
        // Instantiates GradeSubject object.
        gradeSubject = new GradeSubject();
        gradeSubject.setGrade(grade);
        gradeSubject.setSubject(subject);
        gradeSubject.setSubjectCode("SubjectCode5");
        gradeSubject.setModifiedTime(new Date());
        
        gradeSubject = gradeSubjectDao.save(gradeSubject);
        assertNotNull("New GradeSubject should not be null " + gradeSubject);
        
        // ///////////////
        
        final int mark = 75;
        this.average = mark;
        
        // term = new Term(1);
        meritAward = new MeritAward(1);
        
        studentTermMarkList = new ArrayList<StudentTermMark>();
        
        studentTermMark = new StudentTermMark();
        
        studentTermMark.setAbsent(false);
        studentTermMark.setComments("The comment5 56");
        studentTermMark.setGradeSubjectId(gradeSubject.getGradeSubjectId());
        studentTermMark.setMarks(mark);
        studentTermMark.setMeritAward(meritAward);
        studentTermMark.setModifiedTime(date);
        studentTermMark.setStudentClassInfoId(studentClassInfo.getStudentClassInfoId());
        studentTermMark.setTermId(term.getTermId());
        studentTermMark.seteditMarkState(false);
        
        studentTermMark = studentTermMarkDaoRef.save(studentTermMark);
        studentTermMarkList.add(studentTermMark);
        
        studentTermMarkList.get(0);
        
        participantCategory = new ParticipantCategory();
        participantCategory.setDescription("descriptionRef SSI 12556");
        participantCategory.setModifiedTime(new Date());
        ParticipantCategory newParticipantCategory = participantCategoryDao.save(participantCategory);
        
        specialEvents = new SpecialEvents();
        specialEvents.setDate(new Date());
        specialEvents.setDescription("descriptionRef SSI 12556");
        specialEvents.setModifiedTime(new Date());
        specialEvents.setName("nameRef SSI 123456");
        specialEvents.setParticipantCategory(newParticipantCategory);
        specialEvents = specialEventsDao.save(specialEvents);
        
        specialEventsParticipation = new SpecialEventsParticipation();
        specialEventsParticipation.setClassGrade(classGrade);
        specialEventsParticipation.setModifiedTime(new Date());
        specialEventsParticipation.setSpecialEvents(specialEvents);
        
    }
    
    /**
     * Setting the initialized objects to null.
     * 
     * @throws AkuraAppException akura Exception.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            if (specialEventsParticipation.getSpecialEventsParticipationId() > 0) {
                specialEventsParticipationDao.delete(specialEventsParticipation);
            }
            if (specialEvents.getSpecialEventsId() > 0) {
                specialEventsDao.delete(specialEvents);
            }
            if (participantCategory.getParticipantCategoryId() > 0) {
                participantCategoryDao.delete(participantCategory);
            }
            
            if (studentTermMark.getStudentTermMarkId() > 0) {
                studentTermMarkDaoRef.delete(studentTermMark);
            }
            if (studentClassInfo.getStudentClassInfoId() > 0) {
                studentClassInfoDao.delete(studentClassInfo);
            }
            if (student.getStudentId() > 0) {
                studentDao.delete(student);
            }
            if (studyMedium.getStudyMediumId() > 0) {
                studyMediumDao.delete(studyMedium);
            }
            if (religion.getReligionId() > 0) {
                religionDao.delete(religion);
            }
            if (methodOfTravel.getTravelId() > 0) {
                methodOfTravelDao.delete(methodOfTravel);
            }
            if (bloodGroup1.getBloodGroupId() > 0) {
                bloodGroupDao.delete(bloodGroup1);
            }
            if (house.getHouseId() > 0) {
                houseDao.delete(house);
            }
            if (city.getCityId() > 0) {
                cityDao.delete(city);
            }
            if (district.getDistrictId() > 0) {
                districtDao.delete(district);
            }
            if (province.getProvinceId() > 0) {
                provinceDao.delete(province);
            }
            if (nationality1.getNationalityId() > 0) {
                nationalityDao.delete(nationality1);
            }
            if (classGrade.getClassGradeId() > 0) {
                classGradeDao.delete(classGrade);
            }
            if (schoolClass.getClassId() > 0) {
                schoolClassDao.delete(schoolClass);
            }
            if (gradeSubject.getGradeSubjectId() > 0) {
                gradeSubjectDao.delete(gradeSubject);
            }
            if (grade.getGradeId() > 0) {
                gradeDao.delete(grade);
            }
            if (subject.getSubjectId() > 0) {
                subjectDao.delete(subject);
            }
            if (term.getTermId() > 0) {
                termDAO.delete(term);
            }
            
            if (studentDis.getStudentDisciplineId() > 0) {
                studentService.deleteStudentDisciplineInfo(studentDis);
            }
            if (warnLevel.getWarningLevelId() > 0) {
                commonService.deleteWarningLevelsInfo(warnLevel);
            }
            if (bloodGroup.getBloodGroupId() > 0) {
                commonService.deleteBloodGroup(bloodGroup);
            }
            if (nationality.getNationalityId() > 0) {
                commonService.deleteNationality(nationality);
            }
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.akura.student.service# StudentSearch()}.
     * 
     * @throws AkuraAppException when searching students.
     */
    @Test
    public final void testStudentSearch() throws AkuraAppException {
    
        List<Object> studentList = studentService.studentSearch(critiria);
        assertNotNull("Student list should not null ", studentList);
    }
    
    // ------ Student Character Related Test methods ------------//
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student.service.StudentServiceImpl# addStudentDisciplineInfo (com.virtusa.akura.api.dto.StudentDiscipline)}
     * .
     * 
     * @throws AkuraAppException akura Exception.
     */
    @Test
    public void testAddStudentDiscipline() throws AkuraAppException {
    
        try {
            StudentDiscipline newStudentDis = this.studentService.addStudentDisciplineInfo(studentDis);
            assertNotNull("New studentDiscipline should not be null " + newStudentDis);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student.service.StudentServiceImpl# editStudentDisciplineInfo (com.virtusa.akura.api.dto.StudentDiscipline)}
     * .
     * 
     * @throws AkuraAppException akura Exception.
     */
    
    @Test
    public void testEditStudentDiscipline() throws AkuraAppException {
    
        try {
            StudentDiscipline newStudentDis = this.studentService.addStudentDisciplineInfo(studentDis);
            assertNotNull("New Student Discipline should not be null " + newStudentDis);
            studentDis.setComment("NewTestingService");
            this.studentService.editStudentDisciplineInfo(newStudentDis);
            StudentDiscipline newStudent =
                    this.studentService.viewStudentDisciplineInfoById(newStudentDis.getStudentDisciplineId());
            assertEquals("NewTestingService", newStudent.getComment());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student.service.StudentServiceImpl# viewStudentDisciplineInfo (com.virtusa.akura.api.dto.Student)}
     * .
     * 
     * @throws AkuraAppException akura Exception.
     */
    
    @Test
    public void testViewStudentDisciplineByStudent() throws AkuraAppException {
    
        try {
            StudentDiscipline newStudentDis = studentService.addStudentDisciplineInfo(studentDis);
            assertNotNull("New Student Discipline should not be null " + newStudentDis);
            List<StudentDiscipline> studentDisList =
                    studentService.viewStudentDisciplineInfo(newStudentDis.getStudentId());
            assertNotNull(studentDisList.size());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student.service.StudentServiceImpl# viewStudentDisciplineInfo (com.virtusa.akura.api.dto.Student)}
     * .
     * 
     * @throws AkuraAppException akura Exception.
     */
    
    @Test
    public void testViewStudentDisciplineByStudentIdAndUserLoginId() throws AkuraAppException {
    
        try {
            StudentDiscipline newStudentDis = studentService.addStudentDisciplineInfo(studentDis);
            assertNotNull("New Student Discipline should not be null " + newStudentDis);
            List<StudentDiscipline> studentDisList =
                    studentService.viewStudentDisciplineInfoByUserLoginId(newStudentDis.getStudentId(),
                            userLogin.getUserLoginId());
            assertNotNull(studentDisList.size());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student.service.StudentServiceImpl# viewAllStudentDisciplineInfo()}.
     * 
     * @throws AkuraAppException akura Exception.
     */
    
    @Test
    public void testViewAllStudentDisciplineInfo() throws AkuraAppException {
    
        try {
            StudentDiscipline newStudentDis = studentService.addStudentDisciplineInfo(studentDis);
            assertNotNull("New Student Discipline should not be null " + newStudentDis);
            List<StudentDiscipline> studentDisList = studentService.viewAllStudentDisciplineInfo();
            assertNotNull(studentDisList.size());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student.service.CommonServiceImpl# addWarningLevelsInfo (com.virtusa.akura.api.dto.WarningLevel)}
     * .
     * 
     * @throws AkuraAppException akura Exception.
     */
    
    @Test
    public void testAddWarningLevel() throws AkuraAppException {
    
        try {
            WarningLevel newWarnLevel = commonService.addWarningLevelsInfo(warnLevel);
            assertNotNull("New warning level object can not be null" + newWarnLevel);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student.service.CommonServiceImpl# editWarningLevelInfo (com.virtusa.akura.api.dto.WarningLevel)}
     * .
     * 
     * @throws AkuraAppException akura Exception.
     */
    
    @Test
    public void testEditWarningLevel() throws AkuraAppException {
    
        try {
            WarningLevel newWarnLevel = commonService.addWarningLevelsInfo(warnLevel);
            assertNotNull("New warning level object can not be null" + newWarnLevel);
            warnLevel.setDescription("Good");
            commonService.editWarningLevelInfo(newWarnLevel);
            WarningLevel newWarnL = commonService.viewWarnLevelInfoById(newWarnLevel.getWarningLevelId());
            assertEquals("Good", newWarnL.getDescription());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.akura.student.service.StudentServiceImpl# viewAllWarningLevelInfo()}
     * .
     * 
     * @throws AkuraAppException akura Exception.
     */
    
    @Test
    public void testViewAllWarningLevels() throws AkuraAppException {
    
        try {
            WarningLevel newWarnLevel = commonService.addWarningLevelsInfo(warnLevel);
            assertNotNull("New warning level object can not be null" + newWarnLevel);
            List<WarningLevel> warnLevelList = commonService.viewAllWarningLevelInfo();
            assertNotNull(warnLevelList.size());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    // ------ End of Student Character Related Test methods ------------//
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student.service.CommonServiceImpl#addBloodGroup(com.virtusa.akura.api.dto.BloodGroup)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    
    @Test
    public void testAddBloodGroup() throws AkuraAppException {
    
        try {
            bloodGroup = commonService.addBloodGroup(bloodGroup);
            assertNotNull("BloodGroup Type object should not null ", bloodGroup);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student.service.CommonServiceImpl#addNationality(com.virtusa.akura.api.dto.Nationality)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    
    @Test
    public void testAddNationality() throws AkuraAppException {
    
        try {
            nationality = commonService.addNationality(nationality);
            assertNotNull("Nationality type Object should not null ", nationality);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student.service.CommonServiceImpl#findBloodGroup(java.lang.Integer)}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    
    @Test
    public void testFindBloodGroup() throws AkuraAppException {
    
        try {
            bloodGroup = commonService.addBloodGroup(bloodGroup);
            bloodGroup = (BloodGroup) commonService.findBloodGroup(bloodGroup.getBloodGroupId());
            assertNotNull("BloodGroup type object should not null ", bloodGroup);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student.service.CommonServiceImpl#findNationality(java.lang.Integer)}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    
    @Test
    public void testFindNationality() throws AkuraAppException {
    
        try {
            nationality = commonService.addNationality(nationality);
            assertNotNull(nationality);
            nationality.setDescription("Mathematics");
            commonService.updateNationality(nationality);
            Nationality updateNationality = (Nationality) commonService.findNationality(nationality.getNationalityId());
            assertEquals(nationality.getDescription(), updateNationality.getDescription());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.akura.student.service.CommonServiceImpl#getBloodGroupList()}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    
    @Test
    public void testGetBloodGroupList() throws AkuraAppException {
    
        try {
            bloodGroup = commonService.addBloodGroup(bloodGroup);
            List<BloodGroup> bloodGroupList = commonService.getBloodGroupList();
            assertTrue(bloodGroupList.size() > 0);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.akura.student. service.CommonServiceImpl#getNationalityList()}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    
    @Test
    public void testGetNationalityList() throws AkuraAppException {
    
        try {
            nationality = commonService.addNationality(nationality);
            assertNotNull(nationality);
            List<Nationality> nationalityList = commonService.getNationalityList();
            assertTrue(nationalityList.size() > 0);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student. service.CommonServiceImpl# updateBloodGroup(com.virtusa.akura.api.dto.BloodGroup)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    
    @Test
    public void testUpdateBloodGroup() throws AkuraAppException {
    
        try {
            bloodGroup = commonService.addBloodGroup(bloodGroup);
            bloodGroup.setDescription("Mathematics");
            commonService.updateBloodGroup(bloodGroup);
            BloodGroup updateBloodGroup = (BloodGroup) commonService.findBloodGroup(bloodGroup.getBloodGroupId());
            assertEquals(bloodGroup.getDescription(), updateBloodGroup.getDescription());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.akura.student.service.CommonServiceImpl# updateNationality (com.virtusa.akura.api.dto.Nationality)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    
    @Test
    public void testUpdateNationality() throws AkuraAppException {
    
        try {
            nationality = commonService.addNationality(nationality);
            assertNotNull(nationality);
            nationality.setDescription("Mathematics");
            commonService.updateNationality(nationality);
            Nationality updateNationality = (Nationality) commonService.findNationality(nationality.getNationalityId());
            assertEquals(nationality.getDescription(), updateNationality.getDescription());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /*-------------------------------- StudentTermMarkService related tests -------------------------------- */
    /**
     * Test method for {@link com.virtusa.akura.student.service.StudentTermMarkServiceImpl#getAverage(}).
     * 
     * @throws Exception throws exception.
     */
    @Test
    public void testGetAverage() throws Exception {
    
        final int averageMark = 75;
        assertTrue(this.average == averageMark);
    }
    
/**
     * Test method for {@link com.virtusa.akura.student.service.StudentTermMarkServiceImpl#setAverage()).
     * 
     * @throws Exception throws exception.
     */
    @Test
    public void testSetAverage() throws Exception {
    
        final int averageMark = 70;
        this.average = averageMark;
        assertTrue(this.average == averageMark);
    }
    
/**
     * Test method for {@link com.virtusa.akura.student.service.StudentTermMarkServiceImpl#calculateAverage()).
     * 
     * @throws Exception throws exception.
     * 
     */
    
    @Test
    public void testCalculateAverage() throws Exception {
    
        int termId = term.getTermId();
        int classInfoId = studentClassInfo.getStudentClassInfoId();
        final float averageMark = 75;
        float total = 0;
        int i = 0;
        // Iterator<StudentTermMark> iterator = studentTermMarkList.iterator();
        while (i < studentTermMarkList.size()) {
            if (studentTermMarkList.get(i).getTermId() == termId
                    && studentTermMarkList.get(i).getStudentClassInfoId() == classInfoId) {
                total += studentTermMarkList.get(i).getMarks();
            }
            i++;
        }
        assertEquals(averageMark, total / i);
    }
    
/**
     * Test method for {@link com.virtusa.akura.student.service.StudentTermMarkServiceImpl#getAllSubjectMarks()).
     * 
     * @throws Exception throws exception.
     */
    
    @Test
    public void testGetAllSubjectMarks() throws Exception {
    
        studentTermMarkList = this.studentService.getAllSubjectMarks();
        assertNotNull(studentTermMarkList);
    }
    
/**
     * Test method for {@link com.virtusa.akura.student.service.StudentTermMarkServiceImpl#getSelectedSubjectMarks()).
     * 
     * @throws Exception throws exception.
     */
    // ///
    /*
     * @Test public void testGetSelectedSubjectMarks() throws Exception { studentTermMarkList =
     * this.studentService.getSelectedSubjectMarks(term.getTermId()); assertNotNull(studentTermMarkList); }
     */
    // //////////////////
    
/**
     * Test method for {@link com.virtusa.akura.student.service.StudentTermMarkServiceImpl
     * #getSelectedSubjectMarksByGrade()).
     * 
     * @throws Exception throws exception.
     */
    
    @Test
    public void testGetSelectedSubjectMarksByGrade() throws Exception {
    
        studentTermMarkList =
                this.studentService.getSelectedSubjectMarksByGrade(term.getTermId(), classGrade.getClassGradeId());
        assertNotNull(studentTermMarkList);
    }
    
/**
     * Test method for {@link com.virtusa.akura.student.service.StudentTermMarkServiceImpl#setAllSubjectMarks()).
     * 
     * @throws Exception throws exception.
     */
    
    @Test
    public void testSetAllSubjectMarks() throws Exception {
    
        assertNotNull(studentTermMarkList);
        studentService.setAllSubjectMarks(studentTermMarkList);
    }
    
/**
     * Test method for {@link com.virtusa.akura.student.service.StudentTermMarkServiceImpl#setSubjectMark()).
     * 
     * @throws Exception throws exception.
     */
    
    @Test
    public void testSetSubjectMark() throws Exception {
    
        final int marks = 40;
        Term term1 = new Term();
        term1.setDescription("Term description test SSI4567");
        term1.setFromMonth(new Date());
        term1.setToMonth(new Date());
        
        termDAO.save(term1);
        
        StudentTermMark studentTermMark1 = new StudentTermMark();
        
        studentTermMark1.setAbsent(false);
        studentTermMark1.setComments("The comment ");
        studentTermMark1.setGradeSubjectId(gradeSubject.getGradeSubjectId());
        studentTermMark1.setMarks(marks);
        studentTermMark1.setMeritAward(meritAward);
        studentTermMark1.setModifiedTime(date);
        studentTermMark1.setStudentClassInfoId(studentClassInfo.getStudentClassInfoId());
        studentTermMark1.setTermId(term1.getTermId());
        
        assertNotNull(studentTermMark1);
        studentService.setSubjectMark(studentTermMark1);
        
        if (studentTermMark1.getStudentTermMarkId() > 0) {
            studentTermMarkDaoRef.delete(studentTermMark1);
        }
        if (term1.getTermId() > 0) {
            termDAO.delete(term1);
        }
        
    }
    
    /**
     * Test method for {@link
     * com.virtusa.akura.student.service.StudentTermMarkServiceImpl#deleteSubjectMarks(} ).
     * 
     * @throws Exception throws exception.
     */
    
    @Test
    public void testDeleteSubjectMarks() throws Exception {
    
        final int marks = 40;
        Term term1 = new Term();
        term1.setDescription("Term description test SSI4567");
        term1.setFromMonth(new Date());
        term1.setToMonth(new Date());
        
        termDAO.save(term1);
        
        StudentTermMark studentTermMark1 = new StudentTermMark();
        
        studentTermMark1.setAbsent(false);
        studentTermMark1.setComments("The comment ");
        studentTermMark1.setGradeSubjectId(gradeSubject.getGradeSubjectId());
        studentTermMark1.setMarks(marks);
        studentTermMark1.setMeritAward(meritAward);
        studentTermMark1.setModifiedTime(date);
        studentTermMark1.setStudentClassInfoId(studentClassInfo.getStudentClassInfoId());
        studentTermMark1.setTermId(term1.getTermId());
        
        assertNotNull(studentTermMark);
        studentService.deleteSubjectMarks(studentTermMark1);
        
        if (term1.getTermId() > 0) {
            termDAO.delete(term1);
        }
    }
    
/**
     * Test method for {@link com.virtusa.akura.student.service.StudentTermMarkServiceImpl#editSubjectMarks()).
     * 
     * @throws Exception throws exception.
     */
    
    @Test
    public void testEditSubjectMarks() throws Exception {
    
        assertNotNull(studentTermMark);
        studentService.editSubjectMarks(studentTermMark);
    }
    
    /*-------------------------------- End of StudentTermMarkService related tests -------------------------------- */
    
    // student Seminar related test
    
    /**
     * this method is test saveStudetnSeminar()and deleteStudentSeminar()methods in studentService class.
     * 
     * @throws Exception -when error occurs
     */
    
    @Test
    public void saveStudetnSeminarTest() throws Exception {
    
        // to work properly it is nessory to have at least one record of each
        // Student and Seminar table
        
        List<Seminar> seminarList = studentService.getAllSeminars();
        
        List<Student> studentList = studentService.getStudentList();
        if (seminarList != null && studentList != null && !studentList.isEmpty() && !seminarList.isEmpty()) {
            System.out.println("--------testing---------");
            
            // set up
            StudentSeminar ssObject = new StudentSeminar();
            ssObject.setDescription("seminar desc test");
            ssObject.setStudentId(studentList.get(0).getStudentId());
            ssObject.setSeminar(seminarList.get(0));
            ssObject.setYear(new Date());
            // test saveStudetnSeminar
            
            studentService.saveStudetnSeminar(ssObject);
            assertTrue("saved StudentSeminar must have id >0", ssObject.getStudentSeminarId() > 0);
            // test deleteStudentSeminar()
            
            try {
                studentService.deleteStudentSeminar(ssObject.getStudentSeminarId());
            } catch (AkuraAppException e) {
                assertTrue("Exceptin in deleting ", false);
                throw e;
            }
        } else {
            System.out.println("Plese add atleast one record to Student and Seminar Table");
        }
    }
    
    /**
     * this method is test getAllStudentSeminars(,) in studentService class.
     * 
     * @throws Exception -when error occurs
     */
    
    @Test
    public void getAllStudentSeminarsTest() throws Exception {
    
        // to work properly it is nessory to have at least one record of each
        // Student and Seminar table
        
        List<Seminar> seminarList = studentService.getAllSeminars();
        List<Student> studentList = studentService.getStudentList();
        
        if (seminarList != null && studentList != null && !studentList.isEmpty() && !seminarList.isEmpty()) {
            
            // set up
            Date now = new Date();
            StudentSeminar ssObject = new StudentSeminar();
            ssObject.setDescription("seminar desc test");
            ssObject.setStudentId(studentList.get(0).getStudentId());
            ssObject.setSeminar(seminarList.get(0));
            ssObject.setYear(now);
            studentService.saveStudetnSeminar(ssObject);
            System.out.println("--------testing---------");
            // Test getAllStudentSeminars
            
            List<StudentSeminar> list = studentService.getAllStudentSeminars(studentList.get(0).getStudentId(), now);
            assertNotNull("list Should not null", list);
            assertTrue("list should have at least one eliment", !list.isEmpty());
            // Delete form table
            studentService.deleteStudentSeminar(ssObject.getStudentSeminarId());
        } else {
            System.out.println("Plese add atleast one record to Student and Seminar Tables");
        }
    }
    
    /**
     * Test method for getAllocatedStudentListForEvent method in StudentServiceImpl.
     * 
     * @throws AkuraAppException throws when exception occurs
     */
    @Test
    public void testGetAllocatedStudentListForEvent() throws AkuraAppException {
    
        SpecialEventsParticipation newSpecialEventsParticipation =
                specialEventsParticipationDao.save(specialEventsParticipation);
        
        String year = DateUtil.getStringYear(specialEvents.getDate()) + START_YEAR;
        Date dateYear = DateUtil.getParseDate(year);
        
        List<StudentSpecialEventParticipationView> list =
                studentService.getAllocatedStudentListForEvent(
                        newSpecialEventsParticipation.getSpecialEventsParticipationId(), dateYear);
        
        assertTrue(list.size() > 0);
    }
    
    /**
     * Test method for getSubjectAverageTermMarksById method in StudentServiceImpl.
     * 
     * @throws AkuraAppException throws when exception occurs
     */
    @Test
    public void testGetSubjectAverageTermMarksById() throws AkuraAppException {
    
        final double expectedAverage = 75.0;
        SubjectAverageTermMarks subjectAverageTermMarks =
                studentService.getSubjectAverageTermMarksById(classGrade.getDescription(),
                        DateUtil.getYearFromDate(studentClassInfo.getYear()), gradeSubject.getGradeSubjectId(),
                        term.getDescription());
        
        assertEquals(expectedAverage, subjectAverageTermMarks.getAverage());
        
    }
    
    /**
     * Test method for getTermMarksByTermGradeYear method in StudentServiceImpl.
     * 
     * @throws AkuraAppException if exception occurs
     */
    @Test
    public void getTermMarksByTermGradeYear() throws AkuraAppException {
    
        String year = DateUtil.getStringYear(studentClassInfo.getYear());
        List<StudentTermMarkDTO> list =
                studentService.getTermMarksByTermGradeYear(classGrade.getClassGradeId(), term.getTermId(), year);
        assertTrue(list.size() > 0);
    }
    
    /**
     * Test method for getStudentTermMarksRank method in StudentServiceImpl.
     * 
     * @throws AkuraAppException - if exception occurs
     */
    @Test
    public void testGetStudentTermMarksRank() throws AkuraAppException {
    
        StudentTermMarksRank studentTermMarksRank =
                studentService.getStudentTermMarksRank(studentClassInfo.getStudentClassInfoId(), term.getTermId());
        
        assertTrue(studentTermMarksRank.getRank() == 1);
        
    }
    
    /**
     * Test method for findStudentAnnualGradeRank method in StudentServiceImpl.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testFindStudentAnnualGradeRank() throws AkuraAppException {
    
        final int noOFPrizes = 5;
        List<StudentAnnualGradeRank> list =
                studentService.findStudentAnnualGradeRank(grade.getGradeId(),
                        Integer.parseInt(DateUtil.getStringYear(studentClassInfo.getYear())), noOFPrizes);
        
        assertTrue(list.size() > 0);
    }
    
    /**
     * Test method for getStudentGradeSubjectRankList method in StudentServiceImpl.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testGetStudentGradeSubjectRankList() throws AkuraAppException {
    
        final int noOFPrizes = 5;
        
        List<StudentGradeSubjectRankView> list =
                studentService.getStudentGradeSubjectRankList(gradeSubject.getGradeSubjectId(),
                        Integer.parseInt(DateUtil.getStringYear(studentClassInfo.getYear())), noOFPrizes);
        assertTrue(list.size() > 0);
    }
    
    /**
     * Test method for findTermMarksByClassInfoIdAndGradeSubject method in StudentServiceImpl.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testFindTermMarksByClassInfoIdAndGradeSubject() throws AkuraAppException {
    
        List<StudentTermMarkDTO> list =
                studentService.findTermMarksByClassInfoIdAndGradeSubject(studentClassInfo.getStudentClassInfoId(),
                        gradeSubject.getGradeSubjectId());
        assertTrue(list.size() > 0);
    }
    
    /**
     * Test method for studentGradeRankListByStudentClassInfoId method in StudentServiceImpl.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void findStudentGradeRankListByStudentClassInfoId() throws AkuraAppException {
    
        List<StudentsGradeRankView> list =
                studentService.findTermWiseStudentTotalMarks(studentClassInfo.getStudentClassInfoId());
        
        assertTrue(list.size() > 0);
    }
    
    /**
     * Test method for getMediumWiseClassSubjectAverage method in StudentServiceImpl.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testGetMediumWiseClassSubjectAverage() throws AkuraAppException {
    
        MediumWiseClassSubjectAverageView mediumWiseClassSubjectAverageView =
                studentService.getMediumWiseClassSubjectAverage(classGrade.getClassGradeId(),
                        Integer.parseInt(DateUtil.getStringYear(studentClassInfo.getYear())),
                        gradeSubject.getGradeSubjectId(), term.getDescription(), studyMedium.getStudyMediumId());
        
        assertNotNull(mediumWiseClassSubjectAverageView);
        
    }
    
    /**
     * Test method for getStudyMediumsInClass method in StudentServiceImpl.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testGetStudyMediumsInClass() throws AkuraAppException {
    
        List<Integer> mediumIdList =
                studentService.getStudyMediumsInClass(classGrade.getClassGradeId(),
                        Integer.parseInt(DateUtil.getStringYear(studentClassInfo.getYear())), term.getDescription());
        
        assertTrue(mediumIdList.size() > 0);
    }
}
