@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(moengageInternal.plugins.plugin.android.app) apply false
    alias(moengageInternal.plugins.plugin.android.lib) apply false
    alias(moengageInternal.plugins.plugin.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("org.jlleitschuh.gradle.ktlint") version("11.3.1")
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}