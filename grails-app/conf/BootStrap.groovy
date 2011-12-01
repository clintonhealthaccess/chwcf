import org.apache.shiro.crypto.hash.Sha256Hash;
import org.chai.chwcf.Initializer;
import org.chai.chwcf.security.User;
import org.chai.chwcf.security.Role;
import grails.util.GrailsUtil;
class BootStrap {

	def init = { servletContext ->
		switch (GrailsUtil.environment) {
			case "production":
				Initializer.createPermission();
				Initializer.createRole();
				if (User.findByUsername('admin') == null) {
					def user = new User(username: "admin", passwordHash: new Sha256Hash("kamsfany").toHex(), active: true)
					user.addToRoles(Role.findByName("superUser"))
					user.save()
				}
				if (User.findByUsername('mohadmin') == null) {
					def mohAdmin=new User(username: "mohadmin", passwordHash: new Sha256Hash("mohadmin").toHex() , active: true)
					mohAdmin.addToRoles(Role.findByName("mohAdmin"))
					mohAdmin.save(failOnError: true, flush:true)
				}
				if (User.findByUsername('mohsupervisor') == null) {
					def mohSupervisor = new User(username: "mohsupervisor", passwordHash: new Sha256Hash("mohsupervisor").toHex(), active: true)
					mohSupervisor.addToRoles(Role.findByName("mohSupervisor"))
					mohSupervisor.save(failOnError: true, flush:true)
				}
				break;
			case "development":
				Initializer.createStructure();
				Initializer.createUser();
				Initializer.createTransaction();
				break;
		}
	}

	def destroy = {
	}
}
