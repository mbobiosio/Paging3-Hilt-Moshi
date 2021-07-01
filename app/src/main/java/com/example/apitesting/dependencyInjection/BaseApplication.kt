package com.example.apitesting.dependencyInjection

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApplication : Application()
{

}