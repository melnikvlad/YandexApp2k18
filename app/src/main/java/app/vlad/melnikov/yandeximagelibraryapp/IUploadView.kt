package app.vlad.melnikov.yandeximagelibraryapp

interface IUploadView {
    fun addDirToAdapter(folder: Folder)
    fun addDirToAdapterInPosition(folder: Folder, pos: Int)
    fun viewPath(path: String)
    fun fileUploaded()
    fun error(message: String?)
}