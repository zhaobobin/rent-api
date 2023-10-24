truncate  table ct_account_period;  # 账期表
truncate  table ct_account_period_progress;  # 结算流程
truncate  table ct_account_period_remark;  # 账期备注
truncate  table ct_alipay_face_auth_record;
truncate  table ct_alipay_freeze;  # 预授权流水表
truncate  table ct_alipay_trade_create;  # APP支付记录表
truncate  table ct_alipay_trade_page_pay;  # APP支付记录表
truncate  table ct_alipay_trade_pay;  # 支付宝订单支付表
truncate  table ct_alipay_trade_refund;  # 订单退款信息表
truncate  table ct_alipay_trade_serial;  # 支付流水表
truncate  table ct_alipay_transfer_record;
truncate  table ct_alipay_unfreeze;  # 支付宝资金授权解冻表
truncate  table ct_aliyun_sms_send_serial;
# truncate  table ct_aliyun_sms_template;  # 阿里云短信模板
truncate  table ct_ant_chain_insurance_log;
truncate  table ct_ant_chain_log;
truncate  table ct_ant_chain_shield_log;  # 蚁盾分日志
truncate  table ct_ant_chain_step;
truncate  table ct_audit_user;  # 信审人员
#truncate  table ct_backstage_department;  # 后台部门
delete from ct_backstage_department where id not in(1,2,3);

#truncate  table ct_backstage_department_function;  # 后台部门可以使用的功能
delete from ct_backstage_department_function where department_id not in(1,2,3);

#truncate  table ct_backstage_department_user;  # 后台部门用户映射表

#truncate  table ct_backstage_function;  # 后台功能点

#truncate  table ct_backstage_user;  # 后台用户
delete from ct_backstage_user where id != 1;
truncate  table ct_backstage_user_function;  # 后台用户可以用的功能点

#truncate  table ct_buyun_sms_send_serial; #布云短信记录
truncate  table ct_buyun_sms_template;  # 布云短信模板

truncate  table ct_channel_account_period;  # 渠道结算表
truncate  table ct_channel_account_period_progress;  # 结算流程
truncate  table ct_channel_account_period_remark;  # 渠道账期备注
truncate  table ct_channel_split_bill;  # 渠道分账信息
truncate  table ct_channel_user_orders;  # 渠道用户下单表

#truncate  table ct_config;  # 配置信息
#truncate  table ct_config_banner;  # Banner配置
#truncate  table ct_config_column;  # 栏目配置
#truncate  table ct_config_cubes;  # 豆腐块配置
#truncate  table ct_config_icon;  # 金刚区配置
#truncate  table ct_district;  # 区域表
truncate  table ct_e_sign_user;  # e签宝用户信息映射
truncate  table ct_fee_bill;  # 分账信息
truncate  table ct_fee_bill_ticket;  # 费用开票记录
truncate  table ct_hot_search_config;  # 热门板块配置
truncate  table ct_lite_coupon_package;  # lite优惠券大礼包
truncate  table ct_lite_coupon_template;  # lite优惠券模版
truncate  table ct_lite_coupon_template_range;  # lite优惠券使用范围
truncate  table ct_lite_user_coupon;  # lite用户优惠券表
truncate  table ct_marketing_channel;  # 渠道账号分佣表
truncate  table ct_marketing_channel_link;  # 渠道下的营销码汇总表
#truncate  table ct_material_center_category;  # 素材中心-素材分类
#truncate  table ct_material_center_item;  # 素材中心文件
truncate  table ct_ope_category;
truncate  table ct_ope_category_product;  # 前台类目商品表
truncate  table ct_ope_config;  # 首页运营配置表
truncate  table ct_ope_custom_product;  # 自定义tab产品挂载表
truncate  table ct_ope_index_shop_banner;  # 商家详情轮播配置
truncate  table ct_ope_notice;  # 商家中心通知
truncate  table ct_ope_notice_item;  # 公告常见问题tab内容
truncate  table ct_ope_notice_tab;  # 公告常见问题tab
truncate  table ct_order_additional_services;  # 订单增值服务信息表
truncate  table ct_order_address;  # 订单地址表
truncate  table ct_order_audit_record;  # 订单审核记录表
truncate  table ct_order_audit_user;  # 信审人员
truncate  table ct_order_by_stages_0;  # 用户订单分期表
truncate  table ct_order_by_stages_1;  # 用户订单分期表
truncate  table ct_order_by_stages_2;  # 用户订单分期表
truncate  table ct_order_by_stages_3;  # 用户订单分期表
truncate  table ct_order_by_stages_4;  # 用户订单分期表
truncate  table ct_order_by_stages_5;  # 用户订单分期表
truncate  table ct_order_by_stages_6;  # 用户订单分期表
truncate  table ct_order_by_stages_7;  # 用户订单分期表
truncate  table ct_order_by_stages_8;  # 用户订单分期表
truncate  table ct_order_by_stages_9;  # 用户订单分期表
truncate  table ct_order_center_product;  # 小程序订单中心商品信息
truncate  table ct_order_center_sync_log;  # 小程序订单中心商品信息
truncate  table ct_order_complaints;  # 订单投诉
truncate  table ct_order_complaints_image;  # 订单投诉图片凭证
#truncate  table ct_order_complaints_type;  # 订单投诉类型
truncate  table ct_order_contract;  # 订单合同
truncate  table ct_order_hasten;
truncate  table ct_order_location_address;  # 订单当前位置定位表
truncate  table ct_order_pay_deposit;  # 用户支付押金表
truncate  table ct_order_pay_deposit_log;  # 用户支付押金表修改日志
truncate  table ct_order_remark;  # 订单备注
truncate  table ct_order_report;  # 订单报表统计
truncate  table ct_order_settlement;  # 订单结算记录表
truncate  table ct_order_shop_close;  # 商家风控关单表
truncate  table ct_order_snapshots;  # 订单快照表
truncate  table ct_order_yx_status;
#truncate  table ct_page_element_config;
#truncate  table ct_platform_express;  # 平台物流表
#truncate  table ct_platform_spec;  # 平台商品规格属性分类表
truncate  table ct_product;  # 商品表
truncate  table ct_product_additional_services;  # 商品增值服务表
truncate  table ct_product_audit_log;  # 商品审核日志表
truncate  table ct_product_cycle_prices;  # 商品周期价格表
truncate  table ct_product_evaluation;  # 商品评论
truncate  table ct_product_give_back_addresses;  # 商品归还地址
truncate  table ct_product_image;  # 商品主图表
truncate  table ct_product_label;  # 商品租赁标签
truncate  table ct_product_parameter;  # 商品参数信息表
truncate  table ct_product_service_marks;  # 商品服务标
truncate  table ct_product_sku_values;  # 商品sku规格属性value表
truncate  table ct_product_skus;  # 商品sku表
truncate  table ct_product_snapshots;  # 商品快照表
truncate  table ct_product_spec;  # 商品属性规格表
truncate  table ct_product_spec_value;  # 商品规格属性value表
truncate  table ct_shop;  # 店铺表
truncate  table ct_shop_additional_services;  # 店铺增值服务表
truncate  table ct_shop_attract;  # 招商表
truncate  table ct_shop_enterprise_certificates;  # 店铺资质证书表
truncate  table ct_shop_enterprise_infos;  # 店铺资质表
truncate  table ct_shop_fund_flow;  # 店铺资金变动流水
truncate  table ct_shop_give_back_addresses;  # 店铺归还地址表
truncate  table ct_shop_split_bill_account;
truncate  table ct_shop_split_bill_rule;
truncate  table ct_shop_withdraw_apply;  # 店铺资金变动流水
truncate  table ct_split_bill;  # 分账信息
truncate  table ct_swiping_activity_order;  # 刷单活动表
truncate  table ct_sx_report_record;  # 用户报告表
#truncate  table ct_tab;  # 简版小程序tab配置信息
truncate  table ct_tab_product;  # 小程序tab商品
truncate  table ct_transfer_order_record;  # 转单记录表
truncate  table ct_user;  # 用户主体表
truncate  table ct_user_address;  # 用户地址表
truncate  table ct_user_certification;  # 用户认证表
truncate  table ct_user_collection;  # 用户收藏表
truncate  table ct_user_emergency_contact;  # 用户紧急联系人
truncate  table ct_user_order_buy_out;  # 买断表
truncate  table ct_user_order_cashes;  # 用户订单金额表
truncate  table ct_user_order_items;  # 用户订单商品表
truncate  table ct_user_orders;  # 用户订单表
truncate  table ct_user_orders_purchase;  # 购买订单表
truncate  table ct_user_orders_status_tranfer;  # 订单状态流转表
truncate  table ct_user_point;  # 用户活动埋点统计
truncate  table ct_user_third_info;  # 用户第三方信息表
truncate  table ct_user_word_history;  # 用户搜索记录
truncate  table ct_xunda_sms_send_serial;  # 讯达短信发送记录
#truncate  table ct_xunda_sms_template;  # 讯达短信模板
truncate  table ct_yfb_bank_card;  # 用户易付宝签约的银行卡
truncate  table ct_yfb_trade_pay;  # 易付宝订单支付表
truncate  table ct_yfb_trade_refund;  # 订单退款信息表
truncate  table ct_yfb_trade_serial;  # 易付宝支付流水表
truncate  table ct_yx_credit_appear;  # 云信征信上报账单信息表
truncate  table ct_yx_credit_report;  # 云信征信报告信息表
truncate  table qrtz_blob_triggers;
truncate  table qrtz_calendars;
truncate  table qrtz_cron_triggers;
truncate  table qrtz_fired_triggers;

truncate  table qrtz_locks;
truncate  table qrtz_paused_trigger_grps;
truncate  table qrtz_scheduler_state;
truncate  table qrtz_simple_triggers;
truncate  table qrtz_simprop_triggers;
delete from qrtz_triggers;
#truncate  table qrtz_triggers;
#truncate  table qrtz_job_details;
delete from qrtz_job_details;
truncate  table worker_node;