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

package com.virtusa.akura.reporting.controller;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

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
import com.virtusa.akura.api.dto.StudentMarkSheetTemplate;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.common.BaseWebTest;
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
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.service.CommonReportingService;
import com.virtusa.akura.reporting.validator.ClassWiseStudentMarksSheetValidator;
import com.virtusa.akura.student.dao.StudentClassInfoDao;
import com.virtusa.akura.student.dao.StudentDao;
import com.virtusa.akura.student.dao.StudentTermMarkDao;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;

/**
 * @author Virtusa Corporation
 */
public class ClassWiseStudentMarksSheetControllerTest extends BaseWebTest {
    
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
    
    /** Represents an instance of ModelMap. */
    private ModelMap modelMap;
    
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
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private CommonService commonService;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private StudentService studentService;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private ClassWiseStudentMarksSheetValidator classWiseStudentMarksSheetValidator;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private CommonReportingService commonReportingService;
    
    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;
    
    /** Represents an instance of response request. */
    private MockHttpServletResponse response;
    
    /** Holding instance of testing controller. */
    private ClassWiseStudentMarksSheetController controller;
    
    /** Holding model attribute of the form. */
    private StudentMarkSheetTemplate studentMarkSheetTemplate;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws AkuraAppException - Exception
     */
    @Before
    public final void setUp() throws AkuraAppException {
    
        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        
        // Instantiate a Province object.
        province = new Province();
        province.setDescription("Western Province1S*");
        province.setModifiedTime(new Date());
        
        province = provinceDao.save(province);
        assertNotNull(" province saved to db " + province);
        
        // Instantiates a District object.
        district = new District();
        district.setDescription("Gampaha district 1S*");
        district.setProvince(province);
        district.setModifiedTime(new Date());
        
        district = districtDao.save(district);
        assertNotNull(" district saved to db " + district);
        
        // Instantiates a City object.
        city = new City();
        city.setDescription("Dematagoda city 1S*");
        city.setDistrict(district);
        city.setModifiedTime(new Date());
        
        city = cityDao.save(city);
        assertNotNull(" city saved to db " + city);
        
        // Instantiates a Religion object.
        religion = new Religion();
        religion.setDescription("Religion for test 1S*");
        religion.setModifiedTime(new Date());
        
        religion = religionDao.save(religion);
        assertNotNull(" religion saved to db " + religion);
        
        // Instantiates a BloodGroup object.
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("blood group test 1S**");
        bloodGroup.setModifiedTime(new Date());
        
        bloodGroup = bloodGroupDao.save(bloodGroup);
        assertNotNull(" bloodGroup saved to db " + bloodGroup);
        
        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("Sri Lankan nationality 1S*");
        nationality.setModifiedTime(new Date());
        
        nationality = nationalityDao.save(nationality);
        assertNotNull(" nationality saved to db " + nationality);
        
        // Instantiates a House object.
        house = new House();
        house.setName("House test 1SS*");
        house.setModifiedTime(new Date());
        
        house = houseDao.save(house);
        assertNotNull(" house saved to db " + house);
        
        // Instantiates a Language object.
        language = new Language();
        language.setLanguage("Sinhala language test 1S*");
        language.setModifiedTime(new Date());
        
        language = languageDao.save(language);
        assertNotNull(" language saved to db " + language);
        
        // Instantiates a Method of travel object.
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("Bus method of travel test 1S*");
        methodOfTravel.setModifiedTime(new Date());
        
        methodOfTravel = methodOfTravelDao.save(methodOfTravel);
        assertNotNull(" methodOftravel saved to db " + methodOfTravel);
        
        // Instantiates ScoolClass object.
        schoolClass = new SchoolClass();
        schoolClass.setDescription("ScoolClass test 1S**");
        schoolClass.setModifiedTime(new Date());
        
        schoolClass = schoolClassDao.save(schoolClass);
        assertNotNull("New ScoolClass should not be null " + schoolClass);
        
        // Instantiates Grade object.
        grade = new Grade();
        grade.setDescription("Grade test 1SS*");
        grade.setModifiedTime(new Date());
        
        grade = gradeDao.save(grade);
        assertNotNull("New Grade should not be null " + grade);
        
        // Instantiates ScoolClass object.
        subject = new Subject();
        subject.setDescription("Subject test 1SS*");
        subject.setModifiedTime(new Date());
        
        subject = subjectDao.save(subject);
        assertNotNull("New Subject should not be null " + subject);
        
        // Instantiates student object.
        student = new Student();
        student.setAdmissionNo("A1123 test 1SS*");
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
        classGrade.setDescription("ClassGrade test 1SS*");
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
        gradeSubject.setSubjectCode("SubjectCode test 1SS*");
        gradeSubject.setModifiedTime(new Date());
        
        gradeSubject = gradeSubjectDao.save(gradeSubject);
        assertNotNull("New GradeSubject should not be null " + gradeSubject);
        
        // Instantiates Term object.
        term = new Term();
        term.setDescription("Term test 1SS*");
        term.setFromMonth(new Date());
        term.setToMonth(new Date());
        term.setModifiedTime(new Date());
        
        term = termDao.save(term);
        assertNotNull("New Term should not be null " + term);
        
        // Instantiates StudentTermMark object.
        studentTermMark = new StudentTermMark();
        studentTermMark.setStudentClassInfoId(studentClassInfo.getStudentClassInfoId());
        studentTermMark.setGradeSubjectId(gradeSubject.getGradeSubjectId());
        studentTermMark.setComments("The comment test 1SS*");
        studentTermMark.setTermId(term.getTermId());
        studentTermMark.setModifiedTime(new Date());
        studentTermMark.setMarks(0);
        
        studentTermMark = studentTermMarkDaoRef.save(studentTermMark);
        
        controller = new ClassWiseStudentMarksSheetController();
        controller.setCommonService(commonService);
        controller.setStudentService(studentService);
        controller.setClassWiseStudentMarksSheetValidator(classWiseStudentMarksSheetValidator);
        modelMap = new ModelMap();
        response = new MockHttpServletResponse();
        
        studentMarkSheetTemplate = new StudentMarkSheetTemplate();
        studentMarkSheetTemplate.setGrade(studentClassInfo.getClassGrade().getGrade());
        studentMarkSheetTemplate.setTerm(term);
        studentMarkSheetTemplate.setYear(DateUtil.getStringYear(studentClassInfo.getYear()));
    }
    
    /**
     * Test method for showStudentMarkSheetReportForm method in ClassWiseStudentMarksSheetController.
     */
    @Test
    public void testShowStudentMarkSheetReportForm() {
    
        String returnString = controller.showStudentMarkSheetReportForm(modelMap);
        assertEquals("reporting/classWiseStudentMarksSheet", returnString);
    }
    
    /**
     * Test method for loadClassList method in ClassWiseStudentMarksSheetController.
     * 
     * @throws AkuraAppException - throws if exception occurs
     */
    @Test
    public void testLoadClassList() throws AkuraAppException {
    
        String returnString = controller.loadClassList(modelMap, studentMarkSheetTemplate);
        assertEquals("reporting/classWiseStudentMarksSheet", returnString);
        
        assertTrue(modelMap.containsAttribute("studentMarkSheetTemplate"));
        assertTrue(modelMap.containsAttribute("classGradeList"));
    }
    
    /**
     * Test method for generateClassWiseStudentMarksSheetReport method in
     * ClassWiseStudentMarksSheetController.
     * 
     * @throws AkuraException throws if exception occurs
     */
    @Test
    public void testGenerateClassWiseStudentMarksSheetReport() throws AkuraException {
    
        studentMarkSheetTemplate.setClassGrade(studentClassInfo.getClassGrade());
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        ModelAndView modelAndView =
                controller.generateClassWiseStudentMarksSheetReport(response, studentMarkSheetTemplate,
                        mockBindingResult, modelMap);
        
        assertEquals("reporting/classWiseStudentMarksSheet", modelAndView.getViewName());
        
    }
    
    /**
     * Test method for populateGradeList method in ClassWiseStudentMarksSheetController.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testPopulateGradeList() throws AkuraAppException {
    
        List<Grade> list = controller.populateGradeList();
        assertNotNull(list);
    }
    
    /**
     * Test method for populateTermList method in ClassWiseStudentMarksSheetController.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testPopulateTermList() throws AkuraAppException {
    
        List<Term> list = controller.populateTermList();
        assertNotNull(list);
    }
    
    /**
     * Test method for populateYearList method in ClassWiseStudentMarksSheetController.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testPopulateYearList() throws AkuraAppException {
    
        List<String> list = controller.populateYearList();
        assertNotNull(list);
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
