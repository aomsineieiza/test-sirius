package com.test.sirius.view

import android.os.Bundle
import com.test.sirius.base.BaseActivity
import com.test.sirius.utils.AppConstants
import com.test.sirius.view.search.SearchFragment
import com.test.sirius.R
import com.test.sirius.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun setupView() {
        navigateToSearchFragment()
    }

    private fun navigateToSearchFragment() {
        replaceFragment(
            R.id.container,
            SearchFragment.newInstance(),
            AppConstants.FRAGMENT_SEARCH
        )
    }
}