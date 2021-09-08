package com.prasannakumar.dindinnassignment.adapters

import android.content.Context
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.prasannakumar.dindinnassignment.R
import com.prasannakumar.dindinnassignment.dataClass.OrderList


var width = 0

class FoodListAdapter(private val users: ArrayList<OrderList>) :
    RecyclerView.Adapter<FoodListAdapter.AlbumViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        AlbumViewHolder(
            parent.inflater().inflate(R.layout.list_item, parent, false)
        )


    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        Log.d("ABC", "onBindViewHolder: $position ")
        holder.bind(users[position])
    }


    private fun ViewGroup.inflater(): LayoutInflater = LayoutInflater.from(context)


    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(foodObj: OrderList) {
            itemView.apply {
                // Log.d("ABC", "title: ${foodObj.data.get(position).title}")
                //   Log.d("ABC", "addon title: ${foodObj.data.get(0).title}")

                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display: Display = wm.defaultDisplay
                val test = (display.width / 3)
                val layout: ConstraintLayout = itemView.findViewById(R.id.item_layout)
                val newLayoutParams = layout.getLayoutParams()
                newLayoutParams.width = test
                Log.d("ABC", "bind:$test ")
                layout.setLayoutParams(newLayoutParams)
                /* val title: TextView = itemView.findViewById(R.id.title)
                 val thumbnail: ImageView = itemView.findViewById(R.id.album_art)
                 title.text = foodObj.title
                 Glide.with(itemView)
                     .load(foodObj.url)
                     .centerCrop()
                     .placeholder(R.drawable.monkey)
                     .error(R.drawable.ic_broken_image)
                     .fallback(R.drawable.ic_no_image)
                     .transform(CircleCrop())
                     .into(thumbnail)*/

            }
        }

    }

    fun addItem(foodObj: List<OrderList>, displayWidth: Int) {
        width = displayWidth
        this.users.apply {
            clear()
            addAll(foodObj)
        }

    }
}

