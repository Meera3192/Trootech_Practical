package com.example.meera_trootech.model

data class Franquicias(
    val franquicias: List<Franquicia>?
)

data class Franquicia(
    val APIKEY: String?,
    val franquicia: String?,
    val horaCierreLocal: String?,
    val id_franquicia: String?,
    val negocio: String?,
    val principal: Boolean?,
    val tokenInvu: String?
)