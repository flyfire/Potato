package com.swensun.func.livedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.swensun.potato.R
import kotlinx.android.synthetic.main.live_data_fragment.*
import kotlinx.coroutines.flow.MutableStateFlow


class LiveDataFragment : Fragment() {

    companion object {
        fun newInstance() = LiveDataFragment()
    }

    private lateinit var viewModel: LiveDataViewModel
    private var count = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.live_data_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LiveDataViewModel::class.java)
        btn_livedata.setOnClickListener {
            count += 1
            if (count % 2 == 1) {
            } else {
            }
        }
        F.modelFlow.asLiveData().observe(viewLifecycleOwner) {

        }
    }
}

object F {
    val modelFlow = MutableStateFlow("")
    fun testFlow(number: Int) {
        modelFlow.value = number.toString()
    }
}