package com.uclgroupgh.momoapitesting.activity

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.gson.Gson
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import com.uclgroupgh.momoapitesting.R
import com.uclgroupgh.momoapitesting.databinding.ActivityMainBinding
import com.uclgroupgh.momoapitesting.models.*
import com.uclgroupgh.momoapitesting.network.ApiClient
import com.uclgroupgh.momoapitesting.network.ApiService
import com.uclgroupgh.momoapitesting.util.AndroidUtils
import com.uclgroupgh.momoapitesting.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    lateinit var apiService: ApiService
    lateinit var paymentProgress: ProgressBar
    lateinit var searchProgress: ProgressBar
    lateinit var paymentButton: AppCompatButton
    lateinit var binding: ActivityMainBinding
    private val disposableUserAcc = CompositeDisposable()
    private val publishSubjectUserAcc = PublishSubject.create<String>()
    private lateinit var textView: TextView
    var paymentReference = ""
    var userPojo = UserPojo()
    private val TAG = MainActivity::class.java.simpleName
    val requestToPay = RequestToPay()
    var user = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.pojo = userPojo
        setupApiService()
        setupViews()
        setupClickListener()
        setupTextViewAndListener()
        setupObserver()
    }

    private fun setupClickListener() {
        paymentButton.setOnClickListener {
            paymentProgress.visibility = View.VISIBLE
//            callCreateApiUser()
            callGetToken()
        }
    }

    private fun callGetToken() {
        val call = apiService.generateAPIToken2(
            Constants.API_USER,
            Constants.API_USER_KEY,
            Constants.COLLECTION_SUBSCRIPTION_KEY
        )

        call.enqueue(object : Callback<ApiToken> {
            override fun onFailure(call: Call<ApiToken>, t: Throwable) {
                Log.d(TAG, "MOMO CALL GET API TOKEN::: onError ${t.message}")
            }

            override fun onResponse(call: Call<ApiToken>, response: Response<ApiToken>) {
                Log.d(
                    TAG,
                    "MOMO CALL GET API TOKEN::: ResponsCode ${response.code()} ::: ${response.isSuccessful}"
                )
                if (response.isSuccessful) {
                    paymentProgress.visibility = View.GONE

                    AndroidUtils.savePreferenceData(
                        this@MainActivity, Constants.ACCESS_TOKEN,
                        "Bearer ${response.body()!!.access_token!!}"
                    )
                    requestToPay.currency = Constants.CURRENCY
                    requestToPay.payer.partyIdType = Constants.MSISDN
                    requestToPay.externalId = et_search_account.text.toString().trim()

                    callMaterialDialogEnterPhone()
                }
            }
        })
    }

    private fun amountMaterialDialog() {
        Log.d(TAG, "MOMO CALL DIALOG ENTER AMOUNT")
        val type = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        MaterialDialog(this).show {
            input(
                inputType = type,
                hint = "Please enter amount",
                allowEmpty = false
            ) { dialog, text ->
                requestToPay.amount = text.toString().trim()
                makeRequestToPayNetworkCall()
                // Text submitted with the action button
            }
            title(text = "Enter Amount")
            cancelOnTouchOutside(false)
            cornerRadius(16f)
            positiveButton(text = "PAY")
            negativeButton(text = "CANCEL") { dialog ->
                dialog.dismiss()
            }
        }
    }

    private fun makeRequestToPayNetworkCall() {
        paymentReference = AndroidUtils.getUUID()
        Log.e("REFERENCE", paymentReference)
        Log.e("REFERENCE2", Gson().toJson(requestToPay))
        Log.e("REFERENCE3", AndroidUtils.getPreferenceData(this, Constants.ACCESS_TOKEN, "")!!)
        val call = apiService.requestToPay(
            subscriptionKey = Constants.COLLECTION_SUBSCRIPTION_KEY,
            referenceID = paymentReference,
            targetEnvironment = Constants.TARGET_ENVIRONMENT,
            accessToken = AndroidUtils.getPreferenceData(this, Constants.ACCESS_TOKEN, "")!!,
            body = Gson().toJson(requestToPay)
        )

        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d(TAG, "MOMO CALL rEQUEST TO PAY::: onError ${t.message}")
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d(
                    TAG,
                    "MOMO CALL rEQUEST TO PAY::: ResponsCode ${response.code()} ::: ${response.isSuccessful}"
                )
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Request successful", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun callMaterialDialogEnterPhone() {
        Log.d(TAG, "MOMO CALL DIALOG ENTER NUMBER")
        val type = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        MaterialDialog(this).show {
            input(
                inputType = type,
                hint = "Please enter number",
                allowEmpty = false,
                maxLength = 9
            ) { dialog, text ->
                val number = text.toString()
                    requestToPay.payer.partyId = "233${text.toString().trim()}"

                amountMaterialDialog()
                dialog.dismiss()
            }
            title(text = "Enter MoMo number")
            message(text = "Please enter number without the \"0\"")
            cancelOnTouchOutside(false)
            cornerRadius(16f)
            positiveButton(text = "OK")
            negativeButton(text = "CANCEL") { dialog ->
                dialog.dismiss()
            }
        }
    }

    private fun setupViews() {
        paymentProgress = findViewById(R.id.payment_progress_bar)
        searchProgress = findViewById(R.id.progress_bar)
        paymentButton = findViewById(R.id.payment_ripple)
    }

    private fun setupApiService() {
        apiService = ApiClient.getClient(applicationContext)!!.create(ApiService::class.java)
    }

    private fun setupObserver() {
        val observer = getUserObserver()

        disposableUserAcc.add(
            publishSubjectUserAcc
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMapSingle { s ->
                    apiService.getAyawasoAccount(s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                }
                .subscribeWith(observer))

        disposableUserAcc.add(
            RxTextView.textChangeEvents(textView)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchUserTextWatcher())
        )

        disposableUserAcc.add(observer)
    }

    private fun getUserObserver(): DisposableObserver<List<User>> {
        return object : DisposableObserver<List<User>>() {
            override fun onNext(list: List<User>) {
                Log.d(TAG, "Driver Observer::: OnNext")
                runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                }
                if (list.isNotEmpty())
                    try {

                        user = list[0]
                        setUserDataInLayout()
                        runOnUiThread {
                            binding.materialRippleLayout.visibility = View.VISIBLE
                            binding.tripDetailsCard.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {

                    }
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "Driver Observer::: OnERror ${e.message}")
                runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                }
                ApiClient.resetApiClient()
                setupApiService()
                setupObserver()
            }

            override fun onComplete() {
                runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setUserDataInLayout() {
        userPojo.account.set(user.account)
        userPojo.totalbill.set(user.totalbill)
        userPojo.ownername.set(user.ownername)
        userPojo.phone.set(user.phone)
        userPojo.area.set(user.area)
        userPojo.payment.set(user.payment)
        userPojo.bal.set(user.bal)
        userPojo.arrears.set(user.arrears)
        userPojo.longitude.set(user.longitude)
        userPojo.latitude.set(user.latitude)
        userPojo.currentbill.set(user.currentbill)
        userPojo.category.set(user.category)
    }

    private fun setupTextViewAndListener() {
        textView = TextView(applicationContext)

        et_search_account.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.length > 8) {
                    textView.text = String.format(
                        "https://uclgroupgh.com/UCLRevenueSystemu6l/Android/getAccountInfo.php?account=%s",
                        s.toString()
                    )
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    private fun searchUserTextWatcher(): DisposableObserver<TextViewTextChangeEvent> {
        return object : DisposableObserver<TextViewTextChangeEvent>() {
            override fun onNext(textViewTextChangeEvent: TextViewTextChangeEvent) {
                binding.progressBar.visibility = View.VISIBLE
                runOnUiThread {
                    binding.materialRippleLayout.visibility = View.GONE
                    binding.tripDetailsCard.visibility = View.GONE
                }
                Log.d(TAG, "Search query: ${textViewTextChangeEvent.text()}")
                publishSubjectUserAcc.onNext(
                    textViewTextChangeEvent.text().toString().trim { it <= ' ' })
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: text watcher ${e.message}")
            }

            override fun onComplete() {
                Log.e(TAG, "onError: text watcher completed")

            }
        }
    }
}
