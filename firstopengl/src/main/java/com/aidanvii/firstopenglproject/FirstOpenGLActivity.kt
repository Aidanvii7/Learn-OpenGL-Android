package com.aidanvii.firstopenglproject

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aidanvii.firstopenglproject.databinding.ActivityOpenGlBinding
import com.aidanvii.utils.arch.ViewModelFactory
import com.aidanvii.utils.arch.addTypedFactory
import com.aidanvii.utils.arch.get
import com.aidanvii.utils.arch.viewModelProvider
import com.aidanvii.utils.opengl.v20.OpenGLES2ViewModel


class FirstOpenGLActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpenGlBinding

    private val viewModelProvider by lazy(LazyThreadSafetyMode.NONE) {
        viewModelProvider(
                factory = ViewModelFactory.Builder()
                        .addTypedFactory(OpenGLES2ViewModel.Factory(FirstOpenGLRenderer()))
                        .build()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_gl)
        binding.viewModel = viewModelProvider.get()
    }
}