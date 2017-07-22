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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.Term;
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
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.ReligionDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.common.dao.SubjectDao;
import com.virtusa.akura.common.dao.TermDao;
import com.virtusa.akura.student.BaseStudentTest;

/**
 * @author Virtusa Corporation
 */

public class StudentTermMarkDaoImplTest extends BaseStudentTest {

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

    /** Defines SchoolClass type object. */
    private SchoolClass schoolClass;

    /** Defines Grade type object. */
    private Grade grade;

    /** Defines ClassGrade type object. */
    private ClassGrade classGrade;

    /** Defines Subject type object. */
    private Subject subject;

    /** Defines Student type object. */
    private Student student;

    /** Defines StudentClassInfo type object. */
    private StudentClassInfo studentClassInfo;

    /** Defines GradeSubject type object. */
    private GradeSubject gradeSubject;

    /** Defines Term type object. */
    private Term term;

    /** Defines StudentTermMark type object. */
    private StudentTermMark studentTermMark;

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
    private SchoolClassDao schoolClassDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private GradeDao gradeDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private ClassGradeDao classGradeDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private SubjectDao subjectDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private StudentDao studentDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private StudentClassInfoDao studentClassInfoDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private GradeSubjectDao gradeSubjectDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private TermDao termDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private StudentTermMarkDao studentTermMarkDaoRef;


    /**
     * @throws AkuraAppException - Exception
     */
    @Before
    public final void setUp() throws AkuraAppException {
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

        // Instantiates ScoolClass object.
        schoolClass = new SchoolClass();
        schoolClass.setDescription("ScoolClass"+randomString);
        schoolClass.setModifiedTime(new Date());

        schoolClass = schoolClassDao.save(schoolClass);
        assertNotNull("New ScoolClass should not be null " + schoolClass);

        // Instantiates Grade object.
        grade = new Grade();
        grade.setDescription("Grade"+randomString);
        grade.setModifiedTime(new Date());

        grade = gradeDao.save(grade);
        assertNotNull("New Grade should not be null " + grade);

        // Instantiates ScoolClass object.
        subject = new Subject();
        subject.setDescription("Subject"+randomString);
        subject.setModifiedTime(new Date());

        subject = subjectDao.save(subject);
        assertNotNull("New Subject should not be null " + subject);

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

        // Instantiates classGrade object.
        classGrade = new ClassGrade();
        classGrade.setSchoolClass(schoolClass);
        classGrade.setGrade(grade);
        classGrade.setDescription("ClassGrade");
        classGrade.setModifiedTime(new Date());

        classGrade = classGradeDao.save(classGrade);
        assertNotNull("New ClassGrade should not be null " + classGrade);

        // Instantiates studentClassInfo object.
        studentClassInfo = new StudentClassInfo();
        studentClassInfo.setStudent(student);
        studentClassInfo.setClassGrade(classGrade);
        studentClassInfo.setModifiedTime(new Date());
        studentClassInfo.setYear(new Date());
        studentClassInfo.setCheckMonitor(false);

        studentClassInfo = studentClassInfoDao.save(studentClassInfo);
        assertNotNull("New StudentClassInfo should not be null " + studentClassInfo);

        // Instantiates GradeSubject object.
        gradeSubject = new GradeSubject();
        gradeSubject.setGrade(grade);
        gradeSubject.setSubject(subject);
        gradeSubject.setSubjectCode("SubjectCode");
        gradeSubject.setModifiedTime(new Date());

        gradeSubject = gradeSubjectDao.save(gradeSubject);
        assertNotNull("New GradeSubject should not be null " + gradeSubject);

        // Instantiates Term object.
        term = new Term();
        term.setDescription("Term");
        term.setFromMonth(new Date());
        term.setToMonth(new Date());
        term.setModifiedTime(new Date());

        term = termDao.save(term);
        assertNotNull("New Term should not be null " + term);

        // Instantiates StudentTermMark object.
        studentTermMark = new StudentTermMark();
        studentTermMark.setStudentClassInfoId(studentClassInfo.getStudentClassInfoId());
        studentTermMark.setGradeSubjectId(gradeSubject.getGradeSubjectId());
        studentTermMark.setComments("The comment ");
        studentTermMark.setTermId(term.getTermId());
        studentTermMark.setModifiedTime(new Date());

    }

    /**
     * Test method to check if a StudentTermMark object was successfully added to the db.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveStudentMark() throws AkuraAppException {

        try {
            StudentTermMark newTermMark = studentTermMarkDaoRef.save(studentTermMark);
            assertNotNull("Student Term Mark type should not be null " + newTermMark);

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.akura.student.dao.StudentTermMarkDaoImpl#update(}).
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateStudentMark() throws AkuraAppException {

        try {
            StudentTermMark newTermMark = studentTermMarkDaoRef.save(studentTermMark);
            assertNotNull("Student Term Mark type should not be null " + newTermMark);

            newTermMark.setComments("Updated Comment");
            studentTermMarkDaoRef.update(newTermMark);

            StudentTermMark findTermMark =
                    (StudentTermMark) studentTermMarkDaoRef.findById(StudentTermMark.class,
                            newTermMark.getStudentTermMarkId());
            assertEquals(studentTermMark.getComments(), findTermMark.getComments());

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.akura.student.dao.StudentTermMarkDaoImpl#saveAllSubjectMarks(List
     * listAllSubjectMarks}).
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveOrUpdateSubjectMarks() throws AkuraAppException {

        try {
            StudentTermMark newTermMark = studentTermMarkDaoRef.save(studentTermMark);
            assertNotNull("Student Term Mark type should not be null " + newTermMark);

            List<StudentTermMark> allSubjectMarks = new ArrayList<StudentTermMark>();
            allSubjectMarks.add(newTermMark);
            studentTermMarkDaoRef.saveOrUpdateAll(allSubjectMarks);

            List<StudentTermMark> findTermMarks = studentTermMarkDaoRef.findAll(StudentTermMark.class);
            assertTrue(findTermMarks.size() > 0);

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.akura.student.dao.StudentTermMarkDaoImpl#findAll().
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAllStudentTermMark() throws AkuraAppException {

        try {
            StudentTermMark newTermMark = studentTermMarkDaoRef.save(studentTermMark);
            assertNotNull("Student Term Mark type should not be null " + newTermMark);

            List<StudentTermMark> allSubjectMarks = studentTermMarkDaoRef.findAll(StudentTermMark.class);
            assertTrue(allSubjectMarks.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

/**
     * Test method for {@link com.virtusa.akura.student.dao.StudentTermMarkDaoImpl#findById().
     *
     * @throws AkuraAppException - the exception that occurred.
     */

    @Test
    public void testFindStudentMarkById() throws AkuraAppException {

        try {
            StudentTermMark newTermMark = studentTermMarkDaoRef.save(studentTermMark);
            assertNotNull("Student Term Mark type should not be null " + newTermMark);

            StudentTermMark findTermMark =
                    (StudentTermMark) studentTermMarkDaoRef.findById(StudentTermMark.class,
                            newTermMark.getStudentTermMarkId());
            assertNotNull("Find StudentTermMark should not be null ", findTermMark);

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }


    /**
     * Test method for {@link com.virtusa.akura.student.dao.StudentTermMarkDaoImpl#getSelectedSubjectMarksByGrade().
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetSelectedSubjectMarksByGrade() throws AkuraAppException {

        List<StudentTermMark> allSubjectMarks = studentTermMarkDaoRef.getSelectedSubjectMarksByGrade(1, 1);
        assertNotNull(allSubjectMarks);
    }

    /**
     * Tear down method for each test case.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {

        studentTermMarkDaoRef.delete(studentTermMark);

        studentClassInfoDao.delete(studentClassInfo);
        gradeSubjectDao.delete(gradeSubject);
        termDao.delete(term);

        studentDao.delete(student);
        classGradeDao.delete(classGrade);
        subjectDao.delete(subject);
        schoolClassDao.delete(schoolClass);
        gradeDao.delete(grade);

        cityDao.delete(city);
        districtDao.delete(district);
        provinceDao.delete(province);
        bloodGroupDao.delete(bloodGroup);
        nationalityDao.delete(nationality);
        religionDao.delete(religion);
        houseDao.delete(house);
        methodOfTravelDao.delete(methodOfTravel);
        languageDao.delete(language);

    }
}
