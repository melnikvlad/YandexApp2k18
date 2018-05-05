package app.vlad.melnikov.yandeximagelibraryapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_options.*


class MainActivity : AppCompatActivity(), IMainView, TextWatcher, View.OnClickListener {
    private var mPresenter: MainPresenter? = null
    private var mPhotoAdapter: PhotoFeedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpActionBar()
        initRecyclerView()

        mPresenter.let {
            mPresenter = MainPresenter(this)
        }

        mPresenter?.getPhotos()

        input_search.addTextChangedListener(this)
        fab_options.setOnClickListener(this)
        fab_reload.setOnClickListener(this)
        fab_search.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.onStart()
    }

    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
    }

    override fun initAdapterWith(photo: Photo) {
        mPhotoAdapter?.addItem(photo)
        mPresenter?.getLink(mPhotoAdapter?.itemCount?.minus(1), photo.path)
        initPhotoCount(mPhotoAdapter?.itemCount)
    }

    override fun loadAdapterPhoto(position: Int?, link: Link) {
        mPhotoAdapter?.loadPhoto(position, link)
    }

    override fun clearPreviousResults() {
        mPhotoAdapter?.clear()
    }

    override fun errorGetPhotos() {

    }

    override fun afterTextChanged(t: Editable?) {
        mPhotoAdapter?.search(t?.toString())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            fab_options.id -> viewOptions()
            fab_reload.id -> mPresenter?.reload()
            fab_search.id -> viewSearchField()
        }
    }

    private fun initPhotoCount(itemCount: Int?) {
        text_photo_count.text = String.format(getString(R.string.photo_count_template), itemCount)
    }

    private fun photoClick(photo: Photo) {
        val bundle = Bundle()
        val intent = Intent(this, PhotoActivity::class.java)
        bundle.putParcelable(Constants.BUNDLE, photo)
        intent.putExtra(Constants.PHOTO_PARCELABLE, bundle)
        startActivity(intent)
    }

    private fun initRecyclerView() {
        rv.layoutManager = GridLayoutManager(this, Constants.PHOTOS_IN_ROW)
        mPhotoAdapter = PhotoFeedAdapter(applicationContext, ArrayList(), { photo: Photo -> photoClick(photo) })
        rv.adapter = mPhotoAdapter
    }

    private fun setUpActionBar() {
        supportActionBar?.title = getString(R.string.main_title)
    }

    private fun viewOptions() {
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_anim)
        val slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_anim)
        if (fab_reload.visibility == View.INVISIBLE && fab_search.visibility == View.INVISIBLE) {
            fab_options.setImageResource( R.mipmap.ic_clear_black_24dp)
            fab_reload.visibility = View.VISIBLE
            fab_search.visibility = View.VISIBLE
            fab_reload.startAnimation(slideUp)
            fab_search.startAnimation(slideUp)
        } else {
            fab_options.setImageResource( R.mipmap.ic_more_horiz_black_24dp)
            fab_reload.visibility = View.INVISIBLE
            fab_search.visibility = View.INVISIBLE
            input_search.visibility = View.GONE
            fab_reload.startAnimation(slideOut)
            fab_search.startAnimation(slideOut)
        }
    }

    private fun viewSearchField() {
        if (input_search.visibility == View.VISIBLE) {
            input_search.visibility = View.GONE
        } else {
            input_search.visibility = View.VISIBLE
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
}
