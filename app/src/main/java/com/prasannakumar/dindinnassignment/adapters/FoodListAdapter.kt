package com.prasannakumar.dindinnassignment.adapters

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prasannakumar.dindinnassignment.R
import com.prasannakumar.dindinnassignment.dataClass.OrderList
import com.prasannakumar.dindinnassignment.utils.CountdownRunnable
import java.text.SimpleDateFormat
import java.util.*


class FoodListAdapter(private val users: ArrayList<OrderList>) :
    RecyclerView.Adapter<FoodListAdapter.AlbumViewHolder>() {
    lateinit var handler: Handler
    private val onItemClickListener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        AlbumViewHolder(
            parent.inflater().inflate(R.layout.list_item, parent, false)
        )

    fun clearAll() {
        handler.removeCallbacksAndMessages(null)
    }

    fun clearInstance() {
        //handler.removeCallbacks()
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        Log.d("ABC", "onBindViewHolder: $position ")
        holder.bind(users[position])
    }


    private fun ViewGroup.inflater(): LayoutInflater = LayoutInflater.from(context)


    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(foodObj: OrderList) {
            itemView.apply {

                for (item in foodObj.data) {
                    Log.d("ABC", "addon title: ${item.title}")


                    val sdf = SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                        Locale.ENGLISH
                    )
                    val parsedDate = sdf.parse(item.created_at)
                    val localDateTime = sdf.parse(item.expired_at)
                    val alertTime = sdf.parse(item.alerted_at)
                    val totTime = getTime(parsedDate, localDateTime)

                    Log.d("ABC", "totTime : $totTime")
                    Log.d("ABC", "LocalDateTime : $localDateTime")
                    val print = SimpleDateFormat("hh:mm a")
                    Log.d("ABC", "bind: ${print.format(parsedDate)}")
                    val title: TextView = itemView.findViewById(R.id.tv_orderNo)
                    val time: TextView = itemView.findViewById(R.id.tv_time)
                    val timer: TextView = itemView.findViewById(R.id.tv_timer)
                    val progressBar: ProgressBar = itemView.findViewById(R.id.progressbar)
                    title.text = item.id.toString()
                    time.text = print.format(parsedDate)

                    var handler = Handler()
                    val countdownRunnable = CountdownRunnable(
                        handler,
                        timer,
                        600000,
                        getTime(parsedDate, alertTime),
                        progressBar
                    )
                    countdownRunnable.holder = timer
                    countdownRunnable.addObj(countdownRunnable)
                    countdownRunnable.millisUntilFinished =
                        (600000 * (getAdapterPosition() + 1))

                    handler.postDelayed(countdownRunnable, 100);

                }

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

        private fun getTime(parsedDate: Date, localDateTime: Date): Long {
            val difference_In_Time: Long = localDateTime.time - parsedDate.time
            val difference_In_Seconds = ((difference_In_Time
                    / 1000)
                    % 60)
            val difference_In_Minutes = ((difference_In_Time
                    / (1000 * 60))
                    % 60)
            val difference_In_Hours = ((difference_In_Time
                    / (1000 * 60 * 60))
                    % 24)
            val difference_In_Years = (difference_In_Time / (1000L * 60 * 60 * 24 * 365))
            val difference_In_Days = ((difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365)
            val h = (difference_In_Hours * 3600000)
            val m = (difference_In_Minutes * 60000)
            val s = (difference_In_Seconds * 6000)
            return h + m + s
            /*return "" + (difference_In_Years
                .toString() + " years, "
                    + difference_In_Days
                    + " days, "
                    + difference_In_Hours
                    + " hours, "
                    + difference_In_Minutes
                    + " minutes, "
                    + difference_In_Seconds
                    + " seconds")*/
        }

    }

    fun addItem(foodObj: List<OrderList>) {
        this.users.apply {
            clear()
            addAll(foodObj)
        }

    }
}

