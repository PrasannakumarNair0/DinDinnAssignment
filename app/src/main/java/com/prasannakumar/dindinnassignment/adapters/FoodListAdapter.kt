package com.prasannakumar.dindinnassignment.adapters

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prasannakumar.dindinnassignment.R
import com.prasannakumar.dindinnassignment.dataClass.OrderList
import com.prasannakumar.dindinnassignment.utils.CountdownRunnable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

private const val API_DATE_FORMAT="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val SIMPLE_DATE_FORMAT="hh:mm a"
class FoodListAdapter(private val users: ArrayList<OrderList>) :
    RecyclerView.Adapter<FoodListAdapter.AlbumViewHolder>() {
    var hashMapHandler=HashMap<CountdownRunnable,Handler>()
     var onItemClickListener: AcceptBtnClickListener? = null
    var onViewRemoveExpiredViewListener:CountdownRunnable.RemoveExpiredViewListener?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        AlbumViewHolder(
            parent.inflater().inflate(R.layout.list_item, parent, false),
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(users[position],users,onItemClickListener,hashMapHandler,onViewRemoveExpiredViewListener)

    }


    private fun ViewGroup.inflater(): LayoutInflater = LayoutInflater.from(context)


    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_orderNo)
        val time: TextView = itemView.findViewById(R.id.tv_time)
        val foodNm: TextView = itemView.findViewById(R.id.tv_title1)
        val addOnNm: TextView = itemView.findViewById(R.id.tv_title3)
        val timerHolder: TextView = itemView.findViewById(R.id.tv_timer)
        val btnAccept: Button=itemView.findViewById(R.id.btn_accept)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressbar)

        fun bind(
            foodObj: OrderList,
            users: ArrayList<OrderList>,
            onItemClickListener: AcceptBtnClickListener?,
            hashMapHandler: HashMap<CountdownRunnable, Handler>,
            onViewRemoveExpiredViewListener: CountdownRunnable.RemoveExpiredViewListener?
        ) {
            itemView.apply {
                for (item in foodObj.data) {
                    Log.d("ABC", "addon title: ${item.title}")
                    val sdf=getSimpleDate(API_DATE_FORMAT)
                    val parsedDate = sdf.parse(item.created_at)
                    val localDateTime = sdf.parse(item.expired_at)
                    val alertTime = sdf.parse(item.alerted_at)
                    val totTime = getTime(parsedDate, localDateTime)
                    val print = getSimpleDate(SIMPLE_DATE_FORMAT)
                    btnAccept.setOnClickListener(View.OnClickListener {
                        onItemClickListener!!.removeItem(users,adapterPosition,timerHolder.getTag())
                    })
                    btnAccept.tag=adapterPosition
                    title.text = item.id.toString()
                    foodNm.text=item.quantity.toString()+" "+item.title
                    addOnNm.text=item.addon.get(0).quantity.toString()+" "+item.addon.get(0).title

                    time.text = print.format(parsedDate)
                    if (timerHolder.getTag() != null) {
                        val downTimer = timerHolder.tag as CountdownRunnable
                        downTimer.checkIfTimerIsExpired(downTimer)
                    } else {
                        var handler = Handler()
                        val countdownRunnable = CountdownRunnable(
                            handler,
                            timerHolder,
                            totTime,
                            getTime(parsedDate, alertTime),
                            progressBar,
                            btnAccept,onViewRemoveExpiredViewListener
                        )
                        countdownRunnable.addObj(countdownRunnable)
                        countdownRunnable.start()
                        countdownRunnable.addHandler(handler)
                        timerHolder.tag = countdownRunnable
                        hashMapHandler.put(timerHolder.tag as CountdownRunnable,handler)
                    }


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

        private fun getSimpleDate(dateFormat: String): SimpleDateFormat {
            return SimpleDateFormat(
                dateFormat,
                Locale.ENGLISH
            )

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
        }

    }

    fun addItem(foodObj: List<OrderList>) {
        this.users.apply {
            clear()
            addAll(foodObj)
        }
        }
    interface  AcceptBtnClickListener {
        fun removeItem(users: ArrayList<OrderList>, itemView: Int, tag: Any)
    }
    fun removeItem(users: ArrayList<OrderList>, itemView: Int, tag: Any) {
        this.users.apply {
            val position: Int = itemView
            Log.d("ABC", "removeItem:$position ")
            if (position != -1 && position < users.size) {
                val downTimer = tag as CountdownRunnable
                val handler=hashMapHandler.get(tag)
                downTimer.cancel()
                handler!!.removeCallbacks(downTimer)
                users.removeAt(position)
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, itemCount);

                Log.d("ABC", "itemCount:$itemCount ")
            }
        }
    }

     fun removeView(tag: Any,position: Int) {
this.users.apply {
    val downTimer = tag as CountdownRunnable
    val handler=hashMapHandler.get(tag)
    downTimer.cancel()
    handler!!.removeCallbacks(downTimer)
    users.removeAt(position)
    notifyItemRemoved(position);
    notifyItemRangeChanged(position, itemCount);

}
    }
}







