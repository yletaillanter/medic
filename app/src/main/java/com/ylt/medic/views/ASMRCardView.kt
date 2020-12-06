package com.ylt.medic.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.ylt.medic.R
import com.ylt.medic.database.model.ASMR

class ASMRCardView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(
    context!!, attrs, defStyleAttr
) {
    private var asmr: ASMR? = null
    private var libelleAsmr: TextView? = null
    private var valeurAsmr: TextView? = null
    private var motifEvalAsmr: TextView? = null
    private var dateAvisAsmr: TextView? = null
    private var codeDossierHas: TextView? = null
    fun setASMR(asmr: ASMR?) {
        this.asmr = asmr
        init()
    }

    /**
     * Initialize view
     */
    private fun init() {
        inflate(context, R.layout.cardview_asmr, this)

        //Get references to text views
        libelleAsmr = findViewById(R.id.libelle_asmr)
        valeurAsmr = findViewById(R.id.valeur_asmr)
        motifEvalAsmr = findViewById(R.id.motif_eval_asmr)
        dateAvisAsmr = findViewById(R.id.date_avis_asmr)
        codeDossierHas = findViewById(R.id.code_dossier_has)

        // Set text to views elements
        libelleAsmr!!.text = asmr?.libelleAsmr
        valeurAsmr!!.text = asmr?.valeurAsmr
        motifEvalAsmr!!.text = asmr?.motifEval
        dateAvisAsmr!!.text = asmr?.dateAvisCommTransp
        codeDossierHas!!.text = asmr?.codeDossierHas
    }
}