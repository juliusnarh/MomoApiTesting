package com.uclgroupgh.momoapitesting.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uclgroupgh.momoapitesting.R

import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.nav_host_fragment).navigateUp()

}