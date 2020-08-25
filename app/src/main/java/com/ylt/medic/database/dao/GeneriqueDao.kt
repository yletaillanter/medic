package com.ylt.medic.database.dao

import androidx.room.*
import com.ylt.medic.database.model.Generique

@Dao
interface GeneriqueDao {

    @Query("SELECT * FROM generique")
    fun getGenerique(): List<Generique>

    @Query("SELECT * FROM generique WHERE code_cis = :codeCis")
    fun getGeneriqueByCis(codeCis: String): List<Generique>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg generique: Generique)

    @Delete
    fun delete(generique: Generique)

    @Query("DELETE FROM generique")
    fun deleteTable()
}