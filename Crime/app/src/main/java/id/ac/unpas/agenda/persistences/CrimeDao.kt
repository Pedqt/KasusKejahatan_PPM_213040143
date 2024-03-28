package id.ac.unpas.agenda.persistences

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.unpas.agenda.models.Todo
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

@Dao
class CrimeDAO(context: Context) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "crime_database"
        private const val TABLE_NAME = "crimes"
        private const val KEY_ID = "id"
        private const val KEY_JENIS = "jenis"
        private const val KEY_LOKASI = "lokasi"
        private const val KEY_TANGGAL = "tanggal"
        private const val KEY_DESKRIPSI = "deskripsi"
    }

    private lateinit var dbHelper: DatabaseHelper

    init {
        dbHelper = DatabaseHelper(context)
    }

    inner class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase?) {
            val createTableQuery = ("CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY, "
                    + "$KEY_JENIS TEXT, $KEY_LOKASI TEXT, $KEY_TANGGAL TEXT, $KEY_DESKRIPSI TEXT)")
            db?.execSQL(createTableQuery)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }

    fun addCrime(crime: Crime): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(KEY_JENIS, crime.jenis)
        values.put(KEY_LOKASI, crime.lokasi)
        values.put(KEY_TANGGAL, crime.tanggal)
        values.put(KEY_DESKRIPSI, crime.deskripsi)
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return success
    }

    fun getAllCrimes(): ArrayList<Crime> {
        val crimeList = ArrayList<Crime>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = dbHelper.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var jenis: String
        var lokasi: String
        var tanggal: String
        var deskripsi: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                jenis = cursor.getString(cursor.getColumnIndex(KEY_JENIS))
                lokasi = cursor.getString(cursor.getColumnIndex(KEY_LOKASI))
                tanggal = cursor.getString(cursor.getColumnIndex(KEY_TANGGAL))
                deskripsi = cursor.getString(cursor.getColumnIndex(KEY_DESKRIPSI))
                val crime = Crime(id, jenis, lokasi, tanggal, deskripsi)
                crimeList.add(crime)
            } while (cursor.moveToNext())
        }
        return crimeList
    }
}
