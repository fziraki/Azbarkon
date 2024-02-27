package abkabk.azbarkon.features.editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EditorScreen(
    onHomeClick: () -> Unit,
    onBackPressed: () -> Unit,
    viewModel: EditorViewModel = hiltViewModel()
){

    Scaffold(

    ) {

        Box(modifier = Modifier.fillMaxSize().padding(it)){

            Text(text = "editor")
        }

    }
}