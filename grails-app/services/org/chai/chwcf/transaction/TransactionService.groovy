/**
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
package org.chai.chwcf.transaction

import java.util.Date;
import java.util.List;

import org.chai.chwcf.organisation.Cooperative
import org.chai.chwcf.organisation.CooperativeService
import org.chai.chwcf.organisation.Organisation
import org.chai.chwcf.reports.CostingType;
import org.chai.chwcf.security.User;
import org.chai.chwcf.transaction.Transaction;
import org.chai.chwcf.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;

/**
 * @author Jean Kahigiso M.
 *
 */
class TransactionService {

	static transactional = true;
	def localeService;
	def sessionFactory;
	def cooperativeService;

	List<Transaction> getTransaction(Cooperative cooperative){
		List<Transaction> transactions = null;
		if(cooperative)
			transactions= sessionFactory.currentSession.createCriteria(Transaction.class).add(Restrictions.eq('cooperative',cooperative)).list();

		return transactions;
	}

	List<Transaction> getTransaction(Organisation organisation){
		List<Transaction> transactions = null;
		List<Cooperative> cooperatives = cooperativeService.getCooperative(organisation);
		for(Cooperative cooperative: cooperatives)
			transactions.addAll(this.getTransaction(cooperative));
		return transactions;
	}

	List<Transaction> getTransaction(User user){
		List<Transaction> transactions = null;
		if(user)
			transactions = sessionFactory.currentSession.createCriteria(Transaction.class).add(Restrictions.eq('enteredBy',user.id)).list();
		return transactions;
	}
	
	List<Transaction> getTransactions(User user,Cooperative currentCooperative,Integer max){
		List<Transaction> transactions = [];
		def criteria = sessionFactory.getCurrentSession().createCriteria(Transaction.class)
		criteria.add(Restrictions.eq('enteredBy',user.id))
		criteria.add(Restrictions.eq('cooperative',currentCooperative))
		criteria.add(Restrictions.eq('approval',false))
		criteria.addOrder(Order.desc("recordedDate"))
		criteria.setMaxResults(max)
		transactions = criteria.list();
		return transactions;
	}

	List<Transaction> getTransaction(){
		return Transaction.list();
	}
	
	private Criteria getTransactionListCriteria(Date startDate, Date endDate, Cooperative cooperative, CostingType costingType) {
		def criteria = sessionFactory.getCurrentSession().createCriteria(Transaction.class)
		criteria.add(Restrictions.and(
			Restrictions.ge('transactionDate', startDate),
			Restrictions.le('transactionDate', endDate)
		))
		criteria.add(Restrictions.eq('cooperative', cooperative))
		criteria.createAlias('category', 'c')
		criteria.createAlias('c.costingTypes', 'ct')
		criteria.add(Restrictions.eq('ct.id', costingType.id))
		return criteria;
	}
	
	List<Transaction> getTransactions(Date date, Cooperative cooperative, CostingType costingType) {
		return getTransactionListCriteria(Utils.getStartDate(date), Utils.getEndDate(date), cooperative, costingType).list();
	}
	
	Double getTransactionSum(Date date, Cooperative cooperative, CostingType costingType) {
		Double sum = getTransactionListCriteria(Utils.getStartDate(date), Utils.getEndDate(date), cooperative, costingType).setProjection(Projections.sum("amount")).uniqueResult()
		if (sum == null) sum = 0d;
		return sum;
	}
}
