modules = {
	
	// modules
	core {
		dependsOn 'jquery'
		resource url: '/css/main.css', bundle: 'core'
	}

	error {
		resource url: '/css/errors.css'
	}
	
	cluetip {
		dependsOn 'jquery'

		resource url: '/js/jquery/cluetip/jquery.cluetip.js'
		resource url: '/js/jquery/cluetip/lib/jquery.hoverIntent.js'
		resource url: '/js/jquery/cluetip/jquery.cluetip.css'
		resource url: '/js/cluetip_init.js'
	}
	
	form {
		dependsOn 'jquery,cluetip'

		resource url: '/js/jquery/form/jquery.form.js'
		resource url: '/js/form-util.js'
		resource url: '/js/form_init.js'
	}
	
	chosen {
		dependsOn 'jquery'

		resource url: '/js/jquery/chosen/chosen.jquery.js'
		resource url: '/js/jquery/chosen/ajax-chosen.js'
		resource url: '/js/jquery/chosen/chosen.css'
	}

	datepicker {
		dependsOn 'jquery'

		resource url: '/js/jquery/datepicker/glDatePicker.js'
		resource url: '/js/jquery/datepicker/datepicker.css'
	}
	
	dropdown {
		dependsOn 'jquery'

		resource url: '/js/dropdown_init.js', bundle: 'core'
	}
	foldable {
		dependsOn 'jquery'

		resource url: '/js/foldable_init.js', bundle: 'core'
	}
	
}