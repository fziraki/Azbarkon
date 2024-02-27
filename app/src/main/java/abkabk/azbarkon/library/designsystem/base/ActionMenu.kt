package abkabk.azbarkon.library.designsystem.base

import abkabk.azbarkon.R
import abkabk.azbarkon.features.poem.poem_details.PoemDetailsState
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import androidx.compose.runtime.Composable
import abkabk.azbarkon.utils.noRippleClickable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun ActionMenu(
    uiState: State<PoemDetailsState>,
    onLikeToggle: () -> Unit,
    onShareClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .border(1.dp, AzbarkonTheme.colors.onSurface.copy(alpha = 0.1f))
            .shadow(
                elevation = 10.dp,
                clip = true,
                ambientColor = AzbarkonTheme.colors.onSurface
            )
            .fillMaxWidth()
            .height(56.dp)
            .background(AzbarkonTheme.colors.background)
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
            modifier = Modifier.noRippleClickable { onLikeToggle() }
        )


        Icon(
            painterResource(id = R.drawable.ic_share),
            contentDescription = "share",
            tint = AzbarkonTheme.colors.selectedNav,
            modifier = Modifier.noRippleClickable { onShareClicked() }
        )


    }
}