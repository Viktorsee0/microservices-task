plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.specificgroup"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	implementation("org.springframework.boot:spring-boot-starter-validation")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.postgresql:postgresql")

	implementation("org.springframework.boot:spring-boot-starter-amqp")

	implementation("net.logstash.logback:logstash-logback-encoder:7.2")

	testImplementation("org.springframework.amqp:spring-rabbit-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
