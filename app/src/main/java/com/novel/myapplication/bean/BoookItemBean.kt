package com.novel.myapplication.bean

import org.xutils.db.annotation.Column
import org.xutils.db.annotation.Table
import java.io.Serializable

@Table(name = "bookItemBean")
class BoookItemBean  : Serializable {
    @Column(name = "id", isId = true, autoGen = false) // isId是否主键，autoGen是否自增长

    public var id: String? = null

    @Column(name = "book_name")
    public var bookName: String? = null

    @Column(name = "chapter")
    public var chapter: String? = null

    @Column(name = "content")
    public var content: String? = null

    @Column(name = "author")
    public var author: String? = null

    @Column(name = "time")
    public var time: String? = null

    @Column(name = "book_type") //书籍类型

    public var book_type: String? = null

    @Column(name = "page") //书籍类型

    public var page: String? = null



    
    

}