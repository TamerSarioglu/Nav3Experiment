package com.tamersarioglu.nav3experiment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tamersarioglu.nav3experiment.ui.theme.Nav3ExperimentTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Nav3ExperimentTheme {
                Nav3SampleApp()
            }
        }
    }
}

@Composable
fun Nav3SampleTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Nav3SampleTheme {
        HomeScreen(onNavigateToProducts = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    Nav3SampleTheme {
        ProductListScreen(onProductClick = { _, _ -> })
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    Nav3SampleTheme {
        ProductDetailScreen(
            productId = "1", 
            productName = "Smartphone",
            viewModel = ProductDetailViewModel("1", "Smartphone")
        )
    }
}