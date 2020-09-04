package sphe.inews.ui.main.dialogfragments

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
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.fragment_view_youtube.*
import sphe.inews.R
import javax.inject.Inject

class ViewYoutubeDialogFragment @Inject constructor(): DaggerDialogFragment(), YouTubePlayerListener {


    companion object{
      const val URL = "videoUrl"
    }

    private lateinit var videoUrl : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_youtube, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoUrl = arguments?.getString(URL)!!
        
        youtube_player_view.initialize(this)

        (toolbar as Toolbar).navigationIcon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_action_home)

        (toolbar as Toolbar).setNavigationOnClickListener{
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
        youtube_player_view?.let {
            youtube_player_view.release()
        }

    }

    override fun onActivityCreated(args: Bundle?) {
        super.onActivityCreated(args)
        dialog?.window?.let {
            dialog?.window?.attributes?.windowAnimations = R.style.FullScreenDialogStyle //Property access syntax
        }
    }

    override fun onApiChange(youTubePlayer: YouTubePlayer) {

    }

    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {

    }

    override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {

    }

    override fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer,playbackQuality: PlayerConstants.PlaybackQuality) {

    }

    override fun onPlaybackRateChange(youTubePlayer: YouTubePlayer,playbackRate: PlayerConstants.PlaybackRate) {

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