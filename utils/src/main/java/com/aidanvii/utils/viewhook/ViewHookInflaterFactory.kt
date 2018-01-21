package com.aidanvii.utils.viewhook

import android.content.Context
import android.support.v4.view.LayoutInflaterCompat
import android.support.v4.view.LayoutInflaterFactory
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.AppCompatCheckedTextView
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView
import android.support.v7.widget.AppCompatRadioButton
import android.support.v7.widget.AppCompatRatingBar
import android.support.v7.widget.AppCompatSeekBar
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.aidanvii.utils.delegates.weak

import java.lang.ref.WeakReference

/**
 * Created by aidan.mcwilliams on 29/03/2016.
 *
 *  A [LayoutInflaterFactory] that creates Views only when attached [ViewHook]'s care about the view, i.e. when they return true from [ViewHook.shouldCreateView]
 *
 *  [ViewHook]'s that care about said [View] will receive a callback upon successful creation of the [View].
 *
 *  It essentially allows implementations of [ViewHook] to hook into the [View] at inflation time, and hook up custom behavior based on for example, custom xml attributes.
 */
class ViewHookInflaterFactory private constructor(
        compat_activity: AppCompatActivity,
        inflater_clone: LayoutInflater
) : LayoutInflater.Factory2 {
    private val weakActivity by weak(compat_activity)
    private val weakInflater by weak(inflater_clone)
    private val hooks = mutableSetOf<ViewHook>()
    fun onGetLayoutInflater(): LayoutInflater? = weakInflater

    fun onGetSystemService(name: String): Any? {
        return if (name == AppCompatActivity.LAYOUT_INFLATER_SERVICE) weakInflater else null
    }

    fun addViewHook(hook: ViewHook) {
        hooks.add(hook)
    }

    fun removeViewHook(hook: ViewHook) {
        hooks.remove(hook)
    }

    fun clearFactoryModules() {
        hooks.clear()
    }

    override fun onCreateView(viewClassName: String, context: Context, attrs: AttributeSet?): View? {
        return onCreateView(null, viewClassName, context, attrs)
    }

    override fun onCreateView(parent: View?, viewClassName: String, context: Context, attrs: AttributeSet?): View? {
        var view: View? = null

        // iterate the FactoryModules to see if any of them care about the view
        for (hook in hooks) {
            if (hook.shouldCreateView(parent, viewClassName, context, attrs)) {
                // the hook cares about the view, create it if null!
                if (view == null) view = createView(parent, viewClassName, context, attrs)
                //if View is still null then break here, something is wrong and we cannot create it even though the factory wants us to!
                if (view == null)
                    throw RuntimeException() // TODO add whatever view type it is you want to createView..
                // this hook cares about the view we just created, let it do whatever it needs to to the View, assuming it wont abuse it!
                hook.viewCreated(view, parent)
            }
        }
        return view
    }

    private fun createView(parent: View?, name: String, context: Context, attrs: AttributeSet?): View? {

        return weakActivity?.run {
            // Try to let the Activity handle it (inflating fragments from XML)
            onCreateView(name, context, attrs)?.let { return it }

            if (attrs != null) {
                // Get themed views from app compat
                delegate.createView(parent, name, context, attrs)?.let { return it }
            }

            resolveAppCompatView(name, context, attrs)?.let { return it }

            return resolveCustomView(name, context, attrs)
        }
    }

    private fun resolveAppCompatView(viewClassName: String, context: Context, attrs: AttributeSet?): View? {
        // We need to 'inject' AppCompat's tint aware Views in place of the standard framework versions
        // P.S. we could replace this with Font equivalent views so we don't have to reference the fully qualified FontView classes in the XML layouts.
        // (the FontView classes are basically extended AppCompat Views anyway, with custom font support, also means the layout editor wont barf trying to render them, and will render AppCompat views instead)
        return when (viewClassName) {
            "TextView" -> AppCompatTextView(context, attrs)
            "ImageView" -> AppCompatImageView(context, attrs)
            "Button" -> AppCompatButton(context, attrs)
            "EditText" -> AppCompatEditText(context, attrs)
            "Spinner" -> AppCompatSpinner(context, attrs)
            "ImageButton" -> AppCompatImageButton(context, attrs)
            "CheckBox" -> AppCompatCheckBox(context, attrs)
            "RadioButton" -> AppCompatRadioButton(context, attrs)
            "CheckedTextView" -> AppCompatCheckedTextView(context, attrs)
            "AutoCompleteTextView" -> AppCompatAutoCompleteTextView(context, attrs)
            "MultiAutoCompleteTextView" -> AppCompatMultiAutoCompleteTextView(context, attrs)
            "RatingBar" -> AppCompatRatingBar(context, attrs)
            "SeekBar" -> AppCompatSeekBar(context, attrs)
            else -> null
        }
    }

    private fun resolveCustomView(viewClassName: String, context: Context, attrs: AttributeSet?): View? {
        // TODO resolve reflectively
        return null
    }

    //endregion

    interface ViewHook {
        /**
         * Called when the [ViewHookInflaterFactory] is trying to determine if it should create the [View].
         *
         *  This will trigger [viewCreated] to be called if the view could be successfully created.
         *
         * @param parent
         * @param name
         * @param context
         * @param attrs
         * @return true to create the [View], false otherwise
         */
        fun shouldCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet?) = true

        /**
         * Called when the [View] has been created.
         *
         *  If needed, the [ViewHook] should pull out whatever it needs to from the parameters previously passed to [shouldCreateView].
         *
         * @param view   the newly instantiated view. This is never null.
         * @param parent the future parent view of the newly instantiated view (the view is not attached to the parent yet). Note this may be null.
         */
        fun viewCreated(view: View, parent: View?) {}
    }

    companion object {
        fun create(appCompatActivity: AppCompatActivity): ViewHookInflaterFactory {
            val inflaterClone = appCompatActivity.layoutInflater.cloneInContext(appCompatActivity.layoutInflater.context)
            return ViewHookInflaterFactory(appCompatActivity, inflaterClone).also { viewHookInflaterFactory ->
                LayoutInflaterCompat.setFactory2(inflaterClone, viewHookInflaterFactory)
            }
        }
    }
}