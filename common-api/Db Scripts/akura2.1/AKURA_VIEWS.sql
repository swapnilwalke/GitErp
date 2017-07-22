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

use `akura`;

-- EXAM_RESULTS_VIEW
-- Purpose of this view is to generate student's Exam Results

CREATE OR REPLACE VIEW  EXAM_RESULTS_VIEW
AS
select s.STUDENT_ID, s.FULL_NAME, sub.DESCRIPTION as SUB_DESCRIPTION, em.YEAR, em.MARKS, g.GRADE_ACRONYM,
 g.DESCRIPTION as GRADING_DESCRIPTION, e.EXAM_ID, e.DESCRIPTION as EXAM_DESCRIPTION,
 e.MARK_TYPE, es.IS_OPTIONAL_SUBJECT, ea.ADMISSION_NO,
 em.IS_ABSENT AS ABSENT,
 em.IS_OPTIONAL AS OPTIONAL
from EXAM_MARKS em 
LEFT JOIN EXAM_SUBJECT es ON em.EXAM_SUBJECT_ID=es.EXAM_SUBJECT_ID 
LEFT JOIN GRADING g on em.GRADING_ID=g.GRADING_ID 
LEFT JOIN STUDENT s ON em.STUDENT_ID=s.STUDENT_ID 
LEFT JOIN EXAM e ON es.EXAM_ID=e.EXAM_ID 
LEFT JOIN SUBJECT sub ON es.SUBJECT_ID=sub.SUBJECT_ID , EXAM_ADMISSIONS ea
WHERE ea.EXAM_ID=e.EXAM_ID AND em.YEAR=ea.YEAR AND em.STUDENT_ID=ea.STUDENT_ID 
AND 
(es.IS_OPTIONAL_SUBJECT = 0 OR em.MARKS is not null OR em.GRADING_ID is not null 
OR em.IS_ABSENT = 1 
OR (em.IS_ABSENT = 0 AND em.MARKS is not null AND em.GRADING_ID is not null)) 
	