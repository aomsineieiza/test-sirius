package com.test.sirius.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.test.sirius.R
import com.test.sirius.utils.AppConstants

abstract class BaseActivity : AppCompatActivity() {
    abstract fun setupView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
    }

    fun replaceFragment(viewId: Int, fragment: BaseFragment, tag: String) {
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            replace(viewId, fragment, tag)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(tag)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            val currentFragment =
                supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)

            currentFragment.let {
                when (it.name) {
                    AppConstants.FRAGMENT_SEARCH -> {
                        //Block pop stack first page
                    }
                }
            }
        }
    }
}