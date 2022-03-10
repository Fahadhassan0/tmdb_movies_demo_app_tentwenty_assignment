package com.tmdb.movies.demo.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<VB> : AppCompatActivity()  {

    lateinit var context: Context
    protected var mBinding: VB? = null
    protected val binding: VB get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = initBindingRef()
        setContentView(initRoot())
        initRef()
        clicks()
    }

    //root functions
    protected abstract fun initBindingRef(): VB
    protected abstract fun initRoot(): View?
    protected abstract fun initRef()
    protected abstract fun clicks()
}