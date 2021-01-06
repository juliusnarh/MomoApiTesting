package com.uclgroupgh.momoapitesting.fragment


import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.uclgroupgh.momoapitesting.R
import com.uclgroupgh.momoapitesting.databinding.DialogWelcomeBinding

/**
 * A simple [Fragment] subclass.
 */
class WelcomDialog : DialogFragment() {
    lateinit var binding: DialogWelcomeBinding
    private var mParam1: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
        if (arguments != null) {
            mParam1 = requireArguments().getInt(ARG_PARAM1)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_welcome, container, false)
        setupMemberName()
        return binding.root
    }

    private fun setupMemberName() {
        if (mParam1 == 1) {
            binding.animationView.setAnimation("error.json")
        }

        startTimerToDismiss()
    }

    private fun startTimerToDismiss() {
        val timer = object : CountDownTimer(2500, 1000){
            override fun onFinish() {
                welcomeClickListener!!.onWelcomeClickListener("","","")
                dismiss()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }

        timer.start()
    }

    interface WelcomeClickListener {
        fun onWelcomeClickListener(
            item: String,
            dated: String,
            timed: String
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireActivity(), theme) {
            override fun onBackPressed() {
                dismiss()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        val dialog = dialog
        dialog!!.setCancelable(false)
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation2
            dialog.window!!.setLayout(width, height)
        }
    }

    companion object {
        private var welcomeClickListener: WelcomeClickListener? = null

        private val ARG_PARAM1 = "param1"
        var TAG = WelcomDialog::class.java.simpleName

        fun newInstance(type: Int, listener: WelcomeClickListener): WelcomDialog {
            val dialog = WelcomDialog()
            val args = Bundle()
            args.putInt(ARG_PARAM1, type)
            dialog.arguments = args
            welcomeClickListener = listener
            return dialog
        }
    }

}
