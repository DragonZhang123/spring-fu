plugins {
	id("io.spring.dependency-management")
	id("java-library")
}

dependencies {
	api("org.springframework.boot:spring-boot")
	api("org.springframework.boot:spring-boot-autoconfigure")

	compileOnly("org.springframework:spring-webflux")
	compileOnly("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
	compileOnly("org.springframework.data:spring-data-mongodb")
	compileOnly("org.mongodb:mongodb-driver-reactivestreams")
	compileOnly("com.fasterxml.jackson.core:jackson-databind")
	compileOnly("com.samskivert:jmustache")
	compileOnly(project(":coroutines:data-mongodb"))
	compileOnly(project(":coroutines:webflux"))
}

repositories {
	mavenCentral()
	maven("https://repo.spring.io/milestone")
	maven("http://dl.bintray.com/kotlin/kotlin-eap")
	maven("https://jcenter.bintray.com")
}

dependencyManagement {
	val bootVersion: String by project
	val coroutinesVersion: String by project
	imports {
		mavenBom("org.springframework.boot:spring-boot-dependencies:$bootVersion")
	}
	dependencies {
		dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
		dependency("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")
	}
}


publishing {
	publications {
		create(project.name, MavenPublication::class.java) {
			from(components["java"])
			artifactId = "spring-fu-autoconfigure-adapter"
			val sourcesJar by tasks.creating(Jar::class) {
				classifier = "sources"
				from(sourceSets["main"].allSource)
			}
			artifact(sourcesJar)
		}
	}
}
