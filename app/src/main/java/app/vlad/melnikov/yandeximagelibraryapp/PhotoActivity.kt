package app.vlad.melnikov.yandeximagelibraryapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity: AppCompatActivity(), IPhotoView {
    private var mPresenter: PhotoPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        setUpActionBar()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.let { mPresenter = PhotoPresenter(this) }
        mPresenter?.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initViews(path: String, name: String, size: Int) {
        Picasso.with(this).load(path).into(big_photo)
        supportActionBar?.title = name
        text_size?.text = String.format(getString(R.string.photo_size_template), size)
    }

    override fun getBundlePhoto(): Bundle {
        return intent.getBundleExtra(Constants.PHOTO_PARCELABLE)
    }

    private fun setUpActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}