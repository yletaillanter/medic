package com.ylt.medic.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ylt.medic.database.model.Generique

interface GeneriqueDao {

    @Query("SELECT * FROM generique")
    fun getGenerique(): List<Generique>

    @Query("SELECT * FROM generique WHERE code_cis = :codeCis")
    fun getGeneriqueByCis(codeCis: String): List<Generique>

    @Insert
    fun insert(vararg medic: Generique): List<Long>

    @Delete
    fun delete(medic: Generique)
}