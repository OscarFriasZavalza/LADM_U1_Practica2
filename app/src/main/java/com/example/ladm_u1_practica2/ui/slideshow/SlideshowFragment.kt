package com.example.ladm_u1_practica2.ui.slideshow

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ladm_u1_practica2.AdaptadorComida
import com.example.ladm_u1_practica2.databinding.FragmentSlideshowBinding
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val comidas= arrayListOf<String>()
    private lateinit var adaptadorComida:AdaptadorComida

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rv = binding.recyclerComida
        adaptadorComida = AdaptadorComida(comidas,object: AdaptadorComida.onItemClickListenr {
            override fun onItemClick(position: Int) {

                var carList = comidas[position].split(" ")
                var str = "${carList[1]} ${carList[0]} en $${carList[2]}"
                AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar Platillo")
                    .setMessage("¿Está seguro de eliminar ${str}?")
                    .setPositiveButton("Sí") { d, i ->
                        borrar(position)
                        d.dismiss()
                    }
                    .setNegativeButton("Cancelar",{d,i->d.dismiss()})
                    .show()
            }

        })

        leerEnArchivo()

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adaptadorComida

        return root
    }

    private fun leerEnArchivo(){
        try {
            val archivo = InputStreamReader(requireActivity().openFileInput("archivo.txt"))

            var listaContenido = archivo.readLines()
            listaContenido.forEach {
                comidas.add(it)
            }
            archivo.close()

        }catch (e:Exception){
            AlertDialog.Builder(requireContext())
                .setTitle("Error leer delete")
                .setMessage(e.message.toString())
                .show()
        }
    }

    private fun borrar(i:Int) {
        var c = ""
        comidas.removeAt(i)
        comidas.forEach{
            c+=it+"\n"
        }
        try {
            val fileName = "/data/data/mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos/files/archivo.txt"
            var file = File(fileName)
            file.delete()

            val archivo = OutputStreamWriter(requireContext().openFileOutput("archivo.txt", 0))
            archivo.write(c)
            archivo.flush()
            archivo.close()
            adaptadorComida.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Eliminado exitosamente", Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            Log.i("Error Borrar 100",e.message+"")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}