plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
}

group = "com.example.joyread"
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
    implementation(platform(libs.spring.cloud.alibaba.dependencies))
    implementation(libs.spring.cloud.alibaba.nacos)
    implementation(platform(libs.spring.cloud.dependencies))
    implementation(platform(libs.spring.boot.dependencies))
    implementation(libs.spring.boot.webflux)
    implementation(libs.jackson.kotlin)
    implementation(libs.reactor.kotlin)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.spring.doc.webflux.api)
    testImplementation(libs.spring.boot.test)
    testImplementation(libs.reactor.test)
    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
