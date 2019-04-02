package m.vk.k011_normalthread

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var thread : Thread
    var isLooper : Boolean = true
    var countRed : Int = 30
    var countOrange: Int = 5
    var countGreen : Int = 30
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread = object : Thread(){
            override fun run() {
                while (isLooper){
                    try {
                        Thread.sleep(1000)
                    }catch (e : InterruptedException){
                        return
                    }
                    runOnUiThread(Runnable {
                        when {
                            countGreen >= 0 -> {
                                tvTimeCount.text = String.format("%02d",countGreen--)
                                imgRed.setImageResource(R.color.color_gray)
                                imgOrange.setImageResource(R.color.color_gray)
                                imgGreen.setImageResource(R.color.color_green)
                            }
                            countOrange >= 0 -> {
                                tvTimeCount.text = String.format("%02d",countOrange--)
                                imgRed.setImageResource(R.color.color_gray)
                                imgOrange.setImageResource(R.color.color_orange)
                                imgGreen.setImageResource(R.color.color_gray)
                            }
                            countRed >= 0 -> {
                                tvTimeCount.text = String.format("%02d",countRed--)
                                imgRed.setImageResource(R.color.color_rad)
                                imgOrange.setImageResource(R.color.color_gray)
                                imgGreen.setImageResource(R.color.color_gray)
                            }
                            else -> {
                                countRed = 30
                                countOrange = 5
                                countGreen  = 30
                            }
                        }
                    })
                }

            }
        }
        thread.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        thread.interrupt()
    }
}
