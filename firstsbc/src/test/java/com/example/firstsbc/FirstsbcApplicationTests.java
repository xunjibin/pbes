package com.example.firstsbc;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FirstsbcApplicationTests {
	@Autowired
	StringEncryptor stringEncryptor;

	@Test
	public void encryptPwd() {
		/*String result = stringEncryptor.encrypt("uwifit");
		String result1 = stringEncryptor.encrypt("UserA2018#GD");
		System.out.println("==================");
		System.out.println(result);
		System.out.println(result1);
		System.out.println("==================");*/

		StringBuffer sqltext = new StringBuffer()
				.append("SELECT * FROM ")
				.append("(SELECT RESULT/(SELECT COUNT(1) FROM T_BIZ_CUST A ")
				.append("WHERE A.NODE_ID != 449900000000 ")
				.append("AND EXISTS( ")
				.append("SELECT 'X' FROM ")
				.append("(SELECT DISTINCT BIZ_CUST_ID FROM T_HOTSPOT WHERE STATE =0) B ")
				.append("WHERE B.BIZ_CUST_ID = A.ID ")
				.append(") ")
				.append("AND A.CREATE_DATE <= TO_DATE('").append("2019-09-17 00:00:00").append("','yyyy-MM-dd hh24:mi:ss')) AS RESULT FROM ")
				.append("(SELECT ROUND(SUM(NUM)/COUNT(DAT)) AS RESULT FROM ")
				.append("(SELECT DAT,COUNT(DISTINCT BIZ_CUST_ID) AS NUM FROM ")
				.append("((SELECT /*+INDEX(B,IDX_L_NAS_MAC)*/ A.DAT,B.BIZ_CUST_ID ")
				.append("FROM T_ACCESS_LINE B, ")
				.append("(SELECT DAT,SMAC FROM ")
				.append("(SELECT /*+INDEX (TMP_WAP,TMP_WAP_TIME)*/ TO_CHAR(TIME,'yyyy-MM-dd') AS DAT,SMAC FROM TMP_WAP ")
				.append("WHERE TIME <= TO_DATE('").append("2019-09-17 00:00:00").append("','yyyy-MM-dd hh24:mi:ss') ")
				.append("AND TIME >= TO_DATE('").append("2019-09-17 00:00:00").append("','yyyy-MM-dd hh24:mi:ss') ")
				.append("UNION ALL ")
				.append("SELECT TO_CHAR(STATIS_TIME,'yyyy-MM-dd') AS DAT,MAC AS SMAC FROM T_ONLINE_RATE_DETAILS_").append("2019-09-17 00:00:00".substring(0, 7).replaceAll("-", ""));
		if (!"2019-09-17 00:00:00".substring(0, 7).replaceAll("-", "").equals("2019-09-17 00:00:00".substring(0, 7).replaceAll("-", ""))) {
			sqltext = sqltext
					.append(" UNION ALL ")
					.append("SELECT TO_CHAR(STATIS_TIME,'yyyy-MM-dd') AS DAT,MAC AS SMAC FROM T_ONLINE_RATE_DETAILS_").append("2019-09-17 00:00:00".substring(0, 7).replaceAll("-", ""));
		}
		sqltext.append(" WHERE STATE =1 ")
				.append("AND STATIS_TIME <= TO_DATE('").append("2019-09-17 00:00:00").append("','yyyy-MM-dd hh24:mi:ss') ")
				.append("AND STATIS_TIME >= TO_DATE('").append("2019-09-17 00:00:00").append("','yyyy-MM-dd hh24:mi:ss') ")
				.append("UNION ALL ")
				.append("SELECT TO_CHAR(TIME,'yyyy-MM-dd') AS DAT,SMAC FROM TMP_LRT_SMAC ")
				.append("WHERE TIME <= TO_DATE('").append("2019-09-17 00:00:00").append("','yyyy-MM-dd hh24:mi:ss') ")
				.append("AND TIME >= TO_DATE('").append("2019-09-17 00:00:00").append("','yyyy-MM-dd hh24:mi:ss') ")
				.append(" ) T ")
				.append("GROUP BY DAT,SMAC) A ")
				.append("WHERE A.SMAC = B.NAS_MAC) ")
				.append("UNION ALL ")
				.append("(SELECT /*+INDEX(TMP_LOGIN,TMP_LOGIN_TIME)*/TO_CHAR(TIME,'yyyy-MM-dd') AS DAT,BIZ_CUST_ID ")
				.append("FROM TMP_LOGIN ")
				.append("WHERE TIME <= TO_DATE('").append("2019-09-17 00:00:00").append("','yyyy-MM-dd hh24:mi:ss') ")
				.append("AND TIME >= TO_DATE('").append("2019-09-17 00:00:00").append("','yyyy-MM-dd hh24:mi:ss')) ) T ")
				.append("GROUP BY DAT))) T ");

		System.out.println(sqltext);
	}

	@Test
	public void contextLoads() {
	}

}

