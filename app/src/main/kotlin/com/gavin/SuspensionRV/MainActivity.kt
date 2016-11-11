package com.gavin.SuspensionRV

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.gavin.SuspensionRV.adapter.FeedAdapter
import com.gavin.SuspensionRV.bean.ContentBean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import java.util.*

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2016-11-08
 * Time: 10:48
 */
class MainActivity : AppCompatActivity() {

    private var mSuspensionHeight = 0 // 顶部TitleBar的高度
    private var mCurrentPosition = 0 // 当前显示的RecyclerView的Item
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var rLayoutSuspensionBar: RelativeLayout? = null
    private var tvSuspensionNickName: TextView? = null
    private var ivSuspensionAvatar: ImageView? = null
    private var mContentList: ArrayList<ContentBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rLayoutSuspensionBar = find<RelativeLayout>(R.id.rLayoutSuspensionBar)
        tvSuspensionNickName = rLayoutSuspensionBar?.find<TextView>(R.id.tv_nickname)
        ivSuspensionAvatar = rLayoutSuspensionBar?.find<ImageView>(R.id.iv_avatar)
        mLinearLayoutManager = LinearLayoutManager(this@MainActivity)
        mLinearLayoutManager?.orientation = LinearLayoutManager.VERTICAL
        rvFeed.layoutManager = mLinearLayoutManager

        for (i in 0..19) {
            val _bean = ContentBean(getNickName(i), getAvatarResId(i), getContentResId(i))
            mContentList.add(_bean)
        }

        val _adapter = FeedAdapter(mContentList)
        rvFeed.adapter = _adapter
        rvFeed.setHasFixedSize(true)

        rvFeed.addOnScrollListener(CustomOnScrollListener())
    }

    private inner class CustomOnScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            // 找到列表第二个可见的View
            val _view = mLinearLayoutManager?.findViewByPosition(mCurrentPosition + 1) ?: return
            if (_view.top <= mSuspensionHeight) {
                rLayoutSuspensionBar?.y = (-(mSuspensionHeight - _view.top)).toFloat()
            } else {
                rLayoutSuspensionBar?.y = 0.toFloat()
            }

            if (mCurrentPosition != mLinearLayoutManager?.findFirstVisibleItemPosition()) {
                mCurrentPosition = mLinearLayoutManager?.findFirstVisibleItemPosition()!!
                rLayoutSuspensionBar?.y = 0.toFloat()

                updateSuspensionBar(mContentList[mCurrentPosition])
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            mSuspensionHeight = rLayoutSuspensionBar!!.height
        }
    }

    private fun updateSuspensionBar(bean: ContentBean) {
        Picasso.with(this@MainActivity)
                .load(bean.avatar)
                .centerInside()
                .fit()
                .into(ivSuspensionAvatar)

        tvSuspensionNickName!!.text = bean.nickname
    }


    private fun getAvatarResId(position: Int): Int {
        when (position % 4) {
            0 -> return R.drawable.avatar1
            1 -> return R.drawable.avatar2
            2 -> return R.drawable.avatar3
            3 -> return R.drawable.avatar4
        }
        return 0
    }

    private fun getContentResId(position: Int): Int {
        when (position % 4) {
            0 -> return R.drawable.taeyeon_one
            1 -> return R.drawable.taeyeon_two
            2 -> return R.drawable.taeyeon_three
            3 -> return R.drawable.taeyeon_four
        }
        return 0
    }

    private fun getNickName(position: Int): String {
        when (((position * System.currentTimeMillis()) % 4).toInt()) {
            0 -> return "Andy"
            1 -> return "Luff"
            2 -> return "Frank"
            3 -> return "Suspension"
        }
        return "Null"
    }
}