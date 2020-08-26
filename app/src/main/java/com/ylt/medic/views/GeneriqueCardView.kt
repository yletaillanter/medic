package com.ylt.medic.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.ylt.medic.R
import com.ylt.medic.database.model.Generique

class GeneriqueCardView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(
    context!!, attrs, defStyleAttr
) {
    private var generique: Generique? = null
    private var grpGener: TextView? = null
    private var libGrpGener: TextView? = null
    private var typGener: TextView? = null
    private var numTri: TextView? = null
    fun setGenerique(generique: Generique?) {
        this.generique = generique
        init()
    }

    /**
     * Initialize view
     */
    private fun init() {
        inflate(context, R.layout.cardview_generique, this)

        //Get references to text views
        grpGener = findViewById(R.id.grp_gener)
        libGrpGener = findViewById(R.id.lib_grp_gener)
        typGener = findViewById(R.id.typ_gener)
        numTri = findViewById(R.id.num_tri)

        // Set text to views elements
        grpGener!!.setText(generique!!.idGrpGener)
        libGrpGener!!.setText(generique!!.libelleGrpGener)
        typGener!!.setText(generique!!.typeGener)
        numTri!!.setText(generique!!.numeroTri)
    }
}