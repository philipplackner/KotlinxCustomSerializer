package com.plcoding.kotlinxcustomserializer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.plcoding.kotlinxcustomserializer.ui.theme.KotlinxCustomSerializerTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.DefaultJson
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val httpClient = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }

        val jsonObjectEndpoint = "https://openlibrary.org/works/OL82563W.json"
        val stringEndpoint = "https://openlibrary.org/works/OL1968368W.json"
        lifecycleScope.launch {
            listOf(
                stringEndpoint,
                jsonObjectEndpoint
            ).forEach { url ->
                val response = httpClient.get(
                    urlString = url
                )
                val dto = response.body<BookWorkDto>()

                println("Book description is ${dto.description}")
            }
        }

        setContent {
            KotlinxCustomSerializerTheme {

            }
        }
    }
}