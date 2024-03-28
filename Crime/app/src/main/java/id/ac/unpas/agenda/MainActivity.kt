package id.ac.unpas.agenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import id.ac.unpas.agenda.ui.screens.TodoScreen
import id.ac.unpas.agenda.ui.theme.AgendaTheme
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val crimeDAO = CrimeDAO(this)

        // Menambahkan data kejahatan
        val crime = Crime(1, "Pencurian", "Jalan Raya Unpas", "2024-03-28", "Seorang pria mencuri sebuah mobil")
        val insertResult = crimeDAO.addCrime(crime)

        // Mendapatkan semua data kejahatan
        val allCrimes = crimeDAO.getAllCrimes()
        for (c in allCrimes) {
            Log.d("CrimeData", "${c.id}, ${c.jenis}, ${c.lokasi}, ${c.tanggal}, ${c.deskripsi}")
        }
    }
}
