package com.drprog.moodstory.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.drprog.moodstory.R;
import com.drprog.moodstory.view.callback.BackPressHandler;
import com.drprog.moodstory.view.callback.OnResultListener;


/**
 * Activity for showing any fragment in
 */
public class ChildActivity extends AppCompatActivity implements OnResultListener {

    private static final String ARG_FRAGMENT_CLASS = "ARG_FRAGMENT_CLASS";
    private boolean isOnHomeProcessEnabled = true;

    public static void start(@NonNull Context _context, @NonNull Class<? extends Fragment> _fragmentClass, @Nullable Bundle _bundle) {
        _context.startActivity(getIntent(_context, _fragmentClass, _bundle));
    }

    public static Intent getIntent(@NonNull Context _context, @NonNull Class<? extends Fragment> _fragmentClass, @Nullable Bundle _bundle) {
        Intent intent = new Intent(_context, ChildActivity.class);
        final Bundle arg = _bundle != null ? new Bundle(_bundle) : new Bundle();
        arg.putString(ARG_FRAGMENT_CLASS, _fragmentClass.getName());
        intent.putExtras(arg);
        return intent;
    }

    public static void setOnHomeProcessEnabled(Activity activity, boolean enabled) {
        if (activity instanceof ChildActivity) ((ChildActivity) activity).setOnHomeProcessEnabled(enabled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        if (savedInstanceState == null) replaceFragment();
    }

    protected void replaceFragment() {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.flFragmentHolder, getFragment()).
                commit();
    }

    protected Fragment getFragment() {
        final Bundle args = getIntent().getExtras();
        String fname = args.getString(ARG_FRAGMENT_CLASS);
        final Fragment fragment = Fragment.instantiate(getApplicationContext(), fname, args);
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return onHomePressed();
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean onHomePressed() {
        if (isOnHomeProcessEnabled) {
            finish();
            return true;
        }
        return false;
    }

    public void setOnHomeProcessEnabled(boolean enabled) {
        isOnHomeProcessEnabled = enabled;
    }

//    @Override
//    public void setToolbarColor(Integer _color) {
//        if (_color != null) {
//            final ColorDrawable colorDrawable = new ColorDrawable(_color);
//            getWindow().setBackgroundDrawable(colorDrawable);
//        } else {
//            getWindow().setBackgroundDrawableResource(R.color.colorPrimaryDark);
//        }
//    }

    @Override
    public void sendResult (int resultCode, Intent data) {
        setResult(resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.flFragmentHolder);
        if (fragment != null && fragment instanceof BackPressHandler) {
            if (((BackPressHandler) fragment).onBackPressed()) return;
        }
        super.onBackPressed();
    }
}
