package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.R
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchbar(
    modifier: Modifier,
    hint: String,
    searchText: String,
    onValueChange: KFunction1<String, Unit>,
    onSearchClick: () -> Unit
){

    val isKeyboardOpen = WindowInsets.isImeVisible

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
        colors = TextFieldDefaults.textFieldColors(
            containerColor = AzbarkonTheme.colors.background.copy(alpha = 0.7f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = AzbarkonTheme.colors.selectedPoet
        ),
        shape = RoundedCornerShape(10.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search icon",
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

