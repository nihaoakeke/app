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
import com.novel.myapplication.bean.BookBean
import com.novel.myapplication.dao.BookBeanDao
import com.novel.myapplication.utils.SharePrefUtils

class BookCreateListAdapter(content:Context, private val dataSet: MutableList<BookBean>) :
    RecyclerView.Adapter<BookCreateListAdapter.ViewHolder>() {
        var content:Context = content
    var bookBeanDao= BookBeanDao()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val time: TextView = view.findViewById(R.id.time)
        val btn: TextView = view.findViewById(R.id.btn_status)
        val img: ImageView = view.findViewById(R.id.img)
        val linearLayout: RelativeLayout = view.findViewById(R.id.ll)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mycraete, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSet.get(position).bookName
        holder.author.text = "作者："+dataSet.get(position).author
        holder.time.text = "创作时间："+dataSet.get(position).time

        // 加载图片并显示
        val bitmap = BitmapFactory.decodeFile(dataSet.get(position).picture)
        holder.img.setImageBitmap(bitmap)
        if (SharePrefUtils.getLoginType(content).equals(content.getString(R.string.user_type_reader))){
            if (dataSet.get(position).collect.equals(SharePrefUtils.getName(content))){
                holder.btn.text = "已收藏"
            }else {
                holder.btn.text = "收藏"
            }
        }else{
            holder.btn.text ="编辑"
        }
        holder.linearLayout.setOnClickListener{
            var intent = Intent(content,BookDetailsActivity::class.java)
            intent.putExtra("id",dataSet.get(position).bookName)
             content.startActivity(intent)
        }
        holder.btn.visibility= View.GONE
        holder.btn.setOnClickListener{

            if (SharePrefUtils.getLoginType(content).equals(content.getString(R.string.user_type_reader))){
                dataSet.get(position).collect =SharePrefUtils.getName(content)
                bookBeanDao.addOrUpdate(dataSet.get(position))

            }else{
                var intent = Intent(content,PublishActivity::class.java)
                intent.putExtra("id",dataSet.get(position).bookName)
                content.startActivity(intent)
            }

        }
    }

    override fun getItemCount() = dataSet.size
}
