package com.rammstein.imagepickertest.model;

import android.support.annotation.NonNull;

import com.rammstein.imagepickertest.OnDestroyListener;
import com.rammstein.imagepickertest.SimpleActionListener;

import java.io.File;
import java.util.List;

/**
 * Created by klitaviy on 4/11/17.
 */

public interface DeviceImagesModel extends OnDestroyListener {

    // TODO: 4/11/17 Listener
    void init(@NonNull final File destination,
              @NonNull final SimpleActionListener listener);

    boolean isReady();

    File imageByPosition(final int position);

    List<File> filesByPosition(final int position);

    List<String> folders();
}
