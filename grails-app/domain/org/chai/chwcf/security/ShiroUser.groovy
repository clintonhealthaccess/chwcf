package org.chai.chwcf.security

class ShiroUser {
	
	String username
	String passwordHash

	static hasMany = [ roles: ShiroRole, permissions: String ]

	static constraints = {
		username(nullable:false, blank:false, unique:true, size:5..15)
		passwordHash(nullable:false, blank:false, size:5..15)
	}
}
