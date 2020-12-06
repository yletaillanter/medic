package com.ylt.medic.ui.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ylt.medic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import timber.log.Timber

class NoticeFragment : Fragment() {

    companion object {
        fun newInstance() = NoticeFragment()
    }

    lateinit var noticeTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root =  inflater.inflate(R.layout.fragment_notice, container, false)
        Timber.d( "notice")

        noticeTextView = root.findViewById(R.id.notice)

        val args: NoticeFragmentArgs by navArgs()

        lifecycleScope.launch {
            val result = httpGet("http://base-donnees-publique.medicaments.gouv.fr/affichageDoc.php?specid=${args.cis}&typedoc=N")

            try {
                val elements = result.getElementsByClass("textedeno")
                Timber.i("textedeno: ${elements[0].text().split(",")[0]}")
                val content: Element = result.getElementById("textDocument")

                content.children().forEach{ child ->
                    Timber.i(child.text())
                }


                noticeTextView.setText(HtmlCompat.fromHtml(result.getElementById("textDocument").toString(), 0));
            } catch(e: Exception) {
                if (HtmlCompat.fromHtml(result.getElementById("contentPrincipal").toString(), 0).contains("Le document demandé n'est pas disponible pour ce médicament")) {
                    noticeTextView.setText("Le document demandé n'est pas disponible pour ce médicament")
                    Timber.d( "notice non dispo")
                }
            }
        }

        return root
    }

    private suspend fun httpGet(myURL: String): Document {
        Timber.d( myURL)

        return withContext(Dispatchers.IO) {
            Jsoup.connect(myURL).get()
        }
    }
}