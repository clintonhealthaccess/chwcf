<%@ page import="org.chai.chwcf.utils.Utils" %>
<div id="download-report" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Download Reports</h3>
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'report', action:'download',params: [targetURI: targetURI]]" useToken="true">
	<div class="row">
		<label>From</label>
		<label>Month 
		<select name="startMonth">
			<option value="">-- <g:message code="report.select.month.label" default="Select Month"/> --</option>
			<g:each in="${1..12}" var="i">
				<option value="${i}" ${(cmd?.startMonth==i)?'selected':''}>
					 <g:message code="report.month.${i}.label"/> 
				</option>
			</g:each>
		</select>
		Year:
		<select name="startYear">
			<option value="">-- <g:message code="report.select.month.label" default="Select Year"/> --</option>
			<g:each in="${endYear..startYear}" var="i">
				<option value="${i}" ${(cmd?.startYear==i)?'selected':''}>
					 ${i}
				</option>
			</g:each>
		</select>
		</label>
		<g:hasErrors bean="${cmd?.errors}">
         		<div class="error-list">
         			<g:renderErrors bean="${cmd}" field="startMonth"/>
          		 	<g:renderErrors bean="${cmd}" field="startYear"/>
         		</div>
  			</g:hasErrors>
	</div>
	<div class="row">
		<label>To </label>
		<label>Month:
		<select name="endMonth">
			<option value="">-- <g:message code="report.select.month.label" default="Select Month"/> --</option>
			<g:each in="${1..12}" var="j">
				<option value="${j}" ${(cmd?.endMonth==j)?'selected':''}>
					 <g:message code="report.month.${j}.label"/> 
				</option>
			</g:each>
		</select>
		Year:
		<select name="endYear">
			<option value="">-- <g:message code="report.select.month.label" default="Select Year"/> --</option>
			<g:each in="${endYear..startYear}" var="j">
				<option value="${j}" ${(cmd?.endYear==j)?'selected':''}>
					 ${j}
				</option>
			</g:each>
		</select>
		</label>
		<g:hasErrors bean="${cmd?.errors}">
         		<div class="error-list">
         		    <g:renderErrors bean="${cmd}" field="endMonth"/>
          		 	<g:renderErrors bean="${cmd}" field="endYear"/>
         		</div>
  			</g:hasErrors>
	</div>
	<g:if test="${reportOrganisation == null}">
		<div class="row">
			<label>District</label>
			<select name="organisation">
				<option value="">-- <g:message code="report.select.district.label" default="Select District"/> --</option>
				<g:each in="${organisations}" var="organisation">
					<option value="${organisation.organisationUnit.id}" ${(cmd?.organisation==organisation.organisationUnit.id)?'selected':''}>
						 ${organisation.organisationUnit.name}
					</option>
				</g:each>
			</select>
			<g:hasErrors bean="${cmd?.errors}">
	       		<div class="error-list">
	       		 	<g:renderErrors bean="${cmd}" field="organisation"/>
	       		</div>
	   		</g:hasErrors>
		</div>
	</g:if>
	<g:else>
		<input type="hidden" name="organisation" value="${reportOrganisation.id}"/>
	</g:else>
	
	<div class="row">
		<label>Reports Types</label>
		<select name="reportTypes" multiple="true" size="${reportTypes?.size()}">
			<g:each in="${reportTypes}" var="report" status="i" >
				<option value="${i+1}" ${(cmd?.reportTypes?.contains(i+1))?'selected':''}>
					 ${report}
				</option>
			</g:each>
		</select>
		<g:hasErrors bean="${cmd?.errors}">
       		<div class="error-list">
       		 	<g:renderErrors bean="${cmd}" field="reportTypes"/>
       		</div>
   		</g:hasErrors>
	</div>
	<div class="row">
		<button type="submit" >
			<g:message code="default.button.download.label" default="Download" />
		</button>
		<a href="${createLink(uri: targetURI)}">
			<g:message code="default.link.cancel.label" default="Cancel" />
		</a>
	</div>
	</g:form>
	<g:if test="${reports!=null}">
		<div class="main row">
			<g:each in="${reports}" var="report" status="i">
				<h3 class="title">${report.getKey()}</h3>
					<g:each in="${report.getValue()}" var="reportTable">
						<table class="listing">
							<tr>
								<th></th>
								<g:each in="${reportTable.months}" var="month">
									<th>${Utils.REPORT_FORMAT.format(month)}</th>
								</g:each>
							</tr>
							<g:each in="${reportTable.cooperatives}" var="cooperative">
							<tr>
								<td>${cooperative.name}</td>
								<g:each in="${reportTable.months}" var="month">
									<td>
									    ${reportTable.getValue(cooperative,month)}
									</td>
								</g:each>
							</tr>
					  		</g:each>
				  		</table>
			  		</g:each>
			</g:each>
		</div>
	</g:if>
	<div class="clear"></div>
</div>