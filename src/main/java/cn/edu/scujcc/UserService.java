package cn.edu.scujcc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public @Service class UserService {
	@Autowired
	private UserRepository repo;
	private static final Logger logger =  LoggerFactory.getLogger(UserService.class);

	/**
	 * 用户注册，即把用户信息保存下来。
	 * @param user 注册用户信息
	 * @return 保存后的用户信息（包含数据库id）
	 */
	public User register(User user) throws UserExistException {
		User result = null;
		//决断用户名是否已在数据库中存在
		User saved = repo.findFirstByUsername(user.getUsername());
		if (saved == null) {
			result = repo.save(user);
		} else { 
			//用户已存在
			logger.error("用户"+user.getUsername()+"已存在。");
			throw new UserExistException();
		}
		return result;
	}

	/**
	 * 用户登录。
	 * @param user
	 * @return
	 */
	public User login(String u, String p) {
		User result = null;
		result = repo.findOneByUsernameAndPassword(u, p);
		return result;
	}
}
