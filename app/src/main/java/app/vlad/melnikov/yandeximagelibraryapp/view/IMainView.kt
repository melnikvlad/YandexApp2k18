package app.vlad.melnikov.yandeximagelibraryapp.view

import app.vlad.melnikov.yandeximagelibraryapp.model.Link
import app.vlad.melnikov.yandeximagelibraryapp.model.Photo

interface IMainView {
    fun errorGetPhotos()
    fun initAdapterWith(photo: Photo)
    fun loadAdapterPhoto(position: Int?, link: Link)
    fun clearPreviousResults()
}