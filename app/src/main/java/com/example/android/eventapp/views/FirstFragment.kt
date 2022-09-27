package com.example.android.eventapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.eventapp.R
import com.example.android.eventapp.adapters.EventAdapter
import com.example.android.eventapp.adapters.onCardClickHandler
import com.example.android.eventapp.databinding.FragmentFirstBinding
import com.example.android.eventapp.models.EventModel


private const val TAG = "FirstFragment"

class FirstFragment : Fragment(),onCardClickHandler {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val eventAdapter by lazy{
        EventAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getSerializable("event") as List<EventModel>
        }?.let {
            for (counter in it) {
                eventAdapter.updateEvent(counter)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
        Log.d(TAG, "onCreateView: Create View")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: View created")
        binding.mainRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = eventAdapter
            Log.d(TAG, "onViewCreated: entering recycler view")
        }

        binding.add.setOnClickListener{
            if(eventAdapter.getEvent().isNullOrEmpty() ){
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                Log.d(TAG, "onViewCreated: NOT having events")
            }else{
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf(
                    Pair("event",eventAdapter.getEvent()),
                    Pair("index",-100)
                ))
                Log.d(TAG, "onViewCreated: having events")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView: destroying main view")
    }


    override fun onCardClicked(event: EventModel) {
        super.onCardClicked(event)
        Log.d(TAG, "onCardClicked: card Clicked")
        findNavController().navigate(
            R.id.action_FirstFragment_to_SecondFragment,
            bundleOf(
                Pair("event",eventAdapter.getEvent()),
                Pair("index",eventAdapter.getEvent().indexOf(event))
            )
        )
    }

}