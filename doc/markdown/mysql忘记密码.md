## MySQL8 忘记密码

1. 管理员模式打开 cmd
2. `net stop mysql` 关闭 mysql 服务
3. 运行 `mysqld --console --skip-grant-tables --shared-memory`
4. 新打开一个管理员模式的 cmd，上一个不要关闭
5. 执行 `mysql` 直接进入 mysql
6. `use mysql` 使用 mysql 数据库
7. `update user set authentication_string='' where user='roor';` 将对应 user 的密码置空
8. `exit` 退出 mysql
9. 关闭步骤3的 cmd 窗口
10. `net start mysql` 启动 mysql 服务
11. `mysql -u root` 无密码登录 mysql
12. `alter user 'root'@'localhost' identified with mysql_native_password by '新密码';` 重置密码
13. `flush privileges` 刷新 mysql 系统权限表
14. 重新使用新密码登录 mysql