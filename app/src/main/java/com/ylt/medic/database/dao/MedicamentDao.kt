package com.ylt.medic.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.ylt.medic.database.model.Medicament

/**
 * Created by yoannlt on 14/06/2017.
 */
@Dao
interface MedicamentDao {
    @Query("SELECT * FROM medicament")
    fun getAll(): List<Medicament>

    @Query("SELECT * FROM medicament WHERE isBookmarked = 1")
    fun getBookmarked(): List<Medicament>

    @Query("SELECT * FROM medicament WHERE code_cis  = (:code_cis)")
    fun getMedicByCis(code_cis: String): Medicament

    @Update
    fun update(medicament: Medicament)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg medic: Medicament): List<Long>

    @Delete
    fun delete(medic: Medicament)

    @Query("DELETE FROM medicament")
    fun deleteTable()

    @Query("SELECT * FROM medicament WHERE denomination LIKE '%' || :query || '%'")
    fun searchMedicByDenomination(query: String): List<Medicament>



}