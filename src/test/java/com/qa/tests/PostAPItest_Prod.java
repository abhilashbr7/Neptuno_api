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

public class PostAPItest_Prod extends TestBase implements IExecutionListener {

	TestBase testbase;
	RestClient restclient;
	String serviceurl;
	String apiurl;
	String url;
	String Token, citizen_referenceId, corporate_referenceId;
	String Agency_save;
	String Credentials;
	String Citizen_retry;
	String Corporate_Retry;
	String Citizen_details; // Get call
	String Corporate_details; // Get call
	String Citizen_search;
	String Corporate_search;
	String certificate_document; // Get call
	String companyclass; // Get call
	String ValidatePerson; // Get call
	String ChildSupportCase; // Get call
	String Certificate_citizen_detail; // Get call
	String Certificate_corporate_detail; // Get call
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod

	public void setup() throws ClientProtocolException, IOException {
		testbase = new TestBase();
		serviceurl = prop.getProperty("ProdURL");
		Credentials = prop.getProperty("ProdCredentials");
		Credentials = serviceurl + Credentials;
		Agency_save = prop.getProperty("ProdAgency_save");
		Agency_save = serviceurl + Agency_save;
		Citizen_retry = prop.getProperty("ProdCitizen_retry");
		Citizen_retry = serviceurl + Citizen_retry;
		Corporate_Retry = prop.getProperty("ProdCorporate_Retry");
		Corporate_Retry = serviceurl + Corporate_Retry;
		Citizen_details = prop.getProperty("ProdCitizen_details"); // Get call
		Citizen_details = serviceurl + Citizen_details; // Get call
		Corporate_details = prop.getProperty("ProdCorporate_details"); // Get call
		Corporate_details = serviceurl + Corporate_details; // Get call
		Citizen_search = prop.getProperty("ProdCitizen_search");
		Citizen_search = serviceurl + Citizen_search;
		Corporate_search = prop.getProperty("ProdCorporate_search");
		Corporate_search = serviceurl + Corporate_search;
		certificate_document = prop.getProperty("Prodcertificate_document"); // Get call
		certificate_document = serviceurl + certificate_document; // Get call
		companyclass = prop.getProperty("Prodcompanyclass"); // Get call
		companyclass = serviceurl + companyclass; // Get call
		ValidatePerson = prop.getProperty("ProdValidatePerson"); // Get call
		ValidatePerson = serviceurl + ValidatePerson; // Get call
		ChildSupportCase = prop.getProperty("ProdChildSupportCase"); // Get call
		ChildSupportCase = serviceurl + ChildSupportCase; // Get call
		Certificate_citizen_detail = prop.getProperty("ProdCertificate_citizen_detail"); // Get call
		Certificate_citizen_detail = serviceurl + Certificate_citizen_detail; // Get call
		Certificate_corporate_detail = prop.getProperty("ProdCertificate_corporate_detail"); // Get call
		Certificate_corporate_detail = serviceurl + Certificate_corporate_detail; // Get call

	}

	@Test(priority = 1)
	public void PostAPITest_Credentials() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");

		// JACKSON API
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("pritsdev", "prits!@", "456efd38-1ab1-4536-9734-664cba0d8386", "orivera@prits.pr.gov");

		// Object to JSON file conversion
		mapper.writeValue(new File("../users.json"), users);

		// object to json in string
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println(usersJsonString);
  
		closeableHttpResponse = restclient.post(Credentials, usersJsonString, headermap);
		System.out.println("Credentaials for prod is:" + Credentials);

//			//1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

//			//2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

		// to validate
//			Users userobj = mapper.readValue(responsestring, Users.class);
//			System.out.println(userobj);

//			//To get value from JSON Array
		Token = TestUtil.getValueByjpath(responseJson, "/access_token");
		System.out.println("Token is : " + Token);
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
	public void PostAPITest_Agency_save() throws ClientProtocolException, IOException {

		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("referenceId", "03f4f8a0-70e8-4d68-9b19-405236715ab1");
		headermap.put("content-type", "application/json");
		headermap.put("isCitizen", "true");
		headermap.put("isCorporate", "false");
		headermap.put("agencyCode", "Agricultura");
		headermap.put("methodName", "Compliance");
		headermap.put("success", "true");
		headermap.put("userName", "AgriculturaFunction");
		headermap.put("Authorization", "Bearer " + Token);

		// JACKSON API
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		AgencySave agencySave = new AgencySave("", "");

		// Object to JSON file conversion
		//mapper.writeValue(new File("/Users/Dell/IDEAL/DXAPI/src/main/java/com/qa/data/AgencySave.json"), agencySave);
		mapper.writeValue(new File("../AgencySave.json"), agencySave);
		// object to json in string
		String agencyJsonString = mapper.writeValueAsString(agencySave);
		System.out.println(agencyJsonString);

		closeableHttpResponse = restclient.post(Agency_save, agencyJsonString, headermap);

//			//1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

//			//2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

		// To get value from JSON Array
		String responseFromJson = TestUtil.getValueByjpath(responseJson, "/status");
		System.out.println("Response From Json is : " + responseFromJson);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers Array-->" + allHeaders);

	}

	@Test(priority = 3)
	public void PostAPITest_Citizen_Retry() throws ClientProtocolException, IOException {

		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("referenceId", "76b47819-069b-4459-b3cf-b6b679fdd430");
		headermap.put("agencyCode", "Retiro");
		headermap.put("Authorization", "Bearer " + Token);

		closeableHttpResponse = restclient.post_nobody(Citizen_retry, headermap);

		// 1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// 2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

		// To get value from JSON Array
		String Responsefromjson = TestUtil.getValueByjpath(responseJson, "/status");
		System.out.println("Resonse from json payload is: " + Responsefromjson);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array-->" + allHeaders);

	}

	@Test(priority = 4)
	public void PostAPITest_Corporate_Retry() throws ClientProtocolException, IOException {

		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("referenceId", "bab474f4-aa99-432b-864c-35d3ec811e9a");
		headermap.put("agencyCode", "ASG");
		headermap.put("Authorization", "Bearer " + Token);

		closeableHttpResponse = restclient.post_nobody(Corporate_Retry, headermap);

		// 1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// 2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

		// To get value from JSON Array
		String Responsefromjson = TestUtil.getValueByjpath(responseJson, "/status");
		System.out.println("Resonse from json payload is: " + Responsefromjson);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array-->" + allHeaders);

	}

	@Test(priority = 5)
	public void get_CitezenDetails() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("referenceId", "d286928c-9cd9-4b40-af4b-631d07312b0c");
		headermap.put("Authorization", "Bearer " + Token);
		closeableHttpResponse = restclient.get(Citizen_details, headermap);

		// 1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// 2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

		// To get value from JSON Array
		String agencyName = TestUtil.getValueByjpath(responseJson, "/data[0]/Agency");
		System.out.println("Agency name is : " + agencyName);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());

		}
	}

	@Test(priority = 6)
	public void get_CorporateDetails() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("referenceId", "6cae70c9-8755-4973-bb1d-a96964808091");
		headermap.put("Authorization", "Bearer " + Token);
		closeableHttpResponse = restclient.get(Corporate_details, headermap);

		// 1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// 2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

		// To get value from JSON Array
		String agencyName = TestUtil.getValueByjpath(responseJson, "/data[0]/Agency");
		System.out.println("Agency name is : " + agencyName);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());

		}
	}

	@Test(priority = 7)
	public void PostAPITest_Citizen_search() throws ClientProtocolException, IOException {

		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("firstName", "Oscar");
		headermap.put("lastName", "Rivera");
		headermap.put("middleName", "");
		headermap.put("mothersLastName", "");
		headermap.put("ssn", "598408444");
		headermap.put("licenseNumber", "4970687");
		headermap.put("dob", "1993-09-21");
		headermap.put("townOfBirth", "San Juan");
		headermap.put("licenseNumber", "4970687");
		headermap.put("merchantRegistrationNumber", "12642760014");
		headermap.put("email", "");
		headermap.put("mobileNumber", "5087408882");
		headermap.put("searchReason", "Permisos");
		headermap.put("AuthorizationDate", "01/01/2022");
		headermap.put("uniqueIdType", "1");
		headermap.put("taxYear", "0");
		headermap.put("Authorization", "Bearer " + Token);

		closeableHttpResponse = restclient.post_nobody(Citizen_search, headermap);

		// 1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// 2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

		// To get value from JSON Array
		String Responsefromjson = TestUtil.getValueByjpath(responseJson, "/status");
		System.out.println("Resonse from json payload is: " + Responsefromjson);

		citizen_referenceId = TestUtil.getValueByjpath(responseJson, "/citizen_referenceId");
		System.out.println("citizen_referenceId is : " + citizen_referenceId);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array-->" + allHeaders);

	}

	@Test(priority = 8)
	public void PostAPITest_Corporate_search() throws ClientProtocolException, IOException {

		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("companyRepresentative", "Juan");
		headermap.put("companyName", "Code Dog Technology Group LLC");
		headermap.put("ssn", "660962981");
		headermap.put("merchantRegistrationNumber", "");
		headermap.put("stateRegistrationNumber", "411607");
		headermap.put("companyClass", "1");
		headermap.put("companyType", "2");
		headermap.put("jurisdiction", "1");
		headermap.put("email", "mayanka@yahoo.com");
		headermap.put("mobileNumber", "5087408882");
		headermap.put("searchReason", "EntityContract");
		headermap.put("authorizationDate", "11/29/2021");
		headermap.put("choferilSSNPatronal", "");
		headermap.put("uniqueIdType", "2");
		headermap.put("taxYear", "0");
		headermap.put("Authorization", "Bearer " + Token);

		closeableHttpResponse = restclient.post_nobody(Corporate_search, headermap);

		// 1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// 2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

		// To get value from JSON Array
		String Responsefromjson = TestUtil.getValueByjpath(responseJson, "/status");
		System.out.println("Resonse from json payload is: " + Responsefromjson);

		corporate_referenceId = TestUtil.getValueByjpath(responseJson, "/corporate_referenceId");
		System.out.println("corporate_referenceId is : " + corporate_referenceId);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array-->" + allHeaders);

	}

	@Test(priority = 9)
	public void get_certificate_document() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("agencyCode", "AGRICULTURA");
		headermap.put("referenceId", "1940b6a3-7d25-4f1b-af76-a6ca8888d5d8");
		headermap.put("certificateId", "53c0b2d2-f0be-48a0-b879-512ecc52f05d");
		headermap.put("Authorization", "Bearer " + Token);
		closeableHttpResponse = restclient.get(certificate_document, headermap);

		// 1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// 2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

		// To get value from JSON Array
		String agencyCode = TestUtil.getValueByjpath(responseJson, "/agencyCode");
		System.out.println("Agency code is : " + agencyCode);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());

		}
	}

	@Test(priority = 10)
	public void get_companyclass() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("agencyCode", "ASUME");
		headermap.put("referenceId", "20618d49-1a17-4614-a499-049bfba04f2c");
		headermap.put("certificateId", "bfdc8eaa-152b-4f54-b6e4-2741898e8218");
		headermap.put("Authorization", "Bearer " + Token);
		closeableHttpResponse = restclient.get(companyclass, headermap);

		// 1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// 2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

		// To get value from JSON Array
		String Domain = TestUtil.getValueByjpath(responseJson, "/data[0]/Domain");
		System.out.println("Domain is : " + Domain);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());

		}
	}

	
	  @Test (enabled = false) 
	  public void get_ValidatePerson() throws ClientProtocolException,IOException 
	  { 
	restclient = new RestClient();
	  HashMap<String, String> headermap = new HashMap<String, String>();
	  headermap.put("content-type", "application/json");
	  headermap.put("ssn","125661903"); 
	  headermap.put("dateOfBirth", "1981/12/22");
	  headermap.put("Authorization", "Bearer " +Token);
	  
	  closeableHttpResponse = restclient.get(ValidatePerson,headermap);
	  
	  //1.GET status code 
	  int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
	  System.out.println("Status code:" +statuscode);
	  Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200,  "status code is not 200");
	  
	  //2.Json String
	  String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
	  
	  JSONObject responseJson = new JSONObject(responsestring);
	  System.out.println("JSON response from API---->" +responseJson);
	  
	  //To get value from JSON Array
	  String agencyName = TestUtil.getValueByjpath(responseJson, "/data[0]/Domain");
	  System.out.println("Agency name is : " + agencyName);
	  
	  //3.All Headers 
	  Header[] headersArray = closeableHttpResponse.getAllHeaders(); HashMap<String,String> allHeaders =
	  new HashMap<String,String>();
	  
	  for(Header header : headersArray) { allHeaders.put(header.getName(),  header.getValue());
	  
	  } 
	  }
	  
	  @Test (enabled = false)
	  public void get_ChildSupportCase() throws ClientProtocolException, IOException
	  {
	restclient = new RestClient();
	  HashMap<String, String> headermap = new HashMap<String, String>();
	  headermap.put("content-type", "application/json"); 
	  headermap.put("ssn","125661903"); 
	  headermap.put("dateOfBirth", "1981/12/22");
	  headermap.put("caseNumber", "0318492");
	  headermap.put("Authorization", "Bearer " +Token);
	  
	  closeableHttpResponse =restclient.get(ChildSupportCase,headermap);
	  
	  //1.GET status code 
	  int statuscode =closeableHttpResponse.getStatusLine().getStatusCode();
	  System.out.println("Status code:" +statuscode);
	  Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200,
	  "status code is not 200");
	  
	  //2.Json String 
	  String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
	  
	  JSONObject responseJson = new JSONObject(responsestring);
	  System.out.println("JSON response from API---->" +responseJson);
	  
	  //To get value from JSON Array 
	  String agencyName = TestUtil.getValueByjpath(responseJson, "/data[0]/Domain");
	  System.out.println("Agency name is : " + agencyName);
	  
	  //3.All Headers
	  Header[] headersArray = closeableHttpResponse.getAllHeaders(); HashMap<String,String> allHeaders =
	  new HashMap<String,String>();
	  
	  for(Header header : headersArray) { allHeaders.put(header.getName(),
	  header.getValue());
	  
	  } }
	 

	@Test(priority = 13)
	public void get_Certificate_citizen_detail() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("referenceId", citizen_referenceId);
		headermap.put("Authorization", "Bearer " + Token);

		closeableHttpResponse = restclient.get(Certificate_citizen_detail, headermap);

		// 1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// 2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

		// To get value from JSON Array
		String status = TestUtil.getValueByjpath(responseJson, "/status");
		System.out.println("Status is : " + status);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());

		}
	}

	@Test(priority = 14)
	public void get_Certificate_corporate_detail() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("referenceId", corporate_referenceId);
		headermap.put("Authorization", "Bearer " + Token);
		closeableHttpResponse = restclient.get(Certificate_corporate_detail, headermap);

		// 1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// 2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

		// To get value from JSON Array
		String status = TestUtil.getValueByjpath(responseJson, "/status");
		System.out.println("Status is : " + status);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());

		}
	}
}
