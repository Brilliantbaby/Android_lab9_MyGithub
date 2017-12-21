package com.lxc.mygithub.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LaiXiancheng on 2017/12/20.
 * Email: lxc.sysu@qq.com
 */

public class ServicePool {
	static String BASE_URL = "https://api.github.com";

	static public UserService getUserService(){
		Retrofit retrofit =
				new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
		return retrofit.create(UserService.class);
	}

	static public ReposService getReposService(){
		Retrofit retrofit =
				new Retrofit.Builder()
						.baseUrl(BASE_URL)
						.addConverterFactory(GsonConverterFactory.create())
						.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
						.build();
		return retrofit.create(ReposService.class);
	}
}
