/*
 * < ï¿½KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.virtusa.akura.api.dto.Achievement;
import com.virtusa.akura.api.dto.AssignmentMarkView;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClassWiseStudentsSubjectsDTO;
import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.dto.ExamAdmission;
import com.virtusa.akura.api.dto.ExamMark;
import com.virtusa.akura.api.dto.ExamResults;
import com.virtusa.akura.api.dto.FaithLifeRating;
import com.virtusa.akura.api.dto.MediumWiseClassSubjectAverageView;
import com.virtusa.akura.api.dto.PastStudent;
import com.virtusa.akura.api.dto.Seminar;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentAnnualGradeRank;
import com.virtusa.akura.api.dto.StudentAssignmentMark;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentClubSociety;
import com.virtusa.akura.api.dto.StudentDisability;
import com.virtusa.akura.api.dto.StudentDiscipline;
import com.virtusa.akura.api.dto.StudentGradeSubjectRankView;
import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.dto.StudentPrefect;
import com.virtusa.akura.api.dto.StudentScholarship;
import com.virtusa.akura.api.dto.StudentSearchCritiria;
import com.virtusa.akura.api.dto.StudentSeminar;
import com.virtusa.akura.api.dto.StudentSpecialEventParticipationView;
import com.virtusa.akura.api.dto.StudentSport;
import com.virtusa.akura.api.dto.StudentSubTermMark;
import com.virtusa.akura.api.dto.StudentSubTermMarkDTO;
import com.virtusa.akura.api.dto.StudentSubjectAverageViewDTO;
import com.virtusa.akura.api.dto.StudentTemporaryLeave;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.dto.StudentTermMarkPerformance;
import com.virtusa.akura.api.dto.StudentTermMarksRank;
import com.virtusa.akura.api.dto.StudentsGradeRankView;
import com.virtusa.akura.api.dto.SubjectAverageTermMarks;
import com.virtusa.akura.api.dto.SuspendStudent;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AlreadyOnLeaveException;
import com.virtusa.akura.api.exception.InvalidRejoinDateException;
import com.virtusa.akura.api.exception.SuspendRejoinReasonValidationException;

/**
 * interface to declare the student related services.
 */
public interface StudentService {
    
    /**
     * Service method to retrive student.
     * 
     * @param studentId - studentId
     * @return student obj for given studentId.
     * @throws AkuraAppException AkuraAppException
     */
    Student findStudent(int studentId) throws AkuraAppException;
    
    /**
     * Service method is to update student.
     * 
     * @param student - student object to be saved.
     * @return Student.
     * @throws AkuraAppException AkuraAppException
     */
    Student updateStudent(Student student) throws AkuraAppException;
    
    /**
     * Service method is to save student.
     * 
     * @param student - student object to be saved.
     * @return Student.
     * @throws AkuraAppException AkuraAppException
     */
    Student saveStudent(Student student) throws AkuraAppException;
    
    /**
     * Service method is to search student.
     * 
     * @param critiria - StudentSearchCritiria object to be searched.
     * @return List of Students.
     * @throws AkuraAppException SMS Exception.
     */
    List<Object> studentSearch(StudentSearchCritiria critiria) throws AkuraAppException;
    
    /**
     * Service method for student advance search.
     * 
     * @param critiria - StudentSearchCritiria object to be searched.
     * @return List of Students.
     * @throws AkuraAppException SMS Exception.
     */
    List<Object> studentAdvanceSearch(StudentSearchCritiria critiria) throws AkuraAppException;
    
    // -------Student Character related methods----------//
    /**
     * Adding student discipline information.
     * 
     * @param sd specifies the student discipline object
     * @return StudentDiscipline type object
     * @throws AkuraAppException SMS Exception.
     */
    StudentDiscipline addStudentDisciplineInfo(StudentDiscipline sd) throws AkuraAppException;
    
    /**
     * Editing student discipline information.
     * 
     * @param sd specifies the student discipline object
     * @throws AkuraAppException SMS Exception.
     */
    void editStudentDisciplineInfo(StudentDiscipline sd) throws AkuraAppException;
    
    /**
     * Deleting student discipline information.
     * 
     * @param sd specifies the student discipline object
     * @throws AkuraAppException SMS Exception.
     */
    void deleteStudentDisciplineInfo(StudentDiscipline sd) throws AkuraAppException;
    
    /**
     * Viewing student discipline information for a given student.
     * 
     * @param studentId specifies the student discipline id, defined by an integer
     * @return List of StudentDiscipline type objects
     * @throws AkuraAppException SMS Exception.
     */
    List<StudentDiscipline> viewStudentDisciplineInfo(int studentId) throws AkuraAppException;
    
    /**
     * Viewing student discipline information for a given student.
     * 
     * @param studentId specifies the student discipline id, defined by an integer
     * @param userLoginId specifies the user login id, defined by an integer
     * @return List of StudentDiscipline type objects
     * @throws AkuraAppException SMS Exception.
     */
    List<StudentDiscipline> viewStudentDisciplineInfoByUserLoginId(int studentId, int userLoginId)
            throws AkuraAppException;
    
    /**
     * Viewing student discipline information given by id.
     * 
     * @param studDisId specifies the student discipline id, defined by an integer
     * @return StudentDiscipline type object
     * @throws AkuraAppException SMS Exception.
     */
    StudentDiscipline viewStudentDisciplineInfoById(int studDisId) throws AkuraAppException;
    
    /**
     * Display all Student Discipline information.
     * 
     * @return List of StudentDisciplineCategory type objects
     * @throws AkuraAppException SMS Exception.
     */
    List<StudentDiscipline> viewAllStudentDisciplineInfo() throws AkuraAppException;
    
    /**
     * service to add a sport Activity to student.
     * 
     * @param studentSport the StudentSport object to set.
     * @throws AkuraAppException - The exception details that occurred when adding a list of StudentSport
     *         instances.
     */
    void addStudentSport(StudentSport studentSport) throws AkuraAppException;
    
    /**
     * service to edit a sport Activity of a student.
     * 
     * @param studentSport the StudentSport object to edit.
     * @throws AkuraAppException - The exception details that occurred when editing a list of StudentSport
     *         instances.
     */
    void editStudentSport(StudentSport studentSport) throws AkuraAppException;
    
    /**
     * service to delete a sport Activity of a student.
     * 
     * @param studentSportId the id of the StudentSport object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a list of StudentSport
     *         instances.
     */
    void deleteStudentSport(int studentSportId) throws AkuraAppException;
    
    /**
     * service to get the list of sportActivities of a given student.
     * 
     * @param studentId the id of the Student.
     * @param year the Date of the StudentSport activity.
     * @return List {@link StudentSport}
     * @throws AkuraAppException - The exception details that occurred when retrieve a list of StudentSport
     *         instances.
     */
    List<StudentSport> getSportsListForStudent(int studentId, Date year) throws AkuraAppException;
    
    /**
     * service to find a studentSport by studentSportId.
     * 
     * @param studSportId the id of the StudentSport activity.
     * @return StudentSport {@link StudentSport}
     * @throws AkuraAppException - The exception details that occurred when retrieve StudentSport instances.
     */
    StudentSport findStudentSportObjById(int studSportId) throws AkuraAppException;
    
    /**
     * service to find a studentSport.
     * 
     * @param studentId the id of the StudentSport activity.
     * @param sportCategoryId the id of the sportCategory instance.
     * @param year the date of the StudentSport activity.
     * @return StudentSport {@link StudentSport}
     * @throws AkuraAppException - The exception details that occurred when retrieve StudentSport instances.
     */
    List<StudentSport> findStudentSportObj(int studentId, int sportCategoryId, Date year) throws AkuraAppException;
    
    /**
     * service to add ClubSociety to the student.
     * 
     * @param studentClubSociety the StudentClubSociety to add
     * @throws AkuraAppException AkuraAppException
     */
    void addStudentClubSociety(StudentClubSociety studentClubSociety) throws AkuraAppException;
    
    /**
     * service to edit ClubSociety Detail of a student.
     * 
     * @param studentClubSociety the StudentClubSociety object to edit
     * @throws AkuraAppException SMS Exception
     */
    void editStudentClubSocietyDetail(StudentClubSociety studentClubSociety) throws AkuraAppException;
    
    /**
     * service to delete a clubSociety of a student.
     * 
     * @param studentClubSocietyId the id of the studentClubsociety object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete studentClubsociety
     *         instances.
     */
    void deleteStudentClubSocietyDetail(int studentClubSocietyId) throws AkuraAppException;
    
    /**
     * service to find a studentClubSociety.
     * 
     * @param studentId the id of the StudentSport activity.
     * @param year the date of the clubSociety activity.
     * @return StudentClubSociety {@link StudentClubSociety}
     * @throws AkuraAppException - The exception details that occurred when retrieve StudentClubSociety
     *         instances.
     */
    List<StudentClubSociety> getClubSocietyListForStudent(int studentId, Date year) throws AkuraAppException;
    
    /**
     * service to find a studentClubSociety.
     * 
     * @param studentId the id of the StudentSport activity.
     * @param clubSocietyId the id of the clubSociety instance.
     * @param year the date of the clubSociety activity.
     * @return StudentClubSociety {@link StudentClubSociety}
     * @throws AkuraAppException - The exception details that occurred when retrieve StudentClubSociety
     *         instances.
     */
    List<StudentClubSociety> findStudentClubSocietyObj(int studentId, int clubSocietyId, Date year)
            throws AkuraAppException;
    
    /**
     * Finds the StudentClubSociety that relevant to the StudentClubSociety id.
     * 
     * @param studClubSocietyId - the id of StudentClubSociety.
     * @return - StudentClubSociety instance.
     * @throws AkuraAppException - The exception details that occurred when finding a StudentClubSociety
     *         instance.
     */
    StudentClubSociety findStudentClubSocietyObjById(int studClubSocietyId) throws AkuraAppException;
    
    /**
     * service to get list of ClubSociety of a given student.
     * 
     * @param studentId the id of the student
     * @return List {@link StudentClubSociety}
     * @throws AkuraAppException AkuraAppException
     */
    List<StudentClubSociety> getClubSocietiesListForStudent(int studentId) throws AkuraAppException;
    
    /**
     * Returns a list of students in a specific class grade.
     * 
     * @param classGradeId classgrade id.
     * @param year the Year.
     * @return a list of StudentClassInfo.
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentClassInfo> getClassStudentList(int classGradeId, int year) throws AkuraAppException;
    
    /**
     * Returns a list of students in a specific class grade.
     * 
     * @param studentId - studentId.
     * @param classGradeId - classGradeId
     * @return a list of StudentClassInfo.
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentClassInfo> getStudentClassList(int studentId, int classGradeId) throws AkuraAppException;
    
    /**
     * Returns a list of studentClassInfo by student and year.
     * 
     * @param studentId student id.
     * @param year the Year.
     * @return a list of StudentClassInfo.
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentClassInfo> getStudentClassInfoByStudent(int studentId, int year) throws AkuraAppException;
    
    /**
     * Returns a list of students in a specific class grade without old boys and the first date of school is
     * less than the selected attendance marking date.
     * 
     * @param classGradeId - class grade key.
     * @param year - the relevant year.
     * @param attendanceDate - the relevant date for the attendance.
     * @return - a list of StudentClassInfo.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentClassInfo> getPresentClassStudentList(int classGradeId, int year, Date attendanceDate)
            throws AkuraAppException;
    
    /**
     * Returns a list of students in a specific sport.
     * 
     * @param sportCategoryId - integer.
     * @param year the Year.
     * @return a list of StudentSport.
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentSport> getStudentListforSportsCategory(int sportCategoryId, Date year) throws AkuraAppException;
    
    /**
     * Returns a list of students in a specific club.
     * 
     * @param clubSocietyId - integer.
     * @param year the Year.
     * @return a list of StudentClubSociety.
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentClubSociety> getStudentListforClubSociety(int clubSocietyId, Date year) throws AkuraAppException;
    
    /**
     * @param studentId - id of the student.
     * @throws AkuraAppException throws if exception occurs.
     */
    void deleteStudent(int studentId) throws AkuraAppException;
    
    /* StudentTermMarkService Related Methods */
    /**
     * @return average marks.
     */
    float getAverage();
    
    /**
     * @param average to set average of marks . void setAverage(float average);
     */
    /**
     * Calculate average for given student depending on class and term.
     * 
     * @param termId to hold term Id.
     * @param classInfoId to hold class Id.
     * @throws AkuraAppException throws if exception occurs.
     */
    void calculateAverage(int termId, int classInfoId) throws AkuraAppException;
    
    /**
     * @return List of subject marks for all students.
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentTermMark> getAllSubjectMarks() throws AkuraAppException;
    
    /**
     * @param termId to hold term Id.
     * @return List of subject marks for all students matching search criteria.
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentTermMark> getSelectedSubjectMarks(int termId) throws AkuraAppException;
    
    /**
     * @param termId to hold term Id.
     * @param classGradeId to class grade Id.
     * @return List of subject marks for all students matching search criteria.
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentTermMark> getSelectedSubjectMarksByGrade(int termId, int classGradeId) throws AkuraAppException;
    
    /**
     * @param allSubjectMarks to hold subject marks for multiple students.
     * @throws AkuraAppException throws if exception occurs.
     */
    void setAllSubjectMarks(List<StudentTermMark> allSubjectMarks) throws AkuraAppException;
    
    /**
     * @param studentTermMark to hold subject mark for one student for one term subject.
     * @throws AkuraAppException throws if exception occurs.
     */
    void setSubjectMark(StudentTermMark studentTermMark) throws AkuraAppException;
    
    /**
     * @param studentTermMark to hold the object to be deleted.
     * @throws AkuraAppException throws if exception occurs.
     */
    void deleteSubjectMarks(StudentTermMark studentTermMark) throws AkuraAppException;
    
    /**
     * @param objStudentTermMark to hold object to be updated
     * @throws AkuraAppException throws if exception occurs.
     */
    void editSubjectMarks(StudentTermMark objStudentTermMark) throws AkuraAppException;
    
    /**
     * Method is to save term marks of a gradeclass.
     * 
     * @param studentTermMark - studentTermMark objects to save.
     * @return list of StudentTermMark objects.
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentTermMark> saveOrUpdateStudentSubjectList(List<StudentTermMark> studentTermMark)
            throws AkuraAppException;
    
    /**
     * Method is to view student term marks of a gradeclass.
     * 
     * @param classGrade - classgrade to set.
     * @return list of StudentTermMark objects.
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentTermMark> viewStudentTermMarkInfoOfClassGrade(ClassGrade classGrade) throws AkuraAppException;
    
    /**
     * Method is to delete student subjects.
     * 
     * @param deletedTermMarkIdList - StudentTermMarkId s to delete.
     * @throws AkuraAppException when exception occurs.
     */
    void deleteStudentTermMark(List<Integer> deletedTermMarkIdList) throws AkuraAppException;
    
    /**
     * @param changedTermMarkList changed termmarkIDs.
     * @throws AkuraAppException when exception occurs.
     */
    void generateSubtermMarkRecords(List<StudentSubTermMark> changedTermMarkList) throws AkuraAppException;
    
    /* End of StudentTermMarkService Related Methods */
    
    /**
     * Method is to return list of StudentTermMark objects.
     * 
     * @param studentId - integer type object.
     * @param year - Date type object.
     * @return list of StudentTermMark objects.
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentTermMarkDTO> getStudentMarksSubjectTermByStudentIdYear(int studentId, int year)
            throws AkuraAppException;
    
    /**
     * get all examResults for a student.
     * 
     * @param studentId Student id
     * @return list of exam results objects
     * @throws AkuraAppException when exception occurs
     */
    List<ExamResults> getExamResultsByStudentId(int studentId) throws AkuraAppException;
    
    /**
     * Method is to view list of StudentSubTermMark objects given by student term mark id.
     * 
     * @param termMarkId - Student term mark id specified by an integer type variable
     * @throws AkuraAppException when exception occurs
     * @return list of StudentSubTermMark objects
     */
    List<StudentSubTermMark> getSubTermInfoByTermMarkId(int termMarkId) throws AkuraAppException;
    
    /**
     * Service method is to save studentDisability.
     * 
     * @param studentDisability - studentDisability object to be saved.
     * @return studentDisability.
     * @throws AkuraAppException AkuraAppException
     */
    StudentDisability saveStudentDisability(StudentDisability studentDisability) throws AkuraAppException;
    
    /**
     * Service method is to update studentDisability.
     * 
     * @param studentDisability - studentDisability object to be saved.
     * @return studentDisability.
     * @throws AkuraAppException AkuraAppException
     */
    StudentDisability updateStudentDisability(StudentDisability studentDisability) throws AkuraAppException;
    
    /**
     * Service method is to update studentDisability.
     * 
     * @param studentId - related student id to find student disability object.
     * @return studentDisability.
     * @throws AkuraAppException AkuraAppException
     */
    StudentDisability findStudentDisability(int studentId) throws AkuraAppException;
    
    /**
     * Service method is to chech whther admission no is exist.
     * 
     * @param admissionNo - admissionNo.
     * @return flag states whther admission no is exist.
     * @throws AkuraAppException AkuraAppException
     */
    boolean isAdmissionNoExist(String admissionNo) throws AkuraAppException;
    
    /**
     * Method is to view list if StudentClassInfo objects of a particular grade and a year.
     * 
     * @param gradeId GradeID to set.
     * @param selectedyear Year to set.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    List<StudentClassInfo> getStudentSearchByGradeYear(int gradeId, int selectedyear) throws AkuraAppException;
    
    /**
     * Get a List of StudentClassInfo s of a particular grade and a year.
     * 
     * @param studentId Student Id to set.
     * @param year year of the class.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    List<StudentClassInfo> getStudentClassInfoByStudentId(int studentId, int year) throws AkuraAppException;
    
    /**
     * Get a List of StudentClassInfo s of a particular class grade.
     * 
     * @param classGradeIds - classGradeIds.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    List<StudentClassInfo> getStudentClassInfoByClassGradeIdList(List<Integer> classGradeIds) throws AkuraAppException;
    
    /**
     * Method is to update list of StudentClassInfo objects.
     * 
     * @param updatedStudentClassInfoObjList - StudentClassInfo Objects to update.
     * @throws AkuraAppException when exception occurs.
     */
    void updateStudentClassInfoObjects(List<StudentClassInfo> updatedStudentClassInfoObjList) throws AkuraAppException;
    
    /**
     * Method is to find StudentTermMark when provide the studentTermMarkId.
     * 
     * @param studentTermMarkId - int studentTermMarkId.
     * @return StudentTermMark return a student term mark object.
     * @throws AkuraAppException when exception occurs.
     */
    StudentTermMark findStudentTermMark(int studentTermMarkId) throws AkuraAppException;
    
    /**
     * Method is to update StudentTermMark List.
     * 
     * @param studentTermMarkList - studentTermMark list want to update.
     * @throws AkuraAppException when exception occurs.
     */
    void updateStudentSubjectList(List<StudentTermMark> studentTermMarkList) throws AkuraAppException;
    
    /**
     * Method is to get sub term mark objects by proving a term mark id list.
     * 
     * @param termMarkIdList - studentTermMarkID list .
     * @return List of StudentSubTermMark- studentSubTermMark list returns.
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentSubTermMark> getSelectedSubTermMarksByGrade(List<Integer> termMarkIdList) throws AkuraAppException;
    
    /**
     * Method is to get sub term mark object by proving a sub term mark id.
     * 
     * @param studentSubTermMarkId - studentSubTermMarkId .
     * @return StudentSubTermMark StudentSubTermMark object returns.
     * @throws AkuraAppException when exception occurs.
     */
    StudentSubTermMark findStudentSubTermMark(int studentSubTermMarkId) throws AkuraAppException;
    
    /**
     * Method is to update StudentSubTermMark List.
     * 
     * @param studentSubTermMarkList - studentSubTermMark list want to update.
     * @throws AkuraAppException when exception occurs.
     */
    void updateStudentSubTermMarkList(List<StudentSubTermMark> studentSubTermMarkList) throws AkuraAppException;
    
    /**
     * Method is to save StudentClassInfo object.
     * 
     * @param sciObjTosave - StudentClassInfo object want to save.
     * @throws AkuraAppException when exception occurs.
     */
    void saveStudentClassInfoObj(StudentClassInfo sciObjTosave) throws AkuraAppException;
    
    /**
     * Method is to view all Student objects.
     * 
     * @return List of Student objects
     * @throws AkuraAppException when exception occurs.
     */
    List<Student> getStudentList() throws AkuraAppException;
    
    /**
     * Method is to view all StudentClassInfo objects.
     * 
     * @return List of StudentClassInfo objects
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentClassInfo> getAllStudentClassInfoObjList() throws AkuraAppException;
    
    /**
     * Service to add Achievement instance.
     * 
     * @param achievement Achievement instance.
     * @throws AkuraAppException throws if exception occurs when saving a Achievement instance.
     */
    void addAchievement(Achievement achievement) throws AkuraAppException;
    
    /**
     * Updates the relevant Achievement object.
     * 
     * @param achievement - Achievement instance.
     * @throws AkuraAppException - if error occurs when updating a Achievement instance.
     */
    void editAchievement(Achievement achievement) throws AkuraAppException;
    
    /**
     * Deletes the relevant Achievement object.
     * 
     * @param achievementId - id of Achievement.
     * @throws AkuraAppException - if error occurs when deleting a Achievement instance.
     */
    void deleteAchievement(int achievementId) throws AkuraAppException;
    
    /**
     * Returns a list of Achievement instances.
     * 
     * @param studentId - id of student.
     * @param date - Date of Achievement.
     * @return a list of Achievement instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Achievement instances.
     */
    List<Achievement> getAchievementsListForStudent(int studentId, Date date) throws AkuraAppException;
    
    /**
     * Finds the Achievement that relevant to the Achievement id.
     * 
     * @param achievementId - Achievement id.
     * @return a Achievement instance.
     * @throws AkuraAppException throws if exception occurs when finding a Achievement instance.
     */
    Achievement findAchievementById(int achievementId) throws AkuraAppException;
    
    /**
     * service to find a studentAchievement.
     * 
     * @param studentId the id of the Student.
     * @param strDescription the Achievement.
     * @param strActivity the activity.
     * @param year the date of the achievement.
     * @return Achievement {@link Achievement}
     * @throws AkuraAppException - The exception details that occurred when retrieve Achievement instances.
     */
    List<Achievement> findStudentAchievementObj(int studentId, String strDescription, String strActivity, Date year)
            throws AkuraAppException;
    
    /**
     * service to find a studentAchievement.
     * 
     * @param studentId the id of the Student.
     * @param strDescription the Achievement.
     * @param clubSocietyId the id of the club or society.
     * @param year the date of the achievement.
     * @return Achievement {@link Achievement}
     * @throws AkuraAppException - The exception details that occurred when retrieve Achievement instances.
     */
    List<Achievement> findStudentClubAchievementObj(int studentId, String strDescription, int clubSocietyId, Date year)
            throws AkuraAppException;
    
    /**
     * Create a StudentLeave.
     * 
     * @param studentLeave {@link StudentLeave}
     * @return {@link StudentLeave}
     * @throws AkuraAppException throws if exception occurs when adding a StudentLeave instance.
     */
    StudentLeave createStudentLeave(StudentLeave studentLeave) throws AkuraAppException;
    
    /**
     * Edit a StudentLeave.
     * 
     * @param studentLeave - {@link StudentLeave} .
     * @throws AkuraAppException throws if exception occurs when update a StudentLeave instance.
     */
    void updateStudentLeave(StudentLeave studentLeave) throws AkuraAppException;
    
    /**
     * Get the StudentLeave by id.
     * 
     * @param studentLeaveId - int .
     * @return {@link StudentLeave}
     * @throws AkuraAppException throws if exception occurs when get a StudentLeave instance.
     */
    StudentLeave findStudentLeaveById(int studentLeaveId) throws AkuraAppException;
    
    /**
     * Delete a StudentLeave.
     * 
     * @param studentLeave - {@link StudentLeave} .
     * @throws AkuraAppException throws if exception occurs when deleting a StudentLeave instance.
     */
    void deleteStudentLeave(StudentLeave studentLeave) throws AkuraAppException;
    
    /**
     * Retrieve all the available StudentLeave.
     * 
     * @return list of StudentLeave
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentLeave> getStudentLeaveList() throws AkuraAppException;
    
    /**
     * Retrieve list of StudentLeave for a particular student.
     * 
     * @param studentId - type int
     * @return list of StudentLeave
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentLeave> getStudentLeaveListByStudentId(int studentId) throws AkuraAppException;
    
    /**
     * Retrieve list of Student temporary Leave for a particular student.
     * 
     * @param studentId - type int
     * @return list of StudentTemporaryLeave
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentTemporaryLeave> getStudentTempLeaveListByStudentId(int studentId) throws AkuraAppException;
    
    /**
     * Method is to view all StudentPrefect objects given by StudentId.
     * 
     * @param studentId Student Id to set.
     * @param year specifies the year which holds the prefect details given by Date type object.
     * @return List of StudentPrefect objects
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentPrefect> getStudentPrefectDetailsByStudentId(int studentId, int year) throws AkuraAppException;
    
    /**
     * Method is to view all StudentScholarship objects given by StudentId.
     * 
     * @param studentId Student Id to set.
     * @param year specifies the year which holds the scholarship details given by Date type object.
     * @return List of StudentScholarship objects
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentScholarship> getStudentScholarshipDetailsByStudentId(int studentId, int year) throws AkuraAppException;
    
    /**
     * service to add a FaithLifeRating to student.
     * 
     * @param faithLifeRating the FaithLifeRating object to add.
     * @return the faith life rating
     * @throws AkuraAppException - The exception details that occurred when adding a FaithLifeRating
     *         instances.
     */
    FaithLifeRating addFaithLifeRating(FaithLifeRating faithLifeRating) throws AkuraAppException;
    
    /**
     * service to edit a FaithLifeRating of a student.
     * 
     * @param faithLifeRating the FaithLifeRating object to edit.
     * @throws AkuraAppException - The exception details that occurred when editing a FaithLifeRating
     *         instances.
     */
    void editFaithLifeRating(FaithLifeRating faithLifeRating) throws AkuraAppException;
    
    /**
     * service to delete a FaithLifeRating of a student.
     * 
     * @param faithLifeRatingId the id of the FaithLifeRating object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a FaithLifeRating
     *         instances.
     */
    void deleteFaithLifeRating(int faithLifeRatingId) throws AkuraAppException;
    
    /**
     * service to get the list of FaithLifeRating of a given student.
     * 
     * @param studentId the id of the Student.
     * @param year the Date
     * @return List {@link StudentSport}
     * @throws AkuraAppException - The exception details that occurred when retrieve a list of FaithLifeRating
     *         instances.
     */
    List<FaithLifeRating> getFaithLifeRatingsListForStudent(int studentId, Date year) throws AkuraAppException;

    
    /**
     * service to get the list of FaithLifeRating of a given student.
     *
     * @param studentId the id of the Student.
     * @param year -current year
     * @return List {@link StudentSport}
     * @throws AkuraAppException - The exception details that occurred when retrieve a list of FaithLifeRating
     *         instances.
     */
   List<Object> getFaithLifeRateListForStudent(int studentId, Date year) throws AkuraAppException;



    /**
     * service to find a FaithLifeRating by faithLifeRatingId.
     * 
     * @param faithLifeRatingId the id of the FaithLifeRating.
     * @return FaithLifeRating {@link FaithLifeRating}
     * @throws AkuraAppException - The exception details that occurred when retrieve FaithLifeRating
     *         instances.
     */
    FaithLifeRating findFaithLifeRatingObjById(int faithLifeRatingId) throws AkuraAppException;
    
    /**
     * service to find a FaithLifeRating.
     * 
     * @param studentId the id Student.
     * @param description the description of the faithLifeRating.
     * @param year the Date
     * @return FaithLifeRating {@link FaithLifeRating}
     * @throws AkuraAppException - The exception details that occurred when retrieve FaithLifeRating
     *         instances.
     */
    List<FaithLifeRating> findFaithLifeRatingObj(int studentId, String description, Date year) throws AkuraAppException;
    
    /**
     * service method is to get StudentId for the admissionNo.
     * 
     * @param admissionNo - admissionNo.
     * @return student id for the registrationNo.
     * @throws AkuraAppException AkuraAppException
     */
    int findStudentIdForAdmissionNo(String admissionNo) throws AkuraAppException;
    
    /**
     * Method is to save StudentPrefect object.
     * 
     * @param studentPrefectObj PrefectType Id to set.
     * @throws AkuraAppException when exception occurs.
     */
    void saveStudentPrefectDetails(StudentPrefect studentPrefectObj) throws AkuraAppException;
    
    /**
     * Method is to update StudentPrefect object.
     * 
     * @param studentPrefectObj StudentPrefect object to update.
     * @throws AkuraAppException when exception occurs.
     */
    void updateStudentPrefectDetails(StudentPrefect studentPrefectObj) throws AkuraAppException;
    
    /**
     * Method is to find StudentPrefect object given by Id.
     * 
     * @param studentPrefectId PrefectType Id to find.
     * @return StudentPrefect object
     * @throws AkuraAppException when exception occurs.
     */
    StudentPrefect findStudentPrefectDetailsById(int studentPrefectId) throws AkuraAppException;
    
    /**
     * Method is to delete a StudentPrefect object.
     * 
     * @param studentPrefectObj PrefectType object to delete.
     * @throws AkuraAppException when exception occurs.
     */
    void deleteStudentPrefectDetails(StudentPrefect studentPrefectObj) throws AkuraAppException;
    
    /**
     * Method is to get all StudentPrefect objects.
     * 
     * @return List of StudentPrefect objects
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentPrefect> getAllStudentPrefectDetails() throws AkuraAppException;
    
    /**
     * Method is to save StudentScholarship object.
     * 
     * @param studentScholarship Student Scholarship object to set.
     * @throws AkuraAppException when exception occurs.
     */
    void saveStudentScholarshipDetails(StudentScholarship studentScholarship) throws AkuraAppException;
    
    /**
     * Method is to update StudentScholarship object.
     * 
     * @param studentScholarship StudentScholarship object to update.
     * @throws AkuraAppException when exception occurs.
     */
    void updateStudentScholarshipDetails(StudentScholarship studentScholarship) throws AkuraAppException;
    
    /**
     * Method is to find StudentScholarship object given by Id.
     * 
     * @param studentScholarshipId StudentScholarship Id to set.
     * @return StudentScholarship object.
     * @throws AkuraAppException when exception occurs.
     */
    StudentScholarship findStudentScholarshipDetailsById(int studentScholarshipId) throws AkuraAppException;
    
    /**
     * Method is to delete a StudentScholarship object.
     * 
     * @param studentPrefectObj StudentPrefect object to delete.
     * @throws AkuraAppException when exception occurs.
     */
    void deleteStudentScholarshipDetails(StudentScholarship studentPrefectObj) throws AkuraAppException;
    
    /**
     * Method is to get all StudentScholarship objects.
     * 
     * @return List of StudentScholarship objects
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentScholarship> getAllStudentScholarshipDetails() throws AkuraAppException;
    
    /**
     * Returns a list of StudentSubTermMarks for a given termId and a sub term.
     * 
     * @param subTermId - id of a subTerm
     * @param termId - id of a relevant studentTermMarks
     * @return - a list of StudentSubTermMarks
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<StudentSubTermMark> getSubTermMarks(int subTermId, int termId) throws AkuraAppException;
    
    /**
     * Returns the id of the subTerms relevant to a given id of a studentTermMark.
     * 
     * @param studentTermMarkId - id of a studentTermMark
     * @return - id of the subTerms
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<Integer> getSubTerms(int studentTermMarkId) throws AkuraAppException;
    
    /**
     * Returns a value of a given faithLifeRating value.
     * 
     * @param faithLifeValue - faithLifeValue of FaithLifeRating
     * @return - a value of the rating
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    int getRating(String faithLifeValue) throws AkuraAppException;
    
    /**
     * Gets the student personal details by admission no.
     * 
     * @param studentAdmissionNumber the student admission number
     * @return the student personal details by admission no
     * @throws AkuraAppException the sMS app exception
     */
    List<Student> getStudentPersonalDetailsByAdmissionNo(String studentAdmissionNumber) throws AkuraAppException;
    
    /**
     * Gets the new students for the given year.
     * 
     * @param year the year.
     * @return the new student list.
     * @throws AkuraAppException AkuraAppException.
     */
    List<Object> getNewStudentForYear(int year) throws AkuraAppException;
    
    /**
     * Retreives the marks for the keys of a given class information , grade subject and a term.
     * 
     * @param classInfoId - primary key of the ClassInfo.
     * @param gradeSubjectId - primary key of the GradeSubject.
     * @param termId - primary key of the term.
     * @return - the marks of the student term marks.
     * @throws AkuraAppException - The exception details that occurred.
     */
    Float getStuTermMarks(int classInfoId, int gradeSubjectId, int termId) throws AkuraAppException;
    
    /**
     * Display all Student Discipline information for given year.
     * 
     * @param startDate - start date of year.
     * @param endDate - end date of year.
     * @return List of StudentDiscipline type objects
     * @throws AkuraAppException SMS Exception.
     */
    List<StudentDiscipline> viewAllStudentDisciplineInfoByYear(Date startDate, Date endDate) throws AkuraAppException;
    
    /**
     * Display all StudentClassInfo objects for given class and given year.
     * 
     * @param classGradeId - classId.
     * @param year - calendar year.
     * @return List of StudentClassInfo type objects
     * @throws AkuraAppException SMS Exception.
     */
    List<StudentClassInfo> getStudentClassInfoOfClassByYear(int classGradeId, Date year) throws AkuraAppException;
    
    /**
     * Display all StudentClassInfo objects for given class,student id and given year.
     * 
     * @param classGradeId - classId.
     * @param year - calendar year.
     * @param studentId - StudentId.
     * @return List of StudentClassInfo type objects
     * @throws AkuraAppException SMS Exception.
     */
    List<StudentClassInfo> getStudentClassInfoOfStudentClassByYear(int studentId, int classGradeId, Date year)
            throws AkuraAppException;
    
    /**
     * Returns the absent days for a given primary key of a student.
     * 
     * @param studentId - the primary key of the student.
     * @param year - the current year.
     * @return - a list of student leave.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentLeave> getStudentLeaveListByStudentId(int studentId, int year) throws AkuraAppException;
    
    /**
     * Returns the StudentClassInfo object for a given of a student id.
     * 
     * @param studentId - the primary key of the student.
     * @return - a list of studentClassInfo object.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentClassInfo> getStudentClassInfoByStudentId2(int studentId) throws AkuraAppException;
    
    /**
     * Method is to find StudentClassInfo object given by Class Grade Id.
     * 
     * @param classGradeIds ClassGrade Id to find.
     * @return StudentClassInfo object
     * @throws AkuraAppException when exception occurs.
     */
    StudentClassInfo findStudentClassInfoById(int classGradeIds) throws AkuraAppException;
    
    /**
     * Method to find all the students allocated for the event.
     * 
     * @param participantId can be class grade id|sport category id|club society id
     * @param eventYear event created year
     * @return list of StudentSpecialEventParticipationView
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentSpecialEventParticipationView> getAllocatedStudentListForEvent(int participantId, Date eventYear)
            throws AkuraAppException;
    
    /**
     * Returns the list of seminars for the student for given year.
     * 
     * @param studentId - student Id of the StudentSeminars.
     * @param dateSelectedYear - selected year.
     * @return - a list of seminars for the student.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentSeminar> getAllStudentSeminars(int studentId, Date dateSelectedYear) throws AkuraAppException;
    
    /**
     * Returns the list of Seminar available.
     * 
     * @return - a list of Seminar available.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Seminar> getAllSeminars() throws AkuraAppException;
    
    /**
     * Save the given seminars for Student.
     * 
     * @param studentSem - new values with a studentSeminar object.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void saveStudetnSeminar(StudentSeminar studentSem) throws AkuraAppException;
    
    /**
     * To update the existing StudentSeminar record.
     * 
     * @param studentSem - the object of student seminar to update.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void updateStudentSeminar(StudentSeminar studentSem) throws AkuraAppException;
    
    /**
     * To remove the given Seminar form the student.
     * 
     * @param studentSeminarId - id of the given record in StudentSeminar.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void deleteStudentSeminar(int studentSeminarId) throws AkuraAppException;
    
    /**
     * Checks if is student term mark exist.
     * 
     * @param studentClassInfoId - the student class info id
     * @param gradeSubjectId - the grade subject id
     * @param termId - the term id
     * @return - the status of the existance of the student term mark for a given term key, student class info
     *         key and the grade subject key.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    boolean isStudentTermMarkExist(final int studentClassInfoId, final int gradeSubjectId, final int termId)
            throws AkuraAppException;
    
    /**
     * Gets the student sub term marks.
     * 
     * @param studentId the student id
     * @param year the year
     * @return the student sub term marks
     * @throws AkuraAppException the akura app exception
     */
    List<StudentSubTermMarkDTO> getStudentSubTermMarks(int studentId, int year) throws AkuraAppException;
    
    /**
     * Get list of StudentTermMarkDTO based on class grade id, term id, year.
     * 
     * @param classGradeId class grade id
     * @param termid term id
     * @param year year
     * @return list of StudentTermMarkDTO
     * @throws AkuraAppException the akura app exception
     */
    List<StudentTermMarkDTO> getTermMarksByTermGradeYear(int classGradeId, int termid, String year)
            throws AkuraAppException;
    
    /**
     * Get student terms rank object.
     * 
     * @param studentClassInfo student class info id
     * @param termId term id
     * @return StudentTermMarksRank object
     * @throws AkuraAppException the akura app exception
     */
    StudentTermMarksRank getStudentTermMarksRank(int studentClassInfo, int termId) throws AkuraAppException;
    
    /**
     * Gets the student sub term marks by subject.
     * 
     * @param studentId the student id
     * @param year the year
     * @param subject the subject
     * @return the student sub term marks by subject
     * @throws AkuraAppException the akura app exception
     */
    List<StudentSubTermMarkDTO> getStudentSubTermMarksBySubject(int studentId, Date year, String subject)
            throws AkuraAppException;
    
    /**
     * Retrieves a list of StudentSubjectAverageViewDTO for a given student id and the year.
     * 
     * @param studentId - a given key.
     * @param year - the current yea.
     * @return - a list of StudentSubjectAverageViewDTO for a given student id and the year.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentSubjectAverageViewDTO> getStudentSubjectAverage(int studentId, int year) throws AkuraAppException;
    
    /**
     * Get SubjectAverageTermMarks object by class description, year, grade subject id, term.
     * 
     * @param classDescription class description
     * @param year year
     * @param gradeSubjectId grade subject id
     * @param term term
     * @return SubjectAverageTermMarks object
     * @throws AkuraAppException when exception occurs
     */
    SubjectAverageTermMarks getSubjectAverageTermMarksById(String classDescription, int year, int gradeSubjectId,
            String term) throws AkuraAppException;
    
    /**
     * Returns a list of StudentAverage with some properties.
     * 
     * @param year - the current year.
     * @param studentId - the given student id.
     * @return - a list of StudentAverage with some properties.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Object> populateStudentAverageTerm(int year, int studentId) throws AkuraAppException;
    
    /**
     * Returns a list of StudentTermMarksRank for a given classInfo id.
     * 
     * @param studentClassInfoId - the class info id for a student.
     * @return - a list of StudentTermMarksRank for a given classInfo id.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentTermMarksRank> getStudentRank(int studentClassInfoId) throws AkuraAppException;
    
    /**
     * Gets list of StudentTermMarkDTO by classInfoID and gradeSubjectId.
     * 
     * @param studentClassInfoId studemt class info id.
     * @param gradeSubjectId grade subject id.
     * @return the list of StudentTermMarkDTO.
     * @throws AkuraAppException the AkuraAppException
     */
    List<StudentTermMarkDTO> findTermMarksByClassInfoIdAndGradeSubject(int studentClassInfoId, int gradeSubjectId)
            throws AkuraAppException;
    
    /**
     * Gets the student assignment marks.
     * 
     * @param classGradeId the class grade id
     * @param gradeSubjectId the grade subject id
     * @param year the year
     * @return the student assignment marks
     * @throws AkuraAppException the akura app exception
     */
    List<ClassWiseStudentsSubjectsDTO> getClassWiseStudentListBySubject(int classGradeId, int gradeSubjectId, Date year)
            throws AkuraAppException;
    
    /**
     * Get student grade rank list by student class info id.
     * 
     * @param studentClassInfoId student class info id
     * @return list
     * @throws AkuraAppException throws if exception occurs
     */
    List<StudentsGradeRankView> findTermWiseStudentTotalMarks(int studentClassInfoId) throws AkuraAppException;
    
    /**
     * This method returns list of StudentAnnualGradeRank objects.
     * 
     * @param gradeId grade id
     * @param year year
     * @param noOfPrizes no of prizes
     * @return list
     * @throws AkuraAppException when exception occurs
     */
    List<StudentAnnualGradeRank> findStudentAnnualGradeRank(int gradeId, int year, int noOfPrizes)
            throws AkuraAppException;
    
    /**
     * This method returns list of StudentGradeSubjectRankView objects.
     * 
     * @param gradeSubjectId grade subject id
     * @param year year
     * @param noOfPrizes no of prizes
     * @return list
     * @throws AkuraAppException if exception occurs
     */
    List<StudentGradeSubjectRankView> getStudentGradeSubjectRankList(int gradeSubjectId, int year, int noOfPrizes)
            throws AkuraAppException;
    
    /**
     * Retrieves the size of the exam marks for a given exam key and the year.
     * 
     * @param year - the relevant year.
     * @param examId - the key of the exam object.
     * @return - the size of the exam marks for a given exam key and the year.
     * @throws AkuraAppException - The exception details that occurred when deleting a examSubject instance.
     */
    int getAllExamMarksList(final int year, final int examId) throws AkuraAppException;
    
    /**
     * Returns the list of objects for relvant data.
     * 
     * @param examMarksListSize - the size of the exam marks.
     * @param year - the relevant year.
     * @param gradeId - the key of the Grade.
     * @param examId - the key of the Exam.
     * @return - the list of objects for relvant data.
     * @throws AkuraAppException - The exception details that occurred when searching a exam mark instance.
     */
    List<Object> searchExamMarks(int examMarksListSize, int year, String gradeId, int examId) throws AkuraAppException;
    
    /**
     * Retrives the student id by the full name.
     * 
     * @param studentName - the full name of the student.
     * @return - the student id by the full name.
     * @throws AkuraAppException - The exception details that occurred when deleting a exam mark instance.
     */
    int getStudentIdByName(final String studentName) throws AkuraAppException;
    
    /**
     * Finds the Exam Mark by the key.
     * 
     * @param examMarkKey - the key of the Exam mark.
     * @return - the Exam Mark by the key.
     * @throws AkuraAppException - The exception details that occurred when finding a exam mark instance.
     */
    ExamMark findExamMarkById(final int examMarkKey) throws AkuraAppException;
    
    /**
     * Updates the relevant Exam mark object.
     * 
     * @param examMarks - the relevant Exam mark object.
     * @throws AkuraAppException - The exception details that occurred when updating a exam mark instance.
     */
    void updateExamMarks(ExamMark examMarks) throws AkuraAppException;
    
    /**
     * Save student assignment marks list.
     * 
     * @param studentAssignmentMarksList the student assignment marks list
     * @throws AkuraAppException the akura app exception
     */
    void saveStudentAssignmentMarksList(List<StudentAssignmentMark> studentAssignmentMarksList)
            throws AkuraAppException;
    
    /**
     * Gets the student assignment marks list.
     * 
     * @param gradeSubjectId the grade subject id
     * @param assignmentId the assignment id
     * @param year the year
     * @param classGradeId the class grade id
     * @return the student assignment marks list
     * @throws AkuraAppException the akura app exception
     */
    List<Object[]> getStudentAssignmentMarksList(int gradeSubjectId, int assignmentId, int year, int classGradeId)
            throws AkuraAppException;
    
    /**
     * Finds the Exam Admission by the admission number.
     * 
     * @param admissionNo - the admission number of the Exam Admission.
     * @param year - the relevant year
     * @param examId - examId
     * @return - the relevant Exam Admission object.
     * @throws AkuraAppException - The exception details that occurred when finding.
     */
    ExamAdmission findExamAdmissionByAdmission(final String admissionNo, final int year, int examId)
            throws AkuraAppException;
    
    /**
     * Saves the relevant Exam Admission object.
     * 
     * @param examAdmission - the exam admission object.
     * @return - the relevant saved Exam Admission object.
     * @throws AkuraAppException - The exception details that occurred when saving.
     */
    ExamAdmission saveExamAdmission(final ExamAdmission examAdmission) throws AkuraAppException;
    
    /**
     * Updates the relevant Exam Admission object.
     * 
     * @param examAdmission - the relevant Exam Admission object.
     * @throws AkuraAppException - The exception details that occurred when saving.
     */
    void updateExamAdmission(final ExamAdmission examAdmission) throws AkuraAppException;
    
    /**
     * Finds the Exam Admission by the exam admission key.
     * 
     * @param examAdmissionId - the key of the Exam Admission.
     * @return - the Exam Admission.
     * @throws AkuraAppException - The exception details that occurred when finding.
     */
    ExamAdmission findExamAdmissionById(final int examAdmissionId) throws AkuraAppException;
    
    /**
     * Saves or updates a list of Exam Mark objects.
     * 
     * @param examMarkList - a list of Exam Marks.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void saveOrUpdateExamMarks(final List<ExamMark> examMarkList) throws AkuraAppException;
    
    /**
     * Checks the existence of the Assignment marks is already assigned for selected assignment.
     * 
     * @param assignmentId - the key of the assignment idt.
     * @return - a list of existing AssignmentMarks for given assignment
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    boolean isExistAssignmentMarks(final int assignmentId) throws AkuraAppException;
    
    /**
     * Retrieves the exam admission by the student key and the relevant year.
     * 
     * @param admission - the admission of the exam admission
     * @param year - the relevant year
     * @param studentId - the key of the student
     * @param examId - the key of the exam
     * @return - a list of Exam Admission
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    ExamAdmission findExamAdmissionByStudentId(String admission, int year, int studentId, int examId)
            throws AkuraAppException;
    
    /**
     * Gets the student assignment mark by id.
     * 
     * @param studentAssignmentMarksId the student assignment marks id
     * @return the student assignment mark by id
     * @throws AkuraAppException the akura app exception
     */
    StudentAssignmentMark getStudentAssignmentMarkById(int studentAssignmentMarksId) throws AkuraAppException;
    
    /**
     * Gets the student class info id list but not in assignment.
     * 
     * @param year - year.
     * @return the student class info id Integer list
     * @throws AkuraAppException the akura app exception
     */
    List<Integer> getStudentClassInfoIdsNotInAssignmentMarks(int year) throws AkuraAppException;
    
    /**
     * Update student assignment mark.
     * 
     * @param studentAssignmentMark the student assignment mark
     * @throws AkuraAppException the akura app exception
     */
    void updateStudentAssignmentMark(StudentAssignmentMark studentAssignmentMark) throws AkuraAppException;
    
    /**
     * Gets the student assignment marks by student id.
     * 
     * @param studentId the student id
     * @return the student assignment marks by student id
     * @throws AkuraAppException the akura app exception
     */
    List<Object[]> getStudentAssignmentMarksByStudentId(int studentId) throws AkuraAppException;
    
    /**
     * Retrieves Exam Results by Exam Admission No.
     * 
     * @param examAdmissionNo - examAdmissionNo
     * @param examId - examId
     * @return list of exam results objects
     * @throws AkuraAppException - AkuraAppException
     */
    List<ExamResults> findExamResultsByExamAdmissionNo(String examAdmissionNo, int examId) throws AkuraAppException;
    
    /**
     * Gets the student assignment marks by subject.
     * 
     * @param studentId the student id
     * @param year the year
     * @param subject the subject
     * @return the student assignment marks by subject
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<AssignmentMarkView> getStudentAssignmentMarksBySubject(int studentId, Date year, String subject)
            throws AkuraAppException;
    
    /**
     * Retrieves a list of StudentAssignmentMarks for given student Id and the year.
     * 
     * @param studentId - a given key.
     * @param year - the current year.
     * @return a list of StudentAssignmentMarks for given student Id and the year.
     * @throws AkuraAppException when exceptions occurred.
     */
    List<AssignmentMarkView> getStudentAssignmentMarks(int studentId, int year) throws AkuraAppException;
    
    /**
     * Saves or updates a list of Exam Admissions for new students.
     * 
     * @param newExamAdmissionList - a list of Exam Admissions for new students.
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    void saveOrUpdateExamAdmissions(final List<ExamAdmission> newExamAdmissionList) throws AkuraAppException;
    
    /**
     * Searches the Exam Marks for new subjects.
     * 
     * @param year - the relevant year
     * @param gradeId - the key of the grade
     * @param examId - the key of the exam
     * @param newSubjects - a list of the exam subject keys
     * @return - a list of the exam marks for new subjects
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Object> searchExamMarksWithArray(final int year, final String gradeId, final int examId,
            final List<Integer> newSubjects) throws AkuraAppException;
    
    /**
     * This method is used to set marking flag.
     * 
     * @param markingCompleted boolean value
     * @param classGradeId class grade id
     * @param gradeId grade id
     * @param termID term id
     * @param year year
     * @throws AkuraAppException throws if exception occurs
     */
    void setMarkingFlag(boolean markingCompleted, int gradeId, int classGradeId, int termID, Date year)
            throws AkuraAppException;
    
    /**
     * This method returns boolean values, if marking is completed for all the terms in the year, true will
     * add to list, Otherwise false will add.
     * 
     * @param gradeId grade id
     * @param year year value
     * @return list of boolean values
     * @throws AkuraAppException - throws if exception occurs
     */
    boolean isAnnualMarkingDoneForGrade(int gradeId, int year) throws AkuraAppException;
    
    /**
     * This method returns true if marking is completed for this term. Otherwise returns false.
     * 
     * @param classGradeId class grade id
     * @param termId term id
     * @param year year value
     * @return boolean
     * @throws AkuraAppException - throws if exception occurs
     */
    boolean isMarkingCompletedForTerm(int classGradeId, int termId, int year) throws AkuraAppException;
    
    /**
     * Service method is to check whether exam admission no is exist.
     * 
     * @param examAdmissionNo - admissionNo.
     * @return flag states whether admission no is exist.
     * @throws AkuraAppException AkuraAppException
     */
    boolean isExamAdmissionNoExist(String examAdmissionNo) throws AkuraAppException;
    
    /**
     * Gets the student admission nos by class grade id.
     * 
     * @param classGradeId the class grade id
     * @return the student admission nos by class grade id
     * @throws AkuraAppException the akura app exception
     */
    List<String> getStudentAdmissionNosByClassGradeId(final int classGradeId) throws AkuraAppException;
    
    /**
     * Finds the already leaves for the given student and the given date range.
     * 
     * @param studentId - the key of the student
     * @param dateFrom - the start date of the leave
     * @param dateTo - the end date of the leave
     * @return - a list of leaves
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentLeave> findAlreadyExistLeave(int studentId, Date dateFrom, Date dateTo) throws AkuraAppException;
    
    /**
     * Retrieves a list of exam marks results for a given key of the student and the date.
     * 
     * @param studentId - the key of the student
     * @param date - the date
     * @return - a list of exam marks results
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<ExamResults> getExamResults(final int studentId, final Date date) throws AkuraAppException;
    
    /**
     * Retrieves a list of student term marks marks results for a given key of the class and the year ,with in
     * marks range .
     * 
     * @param grade - the key of the Grade description.
     * @param term - the key of the term description.
     * @param year - the year of current year
     * @param less - the less marks
     * @param grater - the grater marks
     * @return - a list of student marks results
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentTermMarkPerformance> getTermMarksByGradeYear(String grade, int term, String year, float less,
            float grater) throws AkuraAppException;
    
    /**
     * Gets the best student average in the class for a term.
     * 
     * @param classGradeId the class grade id
     * @param term the term
     * @param year the year
     * @return the best student average
     * @throws AkuraAppException the akura app exception
     */
    Double getBestStudentAverage(int classGradeId, String term, Date year) throws AkuraAppException;
    
    /**
     * Gets the class average for a term.
     * 
     * @param classGradeId the class grade id
     * @param term the term
     * @param year the year
     * @return the class average
     * @throws AkuraAppException the akura app exception
     */
    Object[] getClassAverage(int classGradeId, String term, Date year) throws AkuraAppException;
    
    /**
     * This method is used to get list of averages of subjects per class.
     * 
     * @param classGradeid class grade id
     * @param year int value of year
     * @param gradeSubjectId grade subject id
     * @param term term description
     * @param languageId language id represents study medium id
     * @return list of MediumWiseClassSubjectAverageView objects
     * @throws AkuraAppException throws if exception occurs
     */
    MediumWiseClassSubjectAverageView getMediumWiseClassSubjectAverage(int classGradeid, int year, int gradeSubjectId,
            String term, int languageId) throws AkuraAppException;
    
    /**
     * This method is used to find list of study medium id available for class.
     * 
     * @param classGradeid class grade id
     * @param year int value of year
     * @param term term description
     * @return list of study medium id available for class
     * @throws AkuraAppException throws if exception occurs
     */
    List<Integer> getStudyMediumsInClass(int classGradeid, int year, String term) throws AkuraAppException;
    
    /**
     * Gets the student term marks by student id year subject.
     * 
     * @param studentId the student id
     * @param year the year
     * @param gradeSubjectId the grade subject id
     * @return the student term marks by student id year subject
     * @throws AkuraAppException the akura app exception
     */
    List<StudentTermMarkDTO> getStudentTermMarksByStudentIdYearSubject(int studentId, int year, int gradeSubjectId)
            throws AkuraAppException;
    
    /**
     * Get student sub term marks by student id and year.
     * 
     * @param studentId the student id
     * @param year the year
     * @return the student term marks by student id and year
     * @throws AkuraAppException the akura app exception
     */
    List<StudentSubTermMarkDTO> getSubTermMarksForStudent(int studentId, int year) throws AkuraAppException;
    
    /**
     * Get student term marks by student id and year.
     * 
     * @param studentId the student id
     * @param year the year
     * @param classGradeId the class grade id
     * @return the student term marks by student id and year
     * @throws AkuraAppException the akura app exception
     */
    List<StudentTermMarkDTO> getStudentTermMarksByStudentIdYearClassGrade(int studentId, int year, int classGradeId)
            throws AkuraAppException;
    
    /**
     * Get student term marks by student id and year.
     * 
     * @param identification - identification.
     * @return the identification number.
     * @throws AkuraAppException the akura app exception
     */
    int getKeyByIdentificationNo(String identification) throws AkuraAppException;
    
    /**
     * Check whether StudentDiscipline already exist.
     * 
     * @param studentDisciplineName - StudentDiscipline
     * @return true if the StudentDiscipline exist else false.
     * @throws AkuraAppException - Throw detailed exception.
     */
    boolean isExistStudentDiscipline(StudentDiscipline studentDisciplineName) throws AkuraAppException;
    
    /**
     * Get the student term marks by student id and year.
     * 
     * @param studentId - the id of the student.
     * @param year - year of the term marks to be retrieved.
     * @return - a list of student term marks.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentTermMark> getStudentTermMarkListByStudentIdAndYear(int studentId, Date year) throws AkuraAppException;
    
    /**
     * This method handles the student member rejoin functionality.
     * 
     * @param studentId - the id of the student member to be rejoined.
     * @param rejoinDate - the rejoin date of the student member.
     * @return true if student member is rejoined successfully.
     * @throws InvalidRejoinDateException - Throws when user enters a date lower than the departure date for
     *         rejoin.
     * @throws IOException - Throws detailed exception when setting student member image fails.
     * @throws AkuraAppException - Throws detailed exception when fails find student member by Id.
     */
    boolean rejoinStudentMemberService(int studentId, Date rejoinDate) throws InvalidRejoinDateException, IOException,
            AkuraAppException;
    
    /**
     * Get the student discipline in current year.
     * 
     * @param studentIdVal - the id of the student.
     * @param year - Current year.
     * @return - a list of StudentDiscipline.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentDiscipline> viewCurrentYearStudentDisciplineInfo(int studentIdVal, int year) throws AkuraAppException;
    
    /**
     * Get the student details.
     * 
     * @param studentId - the id of the student.
     * @return - a list of Student Objects.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    Object viewStudent(int studentId) throws AkuraAppException;
    
    /**
     * Suspend the students.
     * 
     * @param suspendStudent - SuspendStudent object.
     * @return - boolean value.
     * @throws AkuraAppException - The exception details that occurred when processing.
     * @throws AlreadyOnLeaveException Newly created Exception
     */
    boolean suspendStudent(SuspendStudent suspendStudent) throws AkuraAppException, AlreadyOnLeaveException;
    
    /**
     * terminates a student.
     * 
     * @param pastStudent - PastStudent object of the particular student
     * @return - boolean value.
     * @throws AkuraAppException when exception occurs.
     */
    boolean terminatePastStudent(PastStudent pastStudent) throws AkuraAppException;
    
    /**
     * Check whether Mark Entries completed.
     * 
     * @param studentId - student Id
     * @return true if the MarkEntriesCompleted else false.
     * @throws AkuraAppException - Throw detailed exception.
     */
    
    boolean checkMarkEntriesCompleted(int studentId) throws AkuraAppException;
    
    /**
     * terminates a student.
     * 
     * @param studentTemporaryLeave - studentTemporaryLeave object of the particular student
     * @throws AkuraAppException when exception occurs.
     * @return true
     */
    boolean terminateTemporaryLeaveStudent(StudentTemporaryLeave studentTemporaryLeave) throws AkuraAppException;
    
    /**
     * Delete term marks.
     * 
     * @param studentId - student Id
     * @throws AkuraAppException - Throw detailed exception.
     */
    void deleteStudentTermmarks(int studentId) throws AkuraAppException;
    
    /**
     * save Extended Temporary Student.
     * 
     * @param w - w.
     * @param date - date.
     * @param studentId - student Id
     * @throws AkuraAppException - Throw detailed exception.
     */
    void saveExtendedTemporaryStudent(int studentId, int w, Date date) throws AkuraAppException;
    
    /**
     * Get the SuspendedStudent details by student id.
     * 
     * @param studentId - the id of the student.
     * @return - a list of SuspendedStudent details.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<SuspendStudent> getSuspendedStudentByStudentId(int studentId) throws AkuraAppException;
    
    /**
     * active SuspendedStudent .
     * 
     * @param studentId - the id of the student.
     * @param rejoinDate - rejoined date
     * @param reason - reason
     * @return - rejoin status.
     * @throws AkuraAppException - The exception details that occurred when processing.
     * @throws InvalidRejoinDateException The exception of invalid data type.
     * @throws IOException The exception of input output.
     * @throws SuspendRejoinReasonValidationException Newly created Exception
     */
    boolean rejoinSuspendedStudentMemberService(int studentId, Date rejoinDate, String reason)
            throws InvalidRejoinDateException, IOException, AkuraAppException, SuspendRejoinReasonValidationException;
    
    /**
     * Viewing all the Warning Level Information available in the table.
     * 
     * @return List of WarningLevel objects
     * @throws AkuraAppException SMS Exception.
     */
    List<StudentDiscipline> viewStudentDisciplineInfo() throws AkuraAppException;
    
    /**
     * Getting all StudentParent Information available in the table.
     * 
     * @param studentIdVal - the id of the student.
     * @return List of StudentParent objects
     * @throws AkuraAppException SMS Exception.
     */
    List<StudentParent> getParentId(int studentIdVal) throws AkuraAppException;
    
    /**
     * get currently logged user.
     * 
     * @param user - user
     * @return logged user
     * @throws AkuraAppException SMS Exception.
     */
    String getLoggedUser(String user) throws AkuraAppException;
    
    /**
     * Get the latest Student Temporary Leave by student Id.
     * 
     * @param studentId - the id of the student.
     * @return List of Student Temporary Leave.
     * @throws AkuraAppException - Throws detailed exception when fails.
     */
    List<StudentTemporaryLeave> getLatestStudentTempLeaveByStudentId(int studentId) throws AkuraAppException;
    
    /**
     * Get the list of non current students in the given class.
     * 
     * @param classGradeId - the class of interest.
     * @param year - year of interest.
     * @param date - date of interest.
     * @return List of StudentClassInfo.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<StudentClassInfo> getNonCurrentStudentClassInfoList(int classGradeId, int year, Date date)
            throws AkuraAppException;
    
    /**
     * Get the most recent student suspended record by student Id.
     * 
     * @param studentId - the students id.
     * @return List containing the latest SuspendedStudent record.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<SuspendStudent> getLatestStudentSuspendRecordByStudentId(int studentId) throws AkuraAppException;
    
    /**
     * Get the non current StudentSport list.
     * 
     * @param sportCategoryId - the sport category id.
     * @param year - the year of interest.
     * @param date - date of the event.
     * @return list of non current StudentSport records.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<StudentSport> getNonCurrentStudentListforSportsCategory(int sportCategoryId, Date year, Date date)
            throws AkuraAppException;
    
    /**
     * Get the non current StudentClubSociety list.
     * 
     * @param clubOrSocietyId - the club or society Id.
     * @param year - the year of interest.
     * @param date - date of the event.
     * @return list of non current StudentClubSociety records..
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<StudentClubSociety> getNonCurrentStudentListByClubSociety(int clubOrSocietyId, Date year, Date date)
            throws AkuraAppException;
    
    /**
     * Get the non current StudentTermMark object.
     * 
     * @param studentClassInfoId - the studentClassInfoId id.
     * @param gradeSubjectId - gradeSubjectId.
     * @param termId - termId.
     * @return List containing the latest StudentTermMark object record.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<StudentTermMark> getStudentTermMarkObject(int studentClassInfoId, int gradeSubjectId, int termId)
            throws AkuraAppException;
    
    /**
     * Remove all students that are past, suspended or temporary leaved considered to the date passed.
     * 
     * @param date - day of interest.
     * @param studentClassesToBeRemoved - list of StudentClassInfo to be removed.
     * @param studentClass - StudentClassInfo to be considered for removal.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    void checkStudentStatusForRemoval(Date date, List<StudentClassInfo> studentClassesToBeRemoved,
            StudentClassInfo studentClass) throws AkuraAppException;
    
    /**
     * Retrieves the classInfo id list for a given class grade key and the current year.
     * 
     * @param classGradeId - the key of the class grade.
     * @param currentYear - the current year
     * @return - the list of class info keys
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Integer> getStudentClassInfoIdByYear(final int classGradeId, final Date currentYear) throws AkuraAppException;
    
    /**
     * Check today is exist within any leave period.
     * 
     * @param studentId - id of the student.
     * @return list of StudentLeave.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<StudentLeave> checkTodayIsWithinLeavePeriod(int studentId) throws AkuraAppException;
    
    /**
     * Checks the marking completion status for the relevant class grade , year and the required status.
     * Completed or not.
     * 
     * @param classGradeId - the relevant class grade key.
     * @param year - the relevant year
     * @param markingStatus - the marking completion status true/false
     * @return - the list contains the relevant status objects.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    boolean isMarkingCompleted(final int classGradeId, final String year, boolean markingStatus)
            throws AkuraAppException;
    
    /**
     * Finds the marking status for a relevant class grade key, term and the year.
     * 
     * @param classGradeId - the class grade key
     * @param term - the term key
     * @param currentYear - year
     * @return - the status of the marking status.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    boolean findMarkingFlag(final int classGradeId, final String term, final Date currentYear) throws AkuraAppException;
    
    /**
     * Finds the marking completion object for the given grade key, term and the relevant year.
     * 
     * @param gradeId - the grade key
     * @param term - the term
     * @param currentYear - the relevant year
     * @return - the status of the marking completion
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    boolean findMarkingStatusForGrade(final int gradeId, final String term, final Date currentYear)
            throws AkuraAppException;
    
    /**
     * Retrieves the date of the first date of the student.
     * 
     * @param studentId - the key of the student.
     * @return - the first date of the student.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    Date getStudentStartedDate(int studentId) throws AkuraAppException;
    
    /**
     * Retrieves the marking status for the class grade key and the year.
     * 
     * @param clsGradeId - the class grade key.
     * @param year - the relevant year
     * @return - the status of the marking completion for the relevant class grade key and the year.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Boolean> isExistMarkingCompleted(int clsGradeId, String year) throws AkuraAppException;
    
    /**
     * Get the selected disciplinary action for student.
     * 
     * @param disciplinaryActionId - Id of the selected disciplinary action for student.
     * @return ListStudentDiscipline disciplinary action.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<StudentDiscipline> getSelectedDisciplinaryActionByStudent(int disciplinaryActionId) throws AkuraAppException;
    
    /**
     * This method retrieves the StudentClassInfo object by its Id.
     * 
     * @param studentClassInfoId - the id of the StudentClassInfo to be retrieved.
     * @return a list of StudentClassInfo objects.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    StudentClassInfo getStudentClassInfoByStudentClassInfoId(final int studentClassInfoId) throws AkuraAppException;
    
    /**
     * Gets the student assignment marks by student id for the departed students.
     * 
     * @param studentId the student id
     * @return the student assignment marks by student id
     * @throws AkuraAppException the akura app exception
     */
    List<Object[]> getStudentAssignmentMarksByStudentIdForReport(int studentId) throws AkuraAppException;
    
    /**
     * Returns a list of students in a specific class grade without old boys.
     * 
     * @param classGradeId classgrade id.
     * @param year the Year.
     * @param dateObj
     * @return a list of StudentClassInfo.
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentClassInfo> getPresentClassStudentList(int classGradeId, int year) throws AkuraAppException;
    
    /**
     * Retrieves the count of the terms.
     * 
     * @return - the number of the terms.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    int getNoOfTerms() throws AkuraAppException;
    
    /**
     * Retrieve the list of student status Ids by passing a list of studentIds.
     * 
     * @param studentIdsList - the list of student ids.
     * @return list of studentStatusIds.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<Integer> getStudentStatusListByStudentIdsList(List<Integer> studentIdsList) throws AkuraAppException;
    
    /**
     * Retrieve a student status Ids by passing a studentId.
     * 
     * @param studentId - a student id.
     * @return studentStatusId of relavant student.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    int getStudentStatusId(int studentId) throws AkuraAppException;
    
    /**
     * Retrieve the list of StudentClassInfo of Suspended and Temporary leaved students, who are yet to be
     * activated.
     * 
     * @param classGradeId - the class grade id of the student.
     * @param year - year of interest.
     * @param date - date of interest.
     * @return list of StudentClassInfo of students.
     * @throws AkuraAppException - throws detailed exception when fails to retrieve.
     */
    List<StudentClassInfo> getSuspendedAndTemporaryLeavedStudentsToBeActive(int classGradeId, int year, Date date)
            throws AkuraAppException;
    
    /**
     * service to find a StudentDiscipline.
     * 
     * @param studentId the id Student.
     * @param year the Date
     * @return StudentDiscipline {@link StudentDiscipline}
     * @throws AkuraAppException - The exception details that occurred when retrieve StudentDiscipline
     *         instances.
     */
    List<StudentDiscipline> findStudentDisciplineListForStudent(int studentId, Date year) throws AkuraAppException;
    
    /**
     * service to set e mail properties.
     * 
     * @param strFromAddress - strFromAddress , from e mail address.
     * @param strToemail - strToemail, to email address.
     * @param templateName - templateName , email template name.
     * @param mapObjectMap - mapObjectMap, for e mail template properties.
     * @param subject - subject, email subject.
     * @return CommonEmailBean - CommonEmailBean object.
     * @throws AkuraAppException - The exception details that occurred when retrieve StudentDiscipline
     *         instances.
     */
    CommonEmailBean setEmailProperties(String strFromAddress, String strToemail, String templateName,
            Map<String, Object> mapObjectMap, String subject) throws AkuraAppException;
    
    /**
     * Check the student is on leave on the selected date.
     * 
     * @param studentId - id of the student.
     * @param date - date to be considered to check if student leave is exist.
     * @return true if student is on leave on the considered date.
     * @throws AkuraAppException - The exception details that will occur when fails to check StudentDiscipline
     *         availability.
     */
    boolean checkStudentIsOnLeave(int studentId, Date date) throws AkuraAppException;
    
    /**
     * Check the student is present on selected date range.
     * 
     * @param studentId - id of the student.
     * @param fromDate - fromDate to be considered to check if student leave is present.
     * @param toDate - toDate to be considered to check if student leave is present.
     * @return true if student is on leave on the considered date.
     * @throws AkuraAppException - The exception details that will occur when fails to check StudentDiscipline
     *         availability.
     */
    boolean isPresentDay(int studentId, Date fromDate, Date toDate) throws AkuraAppException;

    
    /**
     * Viewing student discipline information for a given student.
     *
     * @param studentId specifies the student discipline id, defined by an integer
     * @return List of StudentDiscipline type objects
     * @throws AkuraAppException SMS Exception.
     */
//    List<Object> viewStudentDisciplineDetailsInfo(int studentId) throws AkuraAppException;


    
    /**
     * for mobility Returns a list of scholarship descriptions for the particular student key and the current
     * year.
     * 
     * @param studentKey - the key of the student
     * @param date - the current year
     * @return - a list of the scholarship description.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<String> getScholarshipNameByStudentId(final int studentKey, final int date) throws AkuraAppException;
    
    /**
     * for mobility Returns a list of prefect type descriptions for the particular student key and the current
     * year.
     * 
     * @param studentKey - the key of the student
     * @param currentYear - the current year
     * @return - a list of the prefect type description.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<String> getPrefectTypeByStudentId(final int studentKey, final int currentYear) throws AkuraAppException;
    
    /**
     * Returns the club and societies of the student for the current year.
     * 
     * @param studentKey - the key of the student.
     * @param currentYear - the current year
     * @return - the club and societies of the student for the current year.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<List<String>> viewClubAndSocieties(int studentKey, int currentYear) throws AkuraAppException;
    
    /**
     * Returns the list of student seminar descriptions for a particular student and the current year.
     * 
     * @param studentKey - the key of the student
     * @param currentYear - current date
     * @return - the list of student seminar descriptions
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<List<String>> viewStudentSeminars(int studentKey, int currentYear) throws AkuraAppException;
    
    /**
     * Returns the academic achievements of the student, current year.
     * 
     * @param studentKey - the key of the student
     * @param currentYearOnly - current year
     * @param acheivementStatus - achievements status, academic status
     * @return - the academic achievements of the student, current year.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<String> viewAchievements(int studentKey, int currentYearOnly, boolean acheivementStatus)
            throws AkuraAppException;





    
    /**
     * Get the total number of students in the school.
     *  
     * @return the number of students in the school.
     * @throws AkuraAppException -  The exception details that occurred when processing.
     */
    int getNumberOfStudntsInSchool() throws AkuraAppException;

}
