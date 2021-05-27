/*
 * Copyright (c) Universal Consulting Links Ghana 2019.
 */

package com.uclgroupgh.momoapitesting.network

import com.uclgroupgh.momoapitesting.models.*
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by ravi on 31/01/18.
 */

interface ApiService {

    @GET
    fun getAyawasoAccount(@Url url: String): Single<List<User>>

    @POST("token.php")
    fun generateAPIToken2(@Query("api_user") apiUser: String, @Query("api_user_key") apiUserKey: String, @Query("subscription_key") subscriptionKey: String): Call<ApiToken>

    @POST("token.php")
    fun generateAPIToken2(@Query("product") product: String): Call<ApiToken>

    @POST("gateway/checkout")
    fun initiatePayment(
        @Query("merchant_key") merchantKey: String,
        @Query("invoice_id") invoiceID: String,
        @Query("total") total: String,
        @Query("extra_mobile") customerMobile: String,
        @Query("extra_email") customerEmail: String,
        @Query("extra_name") custumerName: String,
        @Query("description") description: String
    ): Call<ApiToken>

    @POST("requestToPay.php")
    fun requestToPay(
        @Query("subscription_key") subscriptionKey: String,
        @Query("reference_id") referenceID: String,
        @Query("target_environment") targetEnvironment: String,
        @Query("token") accessToken: String,
        @Query("bodyz")body: String
        ): Call<Void>

    @POST("requestToPay.php")
    fun requestToPay(
        @Query("reference_id") referenceID: String,
        @Query("target_environment") targetEnvironment: String,
        @Query("token") accessToken: String,
        @Query("bodyz")body: String
        ): Call<Void>

    @GET("verifyPayment.php")
    fun paymentStatus(
        @Query("subscription_key") subscriptionKey: String,
        @Query("target_environment") targetEnvironment: String,
        @Query("token") accessToken: String,
        @Query("reference_id") referenceId: String
        ): Call<RequestToPayStatus>

    @GET("verifyPayment.php")
    fun paymentStatus(
        @Query("target_environment") targetEnvironment: String,
        @Query("token") accessToken: String,
        @Query("reference_id") referenceId: String
        ): Call<RequestToPayStatus>

    @GET("checkBalance.php")
    fun getAccountBalance(
        @Query("subscription_key") subscriptionKey: String,
        @Query("product") product: String,
        @Query("target_environment") targetEnvironment: String,
        @Query("token") accessToken: String
        ): Call<AccountBalance>

    @GET("checkBalance.php")
    fun getAccountBalance(
        @Query("target_environment") targetEnvironment: String,
        @Query("token") accessToken: String,
        @Query("product") product: String
        ): Call<AccountBalance>

    @GET("verifyAccountHolder.php")
    fun verifyAccountHolder(
        @Query("target_environment") targetEnvironment: String,
        @Query("account_holder_id") accHolderId: String,
        @Query("account_holder_id_type") accHolderIdType: String,
        @Query("token") accessToken: String
        ): Call<AccountHolder>

    @GET("verifyAccountHolder.php")
    fun verifyAccountHolder(
        @Query("subscription_key") subscriptionKey: String,
        @Query("target_environment") targetEnvironment: String,
        @Query("account_holder_id") accHolderId: String,
        @Query("account_holder_id_type") accHolderIdType: String,
        @Query("token") accessToken: String
        ): Call<AccountHolder>
}
