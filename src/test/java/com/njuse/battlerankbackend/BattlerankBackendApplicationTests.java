package com.njuse.battlerankbackend;

import com.njuse.battlerankbackend.controller.UserController;
import com.njuse.battlerankbackend.service.UserService;
import com.njuse.battlerankbackend.vo.ResultVO;
import com.njuse.battlerankbackend.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class BattlerankBackendApplicationTests {

	@Autowired
	UserController userController;
	@Autowired
	UserService userService;

	@Test
	void UserLoad() {
		UserVO cwh = new UserVO();
		cwh.setPhone("111");
		cwh.setPassword("222");
		//ResponseEntity<Boolean> register = userController.register(cwh);
		UserVO hyf = new UserVO();
		hyf.setPhone("333");
		hyf.setPhone("444");
		ResultVO<Boolean> register = userController.register(hyf);
		assert register.getResult();
		assert userController.login(hyf).getResult();
		assert userController.login(cwh).getResult();

	}

}
