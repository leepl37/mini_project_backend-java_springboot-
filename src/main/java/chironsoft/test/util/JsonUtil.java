package chironsoft.test.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

/**
 * JSon 관련 유틸 클래스
 * 
 * @author jskang
 *
 */
public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	static Gson gson = new Gson();

	public static void main(String[] args) {
		System.out.println("=== 단위테스트 toList ===");
		String data = "['a', {1: '3'}, \"c\"]";
		List objectResult = JsonUtil.toList(data);
		System.out.println("\tㄴjson to List: " + objectResult);
		System.out.println("\tㄴList to json: " + JsonUtil.toJson(objectResult));
		System.out.println();
		
		System.out.println("=== 단위테스트 toListNumber ===");
		data = "[5, 2, 3]";
		List<Number> numberResult = JsonUtil.toListNumber(data);
		System.out.println("\tㄴjson to List<Number>: " + numberResult);
		System.out.println("\tㄴList<Number> to json: " + JsonUtil.toJson(numberResult));
		System.out.println();
		
		System.out.println("=== 단위테스트 toIntArray ===");
		data = "[4, 2, 3]";
		int[] integerResult = JsonUtil.toIntArray(data);
		String str = "[";
		for (int i = 0; i < integerResult.length; i++) {
			if (i > 0) {
				str += ", ";
			}
			str += integerResult[i];
		}
		str += "]";
		System.out.println("\tㄴjson to int[]: " + str);
		System.out.println("\tㄴint[] to json: " + JsonUtil.toJson(integerResult));
		System.out.println();

		System.out.println("=== 단위테스트 toListString ===");
		data = "['a', 'b', \"c\"]";
		List<String> stringResult = JsonUtil.toListString(data);
		System.out.println("\tㄴjson to List<String>: " + stringResult);
		System.out.println("\tㄴList<String> to json: " + JsonUtil.toJson(stringResult));
		System.out.println();

		System.out.println("=== 단위테스트 toMap ===");
		data = "{\r\n"
				+ "    \"name\": \"accounts/118134071001002488883/locations/4377352248290368783/localPosts/3016418744628752790\",\r\n"
				+ "    \"languageCode\": \"ko\",\r\n"
				+ "    \"summary\": \"$5.99 Lunch Special  Sale at Papa's Wing & Seafood!!\\r\\n\\r\\n- Half Pound Homemade Bacon Cheeseburger w/ French Fries\\r\\n- Philly  Cheese Steak Sandwich w/ French Fries\\r\\n- Chicken Philly Steak Sandwich w/ French Fries\\r\\n- Chicken Teriyaki Steak Sandwich w/ French Fries\\r\\n\\r\\nStop in today!!\",\r\n"
				+ "    \"state\": \"LIVE\",\r\n" + "    \"updateTime\": \"2019-10-16T16:12:28.810Z\",\r\n"
				+ "    \"createTime\": \"2019-10-16T16:12:28.810Z\",\r\n"
				+ "    \"searchUrl\": \"https://local.google.com/place?id=15495218520188348604&use=posts&lpsid=3016418744628752790\",\r\n"
				+ "    \"media\": [{\r\n"
				+ "            \"name\": \"accounts/118134071001002488883/locations/4377352248290368783/media/localPosts/AF1QipNtdA1fcf9TPGMAnrN7YcNhv7N5jamcbmmg33Lp\",\r\n"
				+ "            \"mediaFormat\": \"PHOTO\",\r\n"
				+ "            \"googleUrl\": \"https://lh3.googleusercontent.com/d6hfFBCOpTZnW_Vq4SFh0UVbUhaTrHfYU-DgBzSJkyhVOhjonJlRFWF8FZ5rzXolGFKl6ZMj8MSjeNl9rA\"\r\n"
				+ "        }\r\n" + "    ],\r\n" + "    \"topicType\": \"STANDARD\"\r\n" + "}\r\n" + "";
		Map gmbPost = JsonUtil.toMap(data);
		System.out.println("\tㄴjson to Map: " + gmbPost);
		System.out.println("\tㄴMap to json: " + JsonUtil.toJson(gmbPost));
		System.out.println(gmbPost.get("name").toString().replaceAll("accounts/\\d+/locations/\\d+/localPosts/", ""));
	}

	/**
	 * 객체를 json형식의 String으로 변환한다.
	 * 
	 * @param obj Object
	 * @return String
	 * @author jskang
	 */
	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}

	/**
	 * JsonString을 Map&lt;String, Object>으로 변환한다.
	 * 
	 * @param jsonString String
	 * @return Map&lt;String, Object>
	 * @author jskang
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String jsonString) {
		return gson.fromJson(jsonString, Map.class);
	}
	
	/**
	 * File을 Map&lt;String, Object>으로 변환한다.
	 * 
	 * @param jsonString String
	 * @return Map&lt;String, Object>
	 * @author jskang
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMapFromFile(String path) throws FileNotFoundException {
		JsonReader reader = new JsonReader(new FileReader(path));
		return gson.fromJson(reader, Map.class);
	}

	/**
	 * JsonString을 List&lt;T>로 변환한다.
	 * 
	 * @param <T> class
	 * @param jsonString String
	 * @param t
	 * @return Map&lt;String, Object>
	 * @author jskang
	 */
	public static <T> List toList(String jsonString, T t) {
		return gson.fromJson(jsonString, new TypeToken<List<T>>(){}.getType());
	}

	/**
	 * JsonString을 List&lt;Object>로 변환한다.
	 * 
	 * @param jsonString String
	 * @return Map&lt;String, Object>
	 * @author jskang
	 */
	@SuppressWarnings("unchecked")
	public static List toList(String jsonString) {
		return toList(jsonString, Object.class);
	}

	/**
	 * JsonString을 List&lt;String>로 변환한다
	 * 
	 * @param jsonString String
	 * @return List&lt;String>
	 * @author jskang
	 */
	@SuppressWarnings("unchecked")
	public static List<String> toListString(String jsonString) {
		return toList(jsonString, String.class);
	}

	/**
	 * JsonString을 List&lt;Integer>로 변환한다
	 * 
	 * @param jsonString String
	 * @return List&lt;Integer>
	 * @author jskang
	 */
	@SuppressWarnings("unchecked")
	public static int[] toIntArray(String jsonString) {
		List<Double> list = toList(jsonString, new TypeToken<List<Integer>>(){}.getType());
		return list.stream().mapToInt(i->i.intValue()).toArray();
	}
	
	/**
	 * JsonString을 List&lt;Number>로 변환한다
	 * 
	 * @param jsonString String
	 * @return List&lt;Number>
	 * @author jskang
	 */
	@SuppressWarnings("unchecked")
	public static List<Number> toListNumber(String jsonString) {
		return toList(jsonString, new TypeToken<List<Number>>(){}.getType());
	}

	/**
	 * JsonString을 List&lt;Map&lt;String, Object>>로 변환한다
	 * 
	 * @param jsonString String
	 * @return List&lt;Map&lt;String, Object>>
	 * @author jskang
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> toListMap(String jsonString) {
		return toList(jsonString, new TypeToken<Map<String, Object>>(){}.getClass());
	}
}