package com.mparticle.kits.mocks

import android.content.Context
import com.moengage.mparticle.kits.MoEngageKit
import com.mparticle.internal.CoreCallbacks
import com.mparticle.internal.ReportingManager
import com.mparticle.kits.KitConfiguration
import org.mockito.Mockito

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
}