package test.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var isRunning = true
    private var timerTask: Timer? = null
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            if (isRunning) {
                start()
                isRunning = false
                resetFab.setImageResource(R.drawable.ic_border_color_black_24dp)
            } else {
                pause()
                isRunning = true
                resetFab.setImageResource(R.drawable.ic_refresh_black_24dp)
            }
        }
        resetFab.setOnClickListener {
            resetLap()
        }
    }

    private fun pause() {
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel()
    }

    private fun start() {
        fab.setImageResource(R.drawable.ic_pause_black_24dp)

        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun resetLap(){
        if (isRunning) {
            timerTask?.cancel()

            time = 0
            isRunning = true
            secTextView.text = "0"
            milliTextView.text = "00"

            lapLayout.removeAllViews()
            lap = 1
        } else {
            val lapTime = this.time
            val textView = TextView(this)
            textView.text = "$lap LAP : ${lapTime/100}.${lapTime % 100}"

            lapLayout.addView(textView, 0)
            lap++
        }

    }
}
