package com.ordolabs.asciiArt.base;

/**
 * Created by ordogod on 10.05.19.
 **/

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 *  Basic superinterface for any '...MvpView' interface.
 */
public interface BaseMvpView {

    /**
     * Returns Intent which could be used to start implementing Activity.
     * @param fromContext Context of calling Activity.
     * @return Intent.
     */
    Intent getStartIntent(@NonNull Context fromContext);
}
