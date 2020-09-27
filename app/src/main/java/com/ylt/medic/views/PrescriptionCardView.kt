package com.ylt.medic.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.ylt.medic.R
import com.ylt.medic.database.model.ConditionPrescription

class PrescriptionCardView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context!!, attrs, defStyleAttr) {
    private var conditionPrescritextView: TextView? = null
    private var conditionPrescri: ConditionPrescription? = null

    fun setConditionPrescription(prescri: ConditionPrescription) {
        this.conditionPrescri = prescri
        init()
    }

    /**
     * Initialize view
     */
    private fun init() {
        inflate(context, R.layout.cardview_prescription, this)

        //Get references to text views
        conditionPrescritextView = findViewById(R.id.condition_prescri)

        // Set text to views elements
        conditionPrescritextView!!.setText(conditionPrescri?.condition)
    }
}