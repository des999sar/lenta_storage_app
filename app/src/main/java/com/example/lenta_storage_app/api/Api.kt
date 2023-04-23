package com.example.lenta_storage_app.api
import android.os.StrictMode
import com.example.lenta_storage_app.model.tables.Storages
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import java.sql.*
import java.sql.DriverManager.println

class Api {

    //для удаленного подключения
    internal var username = "freedb_lenta_storage_admin"
    internal var password = "?PReRs@7fvwb!V7"
    internal var hostName = "sql.freedb.tech"
    internal var port = "3306"
    internal var databaseName = "freedb_lenta_storage"

    //для локального подключения
/*    internal var username = "root"
    internal var password = "root"
    internal var hostName = "10.0.2.2"
    internal var port = "3306"
    internal var databaseName = "lenta_storage"*/

    fun getConnection() : Database {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        //для удаленного подключения
        val url = "jdbc:mysql://" + hostName + ":" + port + "/" + databaseName + "?useSSL=false"
        //для локального подключения
        //val url = "jdbc:mysql://" + hostName + "/" + databaseName + "?serverTimezone=UTC"
        val database = Database.connect(
            url,
            user = username,
            password = password
        )

        return database
    }
}