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
import timber.log.Timber


class NoticeActivity : AppCompatActivity() {

    var TAG: String = "NoticeActivity"
    lateinit var noticeTextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        Timber.d( "notice")

        noticeTextView = findViewById(R.id.notice)

        val codeCis = intent.getStringExtra(EXTRA_MESSAGE)

        if (checkNetworkConnection()) {
            lifecycleScope.launch {

                val result = httpGet("http://base-donnees-publique.medicaments.gouv.fr/affichageDoc.php?specid=$codeCis&typedoc=N")
                Timber.d("Parse result:" + result.getElementById("contentDocument"))

                try {
                    noticeTextView.setText(HtmlCompat.fromHtml(result.getElementById("contentDocument").toString(), 0));
                } catch(npe: NullPointerException) {
                    if (HtmlCompat.fromHtml(result.getElementById("contentPrincipal").toString(), 0).contains("Le document demandé n'est pas disponible pour ce médicament")) {
                        noticeTextView.setText("Le document demandé n'est pas disponible pour ce médicament")
                        Timber.d( "notice non dispo")
                    }
                }
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
            Timber.i( "Connected!")
            //tvIsConnected.setText("Connected "+networkInfo?.typeName)
            //tvIsConnected.setBackgroundColor(0xFF7CCC26.toInt())
        } else {
            Timber.i( "Not Connected!")
            //tvIsConnected.setText("Not Connected!")
            //tvIsConnected.setBackgroundColor(0xFFFF0000.toInt())
        }
        return isConnected;
    }


    private suspend fun httpGet(myURL: String): Document {
        Timber.d( myURL)

        return withContext(Dispatchers.IO) {
            Jsoup.connect(myURL).get()
        }
    }
}