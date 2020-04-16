package com.ylt.medic.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ylt.medic.database.model.SMR

interface SmrDao {
    @Query("SELECT * FROM smr")
    fun getSmr(): List<SMR>

    @Query("SELECT * FROM smr WHERE code_cis = :codeCis")
    fun getSmrByCis(codeCis: String): List<SMR>

    @Insert
    fun insert(vararg medic: SMR): List<Long>

    @Delete
    fun delete(medic: SMR)
}