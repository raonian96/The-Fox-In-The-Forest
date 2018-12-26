package com.raonstudio.foxforest

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raonstudio.foxforest.databinding.FragmentRoomListBinding
import com.raonstudio.foxforest.databinding.ItemRoomBinding
import kotlinx.android.synthetic.main.fragment_room_list.*

class RoomListFragment: Fragment() {
    private lateinit var binding: FragmentRoomListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_room_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomList.apply {
            adapter = RoomListAdapter().apply {
                FireStoreRepository.getRoomList {
                    bindRooms(it)
                }
            }
        }

        createRoomButton.setOnClickListener {
            FireStoreRepository.createNewRoom("New Room ${System.currentTimeMillis()}") { room ->
                newRoom.text = room.name
            }
        }
    }
}

class RoomListAdapter : RecyclerView.Adapter<RoomItemViwHolder>() {
    private var rooms: List<GameRoom?>? = null

    init {
        setHasStableIds(true)
    }

    fun bindRooms(rooms: List<GameRoom?>) {
        this.rooms = rooms
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomItemViwHolder {
        return ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false).run {
            RoomItemViwHolder(this)
        }
    }

    override fun getItemCount(): Int = rooms?.size ?: 0

    override fun onBindViewHolder(holder: RoomItemViwHolder, position: Int) {
        rooms?.get(position)?.let {
            holder.binding.room = it
        }
    }

    override fun getItemId(position: Int): Long {
        return rooms?.get(position)?.roomId?.filterNumber()?.toLong() ?: 0
    }
}

class RoomItemViwHolder(val binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root)