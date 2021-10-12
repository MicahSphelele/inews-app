package sphe.inews.presentation.ui.permission

import android.Manifest
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sphe.inews.R
import sphe.inews.databinding.FragmentPermissionsBinding
import sphe.inews.util.navigateAndClearBackStack

@AndroidEntryPoint
class PermissionsFragment : Fragment(R.layout.fragment_permissions) {

    companion object {
        const val EXTRA_PERMISSION_TYPE = "permissionType"
        const val TYPE_LOCATION = "location"
    }

    private lateinit var binding: FragmentPermissionsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPermissionsBinding.bind(view)

        val permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    findNavController().navigateAndClearBackStack(fragmentFrom = R.id.permissionsFragment)
                }
            }

        val permissionType = requireArguments().getString(EXTRA_PERMISSION_TYPE, "")

        if (permissionType == TYPE_LOCATION) {
            startOrStopAnimation(animation = R.raw.animation_location, startAnimation = true)
            binding.message = String.format(
                "%s would like to access your device location",
                getString(R.string.app_name)
            )
        }

        binding.btnAllow.setOnClickListener {
            //val rationalDeniedPermission = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
            if (permissionType == TYPE_LOCATION) {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        binding.btnDeny.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    @Suppress("SameParameterValue")
    private fun startOrStopAnimation(
        animation: Int = R.raw.animation_location,
        startAnimation: Boolean = true
    ) {
        if (startAnimation) {
            binding.lottieView.setAnimation(animation)
            binding.lottieView.repeatCount = ValueAnimator.INFINITE
            binding.lottieView.playAnimation()
            return
        }
        binding.lottieView.cancelAnimation()
    }

}