package abkabk.azbarkon.library.designsystem.base

import abkabk.azbarkon.R
import abkabk.azbarkon.features.poet.model.PoetUi
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun PinHeader(
    selectedPoets: List<PoetUi>,
    onCloseClicked: () -> Unit,
    onTogglePin: () -> Unit
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

        Text(
            modifier = Modifier
                .noRippleClickable {
                    onTogglePin()
                }
                .wrapContentSize(),
            text = if (selectedPoets.any { !it.isPinned }) {
                stringResource(R.string.pin)
            } else {
                stringResource(R.string.unpin)

            },
            style = AzbarkonTheme.typography.title1,
            color = AzbarkonTheme.colors.selectedNav,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier
                .noRippleClickable { onCloseClicked() }
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = selectedPoets.size.toString(),
                color = AzbarkonTheme.colors.selectedNav,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                painterResource(id = R.drawable.ic_close),
                contentDescription = "close icon",
                tint = AzbarkonTheme.colors.selectedNav
            )
        }
    }
}