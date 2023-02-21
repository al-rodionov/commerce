package com.commerce.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private val DATE_PATTERN: String = "yyyy-MM-dd\'T\'HH:mm:ss\'Z\'"

fun parseDate(date: String): LocalDateTime {
    val pattern = DateTimeFormatter.ofPattern(DATE_PATTERN)
    return LocalDateTime.parse(date, pattern)
}

fun formatDate(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
    return date.format(formatter)
}