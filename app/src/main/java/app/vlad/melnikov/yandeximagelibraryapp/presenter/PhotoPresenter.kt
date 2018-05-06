package app.vlad.melnikov.yandeximagelibraryapp.presenter

import app.vlad.melnikov.yandeximagelibraryapp.model.Photo
import app.vlad.melnikov.yandeximagelibraryapp.utils.Constants
import app.vlad.melnikov.yandeximagelibraryapp.view.IPhotoView

class PhotoPresenter(val mView: IPhotoView) {

    fun onResume() {
        initPhoto()
    }

    private fun initPhoto() {
        val bundle = mView.getBundlePhoto()
        val photo = bundle.getParcelable(Constants.BUNDLE) as Photo
        mView.initViews(photo.path, photo.name, photo.size / 1000)
    }
}