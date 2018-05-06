package app.vlad.melnikov.yandeximagelibraryapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity(), IUploadView, View.OnClickListener {
    private var mPresenter: UploadPresenter? = null
    private var mDirsAdapter: DirsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        setUpActionBar()
        initRecyclerView()

        mPresenter.let {
            mPresenter = UploadPresenter(this)
        }
        mPresenter?.getDirs()

        fab_send.setOnClickListener(this)
    }

    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun addDirToAdapter(folder: Folder) {
        mDirsAdapter?.addItem(folder)
        mPresenter?.getChildDirs(mDirsAdapter!!.itemCount, folder.path)
    }

    override fun addDirToAdapterInPosition(folder: Folder, pos: Int) {
        mDirsAdapter?.addItemAt(folder, pos)
        mPresenter?.getChildDirs(pos + 1, folder.path)
    }

    override fun viewPath(path: String) {
        text_path.text = path
    }

    override fun error(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            fab_send.id -> mPresenter?.uploadFile(text_path.text.toString() + "/photo", input_url.text.toString())
        }
    }

    override fun fileUploaded() {
        Toast.makeText(this, getString(R.string.sended), Toast.LENGTH_LONG).show()
    }

    private fun initRecyclerView() {
        rv_dirs.layoutManager = LinearLayoutManager(this)
        mDirsAdapter = DirsAdapter(this, ArrayList(), { folder: Folder -> mPresenter?.changePath(folder.path) })
        rv_dirs.adapter = mDirsAdapter
    }

    private fun setUpActionBar() {
        supportActionBar?.title = getString(R.string.upload_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}