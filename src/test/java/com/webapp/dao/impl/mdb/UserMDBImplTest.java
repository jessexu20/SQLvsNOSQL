package com.webapp.dao.impl.mdb;



import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.webapp.common.test.SpringTransactionContextTest;
import com.webapp.daoimpl.mdb.UserMDBImpl;
import com.webapp.model.User;

public class UserMDBImplTest extends SpringTransactionContextTest {
	
	@Resource(name = "userMDBImpl")
	private UserMDBImpl userDao;
	
	@Test
	public void findAllUser(){
		userDao.deleteAll();
		List<User> result =userDao.findAll();
		//assertThat(result.size(),60);
	}
	
	@Test
	@Rollback(true)
	public void findByString(){
		User u = new User();
		u.setName("jiang");
		User u1 = new User();
		u1.setName("xu");
		userDao.save(u);
		userDao.save(u1);
		List<User> result = userDao.findAll("{ studentId : {$in :[12, 13]}}");
		assertEquals( result.size(),2);
	}
	
	@Test
	public void delete(){
		User user = new User();
		user.setName("jiang");
		userDao.save(user);
		User u = userDao.findById(user.getId());
		userDao.delete(u);
		assertEquals(userDao.findOne("{name: 'jiang'}"), null);
	}
	
	@Test
	@Rollback
	public void deleteById(){
		User user = new User();
		user.setName("jiang");
		userDao.save(user);
		userDao.deleteById(user.getId());
		assertEquals(userDao.findOne("{name: 'jiang'}"), null);

	}
	
//	@Test
//	public void findByName(){
//		User u1 = new User();
//		u1.setName("jiang");
//		User u2 = new User("Jiang",26);
//		userDao.save(u1);
//		userDao.save(u2);
//		assertEquals(userDao.findByName("Jiang").size(), 2);
//		userDao.delete(u1);
//		userDao.delete(u2);
//	}
//	@Test
//	public void findByParams() {
//		User u1 = new User("fred",25);
//		User u2 = new User("fred",26);
//		userDao.save(u1);
//		userDao.save(u2);
//		Parameter parameter = new Parameter();
//		parameter.put("name", "fred");
//		parameter.put("studentId", 25);
//		assertEquals(userDao.findAll(parameter).size(), 1);
//		userDao.delete(u1);
//		userDao.delete(u2);
//	}
//	
//	@Test
//	public void save() {
//		User u1 = new User("fred", 100);
//		User u2 = null;
//		userDao.save(u1);
//		u2 = userDao.findOne("{name: 'fred', studentId: 100}");
//		assertEquals(u2.getStudentId(), 100);
//		userDao.deleteById(u1.getId());
//	}
//	
//	@Test
//	public void saveByList(){
//		User u1 = new User("fred",25);
//		User u2 = new User("fred",26);
//		List<User> list = new ArrayList<User>();
//		list.add(u1);
//		list.add(u2);
//		userDao.save(list);
//		assertEquals(userDao.findByName("fred").size(), 2);
//		userDao.delete(u1);
//		userDao.delete(u2);
//	}
//	
//	@Test
//	public void multiParameter(){
//		User u1 = new User("Jiang",25);
//		User u2 = new User("Jiang",26);
//		userDao.save(u1);
//		userDao.save(u2);
//		Parameter p = new Parameter();
//		p.put("name", "Jiang");
//		p.put("studentId", 25);
//		
//		assertEquals(userDao.findAll(p).size(), 1);
//		userDao.delete(u1);
//		userDao.delete(u2);
//	}
//	
}
