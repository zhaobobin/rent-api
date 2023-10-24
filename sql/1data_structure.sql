/*
 表结构
 */

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ct_account_period
-- ----------------------------
DROP TABLE IF EXISTS `ct_account_period`;
CREATE TABLE `ct_account_period` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` varchar(64) DEFAULT NULL COMMENT '商家id',
  `shop_name` varchar(255) DEFAULT NULL COMMENT '商家名称',
  `account_identity` varchar(255) DEFAULT NULL COMMENT '结算账号',
  `account_name` varchar(255) DEFAULT NULL COMMENT '结算账号名称',
  `status` varchar(20) DEFAULT NULL COMMENT '状态：待结算；待审核；待支付；已支付',
  `total_amount` decimal(20,2) DEFAULT NULL COMMENT '结算总金额',
  `total_settle_amount` decimal(20,2) DEFAULT NULL COMMENT '结算金额',
  `total_brokerage` decimal(20,2) DEFAULT NULL COMMENT '佣金',
  `rent_amount` decimal(20,2) DEFAULT NULL COMMENT '常规订单结算总金额',
  `rent_settle_amount` decimal(20,2) DEFAULT NULL COMMENT '常规订单结算金额',
  `rent_brokerage` decimal(20,2) DEFAULT NULL COMMENT '常规订单佣金',
  `purchase_amount` decimal(20,2) DEFAULT NULL COMMENT '购买订单结算总金额',
  `purchase_settle_amount` decimal(20,2) DEFAULT NULL COMMENT '购买订单结算金额',
  `purchase_brokerage` decimal(20,2) DEFAULT NULL COMMENT '购买订单佣金',
  `buyout_amount` decimal(20,2) DEFAULT NULL COMMENT '买断订单结算总金额',
  `buyout_settle_amount` decimal(20,2) DEFAULT NULL COMMENT '买断订单结算金额',
  `buyout_brokerage` decimal(20,2) DEFAULT NULL COMMENT '买断订单佣金',
  `settle_date` datetime DEFAULT NULL COMMENT '结算日期',
  `settle_amount` decimal(20,2) DEFAULT NULL COMMENT '实际结算金额',
  `settle_title` varchar(255) DEFAULT NULL COMMENT '转账业务标题',
  `settle_remark` varchar(255) DEFAULT NULL COMMENT '业务备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1481 COMMENT='账期表';

-- ----------------------------
-- Table structure for ct_account_period_progress
-- ----------------------------
DROP TABLE IF EXISTS `ct_account_period_progress`;
CREATE TABLE `ct_account_period_progress` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_period_id` bigint NOT NULL COMMENT '账期ID',
  `operator` varchar(255) NOT NULL COMMENT '操作人信息',
  `status` varchar(255) NOT NULL COMMENT '流转状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12398 COMMENT='结算流程';

-- ----------------------------
-- Table structure for ct_account_period_remark
-- ----------------------------
DROP TABLE IF EXISTS `ct_account_period_remark`;
CREATE TABLE `ct_account_period_remark` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_period_id` bigint NOT NULL COMMENT '账期ID',
  `backstage_user_name` varchar(255) NOT NULL COMMENT '备注人姓名',
  `content` varchar(255) NOT NULL COMMENT '备注内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 COMMENT='账期备注';

-- ----------------------------
-- Table structure for ct_alipay_face_auth_record
-- ----------------------------
DROP TABLE IF EXISTS `ct_alipay_face_auth_record`;
CREATE TABLE `ct_alipay_face_auth_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `certify_id` varchar(255) DEFAULT NULL COMMENT '认证id',
  `uid` varchar(255) DEFAULT NULL COMMENT 'uid',
  `user_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `id_card` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `status` tinyint(1) DEFAULT '1' COMMENT '1  初始化 2 成功 ',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_certify_id` (`certify_id`(191)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=943;

-- ----------------------------
-- Table structure for ct_alipay_freeze
-- ----------------------------
DROP TABLE IF EXISTS `ct_alipay_freeze`;
CREATE TABLE `ct_alipay_freeze` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) DEFAULT NULL COMMENT '商户订单',
  `uid` varchar(64) DEFAULT NULL COMMENT 'uid',
  `payer_user_id` varchar(64) DEFAULT NULL COMMENT '付款方id',
  `pay_id` varchar(64) DEFAULT NULL COMMENT '支付订单',
  `auth_no` varchar(64) DEFAULT NULL COMMENT '支付宝的资金授权订单号',
  `out_order_no` varchar(64) DEFAULT NULL COMMENT '商户订单号',
  `out_request_no` varchar(64) DEFAULT NULL COMMENT '商户本次资金操作的请求流水号 ',
  `operation_id` varchar(64) DEFAULT NULL COMMENT '支付宝的资金操作流水号',
  `request` mediumtext COMMENT '请求参数',
  `response` mediumtext COMMENT '请求响应参数',
  `amount` varchar(255) DEFAULT NULL COMMENT '本次操作冻结的金额，单位为：元（人民币）',
  `status` char(2) DEFAULT NULL COMMENT '状态 01：支付中 02：成功 03：失败',
  `gmt_trans` datetime DEFAULT NULL COMMENT '预授权成功时间',
  `pre_auth_type` varchar(32) DEFAULT NULL COMMENT '预授权类型',
  `credit_amount` decimal(11,2) DEFAULT NULL COMMENT '信用冻结金额',
  `fund_amount` decimal(11,2) DEFAULT NULL COMMENT '自有资金冻结金额',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=294337 COMMENT='预授权流水表';

-- ----------------------------
-- Table structure for ct_alipay_trade_create
-- ----------------------------
DROP TABLE IF EXISTS `ct_alipay_trade_create`;
CREATE TABLE `ct_alipay_trade_create` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` varchar(64) NOT NULL COMMENT '订单号',
  `period` varchar(32) DEFAULT NULL COMMENT '期次',
  `out_trade_no` varchar(64) NOT NULL COMMENT '商户订单号',
  `trade_no` varchar(64) NOT NULL COMMENT '交易号',
  `subject` varchar(255) DEFAULT NULL COMMENT '主题',
  `request` text COMMENT '请求参数',
  `response` text COMMENT '响应参数',
  `trade_type` char(2) NOT NULL COMMENT '交易类型EnumTradeType 01:下单 02：关单 03：账单代扣 04：账单主动支付 05：结算单支付 06：充值 07：买断 08：商家分账',
  `amount` decimal(11,2) NOT NULL COMMENT '交易金额',
  `status` char(2) NOT NULL COMMENT '00:初始化  01：支付中 02：支付成功 03：支付失败',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=462 COMMENT='APP支付记录表';

-- ----------------------------
-- Table structure for ct_alipay_trade_page_pay
-- ----------------------------
DROP TABLE IF EXISTS `ct_alipay_trade_page_pay`;
CREATE TABLE `ct_alipay_trade_page_pay` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shop_id` varchar(64) NOT NULL COMMENT '店铺编号',
  `out_trade_no` varchar(64) NOT NULL COMMENT '商户订单号',
  `trade_no` varchar(64) NOT NULL COMMENT '交易号',
  `subject` varchar(255) DEFAULT NULL COMMENT '主题',
  `request` text COMMENT '请求参数',
  `response` text COMMENT '响应参数',
  `trade_type` char(2) NOT NULL COMMENT '交易类型EnumTradeType 01:下单 02：关单 03：账单代扣 04：账单主动支付 05：结算单支付 06：充值 07：买断 08：商家分账',
  `amount` decimal(11,2) NOT NULL COMMENT '交易金额',
  `status` char(2) NOT NULL COMMENT '00:初始化  01：支付中 02：支付成功 03：支付失败',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=683 COMMENT='APP支付记录表';

-- ----------------------------
-- Table structure for ct_alipay_trade_pay
-- ----------------------------
DROP TABLE IF EXISTS `ct_alipay_trade_pay`;
CREATE TABLE `ct_alipay_trade_pay` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `subject` varchar(155) DEFAULT NULL COMMENT '标题',
  `auth_code` varchar(64) DEFAULT NULL COMMENT '支付授权码',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户订单号',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单号',
  `period` varchar(12) DEFAULT NULL COMMENT '期次',
  `amount` varchar(25) DEFAULT NULL COMMENT '金额',
  `status` char(2) DEFAULT NULL COMMENT '状态',
  `request` mediumtext COMMENT '请求参数',
  `response` mediumtext COMMENT '返回参数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=286375 COMMENT='支付宝订单支付表';

-- ----------------------------
-- Table structure for ct_alipay_trade_refund
-- ----------------------------
DROP TABLE IF EXISTS `ct_alipay_trade_refund`;
CREATE TABLE `ct_alipay_trade_refund` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` varchar(64) NOT NULL COMMENT '订单编号',
  `out_trade_no` varchar(64) NOT NULL COMMENT '订单支付时传入的商户订单号',
  `trade_no` varchar(64) NOT NULL COMMENT '支付宝交易号',
  `out_request_no` varchar(64) DEFAULT NULL COMMENT '标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。',
  `refund_amount` decimal(10,2) NOT NULL COMMENT '退款金额',
  `refund_reason` varchar(255) DEFAULT NULL COMMENT '退款原因',
  `request` text COMMENT '请求参数',
  `response` text COMMENT '响应参数',
  `status` char(2) DEFAULT NULL COMMENT '支付状态 00：初始化 01：支付中 02：成功 03：失败',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=863 COMMENT='订单退款信息表';

-- ----------------------------
-- Table structure for ct_alipay_trade_serial
-- ----------------------------
DROP TABLE IF EXISTS `ct_alipay_trade_serial`;
CREATE TABLE `ct_alipay_trade_serial` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` varchar(64) NOT NULL COMMENT '订单编号',
  `period` varchar(64) DEFAULT NULL COMMENT '期数（仅分期还款该值存在）',
  `uid` varchar(64) DEFAULT NULL COMMENT 'uid',
  `user_id` varchar(64) DEFAULT NULL COMMENT '支付宝user_id',
  `out_order_no` varchar(64) NOT NULL COMMENT '商户订单号',
  `serial_no` varchar(64) DEFAULT NULL COMMENT '流水号',
  `amount` decimal(16,2) DEFAULT NULL COMMENT '交易金额',
  `pay_type` char(2) NOT NULL COMMENT '支付类型 01：预授权冻结 02：预授权转支付 03：订单创建 04：预授权转支付 05：退款 06：解冻 07：转账',
  `trade_type` char(2) NOT NULL COMMENT '交易类型 01:下单 02：关单 03：账单代扣 04：账单主动支付 05：结算单支付 06：充值 07：买断 08：商家分账',
  `status` char(2) NOT NULL COMMENT '状态  00:初始化 01:支付中 02：成功 03：失败',
  `channel_id` varchar(10) NOT NULL COMMENT '渠道编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16167 COMMENT='支付流水表';

-- ----------------------------
-- Table structure for ct_alipay_transfer_record
-- ----------------------------
DROP TABLE IF EXISTS `ct_alipay_transfer_record`;
CREATE TABLE `ct_alipay_transfer_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `out_biz_no` varchar(128) DEFAULT NULL COMMENT '外部订单号',
  `identity` varchar(255) DEFAULT NULL COMMENT '支付宝账号',
  `name` varchar(255) DEFAULT NULL COMMENT '支付宝实名信息',
  `uid` varchar(128) DEFAULT NULL COMMENT 'uid',
  `user_id` varchar(64) DEFAULT NULL COMMENT '支付宝userId',
  `amount` varchar(255) DEFAULT NULL COMMENT '金额',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `error_msg` varchar(255) DEFAULT NULL COMMENT '错误信息',
  `req_params` varchar(1024) DEFAULT NULL COMMENT '请求参数',
  `resp` varchar(1024) DEFAULT NULL COMMENT '响应参数',
  `query_error_info` varchar(255) DEFAULT NULL COMMENT '查询错误信息',
  `query_resp` varchar(1024) DEFAULT NULL COMMENT '查询响应结果',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `out_biz_no_idx` (`out_biz_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15859 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ct_alipay_unfreeze
-- ----------------------------
DROP TABLE IF EXISTS `ct_alipay_unfreeze`;
CREATE TABLE `ct_alipay_unfreeze` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `request` mediumtext COMMENT '请求参数',
  `response` mediumtext COMMENT '响应参数',
  `auth_no` varchar(255) DEFAULT NULL COMMENT '支付宝资金授权订单号',
  `order_id` varchar(64) DEFAULT NULL COMMENT '所属租单订单号',
  `operation_id` varchar(255) DEFAULT NULL COMMENT '支付宝资金操作流水号',
  `uid` varchar(255) DEFAULT NULL,
  `unfreeze_request_no` varchar(64) DEFAULT NULL COMMENT '解冻流水号',
  `amount` decimal(12,2) NOT NULL COMMENT '解冻金额',
  `remark` varchar(255) DEFAULT NULL COMMENT '解冻说明',
  `status` smallint NOT NULL COMMENT '订单状态 1 等待解冻,2 解冻成功',
  PRIMARY KEY (`id`),
  KEY `order_id_index` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39854 COMMENT='支付宝资金授权解冻表';

-- ----------------------------
-- Table structure for ct_ant_chain_insurance_log
-- ----------------------------
DROP TABLE IF EXISTS `ct_ant_chain_insurance_log`;
CREATE TABLE `ct_ant_chain_insurance_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) DEFAULT NULL,
  `operate` varchar(16) DEFAULT NULL,
  `req_params` longtext ,
  `resp` longtext ,
  `status` varchar(16) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=232 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ct_ant_chain_log
-- ----------------------------
DROP TABLE IF EXISTS `ct_ant_chain_log`;
CREATE TABLE `ct_ant_chain_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) DEFAULT NULL,
  `type` varchar(16) DEFAULT NULL,
  `req_params` longtext ,
  `resp` longtext ,
  `status` varchar(16) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3212 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ct_ant_chain_shield_log
-- ----------------------------
DROP TABLE IF EXISTS `ct_ant_chain_shield_log`;
CREATE TABLE `ct_ant_chain_shield_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `uid` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `id_card` varchar(64) DEFAULT NULL COMMENT '身份证号码',
  `request` text COMMENT '请求参数',
  `response` text COMMENT '响应结果',
  `status_code` varchar(64) DEFAULT NULL COMMENT '响应状态码',
  `score` varchar(64) DEFAULT NULL COMMENT '分值',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_no_idx` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=397 COMMENT='蚁盾分日志';

-- ----------------------------
-- Table structure for ct_ant_chain_step
-- ----------------------------
DROP TABLE IF EXISTS `ct_ant_chain_step`;
CREATE TABLE `ct_ant_chain_step` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `shield_score` varchar(64) DEFAULT NULL COMMENT '蚁盾分',
  `sync_to_chain` int DEFAULT NULL,
  `insure` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=314;

-- ----------------------------
-- Table structure for ct_backstage_department
-- ----------------------------
DROP TABLE IF EXISTS `ct_backstage_department`;
CREATE TABLE `ct_backstage_department` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '部门名称',
  `description` varchar(255) NOT NULL COMMENT '只能描述',
  `platform` varchar(8) NOT NULL COMMENT '部门所属平台 OPE：运营平台 BUSINESS:商家平台',
  `shop_id` varchar(64) NOT NULL COMMENT '所属店铺ID ，运营平台固定为OPE',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `platform_shop_idx` (`platform`,`shop_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=69 COMMENT='后台部门';

-- ----------------------------
-- Table structure for ct_backstage_department_function
-- ----------------------------
DROP TABLE IF EXISTS `ct_backstage_department_function`;
CREATE TABLE `ct_backstage_department_function` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `department_id` bigint NOT NULL COMMENT '部门ID',
  `function_id` bigint NOT NULL COMMENT '功能CODE',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19240 COMMENT='后台部门可以使用的功能';

-- ----------------------------
-- Table structure for ct_backstage_department_user
-- ----------------------------
DROP TABLE IF EXISTS `ct_backstage_department_user`;
CREATE TABLE `ct_backstage_department_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `department_id` bigint NOT NULL COMMENT '部门ID',
  `backstage_user_id` bigint NOT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `backstage_user_id_index` (`backstage_user_id`) USING BTREE,
  KEY `department_id_index` (`department_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=416 COMMENT='后台部门用户映射表';

-- ----------------------------
-- Table structure for ct_backstage_function
-- ----------------------------
DROP TABLE IF EXISTS `ct_backstage_function`;
CREATE TABLE `ct_backstage_function` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父级功能ID',
  `name` varchar(64) NOT NULL COMMENT '功能名称',
  `code` varchar(64) NOT NULL COMMENT '功能编码',
  `type` varchar(16) NOT NULL COMMENT 'MENU：菜单栏，FUNCTION：功能',
  `platform` varchar(8) NOT NULL COMMENT 'OPE: 运营平台 BUSINESS：商家平台',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `parent_id_idx` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10417 COMMENT='后台功能点';

-- ----------------------------
-- Table structure for ct_backstage_user
-- ----------------------------
DROP TABLE IF EXISTS `ct_backstage_user`;
CREATE TABLE `ct_backstage_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮件',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `type` varchar(16) NOT NULL COMMENT '类型：运营=OPE，商家=BUSINESS',
  `status` varchar(16) NOT NULL COMMENT '状态值 有效=VALID 冻结=FROZEN',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `shop_id` varchar(64) NOT NULL COMMENT '店铺ID，运营人员为固定OPE',
  `salt` varchar(16) DEFAULT NULL COMMENT '盐值',
  `remark` varchar(255) DEFAULT NULL,
  `create_user_name` varchar(64) NOT NULL COMMENT '添加该用户的用户名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile_type_index` (`mobile`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=390 COMMENT='后台用户';

-- ----------------------------
-- Table structure for ct_backstage_user_function
-- ----------------------------
DROP TABLE IF EXISTS `ct_backstage_user_function`;
CREATE TABLE `ct_backstage_user_function` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backstage_user_id` bigint NOT NULL COMMENT '用户ID',
  `function_id` bigint NOT NULL COMMENT '功能CODE',
  `source_type` varchar(64) NOT NULL COMMENT '来源类型：DEPARTMENT：部门 ADD：其他管理员手动添加',
  `source_value` bigint NOT NULL DEFAULT '0' COMMENT '来源的值 部门ID|管理员ID',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `backstage_user_id` (`backstage_user_id`,`function_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=173729 COMMENT='后台用户可以用的功能点';

-- ----------------------------
-- Table structure for ct_buyun_sms_send_serial
-- ----------------------------
DROP TABLE IF EXISTS `ct_buyun_sms_send_serial`;
CREATE TABLE `ct_buyun_sms_send_serial` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sms_code` varchar(64) NOT NULL COMMENT '短信编号',
  `params` varchar(255) NOT NULL COMMENT '短信参数',
  `mobile` varchar(64) NOT NULL COMMENT '接收人手机号',
  `result` mediumtext COMMENT '发送结果',
  `status` varchar(12) DEFAULT NULL COMMENT '发送结果返回码',
  `balance` varchar(255) DEFAULT NULL COMMENT '当前账户余额，单位厘',
  `channel_id` varchar(10) NOT NULL COMMENT '渠道编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `sms_type` varchar(255) DEFAULT NULL COMMENT '短信行业类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1658;

-- ----------------------------
-- Table structure for ct_buyun_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `ct_buyun_sms_template`;
CREATE TABLE `ct_buyun_sms_template` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tpl_id` varchar(64) DEFAULT NULL COMMENT '短信模板Id',
  `autograph` varchar(64) NOT NULL COMMENT '签名',
  `content` varchar(500) NOT NULL COMMENT '短信内容',
  `use_case` varchar(500) DEFAULT NULL COMMENT '使用场景',
  `status` bigint DEFAULT NULL COMMENT '是否生效 0:有效,1:无效',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `sms_type` varchar(64) NOT NULL COMMENT '短信行业类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `all_idx` (`tpl_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=482 COMMENT='布云短信模板';

-- ----------------------------
-- Table structure for ct_channel_account_period
-- ----------------------------
DROP TABLE IF EXISTS `ct_channel_account_period`;
CREATE TABLE `ct_channel_account_period` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `marketing_channel_id` varchar(64) DEFAULT NULL COMMENT '渠道id',
  `marketing_channel_name` varchar(255) DEFAULT NULL COMMENT '渠道名称',
  `status` varchar(20) DEFAULT NULL COMMENT '状态：待结算；待审核；待支付；已支付',
  `total_amount` decimal(20,2) DEFAULT NULL COMMENT '结算总金额',
  `total_brokerage` decimal(20,2) DEFAULT NULL COMMENT '佣金',
  `settle_date` datetime DEFAULT NULL COMMENT '结算日期',
  `settle_amount` decimal(20,2) DEFAULT NULL COMMENT '结算金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 COMMENT='渠道结算表';

-- ----------------------------
-- Table structure for ct_channel_account_period_progress
-- ----------------------------
DROP TABLE IF EXISTS `ct_channel_account_period_progress`;
CREATE TABLE `ct_channel_account_period_progress` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_period_id` bigint NOT NULL COMMENT '账期ID',
  `operator` varchar(255) NOT NULL COMMENT '操作人信息',
  `status` varchar(255) NOT NULL COMMENT '流转状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 COMMENT='结算流程';

-- ----------------------------
-- Table structure for ct_channel_account_period_remark
-- ----------------------------
DROP TABLE IF EXISTS `ct_channel_account_period_remark`;
CREATE TABLE `ct_channel_account_period_remark` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_period_id` bigint NOT NULL COMMENT '账期ID',
  `backstage_user_name` varchar(255) NOT NULL COMMENT '备注人姓名',
  `content` varchar(255) NOT NULL COMMENT '备注内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='渠道账期备注';

-- ----------------------------
-- Table structure for ct_channel_split_bill
-- ----------------------------
DROP TABLE IF EXISTS `ct_channel_split_bill`;
CREATE TABLE `ct_channel_split_bill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_period_id` bigint DEFAULT NULL COMMENT '账期ID',
  `order_id` varchar(64) NOT NULL COMMENT '分账订单ID',
  `period` int DEFAULT NULL COMMENT '期数',
  `uid` varchar(64) NOT NULL COMMENT '分账订单所属用户ID',
  `marketing_id` varchar(255) NOT NULL COMMENT '渠道ID',
  `scale` decimal(3,2) DEFAULT NULL COMMENT '分账比例',
  `user_pay_amount` decimal(10,2) NOT NULL COMMENT '用户支付金额',
  `channel_amount` decimal(10,2) NOT NULL COMMENT '分给渠道的金额',
  `status` varchar(255) NOT NULL COMMENT '创建时间',
  `user_pay_time` datetime DEFAULT NULL COMMENT '用户支付时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 ROW_FORMAT=DYNAMIC COMMENT='渠道分账信息';

-- ----------------------------
-- Table structure for ct_channel_user_orders
-- ----------------------------
DROP TABLE IF EXISTS `ct_channel_user_orders`;
CREATE TABLE `ct_channel_user_orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `marketing_channel_id` varchar(255) DEFAULT NULL COMMENT '渠道id',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `product_name` varchar(64) DEFAULT NULL COMMENT '商品名称',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '总租金',
  `total_periods` int DEFAULT NULL COMMENT '总期数',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `shop_name` varchar(255) DEFAULT NULL COMMENT '店铺名称',
  `settle_status` varchar(255) DEFAULT NULL COMMENT '结算状态',
  `scale` decimal(10,2) DEFAULT NULL COMMENT '分账比例',
  `user_name` varchar(255) DEFAULT NULL COMMENT '下单人姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '下单人手机号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=332 COMMENT='渠道用户下单表';

-- ----------------------------
-- Table structure for ct_config
-- ----------------------------
DROP TABLE IF EXISTS `ct_config`;
CREATE TABLE `ct_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL COMMENT '编码',
  `name` varchar(255) NOT NULL,
  `value` varchar(64) NOT NULL COMMENT '值',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 COMMENT='配置信息';

-- ----------------------------
-- Table structure for ct_district
-- ----------------------------
DROP TABLE IF EXISTS `ct_district`;
CREATE TABLE `ct_district` (
  `id` int NOT NULL AUTO_INCREMENT,
  `district_id` int DEFAULT NULL,
  `parent_id` int DEFAULT NULL COMMENT '父id',
  `name` varchar(45) DEFAULT NULL COMMENT '区域名称',
  `merger_name` varchar(200) DEFAULT NULL COMMENT '合并名称',
  `short_name` varchar(45) DEFAULT NULL COMMENT '缩写名',
  `merger_short_name` varchar(200) DEFAULT NULL COMMENT '合并短名',
  `level_type` varchar(45) DEFAULT NULL COMMENT '类型',
  `city_code` varchar(45) DEFAULT NULL,
  `zip_code` varchar(45) DEFAULT NULL,
  `pinyin` varchar(45) DEFAULT NULL,
  `jianpin` varchar(45) DEFAULT NULL,
  `first_char` varchar(45) DEFAULT NULL,
  `lng` varchar(45) DEFAULT NULL,
  `lat` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `d_idx` (`district_id`) USING BTREE,
  KEY `p_idx` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10001 COMMENT='区域表';

-- ----------------------------
-- Table structure for ct_e_sign_user
-- ----------------------------
DROP TABLE IF EXISTS `ct_e_sign_user`;
CREATE TABLE `ct_e_sign_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_no` varchar(36) NOT NULL COMMENT '身份账号码',
  `user_name` varchar(36) NOT NULL COMMENT '真实姓名',
  `account_id` varchar(64) NOT NULL COMMENT 'e签宝账户ID',
  `seal_data` longtext COMMENT 'e签宝sealdata',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_no_idx` (`id_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6348 COMMENT='e签宝用户信息映射';

-- ----------------------------
-- Table structure for ct_fee_bill
-- ----------------------------
DROP TABLE IF EXISTS `ct_fee_bill`;
CREATE TABLE `ct_fee_bill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fund_flow_id` bigint DEFAULT NULL COMMENT '账期ID',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单ID',
  `uid` varchar(64) DEFAULT NULL COMMENT '分账订单所属用户ID',
  `ali_uid` varchar(64) DEFAULT NULL COMMENT '用户支付宝uid',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '费用',
  `shop_id` varchar(255) DEFAULT NULL COMMENT '店铺ID',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `type` varchar(16) DEFAULT NULL COMMENT '费用类型',
  `channel_id` varchar(255) DEFAULT NULL COMMENT '订单所属渠道',
  `order_status` varchar(64) DEFAULT NULL,
  `order_time` datetime DEFAULT NULL COMMENT '用户下单时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `backstage_user_id` bigint DEFAULT NULL COMMENT '操作员人ID',
  PRIMARY KEY (`id`),
  KEY `aliuid_channel` (`ali_uid`,`channel_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67480 COMMENT='分账信息';

-- ----------------------------
-- Table structure for ct_hot_search_config
-- ----------------------------
DROP TABLE IF EXISTS `ct_hot_search_config`;
CREATE TABLE `ct_hot_search_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `channel_id` varchar(8) NOT NULL COMMENT '渠道编号',
  `word` varchar(64) NOT NULL COMMENT '内容',
  `index_sort` int NOT NULL COMMENT '顺序',
  `create_time` datetime NOT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 COMMENT='热门板块配置';

-- ----------------------------
-- Table structure for ct_lite_coupon_package
-- ----------------------------
DROP TABLE IF EXISTS `ct_lite_coupon_package`;
CREATE TABLE `ct_lite_coupon_package` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bind_id` bigint DEFAULT NULL,
  `type` varchar(16) NOT NULL COMMENT '优惠券用法 SINGLE-独立优惠券|ACTIVITY—营销活动',
  `name` varchar(255) NOT NULL,
  `contain_scene` varchar(32) DEFAULT NULL COMMENT '包含的优惠券的使用场景，RENT，BUY_OUT,BOTH',
  `template_ids` varchar(255) NOT NULL COMMENT '优惠券模版ID，多个以","分隔开',
  `for_new` varchar(2) NOT NULL COMMENT '是否针对新用户：T：是。 F：否',
  `status` varchar(32) NOT NULL COMMENT 'VALID：有效 INVALID：失效 RUN_OUT:已经领取完',
  `left_num` int NOT NULL,
  `num` int NOT NULL,
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `user_limit_num` int NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `source_shop_id` varchar(128) DEFAULT NULL,
  `source_shop_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 COMMENT='lite优惠券大礼包';

-- ----------------------------
-- Table structure for ct_lite_coupon_template
-- ----------------------------
DROP TABLE IF EXISTS `ct_lite_coupon_template`;
CREATE TABLE `ct_lite_coupon_template` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bind_id` bigint DEFAULT NULL,
  `title` varchar(64) NOT NULL COMMENT '标题',
  `scene` varchar(32) NOT NULL COMMENT '使用场景 RENT=租赁 BUY_OUT=买断',
  `display_note` varchar(64) DEFAULT NULL COMMENT '展示说明',
  `num` int NOT NULL COMMENT '发放数量',
  `left_num` int NOT NULL COMMENT '剩余数量',
  `min_amount` decimal(10,2) NOT NULL COMMENT '最小使用金额',
  `discount_amount` decimal(10,2) NOT NULL COMMENT '减免金额',
  `user_limit_num` int NOT NULL COMMENT '限制用户最多拥有数量',
  `start_time` datetime DEFAULT NULL COMMENT '有效期开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '有效期结束时间',
  `delay_day_num` int DEFAULT NULL COMMENT '有效期，以用户领取时间+delay_day_num',
  `source_shop_id` varchar(255) DEFAULT NULL,
  `source_shop_name` varchar(255) DEFAULT NULL,
  `for_new` varchar(1) NOT NULL COMMENT '是否针对新人 T：是 F：不是',
  `package_id` bigint DEFAULT NULL COMMENT '所属大礼包的ID',
  `type` varchar(16) NOT NULL COMMENT '用法 SINGLE：独立   PACKAGE：大礼包 ALIPAY：券码券',
  `status` varchar(32) NOT NULL COMMENT 'VALID：有效，用户可自由领取 INVALID：失效 RUN_OUT:已经领取完 UNASSIGNED:未配大礼包，ASSIGNED:已经分配大礼包',
  `ali_code_file` varchar(255) DEFAULT NULL,
  `bind_url` longtext COMMENT '券码券设置领取地址',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `create_time` datetime NOT NULL COMMENT ' 创建时间',
  `delete_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 COMMENT='lite优惠券模版';

-- ----------------------------
-- Table structure for ct_lite_coupon_template_range
-- ----------------------------
DROP TABLE IF EXISTS `ct_lite_coupon_template_range`;
CREATE TABLE `ct_lite_coupon_template_range` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `template_id` bigint NOT NULL COMMENT '模版ID',
  `type` varchar(32) NOT NULL COMMENT '类型：CATEGORY=类目 PRODUCT：商品  SHOP：店铺 ALL：全场通用',
  `value` varchar(255) DEFAULT NULL COMMENT '对应的类型的值',
  `description` varchar(255) DEFAULT NULL COMMENT '对应的值的描述，比如商品名称|类型名称',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `create_time` datetime NOT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1843 COMMENT='lite优惠券使用范围';

-- ----------------------------
-- Table structure for ct_lite_user_coupon
-- ----------------------------
DROP TABLE IF EXISTS `ct_lite_user_coupon`;
CREATE TABLE `ct_lite_user_coupon` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `template_id` bigint NOT NULL,
  `template_bind_id` bigint DEFAULT NULL,
  `discount_amount` decimal(10,2) NOT NULL COMMENT '优惠金额',
  `code` varchar(255) NOT NULL COMMENT '编号',
  `uid` varchar(64) NOT NULL COMMENT '领取用户ID',
  `phone` varchar(16) DEFAULT NULL COMMENT '领取用户的手机号码',
  `receive_time` datetime NOT NULL COMMENT '用户领取时间',
  `receive_type` varchar(32) NOT NULL COMMENT '领取方式 REQUEST：用户主动领取 ASSIGN：系统派发',
  `start_time` datetime NOT NULL COMMENT '有效期开始时间',
  `end_time` datetime NOT NULL COMMENT '有效期结束时间',
  `order_id` varchar(255) DEFAULT NULL COMMENT '订单ID',
  `use_time` datetime DEFAULT NULL COMMENT '用户使用时间',
  `package_id` bigint DEFAULT NULL,
  `package_name` varchar(255) DEFAULT NULL COMMENT '来自的大礼包名称',
  `status` varchar(32) NOT NULL COMMENT '状态：UNUSED：未使用 USED：已使用',
  `channel_id` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tid_index` (`template_id`) USING BTREE,
  KEY `uid_tid_index` (`uid`,`template_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1841 COMMENT='lite用户优惠券表';

-- ----------------------------
-- Table structure for ct_marketing_channel
-- ----------------------------
DROP TABLE IF EXISTS `ct_marketing_channel`;
CREATE TABLE `ct_marketing_channel` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '渠道id',
  `uid` varchar(255) DEFAULT NULL COMMENT '渠道编号',
  `name` varchar(255) DEFAULT NULL COMMENT '渠道名称',
  `scale` decimal(3,2) DEFAULT NULL COMMENT '分账比例，小于等于1',
  `status` varchar(20) NOT NULL COMMENT '审核状态',
  `account` varchar(20) DEFAULT NULL COMMENT '渠道账号',
  `identity` varchar(255) DEFAULT NULL COMMENT '商家支付宝账号',
  `ali_name` varchar(100) DEFAULT NULL COMMENT '商家支付宝实名认证姓名',
  `app_version` varchar(16) DEFAULT NULL COMMENT 'ZWZ:租物租,来源现在只有租物租',
  `add_user` varchar(255) DEFAULT NULL COMMENT '添加信息的人员',
  `audit_user` varchar(255) DEFAULT NULL COMMENT '审核人员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 COMMENT='渠道账号分佣表';

-- ----------------------------
-- Table structure for ct_marketing_channel_link
-- ----------------------------
DROP TABLE IF EXISTS `ct_marketing_channel_link`;
CREATE TABLE `ct_marketing_channel_link` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `marketing_id` varchar(255) DEFAULT NULL COMMENT '营销码',
  `link` varchar(255) DEFAULT NULL COMMENT '首页链接',
  `marketing_channel_id` varchar(255) DEFAULT NULL COMMENT '渠道id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 COMMENT='渠道下的营销码汇总表';

-- ----------------------------
-- Table structure for ct_material_center_category
-- ----------------------------
DROP TABLE IF EXISTS `ct_material_center_category`;
CREATE TABLE `ct_material_center_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '素材分类名称',
  `width` int NOT NULL COMMENT '宽度',
  `height` int NOT NULL COMMENT '高度',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 COMMENT='素材中心-素材分类';

-- ----------------------------
-- Table structure for ct_material_center_item
-- ----------------------------
DROP TABLE IF EXISTS `ct_material_center_item`;
CREATE TABLE `ct_material_center_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint NOT NULL COMMENT '素材所属分类ID',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `file_url` varchar(128) NOT NULL COMMENT '文件路径',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 COMMENT='素材中心文件';

-- ----------------------------
-- Table structure for ct_ope_category
-- ----------------------------
DROP TABLE IF EXISTS `ct_ope_category`;
CREATE TABLE `ct_ope_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '分类图标',
  `banner_icon` varchar(255) DEFAULT NULL COMMENT '宣传图片',
  `parent_id` int NOT NULL COMMENT '父类Id',
  `sort_rule` int NOT NULL COMMENT '排序规则',
  `status` tinyint DEFAULT NULL COMMENT '生效 0 失效 1 有效',
  `ant_chain_code` varchar(64) DEFAULT NULL,
  `zfb_code` varchar(64) DEFAULT NULL COMMENT '支付宝类目',
  `type` varchar(10) DEFAULT NULL COMMENT '类目类型 类目分类 1:一级类目 2：二级类目 3：三级类目',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1715;

-- ----------------------------
-- Table structure for ct_ope_category_product
-- ----------------------------
DROP TABLE IF EXISTS `ct_ope_category_product`;
CREATE TABLE `ct_ope_category_product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `operate_category_id` int NOT NULL COMMENT '分类Id',
  `parent_category_id` int DEFAULT NULL COMMENT '父类id',
  `item_id` varchar(100) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格优惠',
  `sale` decimal(10,2) DEFAULT NULL COMMENT '价格原价（划横线）',
  `old_new_degree` varchar(30) DEFAULT NULL COMMENT '新旧程度',
  `service_marks` varchar(100) DEFAULT NULL COMMENT '服务标签',
  `name` varchar(256) DEFAULT NULL COMMENT '商品名称',
  `shop_id` varchar(100) DEFAULT NULL COMMENT '店铺id',
  `min_rent` int DEFAULT NULL COMMENT '起租天数',
  `sales_volume` int DEFAULT '0' COMMENT '历史销量',
  `monthly_sales_volume` int DEFAULT '0' COMMENT '月销量',
  `status` int DEFAULT '1' COMMENT '0失效  1有效',
  `sort_score` int DEFAULT '0' COMMENT '排序分值',
  `freight_type` varchar(15) DEFAULT NULL COMMENT '快递方式-包邮:FREE-到付:PAY-自提:SELF',
  PRIMARY KEY (`id`),
  KEY `index_itemId` (`item_id`) USING BTREE,
  KEY `idx_categoryid` (`operate_category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19587 COMMENT='前台类目商品表';

-- ----------------------------
-- Table structure for ct_ope_config
-- ----------------------------
DROP TABLE IF EXISTS `ct_ope_config`;
CREATE TABLE `ct_ope_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道ID',
  `index_type` int DEFAULT NULL COMMENT '配置类型：1/banner 2/Icon 3/Loin 4/Waterfall',
  `config_id` int DEFAULT NULL COMMENT '首页配置ID',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3284 COMMENT='首页运营配置表';

-- ----------------------------
-- Table structure for ct_ope_custom_product
-- ----------------------------
DROP TABLE IF EXISTS `ct_ope_custom_product`;
CREATE TABLE `ct_ope_custom_product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '跟新时间',
  `index_sort` int DEFAULT NULL COMMENT '序列',
  `name` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `tab_id` int DEFAULT NULL COMMENT '父tab的id',
  `image` varchar(255) DEFAULT NULL,
  `price` varchar(10) DEFAULT NULL COMMENT '价格优惠',
  `sale` varchar(10) DEFAULT NULL COMMENT '价格原价（划横线）',
  `old_new_degree` varchar(10) DEFAULT NULL COMMENT '新旧程度',
  `product_marks` varchar(64) DEFAULT NULL COMMENT '商品标签',
  `service_marks` varchar(64) DEFAULT NULL COMMENT '服务标签',
  `sales_volume` bigint DEFAULT NULL COMMENT '销售量',
  `sort_score` bigint DEFAULT NULL COMMENT '排序分值',
  `item_id` varchar(255) DEFAULT NULL COMMENT '产品唯一id',
  `link_url` varchar(255) DEFAULT NULL,
  `shop_name` varchar(64) DEFAULT NULL COMMENT '店铺名称 ',
  `min_days` varchar(255) DEFAULT NULL COMMENT '起租时间',
  `status` bigint DEFAULT NULL COMMENT '生效 0生效 1失效',
  `shop_id` varchar(100) DEFAULT NULL COMMENT '店铺ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_tab_id` (`tab_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5137 ROW_FORMAT=DYNAMIC COMMENT='自定义tab产品挂载表';

-- ----------------------------
-- Table structure for ct_ope_index_shop_banner
-- ----------------------------
DROP TABLE IF EXISTS `ct_ope_index_shop_banner`;
CREATE TABLE `ct_ope_index_shop_banner` (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `online_time` timestamp NULL DEFAULT NULL,
  `shop_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `img_src` varchar(255) DEFAULT NULL,
  `jump_url` varchar(255) DEFAULT NULL,
  `status` tinyint DEFAULT NULL COMMENT '0 失效 1 有效',
  `index_sort` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 COMMENT='商家详情轮播配置';

-- ----------------------------
-- Table structure for ct_ope_notice
-- ----------------------------
DROP TABLE IF EXISTS `ct_ope_notice`;
CREATE TABLE `ct_ope_notice` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `material_item_id` bigint NOT NULL COMMENT '素材图片ID',
  `jump_url` varchar(600) DEFAULT NULL COMMENT '跳转链接',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `delete_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `index_sort` int DEFAULT NULL COMMENT '序列',
  `name` varchar(64) DEFAULT NULL COMMENT '标题',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 COMMENT='商家中心通知';

-- ----------------------------
-- Table structure for ct_ope_notice_item
-- ----------------------------
DROP TABLE IF EXISTS `ct_ope_notice_item`;
CREATE TABLE `ct_ope_notice_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `tab_id` bigint NOT NULL COMMENT '公告tabId',
  `delete_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `index_sort` int DEFAULT NULL COMMENT '序列',
  `name` varchar(64) DEFAULT NULL COMMENT '标题',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  `detail` longtext COMMENT '详情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 COMMENT='公告常见问题tab内容';

-- ----------------------------
-- Table structure for ct_ope_notice_tab
-- ----------------------------
DROP TABLE IF EXISTS `ct_ope_notice_tab`;
CREATE TABLE `ct_ope_notice_tab` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `delete_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `index_sort` int DEFAULT NULL COMMENT '序列',
  `name` varchar(64) DEFAULT NULL COMMENT '标题',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 COMMENT='公告常见问题tab';

-- ----------------------------
-- Table structure for ct_order_additional_services
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_additional_services`;
CREATE TABLE `ct_order_additional_services` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `order_id` varchar(64) NOT NULL DEFAULT '' COMMENT '订单id',
  `shop_additional_services_id` int NOT NULL COMMENT '店铺增值服务id',
  `price` decimal(20,2) NOT NULL COMMENT '增值服务费',
  PRIMARY KEY (`id`),
  KEY `order_id_index` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1508 COMMENT='订单增值服务信息表';

-- ----------------------------
-- Table structure for ct_order_address
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_address`;
CREATE TABLE `ct_order_address` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `province` int NOT NULL COMMENT '省',
  `city` int NOT NULL COMMENT '市',
  `uid` varchar(255) NOT NULL COMMENT '所属用户id',
  `area` int DEFAULT '-1' COMMENT '区',
  `street` varchar(255) NOT NULL COMMENT '详细地址',
  `zcode` varchar(255) DEFAULT NULL COMMENT '邮编',
  `telephone` varchar(255) NOT NULL COMMENT '手机号码',
  `realname` varchar(255) NOT NULL COMMENT '收货人姓名',
  `order_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `d_ua_idx` (`province`,`city`,`area`) USING BTREE,
  KEY `idx_uid` (`uid`(191)) USING BTREE,
  KEY `idex_order_id` (`order_id`(191)) USING BTREE,
  KEY `idx_telephone` (`telephone`(191)) USING BTREE,
  KEY `idx_realname` (`realname`(191)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=787440 COMMENT='订单地址表';

-- ----------------------------
-- Table structure for ct_order_audit_record
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_audit_record`;
CREATE TABLE `ct_order_audit_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` varchar(64) NOT NULL COMMENT '订单编号',
  `approve_uid` varchar(64) DEFAULT NULL COMMENT '审核人uid',
  `approve_user_name` varchar(64) DEFAULT NULL COMMENT '审核人姓名',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `approve_status` char(2) NOT NULL COMMENT '审核状态 EnumOrderAuditStatus 00:待审核 01：通过 02：拒绝',
  `refuse_type` char(2) DEFAULT NULL COMMENT '拒绝类型',
  `remark` varchar(1024) DEFAULT NULL COMMENT '审核备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_idx` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=59440 COMMENT='订单审核记录表';

-- ----------------------------
-- Table structure for ct_order_by_stages_0
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_by_stages_0`;
CREATE TABLE `ct_order_by_stages_0` (
  `id` bigint NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `lease_term` int DEFAULT '0' COMMENT '租期，单位：天',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单号',
  `total_periods` int DEFAULT '0' COMMENT '总期数',
  `current_periods` int DEFAULT '0' COMMENT '当前期数',
  `total_rent` decimal(10,2) DEFAULT '0.00' COMMENT '总租金',
  `current_periods_rent` decimal(10,2) DEFAULT '0.00' COMMENT '当期应付租额',
  `status` char(2) DEFAULT NULL COMMENT '状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款',
  `overdue_days` int DEFAULT '0' COMMENT '逾期天数，单位：天',
  `repayment_date` datetime DEFAULT NULL COMMENT '还款日',
  `statement_date` datetime DEFAULT NULL COMMENT '账单日',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户传入的资金交易号',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `refund_transaction_number` varchar(255) DEFAULT NULL COMMENT '退款资金交易号(已退款金额 部分退款才有)',
  `shop_id` varchar(60) DEFAULT NULL COMMENT '商铺id冗余订单表的字段',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `split_bill_time` datetime DEFAULT NULL COMMENT '分账时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式-ZFB,TTF,WX',
  `deduction_times` int DEFAULT NULL COMMENT '代扣扣款次数',
  `sync_to_chain` tinyint(1) DEFAULT NULL COMMENT '是否同步到蚂蚁链',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_out_trans_no` (`out_trade_no`) USING BTREE,
  KEY `idx_shop_id_status` (`shop_id`,`status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_statement_date_status` (`order_id`,`statement_date`,`status`,`create_time`) USING BTREE
) ENGINE=InnoDB COMMENT='用户订单分期表';

-- ----------------------------
-- Table structure for ct_order_by_stages_1
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_by_stages_1`;
CREATE TABLE `ct_order_by_stages_1` (
  `id` bigint NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `lease_term` int DEFAULT '0' COMMENT '租期，单位：天',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单号',
  `total_periods` int DEFAULT '0' COMMENT '总期数',
  `current_periods` int DEFAULT '0' COMMENT '当前期数',
  `total_rent` decimal(10,2) DEFAULT '0.00' COMMENT '总租金',
  `current_periods_rent` decimal(10,2) DEFAULT '0.00' COMMENT '当期应付租额',
  `status` char(2) DEFAULT NULL COMMENT '状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款',
  `overdue_days` int DEFAULT '0' COMMENT '逾期天数，单位：天',
  `repayment_date` datetime DEFAULT NULL COMMENT '还款日',
  `statement_date` datetime DEFAULT NULL COMMENT '账单日',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户传入的资金交易号',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `refund_transaction_number` varchar(255) DEFAULT NULL COMMENT '退款资金交易号(已退款金额 部分退款才有)',
  `shop_id` varchar(60) DEFAULT NULL COMMENT '商铺id冗余订单表的字段',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `split_bill_time` datetime DEFAULT NULL COMMENT '分账时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式-ZFB,TTF,WX',
  `deduction_times` int DEFAULT NULL COMMENT '代扣扣款次数',
  `sync_to_chain` tinyint(1) DEFAULT NULL COMMENT '是否同步到蚂蚁链',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_out_trans_no` (`out_trade_no`) USING BTREE,
  KEY `idx_shop_id_status` (`shop_id`,`status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_statement_date_status` (`order_id`,`statement_date`,`status`,`create_time`) USING BTREE
) ENGINE=InnoDB COMMENT='用户订单分期表';

-- ----------------------------
-- Table structure for ct_order_by_stages_2
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_by_stages_2`;
CREATE TABLE `ct_order_by_stages_2` (
  `id` bigint NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `lease_term` int DEFAULT '0' COMMENT '租期，单位：天',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单号',
  `total_periods` int DEFAULT '0' COMMENT '总期数',
  `current_periods` int DEFAULT '0' COMMENT '当前期数',
  `total_rent` decimal(10,2) DEFAULT '0.00' COMMENT '总租金',
  `current_periods_rent` decimal(10,2) DEFAULT '0.00' COMMENT '当期应付租额',
  `status` char(2) DEFAULT NULL COMMENT '状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款',
  `overdue_days` int DEFAULT '0' COMMENT '逾期天数，单位：天',
  `repayment_date` datetime DEFAULT NULL COMMENT '还款日',
  `statement_date` datetime DEFAULT NULL COMMENT '账单日',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户传入的资金交易号',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `refund_transaction_number` varchar(255) DEFAULT NULL COMMENT '退款资金交易号(已退款金额 部分退款才有)',
  `shop_id` varchar(60) DEFAULT NULL COMMENT '商铺id冗余订单表的字段',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `split_bill_time` datetime DEFAULT NULL COMMENT '分账时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式-ZFB,TTF,WX',
  `deduction_times` int DEFAULT NULL COMMENT '代扣扣款次数',
  `sync_to_chain` tinyint(1) DEFAULT NULL COMMENT '是否同步到蚂蚁链',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_out_trans_no` (`out_trade_no`) USING BTREE,
  KEY `idx_shop_id_status` (`shop_id`,`status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_statement_date_status` (`order_id`,`statement_date`,`status`,`create_time`) USING BTREE
) ENGINE=InnoDB COMMENT='用户订单分期表';

-- ----------------------------
-- Table structure for ct_order_by_stages_3
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_by_stages_3`;
CREATE TABLE `ct_order_by_stages_3` (
  `id` bigint NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `lease_term` int DEFAULT '0' COMMENT '租期，单位：天',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单号',
  `total_periods` int DEFAULT '0' COMMENT '总期数',
  `current_periods` int DEFAULT '0' COMMENT '当前期数',
  `total_rent` decimal(10,2) DEFAULT '0.00' COMMENT '总租金',
  `current_periods_rent` decimal(10,2) DEFAULT '0.00' COMMENT '当期应付租额',
  `status` char(2) DEFAULT NULL COMMENT '状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款',
  `overdue_days` int DEFAULT '0' COMMENT '逾期天数，单位：天',
  `repayment_date` datetime DEFAULT NULL COMMENT '还款日',
  `statement_date` datetime DEFAULT NULL COMMENT '账单日',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户传入的资金交易号',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `refund_transaction_number` varchar(255) DEFAULT NULL COMMENT '退款资金交易号(已退款金额 部分退款才有)',
  `shop_id` varchar(60) DEFAULT NULL COMMENT '商铺id冗余订单表的字段',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `split_bill_time` datetime DEFAULT NULL COMMENT '分账时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式-ZFB,TTF,WX',
  `deduction_times` int DEFAULT NULL COMMENT '代扣扣款次数',
  `sync_to_chain` tinyint(1) DEFAULT NULL COMMENT '是否同步到蚂蚁链',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_out_trans_no` (`out_trade_no`) USING BTREE,
  KEY `idx_shop_id_status` (`shop_id`,`status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_statement_date_status` (`order_id`,`statement_date`,`status`,`create_time`) USING BTREE
) ENGINE=InnoDB COMMENT='用户订单分期表';

-- ----------------------------
-- Table structure for ct_order_by_stages_4
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_by_stages_4`;
CREATE TABLE `ct_order_by_stages_4` (
  `id` bigint NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `lease_term` int DEFAULT '0' COMMENT '租期，单位：天',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单号',
  `total_periods` int DEFAULT '0' COMMENT '总期数',
  `current_periods` int DEFAULT '0' COMMENT '当前期数',
  `total_rent` decimal(10,2) DEFAULT '0.00' COMMENT '总租金',
  `current_periods_rent` decimal(10,2) DEFAULT '0.00' COMMENT '当期应付租额',
  `status` char(2) DEFAULT NULL COMMENT '状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款',
  `overdue_days` int DEFAULT '0' COMMENT '逾期天数，单位：天',
  `repayment_date` datetime DEFAULT NULL COMMENT '还款日',
  `statement_date` datetime DEFAULT NULL COMMENT '账单日',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户传入的资金交易号',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `refund_transaction_number` varchar(255) DEFAULT NULL COMMENT '退款资金交易号(已退款金额 部分退款才有)',
  `shop_id` varchar(60) DEFAULT NULL COMMENT '商铺id冗余订单表的字段',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `split_bill_time` datetime DEFAULT NULL COMMENT '分账时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式-ZFB,TTF,WX',
  `deduction_times` int DEFAULT NULL COMMENT '代扣扣款次数',
  `sync_to_chain` tinyint(1) DEFAULT NULL COMMENT '是否同步到蚂蚁链',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_out_trans_no` (`out_trade_no`) USING BTREE,
  KEY `idx_shop_id_status` (`shop_id`,`status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_statement_date_status` (`order_id`,`statement_date`,`status`,`create_time`) USING BTREE
) ENGINE=InnoDB COMMENT='用户订单分期表';

-- ----------------------------
-- Table structure for ct_order_by_stages_5
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_by_stages_5`;
CREATE TABLE `ct_order_by_stages_5` (
  `id` bigint NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `lease_term` int DEFAULT '0' COMMENT '租期，单位：天',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单号',
  `total_periods` int DEFAULT '0' COMMENT '总期数',
  `current_periods` int DEFAULT '0' COMMENT '当前期数',
  `total_rent` decimal(10,2) DEFAULT '0.00' COMMENT '总租金',
  `current_periods_rent` decimal(10,2) DEFAULT '0.00' COMMENT '当期应付租额',
  `status` char(2) DEFAULT NULL COMMENT '状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款',
  `overdue_days` int DEFAULT '0' COMMENT '逾期天数，单位：天',
  `repayment_date` datetime DEFAULT NULL COMMENT '还款日',
  `statement_date` datetime DEFAULT NULL COMMENT '账单日',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户传入的资金交易号',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `refund_transaction_number` varchar(255) DEFAULT NULL COMMENT '退款资金交易号(已退款金额 部分退款才有)',
  `shop_id` varchar(60) DEFAULT NULL COMMENT '商铺id冗余订单表的字段',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `split_bill_time` datetime DEFAULT NULL COMMENT '分账时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式-ZFB,TTF,WX',
  `deduction_times` int DEFAULT NULL COMMENT '代扣扣款次数',
  `sync_to_chain` tinyint(1) DEFAULT NULL COMMENT '是否同步到蚂蚁链',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_out_trans_no` (`out_trade_no`) USING BTREE,
  KEY `idx_shop_id_status` (`shop_id`,`status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_statement_date_status` (`order_id`,`statement_date`,`status`,`create_time`) USING BTREE
) ENGINE=InnoDB COMMENT='用户订单分期表';

-- ----------------------------
-- Table structure for ct_order_by_stages_6
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_by_stages_6`;
CREATE TABLE `ct_order_by_stages_6` (
  `id` bigint NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `lease_term` int DEFAULT '0' COMMENT '租期，单位：天',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单号',
  `total_periods` int DEFAULT '0' COMMENT '总期数',
  `current_periods` int DEFAULT '0' COMMENT '当前期数',
  `total_rent` decimal(10,2) DEFAULT '0.00' COMMENT '总租金',
  `current_periods_rent` decimal(10,2) DEFAULT '0.00' COMMENT '当期应付租额',
  `status` char(2) DEFAULT NULL COMMENT '状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款',
  `overdue_days` int DEFAULT '0' COMMENT '逾期天数，单位：天',
  `repayment_date` datetime DEFAULT NULL COMMENT '还款日',
  `statement_date` datetime DEFAULT NULL COMMENT '账单日',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户传入的资金交易号',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `refund_transaction_number` varchar(255) DEFAULT NULL COMMENT '退款资金交易号(已退款金额 部分退款才有)',
  `shop_id` varchar(60) DEFAULT NULL COMMENT '商铺id冗余订单表的字段',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `split_bill_time` datetime DEFAULT NULL COMMENT '分账时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式-ZFB,TTF,WX',
  `deduction_times` int DEFAULT NULL COMMENT '代扣扣款次数',
  `sync_to_chain` tinyint(1) DEFAULT NULL COMMENT '是否同步到蚂蚁链',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_out_trans_no` (`out_trade_no`) USING BTREE,
  KEY `idx_shop_id_status` (`shop_id`,`status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_statement_date_status` (`order_id`,`statement_date`,`status`,`create_time`) USING BTREE
) ENGINE=InnoDB COMMENT='用户订单分期表';

-- ----------------------------
-- Table structure for ct_order_by_stages_7
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_by_stages_7`;
CREATE TABLE `ct_order_by_stages_7` (
  `id` bigint NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `lease_term` int DEFAULT '0' COMMENT '租期，单位：天',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单号',
  `total_periods` int DEFAULT '0' COMMENT '总期数',
  `current_periods` int DEFAULT '0' COMMENT '当前期数',
  `total_rent` decimal(10,2) DEFAULT '0.00' COMMENT '总租金',
  `current_periods_rent` decimal(10,2) DEFAULT '0.00' COMMENT '当期应付租额',
  `status` char(2) DEFAULT NULL COMMENT '状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款',
  `overdue_days` int DEFAULT '0' COMMENT '逾期天数，单位：天',
  `repayment_date` datetime DEFAULT NULL COMMENT '还款日',
  `statement_date` datetime DEFAULT NULL COMMENT '账单日',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户传入的资金交易号',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `refund_transaction_number` varchar(255) DEFAULT NULL COMMENT '退款资金交易号(已退款金额 部分退款才有)',
  `shop_id` varchar(60) DEFAULT NULL COMMENT '商铺id冗余订单表的字段',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `split_bill_time` datetime DEFAULT NULL COMMENT '分账时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式-ZFB,TTF,WX',
  `deduction_times` int DEFAULT NULL COMMENT '代扣扣款次数',
  `sync_to_chain` tinyint(1) DEFAULT NULL COMMENT '是否同步到蚂蚁链',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_out_trans_no` (`out_trade_no`) USING BTREE,
  KEY `idx_shop_id_status` (`shop_id`,`status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_statement_date_status` (`order_id`,`statement_date`,`status`,`create_time`) USING BTREE
) ENGINE=InnoDB COMMENT='用户订单分期表';

-- ----------------------------
-- Table structure for ct_order_by_stages_8
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_by_stages_8`;
CREATE TABLE `ct_order_by_stages_8` (
  `id` bigint NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `lease_term` int DEFAULT '0' COMMENT '租期，单位：天',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单号',
  `total_periods` int DEFAULT '0' COMMENT '总期数',
  `current_periods` int DEFAULT '0' COMMENT '当前期数',
  `total_rent` decimal(10,2) DEFAULT '0.00' COMMENT '总租金',
  `current_periods_rent` decimal(10,2) DEFAULT '0.00' COMMENT '当期应付租额',
  `status` char(2) DEFAULT NULL COMMENT '状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款',
  `overdue_days` int DEFAULT '0' COMMENT '逾期天数，单位：天',
  `repayment_date` datetime DEFAULT NULL COMMENT '还款日',
  `statement_date` datetime DEFAULT NULL COMMENT '账单日',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户传入的资金交易号',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `refund_transaction_number` varchar(255) DEFAULT NULL COMMENT '退款资金交易号(已退款金额 部分退款才有)',
  `shop_id` varchar(60) DEFAULT NULL COMMENT '商铺id冗余订单表的字段',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `split_bill_time` datetime DEFAULT NULL COMMENT '分账时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式-ZFB,TTF,WX',
  `deduction_times` int DEFAULT NULL COMMENT '代扣扣款次数',
  `sync_to_chain` tinyint(1) DEFAULT NULL COMMENT '是否同步到蚂蚁链',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_out_trans_no` (`out_trade_no`) USING BTREE,
  KEY `idx_shop_id_status` (`shop_id`,`status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_statement_date_status` (`order_id`,`statement_date`,`status`,`create_time`) USING BTREE
) ENGINE=InnoDB COMMENT='用户订单分期表';

-- ----------------------------
-- Table structure for ct_order_by_stages_9
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_by_stages_9`;
CREATE TABLE `ct_order_by_stages_9` (
  `id` bigint NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `lease_term` int DEFAULT '0' COMMENT '租期，单位：天',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单号',
  `total_periods` int DEFAULT '0' COMMENT '总期数',
  `current_periods` int DEFAULT '0' COMMENT '当前期数',
  `total_rent` decimal(10,2) DEFAULT '0.00' COMMENT '总租金',
  `current_periods_rent` decimal(10,2) DEFAULT '0.00' COMMENT '当期应付租额',
  `status` char(2) DEFAULT NULL COMMENT '状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款',
  `overdue_days` int DEFAULT '0' COMMENT '逾期天数，单位：天',
  `repayment_date` datetime DEFAULT NULL COMMENT '还款日',
  `statement_date` datetime DEFAULT NULL COMMENT '账单日',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户传入的资金交易号',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `refund_transaction_number` varchar(255) DEFAULT NULL COMMENT '退款资金交易号(已退款金额 部分退款才有)',
  `shop_id` varchar(60) DEFAULT NULL COMMENT '商铺id冗余订单表的字段',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `split_bill_time` datetime DEFAULT NULL COMMENT '分账时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式-ZFB,TTF,WX',
  `deduction_times` int DEFAULT NULL COMMENT '代扣扣款次数',
  `sync_to_chain` tinyint(1) DEFAULT NULL COMMENT '是否同步到蚂蚁链',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_out_trans_no` (`out_trade_no`) USING BTREE,
  KEY `idx_shop_id_status` (`shop_id`,`status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_statement_date_status` (`order_id`,`statement_date`,`status`,`create_time`) USING BTREE
) ENGINE=InnoDB COMMENT='用户订单分期表';

-- ----------------------------
-- Table structure for ct_order_center_product
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_center_product`;
CREATE TABLE `ct_order_center_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` varchar(64) NOT NULL COMMENT '产品ID',
  `object_key` varchar(128) DEFAULT NULL COMMENT '文件在oss服务器中的名称',
  `item_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `material_key` varchar(255) DEFAULT NULL COMMENT '文件在商品中心的素材标示，创建/更新商品时使用',
  `material_id` varchar(255) DEFAULT NULL COMMENT '文件在商品中心的素材标识',
  `sku_id` varchar(36) DEFAULT NULL COMMENT '支付宝返回skuID',
  `item_id` varchar(36) DEFAULT NULL COMMENT '支付宝返回itemID',
  `price` int DEFAULT NULL COMMENT '价格，单位分',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_id_index` (`product_id`,`channel_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=122 COMMENT='小程序订单中心商品信息';

-- ----------------------------
-- Table structure for ct_order_center_sync_log
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_center_sync_log`;
CREATE TABLE `ct_order_center_sync_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单ID',
  `product_id` varchar(64) NOT NULL COMMENT '产品ID',
  `type` varchar(255) NOT NULL COMMENT '同步类型("MERCHANT_DELIVERD","商家发货中")("MERCHANT_FINISHED","订单已完成")("MERCHANT_LOANING","租赁中")',
  `state` varchar(255) NOT NULL COMMENT '状态',
  `req_params` mediumtext NOT NULL COMMENT '请求参数',
  `resp` mediumtext NOT NULL COMMENT '响应参数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `channel_id` varchar(11) DEFAULT NULL COMMENT '订单来源',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=440384 ROW_FORMAT=DYNAMIC COMMENT='小程序订单中心商品信息';

-- ----------------------------
-- Table structure for ct_order_complaints
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_complaints`;
CREATE TABLE `ct_order_complaints` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uid` varchar(64) NOT NULL COMMENT '用户ID',
  `telphone` varchar(64) DEFAULT NULL COMMENT '用户手机',
  `name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `content` varchar(255) NOT NULL COMMENT '投诉内容',
  `type_id` bigint NOT NULL COMMENT '投诉类型',
  `order_id` varchar(64) DEFAULT NULL COMMENT '投诉订单ID',
  `shop_id` varchar(64) DEFAULT NULL COMMENT '投诉商户ID',
  `result` varchar(800) DEFAULT NULL COMMENT '平台处理结果',
  `operator` varchar(255) DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `channel` varchar(25) DEFAULT NULL COMMENT '来源渠道 006',
  `status` bigint DEFAULT NULL COMMENT '处理状态 0未处理 1已处理',
  PRIMARY KEY (`id`),
  KEY `telphone_idx` (`telphone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=106 COMMENT='订单投诉';

-- ----------------------------
-- Table structure for ct_order_complaints_image
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_complaints_image`;
CREATE TABLE `ct_order_complaints_image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `complaint_id` bigint NOT NULL COMMENT '投诉ID',
  `image_url` varchar(255) NOT NULL COMMENT '图片链接',
  `create_time` datetime NOT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 COMMENT='订单投诉图片凭证';

-- ----------------------------
-- Table structure for ct_order_complaints_type
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_complaints_type`;
CREATE TABLE `ct_order_complaints_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '类型',
  `create_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 COMMENT='订单投诉类型';

-- ----------------------------
-- Table structure for ct_order_contract
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_contract`;
CREATE TABLE `ct_order_contract` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backstage_user_id` bigint DEFAULT NULL COMMENT '操作员ID',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `shop_id` varchar(64) DEFAULT NULL COMMENT '店铺ID',
  `origin_pdf` varchar(255) DEFAULT NULL COMMENT '合同源文件',
  `view_url` varchar(255) DEFAULT NULL,
  `file_expire_time` datetime DEFAULT NULL,
  `peg_url` varchar(255) DEFAULT NULL,
  `pci_url` varchar(255) DEFAULT NULL,
  `pac_url` varchar(255) DEFAULT NULL,
  `signed_pdf` varchar(255) DEFAULT NULL COMMENT '签署过后的合同文件',
  `fee` decimal(5,2) DEFAULT NULL COMMENT '费用',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `contract_code` varchar(255) DEFAULT NULL COMMENT '合同code,电子签章存证时需要的字段',
  `signer_code` varchar(255) DEFAULT NULL COMMENT '签署人流水号,电子签章存证时需要的字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id_idx` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9310 COMMENT='订单合同';

-- ----------------------------
-- Table structure for ct_order_hasten
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_hasten`;
CREATE TABLE `ct_order_hasten` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `source` varchar(16) NOT NULL COMMENT '备注来源 OPE：运营    BUSINESS：商户',
  `order_id` varchar(128) NOT NULL COMMENT '订单ID',
  `user_name` varchar(128) NOT NULL COMMENT '记录人',
  `result` varchar(255) NOT NULL COMMENT '结果',
  `notes` varchar(1024) NOT NULL COMMENT '小记',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `source_order_id_index` (`source`,`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1568;

-- ----------------------------
-- Table structure for ct_order_location_address
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_location_address`;
CREATE TABLE `ct_order_location_address` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `order_id` varchar(128) DEFAULT NULL COMMENT '订单号',
  `longitude` varchar(64) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(64) DEFAULT NULL COMMENT '纬度',
  `accuracy` varchar(64) DEFAULT NULL COMMENT '精确度，单位米 (m)',
  `horizontal_accuracy` varchar(64) DEFAULT NULL COMMENT '水平精确度，单位为米 (m)',
  `country` varchar(64) DEFAULT NULL COMMENT '国家（type>0生效）',
  `country_code` varchar(64) DEFAULT NULL COMMENT '国家编号（type>0生效）',
  `province` varchar(64) DEFAULT NULL COMMENT '省份（type>0生效）',
  `city` varchar(64) DEFAULT NULL COMMENT '城市（type>0生效）',
  `city_adcode` varchar(64) DEFAULT NULL COMMENT '城市级别的地区代码（type>0生效）',
  `district` varchar(64) DEFAULT NULL COMMENT '区县（type>0生效）',
  `district_adcode` varchar(64) DEFAULT NULL COMMENT '区县级别的地区代码（type>0生效）',
  `ip_addr` varchar(32) DEFAULT NULL,
  `street_number` varchar(64) DEFAULT NULL COMMENT '需要街道级别逆地理的才会有的字段（type>0生效）',
  `pois` mediumtext COMMENT '兴趣点',
  PRIMARY KEY (`id`),
  KEY `idex_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=141036 COMMENT='订单当前位置定位表';

-- ----------------------------
-- Table structure for ct_order_pay_deposit
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_pay_deposit`;
CREATE TABLE `ct_order_pay_deposit` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(128) NOT NULL COMMENT '订单编号',
  `uid` varchar(128) NOT NULL COMMENT '用户ID',
  `total_deposit` decimal(10,2) DEFAULT NULL COMMENT '订单押金',
  `risk_deposit` decimal(10,2) DEFAULT NULL COMMENT '风控之后需要支付的押金',
  `amount` decimal(10,2) NOT NULL COMMENT '实际押金金额',
  `trade_no` varchar(255) DEFAULT NULL COMMENT '交易号',
  `out_trade_no` varchar(255) DEFAULT NULL COMMENT '外部交易号',
  `status` varchar(16) NOT NULL COMMENT '状态 WAITING_PAYMENT:待支付  PAID:已支付  WITHDRAW:已提现',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `refund_time` datetime DEFAULT NULL COMMENT '提现时间',
  `refund_user` varchar(64) DEFAULT NULL COMMENT '退款操作人员',
  `channel_id` varchar(8) NOT NULL COMMENT '渠道编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式-ZFB,TTF,WX',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id_idx` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48314 COMMENT='用户支付押金表';

-- ----------------------------
-- Table structure for ct_order_pay_deposit_log
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_pay_deposit_log`;
CREATE TABLE `ct_order_pay_deposit_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(128) NOT NULL COMMENT '订单编号',
  `origin_amount` decimal(10,2) DEFAULT NULL COMMENT '修改前金额',
  `after_amount` decimal(10,2) NOT NULL COMMENT '修改后金额',
  `backstage_user_id` bigint DEFAULT NULL COMMENT '操作人员ID',
  `backstage_user_name` varchar(64) NOT NULL COMMENT '操作人员姓名',
  `remark` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44790 COMMENT='用户支付押金表修改日志';

-- ----------------------------
-- Table structure for ct_order_remark
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_remark`;
CREATE TABLE `ct_order_remark` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `source` varchar(16) NOT NULL COMMENT '备注来源 OPE：运营    BUSINESS：商户',
  `order_id` varchar(128) NOT NULL COMMENT '订单ID',
  `user_name` varchar(128) NOT NULL COMMENT '备注人姓名',
  `remark` varchar(1024) NOT NULL COMMENT '备注内容',
  `order_type` char(3) NOT NULL COMMENT '订单类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `source_order_id_index` (`source`,`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=828 COMMENT='订单备注';

-- ----------------------------
-- Table structure for ct_order_report
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_report`;
CREATE TABLE `ct_order_report` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `statistics_date` bigint NOT NULL COMMENT '统计时间',
  `total_order_count` bigint DEFAULT NULL COMMENT '总单量',
  `success_order_count` bigint DEFAULT NULL COMMENT '成功下单单量',
  `success_order_rent` decimal(16,2) DEFAULT NULL COMMENT '成功下单总租金',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=465 COMMENT='订单报表统计';

-- ----------------------------
-- Table structure for ct_order_settlement
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_settlement`;
CREATE TABLE `ct_order_settlement` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT ' ',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `settlement_status` char(2) NOT NULL COMMENT '结算状态 01：待支付，02:支付中 03:已结算 04：用户申请修改',
  `settlement_type` char(2) NOT NULL COMMENT '结算类型 01:完好 02:损坏 03:丢失 04:违约',
  `apply_modify_times` int DEFAULT NULL COMMENT '用户申请修改次数',
  `lose_amount` decimal(10,2) NOT NULL COMMENT '丢失金额',
  `damage_amount` decimal(10,2) NOT NULL COMMENT '损坏金额',
  `penalty_amount` decimal(10,2) NOT NULL COMMENT '违约金',
  `deposit` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '解冻押金',
  `rent` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已退租金',
  `payment_rent` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '应付租金',
  `transfer_payment` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '冻结转支付金额',
  `real_deposit` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '冻结押金',
  `before_rent` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '提前支付租金',
  `out_trand_no` varchar(255) DEFAULT NULL COMMENT '请求商户订单号',
  `trade_no` varchar(255) DEFAULT NULL COMMENT '支付宝交易号',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `split_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=406 COMMENT='订单结算记录表';

-- ----------------------------
-- Table structure for ct_order_shop_close
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_shop_close`;
CREATE TABLE `ct_order_shop_close` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` varchar(255) DEFAULT NULL,
  `shop_operator_id` varchar(255) DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `close_reason` varchar(200) DEFAULT NULL COMMENT '关闭原因',
  `certificate_images` text ,
  `create_time` datetime NOT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`(191)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12817 COMMENT='商家风控关单表';

-- ----------------------------
-- Table structure for ct_order_snapshots
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_snapshots`;
CREATE TABLE `ct_order_snapshots` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `order_id` varchar(255) NOT NULL COMMENT '订单id',
  `version` bigint NOT NULL COMMENT '版本号 毫秒时间戳',
  `data` longtext NOT NULL COMMENT '快照内容',
  `shop_id` varchar(255) NOT NULL COMMENT '订单所属店铺id',
  PRIMARY KEY (`id`),
  KEY `idx_updated_at` (`updated_at`) USING BTREE,
  KEY `idx_order_id` (`order_id`(191)) USING BTREE
) ENGINE=InnoDB COMMENT='订单快照表';

-- ----------------------------
-- Table structure for ct_order_yx_status
-- ----------------------------
DROP TABLE IF EXISTS `ct_order_yx_status`;
CREATE TABLE `ct_order_yx_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) DEFAULT NULL,
  `apply` tinyint(1) DEFAULT NULL COMMENT '申请报送',
  `loan` tinyint(1) DEFAULT NULL COMMENT '放款报送',
  `loan_time` datetime DEFAULT NULL COMMENT '放款报送时间',
  `credit_settle` tinyint(1) DEFAULT NULL,
  `sync_period` varchar(255) DEFAULT NULL COMMENT '已经上报的期数',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `oid_idx` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20;

-- ----------------------------
-- Table structure for ct_page_element_config
-- ----------------------------
DROP TABLE IF EXISTS `ct_page_element_config`;
CREATE TABLE `ct_page_element_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `material_center_item_id` bigint DEFAULT NULL COMMENT '素材中心素材项ID',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `link` varchar(255) DEFAULT NULL COMMENT '跳转链接',
  `sort_num` tinyint DEFAULT NULL COMMENT '排序',
  `describe_info` varchar(32) DEFAULT NULL COMMENT '描述',
  `ext_code` varchar(32) DEFAULT NULL COMMENT '编号',
  `channel_id` varchar(4) NOT NULL COMMENT '渠道编号',
  `create_time` datetime NOT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110;

-- ----------------------------
-- Table structure for ct_platform_express
-- ----------------------------
DROP TABLE IF EXISTS `ct_platform_express`;
CREATE TABLE `ct_platform_express` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '物流公司名称',
  `logo` varchar(255) DEFAULT NULL COMMENT '物流公司logo',
  `short_name` varchar(255) DEFAULT NULL COMMENT '物流公司简称',
  `telephone` varchar(255) DEFAULT NULL COMMENT '物流公司官方电话',
  `index` int DEFAULT '0' COMMENT '排位，越大越靠前',
  `ali_code` varchar(32) DEFAULT NULL COMMENT '支付宝对应的的code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 COMMENT='平台物流表';

-- ----------------------------
-- Table structure for ct_platform_spec
-- ----------------------------
DROP TABLE IF EXISTS `ct_platform_spec`;
CREATE TABLE `ct_platform_spec` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `category_id` int DEFAULT '0' COMMENT '所属分类id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 COMMENT='平台商品规格属性分类表';

-- ----------------------------
-- Table structure for ct_product
-- ----------------------------
DROP TABLE IF EXISTS `ct_product`;
CREATE TABLE `ct_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `name` varchar(255)  NOT NULL COMMENT '商品名称',
  `product_id` varchar(255)  NOT NULL COMMENT '商品id',
  `category_id` int(11) NOT NULL COMMENT '平台分类id，为最后一级分类id',
  `shop_id` varchar(255)  NOT NULL COMMENT '所属店铺id',
  `detail` longtext  COMMENT '商品详情',
  `type` int(11) DEFAULT NULL COMMENT '0回收站中的商品 1已上架售卖的商品 2放在仓库的商品',
  `status` tinyint(4) DEFAULT '0' COMMENT '商品状态 0为失效 1为有效 ',
  `sort_score` int(11) DEFAULT '0' COMMENT '排序分值',
  `shop_status` int(11) DEFAULT '0' COMMENT '0失效 1有效',
  `province` varchar(11)  DEFAULT NULL COMMENT '商品发货地所在省',
  `city` varchar(11)  DEFAULT NULL COMMENT '商品发货地所在市',
  `rent_rule_id` int(11) DEFAULT NULL COMMENT '租用规则id',
  `compensate_rule_id` int(11) DEFAULT NULL COMMENT '赔偿规则id',
  `min_rent_cycle` int(11) DEFAULT '1' COMMENT '起租周期',
  `max_rent_cycle` int(11) DEFAULT '1' COMMENT '最大租用周期',
  `min_advanced_days` int(11) DEFAULT NULL COMMENT '最少提前多少天下单',
  `max_advanced_days` int(11) DEFAULT NULL COMMENT '最多提前多少天下单',
  `audit_state` tinyint(4) DEFAULT '0' COMMENT '商品审核状态 0为正在审核中 1为审核不通过 2为审核通过',
  `audit_refuse_reason` varchar(255)  DEFAULT NULL COMMENT '审核不通过原因',
  `sales_volume` int(11) DEFAULT '0' COMMENT '历史销量',
  `monthly_sales_volume` int(11) DEFAULT '0' COMMENT '月销量',
  `old_new_degree` int(11) DEFAULT '0' COMMENT '1为全新 2为99新 3为95新 4为9成新 5为8成新 6为7成新',
  `empty` int(11) DEFAULT '0' COMMENT '是否清空',
  `sale` decimal(10,2) DEFAULT NULL COMMENT '商品最低价',
  `buy_out_support` tinyint(4) DEFAULT '0' COMMENT '是否可以买断 1:可以买断。0:不可以买断',
  `freight_type` varchar(15)  DEFAULT NULL COMMENT '快递方式-包邮:FREE-到付:PAY-自提:SELF',
  `return_rule` tinyint(4) DEFAULT NULL COMMENT '归还规则 1:支持提前归还  2:支持到期归还',
  `hidden` tinyint(4) DEFAULT '0',
  `return_freight_type` varchar(15)  DEFAULT NULL COMMENT '归还物流服务方式-快递方式-FREE-用户支付:PAY-商家承担',
  `is_on_line` tinyint(4) DEFAULT NULL COMMENT '商品标记',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`(191)) USING BTREE,
  KEY `idx_category_id` (`category_id`) USING BTREE,
  KEY `idx_item_id` (`product_id`(191),`status`,`audit_state`) USING BTREE,
  KEY `idx_shop_id` (`shop_id`(191)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4787 COMMENT='商品表';

-- ----------------------------
-- Table structure for ct_product_additional_services
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_additional_services`;
CREATE TABLE `ct_product_additional_services` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  `product_id` varchar(64) NOT NULL DEFAULT '' COMMENT '商品id',
  `shop_additional_services_id` int NOT NULL COMMENT '店铺增值服务id',
  PRIMARY KEY (`id`),
  KEY `item_id_index` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2019 COMMENT='商品增值服务表';

-- ----------------------------
-- Table structure for ct_product_audit_log
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_audit_log`;
CREATE TABLE `ct_product_audit_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `audit_status` tinyint NOT NULL COMMENT '商品审核状态 0为正在审核中 1为审核不通过 2为审核通过',
  `feed_back` varchar(255) NOT NULL COMMENT '反馈',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `item_id` varchar(122) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=544 COMMENT='商品审核日志表';

-- ----------------------------
-- Table structure for ct_product_cycle_prices
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_cycle_prices`;
CREATE TABLE `ct_product_cycle_prices` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `item_id` varchar(128) NOT NULL DEFAULT '' COMMENT '商品id',
  `sku_id` bigint NOT NULL COMMENT 'sku的id',
  `days` int NOT NULL COMMENT '目前官方限制7天 30天 90天 180天 365天 指7天以上 30天以上',
  `price` decimal(20,2) NOT NULL COMMENT '单天价格',
  `sale_price` decimal(20,2) DEFAULT NULL COMMENT '周期销售价',
  `sesame_deposit` decimal(10,2) DEFAULT NULL COMMENT '芝麻押金评估金额',
  PRIMARY KEY (`id`),
  KEY `idx_sku_id` (`sku_id`) USING BTREE,
  KEY `idx_item_id` (`item_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=83137 COMMENT='商品周期价格表';

-- ----------------------------
-- Table structure for ct_product_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_evaluation`;
CREATE TABLE `ct_product_evaluation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单ID',
  `product_id` varchar(64) NOT NULL COMMENT '商品ID',
  `parent_id` bigint DEFAULT NULL,
  `uid` varchar(64) NOT NULL COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户姓名',
  `user_icon` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `star_count_service` int NOT NULL COMMENT '服务星级评价',
  `star_count_express` int NOT NULL COMMENT '物流星级评价',
  `star_count_description` int NOT NULL COMMENT '描述相符评价',
  `content` varchar(255) DEFAULT NULL COMMENT '评论内容',
  `is_chosen` varchar(8) NOT NULL DEFAULT 'F' COMMENT '是否是精选评价',
  `contains_pic` varchar(8) NOT NULL COMMENT '是否包含图片',
  `contains_append` varchar(8) NOT NULL DEFAULT 'F' COMMENT '是否有追加评价',
  `create_time` datetime NOT NULL,
  `delete_time` datetime DEFAULT NULL,
  `channel` varchar(255) DEFAULT NULL COMMENT '来源渠道 001&006',
  `status` bigint DEFAULT NULL COMMENT '生效 0生效 1失效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 COMMENT='商品评论';

-- ----------------------------
-- Table structure for ct_product_give_back_addresses
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_give_back_addresses`;
CREATE TABLE `ct_product_give_back_addresses` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `item_id` varchar(255) NOT NULL COMMENT '商品id',
  `address_id` int NOT NULL COMMENT '归还地址id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11904 COMMENT='商品归还地址';

-- ----------------------------
-- Table structure for ct_product_image
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_image`;
CREATE TABLE `ct_product_image` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `src` varchar(255) NOT NULL COMMENT '图片链接',
  `product_id` varchar(64) NOT NULL COMMENT '所属产品id',
  `is_main` int NOT NULL DEFAULT '0' COMMENT '商品展示主图  1 展示第一栏   其余设定排序按数字大小规则   不排序传0',
  PRIMARY KEY (`id`),
  KEY `idx_item_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31539 COMMENT='商品主图表';

-- ----------------------------
-- Table structure for ct_product_label
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_label`;
CREATE TABLE `ct_product_label` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `label` varchar(64) NOT NULL COMMENT '租赁标签',
  `item_id` varchar(255) NOT NULL COMMENT '所属产品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 COMMENT='商品租赁标签';

-- ----------------------------
-- Table structure for ct_product_parameter
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_parameter`;
CREATE TABLE `ct_product_parameter` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `name` varchar(64) NOT NULL COMMENT '参数名称',
  `value` varchar(64) DEFAULT NULL COMMENT '参数值',
  `product_id` varchar(64) NOT NULL COMMENT '商品Id',
  PRIMARY KEY (`id`),
  KEY `idx_product_spec_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31681 COMMENT='商品参数信息表';

-- ----------------------------
-- Table structure for ct_product_service_marks
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_service_marks`;
CREATE TABLE `ct_product_service_marks` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `item_id` varchar(255) NOT NULL COMMENT '商品id',
  `info_id` int NOT NULL COMMENT '服务标信息id，是指platform_serivce_mark的id',
  PRIMARY KEY (`id`),
  KEY `idx_info_id` (`info_id`) USING BTREE
) ENGINE=InnoDB COMMENT='商品服务标';

-- ----------------------------
-- Table structure for ct_product_skus
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_skus`;
CREATE TABLE `ct_product_skus` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `item_id` varchar(128) DEFAULT NULL COMMENT '产品ID',
  `market_price` decimal(20,2) NOT NULL COMMENT '市场价',
  `inventory` int NOT NULL COMMENT '当前库存',
  `total_inventory` int NOT NULL COMMENT '总库存',
  `old_new_degree` int NOT NULL DEFAULT '0' COMMENT '1为全新 2为99新 3为95新 4为9成新 5为8成新 6为7成新',
  `buy_out_support` tinyint NOT NULL COMMENT '是否可以买断 1:可以买断。0:不可以买断',
  `sale_price` decimal(20,2) DEFAULT NULL COMMENT '销售价',
  `deposit_price` decimal(20,2) DEFAULT NULL COMMENT '押金',
  `finish_keep` tinyint DEFAULT NULL COMMENT '是否租满即送',
  `is_support_stage` bigint DEFAULT NULL COMMENT '是否支持分期 0-不支持；1支持',
  PRIMARY KEY (`id`),
  KEY `item_id` (`item_id`) USING BTREE,
  KEY `idx_id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=45002 COMMENT='商品sku表';

-- ----------------------------
-- Table structure for ct_product_sku_values
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_sku_values`;
CREATE TABLE `ct_product_sku_values` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `spec_value_id` bigint NOT NULL COMMENT '商品参数值id 是product_spec_value中的id',
  `sku_id` bigint NOT NULL COMMENT '库存Id',
  PRIMARY KEY (`id`),
  KEY `idx_sku_id` (`sku_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=81418 COMMENT='商品sku规格属性value表';

-- ----------------------------
-- Table structure for ct_product_snapshots
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_snapshots`;
CREATE TABLE `ct_product_snapshots` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `item_id` varchar(128) NOT NULL COMMENT '商品id',
  `version` bigint NOT NULL COMMENT '版本号 毫秒时间戳',
  `data` longtext NOT NULL COMMENT '快照数据',
  `shop_id` varchar(255) NOT NULL COMMENT '所属店铺id',
  `status` int DEFAULT NULL COMMENT '0 待审核 1审核通过 2审核拒绝',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名字',
  PRIMARY KEY (`id`),
  KEY `idx_item_id` (`item_id`) USING BTREE,
  KEY `index_item_id` (`item_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53424 COMMENT='商品快照表';

-- ----------------------------
-- Table structure for ct_product_spec
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_spec`;
CREATE TABLE `ct_product_spec` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `spec_id` int NOT NULL COMMENT 'ct_platform_spec -->id',
  `item_id` varchar(255) NOT NULL COMMENT '所属产品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21017 COMMENT='商品属性规格表';

-- ----------------------------
-- Table structure for ct_product_spec_value
-- ----------------------------
DROP TABLE IF EXISTS `ct_product_spec_value`;
CREATE TABLE `ct_product_spec_value` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '商品sku值名称',
  `product_spec_id` bigint NOT NULL COMMENT '所属参数id',
  PRIMARY KEY (`id`),
  KEY `idx_product_spec_id` (`product_spec_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=44121 COMMENT='商品规格属性value表';

-- ----------------------------
-- Table structure for ct_shop
-- ----------------------------
DROP TABLE IF EXISTS `ct_shop`;
CREATE TABLE `ct_shop` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '店铺名称',
  `logo` varchar(255) DEFAULT NULL COMMENT '店铺logo链接 80x80',
  `background` varchar(255) DEFAULT NULL COMMENT '店铺背景图链接',
  `description` varchar(512) DEFAULT NULL COMMENT '店铺介绍',
  `shop_type_id` smallint DEFAULT '0' COMMENT '店铺的经营类型',
  `main_category_id` int DEFAULT NULL COMMENT '店铺主营类目id',
  `status` tinyint DEFAULT '0' COMMENT '店铺状态 0待提交企业资质 1待填写店铺信息 2待提交品牌信息 3正在审核 4审核不通过 5审核通过，准备开店 6开店成功',
  `approval_time` datetime DEFAULT NULL COMMENT '店铺审核通过时间',
  `reason` varchar(255) DEFAULT NULL COMMENT '审核原因  拒绝时录入',
  `regist_time` varchar(255) DEFAULT NULL COMMENT '店铺注册时间，用于shop_id加密',
  `shop_id` varchar(255) DEFAULT NULL COMMENT '由平台自动生成的唯一标识',
  `is_locked` tinyint DEFAULT '0' COMMENT '店铺是否被冻结',
  `locked_time` timestamp NULL DEFAULT NULL COMMENT '店铺冻结时间',
  `is_disabled` tinyint DEFAULT '0' COMMENT '店铺是否被封',
  `service_tel` varchar(255) DEFAULT NULL COMMENT '商家客服电话',
  `recv_msg_tel` varchar(255) DEFAULT NULL COMMENT '商家接收短信手机号码',
  `is_high_quality` tinyint DEFAULT '0' COMMENT '是否优质商家',
  `is_signing` int DEFAULT '0' COMMENT '是否签约 0否 1是',
  `is_phone_examination` tinyint DEFAULT '0' COMMENT '是否需要电话审核 0否 1是',
  `user_email` varchar(64) DEFAULT NULL COMMENT '店铺联系邮箱',
  `user_name` varchar(64) DEFAULT NULL COMMENT '店铺联系人姓名',
  `user_tel` varchar(64) DEFAULT NULL COMMENT '店铺联系人电话',
  `zfb_code` varchar(64) DEFAULT NULL COMMENT '支付宝账号',
  `zfb_name` varchar(64) DEFAULT NULL COMMENT '支付宝姓名',
  `shop_contract_link` varchar(255) DEFAULT NULL COMMENT '店铺合同链接',
  `shop_address` varchar(255) DEFAULT NULL,
  `balance` varchar(255) DEFAULT NULL,
  `contract_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '合同code,电子签章存证时需要的字段',
  `signer_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '签署人流水号,电子签章存证时需要的字段',
  PRIMARY KEY (`id`),
  KEY `idx_shop_id` (`shop_id`(191)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=215 COMMENT='店铺表';

-- ----------------------------
-- Table structure for ct_shop_additional_services
-- ----------------------------
DROP TABLE IF EXISTS `ct_shop_additional_services`;
CREATE TABLE `ct_shop_additional_services` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  `shop_id` varchar(64) NOT NULL DEFAULT '' COMMENT '店铺id',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '增值服务名称',
  `content` varchar(10000) DEFAULT NULL COMMENT '增值服务内容',
  `price` decimal(10,0) NOT NULL COMMENT '增值服务价格',
  `approval_status` tinyint(1) DEFAULT '1' COMMENT '增值服务审核状态；0:审核拒绝；1:审核中；2:审核成功；默认1',
  `status` tinyint(1) DEFAULT '1' COMMENT '增值服务状态；0:无效；1:有效；默认1（店铺删除此服务，修改此状态为0）',
  `description` longtext COMMENT '增值简短服务说明',
  `original_add_id` int DEFAULT NULL COMMENT '要转的增值服务ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `shop_id_index` (`shop_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=253 COMMENT='店铺增值服务表';

-- ----------------------------
-- Table structure for ct_shop_attract
-- ----------------------------
DROP TABLE IF EXISTS `ct_shop_attract`;
CREATE TABLE `ct_shop_attract` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `tel` varchar(64) NOT NULL COMMENT '电话',
  `company` varchar(255) DEFAULT NULL COMMENT '公司',
  `city` varchar(255) DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL COMMENT '状态，0:未处理 1:已处理',
  `result` varchar(2) DEFAULT NULL COMMENT '处理结果',
  `resultdesc` varchar(500) DEFAULT NULL COMMENT '处理结果说明',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  PRIMARY KEY (`id`),
  KEY `ct_shop_attract_telidx` (`tel`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=103 COMMENT='招商表';

-- ----------------------------
-- Table structure for ct_shop_enterprise_certificates
-- ----------------------------
DROP TABLE IF EXISTS `ct_shop_enterprise_certificates`;
CREATE TABLE `ct_shop_enterprise_certificates` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `se_info_id` int NOT NULL COMMENT '关联shop_enterprice_info表中的id',
  `image` varchar(255) NOT NULL COMMENT '证书图片链接',
  `type` tinyint NOT NULL COMMENT '类型 0为营业执照号 1为组织机构代码证 2为税务登记证 3为法人身份证正面 4为法人身份证背面',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=630 COMMENT='店铺资质证书表';

-- ----------------------------
-- Table structure for ct_shop_enterprise_infos
-- ----------------------------
DROP TABLE IF EXISTS `ct_shop_enterprise_infos`;
CREATE TABLE `ct_shop_enterprise_infos` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `shop_id` varchar(255) NOT NULL COMMENT '所属店铺id',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '企业名称',
  `registration_capital` decimal(22,2) DEFAULT NULL COMMENT '企业注册资金',
  `business_license_no` varchar(255) DEFAULT NULL COMMENT '营业执照号',
  `license_start` datetime DEFAULT NULL COMMENT '营业执照有效期起始时间',
  `license_end` datetime DEFAULT NULL COMMENT '营业执照有效期结束时间 如果为长期，则为2099-01-01',
  `license_province` int DEFAULT NULL COMMENT '营业执照所在省',
  `license_city` int DEFAULT NULL COMMENT '营业执照所在市',
  `business_scope` text COMMENT '营业执照经营范围',
  `realname` varchar(255) DEFAULT NULL COMMENT '法人代表姓名',
  `telephone` varchar(255) DEFAULT NULL COMMENT '法人手机号码',
  `contact_name` varchar(255) DEFAULT NULL COMMENT '联系人姓名',
  `contact_telephone` varchar(255) DEFAULT NULL COMMENT '联系人手机号',
  `contact_qq` varchar(255) DEFAULT NULL COMMENT '联系人qq',
  `contact_email` varchar(255) DEFAULT NULL COMMENT '联系人邮箱',
  `is_merged` tinyint DEFAULT '0' COMMENT '是否多证合一',
  `ocl_start` datetime DEFAULT NULL COMMENT 'organization code license 即组织机构代码证，太长了，缩写了一下，此证的有效期的起始时间',
  `ocl_end` datetime DEFAULT NULL COMMENT '组织机构代码证有效期的结束时间',
  `status` tinyint DEFAULT '0' COMMENT '0|正在审核1|审核不通过2|审核通过',
  `reason` varchar(255) DEFAULT NULL COMMENT '审核原因  （拒绝时填入）',
  `identity` varchar(32) DEFAULT NULL COMMENT '	身份证号码',
  `license_street` varchar(255) DEFAULT NULL COMMENT '企业地址',
  `seal_no` varchar(20) DEFAULT NULL COMMENT '印章编号',
  `contract_seal_no` varchar(50) DEFAULT NULL COMMENT '合同章编号',
  PRIMARY KEY (`id`),
  KEY `idx_shop_id` (`shop_id`(191)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=215 COMMENT='店铺资质表';

-- ----------------------------
-- Table structure for ct_shop_fund_flow
-- ----------------------------
DROP TABLE IF EXISTS `ct_shop_fund_flow`;
CREATE TABLE `ct_shop_fund_flow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` varchar(128) NOT NULL COMMENT '店铺ID',
  `operate` varchar(32) DEFAULT NULL COMMENT '操作类型',
  `operator` varchar(16) DEFAULT NULL COMMENT '操作员',
  `before_amount` varchar(255) DEFAULT NULL COMMENT '变动前金额',
  `change_amount` varchar(255) DEFAULT NULL COMMENT '变动金额',
  `after_amount` varchar(255) DEFAULT NULL COMMENT '变动后金额',
  `flow_no` varchar(128) DEFAULT NULL,
  `remark` varchar(2048) DEFAULT NULL,
  `status` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `flow_no` (`flow_no`) USING BTREE,
  KEY `shop_id_idx` (`shop_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=167 COMMENT='店铺资金变动流水';

-- ----------------------------
-- Table structure for ct_shop_give_back_addresses
-- ----------------------------
DROP TABLE IF EXISTS `ct_shop_give_back_addresses`;
CREATE TABLE `ct_shop_give_back_addresses` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  `shop_id` varchar(255) NOT NULL COMMENT '店铺id',
  `province_id` int NOT NULL COMMENT '省id',
  `city_id` int NOT NULL COMMENT '市id',
  `area_id` int NOT NULL COMMENT '区id',
  `street` text NOT NULL COMMENT '街道',
  `telephone` varchar(255) NOT NULL COMMENT '收件人手机号码',
  `zcode` varchar(255) DEFAULT NULL COMMENT '邮编',
  `name` varchar(255) NOT NULL COMMENT '收件人姓名',
  `freight_type` varchar(15) DEFAULT NULL COMMENT '快递方式-包邮:FREE-到付:PAY-自提:SELF',
  PRIMARY KEY (`id`),
  KEY `idx_shop_id` (`shop_id`(191)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=210 COMMENT='店铺归还地址表';

-- ----------------------------
-- Table structure for ct_shop_split_bill_account
-- ----------------------------
DROP TABLE IF EXISTS `ct_shop_split_bill_account`;
CREATE TABLE `ct_shop_split_bill_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(255)  DEFAULT NULL COMMENT '店铺名称',
  `shop_id` varchar(255)  DEFAULT NULL COMMENT '店铺ID',
  `identity` varchar(255)  DEFAULT NULL COMMENT '商家支付宝账号',
  `name` varchar(100)  DEFAULT NULL COMMENT '商家支付宝实名认证姓名',
  `add_user` varchar(255)  DEFAULT NULL COMMENT '添加记录的管理员',
  `status` varchar(20)  NOT NULL COMMENT '状态',
  `audit_user` varchar(255)  DEFAULT NULL COMMENT '审核的管理员',
  `audit_remark` varchar(255)  DEFAULT NULL COMMENT '审核备注',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `shop_firm_info` varchar(255)  DEFAULT NULL COMMENT '店铺资质信息',
  `app_version` varchar(16)  DEFAULT NULL COMMENT '分账规则所属小程序版本-LITE:简版 ZWZ:租物租',
  `cycle` varchar(255) DEFAULT NULL COMMENT '周期 周一到周日',
  PRIMARY KEY (`id`),
  KEY `shop_id_idx` (`shop_id`(191)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=475;

-- ----------------------------
-- Table structure for ct_shop_split_bill_rule
-- ----------------------------
DROP TABLE IF EXISTS `ct_shop_split_bill_rule`;
CREATE TABLE `ct_shop_split_bill_rule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` bigint NOT NULL COMMENT '分账账户ID',
  `type` varchar(20) NOT NULL COMMENT '分账类型 首月租金：RENT_MONTH 三月租金：RENT_THREE 全租金：RENT_ALL 买断：BUY_OUT',
  `delay_num` tinyint DEFAULT NULL COMMENT '延迟天数',
  `delay_type` varchar(20) DEFAULT NULL COMMENT '延迟天数类型',
  `scale` decimal(3,2) NOT NULL COMMENT '分账比例，小于等于1',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `accid_type_index` (`account_id`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=236;

-- ----------------------------
-- Table structure for ct_split_bill
-- ----------------------------
DROP TABLE IF EXISTS `ct_split_bill`;
CREATE TABLE `ct_split_bill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `split_bill_rule_id` bigint NOT NULL COMMENT '分账规则ID',
  `account_period_id` bigint DEFAULT NULL COMMENT '账期ID',
  `order_no` varchar(64) NOT NULL DEFAULT '' COMMENT '分账单号',
  `order_id` varchar(64) NOT NULL COMMENT '分账订单ID',
  `period` int DEFAULT NULL COMMENT '期数',
  `uid` varchar(64) NOT NULL COMMENT '分账订单所属用户ID',
  `shop_id` varchar(255) NOT NULL COMMENT '店铺ID',
  `type` varchar(20) NOT NULL COMMENT '分账类型',
  `identity` varchar(255) NOT NULL COMMENT '支付宝账号',
  `name` varchar(255) NOT NULL COMMENT '支付宝实名信息',
  `scale` decimal(3,2) NOT NULL COMMENT '分账比例',
  `user_pay_amount` decimal(10,2) NOT NULL COMMENT '用户支付金额',
  `trans_amount` decimal(10,2) NOT NULL COMMENT '分给商家的金额',
  `app_version` varchar(16) DEFAULT NULL,
  `status` varchar(255) NOT NULL COMMENT '创建时间',
  `user_pay_time` datetime DEFAULT NULL COMMENT '用户支付时间',
  `plan_pay_time` datetime DEFAULT NULL COMMENT '计划支付时间',
  `real_pay_time` datetime DEFAULT NULL COMMENT '实际支付时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2492 COMMENT='分账信息';

-- ----------------------------
-- Table structure for ct_sx_report_record
-- ----------------------------
DROP TABLE IF EXISTS `ct_sx_report_record`;
CREATE TABLE `ct_sx_report_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` varchar(64) DEFAULT NULL COMMENT '用户或商家生成的唯一主键',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户的姓名',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `multiple_score` int DEFAULT NULL COMMENT '分数',
  `report_result` mediumtext COMMENT '查询结果',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int NOT NULL DEFAULT '1' COMMENT '1 首新查询正常 8 连接超时  9 首新查询异常',
  `report_type` char(2) NOT NULL COMMENT '报告类型 01：天狼星 02：新版风控报告',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `forbidden_status` int DEFAULT NULL COMMENT '1 正常 8 连接超时  9 查询异常',
  `forbidden_result` varchar(1024) DEFAULT NULL COMMENT '禁言结果',
  `query_time` datetime DEFAULT NULL COMMENT '查询时间',
  `nsf_level` char(2) DEFAULT NULL COMMENT 'nsf等级',
  `anti_cheating_level` varchar(255) DEFAULT NULL COMMENT '营销反作弊等级',
  PRIMARY KEY (`id`),
  KEY `id_no_idx` (`id_card`) USING BTREE,
  KEY `order_id_idx` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17417 COMMENT='用户报告表';

-- ----------------------------
-- Table structure for ct_tab
-- ----------------------------
DROP TABLE IF EXISTS `ct_tab`;
CREATE TABLE `ct_tab` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `index_sort` int DEFAULT NULL COMMENT '''序列',
  `name` varchar(64) DEFAULT NULL COMMENT 'tab名称',
  `jump_url` varchar(255) DEFAULT NULL COMMENT ' 更多跳转的url',
  `channel_id` varchar(8) DEFAULT NULL COMMENT '渠道编号',
  `shop_id` varchar(64) DEFAULT NULL COMMENT '渠道对应的店铺ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 COMMENT='简版小程序tab配置信息';

-- ----------------------------
-- Table structure for ct_tab_product
-- ----------------------------
DROP TABLE IF EXISTS `ct_tab_product`;
CREATE TABLE `ct_tab_product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '跟新时间',
  `index_sort` int DEFAULT NULL COMMENT '序列',
  `name` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `tab_id` int DEFAULT NULL COMMENT '父tab的id',
  `image` varchar(255) DEFAULT NULL,
  `price` varchar(10) DEFAULT NULL COMMENT '价格优惠',
  `old_new_degree` varchar(10) DEFAULT NULL COMMENT '新旧程度',
  `sales_volume` bigint DEFAULT NULL COMMENT '销售量',
  `item_id` varchar(255) DEFAULT NULL COMMENT '产品唯一id',
  `link_url` varchar(255) DEFAULT NULL,
  `shop_name` varchar(64) DEFAULT NULL COMMENT '店铺名称 ',
  `status` bigint DEFAULT NULL COMMENT '0失效  1有效',
  PRIMARY KEY (`id`),
  KEY `idx_tab_id` (`tab_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=142 COMMENT='小程序tab商品';

-- ----------------------------
-- Table structure for ct_transfer_order_record
-- ----------------------------
DROP TABLE IF EXISTS `ct_transfer_order_record`;
CREATE TABLE `ct_transfer_order_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单ID',
  `transferred_product_id` varchar(64) DEFAULT NULL COMMENT '商品Id-被转',
  `transferred_snap_shot_id` bigint DEFAULT NULL COMMENT '快照id-被转',
  `transferred_sku_id` int DEFAULT NULL COMMENT '商品sku_id-被转',
  `transfer_product_id` varchar(64) DEFAULT NULL COMMENT '商品Id-接手',
  `transfer_snap_shot_id` bigint DEFAULT NULL COMMENT '快照id-接手',
  `transfer_sku_id` int DEFAULT NULL COMMENT '商品sku_id-接手',
  `transferred_shop_id` varchar(64) DEFAULT NULL COMMENT '店铺id-被转',
  `transfer_shop_id` varchar(64) DEFAULT NULL COMMENT '店铺id-接手',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作员',
  `remark` varchar(800) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3707 COMMENT='转单记录表';

-- ----------------------------
-- Table structure for ct_user
-- ----------------------------
DROP TABLE IF EXISTS `ct_user`;
CREATE TABLE `ct_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` timestamp NULL DEFAULT NULL,
  `telephone` varchar(32) DEFAULT NULL COMMENT '用户手机号',
  `uid` varchar(64) DEFAULT NULL COMMENT '用户id 自动生成',
  `is_auth` tinyint DEFAULT '0' COMMENT '0 未认证 1已经认证',
  `is_disabled` tinyint DEFAULT '0' COMMENT '帐号被封 0正常 1已经被封',
  `id_card_photo_status` tinyint DEFAULT '0' COMMENT '身份证照片上传状态',
  `channel` tinyint DEFAULT NULL COMMENT '第一次生成的用户的渠道',
  `points` int NOT NULL DEFAULT '0' COMMENT '用户剩余积分',
  `level` tinyint NOT NULL DEFAULT '1' COMMENT '会员等级',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid_idx` (`uid`) USING BTREE,
  KEY `tel_idx` (`telephone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=255 COMMENT='用户主体表';

-- ----------------------------
-- Table structure for ct_user_address
-- ----------------------------
DROP TABLE IF EXISTS `ct_user_address`;
CREATE TABLE `ct_user_address` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  `province` int NOT NULL COMMENT '省',
  `city` int NOT NULL COMMENT '市',
  `uid` varchar(64) NOT NULL COMMENT '所属用户id',
  `area` int DEFAULT '-1' COMMENT '区',
  `street` varchar(255) NOT NULL COMMENT '详细地址',
  `zcode` varchar(16) DEFAULT NULL COMMENT '邮编',
  `telephone` varchar(16) NOT NULL COMMENT '手机号码',
  `realname` varchar(16) NOT NULL COMMENT '收货人姓名',
  `is_default` tinyint NOT NULL DEFAULT '0' COMMENT '是否为默认收货地址  0否 1是 ',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=171389 COMMENT='用户地址表';

-- ----------------------------
-- Table structure for ct_user_certification
-- ----------------------------
DROP TABLE IF EXISTS `ct_user_certification`;
CREATE TABLE `ct_user_certification` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `uid` varchar(64) NOT NULL DEFAULT '' COMMENT '用户平台唯一标识',
  `user_name` varchar(64) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `id_card` varchar(32) NOT NULL DEFAULT '' COMMENT '用户身份号码',
  `telephone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `id_card_front_url` varchar(128) DEFAULT NULL COMMENT '身份证照片 前面',
  `id_card_back_url` varchar(128) DEFAULT NULL COMMENT '身份证照片背面',
  `limit_date` date DEFAULT NULL COMMENT '身份证到期日期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `start_date` date DEFAULT NULL COMMENT '有效期开始时间',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `sex` varchar(16) DEFAULT NULL COMMENT '性别',
  `nation` varchar(64) DEFAULT NULL COMMENT '民族',
  `issue_org` varchar(255) DEFAULT NULL COMMENT '发证机关',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid` (`uid`) USING BTREE,
  KEY `idx_id_card` (`id_card`) USING BTREE,
  KEY `idx_user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=188979 COMMENT='用户认证表';

-- ----------------------------
-- Table structure for ct_user_collection
-- ----------------------------
DROP TABLE IF EXISTS `ct_user_collection`;
CREATE TABLE `ct_user_collection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uid` varchar(64) NOT NULL COMMENT '用户ID',
  `resource_id` varchar(64) NOT NULL COMMENT '收藏的资源ID',
  `resource_type` varchar(16) NOT NULL COMMENT '收藏的资源类型',
  `create_time` datetime NOT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 COMMENT='用户收藏表';

-- ----------------------------
-- Table structure for ct_user_orders
-- ----------------------------
DROP TABLE IF EXISTS `ct_user_orders`;
CREATE TABLE `ct_user_orders` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) DEFAULT NULL COMMENT '下单用户id',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `payment_no` varchar(255) DEFAULT NULL COMMENT '支付订单号',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `order_id` varchar(128) DEFAULT NULL COMMENT '订单号',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户姓名',
  `product_id` varchar(64) DEFAULT NULL COMMENT '产品id',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `status` varchar(255) DEFAULT NULL COMMENT '订单状态\n--     WAITING_PAYMENT("01", "待支付"),\n--     PAYING("02", "支付中"),\n--     PAYED_USER_APPLY_CLOSE("03", "已支付申请关单"),\n--     TO_AUDIT("11", "待审核"),\n--     PENDING_DEAL("04", "待发货"),\n--     WAITING_USER_RECEIVE_CONFIRM("05", "待确认收货"),\n--     RENTING("06", "租用中"),\n--     TO_GIVE_BACK("12", "待归还"),\n--     WAITING_SETTLEMENT("07", "待结算"),\n--     WAITING_SETTLEMENT_PAYMENT("08", "结算待支付"),\n--     FINISH("09", "订单完成"),\n--     CLOSED("10", "交易关闭")',
  `close_type` char(2) DEFAULT NULL COMMENT '关单类型:01:未支付用户主动申请,02:支付失败,03:超时支付,04:已支付用户主动申请,05:风控拒绝,06:商家关闭订单,07:商家风控关闭订单,08:商家超时发货',
  `price` decimal(10,2) DEFAULT NULL COMMENT '租金单价',
  `rent_duration` int NOT NULL DEFAULT '0' COMMENT '租用时长',
  `express_no` varchar(255) DEFAULT NULL COMMENT '快递单号',
  `express_id` smallint DEFAULT NULL COMMENT '物流公司id',
  `rent_start` datetime DEFAULT NULL COMMENT '租用开始时间',
  `unrent_time` datetime DEFAULT NULL COMMENT '租用结束时间',
  `unrent_express_no` varchar(255) DEFAULT NULL COMMENT '退租物流单号',
  `is_violation` char(2) NOT NULL DEFAULT '0' COMMENT '是否违约 0否 1逾期 2提前归还',
  `unrent_express_id` smallint DEFAULT NULL COMMENT '退租物流id',
  `remark` text COMMENT '用户备注',
  `shop_remark` text COMMENT '商家备注',
  `service_remark` text COMMENT '客服备注',
  `give_back_remark` text COMMENT '归还备注',
  `settlement_remark` text COMMENT '结算备注',
  `order_remark` text COMMENT '订单备注',
  `type` char(2) NOT NULL DEFAULT '0' COMMENT '订单类型 0为常规订单 1为拼团订单    2 续租订单  3买断订单',
  `shop_id` varchar(255) DEFAULT NULL COMMENT '店铺id 平台自动生成',
  `logistic_form` tinyint NOT NULL DEFAULT '0' COMMENT '物流方式类型 0为快递 1为上门 2为自提',
  `settlement_time` datetime DEFAULT NULL COMMENT '结算时间',
  `give_back_offline_shop_id` int DEFAULT NULL COMMENT '归还时选择的自提点id，默认情况下与提货时选择的自提点相同，如果商品标有异地归还的服务标，则此处为之后选择的自提点id',
  `confirm_settlement_time` datetime DEFAULT NULL COMMENT '确认结算时间',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `cancel_reason` text COMMENT '取消原因',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `give_back_address_id` int DEFAULT NULL COMMENT '归还地址id',
  `close_time` datetime DEFAULT NULL COMMENT '关闭时间',
  `return_time` datetime DEFAULT NULL COMMENT '归还时间',
  `examine_status` char(2) DEFAULT NULL COMMENT '0待审核，1审核拒绝，2审核通过',
  `audit_label` char(2) DEFAULT NULL COMMENT '审核标签 00:平台审核 01:商家审核',
  `request_no` varchar(255) DEFAULT NULL,
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `face_auth_status` char(2) NOT NULL DEFAULT '01' COMMENT '刷脸认证状态 01:未认证 02:认证中 03:已认证',
  `payer_user_id` varchar(64) DEFAULT NULL COMMENT '付款方支付宝id',
  `original_order_id` varchar(64) DEFAULT NULL COMMENT '原订单号',
  `rent_credit` tinyint DEFAULT NULL,
  `rent_credit_result` varchar(64) DEFAULT NULL COMMENT '风控结论',
  `credit_level` varchar(12) DEFAULT NULL COMMENT '订单风控级别',
  `order_level` varchar(12) DEFAULT NULL COMMENT '订单风控级别',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式 -ZFB,TTF,WX',
  `user_label` varchar(32) DEFAULT NULL COMMENT '用户标签 GENERAL_USER_ORDER：常规用户 QUALITY_USER_ORDER：优质用户',
  `payment_process_method` varchar(64) DEFAULT NULL COMMENT '订单支付流程方式',
  `nsf_level` varchar(255) DEFAULT NULL,
  `serial_number` varchar(128) DEFAULT NULL COMMENT '串号',
  `cost_price` decimal(8,2) DEFAULT NULL COMMENT '成本价',
  PRIMARY KEY (`id`),
  KEY `idx_shop_id` (`shop_id`) USING BTREE,
  KEY `idx_uid` (`uid`(191)) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE COMMENT '创建时间索引,请勿删除,请勿更改名字',
  KEY `idx_request_no` (`request_no`(191)) USING BTREE,
  KEY `idx_order_id` (`status`(191),`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=340956 COMMENT='用户订单表';

-- ----------------------------
-- Table structure for ct_user_orders_purchase
-- ----------------------------
DROP TABLE IF EXISTS `ct_user_orders_purchase`;
CREATE TABLE `ct_user_orders_purchase` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) NOT NULL COMMENT '购买订单号',
  `uid` varchar(128) NOT NULL COMMENT '用户ID',
  `shop_id` varchar(64) NOT NULL COMMENT '店铺ID',
  `price` decimal(10,2) NOT NULL COMMENT '销售价格',
  `shop_coupon_reduction` decimal(10,0) NOT NULL COMMENT '店铺优惠券减免金额',
  `platform_coupon_reduction` decimal(10,2) NOT NULL COMMENT '平台优惠券减免金额',
  `freight_type` varchar(16) NOT NULL,
  `freight_amount` decimal(10,2) NOT NULL,
  `additional_service_fee` decimal(10,2) NOT NULL COMMENT '增值服务费用',
  `hb_description` varchar(255) DEFAULT NULL,
  `hb_period_num` varchar(8) DEFAULT NULL COMMENT '花呗分期数',
  `user_pay_amount` decimal(10,2) NOT NULL COMMENT '用户支付金额',
  `pay_time` datetime DEFAULT NULL,
  `express_id` varchar(64) DEFAULT NULL COMMENT '物流公司id',
  `express_no` varchar(64) DEFAULT NULL COMMENT '快递单号',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `state` varchar(32) NOT NULL COMMENT '状态',
  `cancel_type` varchar(16) DEFAULT NULL,
  `payer_user_id` varchar(255) DEFAULT NULL,
  `trade_no` varchar(255) DEFAULT NULL COMMENT '支付流水号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `channel_id` varchar(8) NOT NULL COMMENT '渠道ID',
  `cancel_reason` varchar(255) DEFAULT NULL COMMENT '取消原因',
  `split_bill_time` datetime DEFAULT NULL COMMENT '分账时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id_idx` (`order_id`),
  KEY `uid_idx` (`uid`) USING BTREE,
  KEY `shop_id_idx` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2031 COMMENT='购买订单表';

-- ----------------------------
-- Table structure for ct_user_orders_status_tranfer
-- ----------------------------
DROP TABLE IF EXISTS `ct_user_orders_status_tranfer`;
CREATE TABLE `ct_user_orders_status_tranfer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) NOT NULL COMMENT '订单编号',
  `uid` varchar(128) DEFAULT NULL COMMENT 'uid',
  `operator_id` varchar(64) DEFAULT NULL COMMENT '操作人id',
  `operator_name` varchar(64) DEFAULT NULL COMMENT '操作人姓名',
  `old_status` char(2) DEFAULT NULL COMMENT '原状态',
  `new_status` char(2) NOT NULL COMMENT '新状态',
  `operate` varchar(32) DEFAULT NULL COMMENT '操作',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `order_id_idx` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1254369 COMMENT='订单状态流转表';

-- ----------------------------
-- Table structure for ct_user_order_buy_out
-- ----------------------------
DROP TABLE IF EXISTS `ct_user_order_buy_out`;
CREATE TABLE `ct_user_order_buy_out` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) NOT NULL COMMENT '用户ID',
  `order_id` varchar(128) NOT NULL COMMENT '租赁订单ID',
  `buy_out_order_id` varchar(128) NOT NULL COMMENT '买断订单ID',
  `shop_id` varchar(64) DEFAULT NULL COMMENT '店铺id',
  `market_price` decimal(10,2) NOT NULL COMMENT '市场价，从SKU同步过来',
  `sale_price` decimal(10,2) NOT NULL COMMENT '原定销售价格，从SKU同步过来',
  `real_sale_price` decimal(10,2) NOT NULL COMMENT '实际销售价格（商家和用户沟通后可修改）',
  `paid_rent` decimal(10,2) NOT NULL COMMENT '已支付租金',
  `end_fund` decimal(10,2) NOT NULL COMMENT '应支付尾款（实际销售价格-已支付租金）',
  `shop_coupon_reduction` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '店铺优惠券抵扣金额',
  `platform_coupon_reduction` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '平台优惠券抵扣金额',
  `state` varchar(64) NOT NULL COMMENT '买断订单状态 01：用户取消。 02：关闭。  03：已完成。04:待支付。05：支付中（等待回调）',
  `out_trans_no` varchar(64) DEFAULT NULL COMMENT '商户传入的资金交易号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道来源',
  `split_bill_time` datetime DEFAULT NULL COMMENT '分账时间',
  `payment_method` varchar(15) DEFAULT NULL COMMENT '支付方式 -ZFB,TTF,WX',
  `user_label` varchar(32) DEFAULT NULL COMMENT '用户标签 GENERAL_USER_ORDER：常规用户 QUALITY_USER_ORDER：优质用户',
  `user_label_marketing_rule_id` bigint DEFAULT NULL COMMENT '用户标签营销规则id',
  `discount_amount` decimal(20,2) DEFAULT NULL COMMENT '折扣金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=latin1 COMMENT='买断表';

-- ----------------------------
-- Table structure for ct_user_order_cashes
-- ----------------------------
DROP TABLE IF EXISTS `ct_user_order_cashes`;
CREATE TABLE `ct_user_order_cashes` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `order_id` varchar(64) NOT NULL COMMENT '所属订单id，可能为续租的订单等',
  `total_deposit` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总押金 商家设置的起始押金金额',
  `deposit_reduction` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '押金减免金额',
  `deposit` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '实付押金 总的押金-芝麻信用押金减免额/实名认证押金减免额，商家可以修改',
  `coupon_reduction` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '店铺优惠券减免 符合优惠券条件，减免一定金额',
  `full_reduction` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '满减金额 符合满减/送条件，减免一定金额 买断订单为 已付租金 ',
  `platform_coupon_reduction` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '平台优惠券减免 符合平台优惠券条件，减免一定金额',
  `platform_full_reduction` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '平台满减 符合平台满减送条件，减免一定金额',
  `activity_reduction` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '活动减免金额',
  `rent` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '租金单价 商家设置的实际租金单价',
  `total_rent` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总租金 租金单价*天数*数量 + 溢价*天数*数量  买断总价',
  `settlement_rent` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '结算租金 正常情况下即总租金，但当订单开始后，可能被商家各种改，为最终结算时所需要支付的租金   买断订单若发生修改则为原买断总价',
  `total` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额 买断尾款 本次买断订单需支付的',
  `total_premium` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '溢价总金额',
  `freight_price` decimal(20,2) NOT NULL COMMENT '物流费用 物流所需要的金额',
  `lost_price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '丢失赔偿金额',
  `damage_price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '损坏赔偿金额',
  `penalty_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '违约赔偿金额',
  `refund` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '消费者申请退款的金额 消费者发起退款所要求的金额',
  `supplement_price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '补充金额 当押金加租金都不足时，用户需要补充额外的金额',
  `freight_reduction` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '减免的物流费用 商家或平台减免的物流费用，商家修改物流费用，此处的值为原物流金额 - 商家修改后的物流金额',
  `should_refund_price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '应退款金额',
  `additional_services_price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '增值费',
  `base_service_price` decimal(10,2) DEFAULT NULL,
  `original_rent` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '原始月租金',
  `original_total_rent` decimal(20,2) DEFAULT NULL COMMENT '日租单价*天数',
  `freeze_price` decimal(20,2) DEFAULT NULL COMMENT '首次冻结金额 押金加第一期支付金额',
  `coupon_recall_reduction` decimal(20,2) DEFAULT NULL COMMENT '订单唤回优惠券减免金额',
  `retain_deduction_amount` decimal(20,2) DEFAULT NULL,
  `discount_amount` decimal(20,2) DEFAULT NULL COMMENT '折扣金额',
  PRIMARY KEY (`id`),
  KEY `order_id_idx` (`order_id`) USING BTREE,
  KEY `created_at` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=625030 COMMENT='用户订单金额表';

-- ----------------------------
-- Table structure for ct_user_order_items
-- ----------------------------
DROP TABLE IF EXISTS `ct_user_order_items`;
CREATE TABLE `ct_user_order_items` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `snap_shot_id` bigint DEFAULT NULL COMMENT '快照id',
  `amount` int NOT NULL DEFAULT '1' COMMENT '商品数量',
  `product_id` varchar(32) DEFAULT NULL COMMENT '商品id',
  `sku_id` int NOT NULL COMMENT '商品sku_id',
  `order_id` varchar(64) DEFAULT NULL,
  `sale_price` decimal(8,2) DEFAULT NULL COMMENT '商品快照销售价',
  `market_price` decimal(8,2) DEFAULT NULL COMMENT '商品快照市场价',
  `spec_join_name` varchar(128) DEFAULT NULL COMMENT '订单快照规格信息',
  `product_name` varchar(128) DEFAULT NULL COMMENT '订单快照商品名称',
  `product_image` varchar(128) DEFAULT NULL COMMENT '订单快照商品主图',
  `buy_out_support` tinyint DEFAULT '0' COMMENT '是否可以买断 1:可以买断。0:不可以买断',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`,`sku_id`) USING BTREE,
  KEY `idx_snap_shot_id` (`order_id`,`snap_shot_id`) USING BTREE,
  KEY `idx_sku_id` (`sku_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=326139 COMMENT='用户订单商品表';

-- ----------------------------
-- Table structure for ct_user_third_info
-- ----------------------------
DROP TABLE IF EXISTS `ct_user_third_info`;
CREATE TABLE `ct_user_third_info` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '用户昵称',
  `province` varchar(45) DEFAULT NULL COMMENT '省',
  `city` varchar(45) DEFAULT NULL COMMENT '市',
  `gender` varchar(1) DEFAULT NULL COMMENT '性别',
  `user_type` varchar(1) DEFAULT NULL COMMENT '用户类型值 1代表公司账户2代表个人账户 ',
  `is_certified` varchar(1) DEFAULT NULL COMMENT '是否实名认证',
  `is_student_cetified` varchar(1) DEFAULT NULL COMMENT '是否学生',
  `third_id` varchar(64) DEFAULT NULL COMMENT '第三方用户唯一标识',
  `user_status` varchar(1) DEFAULT NULL COMMENT '用户状态 Q代表快速注册用户  T代表已认证用户   B代表被冻结账户  W代表已注册，未激活的账户',
  `uid` varchar(64) NOT NULL COMMENT '用户id',
  `user_name` varchar(16) DEFAULT NULL COMMENT '用户姓名',
  `telephone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `channel` varchar(10) DEFAULT NULL COMMENT 'ALIPAY:支付宝  TOUTIAO：头条',
  `channel_id` varchar(16) DEFAULT NULL,
  `app_name` varchar(255) DEFAULT NULL COMMENT 'app名称',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`) USING BTREE,
  KEY `third_id` (`third_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=410 COMMENT='用户第三方信息表';

-- ----------------------------
-- Table structure for ct_user_word_history
-- ----------------------------
DROP TABLE IF EXISTS `ct_user_word_history`;
CREATE TABLE `ct_user_word_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  `word` varchar(255) NOT NULL COMMENT '用户搜索关键字',
  `uid` varchar(64) NOT NULL COMMENT '用户ID',
  `count` int NOT NULL DEFAULT '0' COMMENT '次数',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=58 COMMENT='用户搜索记录';

-- ----------------------------
-- Table structure for ct_yx_credit_appear
-- ----------------------------
DROP TABLE IF EXISTS `ct_yx_credit_appear`;
CREATE TABLE `ct_yx_credit_appear` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `yx_report_id` bigint DEFAULT NULL COMMENT '云信报告ID',
  `id_card` varchar(64) DEFAULT NULL COMMENT '身份证',
  `uid` varchar(64) DEFAULT NULL COMMENT '用户uid',
  `order_id` varchar(64) NOT NULL COMMENT '订单编号',
  `current_periods` int DEFAULT '0' COMMENT '报送期数',
  `platform_loan_no` varchar(64) DEFAULT NULL COMMENT '申请报送返回的担保平台单号',
  `request_parameter` mediumtext COMMENT '请求参数',
  `report_result` mediumtext COMMENT '返回结果',
  `status` varchar(10) DEFAULT NULL COMMENT '上报状态\nINIT("00", "初始化"),\nPAYING("01", "处理中"),\nSUCCESS("02", "成功"),\nFAILED("03", "失败"),\nCANCEL("04", "取消")',
  `type` varchar(10) DEFAULT NULL COMMENT '上报类型\nAPPLY_FOR_SUBMISSION("00", "申请报送"),\nLOAN_SUBMISSION("01", "放款报送"),\nREPAYMENT_SUBMISSION("02", "还款报送"),\nCOMPENSATION_SUBMISSION("03", "代偿报送"),\nRECOVER_SUBMISSION("04", "追偿报送"),',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '请求时间',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `statement_time` datetime DEFAULT NULL COMMENT '账单到期日',
  `loan_no` varchar(64) DEFAULT NULL COMMENT '资产方订单号',
  `stage_status` varchar(10) DEFAULT NULL COMMENT '分期订单状态\nUN_PAY("1", "待还款"),\nPAYED("2", "已还款"),\nOVERDUE_PAYED("3", "逾期已还款"),\nOVERDUE_NOT_PAY("4", "逾期待还款"),\nCANCEL("5", "已取消"),\nSETTLED("6", "已结算"),\nREFUNDED("7", "已退款"),',
  `current_periods_rent` decimal(10,2) DEFAULT NULL COMMENT '分期账单金额',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_platform` (`platform_loan_no`)
) ENGINE=InnoDB COMMENT='云信征信上报账单信息表';

-- ----------------------------
-- Table structure for ct_yx_credit_report
-- ----------------------------
DROP TABLE IF EXISTS `ct_yx_credit_report`;
CREATE TABLE `ct_yx_credit_report` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uid` varchar(64) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户姓名',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `request_parameter` mediumtext COMMENT '请求参数',
  `report_result` mediumtext COMMENT '查询结果',
  `status` varchar(10) DEFAULT NULL COMMENT '状态 1 查询中 2 成功 3失败',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `query_time` datetime DEFAULT NULL COMMENT '请求时间',
  `serial_no` varchar(64) DEFAULT NULL COMMENT '查询流水号',
  `operator_id` varchar(64) DEFAULT NULL COMMENT '操作人ID',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `query_type` char(2) DEFAULT NULL COMMENT '云信查询类型 1:订单查询 2：三要素查询',
  `audit_operator_id` varchar(64) DEFAULT NULL COMMENT '操作人ID',
  `audit_operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `audit_status` varchar(64) DEFAULT NULL COMMENT '操作人',
  `certificate` longtext COMMENT '查询凭证',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_id_card` (`id_card`) USING BTREE
) ENGINE=InnoDB COMMENT='云信征信报告信息表';

CREATE TABLE `ct_audit_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `backstage_user_id` bigint(20) NOT NULL COMMENT '订单编号',
  `mobile` varchar(16)  DEFAULT NULL COMMENT '电话号码',
  `status` varchar(16)  DEFAULT NULL COMMENT '信审人员状态',
  `name` varchar(64)  DEFAULT NULL COMMENT '名称',
  `type` varchar(16)  DEFAULT NULL COMMENT '类型',
  `shop_id` varchar(64)  DEFAULT NULL COMMENT '店铺ID，运营人员为固定OPE',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bid_idx` (`backstage_user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=394  COMMENT='信审人员';

CREATE TABLE `ct_shop_withdraw_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` varchar(128)  NOT NULL COMMENT '店铺ID',
  `apply_uid` bigint(20) DEFAULT NULL COMMENT '申请人',
  `amount` varchar(255)  DEFAULT NULL COMMENT '金额',
  `status` varchar(64)  DEFAULT NULL COMMENT '状态',
  `audit_uid` bigint(20) DEFAULT NULL COMMENT '审核人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `out_order_no` varchar(64)  DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `shop_id_idx` (`shop_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=195 COMMENT='店铺资金变动流水';

CREATE TABLE `ct_fee_bill_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` varchar(255) DEFAULT NULL COMMENT '店铺ID',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '开票金额',
  `ticket_status` varchar(255) DEFAULT NULL COMMENT '状态',
  `apply_uid` varchar(16) DEFAULT NULL COMMENT '申请用户ID',
  `audit_uid` varchar(255) DEFAULT NULL COMMENT '审核用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `aliuid_channel` (`audit_uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67509 COMMENT='费用开票记录';



CREATE TABLE `ct_config_banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel_id` varchar(10) DEFAULT NULL COMMENT '渠道',
  `url` varchar(255)  DEFAULT NULL COMMENT '图片链接',
  `jump_url` varchar(255)  DEFAULT NULL COMMENT '跳转链接',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `begin_time` datetime DEFAULT NULL COMMENT '生效时间',
  `end_time` datetime DEFAULT NULL COMMENT '失效时间',
  `open_status` int(10) DEFAULT NULL COMMENT '开关',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='Banner配置';


CREATE TABLE `ct_config_column` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '渠道编号',
  `type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型，栏目，活动专区',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '公告内容',
  `url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片链接',
  `product_ids` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '产品id',
  `paperwork` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文案',
  `jump_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '跳转链接',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='栏目配置';

CREATE TABLE `ct_config_cubes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel_id` varchar(10)  DEFAULT NULL COMMENT '渠道',
  `paperwork` varchar(255)  DEFAULT NULL COMMENT '主标题',
  `paperwork_copy` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '副标题',
  `product_ids` varchar(255)  DEFAULT NULL COMMENT '商品id',
  `url` varchar(255)  DEFAULT NULL COMMENT '背景图链接',
  `jump_url` varchar(255)  DEFAULT NULL COMMENT '跳转链接',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='豆腐块配置';


CREATE TABLE `ct_config_icon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel_id` varchar(10)  DEFAULT NULL COMMENT '渠道',
  `url` varchar(255)  DEFAULT NULL COMMENT '图片链接',
  `title` varchar(255)  DEFAULT NULL COMMENT '标题',
  `jump_url` varchar(255)  DEFAULT NULL COMMENT '跳转链接',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `tag` varchar(255)  DEFAULT NULL COMMENT '角标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='金刚区配置';


CREATE TABLE `ct_swiping_activity_order` (
     `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
     `order_id` varchar(64) DEFAULT NULL COMMENT '订单ID',
     `product_id` varchar(64) DEFAULT NULL COMMENT '商品Id',
     `type` varchar(64) DEFAULT NULL COMMENT '活动类型',
     `create_time` datetime NOT NULL COMMENT '创建时间',
     `update_time` datetime NOT NULL COMMENT '更新时间',
     `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 COMMENT='刷单活动表';

CREATE TABLE `ct_user_point` (
     `id` bigint(30) NOT NULL AUTO_INCREMENT,
     `position` varchar(256) DEFAULT NULL COMMENT '渠道位置',
     `uid` varchar(256) DEFAULT NULL COMMENT '用户ID',
     `action` varchar(256) DEFAULT NULL COMMENT '活动名称',
     `channel_id` varchar(256) DEFAULT NULL COMMENT '渠道',
     `create_time` datetime DEFAULT NULL COMMENT '创建时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 COMMENT='用户活动埋点统计';