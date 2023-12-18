package algonquin.cst2335.chen0872;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Tests to see what happens if just numbers are put into the edittext field and the
     * login button is pressed. Expected "YOU SHALL NOT PASS!" from the textview.
     */
    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5723);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.editTextPassword)));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());


        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.pwMessage));
        textView.check(matches(withText("YOU SHALL NOT PASS!")));
    }

    /**
     * Tests to see what happens if everything but an upper case character are put into the edittext field and the
     * login button is pressed. Expected "YOU SHALL NOT PASS!" from the textview.
     */
    @Test
    public void testFindMissingUpperCase(){
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.editTextPassword)));
        appCompatEditText.perform(replaceText("a1!"));


        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.pwMessage));
        textView.check(matches(withText("YOU SHALL NOT PASS!")));
    }

    /**
     * Tests to see what happens if everything but a lower case character are put into the edittext field and the
     * login button is pressed. Expected "YOU SHALL NOT PASS!" from the textview.
     */
    @Test
    public void testFindMissingLowerCase(){
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.editTextPassword)));
        appCompatEditText.perform(replaceText("A1!"));


        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.pwMessage));
        textView.check(matches(withText("YOU SHALL NOT PASS!")));
    }

    /**
     * Tests to see what happens if everything but a number character are put into the edittext field and the
     * login button is pressed. Expected "YOU SHALL NOT PASS!" from the textview.
     */
    @Test
    public void testFindMissingNumber(){
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.editTextPassword)));
        appCompatEditText.perform(replaceText("aA!"));


        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.pwMessage));
        textView.check(matches(withText("YOU SHALL NOT PASS!")));
    }

    /**
     * Tests to see what happens if everything but a special character are put into the edittext field and the
     * login button is pressed. Expected "YOU SHALL NOT PASS!" from the textview.
     */
    @Test
    public void testFindMissingSpecialCharacterCase(){
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.editTextPassword)));
        appCompatEditText.perform(replaceText("aA1"));


        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.pwMessage));
        textView.check(matches(withText("YOU SHALL NOT PASS!")));
    }

    /**
     * Tests to see what happens if everything needed are put into the edittext field and the
     * login button is pressed. Expected "Your password is complex enough" from the textview.
     */
    @Test
    public void testAdequatePassword(){
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.editTextPassword)));
        appCompatEditText.perform(replaceText("aA1!"));


        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.pwMessage));
        textView.check(matches(withText("Your password meets the requirements")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
