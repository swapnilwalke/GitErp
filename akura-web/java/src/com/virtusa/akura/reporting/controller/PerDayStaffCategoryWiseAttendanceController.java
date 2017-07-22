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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.virtusa.akura.api.dto.DailyTeacherAttendance;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.PerDayAttendanceTemplate;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.dto.TeacherAttendance;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.reporting.validator.PerDayStaffCategoryWiseAttendanceValidator;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.HolidayUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * Controller to generate Per day staff category wise Absent list report.
 *
 * @author Virtusa Corporation
 */
@Controller
@SessionAttributes("perDayAttendanceTemplate")
public class PerDayStaffCategoryWiseAttendanceController {

    /**
     * The constant for String "StaffCategoryWiseReport_dayType".
     */
    private static final String STAFF_CATEGORY_WISE_REPORT_DAY_TYPE = "Report_dateType";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(PerDayStaffCategoryWiseAttendanceController.class);

    /** The constant for numeric REPORT_TYPE. */
    private static final int REPORT_TYPE = 2;

    /** attribute for holding view name reporting/perDayStaffCategoryWiseAttendance. */
    public static final String REPORTING_PERDAY_STAFF_CATEGORY_WISE_ATTENDANCE =
            "reporting/perDayStaffCategoryWiseAttendance";

    /** attribute for holding url /AbsentStaffCategoryWiseReport.htm. */
    public static final String ABSENT_STAFFCATEGORY_REPORT = "/AbsentStaffCategoryWiseReport.htm";

    /** The constant for String "staffCategoryList". */
    private static final String STAFF_CATEGORY_LIST = "staffCategoryList";

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

    /** attribute for holding perDayAttendanceTemplate. */
    public static final String PER_DAY_ATTENDANCE_TEMPLATE = "perDayAttendanceTemplate";

    /** attribute for holding url /AbsentClassWiseReport.htm. */
    public static final String ABSENT_CLASSWISE_REPORT = "/AbsentClassWiseReport.htm";

    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA";

    /** attribute for holding error message key. */
    private static final String REPORTING_UI_HOLIDAY = "REPORTING.UI.HOLIDAY";

    /** attribute for holding message. */
    public static final String MESSAGE = "message";

    /** attribute for holding hyphen. */
    public static final String HIPAN = "-";

    /** attribute for holding StudentClassWiseAttendanceReport. */
    public static final String STAFF_CATEGORY_WISE_ATTENDANCE_REPORT = "StaffCategoryWiseAttendanceReport";

    /** attribute for holding sub title. */
    public static final String SUB_TITLE = "subTitle";

    /** attribute for holding student present list. */
    public static final String PRESENT_LIST = "StaffCategoryWiseReport_present";

    /** attribute for holding student absent list. */
    public static final String ABSENT_LIST = "StaffCategoryWiseReport_absent";

    /** attribute for holding string. */
    private static final String STRING_COLAN = ":";

    /** attribute for holding string. */
    private static final String COLAN = "colan";

    /** attribute for holding string. */
    private static final String SUMMARY = "summary";

    /** attribute for holding string. */
    private static final String STRING_SUMMARY = "StaffCategoryWiseReport_summary";

    /** attribute for holding string. */
    private static final String STAFF_NAME2 = "staffName";

    /** attribute for holding string. */
    private static final String STAFF_NAME = "StaffCategoryWiseReport_staffName";

    /** attribute for holding string. */
    private static final String ADMISSION_NUMBER = "StaffCategoryWiseReport_admissionNo";

    /** attribute for holding string. */
    private static final String ADMISSION_NUM = "admissionNum";

    /** attribute for holding string. */
    private static final String DATE_DESCRIPTION = "dateDescription";

    /** attribute for holding string. */
    private static final String DATE_STRING = "StaffCategoryWiseReport_date";

    /** attribute for holding string. */
    private static final String STAFF_TYPE = "StaffCategoryWiseReport_staffType";

    /** attribute for holding string. */
    private static final String STAFFNAME = "staffCategoryName";

    /** attribute for holding string. */
    private static final String ABSENT_COUNT_DES = "AbsentCountDes";

    /** attribute for holding string. */
    private static final String DAY_TYPE_KEY = "leaveReason";

    /** attribute for holding string. */
    private static final String NUMBER_OF_STAFF_ABSENT = "StaffCategoryWiseReport_noOfStaffAbsent";

    /** attribute for holding string. */
    private static final String TOTAL_NUMBER_OF_STAFF_IN = "StaffCategoryWiseReport_totalNoOfStaff";

    /** attribute for holding string. */
    private static final String TOTAL_STAFF_DES = "TotalStudentsDes";

    /** attribute for holding string. */
    private static final String PRESENT_COUNT_DES = "PresentCountDes";

    /** attribute for holding string. */
    private static final String NUMBER_OF_STAFF_PRESENT = "StaffCategoryWiseReport_noOfPresents";

    /** attribute for holding string. */
    private static final String STAFF_ATTANDANCE_REPORT = "StaffCategoryWiseReport_titile";

    /** attribute for holding string. */
    private static final String TITLE = "title";

    /** attribute for holding string. */
    private static final String DATE = "date";

    /** attribute for holding string. */
    private static final String CATEGORY_DESCRIPTION = "CategoryDescription";

    /** attribute for holding string. */
    private static final String ABSENT_COUNT = "AbsentCount";

    /** attribute for holding string. */
    private static final String TOTAL_STUDENTS = "TotalStudents";

    /** attribute for holding string. */
    private static final String PRESENT_COUNT = "PresentCount";

    /** attribute for holding string. */
    private static final String LOGO_PATH = "logoPath";

    /** attribute for holding string. */
    private static final String DATASOURCE = "datasource";

    /** attribute for holding string. */
    private static final String END_YEAR = "-12-31";

    /** attribute for holding string. */
    private static final String START_YEAR = "-01-01";

    /** attribute for holding string style.path. */
    private static final String STYLE_PATH = "style.path";

    /** attribute for holding string appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** attribute for holding string systemConfig. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** attribute for holding string styleTemplate. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** variable for year-month-day. */
    private static final String YEAR_MONTH_DAY = "yyyy-MM-dd";

    /**
     * perDayStaffCategoryWiseAttendanceValidator attribute for holding
     * PerDayStaffCategoryWiseAttendanceValidator.
     */
    private PerDayStaffCategoryWiseAttendanceValidator perDayStaffCategoryWiseAttendanceValidator;

    /**
     * staffService To invoke service methods.
     */
    private StaffService staffService;

    /**
     * dailyAttendanceService To invoke service methods.
     */
    private DailyAttendanceService dailyAttendanceService;

    /**
     * commonService To invoke service methods.
     */
    private CommonService commonService;

    /**
     * staffCommonService To invoke service methods.
     */
    private StaffCommonService staffCommonService;

    /**
     * Sets the setPerDayStaffCategoryWiseAttendanceValidator object.
     *
     * @param perDayStaffCategoryWiseAttendanceValidatorRef set PerDayStaffCategoryWiseAttendanceValidator
     *        object
     */
    public void setPerDayStaffCategoryWiseAttendanceValidator(
            PerDayStaffCategoryWiseAttendanceValidator perDayStaffCategoryWiseAttendanceValidatorRef) {

        this.perDayStaffCategoryWiseAttendanceValidator = perDayStaffCategoryWiseAttendanceValidatorRef;
    }

    /**
     * setter to inject staffServiceRef object.
     *
     * @param staffServiceRef the StaffService to set
     */
    public void setStaffService(StaffService staffServiceRef) {

        this.staffService = staffServiceRef;
    }

    /**
     * Set CommonService.
     *
     * @param commonServiceRef of type CommonService
     */
    public void setCommonService(CommonService commonServiceRef) {

        this.commonService = commonServiceRef;
    }

    /**
     * Set StaffCommonService.
     *
     * @param staffCommonServiceRef of type StaffCommonService
     */
    public void setStaffCommonService(StaffCommonService staffCommonServiceRef) {

        this.staffCommonService = staffCommonServiceRef;
    }

    /**
     * setter to inject DailyAttendanceService object.
     *
     * @param dailyAttendanceServiceRef the DailyAttendanceService to set
     */

    public void setDailyAttendanceService(DailyAttendanceService dailyAttendanceServiceRef) {

        this.dailyAttendanceService = dailyAttendanceServiceRef;
    }

    /**
     * Load the list of Staff categories to load on report.
     *
     * @return a list of available staff categories.
     * @throws AkuraAppException - throws when fails.
     */
    @ModelAttribute(STAFF_CATEGORY_LIST)
    public List<StaffCategory> populateListOfStaffCategories() throws AkuraAppException {

        return SortUtil.sortStaffCategoryList(staffCommonService.getAllStaffCategories());

    }

    /**
     * Used to collect user the input data to generate the report.
     *
     * @param modelMap of type ModelMap
     * @return java.lang.String
     * @throws AkuraAppException throw exception if occur.
     */

    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(ModelMap modelMap) throws AkuraAppException {

        // log output
        LOG.info("Calling perDayStaffCategoryWiseAttendance.jsp to colect input data");
        // create template object
        PerDayAttendanceTemplate perDayAttendanceTemplate = new PerDayAttendanceTemplate();
        // set template with date into session - model map
        modelMap.addAttribute(PER_DAY_ATTENDANCE_TEMPLATE, perDayAttendanceTemplate);

        // return relevant jsp
        return REPORTING_PERDAY_STAFF_CATEGORY_WISE_ATTENDANCE;
    }

    /**
     * Generate Staff Category Wise with present & absent Report.
     *
     * @param perDayAttendanceTemplate dto to transfer user input data to generate the report.
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param errors errors
     * @param map of type ModelMap
     * @return java.lang.String
     * @throws AkuraAppException throw exception if occur.
     * @throws ParseException - ParseException.
     */

    @RequestMapping(value = ABSENT_STAFFCATEGORY_REPORT, method = RequestMethod.POST)
    public String onSubmit(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(PER_DAY_ATTENDANCE_TEMPLATE) PerDayAttendanceTemplate perDayAttendanceTemplate,
            BindingResult errors, ModelMap map) throws AkuraAppException, ParseException {

        // check validations when selecting data on jsp.
        perDayStaffCategoryWiseAttendanceValidator.validate(perDayAttendanceTemplate, errors);

        String returnString = REPORTING_PERDAY_STAFF_CATEGORY_WISE_ATTENDANCE;

        // if not having any errors ,when sending data through JSP
        if (!errors.hasErrors()) {

            // get the selected date on UI
            String selectedDate = perDayAttendanceTemplate.getDateConsidered();

            int year = DateUtil.getYearFromDate(DateUtil.getDateTypeYearValue(selectedDate));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtil.getParseDate(selectedDate));
            List<Holiday> holidayList =
                    getHolidayList(String.valueOf(year) + String.valueOf(AkuraWebConstant.EMPTY_STRING));
            if (!HolidayUtil.isHoliday(holidayList, DateUtil.getParseDate(selectedDate), calendar)) {

                // get selected staff category id
                int staffCategoryId = perDayAttendanceTemplate.getCatogaryID();

                List<DailyTeacherAttendance> staffAttendanceList = null;
                List<Staff> staffByCategoryList = null;
                Date date = DateUtil.getParseDate(selectedDate);

                if (staffCategoryId == 0) {
                    // get all staff attendance list
                    staffAttendanceList =
                            dailyAttendanceService.getStaffAttandanceList(DateUtil.getParseDate(selectedDate));

                    // get all available staff
                    staffByCategoryList = staffService.getAllAvailableStaffWithOutDepartureDate(date, date);

                } else {
                    // get staff type academic or non-academic, by staff category id
                    // boolean staffType = commonService.isAcademicStaffCategory(staffCategoryId);

                    // get staff attendance list for a particular date by staff type(academic or non).
                    staffAttendanceList =
                            dailyAttendanceService.getTeacherAttandanceListByCategoryId(DateUtil
                                    .getParseDate(selectedDate), staffCategoryId);

                    // get staff list by staff category id ,from selected staff type
                    staffByCategoryList = staffService.getStaffListByCategoryByDate(staffCategoryId, date, date);

                }

                // if Staff exists.
                if (!staffByCategoryList.isEmpty()) {

                    // get the present staff, by staff category
                    List<Staff> presentStaffList = getStaffList(staffAttendanceList);

                    SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DAY);
                    Date dateConsider = sdf.parse(perDayAttendanceTemplate.getDateConsidered());

                    // get present staff list with half day leave reasons, if there any half day leave on
                    // particular day.
                    Map<Staff, String> presentMap = getPresentStaffListWithLeaveReason(presentStaffList, dateConsider);

                    // get the absent staffs
                    Map<String, Staff> absentMap =
                            getAbsentStaffList(presentStaffList, staffByCategoryList, dateConsider);

                    // set staff present template list
                    List<Object> staffPresentTemplateList = setPresentTemplate(presentMap);

                    // keep staff absent template list
                    List<Object> staffAbsentTemplateList = setAbsentTemplate(absentMap);

                    // create parameter object for report parameters
                    Map<String, Object> parameterMap = new HashMap<String, Object>();

                    // set templates to the data source
                    JRBeanCollectionDataSource datasource =
                            setTemplateToDataSource(perDayAttendanceTemplate, staffPresentTemplateList,
                                    staffAbsentTemplateList, parameterMap);

                    parameterMap.put(DATASOURCE, datasource);
                    parameterMap.put(LOGO_PATH, ReportUtil.getReportLogo());

                    // present staff count
                    parameterMap.put(PRESENT_COUNT, presentStaffList.size());

                    // total staffs
                    parameterMap.put(TOTAL_STUDENTS, staffByCategoryList.size());

                    // absent count of staff
                    parameterMap.put(ABSENT_COUNT, absentMap.size());

                    parameterMap.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));

                    // staff category All
                    if (staffCategoryId == 0) {

                        parameterMap.put(CATEGORY_DESCRIPTION, "All");

                        // staff category description - academic or Non - academic
                    } else {

                        parameterMap.put(CATEGORY_DESCRIPTION, staffCommonService.getStaffCategory(staffCategoryId)
                                .getDescription());
                    }

                    parameterMap.put(DATE, selectedDate);
                    setStringParameters(parameterMap);

                    // check report data is null, to be avoid empty report
                    if (datasource.getRecordCount() != 0) {
                        ReportUtil.displayReportInPdfForm(response, datasource, parameterMap,
                                STAFF_CATEGORY_WISE_ATTENDANCE_REPORT);
                    } else {
                        // set no data error message for report
                        setErrorMessage(map);
                    }

                } else {
                    setErrorMessage(map);
                }

            } else {
                String message = new ErrorMsgLoader().getErrorMessage(REPORTING_UI_HOLIDAY);
                map.addAttribute(MESSAGE, message);

            }

        }
        return returnString;

    }

    /**
     * Get the Holiday List for the given time period.
     *
     * @param year - string year.
     * @return ListHoliday - a list containing holiday objects.
     * @throws AkuraAppException - AkuraAppException
     */
    private List<Holiday> getHolidayList(String year) throws AkuraAppException {

        String strStartDate = year + START_YEAR;
        String strEndDate = year + END_YEAR;

        Date startDateToSearch = DateUtil.getParseDate(strStartDate);
        Date endDateToSearch = DateUtil.getParseDate(strEndDate);

        return commonService.findHolidayByYear(startDateToSearch, endDateToSearch);
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
     * set String parameters to the map.
     *
     * @param parameterMap - of type map
     * @throws AkuraAppException AkuraAppException.
     */
    private void setStringParameters(Map<String, Object> parameterMap) throws AkuraAppException {

        parameterMap.put(TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STAFF_ATTANDANCE_REPORT));
        parameterMap.put(PRESENT_COUNT_DES, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                NUMBER_OF_STAFF_PRESENT));
        parameterMap.put(TOTAL_STAFF_DES, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                TOTAL_NUMBER_OF_STAFF_IN));
        parameterMap.put(ABSENT_COUNT_DES, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                NUMBER_OF_STAFF_ABSENT));
        parameterMap.put(STAFFNAME, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STAFF_TYPE));
        parameterMap.put(DATE_DESCRIPTION, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_STRING));
        parameterMap.put(ADMISSION_NUM, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, ADMISSION_NUMBER));
        parameterMap.put(STAFF_NAME2, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STAFF_NAME));
        parameterMap.put(COLAN, STRING_COLAN);
        parameterMap.put(SUMMARY, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STRING_SUMMARY));
        parameterMap.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_GENERATED_ON_TEXT));
        parameterMap.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        parameterMap.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        parameterMap.put(GPL, AkuraWebConstant.REPORT_GPL);
        parameterMap.put(DAY_TYPE_KEY, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STAFF_CATEGORY_WISE_REPORT_DAY_TYPE));

    }

    /**
     * set data source according to the required staff list.
     *
     * @param perDayAttendanceTemplate dto to transfer user input data to generate the report.
     * @param staffPresentTemplateList - Object type list to holds present staff
     * @param staffAbsentTemplateList - Object type list to holds absent staff
     * @param parameterMap - of type map
     * @return data source - JRBeanCollectionDataSource
     */
    private JRBeanCollectionDataSource setTemplateToDataSource(PerDayAttendanceTemplate perDayAttendanceTemplate,
            List<Object> staffPresentTemplateList, List<Object> staffAbsentTemplateList,
            Map<String, Object> parameterMap) {

        JRBeanCollectionDataSource datasource;

        if (perDayAttendanceTemplate.getReportType() == REPORT_TYPE) {
            // set staff present template list for report
            datasource = new JRBeanCollectionDataSource(staffPresentTemplateList);
            parameterMap.put(SUB_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PRESENT_LIST));

        } else {
            // set staff absent template list for report
            datasource = new JRBeanCollectionDataSource(staffAbsentTemplateList);
            parameterMap.put(SUB_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, ABSENT_LIST));
        }
        return datasource;
    }

    /**
     * get the present staff in the staff Category of the particular day.
     *
     * @param staffAttendanceList of type DailyTeacherAttendance
     * @return list of type student
     * @throws AkuraAppException throw exception if occur.
     */
    private List<Staff> getStaffList(List<DailyTeacherAttendance> staffAttendanceList) throws AkuraAppException {

        // create staffList array list object
        List<Staff> staffList = new ArrayList<Staff>();

        // check if not empty , staffAttendanceList
        if (!staffAttendanceList.isEmpty()) {
            // iterate through staff list
            Iterator<DailyTeacherAttendance> staffAttendanceIterator = staffAttendanceList.iterator();

            // if there are elements on staff attendance list
            while (staffAttendanceIterator.hasNext()) {
                // get next value , through iterate
                DailyTeacherAttendance dailyStaffAttendance = staffAttendanceIterator.next();

                // add that value into staff List
                staffList.add(staffService.findStaff(dailyStaffAttendance.getStaffId()));
            }
        }
        // all staff in attendance
        return staffList;
    }

    /**
     * get present staff list with half day leave reason if any.
     *
     * @param presentStaffList - present staff list
     * @param dateConsider - date of the report .
     * @return map of staff
     * @throws AkuraAppException throw exception if occur.
     */
    private Map<Staff, String> getPresentStaffListWithLeaveReason(List<Staff> presentStaffList, Date dateConsider)
            throws AkuraAppException {

        // create an present map for staffs with half day leave reason.

        Map<Staff, String> presentMap = new LinkedHashMap<Staff, String>();
        // Map<Staff, String> presentMap = new TreeMap<Staff, String>();
        Date attDate = dateConsider;

        if (!presentStaffList.isEmpty()) {

            for (Staff staff : presentStaffList) {
                String leaveDayType = null;
                List<StaffLeave> staffLeaveList =
                        staffService.getStaffLeaveListByDatePeriodAndStaffId(attDate, attDate, staff.getStaffId());

                if (!staffLeaveList.isEmpty()) {

                    for (StaffLeave sl : staffLeaveList) {

                        Date getFromDate = DateUtil.getParseDate(sl.getFromDate().toString());
                        Date getToDate = DateUtil.getParseDate(sl.getToDate().toString());

                        if (getFromDate.before(attDate) && getToDate.after(attDate)) {

                            if (sl.getStaffLeaveStatusId().intValue() == 1) {
                                leaveDayType = sl.getDateType();
                            }

                        } else if (getFromDate.equals(attDate) || getToDate.equals(attDate)) {
                            if (sl.getStaffLeaveStatusId().intValue() == 1) {
                                leaveDayType = sl.getDateType();
                            }
                        }
                    }
                }

                presentMap.put(staff, leaveDayType);

            }

        }
        return presentMap;

    }

    /**
     * get absent staff in the staff.
     *
     * @param staffList - present staff list
     * @param staffByCategoryList - list of staff in the table - DB.
     * @param considerDate - considerDate.
     * @return map of absent students
     * @throws AkuraAppException throw exception if occur.
     */
    private Map<String, Staff> getAbsentStaffList(List<Staff> staffList, List<Staff> staffByCategoryList,
            Date considerDate) throws AkuraAppException {

        // create an absent map for staffs
        Map<String, Staff> absentMap = new TreeMap<String, Staff>();

        // if there is exists Staff
        if (!staffByCategoryList.isEmpty()) {
            // go through each staff
            Iterator<Staff> staffItr = staffByCategoryList.iterator();

            // if there are staffs
            while (staffItr.hasNext()) {
                Staff staff = staffItr.next();
                absentMap.put(staff.getRegistrationNo(), staff);

            }
        }

        Map<String, Staff> absentTempMap = new TreeMap<String, Staff>();
        // keep all in temp map, before remove elements from it.
        absentTempMap.putAll(absentMap);

        // remove the present staff from the absent list
        for (Map.Entry<String, Staff> entry : absentTempMap.entrySet()) {

            for (Staff stf : staffList) {

                if ((stf.getRegistrationNo().equals(entry.getValue().getRegistrationNo()))) {
                    absentMap.remove(stf.getRegistrationNo());
                }
            }
        }

        // return absent map, contains all the absent
        return absentMap;

    }

    /**
     * set the present staffs name, registration number and leave reason to display in the report.
     *
     * @param presentMap of type map
     * @return staffTemplateList of type object
     */
    private List<Object> setPresentTemplate(Map<Staff, String> presentMap) {

        // create staff template list
        List<Object> staffTemplateList = new ArrayList<Object>();

        // if all the staff is absent
        if (presentMap.isEmpty()) {

            TeacherAttendance teacherAttendance = new TeacherAttendance();
            teacherAttendance.setEmployeeID(HIPAN);
            teacherAttendance.setEmployeeName(HIPAN);
            teacherAttendance.setDateType(HIPAN);
            staffTemplateList.add(teacherAttendance);

        } else {
            // set the present staff name and registration number to display in the report.
            Iterator<Map.Entry<Staff, String>> entries = presentMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<Staff, String> entry = entries.next();
                TeacherAttendance teacherAttendance = new TeacherAttendance();
                teacherAttendance.setEmployeeID((entry.getKey().getRegistrationNo()));
                teacherAttendance.setEmployeeName((entry.getKey().getNameWithIntials()));
                teacherAttendance.setDateType(entry.getValue());
                staffTemplateList.add(teacherAttendance);

            }
        }
        return staffTemplateList;
    }

    /**
     * set setAbsentTemplate - this template for report set the absent staffs name and registration numbers to
     * display in the report.
     *
     * @param absentMap map to holds absent staffs
     * @return staffAbsentTemplateList of type object
     */
    private List<Object> setAbsentTemplate(Map<String, Staff> absentMap) {

        List<Object> staffAbsentTemplateList = new ArrayList<Object>();

        // if all the staffs presents.
        if (absentMap.isEmpty()) {

            TeacherAttendance teacherAttendance = new TeacherAttendance();
            teacherAttendance.setEmployeeID(HIPAN);
            teacherAttendance.setEmployeeName(HIPAN);
            staffAbsentTemplateList.add(teacherAttendance);

        } else {
            // set the absent staff name and registration number to display in the report.
            for (Map.Entry<String, Staff> a : absentMap.entrySet()) {

                TeacherAttendance teacherAttendance = new TeacherAttendance();
                teacherAttendance.setEmployeeID(a.getValue().getRegistrationNo());
                teacherAttendance.setEmployeeName(a.getValue().getNameWithIntials());
                staffAbsentTemplateList.add(teacherAttendance);
            }
        }

        return staffAbsentTemplateList;
    }
}
