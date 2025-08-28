package com.developersphere.bechat.domain.models

import com.developersphere.bechat.domain.enums.BondState

data class Device(
    val name:String?=null,
    val address:String? = null,
    val bondState: BondState? = null,
    val type:Int? = null,
)