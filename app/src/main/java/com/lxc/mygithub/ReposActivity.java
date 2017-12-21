package com.lxc.mygithub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lxc.mygithub.adapter.ReposAdapter;
import com.lxc.mygithub.listener.recyItemClickListener;
import com.lxc.mygithub.model.Repos;
import com.lxc.mygithub.service.ServicePool;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReposActivity extends AppCompatActivity {

	RecyclerView recyclerView;
	Button btFetch;
	Button btClear;
	EditText etInput;
	ProgressBar pbWait;
	ArrayList<Repos> reposList = new ArrayList<>();
	ReposAdapter reposAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repos);
		String userName = getIntent().getStringExtra("USER");
		pbWait = findViewById(R.id.pb_wait);

		recyclerView = findViewById(R.id.rc_items);
		recyItemClickListener itemClickListener = new recyItemClickListener() {
			@Override
			public void onClick(int position) {
			}
			@Override
			public void onLongClick(int position) {
			}
		};
		reposAdapter = new ReposAdapter(this, reposList, itemClickListener);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(reposAdapter);
		ServicePool
				.getReposService()
				.getReposList(userName)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<List<Repos>>() {
					@Override
					public void onCompleted() {
						pbWait.setVisibility(View.GONE);
					}

					@Override
					public void onError(Throwable e) {
						pbWait.setVisibility(View.GONE);
						Toast.makeText(ReposActivity.this, "获取出错",Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onNext(List<Repos> repos) {
						reposList.addAll(repos);
						reposAdapter.notifyDataSetChanged();
					}
				});
	}
}
