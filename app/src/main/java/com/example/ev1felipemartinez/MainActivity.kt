package com.example.ev1felipemartinez

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ev1felipemartinez.modelo.CuentaMesa
import com.example.ev1felipemartinez.modelo.ItemMenu
import com.example.ev1felipemartinez.modelo.ItemMesa
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // 1. Declaración de variables para enlazar con las vistas
    private lateinit var etCantidadPastel: EditText
    private lateinit var etCantidadCazuela: EditText
    private lateinit var tvSubtotalPastel: TextView
    private lateinit var tvSubtotalCazuela: TextView
    private lateinit var tvTotalComida: TextView
    private lateinit var switchPropina: Switch
    private lateinit var tvMontoPropina: TextView
    private lateinit var tvTotalFinal: TextView

    // 2. Declaración de variables para el modelo
    private lateinit var cuentaMesa: CuentaMesa
    private lateinit var itemMesaPastel: ItemMesa
    private lateinit var itemMesaCazuela: ItemMesa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar el modelo con los valores solicitados
        val menuPastel = ItemMenu("Pastel de Choclo", "12000")
        val menuCazuela = ItemMenu("Cazuela", "10000")

        cuentaMesa = CuentaMesa(1)
        itemMesaPastel = ItemMesa(menuPastel, 0)
        itemMesaCazuela = ItemMesa(menuCazuela, 0)

        cuentaMesa.agregarItem(itemMesaPastel)
        cuentaMesa.agregarItem(itemMesaCazuela)

        // Enlazar las variables de Kotlin con los IDs del archivo
        etCantidadPastel = findViewById(R.id.etCantidadPastel)
        etCantidadCazuela = findViewById(R.id.etCantidadCazuela)
        tvSubtotalPastel = findViewById(R.id.tvSubtotalPastel)
        tvSubtotalCazuela = findViewById(R.id.tvSubtotalCazuela)
        tvTotalComida = findViewById(R.id.tvTotalComida)
        switchPropina = findViewById(R.id.switchPropina)
        tvMontoPropina = findViewById(R.id.tvMontoPropina)
        tvTotalFinal = findViewById(R.id.tvTotalFinal)

        // Llamar a la función que configura los eventos del usuario
        configurarEventos()
    }

    private fun configurarEventos() {
        // Objeto TextWatcher para detectar cuando el usuario escribe una cantidad
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Actualiza los montos inmediatamente
                actualizarPantalla()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        // Asignar el evento a los campos de texto
        etCantidadPastel.addTextChangedListener(textWatcher)
        etCantidadCazuela.addTextChangedListener(textWatcher)

        // Evento CheckedChange para el Switch de la propina
        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            // Actualiza los montos inmediatamente al cambiar la opción de propina
            actualizarPantalla()
        }
    }

    private fun actualizarPantalla() {
        // Capturar los valores ingresados. Si el campo está vacío, se asume 0.
        val cantPastel = etCantidadPastel.text.toString().toIntOrNull() ?: 0
        val cantCazuela = etCantidadCazuela.text.toString().toIntOrNull() ?: 0

        // Actualizar las cantidades en el modelo
        itemMesaPastel.cantidad = cantPastel
        itemMesaCazuela.cantidad = cantCazuela

        // Crear un formateador para mostrar los valores como Pesos Chilenos
        val formatoChile = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

        // Actualizar los textos en la pantalla con el formato correcto
        tvSubtotalPastel.text = formatoChile.format(itemMesaPastel.calcularSubtotal())
        tvSubtotalCazuela.text = formatoChile.format(itemMesaCazuela.calcularSubtotal())

        tvTotalComida.text = formatoChile.format(cuentaMesa.calcularTotalSinPropina())
        tvMontoPropina.text = formatoChile.format(cuentaMesa.calcularPropina())
        tvTotalFinal.text = formatoChile.format(cuentaMesa.calcularTotalConPropina())
    }
}