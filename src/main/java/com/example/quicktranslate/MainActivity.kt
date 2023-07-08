package com.example.quicktranslate

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var editTextTextToTranslate: EditText
    private lateinit var editTextFromLanguage: EditText
    private lateinit var editTextToLanguage: EditText
    private lateinit var buttonTranslate: Button
    private lateinit var textViewTranslation: TextView

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(OpenAIApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle(R.string.app_name)
        title = "FastLingo"
        editTextTextToTranslate = findViewById(R.id.editTextTextToTranslate)
        editTextFromLanguage = findViewById(R.id.editTextFromLanguage)
        editTextToLanguage = findViewById(R.id.editTextToLanguage)
        buttonTranslate = findViewById(R.id.buttonTranslate)
        textViewTranslation = findViewById(R.id.textViewTranslation)

        buttonTranslate.setOnClickListener {
            val text = editTextTextToTranslate.text.toString()
            val fromLanguage = editTextFromLanguage.text.toString()
            val toLanguage = editTextToLanguage.text.toString()

            translateText(text, fromLanguage, toLanguage)
        }
    }

    private fun translateText(text: String, fromLanguage: String, toLanguage: String) {
        val prompt = "Translate the following '$fromLanguage' text to '$toLanguage': $text"

        val requestBody = TranslateRequestBody(
            prompt = prompt,
            max_tokens = 60
        )

        val call = service.translateText(requestBody)

        call.enqueue(object : Callback<TranslateResponseBody> {
            override fun onResponse(call: Call<TranslateResponseBody>, response: Response<TranslateResponseBody>) {
                if (response.isSuccessful) {
                    val translatedText = response.body()?.choices?.get(0)?.text ?: "Translation failed."
                    textViewTranslation.text = translatedText
                }  else {
                    // The request was not successful
                    val errorMessage = response.errorBody()?.string() ?: "An unknown error occurred."
                    textViewTranslation.text = errorMessage
                }
            }


            override fun onFailure(call: Call<TranslateResponseBody>, t: Throwable) {
                textViewTranslation.text = "Translation failed."
            }
        })
    }
}
