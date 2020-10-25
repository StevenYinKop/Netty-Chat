package cc.stevenyin.service;

import cc.stevenyin.bo.UsersBo;
import cc.stevenyin.dao.Users;
import cc.stevenyin.utils.IMoocJSONResult;

public interface UserService {

	public boolean queryUsernameIsExist(String username);

	public Users login(Users user);

	public Users regist(Users user);

	public IMoocJSONResult uploadFaceBase64(UsersBo usersBo) throws Exception;
}
