package com.prasannakumar.dindinnassignment.utils

import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

private const val EXPIRED="Expired"
class CountdownRunnable() : Runnable {
    private  val TAG = "ABC"
    private  val DELAY=60000
    private var millisUntilFinished: Long = 600000
    private var holder: TextView? = null
    private var handler: Handler? = null
    private var progressBar: ProgressBar?=null
    private var acceptBtnHolder: Button? = null
    private var list = ArrayList<TextView>()
    private var handlerList = ArrayList<Handler>()
    var time="0"
    var alertTime = ArrayList<String>()
    var countdownRunnableList = ArrayList<CountdownRunnable>()
    var hashMap = HashMap<TextView, String>()
    var listener:RemoveExpiredViewListener?=null

    constructor(
        handler: Handler,
        timer: TextView,
        millisUntilFinished: Long,
        alertTime: Long,
        progressBar: ProgressBar,
        acceptBtn: Button,
        listener: RemoveExpiredViewListener?
    ) : this() {
        this.handler = handler
        this.holder = timer
        this.millisUntilFinished = millisUntilFinished
        this.acceptBtnHolder = acceptBtn
        this.progressBar=progressBar
        this.listener=listener
        list.add(this.holder!!)
        this.alertTime.add(getTime(alertTime))
        hashMap.put(this.holder!!, getTime(alertTime))
        this.progressBar!!.max = millisUntilFinished.toInt()
    }
 fun addHandler(handler: Handler)
{
    handlerList.add(handler)
}
    private fun getTime(alertTime: Long): String {
        val seconds2 = alertTime / 1000
        val minutes2 = seconds2 / 60
        val time2 =
            "" + minutes2 % 60 + ":" + seconds2 % 60
        return time2
    }

    fun addObj(countdownRunnable: CountdownRunnable) {
        countdownRunnableList.add(countdownRunnable)
    }
    fun start() {
        if (handler != null) handler!!.postDelayed(this, 0)
    }
     fun cancel() {
        if (handler != null) handler!!.removeCallbacks(this)
    }
    override fun run() {
        /* do what you need to do */
        val seconds = millisUntilFinished / 1000
        val minutes = seconds / 60
         time =
            "" + minutes % 60 + ":" + seconds % 60

                val alertTime = hashMap.get(holder)
                if (alertTime.equals(time)) {
                    Log.d("ABC", "$alertTime ::$time ")
                    Log.d("ABC", "Alert alarm on")
                }
        if (time.equals("0:0")) {
            acceptBtnHolder!!.text=EXPIRED
            handler!!.removeCallbacksAndMessages(null)
            val pos=acceptBtnHolder!!.tag as Int
            listener!!.removeView(countdownRunnableList.get(pos),pos)
        }

        holder!!.text = time
        progressBar!!.progress=millisUntilFinished.toInt()
        millisUntilFinished -= DELAY
        /* and here comes the "trick" */

            handler?.postDelayed(this, DELAY.toLong())


    }

    fun checkIfTimerIsExpired(downTimer: CountdownRunnable) {
        if (time.equals("0:0")) {
            for (item in list) {
                Log.d("ABC", "in loop run: ")
                if (holder == item) {
                    Log.d("ABC", "timer canceled: ")
                    acceptBtnHolder!!.text=EXPIRED
                   downTimer.cancel()
                }
            }
        }
    }
/*fun removeFromList(pos:Int){
    list.removeAt(pos)
}*/
interface RemoveExpiredViewListener{
    fun removeView(tag: Any,pos:Int)
}
}