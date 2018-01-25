package com.aidanvii.utils.opengles

import android.support.annotation.BinderThread

/**
 * Denotes that the annotated method should only be called on the GLThread.
 * If the annotated element is a class, then all methods in the class should be called
 * on the GLThread.
 */
typealias GLThread = BinderThread