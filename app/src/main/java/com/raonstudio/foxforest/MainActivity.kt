package com.raonstudio.foxforest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.raonstudio.foxforest.databinding.ItemRoomBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roomList.apply {
            adapter = RoomListAdapter().apply {
                setHasStableIds(true)
                FireStoreManager.getRoomList {
                    bindRooms(it)
                }
            }

        }

        createRoomButton.setOnClickListener {
            FireStoreManager.createNewRoom("New Room ${System.currentTimeMillis()}") { room ->
                newRoom.text = room.name
            }

        }
    }
}

class RoomListAdapter : RecyclerView.Adapter<RoomItemViwHolder>() {
    private var rooms: List<GameRoom?>? = null
    private val numberFilter = Regex("[^0-9]")

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
        return numberFilter.replace(rooms?.get(position)?.roomId ?: "", "").toLong()
    }
}

class RoomItemViwHolder(val binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root)