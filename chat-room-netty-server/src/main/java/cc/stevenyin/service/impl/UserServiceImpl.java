package cc.stevenyin.service.impl;


import org.n3r.idworker.Sid;
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
		Users result = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		return result;
	}

	@Override
	public Users regist(Users user) {
		Users result = new Users();
		try {
			result.setNickname(user.getUsername());
			result.setFaceImage("");
			result.setFaceImageBig("");
			result.setQrcode(""); // TODO
			result.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			result.setId(sid.nextShort());
			userRepository.save(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
