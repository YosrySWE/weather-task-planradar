package com.planradar.weatherapptask.util.extensions

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


private const val ANIMATION_DURATION_HIDE = 1800L
private const val ANIMATION_DURATION_SHOW = 250L

fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar{
    this.view.setBackgroundColor(colorInt)
    return this
}
fun View.hideWithAnimation() {
    val fadeOut = AnimationUtils.loadAnimation(this.context, android.R.anim.fade_out)
    fadeOut.interpolator = DecelerateInterpolator()
    fadeOut.duration = ANIMATION_DURATION_HIDE
    this.startAnimation(fadeOut)
    fadeOut.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            // empty
        }

        override fun onAnimationEnd(animation: Animation) {
            this@hideWithAnimation.visibility = View.GONE
        }

        override fun onAnimationRepeat(animation: Animation) {
            // empty
        }
    })
}

fun View.showWithAnimation() {
    val fadeIn = AnimationUtils.loadAnimation(this.context, android.R.anim.fade_in)
    fadeIn.interpolator = DecelerateInterpolator()
    fadeIn.duration = ANIMATION_DURATION_SHOW
    this.startAnimation(fadeIn)
    fadeIn.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            this@showWithAnimation.visibility = View.VISIBLE
        }

        override fun onAnimationEnd(animation: Animation) {
            // empty
        }

        override fun onAnimationRepeat(animation: Animation) {
            // empty
        }
    })
}
fun View.secret() {
    if (!this.isGone()) {
        this.visibility = View.GONE
    }
}

fun String.toLongOrDefault(defaultValue: Long): Long {
    return try {
        toLong()
    } catch (_: NumberFormatException) {
        defaultValue
    }
}

fun View.show() {
    if (!this.isVisible()) {
        this.visibility = View.VISIBLE
    }
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}


fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun View.isGone(): Boolean = visibility == View.GONE

fun EditText.updateText(text: String?) {
    this.setText(text, TextView.BufferType.EDITABLE)
}

fun EditText.itsText() :String= this.text.toString().trim()

fun View.flash() {

    this.alpha = 1f
    this.scaleX = 1f
    this.scaleY = 1f

    val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f)
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, .95f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, .95f)

    val ass = ObjectAnimator.ofPropertyValuesHolder(this, alpha, scaleX, scaleY)
    ass.repeatCount = 3
    ass.duration = 200
    ass.repeatMode = ValueAnimator.REVERSE
    ass.start()

}

fun EditText.flash() {
    this.requestFocus()

    this.alpha = 1f
    this.scaleX = 1f
    this.scaleY = 1f

    val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f)
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, .95f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, .95f)

    val ass = ObjectAnimator.ofPropertyValuesHolder(this, alpha, scaleX, scaleY)
    ass.repeatCount = 3
    ass.duration = 200
    ass.repeatMode = ValueAnimator.REVERSE
    ass.start()
}


fun TextView.setUnderLine() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}


fun View.disableTemp() {
    isEnabled = false
    postDelayed({
        isEnabled = true
    }, 2000)
}

fun Fragment.restoreStatusBar() {
    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = activity?.window?.insetsController

        if (controller != null) {
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}

fun Fragment.hideStatusBar() {
    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = activity?.window?.insetsController

        if (controller != null) {
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
    //activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun Fragment.buildGetContentRequest(function: (Uri?) -> Unit): ActivityResultLauncher<String> =
    this.registerForActivityResult(ActivityResultContracts.GetContent()) {
        function(it)
    }


fun Fragment.buildOpenDocumentRequest(function: (Uri?) -> Unit): ActivityResultLauncher<Array<String>> {
    return this.registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        function(it)
    }
}

fun Fragment.buildTakePhotoRequest(function: (Boolean) -> Unit): ActivityResultLauncher<Uri> {
    return this.registerForActivityResult(ActivityResultContracts.TakePicture()) {
        function(it)
    }
}

fun Fragment.buildSelectMultipleContentRequest(function: (MutableList<Uri>?) -> Unit): ActivityResultLauncher<String> {
    return this.registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        function(it)
    }
}