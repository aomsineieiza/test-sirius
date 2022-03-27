package com.test.sirius.view.custom

import android.content.Context
import android.graphics.Typeface
import java.util.*

object Typefaces {
    private val cache = Hashtable<String, Typeface>()

    operator fun get(c: Context, name: String): Typeface? {
        synchronized(cache) {
            return cache[name]
        }
    }
}