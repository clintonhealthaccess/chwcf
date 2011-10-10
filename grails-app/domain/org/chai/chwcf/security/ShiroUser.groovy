package org.chai.chwcf.security

class ShiroUser {
	
	String username
	String passwordHash
	boolean active = true

	static hasMany = [ roles: ShiroRole, permissions: String ]

	static constraints = {
		username(nullable:false, blank:false, unique:true, size:4..20)
		passwordHash(nullable:false, blank:false, size:5..2000)
	}
}
