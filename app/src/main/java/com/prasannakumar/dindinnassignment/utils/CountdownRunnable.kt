package com.prasannakumar.dindinnassignment.utils

import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView


class CountdownRunnable() : Runnable {
    var millisUntilFinished: Int = 600000
    var holder: TextView? = null
    var handler: Handler? = null
    var progressBar: ProgressBar?=null
    var imageView: ImageView? = null
    var list = ArrayList<TextView>()
    var handlerList = ArrayList<Handler>()

    var time = ArrayList<String>()
    var countdownRunnableList = ArrayList<CountdownRunnable>()
    var hashMap = HashMap<TextView, String>()

    constructor(
        handler: Handler,
        timer: TextView,
        millisUntilFinished: Int,
        alertTime: Long,
        progressBar: ProgressBar
    ) : this() {
        this.handler = handler
        this.holder = timer
        this.millisUntilFinished = millisUntilFinished
        this.imageView = imageView
        this.progressBar=progressBar
        list.add(this.holder!!)
        handlerList.add(this.handler!!)
        time.add(getTime(300000))
        hashMap.put(this.holder!!, getTime(300000))
        progressBar.max = millisUntilFinished.toInt()

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

    override fun run() {
        /* do what you need to do */
        val seconds = millisUntilFinished / 1000
        val minutes = seconds / 60
        val time =
            "" + minutes % 60 + ":" + seconds % 60

        for (item in list) {
            if (holder == item) {
                val alertTime = hashMap.get(holder)
                Log.d("ABC", "$alertTime ::$time ")
                if (alertTime.equals(time)) {
                    Log.d("ABC", "Alert alarm on")
                }
            }
        }

        if (time.equals("0:0")) {
            for (item in list) {
                Log.d("ABC", "in loop run: ")
                if (holder == item) {
                    Log.d("ABC", "match found :")
                    val pos = list.indexOf(item)
                    var handler = handlerList.get(pos)
                    var obj = countdownRunnableList.get(pos)
                    handler.removeCallbacksAndMessages(null)
                }
            }
        }
        holder!!.text = time
        progressBar!!.progress=millisUntilFinished.toInt()
        millisUntilFinished -= 1000
        Log.d("CountdownRunnable", time)
        /* and here comes the "trick" */

        handler?.postDelayed(this, 1000)

    }

}