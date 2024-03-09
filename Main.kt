import javafx.application.Application
import javafx.application.Application.launch
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.Stage
/*
Caso de estudio Santiago Albarracín Quintero, para este projecto utilice Intellij Idea community edition y el openjdk11.
Hice todas las pruebas de lo que requiere la actividad y todo funciona perfectamente, para borrar basta con
poner el código

Acerca de la pregunta de doña Rosa, al ser una aplicacíon pequeña los datos van a estar seguros almacenados localmente en la aplicación
en caso de requerir más seguridad en los datos debido a una expansión en la tienda sería prudente explorar opciónes de almacenamiento en al nube

 */
class Producto(val codigo: Int, val nombre: String, var precio: Double, var cantidad: Int)

class Tienda {
    private val inventario = mutableListOf<Producto>()

    init {
        inicializarInventario()
    }

    private fun inicializarInventario() {
        val productosPredeterminados = listOf(
            Producto(1, "Peras", 4000.0, 65),
            Producto(2, "Limones", 1500.0, 25),
            Producto(3, "Moras", 2000.0, 30),
            Producto(4, "Piñas", 3000.0, 15),
            Producto(5, "Tomates", 1000.0, 30),
            Producto(6, "Fresas", 3000.0, 12),
            Producto(7, "Frunas", 300.0, 50),
            Producto(8, "Galletas", 500.0, 400),
            Producto(9, "Chocolates", 1200.0, 500),
            Producto(10, "Arroz", 1200.0, 60)
        )
        inventario.addAll(productosPredeterminados)
    }

    fun insertarProducto(producto: Producto) {
        inventario.add(producto)
    }

    fun actualizarProducto(codigo: Int, nuevaCantidad: Int) {
        val producto = inventario.find { it.codigo == codigo }
        producto?.cantidad = nuevaCantidad
    }

    fun borrarProducto(codigo: Int) {
        inventario.removeAll { it.codigo == codigo }
    }

    fun productoMasCercanoACero(): Producto? {
        return inventario.minByOrNull { it.cantidad }
    }

    fun costoTotalInventario(): Double {
        return inventario.sumByDouble { it.precio * it.cantidad }
    }
    fun obtenerInventario(): List<Producto> {
        return inventario.toList()
    }

    fun validarCantidadMinima() {
        inventario.forEach { producto ->
            val cantidadMinima = producto.cantidad * 0.1
            if (producto.cantidad <= cantidadMinima) {
                println("¡Atención! El producto ${producto.nombre} está por agotarse.")
            }
        }
    }
}

class InterfazGrafica : Application() {
    private val tienda = Tienda()
    private val areaTexto = TextArea()
    private val labelResultado = Label()

    override fun start(primaryStage: Stage) {
        val vbox = VBox(10.0)
        vbox.alignment = Pos.CENTER

        val labelCodigo = Label("Código:")
        val tfCodigo = TextField()

        val labelNombre = Label("Nombre:")
        val tfNombre = TextField()

        val labelPrecio = Label("Precio:")
        val tfPrecio = TextField()

        val labelCantidad = Label("Cantidad:")
        val tfCantidad = TextField()

        val btnInsertar = Button("Añadir Producto")
        btnInsertar.setOnAction {
            val codigo = tfCodigo.text.toInt()
            val nombre = tfNombre.text
            val precio = tfPrecio.text.toDouble()
            val cantidad = tfCantidad.text.toInt()
            tienda.insertarProducto(Producto(codigo, nombre, precio, cantidad))
            tfCodigo.clear()
            tfNombre.clear()
            tfPrecio.clear()
            tfCantidad.clear()
        }

        val btnActualizar = Button("Actualizar Producto")
        btnActualizar.setOnAction {
            val codigo = tfCodigo.text.toInt()
            val nuevaCantidad = tfCantidad.text.toInt()
            tienda.actualizarProducto(codigo, nuevaCantidad)
            tfCodigo.clear()
            tfCantidad.clear()
        }

        val btnBorrar = Button("Borrar Producto")
        btnBorrar.setOnAction {
            val codigo = tfCodigo.text.toInt()
            tienda.borrarProducto(codigo)
            tfCodigo.clear()
        }

        val btnVerificar = Button("Verificar Producto Más Cercano a Agotarse")
        btnVerificar.setOnAction {
            val productoMasCercano = tienda.productoMasCercanoACero()
            if (productoMasCercano != null) {
                labelResultado.text = "El producto más cercano a agotarse es: ${productoMasCercano.nombre}"
            } else {
                labelResultado.text = "No hay productos en peligro de agotarse."
            }
        }

        val btnCalcularCosto = Button("Calcular Costo Total del Inventario")
        btnCalcularCosto.setOnAction {
            val costoTotal = tienda.costoTotalInventario()
            labelResultado.text = "El costo total del inventario es: $costoTotal"
        }

        val btnMostrarProductos = Button("Mostrar Productos")
        btnMostrarProductos.setOnAction {
            mostrarProductos()
        }

        vbox.children.addAll(
            labelCodigo, tfCodigo, labelNombre, tfNombre, labelPrecio, tfPrecio, labelCantidad, tfCantidad,
            btnInsertar, btnActualizar, btnBorrar, btnVerificar, btnCalcularCosto, btnMostrarProductos, areaTexto, labelResultado
        )

        val scene = Scene(vbox, 400.0, 500.0)
        primaryStage.title = "Tienda doña Rosa"
        primaryStage.scene = scene
        primaryStage.show()
    }
    private fun mostrarProductos() {
        areaTexto.clear()
        val inventario = tienda.obtenerInventario()
        inventario.forEach {
            areaTexto.appendText("Código: ${it.codigo}, Nombre: ${it.nombre}, Precio: ${it.precio}, Cantidad: ${it.cantidad}\n")
        }
    }
}

fun main() {
    launch(InterfazGrafica::class.java)
}
