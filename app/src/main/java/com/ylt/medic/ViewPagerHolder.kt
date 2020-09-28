package com.ylt.medic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ylt.medic.Constants.Companion.ASMR
import com.ylt.medic.Constants.Companion.BOITES
import com.ylt.medic.Constants.Companion.COMPOSITION
import com.ylt.medic.Constants.Companion.CONDI_PRESCRIPTION
import com.ylt.medic.Constants.Companion.GENERIQUES
import com.ylt.medic.Constants.Companion.INFO_GENERALES
import com.ylt.medic.Constants.Companion.INFO_IMPORTANTES
import com.ylt.medic.Constants.Companion.SMR
import com.ylt.medic.database.model.Medicament
import com.ylt.medic.views.*
import kotlinx.android.synthetic.main.card_receiver_viewpager.view.*

class ViewPagerHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    constructor(parent: ViewGroup) : this(LayoutInflater.from(parent.context).inflate(R.layout.card_receiver_viewpager, parent, false))

    fun bind(medic: Medicament, category: String) {

        lateinit var cardView: CardView
        itemView.cardview_receiver.removeAllViews()

        when(category) {
            INFO_GENERALES -> {
                cardView = FrontPageCardView(itemView.context)
                cardView.setMedicament(medic)
                itemView.cardview_receiver.addView(cardView)

                cardView = InfoGeneralCardView(itemView.context)
                cardView.setMedicament(medic)
                itemView.cardview_receiver.addView(cardView)
            }
            BOITES -> {
                medic.presentations.forEach {
                        prez ->
                    cardView = BoiteCardView(itemView.context)
                    (cardView as BoiteCardView).setBoite(prez)
                    itemView.cardview_receiver.addView(cardView)
                }
            }
            COMPOSITION -> {
                medic.compos.forEach {
                compo ->
                    cardView = ComposantCardView(itemView.context)
                    (cardView as ComposantCardView).setComposant(compo)
                    itemView.cardview_receiver.addView(cardView)
                }
            }
            GENERIQUES -> {
                medic.generiques.forEach { generique ->
                    cardView = GeneriqueCardView(itemView.context)
                    (cardView as GeneriqueCardView).setGenerique(generique)
                    itemView.cardview_receiver.addView(cardView)
                }
            }
            SMR -> {
                medic.SMRs.forEach {
                        smr ->
                    cardView = SMRCardView(itemView.context)
                    (cardView as SMRCardView).setSMR(smr)
                    itemView.cardview_receiver.addView(cardView)
                }
            }
            ASMR -> {
                medic.ASMRs.forEach {
                        asmr ->
                    cardView = ASMRCardView(itemView.context)
                    (cardView as ASMRCardView).setASMR(asmr)
                    itemView.cardview_receiver.addView(cardView)
                }
            }
            CONDI_PRESCRIPTION -> {
                medic.conditionPrescritions.forEach {
                        condPrescr ->
                    cardView = PrescriptionCardView(itemView.context)
                    (cardView as PrescriptionCardView).setConditionPrescription(condPrescr)
                    itemView.cardview_receiver.addView(cardView)
                }
            }
            INFO_IMPORTANTES -> {
                medic.infos.forEach {
                        info ->
                    cardView = InfoImportantesCardView(itemView.context)
                    (cardView as InfoImportantesCardView).setInfo(info)
                    itemView.cardview_receiver.addView(cardView)
                }
            }
        }
    }
}