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
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><spring:message code="APPLICATION.NAME" /></title>
<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>
<script language="javascript" src="resources/js/main.js"></script>
<script language="javascript" src="resources/js/common.js"></script>

<!-- Calender controll CSS and JS -->

<link href="resources/css/jquery.ui.core.css" rel="stylesheet" type="text/css">
<link href="resources/css/jquery.ui.theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/jquery.ui.datepicker.css" rel="stylesheet"
	type="text/css">

<script src="resources/js/jquery.ui.core.min.js"></script>
<script src="resources/js/jquery.ui.widget.min.js"></script>
<script src="resources/js/jquery.ui.datepicker.min.js"></script>


<script>

var date = new Date();
var currentMonth = date.getMonth();
var currentDate = date.getDate();
var currentYear = date.getFullYear();
	$(function() {
		var dates = $("#LateAttendanceFromDate, #LateAttendanceToDate")
				.datepicker(
						{
							defaultDate : "+1w",
							changeYear : true,
							changeMonth : true,
							dateFormat: 'yy-mm-dd',
							numberOfMonths : 1,
							showOn : "button",
							buttonImage : "resources/images/calendar.jpg",
							buttonImageOnly : true,
							maxDate : new Date(currentYear, currentMonth,
									currentDate),
							onSelect : function(selectedDate) {
								var option = this.id == "LateAttendanceFromDate" ? "minDate"
										: "maxDate", instance = $(this).data(
										"datepicker"), date = $.datepicker
										.parseDate(
												instance.settings.dateFormat
														|| $.datepicker._defaults.dateFormat,
												selectedDate, instance.settings);
								dates.not(this).datepicker("option",
	option,
										date);
								displayTodate();
							}

						});
	});
</script>

<script type="text/javascript">
function showMoreDetails(audit){

	var url = "showMoreDetails.htm?auditDetailId="+audit;
	window.open(url, 'myPop1','width=750,height=400,scrollbars=yes');

}

function previousOrNext(value){
	pageSize = 10;

	if(value == "next"){
		startFrom = parseInt(${startFrom}) + pageSize;
		document.getElementById('startFrom').value = startFrom + "";
	}else if(value == "previous"){
		startFrom = parseInt(${startFrom}) - pageSize;
		document.getElementById('startFrom').value = startFrom + "";
	}

	document.getElementById('actionType').value = value;
	document.staffSearch.submit();
}

function displayTodate(){
	fromDate = document.getElementById('LateAttendanceFromDate').value;

	if(fromDate == null || fromDate == ""){

		$("#todate").css("display", "none");

	} else {
		$("#todate").css("display", "inline");

	}


}


</script>
</head>
<body >
<h:headerNew parentTabId="26" page="referenceModule.htm" />
<div id="page_container">
	<div id="breadcrumb_area">
	  <div id="breadcrumb">
		<ul>

				<li><a href="adminWelcome.htm"><spring:message code="REF.UI.HOME" /></a>&nbsp;&gt;&nbsp;</li>
				<li><a href="referenceModule.htm"><spring:message code="REF.UI.REFERENCE" /></a>&nbsp;&gt;&nbsp;</li>
				<li><spring:message code="REF.UI.AUDIT.DETAILS.TITLE" /></li>

		</ul>
	  </div>
	  <div class="help_link">
		<a href="#"
		onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/admin/AuditDetailsHelp.html"/>','helpWindow',780,550)"><img
		src="resources/images/ico_help.png" width="20" height="20"
		align="absmiddle"><spring:message code="REF.UI.HELP"/></a>
	  </div>
	</div>
  	<div class="clearfix"></div>
  	<h1><spring:message code="REF.UI.AUDIT.DETAILS.TITLE" /></h1>
  	<div id="content_main">
    <form:form action="" method="POST" commandName="businessAudit" name="form">
      <div class="clearfix"></div>
      <div class="section_full_search">
        <div class="box_border">
        <c:if test="${message != null}">
			<div>
				<label class="required_sign">${message}</label>
			</div>
		</c:if>
		<div>
			<form:errors path="*" class="required_sign" />
		</div>
          <div class="row">
            <div class="float_left">
                <div class="lbl_lock">
					<label><spring:message code="REF.UI.AUDIT.DETAILS.USER.LABEL"/></label>
                </div>
				<form:select name="user" id="selectedUser" path="userLogin.username">
					<option value ="0"><spring:message code="application.drop.down"/></option>
					<c:forEach items="${userLoginList}" var="userLoginList" >
						<option label="${userLoginList.username}" value="${userLoginList.username}"
							<c:if test="${selectedUser == userLoginList.username}">selected="selected"</c:if>>${userLoginList.username}</option>
					</c:forEach>
				</form:select>

            </div>
			<div class="float_left">
				<div class="lbl_lock">
					<label><spring:message code="REF.UI.AUDIT.DETAILS.BUSINESS.FUN.LABEL"/></label>
				</div>
				<form:input type="text" path="businessFunction"/>
            </div>
			<div class="float_left">
				<div class="lbl_lock">
					<label><spring:message code="REF.UI.AUDIT.DETAILS.FROM.DATE"/></label>
				</div>
				<form:input id="LateAttendanceFromDate" class="date_field" path="fromDate" onchange="displayTodate(); document.getElementById('LateAttendanceToDate').value = '';"/>
            </div>


          </div>
		  <div class="row">
		  <div class="float_left">
              <div class="lbl_lock">
                <label><spring:message code="REF.UI.AUDIT.DETAILS.EVENT.TYPE.LABEL"/></label>
              </div>
               <form:select name="eventType" id="selectedEventType" path="eventType.description">
					<option value ="0"><spring:message code="application.drop.down"/></option>
					<c:forEach items="${eventTypeList}" var="eventTypeList" >
						<option label="${eventTypeList.description}" value="${eventTypeList.description}"
							<c:if test="${selectedEvent == eventTypeList.description}">selected="selected"</c:if>>${eventTypeList.description}</option>
					</c:forEach>
				</form:select>
            </div>

			<div class="float_left">
				<div class="lbl_lock">
					<label><spring:message code="REF.UI.AUDIT.DETAILS.MODULE.LABEL"/></label>
				</div>
				<form:input type="text" path="module"/>
            </div>

			<div class="float_left" id ="todate" <c:if test="${(empty businessAudit.toDate)}">style="display: none;" </c:if> >
				<div class="lbl_lock">
					<label><spring:message code="REF.UI.AUDIT.DETAILS.TO.DATE"/></label>
				</div>
				<form:input id="LateAttendanceToDate" class="date_field" path="toDate" />
            </div>

			<div class="float_right">
              <div class="buttion_bar_type1">

              	<sec:authorize access="hasRole('Search Audit Details')">

                	<input type="button" value="<spring:message code="REF.UI.SEARCH" />" onClick="document.form.action='searchAuditDetails.htm'; document.form.submit();document.getElementById('search_results').style.visibility='visible'; " class="button"/>

              	</sec:authorize>

              </div>
            </div>
          </div>

          <div class="clearfix"></div>
        </div>
      </div>
      <c:if test="${(not empty businessAuditList)}">
	  <script language="javascript">
	  $(document).ready(function() {
	    $("#tblFreezed tr").each(function(index){
	     $("#tblScrool tr").eq(index).height($("#tblFreezed tr").eq(index).innerHeight());
	    });
	  });
	  </script>


      <div class="section_box" id="search_results">
        <div class="section_box_header">
          <h2><spring:message code="REF.UI.AUDIT.DETAILS.TITLE" /></h2>
        </div>
        <div class="column_single" style="overflow-x:scroll; ">

  		  <table id="tblFreezed" class="basic_grid basic_grid_freezed" style="width:900px;" border="0" cellspacing="0" >
            <tr>
			  <th style="height: 34px; * height: 32px;"><spring:message code="REF.UI.AUDIT.DETAILS.DATE"/></th>
			  <th style="height: 34px; * height: 32px;"><spring:message code="REF.UI.AUDIT.DETAILS.USER"/></th>
			  <th style="height: 34px; * height: 32px;"><spring:message code="REF.UI.AUDIT.DETAILS.BUSINESS.FUN"/></th>
			  <th style="height: 34px; * height: 32px;"><spring:message code="REF.UI.AUDIT.DETAILS.MODULE"/></th>
			  <th style="height: 34px; * height: 32px;"><spring:message code="REF.UI.AUDIT.DETAILS.EVENT.TYPE"/></th>

			  <th width="130" class="right">
              		<c:if test="${numberOfRecords == 0}">${startFrom}</c:if>
              		<c:if test="${numberOfRecords > 0}">${startFrom+1}</c:if>
              		 - ${maxNumber}  <spring:message code="PAGINATION.OF"/> ${numberOfRecords}<br/><input
            		type="image" class="button" width="15" height="15" src="resources/images/leftSideArrow.png" title="<spring:message code="REF.UI.MANAGE.SYSTEM.USERS.PREVIOUS"/>"
            		onclick="previousOrNext('previous')" <c:if test="${startFrom == 0}">disabled="disabled"</c:if>>
            		<input type="image" width="20" height="15" class="button" src="resources/images/rightSideArrow.png" title="<spring:message code="REF.UI.MANAGE.SYSTEM.USERS.NEXT"/>"
            		onclick="previousOrNext('next')" <c:if test="${maxNumber == numberOfRecords}">disabled="disabled"</c:if>>
              </th>
            </tr>
             <c:choose>
            	<c:when test="${(businessAuditList) == 'Empty' }" >
            		<tr class="odd">
            			<td><spring:message code="REF.UI.AUDIT.SEARCH.NO.RESULTS.FOUND"/></td>
            			<td></td>
            			<td></td>
            			<td></td>
            			<td></td>
            			<td></td>
            		</tr>
            	</c:when>
            	<c:otherwise>

	            <c:forEach var="businessAuditList" items="${businessAuditList}"
				varStatus="status">
					<tr
						<c:choose>
	            		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
	            		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
	            	</c:choose>>
						  <td class="left">${businessAuditList.date}</td>
						  <td class="left">${businessAuditList.userLogin.username}</td>
						  <td class="left">${businessAuditList.businessFunction}</td>
						  <td class="left">${businessAuditList.module}</td>
						  <td class="left">${businessAuditList.eventType.description}</td>
						  <td class="right"><a href="javascript:void(0);" onClick="showMoreDetails(<c:out value="${businessAuditList.businessAuditId}" />);"><img src="resources/images/detail_view.jpeg" align="absbottom" title="<spring:message code="REF.UI.AUDIT.VIEW.DETAILS"/>"></a></td>
					</tr>
				</c:forEach>
			</c:otherwise>

			</c:choose>

          </table>

        </div>
        <div class="clearfix"></div>
      </div>
     </c:if>
     <input type="hidden" name="startFrom" id="startFrom" value="0"/>
	 <input type="hidden" name="actionType" id="actionType" value="search"/>
    </form:form>
  </div>
</div>
<h:footer />
</body>
</html>
