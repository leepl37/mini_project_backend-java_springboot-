package chironsoft.test.web.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import chironsoft.test.domain.model.app.MemberMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/except")
public class ApiController {

	private final MemberMapper memberMapper;

	@GetMapping("test")
	public Map<String, Object> preview(HttpServletRequest request) throws IOException {
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		return result;
	}

	@GetMapping("test02")
	public Map<String, Object> preview02(HttpServletRequest request) throws IOException {
		List<chironsoft.test.Dao.Member> allUser = memberMapper.getAllUser();
		System.out.println("test:" + allUser.size());
  		System.out.println(allUser);
		Map<String, Object> result = new HashMap<>();
		result.put("result", allUser);
		return result;
	}

	@PostMapping("/test_post")
	public void preview03(@RequestBody Map<String, String> requestData) throws IOException {
		requestData.forEach((key, value) -> {
			System.out.println("key :" + key);
			System.out.println("value :" + value);
		});
	}

	@Getter @Setter
	public class Member {
		private int age;
		private String name;
	}

	@PostMapping("/test_post02")
	public String preview04(@ModelAttribute Member member) throws IOException {
		System.out.println(member.age);
		System.out.println(member.name);
		System.out.println("hello post");
		if(member.age!=1){
			return "숫자 1이 아닙니다.";
		}
		return "숫자 1입니다.";
	}

}
