package com.example.quicktranslate

data class TranslateRequestBody(

    val prompt: String,
    val max_tokens: Int = 60,
)
