package com.rank.admin.controller.system;

import com.redimybase.framework.web.BaseController;
import com.rank.manager.security.entity.AppUserEntity;
import com.rank.manager.security.mapper.AppUserMapper;
import com.rank.manager.security.service.impl.AppUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app用户接口
 * Created by Vim 2019/2/14 16:20
 *
 * @author Vim
 */
@RestController
@RequestMapping("app/user")
public class AppUserController extends BaseController<String, AppUserEntity, AppUserMapper, AppUserServiceImpl> {



    @Autowired
    private AppUserServiceImpl appUserService;

    @Override
    protected AppUserServiceImpl getService() {
        return appUserService;
    }
}
