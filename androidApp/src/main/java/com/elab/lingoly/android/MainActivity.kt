package com.elab.lingoly.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.elab.lingoly.Greeting
import com.elab.lingoly.di.AndroidPlatformModule
import com.elab.lingoly.di.ServiceLocator
import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.utils.DataResult
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ServiceLocator.init(AndroidPlatformModule(this))

        lifecycleScope.launch {
            val dialogRepo = ServiceLocator.dialogRepository
            val result = dialogRepo.getAllDialogs()

            when (result) {
                is DataResult.Success -> {
                    val dialogs = result.data
                    Log.d("JsonTest", "Dialogs carregados: ${dialogs.size}")
                    Log.d("JsonTest", "Primeiro diálogo: ${dialogs.firstOrNull()?.title?.get(
                        Language.ENGLISH)}")
                }
                is DataResult.Error -> {
                    Log.e("JsonTest", "Erro ao carregar diálogos: ${result.message}", result.throwable)
                }
                is DataResult.Loading -> {
                    Log.d("JsonTest", "Carregando diálogos...")
                }
            }
        }


        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(Greeting().greet())
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
