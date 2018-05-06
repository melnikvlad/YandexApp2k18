package app.vlad.melnikov.yandeximagelibraryapp.presenter

import app.vlad.melnikov.yandeximagelibraryapp.model.Folder
import app.vlad.melnikov.yandeximagelibraryapp.model.Item
import app.vlad.melnikov.yandeximagelibraryapp.utils.ApiManager
import app.vlad.melnikov.yandeximagelibraryapp.utils.Constants
import app.vlad.melnikov.yandeximagelibraryapp.view.IUploadView
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadPresenter(val mView: IUploadView) {
    private val mDisposable = CompositeDisposable()

    fun onDestroy() {
        mDisposable.clear()
    }

    fun getDirs() {
        fetchDirs(Constants.ROOT)
    }

    private fun fetchDirs(path: String) {
        val dirDisposable = ApiManager.createWithToken(true)
                .fetchDisk(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap({ disk -> Single.just(disk._embedded.items) })
                .flatMapObservable { items -> Observable.fromIterable(items) }
                .subscribe(
                        { checkType(it) }
                )

        mDisposable.add(dirDisposable)
    }

    private fun checkType(item: Item) {
        if (item.type.equals(Constants.TYPE_DIR)) {
            mView.addDirToAdapter(Folder(item.name, item.path, Constants.DEEP_LVL_TOP, false, false))
        }
    }

    fun getChildDirs(pos: Int, path: String) {
        fetchChildDirs(path.substring(5), pos)
    }

    private fun fetchChildDirs(parentPath: String, pos: Int) {
        val childDispodable = ApiManager.createWithToken(true)
                .fetchDisk(parentPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap({ disk -> Single.just(disk._embedded.items) })
                .flatMapObservable { items -> Observable.fromIterable(items) }
                .subscribe(
                        { checkChildType(it, pos) }
                )
        mDisposable.add(childDispodable)
    }


    private fun checkChildType(item: Item, pos: Int) {
        if (item.type.equals(Constants.TYPE_DIR)) {
            mView.addDirToAdapterInPosition(Folder(item.name, item.path, Constants.DEEP_LVL_CHILD, false, true), pos)
        }
    }

    fun changePath(path: String) {
        mView.viewPath(path.substring(5))
    }

    fun uploadFile(path: String, url: String) {
        ApiManager.createWithToken(true).postPhoto(path, url).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response?.code() == 200) {
                    mView.fileUploaded()
                } else {
                    mView.error("Ошибка. Повторите позднее")
                }
            }
        })

    }
}