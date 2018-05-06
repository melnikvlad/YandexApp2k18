package app.vlad.melnikov.yandeximagelibraryapp.model

data class Folder(var name: String, var path: String, var lvl: Int, var hasChild: Boolean, var isChild: Boolean) {
}