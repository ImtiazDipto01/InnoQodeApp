package com.imtiaz.taskmanager.utils

import android.os.Handler

fun postRunnableDelay(timeMillis: Long = 300, action: () -> Unit){
    Handler().postDelayed({ action() }, timeMillis)
}