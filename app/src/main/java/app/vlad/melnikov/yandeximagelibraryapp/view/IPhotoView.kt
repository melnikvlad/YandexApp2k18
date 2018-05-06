package app.vlad.melnikov.yandeximagelibraryapp.view

import android.os.Bundle

interface IPhotoView {

    fun getBundlePhoto() : Bundle
    fun initViews(path: String, name: String, size: Int)
}