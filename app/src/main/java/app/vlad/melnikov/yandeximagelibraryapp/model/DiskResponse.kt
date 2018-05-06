package app.vlad.melnikov.yandeximagelibraryapp.model

data class DiskResponse(val _embedded: Embedded)

data class Embedded(val items: List<Item>)

data class Item(val type: String, val mime_type: String, val path: String, val name: String, val size: Int)

data class Link(val href: String)