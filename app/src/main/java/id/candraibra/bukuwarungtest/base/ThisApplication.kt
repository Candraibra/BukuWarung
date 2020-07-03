package id.candraibra.bukuwarungtest.base

import android.app.Application

class ThisApplication : Application() {

    companion object {
        lateinit var app: ThisApplication
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

}