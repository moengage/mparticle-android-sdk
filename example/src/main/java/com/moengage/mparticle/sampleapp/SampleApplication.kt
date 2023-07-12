package com.moengage.mparticle.sampleapp

import android.app.Application
import com.moengage.core.DataCenter
import com.moengage.core.MoEngage
import com.moengage.core.model.IntegrationPartner
import com.moengage.mparticle.kits.MoEngageKit
import com.mparticle.MParticle
import com.mparticle.MParticleOptions
import com.mparticle.kits.KitOptions

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeMoEngageSdk()
        initializeMParticleSdk()
    }

    private fun initializeMParticleSdk() {
        val options = MParticleOptions.builder(this)
            .credentials(M_PARTICLE_API_KEY, M_PARTICLE_API_SECRET)
            .environment(MParticle.Environment.Development)
            .logLevel(MParticle.LogLevel.VERBOSE)
            .configuration(KitOptions().addKit(MOENGAGE_KIT_ID, MoEngageKit::class.java))
            .build()

        MParticle.start(options)

        // Register for push notifications
        MParticle.getInstance()?.Messaging()?.enablePushNotifications(FCM_SENDER_ID)

        // disable push notifications
        // MParticle.getInstance()?.Messaging()?.disablePushNotifications()
    }

    private fun initializeMoEngageSdk() {
        val moEngage = MoEngage.Builder(this, MOENGAGE_APP_ID, DataCenter.DATA_CENTER_1)
            .enablePartnerIntegration(IntegrationPartner.M_PARTICLE)
            .build()

        MoEngage.initialiseDefaultInstance(moEngage)
    }
}