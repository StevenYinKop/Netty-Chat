package cc.stevenyin.service.impl;


import java.security.NoSuchAlgorithmException;

import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cc.stevenyin.dao.Users;
import cc.stevenyin.repository.UserRepository;
import cc.stevenyin.service.UserService;
import cc.stevenyin.utils.MD5Utils;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Sid sid;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean queryUsernameIsExist(String username) {
		Users user = userRepository.findByUsername(username);
		return user != null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Users login(Users user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), MD5Utils.getMD5Str(user.getPassword()));
	}

	@Override
	public Users regist(Users user) {
		Users result = new Users();
		result.setUsername(user.getUsername());
		result.setNickname(user.getUsername());
		result.setFaceImage("");
		result.setFaceImageBig("");
		result.setQrcode(""); // TODO
		result.setPassword(MD5Utils.getMD5Str(user.getPassword()));
		result.setId(sid.nextShort());
		result.setCid(user.getCid());
		userRepository.save(result);
		return result;
	}

}
