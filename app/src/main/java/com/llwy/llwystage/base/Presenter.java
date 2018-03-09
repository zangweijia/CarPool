package com.llwy.llwystage.base;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
