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
package com.virtusa.akura.api.dto;


import java.util.List;

/**
 * @author Virtusa Corporation
 */

public class StudentTermMarkSubjectCriteria {


    /**
     * To hold the term List and bind it to the JSP view.
     * */
    private List<StudentTermMark> studentTermMarkList;


    /**
     * To hold the student List and bind it to the JSP view.
     * */
    private List<StudentClassInfo> studentNameList;



    /**
     * To hold the student subject List and bind it to the JSP view.
     * */
    private List<GradeSubject> studentSubjectList;


    /**
     * To hold the student monthly grade List and bind it to the JSP view.
     * */
    private List<String> studentGradeList;

    /**
     * To hold the student students subjects mark.
     * */
    private List<String> studentSubjectMarkNameList;

    /**
     * To hold the mark List and bind it to the JSP view.
     * */
    private float[] studentMarkList;

    /*
     *
     *
     *List<GradeSubject> gradeSubjectList = new ArrayList<GradeSubject>();//
        //List<StudentClassInfo> studentClassInfoList = new ArrayList<StudentClassInfo>();
        List<StudentTermMark> studentTermMarkList = new ArrayList<StudentTermMark>();

        //studentTermMarkSubjectCriteria.setStudentSubjectList(gradeSubjectList);
        //studentTermMarkSubjectCriteria.setStudentNameList(studentClassInfoList);
        studentTermMarkSubjectCriteria.setStudentTermMarkList(studentTermMarkList);
     * */

    /**
     * @return the studentSubjectMarkNameList
     */
    public List<String> getStudentSubjectMarkNameList() {

        return studentSubjectMarkNameList;
    }


    /**
     * @param listStudentSubjectMarkNameList the studentSubjectMarkNameList to set
     */
    public void setStudentSubjectMarkNameList(List<String> listStudentSubjectMarkNameList) {

        this.studentSubjectMarkNameList = listStudentSubjectMarkNameList;
    }

    /**
     * @return the studentGradeList
     */
    public List<String> getStudentGradeList() {

        return studentGradeList;
    }

    /**
     * @param listStudentGradeList the studentGradeList to set
     */
    public void setStudentGradeList(List<String> listStudentGradeList) {

        this.studentGradeList = listStudentGradeList;
    }


     /**
     * getter method to return StudentNameList.
     * @return studentNameList.
     * */
    public List<StudentClassInfo> getStudentNameList() {

        return studentNameList;
    }

    /**
     * setter method to set StudentNameList.
     * @param listStudentNameList to hold names to set.
     * */
    public void setStudentNameList(List<StudentClassInfo> listStudentNameList) {

        this.studentNameList = listStudentNameList;
    }

    /**
     * getter method to return StudentSubjectList.
     * @return studentSubjectList.
     * */
    public List<GradeSubject> getStudentSubjectList() {

        return studentSubjectList;
    }

    /**
     * setter method to set studentSubjectList.
     * @param listStudentSubjectList to hold subjects to set.
     * */
    public void setStudentSubjectList(List<GradeSubject> listStudentSubjectList) {

        this.studentSubjectList = listStudentSubjectList;
    }

    /**
     * getter method to return StudentMarkList.
     * @return studentMarkList.
     * */
    public float[] getStudentMarkList() {

        if (studentMarkList != null) {

            return studentMarkList.clone();
        }
        return null;
    }

    /**
     * setter method to set StudentMarkList.
     * @param fltStudentMarkList to hold marks to set.
     * */
    public void setStudentMarkList(float[] fltStudentMarkList) {

        if (fltStudentMarkList != null) {

            this.studentMarkList = fltStudentMarkList.clone();
        }
    }


    /**
     * @return the studentTermMarkList
     */
    public List<StudentTermMark> getStudentTermMarkList() {

        return studentTermMarkList;
    }


    /**
     * @param listStudentTermMarkList the studentTermMarkList to set
     */
    public void setStudentTermMarkList(List<StudentTermMark> listStudentTermMarkList) {

        this.studentTermMarkList = listStudentTermMarkList;
    }
}
