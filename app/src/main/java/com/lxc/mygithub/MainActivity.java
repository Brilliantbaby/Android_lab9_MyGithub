package com.lxc.mygithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lxc.mygithub.adapter.UserAdapter;
import com.lxc.mygithub.listener.recyItemClickListener;
import com.lxc.mygithub.model.User;
import com.lxc.mygithub.service.ServicePool;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

	RecyclerView recyclerView;
	Button btFetch;
	Button btClear;
	EditText etInput;
	ProgressBar pbWait;
	ArrayList<User> userList = new ArrayList<>();
	UserAdapter userAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pbWait = findViewById(R.id.pb_wait);
		etInput = findViewById(R.id.et_input);
		btFetch = findViewById(R.id.bt_fetch);
		btFetch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pbWait.setVisibility(View.VISIBLE);
				String user_name = etInput.getText().toString();
				ServicePool
						.getUserService()
						.getUser(user_name)
						.subscribeOn(Schedulers.newThread())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Subscriber<User>() {
							@Override
							public void onCompleted() {
								pbWait.setVisibility(View.GONE);
							}

							@Override
							public void onError(Throwable e) {
								pbWait.setVisibility(View.GONE);
								Toast.makeText(MainActivity.this, "请确认搜索的用户存在",Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onNext(User user) {
								userList.add(user);
								userAdapter.notifyDataSetChanged();
							}
						});
			}
		});
		btClear = findViewById(R.id.bt_clear);
		btClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				etInput.setText("");
			}
		});
		recyclerView = findViewById(R.id.rc_items);
		recyItemClickListener itemClickListener = new recyItemClickListener() {
			@Override
			public void onClick(int position) {
				Intent i = new Intent(MainActivity.this, ReposActivity.class);
				i.putExtra("USER", userList.get(position).getLogin());
				startActivity(i);
			}
			@Override
			public void onLongClick(int position) {
				userList.remove(position);
				userAdapter.notifyDataSetChanged();
			}
		};
		userAdapter = new UserAdapter(this, userList, itemClickListener);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(userAdapter);
	}

}
