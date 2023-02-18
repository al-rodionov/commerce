package com.commerce.util


private const val INDENTATION_LEVEL: Int = 98
private val SPACES: String = " ".repeat(INDENTATION_LEVEL)
private const val REPLACEABLE: String = "\n"

fun addIndentation(grpcObject: Any): String {
    return REPLACEABLE.plus(grpcObject.toString())
        .replace(REPLACEABLE, REPLACEABLE + SPACES)
}