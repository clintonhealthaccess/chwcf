import org.apache.shiro.crypto.hash.Sha256Hash
import org.chai.chwcf.Initializer;
import org.chai.chwcf.security.ShiroUser;
class BootStrap {

    def init = { servletContext ->
		Initializer.createUser();
		Initializer.createStructure();
		Initializer.createTransaction();
    }
	
    def destroy = {
    }
}
