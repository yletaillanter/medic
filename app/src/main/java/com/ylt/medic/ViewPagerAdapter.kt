package com.ylt.medic

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ylt.medic.database.model.Medicament

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerHolder>() {
    lateinit var medic: Medicament
    var list: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        return ViewPagerHolder(parent)
    }
    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.bind(medic, list[position])
    }
    fun setMedic(list: List<String>, medic: Medicament) {
        this.list = list
        this.medic = medic
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}