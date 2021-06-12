package com.imtiaz.taskmanager.utils

import android.app.Activity
import android.app.DatePickerDialog
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss"
const val DATE_TIME_FORMAT_2 = "dd-MM-yyyy HH:mm:ss"
const val DATE_TIME_FORMAT_3 = "yyyy-MM-dd HH:mm:ss a"
const val DATE_TIME_FORMAT_4 = "dd-MM-yyyy HH:mm:ss a"
const val DATE_TIME_FORMAT_5 = "yyyy-MM-dd"
const val DATE_TIME_FORMAT_6 = "dd-MM-yyyy" // due_date
const val DATE_TIME_FORMAT_7 = "h:mm a"
const val DATE_TIME_FORMAT_8 = "EEE, d MMM yyyy"
const val DATE_TIME_FORMAT_9 = "d MMM yyyy" // user show formar

fun Activity.openCalendarPopUp(handleSelection: (Calendar) -> Unit) {
    val currentCalendar = Calendar.getInstance()

    DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->

            val myCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            handleSelection(myCalendar)

        },
        currentCalendar.get(Calendar.YEAR),
        currentCalendar.get(Calendar.MONTH),
        currentCalendar.get(Calendar.DAY_OF_MONTH)
    ).show()

}

fun isDobDateValid(calendar: Calendar): Boolean {
    val time12yearsAgo = Calendar.getInstance()
    time12yearsAgo.set(Calendar.YEAR, time12yearsAgo.get(Calendar.YEAR) - 12)
    return (calendar.time < time12yearsAgo.time)
}

fun getCurrentDateAndTime(dateFormat: String, incrementDays: Int = 0): String {

    val calendarInstance = Calendar.getInstance()
    calendarInstance.add(Calendar.DATE, incrementDays)

    val df: DateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
    val date = df.format(calendarInstance.time)

    return date
}

fun getCalendarFromStringDate(date: String, dateFormat: String): Calendar? {
    return try {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        cal.time = sdf.parse(date)
        cal

    }catch (exp: Exception) {
        null
    }
}

fun getDateAndTimeFromCalendar(format: String, cal: Calendar?): String? {
    if(cal == null) return null

    return try {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        val date = sdf.format(cal.time)
        date
    }
    catch (exp: Exception){
        null
    }
}

fun getUnixTimeStampFrom(incrementDays: Int = 0): String {

    val calendarInstance = Calendar.getInstance()
    calendarInstance.add(Calendar.DATE, incrementDays)

    return (calendarInstance.timeInMillis / 1000L).toString()
}

fun getUnixTimeStamp(): Long = System.currentTimeMillis() / 1000L

fun getTimeStampMillis(): Long = System.currentTimeMillis()