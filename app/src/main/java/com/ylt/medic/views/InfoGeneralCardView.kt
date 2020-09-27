package com.ylt.medic.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.ylt.medic.R
import com.ylt.medic.database.model.Medicament

class InfoGeneralCardView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(
    context!!, attrs, defStyleAttr
) {
    private var medicament: Medicament? = null
    private var status: TextView? = null
    private var etat: TextView? = null
    private var dateAmm: TextView? = null
    private var titulaire: TextView? = null
    private var survRenforcee: TextView? = null

    fun setMedicament(medicament: Medicament?) {
        this.medicament = medicament
        init()
    }

    /**
     * Initialize view
     */
    private fun init() {
        inflate(context, R.layout.cardview_informations_generales, this)

        //Get references to text views
        status = findViewById(R.id.status)
        etat = findViewById(R.id.etat)
        dateAmm = findViewById(R.id.date_amm)
        titulaire = findViewById(R.id.titulaire)
        survRenforcee = findViewById(R.id.surv_renforcee)

        // Set text to views elements
        status!!.setText(medicament?.statusBdm)
        etat!!.setText(medicament?.etatCommer)
        dateAmm!!.setText(medicament?.dateAmm)
        titulaire!!.setText(medicament?.titulaire)
        survRenforcee!!.setText(medicament?.survRenforcee)
    }
}