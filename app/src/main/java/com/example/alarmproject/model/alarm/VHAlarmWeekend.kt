package com.example.alarmproject.model.alarm

data class VHAlarmWeekend(
    val weekend : String ?= null,
    val isChecked : Boolean = false
){
    class WeekendProperty() {
        companion object{
            const val PROPERTY_MONDAY = "월"
            const val PROPERTY_TUESDAY = "화"
            const val PROPERTY_WEDNESDAY = "수"
            const val PROPERTY_THURSDAY = "목"
            const val PROPERTY_FRIDAY = "금"
            const val PROPERTY_SETURDAY = "토"
            const val PROPERTY_SUNDAY = "일"
        }
    }
}
