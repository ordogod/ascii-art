package com.ordolabs.asciiArt.base;

/**
 * Created by ordogod on 12.05.19.
 **/

/**
 * Base class that implements the Model interface.
 */
public abstract class BaseModel<P extends BasePresenter> implements BaseMvpModel<P> {

    protected P mvpPresenter;

    @Override
    public void attachPresenter(P mvpPresenter) {
        this.mvpPresenter = mvpPresenter;
    }

    @Override
    public void detachPresenter() {
        this.mvpPresenter = null;
    }

    @Override
    public boolean isPresenterAttached() {
        return this.mvpPresenter != null;
    }

    @Override
    public P getPresenter() {
        if (isPresenterAttached() == false) throw new MvpPresenterNotAttachedException();
        else return this.mvpPresenter;
    }

    public static class MvpPresenterNotAttachedException extends RuntimeException {
        MvpPresenterNotAttachedException() {
            super("Please call \'Model.attachView(MvpPresenter)\' before requestion data from the Model.");
        }
    }
}
