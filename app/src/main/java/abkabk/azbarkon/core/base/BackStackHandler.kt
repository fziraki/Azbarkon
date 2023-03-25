package abkabk.azbarkon.core.base


interface BackStackHandler {
    fun onBackPressed()
    fun shouldOverrideBackPressed(): Boolean
}

