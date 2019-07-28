package utils;

import java.sql.*;

/**
 * @package:utils
 * @Description:
 * @author: JoeSion
 * @date: 2019/7/27 14:36
 */
public abstract class BaseUtils {
        private  String driver="com.mysql.jdbc.Driver";
        private  String url="jdbc:mysql://localhost:3306/study?useUnicode=true&characterEncoding=utf8";
        private  String userName="root";
        private  String userPwd="root";
        //用于接收获取连接对象的变量
       protected ResultSet rs=null;
       protected PreparedStatement ps=null;
       protected  Connection conn=null;
    /**
     * 获取连接
     */
    private void getConnection(){
        try {
            //1.加载驱动
            Class.forName(driver);
            //2.获取连接
            conn= DriverManager.getConnection(url,userName,userPwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     * 查询方法
     * @param sql  查询的sql语句
     * @param objs 给sql语句占位符赋值的参数
     * @return 查询到的结果集
     */
    protected ResultSet queryByWhere(String sql,Object[] objs){

        getConnection();
        try {
            ps=conn.prepareStatement(sql);

            if (objs!=null){
                int size=objs.length;
                for (int i = 0; i <size; i++) {
                    ps.setObject(i+1,objs[i]);
                }
            }
            rs= ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 增加删除修改的方法
     * @param sql 增加或者删除或者修改的sql语句
     * @param objs 给sql语句占位符赋值的参数
     * @return 受影响的行数
     */
    protected int addOrDelOrUpdate(String sql,Object[] objs){
        int num=0;
        getConnection();
        try {
            ps=conn.prepareStatement(sql);
            if (objs!=null){
                int size=objs.length;
                for (int i = 0; i <size; i++) {
                    ps.setObject(i+1,objs[i]);
                }
            }
            num= ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeALL();
        }
        return  num;
    }

    /**
     * 释放资源的方法
     */
    protected  void closeALL(){
        if (rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
