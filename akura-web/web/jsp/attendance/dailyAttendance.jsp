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
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	
<script language="javascript" src="resources/js/main.js"></script>
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>

<script language="javascript">
	function checkAll(field) {
		
			for (i = 0; i < field.length; i++){
				field[i].checked = true;
			}
			field.checked = true;
		
	}
	function cancelSearch(thisValue) {

		document.form.action = "changeDateList.htm";
		if (thisValue.value != "") {
			document.form.submit();
		}
	}
	function reloadPage(thisValue) {

		document.form.action = "DailyAttendanceReload.htm";
		if (thisValue.value != "") {
			document.form.submit();
		}
	}
	
</script>

</head>
<body>
	<h:headerNew parentTabId="33" page="DailyAttendance.htm" />

	<div id="page_container">
		<div id="breadcrumb_area">
			<div id="breadcrumb">
			<sec:authorize access="hasRole('View Student Attendance Page')">
				<ul>
					<li><a href="adminWelcome.htm"><spring:message code="application.home"/></a>&nbsp;&gt;&nbsp;</li>
					<li><spring:message code="ATTENDANCE.DAILYATTENDANCE.TITLE"/></li>
				</ul>
			</sec:authorize>
			</div>
			<div class="help_link">
				<a href="#"
					onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/attendance/manualAttendanceHelp.html"/>','helpWindow',780,550)"><img
					src="resources/images/ico_help.png" width="20" height="20"
					align="absmiddle"> <spring:message code="application.help"/></a>
			</div>
		</div>
		<div class="clearfix"></div>
		<h1><spring:message code="ATTENDANCE.DAILYATTENDANCE.TITLE"/></h1>
		<div>
			<c:if test="${message != null}">
				<label class="missing_required_error">${message}</label>
			</c:if>
		</div>
		<div id="content_main">
			<form action="" method="post" name="form"><c:set
	var="gradeclassdescription" value="" />
	<c:set
	var="dateDescription" value="" />
	
				<div class="clearfix"></div>
				<div class="section_full_search">
					<div class="box_border">
						<p><spring:message code="ATTENDANCE.DAILYATTENDANCE.DESCRIPTION"/></p>
						<div class="row">
							<div class="float_left">
								<div class="lbl_lock">
									<div><span class="required_sign">*</span><label><spring:message code="ATTENDANCE.DAILYATTENDANCE.CLASS"/></label></div>
								</div>
								<select name="select" id="select" <c:if test="${(not empty studentList)}"> onchange="reloadPage(this)"</c:if>>
									<option value ="0"><spring:message code="application.drop.down"/></option>
									<c:forEach var="gradeclass" items="${gradeClassList}">
										<c:set var="testval" value="" />
										<c:if test="${gradeclass.classGradeId eq classGradeId}">
											<c:set var="testval" value="SELECTED" />
											<c:set var="gradeclassdescription"
												value="${gradeclass.description}" />
										</c:if>
										<option label="${gradeclass.description}"
											value="${gradeclass.classGradeId}"
											<c:out value="${testval}"></c:out>>
											${gradeclass.description}</option>
									</c:forEach>
								</select>
							</div>
							<div class="float_left">
								<div class="lbl_lock">
									<label><spring:message code="ATTENDANCE.DATE"/></label>
							</div>
							
							<select name="date" id="date" <c:if test="${(not empty studentList)}"> onchange="cancelSearch(this)"</c:if>>
									<c:forEach items="${dateList}" var="date" varStatus="status">								
										<option label="${date}" value="${date}" <c:if test="${currentDate == date}">selected="selected" <c:set var="dateDescription"
												value="${date}" /></c:if>>${date}</option>
									</c:forEach>
							</select>
							
							</div>
							<div class="float_right">
								<div class="buttion_bar_type1">
								<sec:authorize access="hasRole('View Student Attendance')">
									<input type="button" value="<spring:message code="REF.UI.SEARCH"/>"
										onClick="document.form.action='searchStudentAttendance.htm'; document.form.submit();document.getElementById('search_results').style.display=''; document.getElementById('btn_row').style.display=''"
										class="button">
								</sec:authorize>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				<c:if test="${(not empty studentList)}">
				<script language="javascript">
					$(document)
							.ready(
									function() {
										$("#tblFreezed tr")
												.each(
														function(index) {
															$("#tblScrool tr")
																	.eq(index)
																	.height(
																			$(
																					"#tblFreezed tr")
																					.eq(
																							index)
																					.innerHeight());
														});
									});
				</script>
				<div class="section_box" id="search_results">
					<div class="section_box_header">
						<h2>${gradeclassdescription} <spring:message code="ATTENDANCE.ATTENDANCE"/></h2>
					</div>
					<div class="column_single">
						<table id="tblFreezed" class="basic_grid basic_grid_freezed"
							style="width: 650px;" border="0" cellspacing="0">
							<tr>
								<th style="height: 34px; * height: 32px;"><spring:message code="ATTENDANCE.STUDENT.ADMISSION"/></th>
								<th style="height: 34px; * height: 32px;"><spring:message code="ATTENDANCE.STUDENT.NAME"/></th>
								<th style="height: 34px; * height: 32px; text-align: center;">${dateDescription}</th>
							</tr>
							<c:forEach var="studentclass" items="${studentList}"
								varStatus="status">
								<tr
									<c:choose>
					            		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
					            		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
					            	</c:choose>>
					            	<td class="left">${studentclass.student.admissionNo}</td>
									<td class="left">${studentclass.student.nameWtInitials}</td>
									<c:set var="checkval" value="false" />
									<c:if test="${not empty attendanceList}">
										<c:forEach var="attendanceList" items="${attendanceList}">
											<c:if test="${attendanceList.studentId eq studentclass.student.studentId}">
												<c:set var="checkval" value="true" />
												
											</c:if>
										</c:forEach>
									</c:if>
									<td style="text-align: center;"><input name="studentIdList" type="checkbox" value="${studentclass.student.studentId}" 
									<c:if test="${checkval eq true}">checked="checked"</c:if>></td>
								</tr>
							</c:forEach>
							<tfoot style="background: #C0C0C0;">
								<tr>
									<td class="left" style="font-weight:bold;" ><spring:message code="ATTENDANCE.TOTAL"/></td>
									<td></td>
									<td style="text-align: center; font-weight:bold;">${total}</td>
								</tr>
							</tfoot>
							
						</table>
						
					</div>
					<div class="clearfix"></div>
				</div>
				
				<div id="btn_row" class="button_row" >
				<sec:authorize access="hasRole('Save Student Attendance')">
					<div class="buttion_bar_type2">
						<input type="hidden" name="date" value="${date}">
						<input type="hidden" name="select" value="${gradeclass.classGradeId}">
						<input type="button" value="<spring:message code="REF.UI.SAVE"/>" class="button" onclick="document.form.action='saveorupdateStudentAttendance.htm'; document.form.submit();"> 
						<input type="button" value="<spring:message code="REF.UI.CHECK.ALL"/>" class="button" onclick="checkAll(document.form.studentIdList)">
						<input type="button" value="<spring:message code="REF.UI.CANCEL"/>" class="button" onclick="document.location.href='DailyAttendance.htm'">
					</div>
				</sec:authorize>
					<div class="clearfix"></div>
				</div>
			</c:if></form>
		</div>
	</div>
	<h:footer />
</body>
</html>