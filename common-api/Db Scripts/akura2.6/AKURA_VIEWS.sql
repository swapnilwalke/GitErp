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

/*
Stduent Scholarship Report View
*/
CREATE OR REPLACE VIEW STUDENTSCHOLARSHIPVIEW
AS
SELECT 
shlor.name AS scholarshipName,
stu.admission_no AS admissionNo, 
stu.name_wt_initials AS nameWithInitials,
cg.description AS description

FROM STUDENT_SCHOLARSHIP sh, 
     STUDENT stu, SCHOLARSHIP shlor, 
     STUDENT_CLASS_INFO stuInfo,
     CLASS_GRADE cg

WHERE shlor.scholarship_id=sh.scholarship_id AND 
      stu.student_id = sh.student_id 
      AND sh.student_id=stuInfo.student_id 
      AND cg.class_grade_id=stuInfo.class_grade_id
      AND sh.year= stuInfo.year
	  
	  ORDER BY `stu`.`ADMISSION_NO` ASC;
	        