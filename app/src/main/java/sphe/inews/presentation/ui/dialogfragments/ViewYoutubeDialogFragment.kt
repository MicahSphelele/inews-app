package sphe.inews.presentation.ui.dialogfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import sphe.inews.R
import sphe.inews.databinding.FragmentViewYoutubeBinding

@AndroidEntryPoint
class ViewYoutubeDialogFragment : DialogFragment(), YouTubePlayerListener {


    companion object {
        const val URL = "videoUrl"
    }

    private lateinit var videoUrl: String

    private lateinit var binding: FragmentViewYoutubeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)

//        dialog?.window?.let {
//            dialog?.window?.attributes?.windowAnimations =
//                R.style.FullScreenDialogStyle //Property access syntax
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_youtube, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentViewYoutubeBinding.bind(view)

        videoUrl = arguments?.getString(URL)!!

        binding.youtubePlayerView.initialize(this)

        (binding.toolbar as Toolbar).navigationIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_action_home)

        (binding.toolbar as Toolbar).setNavigationOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        dialog?.window.let {
            dialog?.window?.setLayout(width, height)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.let {
            binding.youtubePlayerView.release()
        }

    }

    override fun onApiChange(youTubePlayer: YouTubePlayer) {

    }

    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {

    }

    override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {

    }

    override fun onPlaybackQualityChange(
        youTubePlayer: YouTubePlayer,
        playbackQuality: PlayerConstants.PlaybackQuality
    ) {

    }

    override fun onPlaybackRateChange(
        youTubePlayer: YouTubePlayer,
        playbackRate: PlayerConstants.PlaybackRate
    ) {

    }

    override fun onReady(youTubePlayer: YouTubePlayer) {
        youTubePlayer.cueVideo(videoUrl.split("=")[1], 0f)
    }

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {

    }

    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {

    }

    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {

    }

    override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {

    }
}