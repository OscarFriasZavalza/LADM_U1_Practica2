package com.example.ladm_u1_practica2.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ladm_u1_practica2.AdaptadorComida
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

        leerEnArchivo()
        val rv = binding.recyclerComida
        val adapter = AdaptadorComida(comidas, object : AdaptadorComida.onItemClickListenr {
            override fun onItemClick(position: Int) {
                Log.i("Click ReadF 44",comidas[position])
            }

        })
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun leerEnArchivo() {
        try {
            val archivo = InputStreamReader(requireActivity().openFileInput("archivo.txt"))
            comidas.removeAll(comidas)
            var listaContenido = archivo.readLines()
            listaContenido.forEach {
                Log.i("Read 65 Lista", it)
                comidas.add(it)
            }

            archivo.close()

        } catch (e: Exception) {
            android.app.AlertDialog.Builder(requireContext())
                .setTitle("Error leer readF")
                .setMessage(e.message.toString())
                .show()
            Log.i("Read 76 Lista", e.message+"")
        }
    }

}