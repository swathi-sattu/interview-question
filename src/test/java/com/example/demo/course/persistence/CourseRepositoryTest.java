package com.example.demo.course.persistence;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CourseRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	CourseRepository courseRepo;
	
	CourseEntity entity = new CourseEntity();
	
	@Before
	public void setUp() {
		
		entity.setTitle("title");
	}

	@Test
	public void testSaveEntity() {
		
		entity = entityManager.persist(entity);
		assertEquals(courseRepo.findById(entity.getId()).get(), entity);
	}
	
	@Test
	public void testFindByTitle() {
		entity = entityManager.persist(entity);
		List<CourseEntity> list = courseRepo.findByTitle("title");
		assertEquals(list.get(0), entity);
	}
}
