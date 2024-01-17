package com.example.crudequiposm

data class EquipoMedico(
    val id: String,
    val tipoequipo: String,
    val nombre: String,
    val marca: String,
    val modelo: String,
    val serie: String,
    var url: String
)
