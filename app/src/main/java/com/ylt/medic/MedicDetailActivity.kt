package com.ylt.medic

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.Button
import android.widget.TextView
import com.ylt.medic.database.MedicDatabase
import com.ylt.medic.database.model.Medicament
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MedicDetailActivity : AppCompatActivity() {

    lateinit var db: MedicDatabase

    lateinit var currentMedicament:Medicament

    lateinit var denominationTextView:TextView
    private lateinit var formePharmaTextView:TextView
    private lateinit var voieAdministrationTextView:TextView
    lateinit var statusTextView:TextView
    private lateinit var etatTextView:TextView
    lateinit var dateAuthorTextView:TextView
    private lateinit var titulaireTextView:TextView

    private lateinit var survRenforceeTextView:TextView
    private lateinit var elemPharmaTextView:TextView
    lateinit var denominationSubstanceTextView:TextView
    lateinit var codeSubstanceTextView:TextView
    lateinit var dosageSubstanceTextView:TextView
    lateinit var refSubstanceTextView:TextView
    private lateinit var natureComposantTextView:TextView

    lateinit var noticeButton: Button
    lateinit var fab: FloatingActionButton
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medic_detail)

        val model = ViewModelProvider.AndroidViewModelFactory(this.application).create(DetailViewModel::class.java)

        // View binding
        toolbar = findViewById(R.id.toolbar_detail)
        setSupportActionBar(toolbar)

        denominationTextView = findViewById(R.id.denomination)
        formePharmaTextView = findViewById(R.id.forme_pharma)
        voieAdministrationTextView = findViewById(R.id.voie_admin)
        statusTextView = findViewById(R.id.status)
        etatTextView = findViewById(R.id.etat)
        dateAuthorTextView = findViewById(R.id.date_amm)
        titulaireTextView = findViewById(R.id.titulaire)
        survRenforceeTextView = findViewById(R.id.surv_renforcee)
        elemPharmaTextView = findViewById(R.id.designation_elem)
        denominationSubstanceTextView = findViewById(R.id.deno_substance)
        codeSubstanceTextView = findViewById(R.id.code_substance)
        dosageSubstanceTextView = findViewById(R.id.dosage_substance)
        refSubstanceTextView = findViewById(R.id.ref_substance)
        natureComposantTextView = findViewById(R.id.nature_composant)
        noticeButton = findViewById(R.id.button_notice)

        //Get the medic
        val id:Long = getIntent().getLongExtra("id", 0)
        currentMedicament = model.getById(id)

        //Set view elements
        denominationTextView.text = currentMedicament.denomination
        formePharmaTextView.text = currentMedicament.formePharma
        voieAdministrationTextView.text = currentMedicament.voieAdministration
        statusTextView.text = currentMedicament.statutAdminAmm
        etatTextView.text = currentMedicament.etatCommer
        dateAuthorTextView.text = ""+currentMedicament.dateAmm
        titulaireTextView.text = currentMedicament.titulaire
        survRenforceeTextView.text = currentMedicament.survRenforcee

        noticeButton.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, currentMedicament.codeCis)
            }
            startActivity(intent)
        }

        // TODO modif par compo
        /*
        elemPharmaTextView.text = currentMedicament!!.designation_elem_ph
        denominationSubstanceTextView.text = currentMedicament.deno_substance
        codeSubstanceTextView.text = currentMedicament.code_substance
        dosageSubstanceTextView.text = currentMedicament.dosage_substance
        refSubstanceTextView.text = currentMedicament.ref_substance
        natureComposantTextView.text = currentMedicament.nature_composant
        */

        // Fab bouton

        /*
        fab = findViewById(R.id.favorite) as FloatingActionButton
        if(currentMedicament.isBookmarked)
            fab.setImageResource(R.drawable.star_yellow)

        fab.setOnClickListener(View.OnClickListener {
            if(!currentMedicament.isBookmarked) {
                model.setBookmarked(currentMedicament.id, true)
                fab.setImageResource(R.drawable.star_yellow)
            } else {
                model.setBookmarked(currentMedicament.id, false)
                fab.setImageResource(R.drawable.star)
            }
        })
        */
    }

}
