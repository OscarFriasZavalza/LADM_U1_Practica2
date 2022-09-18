package com.example.ladm_u1_practica2

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.ladm_u1_practica2.databinding.ActivityMainBinding
import java.io.File
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val fileName = "/data/data/mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos/files/archivo.txt"
        var file = File(fileName)
        var fileExists = file.exists()
        if(fileExists){
            Log.i("MA 42","Existe.")
        } else {
            Log.i("MA 44","No existe.")
            guardarEnArchivo("Principal Pozole 60.000\nBebida Refresco 20.000")
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.eliminarFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_settings->finish()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun guardarEnArchivo(car:String){
        try {
            val archivo = OutputStreamWriter(this.openFileOutput("archivo.txt", 0))

            archivo.write(car)
            archivo.flush()
            archivo.close()

        } catch (e: Exception) {
            AlertDialog.Builder(this)
                .setTitle("Error guardar")
                .setMessage(e.message.toString())
                .show()
        }

    }
}