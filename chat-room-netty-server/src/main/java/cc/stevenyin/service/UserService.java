package cc.stevenyin.service;

import cc.stevenyin.dao.Users;

public interface UserService {

	public boolean queryUsernameIsExist(String username);

	public Users login(Users user);

	public Users regist(Users user);
}
