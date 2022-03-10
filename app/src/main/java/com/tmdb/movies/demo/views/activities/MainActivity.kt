package com.tmdb.movies.demo.views.activities

import android.view.View
import com.tmdb.movies.demo.base.BaseActivity
import com.tmdb.movies.demo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initBindingRef(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initRoot(): View? {
        return mBinding?.root
    }

    override fun initRef() {}

    override fun clicks() {}

}