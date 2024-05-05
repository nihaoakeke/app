package com.novel.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.novel.myapplication.R
import com.novel.myapplication.activity.BookDetailsActivity
import com.novel.myapplication.activity.PublishActivity
import com.novel.myapplication.bean.BookBean
import com.novel.myapplication.utils.SharePrefUtils

class BookListAdapter (content:Context,private val dataSet: MutableList<BookBean>) :
    RecyclerView.Adapter<BookListAdapter.ViewHolder>() {
        var content:Context = content

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val time: TextView = view.findViewById(R.id.time)
        val btn: TextView = view.findViewById(R.id.btn_status)
        val linearLayout: RelativeLayout = view.findViewById(R.id.ll)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSet.get(position).bookName
        holder.author.text = "作者："+dataSet.get(position).author
        holder.time.text = "时间："+dataSet.get(position).time
        if (SharePrefUtils.getLoginType(content).equals(content.getString(R.string.user_type_reader))){
                holder.btn.text ="收藏"
        }else{
            holder.btn.text ="编辑"
        }
        holder.linearLayout.setOnClickListener{
            var intent = Intent(content,BookDetailsActivity::class.java)
            intent.putExtra("id",dataSet.get(position).bookName)
             content.startActivity(intent)
        }
        holder.btn.setOnClickListener{

            if (SharePrefUtils.getLoginType(content).equals(content.getString(R.string.user_type_reader))){

            }else{
                var intent = Intent(content,PublishActivity::class.java)
                intent.putExtra("id",dataSet.get(position).bookName)
                content.startActivity(intent)
            }

        }
    }

    override fun getItemCount() = dataSet.size
}
