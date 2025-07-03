plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("jacoco")
	id("info.solidsoft.pitest") version "1.19.0-rc.1"
}


jacoco {
	toolVersion = "0.8.13"
}


group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}


repositories {
	mavenCentral()
}


dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
	testImplementation("io.kotest:kotest-assertions-core:5.9.1")
	testImplementation("io.kotest:kotest-property:5.9.1")
	testImplementation("io.mockk:mockk:1.14.4")
	testImplementation("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.15.0")
	testImplementation("io.kotest.extensions:kotest-extensions-pitest:1.2.0")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<JacocoReport>("jacocoFullReport") {
	dependsOn(tasks.named("test"))
	executionData(tasks.named("test").get())

	sourceSets(sourceSets["main"])

	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}


pitest {
	targetClasses.add("com.example.bookmanager.*")
	junit5PluginVersion.set("1.2.0")
	avoidCallsTo.set(setOf("kotlin.jvm.internal"))
	mutators.set(setOf("STRONGER"))
	threads.set(4)
	jvmArgs.add("-Xmx1024m")
	testSourceSets.addAll(sourceSets["test"])
	mainSourceSets.addAll(sourceSets["main"])
	outputFormats.addAll("XML", "HTML")
	excludedClasses.add("**LibraryApplication")
}
