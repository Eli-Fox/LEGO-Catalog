package com.elifox.legocatalog.util

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

//import com.android.example.github.TestApp

/**
 * Custom runner to disable dependency injection.
 */
class AppTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return TODO()
        //return super.newApplication(cl, TestApp::class.java.name, context)
    }
}
