package com.moengage.mparticle.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<AppCompatButton>(R.id.loginButton).setOnClickListener {
            MParticleRequestHandler.logInUser()
        }
        findViewById<AppCompatButton>(R.id.enableDataTrackingButton).setOnClickListener {
            MParticleRequestHandler.enableDataTracking()
        }
        findViewById<AppCompatButton>(R.id.disableDataTrackingButton).setOnClickListener {
            MParticleRequestHandler.disableDataTracking()
        }
        findViewById<AppCompatButton>(R.id.locationButton).setOnClickListener {
            MParticleRequestHandler.trackUserLocation()
        }
        findViewById<AppCompatButton>(R.id.installReferrerButton).setOnClickListener {
            MParticleRequestHandler.trackInstallReferrerEvent()
        }
        findViewById<AppCompatButton>(R.id.customEventButton).setOnClickListener {
            MParticleRequestHandler.trackCustomEvent()
        }
        findViewById<AppCompatButton>(R.id.userAttributeButton).setOnClickListener {
            MParticleRequestHandler.trackUserAttributes()
        }
        findViewById<AppCompatButton>(R.id.modifyButton).setOnClickListener {
            MParticleRequestHandler.modifyUserAttributes()
        }
        findViewById<AppCompatButton>(R.id.logoutButton).setOnClickListener {
            MParticleRequestHandler.logoutUser()
        }
    }
}