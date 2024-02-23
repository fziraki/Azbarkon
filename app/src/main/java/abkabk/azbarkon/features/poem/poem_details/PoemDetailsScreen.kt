package abkabk.azbarkon.features.poem.poem_details

import abkabk.azbarkon.R
import abkabk.azbarkon.features.poem.poem_list.Toolbar2
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.collectAsLifecycleAwareState
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PoemDetailsScreen(
    onBackPressed: () -> Unit,
    viewModel: PoemDetailsViewModel = hiltViewModel()
) {

    val uiState = viewModel.state.collectAsLifecycleAwareState()

    Scaffold(
        topBar = {
            Toolbar2(title = uiState.value.poemTitle?:"", onBackPressed = onBackPressed)
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
        containerColor = AzbarkonTheme.colors.background
    ) { paddingValues ->

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding() + 16.dp,
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
            factory = { context ->
                return@AndroidView WebView(context).apply {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.color_c7b299))
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
                    webView.loadData(html, "text/html", "UTF-8")
                }
            }
        )
    }


}

@Composable
fun ActionMenu(
    uiState: State<PoemDetailsState>,
    onLikeToggle: () -> Unit,
    onShareClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(AzbarkonTheme.colors.unSelectedNav)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            if (!uiState.value.isLiked){
                painterResource(id = R.drawable.ic_like_border)
            }else{
                painterResource(id = R.drawable.ic_like_filled)
            },
            contentDescription = "like",
            tint = AzbarkonTheme.colors.selectedNav,
            modifier = Modifier.clickable { onLikeToggle() }
        )


        Icon(
            painterResource(id = R.drawable.ic_share),
            contentDescription = "share",
            tint = AzbarkonTheme.colors.selectedNav,
            modifier = Modifier.clickable { onShareClicked() }
        )


    }
}