package com.ylt.medic.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.ylt.medic.R
import com.ylt.medic.database.model.Compo

class ComposantCardView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(
    context!!, attrs, defStyleAttr
) {
    private var composant: Compo? = null
    private var designationElem: TextView? = null
    private var codeSubstance: TextView? = null
    private var denoSubstance: TextView? = null
    private var dosageSubstance: TextView? = null
    private var refSubstance: TextView? = null
    private var natureComposant: TextView? = null
    fun setComposant(composant: Compo?) {
        this.composant = composant
        init()
    }

    /**
     * Initialize view
     */
    private fun init() {
        inflate(context, R.layout.cardview_composant, this)

        //Get references to text views
        designationElem = findViewById(R.id.designation_elem)
        codeSubstance = findViewById(R.id.code_substance)
        denoSubstance = findViewById(R.id.deno_substance)
        dosageSubstance = findViewById(R.id.dosage_substance)
        refSubstance = findViewById(R.id.ref_substance)
        natureComposant = findViewById(R.id.nature_composant)

        // Set text to views elements
        designationElem!!.setText(composant?.designationElemPh)
        codeSubstance!!.setText(composant?.codeSubstance)
        denoSubstance!!.setText(composant?.denoSubstance)
        dosageSubstance!!.setText(composant?.dosageSubstance)
        refSubstance!!.setText(composant?.refSubstance)
        natureComposant!!.setText(composant?.natureComposant)
    }
}