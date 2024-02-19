package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.IExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.AgencySave;
import com.qa.data.Users;
import com.qa.util.TestUtil;

public class Neptuno extends TestBase implements IExecutionListener {

	TestBase testbase;
	RestClient restclient;
	String serviceurl;
	String apiurl;
	String url;
	String Token,token,shipOwn,Tickets,ShipFees,TicketTransaction,ShipRenewal,ship_reg_num,status_tickets;
	String fee,message,shiprenewal_message;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod

	public void setup() throws ClientProtocolException, IOException {
		testbase = new TestBase();
		serviceurl = prop.getProperty("URL");
		Token = prop.getProperty("token");
		Token = serviceurl + Token;
		shipOwn = prop.getProperty("shipOwn");
		shipOwn = serviceurl + shipOwn;
		Tickets = prop.getProperty("Tickets");
		Tickets = serviceurl + Tickets;
		ShipFees = prop.getProperty("ShipFees");
		ShipFees = serviceurl + ShipFees;
		TicketTransaction = prop.getProperty("TicketTransaction");
		TicketTransaction = serviceurl + TicketTransaction;
		ShipRenewal = prop.getProperty("ShipRenewal"); // Get call
		ShipRenewal = serviceurl + ShipRenewal; // Get call
		
	}
	@Test(priority = 1)
	public void Token() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("username", "idealQA");
		headermap.put("password", "%C922Yj0");
		headermap.put("client_secret", "1orlfkcif7e15iet49mg1aojvrg5ovq6acmr8ldpdo6p7998neg3");
		headermap.put("client_id", "6b6iij617t0mrnb1g5v6vua93u");
		headermap.put("x-api-key", "RT3xMZkRjm5lwbjbV4iO86Izfl8JpZ29qPfvR5eb");

		closeableHttpResponse = restclient.get(Token, headermap);

//			//1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

//			//2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

//			//To get value from JSON Array
		token = TestUtil.getValueByjpath(responseJson, "body/token");
		System.out.println("Token is : " + token);
//		    			
//			//3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers Array-->" + allHeaders);

	}

	@Test(priority = 2)
	public void ShipOwn() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("ID", "581-80-9555");
		headermap.put("REGISTRATION_NUMBER", "PR0566EE");
		headermap.put("Authorization", "Bearer " + token);
		

		closeableHttpResponse = restclient.post_nobody(shipOwn, headermap);

		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// //2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

//		// To get value from JSON Array
		String ship_reg_num = TestUtil.getValueByjpath(responseJson, "body/data[0]/ship_reg_num");
		System.out.println("Response From Json is : " + ship_reg_num);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
		allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers Array-->" + allHeaders);

	}

	@Test(priority = 3)
	public void getTickets() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("REGISTRATION_NUMBER", "PR0033EE");
		headermap.put("Authorization", "Bearer " + token);
		

		closeableHttpResponse = restclient.get(Tickets, headermap);

		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// //2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

//		// To get value from JSON Array
		String status_tickets = TestUtil.getValueByjpath(responseJson, "body/data[0]/estatus");
		System.out.println("Tickets Response From Json is : " + status_tickets);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
		allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers Array-->" + allHeaders);

	}

	@Test(priority = 4)
	public void getShipFees() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("Authorization", "Bearer " + token);
		

		closeableHttpResponse = restclient.get(ShipFees, headermap);

		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// //2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

//		// To get value from JSON Array
		String fee = TestUtil.getValueByjpath(responseJson, "body/data[0]/fee");
		System.out.println("Fee Response From Json is : " + fee);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
		allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers Array-->" + allHeaders);

	}
	
	@Test(priority = 5)
	public void TicketTransaction() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("Authorization", "Bearer " + token);
		
		String jsonpayload = "{\r\n"
				+ "  \"ship_reg_num\": \"PR0566EE\",\r\n"
				+ "  \"transaction_date\": \"2024-01-31\",\r\n"
				+ "  \"receipt_num\": \"0000000\",\r\n"
				+ "  \"amount\": 5.00,\r\n"
				+ "  \"trans_code\": \"MP\"\r\n"
				+ "}";
		

		closeableHttpResponse = restclient.post(TicketTransaction, jsonpayload, headermap);

		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// //2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

//		// To get value from JSON Array
		String message = TestUtil.getValueByjpath(responseJson, "/body/message");
		System.out.println("Ticket Transaction message Response From Json is : " + message);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
		allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers Array-->" + allHeaders);

	}

	@Test(priority = 6)
	public void reportShipRenewal() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("Authorization", "Bearer " + token);
		
		String jsonpayload = "{\r\n"
				+ "  \"ship_reg_num\": \"PR0566EE\",\r\n"
				+ "  \"transaction_date\": \"2024-01-31\",\r\n"
				+ "  \"pick_up\": \"Y\",\r\n"
				+ "  \"mailing_address_1\": \"\",\r\n"
				+ "  \"mailing_address_2\": \"\",\r\n"
				+ "  \"mailing_city\": \"\",\r\n"
				+ "  \"mailing_zip\": \"\",\r\n"
				+ "  \"receipt_num\": \"0000000\",\r\n"
				+ "  \"username\": \"USERNAME_1\",\r\n"
				+ "  \"amount\": 5.00,\r\n"
				+ "  \"trans_code\": \"RN\"\r\n"
				+ "}\r\n"
				+ "";
		

		closeableHttpResponse = restclient.post(ShipRenewal, jsonpayload, headermap);

		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// //2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

//		// To get value from JSON Array
		String shiprenewal_message = TestUtil.getValueByjpath(responseJson, "/body/message");
		System.out.println("Ticket Transaction message Response From Json is : " + shiprenewal_message);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
		allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers Array-->" + allHeaders);

	}
}
