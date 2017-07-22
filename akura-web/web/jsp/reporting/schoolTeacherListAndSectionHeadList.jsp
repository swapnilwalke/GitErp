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

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

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
<link rel="shortcut icon" href="resources/images/favicon.ico" type="image/x-icon" />

<script language="javascript" src="resources/js/main.js"></script>
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript">


	function showCategoryList(thisValue){

		document.getElementById("staffCategory").style.display = "block";

		document.getElementById("gradeList").style.display = "none";

		document.getElementById("errorMessage").style.display = "none";

	}

	function hideLists(thisValue){

		document.getElementById("staffCategory").style.display = "none";

		document.getElementById("gradeList").style.display = "none";

		document.getElementById("errorMessage").style.display = "none";
	}

	function showGradeList(thisValue){

			document.getElementById("gradeList").style.display = "block";


	}

	function getGradeDescription(thisValue){

		var gradeValue = "${gradeList.isEmpty()!=true? (gradeList.get(thisValue).description):''}";
		document.TeacherSectionHeadListForm.gradeDescription.value = gradeValue;

	}

	function getCategoryDescription(thisValue){

		var categoryValue = "${staffCategoryList.isEmpty()!=true? (staffCategoryList.get(thisValue).description):''}";
		document.TeacherSectionHeadListForm.category.value = categoryValue;

	}

</script>

</head>
<body>
	<div id="page_container">
		<div id="breadcrumb_area">
			
			<div class="help_link"><a href="#" onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/reporting/customizedStaffGeneralReportHelp.html"/>','helpWindow',780,550)"><img src="resources/images/ico_help.png" width="20" height="20" align="absmiddle"> <spring:message code="application.help"/></a></div>
		</div>
		<div class="clearfix"></div>
		<h1><spring:message code="REPORT.STAFF.GENERAL.REPORTS"/></h1>
		<div id="content_main">
			<div class="clearfix"></div>

			<div class="section_box">
				<div class="section_box_header">
					<h2><spring:message code="REPORT.SCHOOL.TEACHER.LIST"/></h2>
				</div>

				<div class="section_full_inside">
					<div class="box_border">

			<table>

				<tr>
					<form:form action="customizedStaffGeneralReports.htm"
						commandName="SchoolTeacherAndSectionHeadList" method="POST"
						name="TeacherSectionHeadListForm">
						<tr><td><div id="errorMessage"><label class="required_sign" id="errorMessage"> <spring:bind path="SchoolTeacherAndSectionHeadList.*">
						<c:forEach items="${status.errorMessages}" var="error">
							<c:out value="${error}" />
							<br>
						</c:forEach>
					</spring:bind></label></div></td></tr>
						<td><label><spring:message code="REPORT.STAFF.SECTION.HEAD.REPORTTYPE"/></label></td>
						<tr>
						<td><label><form:radiobutton
							path="listType" value="1" label=""
							cssClass="radio_btn" onclick="showCategoryList(this.value);"/><spring:message code="REPORT.STAFF.SECTION.HEAD.STAFF.STAFFTYPE"/></label></td></tr><tr><td><label><form:radiobutton path="listType"
							value="2" label="" cssClass="radio_btn" onclick="hideLists(this.value);"/><spring:message code="REPORT.STAFF.SECTION.HEAD.STAFF.SECTIONTYPE"/></label></td></tr><tr><td><label><form:radiobutton path="listType"
							value="3" label="" cssClass="radio_btn" onclick="hideLists(this.value);showGradeList(this.value);"/><spring:message code="REPORT.STAFF.SECTION.HEAD.STAFF.CLASSTEACHER"/></label></td>
						</tr>
						<tr>
				<td>
				<div class="row" id="staffCategory" name="staffCategory" style="display: none;">
				<div class="frmlabel"><span class="required_sign">*</span><label><spring:message code="REPORT.STAFF.SECTION.HEAD.STAFF.CATEGORY"/></label></div>
				<div class="frmvalue"><form:select path="catogaryID" name="catogaryID" onchange="getCategoryDescription(this.value);">
					<form:option value="0" label=""> <spring:message code="application.drop.down"/></form:option>
					<form:option value="-1" label=""> <spring:message code="REPORT.STAFF.SECTION.HEAD.STAFF.CATEGORY.ALL"/></form:option>
					<c:if test="${staffCategoryList ne null}">
					<c:forEach var="categories" items="${staffCategoryList}">
					<form:option value="${categories.catogaryID}"
						label="${categories.description}" />
						</c:forEach>
					</c:if>
				</form:select></div>
				</div>
				</td>
			</tr>
				<tr>
				<td>
				<div class="row" id="gradeList" name="gradeList" style="display: none;">
				<div class="frmlabel"><span class="required_sign">*</span><label><spring:message code="REPORT.STAFF.SECTION.HEAD.STAFF.GRADE"/></label></div>
				<div class="frmvalue"><form:select path="gradeId" name="gradeId" onchange="getGradeDescription(this.value);">
					<form:option value="0" label="" > <spring:message code="application.drop.down"/></form:option>
					<c:if test="${gradeList ne null}">
					<c:forEach var="grades" items="${gradeList}">
					<form:option value="${grades.gradeId}"
						label="${grades.description}" />
					</c:forEach>
					</c:if>
				</form:select></div>
				</div>
				</td>
			</tr>
						<tr>
							<td><form:hidden path="category" name ="category" id="category"></form:hidden></td>
						</tr>

						<tr>
							<td><form:hidden path="gradeDescription" name ="gradeDescription" id="gradeDescription"></form:hidden></td>
						</tr>


						<tr>
							<td colspan="2"><input type="submit" class="button"
								value="<spring:message code="REPORT.UI.GENERATE.REPORT"/>"></td>
						</tr>
					</form:form>
				</tr>
			</table>
				</div>
				</div>

				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<h:footer/>
</body>
</html>