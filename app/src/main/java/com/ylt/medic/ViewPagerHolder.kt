package com.ylt.medic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.ylt.medic.Constants
import com.ylt.medic.R
import com.ylt.medic.database.model.*
import com.ylt.medic.views.*
import kotlinx.android.synthetic.main.card_receiver_viewpager.view.*

class ViewPagerHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context).inflate(
            R.layout.card_receiver_viewpager,
            parent,
            false
        )
    )

    fun bind(medic: Medicament, category: String, noticeUtility: String, progressBarStatus: Boolean, context: Context?) {
        lateinit var cardView: CardView
        itemView.cardview_receiver.removeAllViews()

        when(category) {
            Constants.INFO_GENERALES -> {
                cardView = FrontPageCardView(itemView.context)
                cardView.setMedicament(medic, noticeUtility)
                cardView.progressBarStatus(progressBarStatus)
                itemView.cardview_receiver.addView(cardView)

                cardView = InfoGeneralCardView(itemView.context)
                cardView.setMedicament(medic)
                itemView.cardview_receiver.addView(cardView)
            }

            Constants.BOITES -> {
                if (medic.presentations.isEmpty())
                    itemView.cardview_receiver.addView(getImageViewNoData(context))
                else {
                    medic.presentations.forEach { prez ->
                        cardView = BoiteCardView(itemView.context)
                        (cardView as BoiteCardView).setBoite(prez)
                        itemView.cardview_receiver.addView(cardView)
                    }
                }
            }

            Constants.COMPOSITION -> {
                if (medic.compos.isEmpty())
                    itemView.cardview_receiver.addView(getImageViewNoData(context))
                else {
                    medic.compos.forEach { compo ->
                        cardView = ComposantCardView(itemView.context)
                        (cardView as ComposantCardView).setComposant(compo)
                        itemView.cardview_receiver.addView(cardView)
                    }
                }
            }

            Constants.GENERIQUES -> {

                if (medic.generiques.isEmpty())
                    itemView.cardview_receiver.addView(getImageViewNoData(context))
                else {
                    medic.generiques.forEach { generique ->
                        cardView = GeneriqueCardView(itemView.context)
                        (cardView as GeneriqueCardView).setGenerique(generique)
                        itemView.cardview_receiver.addView(cardView)
                    }
                }
            }

            Constants.CONDI_PRESCRIPTION -> {

                if (medic.conditionPrescritions.isEmpty())
                    itemView.cardview_receiver.addView(getImageViewNoData(context))
                else {
                    medic.conditionPrescritions.forEach { condPrescr ->
                        cardView = PrescriptionCardView(itemView.context)
                        (cardView as PrescriptionCardView).setConditionPrescription(condPrescr)
                        itemView.cardview_receiver.addView(cardView)
                    }
                }
            }

            Constants.AUTRES -> {
                if (medic.ASMRs.isEmpty() && medic.SMRs.isEmpty() && medic.infos.isEmpty()) {
                    itemView.cardview_receiver.addView(getImageViewNoData(context))
                } else {
                    medic.infos.forEach { info ->
                        cardView = InfoImportantesCardView(itemView.context)
                        (cardView as InfoImportantesCardView).setInfo(info)
                        itemView.cardview_receiver.addView(cardView)
                    }
                    medic.SMRs.forEach { smr ->
                        cardView = SMRCardView(itemView.context)
                        (cardView as SMRCardView).setSMR(smr)
                        itemView.cardview_receiver.addView(cardView)
                    }
                    medic.ASMRs.forEach { asmr ->
                        cardView = ASMRCardView(itemView.context)
                        (cardView as ASMRCardView).setASMR(asmr)
                        itemView.cardview_receiver.addView(cardView)
                    }
                }
            }
        }
    }

    private fun getImageViewNoData(context: Context?): ImageView {
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.ic_baseline_not_interested_24)
        return imageView
    }
}