package com.gavin.SuspensionRV.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.gavin.SuspensionRV.R
import com.gavin.SuspensionRV.bean.ContentBean
import com.gavin.SuspensionRV.widget.CircleImageView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import java.util.*

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2016-11-08
 * Time: 11:39
 */
class FeedAdapter(var contentList:ArrayList<ContentBean>) : RecyclerView.Adapter<FeedAdapter.FeedHolder>() {
    override fun getItemCount(): Int {
        return contentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedHolder {
        val _itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_feed, parent, false);
        return FeedHolder(_itemView)
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        val _bean = contentList[position]
        Picasso.with(holder.itemView.context)
                .load(_bean.avatar)
                .centerInside()
                .fit()
                .into(holder.ivAvatar)

        Picasso.with(holder.itemView.context)
                .load(_bean.content)
                .centerInside()
                .fit()
                .into(holder.ivContent)

        holder.tvNickName!!.text = _bean.nickname
    }

    inner class FeedHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar = itemView?.find<CircleImageView>(R.id.iv_avatar)
        val ivContent = itemView?.find<ImageView>(R.id.iv_content)
        val tvNickName = itemView?.find<TextView>(R.id.tv_nickname)
    }

}