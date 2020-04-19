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

    @Query("SELECT id FROM medicament WHERE code_cis  = (:code_cis)")
    fun getIdByCis(code_cis: String): Long

    @Query("SELECT * FROM medicament WHERE id  = (:id)")
    fun getMedicById(id: Long): Medicament

    @Query("SELECT id FROM medicament WHERE code_cis  = (:code_cis) AND denomination  = (:denomination)")
    fun getIdOfExistingMedic(code_cis: String, denomination: String): Long

    @Query("UPDATE medicament SET isBookmarked = (:state) WHERE id = (:id)")
    fun setAsBookmarkedById(id: Long, state: Boolean)

    @Insert(onConflict = REPLACE)
    fun insert(vararg medic: Medicament): List<Long>

    @Delete
    fun delete(medic: Medicament)

    @Query("DELETE FROM medicament")
    fun deleteTable()
}