package com.blackspider.rxdemo;
/*
 *  ****************************************************************************
 *  * Created by : Arhan Ashik on 7/10/2018 at 3:29 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Arhan Ashik on 7/10/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.util.Log;

import io.reactivex.observers.DisposableObserver;

public class RxObservers {
    private String TAG = RxObservers.class.getSimpleName();

    public DisposableObserver<Note> getNotesObserver() {
        return new DisposableObserver<Note>() {

            @Override
            public void onNext(Note note) {
                Log.d(TAG, "Note: " + note.getNote());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All notes are emitted!");
            }
        };
    }

    public DisposableObserver<String> getAnimalsObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    public DisposableObserver<String> getAnimalsAllCapsObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }
}
