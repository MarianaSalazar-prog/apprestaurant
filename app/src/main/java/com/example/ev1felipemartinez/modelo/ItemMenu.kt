package com.example.ev1felipemartinez.modelo

class ItemMenu(val nombre: String, val precio: String) {

    // Función auxiliar para convertir el precio (String) a un número (Int) para poder multiplicar
    fun obtenerPrecioEntero(): Int {
        // Elimina el punto de los miles si existe y convierte a entero
        return precio.replace(".", "").toIntOrNull() ?: 0
    }
}