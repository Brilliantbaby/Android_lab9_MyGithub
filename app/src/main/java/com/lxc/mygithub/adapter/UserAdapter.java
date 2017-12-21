package com.lxc.mygithub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxc.mygithub.R;
import com.lxc.mygithub.listener.recyItemClickListener;
import com.lxc.mygithub.model.User;

import java.util.List;

/**
 * Created by LaiXiancheng on 2017/12/20.
 * Email: lxc.sysu@qq.com
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsersVH> {

	private List<User> userList;//列表对应的数据
	private Context context;
	private recyItemClickListener itemClickListener;//自定义的接口

	public UserAdapter(Context context, List<User> userList, recyItemClickListener itemClickListener) {
		this.userList = userList;
		this.context = context;
		this.itemClickListener = itemClickListener;
	}

	@Override
	public UsersVH onCreateViewHolder(ViewGroup parent, int viewType) {
		//直接在此处将布局文件inflate成View
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user_item, parent, false);
		return new UsersVH(view);
	}

	@Override
	public void onBindViewHolder(final UsersVH holder, int position) {
		//直接在这里设置数据
		User user = userList.get(position);
		String login = user.getLogin();
		String id = "id:"+ user.getId();
		String blog = "blog:"+ (TextUtils.isEmpty(user.getBlog()) ? "空" : user.getBlog());
		String avatar_url = userList.get(position).getAvatar_url();
		holder.tvLogin.setText(login);
		holder.tvId.setText(id);
		holder.tvBlog.setText(blog);
		Glide.with(context).load(avatar_url).into(holder.avatar);

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				itemClickListener.onClick(holder.getAdapterPosition());
			}
		});
		holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				itemClickListener.onLongClick(holder.getAdapterPosition());
				return true;//消费长按事件
			}
		});
	}

	@Override
	public int getItemCount() {
		return userList.size();
	}

	class UsersVH extends RecyclerView.ViewHolder{
		TextView tvLogin;
		TextView tvId;
		TextView tvBlog;
		ImageView avatar;
		UsersVH(View view) {
			super(view);
			tvLogin = view.findViewById(R.id.tv_login);
			tvId = view.findViewById(R.id.tv_id);
			tvBlog = view.findViewById(R.id.tv_blog);
			avatar = view.findViewById(R.id.iv_avatar);
		}
	}

}