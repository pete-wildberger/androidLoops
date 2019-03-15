
package com.example.Looper

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.example.Looper.LoopPlayer.LoopPlayer
import kotlinx.android.synthetic.main.activity_main.*
//import java.io.File
//import java.io.FileOutputStream
//import java.io.FileNotFoundException
import java.io.IOException

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.*
import android.R.attr.data
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var paths: ArrayList<String>? = null
    var adapter: ArrayAdapter<String>? = null
    private var mediaRecorder: MediaRecorder? = null
    private var start: Boolean = true
    private var tracks: HashMap<String, LoopPlayer> = hashMapOf()
    private var parent: String = Environment.getExternalStorageDirectory().absolutePath
    private lateinit var lastPath: String
    private lateinit var lv: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        paths = ArrayList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, paths)
        lv = findViewById<ListView>(R.id.listview_1)
        lv.adapter = adapter

        mediaRecorder =  MediaRecorder()

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)


        button_start_recording.setOnClickListener {
            var path: String = parent + now() + "recording.aac"
            var loopPlayer: LoopPlayer? = null
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permissions,0)
            } else {
                if(start){
                    mediaRecorder?.setOutputFile(path)
                    startRecording(path)
                    lastPath = path
                    start = !start

                } else {
                    stopRecording(lastPath)
                    loopPlayer = LoopPlayer(lastPath)
                    tracks[lastPath] = loopPlayer
                    paths?.add(lastPath)
                    tracks[lastPath]?.startPlaying()
                }
            }

        }

    }

    private fun startRecording(path: String) {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            Toast.makeText(this, "Recording started!" + path, Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private fun stopRecording(path: String) {
        try {
            mediaRecorder?.stop()
            mediaRecorder?.reset()
            tracks[path]?.startPlaying()
            start = true
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
//    override fun onRequestPermissionsResult(
//        requestCode: Int, permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 10) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startRecording()
//            } else {
//                //User denied Permission.
//            }
//        }
//    }
    override fun onDestroy() {
        super.onDestroy()
        deleteFile(Environment.getExternalStorageDirectory().absolutePath + "/Looper/")
    }
}