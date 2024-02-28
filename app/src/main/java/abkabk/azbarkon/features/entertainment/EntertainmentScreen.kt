package abkabk.azbarkon.features.entertainment

import abkabk.azbarkon.R
import abkabk.azbarkon.library.designsystem.base.Toolbar
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EntertainmentScreen(
    onHomeClick: () -> Unit,
    onBackPressed: () -> Unit,
    viewModel: EntertainmentViewModel = hiltViewModel()
) {

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

        Box(modifier = Modifier.fillMaxSize().padding(it)){

        }

    }


}



