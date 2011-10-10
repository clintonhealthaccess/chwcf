class UrlMappings {

	static mappings = {
		
		"/admin/$action/$region?/$district?"(controller:"admin")
		
		"/chwcf/$cooperative/$action"(controller:"chwcf")
		
		"/auth/$action"(controller:"auth")
		
		"/$controller/$action?"{
			constraints {
				// apply constraints here
			}
		}
		
		// homepage in home controller
		//"/"(controller:"home", action:"index")
		"/"(controller:"admin", action:"regions")
		"500"(view:'/error')
	}
	
}
