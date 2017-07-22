
package com.virtusa.akura.student.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.dto.ParentDetails;
import com.virtusa.akura.api.dto.ParentWrapper;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.EmailService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.student.dao.StudentParentDao;
import com.virtusa.akura.student.service.ParentService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.student.validator.StudentDonationValidator;
import com.virtusa.akura.student.validator.StudentParentValidator;

public class ParentControllerTest extends BaseWebTest {

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

    /** StaffService attribute for holding staffService. */
    @Autowired
    private StaffService staffService;

    /** emailService attribute for holding EmailService. */
    @Autowired
    private EmailService emailService;

    /** Represents an instance of the ParentValidator. */
    @Autowired
    private StudentParentValidator parentValidator;

    /** Represents an instance of the DonationValidator. */
    @Autowired
    private StudentDonationValidator donationValidator;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult result;

    /** Random string to . */
    private static final String RANDOM_STRING = String.valueOf(Math.round(Math.random() * 1000));

    /** Holds Parent instance. */
    private Parent parent;

    /** Holds Student instance. */
    private Student student;

    /** Holds language instance. */
    private Language language;


    /** Holds studentParent instance. */
    private StudentParent studentParent;

    /** Represents an instance of MockHttpServletSession. */
    private MockHttpSession session;

    /** Represents an instance of MockHttpServletRequest. */
    @Mock
    private MockHttpServletRequest request;

    /** Represents an instance of ModelMap. */
    private ModelMap model;

    /** Test class object. */
    private StudentParentController parentController;

    /**
     * Instantiate ParentController object.
     *
     * @return ParentController object
     */
    private StudentParentController getParentController() {

        StudentParentController parentControllerRetun = new StudentParentController();
        parentControllerRetun.setCommonService(commonService);
        parentControllerRetun.setEmailService(emailService);
        parentControllerRetun.setParentService(parentService);
        parentControllerRetun.setStaffService(staffService);
        parentControllerRetun.setStudentService(studentService);
        parentControllerRetun.setDonationValidator(donationValidator);
        parentControllerRetun.setParentValidator(parentValidator);

        return parentControllerRetun;
    }

    /**
     * Instantiate the setup method.
     *
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        parentController = getParentController();

        // Instantiates a newParent object.
        parent = new Parent();
        parent.setTeacher(Boolean.FALSE);
        parent.setPastPupil(Boolean.FALSE);
        parent.setFullName("fullName");
        parent.setNameWithInitials("nameWithInitials");
        parent.setLastName("lastName");
        parent.setNationalIdNo(RANDOM_STRING + "111050V");
        parent.setMobileNo("0123456789");
        parentService.addParent(parent);

        // Instantiates a Language object.
        language = new Language();
        language.setLanguage("LanTest" + RANDOM_STRING);
        language.setModifiedTime(new Date());
        language = languageDao.save(language);

        // Instantiates a student object.
        student = new Student();
        student.setAdmissionNo("A1123" + RANDOM_STRING);
        student.setNameWtInitials("I L");
        student.setLastName("Lolratne ");
        student.setFullName("Lol TestClass");
        student.setLanguageId(language.getLanguageId());
        student.setGender('M');
        student.setIsOldBoy(false);
        student.setDateOfBirth(new Date());
        student.setFirstSchoolDay(new Date());

        student = studentService.saveStudent(student);

        studentParent = new StudentParent();
        studentParent.setParent(parent);
        studentParent.setStudent(student);
        studentParent.setRelationship('F');
        studentParentDao.save(studentParent);

    }

    /**
     * test "saveParentByParent.htm" request huddling.
     *
     * @throws AkuraAppException when error occur
     */
    @Test
    public void testEditParentByParent() throws AkuraAppException {

        // set session attributes
        session = new MockHttpSession();

        List<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
        UserInfo userInfo =
                new ParentDetails("TestUserName", "", "Pa", 4, parent.getNationalIdNo(), true, true, grantedAuthority);

        model = new ModelMap();
        ParentWrapper wrapperParent = new ParentWrapper();
        wrapperParent.setParent(parent);
        model.addAttribute("parentWrapper", wrapperParent);

        request.addParameter("selectedRelationship", "F");
        session.setAttribute("user", userInfo);
        session.setAttribute("studentId", student.getStudentId());

        // testing
        String urlName = parentController.editParentByParent(wrapperParent, result, model, request, session);
        assertTrue("Return url incorrect", urlName.equals("student/parentInfo"));
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
            languageDao.delete(language);

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }

    }

}
