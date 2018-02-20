package com.rammstein.imagepickertest.model;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.rammstein.imagepickertest.SimpleActionListener;
import com.rammstein.imagepickertest.data.DeviceImagesDataProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by klitaviy on 4/11/17.
 */

public class DeviceImagesModelImpl implements DeviceImagesModel {

    private DeviceImagesDataProvider mDataProvider;

    private ArrayMap<String, List<File>> mData;
    private List<File> mCurrentList;
    private List<String> mFolders;


    public DeviceImagesModelImpl(@NonNull final DeviceImagesDataProvider dataProvider) {
        mDataProvider = dataProvider;
    }

    @Override
    public void init(@NonNull final File destination,
                     @NonNull final SimpleActionListener listener) {
        mData = mDataProvider.getData(destination);
        mFolders = new ArrayList<>(mData.keySet());
        mCurrentList = new ArrayList<>();
        listener.onSuccess();
    }

    @Override
    public boolean isReady() {
        return mCurrentList != null;
    }

    @Override
    public File imageByPosition(final int position) {
        return mCurrentList.get(position);
    }

    @Override
    public List<File> filesByPosition(final int position) {
        mCurrentList.clear();
        mCurrentList.addAll(mData.get(
                mFolders.get(position)
        ));
        return mCurrentList;
    }

    @Override
    public List<String> folders() {
        return mFolders;
    }

    @Override
    public void onDestroy() {
        if (mDataProvider != null) {
            mDataProvider.onDestroy();
            mDataProvider = null;
            mData = null;
            mCurrentList = null;
            mFolders = null;
        }
    }
}
