package com.raonstudio.foxforest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.raonstudio.foxforest.databinding.ItemRoomBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roomList.apply {
            adapter = RoomListAdapter()
        }

        createRoomButton.setOnClickListener {
            val newRoom = GameRoom("New Room ${System.currentTimeMillis()}")

        }
    }
}

class RoomListAdapter: RecyclerView.Adapter<RoomItemViwHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomItemViwHolder {
        return ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false).run {
            RoomItemViwHolder(this)
        }
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RoomItemViwHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class RoomItemViwHolder(binding: ItemRoomBinding): RecyclerView.ViewHolder(binding.root)