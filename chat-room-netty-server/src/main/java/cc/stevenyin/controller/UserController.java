package cc.stevenyin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.stevenyin.dao.Users;
import cc.stevenyin.service.UserService;
import cc.stevenyin.utils.IMoocJSONResult;
import cc.stevenyin.vo.UsersVo;

@RestController
@RequestMapping("/u")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping("/registOrLogin")
	public IMoocJSONResult registOrLogin(@RequestBody Users user) {
		// 判断用户名密码不能为空
		if(StringUtils.isBlank(user.getUsername())
				||StringUtils.isBlank(user.getPassword()))
				return IMoocJSONResult.errorMsg("error!");
		boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
		Users result;
		if (usernameIsExist) {
			result = userService.login(user);
			if (result == null) {
				return IMoocJSONResult.errorMsg("用户名或密码不正确");
			}
		} else {
			result = userService.regist(user);
		}
		UsersVo vo = new UsersVo();
		BeanUtils.copyProperties(result, vo);
		return IMoocJSONResult.ok(vo);
	}
}
