package app.vlad.melnikov.yandeximagelibraryapp

interface IMainView {
    fun errorGetPhotos()
    fun initAdapterWith(photo: Photo)
    fun loadAdapterPhoto(position: Int?, link: Link)
}