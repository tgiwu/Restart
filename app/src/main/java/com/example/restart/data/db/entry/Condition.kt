package com.example.restart.data.db.entry

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
) {
    override fun toString(): String {
        return "Condition(code=$code, icon='$icon', text='$text')"
    }
}