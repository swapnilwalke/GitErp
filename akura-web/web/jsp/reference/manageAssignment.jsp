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

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE HTML>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><spring:message code="APPLICATION.NAME" /></title>
<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/jquery.ui.core.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/jquery.ui.theme.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/jquery.ui.datepicker.css" rel="stylesheet"
	type="text/css">

<script language="javascript" src="resources/js/main.js"></script>
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>

<script src="resources/js/jquery.ui.core.min.js"></script>
<script src="resources/js/jquery.ui.widget.min.js"></script>
<script src="resources/js/jquery.ui.datepicker.min.js"></script>

<script type="text/javascript">

function loadSubjects(selectedValue, comments) {

	var url = '<c:url value="/populateGradeSubjectList4.htm" />';

	$.ajax({
		url : url,
		data : ({
			'selectedValue' : selectedValue
		}),
		success : function(response) {

			var c = response;
			var a;

			a = c.split(",");

			var b;
			document.getElementById('selectSubject').innerHTML = '';
			if (comments == null) {

				var dropDownDiv = document.getElementById('selectSubject');
				var selector = document.createElement('select');
				selector.id = "selectSubjects";
				selector.name = "selectSubjects";
				selector.path = "subjectDescription";
				dropDownDiv.appendChild(selector);

				var option = document.createElement('option');
				option.value = '0';
				option
						.appendChild(document
								.createTextNode('<spring:message code="OPT.PLEASE.SELECT"/>'));
				selector.appendChild(option);

				for ( var i = 0; i < a.length; i++) {
					b = a[i].split("_");

					option = document.createElement('option');
					option.value = b[1];
					option.appendChild(document.createTextNode(b[0]));
					selector.appendChild(option);
				}

			}
			else {
				var newComment = comments;
				var dropDownDiv = document
				.getElementById('selectSubject');

		var selector = document.createElement('select');
		selector.id = 'selectSubjects';
		selector.name = 'selectSubjects';
		selector.path = "subjectDescription";
		dropDownDiv.appendChild(selector);

		var option = document.createElement('option');
		option.value = '0';
		option.appendChild(document
				.createTextNode('<spring:message code="OPT.PLEASE.SELECT"/>'));
		selector.appendChild(option);

				for ( var i = 0; i < a.length; i++) {
					b = a[i].split("_");

						option = document.createElement('option');
						option.value = b[1];
						if (newComment == b[0]) {
							option.selected = 'selected';
						}
					option.appendChild(document.createTextNode(b[0]));
					selector.appendChild(option);
				}

	}
		},
		error : function(transport) {
			alert("error");

		}
	});
}

function load(thisValue) {
	document.getElementById('selectGrade').value = document
			.getElementById('selectOptionGrade').value;

}

function addAssignment(thisValue) {
	setAddEditPane(thisValue, 'Add');
	document.assignmentForm.assignmentId.value = 0;
	document.getElementById('name').value = "";
	document.getElementById('description').value = "";
	document.assignmentForm.date.value = '';
	document.getElementById('selectGrade').value = document
	        .getElementById('selectOptionGrade').value;
	document.getElementById('selectSubjects').value= 0;


	if (assignmentForm.DropDownList.length != 0) {
		for ( var i = assignmentForm.DropDownList.length; i >= 0; i--) {

			assignmentForm.DropDownList.remove(i);
		}
	}

}

function updateAssignment(thisValue, name, date, description, selectedValue, isMarksVal, gradeVal, SubjectVal) {

	setAddEditPane(thisValue, 'Edit');

	document.assignmentForm.name.value = name;
	document.assignmentForm.date.value = date;
	document.assignmentForm.description.value = description;
	document.assignmentForm.assignmentId.value = selectedValue;
	document.assignmentForm.isMarks.value = isMarksVal;
	$('input[value='+ isMarksVal +']:radio').attr('checked','checked');
	document.getElementById('selectGrade').value = gradeVal;

	loadSubjects(gradeVal, SubjectVal);
}

$(document).ready(function () {
	$('input[value=true]:radio').attr('checked','checked');
});


function deleteAssignment(thisObj) {
	var elementWraper = $(thisObj).closest('.section_box');
	$(elementWraper).find('.basic_grid tr').removeClass('highlight');
	$(thisObj).parents('tr').addClass('highlight');
	$(elementWraper).find('.section_full_inside').hide();
	var ans = window.confirm('<spring:message code="REF.DELETE.CONFIRMATION"/>');
	if(ans){
		document.assignmentForm.action='manageDeleteAssignment.htm';
		document.assignmentForm.submit();
	}
	else {
		$(thisObj).parents('tr').removeClass('highlight');
	}
	}



	$(function() {
		$( "#date" ).datepicker({
			changeYear: true,
			changeMonth: true,
			dateFormat: 'yy-mm-dd',
			yearRange:"c-100:c+1",
			showOn: "button",
			buttonImage: "resources/images/calendar.jpg",
			buttonImageOnly: true,
			 minDate: new Date()
		});
	});

	function callOnLoadFun(thisValue)
	{

		loadSubjects(thisValue, null);
	}
	
	function showArea(){
		   $(document).ready(function() {
				$("#area").hide();
			});
	   }
	
	function loadTitle(thisValue){

        if (!(thisValue == null || thisValue == "")) {

                $(document).ready(function() {

                        $("#flag").parents('tr').addClass('highlight');

                        document.getElementById('editpanetitle').innerHTML = "<spring:message code="REF.UI.MANAGE.ASSIGNMENT.EDIT"/>";

                });             
   		}

	}
</script>
<head></head>
<body onload="<c:if test="${assignment.assignmentId != 0}">loadTitle('<c:out value="${selectedObj}"></c:out>');</c:if><c:if test="${selectedObjId != null}">callOnLoadFun('<c:out value="${selectedObjId}" />');</c:if>">

<h:headerNew parentTabId="26" page="referenceModule.htm" />

<div id="page_container">
<div id="breadcrumb_area">
<div id="breadcrumb">
<ul>
	<li><a href="adminWelcome.htm"><spring:message
		code="application.home" /></a>&nbsp;&gt;&nbsp;</li>
	<li><a href="referenceModule.htm"><spring:message
		code="REF.UI.REFERENCE" /></a>&nbsp;&gt;&nbsp;</li>
	<li><spring:message
		code="REF.UI.MANAGE.ASSIGNMENT.REFERENCE.TITLE" /></li>
</ul>
</div>
<div class="help_link"><a href="#"
	onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/admin/manageAssignmentHelp.html"/>','helpWindow',780,550)"><img
	src="resources/images/ico_help.png" width="20" height="20"
	align="absmiddle"> <spring:message code="application.help" /></a></div>
</div>
<div class="clearfix"></div>
<h1><spring:message code="REF.UI.MANAGE.ASSIGNMENT.TITLE" /></h1>
<div id="content_main"><form:form
	action="manageSaveOrUpdateAssignment.htm" method="POST"
	commandName="Wrapperassignment" name="assignmentForm">

	<input type="hidden" name="selectedValue" />
	<input type="hidden" name="selectGrade" />

	<div class="section_box">
	<div id="search_results">
	<div class="section_box_header">
	<h2><spring:message
		code="REF.UI.MANAGE.ASSIGNMENT.ASSIGNMENT.DETAILS" /></h2>
	</div>
	<div id="area">
	<label class="required_sign"> <c:if test="${message != null}">${message}</c:if>
	<form:errors path="gradeDescription" id="errormsg"
		cssClass="required_sign" /> <form:errors path="name" id="errormsg"
		cssClass="required_sign" /><br>
	</label>
	</div>
	<div class="column_single">
	<table class="basic_grid" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th><spring:message code="REF.UI.MANAGE.ASSIGNMENT.ASSIGNMENT" /></th>
			<th><spring:message code="REF.UI.MANAGE.ASSIGNMENT.GRADE" /></th>
			<th><spring:message code="REF.UI.MANAGE.ASSIGNMENT.SUBJECT" /></th>
			<th><spring:message
				code="REF.UI.MANAGE.ASSIGNMENT.SUBMISSION.DATE" /></th>

			<th width="78" class="right"><sec:authorize
				access="hasRole('Save/Update Manage Assignments')">
				<img src="resources/images/ico_new.gif" class="icon_new"
					onClick="showArea();addAssignment(this)" width="18" height="20"
					title="<spring:message code='REF.UI.MANAGE.ASSIGNMENT.ADD' />">
			</sec:authorize></th>
		</tr>
		<c:choose>
			<c:when test="${not empty assignmentList}">
				<c:forEach var="assignment" items="${assignmentList}" varStatus="status">
					
					 <tr
            <c:choose>
            	<c:when test="${(status.count) % 2 == 0}">
            		class="odd"
            	</c:when>
            	<c:otherwise>
            		class="even"
            	</c:otherwise>
            </c:choose>>
					<td <c:if test="${selectedObj.name == assignment.name }">
															id="flag" 
												</c:if>>${assignment.name}</td>
					<td>${assignment.gradeSubject.grade.description}</td>
					<td>${assignment.gradeSubject.subject.description}</td>
					<td>${assignment.date}</td>

					<td nowrap class="right"><sec:authorize
						access="hasRole('Save/Update Manage Assignments')">
						<img src="resources/images/ico_edit.gif"
							title="<spring:message code='REF.UI.MANAGE.ASSIGNMENT.EDIT' />"
							onClick="showArea();updateAssignment(this,'<c:out value="${assignment.name}" />',
						'<c:out value="${assignment.date}" />',
						'<c:out value="${assignment.description}" />',
						'<c:out value="${assignment.assignmentId}" />',
						'<c:out value="${assignment.isMarks}" />',
						'<c:out value="${assignment.gradeSubject.grade.gradeId}" />',
						'<c:out value="${assignment.gradeSubject.subject.description}" />'); "
							width="18" height="20" class="icon">
					</sec:authorize><sec:authorize access="hasRole('Delete Manage Assignments')">
						<img src="resources/images/ico_delete.gif"
							onClick="showArea();document.assignmentForm.assignmentId.value='${assignment.assignmentId}';
										                  deleteAssignment(this);"
							title="<spring:message code='REF.UI.DELETE' />" width="18"
							height="20" class="icon">
					</sec:authorize></td>

				</c:forEach>

			</c:when>
			<c:otherwise>
				<tr>
					<td>
					<h5><spring:message
						code="REF.UI.MANAGE.ASSIGNMENT.No.ASSIGNMENT.DATA" /></h5>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</c:otherwise>

		</c:choose>
	</table>
	</div>
	</div>
	<div class="section_full_inside" style='display: ${showEditSection != null ?'block':'none'}'>
	<h3 id="editpanetitle"><spring:message code="REF.UI.MANAGE.ASSIGNMENT.ADD.ASSIGNMENT" /></h3>
	<div class="box_border">

	<div class="row">
	<div class="float_left">
	<div class="lbl_lock"><span class="required_sign">*</span><label><spring:message
		code="REF.UI.MANAGE.ASSIGNMENT.GRADE.LABEL" /></label></div>
	<form:select id="selectGrade" path="gradeDescription"
		onchange="loadSubjects(this.value),null">
		<form:option value="0" id="selectOptionGrade">
			<spring:message code="OPT.PLEASE.SELECT" />
		</form:option>
		<form:options items="${gradeList}" itemLabel="description"
			itemValue="gradeId" />
	</form:select></div>

	<div class="float_right">
	<div class="lbl_lock"><span class="required_sign">*</span><label><spring:message
		code="REF.UI.MANAGE.ASSIGNMENT.SUBJECT.LABEL" /></label></div>
	<div id="selectSubject"></div>
	</div>

	</div>

	<div class="row">
	<div class="float_left">
	<div class="lbl_lock"><span class="required_sign">* </span><label><spring:message
		code="REF.UI.MANAGE.ASSIGNMENT.ASSIGNMENT.LABEl" /></label></div>
	<form:hidden path="assignmentId" /> <form:input path="name"
		maxlength="45" /></div>
	<div class="float_right"
		style="margin-right: 270px; * margin-right: 10px;">
	<div class="lbl_lock"><span class="required_sign">*</span> <label><spring:message
		code="REF.UI.MANAGE.ASSIGNMENT.SUBMISSION.DATE.LABEL" /></label></div>
	<div class="lbl_lock"><form:input id="date" cssClass="date_field"
		path="date" readonly="true" /></div>
	</div>

	</div>

	<div class="row">
	<div class="float_left">
	<div class="lbl_lock"><span class="required_sign">* </span><label><spring:message
		code="REF.UI.MANAGE.ASSIGNMENT.MARKSTYPE" /></label></div>
	<form:radiobutton path="isMarks" cssClass="radio_btn" value="true" /><spring:message
		code="REF.UI.MANAGE.ASSIGNMENT.MARKS" /> <form:radiobutton
		path="isMarks" cssClass="radio_btn" value="false" /><spring:message
		code="REF.UI.MANAGE.ASSIGNMENT.GRADING" /></div>
	<div class="float_right">
	<div class="lbl_lock"><label><spring:message
		code="REF.UI.MANAGE.ASSIGNMENT.DESCRIPTION" /></label></div>
	<form:textarea rows="0" cols="20" path="description" maxlength="250"/></div>
	</div>

	<div class="row">

	<div class="buttion_bar_type1"><input type="submit"
		class="button" value='<spring:message code="REF.UI.SAVE"/>'> <input
		type="button" class="button" onClick="showArea();setAddEditPane(this,'Cancel')"
		value='<spring:message code="REF.UI.CANCEL"/>'></div>
	</div>
	<div class="clearfix"></div>
	</div>
	</div>
	<div class="clearfix"></div>
	</div>
</form:form></div>
</div>
<h:footer />
</body>
</html>
