package com.novel.myapplication.db

import org.xutils.DbManager
import org.xutils.DbManager.DaoConfig
import org.xutils.db.table.TableEntity
import org.xutils.x

  object  DatabaseOpenHelper {
    private val TAG = DatabaseOpenHelper::class.java.simpleName
    private var daoConfig: DaoConfig? = null
    private var dbManager: DbManager? = null
    private val DB_NAME = "novel.db"
    private val DB_VERSION = 1

    private fun DatabaseOpenHelper() {
        daoConfig = DaoConfig()
            .setDbName(DB_NAME)
            .setDbVersion(DB_VERSION)
            .setDbOpenListener { db: DbManager ->
                db.database.enableWriteAheadLogging()
            }
            .setDbUpgradeListener { db: DbManager?, oldVersion: Int, newVersion: Int -> }
            .setTableCreateListener { db: DbManager?, table: TableEntity<*>? -> }
        dbManager = x.getDb(daoConfig)
    }

    fun getInstance(): DbManager? {
        if (dbManager == null) {
            val databaseOpenHelper = DatabaseOpenHelper()
        }
        return dbManager
    }
}