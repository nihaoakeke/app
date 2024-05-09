package com.novel.myapplication.bean

import org.xutils.db.annotation.Column
import org.xutils.db.annotation.Table
import java.io.Serializable

@Table(name = "bookbean")
class BookBean  : Serializable {
    @Column(name = "id", isId = true, autoGen = false) // isId是否主键，autoGen是否自增长

    public var id: String? = null

    @Column(name = "bookName")
    public var bookName: String? = null

    @Column(name = "picture")
    public var picture: String? = null

    @Column(name = "author")
    public var author: String? = null

    @Column(name = "time")
    public var time: String? = null

    @Column(name = "book_type") //书籍类型

    public var book_type: String? = null

    @Column(name = "collect") //收藏

    public var collect: String? = null


    @Column(name = "count") //阅读次数

    public var count: Int? = 0
    @Column(name = "last") //阅读

    public var last: Int? = 0

}