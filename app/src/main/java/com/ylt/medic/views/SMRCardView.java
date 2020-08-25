package com.ylt.medic.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.ylt.medic.R;
import com.ylt.medic.database.model.SMR;

public class SMRCardView extends CardView {

    private SMR smr;
    private TextView libelleSmr, valeurSmr, motifEvalSmr, dateAvisSmr, codeDossierHas;

    public SMRCardView(Context context) {
        this(context,null);
    }

    public SMRCardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SMRCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSMR(SMR smr) {
        this.smr = smr;
        init();
    }

    /**
     * Initialize view
     */
    private void init() {
        inflate(getContext(), R.layout.cardview_smr, this);

        //Get references to text views
        libelleSmr = findViewById(R.id.libelle_smr);
        valeurSmr = findViewById(R.id.valeur_smr);
        motifEvalSmr = findViewById(R.id.motif_eval_smr);
        dateAvisSmr = findViewById(R.id.date_avis_smr);
        codeDossierHas = findViewById(R.id.code_dossier_has);

        // Set text to views elements
        libelleSmr.setText(smr.getLibelleSmr());
        valeurSmr.setText(smr.getValeurSmr());
        motifEvalSmr.setText(smr.getMotifEval());
        dateAvisSmr.setText(smr.getDateAvisCommTransp());
        codeDossierHas.setText(smr.getCodeDossierHas());
    }
}
