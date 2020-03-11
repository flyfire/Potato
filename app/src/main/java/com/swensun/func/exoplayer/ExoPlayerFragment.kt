package com.swensun.func.exoplayer

import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.swensun.potato.R
import kotlinx.android.synthetic.main.exo_player_fragment.*

class ExoPlayerFragment : Fragment() {

    companion object {
        fun newInstance() = ExoPlayerFragment()
    }

    private val exoplayer by lazy { SimpleExoPlayer.Builder(requireContext()).build() }

    private lateinit var viewModel: ExoPlayerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.exo_player_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ExoPlayerViewModel::class.java)
        playView.player = exoplayer

        val dataSourceFactory = DefaultDataSourceFactory(context, "swennsun")
        val mp4uri = Uri.parse("https://html5demos.com/assets/dizzy.mp4")
        val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mp4uri)
        exoplayer.prepare(videoSource)
    }

    override fun onDestroy() {
        super.onDestroy()
        exoplayer.release()
    }
}
