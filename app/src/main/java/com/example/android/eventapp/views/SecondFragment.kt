package com.example.android.eventapp.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.eventapp.R
import com.example.android.eventapp.adapters.EventAdapter
import com.example.android.eventapp.adapters.onCardClickHandler
import com.example.android.eventapp.databinding.FragmentFirstBinding
import com.example.android.eventapp.databinding.FragmentSecondBinding
import com.example.android.eventapp.models.EventModel

private const val TAG = "SecondFragment"
class SecondFragment : Fragment(),onCardClickHandler{

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!


    private val bindingMain by lazy{
        FragmentFirstBinding.inflate(layoutInflater)
    }
    private val eventAdapter by lazy{
        EventAdapter(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    private var updateIndex = -100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            it.getSerializable("index") as Int
        }?.let {
            updateIndex=it
        }?: -100

        arguments?.let {
            it.getSerializable("event") as List<EventModel>
        }?.let {
            for (counter in it) {
                eventAdapter.updateEvent(counter)
            }
        }?: EventModel("","","","")

        if (updateIndex == -100){

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bindingMain.mainRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = eventAdapter
        }
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }


        if(updateIndex != -100){
            var updateEvent:EventModel = eventAdapter.getSpecificEvent(updateIndex)
            binding.inputtitle.setText(updateEvent.name)
            binding.inputdescription.setText(updateEvent.description)
            binding.inputcategory.setText(updateEvent.category)
            binding.inputDate.setText(updateEvent.date)
        }
        binding.sendData.setOnClickListener{
            val newEvent: EventModel = EventModel(binding.inputtitle.text.toString(),binding.inputdescription.text.toString(),binding.inputcategory.text.toString(),binding.inputDate.text.toString())
            if (updateIndex == -100){
                eventAdapter.updateEvent(newEvent)
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment, bundleOf(Pair("event",eventAdapter.getEvent())))
            }else{
                eventAdapter.modifyEvent(newEvent,updateIndex)
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment, bundleOf(Pair("event",eventAdapter.getEvent())))
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
    }
}