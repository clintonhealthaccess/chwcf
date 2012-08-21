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
package org.chai.chwcf.organisation;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.chai.chwcf.Translatable;

/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("serial")
@Entity(name="Pbf")
@Table(name="chwcf_pbf")
public class Pbf extends Translatable {
	
	private Long id;
	private Date startDate;
	private Date endDate;
	private PbfType type;
	private Long amoutMohToHC;
	private Long amountSousCompte;	
	private Long amountHCtoCoop;
	private Cooperative cooperative;
	private Integer order;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@ManyToOne(targetEntity = PbfType.class, optional = false)
	@JoinColumn(nullable = false)
	public PbfType getType() {
		return type;
	}
	public void setType(PbfType type) {
		this.type = type;
	}
	public Long getAmountSousCompte() {
		return amountSousCompte;
	}
	public void setAmountSousCompte(Long amountSousCompte) {
		this.amountSousCompte = amountSousCompte;
	}
	public Long getAmoutMohToHC() {
		return amoutMohToHC;
	}
	public void setAmoutMohToHC(Long amoutMohToHC) {
		this.amoutMohToHC = amoutMohToHC;
	}
	
	public void setAmountHCtoCoop(Long amountHCtoCoop) {
		this.amountHCtoCoop = amountHCtoCoop;
	}
	public Long getAmountHCtoCoop() {
		return amountHCtoCoop;
	}
	@ManyToOne(targetEntity = Cooperative.class, optional = false)
	@JoinColumn(nullable = false)
	public Cooperative getCooperative() {
		return cooperative;
	}
	
	public void setCooperative(Cooperative cooperative) {
		this.cooperative = cooperative;
	}
	@Basic(optional = true)
	@Column(name = "ordering")
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pbf other = (Pbf) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "CooperativePbf [id=" + id + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
	
}
