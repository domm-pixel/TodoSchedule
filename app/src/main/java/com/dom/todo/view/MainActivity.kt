package com.dom.todo.view

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.dom.todo.R
import com.dom.todo.base.BaseActivity
import com.dom.todo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (supportFragmentManager.backStackEntryCount > 0) {
                findNavController(R.id.navHostFragment).popBackStack()
            } else {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.onBackPressedDispatcher.addCallback(backPressedCallback)
        binding {
//            val hostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
//            navHostFragment.setupWithNavController(hostFragment.navController)
//            navHostFragment.itemIconTintList = null
        }
    }
}