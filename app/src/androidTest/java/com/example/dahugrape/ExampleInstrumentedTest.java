package com.example.dahugrape;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 *
 * 仪表测试，将在安卓设备上执行。
 * Instrumented test, which will execute on an Android device.
 * related to the Instrumented test or UI testing so basically whatever test requires
 * the android specific classes for example context, fragment, activity
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.dahugrape", appContext.getPackageName());
    }
}