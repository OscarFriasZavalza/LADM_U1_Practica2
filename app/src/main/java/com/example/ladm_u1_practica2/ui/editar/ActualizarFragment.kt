package com.example.ladm_u1_practica2.ui.editar

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ladm_u1_practica2.AdaptadorComida
import com.example.ladm_u1_practica2.R
import com.example.ladm_u1_practica2.databinding.FragmentActualizarBinding
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class ActualizarFragment : Fragment() {

    private var _binding: FragmentActualizarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val comidas = arrayListOf<String>()
    private lateinit var adapterComida: AdaptadorComida
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(ActualizarViewModel::class.java)

        _binding =FragmentActualizarBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val rv = binding.recyclerComida
        adapterComida = AdaptadorComida(comidas,object: AdaptadorComida.onItemClickListenr {
            override fun onItemClick(position: Int) {
                showdialog(comidas[position], position)
            }

        })

        leerEnArchivo()

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapterComida

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                .setTitle("Error leer readF")
                .setMessage(e.message.toString())
                .show()
        }
    }

    fun showdialog(car:String, i:Int) {

        val builder = AlertDialog.Builder(requireContext())
            .create()
        builder.setTitle("Actualizar Comida")
        val view = layoutInflater.inflate(R.layout.fragment_gallery,null)
        val  button = view.findViewById<Button>(R.id.agregar)
        button.setText("Actualizar")

        val tipo = view.findViewById<EditText>(R.id.tipoplatillo)
        val nombre = view.findViewById<EditText>(R.id.nombreplatillo)
        val precio = view.findViewById<EditText>(R.id.precio)

        //Log.i("Update ",""+car_list.size+" "+car)
        val carList = car.split(" ")
        tipo.setText(carList[0])
        nombre.setText(carList[1])
        precio.setText(carList[2])

        builder.setView(view)
        button.setOnClickListener {
            var c = ""
            comidas.forEach {
                c+=it+"\n"
            }
            comidas[i] = tipo.text.toString().trim() +" "+ nombre.text.toString().trim()+" "+ precio.text.toString().trim()
            adapterComida.notifyDataSetChanged()
            guardarEnArchivo()
            Toast.makeText(requireContext(),"Comida Actualizada", Toast.LENGTH_SHORT).show()
            builder.dismiss()
        }

        builder.show()
    }

    private fun guardarEnArchivo(){
        try {
            var c = ""
            comidas.forEach {
                c+=it+"\n"
            }

            val fileName = "/data/data/mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos/files/archivo.txt"
            var file = File(fileName)
            file.delete()

            val archivo = OutputStreamWriter(requireContext().openFileOutput("archivo.txt", 0))
            archivo.write(c)
            archivo.flush()
            archivo.close()

        } catch (e: Exception) {
            AlertDialog.Builder(requireContext())
                .setTitle("Error guardar")
                .setMessage(e.message.toString())
                .show()
            Log.i("Error Update 140",""+e.message)
        }

    }
}