package com.example.currencyconverter.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel by viewModels<CurrencyViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)
        lateinit var firstCurrency: String
        lateinit var secondCurrency: String


        viewModel.currencies.observe(viewLifecycleOwner) {
        }
        var currenciesFormatted = viewModel.currencies.value



        binding.apply {
            spinnerFirst.adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                currenciesFormatted ?: emptyArray()
            )
            spinnerFirst.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    firstCurrency = adapterView?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
            spinnerSecond.adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                currenciesFormatted ?: emptyArray()
            )
            spinnerSecond.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    secondCurrency = adapterView?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }

        binding.constraintLayout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                closeKeyboard(p0)
                return true
            }
        })

        binding.button.setOnClickListener {
            val stringInTextField = binding.amountText.text.toString()
            val amount = stringInTextField.toDoubleOrNull()
            if (amount == null) {
                showToast("Please enter a number")
                closeKeyboard(binding.amountText)
            } else {
                closeKeyboard(binding.amountText)
                viewModel.getConversionRate(firstCurrency, secondCurrency, amount)
                viewModel.conversionResult.observe(viewLifecycleOwner) {
                    binding.resultText.text = it
                }
            }
        }
    }

    private fun closeKeyboard(view: View?) {
        val imm =
            view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showToast(string: String) {
        Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
    }
}