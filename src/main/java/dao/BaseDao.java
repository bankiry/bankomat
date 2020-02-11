package dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BaseDao {
	
	 SqlSessionFactory sqlSessionFactory;
	 Reader reader = null;
	 
	 public BaseDao() {
	        try {
	            reader = Resources.getResourceAsReader("mybatis.config.xml");
	            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	 }

}
