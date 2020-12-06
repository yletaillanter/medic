package com.ylt.medic.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.ylt.medic.R
import com.ylt.medic.database.model.SMR

class SMRCardView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(
    context!!, attrs, defStyleAttr
) {
    private var smr: SMR? = null
    private var libelleSmr: TextView? = null
    private var valeurSmr: TextView? = null
    private var motifEvalSmr: TextView? = null
    private var dateAvisSmr: TextView? = null
    private var codeDossierHas: TextView? = null
    fun setSMR(smr: SMR?) {
        this.smr = smr
        init()
    }

    /**
     * Initialize view
     */
    private fun init() {
        inflate(context, R.layout.cardview_smr, this)

        //Get references to text views
        libelleSmr = findViewById(R.id.libelle_smr)
        valeurSmr = findViewById(R.id.valeur_smr)
        motifEvalSmr = findViewById(R.id.motif_eval_smr)
        dateAvisSmr = findViewById(R.id.date_avis_smr)
        codeDossierHas = findViewById(R.id.code_dossier_has)

        // Set text to views elements
        libelleSmr!!.text = smr?.libelleSmr
        valeurSmr!!.text = smr?.valeurSmr
        motifEvalSmr!!.text = smr?.motifEval
        dateAvisSmr!!.text = smr?.dateAvisCommTransp
        codeDossierHas!!.text = smr?.codeDossierHas
    }
}