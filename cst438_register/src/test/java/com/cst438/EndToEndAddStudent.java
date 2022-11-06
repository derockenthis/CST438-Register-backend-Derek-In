package com.cst438;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

public class EndToEndAddStudent {
	public static final String CHROME_DRIVER_FILE_LOCATION = "/Users/derekin/desktop/chromedriver";

	public static final String URL = "http://localhost:3000";

	public static final String TEST_USER_EMAIL = "test@csumb.edu";
	
	public static final String TEST_USER_NAME = "test";

	public static final String TEST_USER_STATUS = "0";
	
	public static final int TEST_COURSE_ID = 40443; 

	public static final String TEST_SEMESTER = "2021 Fall";

	public static final int SLEEP_DURATION = 1000; // 1 second.
	
	@Autowired
	EnrollmentRepository enrollmentRepository;

	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Test
	public void addCourseTest() throws Exception {
		
		/*
		 * if student is already enrolled, then delete the enrollment.
		 */
		
		Enrollment x = null;
		do {
			x = enrollmentRepository.findByEmailAndCourseId(TEST_USER_EMAIL, TEST_COURSE_ID);
			if (x != null)
				enrollmentRepository.delete(x);
		} while (x != null);
		
		// set the driver location and start driver
		//@formatter:off
		// browser	property name 				Java Driver Class
		// edge 	webdriver.edge.driver 		EdgeDriver
		// FireFox 	webdriver.firefox.driver 	FirefoxDriver
		// IE 		webdriver.ie.driver 		InternetExplorerDriver
		//@formatter:on

		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver driver = new ChromeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		try {

			driver.get(URL);
			Thread.sleep(SLEEP_DURATION);

			// Locate and click "Add Student" button
			
			driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/div/div/button")).click();
			Thread.sleep(SLEEP_DURATION);
			
			// Locate student email field,name field, status code
			
			driver.findElement(By.xpath("//input[@id='email']")).sendKeys(TEST_USER_EMAIL);
			Thread.sleep(SLEEP_DURATION);
			
			driver.findElement(By.xpath("//input[@id='name']")).sendKeys(TEST_USER_NAME);
			Thread.sleep(SLEEP_DURATION);
			
			driver.findElement(By.xpath("//input[@id='status_code']")).sendKeys(TEST_USER_STATUS);
			Thread.sleep(SLEEP_DURATION);
			
			Student student = studentRepository.findByEmail(TEST_USER_EMAIL);
			
			boolean found = false;
			
			assertNotNull(student, "student not found in database.");
			
		
		}catch (Exception ex) {
			throw ex;
		} finally {
	
			// clean up database.
			
			Student s = studentRepository.findByEmail(TEST_USER_EMAIL);
			if (s != null)
				studentRepository.delete(s);
	
			driver.quit();
		}
	}


}
