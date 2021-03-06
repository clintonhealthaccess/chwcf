// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: [
		'text/html',
		'application/xhtml+xml'
	],
	xml: [
		'text/xml',
		'application/xml'
	],
	text: 'text/plain',
	js: 'text/javascript',
	rss: 'application/rss+xml',
	atom: 'application/atom+xml',
	css: 'text/css',
	csv: 'text/csv',
	all: '*/*',
	json: [
		'application/json',
		'text/json'
	],
	form: 'application/x-www-form-urlencoded',
	multipartForm: 'multipart/form-data'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = [
	'/images/*',
	'/css/*',
	'/js/*',
	'/plugins/*'
]

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// set per-environment serverURL stem for creating absolute links
//environments {
//    production {
//        grails.serverURL = "http://www.changeme.com"
//    }
//    development {
//        grails.serverURL = "http://localhost:8080/${appName}"
//    }
//    test {
//        grails.serverURL = "http://localhost:8080/${appName}"
//    }
//
//}

environments {
	development {
		// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
		grails.logging.jul.usebridge = true
	}
	production {
		// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
		grails.logging.jul.usebridge = false
	}
}

security.shiro.authc.required = false

// log4j configuration
//environments {
//	development {
log4j = {
	// Example of changing the log pattern for the default console
	// appender:
	//
	//appenders {
	//    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
	//}

	error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
			'org.codehaus.groovy.grails.web.pages', //  GSP
			'org.codehaus.groovy.grails.web.sitemesh', //  layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping', // URL mapping
			'org.codehaus.groovy.grails.commons', // core / classloading
			'org.codehaus.groovy.grails.plugins', // plugins
			'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate',
			'grails.app.services.org.grails.plugin.resource',
			'grails.app.resourceMappers.org.grails.plugin.resource',
			'grails.app.taglib.org.grails.plugin.resource',
			'grails.app.resourceMappers.org.grails.plugin.cachedresources'

	debug  'grails.app',
			'org.chai.kevin',
			'grails.plugin.springcache'

}

//// log4j configuration
//log4j = {
//    // Example of changing the log pattern for the default console
//    // appender:
//    //
//    //appenders {
//    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
//    //}
//
//    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
//           'org.codehaus.groovy.grails.web.pages', //  GSP
//           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
//           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
//           'org.codehaus.groovy.grails.web.mapping', // URL mapping
//           'org.codehaus.groovy.grails.commons', // core / classloading
//           'org.codehaus.groovy.grails.plugins', // plugins
//           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
//           'org.springframework',
//           'org.hibernate',
//           'net.sf.ehcache.hibernate'
//
//    warn   'org.mortbay.log'
//}


/**
 * Application specific config
 */

site.languages=["en", "fr"]
site.fallback.language="en"
site.admin=true
site.entity.list.max=32
last.entered.trans.list.max=10

info.group.level=3
district.level=3
facility.level=4
facility.group.type=["Health Center"]
facility.type.group="Type"
report.start.year=2004
report.types=["Net Income","Retained Earnings","Total Cash Flow","Total Assets"]
report.header=["Province","District","Health Facility","Cooperative"]



/**
 * Configuration file override
 */
def locations = [
	"file:${userHome}/.grails/${appName}-config.groovy"
]
if (System.properties['config']) locations.add("file:"+System.properties['config'])
environments {
	production {
		grails.config.locations = locations
	}
}






