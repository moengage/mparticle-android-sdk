package com.mparticle.kits.mocks

import com.mparticle.MParticle
import com.mparticle.identity.IdentityApi
import com.mparticle.identity.MParticleUser
import com.mparticle.kits.CUSTOMER_ID
import com.mparticle.kits.EMAIL

class MockIdentityApi: IdentityApi() {

    override fun getCurrentUser(): MParticleUser {
        val identity = HashMap<MParticle.IdentityType, String>()
        identity[MParticle.IdentityType.Email] = EMAIL
        identity[MParticle.IdentityType.CustomerId] = CUSTOMER_ID

        return MockMParticleUser(identity)
    }
}