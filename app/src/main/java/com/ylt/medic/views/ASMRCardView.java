package com.ylt.medic.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.ylt.medic.R;
import com.ylt.medic.database.model.ASMR;

public class ASMRCardView extends CardView {

    private ASMR asmr;
    private TextView libelleAsmr, valeurAsmr, motifEvalAsmr, dateAvisAsmr, codeDossierHas;

    public ASMRCardView(Context context) {
        this(context,null);
    }

    public ASMRCardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ASMRCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setASMR(ASMR asmr) {
        this.asmr = asmr;
        init();
    }

    /**
     * Initialize view
     */
    private void init() {
        inflate(getContext(), R.layout.cardview_asmr, this);

        //Get references to text views
        libelleAsmr = findViewById(R.id.libelle_asmr);
        valeurAsmr = findViewById(R.id.valeur_asmr);
        motifEvalAsmr = findViewById(R.id.motif_eval_asmr);
        dateAvisAsmr = findViewById(R.id.date_avis_asmr);
        codeDossierHas = findViewById(R.id.code_dossier_has);

        // Set text to views elements
        libelleAsmr.setText(asmr.getLibelleAsmr());
        valeurAsmr.setText(asmr.getValeurAsmr());
        motifEvalAsmr.setText(asmr.getMotifEval());
        dateAvisAsmr.setText(asmr.getDateAvisCommTransp());
        codeDossierHas.setText(asmr.getCodeDossierHas());
    }
}
