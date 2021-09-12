package com.prasannakumar.dindinnassignment.utils

import android.os.Handler
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

private const val EXPIRED = "Expired"

class CountdownRunnable() : Runnable {
    private val DELAY = 60000
    private var millisUntilFinished: Long = 600000
    private var holder: TextView? = null
    private var handler: Handler? = null
    private var progressBar: ProgressBar? = null
    private var acceptBtnHolder: Button? = null
    private var handlerList = ArrayList<Handler>()
    var time = "0"
    var alertTime = ArrayList<String>()
    var countdownRunnableList = ArrayList<CountdownRunnable>()
    var hashMap = HashMap<TextView, String>()
    var listener: RunnableListener? = null

    constructor(
        handler: Handler,
        timer: TextView,
        millisUntilFinished: Long,
        alertTime: Long,
        progressBar: ProgressBar,
        acceptBtn: Button,
        listener: RunnableListener?
    ) : this() {
        this.handler = handler
        this.holder = timer
        this.millisUntilFinished = millisUntilFinished
        this.acceptBtnHolder = acceptBtn
        this.progressBar = progressBar
        this.listener = listener
        this.alertTime.add(getTime(alertTime))
        hashMap[this.holder!!] = getTime(alertTime)
        this.progressBar!!.max = millisUntilFinished.toInt()
    }

    fun addHandler(handler: Handler) {
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
        val alertTime = hashMap[holder]
        if (alertTime.equals(time)) {
            if (listener != null) {
                listener!!.ringBell()
            }

        }
        if (time.equals("0:0")) {
            holder!!.text = time
            progressBar!!.progress = millisUntilFinished.toInt()
            acceptBtnHolder!!.text = EXPIRED
            val countdownRunnable = countdownRunnableList.get(0)
            countdownRunnable.cancel()
            handler!!.removeCallbacks(countdownRunnableList.get(0))
        }
        if (!acceptBtnHolder!!.text.equals(EXPIRED)) {

            holder!!.text = time
            progressBar!!.progress = millisUntilFinished.toInt()
            millisUntilFinished -= DELAY

            handler?.postDelayed(this, DELAY.toLong())
        }


    }

    fun checkIfTimerIsExpired(downTimer: CountdownRunnable) {
        if (time.equals("0:0")) {
            downTimer.cancel()
        }
    }

    interface RunnableListener {
        fun removeView(tag: Any, pos: Int)
        fun ringBell()
    }
}