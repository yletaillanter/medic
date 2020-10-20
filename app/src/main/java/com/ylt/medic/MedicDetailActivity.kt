package com.ylt.medic

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import com.ylt.medic.database.model.Medicament
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ylt.medic.databinding.ActivityMedicDetailBinding
import timber.log.Timber


class MedicDetailActivity : AppCompatActivity() {

    private val TAG = "MedicDetailActivity"
    private lateinit var binding: ActivityMedicDetailBinding
    lateinit var currentMedicament:Medicament
    lateinit var toolbar: Toolbar
    //lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_medic_detail)
        val model = ViewModelProvider.AndroidViewModelFactory(this.application).create(DetailViewModel::class.java)

        // View binding
        toolbar = findViewById(R.id.toolbar_detail)
        setSupportActionBar(toolbar)

        //Get the medic
        val id:Long = getIntent().getLongExtra("id", 0L)
        currentMedicament = model.getByCis(id)
        Timber.i( "detailMedic: $currentMedicament")

        initViews()

        binding.buttonNotice.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, currentMedicament.codeCis)
            }
            startActivity(intent)
        }
    }

    private fun initViews() {
        binding.apply {
            // Top CardView
            denomination.text = currentMedicament.denomination
            formePharma.text = currentMedicament.formePharma
            voieAdmin.text = currentMedicament.voieAdministration
        }

        if (currentMedicament.presentations.size > 0) {
            binding.apply {
                price.text = currentMedicament.presentations[0].prixMedicEuro
                rembRate.text = currentMedicament.presentations[0].txRemboursement
            }
        }

        binding.apply {infoGeneralView.setMedicament(currentMedicament)}

        // Card 3 GENER
        if (currentMedicament.generiques.size > 0)
            binding.apply {generiqueView.setGenerique(currentMedicament.generiques[0])}
        else
            binding.cardViewGenerique.visibility = View.GONE


        // Card 4 Compo
        if (currentMedicament.compos.size > 0)
            binding.apply {composantView.setComposant(currentMedicament.compos[0])}
        else
            binding.cardViewComposition.visibility = View.GONE

        // Card 5
        if (currentMedicament.conditionPrescritions.size > 0) {
            binding.apply {
                conditionPrescri.text = currentMedicament.conditionPrescritions[0].condition
            }
        }  else {
            binding.cardViewConditionPrescription.visibility = View.GONE
        }

        // Card 6 SMR
        if (currentMedicament.SMRs.size > 0)
            binding.apply { smrView.setSMR(currentMedicament.SMRs[0])}
        else
            binding.cardViewSmr.visibility = View.GONE

        // Card 7 ASMR
        if (currentMedicament.ASMRs.size > 0)
            binding.apply {asmrView.setASMR(currentMedicament.ASMRs[0])}
        else
            binding.cardViewAsmr.visibility = View.GONE

        if (currentMedicament.infos.size > 0) {
            binding.apply {
                infoView.setMedicament(currentMedicament)
            }
        } else {
            binding.cardViewInfoImportantes.visibility = View.GONE
        }

        // TODO bookmarked medic
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
