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

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="strEscapeUtil" uri="/WEB-INF/stringEscapeUtil/"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><spring:message code="APPLICATION.NAME" />
</title>
<!--<link rel="shortcut icon" href="http://sstatic.net/stackoverflow/img/favicon.ico">
	Change tha Tab icon in web page -->
<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>
<script language="javascript" src="resources/js/main.js"></script>
<script language="javascript" src="resources/js/common.js"></script>

<script type="text/javascript">
	function deleteAppointmentNature(thisObj) {
		var elementWraper = $(thisObj).closest('.section_box');
		$(elementWraper).find('.basic_grid tr').removeClass('highlight');
		$(thisObj).parents('tr').addClass('highlight');
		$(elementWraper).find('.section_full_inside').hide();
		var ans = window
				.confirm('<spring:message code="REF.DELETE.CONFIRMATION"/>');
		if (ans) {
			
			document.appointmentNatureForm.action = 'manageDeleteAppointmentNature.htm';
			document.appointmentNatureForm.submit();
		} else {
			$(thisObj).parents('tr').removeClass('highlight');
		}
	}

	
	function saveOrUpdateAppointmentNature(thisValue) {

		setAddEditPane(thisValue, 'Save');
		document.appointmentNatureForm.action = "saveOrUpdateAppointmentNature.htm";
		document.appointmentNatureForm.submit();

	}
	function editAppointmentNature(thisValue, selectedValue, description) {
		setAddEditPane(thisValue, 'Edit');
		document.appointmentNatureForm.natureId.value = selectedValue;
		document.appointmentNatureForm.description.value = description;

	}

	
	function showArea() {
		$(document).ready(function() {
			$("#errmsgarea").hide();
		});
	}
	
	
</script>

</head>
<body>

<h:headerNew parentTabId="26" page="referenceModule.htm" />

	<div id="page_container">
		<div id="breadcrumb_area">
			<div id="breadcrumb">
				<ul>
					<li><a href="adminWelcome.htm"> <spring:message
								code="REF.UI.HOME" /> </a>&nbsp;&gt;&nbsp;</li>
					<li><a href="referenceModule.htm"><spring:message
								code="REF.UI.REFERENCE" /> </a>&nbsp;&gt;&nbsp;</li>
					<li><spring:message code="REF.UI.MANAGE.APPOINTMENTNATURE.TITLE" /></li>
				</ul>
			</div>
			<div class="help_link">
				<a href="#"
					onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/admin/manageAppointmentNatureHelp.html"/>','helpWindow',780,550)"><img
					src="resources/images/ico_help.png" width="20" height="20"
					align="absmiddle"><spring:message code="REF.UI.HELP"/></a>
			</div>
		</div>
		<div class="clearfix"></div>
		<h1>
			<spring:message code="REF.UI.MANAGE.APPOINTMENTNATURE.TITLE" />
		</h1>
		<div id="content_main">
			<form:form action="" method="post" commandName="appointmentNature" name="appointmentNatureForm">
				<div class="section_box">
					<div id="search_results">
						<div class="section_box_header">
							<h2><spring:message code="REF.UI.MANAGE.APPOINTMENTNATURE.APPOINTMENTNATURE.DETAILS"/></h2>
						</div>
						<div id="errmsgarea">
							<c:if test="${!(message == null)}">
								<div class="error">
									&nbsp;<label id="errormsg" class="required_sign"><c:out value="${message}" /> </label>
								</div>
							</c:if>
							<form:errors path="natureId" id="errormsg" class="required_sign" />
							<form:errors path="description" id="errormsg" class="required_sign" />
						</div>
						
						<div class="column_single">
							<table class="basic_grid" border="0" cellspacing="0"
								cellpadding="0">
								<tr>
									<th width="830"><spring:message code="REF.UI.MANAGE.APPOINTMENTNATURE.APPOINTMENTNATURE"/></th>
									<th width="78" class="right">
										 <sec:authorize access="hasRole('Add/Edit Appointment Nature')"> 
										<img src="resources/images/ico_new.gif" class="icon_new"
										onClick="showArea();setAddEditPane(this,'Add')
                  if (document.appointmentNatureForm.description.value != null) {
						 document.appointmentNatureForm.description.value='';
						 document.appointmentNatureForm.natureId.value='0';
					}
                  "
										width="18" height="20" title="<spring:message code="REF.UI.MANAGE.APPOINTMENTNATURE.CREATE.NEW"/>">
									</sec:authorize>
									</th>
									</tr>
									<c:choose>
									<c:when test="${not empty appointmentNatureList}">
										<c:forEach items="${appointmentNatureList}" var="appointmentNature"
											varStatus="status">
												<tr <c:if test="${selectedObj != null && (selectedObj.natureId == appointmentNature.natureId)}">class="highlight"</c:if>
												<c:choose>
	            									<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
	            									<c:when test="${status.count % 2 == 0}">class="even"</c:when>
	            								</c:choose>>
	            								<td width="830">${appointmentNature.description}
												</td>
												<td nowrap class="right">
												
												
												<sec:authorize access="hasRole('Add/Edit Appointment Nature')">
												<img src="resources/images/ico_edit.gif" title="<spring:message code="REF.UI.MANAGE.APPOINTMENTNATURE.EDIT"/>"
										onClick="editAppointmentNature(this, '<c:out value="${appointmentNature.natureId}" />','<c:out value="${strEscapeUtil:escapeJS(appointmentNature.description)}" />');showArea();"
										width="18" height="20" class="icon">  
												</sec:authorize>
												<sec:authorize access="hasRole('Delete Appointment Nature')">
												<img src="resources/images/ico_delete.gif"
										onClick="document.appointmentNatureForm.natureId.value='${appointmentNature.natureId}';
                  deleteAppointmentNature(this);showArea();"
										title="<spring:message code="REF.UI.DELETE"/>" width="18" height="20" class="icon">
												</sec:authorize>	
												
													
											  </td>
												
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td width="830"><h5><spring:message code="REF.UI.MANAGE.NO.APPOINTMENTNATURE.FOUND"/></h5></td>
											<td nowrap class="right"></td>
										</tr>
									</c:otherwise>
									</c:choose>
									
									
							</table>
						</div>	
					</div>
					<div class="section_full_inside" style='display: ${showEditSection != null ?'block':'none'}'>
						<h3>
						<c:choose>
						<c:when test="${(selectedObj != null && (selectedObj.natureId > 0))}"> <spring:message code="REF.UI.MANAGE.APPOINTMENTNATURE.EDIT" /></c:when>
						<c:otherwise><spring:message code="REF.UI.MANAGE.APPOINTMENTNATURE.CREATE.NEW"/></c:otherwise>
						</c:choose>						
						</h3>
						<div class="box_border">
							<div class="row">
								<div class="float_left">
									<div class="lbl_lock">
										<span class="required_sign">*</span>
										<label><spring:message code="REF.UI.MANAGE.APPOINTMENTNATURE.APPOINTMENTNATURE"/> :</label>
									</div>
									<form:hidden path="natureId" />
									
									<form:input path="description" maxlength="45" />
								</div>
								<div class="float_right"></div>
							</div>
							
							
								<div class="buttion_bar_type1">
									<input onClick="saveOrUpdateAppointmentNature(this);" type="submit" class="button" value="<spring:message code="REF.UI.SAVE" />"><input
										type="button" class="button"
										onClick="setAddEditPane(this,'Cancel');showArea();" value="<spring:message code="REF.UI.CANCEL" />">
								</div>
							
							<div class="clearfix"></div>
						</div>
						</div>
					
				</div>
			</form:form>
		</div>
		</div>
		<h:footer />
		
		</body>
</html>