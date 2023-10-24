        
package com.rent.dao.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.export.AccountPeriodBuyOutDto;
import com.rent.common.dto.export.AccountPeriodRentDto;
import com.rent.common.dto.order.AccountPeriodItemReqDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.SplitBill;

import java.util.List;

/**
 * SplitBillDao
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
public interface SplitBillDao extends IBaseDao<SplitBill> {


    /**
     * 分页查询列表
     * @param type
     * @param pageNumber
     * @param pageSize
     * @param orderIdList
     * @param shopIdList
     * @param uidList
     * @param status
     * @return
     */
    Page<SplitBill> queryPage(String type, Integer pageNumber, Integer pageSize, List<String> orderIdList, List<String> shopIdList, List<String> uidList, String status);

    /**
     * 分页查询账期列表
     * @param type
     * @param pageNumber
     * @param pageSize
     * @param accountPeriodId
     * @return
     */
    Page<SplitBill> queryPage(String type, Integer pageNumber, Integer pageSize,Long accountPeriodId);

    /**
     * 分页查询账期列表
     * @param typeRent
     * @param request
     * @return
     */
    Page<SplitBill> queryPage(String typeRent, AccountPeriodItemReqDto request);

    /**
     * 不分页查询列表
     * @param type
     * @param orderIdList
     * @param shopIdList
     * @param uidList
     * @param status
     * @return
     */
    List<SplitBill> queryList(String type, List<String> orderIdList, List<String> shopIdList, List<String> uidList, String status);

    /**
     * 获取一个订单的分账信息
     * @param orderId
     * @param period
     * @return
     */
    SplitBill getByOrderId(String orderId,Integer period);

    /**
     * 根据订单ID获取分账列表
     * @param orderId
     * @return
     */
    List<SplitBill> getByOrderIdList(String orderId);



    /**
     * 获取到支付中的分账订单列表
     * @return
     */
    List<SplitBill> getDealingList();

    /**
     * 更新状态为已结算
     * @param accountPeriodId
     */
    void updateSettled(Long accountPeriodId);

    /**
     * 买断导出
     * @param accountPeriodId
     * @return
     */
    List<AccountPeriodBuyOutDto> buyOutExport(Long accountPeriodId);

    /**
     * 租赁导出
     */
    List<AccountPeriodRentDto> rentExport(Long accountPeriodId);
}
