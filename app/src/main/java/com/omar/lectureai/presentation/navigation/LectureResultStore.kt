package com.omar.lectureai.presentation.navigation

import com.omar.lectureai.domain.model.Lecture

/**
 * Holds the latest processed [Lecture] between Home and Result destinations.
 * Replace with type-safe navigation arguments or SavedStateHandle when wiring a real flow.
 */
class LectureResultStore {
    @Volatile
    private var pending: Lecture? = null

    fun set(lecture: Lecture) {
        pending = lecture
    }

    fun take(): Lecture? {
        val value = pending
        pending = null
        return value
    }
}
