dataSource {
    pooled = true
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
	//show_sql = true
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:h2:mem:devDB"
			driverClassName = "org.h2.Driver"
			username = "sa"
			password = ""
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb"
			driverClassName = "org.h2.Driver"
			username = "sa"
			password = ""
        }
    }
    production {
        dataSource {
            dbCreate = "update"
			driverClassName = "com.mysql.jdbc.Driver"
            // url = "jdbc:h2:file:prodDb;shutdown=true"
        }
    }
}
