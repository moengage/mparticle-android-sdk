package com.mparticle.kits

import android.content.Context
import com.moengage.mparticle.kits.KIT_NAME
import com.moengage.mparticle.kits.MoEngageKit
import com.mparticle.MPEvent
import com.mparticle.MParticle
import com.mparticle.identity.MParticleUser
import com.mparticle.kits.mocks.MockApplicationContext
import com.mparticle.kits.mocks.MockMParticleUser
import com.mparticle.kits.mocks.MockMoEngageKit
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

/**
 * Test Class to test the functionality of [MoEngageKit]
 */
internal class MoEngageKitTest {

    private val mockMoEngageKit = MockMoEngageKit()

    @Before
    fun setup() {
        // Setup the MockMoEngageKit instance to use later
        mockMoEngageKit.onKitCreate(
            hashMapOf(MOE_APP_ID_KEY to MoEngage_APP_ID),
            MockApplicationContext()
        )
    }

    /**
     * Description: Test getName of MoEngageKit
     * Excepted: kit name should equal to [KIT_NAME]
     */
    @Test
    fun testGetName() {
        Assert.assertEquals(mockMoEngageKit.name, KIT_NAME)
    }

    /**
     * Description: Test onKitCreate method with invalid setting.
     * Expected Result: Should throw exception as App Id is mandatory
     */
    @Test
    @Throws(Exception::class)
    fun testOnKitCreateWithInValidSetting() {
        var exception: Exception? = null
        try {
            mockMoEngageKit.onKitCreate(hashMapOf(), Mockito.mock(Context::class.java))
        } catch (e: Exception) {
            exception = e
        }
        Assert.assertNotNull(exception)
    }

    /**
     * Description: Test onKitCreate method with valid setting.
     * Expected Result: Should process the request properly without any exception
     */
    @Test
    @Throws(Exception::class)
    fun testOnKitCreateWithValidSetting() {
        var exception: Exception? = null
        try {
            MockMoEngageKit().onKitCreate(
                hashMapOf(MOE_APP_ID_KEY to MoEngage_APP_ID),
                MockApplicationContext()
            )
        } catch (e: Exception) {
            exception = e
        }
        Assert.assertNull(exception)
    }

    /**
     * Description: Test User Identity management methods with invalid user data
     * Expected Result: Should not throw any exception even with invalid data
     */
    @Test
    fun testUserIdentityWithInValidData() {
        var exception: Exception? = null
        try {
            mockMoEngageKit.onIdentifyCompleted(Mockito.mock(MParticleUser::class.java), null)
            mockMoEngageKit.onLoginCompleted(Mockito.mock(MParticleUser::class.java), null)
            mockMoEngageKit.onLogoutCompleted(Mockito.mock(MParticleUser::class.java), null)
            mockMoEngageKit.onModifyCompleted(Mockito.mock(MParticleUser::class.java), null)
        } catch (e: Exception) {
            exception = e
        }
        Assert.assertNull(exception)
    }

    /**
     * Description: Test User Identity management methods with valid user data
     * Expected Result: Should process the request without any exception
     */
    @Test
    fun testUserIdentityWithValidData() {
        var exception: Exception? = null
        try {
            val identity = HashMap<MParticle.IdentityType, String>()
            identity[MParticle.IdentityType.Email] = EMAIL
            identity[MParticle.IdentityType.CustomerId] = CUSTOMER_ID

            mockMoEngageKit.onIdentifyCompleted(MockMParticleUser(identity), null)
            mockMoEngageKit.onLoginCompleted(MockMParticleUser(identity), null)
            mockMoEngageKit.onLogoutCompleted(MockMParticleUser(identity), null)
            mockMoEngageKit.onModifyCompleted(MockMParticleUser(identity), null)
        } catch (e: Exception) {
            exception = e
        }
        Assert.assertNull(exception)
    }

    /**
     * Description: Test Event Log without any event attributes
     * Expected Result: should return proper list of [ReportingMessage]
     */
    @Test
    fun logEventWithWithoutAttributes() {
        val eventWithProperties = MPEvent.Builder(
            "Custom Event",
            MParticle.EventType.Other
        ).build()
        val result: List<ReportingMessage?> = mockMoEngageKit.logEvent(eventWithProperties)
        Assert.assertTrue(result.isNotEmpty())
    }

    /**
     * Description: Test Event Log with event attributes
     * Expected Result: should return proper list of [ReportingMessage]
     */
    @Test
    fun logEventWithAttributes() {
        val eventWithProperties = MPEvent.Builder(
            "Custom Event",
            MParticle.EventType.Other
        ).customAttributes(
            mapOf(
                "category" to "Destination Intro",
                "title" to "Paris"
            )
        ).build()

        val result: List<ReportingMessage?> = mockMoEngageKit.logEvent(eventWithProperties)
        Assert.assertTrue(result.isNotEmpty())
    }

    /**
     * Description: Test Event Log with invalid event name
     * Expected Result: should return empty list of [ReportingMessage]
     */
    @Test
    fun logEventWithInvalidData() {
        val result = mockMoEngageKit.logEvent(MPEvent.Builder("").build())
        Assert.assertTrue(result.isEmpty())
    }
}