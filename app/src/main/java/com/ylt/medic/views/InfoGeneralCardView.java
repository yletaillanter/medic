package com.ylt.medic.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.ylt.medic.R;
import com.ylt.medic.database.model.Medicament;

public class InfoGeneralCardView extends CardView {

    private Medicament medicament;
    private TextView status, etat, dateAmm, titulaire, survRenforcee;

    public InfoGeneralCardView(Context context) {
        this(context,null);
    }

    public InfoGeneralCardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InfoGeneralCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
        init();
    }

    /**
     * Initialize view
     */
    private void init() {
        inflate(getContext(), R.layout.cardview_informations_generales, this);

        //Get references to text views
        status = findViewById(R.id.status);
        etat = findViewById(R.id.etat);
        dateAmm = findViewById(R.id.date_amm);
        titulaire = findViewById(R.id.titulaire);
        survRenforcee = findViewById(R.id.surv_renforcee);

        // Set text to views elements
        status.setText(medicament.getStatusBdm());
        etat.setText(medicament.getEtatCommer());
        dateAmm.setText(medicament.getDateAmm());
        titulaire.setText(medicament.getTitulaire());
        survRenforcee.setText(medicament.getSurvRenforcee());
    }

}
