package com.example.firstsbc.test;

/**
 * @description:
 * @author: pengbin
 * @createDate: 2019/12/11
 * @version: 1.0
 */
public class StringTest {

    public static void main(String[] args) {
        String sql=" SELECT T.NODE_ID,SUM( CASE T.PARTNER_TYPE WHEN 99 THEN T.NUM ELSE 0 END ) undefine,"
                + "SUM( CASE T.PARTNER_TYPE WHEN 1 THEN T.NUM ELSE 0 END ) hotel,"
                + "SUM( CASE T.PARTNER_TYPE WHEN 2 THEN T.NUM ELSE 0 END ) library,"
                + "SUM( CASE T.PARTNER_TYPE WHEN 3 THEN T.NUM ELSE 0 END ) computer,"
                + "SUM( CASE T.PARTNER_TYPE WHEN 4 THEN T.NUM ELSE 0 END ) entertainment,"
                + "SUM( CASE T.PARTNER_TYPE WHEN 5 THEN T.NUM ELSE 0 END ) transportcenter,"
                + " SUM( CASE T.PARTNER_TYPE WHEN 6 THEN T.NUM ELSE 0 END ) transporttool,"
                + "SUM( CASE T.PARTNER_TYPE WHEN 7 THEN T.NUM ELSE 0 END ) canton,"
                + "SUM( CASE T.PARTNER_TYPE WHEN 8 THEN T.NUM ELSE 0 END ) finance,"
                + "SUM( CASE T.PARTNER_TYPE WHEN 9 THEN T.NUM ELSE 0 END ) shop,"
                + "SUM( CASE T.PARTNER_TYPE WHEN 10 THEN T.NUM ELSE 0 END ) publics, "
                + "SUM( CASE T.PARTNER_TYPE WHEN 11 THEN T.NUM ELSE 0 END ) culture,"
                + "SUM( CASE T.PARTNER_TYPE WHEN 12 THEN T.NUM ELSE 0 END ) leisure,"
                + "SUM( CASE T.PARTNER_TYPE WHEN 13 THEN T.NUM ELSE 0 END ) others "
                + "FROM( SELECT COUNT( * ) AS NUM,NODE_ID,nvl ( PARTNER_TYPE, 99 ) AS PARTNER_TYPE "
                + "FROM( SELECT MAX(ID), NODE_ID, PARTNER_TYPE, BIZ_CUST_ID FROM T_HOTSPOT WHERE STATE = 0  "

                + "GROUP BY NODE_ID, PARTNER_TYPE, BIZ_CUST_ID  ) GROUP BY NODE_ID,PARTNER_TYPE ) T "
                + "GROUP BY T.NODE_ID";
        System.out.println(sql);
    }
}
