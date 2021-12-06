package com.ylt.medic.ui.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
    val PAS_DE_NOTICE_1 = "Le médicament demandé n'entre pas dans le périmètre de la base de données publique sur le médicament"
    val PAS_DE_NOTICE_2 = "Le document demandé n'est pas disponible pour ce médicament"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_notice, container, false)
        Timber.d("notice")

        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.title = "Notice"

        noticeTextView = root.findViewById(R.id.notice)

        val args: NoticeFragmentArgs by navArgs()
        Timber.d("Code cis: ${args.cis}")

        lifecycleScope.launch {
            val result = httpGet("http://base-donnees-publique.medicaments.gouv.fr/affichageDoc.php?specid=${args.cis}&typedoc=N")

            try {
                val elements = result.getElementsByClass("textedeno")
                Timber.i("textedeno: ${elements[0].text().split(",")[0]}")
                val content: Element = result.getElementById("textDocument")

                //content.children().forEach{ Timber.i(it.text()) }

                noticeTextView.text = (HtmlCompat.fromHtml(
                    result.getElementById("textDocument").toString(), 0
                ))
            } catch (e: Exception) {
                val contentPrincipal = HtmlCompat.fromHtml(result.getElementById("contentPrincipal").toString(), 0)

                if (contentPrincipal.contains(PAS_DE_NOTICE_1) || contentPrincipal.contains(PAS_DE_NOTICE_2)) {
                    noticeTextView.text = "Pas de notice trouvé pour ce médicament"
                    Timber.e("notice non dispo")
                }
            }
        }

        return root
    }

    private suspend fun httpGet(myURL: String): Document {
        Timber.i("ylt url: $myURL")

        return withContext(Dispatchers.IO) {
            Jsoup.connect(myURL).get()
        }
    }
}