package com.example.meera_trootech.model

data class Menu(
    val `data`: List<Data>
)

data class Data(
    val categoria: Categoria,
    val categoria_ecommerce: CategoriaEcommerce,
    val codigo: String,
    val codigoBarra: String,
    val comision: String,
    val descTipoComision: String,
    val descripcion: String,
    val idmenu: String,
    val imagen: String,
    val impuesto: Int,
    val impuestoAplicado: Boolean,
    val modificadores: List<Modificadore>,
    val nombre: String,
    val permite_descuentos: Boolean,
    val precioSugerido: String,
    val precio_abierto: Boolean,
    val tipo: String,
    val tipo_comision: String,
    val tipo_desc: String
)

data class Categoria(
    val codigo: String,
    val idcategoriamenu: String,
    val impuesto: String,
    val nombremenu: String,
    val orden: Int,
    val porcentaje: String,
    val printers: List<Any>
)

data class CategoriaEcommerce(
    val codigo: String,
    val idcategoriamenu: Int,
    val impuesto: Int,
    val nombremenu: String,
    val porcentaje: Int
)

data class Modificadore(
    val idmodificador: String
)


