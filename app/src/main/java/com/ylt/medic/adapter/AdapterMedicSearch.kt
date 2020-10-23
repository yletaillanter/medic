package com.ylt.medic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ylt.medic.R
import com.ylt.medic.database.model.Medicament
import java.util.ArrayList

/**
 * Created by yoannlt on 06/07/2017.
 */
class AdapterMedicSearch: RecyclerView.Adapter<AdapterMedicSearch.ViewHolder>() {

    private lateinit var clickListener: ClickListener
    private lateinit var context: Context

    private var mDataset: ArrayList<Medicament>? = ArrayList<Medicament>()
    private var medoc: Medicament? = null

    // Constructor
    fun AdapterAnnonceDemandeur(context: Context, mDataset: ArrayList<Medicament>) {
        this.context = context
        this.mDataset = mDataset
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_medic_search, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        medoc = mDataset!!.get(position)

        // set view from medoc
        holder.denomination!!.text = medoc!!.denomination;
        holder.denomination!!.text = medoc!!.denomination;

        //setAnimation(holder.itemView, position)
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        // UI element (R.layout.card_annonce_demandeur)
        internal var denomination: TextView? = null
        internal var forme_pharma: TextView? = null

        init {
            denomination = v.findViewById(R.id.denomination) as TextView
            forme_pharma = v.findViewById(R.id.forme_pharma) as TextView

            v.setOnClickListener(this)
        }

        override fun onClick(view: View) {
                clickListener.itemClicked(view, adapterPosition, AdapterMedicSearch::class.java.getSimpleName())
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

    //Mettre à jour la liste des données
    fun replace(data: ArrayList<Medicament>) {
        this.mDataset!!.clear()
        this.mDataset = data
        this.notifyDataSetChanged()
    }

    fun setContext(context: Context) {
        this.context = context;
    }

    fun getmDataset(): ArrayList<Medicament>? {
        return mDataset
    }

    fun clear(){
        this.mDataset!!.clear()
        this.notifyDataSetChanged()
    }
}