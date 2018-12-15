package ru.akinadude.research.utils

import android.content.Context
import android.view.View
import com.squareup.picasso.Picasso
import ru.akinadude.research.App

object PicassoUtils {

    fun from(context: Context): Picasso {
        return (context.applicationContext as App).picasso
    }

    fun from(view: View): Picasso {
        return from(view.context)
    }
}