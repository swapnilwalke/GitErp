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

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><spring:message code="APPLICATION.NAME"/></title>
<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>
<script language="javascript" src="resources/js/main.js"></script>
<script language="javascript" src="resources/js/common.js"></script>

<script type="text/javascript">
	var classSize = '<c:out value="${classSize}"/>';

	function addNew(thisValue) {
		setAddEditPane(thisValue, 'Add');
		if (document.gradeForm.description.value != null) {
			document.gradeForm.description.value = '';
		}
		if (document.gradeForm.selectedGrade.value != null) {
			document.gradeForm.selectedGrade.value = '';
		}

		for ( var x = 1; x <= classSize; x++) {
			document.getElementById('classId' + x).checked = false;
		}
	}

	function saveGrade(thisValue) {
		setAddEditPane(thisValue, 'Save');
		document.gradeForm.action = "saveOrUpdateGrade.htm";
		document.gradeForm.submit();
	}

	function updateGrade(thisValue, selectedValue, classes, schoolClassList) {
		setAddEditPane(thisValue, 'Edit');
		document.gradeForm.selectedGrade.value = selectedValue;
		document.gradeForm.description.value = selectedValue;

		for ( var x = 1; x <= classSize; x++) {
			var classLabelId = document.getElementById('classLabelId' + x).innerText || document.getElementById('classLabelId' + x).textContent;
			document.getElementById('classId' + x).checked = false;
			var newClass = classes.split(',');

			for ( var y = 0; y < newClass.length; y++) {

				if ((newClass[y] == classLabelId)) {
					document.getElementById('classId' + x).checked = true;
				}
			}
		}
	}

	function deleteGrade(thisValue, selectedValue) {
		var elementWraper = $(thisValue).closest('.section_box');
		$(elementWraper).find('.basic_grid tr').removeClass('highlight');

		document.gradeForm.selectedGrade.value = selectedValue;

		$(thisValue).parents('tr').addClass('highlight');
		$(elementWraper).find('.section_full_inside').hide();

		var ans = window
				.confirm('<spring:message code="REF.DELETE.CONFIRMATION" />')

		if (ans) {
			$(thisValue).parents('tr').hide();
			document.gradeForm.action = "manageDeleteGrade.htm";
			document.gradeForm.submit();
		} else {
			$(thisValue).parents('tr').removeClass('highlight');
		}
	}
	
	function showArea() {
		$(document).ready(function() {
			$("#area").hide();
		});
	}
	
	function load(thisVal) {
		if (thisVal != "") {
			$(document).ready(function() {			
				$("#flag").parents('tr').addClass('highlight');			
				document.getElementById('editpanetitle').innerHTML = "<spring:message code="REF.UI.MANAGE.GRADES.EDIT"/>";
			});		
		} 
	}
	
</script>
</head>
<body onLoad = "load('<c:out value="${selectedObjId}"></c:out>')" >

	<h:headerNew parentTabId="26" page="referenceModule.htm" />

	<div id="page_container">
		<div id="breadcrumb_area">
			<div id="breadcrumb">
				<ul>
					<li><a href="adminWelcome.htm"><spring:message code="REF.UI.HOME" /></a>&nbsp;&gt;&nbsp;</li>
					<li><a href="referenceModule.htm"><spring:message code="REF.UI.REFERENCE" /></a>&nbsp;&gt;&nbsp;</li>
					<li><spring:message code="REF.UI.MANAGE.GRADES.TITLE"/></li>
				</ul>
			</div>
			<div class="help_link"><a href="#" onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/admin/manageGradeHelp.html"/>','helpWindow',780,550)"><img src="resources/images/ico_help.png" width="20" height="20" align="absmiddle"> <spring:message code="REF.UI.HELP"/></a></div>
		</div>
		<div class="clearfix"></div>
		<h1><spring:message code="REF.UI.MANAGE.GRADES.TITLE"/></h1>
		<div id="content_main">
			<form:form method="POST" commandName="grade" action=""
				name="gradeForm">

				<input type="hidden" name="selectedGrade" />

				<div class="section_box">
					<div class="section_box_header">
						<h2><spring:message code="REF.UI.MANAGE.GRADES.DETAILS"/></h2>
					</div>
					<div id="area">
				
					<label class="required_sign">
							<c:if test="${message != null}">${message}</c:if>
						</label>
					
						
						<form:errors path="gradeId" cssClass="required_sign"/>
						<form:errors path="classGrades" cssClass="required_sign"/>
						<form:errors path="description" cssClass="required_sign"/>
					</div>
					<div class="column_single">
						<table class="basic_grid" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<th width="181"><spring:message code="REF.UI.MANAGE.GRADES.GRADE"/></th>
								<th width="676"><spring:message code="REF.UI.MANAGE.GRADES.CLASSES"/></th>
								<th width="51" align="right" valign="top" class="right">
								<sec:authorize access="hasRole('Add/Edit Grade')">
								<img
									src="resources/images/ico_new.gif" class="icon_new"
									onClick="addNew(this,showArea())" width="18" height="20"
									title="<spring:message code="REF.UI.MANAGE.GRADES.ADD"/>"></sec:authorize>
								</th>
							</tr>
							<c:choose>
								<c:when test="${not empty gradeMap}">
									<c:forEach items="${gradeMap}" var="grade" varStatus="status">
										<tr 
											<c:choose>
		            		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
		            		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
		            		</c:choose>>
											<td <c:if test="${selectedObjId == grade.key}">id="flag"</c:if>>${grade.key}</td>
											<td>${grade.value}</td>
											<td nowrap class="right">
											<sec:authorize access="hasRole('Add/Edit Grade')">
											<img
												src="resources/images/ico_edit.gif" title="<spring:message code="REF.UI.MANAGE.GRADES.EDIT"/>"
												onClick="updateGrade(this, '<c:out value="${grade.key}" />', '<c:out value="${grade.value}" />', '<c:out value="${schoolClassList}"/>',showArea());"
												width="18" height="20" class="icon"></sec:authorize>
												<sec:authorize access="hasRole('Delete Grade')">
												 <img
												src="resources/images/ico_delete.gif"
												onClick="deleteGrade(this, '<c:out value="${grade.key}" />' ,showArea());"
												title="<spring:message code="REF.UI.DELETE"/>" width="18" height="20" class="icon"></sec:authorize></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td width="830"><h5><spring:message code="REF.UI.MANAGE.NO.GRADES.FOUND"/></h5>
										</td>
										<td nowrap class="right"></td>
										<td></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
					</div>
					<div class="section_full_inside" style='display: ${editpane != null ?'block':'none'}'>					
					 <h3 id="editpanetitle"><spring:message code="REF.UI.MANAGE.GRADES.ADD" /></h3>
															
						<div class="box_border">
							<div class="row">
								<div class="float_left">
									<div class="lbl_lock">
										<span class="required_sign">*</span><label><spring:message code="REF.UI.MANAGE.GRADES.NAME"/>:</label>
									</div>
									<form:input path="description" 
										onkeypress="return disableEnterKey(event)" maxlength="16"/>
								</div>
								<div class="float_right">
									<div class="lbl_lock">
										<span class="required_sign">*</span><label><spring:message code="REF.UI.MANAGE.GRADES.CLASSES"/>:</label>
									</div>
									<div class="inline_checkboxes">
										<c:forEach items="${schoolClassList}" var="schoolClass"
											varStatus="status">
											<div>
												<input name="classId" id="classId${status.count}"
													type="checkbox" class="checkbox"
													value="${schoolClass.classId}"><label
													class="label_inline" id="classLabelId${status.count}">${schoolClass.description}</label>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="buttion_bar_type1">
									<input type="button" class="button" onClick="saveGrade(this,showArea())"
										value="<spring:message code="REF.UI.SAVE" />"> <input type="button" class="button"
										onClick="setAddEditPane(this,'Cancel',showArea())" value="<spring:message code="REF.UI.CANCEL" />">
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</form:form>
		</div>
	</div>
	<h:footer />
</body>
</html>
