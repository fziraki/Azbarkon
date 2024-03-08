package abkabk.azbarkon.features.entertainment

import abkabk.azbarkon.R
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.collectAsLifecycleAwareState
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaalModalBottomSheet(
    onDismiss: () -> Unit,
    viewModel: FaalViewModel = hiltViewModel()
){


    val uiState = viewModel.state.collectAsLifecycleAwareState()
    val isDark = isSystemInDarkTheme()

    LaunchedEffect(Unit){
        viewModel.getFaal()
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        modifier = Modifier.fillMaxSize().padding(top = 17.dp),
        sheetState = rememberModalBottomSheetState(),
        shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp),
        containerColor = AzbarkonTheme.colors.background,
        tonalElevation = 8.dp,
        dragHandle = { BottomSheetDefaults.DragHandle(color = AzbarkonTheme.colors.unSelectedNav) }
    ) {

        AndroidView(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 48.dp),
            factory = { context ->
                return@AndroidView WebView(context).apply {
                    if (isDark){
                        setBackgroundColor(ContextCompat.getColor(context, R.color.brown_8b7160))
                    }else{
                        setBackgroundColor(ContextCompat.getColor(context, R.color.beige_c7b299))
                    }
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                    settings.defaultFontSize = 36
                    isHorizontalScrollBarEnabled = false
                    isVerticalScrollBarEnabled = true
                }
            },
            update = { webView ->
                uiState.value.poemDetails?.htmlText?.let {
                    val html = it
                        .replace("class=\"m1\"", "style=\'text-align: right;\'")
                        .replace("class=\"m2\"", "style=\'text-align: left;\'")
                        .replace("class=\"b2\"", "style=\'text-align: center;\'")
                    webView.loadData(html, "text/html", "UTF-8")
                }
            }
        )

    }

}