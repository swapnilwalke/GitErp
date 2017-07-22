
package com.virtusa.akura.reporting.controller;

import java.util.Date;
import java.util.List;

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

import com.virtusa.akura.api.dto.Scholarship;
import com.virtusa.akura.api.dto.StudentScholarshipTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.ScholarshipDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.service.CommonReportingService;
import com.virtusa.akura.reporting.validator.StudentScholarshipReportValidator;

/**
 * @author Virtusa Corporation
 */

public class StudentScholarshipReportControllerTest extends BaseWebTest {
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private CommonService commonService;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private CommonReportingService commonReportingService;
    
    /** Holding instance of testing controller. */
    private StudentScholarshipReportController controller;
    
    /** Defines StudentScholarshipTemplate type object. */
    private StudentScholarshipTemplate studentScholarshipTemplate;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private StudentScholarshipReportValidator studentScholarshipReportValidator;
    
    /** Represents an instance of ModelMap. */
    private ModelMap modelMap;
    
    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;
    
    /** Represents an instance of response response. */
    private MockHttpServletResponse response;
    
    /** Defines Scholarship type object. */
    private Scholarship scholarship;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private ScholarshipDao scholarshipDao;
    
    /** constant of no of Student . */
    private static final int NO_OF_STUDENT = 12;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws AkuraAppException - Exception
     */
    @Before
    public final void setUp() throws AkuraAppException {
    
        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        
        scholarship.setName("Grade 5 Scholarship");
        scholarship.setModifiedTime(new Date());
        scholarship.setNoOfStudents(NO_OF_STUDENT);
        scholarship = scholarshipDao.save(scholarship);
        assertNotNull("New Scholarship should not be null " + scholarship);
        
        studentScholarshipTemplate = new StudentScholarshipTemplate();
        
        studentScholarshipTemplate.setAdmissionNo("STD001");
        studentScholarshipTemplate.setDescription("10A");
        studentScholarshipTemplate.setNameWithInitials("A.D. Smith");
        studentScholarshipTemplate.setScholarship("Grade 5 Scholarship");
        studentScholarshipTemplate.setSponsorship("10000");
        studentScholarshipTemplate.setTotalCount(NO_OF_STUDENT);
        
        assertNotNull("New StudentScholarshipTemplate should not be null " + studentScholarshipTemplate);
        
        controller = new StudentScholarshipReportController();
        controller.setCommonService(commonService);
        controller.setCommonReportingService(commonReportingService);
        controller.setStudentScholarshipReportValidator(studentScholarshipReportValidator);
        
        modelMap = new ModelMap();
        response = new MockHttpServletResponse();
    }
    
    /**
     * Test method for showReportForm method in StudentScholarshipReportController.
     * 
     * @throws AkuraAppException - throws if exception occurs
     */
    @Test
    public void testShowReportForm() throws AkuraAppException {
    
        String returnString = controller.showReportForm(modelMap);
        assertEquals("reporting/studentScholarshipReport", returnString);
    }
    
    /**
     * Test method for populateScholarshipList method in StudentScholarshipReportController.
     * 
     * @throws AkuraAppException - throws if exception occurs
     */
    @Test
    public void testPopulateGradeList() throws AkuraAppException {
    
        List<Scholarship> scholarshipList = controller.populateScholarshipList();
        assertNotNull("Scholarship List should not be null", scholarshipList);
    }
    
    /**
     * Test method for generateReport method in StudentScholarshipReportController.
     * 
     * @throws AkuraException throws if exception occurs
     */
    @Test
    public void testGenerateReport() throws AkuraException {
    
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        ModelAndView view =
                controller.generateReport(response, studentScholarshipTemplate, mockBindingResult, modelMap);
        
        assertEquals("reporting/studentScholarshipReport", view.getViewName());

    }
}
