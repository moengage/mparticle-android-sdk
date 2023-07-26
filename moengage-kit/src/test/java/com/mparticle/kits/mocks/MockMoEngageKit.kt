package com.mparticle.kits.mocks

import android.content.Context
import com.moengage.core.internal.initialisation.InitConfig
import com.moengage.core.internal.model.InstanceMeta
import com.moengage.core.internal.model.SdkInstance
import com.moengage.core.internal.remoteconfig.getDefaultRemoteConfig
import com.moengage.mparticle.kits.MoEngageKit
import com.mparticle.internal.CoreCallbacks
import com.mparticle.internal.ReportingManager
import com.mparticle.kits.KitConfiguration
import com.mparticle.kits.MoEngage_APP_ID
import org.mockito.Mockito

/**
 * Mock [MoEngageKit] Class
 */
class MockMoEngageKit : MoEngageKit() {

    init {
        kitManager = MockKitManagerImpl(
            Mockito.mock(Context::class.java),
            Mockito.mock(ReportingManager::class.java),
            Mockito.mock(CoreCallbacks::class.java)
        )
    }

    override fun getConfiguration(): KitConfiguration {
        return Mockito.mock(KitConfiguration::class.java)
    }

    override fun getSdkInstance(appId: String): SdkInstance {
        return SdkInstance(
            instanceMeta = InstanceMeta(MoEngage_APP_ID, true),
            initConfig = InitConfig(MoEngage_APP_ID),
            config = getDefaultRemoteConfig()
        )
    }
}