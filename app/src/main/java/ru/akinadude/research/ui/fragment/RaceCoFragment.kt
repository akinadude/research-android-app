package ru.akinadude.research.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_race.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.akinadude.research.R
import java.util.*
import kotlin.coroutines.CoroutineContext

/*Пример с тремя корутинами, работающими на main треде и не блокирующими его.*/
class RaceCoFragment : Fragment(), CoroutineScope {

    companion object {

        fun newInstance(): RaceCoFragment {
            return RaceCoFragment()
        }
    }

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private var raceEnd = false
    private var greenJob: Job? = null
    private var redJob: Job? = null
    private var blueJob: Job? = null
    private val delayTimeMillis = 10L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
        resetRun()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_race, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //start_btn.setOnClickListener { startUpdate() }
        start_btn.setOnClickListener { asyncBlockingThread() }
        red_progress_bar.progress = 0f
        green_progress_bar.progress = 0f
        blue_progress_bar.progress = 0f
    }

    //+++++++++++++++++++++++++++++++++++++++++
    //todo перенести в другой фрагмент
    //todo прикрутить простенький UI, как в DownloadBlockingActivity

    //crash with NetworkOnMainThreadException
    private fun asyncBlockingThread() {
        launch {
            val deferred = async/*(Dispatchers.IO)*/ {
                downloadData()
            }
            val data = deferred.await()
            Logger.d("Data: $data")
        }
    }

    private fun downloadData(): String {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .build()

        val response = client.newCall(request).execute()
        return response.body()?.string() ?: ""
    }
    //+++++++++++++++++++++++++++++++++++++++++

    private fun startUpdate() {
        resetRun()

        greenJob = launch {
            startRunning(green_progress_bar)
        }

        redJob = launch {
            startRunning(red_progress_bar)
        }

        blueJob = launch {
            startRunning(blue_progress_bar)
        }
    }

    private suspend fun startRunning(progressBar: RoundCornerProgressBar) {
        progressBar.progress = 0f
        while (progressBar.progress < 1000 && !raceEnd) {
            delay(delayTimeMillis)
            progressBar.progress += (1..30).random()
        }
        if (!raceEnd) {
            raceEnd = true
            Toast.makeText(this.context, "${progressBar.tag} won!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start

    private fun resetRun() {
        raceEnd = false
        greenJob?.cancel()
        blueJob?.cancel()
        redJob?.cancel()
    }
}