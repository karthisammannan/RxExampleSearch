package com.example.rxexamplesearch;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.rxexamplesearch.Data.ApiClient;
import com.example.rxexamplesearch.Data.ApiService;
import com.example.rxexamplesearch.POJO.Result;
import com.example.rxexamplesearch.POJO.User;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycler_user_list)
    RecyclerView rvw_user_list;
    @BindView(R.id.spin_kit)
    SpinKitView mProgressBar;
    private UsersAdapterFilterable mAdapter;
    private List<Result> usersList = new ArrayList<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiService apiService;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);


        mAdapter = new UsersAdapterFilterable(this, usersList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvw_user_list.setLayoutManager(mLayoutManager);
        rvw_user_list.setItemAnimator(new DefaultItemAnimator());
        rvw_user_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvw_user_list.setAdapter(mAdapter);
        apiService = ApiClient.getClient().create(ApiService.class);
        fetchUsers();
    }

    private void fetchUsers() {
        disposable.add(apiService
                .getUsers("50")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User contacts) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        rvw_user_list.setVisibility(View.VISIBLE);
                        usersList.clear();
                        usersList.addAll(contacts.getResults());
                        mAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }
    @Override
    protected void onDestroy() {
        disposable.clear();
        unbinder.unbind();
        super.onDestroy();
    }
}
