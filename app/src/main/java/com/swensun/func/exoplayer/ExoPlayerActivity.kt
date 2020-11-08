package com.swensun.func.exoplayer

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.dueeeke.videoplayer.ui.StandardVideoController
import com.liulishuo.okdownload.DownloadTask
import com.swensun.func.exoplayer.download.DownloadManager
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.exo_player_activity.*
import java.io.File

class ExoPlayerActivity : AppCompatActivity() {

    private var video_url = "https://media6.smartstudy.com/ae/07/3997/2/dest.m3u8"

    private var task: DownloadTask? = null

    private var local_video_url = PathUtils.getExternalAppDownloadPath() + "/Video/dest/dest.m3u8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exo_player_activity)
        video_view.setUrl(video_url)
        val control = StandardVideoController(this)
        control.addDefaultControlComponent("dest", false)
        control.setEnableOrientation(true)
        video_view.videoController = control
        video_view.skipPositionWhenPlay(10000)
        video_view.start()
        fab.setOnClickListener {
            video_view.replay(false)
        }

        delete_file.setOnClickListener {
            FileUtils.delete(PathUtils.getExternalAppDownloadPath() + "/Video/dest")
        }

        download_m3u8.setOnClickListener {
            task = DownloadManager.downloadM3u8(video_url, "dest")
        }
        download_cancel.setOnClickListener {
            task?.cancel()
        }
    }

    override fun onPause() {
        super.onPause()
        video_view.pause()
    }


    override fun onResume() {
        super.onResume()
        video_view.resume()
    }


    override fun onDestroy() {
        super.onDestroy()
        video_view.release()

    }

    override fun onBackPressed() {
        if (!video_view.onBackPressed()) {
            super.onBackPressed()
        }
    }
}
