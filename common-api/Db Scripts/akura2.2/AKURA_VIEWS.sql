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

-- CLASS_WISE_STUDENTS_SUBJECTS_VIEW
-- Purpose of this view is to get student to enter assignment marks first time.
CREATE OR REPLACE VIEW  CLASS_WISE_STUDENTS_SUBJECTS_VIEW AS
SELECT s.NAME_WT_INITIALS as FULLNAME,
    sci.STUDENT_ID as STUDENT_ID,
    sci.STUDENT_CLASS_INFO_ID as STUDENT_CLASS_INFO_ID, 
    sci.CLASS_GRADE_ID as CLASS_GRADE_ID,
    stm.GRADE_SUBJECT_ID as GRADE_SUBJECT_ID,
    sci.YEAR as YEAR
FROM STUDENT_CLASS_INFO sci,
     STUDENT_TERM_MARK stm,
     STUDENT s
WHERE sci.STUDENT_CLASS_INFO_ID = stm.STUDENT_CLASS_INFO_ID 
  AND sci.STUDENT_ID = s.STUDENT_ID
    GROUP BY 
	stm.STUDENT_CLASS_INFO_ID,
    sci.CLASS_GRADE_ID,
    stm.GRADE_SUBJECT_ID;