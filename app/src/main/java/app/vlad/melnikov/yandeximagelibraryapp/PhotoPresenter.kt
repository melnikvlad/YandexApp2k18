package app.vlad.melnikov.yandeximagelibraryapp

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