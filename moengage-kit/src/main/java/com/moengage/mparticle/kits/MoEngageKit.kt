package com.moengage.mparticle.kits

import android.content.Context
import com.mparticle.kits.KitIntegration
import com.mparticle.kits.ReportingMessage

class MoEngageKit : KitIntegration() {
    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun onKitCreate(
        settings: MutableMap<String, String>?,
        context: Context?
    ): MutableList<ReportingMessage> {
        TODO("Not yet implemented")
    }

    override fun setOptOut(optedOut: Boolean): MutableList<ReportingMessage> {
        TODO("Not yet implemented")
    }
}