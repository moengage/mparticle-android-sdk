package com.mparticle.kits.mocks

import com.mparticle.MParticle
import com.mparticle.UserAttributeListenerType
import com.mparticle.consent.ConsentState
import com.mparticle.identity.MParticleUser
import com.mparticle.kits.M_PARTICLE_ID

/**
 * Mock [MParticleUser] Class
 */
class MockMParticleUser(
    private val identities: Map<MParticle.IdentityType, String>
) : MParticleUser {

    override fun getId(): Long = M_PARTICLE_ID

    override fun getUserAttributes(): Map<String, Any> = mapOf()

    override fun getUserAttributes(p0: UserAttributeListenerType?): MutableMap<String, Any>? = null

    override fun setUserAttributes(map: Map<String, Any>): Boolean = false

    override fun getUserIdentities(): Map<MParticle.IdentityType, String> = identities

    override fun setUserAttribute(s: String, o: Any): Boolean = false

    override fun setUserAttributeList(s: String, o: Any): Boolean = false

    override fun incrementUserAttribute(p0: String, p1: Number?): Boolean = false

    override fun removeUserAttribute(s: String): Boolean = false

    override fun setUserTag(s: String): Boolean = false

    override fun getConsentState(): ConsentState = ConsentState.withConsentState("").build()

    override fun setConsentState(consentState: ConsentState?) {}

    override fun isLoggedIn(): Boolean = false

    override fun getFirstSeenTime(): Long = 0

    override fun getLastSeenTime(): Long = 0
}