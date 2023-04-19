package abkabk.azbarkon.features.poet.poet_details.memento

class History {
    private val states = ArrayList<EditorState>()
    fun push(editorState: EditorState){
        states.add(editorState)
    }
    fun pop(): EditorState{
        states.remove(states[states.lastIndex])
        return states[states.lastIndex]
    }

}