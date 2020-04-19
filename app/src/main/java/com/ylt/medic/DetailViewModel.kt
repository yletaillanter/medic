package com.ylt.medic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ylt.medic.database.MedicDatabase
import com.ylt.medic.database.model.Medicament


/**
 * Created by yoannlt on 08/07/2017.
 */


class DetailViewModel(application:Application) : AndroidViewModel(application) {
    internal fun getByCis(cis: String): Medicament {
        var resultMedic: Medicament =  MedicDatabase.getInstance(getApplication()).medicamentDao().getMedicByCis(cis)
        resultMedic.ASMRs = MedicDatabase.getInstance(getApplication()).asmrDao().getAsmrByCis(cis)
        resultMedic.compos = MedicDatabase.getInstance(getApplication()).compoDao().getCompoByCis(cis)
        resultMedic.conditionPrescritions = MedicDatabase.getInstance(getApplication()).conditionPrescriptionDao().getConditionPrescriptionByCis(cis)
        resultMedic.generiques = MedicDatabase.getInstance(getApplication()).generiqueDao().getGeneriqueByCis(cis)
        resultMedic.infos = MedicDatabase.getInstance(getApplication()).infoImportantDao().getInfoImportantesByCis(cis)
        resultMedic.presentations = MedicDatabase.getInstance(getApplication()).presentationDao().getPresentationByCis(cis)
        resultMedic.SMRs = MedicDatabase.getInstance(getApplication()).smrDao().getSmrByCis(cis)

        return resultMedic
    }

    internal fun getById(id: Long?): Medicament {
        return MedicDatabase.getInstance(getApplication()).medicamentDao().getMedicById(id!!)
    }

    internal fun setBookmarked(id: Long?, state: Boolean?) {
        MedicDatabase.getInstance(getApplication()).medicamentDao().setAsBookmarkedById(id!!, state!!)
    }
}