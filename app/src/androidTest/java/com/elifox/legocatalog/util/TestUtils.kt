package com.elifox.legocatalog.util

import android.app.Activity
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import com.elifox.legocatalog.legoset.data.LegoSet
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import java.util.*

val legoThemeId = 35

val testLegoSetA = LegoSet("id_123", "Fantastic Car", "imageUrl", legoThemeId,
        "30.04.2019", 234, "url", 2020)

val testLegoSetB = LegoSet("id_462", "Awesome House", "imageUrl", legoThemeId,
        "30.04.2019", 5866, "url", 2020)

/**
 * [Calendar] object used for tests.
 */
val testCalendar: Calendar = Calendar.getInstance().apply {
    set(Calendar.YEAR, 1998)
    set(Calendar.MONTH, Calendar.SEPTEMBER)
    set(Calendar.DAY_OF_MONTH, 4)
}

/**
 * Returns the content description for the navigation button view in the toolbar.
 */
fun getToolbarNavigationContentDescription(activity: Activity, toolbarId: Int) =
    activity.findViewById<Toolbar>(toolbarId).navigationContentDescription as String

/**
 * Simplify testing Intents with Chooser
 *
 * @param matcher the actual intent before wrapped by Chooser Intent
 */
fun chooser(matcher: Matcher<Intent>): Matcher<Intent> = allOf(
    hasAction(Intent.ACTION_CHOOSER),
    hasExtra(`is`(Intent.EXTRA_INTENT), matcher)
)