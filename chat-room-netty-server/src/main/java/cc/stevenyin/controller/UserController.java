package cc.stevenyin.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.ThumbImageConfig;

import cc.stevenyin.bo.UsersBo;
import cc.stevenyin.dao.Users;
import cc.stevenyin.service.UserService;
import cc.stevenyin.utils.FastDFSClient;
import cc.stevenyin.utils.FileUtils;
import cc.stevenyin.utils.IMoocJSONResult;
import cc.stevenyin.vo.UsersVo;

@RestController
@RequestMapping("/u")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
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
	
	@PostMapping("/uploadFaceBase64")
	public IMoocJSONResult uploadFaceBase64(@RequestBody UsersBo usersBo) throws Exception {
		return userService.uploadFaceBase64(usersBo);
	}
	
}
