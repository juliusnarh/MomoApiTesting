package com.uclgroupgh.momoapitesting.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.uclgroupgh.momoapitesting.R
import com.uclgroupgh.momoapitesting.databinding.ActivityAccountBalanceBinding
import com.uclgroupgh.momoapitesting.models.ApiToken
import com.uclgroupgh.momoapitesting.network.ApiClient
import com.uclgroupgh.momoapitesting.network.ApiService
import com.uclgroupgh.momoapitesting.util.AndroidUtils
import com.uclgroupgh.momoapitesting.util.Constants
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AccountBalance : AppCompatActivity() {
    lateinit var apiService: ApiService
    lateinit var binding: ActivityAccountBalanceBinding
    var loading = false
    var disAmount = 0.00
    var colAmount = 0.00
    var accessTokenCollection = ""
    var accessTokenDisbursement = ""
    var tokenExpiry = Date().time
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_account_balance)
        binding.apply {
            loading = this@AccountBalance.loading
            cAmount = AndroidUtils.getPreferenceData(this@AccountBalance, Constants.C_AMOUNT, "0.00")!!
            dAmount = AndroidUtils.getPreferenceData(this@AccountBalance, Constants.D_AMOUNT, "0.00")!!
            executePendingBindings()
        }
        setupApiService()
        getTokenCollection()

        binding.paymentButton.setOnClickListener {
            getTokenCollection()
        }
    }

    private fun getBalances() {
        getCollectionBalance()
    }

    private fun getDisbursementBalance() {
        val call = apiService.getAccountBalance(
            product = "disbursement",
            targetEnvironment = Constants.TARGET_ENVIRONMENT,
            accessToken = accessTokenDisbursement
        )

        call.enqueue(object : Callback<com.uclgroupgh.momoapitesting.models.AccountBalance> {
            override fun onFailure(
                call: Call<com.uclgroupgh.momoapitesting.models.AccountBalance>,
                t: Throwable
            ) {
                Log.d(TAG, "getDisbursementBalance:: onError ${t.message}")
                binding.apply {
                    loading = false
                    executePendingBindings()
                }
            }

            override fun onResponse(
                call: Call<com.uclgroupgh.momoapitesting.models.AccountBalance>,
                response: Response<com.uclgroupgh.momoapitesting.models.AccountBalance>
            ) {
                Log.d(
                    TAG,
                    "getDisbursementBalance:: ResponseCode ${response.code()} ::: ${response.isSuccessful}"
                )
                if (response.isSuccessful) {
                    val result = response.body()!!
                    AndroidUtils.savePreferenceData(
                        this@AccountBalance, Constants.D_AMOUNT,
                        "${result.availableBalance}"
                    )
                    binding.apply {
                        dAmount = result.availableBalance
                    loading = false
                        executePendingBindings()
                    }
                }
            }
        })
    }

    private fun getCollectionBalance() {

        val call = apiService.getAccountBalance(
            product = "collection",
            targetEnvironment = Constants.TARGET_ENVIRONMENT,
            accessToken = accessTokenCollection
        )

        call.enqueue(object : Callback<com.uclgroupgh.momoapitesting.models.AccountBalance> {
            override fun onFailure(
                call: Call<com.uclgroupgh.momoapitesting.models.AccountBalance>,
                t: Throwable
            ) {
                Log.d(TAG, "getCollectionBalance:: onError ${t.message}")
                binding.apply {
                    loading = false
                    executePendingBindings()
                }
            }

            override fun onResponse(
                call: Call<com.uclgroupgh.momoapitesting.models.AccountBalance>,
                response: Response<com.uclgroupgh.momoapitesting.models.AccountBalance>
            ) {
                Log.d(
                    TAG,
                    "getCollectionBalance::: ResponseCode ${response.code()} ::: ${response.isSuccessful}"
                )
                if (response.isSuccessful) {
                    val result = response.body()!!
                    binding.apply {
                        AndroidUtils.savePreferenceData(
                            this@AccountBalance, Constants.C_AMOUNT,
                            "${result.availableBalance}"
                        )
                        cAmount = result.availableBalance
                        executePendingBindings()
                    }
                    getDisbursementBalance()
                }
            }
        })
    }

    private fun getTokenCollection() {
        binding.apply {
            loading = true
            executePendingBindings()
        }
        val call = apiService.generateAPIToken2("collection")

        call.enqueue(object : Callback<ApiToken> {
            override fun onFailure(call: Call<ApiToken>, t: Throwable) {
                Log.d(TAG, "getToken:: onError ${t.message}")
            }

            override fun onResponse(call: Call<ApiToken>, response: Response<ApiToken>) {
                Log.d(
                    TAG,
                    "getToken:: ResponseCode ${response.code()} ::: ${response.isSuccessful}"
                )
                if (response.isSuccessful) {
                    val result = response.body()!!

                    accessTokenCollection = "Bearer ${result.access_token!!}"
                    tokenExpiry = Date().time + result.expires_in
                    getTokenDisbursement()
                }
            }
        })
    }
    private fun getTokenDisbursement() {
        binding.apply {
            loading = true
            executePendingBindings()
        }
        val call = apiService.generateAPIToken2("disbursement")

        call.enqueue(object : Callback<ApiToken> {
            override fun onFailure(call: Call<ApiToken>, t: Throwable) {
                Log.d(TAG, "getToken:: onError ${t.message}")
            }

            override fun onResponse(call: Call<ApiToken>, response: Response<ApiToken>) {
                Log.d(
                    TAG,
                    "getToken:: ResponseCode ${response.code()} ::: ${response.isSuccessful}"
                )
                if (response.isSuccessful) {
                    val result = response.body()!!

                    accessTokenDisbursement = "Bearer ${result.access_token!!}"
                    tokenExpiry = Date().time + result.expires_in
                    getBalances()
                }
            }
        })
    }

    private fun setupApiService() {
        apiService = ApiClient.getClient(applicationContext)!!.create(ApiService::class.java)
    }

    companion object {
        val TAG = AccountBalance::class.java.simpleName
    }
}