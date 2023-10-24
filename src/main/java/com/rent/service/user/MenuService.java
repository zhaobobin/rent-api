package com.rent.service.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.request.MenuPageReq;
import com.rent.common.dto.backstage.resp.AuthPageResp;
import com.rent.common.dto.user.ConfigDto;
import com.rent.common.dto.user.ConfigReqDto;

public interface MenuService {
    Page<AuthPageResp> page(MenuPageReq request);

    boolean add(MenuPageReq request);
}
