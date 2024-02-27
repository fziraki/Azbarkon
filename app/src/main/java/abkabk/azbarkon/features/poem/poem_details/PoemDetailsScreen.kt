package abkabk.azbarkon.features.poem.poem_details

import abkabk.azbarkon.R
import abkabk.azbarkon.library.designsystem.base.ActionMenu
import abkabk.azbarkon.library.designsystem.base.Toolbar
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.collectAsLifecycleAwareState
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PoemDetailsScreen(
    onHomeClick: () -> Unit,
    onBackPressed: () -> Unit,
    viewModel: PoemDetailsViewModel = hiltViewModel()
) {

    val uiState = viewModel.state.collectAsLifecycleAwareState()

    val isDark = isSystemInDarkTheme()

    Scaffold(
        topBar = {
            Toolbar(
                title = uiState.value.poemTitle?:"",
                onHomeClick = onHomeClick,
                onBackPressed = onBackPressed
            )
        },
        bottomBar = {
            ActionMenu(
                uiState,
                onLikeToggle = {
                   viewModel.onUiEvent(PoemDetailsViewModel.Events.ToggleLike)
                }
            ) {

            }
        },
        containerColor = AzbarkonTheme.colors.background,
    ) { paddingValues ->

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
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
                    isVerticalScrollBarEnabled = false
                }
            },
            update = { webView ->
                uiState.value.poemDetails?.htmlText?.let {
                    val html = it
                        .replace("class=\"m1\"", "style=\'text-align: right;\'")
                        .replace("class=\"m2\"", "style=\'text-align: left;\'")
                        .replace("class=\"b2\"", "style=\'text-align: center;\'")
                    webView.loadData("&nbsp".plus(html), "text/html", "UTF-8")
                }
            }
        )
    }


}

