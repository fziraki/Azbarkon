package abkabk.azbarkon.features.poet.poet_details.memento

import abkabk.azbarkon.features.poet.poet_details.PoetDetailsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Editor {

    private val editorScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    lateinit var content : PoetDetailsState

    val contenttt = MutableStateFlow(PoetDetailsState())

    fun createState(): EditorState {
        return EditorState(content)
    }
    fun restoreState(editorState: EditorState){
        content = editorState.contentt
        editorScope.launch {
            try {
                contenttt.emit(content)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

    }

}