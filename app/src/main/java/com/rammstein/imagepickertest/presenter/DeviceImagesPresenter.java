package com.rammstein.imagepickertest.presenter;

import com.rammstein.imagepickertest.OnDestroyListener;

/**
 * Created by klitaviy on 4/11/17.
 */

public interface DeviceImagesPresenter extends OnDestroyListener {

    void init();

    void onViewReady();

    void onFolderSelect(final int position);
}
