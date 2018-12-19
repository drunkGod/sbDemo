package com.jvxb.demo.sbDemo.livable.modules.system.service;

import com.jvxb.demo.sbDemo.base.entity.system.SysUser;
import com.jvxb.demo.sbDemo.livable.modules.base.service.IBaseService;

public interface ISysUserService extends IBaseService {
	
	/**
	 * 检查用户是否存在
	 */
	SysUser checkExist(String username, String password);

	/**
	 * 修改密码
	 */
	void updatePassword(Integer id, String newPass);

}
