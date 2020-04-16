package com.ylt.medic.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ylt.medic.database.model.Presentation

interface PresentationDao {
    @Query("SELECT * FROM presentation")
    fun getPresentation(): List<Presentation>

    @Query("SELECT * FROM presentation WHERE code_cis = :codeCis")
    fun getPresentationByCis(codeCis: String): List<Presentation>

    @Insert
    fun insert(vararg medic: Presentation): List<Long>

    @Delete
    fun delete(medic: Presentation)
}