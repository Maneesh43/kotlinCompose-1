// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


suspend fun getConnection(url:String): String {
    val client by lazy{
        HttpClient(CIO){
            install(ContentNegotiation){
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }
    val response =client.request(url){
        method=HttpMethod.Get

    }
    println("Hi")
    client.close()
    return response.status.toString()
}
@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    val responseStatus= remember { mutableStateOf("0") }
    val scope=rememberCoroutineScope()
    MaterialTheme {
        Button(onClick = {
            scope.launch {
                val res=getConnection("https://www.google.com")
                responseStatus.value=res
            }
        }) {
            Text(text = responseStatus.value)
            repeat(10){
                Text(
                    text = "Maneesh"
                )
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
