package com.ylt.medic.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.ylt.medic.R
import com.ylt.medic.database.model.InfoImportantes
import com.ylt.medic.database.model.Medicament

class InfoImportantesCardView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(
    context!!, attrs, defStyleAttr
) {

    private var infoImportantes: InfoImportantes? = null
    private var dateDebInfo: TextView? = null
    private var dateFinInfo: TextView? = null
    private var info: TextView? = null

    fun setInfo(infoImportantes: InfoImportantes?) {
        this.infoImportantes = infoImportantes
        init()
    }

    /**
     * Initialize view
     */
    private fun init() {
        inflate(context, R.layout.cardview_informations_importantes, this)

        //Get references to text views
        dateDebInfo = findViewById(R.id.date_deb_info)
        dateFinInfo = findViewById(R.id.date_fin_info)
        info = findViewById(R.id.info)

        // Set text to views elements
        dateDebInfo!!.setText(infoImportantes?.dateDeb)
        dateFinInfo!!.setText(infoImportantes?.dateFin)
        info!!.setText(infoImportantes?.textAndLink)
    }


}