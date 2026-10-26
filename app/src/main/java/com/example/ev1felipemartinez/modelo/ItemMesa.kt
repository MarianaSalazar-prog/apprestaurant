package com.example.ev1felipemartinez.modelo

class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {

    fun calcularSubtotal(): Int {
        // Multiplica la cantidad por el precio unitario del plato[cite: 1]
        return cantidad * itemMenu.obtenerPrecioEntero()
    }
}