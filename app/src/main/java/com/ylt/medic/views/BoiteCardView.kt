package com.ylt.medic.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.ylt.medic.R
import com.ylt.medic.database.model.Presentation

class BoiteCardView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(
    context!!, attrs, defStyleAttr
) {
    private var presentation: Presentation? = null
    private var libelle: TextView? = null
    private var statut: TextView? = null
    private var dateDeclaCommer: TextView? = null
    private var rembRate: TextView? = null
    private var price: TextView? = null
    private var indicDroitRemb: TextView? = null


    fun setBoite(presentation: Presentation?) {
        this.presentation = presentation
        init()
    }

    /**
     * Initialize view
     */
    private fun init() {
        inflate(context, R.layout.cardview_boite, this)

        //Get references to text views
        libelle = findViewById(R.id.libelle)
        statut = findViewById(R.id.statut)
        dateDeclaCommer = findViewById(R.id.date_decla_commer)
        rembRate = findViewById(R.id.remb_rate)
        price = findViewById(R.id.price)
        indicDroitRemb = findViewById(R.id.indic_droit_remb)

        // Set text to views elements
        libelle!!.text = presentation?.libellePresentation
        statut!!.text = presentation?.statutAdminPres
        dateDeclaCommer!!.text = presentation?.dateDeclaCommer

        if (!presentation?.txRemboursement.isNullOrEmpty())
            rembRate!!.text = presentation?.txRemboursement
        else
            rembRate!!.visibility = View.GONE
        if (!presentation?.prixMedicEuro.isNullOrEmpty())
            price!!.text = "${presentation?.prixMedicEuro}€"
        else
            price!!.visibility = View.GONE


        //rembRate!!.text = presentation?.txRemboursement
        //price!!.text = "${presentation?.prixMedicEuro}€"


        indicDroitRemb!!.text = presentation?.indicDroitRemb
    }
}