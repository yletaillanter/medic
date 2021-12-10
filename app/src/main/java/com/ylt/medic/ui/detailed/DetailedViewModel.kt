package com.ylt.medic.ui.detailed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ylt.medic.database.MedicDatabase
import com.ylt.medic.database.model.Medicament
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import timber.log.Timber
import kotlin.coroutines.CoroutineContext


/**
 * Created by yoannlt on 06/12/2020.
 */
class DetailedViewModel(application:Application) : AndroidViewModel(application) {

    private val _notice = MutableLiveData<String>()
    val notice: LiveData<String>
        get() = _notice

    private val _medic = MutableLiveData<Medicament>()
    val medic: LiveData<Medicament>
        get() = _medic

    fun getLiveDataMedic(cis: Long) : LiveData<Medicament> {
        _medic.value = getByCis(cis)
        return _medic
    }

    fun getLiveDataNotice(cis: String) {
        loadNotice(cis)
    }

    private fun getByCis(id: Long): Medicament {
        Timber.d("medic id: $id")

        val resultMedic: Medicament =  MedicDatabase.getInstance(getApplication()).medicamentDao().getMedicById(id)

        resultMedic.ASMRs = MedicDatabase.getInstance(getApplication()).asmrDao().getAsmrByCis(resultMedic.codeCis)
        resultMedic.compos = MedicDatabase.getInstance(getApplication()).compoDao().getCompoByCis(resultMedic.codeCis)
        resultMedic.conditionPrescritions = MedicDatabase.getInstance(getApplication()).conditionPrescriptionDao().getConditionPrescriptionByCis(resultMedic.codeCis)
        resultMedic.generiques = MedicDatabase.getInstance(getApplication()).generiqueDao().getGeneriqueByCis(resultMedic.codeCis)
        resultMedic.infos = MedicDatabase.getInstance(getApplication()).infoImportantDao().getInfoImportantesByCis(resultMedic.codeCis)
        resultMedic.presentations = MedicDatabase.getInstance(getApplication()).presentationDao().getPresentationByCis(resultMedic.codeCis)
        resultMedic.SMRs = MedicDatabase.getInstance(getApplication()).smrDao().getSmrByCis(resultMedic.codeCis)

        return resultMedic
    }

    internal fun getById(id: Long): Medicament {
        return MedicDatabase.getInstance(getApplication()).medicamentDao().getMedicById(id)
    }

    internal fun setBookmarked(id: Long, bookmarked: Boolean) {
        MedicDatabase.getInstance(getApplication()).medicamentDao().setAsBookmarkedById(id, bookmarked)
    }

    private fun loadNotice(cis: String) {
        viewModelScope.launch {
            val result = makeHttpRequest("http://base-donnees-publique.medicaments.gouv.fr/affichageDoc.php?specid=${cis}&typedoc=N")

            if (result.getElementsByClass("AmmCorpsTexte").size > 9) {
                for (index in 8..9)
                    _notice.value += result.getElementsByClass("AmmCorpsTexte")[index].text() + "\n\n"

                // TODO remove properly
                if(_notice.value?.substring(0,4) == "null") {
                    _notice.value = _notice.value?.substring(4,_notice.value?.length!!)
                }

            } else {
                _notice.value = "Aucunes données"
            }

            //var result2: ArrayList<Element> = ArrayList<Element>()

            // <h2><a name="Ann3bQuestceque">
            //<a name="Ann3bQuestceque">
/*
            result.allElements.take(110).forEachIndexed { index, element ->
                var capture = false
                if (element.toString().length > 31) {
                    if(element.toString().subSequence(0,30) == "<h2><a name=\"Ann3bQuestceque\">") {
                        capture = true
                    }
                }

                if (capture) {
                    if(element.toString().subSequence(0,4) == "<h2>") {
                        coroutineContext.cancel()
                    } else {
                        result2.add(element)
                        _notice.value = result2.toString()
                    }
                }
            }
 */

            //} else if (index>capture && index<capture+2){
            //}

            /*else if (it.toString().contains("<a name=\"Ann3bInfoNecessaires\">")) {
                capture = false
            }

            if (capture)
                _notice.value += "TEST" // it
             */
            //Timber.i("ylt test $capture")
        }
    }

    private suspend fun makeHttpRequest(url: String)
        = withContext(Dispatchers.IO) {
                Jsoup.connect(url).get()
            }
}