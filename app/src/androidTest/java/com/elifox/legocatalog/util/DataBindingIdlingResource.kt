package com.elifox.legocatalog.util

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.test.espresso.IdlingResource
import androidx.test.rule.ActivityTestRule
import java.util.*

/**
 * An espresso idling resource implementation that reports idle status for all data binding
 * layouts.
 * <b/>
 * Since this application only uses fragments, the resource only checks the fragments instead
 * of the whole view tree.
 */
class DataBindingIdlingResource(
    private val activityTestRule: ActivityTestRule<*>
) : IdlingResource {
    // list of registered callbacks
    private val idlingCallbacks = mutableListOf<IdlingResource.ResourceCallback>()
    // give it a unique id to workaround an espresso bug where you cannot register/unregister
    // an idling resource w/ the same name.
    private val id = UUID.randomUUID().toString()
    // holds whether isIdle is called and the result was false. We track this to avoid calling
    // onTransitionToIdle callbacks if Espresso never thought we were idle in the first place.
    private var wasNotIdle = false

    override fun getName() = "DataBinding $id"

    override fun isIdleNow(): Boolean {
        val idle = !getBindings().any { it.hasPendingBindings() }
        @Suppress("LiftReturnOrAssignment")
        if (idle) {
            if (wasNotIdle) {
                // notify observers to avoid espresso race detector
                idlingCallbacks.forEach { it.onTransitionToIdle() }
            }
            wasNotIdle = false
        } else {
            wasNotIdle = true
            // check next frame
            activityTestRule.activity.findViewById<View>(android.R.id.content).postDelayed({
                isIdleNow
            }, 16)
        }
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        idlingCallbacks.add(callback)
    }

    /**
     * Find all binding classes in all currently available fragments.
     */
    private fun getBindings(): List<ViewDataBinding> {
        return (activityTestRule.activity as? FragmentActivity)
            ?.supportFragmentManager
            ?.fragments
            ?.mapNotNull {
                it.view?.let { view ->
                    DataBindingUtil.getBinding<ViewDataBinding>(
                        view
                    )
                }
            } ?: emptyList()
    }
}