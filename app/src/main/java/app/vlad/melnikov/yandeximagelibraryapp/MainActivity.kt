package app.vlad.melnikov.yandeximagelibraryapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMainView, TextWatcher {
    private var mPresenter: MainPresenter? = null
    private var mPhotoAdapter: PhotoFeedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideActionBar()
        initRecyclerView()

        input_search.addTextChangedListener(this)
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.onStart()
    }

    override fun onResume() {
        super.onResume()

        mPresenter.let { mPresenter = MainPresenter(this) }

        mPresenter?.onResume()
    }

    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
    }

    override fun initAdapterWith(photo: Photo) {
        mPhotoAdapter?.addItem(photo)
        mPresenter?.getLink(mPhotoAdapter?.itemCount?.minus(1), photo.path)
    }

    override fun loadAdapterPhoto(position: Int?, link: Link) {
        mPhotoAdapter?.loadPhoto(position, link)
    }

    override fun errorGetPhotos() {

    }

    override fun afterTextChanged(t: Editable?) {
        mPhotoAdapter?.search(t?.toString())
    }

    private fun photoClick(photo: Photo) {
//        val intent = Intent(this, PhotoActivity::class.java)
//        intent.putExtra("N", photo)

        mPresenter?.onPhotoClick(photo)
    }

    private fun initRecyclerView() {
        rv.layoutManager = GridLayoutManager(this, Constants.PHOTOS_IN_ROW)
        mPhotoAdapter = PhotoFeedAdapter(applicationContext, ArrayList(), { photo: Photo -> photoClick(photo) })
        rv.adapter = mPhotoAdapter
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}
