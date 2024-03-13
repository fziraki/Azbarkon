package abkabk.azbarkon.utils

import abkabk.azbarkon.ui.theme.AzbarkonTheme
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoadImage(
    modifier: Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(5.dp),
    url: String,
    errorImage: String,
    contentScale: ContentScale = ContentScale.Fit,
    hasShimmer: Boolean = true
){

    val context = LocalContext.current

    val showShimmer = remember { mutableStateOf(false) }
    val imageUrl = remember { mutableStateOf(url) }

    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl.value)
        .memoryCacheKey(imageUrl.value)
        .diskCacheKey(imageUrl.value)
        .build()

    SubcomposeAsyncImage(
        modifier = modifier
            .then(
                if (showShimmer.value) {
                    modifier.background(brush = shimmerEffectBrush(), shape = shape)
                } else {
                    modifier.padding()
                }
            ),
        model = imageRequest,
        contentDescription = "image",
        onLoading = {
            if (hasShimmer){
                showShimmer.value = true
            }
        },
        onSuccess = {
            if (hasShimmer){
                showShimmer.value = false
            }
        },
        onError = {
            if (hasShimmer){
                showShimmer.value = false
            }
        },
        error = { imageUrl.value = errorImage },
        contentScale = contentScale,
        imageLoader = context.imageLoader,
    )
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun <T> StateFlow<T>.collectAsLifecycleAwareState(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): State<T> {
    return rememberFlowWithLifecycle(
        flow = this,
        lifecycle = lifecycle,
        minActiveState = minActiveState
    ).collectAsState(initial = value)
}

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}


fun <A> String.navFromJson(type: Class<A>): A {
    return Gson().fromJson(this, type)
}

fun <A> A.navToJson(): String? {
    return Gson().toJson(this)
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Composable
fun String.highlightWord(
    searchQuery: String
): AnnotatedString {

    var annotatedString = buildAnnotatedString {}
    this.let {
        annotatedString = buildAnnotatedString { append(it) }
    }
    annotatedString = buildAnnotatedString {
        val startIndex = annotatedString.indexOf(searchQuery)
        val endIndex = startIndex + searchQuery.length
        append(annotatedString)
        addStyle(style = SpanStyle(color = AzbarkonTheme.colors.highlight), start = startIndex, end = endIndex)
    }

    return annotatedString
}