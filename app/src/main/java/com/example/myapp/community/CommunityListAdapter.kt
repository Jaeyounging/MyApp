package com.example.myapp.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapp.R
import kotlinx.coroutines.NonDisposableHandle.parent

class CommunityListAdapter(val communityList : MutableList<CommunityModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return communityList.size
    }

    override fun getItem(position: Int): Any {
        return communityList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.community_list_item, parent, false)
        }

        // 제목, 내용 시간 가져오기
        val title = view?.findViewById<TextView>(R.id.titleArea)
        val content = view?.findViewById<TextView>(R.id.contentArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)

        title!!.text = communityList[position].title
        content!!.text = communityList[position].content
        time!!.text = communityList[position].time

        return view!!
    }
}