package com.example.tarea4_3

import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea4_3.databinding.ItemContactoBinding

class ContactosAdapter(private val contactos: List<Contacto>,
                       private val contactoPulsadoListener : ContactoPulsadoListener
) : RecyclerView.Adapter<ContactosAdapter.ViewHolder>() {
    /**
     * La clase viewHolder se encarga de mostrar la información de cada contacto.
     */
    class ViewHolder(private val binding : ItemContactoBinding) : RecyclerView.ViewHolder(binding.root){
        var completo = false
        fun bind (contacto:Contacto){
            if (contacto.genero == "Mujer")
                binding.Foto.setImageResource(R.drawable.avatarfemenino)
            var iniciales = ""
            val array = contacto.nombre.split(" ")
            for (nombre : String in array){
                iniciales == ""
            }
            Log.d("inicial", iniciales)
            binding.Nombre.text = contacto.nombre
            binding.Telefono.text = contacto.tlfn
            binding.Inicial.text = iniciales
            binding.Foto.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    if (completo.equals(false)){
                        binding.Nombre.text = contacto.nombre
                        binding.Telefono.text = contacto.tlfn
                        completo = true
                    }else{
                        binding.Inicial.text = iniciales
                        binding.Nombre.text = ""
                        binding.Telefono.text = ""
                        completo = false
                    }
                }
            })
        }
    }

    /**
     * Función que se encarga de crear la vista.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /**
     * Esta función obtiene la cantidad de contactos que tiene la lista.
     */
    override fun getItemCount(): Int = contactos.size

    /**
     * La siguiente función utiliza una transición cuando se pulsa la imagen del contacto, y accede al modo llamada cuando se pulsa sobre el número de teléfono.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transition = TransitionInflater.from(holder.itemView.context).inflateTransition(android.R.transition.fade)

        TransitionManager.beginDelayedTransition(holder.itemView as ViewGroup?, transition)
        holder.bind(contactos[position])
        holder.itemView.setOnClickListener{
            contactoPulsadoListener.contactoPulsado((contactos[position]))
        }
    }
}