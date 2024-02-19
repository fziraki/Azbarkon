package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.R
import abkabk.azbarkon.features.poet.model.PoetUi
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.LoadImage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun PoetItem(
    modifier: Modifier,
    poet: PoetUi
) {

    Card(
        border = if (poet.isSelected){
            BorderStroke(3.dp, color = AzbarkonTheme.colors.unSelectedNav)
        }else{
            BorderStroke(3.dp, color = Color.Transparent)
        },
    ) {
        Column(
            modifier = modifier
                .wrapContentHeight()
                .background(
                    color = AzbarkonTheme.colors.poetBg,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            LoadImage(
                modifier = Modifier.size(80.dp),
                url = poet.loadableImageUrl,
                errorImage = ""
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = poet.name ?: "",
                style = AzbarkonTheme.typography.title1,
                color = AzbarkonTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )

        }
    }

    AnimatedVisibility(
        visible = poet.isPinned,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(modifier = Modifier.padding(4.dp), contentAlignment = Alignment.TopEnd) {

            Icon(
                painter = painterResource(id = R.drawable.ic_pin_two_tone),
                contentDescription = "",
                modifier = Modifier.size(20.dp),
                tint = AzbarkonTheme.colors.pinTint
            )
        }
    }



}