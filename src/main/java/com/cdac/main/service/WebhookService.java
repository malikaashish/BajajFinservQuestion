package com.cdac.main.service;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import com.cdac.main.dto.SubmitRequest;
import com.cdac.main.dto.WebhookRequest;
import com.cdac.main.dto.WebhookResponse;

@Service
public class WebhookService {

	private final RestTemplate restTemplate = new RestTemplate();
	
	private final String ques1= "SELECT dept_name AS DEPARTMENT_NAME, total_salary AS SALARY, employee_name AS EMPLOYEE_NAME, employee_age AS AGE FROM ( SELECT d.DEPARTMENT_NAME AS dept_name, CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS employee_name, TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS employee_age, SUM(p.AMOUNT) AS total_salary, ROW_NUMBER() OVER (PARTITION BY d.DEPARTMENT_ID ORDER BY SUM(p.AMOUNT) DESC) AS rn FROM DEPARTMENT d JOIN EMPLOYEE e ON d.DEPARTMENT_ID = e.DEPARTMENT JOIN PAYMENTS p ON e.EMP_ID = p.EMP_ID WHERE DAY(p.PAYMENT_TIME) <> 1 GROUP BY d.DEPARTMENT_ID, d.DEPARTMENT_NAME, e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, e.DOB ) ranked_salaries WHERE rn = 1";

	public void startProcess() {
	    String generateUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
	    WebhookRequest request = new WebhookRequest();
	    request.setName("John Doe");
	    request.setRegNo("REG12347");
	    request.setEmail("john@example.com");
	    
	    WebhookResponse response = restTemplate.postForObject(generateUrl, request, WebhookResponse.class);
	    if (response == null) {
	        throw new RuntimeException("Failed to get webhook response");
	    }
	    String finalSqlQuery = solveSqlProblem();
	    submitAnswer(response.getWebhook(), response.getAccessToken(), finalSqlQuery);
	}
	

	private String solveSqlProblem() {

	    return ques1;
	}

	private void submitAnswer(String webhookUrl, String token, String query) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    SubmitRequest body = new SubmitRequest(query);
	    HttpEntity<SubmitRequest> entity = new HttpEntity<>(body, headers);
	    var response = restTemplate.postForEntity(webhookUrl, entity, String.class);
	    System.out.println("STATUS CODE : " + response.getStatusCode());
	    System.out.println("RESPONSE BODY: " + response.getBody());
	}
}

