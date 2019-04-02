package m.vk.k012_handlerthread

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var thread : Thread
    lateinit var handler : Handler//มีหน้าที่ส่งข้อมูลไปยัง Looper
    var isLooper : Boolean = true
    var countRed : Int = 30
    var countOrange: Int = 5
    var countGreen : Int = 30
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = object : Handler(Looper.getMainLooper()){//ผูก Handler เข้ากับ Looper ของ ManiThread ไว้
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                //Run in Main Thread
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
            }
        }


        thread = object : Thread(){
            override fun run() {
                //Run in background
                while (isLooper){
                    try {
                        Thread.sleep(1000)
                    }catch (e : InterruptedException){
                        return
                    }
                    var msg = Message()
                    handler.sendMessage(msg)//ส่งข้อมูลไปเข้าคิวรอทำงาน(อัพเดท UI)ใน Main Looper
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
