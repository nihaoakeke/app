package com.novel.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.novel.myapplication.R
import com.novel.myapplication.activity.BookDetailsActivity
import com.novel.myapplication.activity.PublishActivity
import com.novel.myapplication.bean.HomeItem
import com.novel.myapplication.utils.SharePrefUtils

class HomeItemAdapter(content:Context, private val dataSet: MutableList<HomeItem>) :
    RecyclerView.Adapter<HomeItemAdapter.ViewHolder>() {
        var content:Context = content

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.name)
        val img: ImageView = view.findViewById(R.id.img)
        val linearLayout: RelativeLayout = view.findViewById(R.id.ll)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_new, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSet.get(position).name

        if (dataSet.get(position).name!!.equals("学校信息")){
            holder.img.setImageResource(R.mipmap.xuexiao)
        }
        else if (dataSet.get(position).name!!.equals("最新消息")){
            holder.img.setImageResource(R.mipmap.xx)
        }
        else if (dataSet.get(position).name!!.equals("抽样调查")){
            holder.img.setImageResource(R.mipmap.cy)
        }
        else if (dataSet.get(position).name!!.equals("学生菜谱")){
            holder.img.setImageResource(R.mipmap.cd)
        }
        else if (dataSet.get(position).name!!.equals("营养搭配")){
            holder.img.setImageResource(R.mipmap.basket)
        }
        else if (dataSet.get(position).name!!.equals("卫生检查")){
            holder.img.setImageResource(R.mipmap.ws)
        }
        else if (dataSet.get(position).name!!.equals("投诉统计")){
            holder.img.setImageResource(R.mipmap.ws)
        }

//        // 加载图片并显示
//        val bitmap = BitmapFactory.decodeFile(dataSet.get(position).icon)
//        holder.img.setImageBitmap(bitmap)
        holder.linearLayout.setOnClickListener{
            var intent = Intent(content,BookDetailsActivity::class.java)
            intent.putExtra("id",dataSet.get(position).name)
             content.startActivity(intent)
        }

    }

    override fun getItemCount() = dataSet.size
}
