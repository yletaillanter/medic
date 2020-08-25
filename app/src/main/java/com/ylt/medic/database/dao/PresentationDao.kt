package com.ylt.medic.database.dao

import androidx.room.*
import com.ylt.medic.database.model.Presentation

@Dao
interface PresentationDao {

    @Query("SELECT * FROM presentation")
    fun getPresentation(): List<Presentation>

    @Query("SELECT * FROM presentation WHERE code_cis = :codeCis")
    fun getPresentationByCis(codeCis: String): List<Presentation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg presentation: Presentation)

    @Delete
    fun delete(presentation: Presentation)

    @Query("DELETE FROM presentation")
    fun deleteTable()
}