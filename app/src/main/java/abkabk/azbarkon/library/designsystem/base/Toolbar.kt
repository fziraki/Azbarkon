package abkabk.azbarkon.library.designsystem.base

import abkabk.azbarkon.R
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.noRippleClickable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun Toolbar(
    title: String,
    onHomeClick: () -> Unit,
    onBackPressed: () -> Unit
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
            .height(48.dp)
            .background(AzbarkonTheme.colors.background)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
        ) {

            Icon(
                painterResource(id = R.drawable.ic_return_home),
                contentDescription = "return home",
                tint = AzbarkonTheme.colors.selectedNav,
                modifier = Modifier.wrapContentSize().noRippleClickable { onHomeClick() }
            )
            Text(
                modifier = Modifier.wrapContentSize(),
                text = title,
                color = AzbarkonTheme.colors.selectedNav,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis
            )

        }

        Icon(
            painterResource(id = R.drawable.ic_back),
            contentDescription = "back",
            tint = AzbarkonTheme.colors.selectedNav,
            modifier = Modifier.wrapContentSize().noRippleClickable { onBackPressed() }
        )


    }
}