        
package com.rent.service.user.impl;

import com.rent.common.converter.user.UserWordHistoryConverter;
import com.rent.common.dto.user.UserWordHistoryDto;
import com.rent.dao.user.UserWordHistoryDao;
import com.rent.model.user.UserWordHistory;
import com.rent.service.user.UserWordHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户搜索记录Service
 *
 * @author zhao
 * @Date 2020-06-18 15:41
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserWordHistoryServiceImpl implements UserWordHistoryService {

    private final UserWordHistoryDao userWordHistoryDao;

    @Override
    public Long addUserWordHistory(UserWordHistoryDto request) {
        UserWordHistory userWordHistory = userWordHistoryDao.getByUidAndWord(request.getUid(),request.getWord());
        if(userWordHistory != null){
            //如果有该搜索记录，记录count+1
            userWordHistory.setCount(userWordHistory.getCount()+1);
            userWordHistoryDao.updateById(userWordHistory);
            return userWordHistory.getId();
        }else{
            //如果没有，新增记录
            UserWordHistory model = UserWordHistoryConverter.dto2Model(request);
            model.setCount(1);
            userWordHistoryDao.save(model);
            return model.getId();
        }
     }

    @Override
    public List<UserWordHistoryDto> getUserWordHistory(String uid) {
        List<UserWordHistory> list =  userWordHistoryDao.getTenUserWordHistory(uid);
        return UserWordHistoryConverter.modelList2DtoList(list);
    }

    @Override
    public Boolean deleteUserWordHistory(String uid) {
        return userWordHistoryDao.deleteUserWordHistory(uid);
    }


}