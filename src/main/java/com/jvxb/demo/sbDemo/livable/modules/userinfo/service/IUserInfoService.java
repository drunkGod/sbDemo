package com.jvxb.demo.sbDemo.livable.modules.userinfo.service;

import java.util.List;

import com.jvxb.demo.sbDemo.livable.modules.base.service.IBaseService;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

public interface IUserInfoService extends IBaseService{

	void insertBatch(List<PageData> listPd);

}
