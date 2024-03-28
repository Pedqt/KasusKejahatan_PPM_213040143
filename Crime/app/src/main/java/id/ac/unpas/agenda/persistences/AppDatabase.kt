package id.ac.unpas.agenda.persistences

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.unpas.agenda.models.Todo
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Crime::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun crimeDao(): CrimeDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "crime_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
