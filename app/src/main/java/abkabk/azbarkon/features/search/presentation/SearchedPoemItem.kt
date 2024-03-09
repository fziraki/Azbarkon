package abkabk.azbarkon.features.search.presentation

import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.highlightWord
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
fun SearchedPoemItem(
    modifier: Modifier,
    poemName: String,
    searchQuery: String,
    searchedPart: String
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(AzbarkonTheme.colors.poetBg, RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = AzbarkonTheme.colors.onSurface,
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                ).padding(8.dp),
            text = poemName,
            style = AzbarkonTheme.typography.title1,
            color = AzbarkonTheme.colors.selectedNav,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            text = searchedPart.highlightWord(searchQuery = searchQuery),
            style = AzbarkonTheme.typography.title1,
            color = AzbarkonTheme.colors.onSurface,
            textAlign = TextAlign.Start,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }




}