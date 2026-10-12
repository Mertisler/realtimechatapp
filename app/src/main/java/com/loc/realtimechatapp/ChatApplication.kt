package com.loc.realtimechatapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Hilt'in kodları derleme zamanında üretebilmesi için zorunlu anotasyondur.
@HiltAndroidApp
class ChatApplication : Application()