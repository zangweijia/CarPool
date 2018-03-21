package com.llwy.carpool.base;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
