package com.example.ladm_u1_practica2.ui.gallery

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ladm_u1_practica2.databinding.FragmentGalleryBinding
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var comidas= arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.agregar.setOnClickListener {
            guardarEnArchivo()
        }
        return root
    }

    private fun leerEnArchivo(){
        try {
            val archivo = InputStreamReader(requireActivity().openFileInput("archivo.txt"))
            var c =""
            var listaContenido = archivo.readLines()
            listaContenido.forEach {
                comidas.add(it)
                c += it+"\n"
            }
            archivo.close()

        }catch (e:Exception){
            AlertDialog.Builder(requireContext())
                .setTitle("Error leer readF")
                .setMessage(e.message.toString())
                .show()
        }
    }

    private fun guardarEnArchivo() {
        try {
            comidas.clear()

            leerEnArchivo()
            var cadena = ""
            comidas.forEach {
                cadena += it +"\n"
            }
            val archivo = OutputStreamWriter(requireActivity().openFileOutput("archivo.txt",0))

            cadena += binding.tipoplatillo.text.toString().trim()+" "+
                    binding.precio.text.toString()+" "+
                    binding.nombreplatillo.text.toString().trim()+"\n"

            archivo.write(cadena)
            archivo.flush()
            archivo.close()

            binding.tipoplatillo.setText("")
            binding.nombreplatillo.setText("")
            binding.precio.setText("")
            AlertDialog.Builder(requireContext())
                .setMessage("Se guardo correctamente")
                .setPositiveButton("Ok",{d,i-> d.dismiss()})
                .show()
        }catch (e:Exception){
            AlertDialog.Builder(requireContext())
                .setTitle("Error guardar")
                .setMessage(e.message.toString())
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}