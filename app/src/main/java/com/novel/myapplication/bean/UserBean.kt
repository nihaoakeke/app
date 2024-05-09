package com.novel.myapplication.bean

import org.xutils.db.annotation.Column
import org.xutils.db.annotation.Table
import java.io.Serializable

@Table(name = "user")
class UserBean  : Serializable {
    @Column(name = "id", isId = true, autoGen = false) // isId是否主键，autoGen是否自增长

    public var id: String? = null

    @Column(name = "name")
    public var name: String? = null

    @Column(name = "phone")
    public var phone: String? = null

    @Column(name = "pwd")
    public var pwd: String? = null

    @Column(name = "address")
    public var address: String? = null

    @Column(name = "type") //管理员是1

    public var type: String? = null

    @Column(name = "birthday")
    public var birthday: String? = null

    @Column(name = "last_time")
    public var last_time: String? = null

    @Column(name = "is_read")
    public var is_read: String? = null

    @Column(name = "user_id") //1 2 3 4

    public var user_id: String? = null


    @Column(name = "Num")
    public var stuNum: String? = null //

    @Column(name = "introduce")
    public var introduce: String? = null //

    

}