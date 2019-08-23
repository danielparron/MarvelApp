package br.com.marvelapp.model

data class Response(
    val code: Int,
    val etag: String,
    val data: Data
)