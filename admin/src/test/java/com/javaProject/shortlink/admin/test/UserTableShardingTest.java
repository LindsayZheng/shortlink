package com.javaProject.shortlink.admin.test;

public class UserTableShardingTest {
    public static final String SQL = "CREATE TABLE `t_user_%d` (\n" +
            "`id` bigint NOT NULL COMMENT 'ID',\n" +
            "`username` varchar(256) DEFAULT NULL COMMENT 'username',\n" +
            "`password` varchar(512) DEFAULT NULL COMMENT 'password',\n" +
            "`real_name` varchar(256) DEFAULT NULL COMMENT 'realname',\n" +
            "`phone` varchar(128) DEFAULT NULL COMMENT 'phone_number',\n" +
            "`mail` varchar(512) DEFAULT NULL COMMENT 'mail_address',\n" +
            "`deletion_time` bigint DEFAULT NULL COMMENT 'account_deletion_time',\n"+
            "`create_time` datetime DEFAULT NULL COMMENT 'create_time',\n" +
            "`update_time` datetime DEFAULT NULL COMMENT 'update_time',\n" +
            "`del_flag` tinyint(1) DEFAULT NULL COMMENT '0 = existed, 1 = deleted',\n" +
            "PRIMARY KEY (`id`),\n" +
            "UNIQUE KEY `idx_unique_username` (`username`) USING BTREE\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((SQL) + "%n", i);
        }
    }
}
