package com.ylt.medic.views

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.ylt.medic.NoticeActivity
import com.ylt.medic.R
import com.ylt.medic.database.model.Medicament

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
        denomination!!.setText(medicament?.denomination)
        formePharma!!.setText(medicament?.formePharma)
        voieAdmin!!.setText(medicament?.voieAdministration)
        if (medicament!!.presentations.size > 0 ) {
            price!!.setText(medicament!!.presentations[0].prixMedicEuro)
            rembRate!!.setText(medicament!!.presentations[0].txRemboursement)
        }
        buttonNotice.setOnClickListener {
            val intent = Intent(context, NoticeActivity::class.java).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, medicament!!.codeCis)
            }
            context.startActivity(intent)
        }
    }
}