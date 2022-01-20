package com.example.alarmproject.model.alarm

data class AlarmTag(
    var title: String = "",
    var timeMilliSecond: Long? = 0,
    var isDay: Boolean = false,
    var isWeekend: Boolean = false,
    var soundId: Int = 0,
    var days: Days = Days(),
    var repeatCycle: Int = 0,
    var groupType: Int = 0,
    var label: Int = 0
) {
    data class Days(
        var monday: Boolean = false,
        var tuesday: Boolean = false,
        var wednesday: Boolean = false,
        var thursday: Boolean = false,
        var friday: Boolean = false,
        var saturday: Boolean = false,
        var sunday: Boolean = false
    )
}