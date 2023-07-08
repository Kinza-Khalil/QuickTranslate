package com.example.quicktranslate

data class TranslateResponseBody(
    val id: String,
    val obj: String,
    val created: Int,
    val model: String,
    val choices: List<Choice>
)

data class Choice(
    val text: String
)
