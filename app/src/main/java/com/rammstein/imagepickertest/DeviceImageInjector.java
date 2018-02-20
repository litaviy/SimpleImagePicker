package com.rammstein.imagepickertest;

import android.support.annotation.NonNull;

import com.rammstein.imagepickertest.data.DeviceImagesDataProvider;
import com.rammstein.imagepickertest.data.DeviceImagesDataProviderImpl;
import com.rammstein.imagepickertest.model.DeviceImagesModel;
import com.rammstein.imagepickertest.model.DeviceImagesModelImpl;
import com.rammstein.imagepickertest.presenter.DeviceImagesPresenter;
import com.rammstein.imagepickertest.presenter.DeviceImagesPresenterImpl;
import com.rammstein.imagepickertest.view.DeviceImagesView;

/**
 * Created by klitaviy on 4/11/17.
 */

public class DeviceImageInjector {

    @NonNull
    public static DeviceImagesPresenter inject(@NonNull final DeviceImagesView view) {
        return new DeviceImagesPresenterImpl(view, model());
    }

    @NonNull
    private static DeviceImagesModel model() {
        return new DeviceImagesModelImpl(dataProvider());
    }

    @NonNull
    private static DeviceImagesDataProvider dataProvider() {
        return new DeviceImagesDataProviderImpl();
    }
}
