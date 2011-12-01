package org.chai.chwcf


/*
* Copyright (c) 2011, Clinton Health Access Initiative.
*
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the <organization> nor the
*       names of its contributors may be used to endorse or promote products
*       derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import grails.plugin.spock.IntegrationSpec

import java.util.Date

import org.chai.chwcf.organisation.Cooperative;
import org.chai.chwcf.organisation.Organisation;
import org.chai.chwcf.organisation.RegistrationLevel;
import org.chai.chwcf.reports.CostingType;
import org.chai.chwcf.transaction.Category;
import org.chai.chwcf.transaction.CategoryType;
import org.chai.chwcf.transaction.Transaction;
import org.hisp.dhis.organisationunit.OrganisationUnit
import org.hisp.dhis.organisationunit.OrganisationUnitGroup
import org.hisp.dhis.organisationunit.OrganisationUnitGroupSet
import org.hisp.dhis.organisationunit.OrganisationUnitLevel
import org.hisp.dhis.period.MonthlyPeriodType
import org.hisp.dhis.period.Period

abstract class IntegrationTests extends IntegrationSpec {
	
	static final String CODE (def number) { return "CODE"+number }
	
	static final String HEALTH_CENTER_GROUP = "Health Center"
	static final String DISTRICT_HOSPITAL_GROUP = "District Hospital"
	
	static final String COUNTRY = "Country"
	static final String DISTRICT = "District"
	static final String PROVINCE = "Province"
	static final String FACILITY = "Facility"
	
	static final String RWANDA = "Rwanda"
	static final String NORTH = "North"
	static final String BURERA = "Burera"
	static final String BUTARO = "Butaro DH"
	static final String KIVUYE = "Kivuye HC"
	
	static final String GROUP_SET_TYPE = "Type"
	
	def setupOrganisationUnitTree() {
		// for the test environment, the facility level is set to 4
		// so we create a tree accordingly
		def set = newOrganisationUnitGroupSet(GROUP_SET_TYPE);
		def hc = newOrganisationUnitGroup(HEALTH_CENTER_GROUP, set);
		def dh = newOrganisationUnitGroup(DISTRICT_HOSPITAL_GROUP, set);
		
		newOrganisationUnitLevel(COUNTRY, 1)
		newOrganisationUnitLevel(PROVINCE, 2)
		newOrganisationUnitLevel(DISTRICT, 3)
		newOrganisationUnitLevel(FACILITY, 4)
		
		def rwanda = newOrganisationUnit(RWANDA)
		def north = newOrganisationUnit(NORTH, rwanda)
		def burera = newOrganisationUnit(BURERA, north)
		newOrganisationUnit(BUTARO, burera, dh)
		newOrganisationUnit(KIVUYE, burera, hc)
	}
	
	private OrganisationUnitLevel newOrganisationUnitLevel(def name, def level) {
		return new OrganisationUnitLevel(level: level, name: name).save(failOnError: true)
	}
	
	private OrganisationUnit newOrganisationUnit(def name) {
		return newOrganisationUnit(name, null, null)
	}
	
	private OrganisationUnit newOrganisationUnit(def name, def parent) {
		return newOrganisationUnit(name, parent, null)
	}
	
	private OrganisationUnit newOrganisationUnit(def name, def parent, def group) {
		def organisation = new OrganisationUnit(name: name, shortName: name).save(failOnError: true)
		if (group != null) {
			organisation.groups << group
			group.members << organisation
			group.save(failOnError: true)
		}
		if (parent != null) {
			organisation.parent = parent
			parent.children << organisation
			parent.save(failOnError: true)
		}
		organisation.save(failOnError: true, flush: true)
		return organisation
	}
	
	private OrganisationUnitGroupSet newOrganisationUnitGroupSet(def name) {
		return new OrganisationUnitGroupSet(name: name).save(failOnError: true)
	} 
	
	private OrganisationUnitGroup newOrganisationUnitGroup(def name, def set) {
		def group = new OrganisationUnitGroup(name: name, uuid: name, groupSet: set).save(failOnError: true)
		set.organisationUnitGroups << group
		set.save(failOnError: true)
		return group
	}
	
	def newRegistrationLevel() {
		return new RegistrationLevel().save(failOnError: true);
	}
	
	def newCooperative(def facility, def name, def registrationLevel) {
		return new Cooperative(organisationUnit: facility, name: name, registrationLevel: registrationLevel, createDate: getDate(2000,1,1)).save(failOnError: true)
	}
	
	def newCostingType(def code, def categories) {
		def costingType = new CostingType(code: code, categories: categories).save(failOnError:true)
		categories.each { category ->
			category.costingTypes << costingType
			category.save(failOnError: true)
		}
		return costingType
	}
	
	def newCategory() {
		def type = new CategoryType(code: 'CODE').save(failOnError: true)
		return new Category(type: type).save(failOnError: true)
	}
	
	def newTransaction(def cooperative, def category, def amount, def transactionDate) {
		return new Transaction(cooperative: cooperative, category: category, enteredBy: 0, transactionDate: transactionDate, recordedDate: new Date(), amount: amount).save(failOnError: true)
	}
	
	static def getOrganisationUnitLevels(def levels) {
		def result = []
		for (def level : levels) {
			result.add OrganisationUnitLevel.findByLevel(new Integer(level).intValue())
		}
		return result;
	}
	
	static def getOrganisation(def name) {
		return new Organisation(OrganisationUnit.findByName(name))
	}
	
	static def getOrganisations(def names) {
		def result = []
		for (String name : names) {
			result.add(getOrganisation(name))
		}
		return result
	}
	
	public static Date getDate(int year, int month, int day)
	{
		final Calendar calendar = Calendar.getInstance();

		calendar.clear();
		calendar.set( Calendar.YEAR, year );
		calendar.set( Calendar.MONTH, month - 1 );
		calendar.set( Calendar.DAY_OF_MONTH, day );

		return calendar.getTime();
	}

	
}
