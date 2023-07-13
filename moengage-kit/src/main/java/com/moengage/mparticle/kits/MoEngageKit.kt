package com.moengage.mparticle.kits

import android.app.Application
import android.content.Context
import android.content.Intent
import android.location.Location
import com.moengage.core.MoECoreHelper
import com.moengage.core.Properties
import com.moengage.core.analytics.MoEAnalyticsHelper
import com.moengage.core.disableDataTracking
import com.moengage.core.enableDataTracking
import com.moengage.core.internal.INSTALL_REFERRER_EVENT
import com.moengage.core.internal.USER_ATTRIBUTE_USER_FIRST_NAME
import com.moengage.core.internal.USER_ATTRIBUTE_USER_GENDER
import com.moengage.core.internal.USER_ATTRIBUTE_USER_LAST_NAME
import com.moengage.core.internal.USER_ATTRIBUTE_USER_MOBILE
import com.moengage.core.internal.integrations.MoEIntegrationHelper
import com.moengage.core.internal.logger.Logger
import com.moengage.core.internal.model.IntegrationMeta
import com.moengage.core.model.IntegrationPartner
import com.moengage.firebase.MoEFireBaseHelper
import com.moengage.pushbase.MoEPushHelper
import com.mparticle.MPEvent
import com.mparticle.MParticle.IdentityType
import com.mparticle.MParticle.UserAttributes.FIRSTNAME
import com.mparticle.MParticle.UserAttributes.GENDER
import com.mparticle.MParticle.UserAttributes.LASTNAME
import com.mparticle.MParticle.UserAttributes.MOBILE_NUMBER
import com.mparticle.consent.ConsentState
import com.mparticle.identity.MParticleUser
import com.mparticle.kits.FilteredIdentityApiRequest
import com.mparticle.kits.FilteredMParticleUser
import com.mparticle.kits.KitIntegration
import com.mparticle.kits.KitIntegration.EventListener
import com.mparticle.kits.KitIntegration.IdentityListener
import com.mparticle.kits.KitIntegration.PushListener
import com.mparticle.kits.KitIntegration.UserAttributeListener
import com.mparticle.kits.KitUtils
import com.mparticle.kits.ReportingMessage
import java.lang.Exception

/**
 * MoEngage Kit to integrate MoEngage Android SDK with mParticle Android SDK
 */
open class MoEngageKit :
    KitIntegration(), IdentityListener, UserAttributeListener, EventListener, PushListener {

    private lateinit var moEngageIntegrationHelper: MoEIntegrationHelper
    private lateinit var moEngageAppId: String

    override fun getName(): String = KIT_NAME

    public override fun onKitCreate(
        settings: MutableMap<String, String?>,
        context: Context
    ): MutableList<ReportingMessage> {
        val appId = settings[MOE_APP_ID_KEY]
        require(appId != null && !KitUtils.isEmpty(appId)) { "MoEngage App Id can't be empty" }
        moEngageIntegrationHelper = MoEIntegrationHelper(context, IntegrationPartner.M_PARTICLE)
        moEngageIntegrationHelper.initialize(appId, context.applicationContext as Application)
        MoEIntegrationHelper.addIntegrationMeta(
            IntegrationMeta(
                INTEGRATION_META_TYPE,
                BuildConfig.MOENGAGE_KIT_VERSION
            ),
            appId
        )

        Logger.print { "$tag onKitCreate(): mParticle Integration Initialised" }
        this.moEngageAppId = appId
        val messages: MutableList<ReportingMessage> = ArrayList()
        messages.add(
            ReportingMessage(
                this,
                ReportingMessage.MessageType.APP_STATE_TRANSITION,
                System.currentTimeMillis(),
                null
            )
        )
        return messages
    }

    override fun onIdentifyCompleted(
        mParticleUser: MParticleUser,
        identityApiRequest: FilteredIdentityApiRequest?
    ) {
        updateUserIds(false, mParticleUser)
    }

    override fun onLoginCompleted(
        mParticleUser: MParticleUser,
        identityApiRequest: FilteredIdentityApiRequest?
    ) {
        updateUserIds(false, mParticleUser)
    }

    override fun onModifyCompleted(
        mParticleUser: MParticleUser,
        identityApiRequest: FilteredIdentityApiRequest?
    ) {
        updateUserIds(true, mParticleUser)
    }

    private fun updateUserIds(isUserModified: Boolean, mParticleUser: MParticleUser) {
        val email = mParticleUser.userIdentities[IdentityType.Email]
        val phone = mParticleUser.userIdentities[IdentityType.MobileNumber]
        val uniqueId = mParticleUser.userIdentities[IdentityType.CustomerId]

        email?.let { MoEAnalyticsHelper.setEmailId(context, it, moEngageAppId) }
        phone?.let { MoEAnalyticsHelper.setEmailId(context, it, moEngageAppId) }
        uniqueId?.let { id ->
            Logger.print { "$tag updateUserIds(): isUserModified-$isUserModified, UniqueId-$uniqueId" }
            if (isUserModified) {
                MoEAnalyticsHelper.setAlias(context, id)
            } else {
                MoEAnalyticsHelper.setUniqueId(context, id, moEngageAppId)
            }
        }
    }

    override fun onLogoutCompleted(
        mParticleUser: MParticleUser,
        identityApiRequest: FilteredIdentityApiRequest?
    ) {
        MoECoreHelper.logoutUser(context, moEngageAppId)
    }

    override fun onUserIdentified(mParticleUser: MParticleUser) {
        Logger.print { "$tag onUserIdentified(): mParticle Id: ${mParticleUser.id}" }
        moEngageIntegrationHelper.trackAnonymousId(mParticleUser.id.toString(), moEngageAppId)
    }

    override fun setLocation(location: Location) {
        MoEAnalyticsHelper.setLocation(
            context,
            location.latitude,
            location.longitude,
            moEngageAppId
        )
    }

    override fun setOptOut(optedOut: Boolean): MutableList<ReportingMessage> {
        Logger.print { "$tag setOptOut(): is tracking opted in-$optedOut" }
        if (optedOut) {
            disableDataTracking(context, moEngageAppId)
        } else {
            enableDataTracking(context, moEngageAppId)
        }

        val messages: MutableList<ReportingMessage> = ArrayList()
        messages.add(
            ReportingMessage(
                this,
                ReportingMessage.MessageType.OPT_OUT,
                System.currentTimeMillis(),
                null
            )
        )
        return messages
    }

    override fun setInstallReferrer(intent: Intent) {
        val installReferrerProperties = Properties()
        installReferrerProperties.addAttribute(REFERRER_EXTRA, intent.dataString)
        MoEAnalyticsHelper.trackEvent(
            context,
            INSTALL_REFERRER_EVENT,
            installReferrerProperties,
            moEngageAppId
        )
    }

    override fun onIncrementUserAttribute(
        key: String,
        incrementedBy: Number,
        value: String,
        user: FilteredMParticleUser
    ) {
    }

    override fun onRemoveUserAttribute(key: String, user: FilteredMParticleUser) {}

    override fun onSetUserTag(key: String, user: FilteredMParticleUser) {}

    override fun onConsentStateUpdated(
        oldState: ConsentState,
        newState: ConsentState,
        user: FilteredMParticleUser
    ) {
    }

    override fun onSetUserAttribute(
        attributeKey: String,
        attributeValue: Any,
        user: FilteredMParticleUser
    ) {
        trackUserAttribute(attributeKey, attributeValue)
    }

    override fun onSetUserAttributeList(
        attributeKey: String,
        attributeValueList: List<String>,
        user: FilteredMParticleUser
    ) {
        trackUserAttribute(attributeKey, attributeValueList.toTypedArray())
    }

    override fun onSetAllUserAttributes(
        userAttributes: Map<String, String>,
        userAttributeLists: Map<String, List<String>>,
        user: FilteredMParticleUser
    ) {
        if (!kitPreferences.getBoolean(PREF_KEY_HAS_SYNCED_ATTRIBUTES, false)) {
            for ((attributeKey, attributeValue) in userAttributes) {
                trackUserAttribute(attributeKey, attributeValue)
            }
            for ((attributeKey, attributeValue) in userAttributeLists) {
                trackUserAttribute(attributeKey, attributeValue.toTypedArray())
            }
            kitPreferences.edit().putBoolean(PREF_KEY_HAS_SYNCED_ATTRIBUTES, true).apply()
        }
    }

    override fun supportsAttributeLists(): Boolean = true

    private fun trackUserAttribute(attributeKey: String, attributeValue: Any) {
        Logger.print { "$tag trackUserAttribute(): Key-$attributeKey, Value-$attributeKey" }
        val mappedKey = attributeKeyMap[attributeKey] ?: attributeKey
        MoEAnalyticsHelper.setUserAttribute(context, mappedKey, attributeValue, moEngageAppId)
    }

    override fun leaveBreadcrumb(breadcrumb: String): List<ReportingMessage> = emptyList()

    override fun logError(
        message: String,
        errorAttributes: MutableMap<String, String>
    ): List<ReportingMessage> = emptyList()

    override fun logException(
        exception: Exception,
        exceptionAttributes: MutableMap<String, String>,
        message: String
    ): List<ReportingMessage> = emptyList()

    override fun logScreen(
        screenName: String,
        screenAttributes: MutableMap<String, String>
    ): List<ReportingMessage> = emptyList()

    override fun logEvent(event: MPEvent): MutableList<ReportingMessage> {
        if (event.eventName.isBlank()) return mutableListOf()

        val properties = Properties()
        event.customAttributeStrings?.let { customAttributes ->
            for ((customAttributesKey, customAttributesValue) in customAttributes) {
                properties.addAttribute(customAttributesKey, customAttributesValue)
            }
        }
        MoEAnalyticsHelper.trackEvent(context, event.eventName, properties, moEngageAppId)

        val messages: MutableList<ReportingMessage> = ArrayList()
        messages.add(ReportingMessage.fromEvent(this, event))
        return messages
    }

    override fun onPushRegistration(instanceId: String, senderId: String): Boolean {
        MoEFireBaseHelper.getInstance().passPushToken(context, instanceId, moEngageAppId)
        return true
    }

    override fun willHandlePushMessage(intent: Intent): Boolean {
        var isFromMoEngage = false
        intent.extras?.let { bundle ->
            isFromMoEngage = MoEPushHelper.getInstance().isFromMoEngagePlatform(bundle)
        }

        return isFromMoEngage
    }

    override fun onPushMessageReceived(context: Context, pushIntent: Intent) {
        pushIntent.extras?.let { bundle ->
            MoEFireBaseHelper.getInstance().passPushPayload(context, bundle)
        }
    }

    companion object {
        private const val tag = "MoEngageKit_${BuildConfig.MOENGAGE_KIT_VERSION}"

        const val KIT_NAME = "MoEngage"
        private const val MOE_APP_ID_KEY = "appId"
        private const val INTEGRATION_META_TYPE = "mparticle_native"
        private const val REFERRER_EXTRA = "referrer"
        private const val PREF_KEY_HAS_SYNCED_ATTRIBUTES = "moengage::has_synced_attributes"

        private val attributeKeyMap: Map<String, String> = mapOf(
            MOBILE_NUMBER to USER_ATTRIBUTE_USER_MOBILE,
            GENDER to USER_ATTRIBUTE_USER_GENDER,
            FIRSTNAME to USER_ATTRIBUTE_USER_FIRST_NAME,
            LASTNAME to USER_ATTRIBUTE_USER_LAST_NAME
        )
    }
}