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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="strEscapeUtil" uri="/WEB-INF/stringEscapeUtil/"%>	
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="PRAGMA" content="NO-CACHE">
<title><spring:message code="APPLICATION.NAME" /></title>
<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>
<script language="javascript" src="resources/js/main.js"></script>

<!-- START CSS and JS -->

<link href="resources/css/jquery.ui.core.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/jquery.ui.theme.css" rel="stylesheet"
	type="text/css">

<script src="resources/js/jquery.ui.core.min.js"></script>
<script src="resources/js/jquery.ui.widget.min.js"></script>

<!-- END CSS and JS -->

<script type="text/javascript">
	
	function editStaffLeave(thisValue, staffLeaveId, staffId, fromDate, toDate, reason, 
			noOfDays, staffLeaveTypeId, appliedDate, dateType) {
		setAddEditPane(thisValue, 'Edit');
		document.showLeaveApprovalProcessForm.staffLeaveId.value =staffLeaveId;
		document.showLeaveApprovalProcessForm.staffId.value =staffId;
		document.showLeaveApprovalProcessForm.fromDate.value =fromDate;
		document.showLeaveApprovalProcessForm.toDate.value =toDate;
		document.showLeaveApprovalProcessForm.reason.value =reason;
		document.showLeaveApprovalProcessForm.noOfDays.value =noOfDays;
		document.showLeaveApprovalProcessForm.staffLeaveTypeId.value =staffLeaveTypeId;
		document.showLeaveApprovalProcessForm.appliedDate.value =appliedDate;
		document.showLeaveApprovalProcessForm.dateType.value =dateType;
		document.showLeaveApprovalProcessForm.staffLeaveStatusId.value ='0';
		document.showLeaveApprovalProcessForm.comment.value ='';

	}
	
</script>

</head>
<body>
	<h:headerNew parentTabId="1" page="leaveApprovalProcess.htm" />
	
	<div id="page_container">
	  <div id="breadcrumb_area">
	    <div id="breadcrumb">
	      <ul>
	        <li><a href="adminWelcome.htm"><spring:message code="application.home" /></a>&nbsp;&gt;&nbsp;</li>
	        <li><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS" /></li>
	      </ul>
	    </div>
	    <div class="help_link">
			<a href="#"
				onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/staff/staffLeaveApprovalHelp.html"/>','helpWindow',780,550)"><img
				src="resources/images/ico_help.png" width="20" height="20"
				align="absmiddle"> <spring:message code="application.help" />
			</a>
		</div>
	  </div>
	  <div class="clearfix"></div>
	  <h1><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS" /></h1>
	  <div id="content_main">
	    
		<form:form method="POST" commandName="staffLeave" name="showLeaveApprovalProcessForm">
		<div class="clearfix"></div>
		<div class="messageArea" style="padding-left:14px;">
					<label class="required_sign"> 
					<c:if test="${message != null}">${message}</c:if>
						<form:errors path="staffLeaveId" />
					</label>
					<label class="success_sign"> 
						<c:if test="${successMessage != null}">${successMessage}</c:if>
					</label><br>
					<label class="required_sign"> 
						<c:if test="${emailMessage != null}">${emailMessage}</c:if>
					</label>
		</div>	
		<div class="section_box">
		  <div id="search_results" >
	        <div class="section_box_header">
	          <h2><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS_TABLE_HEADER" /></h2>
	        </div>
	        <div class="column_single" >
	          <table class="basic_grid"  border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <th width="300"><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS.REG_NO"/></th>
	              <th width="600"><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS.STAFF_NAME"/></th>
	              <th width="275"><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS.FROM_DATE"/></th>
	              <th width="275"><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS.TO_DATE"/></th>			  
	              <th width="150"><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS.NO_OF_DAY"/></th>
	              <th width="400"><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS.LEAVE_TYPE"/></th>
				  <th width="600"><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS.REASON"/></th>
				  <th width="150"><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS.APPLIED_DATE"/></th>
	            </tr>
	            
	            <c:choose>
            		<c:when test="${not empty staffLeaveList}" >
            			<c:forEach items="${staffLeaveList}" var="staffLeaveObj" varStatus="status">
						<tr <c:if test="${editedStaffLeaveId != null && (editedStaffLeaveId == staffLeaveObj[3])}">class="highlight"</c:if>
							<c:choose>
		            			<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
		            			<c:when test="${status.count % 2 == 0}">class="even"</c:when>
		            		</c:choose>>
		            		<td>${staffLeaveObj[0]}</td>
							<td>${staffLeaveObj[12]}</td>
							<td>${staffLeaveObj[4]}</td>
							<td>${staffLeaveObj[5]}</td>
							<td>${staffLeaveObj[7]}</td>
							<td>${staffLeaveObj[11]}</td>
							<td>${staffLeaveObj[6]}</td>
							<td nowrap class="left">${staffLeaveObj[9]}
								 
									<img src="resources/images/icon_accept.png"
										onClick="clearMessages(); editStaffLeave(this, '<c:out value="${staffLeaveObj[2]}"/>', '<c:out value="${staffLeaveObj[3]}"/>',
										'<c:out value="${staffLeaveObj[4]}"/>', '<c:out value="${staffLeaveObj[5]}"/>',  '<c:out value="${strEscapeUtil:escapeJS(staffLeaveObj[6])}"/>', 
										'<c:out value="${staffLeaveObj[7]}"/>', '<c:out value="${staffLeaveObj[8]}"/>', '<c:out value="${staffLeaveObj[9]}"/>', '<c:out value="${staffLeaveObj[10]}"/>');"
										title="<spring:message code='STAFF.LEAVE_APPROVAL_PROCESS.APPROVE_REJECT_LEAVE'/>" width="18" height="20" class="icon">
										
							</td>
							</tr>
						</c:forEach>
            		</c:when>
	            	<c:otherwise>
            			<tr>
							<td width="830">
							<h5><spring:message code="staff.leave.noStasffLeave_found" /></h5>
							</td>
							<td nowrap class="right"></td>
							<td nowrap class="right"></td>
							<td nowrap class="right"></td>
							<td nowrap class="right"></td>
							<td nowrap class="right"></td>
							<td nowrap class="right"></td>
						</tr>
					</c:otherwise>
				</c:choose>
	          </table>
	        </div>
			</div>
			<div class="section_full_inside" style='display: ${editpane != null ?'block':'none'}'>
	          <h3><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS.APPROVE_REJECT_LEAVE"/></h3>
	          <div class="box_border">
			 
			  <div class="row">
	              <div class="float_left">
	                <div class="lbl_lock">
	                  <span class="required_sign">*</span><label><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS.LEAVE_STATUS"/></label>
	                </div>
	               <form:select path="staffLeaveStatusId" id="staffLeaveStatusId">
						<option value="0">
							<spring:message code="application.drop.down" />
						</option>
				   <form:options items="${leaveStatusList}" itemValue="staffLeaveStatusId" itemLabel="description" />
				  </form:select> 
	              </div>
				  <div class="float_right">
	                <div class="lbl_lock">
	                  <span class="required_sign">*</span><label><spring:message code="STAFF.LEAVE_APPROVAL_PROCESS.COMMENT"/></label>
	                </div>
	                <form:textarea path="comment" maxlength="150"/>
	                <form:hidden path="staffLeaveId" id="staffLeaveId"/>
	                <form:hidden path="staffId" id="staffId"/>
	                <form:hidden path="fromDate" id="fromDate"/>
	                <form:hidden path="toDate" id="toDate"/>
	                <form:hidden path="reason" id="reason"/>
	                <form:hidden path="noOfDays" id="noOfDays"/>
	                <form:hidden path="staffLeaveTypeId" id="staffLeaveTypeId"/>
	                <form:hidden path="appliedDate" id="appliedDate"/>
	                <form:hidden path="dateType" id="dateType"/>
	              </div>
	            </div>
				
				<div class="row">
	              <div class="buttion_bar_type1">
				    <input type="submit" class="button" onClick="saveStaffLeave(thisValue)" value="<spring:message code="REF.UI.SAVE"/>">
	                <input type="button" class="button" onClick="setAddEditPane(this,'Cancel'); clearMessages()" value="<spring:message code="REF.UI.CANCEL"/>">
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