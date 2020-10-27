package com.ylt.medic.database.dao

import androidx.room.*
import com.ylt.medic.database.model.ASMR
import com.ylt.medic.database.model.SMR

@Dao
interface SmrDao {

    @Query("SELECT * FROM smr")
    fun getSmr(): List<SMR>

    @Query("SELECT * FROM smr WHERE code_cis = :codeCis")
    fun getSmrByCis(codeCis: String): List<SMR>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg smr: SMR)

    @Update
    fun update(smr: SMR)

    @Delete
    fun delete(smr: SMR)

    @Query("DELETE FROM smr")
    fun deleteTable()
}