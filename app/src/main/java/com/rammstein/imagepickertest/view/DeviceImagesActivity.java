package com.rammstein.imagepickertest.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.rammstein.imagepickertest.DeviceImageInjector;
import com.rammstein.imagepickertest.R;
import com.rammstein.imagepickertest.presenter.DeviceImagesPresenter;

import java.io.File;
import java.util.List;

public class DeviceImagesActivity extends AppCompatActivity implements DeviceImagesView {

    private DeviceImagesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ImageView mMainImage;
    private AppBarLayout mAppBarLayout;

    private DeviceImagesPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        mPresenter = DeviceImageInjector.inject(this);
        mPresenter.init();

        setupToolbar();
        setupList();
        setupAppBar();

        mMainImage = (ImageView) findViewById(R.id.main_image);
    }

    private void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setupList() {
        mRecyclerView = (RecyclerView) findViewById(R.id.main_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                this, 4
        ));
    }

    private void setupAppBar() {
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mAppBarLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
                params.height = mAppBarLayout.getWidth() + getToolbarHeight();
                mPresenter.onViewReady();
                return true;
            }
        });
    }

    private int getToolbarHeight() {
        final ActionBar bar = getSupportActionBar();
        if (bar != null) {
            return bar.getHeight();
        }
        return 0;
    }

    @Override
    public void setupFoldersSpinner(@NonNull final List<String> folders) {
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, folders
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.main_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                mPresenter.onFolderSelect(position);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });
        spinner.setSelection(0);
    }

    @Override
    public void setupImagesList(@NonNull final List<File> images,
                                @NonNull final DeviceImagesAdapter.DeviceImageAdapterListener listener) {
        mAdapter = new DeviceImagesAdapter(
                DeviceImagesActivity.this,
                images,
                listener
        );
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setImage(final File image) {
        Glide.with(DeviceImagesActivity.this)
                .load(image)
                .crossFade()
                .into(mMainImage);
    }

    @Override
    public void refreshImagesList() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCropViewIfNeed() {
        mAppBarLayout.setExpanded(true, true);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
