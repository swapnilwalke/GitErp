
package com.virtusa.akura.reporting.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentAttendance;
import com.virtusa.akura.api.dto.StudentWiseAttendanceTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.reporting.service.AttendanceReportingService;
import com.virtusa.akura.reporting.validator.StudentWiseAttendanceValidator;
import com.virtusa.akura.student.dao.StudentDao;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * @author Virtusa Corporation
 */

public class GenerateStudentWiseAttendanceReportControllerTest extends BaseWebTest {

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Holds the value for sourceJrxmlPath. */
    private static final String SOURCE_JRXML_PATH = "sourceJrxmlPath";

    /**
     * systemConfig property file.
     */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /**
     * initialized an variable of AVERAGE HOURS.
     */
    private static final double NUMBER_HOURS = 10;

    /**
     * initialized an instance of REPORT.
     */
    public static final String REPORT = "report";

    /** Represents an instance of Student. */
    private Student student;

    /**
     * Represents an instance of Province.
     */
    private Province province;

    /**
     * Represents an instance of District.
     */
    private District district;

    /** Injects an instance of StudentDao. */
    @Autowired
    private ProvinceDao provinceDao;

    /** Injects an instance of StudentDao. */
    @Autowired
    private DistrictDao districtDao;

    /**
     * Represents an instance of City.
     */
    private City city;

    /** Injects an instance of CityDao. */
    @Autowired
    private CityDao cityDao;

    /** Holds Nationality instance. */
    private House house;

    /** Holds Religion instance. */
    private Religion religion;

    /** Holds BloodGroup instance. */
    private BloodGroup bloodGroup;

    /** Holds Nationality instance. */
    private Nationality nationality;

    /**
     * Holds methodOfTravel instance of {@link MethodOfTravel}.
     */
    private MethodOfTravel methodOfTravel;

    /**
     * Holds language instance of {@link Language}.
     */
    private Language language;

    /** Injects an instance of StudentDao. */
    @Autowired
    private StudentDao studentDao;

    /**
     * Represents an instance of GenerateTeacherWiseAttendanceReportController.
     */
    private GenerateStudentWiseAttendanceReportController generateStudentWiseAttendanceReportController;

    /**
     * Represents an instance of AttendanceReportingService.
     */
    @Autowired
    private AttendanceReportingService attendanceReportingService;

    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;

    /**
     * Represents an instance of StudentWiseAttendanceValidator.
     */
    @Autowired
    private StudentWiseAttendanceValidator studentWiseAttendanceValidator;

    /**
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * Represents an instance of studentWiseAttendanceTemplate.
     */
    private StudentWiseAttendanceTemplate studentWiseAttendanceTemplate;

    /**
     * Represents an instance of StudentAttendance.
     */
    private StudentAttendance studentAttendance;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);

        // Instantiates Province object.
        province = new Province();
        province.setDescription("Province test6");
        provinceDao.save(province);
        assertNotNull(province);

        // Instantiates District object.
        district = new District();
        district.setDescription("District Description test6");
        district.setProvince(province);
        districtDao.save(district);
        assertNotNull(district);

        // Instantiates City object.
        city = new City();
        city.setDescription("City Description test6");
        city.setDistrict(district);
        cityDao.save(city);
        assertNotNull(city);

        student = new Student();
        student.setAdmissionNo(student.getAdmissionNo());
        student.setAdmissionDate(new Date());
        student.setNameWtInitials("I L");
        student.setLastName("Lolratne test2");
        student.setAddress("Gampaha");
        student.setFullName("Lol Lolrathne");
        student.setFirstSchoolDay(new Date());
        student.setDateOfBirth(new Date());
        student.setCityId(city.getCityId());
        student.setHouseId(house.getHouseId());
        student.setBloodGroupId(bloodGroup.getBloodGroupId());
        student.setNationalityId(nationality.getNationalityId());
        student.setReligionId(religion.getReligionId());
        student.setLanguageId(language.getLanguageId());
        student.setTravelId(methodOfTravel.getTravelId());
        studentDao.save(student);
        assertNotNull(student);

        generateStudentWiseAttendanceReportController = new GenerateStudentWiseAttendanceReportController();
        studentWiseAttendanceTemplate = new StudentWiseAttendanceTemplate();

        studentWiseAttendanceTemplate.setDateFrom("2011-10-10");
        studentWiseAttendanceTemplate.setDateTo("2012-10-10");
        studentWiseAttendanceTemplate.setStudentID("098234");

        studentAttendance = new StudentAttendance();
        studentAttendance.setDate("1999-10-10");
        studentAttendance.setStudentID("093546");
        studentAttendance.setStudentName("Nuwan Karunarathne");
        studentAttendance.setNumOfHours(NUMBER_HOURS);
        studentAttendance.setTimeIn("2.30");
        studentAttendance.setTimeOut("5.40");

        model = new ModelMap();
        model.addAttribute(studentWiseAttendanceTemplate);

        generateStudentWiseAttendanceReportController.setAttendanceReportingService(attendanceReportingService);
    }

/**
     * Test method for {@link com.virtusa.sms.reporting.controller.
     * GenerateStudentWiseAttendanceReportController.
     * showReportForm(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String view = generateStudentWiseAttendanceReportController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/studentWiseAttendance", view);
    }

    /**
     * Test method for GenerateStudentWiseAttendanceReportController.
     * onSubmit(org.springframework.ui.ModelMap)
     *
     * @throws AkuraAppException when an error has occurred during processing.
     * @throws JRException when an error has occurred during Jasper processing
     */
    @Test
    public void testOnSubmit() throws AkuraAppException, JRException {

        generateStudentWiseAttendanceReportController.setStudentWiseAttendanceValidator(studentWiseAttendanceValidator);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        assertTrue(model.size() > 0);

        Map<String, String> params = new HashMap<String, String>();

        params.put("title", " Student Wise Attendance Report");
        params.put("studentid", "Student ID");
        params.put("studentname", "Student Name");
        params.put("date", "Date ");
        params.put("timein", "Time In");
        params.put("timeout", "Time Out");
        params.put("student", studentWiseAttendanceTemplate.getDateFrom());
        params.put("datefrom", studentWiseAttendanceTemplate.getDateFrom());
        params.put("dateto", studentWiseAttendanceTemplate.getDateTo());
        params.put("logoPath", ReportUtil.getReportLogo());

        final List<StudentAttendance> list = new ArrayList<StudentAttendance>();
        list.add(studentAttendance);

        String path =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH) + "StudentWiseAttendance"
                        + ".jrxml";
        String pdfPath =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH) + "StudentWiseAttendance"
                        + ".pdf";
        JasperDesign subReportDesign2 = JRXmlLoader.load(path);
        JasperReport subCompiled2 = JasperCompileManager.compileReport(subReportDesign2);
        JRBeanCollectionDataSource ds2 = new JRBeanCollectionDataSource(list);
        JasperPrint printSubreport2 = JasperFillManager.fillReport(subCompiled2, params, ds2);
        JasperExportManager.exportReportToPdfFile(printSubreport2, pdfPath);
    }
}
