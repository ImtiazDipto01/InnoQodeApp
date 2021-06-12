package com.imtiaz.innoqodetest.utils

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss"
const val DATE_TIME_FORMAT_2 = "dd-MM-yyyy HH:mm:ss"
const val DATE_TIME_FORMAT_3 = "yyyy-MM-dd HH:mm:ss a"
const val DATE_TIME_FORMAT_4 = "dd-MM-yyyy HH:mm:ss a"
const val DATE_TIME_FORMAT_5 = "yyyy-MM-dd"
const val DATE_TIME_FORMAT_6 = "dd-MM-yyyy"
const val DATE_TIME_FORMAT_7 = "h:mm a"
const val DATE_TIME_FORMAT_8 = "EEE, d MMM yyyy"
const val DATE_TIME_FORMAT_9 = "dd MMM, yyyy"
const val DATE_TIME_FORMAT_10 = "d MMM, yyyy h:mm aa"
const val DATE_TIME_FORMAT_11 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val DATE_TIME_FORMAT_12 = "d MMM, yyyy"
const val DATE_TIME_FORMAT_13 = "yyyy-MM-dd'T'HH:mm:ss.SSS+hh:mm"
const val DATE_TIME_FORMAT_14 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
const val DATE_TIME_FORMAT_15 = "yyyy-MM-dd'T'HH:mm:ss"

fun Context.openDatePicker(
    title: String,
    fm: FragmentManager,
    year: String = "",
    month: String = "",
    day: String = "",
    disableFutureFrom: Long? = null,
    disablePastFrom: Long? = null,
    selectedDate: (year: String, month: String, day: String) -> Unit,
) {
    /*DatePickerMax()
        .title(title)
        .selectedDate(year, month, day)
        .setDisableFutureFrom(disableFutureFrom)
        .setDisablePastFrom(disablePastFrom)
        .fragmentManager(fm)
        .onSelected { year, month, day ->
            selectedDate(year, month, day)
        }.build()*/
}

fun openTimePicker(
    fm: FragmentManager,
    hour: Int? = null,
    min: Int? = null,
    selectedTime: (hour: Int, min: Int) -> Unit
) {
    val picker =
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(hour ?: 12)
            .setMinute(min ?: 0)
            .build()
    picker.show(fm, "timePicker")
    picker.addOnPositiveButtonClickListener {
        selectedTime(picker.hour, picker.minute)
    }
}

/**
 * this method will split your date and return specific a [Year] [Month] [Day]
 * for getting splitted date please convert your date into [DATE_TIME_FORMAT_5]
 */
fun getSplitDateInfo(
    date: String?,
    isYear: Boolean = false,
    isMonth: Boolean = false,
    isDay: Boolean = false
): String {
    if (date == null || date == "") return ""
    else {
        try {
            date.split("-").let {
                return when {
                    isYear -> it[0]
                    isMonth -> it[1]
                    isDay -> it[2]
                    else -> ""
                }
            }
        } catch (exp: Exception) {
            return ""
        }
    }
}

fun getDateInRequestedFormat(
    calendar: Calendar,
    dateFormat: String = DATE_TIME_FORMAT_5
): String {

    return try {
        val formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        formatter.format(calendar.time)
    } catch (exp: Exception) {
        ""
    }
}

fun getDateInRequestedFormat(
    date: String,
    currDateFormat: String,
    outputDateFormat: String = DATE_TIME_FORMAT_5
): String {
    return try {
        val originalFormat: DateFormat = SimpleDateFormat(currDateFormat, Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat(outputDateFormat, Locale.ENGLISH)
        val newDate = originalFormat.parse(date)
        targetFormat.format(newDate)
    } catch (exp: Exception) {
        ""
    }
}

fun getDateFromUnixTimeStamp(timeStamp: Long, dateFormat: String): String {
    return try {
        val calendar = getCalendarInstance(unixTimeStamp = timeStamp)
        getDateInRequestedFormat(calendar, dateFormat)
    } catch (exp: Exception) {
        ""
    }
}

fun getDateFromUnixTimeStamp(timeStamp: String, dateFormat: String): String {
    return try {
        val calendar = getCalendarInstance(unixTimeStamp = timeStamp.toLong())
        getDateInRequestedFormat(calendar, dateFormat)
    } catch (exp: Exception) {
        ""
    }
}

fun getCurrentDateAndTime(dateFormat: String): String {
    val calendarInstance = Calendar.getInstance()
    val df: DateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)

    return df.format(calendarInstance.time)
}

fun getCalendarInstance(
    year: Int? = null,
    month: Int? = null,
    day: Int? = null,
    hour: Int? = null,
    min: Int? = null,
    unixTimeStamp: Long? = null
): Calendar {
    return Calendar.getInstance().apply {
        unixTimeStamp?.let {
            timeInMillis = unixTimeStamp * 1000L
            return@apply
        }
        year?.let { this[Calendar.YEAR] = it }
        month?.let { this[Calendar.MONTH] = it - 1 }
        day?.let { this[Calendar.DATE] = it }
        hour?.let { this[Calendar.HOUR_OF_DAY] = it }
        min?.let { this[Calendar.MINUTE] = it }
    }
}

fun getDifferenceTime(
    dateFormat: String,
    startDate: String,
    endDate: String
): TimeDifference? {
    val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
    return try {
        val startDate = simpleDateFormat.parse(startDate)
        val endDate = simpleDateFormat.parse(endDate)
        var different = endDate.time - startDate.time
        val secondsInMilli = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        val elapsedDays = different / daysInMilli
        different %= daysInMilli

        val elapsedHours = different / hoursInMilli
        different %= hoursInMilli

        val elapsedMinutes = different / minutesInMilli
        different %= minutesInMilli

        val elapsedSeconds = different / secondsInMilli
        val elapsedYears = elapsedDays / 365
        TimeDifference(elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds, elapsedYears)

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getUtcFormatDateAndTime(calendar: Calendar) {
    val time = calendar.time
    /*val outputFmt = SimpleDateFormat(DATE_TIME_FORMAT_3)
    val dateAsString = outputFmt.format(time)
    Logs.e("Date And Time : $dateAsString")*/
    val timestamp = calendar.timeInMillis
    val date = Date(timestamp)
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ")   //yyyy-MM-dd'T'HH:mm:ss.SSSXXX
    val formattedDate = sdf.format(date)

}

fun getSystemTimeMilliSeconds(): Long = System.currentTimeMillis()

fun getUnixTimeStamp(calendar: Calendar): Long = calendar.timeInMillis / 1000L

fun getDay(date: String?): String = getSplitDateInfo(date, isDay = true)

fun getMonth(date: String?): String = getSplitDateInfo(date, isMonth = true)

fun getYear(date: String?): String = getSplitDateInfo(date, isYear = true)

fun getDateFromStringDate(date: String, dateFormat: String): Date? {
    val format: DateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)

    return if (date.isNotEmpty()) {
        format.parse(date)
    } else {
        null
    }
}

data class TimeDifference(
    val days: Long? = null,
    val hours: Long? = null,
    val minutes: Long? = null,
    val seconds: Long? = null,
    val years: Long? = null
)

