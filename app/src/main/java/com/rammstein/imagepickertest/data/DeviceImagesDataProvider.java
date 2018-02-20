package com.rammstein.imagepickertest.data;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.rammstein.imagepickertest.OnDestroyListener;

import java.io.File;
import java.util.List;

/**
 * Created by klitaviy on 4/11/17.
 */

public interface DeviceImagesDataProvider extends OnDestroyListener {

    ArrayMap<String, List<File>> getData(@NonNull final File destination);
}
