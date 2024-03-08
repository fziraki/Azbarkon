package abkabk.azbarkon.features.entertainment

import abkabk.azbarkon.R
import abkabk.azbarkon.library.designsystem.base.Toolbar
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.noRippleClickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EntertainmentScreen(
    onHomeClick: () -> Unit,
    onBackPressed: () -> Unit,
    viewModel: EntertainmentViewModel = hiltViewModel()
) {

    var showSheet by remember { mutableStateOf(false) }


    if (showSheet){
        FaalModalBottomSheet(onDismiss = {
            showSheet = false
        })
    }


        Scaffold(
            topBar = {
                Toolbar(
                    title = stringResource(id = R.string.entertainment),
                    onHomeClick = onHomeClick,
                    onBackPressed = onBackPressed
                )
            },
            containerColor = AzbarkonTheme.colors.background
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ){
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(28.dp)
                ){

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 64.dp)
                            .background(
                                color = AzbarkonTheme.colors.poetBg,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(vertical = 20.dp)
                        ,
                        text = stringResource(id = R.string.guess_next),
                        style = AzbarkonTheme.typography.title1,
                        color = AzbarkonTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 64.dp)
                            .background(
                                color = AzbarkonTheme.colors.poetBg,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(vertical = 20.dp)
                        ,
                        text = stringResource(id = R.string.guess_poet),
                        style = AzbarkonTheme.typography.title1,
                        color = AzbarkonTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = Modifier
                            .noRippleClickable {
                                showSheet = true
                            }
                            .fillMaxWidth()
                            .padding(horizontal = 64.dp)
                            .background(
                                color = AzbarkonTheme.colors.poetBg,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(vertical = 20.dp)
                        ,
                        text = stringResource(id = R.string.faal),
                        style = AzbarkonTheme.typography.title1,
                        color = AzbarkonTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }



        }




}




