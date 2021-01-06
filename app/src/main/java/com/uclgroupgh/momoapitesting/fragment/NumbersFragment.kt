package com.uclgroupgh.momoapitesting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import com.uclgroupgh.momoapitesting.R
import com.uclgroupgh.momoapitesting.databinding.FragmentNumbersBinding
import com.uclgroupgh.momoapitesting.ui.disable
import com.uclgroupgh.momoapitesting.ui.enable
import com.uclgroupgh.momoapitesting.ui.hide
import com.uclgroupgh.momoapitesting.util.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NumbersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NumbersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var gameName = ""
    val safeArg: NumbersFragmentArgs by navArgs()
    var selectedNumbers = ""
    var adapter: MainAdapter = MainAdapter()
    var tracker: SelectionTracker<Long>? = null
    var limit: Int? = 1
    private var _binding: FragmentNumbersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNumbersBinding.inflate(inflater, container, false)
        gameName = AndroidUtils.getPreferenceData(requireContext(), Constants.GAME_NAME, "null")!!
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        limit = Constants.GAMES_NUMBER_LIMIT[gameName?.toLowerCase(Locale.ENGLISH)]
        setupNumberSelectionLimit()
        setupRecycler()
        setupAdapter()
        setupTracker()
        clickEvents()
    }

    private fun clickEvents() {
        binding.paymentButton.setOnClickListener {
            selectedNumbers = tracker?.selection!!.map { adapter.list[it.toInt()] }.joinToString()
            val action = NumbersFragmentDirections.actionNumbersFragmentToPaymentFragment()
            action.selectedNumbers = selectedNumbers
            action.gamePlayed = gameName
            findNavController().navigate(action)
        }
    }

    private fun setupNumberSelectionLimit() {
        when (limit) {
            1 -> {
                binding.number2Layout.hide()
                binding.number3Layout.hide()
                binding.number4Layout.hide()
                binding.number5Layout.hide()
            }
            2 -> {
                binding.number3Layout.hide()
                binding.number4Layout.hide()
                binding.number5Layout.hide()
            }
            3 -> {
                binding.number4Layout.hide()
                binding.number5Layout.hide()
            }
            4 -> binding.number5Layout.hide()

            null -> {
                Toast.makeText(requireContext(), "Game not found. Exiting", Toast.LENGTH_LONG)
                    .show()
                requireActivity().finish()
            }
        }
    }

    private fun setupTracker() {
        var selectionPredicates = SelectionPredicates.createSelectAnything<Long>()

        if (gameName.equals("wulucky 1", ignoreCase = true) || gameName.equals(
                "banker",
                ignoreCase = true
            )
        ) {
            selectionPredicates = SelectionPredicates.createSelectSingleAnything<Long>()
        }

        tracker = SelectionTracker.Builder<Long>(
            "selectedNumbers",
            binding.recyclerView,
            CustomKeyProvider(binding.recyclerView),
            NumberDetailsLookup(binding.recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            selectionPredicates
        ).build()

        tracker?.addObserver(

            object : SelectionTracker.SelectionObserver<Long>() {

                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    val nItems: Int? = tracker?.selection!!.size()

                    if (limit != null) {
                        if (nItems!! < limit!!) {
                            binding.paymentButton.disable()
                        } else if (nItems == limit!!) {
                            binding.paymentButton.enable()
                        }
                    }
                }
            })


        adapter.tracker = tracker
    }

    private fun setupAdapter() {
        adapter.limit = limit
        adapter.list = createRandomIntList()
        adapter.notifyDataSetChanged()
    }

    private fun setupRecycler() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 5)
        binding.recyclerView.adapter = adapter
    }

    private fun createRandomIntList(): List<Int> {
        return (1..50).toList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = NumbersFragment::class.java.simpleName
    }
}
