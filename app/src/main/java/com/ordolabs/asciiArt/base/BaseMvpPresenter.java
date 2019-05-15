package com.ordolabs.asciiArt.base;

/**
 * Created by ordogod on 10.05.19.
 **/

/**
 * Basic interface for any '...Presenter' or superinterface for any '...MvpPresenter' interface.
 *
 * @param <V> Represents attached mvpView.
 */
public interface BaseMvpPresenter<V extends BaseMvpView> {

    /**
     * Initializes Views of attached 'mvpView'
     */
    void initLayoutViews();

    /**
     * Attaches given mvpView to this '...Presenter'.
     *
     * @param mvpView Storing mvpView to be attached.
     */
    void attachView(V mvpView);

    /**
     * Detaches current attached mvpView.
     */
    void detachView();

    /**
     * Checks if mvpView is attached.
     *
     * @return True if current mvpView is not null.
     */
    boolean isViewAttached();

    /**
     * Returns current attached mvpView.
     *
     * @return this.mvpView.
     */
    V getMvpView();

    /**
     * Sets activity toolbar.
     *
     * @param title The title string to be placed in the toolbar.
     */
    void setToolbar(String title);
}

