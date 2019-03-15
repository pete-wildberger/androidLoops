package com.example.Looper.LoopPlayer

import android.media.MediaPlayer
import android.util.Log
import java.io.IOException


class LoopPlayer{
    private var path: String
    private var mediaPlayer: MediaPlayer? = null
    constructor(path: String){
        this.path = path
    }

    fun startPlaying() {
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(path)
                setLooping(true)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e(path, "start() failed")
            }
        }
    }

    fun stopPlaying() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}