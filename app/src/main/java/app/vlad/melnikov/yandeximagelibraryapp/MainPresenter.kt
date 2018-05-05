package app.vlad.melnikov.yandeximagelibraryapp

import android.content.Intent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(val mView: IMainView) {
    private val mDisposable = CompositeDisposable()

    fun onStart() {

    }

    fun onResume() {
        getPhotos(Constants.ROOT)
    }

    fun onDestroy() {
        mDisposable.clear()
    }

    private fun getPhotos(path: String) {
        val photosDisposable = ApiManager.createWithToken(true)
                .fetchDisk(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap({ disk -> Single.just(disk._embedded.items) })
                .flatMapObservable { items -> Observable.fromIterable(items) }
                .subscribe(
                        { checkType(it) },
                        { mView.errorGetPhotos() }
                )

        mDisposable.add(photosDisposable)
    }

    private fun checkType(item: Item) {
        if (item.type.equals(Constants.TYPE_DIR)) {
            getPhotos(item.path)
        } else if (item.type.equals(Constants.TYPE_FILE) && item.mime_type.startsWith(Constants.MIME_TYPE_IMAGE)) {
            mView.initAdapterWith(Photo(item.path, item.name, item.size, true))
        }
    }

    fun getLink(position: Int?, path: String) {
        val linksDisposable = ApiManager.createWithToken(true)
                .fetchLink(path.substring(5))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it ->  mView.loadAdapterPhoto(position, it)
                })

        mDisposable.add(linksDisposable)
    }
}