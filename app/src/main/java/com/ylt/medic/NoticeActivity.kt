package com.ylt.medic

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class NoticeActivity : AppCompatActivity() {

    var TAG: String = "NoticeActivity"
    lateinit var noticeTextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        Log.d(TAG, "notice")

        noticeTextView = findViewById(R.id.notice)

        //val codeCis = intent.getStringExtra(EXTRA_MESSAGE)
        val codeCis = "60474889"

        if (checkNetworkConnection()) {
            lifecycleScope.launch {

                val result = httpGet("http://base-donnees-publique.medicaments.gouv.fr/affichageDoc.php?specid=$codeCis&typedoc=N")
                //tvResult.setText(result)
                Log.i(TAG,"Parse result:" + result!!.getElementById("contentDocument"))
                noticeTextView.setText(HtmlCompat.fromHtml(result!!.getElementById("contentDocument").toString(), 0));
            }
        } else {
            Log.i("TAG", "no connection")
        }
    }

    private fun checkNetworkConnection(): Boolean {
        val cm: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = if(networkInfo != null) networkInfo.isConnected() else false

        if (isConnected) {
            Log.i(TAG, "Connected!")
            //tvIsConnected.setText("Connected "+networkInfo?.typeName)
            //tvIsConnected.setBackgroundColor(0xFF7CCC26.toInt())
        } else {
            Log.i(TAG, "Not Connected!")
            //tvIsConnected.setText("Not Connected!")
            //tvIsConnected.setBackgroundColor(0xFFFF0000.toInt())
        }
        return isConnected;
    }


    private suspend fun httpGet(myURL: String): Document {
        Log.d(TAG, myURL)

        return withContext(Dispatchers.IO) {
            Jsoup.connect(myURL).get()
        }
    }
}