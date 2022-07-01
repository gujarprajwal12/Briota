package app.briota.sia.Front_End.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.Front_End.view.adapter.VideoAdapter
import app.briota.sia.R
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class VideoPlayerActivity : AppCompatActivity() {

    private var youtubeVideos = Vector<youTubeVideos>()
    private lateinit var videoRecycleview: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        initView()

        onClick()


        youtubeVideos.add(youTubeVideos("Inhaler Training","<iframe width=\"100%\" height=\"100%\" src=\"https://www" +
                ".youtube.com/embed/fHYTz-ZoRLw\" frameborder=\"0\" fullscreen></iframe>","How to use Metered Dose Inhaler (MDI)"))
        youtubeVideos.add(youTubeVideos("Breathing Exercises","<iframe width=\"100%\" height=\"100%\" src=\"https://www" +
                ".youtube.com/embed/gd1r2o25k7w\" frameborder=\"0\" fullscreen></iframe>","How to use Metered Dose Inhaler (MDI)"))
        youtubeVideos.add(youTubeVideos("Breathing Exercise for Relaxation or COPD","<iframe width=\"100%\" height=\"100%\" src=\"https://www" +
                ".youtube.com/embed/-7-CAFhJn78\" frameborder=\"0\" fullscreen></iframe>","How to make lungs healthy by doing breathing exercise"))
        youtubeVideos.add(youTubeVideos("Improve Oxygen Level & Lungs Function","<iframe width=\"100%\" height=\"100%\" src=\"https://www" +
                ".youtube.com/embed/hRW7UUzxYzw\" frameborder=\"0\" fullscreen></iframe>","How to make lungs healthy by doing breathing exercise"))



        val videoAdapter = VideoAdapter(youtubeVideos)
        videoRecycleview.adapter = videoAdapter
    }

    private fun onClick() {

        back_tol.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initView() {

        txtTitle.setText("Guidance Videos")

        videoRecycleview = findViewById(R.id.video_RecyclerView)
        videoRecycleview.setHasFixedSize(true)
        videoRecycleview.layoutManager = LinearLayoutManager(this)
    }
}