package com.example.tarea4_3

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tarea4_3.databinding.ContactosBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        var toast = Toast.makeText(this, "", Toast.LENGTH_LONG)
        val contactos = ContactosBinding.inflate(layoutInflater)
        setContentView(contactos.root)
        val db = FirebaseDatabase.getInstance().getReference("Contactos")
        val lista = listOf(
            Contacto("1","Alejandro Sosa García", "123456789", "Hombre"),
            Contacto("2","Angel Navarro Mesas", "987654321", "Hombre"),
            Contacto("3","David Bermúdez Alcántara", "666666666", "Hombre"),
            Contacto("4","Alejandro Mulero Muletas", "000000000", "Hombre"),
            Contacto("5","Francisco Javier ", "987654321", "Hombre"),
            Contacto("6","Rubén Lindes ", "612511120", "Hombre"),
            Contacto("7","David Perea ", "123456789", "Hombre"),
            Contacto("8","Claudia Mendoza", "666666666", "Mujer"),
            Contacto("9","Lydia Pérez González", "123456789", "Mujer"),
            Contacto("10","Carmen Martín Núñez", "987654321", "Mujer"),
            Contacto("11","Antonio Navarro", "666666666", "Hombre"),
            Contacto("12","Fernando José Miguel Gómez", "123456789", "Hombre"),
            Contacto("13","Britany Sanchez Ballón", "987654321", "Mujer"),
            Contacto("14","Yeray Jimenez", "666666666", "Hombre"),
            Contacto("15","Juan Manuel Sánchez Moreno", "123456789", "Hombre"))
        for (contacto in lista){
            db.child(contacto.id).setValue(contacto)
        }
        contactos.VistaContactos.adapter = ContactosAdapter(lista, object :
            ContactoPulsadoListener {
            override fun contactoPulsado(contacto: Contacto) {
                val dial = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:" + contacto.tlfn)
                )
                startActivity(dial)
            }
        })
        db.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue<String>()
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                toast.setText("Error al leer la base de datos")
                toast.show()
                Log.w(TAG, "Error al leer.", error.toException())
            }
        })
    }
}