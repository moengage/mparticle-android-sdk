package com.mparticle.kits.mocks

import android.content.Context
import com.mparticle.MParticleOptions
import com.mparticle.internal.CoreCallbacks
import com.mparticle.internal.ReportingManager
import com.mparticle.kits.KitManagerImpl
import org.mockito.Mockito

class MockKitManagerImpl(
    context: Context,
    reportingManager: ReportingManager,
    callback: CoreCallbacks
) : KitManagerImpl(
    context,
    reportingManager,
    callback,
    Mockito.mock(MParticleOptions::class.java)
)