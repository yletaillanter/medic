package com.ylt.medic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ylt.medic.R
import com.ylt.medic.database.model.Medicament
import timber.log.Timber
import java.util.ArrayList

/**
 * Created by yoannlt on 06/07/2017.
 */
class AdapterMedicSearch: RecyclerView.Adapter<AdapterMedicSearch.ViewHolder>() {

    private lateinit var clickListener: ClickListener
    private lateinit var context: Context

    private var mDataset: ArrayList<Medicament>? = ArrayList<Medicament>()
    private var medoc: Medicament? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_medic_search, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        medoc = mDataset!![position]

        // set view from medicament
        holder.denomination.text = medoc!!.denomination.split(",")[0]
        holder.formePharma.text = medoc!!.formePharma

        when(medoc!!.formePharma) {
            "comprimé",
            "comprimé pélliculé sécable",
            "comprimé enrobé" -> holder.logoFormePharma.setImageResource(R.drawable.comprime)

            "comprimé pélliculé" -> holder.logoFormePharma.setImageResource(R.drawable.comprime_pellicule)
            "solution injectable" -> holder.logoFormePharma.setImageResource(R.drawable.seringue)
            "gélule" -> holder.logoFormePharma.setImageResource(R.drawable.gelule)
            "solution buvable" -> holder.logoFormePharma.setImageResource(R.drawable.drink)
            "solution pour perfusion" -> holder.logoFormePharma.setImageResource(R.drawable.perfusion)
            "sirop" -> holder.logoFormePharma.setImageResource(R.drawable.sirop)
            "crème" -> holder.logoFormePharma.setImageResource(R.drawable.creme)
            "suppositoire" -> holder.logoFormePharma.setImageResource(R.drawable.suppositoires)
            else -> holder.logoFormePharma.setImageResource(R.drawable.comprime)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        internal var denomination: TextView = v.findViewById(R.id.denomination) as TextView
        internal var formePharma: TextView = v.findViewById(R.id.forme_pharma) as TextView
        internal var logoFormePharma: ImageView = v.findViewById(R.id.logo_forme) as ImageView

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(view: View) {
                clickListener.itemClicked(view, bindingAdapterPosition, AdapterMedicSearch::class.java.getSimpleName())
        }
    }

    override fun getItemCount(): Int {
        return if (mDataset != null)
            mDataset!!.size
        else
            0
    }

    fun setClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    /* Add an item to the dataList */
    fun add(position: Int, annonce: Medicament) {
        mDataset!!.add(position, annonce)
        notifyItemInserted(position)
    }

    fun replace(data: ArrayList<Medicament>) {
        this.mDataset!!.clear()
        this.mDataset = data
        this.notifyDataSetChanged()
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun clear(){
        this.mDataset!!.clear()
        this.notifyDataSetChanged()
    }
}