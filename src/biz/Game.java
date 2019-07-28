package biz;

import pojo.Books;
import pojo.Users;
import utils.BaseUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @desc 图书管理系统游戏类
 * @author JoeSion
 *
 */
public class Game extends BaseUtils {
    /**
     * 菜单方法
     */
        public void menu() throws SQLException {
            System.out.println("欢迎来到图书管理系统");
            System.out.println("1.注册 2.登录");
            System.out.println("请选择:");
            Scanner input = new Scanner(System.in);
            if (input.hasNextInt()) {
                int num =input.nextInt();
                switch (num){
                    case 1:
                        zc();
                        break;
                    case 2:
                        login();
                        break;
                    default:
                        exits();
                        break;
                }
            }else{
                System.out.println("输入错误，请重新输入");
                menu();
            }
        }

    /**
     * 二级菜单
     */
    private   void menu2() throws SQLException {
            System.out.println("1 列表  2 删除  3 修改  4 添加  5 退出");
            System.out.println("请选择：");
            Scanner input = new Scanner(System.in);
            if (input.hasNextInt()) {
                int num =input.nextInt();
                switch (num){
                    case 1:
                        list();
                        break;
                    case 2:
                        del();
                        break;
                    case 3:
                        update();
                        break;
                    case 4:
                        add();
                        break;
                    case 5:
                        exits();
                        break;
                    default:
                        System.out.println("选择错误，请重选:");
                        menu2();
                       break;
                }
            }else{
                System.out.println("选择错误，请重选:");
                menu2();
            }
        }

    /**
     * 添加的方法
     */
    private void add() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入图书名称:");
        String name=input.next();
        System.out.println("请输入价格");
        double price=input.nextDouble();
        String sql="INSERT  INTO  books(bookName, price) VALUES (?,?);";
        Object[] objs={name,price};
        if (addOrDelOrUpdate(sql,objs)>0) {
            System.out.println("添加成功");
        }else{
            System.out.println("服务器繁忙.....");
        }
        menu2();
    }

    /**
     * 修改的方法
     */
    private void update() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入图书名称:");
        String name=input.next();
        System.out.println("请输入新价格");
        double price=input.nextDouble();
        String sql="UPDATE  books SET price=? where bookName=?";
        Object[] objs ={price,name};
        if (addOrDelOrUpdate(sql,objs)>0) {
            System.out.println("更新成功");
        }else{
            System.out.println("服务器繁忙.....");
        }
        menu2();
    }

    /**
     * 删除图书
     */
    private void del() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入图书名称:");
        String name=input.next();
        String sql="delete from books where bookName=?";
        Object[] objs={name};
        if (addOrDelOrUpdate(sql,objs)>0) {
            System.out.println("删除成功");
        }else{
            System.out.println("服务器繁忙.....");
        }
        menu2();

    }

    /**
     * 查询图书列表
     */
    private void list() throws SQLException {
        System.out.println("编号    图书名称    图书价格");
        String sql="select * from books";
        queryByWhere(sql,null);
        ArrayList<Books> list = new ArrayList<>();
        while (rs.next()){
            Books books= new Books();
            books.setId(rs.getInt(1));
            books.setBookName(rs.getString(2));
            books.setPrice(rs.getDouble(3));
            list.add(books);
        }
        closeALL();
        for (Books book:list) {
            System.out.println(book.getId()+"    "+book.getBookName()+"    "+book.getPrice());
        }
        menu2();
    }

    /**
     * 退出方法
     */
    private void exits() {
        System.out.println("欢迎下次光临......");
        System.exit(0);

    }

    /**
     * 登录的方法
     */
    private void login() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入用户名");
        String name=input.next();
        System.out.println("请输入密码");
        String pwd= input.next();
        String sql="select * from users where userName=? and userPwd=?";
        Object[] objs={name,pwd};
        queryByWhere(sql,objs);
        Users user= null;
        while(rs.next()){
            user = new Users();
            user.setId(rs.getInt(1));
            user.setUserName(rs.getString(2));
            user.setUserPwd(rs.getString(3));
        }
        closeALL();
        if (user != null) {
            System.out.println("登陆成功");
            menu2();
        }else {
            System.out.println("账号或密码不正确，请重新输入");
            menu();
        }
    }

    /**
     * 注册方法
     */
    private void zc() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入用户名");
        String name=input.next();
        System.out.println("请输入密码");
        String pwd= input.next();
        String sql="INSERT  INTO  users(userName, userPwd) VALUES (?,?);";
        Object[] objs={name,pwd};
        if (addOrDelOrUpdate(sql,objs)>0) {
            System.out.println("注册成功");
            menu();
        }else{
            System.out.println("服务器繁忙.....");
        }

    }

}
