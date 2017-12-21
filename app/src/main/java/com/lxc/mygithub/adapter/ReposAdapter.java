package com.lxc.mygithub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxc.mygithub.R;
import com.lxc.mygithub.listener.recyItemClickListener;
import com.lxc.mygithub.model.Repos;

import java.util.List;

/**
 * Created by LaiXiancheng on 2017/12/20.
 * Email: lxc.sysu@qq.com
 */

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.UsersVH> {

	private List<Repos> reposList;//列表对应的数据
	private Context context;
	private recyItemClickListener itemClickListener;//自定义的接口

	public ReposAdapter(Context context, List<Repos> reposList, recyItemClickListener itemClickListener) {
		this.reposList = reposList;
		this.context = context;
		this.itemClickListener = itemClickListener;
	}

	@Override
	public UsersVH onCreateViewHolder(ViewGroup parent, int viewType) {
		//直接在此处将布局文件inflate成View
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_repos_item, parent, false);
		return new UsersVH(view);
	}

	@Override
	public void onBindViewHolder(final UsersVH holder, int position) {
		//直接在这里设置数据
		Repos repos = reposList.get(position);
		String name = repos.getName();
		String language = repos.getLanguage();
		String des = repos.getDescription();
		holder.tvName.setText(name);
		holder.tvLanguage.setText(language);
		holder.tvDes.setText(des);

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
		return reposList.size();
	}

	class UsersVH extends RecyclerView.ViewHolder{
		TextView tvName;
		TextView tvLanguage;
		TextView tvDes;
		UsersVH(View view) {
			super(view);
			tvName = view.findViewById(R.id.tv_name);
			tvLanguage = view.findViewById(R.id.tv_language);
			tvDes = view.findViewById(R.id.tv_description);
		}
	}

}