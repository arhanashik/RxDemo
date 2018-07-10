package com.blackspider.rxdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    // observable
    Observable<String> animalsObservable;
    Observable<Note> notesObservable;
    // observer
    DisposableObserver<String> animalsObserver;
    DisposableObserver<String> animalsObserverAllCaps;
    DisposableObserver<Note> notesObserver;
    // disposable
    Disposable disposable;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        animalsObservable = new RxObservables().getAnimalsObservable();
        notesObservable = new RxObservables().getNotesObservable();

        animalsObserver = new RxObservers().getAnimalsObserver();
        animalsObserverAllCaps = new RxObservers().getAnimalsAllCapsObserver();
        notesObserver = new RxObservers().getNotesObserver();

        compositeDisposable = new CompositeDisposable();

        /**
         * filter() is used to filter out the animal names starting with `b`
         * */
        compositeDisposable.add(
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return s.toLowerCase().startsWith("b");
                            }
                        })
                        .subscribeWith(animalsObserver));

        /**
         * filter() is used to filter out the animal names starting with 'c'
         * map() is used to transform all the characters to UPPER case
         * */
        compositeDisposable.add(
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return s.toLowerCase().startsWith("c");
                            }
                        })
                        .map(new Function<String, String>() {
                            @Override
                            public String apply(String s) throws Exception {
                                return s.toUpperCase();
                            }
                        })
                        .subscribeWith(animalsObserverAllCaps));

        // add to Composite observable
        // .map() operator is used to turn the note into all uppercase letters
        compositeDisposable.add(notesObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Note, Note>() {
                    @Override
                    public Note apply(Note note) throws Exception {
                        // Making the note to all uppercase
                        note.setNote(note.getNote().toUpperCase());
                        return note;
                    }
                })
                .subscribeWith(notesObserver));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // don't send events once the activity is destroyed
        compositeDisposable.clear();
    }

    private List<Note> modifiedNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1, "don't buy tooth paste!"));
        notes.add(new Note(2, "don't call brother! call your grandfather. ha ha ha"));
        notes.add(new Note(3, "don't watch narcos tonight! what's about game of thrones"));
        notes.add(new Note(4, "pay power bill! Or, punishment is waiting for you."));

        return notes;
    }
}
