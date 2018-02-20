package com.rammstein.imagepickertest.view;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.List;

/**
 * Created by klitaviy on 4/11/17.
 */

public interface DeviceImagesView {

    void setupFoldersSpinner(@NonNull final List<String> folders);

    void setupImagesList(@NonNull final List<File> images,
                         @NonNull final DeviceImagesAdapter.DeviceImageAdapterListener listener);

    void setImage(final File image);

    void refreshImagesList();

    void showCropViewIfNeed();
}
