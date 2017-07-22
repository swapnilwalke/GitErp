
package com.virtusa.akura.student.controller;

import java.util.Date;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.dao.StudentParentDao;
import com.virtusa.akura.student.service.ParentService;
import com.virtusa.akura.student.service.StudentService;

/**
 * this class is test ParentsChildsControler functions.
 * @author Virtusa
 *
 */
public class ParentsChildsControlerTest extends BaseWebTest {
    
    /** StudentService attribute for holding studentService. */
    @Autowired
    private StudentService studentService;
    
    /** CommonService attribute for holding commonService. */
    @Autowired
    private CommonService commonService;
    
    /** ParentService attribute for holding parentService. */
    @Autowired
    private ParentService parentService;
    
    /**
     * Represents an instance of LanguageDao.
     */
    @Autowired
    private LanguageDao languageDao;
    
    /** Holds StudentParentDao instance. */
    @Autowired
    private StudentParentDao studentParentDao;
    
    /** Random string to . */
    private static final String RANDOM_STRING = String.valueOf(Math.round(Math.random() * 1000));
    
    /** Holds Parent instance. */
    private Parent parent;
    
    /** Holds Student instance. */
    private Student student;
    
    
    /** Holds Religion instance. */
    private Religion religion;
    
    /** Holds province instance. */
    private Province province;
    
    /** Holds city instance. */
    private City city;
    
    /** Holds district instance. */
    private District district;
    
    /** Holds bloodGroup instance. */
    private BloodGroup bloodGroup;
    
    /** Holds nationality instance. */
    private Nationality nationality;
    
    /** Holds house instance. */
    private House house;
    
    /** Holds language instance. */
    private Language language;
    
    /** Holds methodOfTravel instance. */
    private MethodOfTravel methodOfTravel;
    
    /** Holds studentParent instance. */
    private StudentParent studentParent;
        
    /** Represents an instance of MockHttpServletSession. */
    private MockHttpSession session;
    
    /** Represents an instance of ModelMap. */
    private ModelMap model;
    
    /** Test class object. */
    private ParentsChildsControler parentsChildsControler;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {
    
        MockitoAnnotations.initMocks(this);
        parentsChildsControler = new ParentsChildsControler();
        parentsChildsControler.setParentService(parentService);
        
        // Instantiate a Province object.
        Province newProvince = new Province();
        newProvince.setDescription("North2" + RANDOM_STRING);
        // Instantiates a District object.
        District newDistrict = new District();
        newDistrict.setDescription("Gampaha1" + RANDOM_STRING);
        // Instantiates a City object.
        City newCity = new City();
        newCity.setDescription("Dematagoda1" + RANDOM_STRING);
        // Instantiates a Religion object.
        religion = new Religion();
        religion.setDescription("Religion" + RANDOM_STRING);
        
        province = commonService.addProvince(newProvince);
        newDistrict.setProvince(newProvince);
        district = commonService.addDistrict(newDistrict);
        newCity.setDistrict(newDistrict);
        city = commonService.addCity(newCity);
        
        religion = commonService.createReligion(religion);
        // Instantiates a newParent object.
        parent = new Parent();
        parent.setDesignation("designation");
        parent.setTeacher(Boolean.FALSE);
        parent.setPastPupil(Boolean.FALSE);
        parent.setFullName("fullName");
        parent.setNameWithInitials("nameWithInitials");
        parent.setLastName("lastName");
        parent.setNationalIdNo(RANDOM_STRING + "111050V");
        parent.setAddress("address");
        parent.setMobileNo("0123456789");
        parent.setResidenceNo("0123456789");
        parent.setOfficeNo("0123456789");
        parent.setRelationship("Mother");
        parent.setEmail("emal");
        parent.setCityId(city.getCityId());
        parent.setReligionId(religion.getReligionId());
        
        parentService.addParent(parent);
        
        // Instantiates a BloodGroup object.
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("AAAA" + RANDOM_STRING);
        bloodGroup.setModifiedTime(new Date());
        bloodGroup = commonService.addBloodGroup(bloodGroup);      
        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("Sri Lankan1111" + RANDOM_STRING);
        nationality.setModifiedTime(new Date());
        nationality = commonService.addNationality(nationality);       
        // Instantiates a House object.
        house = new House();
        house.setName("House" + RANDOM_STRING);
        house.setModifiedTime(new Date());
        house = commonService.createHouse(house);
        // Instantiates a Language object.
        language = new Language();
        language.setLanguage("LanTest" + RANDOM_STRING);
        language.setModifiedTime(new Date());       
        language = languageDao.save(language);
        
        // Instantiates a Method of travel object.
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("Bus" + RANDOM_STRING);        
        methodOfTravel = commonService.createMethodOfTravel(methodOfTravel);
        
        // Instantiates a student object.
        student = new Student();
        student.setAdmissionNo("A1123" + RANDOM_STRING);
        student.setNameWtInitials("I L");
        student.setLastName("Lolratne ");
        student.setAddress("Gampaha");
        student.setFullName("Lol TestClass");
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
        
        student = studentService.saveStudent(student);
        
        studentParent = new StudentParent();
        studentParent.setParent(parent);
        studentParent.setStudent(student);
        studentParent.setRelationship('F');
        studentParentDao.save(studentParent);
     
    }
    
    /**
     * test getChildList.htm request huddling.
     * @throws AkuraAppException when error occur
     */
    @Test
    public void testGetChildList() throws AkuraAppException {
        // set session attributes
        model = new ModelMap();
        session = new MockHttpSession();        
        UserLogin parentLogin =new UserLogin();
        parentLogin.setUserIdentificationNo(parent.getNationalIdNo());       
        session.setAttribute("userLogin", parentLogin);
        
        //testing
        String urlNamr=parentsChildsControler.getChildList(model, session);
        assertTrue("Return url incorrect",urlNamr.equals("student/childList"));
        
        Object imgePaths=model.get("imagePaths");
        assertNotNull("model map should cantain imagePaths attribute ",imgePaths);
        Map<String, String> imgePathsMap=(Map<String, String>)imgePaths;
        assertTrue("map atleast contain one value", !imgePathsMap.isEmpty());
        
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            studentParentDao.delete(studentParent);
            parentService.deleteParent(parent);
            studentService.deleteStudent(student.getStudentId());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        try {
            commonService.deleteCity(city);
            commonService.deleteDistrict(district);
            commonService.deleteProvince(province);
            commonService.deleteReligion(religion.getReligionId());
            
            commonService.deleteMethodOfTravel(methodOfTravel.getTravelId());
            languageDao.delete(language);
            commonService.deleteHouse(house.getHouseId());
            commonService.deleteNationality(nationality);
            commonService.deleteBloodGroup(bloodGroup);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
}
