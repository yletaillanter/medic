package com.ylt.medic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ylt.medic.database.MedicDatabase
import com.ylt.medic.database.model.Medicament


/**
 * Created by yoannlt on 08/07/2017.
 */


class DetailViewModel(application:Application) : AndroidViewModel(application) {
    internal fun getByCis(cis: String?): Medicament {
        return MedicDatabase.getInstance(getApplication()).medicamentDao().loadMedicByCis(cis!!)
    }

    internal fun getById(id: Long?): Medicament {
        return MedicDatabase.getInstance(getApplication()).medicamentDao().loadMedicById(id!!)
    }

    internal fun setBookmarked(id: Long?, state: Boolean?) {
        MedicDatabase.getInstance(getApplication()).medicamentDao().setAsBookmarkedById(id!!, state!!)
    }
}