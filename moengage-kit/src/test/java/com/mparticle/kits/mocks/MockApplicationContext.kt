package com.mparticle.kits.mocks

import android.app.Application
import android.content.Context

/**
 * Mock Application Class
 */
class MockApplicationContext : Application() {

    override fun getApplicationContext(): Context {
        return this
    }
}