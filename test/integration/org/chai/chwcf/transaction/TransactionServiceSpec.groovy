package org.chai.chwcf.transaction

import java.util.Date;

import org.chai.chwcf.IntegrationTests;
import org.chai.chwcf.organisation.Cooperative;
import org.chai.chwcf.reports.CostingType;
import org.hisp.dhis.organisationunit.OrganisationUnit;

class TransactionServiceSpec extends IntegrationTests {

	def transactionService
	
	def "get transactions"() {
		setup:
		setupOrganisationUnitTree()
		def registrationLevel = newRegistrationLevel()
		def cooperative = newCooperative(OrganisationUnit.findByName(BUTARO), BUTARO, registrationLevel)
		def category = newCategory()
		def costing = newCostingType(CODE(1), [category])
		
		when: "returns transaction at beginning of month"
		def transaction1 = newTransaction(cooperative, category, 100, getDate(2000, 1, 1))
		def transactions = transactionService.getTransactions(getDate(2000, 1, 1), cooperative, costing)
		def transactionSum = transactionService.getTransactionSum(getDate(2000, 1, 1), cooperative, costing)
		
		then:
		transactions.equals([transaction1])
		transactionSum == 100
		
		when: "returns transaction at end of month"
		def transaction2 = newTransaction(cooperative, category, 100, getDate(2000, 1, 31))
		transactions = transactionService.getTransactions(getDate(2000, 1, 1), cooperative, costing)
		transactionSum = transactionService.getTransactionSum(getDate(2000, 1, 1), cooperative, costing)
		
		then:
		transactions.equals([transaction1, transaction2])
		transactionSum == 200
		
		when: "date outside range returns empty list"
		transactions = transactionService.getTransactions(getDate(2001, 2, 1), cooperative, costing)
		transactionSum = transactionService.getTransactionSum(getDate(2000, 2, 1), cooperative, costing)
		
		then:
		transactions.isEmpty()
		transactionSum == 0
		
		when: "date outside range returns empty list"
		transactions = transactionService.getTransactions(getDate(2000, 2, 1), cooperative, costing)
		transactionSum = transactionService.getTransactionSum(getDate(2000, 2, 1), cooperative, costing)
		
		then:
		transactions.isEmpty()
		transactionSum == 0
	}
	
	def "get transactions does not return transaction from other cooperative"() {
		setup:
		setupOrganisationUnitTree()
		def registrationLevel = newRegistrationLevel()
		def cooperative1 = newCooperative(OrganisationUnit.findByName(BUTARO), BUTARO+'1', registrationLevel)
		def cooperative2 = newCooperative(OrganisationUnit.findByName(BUTARO), BUTARO+'2', registrationLevel)
		def category = newCategory()
		def costing = newCostingType(CODE(1), [category])
		
		when:
		def transaction1 = newTransaction(cooperative1, category, 100, getDate(2000, 1, 1))
		def transaction2 = newTransaction(cooperative2, category, 100, getDate(2000, 1, 1))
		def transactions = transactionService.getTransactions(getDate(2000, 1, 1), cooperative1, costing)
		
		then:
		transactions.equals([transaction1])
	}

	def "get transactions does not return transaction from other costing type"() {
		setup:
		setupOrganisationUnitTree()
		def registrationLevel = newRegistrationLevel()
		def cooperative = newCooperative(OrganisationUnit.findByName(BUTARO), BUTARO, registrationLevel)
		def category1 = newCategory()
		def category2 = newCategory()
		def costing1 = newCostingType(CODE(1), [category1])
		def costing2 = newCostingType(CODE(2), [category2])
		
		when:
		def transaction1 = newTransaction(cooperative, category1, 100, getDate(2000, 1, 1))
		def transaction2 = newTransaction(cooperative, category2, 100, getDate(2000, 1, 1))
		def transactions = transactionService.getTransactions(getDate(2000, 1, 1), cooperative, costing1)
		
		then:
		transactions.equals([transaction1])
	}
		
}
