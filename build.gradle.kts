import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenCentral()
	}

	dependencies {
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
		classpath("org.jetbrains.kotlin:kotlin-allopen:1.5.21")
		classpath("org.jetbrains.kotlin:kotlin-noarg:1.5.21")
	}
}

plugins {
	id("org.springframework.boot") version "2.5.3"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
}

group = "io.species"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") {
		exclude(group = "org.hibernate", module = "hibernate-entitymanager")
		exclude(group = "org.hibernate", module = "hibernate-core")
	}

	implementation("org.eclipse.persistence:eclipselink:2.7.4")
	implementation("org.postgresql:postgresql")
	implementation("org.liquibase:liquibase-core")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	runtimeOnly("org.jetbrains.kotlin:kotlin-maven-noarg:1.5.21")

	runtimeOnly("com.h2database:h2")

	testImplementation("com.h2database:h2")
	testImplementation("org.dbunit:dbunit:2.7.0")
	testImplementation("org.awaitility:awaitility:4.0.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
