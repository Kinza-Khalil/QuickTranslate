package com.example.quicktranslate

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIApi {
    @Headers("Authorization: Bearer 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", "Content-Type: application/json")
    @POST("v1/engines/text-davinci-002/completions")
    fun translateText(@Body body: TranslateRequestBody): Call<TranslateResponseBody>
}
