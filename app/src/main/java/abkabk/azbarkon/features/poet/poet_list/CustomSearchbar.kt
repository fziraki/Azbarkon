package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.R
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.noRippleClickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.reflect.KFunction1

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomSearchbar(
    modifier: Modifier,
    hint: String,
    searchText: String,
    onValueChange: KFunction1<String, Unit>,
    onSearchClick: () -> Unit,
    onClearClick: () -> Unit
){

    val isKeyboardOpen = WindowInsets.isImeVisible

    val containerColor = AzbarkonTheme.colors.background.copy(alpha = 0.7f)
    TextFieldPaddingLess(
        value = searchText,
        onValueChange = { onValueChange(it) },
        modifier = modifier.height(48.dp),

        placeholder = {
            Text(
                modifier = Modifier.fillMaxHeight(),
                text = hint,
                style = AzbarkonTheme.typography.subtitle1,
                color = AzbarkonTheme.colors.unSelectedNav,
                textAlign = TextAlign.Center
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            cursorColor = AzbarkonTheme.colors.selectedPoet,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(10.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                modifier = Modifier.noRippleClickable {
                    onSearchClick()
                },
                contentDescription = "search icon",
                tint = AzbarkonTheme.colors.unSelectedNav
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                modifier = Modifier.noRippleClickable {
                    onClearClick()
                },
                contentDescription = "close icon",
                tint = AzbarkonTheme.colors.unSelectedNav
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearchClick() },
        ),
        cursorBrush =
        if (isKeyboardOpen){
                    SolidColor(AzbarkonTheme.colors.unSelectedNav)
                }else{
                    SolidColor(Color.Unspecified)
                }
    )
}

