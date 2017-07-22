<%--
    < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>

    Copyright (C) 2012 Virtusa Corporation.
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><spring:message code="APPLICATION.NAME"/></title>
<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">

<script src="resources/js/jquery-1.6.2.min.js" language="javascript"></script>
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<script language="javascript" src="resources/js/main.js"></script>


<SCRIPT LANGUAGE="JavaScript">
	function checkAll(field) {
		for (i = 0; i < field.length; i++)
			field[i].checked = true;

	}
</script>
</head>
<body>

<h:headerNew parentTabId="10" page="studentSubject.htm" />

<div id="page_container">
<div id="breadcrumb">
<ul>
	<li><a href="adminWelcome.htm"><spring:message code="application.home"/></a>&nbsp;&gt;&nbsp;</li>
	<li><spring:message code="STUDENT.SUBJECT.TITLE"/></li>
</ul>
</div>
<div class="help_link"><a href="#"
	onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/student/studentSubjectHelp.html"/>','helpWindow',780,550)"><img
	src="resources/images/ico_help.png" width="20" height="20"
	align="absmiddle"><spring:message code="application.help"/> </a></div>
<div class="clearfix"></div>
<h1><spring:message code="STUDENT.SUBJECT.TITLE"/></h1>
<div>
	<c:if test="${message != null}">
		<label class="missing_required_error"> ${message}</label>
	</c:if>
</div>
<div id="content_main">
<form action="" method="post" name="form"><c:set
	var="gradeclassdescription" value="" />

<div class="clearfix"></div>
<div class="section_full_search">
<div class="box_border">
<p><spring:message code="STUDENT.SUBJECT.SUB.TITLE"/></p>
<div class="row">
<div class="float_left">
<div class="lbl_lock"><span class="required_sign">*</span><label><spring:message code="STUDENT.SUBJECT.CLASS"/></label></div>
<select name="select">
	<option value ="0"><spring:message code="application.drop.down"/></option>
	<c:forEach var="gradeclass" items="${gradeClassList}">
		<c:set var="testval" value="" />
		<c:if test="${gradeclass.classGradeId eq classGradeId}">
			<c:set var="testval" value="SELECTED" />
			<c:set var="gradeclassdescription" value="${gradeclass.description}" />
		</c:if>
		<option label="${gradeclass.description}"
			value="${gradeclass.classGradeId}" <c:out value="${testval}"></c:out>>
		${gradeclass.description}</option>
	</c:forEach>
</select></div>
<div class="float_left">
<div class="lbl_lock"><label><spring:message code="STUDENT.SUBJECT.YEAR"/></label></div>
<select name="year" id="selectedYear">
									<c:forEach items="${yearList}" var="year" varStatus="status">
										<option label="${year}" value="${year}" <c:if test="${thisYear == year}">selected="selected"</c:if>>${year}</option>
									</c:forEach>
								</select></div>
<div class="float_right">
<div class="buttion_bar_type1">
<sec:authorize access="hasAnyRole('Search Student Subject List')">
<input type="button"
	value="<spring:message code="STUDENT.SUBJECT.UI.SEARCH.CLASS"/>"
	onClick="document.form.action='searchStudentSubjectList.htm'; document.form.submit();document.getElementById('search_results').style.display=''; document.getElementById('btn_row').style.display=''"
	class="button">
</sec:authorize></div>
</div>
</div>
<div class="clearfix"></div>
</div>
</div>
<c:if test="${(not empty studentList) and (not empty gradeSubjectList)}">

	<script language="javascript">
          $(document).ready(function(){
              $("#tblFreezed tr").each(function(index){
                  $("#tblScrool tr").eq(index).height($("#tblFreezed tr").eq(index).innerHeight());
              });

              if ($.browser.msie) {
                  $("#tblFreezed th").eq(0).height($("#tblScrool th").eq(0).innerHeight());
              }
              else {
                  $("#tblFreezed th").eq(0).height($("#tblScrool th").eq(0).innerHeight() + 2);
              }

          });
      </script>

	<script language="javascript">
						//Popup.show('search_results');
					</script>
	<div class="section_box" id="search_results">
	<div class="section_box_header">
	<h2>${gradeclassdescription} (${thisYear}) <spring:message code="STUDENT.SUBJECT.SUBJECT"/></h2>
	</div>
	<div class="column_single">
	<table id="tblFreezed" class="basic_grid basic_grid_freezed"
		style="width: 200px;" border="0" cellspacing="0">
		<tr>
			<th style="height: 34px; * height: 32px;"><spring:message code="STUDENT.SUBJECT.STUDENT.NAME"/></th>
		</tr>
		<c:forEach var="studentclass" items="${studentList}"
			varStatus="status">
			<tr
				<c:choose>
            		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
            		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
            		</c:choose>>
				<td class="left"><p class="wWrap">${studentclass.student.nameWtInitials}</p></td>
			</tr>
		</c:forEach>
	</table>
	<div style="width: 708px; overflow-x: scroll; float: left">
	<table id="tblScrool" class="basic_grid marksGrid" border="0"
		cellspacing="0">
		<tr>
			<c:forEach var="gradesubject" items="${gradeSubjectList}">
				<th width="80" style="height: 34px; * height: 32px; text-align: center;">${gradesubject.subject.description}</th>
			</c:forEach>
		</tr>

		<c:forEach var="studentclass" items="${studentList}"
			varStatus="status">
			<tr
				<c:choose>
            		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
            		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
            		</c:choose>>
				<c:forEach var="gradesubject" items="${gradeSubjectList}">

					<c:choose>
						<c:when test='${! empty studentsubjectmap}'>


							<c:set var="myString"
								value="${gradesubject.gradeSubjectId},${studentclass.studentClassInfoId}" />
							<c:choose>
								<c:when test='${! empty studentsubjectmap[myString]}'>
									<c:set var="checkval" value="CHECKED" />
									<c:set var="termmarkID" value="${studentsubjectmap[myString]}" />
								</c:when>
								<c:otherwise>
									<c:set var="checkval" value="" />
									<c:set var="termmarkID" value="-1" />

								</c:otherwise>
							</c:choose>

							<td><input name="gradesubjectcheck" type="checkbox"
								value="${gradesubject.gradeSubjectId},${studentclass.studentClassInfoId},${termmarkID}"
								<c:out value="${checkval}"></c:out>></td>

						</c:when>
						<c:otherwise>
							<c:set var="checkval" value="" />
							<c:set var="termmarkID" value="-1" />
							<td><input name="gradesubjectcheck" type="checkbox"
								value="${gradesubject.gradeSubjectId},${studentclass.studentClassInfoId},${termmarkID}"
								<c:out value="${checkval}"></c:out>></td>

						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tr>
		</c:forEach>

	</table>
	</div>
	</div>
	<div class="clearfix"></div>
	</div>

		<div id="btn_row" class="button_row">

		<div class="buttion_bar_type2">
		<sec:authorize access="hasAnyRole('Save Or Update Student Subject')">
		<input type="button"
			name="Check_All" value="<spring:message code="STUDENT.SUBJECT.UI.CHECK.ALL"/>" class="button"
			onClick="checkAll(document.form.gradesubjectcheck)">
			<c:if
			test="${(classGradeId ne null) and (year ne null)}">
			<input type="hidden" name="select" value="${classGradeId}">
			<input type="hidden" name="selecting" value="${year}">
		</c:if> <input type="button" value="<spring:message code="REF.UI.RESET"/>"
			onClick="document.form.action='searchStudentSubjectList.htm'; 
		    document.form.submit();
			document.getElementById('search_results').style.display=''; 
			document.getElementById('btn_row').style.display=''"
			class="button"> <input type="button" value="<spring:message code="REF.UI.SAVE"/>"
			onClick="document.form.action='saveorupdatestudentsubject.htm'; document.form.submit();"
			class="button"> <input type="button" value="<spring:message code="REF.UI.CANCEL"/>"
			class="button" onclick="document.location.href='studentSubject.htm'">


</sec:authorize>
		</div>

		<div class="clearfix"></div>
		</div>

</c:if></form>
</div>
</div>
<h:footer />
</body>
</html>





