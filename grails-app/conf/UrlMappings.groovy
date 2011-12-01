class UrlMappings {

	static mappings = {
		
		"/admin/$action/$region?/$district?"(controller:"admin")
		
		"/chwcf/$cooperative/$action"(controller:"chwcf")
		
		"/auth/$action"(controller:"auth")
		
		"/cooperative/$action/$organisation?"(controller:"cooperative")
		"/activity/$action/$cooperative?"(controller:"activity")
		"/pbfScore/$action/$cooperative?"(controller:"pbfScore")
		"/share/$action/$cooperative?"(controller:"share")
		"/memberCategory/$action"(controller:"memberCategory")
		"/member/$action/$cooperative?"(controller:"member")
		"/categoryType/$action"(controller:"categoryType")
		"/category/$action/$typeId?"(controller:"category")
		"/transaction/$action/$cooperative?"(controller:"transaction")
		
		"/$controller/$action?"{
			constraints {
				// apply constraints here
			}
		}
		
		// homepage in home controller
		"/"(controller:"home", action:"index")
		//"/"(controller:"cooperative", action:"list")
		"500"(view:'/error')
	}
	
}
