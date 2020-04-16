package com.ylt.medic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ylt.medic.database.MedicDatabase
import com.ylt.medic.database.model.Medicament

/**
 * Created by yoannlt on 15/07/2017.
 */
class FavoriteViewModel(application:Application): AndroidViewModel(application) {
    internal fun getBookmarked(): List<Medicament> {
        return MedicDatabase.getInstance(getApplication()).medicamentDao().getBookmarked()
    }
}