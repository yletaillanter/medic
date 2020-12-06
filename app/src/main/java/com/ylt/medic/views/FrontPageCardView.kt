package com.ylt.medic.views

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import com.ylt.medic.R
import com.ylt.medic.database.model.Medicament
import com.ylt.medic.ui.detailed.DetailedFragmentDirections

class FrontPageCardView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(
    context!!, attrs, defStyleAttr
) {
    private var medicament: Medicament? = null
    private var denomination: TextView? = null
    private var formePharma: TextView? = null
    private var voieAdmin: TextView? = null
    private var price: TextView? = null
    private var rembRate: TextView? = null
    private lateinit var buttonNotice: Button

    fun setMedicament(medicament: Medicament?) {
        this.medicament = medicament
        init()
    }

    /**
     * Initialize view
     */
    private fun init() {
        inflate(context, R.layout.cardview_frontpage, this)

        //Get references to text views
        denomination = findViewById(R.id.denomination)
        formePharma = findViewById(R.id.forme_pharma)
        voieAdmin = findViewById(R.id.voie_admin)
        price = findViewById(R.id.price)
        rembRate = findViewById(R.id.remb_rate)
        buttonNotice = findViewById(R.id.button_notice)

        // Set text to views elements
        denomination!!.text = medicament?.denomination
        formePharma!!.text = medicament?.formePharma
        voieAdmin!!.text = medicament?.voieAdministration
        if (medicament!!.presentations.size > 0 ) {
            price!!.text = "${medicament!!.presentations[0].prixMedicEuro}€"
            rembRate!!.text = medicament!!.presentations[0].txRemboursement
        }
        buttonNotice.setOnClickListener {
            it?.findNavController()?.navigate(DetailedFragmentDirections.actionNavigationDetailedToNoticeFragment(medicament!!.codeCis, medicament!!.denomination.split(",")[0]))
        }
    }
}