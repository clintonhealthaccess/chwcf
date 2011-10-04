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
package org.chai.chwcf.person;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.chai.chwcf.HealthWorkerCategory;
import org.chai.chwcf.organisation.Cooperative;

/**
 * @author Jean Kahigiso M.
 * 
 */
@SuppressWarnings("serial")
@Entity(name = "CooperativeMember")
@Table(name = "chwcf_cooperative_member")
public class CooperativeMember extends Person {

	private Long id;
	private Integer idNumber;
	private Cooperative cooperative;
	private Date joinDate;
	private Date leftDate;
	private HealthWorkerCategory category;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setIdNumber(Integer idNumber) {
		this.idNumber = idNumber;
	}

	public Integer getIdNumber() {
		return idNumber;
	}

	public void setCooperative(Cooperative cooperative) {
		this.cooperative = cooperative;
	}
	
	@ManyToOne(targetEntity = Cooperative.class, optional = false)
	@JoinColumn(nullable = false)
	public Cooperative getCooperative() {
		return cooperative;
	}
	
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	@Basic(optional = true)
	@Temporal(TemporalType.DATE)
	public Date getLeftDate() {
		return leftDate;
	}

	public void setLeftDate(Date leftDate) {
		this.leftDate = leftDate;
	}

	public HealthWorkerCategory getCategory() {
		return category;
	}

	public void setCategory(HealthWorkerCategory category) {
		this.category = category;
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
		CooperativeMember other = (CooperativeMember) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CooperativeMember [id=" + id + ", cooperative=" + cooperative
				+ ", category=" + category + "]";
	}
	
	

}
