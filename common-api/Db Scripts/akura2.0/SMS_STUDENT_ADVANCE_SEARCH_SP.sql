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

DROP PROCEDURE IF EXISTS `sp_student_advance_search` $$

CREATE PROCEDURE `sp_student_advance_search`(IN classId integer(10), IN sportId integer(10), IN subjectId integer(10), IN prefectTypeId integer(10), IN workingSegmentId integer(10), IN currentYear integer(4))
BEGIN
     IF (classId > 0 && sportId = 0 && subjectId = 0 && prefectTypeId = 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
            STUDENT_CLASS_INFO info,
            CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId > 0 && subjectId = 0 && prefectTypeId = 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
            STUDENT_SPORT sport,
            SPORT_CATEGORY cat,
            STUDENT_CLASS_INFO info,
            CLASS_GRADE cg
        where st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND st.student_id = info.student_id
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId = 0 && subjectId > 0 && prefectTypeId = 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
            STUDENT_CLASS_INFO info,
            STUDENT_TERM_MARK term,
            GRADE_SUBJECT sub,
            CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId = 0 && subjectId = 0 && prefectTypeId > 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
            STUDENT_PREFECT stPref,
            PREFECT_TYPE prefType,
            STUDENT_CLASS_INFO info,
            CLASS_GRADE cg
        where st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND st.student_id = info.student_id
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear

        order by st.full_name;
    ELSEIF (classId = 0 && sportId = 0 && subjectId = 0 && prefectTypeId = 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
            PARENT p,
            STUDENT_PARENT stp,
            WORKING_SEGMENT ws,
            STUDENT_CLASS_INFO info,
            CLASS_GRADE cg
        where st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND st.student_id = info.student_id
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId > 0 && sportId > 0 && subjectId = 0 && prefectTypeId = 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
            STUDENT_CLASS_INFO info,
            STUDENT_SPORT sport,
            SPORT_CATEGORY cat,
            CLASS_GRADE cg
        where st.student_id = info.student_id
            AND st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND info.class_grade_id = classId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId > 0 && sportId = 0 && subjectId > 0 && prefectTypeId = 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
            STUDENT_CLASS_INFO info,
            STUDENT_TERM_MARK term,
            GRADE_SUBJECT sub,
            CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND info.class_grade_id = classId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId > 0 && sportId = 0 && subjectId = 0 && prefectTypeId > 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
            STUDENT_CLASS_INFO info,
            STUDENT_PREFECT stPref,
            PREFECT_TYPE prefType,
            CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId > 0 && sportId = 0 && subjectId = 0 && prefectTypeId = 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
            PARENT p,
            STUDENT_PARENT stp,
            WORKING_SEGMENT ws,
            STUDENT_CLASS_INFO info,
            CLASS_GRADE cg
        where st.student_id = info.student_id
	        AND info.class_grade_id = classId
	        AND st.student_id = stp.student_id
            	AND p.parent_id = stp.parent_id
            	AND p.working_segment_id = ws.working_segment_id
            	AND ws.working_segment_id = workingSegmentId
                AND info.class_grade_id = cg.class_grade_id
                AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId > 0 && subjectId > 0 && prefectTypeId = 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
            STUDENT_SPORT sport,
            SPORT_CATEGORY cat,
            STUDENT_CLASS_INFO info,
            STUDENT_TERM_MARK term,
            GRADE_SUBJECT sub,
            CLASS_GRADE cg
        where st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND st.student_id = info.student_id
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = info.student_id
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId > 0 && subjectId = 0 && prefectTypeId > 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
            STUDENT_SPORT sport,
            SPORT_CATEGORY cat,
            STUDENT_PREFECT stPref,
            PREFECT_TYPE prefType,
            STUDENT_CLASS_INFO info,
            CLASS_GRADE cg
        where st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND st.student_id = info.student_id
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId > 0 && subjectId = 0 && prefectTypeId = 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
       from STUDENT st,
            PARENT p,
            STUDENT_PARENT stp,
            WORKING_SEGMENT ws,
            STUDENT_SPORT sport,
            SPORT_CATEGORY cat,
            STUDENT_CLASS_INFO info,
            CLASS_GRADE cg
        where st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND st.student_id = info.student_id
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId = 0 && subjectId > 0 && prefectTypeId > 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
            from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId = 0 && subjectId > 0 && prefectTypeId = 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
            from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId = 0 && subjectId = 0 && prefectTypeId > 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
            from STUDENT st,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                STUDENT_CLASS_INFO info,
                CLASS_GRADE cg
        where st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND st.student_id = info.student_id
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId > 0 && sportId > 0 && subjectId > 0 && prefectTypeId = 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_SPORT sport,
                SPORT_CATEGORY cat,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND info.class_grade_id = classId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId > 0 && sportId > 0 && subjectId = 0 && prefectTypeId > 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_SPORT sport,
                SPORT_CATEGORY cat,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId > 0 && sportId > 0 && subjectId = 0 && prefectTypeId = 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_SPORT sport,
                SPORT_CATEGORY cat,
                 PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId > 0 && sportId = 0 && subjectId > 0 && prefectTypeId > 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId > 0 && sportId = 0 && subjectId > 0 && prefectTypeId = 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;

    ELSEIF (classId > 0 && sportId = 0 && subjectId = 0 && prefectTypeId > 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;

    ELSEIF (classId = 0 && sportId > 0 && subjectId > 0 && prefectTypeId > 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_SPORT sport,
                SPORT_CATEGORY cat,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId > 0 && subjectId > 0 && prefectTypeId = 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_SPORT sport,
                SPORT_CATEGORY cat,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId > 0 && subjectId = 0 && prefectTypeId > 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_SPORT sport,
                SPORT_CATEGORY cat,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                STUDENT_CLASS_INFO info,
                CLASS_GRADE cg
        where  st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND st.student_id = info.student_id
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId = 0 && subjectId > 0 && prefectTypeId > 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
  ELSEIF (classId > 0 && sportId > 0 && subjectId > 0 && prefectTypeId > 0 && workingSegmentId = 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_SPORT sport,
                SPORT_CATEGORY cat,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId > 0 && sportId > 0 && subjectId > 0 && prefectTypeId = 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_SPORT sport,
                SPORT_CATEGORY cat,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId = 0 && sportId > 0 && subjectId > 0 && prefectTypeId > 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_SPORT sport,
                SPORT_CATEGORY cat,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId > 0 && sportId = 0 && subjectId > 0 && prefectTypeId > 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSEIF (classId > 0 && sportId > 0 && subjectId = 0 && prefectTypeId > 0 && workingSegmentId > 0) THEN
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_SPORT sport,
                SPORT_CATEGORY cat,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    ELSE
        select distinct(st.student_id), st.full_name, cg.description
        from STUDENT st,
                STUDENT_CLASS_INFO info,
                STUDENT_SPORT sport,
                SPORT_CATEGORY cat,
                STUDENT_TERM_MARK term,
                GRADE_SUBJECT sub,
                STUDENT_PREFECT stPref,
                PREFECT_TYPE prefType,
                PARENT p,
                STUDENT_PARENT stp,
                WORKING_SEGMENT ws,
                CLASS_GRADE cg
        where st.student_id = info.student_id
            AND info.class_grade_id = classId
            AND st.student_id = sport.student_id
            AND sport.sport_category_id = cat.sport_category_id
            AND cat.sport_id = sportId
            AND info.student_class_info_id = term.student_class_info_id
            AND term.grade_subject_id = sub.grade_subject_id
            AND sub.subject_id = subjectId
            AND st.student_id = stPref.student_id
            AND stPref.prefect_type_id = prefType.prefect_type_id
            AND prefType.prefect_type_id = prefectTypeId
            AND st.student_id = stp.student_id
            AND p.parent_id = stp.parent_id
            AND p.working_segment_id = ws.working_segment_id
            AND ws.working_segment_id = workingSegmentId
            AND info.class_grade_id = cg.class_grade_id
            AND year(info.year) = currentYear
        order by st.full_name;
    END IF;
END

