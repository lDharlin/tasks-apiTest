package br.com.luandharlin.apiTest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Requisicoes {


	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
		
	
	}

	@Test
	public void deveRetornarTodasTasks() {
		RestAssured
			.given()
				.log().all()
			.when()
				.get("/todo")
			.then()
				.log().all()
				.assertThat().statusCode(200);
	}
	
	@Test
	public void deveCadastrarNovaTasks() {
		RestAssured
			.given()
				.body("	{" + 
						"		\"task\":\"teste RestAssured\"," + 
						"		\"dueDate\":\"2030-12-30\"" + 
						"	}")
				.contentType(ContentType.JSON)
				.log().all()
			.when()
				.post("/todo")
			.then()
				.log().all()
				.assertThat().statusCode(201);
	}
	
	@Test
	public void naoDeveCadastrarTasksComDataPassada() {
		RestAssured
			.given()
				.body("	{" + 
						"		\"task\":\"teste RestAssured\"," + 
						"		\"dueDate\":\"2010-12-30\"" + 
						"	}")
				.contentType(ContentType.JSON)
				.log().all()
			.when()
				.post("/todo")
			.then()
				.log().all()
				.assertThat()
				.statusCode(400)
				.body("message", CoreMatchers.is("Due date must not be in past"));
	}

	
}
