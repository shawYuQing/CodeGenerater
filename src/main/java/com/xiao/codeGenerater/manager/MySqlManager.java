package com.xiao.codeGenerater.manager;

import com.xiao.codeGenerater.config.BuildConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by xiaoyq on 2019/2/27.
 */
public class MySqlManager {

    /**
     * 获取表创建sql
     * @param conficg
     * @return
     * @throws Exception
     */
    public static String getTableCreateSql(BuildConfig conficg) throws Exception {
        StringBuilder url = new StringBuilder("jdbc:mysql://");
        url.append(conficg.getDriveHost()).append(":").append(conficg.getDrivePort()).append("/")
                .append(conficg.getDriveDatabase()).append("?");
        url.append("user=").append(conficg.getDriveUsername());
        url.append("&password=").append(conficg.getDrivePassword());
        url.append("&useUnicode=true");
        url.append("&characterEncoding=UTF8");

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url.toString());
        Statement stmt = conn.createStatement();
        String sql = "show create table " + conficg.getDriveTable();
        ResultSet rs = stmt.executeQuery(sql);

        String createSql = null;
        while (rs.next()) {
            createSql = rs.getString(2);
        }

        conn.close();

        return createSql;
    }

}
