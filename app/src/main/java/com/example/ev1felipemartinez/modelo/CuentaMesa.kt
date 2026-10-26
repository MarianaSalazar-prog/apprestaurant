package com.example.ev1felipemartinez.modelo

class CuentaMesa(val mesa: Int) {
    private val _items: MutableList<ItemMesa> = mutableListOf() // Lista para guardar los platillos
    var aceptaPropina: Boolean = true // Se cobra propina por defecto

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        _items.add(ItemMesa(itemMenu, cantidad))
    }

    fun agregarItem(itemMesa: ItemMesa) {
        _items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int {
        var total = 0
        for (item in _items) {
            total += item.calcularSubtotal()
        }
        return total
    }

    fun calcularPropina(): Int {
        // Calcula el 10% opcional
        return if (aceptaPropina) (calcularTotalSinPropina() * 0.1).toInt() else 0
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}