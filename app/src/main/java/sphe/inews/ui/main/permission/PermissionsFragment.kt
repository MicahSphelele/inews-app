package sphe.inews.ui.main.permission

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import sphe.inews.R
import sphe.inews.databinding.FragmentPermissionsBinding

class PermissionsFragment : Fragment(R.layout.fragment_permissions) {

    private lateinit var binding: FragmentPermissionsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPermissionsBinding.bind(view)
    }

}