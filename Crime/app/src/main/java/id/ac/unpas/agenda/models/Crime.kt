package id.ac.unpas.agenda.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Crime(
    @PrimaryKey
    val id: Int,
    val jenis: String,
    val lokasi: String,
    val tanggal: String,
    val deskripsi: String
)
