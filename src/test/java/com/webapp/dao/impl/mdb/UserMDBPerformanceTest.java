package com.webapp.dao.impl.mdb;



import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;

import com.webapp.common.test.SpringTransactionContextTest;
import com.webapp.daoimpl.mdb.UserMDBImpl;
import com.webapp.model.User;

public class UserMDBPerformanceTest extends SpringTransactionContextTest{
	
	@Resource(name = "userMDBImpl")
	private UserMDBImpl userDao;
	public List<String> idsList = new ArrayList<String>();
	
//	@Before
	public void addTestUsers(int times){
		
		for (int i = 0; i < times; i++) {
			User user=new User();
			user.setName(Integer.toString(i));
			userDao.save(user);
//			idsList.add(user.getId());
		}
	}

	@Test
	public void addPerformaceTest(){

		int [] testData={1000,5000,10000,20000,40000,80000,120000,160000,200000};
 		for (int i = 0; i < testData.length; i++) {
			userDao.deleteAll();
			long startTime,totalTime;
			int times=140000;
			startTime=System.currentTimeMillis();
			addTestUsers(times);
			totalTime=System.currentTimeMillis()-startTime;
			System.out.println("MDB add "+times+" pieces of data needs "+totalTime+" ms");
			userDao.deleteAll();
		}
	}
	/*
	 *  Find different data size from 200000
	 */
	@Test
	public void findbyIdPerformance1(){
		userDao.deleteAll();
		int times=200000;   // data size in the database
//		int[] testData ={1000,5000,10000,20000,40000,80000,120000,160000,200000}; // find times
		int[] testData = {200000};
		addTestUsers(times);
		for (int k = 0; k < testData.length; k++) {
			Random randomGenerator = new Random();
			long startTime,totalTime;
			List<User>resUsers=new ArrayList<User>();
			startTime=System.currentTimeMillis();
			for (int i = 0; i < testData[k]; i++) {
				resUsers.add(userDao.findById(randomGenerator.nextInt(times)));
			}
			totalTime=System.currentTimeMillis()-startTime;
			System.out.println("MDB find "+testData[k]+" records of data by id needs "+totalTime+" ms");
			assertEquals(resUsers.size(), testData[k]);
		}
		userDao.deleteAll();
	}
	
	/*
	 *  Find 5000 from different data size in the database
	 */
//	@Test
	public void findbyIdPerformance(){
		userDao.deleteAll();
		int times=5000;   // data size in the database
		int[] testData1 ={5000, 10000, 20000, 40000, 80000, 100000, 150000, 180000, 200000}; // find times
		int[] testData = {200000};
		for (int k = 0; k < testData.length; k++) {
			for (int j = 0; j < testData[k]; j++) {
				User user = new User();
				user.setName(Integer.toString(j));
				userDao.save(user);
				idsList.add(user.getId());
			}
			Random randomGenerator = new Random();
			long startTime,totalTime;
			List<User>resUsers=new ArrayList<User>();
			startTime=System.currentTimeMillis();
			for (int i = 0; i < times; i++) {
				int random = randomGenerator.nextInt(testData[k]);
				resUsers.add(userDao.findById(idsList.get(random)));
//				userDao.findById(idsList.get(random));
			}
			totalTime=System.currentTimeMillis()-startTime;
			System.out.println("MDB find "+testData[k]+" records of data by id needs "+totalTime+" ms");
			assertEquals(resUsers.size(), times);
			idsList.clear();
		}
		userDao.deleteAll();
	}

//	@Test
	public void deletebyIdPerformance() {
		userDao.deleteAll();
//		int times=1000000;   // data size in the database
		int deleteItems=30000;  // random delete times
		int [] testData = {50000,100000,200000,300000,400000,500000,600000,700000,800000,900000,1000000};
		for (int i = 0; i < testData.length; i++) {
			long startTime,totalTime;
			addTestUsers(testData[i]);
			
			Set<String>randoms=new HashSet<String>();
			Random randomGenerator = new Random();
			while(randoms.size()<deleteItems) {
				int temp= randomGenerator.nextInt(testData[i]);
				randoms.add(idsList.get(temp));
			}
			
			startTime=System.currentTimeMillis();
			for (int j = 0; j < randoms.size(); j++) {
				userDao.deleteById((String)randoms.toArray()[j]);
			}
			
//			idsList.retainAll(idsList);
			totalTime=System.currentTimeMillis()-startTime;
			System.out.println("MDB delete "+deleteItems+" from "+ testData[i]+" records of data by id needs "+totalTime+" ms");
			userDao.deleteAll();
		}
	}
}
