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

USE `akura`;



DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_student_exam_mark` $$

CREATE PROCEDURE `sp_student_exam_mark`(IN examMarksSize integer(11), IN gradeId varchar(45), IN year date, 
IN examId integer(11))
BEGIN
    IF (examMarksSize = -1) THEN
       SELECT student.NAME_WT_INITIALS as student, 
               student.student_id as studentId,
               subject.description as subject,
               class_grade.class_grade_id as gradeId , 
               es.exam_id as examId,
               sci.year, 
               subject.subject_id as subjectId,
               exam.mark_type AS markType,
               es.exam_subject_id,
               ea.admission_no,
               ea.exam_admission_id
        FROM STUDENT student
        INNER join STUDENT_CLASS_INFO sci
            ON sci.student_id = student.student_id
        INNER join CLASS_GRADE class_grade
            ON sci.class_grade_id = class_grade.class_grade_id
        INNER join GRADE grade
            ON grade.grade_id = class_grade.grade_id
        INNER JOIN EXAM exam
            ON exam.grade_id = grade.grade_id
        INNER JOIN EXAM_SUBJECT es
            ON es.exam_id = exam.exam_id
        INNER JOIN SUBJECT subject
            ON subject.subject_id = es.subject_id
        LEFT JOIN EXAM_ADMISSIONS ea
            ON ea.student_id = student.student_id 
            and ea.exam_id = exam.exam_id
            and sci.year = ea.year
        WHERE class_grade.class_grade_id = gradeId 
        and sci.year= year 
        AND es.exam_id = examId
        AND  ea.exam_admission_id is null
        AND (student.student_status_id = 1 OR student.student_status_id = 3)
        GROUP by examId, class_grade.class_grade_id, subject, student.NAME_WT_INITIALS,studentId;
        
    ELSEIF (examMarksSize = 0) THEN
       SELECT student.NAME_WT_INITIALS as student, 
               student.student_id as studentId,
               subject.description as subject,
               class_grade.class_grade_id as gradeId , 
               es.exam_id as examId,
               sci.year, 
               subject.subject_id as subjectId,
               exam.mark_type AS markType,
               es.exam_subject_id,
               ea.admission_no,
               ea.exam_admission_id
        FROM STUDENT student
        INNER join STUDENT_CLASS_INFO sci
            ON sci.student_id = student.student_id
        INNER join CLASS_GRADE class_grade
            ON sci.class_grade_id = class_grade.class_grade_id
        INNER join GRADE grade
            ON grade.grade_id = class_grade.grade_id
        INNER JOIN EXAM exam
            ON exam.grade_id = grade.grade_id
        INNER JOIN EXAM_SUBJECT es
            ON es.exam_id = exam.exam_id
        INNER JOIN SUBJECT subject
            ON subject.subject_id = es.subject_id
        INNER JOIN EXAM_ADMISSIONS ea
            ON ea.student_id = student.student_id 
            and ea.exam_id = exam.exam_id
            AND ea.year = sci.year
        WHERE class_grade.class_grade_id = gradeId and sci.year= year
        AND (student.student_status_id = 1 OR student.student_status_id = 3)
        AND es.exam_id = examId
        GROUP by examId, class_grade.class_grade_id, subject, student.NAME_WT_INITIALS,studentId;
        
        ELSEIF(examMarksSize > 0 && gradeId = '0') THEN
         SELECT student.NAME_WT_INITIALS as student, 
               student.student_id as studentId,
               subject.description as subject,
               class_grade.class_grade_id as gradeId , 
               es.exam_id as examId,
               sci.year, 
               subject.subject_id as subjectId,
               exam.mark_type AS markType,
               es.exam_subject_id,
               ea.admission_No,
               ea.exam_admission_id,
               em.marks,
               em.grading_id,
               em.exam_marks_id,
               em.status,
               grading.grade_acronym AS grading,
               em.is_optional,
               em.is_absent
        FROM STUDENT student
        INNER join STUDENT_CLASS_INFO sci
            ON sci.student_id = student.student_id
        INNER join CLASS_GRADE class_grade
            ON sci.class_grade_id = class_grade.class_grade_id
        INNER join GRADE grade
            ON grade.grade_id = class_grade.grade_id
        INNER JOIN EXAM exam
            ON exam.grade_id = grade.grade_id
        INNER JOIN EXAM_SUBJECT es
            ON es.exam_id = exam.exam_id
        INNER JOIN SUBJECT subject
            ON subject.subject_id = es.subject_id
        INNER JOIN EXAM_MARKS em
            ON em.exam_subject_id = es.exam_subject_id 
            and em.student_id = student.student_id
            LEFT JOIN EXAM_ADMISSIONS ea
            ON ea.student_id = student.student_id 
            and ea.exam_id = exam.exam_id
        LEFT JOIN GRADING grading
            ON grading.grading_id = em.grading_id
        WHERE sci.year= year 
                AND es.exam_id = examId
                AND (student.student_status_id = 1 OR student.student_status_id = 3)
                GROUP by examId, subject, student.NAME_WT_INITIALS, studentId;
        
                ELSEIF (examMarksSize = -5) THEN
        SELECT student.NAME_WT_INITIALS as student, 
               student.student_id as studentId,
               subject.description as subject,
               class_grade.class_grade_id as gradeId , 
               es.exam_id as examId,
               sci.year, 
               subject.subject_id as subjectId,
               exam.mark_type AS markType,
               es.exam_subject_id,
               ea.admission_no,
               ea.exam_admission_id
        FROM STUDENT student
        INNER join STUDENT_CLASS_INFO sci
            ON sci.student_id = student.student_id
        INNER join CLASS_GRADE class_grade
            ON sci.class_grade_id = class_grade.class_grade_id
        INNER join GRADE grade
            ON grade.grade_id = class_grade.grade_id
        INNER JOIN EXAM exam
            ON exam.grade_id = grade.grade_id
        INNER JOIN EXAM_SUBJECT es
            ON es.exam_id = exam.exam_id
        INNER JOIN SUBJECT subject
            ON subject.subject_id = es.subject_id
        LEFT JOIN EXAM_ADMISSIONS ea
            ON ea.student_id = student.student_id 
            and ea.exam_id = exam.exam_id
            AND ea.year = sci.year
        WHERE class_grade.class_grade_id = gradeId and sci.year= year AND (student.student_status_id = 1 OR student.student_status_id = 3)
        AND es.exam_id = examId
        AND  ea.exam_admission_id is null and
        es.exam_subject_id  IN 
            (SELECT es.exam_subject_Id
                    FROM EXAM_SUBJECT es
                        LEFT JOIN EXAM_MARKS em
                            ON es.exam_subject_id = em.exam_subject_id
                        INNER JOIN SUBJECT subject
                            ON subject.subject_id = es.subject_id
                        WHERE em.exam_subject_id IS NULL
                        AND exam_id = examId AND 
                        em.year IS NULL
                        ORDER BY subject.description)
            GROUP by examId, class_grade.class_grade_id, subject, 
            student.NAME_WT_INITIALS, studentId;
            
            
            ELSEIF (examMarksSize = -6) THEN
        SELECT student.NAME_WT_INITIALS as student, 
               student.student_id as studentId,
               subject.description as subject,
               class_grade.class_grade_id as gradeId , 
               es.exam_id as examId,
               sci.year, 
               subject.subject_id as subjectId,
               exam.mark_type AS markType,
               es.exam_subject_id,
               ea.admission_no,
               ea.exam_admission_id
        FROM STUDENT student
        INNER join STUDENT_CLASS_INFO sci
            ON sci.student_id = student.student_id
        INNER join CLASS_GRADE class_grade
            ON sci.class_grade_id = class_grade.class_grade_id
        INNER join GRADE grade
            ON grade.grade_id = class_grade.grade_id
        INNER JOIN EXAM exam
            ON exam.grade_id = grade.grade_id
        INNER JOIN EXAM_SUBJECT es
            ON es.exam_id = exam.exam_id
        INNER JOIN SUBJECT subject
            ON subject.subject_id = es.subject_id
        LEFT JOIN EXAM_ADMISSIONS ea
            ON ea.student_id = student.student_id 
            and ea.exam_id = exam.exam_id
            AND ea.year = sci.year
        WHERE class_grade.class_grade_id = gradeId and sci.year= year AND (student.student_status_id = 1 OR student.student_status_id = 3)
        AND es.exam_id = examId
        AND  ea.exam_admission_id is null and
        es.exam_subject_id NOT IN 
            (SELECT es.exam_subject_Id
                    FROM EXAM_SUBJECT es
                        LEFT JOIN EXAM_MARKS em
                            ON es.exam_subject_id = em.exam_subject_id
                        INNER JOIN SUBJECT subject
                            ON subject.subject_id = es.subject_id
                        WHERE em.exam_subject_id IS NULL
                        AND exam_id = examId AND em.year IS NULL
                        ORDER BY subject.description)
            GROUP by examId, class_grade.class_grade_id, subject,
            student.NAME_WT_INITIALS, studentId;
            
        ELSEIF(examMarksSize > 0) THEN
         SELECT student.NAME_WT_INITIALS as student, 
               student.student_id as studentId,
               subject.description as subject,
               class_grade.class_grade_id as gradeId , 
               es.exam_id as examId,
               sci.year, subject.subject_id as subjectId,
               exam.mark_type AS markType,
               es.exam_subject_id,
               ea.admission_No,
               ea.exam_admission_id,
               em.marks,
               em.grading_id,
               em.exam_marks_id,
               em.status,
               grading.grade_acronym AS grading,
               em.is_optional,
               em.is_absent
        FROM STUDENT student
        INNER join STUDENT_CLASS_INFO sci
            ON sci.student_id = student.student_id
        INNER join CLASS_GRADE class_grade
            ON sci.class_grade_id = class_grade.class_grade_id
        INNER join GRADE grade
            ON grade.grade_id = class_grade.grade_id
        INNER JOIN EXAM exam
            ON exam.grade_id = grade.grade_id
        INNER JOIN EXAM_SUBJECT es
            ON es.exam_id = exam.exam_id
        INNER JOIN SUBJECT subject
            ON subject.subject_id = es.subject_id
            INNER JOIN EXAM_ADMISSIONS ea
            ON ea.student_id = student.student_id 
            AND ea.exam_id = exam.exam_id
            AND ea.year = sci.year
        INNER JOIN EXAM_MARKS em
            ON em.exam_subject_id = es.exam_subject_id 
            and em.student_id = student.student_id
            AND ea.exam_admission_id = em.exam_admission_id
        LEFT JOIN GRADING grading
            ON grading.grading_id = em.grading_id
        WHERE class_grade.class_grade_id = gradeId and sci.year= year AND (student.student_status_id = 1 OR student.student_status_id = 3)
                AND es.exam_id = examId
                GROUP by examId, class_grade.class_grade_id, subject, 
                student.NAME_WT_INITIALS, studentId;
    END IF;
END
