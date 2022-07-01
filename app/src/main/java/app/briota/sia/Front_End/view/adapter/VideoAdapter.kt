package app.briota.sia.Front_End.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.Front_End.UI.youTubeVideos
import app.briota.sia.R

class VideoAdapter internal constructor(private val youtubeVideoList: List<youTubeVideos>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.web_layout, parent, false)
        return VideoViewHolder(view)
    }
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.videoWeb.loadData(youtubeVideoList[position].videoUrl!!, "text/html", "utf-8")
        holder.videoTitle.setText(youtubeVideoList[position].videoTitle)
        holder.videoDesc.setText(youtubeVideoList[position].videoDesc)
    }
    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var videoWeb: WebView = itemView.findViewById(R.id.webView)
        var videoTitle : TextView = itemView.findViewById(R.id.videoTitle)
        var videoDesc : TextView = itemView.findViewById(R.id.videoDesc)
        init {
            videoWeb.settings.javaScriptEnabled = true
            videoWeb.settings.lightTouchEnabled = false
            videoWeb.webChromeClient = object : WebChromeClient() {
            }
        }
    }
    override fun getItemCount(): Int {
        return youtubeVideoList.size
    }
}