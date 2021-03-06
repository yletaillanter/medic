package com.ylt.medic

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.ylt.medic.database.MedicDatabase
import com.ylt.medic.database.model.Medicament


/**
 * Created by yoannlt on 08/07/2017.
 */
class DetailViewModel(application:Application) : AndroidViewModel(application) {
    internal fun getByCis(id: Long): Medicament {
        Log.d("ViewModel", "medic id: $id")

        var resultMedic: Medicament =  MedicDatabase.getInstance(getApplication()).medicamentDao().getMedicById(id)

        resultMedic.ASMRs = MedicDatabase.getInstance(getApplication()).asmrDao().getAsmrByCis(resultMedic.codeCis)
        resultMedic.compos = MedicDatabase.getInstance(getApplication()).compoDao().getCompoByCis(resultMedic.codeCis)
        resultMedic.conditionPrescritions = MedicDatabase.getInstance(getApplication()).conditionPrescriptionDao().getConditionPrescriptionByCis(resultMedic.codeCis)
        resultMedic.generiques = MedicDatabase.getInstance(getApplication()).generiqueDao().getGeneriqueByCis(resultMedic.codeCis)
        resultMedic.infos = MedicDatabase.getInstance(getApplication()).infoImportantDao().getInfoImportantesByCis(resultMedic.codeCis)
        resultMedic.presentations = MedicDatabase.getInstance(getApplication()).presentationDao().getPresentationByCis(resultMedic.codeCis)
        resultMedic.SMRs = MedicDatabase.getInstance(getApplication()).smrDao().getSmrByCis(resultMedic.codeCis)

        return resultMedic
    }

    internal fun getById(id: Long?): Medicament {
        return MedicDatabase.getInstance(getApplication()).medicamentDao().getMedicById(id!!)
    }

    internal fun setBookmarked(id: Long?, state: Boolean?) {
        MedicDatabase.getInstance(getApplication()).medicamentDao().setAsBookmarkedById(id!!, state!!)
    }
}