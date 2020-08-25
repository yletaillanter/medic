package com.ylt.medic.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.ylt.medic.R;
import com.ylt.medic.database.model.Generique;

public class GeneriqueCardView extends CardView {

    private Generique generique;
    private TextView grpGener, libGrpGener, typGener, numTri;

    public GeneriqueCardView(Context context) {
        this(context,null);
    }

    public GeneriqueCardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GeneriqueCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setGenerique(Generique generique) {
        this.generique = generique;
        init();
    }

    /**
     * Initialize view
     */
    private void init() {
        inflate(getContext(), R.layout.cardview_generique, this);

        //Get references to text views
        grpGener = findViewById(R.id.grp_gener);
        libGrpGener = findViewById(R.id.lib_grp_gener);
        typGener = findViewById(R.id.typ_gener);
        numTri = findViewById(R.id.num_tri);

        // Set text to views elements
        grpGener.setText(generique.getIdGrpGener());
        libGrpGener.setText(generique.getLibelleGrpGener());
        typGener.setText(generique.getTypeGener());
        numTri.setText(generique.getNumeroTri());
    }

}
