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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags" %>
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
<script language="javascript" src="resources/js/main.js"></script>
<script type="text/javascript">

function searchAssignmentMark() {
	document.form.action='searchStudentAssignmentMarks.htm';
	document.form.submit();
}

function selectClass(thisValue) {
	document.form.selectedClass.value = thisValue;
}

function selectAssignment(thisValue) {
	document.form.selectedAssignment.value = thisValue.value;
}

function saveStudentAssignmentMarks() {
	document.form.action='saveStudentAssignmentMarks.htm';
	document.form.submit();
}

function updateStudentAssignmentMarks() {
	document.form.action='updateStudentAssignmentMarks.htm';
	document.form.submit();
}

function loadAssignments(selectedValue, assignment) {

	var url = '<c:url value="/populateAssignmentList.htm" />';

	$.ajax({
		url : url,
		data : ({
			'classGradeId' : selectedValue
		}),
		success : function(response) {

			var responseValue = response;
			var responseArray;
			
			// if the response array is lager than 0 then split it.
			if(responseValue.length > 0){
			responseArray = responseValue.split(",");
			}
			var arrayObject;
			document.getElementById('selectAssignment').innerHTML = '';
			if (assignment == null) {

				var dropDownDiv = document.getElementById('selectAssignment');
				var selector = document.createElement('select');
				selector.id = "selectedAssignment";
				selector.name = "selectedAssignment";
				dropDownDiv.appendChild(selector);

				var option = document.createElement('option');
				option.value = '0';
				option.appendChild(document.createTextNode("<spring:message code="OPT.PLEASE.SELECT"/>"));
				selector.appendChild(option);

				if (responseArray.length > 0) {

					for ( var i = 0; i < responseArray.length; i++) {
						arrayObject = responseArray[i].split("_");

						if (arrayObject[0] != "") {
							option = document.createElement('option');
							option.value = arrayObject[0];
							option.appendChild(document.createTextNode(arrayObject[2]+"-"+arrayObject[1]));
							selector.appendChild(option);
						}
					}
				}
			} else {
				var newAssignment = "${selectedAssignmentKey}";
				
				var dropDownDiv = document.getElementById('selectAssignment');

				var selector = document.createElement('select');
				selector.id = 'selectedAssignment';
				selector.name = 'selectedAssignment';
				dropDownDiv.appendChild(selector);

				var option = document.createElement('option');
				option.value = '0';
				option.appendChild(document.createTextNode("<spring:message code="OPT.PLEASE.SELECT"/>"));
				selector.appendChild(option);
				for ( var i = 0; i < responseArray.length; i++) {
					arrayObject = responseArray[i].split("_");

						option = document.createElement('option');
						option.value = arrayObject[0];
						if(newAssignment != null){
						if (newAssignment == parseInt(arrayObject[0] , 10)) {
							option.selected = 'selected';
						}}
					option.appendChild(document.createTextNode(arrayObject[2]+"-"+arrayObject[1]));
					selector.appendChild(option);
				}

			}
		},

		error : function(transport) {
			alert("error");

		}
	});
}

</script>

</head>
<body onload="
<c:if test = "${selectedClass != null}">
selectClass(${selectedClass});
	<c:if test = "${selectedAssignmentKey != null}">
		loadAssignments(${selectedClass} , ${selectedAssignmentKey});
	</c:if>
</c:if>"></body>

<h:headerNew parentTabId="10" page="studentAssignmentMarks.htm" />

<div id="page_container">
  <div id="breadcrumb">
    <ul>
	    <sec:authorize access="hasAnyRole('View Student Assignment Mark Entry page')">
			<li><a href="adminWelcome.htm"><spring:message code="application.home"/></a>&nbsp;&gt;&nbsp;</li>
		</sec:authorize>
		<li><spring:message code="STUDENT.ASSIGNMENT.MARK.ADD"/></li>
    </ul>
  </div>
  <div class="help_link"><a href="#" onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/student/studentAssignmentMarksHelp.html"/>','helpWindow',780,550)"><img src="resources/images/ico_help.png" width="20" height="20" align="absmiddle"> <spring:message code="application.help"/></a></div>
  <div class="clearfix"></div>
  <h1><spring:message code="STUDENT.ASSIGNMENT.MARK.ADD"/></h1>
  	<div>
  		<c:if test="${message != null}">
        	<label class="missing_required_error">${message}</label>
    	</c:if>
   	</div>  	
  <div id="content_main">
    <form action="" method="post" name="form">

      <div class="clearfix"></div>
	  <div class="section_full_search">
          <div class="box_border">
            <div class="row">
             <div class="float_left">
                <div class="lbl_lock">
                  <span class="required_sign">*</span><label><spring:message code="STUDENT.ASSIGNMENT.MARK.SELECT.CLASS"/></label>
                </div><select name="selectedClass" id="selectedClass" size=""
                onchange="javascript:selectClass(this.value);
                javascript:loadAssignments(this.value, null);">
                <c:set var="printClass" value="" />
                <c:set var="printClassId" value="" />
           			<option value="0"> <spring:message code="OPT.PLEASE.SELECT"/> </option>
                  <c:forEach items="${classGradeList}" var="classGrade">
                  	<option value="${classGrade.classGradeId}"
                  	
                  	<c:if test="${selectedClass != null && classGrade.classGradeId eq selectedClass}">
                  	
                  	<c:set var="printClass" value="${classGrade.description}" /> 
                  	 <c:set var="printClassGradeId" value="${classGrade.classGradeId}" />
                  	 selected="selected"
                  	 </c:if> >
                  	 ${classGrade.description}</option>
                  </c:forEach>
                </select>
                </div>
			  <div class="float_left">
                <div class="lbl_lock">
                  <span class="required_sign">*</span><label><spring:message code="STUDENT.ASSIGNMENT.MARK.SELECT.ASSIGNMENT"/></label>
                </div>
                <div id="selectAssignment"></div>

              </div>
			<sec:authorize access="hasRole('Search Student Assignment Marks')">
			  <div class="float_right">

                 <div class="buttion_bar_type1">
                <input type="button" value="<spring:message code="REF.UI.SEARCH"/>" onClick="searchAssignmentMark();document.getElementById('search_results').style.visibility='visible'; document.getElementById('btn_row').style.display=''" class="button">
              </div>
              </div>
              </sec:authorize>
            </div>
            <div class="clearfix"></div>
          </div>
      </div>
      <c:if test="${not empty studentNewAssignmentMarkList}">
      	<script type="text/javascript">
      		var thisValue = document.form.selectedClass.value;
      		loadAssignments(thisValue, "${assignment.gradeSubject.subject.description}-${assignment.name}");
      </script>
      </script>
      <div class="section_box" id="search_results">
        <div class="section_box_header">
          <h2>${printClass} (${assignment.gradeSubject.subject.description}-${assignment.name}) Marks</h2>
        </div>
        <div class="column_single" >
		  <table id="tblScrool" class="basic_grid marksGrid"  border="0" cellspacing="1">
            <tr>
              <th><spring:message code="STUDENT.ASSIGNMENT.MARK.STUDENT.NAME"/></th>
              <th>
              <c:choose>
              <c:when test="${assignment.isMarks eq true}">
    	       	<spring:message code="STUDENT.ASSIGNMENT.MARK.MARKS" />
              </c:when>
              <c:when test="${assignment.isMarks eq false}">
	             <spring:message code="STUDENT.ASSIGNMENT.MARK.GRADE" />
              </c:when>
              </c:choose>
              </th>
            </tr>  
            <c:forEach items="${studentNewAssignmentMarkList}" var="studentAssignmentMark" varStatus="status">
			
	            <tr <c:choose>
            		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
            		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
            		</c:choose>
			<c:forEach items="${studentClassInfoList}" var="studentClassInfo" varStatus="classInfoStatus">
				<c:choose>
				<c:when test="${studentAssignmentMark.studentClassInfoId == studentClassInfo.studentClassInfoId && studentClassInfo.student.statusId == 3}">style="background-color: #8B8B83" title ="Suspended student"</c:when>
				</c:choose>
			</c:forEach>>
	              <td class="left"><p class="wWrap">${studentAssignmentMark.nameWithInit}</p></td>
           		<td><input type="text" name="assignmentMarkValue"><c:if test="${assignment.isMarks eq true}">%</c:if></td>
	            </tr>
            </c:forEach>
          </table>
        </div>
        <div class="clearfix"></div>
      </div>
      <sec:authorize access="hasRole('Save Student Assignment')">
	  <div id="btn_row" class="button_row">
	  <div class="buttion_bar_type2" >
	  <input type="button" value="<spring:message code="REF.UI.RESET"/>" class="button"
	  onClick="document.form.action='searchStudentAssignmentMarks.htm'; document.form.submit();document.getElementById('search_results').style.display=''; document.getElementById('btn_row').style.display=''">
                <input type="button" value="<spring:message code="REF.UI.SAVE"/>" class="button" onclick="saveStudentAssignmentMarks();">
				<input type="button" value="<spring:message code="REF.UI.CANCEL"/>" class="button" onclick="document.location.href='studentAssignmentMarks.htm'">
              </div>
			  <div class="clearfix"></div>
			  </div>
			  </sec:authorize>
	</c:if>

	<c:if test="${not empty studentOldAssignmentMarkList}">
	<script type="text/javascript">
      var thisValue = document.form.selectedClass.value;
      loadAssignments(thisValue, "${assignment.gradeSubject.subject.description}-${assignment.name}");
      </script>
      <div class="section_box" id="search_results">
        <div class="section_box_header">
          <h2>${printClass} (${assignment.gradeSubject.subject.description}-${assignment.name}) Marks</h2>
        </div>
        <div class="column_single" >
		  <table id="tblScrool" class="basic_grid marksGrid"  border="0" cellspacing="1">
            <tr>
              <th><spring:message code="STUDENT.ASSIGNMENT.MARK.STUDENT.NAME"/></th>
              <th>
              <c:choose>
              <c:when test="${assignment.isMarks eq true}">
    	       	<spring:message code="STUDENT.ASSIGNMENT.MARK.MARKS" />
              </c:when>
              <c:when test="${assignment.isMarks eq false}">
	             <spring:message code="STUDENT.ASSIGNMENT.MARK.GRADE" />
              </c:when>
              </c:choose>
              </th>
            </tr>
            <c:forEach items="${studentOldAssignmentMarkList}" var="studentOldAssignmentMark" varStatus="status">
            	<input type="hidden" name="assignmentMarkKey" value="${studentOldAssignmentMark.key.studentAssignmentMarksId}">
	            <tr <c:choose>
            		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
            		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
            		</c:choose>
            	<c:forEach items="${studentClassInfoList}" var="studentClassInfo" varStatus="classInfoStatus">
					<c:choose>
					<c:when test="${studentOldAssignmentMark.key.studentClassInfoId == studentClassInfo.studentClassInfoId && 
					studentClassInfo.student.statusId == 3}">style="background-color: #8B8B83" title ="Suspended student"</c:when>
					</c:choose>
				</c:forEach>>
	              <td class="left">${studentOldAssignmentMark.value.split("_")[0]}</td>
           		<td><input type="text" name="assignmentMarkValue" <c:if test="${studentOldAssignmentMark.value.split('_')[1] ne 'null'}">
           		value="${studentOldAssignmentMark.value.split('_')[1]}" </c:if>
           		<c:if test="${studentOldAssignmentMark.value.split('_')[1] eq 'null'}">
           		value = ""
           		</c:if>>
           		<c:if test="${assignment.isMarks eq true}">%</c:if></td>
	            </tr>
            </c:forEach>
          </table>
        </div>
        <div class="clearfix"></div>
      </div>
	<sec:authorize access="hasRole('Update Student Assignment Marks')">
	  <div id="btn_row" class="button_row">
	  <div class="buttion_bar_type2" >
	  <input type="reset" value="<spring:message code="REF.UI.RESET"/>" class="button"
	  onClick="document.form.action='searchStudentAssignmentMarks.htm'; document.form.submit();document.getElementById('search_results').style.display=''; document.getElementById('btn_row').style.display=''">
                <input type="button" value="<spring:message code="REF.UI.SAVE"/>" class="button" onclick="updateStudentAssignmentMarks();">
				<input type="button" value="<spring:message code="REF.UI.CANCEL"/>" class="button" onclick="document.location.href='studentAssignmentMarks.htm'">
              </div>
			  <div class="clearfix"></div>
			  </div>
	</sec:authorize>
	</c:if>

	<input type="hidden" name="assignmentId" value="${assignment.assignmentId}">
	<input type="hidden" name="classGradeId" value="${printClassGradeId}">
	<input type="hidden" name="marksType" value="${assignment.isMarks}">

    </form>
  </div>
</div>
<h:footer />
</body>
</html>
