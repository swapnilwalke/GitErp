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

package com.virtusa.akura.reporting.controller;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.virtusa.akura.api.dto.AttendeceStatus;
import com.virtusa.akura.api.dto.DailyStudentAttendance;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.dto.StudentWiseSwipInOutTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.service.AttendanceReportingService;
import com.virtusa.akura.reporting.validator.StudentWiseSwipeInOutAttendanceValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * Controller class to generate a report of Student wise SwipIn SwipOut attendance.
 */

@Controller
@SessionAttributes("studentWiseSwipInOutTemplate")
public class GenerateStudentWiseSwipInOutReportController {

    /** session attribute name for student classGradeDescription. */
    private static final String STUDENT_GRADE = "studentGrade";

    /** request URL. */
    private static final String POPULATE_ATTENDENCE_HTM = "populateAttendence.htm";

    /** model map attribute name. */
    private static final String ATTENDECE_MAP = "attendeceMap";

    /** session attribute name for studentId(primary key). */
    private static final String STUDENT_ID = "studentId";

    /** attribute for holding style path. */
    private static final String STYLE_PATH = "style.path";

    /** attribute for holding app home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** attribute for holding system config. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** attribute for holding style template. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** variable for holding string "generatedDate". */
    private static final String GENERATED_DATE = "generatedDate";

    /** model attribute for report generated date. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";

    /** The Constant REPORT_PAGE. */
    private static final String REPORT_PAGE = "Report_page";

    /** The Constant PAGE. */
    private static final String PAGE = "page";

    /** The Constant GPL. */
    private static final String GPL = "GPLComment";

    /** The Constant REPORT_GENERATED_ON. */
    private static final String REPORT_GENERATED_ON = "reportGeneratedOn";

    /** The Constant REPORT_GENERATED_ON_TEXT. */
    private static final String REPORT_GENERATED_ON_TEXT = "Report_reportGeneratedOn";

    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA";

    /** attribute for holding message. */
    public static final String MESSAGE = "message";

    /** report value for total hours. */
    private static final String REPORT_VALUE_TOTAL_HOURS = "StuWiseAttendees_totalHoursText";

    /** report value for swipe out. */
    private static final String REPORT_VALUE_LAST_SWIPE_OUT = "StuLateAttendees_swipeOutText";

    /** report value for swipe in. */
    private static final String REPORT_VALUE_FIRST_SWIPE_IN = "StuLateAttendees_swipeInText";

    /** report value for day name. */
    private static final String REPORT_VALUE_DAY_NAME = "StuWiseAttendees_dayText";

    /** report value for date. */
    private static final String REPORT_VALUE_DATE = "StuWiseAttendees_dateText";

    /** report value for title. */
    private static final String REPORT_VALUE_ATTENDANCE_REPORT = "StuWiseAttendees_titleText";

    /** report name of jrxml report logo. */
    private static final String REPORT_NAME_STUDENT_WISE_SWIP_IN_OUT = "StudentWiseSwipInOut";

    /** report value for date from parameter. */
    private static final String REPORT_PARA_DATE_FROM = "dateFromLabel";

    /** report value for date from value. */
    private static final String REPORT_PARA_DATE_FROM_LABEL = "StuSwipeInOutAttendees_dateFrom";

    /** report value for date to parameter. */
    private static final String REPORT_PARA_DATE_TO = "dateToLabel";

    /** report value for date to value. */
    private static final String REPORT_PARA_DATE_TO_LABEL = "StuSwipeInOutAttendees_dateTo";

    /** report value for student id to parameter. */
    private static final String REPORT_PARA_STUDENT_ID = "studentIdLabel";

    /** report value for student id to value. */
    private static final String REPORT_PARA_STUDENT_ID_LABEL = "StuSwipeInOutAttendees_studentId";

    /** report parameter of report logo. */
    private static final String REPORT_PARA_LOGO_PATH1 = "logoPath1";

    /** report parameter of report logo. */
    private static final String REPORT_PARA_DATETO = "dateto";

    /** report parameter of report from date. */
    private static final String REPORT_PARA_DATEFROM = "datefrom";

    /** report parameter of report number of hours. */
    private static final String REPORT_PARA_NUM_OF_HOURS = "numOfHours";

    /** report parameter of report time out. */
    private static final String REPORT_PARA_TIMEOUT = "timeout";

    /** report parameter of report in time. */
    private static final String REPORT_PARA_TIMEIN = "timein";

    /** report parameter of report day name. */
    private static final String REPORT_PARA_DAYNAME = "dayname";

    /** report parameter of report date. */
    private static final String REPORT_PARA_DATE = "date";

    /** report parameter of report title. */
    private static final String REPORT_PARA_TITLE = "title";

    /** session attribute of student id. */
    private static final String SESSION_ATT_STUDENT_ID = STUDENT_ID;

    /** view get method Student Wise Swipe In/Out. */
    private static final String GET_REPORTING_STUDENT_WISE_SWIP_IN_OUT_ATTENDANCE =
            "student/studentWiseSwipInOutAttendance";

    /** session attribute of average academic life rate value. */
    private static final String SESSION_ATT_AVERAGE_ACADEMIC_LIFE_RATING = "averageAcademicLifeRating";

    /** session attribute of average faith life rate value. */
    private static final String MODEL_ATT_AVERAGE_FAITH_LIFE_RATING = "averageFaithLifeRating";

    /** key to define the averageAttendanceRating. */
    private static final String AVERAGE_ATTENDANCE_RATING = "averageAttendanceRating";

    /** key to define the attendanceRating. */
    private static final String MODEL_ATT_ATTENDANCE_RATING = "attendanceRating";

    /** model attribute of student wise swipe in/out template. */
    private static final String MODEL_ATT_STUDENT_WISE_SWIP_IN_OUT_TEMPLATE = "studentWiseSwipInOutTemplate";

    /** model attribute of student faithLife average value. */
    private static final String MODEL_ATT_STUDENT_FAITH_LIFE = "averageFaithLife";

    /** model attribute of student academic life average value. */
    private static final String MODEL_ATT_STUDENT_ACADEMIC_LIFE = "averageAcademicLife";

    /** request mapping value for reterieve swipe In/Out. */
    private static final String REQ_MAP_VALUE_SWIPE_IN_OUT = "/studentSwipeInOutReport.htm";

    /**
     * attendanceReportingService of type AttendanceReportingService to use methods related to SwipIn SwipOut
     * attendance Reporting .
     */

    private AttendanceReportingService attendanceReportingService;

    /**
     * studentWiseSwipeInOutAttendanceValidator of type StudentWiseSwipeInOutAttendanceValidator to use
     * methods related to Validation .
     */

    private StudentWiseSwipeInOutAttendanceValidator studentWiseSwipeInOutAttendanceValidator;

    /**
     * studentService of type StudentService to use methods related to SwipIn SwipOut attendance Reporting .
     */
    private StudentService studentService;

    /** CommonService attribute for holding commonService. */
    private CommonService commonService;

    /** dailyAttendanceService To invoke service methods. */
    private DailyAttendanceService dailyAttendanceService;

    /**
     * set the dailyAttendanceService object.
     *
     * @param dailyAttendanceServiceArg object to set
     */
    public void setDailyAttendanceService(DailyAttendanceService dailyAttendanceServiceArg) {

        this.dailyAttendanceService = dailyAttendanceServiceArg;
    }

    /**
     * Set commonService object.
     *
     * @param commonServiceArg set commonService object.
     */

    public void setCommonService(CommonService commonServiceArg) {

        this.commonService = commonServiceArg;
    }

    /**
     * Sets an instance of StudentService.
     *
     * @param studentServiceVal - the relevant instance of StudentService
     */

    public void setStudentService(StudentService studentServiceVal) {

        studentService = studentServiceVal;
    }

    /**
     * Sets an instance of AttendanceReportingService.
     *
     * @param attendanceReportingServiceRef of type AttendanceReportingService
     */
    public void setAttendanceReportingService(AttendanceReportingService attendanceReportingServiceRef) {

        this.attendanceReportingService = attendanceReportingServiceRef;
    }

    /**
     * Set StudentWiseAttendanceValidator.
     *
     * @param studentWiseSwipeInOutAttendanceValidatorVal of type setStudentWiseSwipeInOutAttendanceValidator
     */

    public void setStudentWiseSwipeInOutAttendanceValidator(
            StudentWiseSwipeInOutAttendanceValidator studentWiseSwipeInOutAttendanceValidatorVal) {

        this.studentWiseSwipeInOutAttendanceValidator = studentWiseSwipeInOutAttendanceValidatorVal;
    }

    /**
     * Display Form View for of the controller and binding it to
     * MonthlyStudentSwipInSwipOutAttendanceTemplate.
     *
     * @param session of type HttpSession
     * @param modelMap of type ModelMap
     * @return java.lang.String
     * @throws AkuraAppException when exception occurs
     */

    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(HttpSession session, ModelMap modelMap) throws AkuraAppException {

        // studentWiseSwipInOutTemplate should fill with first day of current month and today
        StudentWiseSwipInOutTemplate studentTemplate = new StudentWiseSwipInOutTemplate();

        String clsGradeDes = (String) session.getAttribute(STUDENT_GRADE);
        // if student does not assign to a class, no need calculations for current moth.
        if (clsGradeDes != null && !clsGradeDes.isEmpty()) {
            // fist day of the month
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
            String fistDay = DateUtil.getFormatDate(cal.getTime());
            studentTemplate.setDateFrom(fistDay);
            // today
            String toDay = DateUtil.getFormatDate(new Date());
            studentTemplate.setDateTo(toDay);

            int studentId = (Integer) session.getAttribute(STUDENT_ID);
            Map<String, AttendeceStatus> allDays = getAllAttendeceStatus(studentTemplate, studentId);
            modelMap.addAttribute(ATTENDECE_MAP, allDays);
        }

        modelMap.addAttribute(MODEL_ATT_STUDENT_WISE_SWIP_IN_OUT_TEMPLATE, studentTemplate);
        setupStudentRatingDetails(modelMap, session);
        return GET_REPORTING_STUDENT_WISE_SWIP_IN_OUT_ATTENDANCE;
    }

    /**
     * Setups the faith life and academic details rating for student.
     *
     * @param model the model.
     * @param session the session.
     */
    private void setupStudentRatingDetails(ModelMap model, HttpSession session) {

        if (session.getAttribute(MODEL_ATT_AVERAGE_FAITH_LIFE_RATING) != null) {
            double faithLifeAverage = (Double) session.getAttribute(MODEL_ATT_AVERAGE_FAITH_LIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_FAITH_LIFE, (int) Math.round(faithLifeAverage));
        }
        if (session.getAttribute(SESSION_ATT_AVERAGE_ACADEMIC_LIFE_RATING) != null) {
            double academicLifeAverage = (Double) session.getAttribute(SESSION_ATT_AVERAGE_ACADEMIC_LIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_ACADEMIC_LIFE, (int) Math.round(academicLifeAverage));
        }
        if (session.getAttribute(AVERAGE_ATTENDANCE_RATING) != null) {
            double attendanceAverage = (Double) session.getAttribute(AVERAGE_ATTENDANCE_RATING);
            model.addAttribute(MODEL_ATT_ATTENDANCE_RATING, (int) Math.round(attendanceAverage));
        }
    }

    /**
     * Perform the logic of the controller to generate Student Wise SwipIn SwipOutAttendance Report .
     *
     * @param studentTemplate of type StudentWiseSwipInOutTemplate
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param errors of type BindingResult
     * @param session the session.
     * @return java.lang.String
     * @param map of type ModelMap
     * @throws AkuraAppException AkuraAppException
     */

    @RequestMapping(value = REQ_MAP_VALUE_SWIPE_IN_OUT, method = RequestMethod.POST)
    public String onSubmit(HttpServletRequest request, HttpServletResponse response, HttpSession session,
            @ModelAttribute(MODEL_ATT_STUDENT_WISE_SWIP_IN_OUT_TEMPLATE) StudentWiseSwipInOutTemplate studentTemplate,
            BindingResult errors, ModelMap map) throws AkuraAppException {

        studentWiseSwipeInOutAttendanceValidator.validate(studentTemplate, errors);

        if (errors.hasErrors()) {
            setupStudentRatingDetails(map, session);
            return GET_REPORTING_STUDENT_WISE_SWIP_IN_OUT_ATTENDANCE;

        }

        int studentId = (Integer) request.getSession().getAttribute(SESSION_ATT_STUDENT_ID);
        Student student = studentService.findStudent(studentId);

        String addmissionNo = student.getAdmissionNo();

        Map<String, Object> params = new HashMap<String, Object>();

        params.put(REPORT_PARA_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_ATTENDANCE_REPORT));
        params.put(REPORT_PARA_DATE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_DATE));
        params.put(REPORT_PARA_DAYNAME, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_DAY_NAME));
        params.put(REPORT_PARA_TIMEIN, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_FIRST_SWIPE_IN));
        params.put(REPORT_PARA_TIMEOUT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_LAST_SWIPE_OUT));
        params.put(REPORT_PARA_NUM_OF_HOURS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_TOTAL_HOURS));
        params.put(REPORT_PARA_DATE_FROM, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_PARA_DATE_FROM_LABEL));
        params.put(REPORT_PARA_DATE_TO, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_PARA_DATE_TO_LABEL));
        params.put(REPORT_PARA_STUDENT_ID, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_PARA_STUDENT_ID_LABEL));
        params.put(REPORT_PARA_DATEFROM, studentTemplate.getDateFrom());
        params.put(REPORT_PARA_DATETO, studentTemplate.getDateTo());
        params.put(REPORT_PARA_LOGO_PATH1, ReportUtil.getReportLogo());
        params.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        params.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_GENERATED_ON_TEXT));
        params.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        params.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        params.put(GPL, AkuraWebConstant.REPORT_GPL);

        JRBeanCollectionDataSource studentWiseSwipInOutSummary =
                attendanceReportingService.getStudentWiseSwipInOutSummary(studentTemplate, addmissionNo);

        if (studentWiseSwipInOutSummary.getRecordCount() != 0) {
            ReportUtil.displayReportInPdfForm(response, studentWiseSwipInOutSummary, params,
                    REPORT_NAME_STUDENT_WISE_SWIP_IN_OUT);
            return null;
        } else {
            setErrorMessage(map);
            setupStudentRatingDetails(map, session);
            return GET_REPORTING_STUDENT_WISE_SWIP_IN_OUT_ATTENDANCE;
        }

    }

    /**
     * set the error messages when returning to the jsp.
     *
     * @param map - ModelMap
     */
    private void setErrorMessage(ModelMap map) {

        String message = new ErrorMsgLoader().getErrorMessage(NO_DATA_ERROR_MESSAGE);
        map.addAttribute(MESSAGE, message);
    }

    /**
     * request to get student attendant status for given time range.
     *
     * @param request HttpRequest
     * @param session HttpSession
     * @param studentTemplate model object to bind parameters to request object.
     * @param errors validation falters
     * @param map model map
     * @return view name
     * @throws AkuraAppException exception occurs.
     */
    @RequestMapping(value = POPULATE_ATTENDENCE_HTM, method = RequestMethod.POST)
    public String populateAttendence(HttpServletRequest request, HttpSession session,
            @ModelAttribute(MODEL_ATT_STUDENT_WISE_SWIP_IN_OUT_TEMPLATE) StudentWiseSwipInOutTemplate studentTemplate,
            BindingResult errors, ModelMap map) throws AkuraAppException {

        studentWiseSwipeInOutAttendanceValidator.validate(studentTemplate, errors);
        if (errors.hasErrors()) {
            setupStudentRatingDetails(map, session);
            return GET_REPORTING_STUDENT_WISE_SWIP_IN_OUT_ATTENDANCE;

        }
        int studentId = (Integer) session.getAttribute(STUDENT_ID);

        Map<String, AttendeceStatus> allDays = getAllAttendeceStatus(studentTemplate, studentId);

        map.addAttribute(ATTENDECE_MAP, allDays);
        return GET_REPORTING_STUDENT_WISE_SWIP_IN_OUT_ATTENDANCE;
    }

    /**
     * generate student attendance detail for given time range in studentTemplate. map key contains date and
     * value contains attendance status as AttendeceStatus object.
     *
     * @param studentTemplate time rage as template
     * @param studentId for given studentId(primary key)
     * @return map with attendant status
     * @throws AkuraAppException when exception occurs
     */
    private Map<String, AttendeceStatus> getAllAttendeceStatus(StudentWiseSwipInOutTemplate studentTemplate,
            int studentId) throws AkuraAppException {

        Date from = DateUtil.getParseDate(studentTemplate.getDateFrom());
        Date to = DateUtil.getParseDate(studentTemplate.getDateTo());

        // map contains all the days with absent in default
        Map<String, AttendeceStatus> allDays = getDaysWithoutHolydays(from, to);

        // to overwrite the "allDays" map values for absent with a reason
        List<StudentLeave> leaveList = studentService.findAlreadyExistLeave(studentId, from, to);
        for (StudentLeave leave : leaveList) {
            Date leaveFrom = leave.getFromDate();
            Date leaveTo = leave.getToDate();

            for (Entry<String, AttendeceStatus> entry : allDays.entrySet()) {
                Date day = DateUtil.getParseDate(entry.getKey());
                if (day.equals(leaveFrom) || day.equals(leaveTo) || (day.after(leaveFrom) && day.before(leaveTo))) {
                    entry.getValue().setDescription(leave.getReason());
                }
            }
        }

        // to overwrite the "allDays" map values for attended days
        List<DailyStudentAttendance> dalyAttendList = dailyAttendanceService.getAttendanceBettween(studentId, from, to);
        for (DailyStudentAttendance dalyAttend : dalyAttendList) {

            String dayKey = DateUtil.getFormatDate(dalyAttend.getDate());
            if (allDays.containsKey(dayKey)) {
                AttendeceStatus attendStatus = allDays.get(dayKey);
                attendStatus.setAbsent(false);
                attendStatus.setTimeIn(dalyAttend.getTimeIn());
                attendStatus.setTimeOut(dalyAttend.getTimeOut());
            }
        }

        return allDays;
    }

    /**
     * get all days without special holidays and Saturday,Sunday for given Date range. map key contains date
     * and value contains AttendeceStatus object with default values(as absent day)
     *
     * @param from from date
     * @param to to date
     * @return map
     * @throws AkuraAppException when exception occurs
     */
    private Map<String, AttendeceStatus> getDaysWithoutHolydays(Date from, Date to) throws AkuraAppException {

        Calendar calFrom = Calendar.getInstance();
        Calendar calTo = Calendar.getInstance();

        calFrom.setTime(from);
        calTo.setTime(to);

        List<Holiday> holidayList = commonService.findHolidayByYear(from, to);

        Map<String, AttendeceStatus> allDaysBetween = new TreeMap<String, AttendeceStatus>();

        // to get name ex Sunday ,Monday ..
        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] weekDays = symbols.getWeekdays();

        while (calFrom.before(calTo) || calFrom.equals(calTo)) {

            int dyaOfWeek = calFrom.get(Calendar.DAY_OF_WEEK);
            // remove weekends and special holidays
            if (dyaOfWeek != Calendar.SATURDAY && dyaOfWeek != Calendar.SUNDAY
                    && !DateUtil.isHoliday(holidayList, calFrom.getTime())) {

                AttendeceStatus attSttus = new AttendeceStatus();
                attSttus.setDay(weekDays[dyaOfWeek]);
                allDaysBetween.put(DateUtil.getFormatDate(calFrom.getTime()), attSttus);
            }

            calFrom.set(Calendar.DATE, calFrom.get(Calendar.DATE) + 1);
        }

        return allDaysBetween;
    }

}
