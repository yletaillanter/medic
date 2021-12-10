package com.ylt.medic

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ylt.medic.adapter.ViewPagerHolder
import com.ylt.medic.database.model.Medicament

class ViewPagerAdapter(var context: Context?) : RecyclerView.Adapter<ViewPagerHolder>() {
    lateinit var medic: Medicament
    var list: List<String> = listOf()
    private var noticeUtility: String = ""
    private var progressBarStatus: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        return ViewPagerHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.bind(medic, list[position], noticeUtility, progressBarStatus, context)
    }

    fun setMedic(list: List<String>, medic: Medicament) {
        this.list = list
        this.medic = medic
        notifyDataSetChanged()
    }

    fun setNotice(noticeUtility: String) {
        this.noticeUtility = noticeUtility
    }

    fun setProgressBarStatus(progressBarStatus: Boolean) {
        this.progressBarStatus = progressBarStatus
    }

    override fun getItemCount(): Int = list.size
}