package com.rammstein.imagepickertest.data;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by klitaviy on 4/10/17.
 */

public class DeviceImagesDataProviderImpl implements DeviceImagesDataProvider {

    private ArrayMap<String, List<File>> mData;

    public DeviceImagesDataProviderImpl() {
        mData = new ArrayMap<>();
    }

    @Override
    @NonNull
    public ArrayMap<String, List<File>> getData(@NonNull final File destination) {
        // TODO: 4/11/17 Add RX
        search(destination);
        return mData;
    }

    private void search(@NonNull final File dir) {
        final File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (final File file : files) {
                if (shouldSearchHere(file)) {
                    search(file);
                } else if (isFileImage(file)) {
                    final String path = getShortPath(file);
                    if (shouldBeAdded(path)) {
                        addToList(dir);
                    }
                }
            }
        }
    }

    private boolean shouldSearchHere(@NonNull final File file) {
        return file.isDirectory() && !file.isHidden() && !ignored(file);
    }

    private void addToList(final File dir) {
        final File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            final List<File> images = new ArrayList<>();
            for (final File file : files) {
                if (isFileImage(file) && !file.isHidden()) {
                    images.add(file);
                }
            }
            if (images.size() > 0) {
                mData.put(
                        getParentShotPath(dir),
                        images
                );
            }
        }
    }

    private boolean isFileImage(@NonNull final File file) {
        final String name = file.getName();
        return name.endsWith(".png") ||
                name.endsWith(".jpg") ||
                name.endsWith(".jpeg");
    }

    private String getParentShotPath(@NonNull final File file) {
        return file.getParent().substring(file.getParent().lastIndexOf('/'));
    }

    private String getShortPath(@NonNull final File dir) {
        return dir.getPath().substring(dir.getPath().lastIndexOf('/'));
    }

    private boolean shouldBeAdded(@NonNull final String path) {
        return mData.get(path) == null;
    }

    private boolean ignored(@NonNull final File file) {
        final String path = file.getPath();
        final String folder = path.substring(path.lastIndexOf('/') + 1).toLowerCase();
        Log.d("files_debug", "ignored: " + folder);
        return folder.equals("android") || folder.equals("emulated");
    }

    @Override
    public void onDestroy() {
        if (mData != null) {
            mData.clear();
            mData = null;
        }
    }
}
