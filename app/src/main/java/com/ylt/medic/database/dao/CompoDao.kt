package com.ylt.medic.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ylt.medic.database.model.Compo

@Dao
interface CompoDao {

    @Query("SELECT * FROM composition")
    fun getAllCompo(): List<Compo>

    @Query("SELECT * FROM composition WHERE code_cis = :codeCis")
    fun getCompoByCis(codeCis: String): List<Compo>

    @Insert
    fun insert(vararg medic: Compo): List<Long>

    @Delete
    fun delete(medic: Compo)
}