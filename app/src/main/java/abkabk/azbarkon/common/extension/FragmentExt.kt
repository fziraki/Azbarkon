package abkabk.azbarkon.common.extension

import abkabk.azbarkon.databinding.ProgressLoadingBinding
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

fun Fragment.showLoadingDialog(): AlertDialog {
    val builder = AlertDialog.Builder(
        requireActivity()
    )
    val inflater = LayoutInflater.from(requireActivity())
    val binding = ProgressLoadingBinding.inflate(inflater)
    builder.setView(binding.root)
    builder.setCancelable(false)
    val dialog = builder.create()
    dialog.setOnKeyListener { _, i, keyEvent ->
        if (i == KeyEvent.KEYCODE_BACK &&
            keyEvent.action == KeyEvent.ACTION_UP &&
            !keyEvent.isCanceled
        ) {
            dialog.dismiss()
            if (!NavHostFragment.findNavController(this).popBackStack())
                activity?.finish()
            return@setOnKeyListener true
        }
        return@setOnKeyListener false
    }
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
    return dialog
}
