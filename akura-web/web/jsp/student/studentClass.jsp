<%--
    < �KURA, This application manages the daily activities of a Teacher and a Student of a School>

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
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><spring:message code='APPLICATION.NAME' /></title>
<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/list.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="resources/js/list.js"
	type="text/JavaScript"></script>
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<script language="javascript" src="resources/js/main.js"></script>
<script type="text/javascript">

	function formSubmit(){
		document.form.action = "searchStudentByGradeYear.htm";
		document.form.submit();
	}

	
	function submitList(form) {
		var result = new Array();
		var fromArrayList = new Array(); // array to keep the values to be removed.
		fromList = document.getElementById("FromList");
		if (form.ToList.length != 0) {
			for ( var i = 0; i < form.ToList.length; i++) {
				form.ToList.options[i].selected = "selected";
				if (form.ToList.options[i].selected) {
					result[i] = form.ToList.options[i].value + "-"
							+ form.ToList.options[i].text;
				}

			}

			// generates the list to be removed for the From list.
			for ( var index = 0; index < fromList.length; index++) {
				fromArrayList[index] = fromList.options[index].value + "-"
						+ fromList.options[index].text;

			}
			document.form.selectedArray.value = result;
			document.form.removedFromArray.value = fromArrayList;
		}
		document.form.action = "saveStudentList.htm";
		document.form.submit();
	}

	function cancel() {
		document.form.action = 'resetForm.htm';
		document.form.submit();
	}

	function disableYear() {
		var d = new Date();
		var curr_year = d.getFullYear();
		var fyear = curr_year + 1;
		var selectedYear = "${selectedNewYear}";
		document.form.NewSelectedYear.options[0] = new Option(
				"<spring:message code='OPT.PLEASE.SELECT' />", "");

		document.form.NewSelectedYear.options[1] = new Option(curr_year,
				curr_year);
		
	document.form.NewSelectedYear.options[2] = new Option(fyear, fyear);
		if (selectedYear != null) {
			if (curr_year == selectedYear) {
				document.form.NewSelectedYear.options[1].selected = "selected";
			}
			if (fyear == selectedYear) {
				document.form.NewSelectedYear.options[2].selected = "selected";
			}
		}

	}
</script>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
</head>
<body
	onload="disableYear(); <c:if test = "${selectedToList != null}">
 openMultipleSelect('${selectedToList}' , '${removedFromArray}', ',' , '-'); </c:if>">
	<h:headerNew parentTabId="10" page="studentClass.htm" />
	<div id="page_container">
		<div id="breadcrumb">
			<ul>
				<sec:authorize access="hasAnyRole('View Student Class search page')">
					<li><a href="adminWelcome.htm"><spring:message
								code='application.home' /></a>&nbsp;&gt;&nbsp;</li>
				</sec:authorize>
				<li><spring:message code='STUDENT.CLASS.TITLE' /></li>
			</ul>
		</div>
		<div class="help_link">
			<a href="#"
				onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/student/studentClassHelp.html"/>','helpWindow',780,550)"><img
				src="resources/images/ico_help.png" width="20" height="20"
				align="absmiddle"> <spring:message code="application.help" />
			</a>
		</div>

		<div class="clearfix"></div>
		<h1>
			<spring:message code='STUDENT.CLASS.TITLE' />
		</h1>

		<div class="error">
			<c:if test="${message != null}">
				<label id="errormsg" class="missing_required_error">${message}</label>
			</c:if>
		</div>
		<div class="error">
			<c:if test="${errorMessage != null}">
				<label id="errormsg" class="required_sign">${errorMessage}</label>
			</c:if>
		</div>
		<div class="error">
			<c:if test="${message_success != null}">
				<label id="errormsg" class="success_sign">${message_success}</label>
			</c:if>
		</div>

		<div id="content_main">
			<form action="#" method="POST" name="form" id="form">

				<div class="clearfix"></div>
				<div class="section_full_search">
					<div class="box_border">
						<p>
							<spring:message code='STUDENT.CLASS.SUB.TITLE' />
						</p>
						<div class="row">
							<div class="float_left">
								<div class="lbl_lock">
									<span class="required_sign">*</span> <label><spring:message
											code='STUDENT.CLASS.SELECT.GRADE' /></label>
								</div>
								<select name="SelectedGrade" id="SelectedGrade">
									<option value="">
										<spring:message code="application.drop.down" />
									</option>
									<c:choose>
										<c:when test="${NewStudents eq true}">
											<option value="NewStudents" selected="selected">
												<spring:message code='STUDENT.CLASS.NEW.STUDENTS' />
											</option>
										</c:when>
										<c:otherwise>
											<option value="NewStudents">
												<spring:message code='STUDENT.CLASS.NEW.STUDENTS' />
											</option>
										</c:otherwise>
									</c:choose>
									<c:forEach items="${GradeList}" var="gradeList"
										varStatus="status">
										<c:choose>
											<c:when test="${GradeId == gradeList.gradeId}">
												<option value="${gradeList.gradeId}" selected="selected">
													${gradeList.description}</option>
											</c:when>
											<c:otherwise>
												<option value="${gradeList.gradeId}">
													${gradeList.description}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
							<div class="float_left">
								<div class="lbl_lock">
									<span class="required_sign">*</span> <label><spring:message
											code='STUDENT.CLASS.SELECT.YEAR' /></label>
								</div>
								<select name="SelectedYear" id="SelectedYear">
									<option value="">
										<spring:message code='application.drop.down' />
									</option>
									<c:choose>
										<c:when test="${SelectedYear == year+1}">
											<option value="${year + 1}" selected="selected">${year
												+ 1}</option>
										</c:when>
										<c:otherwise>
											<option value="${year + 1}">${year + 1}</option>
										</c:otherwise>
									</c:choose>
									<c:forEach var="i" begin="0" end="14" varStatus="loop" step="1">
										<c:choose>
											<c:when test="${SelectedYear == year-i}">
												<option value="${year - i}" selected="selected">${year
													- i}</option>
											</c:when>
											<c:otherwise>
												<option value="${year - i}">${year - i}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
							<div class="float_right">

								<div class="buttion_bar_type1">
									<sec:authorize access="hasAnyRole('Search Student Class')">
										<input type="button"
											value="<spring:message code='STUDENT.CLASS.UI.SEARCH.STUDENT'/>"
											class="button" onclick="formSubmit();">
									</sec:authorize>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				<div class="section_box">
					<!--    <div class="section_box_header">
          <h2>Search Results</h2>
        </div> -->
					<div class="column_single">
						<div id="add_remove_list">
							<div style="width: 340px; float: left">

								<label><spring:message code='STUDENT.CLASS.STUDENTS.IN' />
									<c:out value="${Grade}" /> (<c:out value="${SelectedYear}" />):</label>
								<select name="FromList" size="15" multiple="multiple"
									id="FromList">
									<c:forEach items="${LastList}" var="lastList"
										varStatus="status">
										<c:choose>
											<c:when test="${lastList.key == 'Empty'}">
												<optgroup label="New" id="NewStudent">
													<c:forEach items="${lastList.value}" var="student"
														varStatus="status">
														<option id="New" value="${student[0]}">${student[1]}
															- ${student[2]}</option>
													</c:forEach>
												</optgroup>
											</c:when>
											<c:otherwise>
												<optgroup label="${lastList.key}" id="ExistingStudents"
													name="FromListGroup">
													<c:forEach items="${lastList.value}" var="student"
														varStatus="status">

														<option title="${student.value}"
															<c:choose><c:when test="${(student.value eq 'Future Year Class Allocation Exist') or (student.value eq 'Current Year Class Allocation Exist')}">class="student_allocated"</c:when><c:otherwise>class="student_notallocated"</c:otherwise></c:choose>
															id="${lastList.key}"
															value="${student.key.student.studentId}">${student.key.student.nameWtInitials}
															- ${student.key.student.admissionNo}</option>
													</c:forEach>
												</optgroup>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
							<div id="selected_list" class="controller">
								<input type="button" class="button" name="Button" value="&gt;"
									onClick="add()"><br> <br> <input
									type="button" class="button" name="Button" value="&lt;"
									onClick="remove()">
							</div>
							<input type="hidden" name="selectedArray" /> <input
								type="hidden" name="removedFromArray" />
							<div style="width: 340px; float: left">
								<span class="required_sign">*</span> <label><spring:message
										code='STUDENT.CLASS.SELECTED.STUDENT' /></label> <select
									name="ToList" size="15" multiple="multiple" id="ToList">

								</select>
							</div>
							<div class="extra_box">
								<span class="required_sign">*</span> <label><spring:message
										code='STUDENT.CLASS.ASSIGN.TO.CLASS' /></label>
								<div class="row">
									<select name="NewSelectedGrade" id="NewSelectedGrade">
										<option value="">
											<spring:message code="application.drop.down" />
										</option>
										<c:forEach items="${ClassGradeList}" var="clsGradeLst"
											varStatus="status">
											<option value="${clsGradeLst.classGradeId}"
												<c:if test = "${NewSelectedGrade != null && NewSelectedGrade == clsGradeLst.classGradeId}">
		selected = "selected"</c:if>>${clsGradeLst.description}</option>
										</c:forEach>
									</select>
								</div>
								<div class="row">&nbsp;</div>
								<span class="required_sign">*</span> <label><spring:message
										code='STUDENT.CLASS.ASSIGN.TO.YEAR' /></label>
								<div class="row">
									<select name="NewSelectedYear" id="NewSelectedYear">
									</select>
								</div>
							</div>
							<div class="clearfix"></div>

						</div>
						<spring:message code='STUDENT.CLASS.NOTE' />
						<img src="resources/images/student_allocated.png">
						<spring:message code='STUDENT.CLASS.NOTE.ALLOCATION' />

						<c:if test="${SelectedYear != null}">
							<c:choose>
								<c:when test="${SelectedYear < year}">
									<c:out value="${year}" />
								</c:when>
								<c:otherwise>
									<c:out value="${year+1}" />
								</c:otherwise>
							</c:choose>
						</c:if>
					</div>
					<div class="clearfix"></div>
					<br>

				</div>
				<div class="button_row">
					<div class="buttion_bar_type2">
						<sec:authorize access="hasRole('Save Student Class')">
							<input type="button" value="<spring:message code='REF.UI.SAVE'/>"
								class="button" onclick="submitList(form)">
							<input type="button"
								value="<spring:message code='REF.UI.CANCEL'/>" class="button"
								onclick="cancel()">
						</sec:authorize>
					</div>
					<div class="clearfix"></div>
				</div>
			</form>
		</div>
	</div>
	<h:footer />
</body>
</html>
