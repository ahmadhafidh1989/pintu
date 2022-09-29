package Steps;

import Model.BodyDto;
import Model.DataDto;
import com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;
import org.testng.Assert;

import java.io.IOException;

public class ApiTestSteps {
    public Response rspn;
    public String json;
    public String baseUrl;
    public String BodyRequest;
    public BodyDto bodyDto;

    public ApiTestSteps() {
        baseUrl = "https://jsonplaceholder.typicode.com/posts";
    }

    @When("I call get")
    public void i_call_get() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(baseUrl)
                .get()
                .addHeader("accept", "application/json")
                .build();

        rspn = client.newCall(request).execute();
        json = rspn.body().string();
    }

    @Then("The API should Return 200 OK")
    public void the_api_should_return_ok() {
        Assert.assertEquals(200,rspn.code());
    }

    @Then("The Respons Return Correct DataType")
    public void the_respons_return_correct_data_type() {
        Gson gson = new Gson();
        DataDto [] dataDto = gson.fromJson(json, DataDto[].class);
        Assert.assertNotNull(dataDto);
        for(DataDto data : dataDto) {
            Assert.assertTrue(data.id instanceof java.lang.Integer);
            Assert.assertTrue(data.userId instanceof java.lang.Integer);
            Assert.assertTrue(data.body instanceof  java.lang.String);
        }
    }

    @Given("I provide correct body request")
    public void i_provide_correct_body_request() {
        BodyRequest = "{ \"title\" : \"recomendation\", \"body\" : \"motorcycle\",\"userId\": 12}";
        Gson gson = new Gson();
        bodyDto = gson.fromJson(BodyRequest,BodyDto.class);
    }
    @When("I Post the body request")
    public void i_post_the_body_request() throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, BodyRequest);
        Request request = new Request.Builder()
                .url(baseUrl)
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();
        rspn = client.newCall(request).execute();
        json = rspn.body().string();
    }

    @Then("the response should return as body request")
    public void the_response_should_return_as_body_request() {
        Gson gson = new Gson();
        BodyDto rspnBody = gson.fromJson(json,BodyDto.class);

        Assert.assertEquals(rspnBody.userId, bodyDto.userId);
        Assert.assertEquals(rspnBody.title, bodyDto.title);
        Assert.assertEquals(rspnBody.body, bodyDto.body);
    }

    @Then("The API should Return 201 OK")
    public void theAPIShouldReturnOK() {
        Assert.assertEquals(201,rspn.code());
    }
}