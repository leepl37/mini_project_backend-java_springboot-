package chironsoft.test.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http 통신을 위한 클래스
 * 
 * @author jskang
 *
 */
public class CommonHttpClient {
	private static final Logger logger = LoggerFactory.getLogger(CommonHttpClient.class);
	private static int TIME_OUT = 60000;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	public static void main(String[] args) {
		logger.info("start");
		String body = "{" + "    \"glossary\": {" + "        \"title\": \"example glossary\"," + "		\"GlossDiv\": {"
				+ "            \"title\": \"S\"," + "			\"GlossList\": {" + "                \"GlossEntry\": {"
				+ "                    \"ID\": \"SGML\"," + "					\"SortAs\": \"SGML\","
				+ "					\"GlossTerm\": \"Standard Generalized Markup Language\","
				+ "					\"Acronym\": \"SGML\"," + "					\"Abbrev\": \"ISO 8879:1986\","
				+ "					\"GlossDef\": {"
				+ "                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\","
				+ "						\"GlossSeeAlso\": [\"GML\", \"XML\"]" + "                    },"
				+ "					\"GlossSee\": \"markup\"" + "                }" + "            }" + "        }"
				+ "    }," + "	\"a\": \"b\"," + "	\"e\": 1,"
				+ "	\"redirect_uri\": \"https://www.chironsoft.com:3280/member/login\"" + "}";
		try {
			CommonHttpClient.setRequestEntity(null, body);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get 방식 요청
	 * 
	 * @param url
	 * @param query
	 * @param printFlag 결과 출력 유무
	 * @return
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public HttpMethodBase sendGet(String url) throws IOException {
		return sendGet(url, null);
	}

	public HttpMethodBase sendGet(String url, Map query) throws IOException {
		GetMethod method = new GetMethod(url);
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.socket.timeout", TIME_OUT);

		if (!(query == null || query.isEmpty())) {
			method.setQueryString(new QueryString(query).getQuery());
		}

		method.addRequestHeader("Accept", "application/json");
		client.executeMethod(method);
		return method;
	}

	public GetMethod downloadGet(String url, Map<String, Object> query) throws IOException {
		GetMethod method = new GetMethod(url);
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.socket.timeout", TIME_OUT);

		if (!(query == null || query.isEmpty())) {
			method.setQueryString(new QueryString(query).getQuery());
		}

		client.executeMethod(method);
		return method;
	}

	/**
	 * post 방식 요청 session 유지 X
	 * 
	 * @param url
	 * @param body
	 * @return
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public EntityEnclosingMethod sendPost(String url, Map<String, Object> body) throws IOException {
		return sendPost(url, null, null, body);
	}

	public EntityEnclosingMethod sendPost(String url, Map query, Map<String, Object> body) throws IOException {
		return sendPost(url, null, query, body);
	}

	public EntityEnclosingMethod sendPost(String url, Header[] headers, Map<String, Object> body) throws IOException {
		return sendPost(url, headers, null, body);
	}

	/**
	 * post 방식 요청 session 유지 X
	 * 
	 * @param url
	 * @param headers
	 * @param query
	 * @param body
	 * @param printFlag
	 * @return
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public EntityEnclosingMethod sendPost(String url, Header[] headers, Map<String, Object> query,
			Map<String, Object> body) throws IOException {
		PostMethod method = new PostMethod(url);
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.socket.timeout", TIME_OUT);
		if (!(query == null || query.isEmpty())) {
			method.setQueryString(new QueryString(query).getQuery());
		}
		boolean existAccept = false;
		if (headers != null && headers.length > 0) {
			for (Header header : headers) {
				method.addRequestHeader(header);
				if (header.getName().equals("Accept")) {
					existAccept = true;
				}
			}
		}
		if (!existAccept) {
			method.addRequestHeader("Accept", "application/json");
			System.out.println("Accept: application/json");
		}
		if (body != null && !body.isEmpty()) {
			setRequestEntity(method, body);
		}
		client.executeMethod(method);
		return method;
	}

	/**
	 * post 방식 요청 session 유지 X
	 * 
	 * @param url
	 * @param headers
	 * @param query
	 * @param body
	 * @param printFlag
	 * @return
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public EntityEnclosingMethod sendMultipartPost(String url, Map<String, Object> body) throws IOException {
		return sendMultipartPost(url, null, body);
	}

	public EntityEnclosingMethod sendMultipartPost(String url, Map query, Map<String, Object> body) throws IOException {
		PostMethod method = new PostMethod(url);
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.socket.timeout", TIME_OUT * 6 * 5);
		if (!(query == null || query.isEmpty())) {
			method.setQueryString(new QueryString(query).getQuery());
		}
//		method.addRequestHeader("Content-Type", "multipart/form-data");
		method.addRequestHeader("Accept", "application/json");
		Object[] keyList = body.keySet().toArray();
		Part[] parts = new Part[keyList.length];
		for (int i = 0; i < keyList.length; i++) {
			String key = keyList[i].toString();
			if (keyList[i].equals("video_file_chunk")) {
				byte[] fileByte = (byte[]) body.get(key);
				FilePart fp = new FilePart(key, new ByteArrayPartSource("video.mp4", fileByte));
				fp.setContentType(null);
				fp.setTransferEncoding(null);
				parts[i] = fp;
			} else {
				StringPart sp = new StringPart(key, body.get(key).toString(), "UTF-8");
				sp.setContentType(null);
				sp.setTransferEncoding(null);
				parts[i] = sp;
			}
		}
		MultipartRequestEntity entity = new MultipartRequestEntity(parts, method.getParams());
		method.setRequestEntity(entity);
		client.executeMethod(method);

		return method;
	}

	public EntityEnclosingMethod sendPost(String url, Map<String, Object> query, File[] fileList) throws IOException {
		return sendPost(url, null, query, fileList);
	}

	public EntityEnclosingMethod sendPost2(String url, Map<String, Object> param, File[] fileList) throws IOException {
		return sendPost2(url, null, param, fileList);
	}

	/**
	 * post 방식 요청 session 유지 X multipart
	 * 
	 * @param url
	 * @param fileList
	 * @param printFlag 결과 출력 유무
	 * @return
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public EntityEnclosingMethod sendPost(String url, Header[] headers, Map<String, Object> query, File[] fileList)
			throws IOException {
		PostMethod method = new PostMethod(url);
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.socket.timeout", TIME_OUT);
		method.addRequestHeader("Content-Type", "multipart/form-data");
		if (!(query == null || query.isEmpty())) {
			method.setQueryString(new QueryString(query).getQuery());
		}
		if (headers != null && headers.length > 0) {
			for (Header header : headers) {
				method.addRequestHeader(header);
			}
		}
		method.addRequestHeader("Accept", "application/json");
		if (fileList != null && fileList.length > 0) {
			Part[] parts = new Part[fileList.length];
			for (int i = 0; i < fileList.length; i++) {
				logger.info(Files.probeContentType(fileList[i].toPath()));
				parts[i] = new FilePart("file", fileList[i], Files.probeContentType(fileList[i].toPath()), null);
			}

			MultipartRequestEntity entity = new MultipartRequestEntity(parts, method.getParams());
			method.setRequestEntity(entity);
		}
		client.executeMethod(method);
		return method;
	}

	/**
	 * post 방식 요청 session 유지 X multipart
	 * 
	 * @param url
	 * @param fileList
	 * @param printFlag 결과 출력 유무
	 * @return
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public EntityEnclosingMethod sendPost2(String url, Header[] headers, Map<String, Object> param, File[] fileList)
			throws IOException {
		PostMethod method = new PostMethod(url);
		HttpClient client = new HttpClient();

		client.getParams().setParameter("http.socket.timeout", TIME_OUT);
		method.addRequestHeader("Content-Type", "multipart/form-data");
		// query에 param 넣기
//			if (!(param == null || param.isEmpty())) {
//					method.setQueryString(new QueryString(param).getQuery());
//			}
		// header 추가
		if (headers != null && headers.length > 0) {
			for (Header header : headers) {
				method.addRequestHeader(header);
			}
		}
		method.addRequestHeader("Accept", "*/*");

		// param -> HttpMethodParams -> method.param 에 넣기
		if (param != null) {
			HttpMethodParams hmp = new HttpMethodParams();
			Iterator keyIter = param.keySet().iterator();
			List<Part> spList = new ArrayList<Part>();
			for (; keyIter.hasNext();) {
				String key = (String) keyIter.next();
//					hmp.setParameter(key, param.get(key));
				spList.add(new StringPart(key, param.get(key).toString()));
			}
			if (fileList != null && fileList.length > 0) {
				int i = 0;
				for (; i < fileList.length; i++) {
					spList.add(new FilePart("file", fileList[i]));
				}
			}
			// part에 param, file 넣기
			Part[] parts = new Part[spList.size()];
			for (int i = 0; i < parts.length; i++) {
				parts[i] = spList.get(i);
			}
			MultipartRequestEntity entity = new MultipartRequestEntity(parts, hmp);
			method.setRequestEntity(entity);
			RequestEntity entity1 = method.getRequestEntity();
		}
		client.executeMethod(method);
		return method;
	}

	/**
	 * post 방식 요청, session 유지
	 * 
	 * @param request
	 * @param url
	 * @param body
	 * @param printFlag 결과 출력 유무
	 * @return
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public EntityEnclosingMethod sendPost(String sessionId, String url, String body) throws IOException {
		PostMethod method = new PostMethod(url);
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.socket.timeout", TIME_OUT);

		method.addRequestHeader("Accept", "application/json");
		method.addRequestHeader("X-Auth-Token", sessionId);

		if (body != null && !body.isEmpty()) {
			setRequestEntity(method, body);
		}

		client.executeMethod(method);

		return method;
	}

	/**
	 * delete 방식 요청
	 * 
	 * @param url
	 * @param query
	 * @param printFlag 결과 출력 유무
	 * @return
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public HttpMethodBase sendDelete(String url) throws IOException {
		return sendDelete(url, null);
	}

	public HttpMethodBase sendDelete(String url, Map<String, Object> query) throws IOException {
		DeleteMethod method = new DeleteMethod(url);
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.socket.timeout", TIME_OUT);

		if (!(query == null || query.isEmpty())) {
			method.setQueryString(new QueryString(query).getQuery());
		}

		method.addRequestHeader("Accept", "application/json");
		client.executeMethod(method);
		return method;
	}

	public EntityEnclosingMethod sendPatch(String url, Map<String, Object> body) throws IOException {
		return sendPatch(url, null, null, body);
	}

	public EntityEnclosingMethod sendPatch(String url, Map<String, Object> query, Map<String, Object> body)
			throws IOException {
		return sendPatch(url, null, query, body);
	}

	public EntityEnclosingMethod sendPatch(String url, Header[] headers, Map<String, Object> body) throws IOException {
		return sendPatch(url, headers, null, body);
	}

	public EntityEnclosingMethod sendPatch(String url, Header[] headers, Map<String, Object> query, Map<String, Object> body) throws IOException {
		PostMethod method = new PostMethod(url) {
			@Override
			public String getName() {
				return "PATCH";
			}
		};

		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.socket.timeout", TIME_OUT);
		if (!(query == null || query.isEmpty())) {
			method.setQueryString(new QueryString(query).getQuery());
		}
		if (headers != null && headers.length > 0) {
			for (Header header : headers) {
				method.addRequestHeader(header);
			}
		}
		method.addRequestHeader("Accept", "application/json");
		if (body != null && !body.isEmpty()) {
			setRequestEntity(method, body);
		}
		client.executeMethod(method);
		return method;
	}

	public EntityEnclosingMethod sendPut(String url, String body) throws IOException {
		return sendPut(url, null, null, body);
	}

	public EntityEnclosingMethod sendPut(String url, Map<String, Object> query, String body) throws IOException {
		return sendPut(url, null, query, body);
	}

	public EntityEnclosingMethod sendPut(String url, Header[] headers, String body) throws IOException {
		return sendPut(url, headers, null, body);
	}

	public EntityEnclosingMethod sendPut(String url, Header[] headers, Map<String, Object> query, String body)
			throws IOException {
		PutMethod method = new PutMethod(url);
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.socket.timeout", TIME_OUT);
		if (!(query == null || query.isEmpty())) {
			method.setQueryString(new QueryString(query).getQuery());
		}
		if (headers != null && headers.length > 0) {
			for (Header header : headers) {
				method.addRequestHeader(header);
			}
		}
		method.addRequestHeader("Accept", "application/json");
		if (body != null && !body.isEmpty()) {
			setRequestEntity(method, body);
		}
		client.executeMethod(method);
		return method;
	}

	/**
	 * jsonString을 parameter로 적용
	 * 
	 * @param method
	 * @param stringEntity
	 * @throws Exception
	 * @author jskang
	 * @throws UnsupportedEncodingException
	 * @date 2019. 3. 5.
	 */
	private static void setRequestEntity(EntityEnclosingMethod method, String stringEntity)
			throws UnsupportedEncodingException {
		StringRequestEntity entity = new StringRequestEntity(stringEntity,
				method.getRequestHeader("Content-Type").getValue(), "utf-8");
		method.setRequestEntity(entity);
	}

	/**
	 * Request Body Set
	 * 
	 * @param method
	 * @param map
	 * @throws UnsupportedEncodingException
	 * @author jskang
	 * @date 2019. 5. 17.
	 */
	private static void setRequestEntity(EntityEnclosingMethod method, Map<String, Object> map)
			throws UnsupportedEncodingException {
		String data;
		Header header = method.getRequestHeader("Content-Type");
		if (header != null && header.getValue().equals("application/x-www-form-urlencoded")) {
			data = convertMapToQueryString(map);
		} else {
			header = new Header();
			header.setName("Content-Type");
			header.setValue("application/json");
			data = JsonUtil.toJson(map);
		}
		System.out.println(data);
		StringRequestEntity entity = new StringRequestEntity(data, header.getValue(), "utf-8");
		method.setRequestEntity(entity);
	}

	/**
	 * Query String -> Map
	 * 
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author jskang
	 * @date 2019. 5. 17.
	 */
	public static String convertMapToQueryString(Map<String, Object> map) throws UnsupportedEncodingException {
		QueryString query = new QueryString(map);
		return query.getQuery();
	}

	/**
	 * [GET/DELETE] 방식 응답 body String 변환
	 * 
	 * @param method
	 * @return
	 * @author jskang
	 * @date 2019. 5. 17.
	 */
	public static String getResponseBodyAsString(HttpMethodBase method) {
		String result = "notConnect";
		try {
			result = method.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * [POST/PUT/PATCH] 방식 응답 body String 변환
	 * 
	 * @param method
	 * @return
	 * @author jskang
	 * @date 2019. 5. 17.
	 */
	public static String getResponseBodyAsString(EntityEnclosingMethod method) {
		String result = "notConnect";
		try {
			result = method.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String toStringHeaders(Header[] headers) throws UnsupportedEncodingException {
		String result = "";
		for (Header header : headers) {
			result += header.getName() + " : " + URLDecoder.decode(header.getValue(), "UTF-8") + ", ";
		}
		return result;
	}

	/**
	 * [get/delete]방식 요청 출력
	 * 
	 * @param method
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public static String toStringRequest(HttpMethodBase method) throws IOException {
		Header[] requestHeaders = method.getRequestHeaders();
		String str = "[" + method.getName() + "]" + method.getURI() + System.lineSeparator();
		str += "HEADER: ";
		str += toStringHeaders(requestHeaders);
		return str;
	}

	/**
	 * [get/delete]방식 응답 출력
	 * 
	 * @param method
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public static String toStringResponse(HttpMethodBase method) throws IOException {
		Header[] responseHeaders = method.getResponseHeaders();
		String str = "HEADER: ";
		str += toStringHeaders(responseHeaders);
		str += System.lineSeparator();
		str += "BODY: " + method.getResponseBodyAsString();
		return str;
	}

	/**
	 * [get/delete]방식 요청 및 응답 출력
	 * 
	 * @param method
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public static String toStringRqeustAndResponse(HttpMethodBase method) throws IOException {
		String str = "================ request =================" + System.lineSeparator();
		str += toStringRequest(method);
		str += System.lineSeparator();
		str += "================ response ================" + System.lineSeparator();
		str += toStringResponse(method);
		str += System.lineSeparator();
		str += "==========================================";
		return str;
	}

	/**
	 * [post/put/patch]방식 요청 Body String 변환
	 * 
	 * @param method
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public static String getRequestBodyAsString(EntityEnclosingMethod method) throws IOException {
		String str = null;
		RequestEntity entity = method.getRequestEntity();
		if (entity != null) {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			entity.writeRequest(bytes);
			str = bytes.toString();
		}
		return str;
	}

	/**
	 * [post/put/patch]방식 요청 출력
	 * 
	 * @param method
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public static String toStringRequest(EntityEnclosingMethod method) throws IOException {
		Header[] requestHeaders = method.getRequestHeaders();

		String str = "[" + method.getName() + "]" + method.getURI() + System.lineSeparator();
		str += "HEADER: ";
		str += toStringHeaders(requestHeaders);
		str += System.lineSeparator();
		str += "BODY: ";
		RequestEntity entity = method.getRequestEntity();
		if (entity != null) {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			entity.writeRequest(bytes);
			str += bytes.toString();
		} // else {
			// str += "{" + System.lineSeparator();
			// for (HttpMethodParams hmp : method.getParams()) {
			// str += "\"" + hmp.getName() + "\": " + hmp.getValue() +
			// System.lineSeparator();
			// }
			// str += "}" + System.lineSeparator();
			// }

		return str;
	}

	/**
	 * [post/put/patch]방식 응답 출력
	 * 
	 * @param method
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public static String toStringResponse(EntityEnclosingMethod method) throws IOException {
		Header[] responseHeaders = method.getResponseHeaders();

		String str = "HEADER: ";
		str += toStringHeaders(responseHeaders);
		str += System.lineSeparator();
		str += "BODY: " + method.getResponseBodyAsString();
		return str;
	}

	/**
	 * [post/put/patch]방식 요청 및 응답 출력
	 * 
	 * @param method
	 * @author jskang
	 * @throws IOException
	 * @date 2019. 3. 5.
	 */
	public static String toStringRequestAndResponse(EntityEnclosingMethod method) throws IOException {
		Header[] responseHeaders = method.getResponseHeaders();

		String str = System.lineSeparator() + "================ request =================" + System.lineSeparator();
		str += toStringRequest(method);
		str += System.lineSeparator();
		str += "================ response ================" + System.lineSeparator();
		str += toStringResponse(method);
		str += System.lineSeparator();
		str += "==========================================";
		return str;
	}

	public static Map<String, Object> getQueryMap(String query) {
		QueryString queryString = new QueryString(query);
		return queryString.getQueryMap();
	}

	public static Map<String, Object> toMapRequestAndResponse(HttpMethodBase method) {
		Map<String, Object> result = new HashMap<>();
		try {
			result.put("method", method.getName()); // {13: get, 14: post, 15: put, 16: patch, 17: delete}
			result.put("uri", method.getURI().getHost() + method.getURI().getPath());
			String query = method.getQueryString();
			if (query != null && !query.isEmpty()) {
				result.put("query", query);
			}
			result.put("request_headers", CommonHttpClient.toStringHeaders(method.getRequestHeaders()));
			result.put("request_at", sdf.format(new Date()));
			result.put("response_code", method.getStatusCode());
			result.put("response_headers", CommonHttpClient.toStringHeaders(method.getResponseHeaders()));
			String responseBody = method.getResponseBodyAsString();
			if (responseBody != null && !responseBody.isEmpty()) {
				result.put("response_body", responseBody);
			}

			String metadata = CommonHttpClient.toStringRqeustAndResponse(method);
			if (metadata != null) {
				result.put("metadata", metadata);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static String maxSubstring(String str) {
		if (str.length() > 100000) {
			return str.substring(0, 100000);
		}
		return str;
	}

	public static Map<String, Object> toMapRequestAndResponse(EntityEnclosingMethod method) {
		Map<String, Object> result = new HashMap<>();
		try {
			result.put("method", method.getName());
			result.put("uri", method.getURI().getHost() + method.getURI().getPath());
			String query = method.getQueryString();
			if (query != null && !query.isEmpty()) {
				result.put("query", query);
			}
			result.put("request_headers", CommonHttpClient.toStringHeaders(method.getRequestHeaders()));
			String requestBody = CommonHttpClient.getRequestBodyAsString(method);
			if (requestBody != null && !requestBody.isEmpty()) {
				result.put("request_body", maxSubstring(requestBody));
			}
			result.put("request_at", sdf.format(new Date()));
			result.put("response_code", method.getStatusCode());
			result.put("response_headers", CommonHttpClient.toStringHeaders(method.getResponseHeaders()));
			String responseBody = method.getResponseBodyAsString();
			if (responseBody != null && !responseBody.isEmpty()) {
				result.put("response_body", maxSubstring(responseBody));
			}

			String metadata = CommonHttpClient.toStringRqeustAndResponse(method);
			if (metadata != null) {
				result.put("metadata", maxSubstring(metadata));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}

class QueryString {
	private String query;

	public QueryString() {
	}

	public QueryString(String query) {
		this.query = query;
	}

	public QueryString(Map<String, Object> map) throws UnsupportedEncodingException {
		if (map != null) {
			for (String key : map.keySet()) {
				add(key, map.get(key).toString());
			}
		}
	}

	private void add(String name, String value) throws UnsupportedEncodingException {
		if (query == null) {
			query = "";
		} else {
			query += "&";
		}
		query += URLEncoder.encode(name, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
	}

	public String getQuery() {
		if (query == null) {
			query = "";
		}
		return query;
	}

	public Map getQueryMap() {
		Map<String, String> result = new HashMap<>();
		String[] queryArray = query.split("&");
		for (String queryString : queryArray) {
			String[] temp = queryString.split("=");
			if (temp.length > 1) {
				result.put(temp[0], temp[1]);
			} else {
				result.put(temp[0], "");
			}
		}
		return result;
	}

	public String toString() {
		return getQuery();
	}

}