@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(moengageInternal.plugins.plugin.android.app) apply false
    alias(moengageInternal.plugins.plugin.android.lib) apply false
    alias(moengageInternal.plugins.plugin.kotlin.android) apply false
    alias(moengageInternal.plugins.plugin.ktlint) apply false
    alias(libs.plugins.plugin.google.services) apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}