package com.futsch1.medtimer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.view.MenuProvider;
import androidx.navigation.Navigation;


import com.futsch1.medtimer.helpers.FileHelper;
import com.futsch1.medtimer.helpers.PathHelper;
import com.futsch1.medtimer.reminders.ReminderProcessor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OptionsMenu implements MenuProvider {
    private final Context context;
    private final MedicineViewModel medicineViewModel;
    private final View view;
    private final HandlerThread backgroundThread;

    private Menu menu;


    public OptionsMenu(Context context, MedicineViewModel medicineViewModel, ActivityResultCaller caller, View view) {
        this.context = context;
        this.medicineViewModel = medicineViewModel;
        this.view = view;

        backgroundThread = new HandlerThread("Export");
        backgroundThread.start();
    }



    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.main, menu);
        menu.setGroupDividerEnabled(true);
        enableOptionalIcons(menu);


        this.menu = menu;
        setupSettings();
        setupVersion();

    }

    @SuppressLint("RestrictedApi")
    private static void enableOptionalIcons(@NonNull Menu menu) {
        if (menu instanceof MenuBuilder) {
            try {
                Method m = menu.getClass().getDeclaredMethod(
                        "setOptionalIconsVisible", Boolean.TYPE);
                m.invoke(menu, true);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                Log.e("Menu", "onMenuOpened", e);
            }
        }
    }

    private void setupSettings() {
        MenuItem item = menu.findItem(R.id.settings);
        item.setOnMenuItemClickListener(menuItem -> {
            Navigation.findNavController(view).navigate(R.id.action_global_preferencesFragment);
            return true;
        });
    }

    private void setupVersion() {
        MenuItem item = menu.findItem(R.id.version);
        item.setTitle(context.getString(R.string.version, BuildConfig.VERSION_NAME));
    }













    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
