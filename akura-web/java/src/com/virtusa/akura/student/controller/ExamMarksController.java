/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

package com.virtusa.akura.student.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Exam;
import com.virtusa.akura.api.dto.ExamAdmission;
import com.virtusa.akura.api.dto.ExamMark;
import com.virtusa.akura.api.dto.ExamSubject;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.StaticDataUtil;

/**
 * This class , manages marks for a given examination.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ExamMarksController {

	/** Represents the model attribute name for index of the status. */
	private static final int STATUS = 14;

	/** Represents the index of admission key. */
	private static final int INDEX_ADMISSION_ID = 10;

	/** Represents the index of admission number. */
	private static final int INDEX_ADMISSION_NO = 9;

	/** Represents the index of subject. */
	private static final int SUBJECT_INDEX = 8;

	/** Represents the index for the exam mark. */
	private static final int EXAM_MARK_ID = 13;

	/**
	 * Represents the condition for the stored procedure where access the new
	 * exam marks.
	 */
	private static final int MARKS_FOR_NEW_STUDENTS = -6;

	/**
	 * Represents the condition for the stored procedure where access the new
	 * exam marks for new subjects.
	 */
	private static final int MARKS_FOR_NEW_SUBJECTS = -5;

	/** Represents the maximum marks. */
	private static final float MAX_MARKS = 100f;

	/** Represents the parameter name of Grade. */
	private static final String GRADE = "grade";

	/** Represents the parameter name of exam key. */
	private static final String EXAM_ID = "examId";

	/** Represents the model attribute name for the optional subjects. */
	private static final String MODEL_OPTIONAL_SUBJECTS = "optionalSubjectList";

	/**
	 * Represents the key for the error message for entering admission number
	 * before saving marks.
	 */
	private static final String EXAM_MARK_UI_ADMISSION = "EXAM.MARK.UI.ADMISSION";

	/** Represents the constant for not applicable. */
	private static final String N_A = "NA";

	/** Represents the model key for the class grade key when it is selected. */
	private static final String CLASS_GRADE_ERROR = "classGradeError";

	/** Represents the model key for the year when it is selected. */
	private static final String ERROR_YEAR = "errorYear";

	/** Represents the model key for the exam key when it is selected. */
	private static final String EXAM_KEY_ERROR = "examKeyError";

	/** Represents the parameter name for the relevant year. */
	private static final String YEAR_VAL = "yearVal";

	/** Represents the key for the message for the max marks. */
	private static final String ERROR_MAX_MARKS = "EXAM.MARKS.MAX.MARKS";

	/** Represents the model name for the status of the edit. */
	private static final String MODEL_STATUS = "status";

	/** Represents the hyphen. */
	private static final String STRING_HIPHEN = "-";

	/** Represents the key for the message for the mandatory. */
	private static final String FOR_CORE_SUBJECTS_MARKS_REQUIRED = "EXAM.MARKS.MADATORY.CORE";

	/** Represents the key for the message for successfully added. */
	private static final String SUCCESSFULLY_ADDED = "EXAM.MARKS.SUCCESS";

	/** Represents the model name for the subjects list. */
	private static final String SUBJECT_LIST = "subjectList";

	/** The model attribute name for the student status id list. */
	private static final String MODEL_STUDENT_STATUS_IDS_LIST = "studentStatusIdsList";

	/** Represents the parameter name of exam subject count. */
	private static final String EXAM_SUBJECT_COUNT = "examSubjectCount";

	/** Represents the model name for studentSubjectList. */
	private static final String STUDENT_SUBJECT_LIST = "studentSubjectList";

	/** Represents the model for names of students. */
	private static final String STUDENT_LIST = "studentList";

	/** Represents the model attribute name for the admission key list. */
	private static final String MODEL_ADMISSION_ID_LIST = "admissionIdList";

	/** Represents the model attribute name for the admission numbers list. */
	private static final String MODEL_ADMISSIONLIST = "admissionList";

	/** Represents the path of the studentExam marks page. */
	private static final String RESULT_PAGE = "student/studentExamMarks";

	/** Represents the key for the error message. */
	private static final String SEARCH_CRITERIA = "EXAM.MARK.SEARCH.CRITERIA";

	/** Represents the model name of message. */
	private static final String MESSAGE = "message";

	/** Represents the parameter name for the exam admission key. */
	private static final String EXAM_ADMISSION_ID = "examAdmissionId";

	/** Represents the parameter name for the exam admission number. */
	private static final String ADMISSION = "Admission";

	/**
	 * Represents the error key for the existence of the admission already
	 * exists.
	 */
	private static final String EXAM_ADMISSION_EXISTS = "EXAM.MARK.ADMISSION.EXISTS";

	/** Represents the parameter name for the exam. */
	private static final String EXAM = "exam";

	/** Represents the parameter name for the grade key. */
	private static final String GRADE_ID = "gradeId";

	/** Represents the attribute name for the Exam Mark object. */
	private static final String MODEL_EXAM_MARK = "examMark";

	/** Represents the path of the studentExammark page. */
	private static final String VIEW_EXAM_MARKS = RESULT_PAGE;

	/** Represents the request mapping for the managing exam mark. */
	private static final String REQ_EXAM_MARKS = "studentExamMarks";

	/** Represents the attribute name for the Exam list. */
	private static final String MODEL_EXAM = "examList";

	/** Represents the request mapping for the exam marks. */
	private static final String SEARCH_EXAM_MARK_REQ = "/searchExamMarks.htm";

	/** model attribute of year list. */
	private static final String MODEL_ATT_YEAR_LIST = "yearList";

	/** Represents the regex pattern. */
	private static final String REGEXP_STRINGONLY = "[^A-Za-z ]";

	/** Represents the key for the error message. */
	private static final String MANDATORY_FIELD_SELECTED_REQUIRED = "REF.UI.MANDATORY.FIELD.SELECTED.REQUIRED";

	/** Represents the request mapping for the saving of Exam Mark. */
	private static final String SAVE_EXAM_MARK = "/saveExamMarks.htm";

	/** Represents the model name for the exam description. */
	private static final String MODEL_EXAM_NAME = "examName";

	/** Represents the mapping for the classes. */
	private static final String FIND_CLASSES_MAPPING = "/findGradeClasses.htm";

	/** attribute for holding selectedValue. */
	private static final String SELECTED_VALUE = "selectedValue";

	/** Represents the model name for the grade description. */
	private static final String MODEL_GRADE_NAME = "gradeName";

	/** Represents the minimum value of the marks. */
	private static final Float MIN_MARKS = 0f;

	/** Represents the model name for the successfully saving. */
	private static final String SUCCESS_MESSAGE = "successMessage";

	/** Represents the key for representing wrong grading. */
	private static final String GRADING_ACRONYM_INCORRECT = "EXAM.MARKS.GRADING.ACRONYM";

	/** Represents the name for model for the year. */
	private static final String MODEL_YEAR = "modelYear";

	/** Represents an instance of CommonService. */
	private CommonService commonService;

	/** Represents an instance of StudentService. */
	private StudentService studentService;

	/**
	 * Injects an instance of StudentService.
	 * 
	 * @param objStudentService
	 *            - an instance of StudentService.
	 */
	public void setStudentService(StudentService objStudentService) {

		this.studentService = objStudentService;
	}

	/**
	 * Injects an instance of CommonService.
	 * 
	 * @param objCommonService
	 *            - an instance of CommonService.
	 */
	public void setCommonService(final CommonService objCommonService) {

		this.commonService = objCommonService;
	}

	/**
	 * Manages the Exam Mark view.
	 * 
	 * @param model
	 *            - a hash map contains an object of Exam Mark.
	 * @return - a model contains an object of Exam Mark.
	 */
	@RequestMapping(REQ_EXAM_MARKS)
	public String manageExamMarks(final ModelMap model) {

		model.addAttribute(MODEL_EXAM_MARK, new ExamMark());
		return VIEW_EXAM_MARKS;
	}

	/**
	 * Returns a list of Exams.
	 * 
	 * @return - a list of Exams.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@ModelAttribute(MODEL_EXAM)
	public List<Exam> populateExam() throws AkuraAppException {

		return SortUtil.sortExamList(commonService.getExamList());
	}

	/**
	 * Returns a list of years.
	 * 
	 * @return gradeClassList - a list of years.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@ModelAttribute(MODEL_ATT_YEAR_LIST)
	public List<String> populateYearList() throws AkuraAppException {

		final int maxYear = 2;
		return DateUtil.getPastYears(maxYear);
	}

	/**
	 * Searches the exam mark for a given criteria.
	 * 
	 * @param examMark
	 *            - an instance of Exam Mark.
	 * @param result
	 *            - error messages of Exam Mark.
	 * @param request
	 *            - an instance keeps request scope data.
	 * @param model
	 *            - a hash map containing an object of Exam Mark.
	 * @return - the view to be redirected.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when searching a exam
	 *             mark instance.
	 */
	@RequestMapping(SEARCH_EXAM_MARK_REQ)
	public String searchExamMarks(
			@ModelAttribute(MODEL_EXAM_MARK) final ExamMark examMark,
			final BindingResult result, final HttpServletRequest request,
			final ModelMap model) throws AkuraAppException {

		ErrorMsgLoader errorMsgLoader = new ErrorMsgLoader();
		String resultPage = RESULT_PAGE;
		String exam = request.getParameter(EXAM_ID);
		int examId = 0;
		int year = 0;
		if (exam != null) {
			examId = Integer.parseInt(exam);
		}
		String stringYear = request.getParameter(AkuraWebConstant.YEAR);

		// if the user selected the year then take from the hidden field.
		if (stringYear != null) {
			year = Integer.parseInt(stringYear);
		}

		String grade = request.getParameter(GRADE);

		// if error messages are there set the values to the drop downs.
		setValuesWhenError(model, examId, grade, year);

		int examMarksListSize = studentService
				.getAllExamMarksList(year, examId);

		if ((examMarksListSize != 0
				&& (grade == null
						|| (AkuraWebConstant.STRING_ZERO.equals(grade)) || examId == 0)) || year == 0) {
			String message = errorMsgLoader
					.getErrorMessage(MANDATORY_FIELD_SELECTED_REQUIRED);
			model.addAttribute(MESSAGE, message);
		}
		if (((examId == 0 || (grade == null || (AkuraWebConstant.STRING_ZERO
				.equals(grade)))) && examMarksListSize != 0) || year == 0) {
			String message = errorMsgLoader
					.getErrorMessage(MANDATORY_FIELD_SELECTED_REQUIRED);
			model.addAttribute(MESSAGE, message);
		} else {

			if (examMarksListSize == 0) {
				if ((grade == null
						|| (AkuraWebConstant.STRING_ZERO.equals(grade))
						|| examId == 0) || year == 0) {
					String message = errorMsgLoader
							.getErrorMessage(MANDATORY_FIELD_SELECTED_REQUIRED);
					model.addAttribute(MESSAGE, message);
				} else {

					searchSavedExamMarks(request, model, year,
							examMarksListSize);
				}
			} else {
				searchSavedExamMarks(request, model, year, examMarksListSize);
			}
		}
		return resultPage;
	}

	/**
	 * Searches saved Exam Marks.
	 * 
	 * @param request
	 *            - an instance contains the request scope data.
	 * @param model
	 *            - a hash map contains the exam marks related data.
	 * @param year
	 *            - the relevant year.
	 * @param examMarksListSize
	 *            - the size of the Exam Marks.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	private void searchSavedExamMarks(final HttpServletRequest request,
			final ModelMap model, int year, final int examMarksListSize)
			throws AkuraAppException {

		final Map<Integer, String> studentList = new LinkedHashMap<Integer, String>();
		final List<Integer> studentIdList = new ArrayList<Integer>();
		List<Integer> newSubjects = null;
		int status = 0;
		int indexSubject = 2;
		List<String> subjectList = new ArrayList<String>();
		final Map<Integer, String> admissionList = new LinkedHashMap<Integer, String>();
		final Map<Integer, Integer> admissionIdList = new LinkedHashMap<Integer, Integer>();
		final List<Integer> studentStatusIdList = new ArrayList<Integer>();

		// populates exam key.
		int examId = populatesExamKey(request);
		Exam exam = commonService.findExam(examId);

		// populates the key of the grade.
		String gradeId = populatesGradeKey(request);
		ClassGrade classGrade = commonService.findClassGrade(Integer
				.parseInt(gradeId));

		// if exam subjects list is not empty check weather there are new
		// subjects for the relevant exam.
		if (examMarksListSize != 0) {
			newSubjects = commonService.getNewSubjects(examId, year);
		}

		List<?> examStudentList = studentService.searchExamMarks(
				examMarksListSize, year, gradeId, examId);
		examStudentList = getsExamStudentList(examId, newSubjects, gradeId,
				year, examStudentList);

		// sort the exam student list with the subject name.
		List<Object[]> sortedList = (List<Object[]>) examStudentList;
		sortedList = SortUtil.sortObjectArrayList(sortedList, indexSubject);
		Iterator<?> iteratorList = sortedList.iterator();

		while (iteratorList.hasNext()) {
			int indexName = 0;
			int indexStudentKey = 1;
			Object[] object = (Object[]) iteratorList.next();
			String studentName = (String) object[indexName];
			Integer studentKey = (Integer) object[indexStudentKey];
			String subject = (String) object[indexSubject];
			String admissionNo = (String) object[INDEX_ADMISSION_NO];
			Integer admissionId = (Integer) object[INDEX_ADMISSION_ID];
			if (status == 0) {
				if (object.length >= STATUS) {
					status = (Integer) object[STATUS];
				}
			}
			if (!studentIdList.contains(studentKey)) {
				generateAccessLists(studentList, admissionList,
						admissionIdList, studentName, studentKey, admissionNo,
						admissionId);
				studentIdList.add(studentKey);
			}
			// sets a list of subjects
			if (!subjectList.contains(subject)) {
				subjectList.add(subject);
			}
		}

		// get the student status and set it to the model to check the student
		// status.
		if (!studentIdList.isEmpty()) {

			studentStatusIdList.addAll(studentService
					.getStudentStatusListByStudentIdsList(studentIdList));
		}

		// Add the student status keys list to the model.
		model.addAttribute(MODEL_STUDENT_STATUS_IDS_LIST, studentStatusIdList);

		int examSubjectCount = commonService.getCountExamSubjects(examId);
		setModel(request, model, studentList, admissionList, admissionIdList,
				examStudentList, examSubjectCount);
		setModelForMessages(model, year, exam, classGrade, status, subjectList);

		// gets optional subjects to indicates in the view.
		List<?> optionalSubjectList = commonService
				.getOptionalSubjectList(examId);
		model.addAttribute(MODEL_OPTIONAL_SUBJECTS, optionalSubjectList);

	}

	/**
	 * Generates the error message for the no results found for the search
	 * criteria.
	 * 
	 * @param model
	 *            - the hash map contains the search related error messages.
	 */
	private void generateErrorForSearch(final ModelMap model) {

		String message = new ErrorMsgLoader().getErrorMessage(SEARCH_CRITERIA);
		model.addAttribute(MESSAGE, message);
	}

	/**
	 * Set values of the drop downs when error messages populated.
	 * 
	 * @param model
	 *            - the relevant hash map contains the fields related values.
	 * @param examId
	 *            - the selected exam key.
	 * @param classGrade
	 *            - the selected class grade.
	 * @param year
	 *            - the selected year.
	 */
	private void setValuesWhenError(final ModelMap model, int examId,
			String classGrade, int year) {

		// set year into model as if the year is not selected, year = 0;
		model.addAttribute(ERROR_YEAR, year);

		// sets the value to the class grade drop down if it is selected.
		if (classGrade != null) {
			int classGradeKey = Integer.parseInt(classGrade);
			if (classGradeKey > 0) {
				model.addAttribute(CLASS_GRADE_ERROR, classGradeKey);
			}
		}

		// sets the value to the exam drop down if it is selected.
		if (examId > 0) {
			model.addAttribute(EXAM_KEY_ERROR, examId);
		}

	}

	/**
	 * Manages the maps for admission number keys, student names, admission
	 * numbers for the relevant student key to refer on the view.
	 * 
	 * @param studentList
	 *            - the map contains the student names for the relevant student
	 *            key.
	 * @param admissionList
	 *            - the map contains the admission numbers of the all students
	 *            of a given exam class grade.
	 * @param admissionIdList
	 *            - the map contains the admission number primary keys of the
	 *            all students of a given exam class grade.
	 * @param studentName
	 *            - the name of the student.
	 * @param studentKey
	 *            - the relevant primary key of the student.
	 * @param admissionNo
	 *            - the relevant admission number description of the student.
	 * @param admissionId
	 *            - the relevant admission number key of the student.
	 */
	private void generateAccessLists(final Map<Integer, String> studentList,
			final Map<Integer, String> admissionList,
			final Map<Integer, Integer> admissionIdList, String studentName,
			Integer studentKey, String admissionNo, Integer admissionId) {

		studentList.put(studentKey, studentName);
		admissionList.put(studentKey, admissionNo);
		admissionIdList.put(studentKey, admissionId);
	}

	/**
	 * Populates the key of the exam.
	 * 
	 * @param request
	 *            - the object for the request scope
	 * @return - the key of the exam.
	 */
	private int populatesExamKey(final HttpServletRequest request) {

		int examId = Integer.parseInt(request.getParameter(EXAM_ID));
		if (examId == 0) {
			// when editing.
			examId = Integer.parseInt(request.getParameter(EXAM));
		}
		return examId;
	}

	/**
	 * Populates the key of the grade.
	 * 
	 * @param request
	 *            - the object for the request scope
	 * @return - the key of the grade.
	 */
	private String populatesGradeKey(final HttpServletRequest request) {

		String gradeId = request.getParameter(GRADE);
		if (gradeId == null) {
			gradeId = request.getParameter(GRADE_ID);
		}
		return gradeId;
	}

	/**
	 * Sets the model objects to be used in the view.
	 * 
	 * @param model
	 *            - a hash map contains the data.
	 * @param year
	 *            - the relevant year
	 * @param exam
	 *            - an instance of Exam
	 * @param classGrade
	 *            - an instance of Class Grade
	 * @param status
	 *            - the status of the exam marks update.
	 * @param subjectList
	 *            - a list of subjects.
	 */
	private void setModelForMessages(final ModelMap model, int year, Exam exam,
			ClassGrade classGrade, int status, List<String> subjectList) {

		if (classGrade != null) {
			model.addAttribute(MODEL_GRADE_NAME, classGrade.getDescription());
		}
		if (exam != null) {
			model.addAttribute(MODEL_EXAM_NAME, exam.getDescription());
		}
		if (year > 0) {
			model.addAttribute(MODEL_YEAR, year);
		}
		model.addAttribute(MODEL_STATUS, status);
		model.addAttribute(SUBJECT_LIST, subjectList);
	}

	/**
	 * Returns the model and the request with the properties of the Exam Marks.
	 * 
	 * @param request
	 *            - an object of the request scope attributes
	 * @param model
	 *            - a hash map contains the properties of the exam marks
	 * @param studentList
	 *            - the names of the students
	 * @param admissionList
	 *            - a list of admissions of exams
	 * @param admissionIdList
	 *            - a list of keys of the admission
	 * @param examStudentList
	 *            - a list of exam students
	 * @param examSubjectCount
	 *            - the count of the exam subjects
	 */
	private void setModel(final HttpServletRequest request,
			final ModelMap model, final Map<Integer, String> studentList,
			final Map<Integer, String> admissionList,
			final Map<Integer, Integer> admissionIdList,
			List<?> examStudentList, int examSubjectCount) {

		request.setAttribute(EXAM_SUBJECT_COUNT, examSubjectCount);
		model.addAttribute(STUDENT_LIST, studentList);
		model.addAttribute(MODEL_ADMISSIONLIST, admissionList);
		model.addAttribute(MODEL_ADMISSION_ID_LIST, admissionIdList);
		if (examStudentList.isEmpty()) {
			// generates the error message when no data for the search criteria.
			generateErrorForSearch(model);
		}
		model.addAttribute(STUDENT_SUBJECT_LIST, examStudentList);
	}

	/**
	 * Finds the class grades relevant to the grade of the given exam.
	 * 
	 * @param examMark
	 *            - an instance of ExamMark
	 * @param request
	 *            - an object contains the request scope data.
	 * @return - a list of classes
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@ResponseBody
	@RequestMapping(FIND_CLASSES_MAPPING)
	public String findClasses(
			@ModelAttribute(MODEL_EXAM_MARK) final ExamMark examMark,
			HttpServletRequest request) throws AkuraAppException {

		StringBuilder allClasses = new StringBuilder();
		String selectedExamDescription = request.getParameter(SELECTED_VALUE);
		int selectedExamId = Integer.parseInt(selectedExamDescription);
		Exam exam = commonService.findExamById(selectedExamId);

		// if exam is not null.
		if (exam != null) {
			Grade selectedGrade = commonService
					.findGradeById(exam.getGradeId());
			List<ClassGrade> classGrades = commonService
					.getClassGradeListByGrade(selectedGrade);
			classGrades = SortUtil.sortClassGrades(classGrades);
			boolean isFirst = true;
			StringBuilder classes = new StringBuilder();

			for (ClassGrade classGrade : classGrades) {
				classes.append(classGrade.getDescription());
				classes.append(STRING_HIPHEN);
				classes.append(classGrade.getClassGradeId());

				// appends values with comma separated.
				isFirst = StaticDataUtil.appendValues(allClasses, isFirst,
						classes, AkuraWebConstant.STRING_COMMA);
			}
		}
		return allClasses.toString();
	}

	/**
	 * Saves exam marks.
	 * 
	 * @param examMark
	 *            - an instance of Exam Mark.
	 * @param request
	 *            - an instance of HttpServletRequest.
	 * @param result
	 *            - result of the exam marks
	 * @param model
	 *            - a hash map containing details of Exam Mark.
	 * @return - the view to be redirected.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(SAVE_EXAM_MARK)
	public String saveExamMarks(
			@ModelAttribute(MODEL_EXAM_MARK) final ExamMark examMark,
			BindingResult result, final HttpServletRequest request,
			final ModelMap model) throws AkuraAppException {

		String resultPage = RESULT_PAGE;
		ExamMark examMarks = null;
		int examId = 0;
		List<Integer> newSubjects = null;
		ErrorMsgLoader errorMsgLoader = new ErrorMsgLoader();
		Map<String, ExamMark> examMarkList = new LinkedHashMap<String, ExamMark>();
		List<Integer> studentIdList = new ArrayList<Integer>();
		String stringExamId = request.getParameter(EXAM);
		String gradeKey = request.getParameter(GRADE_ID);

		if (stringExamId != null) {
			examId = Integer.parseInt(stringExamId);
		}

		// access the selected year value.
		String yearString = request.getParameter(YEAR_VAL);
		int yearVal = Integer.parseInt((yearString.split(STRING_HIPHEN))[0]);

		// if error messages are there set the values to the drop downs.
		setValuesWhenError(model, examId, gradeKey, yearVal);

		// gets the current year.
		int year = getCurrentYear(request, yearVal);

		int examMarksListSize = studentService
				.getAllExamMarksList(year, examId);
		Exam exam = commonService.findExam(examId);

		// if exam marks are not empty then check weather there are new subjects
		// for the relevant exam.
		if (examMarksListSize != 0) {
			newSubjects = commonService.getNewSubjects(examId, year);
		}

		List<?> examStudentList = studentService.searchExamMarks(
				examMarksListSize, year, gradeKey, examId);
		examStudentList = getsExamStudentList(examId, newSubjects, gradeKey,
				year, examStudentList);
		Iterator<?> iteratorList = examStudentList.iterator();
		try {
			while (iteratorList.hasNext()) {
				int examAdmissionId = 0;
				int examMarkKey = 0;
				int index = 2;
				int indexStudentId = 1;
				Integer examSubject = 0;
				ExamAdmission examAdmission = null; // declares an instance of
													// ExamAdmission.
				Object[] object = (Object[]) iteratorList.next();
				String student = (String) object[0];
				Integer studentId = (Integer) object[indexStudentId];
				String subject = (String) object[index];

				// if exam marks list is not empty gets the key of the exam
				// mark.
				if (examMarksListSize != 0) {
					examMarkKey = getExamMarkKey(request, examMarkKey, object);
				}
				if (studentId != null) {
					String marks = (request.getParameter(subject + studentId
							+ student)).trim();
					String admission = (request.getParameter(student
							+ studentId + ADMISSION)).trim();
					String examAdmissions = request.getParameter(student
							+ studentId + EXAM_ADMISSION_ID);

					// retrieves the exam admission object.
					examAdmission = getExamAdmission(examId, year,
							examAdmissionId, examAdmission, studentId,
							admission, examAdmissions);

					// initializes the exam mark object.
					examMarks = initializesExamMark(examMarkKey);

					examSubject = (Integer) object[SUBJECT_INDEX];
					ExamSubject examSubjectObj = commonService
							.findExamSubject(examSubject);

					// creates exam mark object.
					// if admission number is null for already saved object set
					// it a s null itself.
					if (examAdmission.getAdmissionNo() != null
							&& AkuraWebConstant.EMPTY_STRING.equals(admission)
							&& examAdmission.getExamAdmissionId() != 0) {
						String indexNo = examAdmission.getAdmissionNo();
						createExamAdmission(studentId, examId, year,
								examAdmission, indexNo);

						// populates the error message for the required
						// admission number for the existing
						// exam marks.
						populateRequiredAdmissionMsg(model, examMarks,
								examAdmission);
					} else {

						// returns database admission number if user passes
						// empty admission number.
						if (examAdmission.getExamAdmissionId() > 0
								&& AkuraWebConstant.EMPTY_STRING
										.equals(admission)) {
							admission = examAdmission.getAdmissionNo();
						}
						createExamAdmission(studentId, examId, year,
								examAdmission, admission);
					}

					if (examMarks != null) {
						createExamMarks(examMarks, studentId, year,
								examSubject, model, gradeKey);
					}

					// if the admission number is not equal with the admission
					// number update.
					if (examAdmission.getExamAdmissionId() > 0) {

						// updates the exam admission number.
						admission = updateExamAdmissionNumber(examAdmission);

						examAdmission = studentService
								.findExamAdmissionById(examAdmission
										.getExamAdmissionId());
					}

					// sets the exam mark for a non-empty admission.
					marks = setMarksForAdmission(model, examMarks,
							examAdmission, marks, admission, exam);

					// saves exam admission object.
					saveExamAdmission(examMarks, studentIdList, examAdmission,
							studentId);

					if (examMarks != null) {
						setExamGradesMarks(model, examMarks, errorMsgLoader,
								exam, marks, examSubjectObj);
					}

					if (examAdmission != null) {
						examMarks.setExamAdmission(examAdmission
								.getExamAdmissionId());
					}

					// sets update status.
					setUpdateStatus(examMarks);
					examMarkList.put(
							String.valueOf(examSubject)
									+ String.valueOf(studentId), examMarks);
				}
			}

			Set<ExamMark> list = new HashSet<ExamMark>(examMarkList.values());
			studentService.saveOrUpdateExamMarks(new ArrayList<ExamMark>(list));

			// after saving, make the marks panel visible.
			makeVisibleMarksPanel(request, model, examId, year);

			if (model.size() == 0) {
				model.addAttribute(SUCCESS_MESSAGE,
						errorMsgLoader.getErrorMessage(SUCCESSFULLY_ADDED));
			}
		} catch (NumberFormatException e) {
			model.addAttribute(MESSAGE, AkuraWebConstant.MISMATCH_ERROR_MARKS);
		} catch (AkuraAppException e) {
			handleDataAccessException(model, errorMsgLoader, e);
		}
		return resultPage;
	}

	/**
	 * Saves exam admission object, if it is a new student and the new exam
	 * admission. Then sets the exam admission id to the exam marks that refers
	 * it.
	 * 
	 * @param examMarks
	 *            - the exam marks object
	 * @param studentIdList
	 *            - the list of the student keys
	 * @param examAdmission
	 *            - the newly created exam admission object
	 * @param studentId
	 *            - the key of the student.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	private void saveExamAdmission(ExamMark examMarks,
			List<Integer> studentIdList, ExamAdmission examAdmission,
			Integer studentId) throws AkuraAppException {

		if (!studentIdList.contains(studentId)
				&& examAdmission.getExamAdmissionId() == 0) {
			studentIdList.add(studentId);
			ExamAdmission newExamAdmission = studentService
					.saveExamAdmission(examAdmission);
			examMarks.setExamAdmission(newExamAdmission.getExamAdmissionId());
		}
	}

	/**
	 * Makes visible the exam marks panel after saving the exam marks.
	 * 
	 * @param request
	 *            - the object with the request scope.
	 * @param model
	 *            - the hash map contains the exam marks related data.
	 * @param examId
	 *            - the key of the exam.
	 * @param year
	 *            - the relevant year.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	private void makeVisibleMarksPanel(final HttpServletRequest request,
			final ModelMap model, int examId, int year)
			throws AkuraAppException {

		int examMarksListSize;
		examMarksListSize = studentService.getAllExamMarksList(year, examId);
		searchSavedExamMarks(request, model, year, examMarksListSize);
	}

	/**
	 * Populates the required error message.
	 * 
	 * @param model
	 *            - the hash map contains the exam related data.
	 * @param examMarks
	 *            - the relevant exam mark.
	 * @param examAdmission
	 *            - the exam admission number.
	 */
	private void populateRequiredAdmissionMsg(final ModelMap model,
			ExamMark examMarks, ExamAdmission examAdmission) {

		if (!AkuraWebConstant.EMPTY_STRING.equals(examAdmission
				.getAdmissionNo())
				&& examMarks != null
				&& examMarks.getExamMarksId() > 0) {
			model.addAttribute(MESSAGE, new ErrorMsgLoader()
					.getErrorMessage(EXAM_MARK_UI_ADMISSION));
		}
	}

	/**
	 * Updates the exam admission number.
	 * 
	 * @param examAdmission
	 *            - the existing exam admission
	 * @return - the updated value of the admission number.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	private String updateExamAdmissionNumber(ExamAdmission examAdmission)
			throws AkuraAppException {

		String admission = examAdmission.getAdmissionNo();
		examAdmission.setAdmissionNo(admission);
		studentService.updateExamAdmission(examAdmission);
		return admission;
	}

	/**
	 * Manages the update status of the exam marks.
	 * 
	 * @param examMarks
	 *            - marks of the exam.
	 */
	private void setUpdateStatus(final ExamMark examMarks) {

		// if the exam marks grading or the marks is empty, then set the status
		// as 0.
		// else increment in one.
		if (examMarks != null && examMarks.getMarks() != null
				&& examMarks.getMarks() > 0 || examMarks != null
				&& examMarks.getGrading() != null && examMarks.getGrading() > 0
				|| examMarks != null && examMarks.getAbsent()
				|| examMarks != null && examMarks.getOptional()) {
			int status = examMarks.getStatus();
			status = status + 1;
			examMarks.setStatus(status);
		}
	}

	/**
	 * Initializes the exam marks.
	 * 
	 * @param examMarkKey
	 *            - the key of the exam marks.
	 * @return - the initialized exam marks.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	private ExamMark initializesExamMark(final int examMarkKey)
			throws AkuraAppException {

		ExamMark examMarks;

		// if exam mark key is zero instantiates a new exam mark object.
		if (examMarkKey == 0) {
			examMarks = new ExamMark();
		} else {
			examMarks = studentService.findExamMarkById(examMarkKey);
		}
		return examMarks;
	}

	/**
	 * Retrieves the exam admission.
	 * 
	 * @param examId
	 *            - the key of the exam
	 * @param year
	 *            - the relevant year
	 * @param examAdmissionId
	 *            - the key of the exam admission
	 * @param examAdmission
	 *            - an instance of exam admission
	 * @param studentId
	 *            - the key of the student
	 * @param admission
	 *            - the admission
	 * @param examAdmissions
	 *            - the admission number
	 * @return - the relevant exam admission
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	private ExamAdmission getExamAdmission(int examId, int year,
			int examAdmissionId, ExamAdmission examAdmission,
			Integer studentId, String admission, String examAdmissions)
			throws AkuraAppException {

		if (examAdmissions.trim().length() != 0 && admission != null) {
			examAdmissionId = Integer.parseInt(examAdmissions);
			examAdmission = studentService
					.findExamAdmissionById(examAdmissionId);
		}

		// finds exam admission if the exam admission is not already saved with
		// other details.
		if (examAdmission == null) {
			examAdmission = studentService.findExamAdmissionByStudentId(
					admission, year, studentId, examId);
		}
		if ((examAdmissionId == 0 && examAdmission == null)) {
			examAdmission = new ExamAdmission();
		}
		return examAdmission;
	}

	/**
	 * Retrieves the current year as YYYY format.
	 * 
	 * @param request
	 *            - a request scope object.
	 * @param yearVal
	 *            - the relevant year when it is going to save.
	 * @return - the current year.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	private int getCurrentYear(final HttpServletRequest request, int yearVal)
			throws AkuraAppException {

		if (yearVal == 0) {
			yearVal = Integer.parseInt(request
					.getParameter(AkuraWebConstant.YEAR));
			if (yearVal <= 0) {
				yearVal = DateUtil.currentYearOnly();
			}
		}
		return yearVal;
	}

	/**
	 * Handles the data access exception.
	 * 
	 * @param model
	 *            - a hash map contains the error messages.
	 * @param errorMsgLoader
	 *            - an instance of Error message loader.
	 * @param exception
	 *            - an instance of AkuraAppException
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	private void handleDataAccessException(final ModelMap model,
			ErrorMsgLoader errorMsgLoader, AkuraAppException exception)
			throws AkuraAppException {

		if (exception.getCause() instanceof DataIntegrityViolationException) {
			model.addAttribute(MESSAGE,
					errorMsgLoader.getErrorMessage(EXAM_ADMISSION_EXISTS));
		} else {
			throw exception;
		}
	}

	/**
	 * Sets marks for the exam admission.
	 * 
	 * @param model
	 *            - a hash map containing the data.
	 * @param examMarks
	 *            - an instance of exam marks
	 * @param examAdmission
	 *            - an instance of exam admission
	 * @param marks
	 *            - the marks
	 * @param admission
	 *            - the user provided admission
	 * @param exam
	 *            - an instance of the relevant exam
	 * @return - the marks
	 */
	private String setMarksForAdmission(final ModelMap model,
			ExamMark examMarks, ExamAdmission examAdmission, String marks,
			String admission, Exam exam) {

		if (AkuraWebConstant.EMPTY_STRING.equals(admission)
				&& !(AkuraWebConstant.EMPTY_STRING.equals(marks))) {

			if (examAdmission.getExamAdmissionId() == 0) {
				marks = AkuraWebConstant.EMPTY_STRING;
			} else if (examMarks != null && examMarks.getExamMarksId() > 0) {

				if (exam.getMark()) {
					Float dbMarks = examMarks.getMarks();

					// returns database marks is null for already saved exam
					// mark
					// then set as empty. Else convert into string.
					marks = dbMarks != null ? String.valueOf(dbMarks)
							: AkuraWebConstant.EMPTY_STRING;

					// returns empty string for marks if the admission number is
					// empty for a newly
					// saving exam mark.
				} else {
					Integer dbGrading = examMarks.getGrading();
					marks = dbGrading != null ? String.valueOf(dbGrading)
							: AkuraWebConstant.EMPTY_STRING;
				}
			} else if (examMarks != null && examMarks.getExamMarksId() == 0) {
				marks = AkuraWebConstant.EMPTY_STRING;
			}

			model.addAttribute(MESSAGE, new ErrorMsgLoader()
					.getErrorMessage(EXAM_MARK_UI_ADMISSION));
		}
		return marks;
	}

	/**
	 * Creates Exam Admission object.
	 * 
	 * @param studentId
	 *            - the key of the student
	 * @param examId
	 *            - the key of the exam
	 * @param year
	 *            - the relevant year
	 * @param examAdmission
	 *            - the exam admission object
	 * @param admission
	 *            - the admission of the exam admission
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	private void createExamAdmission(final Integer studentId, final int examId,
			final int year, final ExamAdmission examAdmission,
			final String admission) throws AkuraAppException {

		if (examAdmission != null) {
			examAdmission.setAdmissionNo(admission);
			examAdmission.setStudentId(studentId);
			examAdmission.setExamId(examId);
			examAdmission.setYear(DateUtil.getDate(year));
		}
	}

	/**
	 * Returns a list of exam student list based on the conditions.
	 * 
	 * @param examId
	 *            - the key of the exam
	 * @param newSubjects
	 *            - a list of new subjects
	 * @param gradeKey
	 *            - the key of the grade
	 * @param year
	 *            - the relevant year
	 * @param examStudentList
	 *            - a list of exam students
	 * @return - a list of exam subjects
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	private List<?> getsExamStudentList(final int examId,
			final List<Integer> newSubjects, String gradeKey, int year,
			List<?> examStudentList) throws AkuraAppException {

		List<?> newStudentList = null;

		// gets exam student list for new students that is newly assigned to the
		// class and the
		// exam students list for newly assigned subject.
		if (newSubjects != null && !newSubjects.isEmpty()
				&& !examStudentList.isEmpty()) {
			newStudentList = unionExamMarksList(examId, gradeKey, year);
		} else {
			newStudentList = studentService.searchExamMarks(-1, year, gradeKey,
					examId);
		}

		// merges with the existing exam students list.
		if (newStudentList != null && !newStudentList.isEmpty()) {
			examStudentList = ListUtils.union(examStudentList, newStudentList);
		}

		// if new subjects are allocated for the exam , then get the exam
		// related data.
		if (newSubjects != null && !newSubjects.isEmpty()
				&& !examStudentList.isEmpty()) {
			List<?> newExamStudentList = studentService
					.searchExamMarksWithArray(year, gradeKey, examId,
							newSubjects);
			examStudentList = ListUtils.union(examStudentList,
					newExamStudentList);
		}
		return examStudentList;
	}

	/**
	 * Returns the exam mark key for a given student key.
	 * 
	 * @param request
	 *            - an request scope object contains the relevant data
	 * @param examMarkKey
	 *            - the key of the exam mark for a given student
	 * @param object
	 *            - an object array contains the exam marks for student
	 * @return - the key of the exam mark
	 */
	private int getExamMarkKey(final HttpServletRequest request,
			int examMarkKey, Object[] object) {

		if (object.length > EXAM_MARK_ID) {
			Integer examMar = (Integer) object[EXAM_MARK_ID];
			String examMarkId = Integer.toString(examMar);
			examMarkId = request.getParameter(examMarkId);
			examMarkKey = Integer.parseInt(examMarkId);
		}
		return examMarkKey;
	}

	/**
	 * Adds the lists of the exam subject marks for new students that admission
	 * number is null and the list of exam subject marks for the new subjects.
	 * 
	 * @param examId
	 *            - the key of the exam
	 * @param gradeKey
	 *            - the key of the grade
	 * @param year
	 *            - the relevant year
	 * @return - a list of the exam mark subjects
	 * @throws AkuraAppException
	 *             - The exception details that occurred processing.
	 */
	private List<?> unionExamMarksList(int examId, String gradeKey, int year)
			throws AkuraAppException {

		List<?> newStudentList = studentService.searchExamMarks(
				MARKS_FOR_NEW_SUBJECTS, year, gradeKey, examId);
		List<?> newStudentListNotIn = studentService.searchExamMarks(
				MARKS_FOR_NEW_STUDENTS, year, gradeKey, examId);
		return ListUtils.union(newStudentListNotIn, newStudentList);
	}

	/**
	 * Sets the Exam Marks and the Grading for the Exam Mark object.
	 * 
	 * @param model
	 *            - a hash map contains the exam mark properties.
	 * @param examMarks
	 *            - an instance of Exam Mark
	 * @param errorMsgLoader
	 *            - the error loader
	 * @param exam
	 *            - an instance of Exam
	 * @param marks
	 *            - the marks of the exam
	 * @param examSubjectObj
	 *            - an instance of ExamSubject for the relevant exam subject
	 * @throws AkuraAppException
	 *             - The exception details that occurred processing.
	 */
	private void setExamGradesMarks(final ModelMap model, ExamMark examMarks,
			ErrorMsgLoader errorMsgLoader, Exam exam, String marks,
			ExamSubject examSubjectObj) throws AkuraAppException {

		Float subjectMark = null;
		boolean isOptional = examSubjectObj.getOptionalSubject();
		boolean absent = AkuraWebConstant.ABSENT_STRING.equalsIgnoreCase(marks);
		boolean optionalSubject = N_A.equalsIgnoreCase(marks);
		if (!isOptional) {
			if (optionalSubject) {
				model.addAttribute(MESSAGE, errorMsgLoader
						.getErrorMessage(FOR_CORE_SUBJECTS_MARKS_REQUIRED));
				marks = null;
			}
		} else {
			examMarks.setOptional(optionalSubject);
		}
		examMarks.setAbsent(absent);

		if (exam.getMark()) {

			// manages the exam marks.
			if (!(absent || (isOptional && optionalSubject) || AkuraWebConstant.EMPTY_STRING
					.equals(marks))) {
				try {
					if (!optionalSubject) {
						subjectMark = Float.valueOf(marks);
						if (subjectMark != null && subjectMark > MAX_MARKS
								|| subjectMark != null
								&& subjectMark < MIN_MARKS) {
							model.addAttribute(MESSAGE, errorMsgLoader
									.getErrorMessage(ERROR_MAX_MARKS));

							// if the marks is less than 0 or higher than 100 ,
							// subjectMark will be reset to
							// null.
							subjectMark = null;
						}
					}
				} catch (NumberFormatException e) {
					model.addAttribute(MESSAGE,
							AkuraWebConstant.MISMATCH_ERROR_MARKS);
				}
			}
			examMarks.setMarks(subjectMark);
		} else {

			// manage exam gradings.
			manageExamGrading(model, examMarks, marks, isOptional, absent,
					optionalSubject);
		}
	}

	/**
	 * Manages the exam gradings.
	 * 
	 * @param model
	 *            - the hash map contains the grading related values.
	 * @param examMarks
	 *            - the relevant exam mark for the exam subject.
	 * @param marks
	 *            - the relevant mark/grading
	 * @param isOptional
	 *            - the status of optional subject.
	 * @param absent
	 *            - the status of the presence
	 * @param optionalSubject
	 *            - optional subject status.
	 * @throws AkuraAppException
	 *             - The exception details that occurred processing.
	 */
	private void manageExamGrading(final ModelMap model, ExamMark examMarks,
			String marks, boolean isOptional, boolean absent,
			boolean optionalSubject) throws AkuraAppException {

		Integer gradingId = null;
		if (!(absent || (isOptional && optionalSubject) || AkuraWebConstant.EMPTY_STRING
				.equals(marks))) {
			if (!optionalSubject) {
				Matcher makeMatch = checkPattern(marks);

				if (makeMatch.find()) {
					model.addAttribute(MESSAGE,
							AkuraWebConstant.MISMATCH_ERROR_MARKS);
				} else {
					gradingId = commonService.findGradingByDes(marks);
					if (gradingId == null) {
						model.addAttribute(MESSAGE, new ErrorMsgLoader()
								.getErrorMessage(GRADING_ACRONYM_INCORRECT));
					}
				}
			}
		}
		examMarks.setGrading(gradingId);
	}

	/**
	 * Checks the pattern of the float.
	 * 
	 * @param marks
	 *            - the marks of the Exam for a student.
	 * @return - the status of the pattern of the Marks.
	 */
	private Matcher checkPattern(final String marks) {

		Pattern stringOnly = Pattern.compile(REGEXP_STRINGONLY);
		return stringOnly.matcher(marks);
	}

	/**
	 * Sets the properties of the Exam Mark object.
	 * 
	 * @param examMarks
	 *            - an instance of Exam Mark.
	 * @param studentId
	 *            - the key of the student.
	 * @param year
	 *            - the relevant year of the exam mark.
	 * @param model
	 *            - a hash map contains the exam marks related data
	 * @param examSubject
	 *            - the exam key of the exam marks.
	 * @param classGradeKey
	 *            - the key of the class grade.
	 * @throws AkuraAppException
	 *             - The exception details that occurred processing.
	 */
	private void createExamMarks(ExamMark examMarks, int studentId, int year,
			Integer examSubject, ModelMap model, String classGradeKey)
			throws AkuraAppException {

		int status = examMarks.getStatus();
		model.addAttribute(MODEL_STATUS, status);
		examMarks.setStatus(status);
		examMarks.setExamSubject(examSubject);
		examMarks.setStudent(studentId);
		examMarks.setYear(DateUtil.getDate(year));
	}
}
