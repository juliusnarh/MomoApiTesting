/*
 * Copyright (c) Universal Consulting Links Ghana 2019. 
 */




package com.uclgroupgh.momoapitesting.util

/**
 * Created by ravi on 20/02/18.
 */

object Constants {
    const val MAIN_PREFERENCE = "momo_pref"
    const val USER_KEY = "user_key"
    const val BASE_URL_2 = "https://proxy.momoapi.mtn.com/"
    const val BASE_URL_SANDBOX = "https://sandbox.momodeveloper.mtn.com/"
    const val BASE_URL = "https://uclgroupgh.com/WuLucky/mobileManager/momo/"
    const val ENDPOINT_DISBURSEMENT = "/api/tracking?key=1234"
    const val COLLECTION_SUBSCRIPTION_KEY = "9dfacffb399048958c0ad479a2332a4e"
    const val API_USER = "8d0ecaf3-e9a9-49a6-8af9-7b110c5eb3d3"
    const val API_USER_KEY = "bb8f9932c6d046e786aa36046960d05c"
    const val X_REFERENCE_ID = "x_ref_id"
    const val API_KEY = "api_key"
    const val GAME_NAME = "game_number"
    const val TARGET_ENVIRONMENT = "mtnghana"
    const val ACCESS_TOKEN = "api_token"
    const val D_AMOUNT = "d_amount"
    const val C_AMOUNT = "c_amount"
    const val CURRENCY = "GHS"
    const val MSISDN = "msisdn"
    const val ERROR = "error"
    val DATE_PATTERNS = arrayListOf(
        "EEE, MMM dd, yyyy hh:mm a"
        , "dd/MM/yyyy",
        "ddMMyyyy",
        "EEEE, MMM dd, yyyy hh:mm a"
        , "dd"
        , "MMMM"
        , "MM"
        , "yyyy"
    )
    val RESPONSE_CODES: HashMap<Int, String> = hashMapOf(
        200 to "OK",
        401 to "Unauthorized",
        500 to "Internal Server Error"
    )
    val GAMES_NUMBER_LIMIT: HashMap<String, Int> = hashMapOf(
        "wulucky 1" to 1,
        "wulucky 2" to 2,
        "wulucky 3" to 3,
        "wulucky 4" to 4,
        "wulucky 5" to 5,
        "perm 2" to 2,
        "perm 3" to 4,
        "banker" to 1
    )

    val GAMES_MULTIPLIER: HashMap<String, Int> = hashMapOf(
        "wulucky 1" to 40,
        "wulucky 2" to 240,
        "wulucky 3" to 2100,
        "wulucky 4" to 2550,
        "wulucky 5" to 3500,
        "perm 1" to 5000,
        "perm 2" to 10000,
        "banker" to 50000
    )
}
