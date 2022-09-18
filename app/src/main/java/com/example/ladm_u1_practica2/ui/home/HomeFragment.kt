package com.example.ladm_u1_practica2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ladm_u1_practica2.databinding.FragmentHomeBinding
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.lang.Exception

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val comidas= arrayListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.agregar.setOnClickListener{

        }


        return root
    }
    private fun leer(){
        try {
            var archivo=InputStreamReader(requireActivity().openFileInput("archivo.txt"))
            var e=""
            var listacontenido=archivo.readLines()
            listacontenido.forEach{
                comidas.add(it)
                e+=it+"\n"
            }
            archivo.close()
        } catch (e:Exception){
            AlertDialog.Builder(requireContext())
                .setTitle("error al leer")
                .setMessage(e.message.toString())
                .show()

        }
    }
    private fun guardar(){
        try{
            comidas.clear()
            leer()
            var cadena=""
            comidas.forEach {
                cadena+=it+"\n"
            }
            var archivo=OutputStreamWriter(requireActivity().openFileOutput("archivo.txt",0))

            cadena+=binding.tipo.text.toString().trim()+" "+
                    binding.nombre.text.toString().trim()+" "+
                    binding.precio.text.toString()+"\n"

            archivo.write(cadena)
            archivo.flush()
            archivo.close()

            binding.tipo.setText("")
            binding.nombre.setText("")
            binding.precio.setText("")
            android.app.AlertDialog.Builder(requireContext())
                .setMessage("Se guardo correctamente")
                .setPositiveButton("Ok",{d,i-> d.dismiss()})
                .show()
        }catch (e:Exception){
            android.app.AlertDialog.Builder(requireContext())
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