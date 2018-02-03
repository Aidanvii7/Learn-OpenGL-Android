package com.aidanvii.airhockey

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aidanvii.airhockey.databinding.ActivityAirHockeyBinding
import com.aidanvii.utils.arch.ViewModelFactory
import com.aidanvii.utils.arch.addTypedFactory
import com.aidanvii.utils.arch.get
import com.aidanvii.utils.arch.viewModelProvider
import com.aidanvii.utils.opengles.glsl.ShaderLoaderProvider
import com.aidanvii.utils.opengles.v20.GLRendererViewModel


class AirHockeyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAirHockeyBinding

    private val viewModelProvider by lazy(LazyThreadSafetyMode.NONE) {
        viewModelProvider(
                factory = ViewModelFactory.Builder()
                        .addTypedFactory(
                                GLRendererViewModel.Factory(AirHockeyRenderer(ShaderLoaderProvider(this)))
                        ).build()
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        ViewHookInflaterFactory.create(this).addViewHook(object : ViewHookInflaterFactory.ViewHook {
            override fun shouldCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet?): Boolean {
                return false
            }
            override fun viewCreated(view: View, parent: View?) {
            }
        })
        */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_air_hockey)
        binding.viewModel = viewModelProvider.get()
    }
}