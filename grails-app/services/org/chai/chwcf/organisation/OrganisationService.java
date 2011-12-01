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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chai.chwcf.organisation.Organisation;
import org.chai.chwcf.organisation.OrganisationService;
import org.chai.chwcf.GroupCollection;
import org.chai.chwcf.OrganisationSorter;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitGroup;
import org.hisp.dhis.organisationunit.OrganisationUnitGroupService;
import org.hisp.dhis.organisationunit.OrganisationUnitGroupSet;
import org.hisp.dhis.organisationunit.OrganisationUnitService;

/**
 * @author Jean Kahigiso M.
 *
 */
public class OrganisationService {
	
	private static final Log log = LogFactory.getLog(OrganisationService.class);
	
	private String group;
	private OrganisationUnitService organisationUnitService;
	private OrganisationUnitGroupService organisationUnitGroupService;
	private int facilityLevel;
	
    public Organisation getRootOrganisation() {
		Collection<OrganisationUnit> organisations = organisationUnitService.getRootOrganisationUnits();
		if (organisations.size() != 1) {
			if (log.isErrorEnabled()) log.error("there is no root objective in the system, please create one");
			throw new IllegalStateException("there is no root objective in the system, please create one");
		}
		return createOrganisation(organisations.iterator().next());
    }
    
    private Organisation createOrganisation(OrganisationUnit organisationUnit) {
		if (organisationUnit == null) return null;
		Organisation organisation = new Organisation(organisationUnit);
		return organisation;
	}
    
	public int getLevel(Organisation organisation) {
		if (organisation.getLevel() != 0) return organisation.getLevel();
		int level = organisationUnitService.getLevelOfOrganisationUnit(organisation.getOrganisationUnit());
		organisation.setLevel(level);
		return level;
	}
	
	public boolean isAncestor(Organisation parent, Organisation child) {
		if (child == null) return false;
		if (child.equals(parent)) return true;
		else {
			loadParent(child);
			return isAncestor(parent, child.getParent());
		}
	}
	
	public boolean isAncestor(List<Organisation> parents, Organisation child) {
		if (child == null) return false;
		for(Organisation parent : parents){
			if (child.equals(parent)) return true;
			else {
				parents.remove(parent);
				if(!parents.isEmpty()){
					loadParent(child);
					return isAncestor(parents, child.getParent());
				}
				return false;
			}
		}
		return false;
	}
	
	public List<Organisation> getOrganisationsOfLevel(int level) {
		Collection<OrganisationUnit> organisationUnits = organisationUnitService.getOrganisationUnitsAtLevel(level);
		List<Organisation> result = new ArrayList<Organisation>();
		for (OrganisationUnit organisationUnit : organisationUnits) {
			result.add(createOrganisation(organisationUnit));
		}
		return result;
	}
	
	public Organisation getOrganisation(int id) {
		OrganisationUnit organisationUnit = organisationUnitService.getOrganisationUnit(id);
		return createOrganisation(organisationUnit);
	}
	
	
	public boolean loadParent(Organisation organisation, Integer... skipLevels) {
		if (organisation.getParent() != null) return true;
		OrganisationUnit parent = getParent(organisation.getOrganisationUnit(), skipLevels);
		if (parent == null) return false;
		else {
			organisation.setParent(createOrganisation(parent));
			return true;
		}
	}
	
    private OrganisationUnitGroupSet unitGroupSetCache;
	
	private OrganisationUnitGroupSet getOrganisationUnitGroupSet() {
		if (unitGroupSetCache == null) {
			unitGroupSetCache = organisationUnitGroupService.getOrganisationUnitGroupSetByName(group);
			
			for (@SuppressWarnings("unused") OrganisationUnitGroup group : unitGroupSetCache.getOrganisationUnitGroups()) {
				// load
			}
		}
		return unitGroupSetCache;
	}
	
	
	public Organisation getOrganisationTreeUntilLevel(int level, Integer... skipLevels) {
		List<Integer> skipLevelList = Arrays.asList(skipLevels);
		Organisation rootOrganisation = getRootOrganisation();
		loadUntilLevel(rootOrganisation, level-skipLevelList.size(), skipLevels);
		return rootOrganisation;
	} 
	
	public void loadUntilLevel(Organisation organisation, int level, Integer... skipLevels) {
		getLevel(organisation);
		if (organisation.getLevel() < level) {
			loadChildren(organisation, skipLevels);
			for (Organisation child : organisation.getChildren()) {
				loadUntilLevel(child, level, skipLevels);
			}
		}
	}
	
	public void loadGroup(Organisation organisation) {
		organisation.setOrganisationUnitGroup(organisation.getOrganisationUnit().getGroupInGroupSet(getOrganisationUnitGroupSet()));
	}
	
	public void loadChildren(Organisation organisation, Integer... skipLevels) {
		if (organisation.getChildren() != null) return;
		List<Organisation> result = new ArrayList<Organisation>();
		for (OrganisationUnit organisationUnit : getChildren(organisation.getOrganisationUnit(), skipLevels)) {
			Organisation child = createOrganisation(organisationUnit);
			child.setParent(organisation);
			result.add(child);
		}
		organisation.setChildren(result);
	}
	
	private OrganisationUnit getParent(OrganisationUnit organisationUnit, Integer... skipLevels) {
		List<Integer> skipLevelList = Arrays.asList(skipLevels);
		if (organisationUnit.getParent() == null) return null;
		int level = organisationUnitService.getLevelOfOrganisationUnit(organisationUnit.getParent());
		if (skipLevelList.contains(level)) {
			if (log.isInfoEnabled()) log.info("skipping parent: "+organisationUnit.getParent()+" of level: "+level);
			return getParent(organisationUnit.getParent(), skipLevels);
		}
		return organisationUnit.getParent();
	}
	
	public Organisation getParentOfLevel(Organisation organisation, Integer level) {
		Organisation tmp = organisation;
		this.loadParent(tmp);
		while (tmp.getParent() != null) {
			this.getLevel(tmp.getParent());
			if (tmp.getParent().getLevel() == level)
				return tmp.getParent();
			tmp = tmp.getParent();
			this.loadParent(tmp);
		}
		return null;
	}
	
	public List<Organisation> getChildrenOfLevel(Organisation organisation, int level) {
		List<OrganisationUnit> children = getChildrenOfLevel(organisation.getOrganisationUnit(), level);
		List<Organisation> result = new ArrayList<Organisation>();
		for (OrganisationUnit child : children) {
			result.add(createOrganisation(child));
		}
		return result;
	}
	
	public List<Organisation> getChildrenOfLevelAndGroups(Organisation organisation, int level, List<OrganisationUnitGroup> groups) {
		List<OrganisationUnit> children = getChildrenOfLevel(organisation.getOrganisationUnit(), level);
		List<Organisation> result = new ArrayList<Organisation>();
		for (OrganisationUnit child : children) {
			Organisation org = this.createOrganisation(child);
			this.loadGroup(org);
			if (groups.contains(org.getOrganisationUnitGroup()))
				result.add(org);
		}
		return result;
	}
	
	public List<Organisation> getOrganisation(int districtLevel,int faciltyLevel, List<String> uuids) {
		List<OrganisationUnitGroup> groups = this.getOrganisationUnitGroups(uuids);
		List<Organisation> organisations = this.getOrganisationsOfLevel(districtLevel);
		List<Organisation> facilities = this.getOrganisationsOfLevel(faciltyLevel);
		
		for (Organisation district : organisations)
			this.loadGroup(district);
		    
		for (Organisation facility : facilities) {
			this.loadGroup(facility);
			if (groups.contains(facility.getOrganisationUnitGroup()))
				organisations.add(facility);
		}
		
		return organisations;
	}
	
	
	public List<Organisation> searchOrganisation(List<Organisation> organisations,String term){
		List<Organisation> orgs = new ArrayList<Organisation>();
		for(Organisation organisation: organisations){
			this.loadGroup(organisation);
			if(organisation.getOrganisationUnit().getName().toLowerCase().contains(term.toLowerCase()))
				orgs.add(organisation);
		}
		if(!orgs.isEmpty())
			Collections.sort(orgs,new OrganisationSorter());
		return orgs;
		
	}


	public List<OrganisationUnitGroup> getOrganisationUnitGroups(List<String> uuIds){
		List<OrganisationUnitGroup> organisationUnitGroups = new ArrayList<OrganisationUnitGroup>();
		for(String uuid: uuIds)
			organisationUnitGroups.add(this.getOrganisationUnitGroup(uuid));
		return organisationUnitGroups;
	}
	
	public OrganisationUnitGroup getOrganisationUnitGroup(String uuid) {
	    	return getGroupCollection().getGroupByUuid(uuid);
	 }
	  // optimization, we cache all the groups
    private GroupCollection groupCollection = null;
    
    private GroupCollection getGroupCollection() {
    	if (groupCollection == null) {
    		groupCollection = new GroupCollection(getGroupsForExpression());
    	}
    	return groupCollection;
    }
    
	@SuppressWarnings("unused")
	public Set<OrganisationUnitGroup> getGroupsForExpression() {
		Set<OrganisationUnitGroup> result = new HashSet<OrganisationUnitGroup>();
		result.addAll(organisationUnitGroupService.getOrganisationUnitGroupSetByName(group).getOrganisationUnitGroups());
		for (OrganisationUnitGroup organisationUnitGroup : result) {
			for (OrganisationUnit organisationUnit : organisationUnitGroup.getMembers()) {
				// EAGER load		
			}
		}
		return result;
	}
	
	private List<OrganisationUnit> getChildrenOfLevel(OrganisationUnit organisation, final int level) {
		List<OrganisationUnit> result = new ArrayList<OrganisationUnit>(organisationUnitService.getOrganisationUnitsAtLevel(level, organisation));
		return result;
	}
	
	private List<OrganisationUnit> getChildren(OrganisationUnit organisation, Integer... skipLevels) {
		List<Integer> skipLevelList = Arrays.asList(skipLevels);
		List<OrganisationUnit> result = new ArrayList<OrganisationUnit>();
		int level = organisationUnitService.getLevelOfOrganisationUnit(organisation);
		for (OrganisationUnit child : organisation.getChildren()) {
			// we optimize by assuming that the level of the children is <level of parent> + 1
			if (skipLevelList.contains(level+1)) {
				if (log.isInfoEnabled()) log.info("skipping child: "+child+" of level: "+level);
				result.addAll(getChildren(child, skipLevels));
			}
			else {
				result.add(child);
			}
		}
		return result;
	}


	public void setGroup(String group) {
		this.group = group;
	}

	public void setOrganisationUnitService(
			OrganisationUnitService organisationUnitService) {
		this.organisationUnitService = organisationUnitService;
	}

	public void setOrganisationUnitGroupService(
			OrganisationUnitGroupService organisationUnitGroupService) {
		this.organisationUnitGroupService = organisationUnitGroupService;
	}

	public int getFacilityLevel() {
		return facilityLevel;
	}

	public void setFacilityLevel(int facilityLevel) {
		this.facilityLevel = facilityLevel;
	}
    
    

}
