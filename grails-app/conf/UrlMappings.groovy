class UrlMappings {

	static mappings = {
		
		"/admin/$user/$action"(controller:"admin")
		"/chwcf/$user/$cooperative/$action"(controller:"chwcf")
		
		"/auth/$action"(controller:"auth")
		
		"/$controller/$action?"{
			constraints {
				// apply constraints here
			}
		}
		
		// homepage in home controller
		"/"(controller:"home", action:"index")
		"500"(view:'/error')
	}
	
}
