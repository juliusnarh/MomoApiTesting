package com.uclgroupgh.momoapitesting.models

import com.google.gson.annotations.SerializedName

class AccessToken {
    @SerializedName("access_token")
    var accessToken: String? = null

    @SerializedName("token_type")
    var tokenType: String? = null
}