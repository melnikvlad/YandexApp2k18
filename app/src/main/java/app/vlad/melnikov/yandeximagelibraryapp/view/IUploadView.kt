package app.vlad.melnikov.yandeximagelibraryapp.view

import app.vlad.melnikov.yandeximagelibraryapp.model.Folder

interface IUploadView {
    fun addDirToAdapter(folder: Folder)
    fun addDirToAdapterInPosition(folder: Folder, pos: Int)
    fun viewPath(path: String)
    fun fileUploaded()
    fun error(message: String?)
}