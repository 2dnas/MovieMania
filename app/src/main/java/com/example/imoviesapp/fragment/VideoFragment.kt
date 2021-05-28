package com.example.imoviesapp.fragment

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.MediaController
import android.widget.SeekBar
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.imoviesapp.R
import com.example.imoviesapp.databinding.FragmentVideoBinding
import kotlin.math.ln

@RequiresApi(Build.VERSION_CODES.O)
class VideoFragment : Fragment(R.layout.fragment_video) , SurfaceHolder.Callback
    , MediaPlayer.OnPreparedListener ,  SeekBar.OnSeekBarChangeListener {
    private lateinit var binding: FragmentVideoBinding

    lateinit var videoView : VideoView
    lateinit var mediaController: MediaController
    private val mediaPlayer = MediaPlayer()
    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())



    companion object {
        const val GET_VIDEO = 123
        const val SECONDS = 1000

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding.videoView.holder.addCallback(this)
        mediaPlayer.setOnPreparedListener(this)
        binding.seekBar.setOnSeekBarChangeListener(this)



        val audioManager = activity?.applicationContext?.getSystemService(Context.AUDIO_SERVICE) as AudioManager



        binding.playButton.setOnClickListener {
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
                binding.playButton.setImageResource(android.R.drawable.ic_media_play)
            } else {
                mediaPlayer.start()
                binding.playButton.setImageResource(android.R.drawable.ic_media_pause)
            }
        }

        binding.startOver.setOnClickListener {
            mediaPlayer.seekTo(0)
        }
        binding.end.setOnClickListener {
            mediaPlayer.seekTo(mediaPlayer.seconds)
        }

        binding.volumeUp.setOnClickListener {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
        }

        binding.volumeDown.setOnClickListener {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
        }






//        videoView = binding.movieVideo
//        mediaController = MediaController(requireContext())
//
//        videoView.setVideoPath("android.resource://" + activity?.packageName + "/" + R.raw.trailer)
//
//
//        mediaController.setAnchorView(videoView)
//        videoView.setMediaController(mediaController)
//
//        videoView.start()

    }


    private fun timeInString(seconds: Int) : String {
        return String.format("%02d:%02d",(seconds / 3600 * 60 + ((seconds % 3600) / 60)),(seconds % 60))
    }

    private fun initializeSeekBar() {
        binding.seekBar.max = mediaPlayer.seconds
        binding.textProgress.text = "00:00"
        binding.textTotalTime.text = timeInString(mediaPlayer.seconds)
        binding.progressBar.visibility = View.GONE
        binding.playButton.isEnabled = true
    }


    private fun updateSeekBar() {
        runnable = Runnable {
            binding.textProgress.text = timeInString(mediaPlayer.currentSeconds)
            binding.seekBar.progress = mediaPlayer.currentSeconds
            handler.postDelayed(runnable, SECONDS.toLong())
        }
        handler.postDelayed(runnable, SECONDS.toLong())
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mediaPlayer.apply {
            setDataSource(activity?.applicationContext!!,
                Uri.parse("android.resource://${activity?.packageName}/${R.raw.trailer}"))

                setDisplay(holder)
                prepareAsync()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    override fun onPrepared(mp: MediaPlayer?) {
        initializeSeekBar()
        updateSeekBar()
        mediaPlayer.start()
        binding.playButton.setImageResource(android.R.drawable.ic_media_pause)
    }




    private val MediaPlayer.seconds : Int
    get() = this.duration / SECONDS

    private val MediaPlayer.currentSeconds : Int
    get() = this.currentPosition / SECONDS



    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if(fromUser){
            mediaPlayer.seekTo(progress * SECONDS)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }


}