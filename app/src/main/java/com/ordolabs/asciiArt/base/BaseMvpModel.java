package com.ordolabs.asciiArt.base;

/**
 * Created by ordogod on 12.05.19.
 **/

/**
 * Basic interface for any '...Model' or superinterface for any '...MvpModel' interface.
 *
 * @param <P> Represents attached mvpPresenter.
 */
public interface BaseMvpModel<P extends BaseMvpPresenter> {

    /**
     * Attaches given mvpPresenter to this '...Model'.
     *
     * @param mvpPresenter Storing mvpPresenter to be attached.
     */
    void attachPresenter(P mvpPresenter);

    /**
     * Detaches current attached mvpPresenter.
     */
    void detachPresenter();

    /**
     * Checks if mvpPresenter is attached.
     *
     * @return True if current mvpPresenter is not null.
     */
    boolean isPresenterAttached();

    /**
     * Returns current attached mvpPresenter.
     *
     * @return this.mvpPresenter.
     */
    P getPresenter();
}
