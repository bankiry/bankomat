package com.solvd.bankomat.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Reader;

public class SessionFactory {

    private static final Logger LOGGER = Logger.getLogger(SessionFactory.class);

    public static SqlSession getSession() {
        SqlSessionFactory sqlSessionFactory;
        SqlSession sqlSession = null;
        Reader reader;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            sqlSession = sqlSessionFactory.openSession();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return sqlSession;
    }
}
