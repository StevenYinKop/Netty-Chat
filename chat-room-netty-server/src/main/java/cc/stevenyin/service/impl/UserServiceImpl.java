package cc.stevenyin.service.impl;


import java.util.Optional;

import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.ThumbImageConfig;

import cc.stevenyin.bo.UsersBo;
import cc.stevenyin.dao.Users;
import cc.stevenyin.exception.BusinessException;
import cc.stevenyin.exception.CodeMsg;
import cc.stevenyin.repository.UserRepository;
import cc.stevenyin.service.UserService;
import cc.stevenyin.utils.FastDFSClient;
import cc.stevenyin.utils.FileUtils;
import cc.stevenyin.utils.IMoocJSONResult;
import cc.stevenyin.utils.MD5Utils;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Sid sid;
	@Autowired
	private FastDFSClient fastDFSClient;
	@Autowired
	private ThumbImageConfig thumbImageConfig;
	
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
	@Transactional(propagation = Propagation.SUPPORTS)
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

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public IMoocJSONResult uploadFaceBase64(UsersBo usersBo) throws Exception {
		Optional<Users> userOptional = userRepository.findById(usersBo.getId());
		Users dbUser = userOptional.orElseThrow(() -> new BusinessException(CodeMsg.USER_NOT_EXIST));
		// 获取前端传过来的base64字符串, 然后转换为文件对象再上传
		String base64Data = usersBo.getFaceData();
		String userFacePath = createUserFacePath(dbUser);
		FileUtils.base64ToFile(userFacePath, base64Data);
		MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
		String storePath = fastDFSClient.uploadBase64(faceFile);
		String thumbPath = getThumbUrl(storePath);
		logger.info("upload face file successfully! user nickname: {}, store_path: {}, thumb_path: {}", dbUser.getNickname(), storePath, thumbPath);
		updateUserThumbAndFaceFile(dbUser, storePath, thumbPath);
		return IMoocJSONResult.ok(dbUser);
	}


	private void updateUserThumbAndFaceFile(Users dbUser, String storePath, String thumbPath) {
		dbUser.setId(dbUser.getId());
		dbUser.setFaceImageBig(storePath);
		dbUser.setFaceImage(thumbPath);
		userRepository.save(dbUser);		
	}

	private String getThumbUrl(String storePath) {
		String[] storePathArray = storePath.split("\\.");
		int thumbWidth = thumbImageConfig.getWidth();
		int thumbHeight = thumbImageConfig.getHeight();
		String suffix = "_" + thumbWidth + "x" + thumbHeight + ".";
		return storePathArray[0] + suffix + storePathArray[1];
	}
	
	private String createUserFacePath(Users user) {
		return user.getId() + "_userface64.png";
	}
}
