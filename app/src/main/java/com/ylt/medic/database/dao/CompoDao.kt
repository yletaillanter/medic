package com.ylt.medic.database.dao

import androidx.room.*
import com.ylt.medic.database.model.Compo

@Dao
interface CompoDao {

    @Query("SELECT * FROM composition")
    fun getAllCompo(): List<Compo>

    @Query("SELECT * FROM composition WHERE code_cis = :codeCis")
    fun getCompoByCis(codeCis: String): List<Compo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg compo: Compo)

    @Delete
    fun delete(compo: Compo)

    @Query("DELETE FROM composition")
    fun deleteTable()
}