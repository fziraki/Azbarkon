package abkabk.azbarkon.features.poet.poet_details

import abkabk.azbarkon.ui.theme.AzbarkonTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CategoryItem(
    modifier: Modifier,
    categoryName: String
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 32.dp)
            .background(AzbarkonTheme.colors.poetBg, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            text = categoryName,
            style = AzbarkonTheme.typography.title1,
            color = AzbarkonTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }




}