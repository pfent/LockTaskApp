package de.tum.in.fent.locktaskapp;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private String pkg;
    private DevicePolicyManager policyManager;
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pkg = getPackageName();
        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        decorView = getWindow().getDecorView();

        ComponentName admin = new ComponentName(this, AdminReceiver.class);
        if (!policyManager.isAdminActive(admin) || !policyManager.isDeviceOwnerApp(getPackageName())) {
            Toast.makeText(this, "This app needs to be a device owner", Toast.LENGTH_LONG).show();
            return;
        }

        policyManager.setLockTaskPackages(admin, new String[]{pkg});

        pinScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    @Override
    public void onBackPressed() {
        // catch this, so the back button does nothing
    }

    private void pinScreen() {
        if (!policyManager.isLockTaskPermitted(pkg)) {
            Toast.makeText(this, "Something went wrong granting permissions for LockTask", Toast.LENGTH_LONG).show();
            return;
        }
        startLockTask();
    }

    private void hideSystemUI() {
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
