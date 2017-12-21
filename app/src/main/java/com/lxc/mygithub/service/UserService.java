package com.lxc.mygithub.service;

import com.lxc.mygithub.model.User;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by LaiXiancheng on 2017/12/20.
 * Email: lxc.sysu@qq.com
 */

public interface UserService {
	@GET("/users/{user}")
	Observable<User> getUser(@Path("user") String user);
}
