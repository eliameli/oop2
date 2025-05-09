package com.example.oop2.models

data class GoogleBooksResponse(
    val items: List<GoogleBookItem>?
)

data class GoogleBookItem(
    val id: String,
    val volumeInfo: VolumeInfo?
)

data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val pageCount: Int?,
    val industryIdentifiers: List<IndustryIdentifier>?
)

data class IndustryIdentifier(
    val type: String,
    val identifier: String
)
