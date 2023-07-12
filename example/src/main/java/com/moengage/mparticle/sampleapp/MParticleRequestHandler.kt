package com.moengage.mparticle.sampleapp

import android.location.Location
import android.util.Log
import com.mparticle.MPEvent
import com.mparticle.MParticle
import com.mparticle.MParticle.EventType
import com.mparticle.identity.IdentityApiRequest

internal object MParticleRequestHandler {

    private const val TAG = "MParticleRequestHandler"

    fun logInUser() {
        val identityRequest = IdentityApiRequest.withEmptyUser().run {
            email("moengage@test.com")
            customerId("loginUserId")
            userIdentity(MParticle.IdentityType.Other, "moengage-mparticle-other-id-1")
            build()
        }

        MParticle.getInstance()?.Identity()
            ?.login(identityRequest)
            ?.addSuccessListener { identityApiResult ->
                Log.d(TAG, "identifyTask Id: ${identityApiResult.user.id}")
            }?.addFailureListener { identityHttpResponse ->
                Log.d(TAG, "identifyTask Error: ${identityHttpResponse?.errors}")
            }
    }

    fun enableDataTracking() {
        MParticle.getInstance()?.optOut = true
    }

    fun disableDataTracking() {
        MParticle.getInstance()?.optOut = false
    }

    fun trackUserLocation() {
        val location = Location("dummy")
        location.latitude = 70.90
        location.longitude = 89.00
        MParticle.getInstance()?.setLocation(location)
    }

    fun trackInstallReferrerEvent() {
        MParticle.getInstance()?.installReferrer = "CustomInstallReferrerString"
    }

    fun trackCustomEvent() {
        // Event With Properties
        val customAttributes = mapOf(
            "category" to "Destination Intro",
            "title" to "Paris"
        )

        val eventWithProperties = MPEvent.Builder("Custom Event With Prop", EventType.Navigation)
            .customAttributes(customAttributes)
            .build()

        MParticle.getInstance()?.logEvent(eventWithProperties)

        // Event Without Properties
        val eventWithoutProperties = MPEvent.Builder("Custom Event", EventType.Navigation)
            .build()

        MParticle.getInstance()?.logEvent(eventWithoutProperties)
    }

    fun trackUserAttributes() {
        val currentUser = MParticle.getInstance()?.Identity()?.currentUser

        // Track Normal Attributes
        currentUser?.setUserAttribute("top_region", "Europe")

        // Track Standard Attributes
        currentUser?.setUserAttribute("Age", 99)
        currentUser?.setUserAttribute("FirstName", "First")
        currentUser?.setUserAttribute("LastName", "Last")
        currentUser?.setUserAttribute("Gender", "Male")
        currentUser?.setUserAttribute("Mobile", 1234567890)
        currentUser?.setUserAttribute("Address", "Koramangala")
        currentUser?.setUserAttribute("City", "Bengaluru")
        currentUser?.setUserAttribute("State", "Karnataka")
        currentUser?.setUserAttribute("Zip", 5600031)
        currentUser?.setUserAttribute("Country", "India")

        // Track In List
        val attributeList = ArrayList<String>()
        attributeList.add("Rome")
        attributeList.add("San Juan")
        attributeList.add("Denver")
        currentUser?.setUserAttributeList("destinations", attributeList)
    }

    fun modifyUserAttributes() {
        val modifyRequest = IdentityApiRequest.withUser(MParticle.getInstance()?.Identity()?.currentUser)
            .email("moengageChangedEmail@test.com")
            .build()
        MParticle.getInstance()?.Identity()?.modify(modifyRequest)
            ?.addSuccessListener { identityApiResult ->
                Log.d(TAG, "MoEngage Id Changed ${identityApiResult.user.userAttributes}")
            }?.addFailureListener { identityHttpResponse ->
                Log.d(TAG, "MoEngage Id Changed Error ${identityHttpResponse?.errors}")
            }
    }

    fun logoutUser() {
        MParticle.getInstance()?.Identity()
            ?.logout(IdentityApiRequest.withEmptyUser().build())
            ?.addSuccessListener {
                Log.d(TAG, "Logout Completed")
            }?.addFailureListener {
                Log.d(TAG, "Logout Failed Error")
            }
    }
}