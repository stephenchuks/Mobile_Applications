package com.example.biometricsattendance

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import java.text.SimpleDateFormat
import java.util.*

class AttendanceUtils(private val context: Context) {

    private val attendanceSharedPreferences: SharedPreferences =
        context.getSharedPreferences("AttendancePrefs", Context.MODE_PRIVATE)

    fun checkIn(location: Location): Boolean {
        if (isAlreadyCheckedInToday()) {
            return false // Already checked in for the day
        }

        // Perform GPS location check
        if (isOnOfficePremises(location)) {
            saveCheckInTime()
            return true // Check-in successful
        }

        return false // GPS location does not match office premises
    }

    fun checkOut(location: Location): Boolean {
        if (isAlreadyCheckedOutToday()) {
            return false // Already checked out for the day
        }

        // Perform GPS location check
        if (isOnOfficePremises(location)) {
            saveCheckOutTime()
            return true // Check-out successful
        }

        return false // GPS location does not match office premises
    }

    fun getAttendanceHistory(): List<String> {
        val attendanceSet: Set<String> =
            attendanceSharedPreferences.getStringSet("attendance_history", setOf()) ?: setOf()
        return attendanceSet.toList()
    }

    private fun isAlreadyCheckedInToday(): Boolean {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return attendanceSharedPreferences.getString("check_in_date", "") == currentDate
    }

    private fun isAlreadyCheckedOutToday(): Boolean {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return attendanceSharedPreferences.getString("check_out_date", "") == currentDate
    }

    private fun isOnOfficePremises(location: Location): Boolean {
        // Dummy check for office premises based on latitude and longitude
        val officeLocation = Location("")
        officeLocation.latitude = 37.7749
        officeLocation.longitude = -122.4194

        val distance = location.distanceTo(officeLocation)
        return distance < 1000 // Assume 1000 meters as the office premises
    }

    private fun saveCheckInTime() {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        val checkInDateTime = "$currentDate $currentTime"
        saveToAttendanceHistory("Check-in: $checkInDateTime")

        val editor = attendanceSharedPreferences.edit()
        editor.putString("check_in_date", currentDate)
        editor.apply()
    }

    private fun saveCheckOutTime() {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        val checkOutDateTime = "$currentDate $currentTime"
        saveToAttendanceHistory("Check-out: $checkOutDateTime")

        val editor = attendanceSharedPreferences.edit()
        editor.putString("check_out_date", currentDate)
        editor.apply()
    }

    private fun saveToAttendanceHistory(entry: String) {
        val attendanceSet: MutableSet<String> =
            attendanceSharedPreferences.getStringSet("attendance_history", mutableSetOf()) ?: mutableSetOf()

        attendanceSet.add(entry)
        val editor = attendanceSharedPreferences.edit()
        editor.putStringSet("attendance_history", attendanceSet)
        editor.apply()
    }
}
