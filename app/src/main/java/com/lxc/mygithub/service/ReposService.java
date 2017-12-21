package com.lxc.mygithub.service;

import com.lxc.mygithub.model.Repos;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by LaiXiancheng on 2017/12/20.
 * Email: lxc.sysu@qq.com
 */

public interface ReposService {
	@GET("/users/{user}/repos")
	Observable<List<Repos>> getReposList(@Path("user") String user);
}
