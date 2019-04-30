/**
 * @filename RedisDemo.java
 * @author lg
 * @date 2018��6��18�� ����9:00:58
 * @version 1.0
 * Copyright (C) 2018 
 */

package redisTest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDemo {
	JedisPool pool;   
	Jedis jedis;   
	@Before   
	public void setUp() {   
	pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");    
	jedis = pool.getResource();   
	   //jedis.auth("password");   
	}   
	  
	@Test   
	public void testGet(){   
	System.out.println(jedis.get("ename"));   
	}   
	/**  
	* Redis�洢�������ַ���  
	* CRUD  
	*/   
	@Test   
	public void testBasicString(){   
	//-----�������----------   
	jedis.set("name123","lg123");//��key-->name�з�����value-->lg   
	System.out.println(jedis.get("name123"));//ִ�н����lg   
	//-----�޸�����-----------   
	//1����ԭ���������޸�   
//	jedis.append("name123","456");   //��ֱ�ۣ�����map ��lg123 append���Ѿ��е�value֮��   
//	System.out.println(jedis.get("name123"));//ִ�н��:lglg123   
	//2��ֱ�Ӹ���ԭ��������   
//	jedis.set("name123","����");   
//	System.out.println(jedis.get("name123"));//ִ�н��������
	//ɾ��key��Ӧ�ļ�¼   
	//jedis.del("name123");   
	//System.out.println(jedis.get("name123"));//ִ�н����null       
	
	jedis.mset("name123","lg111","age","30");              
	System.out.println(jedis.mget("name123","age")); 
	}   
	/**  
	* jedis����Map  
	*/   
	@Test   
	public void testMap(){   
	Map<String,String> user=new HashMap<String,String>();   
	user.put("name","lg");   
	user.put("pwd","password");   
	jedis.hmset("user",user); 
	//ȡ��user�е�name��ִ�н��:[lg]-->ע������һ�����͵�List   
	//��һ�������Ǵ���redis��map�����key����������Ƿ���map�еĶ����key�������key���Ը�������ǿɱ����   
//	List<String> rsmap = jedis.hmget("user", "name");   
//	System.out.println(rsmap);    
	//ɾ��map�е�ĳ����ֵ   
//	 jedis.hdel("user","pwd");   
//	 System.out.println(jedis.hmget("user", "pwd")); //��Ϊɾ���ˣ����Է��ص���null   
//	System.out.println(jedis.hlen("user")); //����keyΪuser�ļ��д�ŵ�ֵ�ĸ���1   
//	System.out.println(jedis.exists("user"));//�Ƿ����keyΪuser�ļ�¼ ����true   
//	System.out.println(jedis.hkeys("user"));//����map�����е�����key  [pwd, name]   
//	System.out.println(jedis.hvals("user"));//����map�����е�����value  [minxr, password]   
	/*Iterator<String> iter=jedis.hkeys("user").iterator();   
	while (iter.hasNext()){   
	String key = iter.next();              
	System.out.println(key+":"+jedis.hmget("user",key));   
	}  */     
	}   
	/**  
	* jedis����List  
	*/   
	@Test   
	public void testList(){   
	//��ʼǰ�����Ƴ����е�����   
	//jedis.del("java framework");   
	//System.out.println(jedis.lrange("java framework",0,-1));   
	//����key java framework�д����������   
	jedis.rpush("java framework","spring1");   
	jedis.rpush("java framework","struts1");   
	jedis.rpush("java framework","hibernate1");   
	//��ȡ����������jedis.lrange�ǰ���Χȡ����   
	// ��һ����key���ڶ�������ʼλ�ã��������ǽ���λ�ã�jedis.llen��ȡ���� -1��ʾȡ������   
	System.out.println(jedis.lrange("java framework",0,-1));   
	}   
	/**  
	* jedis����Set  
	*/   
	@Test   
	public void testSet(){   
	//���   
	jedis.sadd("sname","lg");   
	jedis.sadd("sname","123");   
	jedis.sadd("sname","lg123456");   
	jedis.sadd("sname","noname");   
	//�Ƴ�noname   
	//jedis.srem("sname","noname");   
	System.out.println(jedis.smembers("sname"));//��ȡ���м����value   
	System.out.println(jedis.sismember("sname", "lg"));//�ж� lg �Ƿ���sname���ϵ�Ԫ��   
	System.out.println(jedis.srandmember("sname"));   
	System.out.println(jedis.scard("sname"));//���ؼ��ϵ�Ԫ�ظ���   
	}       
	@Test   
	public void test() throws InterruptedException {   
	//keys�д���Ŀ�����ͨ���   
	 System.out.println(jedis.keys("*")); //���ص�ǰ�������е�key  [sose, sanme, name, foo, sname, java framework, user, braand]   
	System.out.println(jedis.keys("*name"));//���ص�sname   [sname, name]   
	System.out.println(jedis.del("sanmdde"));//ɾ��keyΪsanmdde�Ķ���  ɾ���ɹ�����1 ɾ��ʧ�ܣ����߲����ڣ����� 0   
	System.out.println(jedis.ttl("sname"));//���ظ���key����Чʱ�䣬�����-1���ʾ��Զ��Ч   
	jedis.setex("timekey", 10, "min");//ͨ���˷���������ָ��key�Ĵ���Чʱ�䣩 ʱ��Ϊ��   
	Thread.sleep(5000);//˯��5���ʣ��ʱ�佫Ϊ<=5   
	System.out.println(jedis.ttl("timekey"));   //������Ϊ5   
	jedis.setex("timekey", 1, "min");        //��Ϊ1�������ٿ�ʣ��ʱ�����1��   
	System.out.println(jedis.ttl("timekey"));  //������Ϊ1   
	System.out.println(jedis.exists("key"));//���key�Ƿ����             System.out.println(jedis.rename("timekey","time"));   
	System.out.println(jedis.get("timekey"));//��Ϊ�Ƴ�������Ϊnull   
	System.out.println(jedis.get("time")); //��Ϊ��timekey ������Ϊtime ���Կ���ȡ��ֵ min   
	//jedis ����   
	//ע�⣬�˴���rpush��lpush��List�Ĳ�������һ��˫���������ӱ��������ģ�   
	jedis.del("a");//��������ݣ��ټ������ݽ��в���   
	jedis.rpush("a", "1");   
	jedis.lpush("a","6");   
	jedis.lpush("a","3");   
	jedis.lpush("a","9");   
	System.out.println(jedis.lrange("a",0,-1));// [9, 3, 6, 1]   
	System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //�����������   
	System.out.println(jedis.lrange("a",0,-1));   
	}   

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
