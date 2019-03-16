package com.example.Looper.LoopPlayer

import android.media.MediaPlayer
import android.util.Log
import java.io.IOException
import android.media.AudioManager
import android.os.Build
import android.R
import javax.swing.UIManager.put
import android.util.SparseArray
import android.media.SoundPool
import android.media.AudioAttributes
import android.content.Context.AUDIO_SERVICE
import android.support.v4.content.ContextCompat.getSystemService




class LoopPlayer{
    var SOUNDPOOLSND_MENU_BTN = 0
    var SOUNDPOOLSND_WIN = 1
    var SOUNDPOOLSND_LOOSE = 2
    var SOUNDPOOLSND_DRAW = 3
    var SOUNDPOOLSND_TICK1 = 4
    var SOUNDPOOLSND_TICK2 = 5
    var SOUNDPOOLSND_OUT_OF_TIME = 6
    var SOUNDPOOLSND_HISCORE = 7
    var SOUNDPOOLSND_CORRECT_LETTER = 8

    var isSoundTurnedOff: Boolean = false
    private var mAudioManager: AudioManager? = null
    val streamVolume = mAudioManager?.getStreamVolume(AudioManager.STREAM_MUSIC)

    private var mSoundPoolMap: SparseArray<Int>


    val maxSounds = 4
    private var mSound: SoundPool
    constructor(mContext: Context){
        this.mAudioManager = mContext.getSystemService(Context.AUDIO_SERVICE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            var audioAttrib: AudioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
            mSound = SoundPool.Builder().setAudioAttributes(audioAttrib).setMaxStreams(6).build();
        }
        else {

            mSound = SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        }
    }


    fun SoundManager(mContext: Context) {



        //        mSoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
        //            public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
        //               loaded = true;
        //            }
        //        });

        mSoundPoolMap = SparseArray()
        mSoundPoolMap.put(SOUNDPOOLSND_MENU_BTN, mSound.load(mContext, R.raw.menubutton, 1))

        // testing simultaneous playing

        mSound.play(mSoundPoolMap.get(0), streamVolume.toFloat(), streamVolume.toFloat(), 1, 20, 1f)


    }

    fun playSound(index: Int) {
        if (isSoundTurnedOff)
            return
        var streamVolume: Float = mAudioManager?.getStreamVolume(AudioManager.STREAM_MUSIC)?.toFloat()
        try {
            mSound.play(mSoundPoolMap.get(index), streamVolume.toFloat(), streamVolume.toFloat(), 1, -1, 1f)


    }

//    fun clear() {
//        if (mSoundManager != null) {
//            mSoundManager!!.mSoundPool = null
//            mSoundManager!!.mAudioManager = null
//            mSoundManager!!.mSoundPoolMap = null
//        }
//        mSoundManager = null
//    }
}