package com.uclgroupgh.momoapitesting.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.ilhasoft.support.validation.Validator
import com.google.gson.Gson
import com.uclgroupgh.momoapitesting.databinding.FragmentPaymentBinding
import com.uclgroupgh.momoapitesting.models.AccountHolder
import com.uclgroupgh.momoapitesting.models.ApiToken
import com.uclgroupgh.momoapitesting.models.PaymentReceipt
import com.uclgroupgh.momoapitesting.models.RequestToPay
import com.uclgroupgh.momoapitesting.network.ApiService
import com.uclgroupgh.momoapitesting.util.AndroidUtils
import com.uclgroupgh.momoapitesting.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PaymentFragment : Fragment() {
    lateinit var binding: FragmentPaymentBinding
    lateinit var validator: Validator
    lateinit var apiService: ApiService
    private val safeArgs: PaymentFragmentArgs by navArgs()
    private var gameReceipt = PaymentReceipt(
        selectedNumbers = safeArgs.selectedNumbers,
        gamePlayed = safeArgs.gamePlayed,
        reference = AndroidUtils.getUUID()
    )
    private val requestToPay = RequestToPay()
    private var apiToken = ApiToken()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        validator = Validator(binding)
        binding.apply {
            receipt = gameReceipt
            executePendingBindings()
        }
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        binding.amount.doOnTextChanged { text, _, _, _ ->
            val textVal = text.toString().toInt()
            binding.receipt!!.possibleWinnings =
                (textVal * Constants.GAMES_MULTIPLIER[gameReceipt.gamePlayed]!!).toString()
            binding.executePendingBindings()
        }

        binding.momoNumber.doAfterTextChanged {
            if (validator.validate()) {
                runPaymentRequests()
            } else {
                //TODO TOAST
            }
        }

        binding.paymentButton.setOnClickListener {

        }
    }

    private fun runPaymentRequests() {
        verifyAccountHolder()
    }

    private fun verifyAccountHolder() {
        val call = apiService.verifyAccountHolder(
            subscriptionKey = Constants.COLLECTION_SUBSCRIPTION_KEY,
            accHolderId = "233${gameReceipt.playerNumber.substring(1)}",
            accHolderIdType = Constants.MSISDN.toUpperCase(Locale.ENGLISH),
            targetEnvironment = Constants.TARGET_ENVIRONMENT,
            accessToken = "Bearer ${apiToken.access_token}"
        )

        call.enqueue(object : Callback<AccountHolder> {
            override fun onFailure(call: Call<AccountHolder>, t: Throwable) {
                Log.d(TAG, "verifyAccountHolder onError ${t.message}")
            }

            override fun onResponse(call: Call<AccountHolder>, response: Response<AccountHolder>) {
                Log.d(
                    TAG,
                    "verifyAccountHolder: ResponseCode ${response.code()} ::: ${response.isSuccessful}"
                )
                if (response.isSuccessful) {
                    val result = response.body()!!
                    if (result.result)
                        getAccessToken()
                    else {
                        //TODO TOAST

                    }

                }

            }
        })

    }

    private fun getAccessToken() {
        val call = apiService.generateAPIToken2(
            Constants.API_USER,
            Constants.API_USER_KEY,
            Constants.COLLECTION_SUBSCRIPTION_KEY
        )

        call.enqueue(object : Callback<ApiToken> {
            override fun onFailure(call: Call<ApiToken>, t: Throwable) {
                Log.d(TAG, "getAccessToken onError ${t.message}")
            }

            override fun onResponse(call: Call<ApiToken>, response: Response<ApiToken>) {
                Log.d(
                    TAG,
                    "getAccessToken: ResponseCode ${response.code()} ::: ${response.isSuccessful}"
                )
                if (response.isSuccessful) {

                    apiToken = response.body()!!
                    makeRequestToPay()
                }
            }
        })
    }

    private fun makeRequestToPay() {
        requestToPay.currency = Constants.CURRENCY
        requestToPay.payer.partyIdType = Constants.MSISDN
        requestToPay.externalId = "${gameReceipt.gamePlayed}-${gameReceipt.playerNumber}"
        requestToPay.amount = gameReceipt.amountStaked
        requestToPay.payer.partyId = "233${gameReceipt.playerNumber.substring(1)}"
        requestToPay.payerMessage = "Paying for lotto stake"
        Log.e("REFERENCE", gameReceipt.reference)
        Log.e("REFERENCE2", Gson().toJson(requestToPay))
        Log.e("REFERENCE3", apiToken.access_token)
        val call = apiService.requestToPay(
            subscriptionKey = Constants.COLLECTION_SUBSCRIPTION_KEY,
            referenceID = gameReceipt.reference,
            targetEnvironment = Constants.TARGET_ENVIRONMENT,
            accessToken = "Bearer ${apiToken.access_token}",
            body = Gson().toJson(requestToPay)
        )

        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d(TAG, "makeRequestToPay:: onError ${t.message}")
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d(
                    TAG,
                    "makeRequestToPay:: ResponseCode ${response.code()} ::: ${response.isSuccessful}"
                )
                if (response.isSuccessful) {
                    //TODO: TOAST
                }
            }
        })
    }


    companion object {
        val TAG = PaymentReceipt::class.java.simpleName
    }
}