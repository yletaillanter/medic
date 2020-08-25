package com.ylt.medic.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.ylt.medic.R;
import com.ylt.medic.database.model.Compo;

public class ComposantCardView extends CardView {

    private Compo composant;
    private TextView designationElem, codeSubstance, denoSubstance;
    private TextView dosageSubstance, refSubstance, natureComposant;

    public ComposantCardView(Context context) {
        this(context,null);
    }

    public ComposantCardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ComposantCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setComposant(Compo composant) {
        this.composant = composant;
        init();
    }

    /**
     * Initialize view
     */
    private void init() {
        inflate(getContext(), R.layout.cardview_composant, this);

        //Get references to text views
        designationElem = findViewById(R.id.designation_elem);
        codeSubstance = findViewById(R.id.code_substance);
        denoSubstance = findViewById(R.id.deno_substance);
        dosageSubstance = findViewById(R.id.dosage_substance);
        refSubstance = findViewById(R.id.ref_substance);
        natureComposant = findViewById(R.id.nature_composant);

        // Set text to views elements
        designationElem.setText(composant.getDesignationElemPh());
        codeSubstance.setText(composant.getCodeSubstance());
        denoSubstance.setText(composant.getDenoSubstance());
        dosageSubstance.setText(composant.getDosageSubstance());
        refSubstance.setText(composant.getRefSubstance());
        natureComposant.setText(composant.getNatureComposant());
    }

}
