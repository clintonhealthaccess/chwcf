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

import java.util.List;

import org.chai.chwcf.AbstractEntityController;
import org.chai.chwcf.TransactionSorter
import org.chai.chwcf.organisation.Cooperative;
import org.chai.chwcf.organisation.OrganisationService;
import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.chai.chwcf.security.User;
import org.chai.chwcf.transaction.CategoryType;
import org.chai.chwcf.organisation.CooperativeService;
import org.chai.chwcf.utils.Utils;
import org.apache.shiro.SecurityUtils
import org.chai.chwcf.transaction.Transaction
import org.chai.chwcf.transaction.TransactionService
/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class TransactionController extends AbstractEntityController {
	OrganisationService organisationService;
	TransactionService transactionService;
	int districtLevel = ConfigurationHolder.config.district.level;
	int max = ConfigurationHolder.config.last.entered.trans.list.max;

	def getEntity(def id){
		return Transaction.get(id);
	}
	def createEntity(){
		def entity = new Transaction();
		if(!params['cooperative.id']) entity.cooperative = Cooperative.get(params.int('cooperative'));
		return entity;
	}
	def getModel(def entity) {
		List<Transaction> transactions= transactionService.getTransactions(getUser(), entity.cooperative, max);  
		def readonly = false
		if (entity.id != null) 
			if (!canApprove(entity) && entity.approval == true) 
			   readonly = true;   
			   
		[
			transaction:entity,
			entities: transactions,
			listTemplate: "/admin/transaction/transactionList",
			categories: Category.list(),
			readonly: readonly
		]
	}
	
	def canApprove(def entity) {
		return SecurityUtils.subject.isPermitted('transaction:approve')
	}

	def getTemplate() {
		return "/admin/transaction/createTransaction"
	}
	def getLabel() {
		return "admin.transaction.label"
	}
	def bindParams(def entity) {
		bindData(entity,params,[exclude:['transactionDate','approval']])

		if(!params.int('enteredBy')) entity.enteredBy = getUser().id;

		if (canApprove(entity)) {
			// approval
			if (params['approval']) {
				if (!entity.validatedBy) {
					entity.approval = true
					entity.validatedBy = getUser().id;
					entity.approvalDate = new Date();
				}
			} else {
				entity.approval = false
				entity.validatedBy = null;
				entity.approvalDate = null;
			}
		}
		
		if(!entity.recordedDate) entity.recordedDate= new Date();

		if(params.transactionDate!='' && params.transactionDate!=null){
			if(Utils.parseDate(params.transactionDate)!=null)
				entity.transactionDate=Utils.parseDate(params.transactionDate);
			else{
				entity.transactionDate=null
			}
		}else{
			entity.transactionDate=null
		}

	}

	def list = {
		Cooperative cooperative = Cooperative.get(params.int('cooperative'));
		List<Transaction> transactions=[];
		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 20)
		params.offset = params.offset ? params.int('offset'): 0

		// branch depending on user
		if (getUser().organisation != null) {
			if (!organisationService.isAncestor(organisationService.getOrganisation(getUser().organisation), organisationService.getOrganisation(cooperative.organisationUnit.id))) {
				response.sendError(403)
				return
			}
		}
        if(cooperative!=null)
			if(!params['sort']){
				transactions = Transaction.findAllByCooperative(cooperative);
				if(!transactions.isEmpty())
					Collections.sort(transactions,new TransactionSorter())
			}else{
				transactions = Transaction.findAllByCooperative(cooperative,[sort:params['sort'],order:params['order']]);
			}	

		def max = Math.min(params['offset']+params['max'],transactions.size())

		render (view: '/admin/list', model:[
					template: "/transaction/transactionList",
					entities: transactions.subList(params['offset'], max),
					showLocation: false,
					cooperative: cooperative,
					entityCount: transactions.size(),
					targetURI: getTargetURI(),
					code: getLabel()
				])
	}
}
