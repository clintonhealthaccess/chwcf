package org.chai.chwcf.security

class User {
	
	String username
	String passwordHash
	boolean active = false
	String email
	String familyName
	String otherName
	String affiliation
	Integer organisation
		
	static hasMany = [ roles: Role, permissions: String ]

	static constraints = {
		username(nullable: false, blank: false, unique: true, size: 4..20)
		passwordHash(nullable: false, blank: false, size: 5..2000)
		email(nullable: true, blank: true, email: true,unique: true)
		familyName(nullable: true,blank: true)
		otherName(nullable: true,blank: true)
		affiliation(nullable:true, blank:true)
		organisation(nullable:true, blank:true)
	}
	
}
