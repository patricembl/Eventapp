package com.example.android.eventapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.eventapp.databinding.FragmentViewEventBinding
import com.example.android.eventapp.models.EventModel


class EventAdapter(
    private val onCardClickHandler: onCardClickHandler,
    private val eventDataSet: MutableList<EventModel> = mutableListOf()
): RecyclerView.Adapter<EventViewHolder>() {

    fun updateEvent(newEvent: EventModel){
        eventDataSet.add(newEvent).also {
            if(it){
                eventDataSet.sortBy { data -> data.date}
            }
        }
        notifyItemInserted(eventDataSet.indexOf(newEvent))
    }

    fun getSpecificEvent(index: Int):EventModel{
        return eventDataSet[index]
    }

    fun modifyEvent(
        event: EventModel,
        index: Int){
        event
        eventDataSet.removeAt(index)

        eventDataSet.add(index, event).also{
            eventDataSet.sortBy { data -> data.date}
        }
        notifyItemInserted(eventDataSet.indexOf(event))
    }



    fun getEvent(): MutableList<EventModel> {
        Log.d("function", "getEvent: $eventDataSet + holder")
        return eventDataSet

    }

    fun indexEvent(event: EventModel): Int {
        return eventDataSet.indexOf(event)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder =
        EventViewHolder(
            FragmentViewEventBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventDataSet[position],onCardClickHandler)
    }

    override fun getItemCount(): Int = eventDataSet.size
}

class EventViewHolder(
    private val binding: FragmentViewEventBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(event: EventModel,onCardClickHandler: onCardClickHandler){
        binding.title.text = event.name
        binding.description.text = event.description
        binding.category.text = event.category
        binding.date.text = event.date
        binding.root.setOnClickListener{
            onCardClickHandler.onCardClicked(event)
        }
    }
}

interface onCardClickHandler{
    fun onCardClicked(event: EventModel){

    }
}