package com.rammstein.imagepickertest.presenter;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.rammstein.imagepickertest.SimpleActionListener;
import com.rammstein.imagepickertest.model.DeviceImagesModel;
import com.rammstein.imagepickertest.view.DeviceImagesAdapter;
import com.rammstein.imagepickertest.view.DeviceImagesView;

import java.io.File;

/**
 * Created by klitaviy on 4/11/17.
 */

public class DeviceImagesPresenterImpl implements DeviceImagesPresenter {

    private DeviceImagesView mView;
    private DeviceImagesModel mModel;

    private DeviceImagesAdapter.DeviceImageAdapterListener mAdapterListener;

    private boolean mViewReady;

    public DeviceImagesPresenterImpl(@NonNull final DeviceImagesView view,
                                     @NonNull final DeviceImagesModel model) {
        mView = view;
        mModel = model;
        mAdapterListener = new DeviceImagesAdapter.DeviceImageAdapterListener() {
            @Override
            public void onItemSelect(final File file) {
                if (mView != null) {
                    mView.setImage(file);
                    mView.showCropViewIfNeed();
                }
            }
        };
    }

    @Override
    public void init() {
        if (mView != null) {
            mModel.init(
                    new File(Environment.getExternalStorageDirectory().getAbsolutePath()),
                    new SimpleActionListener() {
                        @Override
                        public void onSuccess() {
                            if (mViewReady && mView != null){
                                setupView();
                            }
                        }
                    });
        }
    }

    @Override
    public void onViewReady() {
        mViewReady = true;
        if (mView != null) {
            if (mModel.isReady()) {
                setupView();
            }
        }
    }

    private void setupView() {
        mView.setupFoldersSpinner(
                mModel.folders()
        );
        mView.setupImagesList(
                mModel.filesByPosition(0),
                mAdapterListener
        );
        mView.setImage(
                mModel.imageByPosition(0)
        );
    }

    @Override
    public void onFolderSelect(final int position) {
        if (mView != null) {
            mView.setupImagesList(
                    mModel.filesByPosition(position),
                    mAdapterListener
            );
            mView.refreshImagesList();
            mView.showCropViewIfNeed();
        }
    }

    @Override
    public void onDestroy() {
        if (mView != null) {
            mView = null;
            mModel.onDestroy();
            mModel = null;
            mAdapterListener = null;
        }
    }
}
