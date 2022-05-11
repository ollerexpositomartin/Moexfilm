package com.example.moexfilm.views

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.moexfilm.R
import com.example.moexfilm.application.Application.Access.TMDB_IMAGE_URL
import com.example.moexfilm.application.expandCollapseTextView
import com.example.moexfilm.application.loadImage
import com.example.moexfilm.application.round
import com.example.moexfilm.databinding.ActivityDetailsTvShowBinding
import com.example.moexfilm.databinding.ItemEpisodeLayoutBinding
import com.example.moexfilm.models.data.mediaObjects.Episode
import com.example.moexfilm.models.data.mediaObjects.Season
import com.example.moexfilm.models.data.mediaObjects.TvShow
import com.example.moexfilm.util.StringUtil
import java.util.stream.Collectors

class DetailsTvShowActivity : LikeActivity() {
    lateinit var binding:ActivityDetailsTvShowBinding
    private lateinit var tvShow:TvShow
    private lateinit var seasons:List<Season>
    private var correlationViewEpisode:HashMap<View,Episode> = hashMapOf()
    private var expandOrCollapse:Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsTvShowBinding.inflate(layoutInflater)
        btnLike = binding.btnLike
        setTransparentStatusBar()
        setContentView(binding.root)
        getData()
        checkLike()
        loadSpinner()
        setListener()
    }

    private fun setTransparentStatusBar() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

    private fun expandCollapse() {
        binding.tvOverview.expandCollapseTextView()
        expandOrCollapse = if(expandOrCollapse) {
            binding.btnEspandCollapse.setImageResource(R.drawable.ic_arrow_up)
            false
        } else{
            binding.btnEspandCollapse.setImageResource(R.drawable.ic_arrow_down)
            true
        }
    }

    private fun setListener() {
        (binding.spinnerSeasons.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
            binding.lnLayoutEpisodes.removeAllViewsInLayout()
            loadEpisodes(i)
        }
        binding.btnEspandCollapse.setOnClickListener { expandCollapse() }
        btnLikeListener()
    }

    private fun loadEpisodes(numberSeason: Int) {
        val currentSeason = seasons[numberSeason]
        val episodes = currentSeason.episodes.values.stream().collect(Collectors.toList())
        episodes.sortBy { episode -> episode.episode_number }

        val views = createViews(episodes)
        for(view in views)
        binding.lnLayoutEpisodes.addView(view)
    }

    private fun createViews(episodes:List<Episode>):List<View> {
        val views = ArrayList<View>()
        correlationViewEpisode = hashMapOf()

        for( episode in episodes) {
            val view:View = ItemEpisodeLayoutBinding.inflate(layoutInflater).apply {
                tvEpisodeName.text = episode.name
                tvEpisodeOverview.text = episode.overview
                tvAverage.text = episode.vote_average!!.round().toString()
                imvEpisode.loadImage(TMDB_IMAGE_URL.format(episode.stillPath))
            }.root
            correlationViewEpisode[view] = episode
            view.setOnClickListener{
                startActivity(Intent(this,VideoPlayerActivity::class.java).apply {
                    putExtra("CONTENT",correlationViewEpisode[view])
                })
            }

           views.add(view)
        }
        return views
    }

    private fun loadSpinner(){
        if(tvShow.seasons.size > 0) {
            seasons = tvShow.seasons.values.stream().collect(Collectors.toList())
            val seasonsNames = seasons.sortedBy { s -> s.season_number }
                .map { s -> getString(R.string.season_text).format(s.season_number) }
            val adapterTextSeasons = ArrayAdapter(this, R.layout.list_item, seasonsNames)
            (binding.spinnerSeasons.editText as? AutoCompleteTextView)?.apply {
                setAdapter(adapterTextSeasons)
                setText(adapter.getItem(0).toString(), false)
            }
            loadEpisodes(0)
        }else{
            binding.tvSeason.setText(getString(R.string.noSeasons_text),false)
        }
    }

    private fun getData() {
        val data = intent.extras
        tvShow = data!!.getSerializable("TVSHOW") as TvShow
        media = tvShow
        binding.tvNameTvShow.text = tvShow.name
        binding.tvOverview.text = tvShow.overview
        binding.tvDate.text = StringUtil.dateToYear(tvShow.release_date?:"")
        binding.imvPoster.loadImage(TMDB_IMAGE_URL.format(tvShow.poster_path))
        binding.imvBackground.loadImage(TMDB_IMAGE_URL.format(tvShow.backdrop_path))
    }
}