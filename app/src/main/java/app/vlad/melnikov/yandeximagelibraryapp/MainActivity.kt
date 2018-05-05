package app.vlad.melnikov.yandeximagelibraryapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity(), IMainView {

    private lateinit var mPresenter: MainPresenter
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideActionBar()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

        mPresenter = MainPresenter(this)
        mPresenter.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}
