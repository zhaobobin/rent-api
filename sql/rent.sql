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


/*
定时任务需要使用到的表结构
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int DEFAULT NULL,
  `INT_PROP_2` int DEFAULT NULL,
  `LONG_PROP_1` bigint DEFAULT NULL,
  `LONG_PROP_2` bigint DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint DEFAULT NULL,
  `PREV_FIRE_TIME` bigint DEFAULT NULL,
  `PRIORITY` int DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for worker_node
-- ----------------------------
DROP TABLE IF EXISTS `worker_node`;
CREATE TABLE `worker_node` (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT 'auto increment id',
  `HOST_NAME` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'host name',
  `PORT` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'port',
  `TYPE` int NOT NULL COMMENT 'node type: ACTUAL or CONTAINER',
  `LAUNCH_DATE` date NOT NULL COMMENT 'launch date',
  `MODIFIED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'modified time',
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1969 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='DB WorkerID Assigner for UID Generator';


-- 基础配置
INSERT INTO `ct_backstage_user` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `email`, `password`, `type`, `status`, `mobile`, `shop_id`, `salt`, `remark`, `create_user_name`) VALUES ('1', '2020-06-28 07:25:10', '2020-10-20 15:38:42', NULL, '主账号', 'zwz@shouxin168.com', '6291dd26695ec9ec7deca371dfd379d0', 'OPE', 'VALID', 'admin', 'OPE', 'TEMP', NULL, 'sys');


INSERT INTO `ct_platform_spec` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `category_id`) VALUES ('1', '2019-01-01 17:33:51', NULL, NULL, '颜色', '0');
INSERT INTO `ct_platform_spec` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `category_id`) VALUES ('2', '2019-01-01 17:35:51', NULL, NULL, '规格', '0');


INSERT INTO `ct_config` (`id`, `code`, `name`, `value`, `create_time`, `update_time`, `delete_time`) VALUES ('1', 'contract:fee', '电子合同', '1', '2020-11-11 17:24:25', '2020-11-23 15:50:06', NULL);
INSERT INTO `ct_config` (`id`, `code`, `name`, `value`, `create_time`, `update_time`, `delete_time`) VALUES ('2', 'assessment:fee', '租押分离费用', '1.25', '2021-08-25 16:02:11', '2021-08-25 16:02:13', NULL);
INSERT INTO `ct_config` (`id`, `code`, `name`, `value`, `create_time`, `update_time`, `delete_time`) VALUES ('3', 'report:fee', '风控报告费用', '2.5', '2021-11-03 13:48:19', '2021-11-03 13:48:21', NULL);


INSERT INTO `ct_backstage_department` (`id`, `name`, `description`, `platform`, `shop_id`, `create_time`, `update_time`) VALUES ('1', '运营主管', '商家端最高权限部门-系统级别部门，勿删', 'SYS', 'SYS', '2020-08-05 14:48:32', '2020-08-05 14:48:33');
INSERT INTO `ct_backstage_department` (`id`, `name`, `description`, `platform`, `shop_id`, `create_time`, `update_time`) VALUES ('2', '商家主管', '商家端最高权限部门-系统级别部门，勿删', 'SYS', 'SYS', '2020-08-05 14:45:55', '2020-08-05 14:45:57');
INSERT INTO `ct_backstage_department` (`id`, `name`, `description`, `platform`, `shop_id`, `create_time`, `update_time`) VALUES ('3', '店铺未通过审核商家权限', '商家端最高权限部门-系统级别部门，勿删', 'SYS', 'SYS', '2021-02-03 10:04:28', '2021-02-03 10:04:30');


INSERT INTO `ct_order_complaints_type` (`id`, `name`, `create_time`, `delete_time`) VALUES ('1', '其他', '2020-10-10 14:33:19', NULL);
INSERT INTO `ct_order_complaints_type` (`id`, `name`, `create_time`, `delete_time`) VALUES ('2', '引导至其他渠道下单', '2020-09-27 16:04:40', NULL);
INSERT INTO `ct_order_complaints_type` (`id`, `name`, `create_time`, `delete_time`) VALUES ('3', '虚假宣传', '2020-09-27 16:04:51', NULL);
INSERT INTO `ct_order_complaints_type` (`id`, `name`, `create_time`, `delete_time`) VALUES ('4', '价格欺诈', '2020-09-27 16:05:03', NULL);
INSERT INTO `ct_order_complaints_type` (`id`, `name`, `create_time`, `delete_time`) VALUES ('5', '假冒伪劣品牌', '2020-10-10 14:32:39', NULL);
INSERT INTO `ct_order_complaints_type` (`id`, `name`, `create_time`, `delete_time`) VALUES ('6', '侵权', '2020-10-10 14:32:53', NULL);
INSERT INTO `ct_order_complaints_type` (`id`, `name`, `create_time`, `delete_time`) VALUES ('7', '以租赁形式从事贷款', '2020-10-10 14:33:06', NULL);
INSERT INTO `ct_order_complaints_type` (`id`, `name`, `create_time`, `delete_time`) VALUES ('8', '发货问题', '2021-11-17 16:26:22', NULL);
INSERT INTO `ct_order_complaints_type` (`id`, `name`, `create_time`, `delete_time`) VALUES ('9', '审核问题', '2021-11-17 16:26:36', NULL);


INSERT INTO `ct_ope_notice_tab` (`id`, `create_time`, `delete_time`, `update_time`, `index_sort`, `name`, `remark`) VALUES ('128', '2021-08-26 15:08:44', NULL, '2021-08-26 15:08:44', '1', '通知', NULL);

INSERT INTO `ct_material_center_category` (`id`, `name`, `width`, `height`, `create_time`, `delete_time`) VALUES ('60', '金刚区', '116', '116', '2022-07-18 18:43:37', NULL);
INSERT INTO `ct_material_center_category` (`id`, `name`, `width`, `height`, `create_time`, `delete_time`) VALUES ('56', '我的页面', '58', '58', '2022-06-08 17:23:52', NULL);
INSERT INTO `ct_material_center_category` (`id`, `name`, `width`, `height`, `create_time`, `delete_time`) VALUES ('57', '专区主图', '310', '366', '2022-06-08 18:18:20', NULL);
INSERT INTO `ct_material_center_category` (`id`, `name`, `width`, `height`, `create_time`, `delete_time`) VALUES ('58', '横幅', '710', '260', '2022-06-08 18:18:37', NULL);
INSERT INTO `ct_material_center_category` (`id`, `name`, `width`, `height`, `create_time`, `delete_time`) VALUES ('59', '专区副图', '380', '174', '2022-06-14 11:16:54', NULL);



-- 省市区字典表数据
INSERT INTO `ct_district` (`id`, `district_id`, `parent_id`, `name`, `merger_name`, `short_name`, `merger_short_name`, `level_type`, `city_code`, `zip_code`, `pinyin`, `jianpin`, `first_char`, `lng`, `lat`) VALUES
('1', '100000', '0', '中国', '中国', '中国', '中国', '0', '', '', 'China', 'CN', 'C', '116.3683244', '39.915085'),
('2', '110000', '100000', '北京', '中国,北京', '北京', '中国,北京', '1', '', '', 'Beijing', 'BJ', 'B', '116.405285', '39.904989'),
('3', '110100', '110000', '北京市', '中国,北京,北京市', '北京', '中国,北京,北京', '2', '010', '100000', 'Beijing', 'BJ', 'B', '116.405285', '39.904989'),
('4', '110101', '110100', '东城区', '中国,北京,北京市,东城区', '东城', '中国,北京,北京,东城', '3', '010', '100000', 'Dongcheng', 'DC', 'D', '116.41005', '39.93157'),
('5', '110102', '110100', '西城区', '中国,北京,北京市,西城区', '西城', '中国,北京,北京,西城', '3', '010', '100000', 'Xicheng', 'XC', 'X', '116.36003', '39.9305'),
('6', '110105', '110100', '朝阳区', '中国,北京,北京市,朝阳区', '朝阳', '中国,北京,北京,朝阳', '3', '010', '100000', 'Chaoyang', 'CY', 'C', '116.48548', '39.9484'),
('7', '110106', '110100', '丰台区', '中国,北京,北京市,丰台区', '丰台', '中国,北京,北京,丰台', '3', '010', '100000', 'Fengtai', 'FT', 'F', '116.28625', '39.8585'),
('8', '110107', '110100', '石景山区', '中国,北京,北京市,石景山区', '石景山', '中国,北京,北京,石景山', '3', '010', '100000', 'Shijingshan', 'SJS', 'S', '116.2229', '39.90564'),
('9', '110108', '110100', '海淀区', '中国,北京,北京市,海淀区', '海淀', '中国,北京,北京,海淀', '3', '010', '100000', 'Haidian', 'HD', 'H', '116.29812', '39.95931'),
('10', '110109', '110100', '门头沟区', '中国,北京,北京市,门头沟区', '门头沟', '中国,北京,北京,门头沟', '3', '010', '102300', 'Mentougou', 'MTG', 'M', '116.10137', '39.94043'),
('11', '110111', '110100', '房山区', '中国,北京,北京市,房山区', '房山', '中国,北京,北京,房山', '3', '010', '102400', 'Fangshan', 'FS', 'F', '116.14257', '39.74786'),
('12', '110112', '110100', '通州区', '中国,北京,北京市,通州区', '通州', '中国,北京,北京,通州', '3', '010', '101100', 'Tongzhou', 'TZ', 'T', '116.65716', '39.90966'),
('13', '110113', '110100', '顺义区', '中国,北京,北京市,顺义区', '顺义', '中国,北京,北京,顺义', '3', '010', '101300', 'Shunyi', 'SY', 'S', '116.65417', '40.1302'),
('14', '110114', '110100', '昌平区', '中国,北京,北京市,昌平区', '昌平', '中国,北京,北京,昌平', '3', '010', '102200', 'Changping', 'CP', 'C', '116.2312', '40.22072'),
('15', '110115', '110100', '大兴区', '中国,北京,北京市,大兴区', '大兴', '中国,北京,北京,大兴', '3', '010', '102600', 'Daxing', 'DX', 'D', '116.34149', '39.72668'),
('16', '110116', '110100', '怀柔区', '中国,北京,北京市,怀柔区', '怀柔', '中国,北京,北京,怀柔', '3', '010', '101400', 'Huairou', 'HR', 'H', '116.63168', '40.31602'),
('17', '110117', '110100', '平谷区', '中国,北京,北京市,平谷区', '平谷', '中国,北京,北京,平谷', '3', '010', '101200', 'Pinggu', 'PG', 'P', '117.12133', '40.14056'),
('18', '110118', '110100', '密云区', '中国,北京,北京市,密云区', '密云', '中国,北京,北京,密云', '3', '010', '101500', 'Miyun', 'MY', 'M', '116.84295', '40.37618'),
('19', '110119', '110100', '延庆区', '中国,北京,北京市,延庆区', '延庆', '中国,北京,北京,延庆', '3', '010', '102100', 'Yanqing', 'YQ', 'Y', '115.97494', '40.45672'),
('20', '110120', '110100', '中关村科技园区', '中国,北京,北京市,中关村科技园区', '中关村科技园区', '中国,北京,北京,中关村科技园区', '3', '010', '100190', 'Zhongguancun', 'ZGC', 'Z', '116.314569', '39.982578'),
('21', '120000', '100000', '天津', '中国,天津', '天津', '中国,天津', '1', '', '', 'Tianjin', 'TJ', 'T', '117.190182', '39.125596'),
('22', '120100', '120000', '天津市', '中国,天津,天津市', '天津', '中国,天津,天津', '2', '022', '300000', 'Tianjin', 'TJ', 'T', '117.190182', '39.125596'),
('23', '120101', '120100', '和平区', '中国,天津,天津市,和平区', '和平', '中国,天津,天津,和平', '3', '022', '300000', 'Heping', 'HP', 'H', '117.21456', '39.11718'),
('24', '120102', '120100', '河东区', '中国,天津,天津市,河东区', '河东', '中国,天津,天津,河东', '3', '022', '300000', 'Hedong', 'HD', 'H', '117.22562', '39.12318'),
('25', '120103', '120100', '河西区', '中国,天津,天津市,河西区', '河西', '中国,天津,天津,河西', '3', '022', '300000', 'Hexi', 'HX', 'H', '117.22327', '39.10959'),
('26', '120104', '120100', '南开区', '中国,天津,天津市,南开区', '南开', '中国,天津,天津,南开', '3', '022', '300000', 'Nankai', 'NK', 'N', '117.15074', '39.13821'),
('27', '120105', '120100', '河北区', '中国,天津,天津市,河北区', '河北', '中国,天津,天津,河北', '3', '022', '300000', 'Hebei', 'HB', 'H', '117.19697', '39.14816'),
('28', '120106', '120100', '红桥区', '中国,天津,天津市,红桥区', '红桥', '中国,天津,天津,红桥', '3', '022', '300000', 'Hongqiao', 'HQ', 'H', '117.15145', '39.16715'),
('29', '120110', '120100', '东丽区', '中国,天津,天津市,东丽区', '东丽', '中国,天津,天津,东丽', '3', '022', '300000', 'Dongli', 'DL', 'D', '117.31436', '39.0863'),
('30', '120111', '120100', '西青区', '中国,天津,天津市,西青区', '西青', '中国,天津,天津,西青', '3', '022', '300000', 'Xiqing', 'XQ', 'X', '117.00927', '39.14123'),
('31', '120112', '120100', '津南区', '中国,天津,天津市,津南区', '津南', '中国,天津,天津,津南', '3', '022', '300000', 'Jinnan', 'JN', 'J', '117.38537', '38.99139'),
('32', '120113', '120100', '北辰区', '中国,天津,天津市,北辰区', '北辰', '中国,天津,天津,北辰', '3', '022', '300000', 'Beichen', 'BC', 'B', '117.13217', '39.22131'),
('33', '120114', '120100', '武清区', '中国,天津,天津市,武清区', '武清', '中国,天津,天津,武清', '3', '022', '301700', 'Wuqing', 'WQ', 'W', '117.04443', '39.38415'),
('34', '120115', '120100', '宝坻区', '中国,天津,天津市,宝坻区', '宝坻', '中国,天津,天津,宝坻', '3', '022', '301800', 'Baodi', 'BD', 'B', '117.3103', '39.71761'),
('35', '120116', '120100', '滨海新区', '中国,天津,天津市,滨海新区', '滨海', '中国,天津,天津,滨海', '3', '022', '300457', 'Binhai', 'BH', 'B', '117.70162', '39.02668'),
('36', '120117', '120100', '宁河区', '中国,天津,天津市,宁河区', '宁河', '中国,天津,天津,宁河', '3', '022', '301500', 'Ninghe', 'NH', 'N', '117.8255', '39.33048'),
('37', '120118', '120100', '静海区', '中国,天津,天津市,静海区', '静海', '中国,天津,天津,静海', '3', '022', '301600', 'Jinghai', 'JH', 'J', '116.97436', '38.94582'),
('38', '120119', '120100', '蓟州区', '中国,天津,天津市,蓟州区', '蓟州', '中国,天津,天津,蓟州', '3', '022', '301900', 'Jizhou', 'JZ', 'J', '117.40799', '40.04567'),
('39', '120120', '120100', '滨海高新区', '中国,天津,天津市,滨海高新区', '滨海高新区', '中国,天津,天津,滨海高新区', '3', '022', '300000', 'Binhaigaoxinqu', 'BHGXQ', 'B', '117.124791', '39.095212'),
('40', '130000', '100000', '河北省', '中国,河北省', '河北', '中国,河北', '1', '', '', 'Hebei', 'HE', 'H', '114.502461', '38.045474'),
('41', '130100', '130000', '石家庄市', '中国,河北省,石家庄市', '石家庄', '中国,河北,石家庄', '2', '0311', '050000', 'Shijiazhuang', 'SJZ', 'S', '114.502461', '38.045474'),
('42', '130102', '130100', '长安区', '中国,河北省,石家庄市,长安区', '长安', '中国,河北,石家庄,长安', '3', '0311', '050000', 'Chang\'an', 'CA', 'C', '114.53906', '38.03665'),
('43', '130104', '130100', '桥西区', '中国,河北省,石家庄市,桥西区', '桥西', '中国,河北,石家庄,桥西', '3', '0311', '050000', 'Qiaoxi', 'QX', 'Q', '114.46977', '38.03221'),
('44', '130105', '130100', '新华区', '中国,河北省,石家庄市,新华区', '新华', '中国,河北,石家庄,新华', '3', '0311', '050000', 'Xinhua', 'XH', 'X', '114.46326', '38.05088'),
('45', '130107', '130100', '井陉矿区', '中国,河北省,石家庄市,井陉矿区', '井陉矿区', '中国,河北,石家庄,井陉矿区', '3', '0311', '050100', 'Jingxingkuangqu', 'JXKQ', 'J', '114.06518', '38.06705'),
('46', '130108', '130100', '裕华区', '中国,河北省,石家庄市,裕华区', '裕华', '中国,河北,石家庄,裕华', '3', '0311', '050000', 'Yuhua', 'YH', 'Y', '114.53115', '38.00604'),
('47', '130109', '130100', '藁城区', '中国,河北省,石家庄市,藁城区', '藁城', '中国,河北,石家庄,藁城', '3', '0311', '052160', 'Gaocheng', 'GC', 'G', '114.84671', '38.02162'),
('48', '130110', '130100', '鹿泉区', '中国,河北省,石家庄市,鹿泉区', '鹿泉', '中国,河北,石家庄,鹿泉', '3', '0311', '050200', 'Luquan', 'LQ', 'L', '114.31347', '38.08782'),
('49', '130111', '130100', '栾城区', '中国,河北省,石家庄市,栾城区', '栾城', '中国,河北,石家庄,栾城', '3', '0311', '051430', 'Luancheng', 'LC', 'L', '114.64834', '37.90022'),
('50', '130121', '130100', '井陉县', '中国,河北省,石家庄市,井陉县', '井陉', '中国,河北,石家庄,井陉', '3', '0311', '050300', 'Jingxing', 'JX', 'J', '114.14257', '38.03688'),
('51', '130123', '130100', '正定县', '中国,河北省,石家庄市,正定县', '正定', '中国,河北,石家庄,正定', '3', '0311', '050800', 'Zhengding', 'ZD', 'Z', '114.57296', '38.14445'),
('52', '130125', '130100', '行唐县', '中国,河北省,石家庄市,行唐县', '行唐', '中国,河北,石家庄,行唐', '3', '0311', '050600', 'Xingtang', 'XT', 'X', '114.55316', '38.43654'),
('53', '130126', '130100', '灵寿县', '中国,河北省,石家庄市,灵寿县', '灵寿', '中国,河北,石家庄,灵寿', '3', '0311', '050500', 'Lingshou', 'LS', 'L', '114.38259', '38.30845'),
('54', '130127', '130100', '高邑县', '中国,河北省,石家庄市,高邑县', '高邑', '中国,河北,石家庄,高邑', '3', '0311', '051330', 'Gaoyi', 'GY', 'G', '114.61142', '37.61556'),
('55', '130128', '130100', '深泽县', '中国,河北省,石家庄市,深泽县', '深泽', '中国,河北,石家庄,深泽', '3', '0311', '052500', 'Shenze', 'SZ', 'S', '115.20358', '38.18353'),
('56', '130129', '130100', '赞皇县', '中国,河北省,石家庄市,赞皇县', '赞皇', '中国,河北,石家庄,赞皇', '3', '0311', '051230', 'Zanhuang', 'ZH', 'Z', '114.38775', '37.66135'),
('57', '130130', '130100', '无极县', '中国,河北省,石家庄市,无极县', '无极', '中国,河北,石家庄,无极', '3', '0311', '052460', 'Wuji', 'WJ', 'W', '114.97509', '38.17653'),
('58', '130131', '130100', '平山县', '中国,河北省,石家庄市,平山县', '平山', '中国,河北,石家庄,平山', '3', '0311', '050400', 'Pingshan', 'PS', 'P', '114.186', '38.25994'),
('59', '130132', '130100', '元氏县', '中国,河北省,石家庄市,元氏县', '元氏', '中国,河北,石家庄,元氏', '3', '0311', '051130', 'Yuanshi', 'YS', 'Y', '114.52539', '37.76668'),
('60', '130133', '130100', '赵县', '中国,河北省,石家庄市,赵县', '赵县', '中国,河北,石家庄,赵县', '3', '0311', '051530', 'Zhaoxian', 'ZX', 'Z', '114.77612', '37.75628'),
('61', '130181', '130100', '辛集市', '中国,河北省,石家庄市,辛集市', '辛集', '中国,河北,石家庄,辛集', '3', '0311', '052360', 'Xinji', 'XJ', 'X', '115.20626', '37.94079'),
('62', '130183', '130100', '晋州市', '中国,河北省,石家庄市,晋州市', '晋州', '中国,河北,石家庄,晋州', '3', '0311', '052200', 'Jinzhou', 'JZ', 'J', '115.04348', '38.03135'),
('63', '130184', '130100', '新乐市', '中国,河北省,石家庄市,新乐市', '新乐', '中国,河北,石家庄,新乐', '3', '0311', '050700', 'Xinle', 'XL', 'X', '114.68985', '38.34417'),
('64', '130185', '130100', '高新区', '中国,河北省,石家庄市,高新区', '高新区', '中国,河北,石家庄,高新区', '3', '0311', '050000', 'Gaoxinqu', 'GXQ', 'G', '114.648171', '38.042413'),
('65', '130186', '130100', '经济技术开发区', '中国,河北省,石家庄市,经济技术开发区', '经济技术开发区', '中国,河北,石家庄,经济技术开发区', '3', '0311', '052165', 'Jingjikaifaqu', 'JJKFQ', 'J', '114.65887', '38.021185'),
('66', '130200', '130000', '唐山市', '中国,河北省,唐山市', '唐山', '中国,河北,唐山', '2', '0315', '063000', 'Tangshan', 'TS', 'T', '118.175393', '39.635113'),
('67', '130202', '130200', '路南区', '中国,河北省,唐山市,路南区', '路南', '中国,河北,唐山,路南', '3', '0315', '063000', 'Lunan', 'LN', 'L', '118.15431', '39.62505'),
('68', '130203', '130200', '路北区', '中国,河北省,唐山市,路北区', '路北', '中国,河北,唐山,路北', '3', '0315', '063000', 'Lubei', 'LB', 'L', '118.20079', '39.62436'),
('69', '130204', '130200', '古冶区', '中国,河北省,唐山市,古冶区', '古冶', '中国,河北,唐山,古冶', '3', '0315', '063100', 'Guye', 'GY', 'G', '118.45803', '39.71993'),
('70', '130205', '130200', '开平区', '中国,河北省,唐山市,开平区', '开平', '中国,河北,唐山,开平', '3', '0315', '063000', 'Kaiping', 'KP', 'K', '118.26171', '39.67128'),
('71', '130207', '130200', '丰南区', '中国,河北省,唐山市,丰南区', '丰南', '中国,河北,唐山,丰南', '3', '0315', '063300', 'Fengnan', 'FN', 'F', '118.11282', '39.56483'),
('72', '130208', '130200', '丰润区', '中国,河北省,唐山市,丰润区', '丰润', '中国,河北,唐山,丰润', '3', '0315', '063000', 'Fengrun', 'FR', 'F', '118.12976', '39.8244'),
('73', '130209', '130200', '曹妃甸区', '中国,河北省,唐山市,曹妃甸区', '曹妃甸', '中国,河北,唐山,曹妃甸', '3', '0315', '063200', 'Caofeidian', 'CFD', 'C', '118.460379', '39.273070'),
('74', '130223', '130200', '滦县', '中国,河北省,唐山市,滦县', '滦县', '中国,河北,唐山,滦县', '3', '0315', '063700', 'Luanxian', 'LX', 'L', '118.70346', '39.74056'),
('75', '130224', '130200', '滦南县', '中国,河北省,唐山市,滦南县', '滦南', '中国,河北,唐山,滦南', '3', '0315', '063500', 'Luannan', 'LN', 'L', '118.6741', '39.5039'),
('76', '130225', '130200', '乐亭县', '中国,河北省,唐山市,乐亭县', '乐亭', '中国,河北,唐山,乐亭', '3', '0315', '063600', 'Laoting', 'LT', 'L', '118.9125', '39.42561'),
('77', '130227', '130200', '迁西县', '中国,河北省,唐山市,迁西县', '迁西', '中国,河北,唐山,迁西', '3', '0315', '064300', 'Qianxi', 'QX', 'Q', '118.31616', '40.14587'),
('78', '130229', '130200', '玉田县', '中国,河北省,唐山市,玉田县', '玉田', '中国,河北,唐山,玉田', '3', '0315', '064100', 'Yutian', 'YT', 'Y', '117.7388', '39.90049'),
('79', '130281', '130200', '遵化市', '中国,河北省,唐山市,遵化市', '遵化', '中国,河北,唐山,遵化', '3', '0315', '064200', 'Zunhua', 'ZH', 'Z', '117.96444', '40.18741'),
('80', '130283', '130200', '迁安市', '中国,河北省,唐山市,迁安市', '迁安', '中国,河北,唐山,迁安', '3', '0315', '064400', 'Qian\'an', 'QA', 'Q', '118.70068', '39.99833'),
('81', '130300', '130000', '秦皇岛市', '中国,河北省,秦皇岛市', '秦皇岛', '中国,河北,秦皇岛', '2', '0335', '066000', 'Qinhuangdao', 'QHD', 'Q', '119.586579', '39.942531'),
('82', '130302', '130300', '海港区', '中国,河北省,秦皇岛市,海港区', '海港', '中国,河北,秦皇岛,海港', '3', '0335', '066000', 'Haigang', 'HG', 'H', '119.61046', '39.9345'),
('83', '130303', '130300', '山海关区', '中国,河北省,秦皇岛市,山海关区', '山海关', '中国,河北,秦皇岛,山海关', '3', '0335', '066200', 'Shanhaiguan', 'SHG', 'S', '119.77563', '39.97869'),
('84', '130304', '130300', '北戴河区', '中国,河北省,秦皇岛市,北戴河区', '北戴河', '中国,河北,秦皇岛,北戴河', '3', '0335', '066100', 'Beidaihe', 'BDH', 'B', '119.48388', '39.83408'),
('85', '130306', '130300', '抚宁区', '中国,河北省,秦皇岛市,抚宁区', '抚宁', '中国,河北,秦皇岛,抚宁', '3', '0335', '066300', 'Funing', 'FN', 'F', '119.24487', '39.87538'),
('86', '130321', '130300', '青龙满族自治县', '中国,河北省,秦皇岛市,青龙满族自治县', '青龙', '中国,河北,秦皇岛,青龙', '3', '0335', '066500', 'Qinglong', 'QL', 'Q', '118.95242', '40.40743'),
('87', '130322', '130300', '昌黎县', '中国,河北省,秦皇岛市,昌黎县', '昌黎', '中国,河北,秦皇岛,昌黎', '3', '0335', '066600', 'Changli', 'CL', 'C', '119.16595', '39.70884'),
('88', '130324', '130300', '卢龙县', '中国,河北省,秦皇岛市,卢龙县', '卢龙', '中国,河北,秦皇岛,卢龙', '3', '0335', '066400', 'Lulong', 'LL', 'L', '118.89288', '39.89176'),
('89', '130325', '130300', '北戴河新区', '中国,河北省,秦皇岛市,北戴河新区', '北戴河新区', '中国,河北,秦皇岛,北戴河新区', '3', '0335', '066311', 'Beidaihexinqu', 'BDH', 'B', '119.386541', '39.76748'),
('90', '130400', '130000', '邯郸市', '中国,河北省,邯郸市', '邯郸', '中国,河北,邯郸', '2', '0310', '056000', 'Handan', 'HD', 'H', '114.490686', '36.612273'),
('91', '130402', '130400', '邯山区', '中国,河北省,邯郸市,邯山区', '邯山', '中国,河北,邯郸,邯山', '3', '0310', '056000', 'Hanshan', 'HS', 'H', '114.48375', '36.60006'),
('92', '130403', '130400', '丛台区', '中国,河北省,邯郸市,丛台区', '丛台', '中国,河北,邯郸,丛台', '3', '0310', '056000', 'Congtai', 'CT', 'C', '114.49343', '36.61847'),
('93', '130404', '130400', '复兴区', '中国,河北省,邯郸市,复兴区', '复兴', '中国,河北,邯郸,复兴', '3', '0310', '056000', 'Fuxing', 'FX', 'F', '114.45928', '36.61134'),
('94', '130406', '130400', '峰峰矿区', '中国,河北省,邯郸市,峰峰矿区', '峰峰矿区', '中国,河北,邯郸,峰峰矿区', '3', '0310', '056200', 'Fengfengkuangqu', 'FFKQ', 'F', '114.21148', '36.41937'),
('95', '130407', '130400', '肥乡区', '中国,河北省,邯郸市,肥乡区', '肥乡', '中国,河北,邯郸,肥乡', '3', '0310', '057550', 'Feixiang', 'FX', 'F', '114.79998', '36.54807'),
('96', '130408', '130400', '永年区', '中国,河北省,邯郸市,永年区', '永年', '中国,河北,邯郸,永年', '3', '0310', '057150', 'Yongnian', 'YN', 'Y', '114.48925', '36.78356'),
('97', '130423', '130400', '临漳县', '中国,河北省,邯郸市,临漳县', '临漳', '中国,河北,邯郸,临漳', '3', '0310', '056600', 'Linzhang', 'LZ', 'L', '114.6195', '36.33461'),
('98', '130424', '130400', '成安县', '中国,河北省,邯郸市,成安县', '成安', '中国,河北,邯郸,成安', '3', '0310', '056700', 'Cheng\'an', 'CA', 'C', '114.66995', '36.44411'),
('99', '130425', '130400', '大名县', '中国,河北省,邯郸市,大名县', '大名', '中国,河北,邯郸,大名', '3', '0310', '056900', 'Daming', 'DM', 'D', '115.15362', '36.27994'),
('100', '130426', '130400', '涉县', '中国,河北省,邯郸市,涉县', '涉县', '中国,河北,邯郸,涉县', '3', '0310', '056400', 'Shexian', 'SX', 'S', '113.69183', '36.58072'),
('101', '130427', '130400', '磁县', '中国,河北省,邯郸市,磁县', '磁县', '中国,河北,邯郸,磁县', '3', '0310', '056500', 'Cixian', 'CX', 'C', '114.37387', '36.37392'),
('102', '130430', '130400', '邱县', '中国,河北省,邯郸市,邱县', '邱县', '中国,河北,邯郸,邱县', '3', '0310', '057450', 'Qiuxian', 'QX', 'Q', '115.17407', '36.82082'),
('103', '130431', '130400', '鸡泽县', '中国,河北省,邯郸市,鸡泽县', '鸡泽', '中国,河北,邯郸,鸡泽', '3', '0310', '057350', 'Jize', 'JZ', 'J', '114.8742', '36.92374'),
('104', '130432', '130400', '广平县', '中国,河北省,邯郸市,广平县', '广平', '中国,河北,邯郸,广平', '3', '0310', '057650', 'Guangping', 'GP', 'G', '114.94653', '36.48046'),
('105', '130433', '130400', '馆陶县', '中国,河北省,邯郸市,馆陶县', '馆陶', '中国,河北,邯郸,馆陶', '3', '0310', '057750', 'Guantao', 'GT', 'G', '115.29913', '36.53719'),
('106', '130434', '130400', '魏县', '中国,河北省,邯郸市,魏县', '魏县', '中国,河北,邯郸,魏县', '3', '0310', '056800', 'Weixian', 'WX', 'W', '114.93518', '36.36171'),
('107', '130435', '130400', '曲周县', '中国,河北省,邯郸市,曲周县', '曲周', '中国,河北,邯郸,曲周', '3', '0310', '057250', 'Quzhou', 'QZ', 'Q', '114.95196', '36.77671'),
('108', '130481', '130400', '武安市', '中国,河北省,邯郸市,武安市', '武安', '中国,河北,邯郸,武安', '3', '0310', '056300', 'Wu\'an', 'WA', 'W', '114.20153', '36.69281'),
('109', '130482', '130400', '高新技术产业开发区', '中国,河北省,邯郸市,高新技术产业开发区', '高新技术产业开发区', '中国,河北,邯郸,高新技术产业开发区', '3', '0310', '056000', 'GaoXinKaiFaQu', 'GXKFQ', 'G', '114.566033', '36.675235'),
('110', '130500', '130000', '邢台市', '中国,河北省,邢台市', '邢台', '中国,河北,邢台', '2', '0319', '054000', 'Xingtai', 'XT', 'X', '114.508851', '37.0682'),
('111', '130502', '130500', '桥东区', '中国,河北省,邢台市,桥东区', '桥东', '中国,河北,邢台,桥东', '3', '0319', '054000', 'Qiaodong', 'QD', 'Q', '114.50725', '37.06801'),
('112', '130503', '130500', '桥西区', '中国,河北省,邢台市,桥西区', '桥西', '中国,河北,邢台,桥西', '3', '0319', '054000', 'Qiaoxi', 'QX', 'Q', '114.46803', '37.05984'),
('113', '130521', '130500', '邢台县', '中国,河北省,邢台市,邢台县', '邢台', '中国,河北,邢台,邢台', '3', '0319', '054000', 'Xingtai', 'XT', 'X', '114.56575', '37.0456'),
('114', '130522', '130500', '临城县', '中国,河北省,邢台市,临城县', '临城', '中国,河北,邢台,临城', '3', '0319', '054300', 'Lincheng', 'LC', 'L', '114.50387', '37.43977'),
('115', '130523', '130500', '内丘县', '中国,河北省,邢台市,内丘县', '内丘', '中国,河北,邢台,内丘', '3', '0319', '054200', 'Neiqiu', 'NQ', 'N', '114.51212', '37.28671'),
('116', '130524', '130500', '柏乡县', '中国,河北省,邢台市,柏乡县', '柏乡', '中国,河北,邢台,柏乡', '3', '0319', '055450', 'Baixiang', 'BX', 'B', '114.69332', '37.48242'),
('117', '130525', '130500', '隆尧县', '中国,河北省,邢台市,隆尧县', '隆尧', '中国,河北,邢台,隆尧', '3', '0319', '055350', 'Longyao', 'LY', 'L', '114.77615', '37.35351'),
('118', '130526', '130500', '任县', '中国,河北省,邢台市,任县', '任县', '中国,河北,邢台,任县', '3', '0319', '055150', 'Renxian', 'RX', 'R', '114.6842', '37.12575'),
('119', '130527', '130500', '南和县', '中国,河北省,邢台市,南和县', '南和', '中国,河北,邢台,南和', '3', '0319', '054400', 'Nanhe', 'NH', 'N', '114.68371', '37.00488'),
('120', '130528', '130500', '宁晋县', '中国,河北省,邢台市,宁晋县', '宁晋', '中国,河北,邢台,宁晋', '3', '0319', '055550', 'Ningjin', 'NJ', 'N', '114.92117', '37.61696'),
('121', '130529', '130500', '巨鹿县', '中国,河北省,邢台市,巨鹿县', '巨鹿', '中国,河北,邢台,巨鹿', '3', '0319', '055250', 'Julu', 'JL', 'J', '115.03524', '37.21801'),
('122', '130530', '130500', '新河县', '中国,河北省,邢台市,新河县', '新河', '中国,河北,邢台,新河', '3', '0319', '051730', 'Xinhe', 'XH', 'X', '115.24987', '37.52718'),
('123', '130531', '130500', '广宗县', '中国,河北省,邢台市,广宗县', '广宗', '中国,河北,邢台,广宗', '3', '0319', '054600', 'Guangzong', 'GZ', 'G', '115.14254', '37.0746'),
('124', '130532', '130500', '平乡县', '中国,河北省,邢台市,平乡县', '平乡', '中国,河北,邢台,平乡', '3', '0319', '054500', 'Pingxiang', 'PX', 'P', '115.03002', '37.06317'),
('125', '130533', '130500', '威县', '中国,河北省,邢台市,威县', '威县', '中国,河北,邢台,威县', '3', '0319', '054700', 'Weixian', 'WX', 'W', '115.2637', '36.9768'),
('126', '130534', '130500', '清河县', '中国,河北省,邢台市,清河县', '清河', '中国,河北,邢台,清河', '3', '0319', '054800', 'Qinghe', 'QH', 'Q', '115.66479', '37.07122'),
('127', '130535', '130500', '临西县', '中国,河北省,邢台市,临西县', '临西', '中国,河北,邢台,临西', '3', '0319', '054900', 'Linxi', 'LX', 'L', '115.50097', '36.87078'),
('128', '130581', '130500', '南宫市', '中国,河北省,邢台市,南宫市', '南宫', '中国,河北,邢台,南宫', '3', '0319', '051800', 'Nangong', 'NG', 'N', '115.39068', '37.35799'),
('129', '130582', '130500', '沙河市', '中国,河北省,邢台市,沙河市', '沙河', '中国,河北,邢台,沙河', '3', '0319', '054100', 'Shahe', 'SH', 'S', '114.4981', '36.8577'),
('130', '130600', '130000', '保定市', '中国,河北省,保定市', '保定', '中国,河北,保定', '2', '0312', '071000', 'Baoding', 'BD', 'B', '115.482331', '38.867657'),
('131', '130602', '130600', '竞秀区', '中国,河北省,保定市,竞秀区', '竞秀', '中国,河北,保定,竞秀', '3', '0312', '071052', 'Jingxiu', 'JX', 'J', '115.4587', '38.87751'),
('132', '130606', '130600', '莲池区', '中国,河北省,保定市,莲池区', '莲池', '中国,河北,保定,莲池', '3', '0312', '071000', 'Lianchi', 'LC', 'L', '115.49715', '38.88322'),
('133', '130607', '130600', '满城区', '中国,河北省,保定市,满城区', '满城', '中国,河北,保定,满城', '3', '0312', '072150', 'Mancheng', 'MC', 'M', '115.32296', '38.94972'),
('134', '130608', '130600', '清苑区', '中国,河北省,保定市,清苑区', '清苑', '中国,河北,保定,清苑', '3', '0312', '071100', 'Qingyuan', 'QY', 'Q', '115.49267', '38.76709'),
('135', '130609', '130600', '徐水区', '中国,河北省,保定市,徐水区', '徐水', '中国,河北,保定,徐水', '3', '0312', '072550', 'Xushui', 'XS', 'X', '115.65829', '39.02099'),
('136', '130623', '130600', '涞水县', '中国,河北省,保定市,涞水县', '涞水', '中国,河北,保定,涞水', '3', '0312', '074100', 'Laishui', 'LS', 'L', '115.71517', '39.39404'),
('137', '130624', '130600', '阜平县', '中国,河北省,保定市,阜平县', '阜平', '中国,河北,保定,阜平', '3', '0312', '073200', 'Fuping', 'FP', 'F', '114.19683', '38.84763'),
('138', '130626', '130600', '定兴县', '中国,河北省,保定市,定兴县', '定兴', '中国,河北,保定,定兴', '3', '0312', '072650', 'Dingxing', 'DX', 'D', '115.80786', '39.26312'),
('139', '130627', '130600', '唐县', '中国,河北省,保定市,唐县', '唐县', '中国,河北,保定,唐县', '3', '0312', '072350', 'Tangxian', 'TX', 'T', '114.98516', '38.74513'),
('140', '130628', '130600', '高阳县', '中国,河北省,保定市,高阳县', '高阳', '中国,河北,保定,高阳', '3', '0312', '071500', 'Gaoyang', 'GY', 'G', '115.7788', '38.70003'),
('141', '130629', '130600', '容城县', '中国,河北省,保定市,容城县', '容城', '中国,河北,保定,容城', '3', '0312', '071700', 'Rongcheng', 'RC', 'R', '115.87158', '39.0535'),
('142', '130630', '130600', '涞源县', '中国,河北省,保定市,涞源县', '涞源', '中国,河北,保定,涞源', '3', '0312', '074300', 'Laiyuan', 'LY', 'L', '114.69128', '39.35388'),
('143', '130631', '130600', '望都县', '中国,河北省,保定市,望都县', '望都', '中国,河北,保定,望都', '3', '0312', '072450', 'Wangdu', 'WD', 'W', '115.1567', '38.70996'),
('144', '130632', '130600', '安新县', '中国,河北省,保定市,安新县', '安新', '中国,河北,保定,安新', '3', '0312', '071600', 'Anxin', 'AX', 'A', '115.93557', '38.93532'),
('145', '130633', '130600', '易县', '中国,河北省,保定市,易县', '易县', '中国,河北,保定,易县', '3', '0312', '074200', 'Yixian', 'YX', 'Y', '115.4981', '39.34885'),
('146', '130634', '130600', '曲阳县', '中国,河北省,保定市,曲阳县', '曲阳', '中国,河北,保定,曲阳', '3', '0312', '073100', 'Quyang', 'QY', 'Q', '114.70123', '38.62154'),
('147', '130635', '130600', '蠡县', '中国,河北省,保定市,蠡县', '蠡县', '中国,河北,保定,蠡县', '3', '0312', '071400', 'Lixian', 'LX', 'L', '115.57717', '38.48974'),
('148', '130636', '130600', '顺平县', '中国,河北省,保定市,顺平县', '顺平', '中国,河北,保定,顺平', '3', '0312', '072250', 'Shunping', 'SP', 'S', '115.1347', '38.83854'),
('149', '130637', '130600', '博野县', '中国,河北省,保定市,博野县', '博野', '中国,河北,保定,博野', '3', '0312', '071300', 'Boye', 'BY', 'B', '115.47033', '38.4564'),
('150', '130638', '130600', '雄县', '中国,河北省,保定市,雄县', '雄县', '中国,河北,保定,雄县', '3', '0312', '071800', 'Xiongxian', 'XX', 'X', '116.10873', '38.99442'),
('151', '130681', '130600', '涿州市', '中国,河北省,保定市,涿州市', '涿州', '中国,河北,保定,涿州', '3', '0312', '072750', 'Zhuozhou', 'ZZ', 'Z', '115.98062', '39.48622'),
('152', '130682', '130600', '定州市', '中国,河北省,保定市,定州市', '定州', '中国,河北,保定,定州', '3', '0312', '073000', 'Dingzhou', 'DZ', 'D', '114.9902', '38.51623'),
('153', '130683', '130600', '安国市', '中国,河北省,保定市,安国市', '安国', '中国,河北,保定,安国', '3', '0312', '071200', 'Anguo', 'AG', 'A', '115.32321', '38.41391'),
('154', '130684', '130600', '高碑店市', '中国,河北省,保定市,高碑店市', '高碑店', '中国,河北,保定,高碑店', '3', '0312', '074000', 'Gaobeidian', 'GBD', 'G', '115.87368', '39.32655'),
('155', '130685', '130600', '雄安新区', '中国,河北省,保定市,雄安新区', '雄安新区', '中国,河北,保定,雄安新区', '3', '0312', '071000', 'XionganXinQu', 'XAXQ', 'X', '115.976478', '38.995544'),
('156', '130686', '130600', '高新区', '中国,河北省,保定市,高新区', '高新区', '中国,河北,保定,高新区', '3', '0312', '071000', 'Gaoxinqu', 'GXQ', 'G', '115.46533', '38.904525'),
('157', '130700', '130000', '张家口市', '中国,河北省,张家口市', '张家口', '中国,河北,张家口', '2', '0313', '075000', 'Zhangjiakou', 'ZJK', 'Z', '114.884091', '40.811901'),
('158', '130702', '130700', '桥东区', '中国,河北省,张家口市,桥东区', '桥东', '中国,河北,张家口,桥东', '3', '0313', '075000', 'Qiaodong', 'QD', 'Q', '114.8943', '40.78844'),
('159', '130703', '130700', '桥西区', '中国,河北省,张家口市,桥西区', '桥西', '中国,河北,张家口,桥西', '3', '0313', '075000', 'Qiaoxi', 'QX', 'Q', '114.86962', '40.81945'),
('160', '130705', '130700', '宣化区', '中国,河北省,张家口市,宣化区', '宣化', '中国,河北,张家口,宣化', '3', '0313', '075000', 'Xuanhua', 'XH', 'X', '115.06543', '40.60957'),
('161', '130706', '130700', '下花园区', '中国,河北省,张家口市,下花园区', '下花园', '中国,河北,张家口,下花园', '3', '0313', '075300', 'Xiahuayuan', 'XHY', 'X', '115.28744', '40.50236'),
('162', '130708', '130700', '万全区', '中国,河北省,张家口市,万全区', '万全', '中国,河北,张家口,万全', '3', '0313', '076250', 'Wanquan', 'WQ', 'W', '114.7405', '40.76694'),
('163', '130709', '130700', '崇礼区', '中国,河北省,张家口市,崇礼区', '崇礼', '中国,河北,张家口,崇礼', '3', '0313', '076350', 'Chongli', 'CL', 'C', '115.27993', '40.97519'),
('164', '130722', '130700', '张北县', '中国,河北省,张家口市,张北县', '张北', '中国,河北,张家口,张北', '3', '0313', '076450', 'Zhangbei', 'ZB', 'Z', '114.71432', '41.15977'),
('165', '130723', '130700', '康保县', '中国,河北省,张家口市,康保县', '康保', '中国,河北,张家口,康保', '3', '0313', '076650', 'Kangbao', 'KB', 'K', '114.60031', '41.85225'),
('166', '130724', '130700', '沽源县', '中国,河北省,张家口市,沽源县', '沽源', '中国,河北,张家口,沽源', '3', '0313', '076550', 'Guyuan', 'GY', 'G', '115.68859', '41.66959'),
('167', '130725', '130700', '尚义县', '中国,河北省,张家口市,尚义县', '尚义', '中国,河北,张家口,尚义', '3', '0313', '076750', 'Shangyi', 'SY', 'S', '113.97134', '41.07782'),
('168', '130726', '130700', '蔚县', '中国,河北省,张家口市,蔚县', '蔚县', '中国,河北,张家口,蔚县', '3', '0313', '075700', 'Yuxian', 'YX', 'Y', '114.58892', '39.84067'),
('169', '130727', '130700', '阳原县', '中国,河北省,张家口市,阳原县', '阳原', '中国,河北,张家口,阳原', '3', '0313', '075800', 'Yangyuan', 'YY', 'Y', '114.15051', '40.10361'),
('170', '130728', '130700', '怀安县', '中国,河北省,张家口市,怀安县', '怀安', '中国,河北,张家口,怀安', '3', '0313', '076150', 'Huai\'an', 'HA', 'H', '114.38559', '40.67425'),
('171', '130730', '130700', '怀来县', '中国,河北省,张家口市,怀来县', '怀来', '中国,河北,张家口,怀来', '3', '0313', '075400', 'Huailai', 'HL', 'H', '115.51773', '40.41536'),
('172', '130731', '130700', '涿鹿县', '中国,河北省,张家口市,涿鹿县', '涿鹿', '中国,河北,张家口,涿鹿', '3', '0313', '075600', 'Zhuolu', 'ZL', 'Z', '115.22403', '40.37636'),
('173', '130732', '130700', '赤城县', '中国,河北省,张家口市,赤城县', '赤城', '中国,河北,张家口,赤城', '3', '0313', '075500', 'Chicheng', 'CC', 'C', '115.83187', '40.91438'),
('174', '130800', '130000', '承德市', '中国,河北省,承德市', '承德', '中国,河北,承德', '2', '0314', '067000', 'Chengde', 'CD', 'C', '117.939152', '40.976204'),
('175', '130802', '130800', '双桥区', '中国,河北省,承德市,双桥区', '双桥', '中国,河北,承德,双桥', '3', '0314', '067000', 'Shuangqiao', 'SQ', 'S', '117.9432', '40.97466'),
('176', '130803', '130800', '双滦区', '中国,河北省,承德市,双滦区', '双滦', '中国,河北,承德,双滦', '3', '0314', '067000', 'Shuangluan', 'SL', 'S', '117.74487', '40.95375'),
('177', '130804', '130800', '鹰手营子矿区', '中国,河北省,承德市,鹰手营子矿区', '鹰手营子矿区', '中国,河北,承德,鹰手营子矿区', '3', '0314', '067200', 'Yingshouyingzikuangqu', 'YSYZKQ', 'Y', '117.65985', '40.54744'),
('178', '130821', '130800', '承德县', '中国,河北省,承德市,承德县', '承德', '中国,河北,承德,承德', '3', '0314', '067400', 'Chengde', 'CD', 'C', '118.17639', '40.76985'),
('179', '130822', '130800', '兴隆县', '中国,河北省,承德市,兴隆县', '兴隆', '中国,河北,承德,兴隆', '3', '0314', '067300', 'Xinglong', 'XL', 'X', '117.50073', '40.41709'),
('180', '130823', '130800', '平泉市', '中国,河北省,承德市,平泉市', '平泉', '中国,河北,承德,平泉', '3', '0314', '067500', 'Pingquan', 'PQ', 'P', '118.70196', '41.01839'),
('181', '130824', '130800', '滦平县', '中国,河北省,承德市,滦平县', '滦平', '中国,河北,承德,滦平', '3', '0314', '068250', 'Luanping', 'LP', 'L', '117.33276', '40.94148'),
('182', '130825', '130800', '隆化县', '中国,河北省,承德市,隆化县', '隆化', '中国,河北,承德,隆化', '3', '0314', '068150', 'Longhua', 'LH', 'L', '117.7297', '41.31412'),
('183', '130826', '130800', '丰宁满族自治县', '中国,河北省,承德市,丰宁满族自治县', '丰宁', '中国,河北,承德,丰宁', '3', '0314', '068350', 'Fengning', 'FN', 'F', '116.6492', '41.20481'),
('184', '130827', '130800', '宽城满族自治县', '中国,河北省,承德市,宽城满族自治县', '宽城', '中国,河北,承德,宽城', '3', '0314', '067600', 'Kuancheng', 'KC', 'K', '118.49176', '40.60829'),
('185', '130828', '130800', '围场满族蒙古族自治县', '中国,河北省,承德市,围场满族蒙古族自治县', '围场', '中国,河北,承德,围场', '3', '0314', '068450', 'Weichang', 'WC', 'W', '117.7601', '41.94368'),
('186', '130900', '130000', '沧州市', '中国,河北省,沧州市', '沧州', '中国,河北,沧州', '2', '0317', '061000', 'Cangzhou', 'CZ', 'C', '116.857461', '38.310582'),
('187', '130902', '130900', '新华区', '中国,河北省,沧州市,新华区', '新华', '中国,河北,沧州,新华', '3', '0317', '061000', 'Xinhua', 'XH', 'X', '116.86643', '38.31438'),
('188', '130903', '130900', '运河区', '中国,河北省,沧州市,运河区', '运河', '中国,河北,沧州,运河', '3', '0317', '061000', 'Yunhe', 'YH', 'Y', '116.85706', '38.31352'),
('189', '130921', '130900', '沧县', '中国,河北省,沧州市,沧县', '沧县', '中国,河北,沧州,沧县', '3', '0317', '061000', 'Cangxian', 'CX', 'C', '116.87817', '38.29361'),
('190', '130922', '130900', '青县', '中国,河北省,沧州市,青县', '青县', '中国,河北,沧州,青县', '3', '0317', '062650', 'Qingxian', 'QX', 'Q', '116.80316', '38.58345'),
('191', '130923', '130900', '东光县', '中国,河北省,沧州市,东光县', '东光', '中国,河北,沧州,东光', '3', '0317', '061600', 'Dongguang', 'DG', 'D', '116.53668', '37.8857'),
('192', '130924', '130900', '海兴县', '中国,河北省,沧州市,海兴县', '海兴', '中国,河北,沧州,海兴', '3', '0317', '061200', 'Haixing', 'HX', 'H', '117.49758', '38.13958'),
('193', '130925', '130900', '盐山县', '中国,河北省,沧州市,盐山县', '盐山', '中国,河北,沧州,盐山', '3', '0317', '061300', 'Yanshan', 'YS', 'Y', '117.23092', '38.05647'),
('194', '130926', '130900', '肃宁县', '中国,河北省,沧州市,肃宁县', '肃宁', '中国,河北,沧州,肃宁', '3', '0317', '062350', 'Suning', 'SN', 'S', '115.82971', '38.42272'),
('195', '130927', '130900', '南皮县', '中国,河北省,沧州市,南皮县', '南皮', '中国,河北,沧州,南皮', '3', '0317', '061500', 'Nanpi', 'NP', 'N', '116.70224', '38.04109'),
('196', '130928', '130900', '吴桥县', '中国,河北省,沧州市,吴桥县', '吴桥', '中国,河北,沧州,吴桥', '3', '0317', '061800', 'Wuqiao', 'WQ', 'W', '116.3847', '37.62546'),
('197', '130929', '130900', '献县', '中国,河北省,沧州市,献县', '献县', '中国,河北,沧州,献县', '3', '0317', '062250', 'Xianxian', 'XX', 'X', '116.12695', '38.19228'),
('198', '130930', '130900', '孟村回族自治县', '中国,河北省,沧州市,孟村回族自治县', '孟村', '中国,河北,沧州,孟村', '3', '0317', '061400', 'Mengcun', 'MC', 'M', '117.10412', '38.05338'),
('199', '130981', '130900', '泊头市', '中国,河北省,沧州市,泊头市', '泊头', '中国,河北,沧州,泊头', '3', '0317', '062150', 'Botou', 'BT', 'B', '116.57824', '38.08359'),
('200', '130982', '130900', '任丘市', '中国,河北省,沧州市,任丘市', '任丘', '中国,河北,沧州,任丘', '3', '0317', '062550', 'Renqiu', 'RQ', 'R', '116.1033', '38.71124'),
('201', '130983', '130900', '黄骅市', '中国,河北省,沧州市,黄骅市', '黄骅', '中国,河北,沧州,黄骅', '3', '0317', '061100', 'Huanghua', 'HH', 'H', '117.33883', '38.3706'),
('202', '130984', '130900', '河间市', '中国,河北省,沧州市,河间市', '河间', '中国,河北,沧州,河间', '3', '0317', '062450', 'Hejian', 'HJ', 'H', '116.0993', '38.44549'),
('203', '130985', '130900', '渤海新区', '中国,河北省,沧州市,渤海新区', '渤海新区', '中国,河北,沧州,渤海新区', '3', '0317', '061100', 'Bohaixinqu', 'BHXQ', 'B', '117.772562', '38.269508'),
('204', '130986', '130900', '临港经济技术开发区', '中国,河北省,沧州市,临港经济技术开发区', '临港经济技术开发区', '中国,河北,沧州,临港经济技术开发区', '3', '0317', '061000', 'Jingjikaifaqu', 'JJKFQ', 'J', '117.520393', '38.356305'),
('205', '131000', '130000', '廊坊市', '中国,河北省,廊坊市', '廊坊', '中国,河北,廊坊', '2', '0316', '065000', 'Langfang', 'LF', 'L', '116.713873', '39.529244'),
('206', '131002', '131000', '安次区', '中国,河北省,廊坊市,安次区', '安次', '中国,河北,廊坊,安次', '3', '0316', '065000', 'Anci', 'AC', 'A', '116.70308', '39.52057'),
('207', '131003', '131000', '广阳区', '中国,河北省,廊坊市,广阳区', '广阳', '中国,河北,廊坊,广阳', '3', '0316', '065000', 'Guangyang', 'GY', 'G', '116.71069', '39.52278'),
('208', '131022', '131000', '固安县', '中国,河北省,廊坊市,固安县', '固安', '中国,河北,廊坊,固安', '3', '0316', '065500', 'Gu\'an', 'GA', 'G', '116.29916', '39.43833'),
('209', '131023', '131000', '永清县', '中国,河北省,廊坊市,永清县', '永清', '中国,河北,廊坊,永清', '3', '0316', '065600', 'Yongqing', 'YQ', 'Y', '116.50091', '39.32069'),
('210', '131024', '131000', '香河县', '中国,河北省,廊坊市,香河县', '香河', '中国,河北,廊坊,香河', '3', '0316', '065400', 'Xianghe', 'XH', 'X', '117.00634', '39.76133'),
('211', '131025', '131000', '大城县', '中国,河北省,廊坊市,大城县', '大城', '中国,河北,廊坊,大城', '3', '0316', '065900', 'Daicheng', 'DC', 'D', '116.65353', '38.70534'),
('212', '131026', '131000', '文安县', '中国,河北省,廊坊市,文安县', '文安', '中国,河北,廊坊,文安', '3', '0316', '065800', 'Wen\'an', 'WA', 'W', '116.45846', '38.87325'),
('213', '131028', '131000', '大厂回族自治县', '中国,河北省,廊坊市,大厂回族自治县', '大厂', '中国,河北,廊坊,大厂', '3', '0316', '065300', 'Dachang', 'DC', 'D', '116.98916', '39.88649'),
('214', '131081', '131000', '霸州市', '中国,河北省,廊坊市,霸州市', '霸州', '中国,河北,廊坊,霸州', '3', '0316', '065700', 'Bazhou', 'BZ', 'B', '116.39154', '39.12569'),
('215', '131082', '131000', '三河市', '中国,河北省,廊坊市,三河市', '三河', '中国,河北,廊坊,三河', '3', '0316', '065200', 'Sanhe', 'SH', 'S', '117.07229', '39.98358'),
('216', '131083', '131000', '经济技术开发区', '中国,河北省,廊坊市,经济技术开发区', '经济技术开发区', '中国,河北,廊坊,经济技术开发区', '3', '0316', '065001', 'Jingjikaifaqu', 'JJKFQ', 'J', '116.758484', '39.56678'),
('217', '131100', '130000', '衡水市', '中国,河北省,衡水市', '衡水', '中国,河北,衡水', '2', '0318', '053000', 'Hengshui', 'HS', 'H', '115.665993', '37.735097'),
('218', '131102', '131100', '桃城区', '中国,河北省,衡水市,桃城区', '桃城', '中国,河北,衡水,桃城', '3', '0318', '053000', 'Taocheng', 'TC', 'T', '115.67529', '37.73499'),
('219', '131103', '131100', '冀州区', '中国,河北省,衡水市,冀州区', '冀州', '中国,河北,衡水,冀州', '3', '0318', '053200', 'Jizhou', 'JZ', 'J', '115.57934', '37.55082'),
('220', '131121', '131100', '枣强县', '中国,河北省,衡水市,枣强县', '枣强', '中国,河北,衡水,枣强', '3', '0318', '053100', 'Zaoqiang', 'ZQ', 'Z', '115.72576', '37.51027'),
('221', '131122', '131100', '武邑县', '中国,河北省,衡水市,武邑县', '武邑', '中国,河北,衡水,武邑', '3', '0318', '053400', 'Wuyi', 'WY', 'W', '115.88748', '37.80181'),
('222', '131123', '131100', '武强县', '中国,河北省,衡水市,武强县', '武强', '中国,河北,衡水,武强', '3', '0318', '053300', 'Wuqiang', 'WQ', 'W', '115.98226', '38.04138'),
('223', '131124', '131100', '饶阳县', '中国,河北省,衡水市,饶阳县', '饶阳', '中国,河北,衡水,饶阳', '3', '0318', '053900', 'Raoyang', 'RY', 'R', '115.72558', '38.23529'),
('224', '131125', '131100', '安平县', '中国,河北省,衡水市,安平县', '安平', '中国,河北,衡水,安平', '3', '0318', '053600', 'Anping', 'AP', 'A', '115.51876', '38.23388'),
('225', '131126', '131100', '故城县', '中国,河北省,衡水市,故城县', '故城', '中国,河北,衡水,故城', '3', '0318', '253800', 'Gucheng', 'GC', 'G', '115.97076', '37.34773'),
('226', '131127', '131100', '景县', '中国,河北省,衡水市,景县', '景县', '中国,河北,衡水,景县', '3', '0318', '053500', 'Jingxian', 'JX', 'J', '116.26904', '37.6926'),
('227', '131128', '131100', '阜城县', '中国,河北省,衡水市,阜城县', '阜城', '中国,河北,衡水,阜城', '3', '0318', '053700', 'Fucheng', 'FC', 'F', '116.14431', '37.86881'),
('228', '131182', '131100', '深州市', '中国,河北省,衡水市,深州市', '深州', '中国,河北,衡水,深州', '3', '0318', '053800', 'Shenzhou', 'SZ', 'S', '115.55993', '38.00109'),
('229', '140000', '100000', '山西省', '中国,山西省', '山西', '中国,山西', '1', '', '', 'Shanxi', 'SX', 'S', '112.549248', '37.857014'),
('230', '140100', '140000', '太原市', '中国,山西省,太原市', '太原', '中国,山西,太原', '2', '0351', '030000', 'Taiyuan', 'TY', 'T', '112.549248', '37.857014'),
('231', '140105', '140100', '小店区', '中国,山西省,太原市,小店区', '小店', '中国,山西,太原,小店', '3', '0351', '030000', 'Xiaodian', 'XD', 'X', '112.56878', '37.73565'),
('232', '140106', '140100', '迎泽区', '中国,山西省,太原市,迎泽区', '迎泽', '中国,山西,太原,迎泽', '3', '0351', '030000', 'Yingze', 'YZ', 'Y', '112.56338', '37.86326'),
('233', '140107', '140100', '杏花岭区', '中国,山西省,太原市,杏花岭区', '杏花岭', '中国,山西,太原,杏花岭', '3', '0351', '030000', 'Xinghualing', 'XHL', 'X', '112.56237', '37.88429'),
('234', '140108', '140100', '尖草坪区', '中国,山西省,太原市,尖草坪区', '尖草坪', '中国,山西,太原,尖草坪', '3', '0351', '030000', 'Jiancaoping', 'JCP', 'J', '112.48709', '37.94193'),
('235', '140109', '140100', '万柏林区', '中国,山西省,太原市,万柏林区', '万柏林', '中国,山西,太原,万柏林', '3', '0351', '030000', 'Wanbailin', 'WBL', 'W', '112.51553', '37.85923'),
('236', '140110', '140100', '晋源区', '中国,山西省,太原市,晋源区', '晋源', '中国,山西,太原,晋源', '3', '0351', '030000', 'Jinyuan', 'JY', 'J', '112.47985', '37.72479'),
('237', '140121', '140100', '清徐县', '中国,山西省,太原市,清徐县', '清徐', '中国,山西,太原,清徐', '3', '0351', '030400', 'Qingxu', 'QX', 'Q', '112.35888', '37.60758'),
('238', '140122', '140100', '阳曲县', '中国,山西省,太原市,阳曲县', '阳曲', '中国,山西,太原,阳曲', '3', '0351', '030100', 'Yangqu', 'YQ', 'Y', '112.67861', '38.05989'),
('239', '140123', '140100', '娄烦县', '中国,山西省,太原市,娄烦县', '娄烦', '中国,山西,太原,娄烦', '3', '0351', '030300', 'Loufan', 'LF', 'L', '111.79473', '38.06689'),
('240', '140181', '140100', '古交市', '中国,山西省,太原市,古交市', '古交', '中国,山西,太原,古交', '3', '0351', '030200', 'Gujiao', 'GJ', 'G', '112.16918', '37.90983'),
('241', '140182', '140100', '高新阳曲园区', '中国,山西省,太原市,高新阳曲园区', '阳曲园区', '中国,山西,太原,阳曲园区', '3', '0351', '030100', 'Yangquyuanqu', 'YQYQ', 'Y', '112.560066', '37.800816'),
('242', '140183', '140100', '高新汾东园区', '中国,山西省,太原市,高新汾东园区', '汾东园区', '中国,山西,太原,汾东园区', '3', '0351', '030032', 'Fendongyuanqu', 'FDYQ', 'F', '112.580394', '37.704707'),
('243', '140184', '140100', '高新姚村园区', '中国,山西省,太原市,高新姚村园区', '姚村园区', '中国,山西,太原,姚村园区', '3', '0351', '030054', 'Yaocunyuanqu', 'YCYQ', 'Y', '112.394774', '37.646479'),
('244', '140200', '140000', '大同市', '中国,山西省,大同市', '大同', '中国,山西,大同', '2', '0352', '037000', 'Datong', 'DT', 'D', '113.295259', '40.09031'),
('245', '140202', '140200', '城区', '中国,山西省,大同市,城区', '城区', '中国,山西,大同,城区', '3', '0352', '037008', 'Chengqu', 'CQ', 'C', '113.298', '40.07566'),
('246', '140203', '140200', '矿区', '中国,山西省,大同市,矿区', '矿区', '中国,山西,大同,矿区', '3', '0352', '037003', 'Kuangqu', 'KQ', 'K', '113.1772', '40.03685'),
('247', '140211', '140200', '南郊区', '中国,山西省,大同市,南郊区', '南郊', '中国,山西,大同,南郊', '3', '0352', '037001', 'Nanjiao', 'NJ', 'N', '113.14947', '40.00539'),
('248', '140212', '140200', '新荣区', '中国,山西省,大同市,新荣区', '新荣', '中国,山西,大同,新荣', '3', '0352', '037002', 'Xinrong', 'XR', 'X', '113.13504', '40.25618'),
('249', '140221', '140200', '阳高县', '中国,山西省,大同市,阳高县', '阳高', '中国,山西,大同,阳高', '3', '0352', '038100', 'Yanggao', 'YG', 'Y', '113.75012', '40.36256'),
('250', '140222', '140200', '天镇县', '中国,山西省,大同市,天镇县', '天镇', '中国,山西,大同,天镇', '3', '0352', '038200', 'Tianzhen', 'TZ', 'T', '114.0931', '40.42299'),
('251', '140223', '140200', '广灵县', '中国,山西省,大同市,广灵县', '广灵', '中国,山西,大同,广灵', '3', '0352', '037500', 'Guangling', 'GL', 'G', '114.28204', '39.76082'),
('252', '140224', '140200', '灵丘县', '中国,山西省,大同市,灵丘县', '灵丘', '中国,山西,大同,灵丘', '3', '0352', '034400', 'Lingqiu', 'LQ', 'L', '114.23672', '39.44043'),
('253', '140225', '140200', '浑源县', '中国,山西省,大同市,浑源县', '浑源', '中国,山西,大同,浑源', '3', '0352', '037400', 'Hunyuan', 'HY', 'H', '113.69552', '39.69962'),
('254', '140226', '140200', '左云县', '中国,山西省,大同市,左云县', '左云', '中国,山西,大同,左云', '3', '0352', '037100', 'Zuoyun', 'ZY', 'Z', '112.70266', '40.01336'),
('255', '140227', '140200', '大同县', '中国,山西省,大同市,大同县', '大同', '中国,山西,大同,大同', '3', '0352', '037300', 'Datong', 'DT', 'D', '113.61212', '40.04012'),
('256', '140228', '140200', '经济开发区', '中国,山西省,大同市,经济开发区', '经济开发区', '中国,山西,大同,经济开发区', '3', '0352', '037000', 'Jingjikaifaqu', 'JJKFQ', 'J', '113.395224', '40.070492'),
('257', '140300', '140000', '阳泉市', '中国,山西省,阳泉市', '阳泉', '中国,山西,阳泉', '2', '0353', '045000', 'Yangquan', 'YQ', 'Y', '113.583285', '37.861188'),
('258', '140302', '140300', '城区', '中国,山西省,阳泉市,城区', '城区', '中国,山西,阳泉,城区', '3', '0353', '045000', 'Chengqu', 'CQ', 'C', '113.60069', '37.8474'),
('259', '140303', '140300', '矿区', '中国,山西省,阳泉市,矿区', '矿区', '中国,山西,阳泉,矿区', '3', '0353', '045000', 'Kuangqu', 'KQ', 'K', '113.55677', '37.86895'),
('260', '140311', '140300', '郊区', '中国,山西省,阳泉市,郊区', '郊区', '中国,山西,阳泉,郊区', '3', '0353', '045000', 'Jiaoqu', 'JQ', 'J', '113.58539', '37.94139'),
('261', '140321', '140300', '平定县', '中国,山西省,阳泉市,平定县', '平定', '中国,山西,阳泉,平定', '3', '0353', '045200', 'Pingding', 'PD', 'P', '113.65789', '37.78601'),
('262', '140322', '140300', '盂县', '中国,山西省,阳泉市,盂县', '盂县', '中国,山西,阳泉,盂县', '3', '0353', '045100', 'Yuxian', 'YX', 'Y', '113.41235', '38.08579'),
('263', '140400', '140000', '长治市', '中国,山西省,长治市', '长治', '中国,山西,长治', '2', '0355', '046000', 'Changzhi', 'CZ', 'C', '113.113556', '36.191112'),
('264', '140402', '140400', '城区', '中国,山西省,长治市,城区', '城区', '中国,山西,长治,城区', '3', '0355', '046000', 'Chengqu', 'CQ', 'C', '113.12308', '36.20351'),
('265', '140411', '140400', '郊区', '中国,山西省,长治市,郊区', '郊区', '中国,山西,长治,郊区', '3', '0355', '046000', 'Jiaoqu', 'JQ', 'J', '113.12653', '36.19918'),
('266', '140421', '140400', '长治县', '中国,山西省,长治市,长治县', '长治', '中国,山西,长治,长治', '3', '0355', '047100', 'Changzhi', 'CZ', 'C', '113.04791', '36.04722'),
('267', '140423', '140400', '襄垣县', '中国,山西省,长治市,襄垣县', '襄垣', '中国,山西,长治,襄垣', '3', '0355', '046200', 'Xiangyuan', 'XY', 'X', '113.05157', '36.53527'),
('268', '140424', '140400', '屯留县', '中国,山西省,长治市,屯留县', '屯留', '中国,山西,长治,屯留', '3', '0355', '046100', 'Tunliu', 'TL', 'T', '112.89196', '36.31579'),
('269', '140425', '140400', '平顺县', '中国,山西省,长治市,平顺县', '平顺', '中国,山西,长治,平顺', '3', '0355', '047400', 'Pingshun', 'PS', 'P', '113.43603', '36.20005'),
('270', '140426', '140400', '黎城县', '中国,山西省,长治市,黎城县', '黎城', '中国,山西,长治,黎城', '3', '0355', '047600', 'Licheng', 'LC', 'L', '113.38766', '36.50301'),
('271', '140427', '140400', '壶关县', '中国,山西省,长治市,壶关县', '壶关', '中国,山西,长治,壶关', '3', '0355', '047300', 'Huguan', 'HG', 'H', '113.207', '36.11301'),
('272', '140428', '140400', '长子县', '中国,山西省,长治市,长子县', '长子', '中国,山西,长治,长子', '3', '0355', '046600', 'Zhangzi', 'ZZ', 'Z', '112.87731', '36.12125'),
('273', '140429', '140400', '武乡县', '中国,山西省,长治市,武乡县', '武乡', '中国,山西,长治,武乡', '3', '0355', '046300', 'Wuxiang', 'WX', 'W', '112.86343', '36.83687'),
('274', '140430', '140400', '沁县', '中国,山西省,长治市,沁县', '沁县', '中国,山西,长治,沁县', '3', '0355', '046400', 'Qinxian', 'QX', 'Q', '112.69863', '36.75628'),
('275', '140431', '140400', '沁源县', '中国,山西省,长治市,沁源县', '沁源', '中国,山西,长治,沁源', '3', '0355', '046500', 'Qinyuan', 'QY', 'Q', '112.33758', '36.50008'),
('276', '140481', '140400', '潞城市', '中国,山西省,长治市,潞城市', '潞城', '中国,山西,长治,潞城', '3', '0355', '047500', 'Lucheng', 'LC', 'L', '113.22888', '36.33414'),
('277', '140500', '140000', '晋城市', '中国,山西省,晋城市', '晋城', '中国,山西,晋城', '2', '0356', '048000', 'Jincheng', 'JC', 'J', '112.851274', '35.497553'),
('278', '140502', '140500', '城区', '中国,山西省,晋城市,城区', '城区', '中国,山西,晋城,城区', '3', '0356', '048000', 'Chengqu', 'CQ', 'C', '112.85319', '35.50175'),
('279', '140521', '140500', '沁水县', '中国,山西省,晋城市,沁水县', '沁水', '中国,山西,晋城,沁水', '3', '0356', '048200', 'Qinshui', 'QS', 'Q', '112.1871', '35.69102'),
('280', '140522', '140500', '阳城县', '中国,山西省,晋城市,阳城县', '阳城', '中国,山西,晋城,阳城', '3', '0356', '048100', 'Yangcheng', 'YC', 'Y', '112.41485', '35.48614'),
('281', '140524', '140500', '陵川县', '中国,山西省,晋城市,陵川县', '陵川', '中国,山西,晋城,陵川', '3', '0356', '048300', 'Lingchuan', 'LC', 'L', '113.2806', '35.77532'),
('282', '140525', '140500', '泽州县', '中国,山西省,晋城市,泽州县', '泽州', '中国,山西,晋城,泽州', '3', '0356', '048000', 'Zezhou', 'ZZ', 'Z', '112.83947', '35.50789'),
('283', '140581', '140500', '高平市', '中国,山西省,晋城市,高平市', '高平', '中国,山西,晋城,高平', '3', '0356', '048400', 'Gaoping', 'GP', 'G', '112.92288', '35.79705'),
('284', '140582', '140500', '经济开发区', '中国,山西省,晋城市,经济开发区', '经济开发区', '中国,山西,晋城,经济开发区', '3', '0356', '048000', 'Jingjikaifaqu', 'JJKFQ', 'J', '112.882055', '35.497734'),
('285', '140600', '140000', '朔州市', '中国,山西省,朔州市', '朔州', '中国,山西,朔州', '2', '0349', '036000', 'Shuozhou', 'SZ', 'S', '112.433387', '39.331261'),
('286', '140602', '140600', '朔城区', '中国,山西省,朔州市,朔城区', '朔城', '中国,山西,朔州,朔城', '3', '0349', '036002', 'Shuocheng', 'SC', 'S', '112.43189', '39.31982'),
('287', '140603', '140600', '平鲁区', '中国,山西省,朔州市,平鲁区', '平鲁', '中国,山西,朔州,平鲁', '3', '0349', '036800', 'Pinglu', 'PL', 'P', '112.28833', '39.51155'),
('288', '140621', '140600', '山阴县', '中国,山西省,朔州市,山阴县', '山阴', '中国,山西,朔州,山阴', '3', '0349', '036900', 'Shanyin', 'SY', 'S', '112.81662', '39.52697'),
('289', '140622', '140600', '应县', '中国,山西省,朔州市,应县', '应县', '中国,山西,朔州,应县', '3', '0349', '037600', 'Yingxian', 'YX', 'Y', '113.19052', '39.55279'),
('290', '140623', '140600', '右玉县', '中国,山西省,朔州市,右玉县', '右玉', '中国,山西,朔州,右玉', '3', '0349', '037200', 'Youyu', 'YY', 'Y', '112.46902', '39.99011'),
('291', '140624', '140600', '怀仁县', '中国,山西省,朔州市,怀仁县', '怀仁', '中国,山西,朔州,怀仁', '3', '0349', '038300', 'Huairen', 'HR', 'H', '113.10009', '39.82806'),
('292', '140700', '140000', '晋中市', '中国,山西省,晋中市', '晋中', '中国,山西,晋中', '2', '0354', '030600', 'Jinzhong', 'JZ', 'J', '112.736465', '37.696495'),
('293', '140702', '140700', '榆次区', '中国,山西省,晋中市,榆次区', '榆次', '中国,山西,晋中,榆次', '3', '0354', '030600', 'Yuci', 'YC', 'Y', '112.70788', '37.6978'),
('294', '140721', '140700', '榆社县', '中国,山西省,晋中市,榆社县', '榆社', '中国,山西,晋中,榆社', '3', '0354', '031800', 'Yushe', 'YS', 'Y', '112.97558', '37.0721'),
('295', '140722', '140700', '左权县', '中国,山西省,晋中市,左权县', '左权', '中国,山西,晋中,左权', '3', '0354', '032600', 'Zuoquan', 'ZQ', 'Z', '113.37918', '37.08235'),
('296', '140723', '140700', '和顺县', '中国,山西省,晋中市,和顺县', '和顺', '中国,山西,晋中,和顺', '3', '0354', '032700', 'Heshun', 'HS', 'H', '113.56988', '37.32963'),
('297', '140724', '140700', '昔阳县', '中国,山西省,晋中市,昔阳县', '昔阳', '中国,山西,晋中,昔阳', '3', '0354', '045300', 'Xiyang', 'XY', 'X', '113.70517', '37.61863'),
('298', '140725', '140700', '寿阳县', '中国,山西省,晋中市,寿阳县', '寿阳', '中国,山西,晋中,寿阳', '3', '0354', '045400', 'Shouyang', 'SY', 'S', '113.17495', '37.88899'),
('299', '140726', '140700', '太谷县', '中国,山西省,晋中市,太谷县', '太谷', '中国,山西,晋中,太谷', '3', '0354', '030800', 'Taigu', 'TG', 'T', '112.55246', '37.42161'),
('300', '140727', '140700', '祁县', '中国,山西省,晋中市,祁县', '祁县', '中国,山西,晋中,祁县', '3', '0354', '030900', 'Qixian', 'QX', 'Q', '112.33358', '37.3579'),
('301', '140728', '140700', '平遥县', '中国,山西省,晋中市,平遥县', '平遥', '中国,山西,晋中,平遥', '3', '0354', '031100', 'Pingyao', 'PY', 'P', '112.17553', '37.1892'),
('302', '140729', '140700', '灵石县', '中国,山西省,晋中市,灵石县', '灵石', '中国,山西,晋中,灵石', '3', '0354', '031300', 'Lingshi', 'LS', 'L', '111.7774', '36.84814'),
('303', '140781', '140700', '介休市', '中国,山西省,晋中市,介休市', '介休', '中国,山西,晋中,介休', '3', '0354', '032000', 'Jiexiu', 'JX', 'J', '111.91824', '37.02771'),
('304', '140800', '140000', '运城市', '中国,山西省,运城市', '运城', '中国,山西,运城', '2', '0359', '044000', 'Yuncheng', 'YC', 'Y', '111.003957', '35.022778'),
('305', '140802', '140800', '盐湖区', '中国,山西省,运城市,盐湖区', '盐湖', '中国,山西,运城,盐湖', '3', '0359', '044000', 'Yanhu', 'YH', 'Y', '110.99827', '35.0151'),
('306', '140821', '140800', '临猗县', '中国,山西省,运城市,临猗县', '临猗', '中国,山西,运城,临猗', '3', '0359', '044100', 'Linyi', 'LY', 'L', '110.77432', '35.14455'),
('307', '140822', '140800', '万荣县', '中国,山西省,运城市,万荣县', '万荣', '中国,山西,运城,万荣', '3', '0359', '044200', 'Wanrong', 'WR', 'W', '110.83657', '35.41556'),
('308', '140823', '140800', '闻喜县', '中国,山西省,运城市,闻喜县', '闻喜', '中国,山西,运城,闻喜', '3', '0359', '043800', 'Wenxi', 'WX', 'W', '111.22265', '35.35553'),
('309', '140824', '140800', '稷山县', '中国,山西省,运城市,稷山县', '稷山', '中国,山西,运城,稷山', '3', '0359', '043200', 'Jishan', 'JS', 'J', '110.97924', '35.59993'),
('310', '140825', '140800', '新绛县', '中国,山西省,运城市,新绛县', '新绛', '中国,山西,运城,新绛', '3', '0359', '043100', 'Xinjiang', 'XJ', 'X', '111.22509', '35.61566'),
('311', '140826', '140800', '绛县', '中国,山西省,运城市,绛县', '绛县', '中国,山西,运城,绛县', '3', '0359', '043600', 'Jiangxian', 'JX', 'J', '111.56668', '35.49096'),
('312', '140827', '140800', '垣曲县', '中国,山西省,运城市,垣曲县', '垣曲', '中国,山西,运城,垣曲', '3', '0359', '043700', 'Yuanqu', 'YQ', 'Y', '111.67166', '35.29923'),
('313', '140828', '140800', '夏县', '中国,山西省,运城市,夏县', '夏县', '中国,山西,运城,夏县', '3', '0359', '044400', 'Xiaxian', 'XX', 'X', '111.21966', '35.14121'),
('314', '140829', '140800', '平陆县', '中国,山西省,运城市,平陆县', '平陆', '中国,山西,运城,平陆', '3', '0359', '044300', 'Pinglu', 'PL', 'P', '111.21704', '34.83772'),
('315', '140830', '140800', '芮城县', '中国,山西省,运城市,芮城县', '芮城', '中国,山西,运城,芮城', '3', '0359', '044600', 'Ruicheng', 'RC', 'R', '110.69455', '34.69384'),
('316', '140881', '140800', '永济市', '中国,山西省,运城市,永济市', '永济', '中国,山西,运城,永济', '3', '0359', '044500', 'Yongji', 'YJ', 'Y', '110.44537', '34.86556'),
('317', '140882', '140800', '河津市', '中国,山西省,运城市,河津市', '河津', '中国,山西,运城,河津', '3', '0359', '043300', 'Hejin', 'HJ', 'H', '110.7116', '35.59478'),
('318', '140900', '140000', '忻州市', '中国,山西省,忻州市', '忻州', '中国,山西,忻州', '2', '0350', '034000', 'Xinzhou', 'XZ', 'X', '112.733538', '38.41769'),
('319', '140902', '140900', '忻府区', '中国,山西省,忻州市,忻府区', '忻府', '中国,山西,忻州,忻府', '3', '0350', '034000', 'Xinfu', 'XF', 'X', '112.74603', '38.40414'),
('320', '140921', '140900', '定襄县', '中国,山西省,忻州市,定襄县', '定襄', '中国,山西,忻州,定襄', '3', '0350', '035400', 'Dingxiang', 'DX', 'D', '112.95733', '38.47387'),
('321', '140922', '140900', '五台县', '中国,山西省,忻州市,五台县', '五台', '中国,山西,忻州,五台', '3', '0350', '035500', 'Wutai', 'WT', 'W', '113.25256', '38.72774'),
('322', '140923', '140900', '代县', '中国,山西省,忻州市,代县', '代县', '中国,山西,忻州,代县', '3', '0350', '034200', 'Daixian', 'DX', 'D', '112.95913', '39.06717'),
('323', '140924', '140900', '繁峙县', '中国,山西省,忻州市,繁峙县', '繁峙', '中国,山西,忻州,繁峙', '3', '0350', '034300', 'Fanshi', 'FS', 'F', '113.26303', '39.18886'),
('324', '140925', '140900', '宁武县', '中国,山西省,忻州市,宁武县', '宁武', '中国,山西,忻州,宁武', '3', '0350', '036000', 'Ningwu', 'NW', 'N', '112.30423', '39.00211'),
('325', '140926', '140900', '静乐县', '中国,山西省,忻州市,静乐县', '静乐', '中国,山西,忻州,静乐', '3', '0350', '035100', 'Jingle', 'JL', 'J', '111.94158', '38.3602'),
('326', '140927', '140900', '神池县', '中国,山西省,忻州市,神池县', '神池', '中国,山西,忻州,神池', '3', '0350', '036100', 'Shenchi', 'SC', 'S', '112.20541', '39.09'),
('327', '140928', '140900', '五寨县', '中国,山西省,忻州市,五寨县', '五寨', '中国,山西,忻州,五寨', '3', '0350', '036200', 'Wuzhai', 'WZ', 'W', '111.8489', '38.90757'),
('328', '140929', '140900', '岢岚县', '中国,山西省,忻州市,岢岚县', '岢岚', '中国,山西,忻州,岢岚', '3', '0350', '036300', 'Kelan', 'KL', 'K', '111.57388', '38.70452'),
('329', '140930', '140900', '河曲县', '中国,山西省,忻州市,河曲县', '河曲', '中国,山西,忻州,河曲', '3', '0350', '036500', 'Hequ', 'HQ', 'H', '111.13821', '39.38439'),
('330', '140931', '140900', '保德县', '中国,山西省,忻州市,保德县', '保德', '中国,山西,忻州,保德', '3', '0350', '036600', 'Baode', 'BD', 'B', '111.08656', '39.02248'),
('331', '140932', '140900', '偏关县', '中国,山西省,忻州市,偏关县', '偏关', '中国,山西,忻州,偏关', '3', '0350', '036400', 'Pianguan', 'PG', 'P', '111.50863', '39.43609'),
('332', '140981', '140900', '原平市', '中国,山西省,忻州市,原平市', '原平', '中国,山西,忻州,原平', '3', '0350', '034100', 'Yuanping', 'YP', 'Y', '112.70584', '38.73181'),
('333', '141000', '140000', '临汾市', '中国,山西省,临汾市', '临汾', '中国,山西,临汾', '2', '0357', '041000', 'Linfen', 'LF', 'L', '111.517973', '36.08415'),
('334', '141002', '141000', '尧都区', '中国,山西省,临汾市,尧都区', '尧都', '中国,山西,临汾,尧都', '3', '0357', '041000', 'Yaodu', 'YD', 'Y', '111.5787', '36.08298'),
('335', '141021', '141000', '曲沃县', '中国,山西省,临汾市,曲沃县', '曲沃', '中国,山西,临汾,曲沃', '3', '0357', '043400', 'Quwo', 'QW', 'Q', '111.47525', '35.64119'),
('336', '141022', '141000', '翼城县', '中国,山西省,临汾市,翼城县', '翼城', '中国,山西,临汾,翼城', '3', '0357', '043500', 'Yicheng', 'YC', 'Y', '111.7181', '35.73881'),
('337', '141023', '141000', '襄汾县', '中国,山西省,临汾市,襄汾县', '襄汾', '中国,山西,临汾,襄汾', '3', '0357', '041500', 'Xiangfen', 'XF', 'X', '111.44204', '35.87711'),
('338', '141024', '141000', '洪洞县', '中国,山西省,临汾市,洪洞县', '洪洞', '中国,山西,临汾,洪洞', '3', '0357', '031600', 'Hongtong', 'HT', 'H', '111.67501', '36.25425'),
('339', '141025', '141000', '古县', '中国,山西省,临汾市,古县', '古县', '中国,山西,临汾,古县', '3', '0357', '042400', 'Guxian', 'GX', 'G', '111.92041', '36.26688'),
('340', '141026', '141000', '安泽县', '中国,山西省,临汾市,安泽县', '安泽', '中国,山西,临汾,安泽', '3', '0357', '042500', 'Anze', 'AZ', 'A', '112.24981', '36.14803'),
('341', '141027', '141000', '浮山县', '中国,山西省,临汾市,浮山县', '浮山', '中国,山西,临汾,浮山', '3', '0357', '042600', 'Fushan', 'FS', 'F', '111.84744', '35.96854'),
('342', '141028', '141000', '吉县', '中国,山西省,临汾市,吉县', '吉县', '中国,山西,临汾,吉县', '3', '0357', '042200', 'Jixian', 'JX', 'J', '110.68148', '36.09873'),
('343', '141029', '141000', '乡宁县', '中国,山西省,临汾市,乡宁县', '乡宁', '中国,山西,临汾,乡宁', '3', '0357', '042100', 'Xiangning', 'XN', 'X', '110.84652', '35.97072'),
('344', '141030', '141000', '大宁县', '中国,山西省,临汾市,大宁县', '大宁', '中国,山西,临汾,大宁', '3', '0357', '042300', 'Daning', 'DN', 'D', '110.75216', '36.46624'),
('345', '141031', '141000', '隰县', '中国,山西省,临汾市,隰县', '隰县', '中国,山西,临汾,隰县', '3', '0357', '041300', 'Xixian', 'XX', 'X', '110.93881', '36.69258'),
('346', '141032', '141000', '永和县', '中国,山西省,临汾市,永和县', '永和', '中国,山西,临汾,永和', '3', '0357', '041400', 'Yonghe', 'YH', 'Y', '110.63168', '36.7584'),
('347', '141033', '141000', '蒲县', '中国,山西省,临汾市,蒲县', '蒲县', '中国,山西,临汾,蒲县', '3', '0357', '041200', 'Puxian', 'PX', 'P', '111.09674', '36.41243'),
('348', '141034', '141000', '汾西县', '中国,山西省,临汾市,汾西县', '汾西', '中国,山西,临汾,汾西', '3', '0357', '031500', 'Fenxi', 'FX', 'F', '111.56811', '36.65063'),
('349', '141081', '141000', '侯马市', '中国,山西省,临汾市,侯马市', '侯马', '中国,山西,临汾,侯马', '3', '0357', '043000', 'Houma', 'HM', 'H', '111.37207', '35.61903'),
('350', '141082', '141000', '霍州市', '中国,山西省,临汾市,霍州市', '霍州', '中国,山西,临汾,霍州', '3', '0357', '031400', 'Huozhou', 'HZ', 'H', '111.755', '36.5638'),
('351', '141100', '140000', '吕梁市', '中国,山西省,吕梁市', '吕梁', '中国,山西,吕梁', '2', '0358', '033000', 'Lvliang', 'LL', 'L', '111.134335', '37.524366'),
('352', '141102', '141100', '离石区', '中国,山西省,吕梁市,离石区', '离石', '中国,山西,吕梁,离石', '3', '0358', '033000', 'Lishi', 'LS', 'L', '111.15059', '37.5177'),
('353', '141121', '141100', '文水县', '中国,山西省,吕梁市,文水县', '文水', '中国,山西,吕梁,文水', '3', '0358', '032100', 'Wenshui', 'WS', 'W', '112.02829', '37.43841'),
('354', '141122', '141100', '交城县', '中国,山西省,吕梁市,交城县', '交城', '中国,山西,吕梁,交城', '3', '0358', '030500', 'Jiaocheng', 'JC', 'J', '112.1585', '37.5512'),
('355', '141123', '141100', '兴县', '中国,山西省,吕梁市,兴县', '兴县', '中国,山西,吕梁,兴县', '3', '0358', '033600', 'Xingxian', 'XX', 'X', '111.12692', '38.46321'),
('356', '141124', '141100', '临县', '中国,山西省,吕梁市,临县', '临县', '中国,山西,吕梁,临县', '3', '0358', '033200', 'Linxian', 'LX', 'L', '110.99282', '37.95271'),
('357', '141125', '141100', '柳林县', '中国,山西省,吕梁市,柳林县', '柳林', '中国,山西,吕梁,柳林', '3', '0358', '033300', 'Liulin', 'LL', 'L', '110.88922', '37.42932'),
('358', '141126', '141100', '石楼县', '中国,山西省,吕梁市,石楼县', '石楼', '中国,山西,吕梁,石楼', '3', '0358', '032500', 'Shilou', 'SL', 'S', '110.8352', '36.99731'),
('359', '141127', '141100', '岚县', '中国,山西省,吕梁市,岚县', '岚县', '中国,山西,吕梁,岚县', '3', '0358', '035200', 'Lanxian', 'LX', 'L', '111.67627', '38.27874'),
('360', '141128', '141100', '方山县', '中国,山西省,吕梁市,方山县', '方山', '中国,山西,吕梁,方山', '3', '0358', '033100', 'Fangshan', 'FS', 'F', '111.24011', '37.88979'),
('361', '141129', '141100', '中阳县', '中国,山西省,吕梁市,中阳县', '中阳', '中国,山西,吕梁,中阳', '3', '0358', '033400', 'Zhongyang', 'ZY', 'Z', '111.1795', '37.35715'),
('362', '141130', '141100', '交口县', '中国,山西省,吕梁市,交口县', '交口', '中国,山西,吕梁,交口', '3', '0358', '032400', 'Jiaokou', 'JK', 'J', '111.18103', '36.98213'),
('363', '141181', '141100', '孝义市', '中国,山西省,吕梁市,孝义市', '孝义', '中国,山西,吕梁,孝义', '3', '0358', '032300', 'Xiaoyi', 'XY', 'X', '111.77362', '37.14414'),
('364', '141182', '141100', '汾阳市', '中国,山西省,吕梁市,汾阳市', '汾阳', '中国,山西,吕梁,汾阳', '3', '0358', '032200', 'Fenyang', 'FY', 'F', '111.7882', '37.26605'),
('365', '150000', '100000', '内蒙古自治区', '中国,内蒙古自治区', '内蒙古', '中国,内蒙古', '1', '', '', 'Inner Mongolia', 'NM', 'I', '111.670801', '40.818311'),
('366', '150100', '150000', '呼和浩特市', '中国,内蒙古自治区,呼和浩特市', '呼和浩特', '中国,内蒙古,呼和浩特', '2', '0471', '010000', 'Hohhot', 'HHHT', 'H', '111.670801', '40.818311'),
('367', '150102', '150100', '新城区', '中国,内蒙古自治区,呼和浩特市,新城区', '新城', '中国,内蒙古,呼和浩特,新城', '3', '0471', '010000', 'Xincheng', 'XC', 'X', '111.66554', '40.85828'),
('368', '150103', '150100', '回民区', '中国,内蒙古自治区,呼和浩特市,回民区', '回民', '中国,内蒙古,呼和浩特,回民', '3', '0471', '010000', 'Huimin', 'HM', 'H', '111.62402', '40.80827'),
('369', '150104', '150100', '玉泉区', '中国,内蒙古自治区,呼和浩特市,玉泉区', '玉泉', '中国,内蒙古,呼和浩特,玉泉', '3', '0471', '010000', 'Yuquan', 'YQ', 'Y', '111.67456', '40.75227'),
('370', '150105', '150100', '赛罕区', '中国,内蒙古自治区,呼和浩特市,赛罕区', '赛罕', '中国,内蒙古,呼和浩特,赛罕', '3', '0471', '010020', 'Saihan', 'SH', 'S', '111.70224', '40.79207'),
('371', '150121', '150100', '土默特左旗', '中国,内蒙古自治区,呼和浩特市,土默特左旗', '土左旗', '中国,内蒙古,呼和浩特,土左旗', '3', '0471', '010100', 'Tuzuoqi', 'TZQ', 'T', '111.14898', '40.72229'),
('372', '150122', '150100', '托克托县', '中国,内蒙古自治区,呼和浩特市,托克托县', '托克托', '中国,内蒙古,呼和浩特,托克托', '3', '0471', '010200', 'Tuoketuo', 'TKT', 'T', '111.19101', '40.27492'),
('373', '150123', '150100', '和林格尔县', '中国,内蒙古自治区,呼和浩特市,和林格尔县', '和林格尔', '中国,内蒙古,呼和浩特,和林格尔', '3', '0471', '011500', 'Helingeer', 'HLGE', 'H', '111.82205', '40.37892'),
('374', '150124', '150100', '清水河县', '中国,内蒙古自治区,呼和浩特市,清水河县', '清水河', '中国,内蒙古,呼和浩特,清水河', '3', '0471', '011600', 'Qingshuihe', 'QSH', 'Q', '111.68316', '39.9097'),
('375', '150125', '150100', '武川县', '中国,内蒙古自治区,呼和浩特市,武川县', '武川', '中国,内蒙古,呼和浩特,武川', '3', '0471', '011700', 'Wuchuan', 'WC', 'W', '111.45785', '41.09289'),
('376', '150200', '150000', '包头市', '中国,内蒙古自治区,包头市', '包头', '中国,内蒙古,包头', '2', '0472', '014000', 'Baotou', 'BT', 'B', '109.840405', '40.658168'),
('377', '150202', '150200', '东河区', '中国,内蒙古自治区,包头市,东河区', '东河', '中国,内蒙古,包头,东河', '3', '0472', '014000', 'Donghe', 'DH', 'D', '110.0462', '40.58237'),
('378', '150203', '150200', '昆都仑区', '中国,内蒙古自治区,包头市,昆都仑区', '昆都仑', '中国,内蒙古,包头,昆都仑', '3', '0472', '014010', 'Kundulun', 'KDL', 'K', '109.83862', '40.64175'),
('379', '150204', '150200', '青山区', '中国,内蒙古自治区,包头市,青山区', '青山', '中国,内蒙古,包头,青山', '3', '0472', '014000', 'Qingshan', 'QS', 'Q', '109.90131', '40.64329'),
('380', '150205', '150200', '石拐区', '中国,内蒙古自治区,包头市,石拐区', '石拐', '中国,内蒙古,包头,石拐', '3', '0472', '014070', 'Shiguai', 'SG', 'S', '110.27322', '40.67297'),
('381', '150206', '150200', '白云鄂博矿区', '中国,内蒙古自治区,包头市,白云鄂博矿区', '白云矿区', '中国,内蒙古,包头,白云矿区', '3', '0472', '014080', 'Baiyunkuangqu', 'BYKQ', 'B', '109.97367', '41.76968'),
('382', '150207', '150200', '九原区', '中国,内蒙古自治区,包头市,九原区', '九原', '中国,内蒙古,包头,九原', '3', '0472', '014060', 'Jiuyuan', 'JY', 'J', '109.96496', '40.60554'),
('383', '150221', '150200', '土默特右旗', '中国,内蒙古自治区,包头市,土默特右旗', '土右旗', '中国,内蒙古,包头,土右旗', '3', '0472', '014100', 'Tuyouqi', 'TYQ', 'T', '110.52417', '40.5688'),
('384', '150222', '150200', '固阳县', '中国,内蒙古自治区,包头市,固阳县', '固阳', '中国,内蒙古,包头,固阳', '3', '0472', '014200', 'Guyang', 'GY', 'G', '110.06372', '41.01851'),
('385', '150223', '150200', '达尔罕茂明安联合旗', '中国,内蒙古自治区,包头市,达尔罕茂明安联合旗', '达茂旗', '中国,内蒙古,包头,达茂旗', '3', '0472', '014500', 'Damaoqi', 'DMQ', 'D', '110.43258', '41.69875'),
('386', '150300', '150000', '乌海市', '中国,内蒙古自治区,乌海市', '乌海', '中国,内蒙古,乌海', '2', '0473', '016000', 'Wuhai', 'WH', 'W', '106.825563', '39.673734'),
('387', '150302', '150300', '海勃湾区', '中国,内蒙古自治区,乌海市,海勃湾区', '海勃湾', '中国,内蒙古,乌海,海勃湾', '3', '0473', '016000', 'Haibowan', 'HBW', 'H', '106.8222', '39.66955'),
('388', '150303', '150300', '海南区', '中国,内蒙古自治区,乌海市,海南区', '海南', '中国,内蒙古,乌海,海南', '3', '0473', '016000', 'Hainan', 'HN', 'H', '106.88656', '39.44128'),
('389', '150304', '150300', '乌达区', '中国,内蒙古自治区,乌海市,乌达区', '乌达', '中国,内蒙古,乌海,乌达', '3', '0473', '016000', 'Wuda', 'WD', 'W', '106.72723', '39.505'),
('390', '150400', '150000', '赤峰市', '中国,内蒙古自治区,赤峰市', '赤峰', '中国,内蒙古,赤峰', '2', '0476', '024000', 'Chifeng', 'CF', 'C', '118.956806', '42.275317'),
('391', '150402', '150400', '红山区', '中国,内蒙古自治区,赤峰市,红山区', '红山', '中国,内蒙古,赤峰,红山', '3', '0476', '024000', 'Hongshan', 'HS', 'H', '118.95755', '42.24312'),
('392', '150403', '150400', '元宝山区', '中国,内蒙古自治区,赤峰市,元宝山区', '元宝山', '中国,内蒙古,赤峰,元宝山', '3', '0476', '024000', 'Yuanbaoshan', 'YBS', 'Y', '119.28921', '42.04005'),
('393', '150404', '150400', '松山区', '中国,内蒙古自治区,赤峰市,松山区', '松山', '中国,内蒙古,赤峰,松山', '3', '0476', '024000', 'Songshan', 'SS', 'S', '118.9328', '42.28613'),
('394', '150421', '150400', '阿鲁科尔沁旗', '中国,内蒙古自治区,赤峰市,阿鲁科尔沁旗', '阿旗', '中国,内蒙古,赤峰,阿旗', '3', '0476', '025500', 'Aqi', 'AQ', 'A', '120.06527', '43.87988'),
('395', '150422', '150400', '巴林左旗', '中国,内蒙古自治区,赤峰市,巴林左旗', '左旗', '中国,内蒙古,赤峰,左旗', '3', '0476', '025450', 'Zuoqi', 'ZQ', 'Z', '119.38012', '43.97031'),
('396', '150423', '150400', '巴林右旗', '中国,内蒙古自治区,赤峰市,巴林右旗', '右旗', '中国,内蒙古,赤峰,右旗', '3', '0476', '025150', 'Youqi', 'YQ', 'Y', '118.66461', '43.53387'),
('397', '150424', '150400', '林西县', '中国,内蒙古自治区,赤峰市,林西县', '林西', '中国,内蒙古,赤峰,林西', '3', '0476', '025250', 'Linxi', 'LX', 'L', '118.04733', '43.61165'),
('398', '150425', '150400', '克什克腾旗', '中国,内蒙古自治区,赤峰市,克什克腾旗', '克旗', '中国,内蒙古,赤峰,克旗', '3', '0476', '025350', 'Keqi', 'KQ', 'K', '117.54562', '43.26501'),
('399', '150426', '150400', '翁牛特旗', '中国,内蒙古自治区,赤峰市,翁牛特旗', '翁旗', '中国,内蒙古,赤峰,翁旗', '3', '0476', '024500', 'Wengqi', 'WQ', 'W', '119.03042', '42.93147'),
('400', '150428', '150400', '喀喇沁旗', '中国,内蒙古自治区,赤峰市,喀喇沁旗', '喀旗', '中国,内蒙古,赤峰,喀旗', '3', '0476', '024400', 'Kaqi', 'KQ', 'K', '118.70144', '41.92917'),
('401', '150429', '150400', '宁城县', '中国,内蒙古自治区,赤峰市,宁城县', '宁城', '中国,内蒙古,赤峰,宁城', '3', '0476', '024200', 'Ningcheng', 'NC', 'N', '119.34375', '41.59661'),
('402', '150430', '150400', '敖汉旗', '中国,内蒙古自治区,赤峰市,敖汉旗', '敖汉', '中国,内蒙古,赤峰,敖汉', '3', '0476', '024300', 'Aohan', 'AH', 'A', '119.92163', '42.29071'),
('403', '150500', '150000', '通辽市', '中国,内蒙古自治区,通辽市', '通辽', '中国,内蒙古,通辽', '2', '0475', '028000', 'Tongliao', 'TL', 'T', '122.263119', '43.617429'),
('404', '150502', '150500', '科尔沁区', '中国,内蒙古自治区,通辽市,科尔沁区', '科尔沁', '中国,内蒙古,通辽,科尔沁', '3', '0475', '028000', 'Keerqin', 'KEQ', 'K', '122.25573', '43.62257'),
('405', '150521', '150500', '科尔沁左翼中旗', '中国,内蒙古自治区,通辽市,科尔沁左翼中旗', '科左中旗', '中国,内蒙古,通辽,科左中旗', '3', '0475', '029300', 'Kezuozhongqi', 'KZZQ', 'K', '123.31912', '44.13014'),
('406', '150522', '150500', '科尔沁左翼后旗', '中国,内蒙古自治区,通辽市,科尔沁左翼后旗', '科左后旗', '中国,内蒙古,通辽,科左后旗', '3', '0475', '028100', 'Kezuohouqi', 'KZHQ', 'K', '122.35745', '42.94897'),
('407', '150523', '150500', '开鲁县', '中国,内蒙古自治区,通辽市,开鲁县', '开鲁', '中国,内蒙古,通辽,开鲁', '3', '0475', '028400', 'Kailu', 'KL', 'K', '121.31884', '43.60003'),
('408', '150524', '150500', '库伦旗', '中国,内蒙古自治区,通辽市,库伦旗', '库伦', '中国,内蒙古,通辽,库伦', '3', '0475', '028200', 'Kulun', 'KL', 'K', '121.776', '42.72998'),
('409', '150525', '150500', '奈曼旗', '中国,内蒙古自治区,通辽市,奈曼旗', '奈曼', '中国,内蒙古,通辽,奈曼', '3', '0475', '028300', 'Naiman', 'NM', 'N', '120.66348', '42.84527'),
('410', '150526', '150500', '扎鲁特旗', '中国,内蒙古自治区,通辽市,扎鲁特旗', '扎鲁特', '中国,内蒙古,通辽,扎鲁特', '3', '0475', '029100', 'Zhalute', 'ZLT', 'Z', '120.91507', '44.55592'),
('411', '150581', '150500', '霍林郭勒市', '中国,内蒙古自治区,通辽市,霍林郭勒市', '霍林郭勒', '中国,内蒙古,通辽,霍林郭勒', '3', '0475', '029200', 'Huolinguole', 'HLGL', 'H', '119.65429', '45.53454'),
('412', '150600', '150000', '鄂尔多斯市', '中国,内蒙古自治区,鄂尔多斯市', '鄂尔多斯', '中国,内蒙古,鄂尔多斯', '2', '0477', '017000', 'Ordos', 'EEDS', 'O', '109.99029', '39.817179'),
('413', '150602', '150600', '东胜区', '中国,内蒙古自治区,鄂尔多斯市,东胜区', '东胜', '中国,内蒙古,鄂尔多斯,东胜', '3', '0477', '017000', 'Dongsheng', 'DS', 'D', '109.96289', '39.82236'),
('414', '150603', '150600', '康巴什区', '中国,内蒙古自治区,鄂尔多斯市,康巴什区', '康巴什', '中国,内蒙古,鄂尔多斯,康巴什', '3', '0477', '017000', 'Kangbashen', 'KBS', 'K', '109.789197', '39.605387'),
('415', '150621', '150600', '达拉特旗', '中国,内蒙古自治区,鄂尔多斯市,达拉特旗', '达拉特', '中国,内蒙古,鄂尔多斯,达拉特', '3', '0477', '014300', 'Dalate', 'DLT', 'D', '110.03317', '40.4001'),
('416', '150622', '150600', '准格尔旗', '中国,内蒙古自治区,鄂尔多斯市,准格尔旗', '准格尔', '中国,内蒙古,鄂尔多斯,准格尔', '3', '0477', '017100', 'Zhungeer', 'ZGE', 'Z', '111.23645', '39.86783'),
('417', '150623', '150600', '鄂托克前旗', '中国,内蒙古自治区,鄂尔多斯市,鄂托克前旗', '鄂前旗', '中国,内蒙古,鄂尔多斯,鄂前旗', '3', '0477', '016200', 'Eqianqi', 'EQQ', 'E', '107.48403', '38.18396'),
('418', '150624', '150600', '鄂托克旗', '中国,内蒙古自治区,鄂尔多斯市,鄂托克旗', '鄂托克', '中国,内蒙古,鄂尔多斯,鄂托克', '3', '0477', '016100', 'Etuoke', 'ETK', 'E', '107.98226', '39.09456'),
('419', '150625', '150600', '杭锦旗', '中国,内蒙古自治区,鄂尔多斯市,杭锦旗', '杭锦旗', '中国,内蒙古,鄂尔多斯,杭锦旗', '3', '0477', '017400', 'Hangjinqi', 'HJQ', 'H', '108.72934', '39.84023'),
('420', '150626', '150600', '乌审旗', '中国,内蒙古自治区,鄂尔多斯市,乌审旗', '乌审旗', '中国,内蒙古,鄂尔多斯,乌审旗', '3', '0477', '017300', 'Wushenqi', 'WSQ', 'W', '108.8461', '38.59092'),
('421', '150627', '150600', '伊金霍洛旗', '中国,内蒙古自治区,鄂尔多斯市,伊金霍洛旗', '伊金霍洛', '中国,内蒙古,鄂尔多斯,伊金霍洛', '3', '0477', '017200', 'Yijinhuoluo', 'YJHL', 'Y', '109.74908', '39.57393'),
('422', '150700', '150000', '呼伦贝尔市', '中国,内蒙古自治区,呼伦贝尔市', '呼伦贝尔', '中国,内蒙古,呼伦贝尔', '2', '0470', '021000', 'Hulunber', 'HLBE', 'H', '119.758168', '49.215333'),
('423', '150702', '150700', '海拉尔区', '中国,内蒙古自治区,呼伦贝尔市,海拉尔区', '海拉尔', '中国,内蒙古,呼伦贝尔,海拉尔', '3', '0470', '021000', 'Hailaer', 'HLE', 'H', '119.7364', '49.2122'),
('424', '150703', '150700', '扎赉诺尔区', '中国,内蒙古自治区,呼伦贝尔市,扎赉诺尔区', '扎赉诺尔', '中国,内蒙古,呼伦贝尔,扎赉诺尔', '3', '0470', '021410', 'Zhalainuoer', 'ZLNE', 'Z', '117.792702', '49.486943'),
('425', '150721', '150700', '阿荣旗', '中国,内蒙古自治区,呼伦贝尔市,阿荣旗', '阿荣旗', '中国,内蒙古,呼伦贝尔,阿荣旗', '3', '0470', '162750', 'Arongqi', 'ARQ', 'A', '123.45941', '48.12581'),
('426', '150722', '150700', '莫力达瓦达斡尔族自治旗', '中国,内蒙古自治区,呼伦贝尔市,莫力达瓦达斡尔族自治旗', '莫旗', '中国,内蒙古,呼伦贝尔,莫旗', '3', '0470', '162850', 'Moqi', 'MQ', 'M', '124.51498', '48.48055'),
('427', '150723', '150700', '鄂伦春自治旗', '中国,内蒙古自治区,呼伦贝尔市,鄂伦春自治旗', '鄂伦春', '中国,内蒙古,呼伦贝尔,鄂伦春', '3', '0470', '022450', 'Elunchun', 'ELC', 'E', '123.72604', '50.59777'),
('428', '150724', '150700', '鄂温克族自治旗', '中国,内蒙古自治区,呼伦贝尔市,鄂温克族自治旗', '鄂温克旗', '中国,内蒙古,呼伦贝尔,鄂温克旗', '3', '0470', '021100', 'Ewenkeqi', 'EWKQ', 'E', '119.7565', '49.14284'),
('429', '150725', '150700', '陈巴尔虎旗', '中国,内蒙古自治区,呼伦贝尔市,陈巴尔虎旗', '陈旗', '中国,内蒙古,呼伦贝尔,陈旗', '3', '0470', '021500', 'Chenqi', 'CQ', 'C', '119.42434', '49.32684'),
('430', '150726', '150700', '新巴尔虎左旗', '中国,内蒙古自治区,呼伦贝尔市,新巴尔虎左旗', '新左旗', '中国,内蒙古,呼伦贝尔,新左旗', '3', '0470', '021200', 'Xinzuoqi', 'XZQ', 'X', '118.26989', '48.21842'),
('431', '150727', '150700', '新巴尔虎右旗', '中国,内蒙古自治区,呼伦贝尔市,新巴尔虎右旗', '新右旗', '中国,内蒙古,呼伦贝尔,新右旗', '3', '0470', '021300', 'Xinyouqi', 'XYQ', 'X', '116.82366', '48.66473'),
('432', '150781', '150700', '满洲里市', '中国,内蒙古自治区,呼伦贝尔市,满洲里市', '满洲里', '中国,内蒙古,呼伦贝尔,满洲里', '3', '0470', '021400', 'Manzhouli', 'MZL', 'M', '117.47946', '49.58272'),
('433', '150782', '150700', '牙克石市', '中国,内蒙古自治区,呼伦贝尔市,牙克石市', '牙克石', '中国,内蒙古,呼伦贝尔,牙克石', '3', '0470', '022150', 'Yakeshi', 'YKS', 'Y', '120.7117', '49.2856'),
('434', '150783', '150700', '扎兰屯市', '中国,内蒙古自治区,呼伦贝尔市,扎兰屯市', '扎兰屯', '中国,内蒙古,呼伦贝尔,扎兰屯', '3', '0470', '162650', 'Zhalantun', 'ZLT', 'Z', '122.73757', '48.01363'),
('435', '150784', '150700', '额尔古纳市', '中国,内蒙古自治区,呼伦贝尔市,额尔古纳市', '额尔古纳', '中国,内蒙古,呼伦贝尔,额尔古纳', '3', '0470', '022250', 'Eerguna', 'EEGN', 'E', '120.19094', '50.24249'),
('436', '150785', '150700', '根河市', '中国,内蒙古自治区,呼伦贝尔市,根河市', '根河', '中国,内蒙古,呼伦贝尔,根河', '3', '0470', '022350', 'Genhe', 'GH', 'G', '121.52197', '50.77996'),
('437', '150800', '150000', '巴彦淖尔市', '中国,内蒙古自治区,巴彦淖尔市', '巴彦淖尔', '中国,内蒙古,巴彦淖尔', '2', '0478', '015000', 'Bayan Nur', 'BYNE', 'B', '107.416959', '40.757402'),
('438', '150802', '150800', '临河区', '中国,内蒙古自治区,巴彦淖尔市,临河区', '临河', '中国,内蒙古,巴彦淖尔,临河', '3', '0478', '015000', 'Linhe', 'LH', 'L', '107.42668', '40.75827'),
('439', '150821', '150800', '五原县', '中国,内蒙古自治区,巴彦淖尔市,五原县', '五原', '中国,内蒙古,巴彦淖尔,五原', '3', '0478', '015100', 'Wuyuan', 'WY', 'W', '108.26916', '41.09631'),
('440', '150822', '150800', '磴口县', '中国,内蒙古自治区,巴彦淖尔市,磴口县', '磴口', '中国,内蒙古,巴彦淖尔,磴口', '3', '0478', '015200', 'Dengkou', 'DK', 'D', '107.00936', '40.33062'),
('441', '150823', '150800', '乌拉特前旗', '中国,内蒙古自治区,巴彦淖尔市,乌拉特前旗', '乌前旗', '中国,内蒙古,巴彦淖尔,乌前旗', '3', '0478', '014400', 'Wuqianqi', 'WQQ', 'W', '108.65219', '40.73649'),
('442', '150824', '150800', '乌拉特中旗', '中国,内蒙古自治区,巴彦淖尔市,乌拉特中旗', '乌中旗', '中国,内蒙古,巴彦淖尔,乌中旗', '3', '0478', '015300', 'Wuzhongqi', 'WZQ', 'W', '108.52587', '41.56789'),
('443', '150825', '150800', '乌拉特后旗', '中国,内蒙古自治区,巴彦淖尔市,乌拉特后旗', '乌后旗', '中国,内蒙古,巴彦淖尔,乌后旗', '3', '0478', '015500', 'Wuhouqi', 'WHQ', 'W', '106.98971', '41.43151'),
('444', '150826', '150800', '杭锦后旗', '中国,内蒙古自治区,巴彦淖尔市,杭锦后旗', '杭锦后旗', '中国,内蒙古,巴彦淖尔,杭锦后旗', '3', '0478', '015400', 'Hangjinhouqi', 'HJHQ', 'H', '107.15133', '40.88627'),
('445', '150900', '150000', '乌兰察布市', '中国,内蒙古自治区,乌兰察布市', '乌兰察布', '中国,内蒙古,乌兰察布', '2', '0474', '012000', 'Ulanqab', 'WLCB', 'U', '113.114543', '41.034126'),
('446', '150902', '150900', '集宁区', '中国,内蒙古自治区,乌兰察布市,集宁区', '集宁', '中国,内蒙古,乌兰察布,集宁', '3', '0474', '012000', 'Jining', 'JN', 'J', '113.11452', '41.0353'),
('447', '150921', '150900', '卓资县', '中国,内蒙古自治区,乌兰察布市,卓资县', '卓资', '中国,内蒙古,乌兰察布,卓资', '3', '0474', '012300', 'Zhuozi', 'ZZ', 'Z', '112.57757', '40.89414'),
('448', '150922', '150900', '化德县', '中国,内蒙古自治区,乌兰察布市,化德县', '化德', '中国,内蒙古,乌兰察布,化德', '3', '0474', '013350', 'Huade', 'HD', 'H', '114.01071', '41.90433'),
('449', '150923', '150900', '商都县', '中国,内蒙古自治区,乌兰察布市,商都县', '商都', '中国,内蒙古,乌兰察布,商都', '3', '0474', '013400', 'Shangdu', 'SD', 'S', '113.57772', '41.56213'),
('450', '150924', '150900', '兴和县', '中国,内蒙古自治区,乌兰察布市,兴和县', '兴和', '中国,内蒙古,乌兰察布,兴和', '3', '0474', '013650', 'Xinghe', 'XH', 'X', '113.83395', '40.87186'),
('451', '150925', '150900', '凉城县', '中国,内蒙古自治区,乌兰察布市,凉城县', '凉城', '中国,内蒙古,乌兰察布,凉城', '3', '0474', '013750', 'Liangcheng', 'LC', 'L', '112.49569', '40.53346'),
('452', '150926', '150900', '察哈尔右翼前旗', '中国,内蒙古自治区,乌兰察布市,察哈尔右翼前旗', '察右前旗', '中国,内蒙古,乌兰察布,察右前旗', '3', '0474', '012200', 'Chayouqianqi', 'CYQQ', 'C', '113.22131', '40.7788'),
('453', '150927', '150900', '察哈尔右翼中旗', '中国,内蒙古自治区,乌兰察布市,察哈尔右翼中旗', '察右中旗', '中国,内蒙古,乌兰察布,察右中旗', '3', '0474', '013500', 'Chayouzhongqi', 'CYZQ', 'C', '112.63537', '41.27742'),
('454', '150928', '150900', '察哈尔右翼后旗', '中国,内蒙古自治区,乌兰察布市,察哈尔右翼后旗', '察右后旗', '中国,内蒙古,乌兰察布,察右后旗', '3', '0474', '012400', 'Chayouhouqi', 'CYHQ', 'C', '113.19216', '41.43554'),
('455', '150929', '150900', '四子王旗', '中国,内蒙古自治区,乌兰察布市,四子王旗', '四子王旗', '中国,内蒙古,乌兰察布,四子王旗', '3', '0474', '011800', 'Siziwangqi', 'SZWQ', 'S', '111.70654', '41.53312'),
('456', '150981', '150900', '丰镇市', '中国,内蒙古自治区,乌兰察布市,丰镇市', '丰镇', '中国,内蒙古,乌兰察布,丰镇', '3', '0474', '012100', 'Fengzhen', 'FZ', 'F', '113.10983', '40.4369'),
('457', '152200', '150000', '兴安盟', '中国,内蒙古自治区,兴安盟', '兴安盟', '中国,内蒙古,兴安盟', '2', '0482', '137400', 'Hinggan', 'XAM', 'H', '122.070317', '46.076268'),
('458', '152201', '152200', '乌兰浩特市', '中国,内蒙古自治区,兴安盟,乌兰浩特市', '乌兰浩特', '中国,内蒙古,兴安盟,乌兰浩特', '3', '0482', '137400', 'Wulanhaote', 'WLHT', 'W', '122.06378', '46.06235'),
('459', '152202', '152200', '阿尔山市', '中国,内蒙古自治区,兴安盟,阿尔山市', '阿尔山', '中国,内蒙古,兴安盟,阿尔山', '3', '0482', '137400', 'Aershan', 'AES', 'A', '119.94317', '47.17716'),
('460', '152221', '152200', '科尔沁右翼前旗', '中国,内蒙古自治区,兴安盟,科尔沁右翼前旗', '科右前旗', '中国,内蒙古,兴安盟,科右前旗', '3', '0482', '137400', 'Keyouqianqi', 'KYQQ', 'K', '121.95269', '46.0795'),
('461', '152222', '152200', '科尔沁右翼中旗', '中国,内蒙古自治区,兴安盟,科尔沁右翼中旗', '科右中旗', '中国,内蒙古,兴安盟,科右中旗', '3', '0482', '029400', 'Keyouzhongqi', 'KYZQ', 'K', '121.46807', '45.05605'),
('462', '152223', '152200', '扎赉特旗', '中国,内蒙古自治区,兴安盟,扎赉特旗', '扎赉特旗', '中国,内蒙古,兴安盟,扎赉特旗', '3', '0482', '137600', 'Zhalaiteqi', 'ZLTQ', 'Z', '122.91229', '46.7267'),
('463', '152224', '152200', '突泉县', '中国,内蒙古自治区,兴安盟,突泉县', '突泉', '中国,内蒙古,兴安盟,突泉', '3', '0482', '137500', 'Tuquan', 'TQ', 'T', '121.59396', '45.38187'),
('464', '152500', '150000', '锡林郭勒盟', '中国,内蒙古自治区,锡林郭勒盟', '锡林郭勒盟', '中国,内蒙古,锡林郭勒盟', '2', '0479', '026000', 'Xilin Gol', 'XLGLM', 'X', '116.090996', '43.944018'),
('465', '152501', '152500', '二连浩特市', '中国,内蒙古自治区,锡林郭勒盟,二连浩特市', '二连浩特', '中国,内蒙古,锡林郭勒盟,二连浩特', '3', '0479', '012600', 'Erlianhaote', 'ELHT', 'E', '111.98297', '43.65303'),
('466', '152502', '152500', '锡林浩特市', '中国,内蒙古自治区,锡林郭勒盟,锡林浩特市', '锡林浩特', '中国,内蒙古,锡林郭勒盟,锡林浩特', '3', '0479', '026000', 'Xilinhaote', 'XLHT', 'X', '116.08603', '43.93341'),
('467', '152522', '152500', '阿巴嘎旗', '中国,内蒙古自治区,锡林郭勒盟,阿巴嘎旗', '阿巴嘎旗', '中国,内蒙古,锡林郭勒盟,阿巴嘎旗', '3', '0479', '011400', 'Abagaqi', 'ABGQ', 'A', '114.96826', '44.02174'),
('468', '152523', '152500', '苏尼特左旗', '中国,内蒙古自治区,锡林郭勒盟,苏尼特左旗', '东苏旗', '中国,内蒙古,锡林郭勒盟,东苏旗', '3', '0479', '011300', 'Dongsuqi', 'DSQ', 'D', '113.6506', '43.85687'),
('469', '152524', '152500', '苏尼特右旗', '中国,内蒙古自治区,锡林郭勒盟,苏尼特右旗', '西苏旗', '中国,内蒙古,锡林郭勒盟,西苏旗', '3', '0479', '011200', 'Xisuqi', 'XSQ', 'X', '112.65741', '42.7469'),
('470', '152525', '152500', '东乌珠穆沁旗', '中国,内蒙古自治区,锡林郭勒盟,东乌珠穆沁旗', '东乌旗', '中国,内蒙古,锡林郭勒盟,东乌旗', '3', '0479', '026300', 'Dongwuqi', 'DWQ', 'D', '116.97293', '45.51108'),
('471', '152526', '152500', '西乌珠穆沁旗', '中国,内蒙古自治区,锡林郭勒盟,西乌珠穆沁旗', '西乌旗', '中国,内蒙古,锡林郭勒盟,西乌旗', '3', '0479', '026200', 'Xiwuqi', 'XWQ', 'X', '117.60983', '44.59623'),
('472', '152527', '152500', '太仆寺旗', '中国,内蒙古自治区,锡林郭勒盟,太仆寺旗', '太旗', '中国,内蒙古,锡林郭勒盟,太旗', '3', '0479', '027000', 'Taiqi', 'TQ', 'T', '115.28302', '41.87727'),
('473', '152528', '152500', '镶黄旗', '中国,内蒙古自治区,锡林郭勒盟,镶黄旗', '镶黄旗', '中国,内蒙古,锡林郭勒盟,镶黄旗', '3', '0479', '013250', 'Xianghuangqi', 'XHQ', 'X', '113.84472', '42.23927'),
('474', '152529', '152500', '正镶白旗', '中国,内蒙古自治区,锡林郭勒盟,正镶白旗', '正镶白旗', '中国,内蒙古,锡林郭勒盟,正镶白旗', '3', '0479', '013800', 'Zhengxiangbaiqi', 'ZXBQ', 'Z', '115.00067', '42.30712'),
('475', '152530', '152500', '正蓝旗', '中国,内蒙古自治区,锡林郭勒盟,正蓝旗', '正蓝旗', '中国,内蒙古,锡林郭勒盟,正蓝旗', '3', '0479', '027200', 'Zhenglanqi', 'ZLQ', 'Z', '116.00363', '42.25229'),
('476', '152531', '152500', '多伦县', '中国,内蒙古自治区,锡林郭勒盟,多伦县', '多伦', '中国,内蒙古,锡林郭勒盟,多伦', '3', '0479', '027300', 'Duolun', 'DL', 'D', '116.48565', '42.203'),
('477', '152900', '150000', '阿拉善盟', '中国,内蒙古自治区,阿拉善盟', '阿拉善盟', '中国,内蒙古,阿拉善盟', '2', '0483', '750300', 'Alxa', 'ALSM', 'A', '105.706422', '38.844814'),
('478', '152921', '152900', '阿拉善左旗', '中国,内蒙古自治区,阿拉善盟,阿拉善左旗', '阿左旗', '中国,内蒙古,阿拉善盟,阿左旗', '3', '0483', '750300', 'Azuoqi', 'AZQ', 'A', '105.67532', '38.8293'),
('479', '152922', '152900', '阿拉善右旗', '中国,内蒙古自治区,阿拉善盟,阿拉善右旗', '阿右旗', '中国,内蒙古,阿拉善盟,阿右旗', '3', '0483', '737300', 'Ayouqi', 'AYQ', 'A', '101.66705', '39.21533'),
('480', '152923', '152900', '额济纳旗', '中国,内蒙古自治区,阿拉善盟,额济纳旗', '额济纳', '中国,内蒙古,阿拉善盟,额济纳', '3', '0483', '735400', 'Ejina', 'EJN', 'E', '101.06887', '41.96755'),
('481', '210000', '100000', '辽宁省', '中国,辽宁省', '辽宁', '中国,辽宁', '1', '', '', 'Liaoning', 'LN', 'L', '123.429096', '41.796767'),
('482', '210100', '210000', '沈阳市', '中国,辽宁省,沈阳市', '沈阳', '中国,辽宁,沈阳', '2', '024', '110000', 'Shenyang', 'SY', 'S', '123.429096', '41.796767'),
('483', '210102', '210100', '和平区', '中国,辽宁省,沈阳市,和平区', '和平', '中国,辽宁,沈阳,和平', '3', '024', '110000', 'Heping', 'HP', 'H', '123.4204', '41.78997'),
('484', '210103', '210100', '沈河区', '中国,辽宁省,沈阳市,沈河区', '沈河', '中国,辽宁,沈阳,沈河', '3', '024', '110000', 'Shenhe', 'SH', 'S', '123.45871', '41.79625'),
('485', '210104', '210100', '大东区', '中国,辽宁省,沈阳市,大东区', '大东', '中国,辽宁,沈阳,大东', '3', '024', '110000', 'Dadong', 'DD', 'D', '123.46997', '41.80539'),
('486', '210105', '210100', '皇姑区', '中国,辽宁省,沈阳市,皇姑区', '皇姑', '中国,辽宁,沈阳,皇姑', '3', '024', '110000', 'Huanggu', 'HG', 'H', '123.42527', '41.82035'),
('487', '210106', '210100', '铁西区', '中国,辽宁省,沈阳市,铁西区', '铁西', '中国,辽宁,沈阳,铁西', '3', '024', '110020', 'Tiexi', 'TX', 'T', '123.37675', '41.80269'),
('488', '210111', '210100', '苏家屯区', '中国,辽宁省,沈阳市,苏家屯区', '苏家屯', '中国,辽宁,沈阳,苏家屯', '3', '024', '110100', 'Sujiatun', 'SJT', 'S', '123.34405', '41.66475'),
('489', '210112', '210100', '浑南新区', '中国,辽宁省,沈阳市,浑南新区', '浑南新区', '中国,辽宁,沈阳,浑南新区', '3', '024', '110015', 'Hunnan', 'HN', 'H', '123.457707', '41.719450'),
('490', '210113', '210100', '沈北新区', '中国,辽宁省,沈阳市,沈北新区', '沈北新区', '中国,辽宁,沈阳,沈北新区', '3', '024', '110000', 'Shenbei', 'SB', 'S', '123.52658', '42.05297'),
('491', '210114', '210100', '于洪区', '中国,辽宁省,沈阳市,于洪区', '于洪', '中国,辽宁,沈阳,于洪', '3', '024', '110000', 'Yuhong', 'YH', 'Y', '123.30807', '41.794'),
('492', '210115', '210100', '辽中区', '中国,辽宁省,沈阳市,辽中区', '辽中', '中国,辽宁,沈阳,辽中', '3', '024', '110200', 'Liaozhong', 'LZ', 'L', '122.72659', '41.51302'),
('493', '210123', '210100', '康平县', '中国,辽宁省,沈阳市,康平县', '康平', '中国,辽宁,沈阳,康平', '3', '024', '110500', 'Kangping', 'KP', 'K', '123.35446', '42.75081'),
('494', '210124', '210100', '法库县', '中国,辽宁省,沈阳市,法库县', '法库', '中国,辽宁,沈阳,法库', '3', '024', '110400', 'Faku', 'FK', 'F', '123.41214', '42.50608'),
('495', '210181', '210100', '新民市', '中国,辽宁省,沈阳市,新民市', '新民', '中国,辽宁,沈阳,新民', '3', '024', '110300', 'Xinmin', 'XM', 'X', '122.82867', '41.99847'),
('496', '210182', '210100', '高新区', '中国,辽宁省,沈阳市,高新区', '高新区', '中国,辽宁,沈阳,高新区', '3', '024', '110000', 'Gaoxinqu', 'GXQ', 'G', '123.4466', '41.714636'),
('497', '210200', '210000', '大连市', '中国,辽宁省,大连市', '大连', '中国,辽宁,大连', '2', '0411', '116000', 'Dalian', 'DL', 'D', '121.618622', '38.91459'),
('498', '210202', '210200', '中山区', '中国,辽宁省,大连市,中山区', '中山', '中国,辽宁,大连,中山', '3', '0411', '116000', 'Zhongshan', 'ZS', 'Z', '121.64465', '38.91859'),
('499', '210203', '210200', '西岗区', '中国,辽宁省,大连市,西岗区', '西岗', '中国,辽宁,大连,西岗', '3', '0411', '116000', 'Xigang', 'XG', 'X', '121.61238', '38.91469'),
('500', '210204', '210200', '沙河口区', '中国,辽宁省,大连市,沙河口区', '沙河口', '中国,辽宁,大连,沙河口', '3', '0411', '116000', 'Shahekou', 'SHK', 'S', '121.58017', '38.90536'),
('501', '210211', '210200', '甘井子区', '中国,辽宁省,大连市,甘井子区', '甘井子', '中国,辽宁,大连,甘井子', '3', '0411', '116000', 'Ganjingzi', 'GJZ', 'G', '121.56567', '38.95017'),
('502', '210212', '210200', '旅顺口区', '中国,辽宁省,大连市,旅顺口区', '旅顺口', '中国,辽宁,大连,旅顺口', '3', '0411', '116000', 'Lvshunkou', 'LSK', 'L', '121.26202', '38.85125'),
('503', '210213', '210200', '金州区', '中国,辽宁省,大连市,金州区', '金州', '中国,辽宁,大连,金州', '3', '0411', '116000', 'Jinzhou', 'JZ', 'J', '121.71893', '39.1004'),
('504', '210214', '210200', '普兰店市', '中国,辽宁省,大连市,普兰店区', '普兰店', '中国,辽宁,大连,普兰店', '3', '0411', '116200', 'Pulandian', 'PLD', 'P', '121.96316', '39.39465'),
('505', '210224', '210200', '长海县', '中国,辽宁省,大连市,长海县', '长海', '中国,辽宁,大连,长海', '3', '0411', '116500', 'Changhai', 'CH', 'C', '122.58859', '39.27274'),
('506', '210281', '210200', '瓦房店市', '中国,辽宁省,大连市,瓦房店市', '瓦房店', '中国,辽宁,大连,瓦房店', '3', '0411', '116300', 'Wafangdian', 'WFD', 'W', '121.98104', '39.62843'),
('507', '210283', '210200', '庄河市', '中国,辽宁省,大连市,庄河市', '庄河', '中国,辽宁,大连,庄河', '3', '0411', '116400', 'Zhuanghe', 'ZH', 'Z', '122.96725', '39.68815'),
('508', '210284', '210200', '高新区', '中国,辽宁省,大连市,高新区', '高新区', '中国,辽宁,大连,高新区', '3', '0411', '116000', 'Gaoxinqu', 'GXQ', 'G', '121.512402', '38.844665'),
('509', '210285', '210200', '经济开发区', '中国,辽宁省,大连市,经济开发区', '经济开发区', '中国,辽宁,大连,经济开发区', '3', '0411', '116000', 'Jingjikaifaqu', 'JJQ', 'J', '121.863577', '39.063208'),
('510', '210286', '210200', '金普新区', '中国,辽宁省,大连市,金普新区', '金普新区', '中国,辽宁,大连,金普新区', '3', '0411', '116100', 'Jinpuxinqu', 'JPXQ', 'J', '121.789627', '39.055451'),
('511', '210300', '210000', '鞍山市', '中国,辽宁省,鞍山市', '鞍山', '中国,辽宁,鞍山', '2', '0412', '114000', 'Anshan', 'AS', 'A', '122.995632', '41.110626'),
('512', '210302', '210300', '铁东区', '中国,辽宁省,鞍山市,铁东区', '铁东', '中国,辽宁,鞍山,铁东', '3', '0412', '114000', 'Tiedong', 'TD', 'T', '122.99085', '41.08975'),
('513', '210303', '210300', '铁西区', '中国,辽宁省,鞍山市,铁西区', '铁西', '中国,辽宁,鞍山,铁西', '3', '0412', '114000', 'Tiexi', 'TX', 'T', '122.96967', '41.11977'),
('514', '210304', '210300', '立山区', '中国,辽宁省,鞍山市,立山区', '立山', '中国,辽宁,鞍山,立山', '3', '0412', '114031', 'Lishan', 'LS', 'L', '123.02948', '41.15008'),
('515', '210311', '210300', '千山区', '中国,辽宁省,鞍山市,千山区', '千山', '中国,辽宁,鞍山,千山', '3', '0412', '114000', 'Qianshan', 'QS', 'Q', '122.96048', '41.07507'),
('516', '210321', '210300', '台安县', '中国,辽宁省,鞍山市,台安县', '台安', '中国,辽宁,鞍山,台安', '3', '0412', '114100', 'Tai\'an', 'TA', 'T', '122.43585', '41.41265'),
('517', '210323', '210300', '岫岩满族自治县', '中国,辽宁省,鞍山市,岫岩满族自治县', '岫岩', '中国,辽宁,鞍山,岫岩', '3', '0412', '114300', 'Xiuyan', 'XY', 'X', '123.28875', '40.27996'),
('518', '210381', '210300', '海城市', '中国,辽宁省,鞍山市,海城市', '海城', '中国,辽宁,鞍山,海城', '3', '0412', '114200', 'Haicheng', 'HC', 'H', '122.68457', '40.88142'),
('519', '210382', '210300', '高新区', '中国,辽宁省,鞍山市,高新区', '高新区', '中国,辽宁,鞍山,高新区', '3', '0412', '114000', 'Gaoxinqu', 'GXQ', 'G', '123.052689', '41.105775'),
('520', '210400', '210000', '抚顺市', '中国,辽宁省,抚顺市', '抚顺', '中国,辽宁,抚顺', '2', '024', '113000', 'Fushun', 'FS', 'F', '123.921109', '41.875956'),
('521', '210402', '210400', '新抚区', '中国,辽宁省,抚顺市,新抚区', '新抚', '中国,辽宁,抚顺,新抚', '3', '024', '113000', 'Xinfu', 'XF', 'X', '123.91264', '41.86205'),
('522', '210403', '210400', '东洲区', '中国,辽宁省,抚顺市,东洲区', '东洲', '中国,辽宁,抚顺,东洲', '3', '024', '113000', 'Dongzhou', 'DZ', 'D', '124.03759', '41.8519'),
('523', '210404', '210400', '望花区', '中国,辽宁省,抚顺市,望花区', '望花', '中国,辽宁,抚顺,望花', '3', '024', '113000', 'Wanghua', 'WH', 'W', '123.78283', '41.85532'),
('524', '210411', '210400', '顺城区', '中国,辽宁省,抚顺市,顺城区', '顺城', '中国,辽宁,抚顺,顺城', '3', '024', '113000', 'Shuncheng', 'SC', 'S', '123.94506', '41.88321'),
('525', '210421', '210400', '抚顺县', '中国,辽宁省,抚顺市,抚顺县', '抚顺', '中国,辽宁,抚顺,抚顺', '3', '024', '113100', 'Fushun', 'FS', 'F', '124.17755', '41.71217'),
('526', '210422', '210400', '新宾满族自治县', '中国,辽宁省,抚顺市,新宾满族自治县', '新宾', '中国,辽宁,抚顺,新宾', '3', '024', '113200', 'Xinbin', 'XB', 'X', '125.04049', '41.73409'),
('527', '210423', '210400', '清原满族自治县', '中国,辽宁省,抚顺市,清原满族自治县', '清原', '中国,辽宁,抚顺,清原', '3', '024', '113300', 'Qingyuan', 'QY', 'Q', '124.92807', '42.10221'),
('528', '210500', '210000', '本溪市', '中国,辽宁省,本溪市', '本溪', '中国,辽宁,本溪', '2', '024', '117000', 'Benxi', 'BX', 'B', '123.770519', '41.297909'),
('529', '210502', '210500', '平山区', '中国,辽宁省,本溪市,平山区', '平山', '中国,辽宁,本溪,平山', '3', '024', '117000', 'Pingshan', 'PS', 'P', '123.76892', '41.2997'),
('530', '210503', '210500', '溪湖区', '中国,辽宁省,本溪市,溪湖区', '溪湖', '中国,辽宁,本溪,溪湖', '3', '024', '117000', 'Xihu', 'XH', 'X', '123.76764', '41.32921'),
('531', '210504', '210500', '明山区', '中国,辽宁省,本溪市,明山区', '明山', '中国,辽宁,本溪,明山', '3', '024', '117000', 'Mingshan', 'MS', 'M', '123.81746', '41.30827'),
('532', '210505', '210500', '南芬区', '中国,辽宁省,本溪市,南芬区', '南芬', '中国,辽宁,本溪,南芬', '3', '024', '117000', 'Nanfen', 'NF', 'N', '123.74523', '41.1006'),
('533', '210521', '210500', '本溪满族自治县', '中国,辽宁省,本溪市,本溪满族自治县', '本溪', '中国,辽宁,本溪,本溪', '3', '024', '117100', 'Benxi', 'BX', 'B', '124.12741', '41.30059'),
('534', '210522', '210500', '桓仁满族自治县', '中国,辽宁省,本溪市,桓仁满族自治县', '桓仁', '中国,辽宁,本溪,桓仁', '3', '024', '117200', 'Huanren', 'HR', 'H', '125.36062', '41.26798'),
('535', '210600', '210000', '丹东市', '中国,辽宁省,丹东市', '丹东', '中国,辽宁,丹东', '2', '0415', '118000', 'Dandong', 'DD', 'D', '124.383044', '40.124296'),
('536', '210602', '210600', '元宝区', '中国,辽宁省,丹东市,元宝区', '元宝', '中国,辽宁,丹东,元宝', '3', '0415', '118000', 'Yuanbao', 'YB', 'Y', '124.39575', '40.13651'),
('537', '210603', '210600', '振兴区', '中国,辽宁省,丹东市,振兴区', '振兴', '中国,辽宁,丹东,振兴', '3', '0415', '118000', 'Zhenxing', 'ZX', 'Z', '124.36035', '40.10489'),
('538', '210604', '210600', '振安区', '中国,辽宁省,丹东市,振安区', '振安', '中国,辽宁,丹东,振安', '3', '0415', '118000', 'Zhen\'an', 'ZA', 'Z', '124.42816', '40.15826'),
('539', '210624', '210600', '宽甸满族自治县', '中国,辽宁省,丹东市,宽甸满族自治县', '宽甸', '中国,辽宁,丹东,宽甸', '3', '0415', '118200', 'Kuandian', 'KD', 'K', '124.78247', '40.73187'),
('540', '210681', '210600', '东港市', '中国,辽宁省,丹东市,东港市', '东港', '中国,辽宁,丹东,东港', '3', '0415', '118300', 'Donggang', 'DG', 'D', '124.16287', '39.86256'),
('541', '210682', '210600', '凤城市', '中国,辽宁省,丹东市,凤城市', '凤城', '中国,辽宁,丹东,凤城', '3', '0415', '118100', 'Fengcheng', 'FC', 'F', '124.06671', '40.45302'),
('542', '210700', '210000', '锦州市', '中国,辽宁省,锦州市', '锦州', '中国,辽宁,锦州', '2', '0416', '121000', 'Jinzhou', 'JZ', 'J', '121.135742', '41.119269'),
('543', '210702', '210700', '古塔区', '中国,辽宁省,锦州市,古塔区', '古塔', '中国,辽宁,锦州,古塔', '3', '0416', '121000', 'Guta', 'GT', 'G', '121.12832', '41.11725'),
('544', '210703', '210700', '凌河区', '中国,辽宁省,锦州市,凌河区', '凌河', '中国,辽宁,锦州,凌河', '3', '0416', '121000', 'Linghe', 'LH', 'L', '121.15089', '41.11496'),
('545', '210711', '210700', '太和区', '中国,辽宁省,锦州市,太和区', '太和', '中国,辽宁,锦州,太和', '3', '0416', '121000', 'Taihe', 'TH', 'T', '121.10354', '41.10929'),
('546', '210726', '210700', '黑山县', '中国,辽宁省,锦州市,黑山县', '黑山', '中国,辽宁,锦州,黑山', '3', '0416', '121400', 'Heishan', 'HS', 'H', '122.12081', '41.69417'),
('547', '210727', '210700', '义县', '中国,辽宁省,锦州市,义县', '义县', '中国,辽宁,锦州,义县', '3', '0416', '121100', 'Yixian', 'YX', 'Y', '121.24035', '41.53458'),
('548', '210781', '210700', '凌海市', '中国,辽宁省,锦州市,凌海市', '凌海', '中国,辽宁,锦州,凌海', '3', '0416', '121200', 'Linghai', 'LH', 'L', '121.35705', '41.1737'),
('549', '210782', '210700', '北镇市', '中国,辽宁省,锦州市,北镇市', '北镇', '中国,辽宁,锦州,北镇', '3', '0416', '121300', 'Beizhen', 'BZ', 'B', '121.79858', '41.59537'),
('550', '210783', '210700', '松山新区', '中国,辽宁省,锦州市,松山新区', '松山新区', '中国,辽宁,锦州,松山新区', '3', '0416', '121221', 'Songshan', 'SS', 'S', '121.128876', '41.08964'),
('551', '210784', '210700', '龙栖湾新区', '中国,辽宁省,锦州市,龙栖湾新区', '龙栖湾新区', '中国,辽宁,锦州,龙栖湾新区', '3', '0416', '121007', 'Longxiwan', 'LXW', 'L', '121.212807', '40.950883'),
('552', '210785', '210700', '经济技术开发区', '中国,辽宁省,锦州市,经济技术开发区', '经济技术开发区', '中国,辽宁,锦州,经济技术开发区', '3', '0416', '121007', 'Jingjikaifaqu', 'JJ', 'J', '121.065822', '40.843928'),
('553', '210800', '210000', '营口市', '中国,辽宁省,营口市', '营口', '中国,辽宁,营口', '2', '0417', '115000', 'Yingkou', 'YK', 'Y', '122.235151', '40.667432'),
('554', '210802', '210800', '站前区', '中国,辽宁省,营口市,站前区', '站前', '中国,辽宁,营口,站前', '3', '0417', '115000', 'Zhanqian', 'ZQ', 'Z', '122.25896', '40.67266'),
('555', '210803', '210800', '西市区', '中国,辽宁省,营口市,西市区', '西市', '中国,辽宁,营口,西市', '3', '0417', '115000', 'Xishi', 'XS', 'X', '122.20641', '40.6664'),
('556', '210804', '210800', '鲅鱼圈区', '中国,辽宁省,营口市,鲅鱼圈区', '鲅鱼圈', '中国,辽宁,营口,鲅鱼圈', '3', '0417', '115000', 'Bayuquan', 'BYQ', 'B', '122.13266', '40.26865'),
('557', '210811', '210800', '老边区', '中国,辽宁省,营口市,老边区', '老边', '中国,辽宁,营口,老边', '3', '0417', '115000', 'Laobian', 'LB', 'L', '122.37996', '40.6803'),
('558', '210881', '210800', '盖州市', '中国,辽宁省,营口市,盖州市', '盖州', '中国,辽宁,营口,盖州', '3', '0417', '115200', 'Gaizhou', 'GZ', 'G', '122.35464', '40.40446'),
('559', '210882', '210800', '大石桥市', '中国,辽宁省,营口市,大石桥市', '大石桥', '中国,辽宁,营口,大石桥', '3', '0417', '115100', 'Dashiqiao', 'DSQ', 'D', '122.50927', '40.64567'),
('560', '210900', '210000', '阜新市', '中国,辽宁省,阜新市', '阜新', '中国,辽宁,阜新', '2', '0418', '123000', 'Fuxin', 'FX', 'F', '121.648962', '42.011796'),
('561', '210902', '210900', '海州区', '中国,辽宁省,阜新市,海州区', '海州', '中国,辽宁,阜新,海州', '3', '0418', '123000', 'Haizhou', 'HZ', 'H', '121.65626', '42.01336'),
('562', '210903', '210900', '新邱区', '中国,辽宁省,阜新市,新邱区', '新邱', '中国,辽宁,阜新,新邱', '3', '0418', '123000', 'Xinqiu', 'XQ', 'X', '121.79251', '42.09181'),
('563', '210904', '210900', '太平区', '中国,辽宁省,阜新市,太平区', '太平', '中国,辽宁,阜新,太平', '3', '0418', '123000', 'Taiping', 'TP', 'T', '121.67865', '42.01065'),
('564', '210905', '210900', '清河门区', '中国,辽宁省,阜新市,清河门区', '清河门', '中国,辽宁,阜新,清河门', '3', '0418', '123000', 'Qinghemen', 'QHM', 'Q', '121.4161', '41.78309'),
('565', '210911', '210900', '细河区', '中国,辽宁省,阜新市,细河区', '细河', '中国,辽宁,阜新,细河', '3', '0418', '123000', 'Xihe', 'XH', 'X', '121.68013', '42.02533'),
('566', '210921', '210900', '阜新蒙古族自治县', '中国,辽宁省,阜新市,阜新蒙古族自治县', '阜新', '中国,辽宁,阜新,阜新', '3', '0418', '123100', 'Fuxin', 'FX', 'F', '121.75787', '42.0651'),
('567', '210922', '210900', '彰武县', '中国,辽宁省,阜新市,彰武县', '彰武', '中国,辽宁,阜新,彰武', '3', '0418', '123200', 'Zhangwu', 'ZW', 'Z', '122.54022', '42.38625'),
('568', '211000', '210000', '辽阳市', '中国,辽宁省,辽阳市', '辽阳', '中国,辽宁,辽阳', '2', '0419', '111000', 'Liaoyang', 'LY', 'L', '123.18152', '41.269402'),
('569', '211002', '211000', '白塔区', '中国,辽宁省,辽阳市,白塔区', '白塔', '中国,辽宁,辽阳,白塔', '3', '0419', '111000', 'Baita', 'BT', 'B', '123.1747', '41.27025'),
('570', '211003', '211000', '文圣区', '中国,辽宁省,辽阳市,文圣区', '文圣', '中国,辽宁,辽阳,文圣', '3', '0419', '111000', 'Wensheng', 'WS', 'W', '123.18521', '41.26267'),
('571', '211004', '211000', '宏伟区', '中国,辽宁省,辽阳市,宏伟区', '宏伟', '中国,辽宁,辽阳,宏伟', '3', '0419', '111000', 'Hongwei', 'HW', 'H', '123.1929', '41.21852'),
('572', '211005', '211000', '弓长岭区', '中国,辽宁省,辽阳市,弓长岭区', '弓长岭', '中国,辽宁,辽阳,弓长岭', '3', '0419', '111000', 'Gongchangling', 'GCL', 'G', '123.41963', '41.15181'),
('573', '211011', '211000', '太子河区', '中国,辽宁省,辽阳市,太子河区', '太子河', '中国,辽宁,辽阳,太子河', '3', '0419', '111000', 'Taizihe', 'TZH', 'T', '123.18182', '41.25337'),
('574', '211021', '211000', '辽阳县', '中国,辽宁省,辽阳市,辽阳县', '辽阳', '中国,辽宁,辽阳,辽阳', '3', '0419', '111200', 'Liaoyang', 'LY', 'L', '123.10574', '41.20542'),
('575', '211081', '211000', '灯塔市', '中国,辽宁省,辽阳市,灯塔市', '灯塔', '中国,辽宁,辽阳,灯塔', '3', '0419', '111300', 'Dengta', 'DT', 'D', '123.33926', '41.42612'),
('576', '211100', '210000', '盘锦市', '中国,辽宁省,盘锦市', '盘锦', '中国,辽宁,盘锦', '2', '0427', '124000', 'Panjin', 'PJ', 'P', '122.06957', '41.124484'),
('577', '211102', '211100', '双台子区', '中国,辽宁省,盘锦市,双台子区', '双台子', '中国,辽宁,盘锦,双台子', '3', '0427', '124000', 'Shuangtaizi', 'STZ', 'S', '122.06011', '41.1906'),
('578', '211103', '211100', '兴隆台区', '中国,辽宁省,盘锦市,兴隆台区', '兴隆台', '中国,辽宁,盘锦,兴隆台', '3', '0427', '124000', 'Xinglongtai', 'XLT', 'X', '122.07529', '41.12402'),
('579', '211104', '211100', '大洼区', '中国,辽宁省,盘锦市,大洼区', '大洼', '中国,辽宁,盘锦,大洼', '3', '0427', '124200', 'Dawa', 'DW', 'D', '122.08239', '41.00244'),
('580', '211122', '211100', '盘山县', '中国,辽宁省,盘锦市,盘山县', '盘山', '中国,辽宁,盘锦,盘山', '3', '0427', '124100', 'Panshan', 'PS', 'P', '121.99777', '41.23805'),
('581', '211200', '210000', '铁岭市', '中国,辽宁省,铁岭市', '铁岭', '中国,辽宁,铁岭', '2', '024', '112000', 'Tieling', 'TL', 'T', '123.844279', '42.290585'),
('582', '211202', '211200', '银州区', '中国,辽宁省,铁岭市,银州区', '银州', '中国,辽宁,铁岭,银州', '3', '024', '112000', 'Yinzhou', 'YZ', 'Y', '123.8573', '42.29507'),
('583', '211204', '211200', '清河区', '中国,辽宁省,铁岭市,清河区', '清河', '中国,辽宁,铁岭,清河', '3', '024', '112000', 'Qinghe', 'QH', 'Q', '124.15911', '42.54679'),
('584', '211221', '211200', '铁岭县', '中国,辽宁省,铁岭市,铁岭县', '铁岭', '中国,辽宁,铁岭,铁岭', '3', '024', '112600', 'Tieling', 'TL', 'T', '123.77325', '42.22498'),
('585', '211223', '211200', '西丰县', '中国,辽宁省,铁岭市,西丰县', '西丰', '中国,辽宁,铁岭,西丰', '3', '024', '112400', 'Xifeng', 'XF', 'X', '124.7304', '42.73756'),
('586', '211224', '211200', '昌图县', '中国,辽宁省,铁岭市,昌图县', '昌图', '中国,辽宁,铁岭,昌图', '3', '024', '112500', 'Changtu', 'CT', 'C', '124.11206', '42.78428'),
('587', '211281', '211200', '调兵山市', '中国,辽宁省,铁岭市,调兵山市', '调兵山', '中国,辽宁,铁岭,调兵山', '3', '024', '112700', 'Diaobingshan', 'DBS', 'D', '123.56689', '42.4675'),
('588', '211282', '211200', '开原市', '中国,辽宁省,铁岭市,开原市', '开原', '中国,辽宁,铁岭,开原', '3', '024', '112300', 'Kaiyuan', 'KY', 'K', '124.03945', '42.54585'),
('589', '211300', '210000', '朝阳市', '中国,辽宁省,朝阳市', '朝阳', '中国,辽宁,朝阳', '2', '0421', '122000', 'Chaoyang', 'CY', 'C', '120.451176', '41.576758'),
('590', '211302', '211300', '双塔区', '中国,辽宁省,朝阳市,双塔区', '双塔', '中国,辽宁,朝阳,双塔', '3', '0421', '122000', 'Shuangta', 'ST', 'S', '120.45385', '41.566'),
('591', '211303', '211300', '龙城区', '中国,辽宁省,朝阳市,龙城区', '龙城', '中国,辽宁,朝阳,龙城', '3', '0421', '122000', 'Longcheng', 'LC', 'L', '120.43719', '41.59264'),
('592', '211321', '211300', '朝阳县', '中国,辽宁省,朝阳市,朝阳县', '朝阳', '中国,辽宁,朝阳,朝阳', '3', '0421', '122000', 'Chaoyang', 'CY', 'C', '120.17401', '41.4324'),
('593', '211322', '211300', '建平县', '中国,辽宁省,朝阳市,建平县', '建平', '中国,辽宁,朝阳,建平', '3', '0421', '122400', 'Jianping', 'JP', 'J', '119.64392', '41.40315'),
('594', '211324', '211300', '喀喇沁左翼蒙古族自治县', '中国,辽宁省,朝阳市,喀喇沁左翼蒙古族自治县', '喀喇沁左翼', '中国,辽宁,朝阳,喀喇沁左翼', '3', '0421', '122300', 'Kalaqinzuoyi', 'KLQZY', 'K', '119.74185', '41.12801'),
('595', '211381', '211300', '北票市', '中国,辽宁省,朝阳市,北票市', '北票', '中国,辽宁,朝阳,北票', '3', '0421', '122100', 'Beipiao', 'BP', 'B', '120.76977', '41.80196'),
('596', '211382', '211300', '凌源市', '中国,辽宁省,朝阳市,凌源市', '凌源', '中国,辽宁,朝阳,凌源', '3', '0421', '122500', 'Lingyuan', 'LY', 'L', '119.40148', '41.24558'),
('597', '211400', '210000', '葫芦岛市', '中国,辽宁省,葫芦岛市', '葫芦岛', '中国,辽宁,葫芦岛', '2', '0429', '125000', 'Huludao', 'HLD', 'H', '120.856394', '40.755572'),
('598', '211402', '211400', '连山区', '中国,辽宁省,葫芦岛市,连山区', '连山', '中国,辽宁,葫芦岛,连山', '3', '0429', '125000', 'Lianshan', 'LS', 'L', '120.86393', '40.75554'),
('599', '211403', '211400', '龙港区', '中国,辽宁省,葫芦岛市,龙港区', '龙港', '中国,辽宁,葫芦岛,龙港', '3', '0429', '125000', 'Longgang', 'LG', 'L', '120.94866', '40.71919'),
('600', '211404', '211400', '南票区', '中国,辽宁省,葫芦岛市,南票区', '南票', '中国,辽宁,葫芦岛,南票', '3', '0429', '125000', 'Nanpiao', 'NP', 'N', '120.74978', '41.10707'),
('601', '211421', '211400', '绥中县', '中国,辽宁省,葫芦岛市,绥中县', '绥中', '中国,辽宁,葫芦岛,绥中', '3', '0429', '125200', 'Suizhong', 'SZ', 'S', '120.34451', '40.32552'),
('602', '211422', '211400', '建昌县', '中国,辽宁省,葫芦岛市,建昌县', '建昌', '中国,辽宁,葫芦岛,建昌', '3', '0429', '125300', 'Jianchang', 'JC', 'J', '119.8377', '40.82448'),
('603', '211481', '211400', '兴城市', '中国,辽宁省,葫芦岛市,兴城市', '兴城', '中国,辽宁,葫芦岛,兴城', '3', '0429', '125100', 'Xingcheng', 'XC', 'X', '120.72537', '40.61492'),
('604', '220000', '100000', '吉林省', '中国,吉林省', '吉林', '中国,吉林', '1', '', '', 'Jilin', 'JL', 'J', '125.3245', '43.886841'),
('605', '220100', '220000', '长春市', '中国,吉林省,长春市', '长春', '中国,吉林,长春', '2', '0431', '130000', 'Changchun', 'CC', 'C', '125.3245', '43.886841'),
('606', '220102', '220100', '南关区', '中国,吉林省,长春市,南关区', '南关', '中国,吉林,长春,南关', '3', '0431', '130000', 'Nanguan', 'NG', 'N', '125.35035', '43.86401'),
('607', '220103', '220100', '宽城区', '中国,吉林省,长春市,宽城区', '宽城', '中国,吉林,长春,宽城', '3', '0431', '130000', 'Kuancheng', 'KC', 'K', '125.32635', '43.90182'),
('608', '220104', '220100', '朝阳区', '中国,吉林省,长春市,朝阳区', '朝阳', '中国,吉林,长春,朝阳', '3', '0431', '130000', 'Chaoyang', 'CY', 'C', '125.2883', '43.83339'),
('609', '220105', '220100', '二道区', '中国,吉林省,长春市,二道区', '二道', '中国,吉林,长春,二道', '3', '0431', '130000', 'Erdao', 'ED', 'E', '125.37429', '43.86501'),
('610', '220106', '220100', '绿园区', '中国,吉林省,长春市,绿园区', '绿园', '中国,吉林,长春,绿园', '3', '0431', '130000', 'Lvyuan', 'LY', 'L', '125.25582', '43.88045'),
('611', '220112', '220100', '双阳区', '中国,吉林省,长春市,双阳区', '双阳', '中国,吉林,长春,双阳', '3', '0431', '130600', 'Shuangyang', 'SY', 'S', '125.65631', '43.52803'),
('612', '220113', '220100', '九台区', '中国,吉林省,长春市,九台区', '九台', '中国,吉林,长春,九台', '3', '0431', '130500', 'Jiutai', 'JT', 'J', '125.8395', '44.15163'),
('613', '220122', '220100', '农安县', '中国,吉林省,长春市,农安县', '农安', '中国,吉林,长春,农安', '3', '0431', '130200', 'Nong\'an', 'NA', 'N', '125.18481', '44.43265'),
('614', '220182', '220100', '榆树市', '中国,吉林省,长春市,榆树市', '榆树', '中国,吉林,长春,榆树', '3', '0431', '130400', 'Yushu', 'YS', 'Y', '126.55688', '44.82523'),
('615', '220183', '220100', '德惠市', '中国,吉林省,长春市,德惠市', '德惠', '中国,吉林,长春,德惠', '3', '0431', '130300', 'Dehui', 'DH', 'D', '125.70538', '44.53719'),
('616', '220184', '220100', '长春新区', '中国,吉林省,长春市,长春新区', '长春新区', '中国,吉林,长春,长春新区', '3', '0431', '130000', 'ChangChunXinQu', 'CCXQ', 'C', '125.338898', '43.870426'),
('617', '220185', '220100', '高新技术产业开发区', '中国,吉林省,长春市,高新技术产业开发区', '高新区', '中国,吉林,长春,高新区', '3', '0431', '130000', 'Gaoxinqu', 'GXQ', 'G', '125.256516', '43.820958'),
('618', '220186', '220100', '经济技术开发区', '中国,吉林省,长春市,经济技术开发区', '经济技术开发区', '中国,吉林,长春,经济技术开发区', '3', '0431', '130000', 'Jingjikaifaqu', 'JJ', 'J', '125.41748', '43.86745'),
('619', '220187', '220100', '汽车产业开发区', '中国,吉林省,长春市,汽车产业开发区', '汽车产业开发区', '中国,吉林,长春,汽车产业开发区', '3', '0431', '130000', 'Qichechanyekaifaqu', 'QC', 'Q', '125.226804', '43.846775'),
('620', '220188', '220100', '兴隆综合保税区', '中国,吉林省,长春市,兴隆综合保税区', '兴隆综合保税区', '中国,吉林,长春,兴隆综合保税区', '3', '0431', '130000', 'Baoshuiqu', 'BS', 'B', '125.484296', '43.954453'),
('621', '220200', '220000', '吉林市', '中国,吉林省,吉林市', '吉林', '中国,吉林,吉林', '2', '0432', '132000', 'Jilin', 'JL', 'J', '126.55302', '43.843577'),
('622', '220202', '220200', '昌邑区', '中国,吉林省,吉林市,昌邑区', '昌邑', '中国,吉林,吉林,昌邑', '3', '0432', '132000', 'Changyi', 'CY', 'C', '126.57424', '43.88183'),
('623', '220203', '220200', '龙潭区', '中国,吉林省,吉林市,龙潭区', '龙潭', '中国,吉林,吉林,龙潭', '3', '0432', '132000', 'Longtan', 'LT', 'L', '126.56213', '43.91054'),
('624', '220204', '220200', '船营区', '中国,吉林省,吉林市,船营区', '船营', '中国,吉林,吉林,船营', '3', '0432', '132000', 'Chuanying', 'CY', 'C', '126.54096', '43.83344'),
('625', '220211', '220200', '丰满区', '中国,吉林省,吉林市,丰满区', '丰满', '中国,吉林,吉林,丰满', '3', '0432', '132000', 'Fengman', 'FM', 'F', '126.56237', '43.82236'),
('626', '220221', '220200', '永吉县', '中国,吉林省,吉林市,永吉县', '永吉', '中国,吉林,吉林,永吉', '3', '0432', '132100', 'Yongji', 'YJ', 'Y', '126.4963', '43.67197'),
('627', '220281', '220200', '蛟河市', '中国,吉林省,吉林市,蛟河市', '蛟河', '中国,吉林,吉林,蛟河', '3', '0432', '132500', 'Jiaohe', 'JH', 'J', '127.34426', '43.72696'),
('628', '220282', '220200', '桦甸市', '中国,吉林省,吉林市,桦甸市', '桦甸', '中国,吉林,吉林,桦甸', '3', '0432', '132400', 'Huadian', 'HD', 'H', '126.74624', '42.97206'),
('629', '220283', '220200', '舒兰市', '中国,吉林省,吉林市,舒兰市', '舒兰', '中国,吉林,吉林,舒兰', '3', '0432', '132600', 'Shulan', 'SL', 'S', '126.9653', '44.40582'),
('630', '220284', '220200', '磐石市', '中国,吉林省,吉林市,磐石市', '磐石', '中国,吉林,吉林,磐石', '3', '0432', '132300', 'Panshi', 'PS', 'P', '126.0625', '42.94628'),
('631', '220285', '220200', '高新区', '中国,吉林省,吉林市,高新区', '高新区', '中国,吉林,吉林,高新区', '3', '0432', '132000', 'Gaoxinqu', 'GXQ', 'G', '126.546936', '43.824666'),
('632', '220300', '220000', '四平市', '中国,吉林省,四平市', '四平', '中国,吉林,四平', '2', '0434', '136000', 'Siping', 'SP', 'S', '124.370785', '43.170344'),
('633', '220302', '220300', '铁西区', '中国,吉林省,四平市,铁西区', '铁西', '中国,吉林,四平,铁西', '3', '0434', '136000', 'Tiexi', 'TX', 'T', '124.37369', '43.17456'),
('634', '220303', '220300', '铁东区', '中国,吉林省,四平市,铁东区', '铁东', '中国,吉林,四平,铁东', '3', '0434', '136000', 'Tiedong', 'TD', 'T', '124.40976', '43.16241'),
('635', '220322', '220300', '梨树县', '中国,吉林省,四平市,梨树县', '梨树', '中国,吉林,四平,梨树', '3', '0434', '136500', 'Lishu', 'LS', 'L', '124.33563', '43.30717'),
('636', '220323', '220300', '伊通满族自治县', '中国,吉林省,四平市,伊通满族自治县', '伊通', '中国,吉林,四平,伊通', '3', '0434', '130700', 'Yitong', 'YT', 'Y', '125.30596', '43.34434'),
('637', '220381', '220300', '公主岭市', '中国,吉林省,四平市,公主岭市', '公主岭', '中国,吉林,四平,公主岭', '3', '0434', '136100', 'Gongzhuling', 'GZL', 'G', '124.82266', '43.50453'),
('638', '220382', '220300', '双辽市', '中国,吉林省,四平市,双辽市', '双辽', '中国,吉林,四平,双辽', '3', '0434', '136400', 'Shuangliao', 'SL', 'S', '123.50106', '43.52099'),
('639', '220400', '220000', '辽源市', '中国,吉林省,辽源市', '辽源', '中国,吉林,辽源', '2', '0437', '136200', 'Liaoyuan', 'LY', 'L', '125.145349', '42.902692'),
('640', '220402', '220400', '龙山区', '中国,吉林省,辽源市,龙山区', '龙山', '中国,吉林,辽源,龙山', '3', '0437', '136200', 'Longshan', 'LS', 'L', '125.13641', '42.89714'),
('641', '220403', '220400', '西安区', '中国,吉林省,辽源市,西安区', '西安', '中国,吉林,辽源,西安', '3', '0437', '136200', 'Xi\'an', 'XA', 'X', '125.14904', '42.927'),
('642', '220421', '220400', '东丰县', '中国,吉林省,辽源市,东丰县', '东丰', '中国,吉林,辽源,东丰', '3', '0437', '136300', 'Dongfeng', 'DF', 'D', '125.53244', '42.6783'),
('643', '220422', '220400', '东辽县', '中国,吉林省,辽源市,东辽县', '东辽', '中国,吉林,辽源,东辽', '3', '0437', '136600', 'Dongliao', 'DL', 'D', '124.98596', '42.92492'),
('644', '220500', '220000', '通化市', '中国,吉林省,通化市', '通化', '中国,吉林,通化', '2', '0435', '134000', 'Tonghua', 'TH', 'T', '125.936501', '41.721177'),
('645', '220502', '220500', '东昌区', '中国,吉林省,通化市,东昌区', '东昌', '中国,吉林,通化,东昌', '3', '0435', '134000', 'Dongchang', 'DC', 'D', '125.9551', '41.72849'),
('646', '220503', '220500', '二道江区', '中国,吉林省,通化市,二道江区', '二道江', '中国,吉林,通化,二道江', '3', '0435', '134000', 'Erdaojiang', 'EDJ', 'E', '126.04257', '41.7741'),
('647', '220521', '220500', '通化县', '中国,吉林省,通化市,通化县', '通化', '中国,吉林,通化,通化', '3', '0435', '134100', 'Tonghua', 'TH', 'T', '125.75936', '41.67928'),
('648', '220523', '220500', '辉南县', '中国,吉林省,通化市,辉南县', '辉南', '中国,吉林,通化,辉南', '3', '0435', '135100', 'Huinan', 'HN', 'H', '126.04684', '42.68497'),
('649', '220524', '220500', '柳河县', '中国,吉林省,通化市,柳河县', '柳河', '中国,吉林,通化,柳河', '3', '0435', '135300', 'Liuhe', 'LH', 'L', '125.74475', '42.28468'),
('650', '220581', '220500', '梅河口市', '中国,吉林省,通化市,梅河口市', '梅河口', '中国,吉林,通化,梅河口', '3', '0435', '135000', 'Meihekou', 'MHK', 'M', '125.71041', '42.53828'),
('651', '220582', '220500', '集安市', '中国,吉林省,通化市,集安市', '集安', '中国,吉林,通化,集安', '3', '0435', '134200', 'Ji\'an', 'JA', 'J', '126.18829', '41.12268'),
('652', '220600', '220000', '白山市', '中国,吉林省,白山市', '白山', '中国,吉林,白山', '2', '0439', '134300', 'Baishan', 'BS', 'B', '126.427839', '41.942505'),
('653', '220602', '220600', '浑江区', '中国,吉林省,白山市,浑江区', '浑江', '中国,吉林,白山,浑江', '3', '0439', '134300', 'Hunjiang', 'HJ', 'H', '126.422342', '41.945656'),
('654', '220605', '220600', '江源区', '中国,吉林省,白山市,江源区', '江源', '中国,吉林,白山,江源', '3', '0439', '134700', 'Jiangyuan', 'JY', 'J', '126.59079', '42.05664'),
('655', '220621', '220600', '抚松县', '中国,吉林省,白山市,抚松县', '抚松', '中国,吉林,白山,抚松', '3', '0439', '134500', 'Fusong', 'FS', 'F', '127.2803', '42.34198'),
('656', '220622', '220600', '靖宇县', '中国,吉林省,白山市,靖宇县', '靖宇', '中国,吉林,白山,靖宇', '3', '0439', '135200', 'Jingyu', 'JY', 'J', '126.81308', '42.38863'),
('657', '220623', '220600', '长白朝鲜族自治县', '中国,吉林省,白山市,长白朝鲜族自治县', '长白', '中国,吉林,白山,长白', '3', '0439', '134400', 'Changbai', 'CB', 'C', '128.20047', '41.41996'),
('658', '220681', '220600', '临江市', '中国,吉林省,白山市,临江市', '临江', '中国,吉林,白山,临江', '3', '0439', '134600', 'Linjiang', 'LJ', 'L', '126.91751', '41.81142'),
('659', '220700', '220000', '松原市', '中国,吉林省,松原市', '松原', '中国,吉林,松原', '2', '0438', '138000', 'Songyuan', 'SY', 'S', '124.823608', '45.118243'),
('660', '220702', '220700', '宁江区', '中国,吉林省,松原市,宁江区', '宁江', '中国,吉林,松原,宁江', '3', '0438', '138000', 'Ningjiang', 'NJ', 'N', '124.81689', '45.17175'),
('661', '220721', '220700', '前郭尔罗斯蒙古族自治县', '中国,吉林省,松原市,前郭尔罗斯蒙古族自治县', '前郭尔罗斯', '中国,吉林,松原,前郭尔罗斯', '3', '0438', '131100', 'Qianguoerluosi', 'QGELS', 'Q', '124.82351', '45.11726'),
('662', '220722', '220700', '长岭县', '中国,吉林省,松原市,长岭县', '长岭', '中国,吉林,松原,长岭', '3', '0438', '131500', 'Changling', 'CL', 'C', '123.96725', '44.27581'),
('663', '220723', '220700', '乾安县', '中国,吉林省,松原市,乾安县', '乾安', '中国,吉林,松原,乾安', '3', '0438', '131400', 'Qian\'an', 'QA', 'Q', '124.02737', '45.01068'),
('664', '220781', '220700', '扶余市', '中国,吉林省,松原市,扶余市', '扶余', '中国,吉林,松原,扶余', '3', '0438', '131200', 'Fuyu', 'FY', 'F', '126.042758', '44.986199'),
('665', '220800', '220000', '白城市', '中国,吉林省,白城市', '白城', '中国,吉林,白城', '2', '0436', '137000', 'Baicheng', 'BC', 'B', '122.841114', '45.619026'),
('666', '220802', '220800', '洮北区', '中国,吉林省,白城市,洮北区', '洮北', '中国,吉林,白城,洮北', '3', '0436', '137000', 'Taobei', 'TB', 'T', '122.85104', '45.62167'),
('667', '220821', '220800', '镇赉县', '中国,吉林省,白城市,镇赉县', '镇赉', '中国,吉林,白城,镇赉', '3', '0436', '137300', 'Zhenlai', 'ZL', 'Z', '123.19924', '45.84779'),
('668', '220822', '220800', '通榆县', '中国,吉林省,白城市,通榆县', '通榆', '中国,吉林,白城,通榆', '3', '0436', '137200', 'Tongyu', 'TY', 'T', '123.08761', '44.81388'),
('669', '220881', '220800', '洮南市', '中国,吉林省,白城市,洮南市', '洮南', '中国,吉林,白城,洮南', '3', '0436', '137100', 'Taonan', 'TN', 'T', '122.78772', '45.33502'),
('670', '220882', '220800', '大安市', '中国,吉林省,白城市,大安市', '大安', '中国,吉林,白城,大安', '3', '0436', '131300', 'Da\'an', 'DA', 'D', '124.29519', '45.50846'),
('671', '222400', '220000', '延边朝鲜族自治州', '中国,吉林省,延边朝鲜族自治州', '延边', '中国,吉林,延边', '2', '0433', '133000', 'Yanbian', 'YB', 'Y', '129.513228', '42.904823'),
('672', '222401', '222400', '延吉市', '中国,吉林省,延边朝鲜族自治州,延吉市', '延吉', '中国,吉林,延边,延吉', '3', '0433', '133000', 'Yanji', 'YJ', 'Y', '129.51357', '42.90682'),
('673', '222402', '222400', '图们市', '中国,吉林省,延边朝鲜族自治州,图们市', '图们', '中国,吉林,延边,图们', '3', '0433', '133100', 'Tumen', 'TM', 'T', '129.84381', '42.96801'),
('674', '222403', '222400', '敦化市', '中国,吉林省,延边朝鲜族自治州,敦化市', '敦化', '中国,吉林,延边,敦化', '3', '0433', '133700', 'Dunhua', 'DH', 'D', '128.23242', '43.37304'),
('675', '222404', '222400', '珲春市', '中国,吉林省,延边朝鲜族自治州,珲春市', '珲春', '中国,吉林,延边,珲春', '3', '0433', '133300', 'Hunchun', 'HCH', 'H', '130.36572', '42.86242'),
('676', '222405', '222400', '龙井市', '中国,吉林省,延边朝鲜族自治州,龙井市', '龙井', '中国,吉林,延边,龙井', '3', '0433', '133400', 'Longjing', 'LJ', 'L', '129.42584', '42.76804'),
('677', '222406', '222400', '和龙市', '中国,吉林省,延边朝鲜族自治州,和龙市', '和龙', '中国,吉林,延边,和龙', '3', '0433', '133500', 'Helong', 'HL', 'H', '129.01077', '42.5464'),
('678', '222424', '222400', '汪清县', '中国,吉林省,延边朝鲜族自治州,汪清县', '汪清', '中国,吉林,延边,汪清', '3', '0433', '133200', 'Wangqing', 'WQ', 'W', '129.77121', '43.31278'),
('679', '222426', '222400', '安图县', '中国,吉林省,延边朝鲜族自治州,安图县', '安图', '中国,吉林,延边,安图', '3', '0433', '133600', 'Antu', 'AT', 'A', '128.90625', '43.11533'),
('680', '230000', '100000', '黑龙江省', '中国,黑龙江省', '黑龙江', '中国,黑龙江', '1', '', '', 'Heilongjiang', 'HL', 'H', '126.642464', '45.756967'),
('681', '230100', '230000', '哈尔滨市', '中国,黑龙江省,哈尔滨市', '哈尔滨', '中国,黑龙江,哈尔滨', '2', '0451', '150000', 'Harbin', 'HEB', 'H', '126.642464', '45.756967'),
('682', '230102', '230100', '道里区', '中国,黑龙江省,哈尔滨市,道里区', '道里', '中国,黑龙江,哈尔滨,道里', '3', '0451', '150000', 'Daoli', 'DL', 'D', '126.61705', '45.75586'),
('683', '230103', '230100', '南岗区', '中国,黑龙江省,哈尔滨市,南岗区', '南岗', '中国,黑龙江,哈尔滨,南岗', '3', '0451', '150000', 'Nangang', 'NG', 'N', '126.66854', '45.75996'),
('684', '230104', '230100', '道外区', '中国,黑龙江省,哈尔滨市,道外区', '道外', '中国,黑龙江,哈尔滨,道外', '3', '0451', '150000', 'Daowai', 'DW', 'D', '126.64938', '45.79187'),
('685', '230108', '230100', '平房区', '中国,黑龙江省,哈尔滨市,平房区', '平房', '中国,黑龙江,哈尔滨,平房', '3', '0451', '150000', 'Pingfang', 'PF', 'P', '126.63729', '45.59777'),
('686', '230109', '230100', '松北区', '中国,黑龙江省,哈尔滨市,松北区', '松北', '中国,黑龙江,哈尔滨,松北', '3', '0451', '150000', 'Songbei', 'SB', 'S', '126.56276', '45.80831'),
('687', '230110', '230100', '香坊区', '中国,黑龙江省,哈尔滨市,香坊区', '香坊', '中国,黑龙江,哈尔滨,香坊', '3', '0451', '150000', 'Xiangfang', 'XF', 'X', '126.67968', '45.72383'),
('688', '230111', '230100', '呼兰区', '中国,黑龙江省,哈尔滨市,呼兰区', '呼兰', '中国,黑龙江,哈尔滨,呼兰', '3', '0451', '150500', 'Hulan', 'HL', 'H', '126.58792', '45.88895'),
('689', '230112', '230100', '阿城区', '中国,黑龙江省,哈尔滨市,阿城区', '阿城', '中国,黑龙江,哈尔滨,阿城', '3', '0451', '150000', 'A\'cheng', 'AC', 'A', '126.97525', '45.54144'),
('690', '230113', '230100', '双城区', '中国,黑龙江省,哈尔滨市,双城区', '双城', '中国,黑龙江,哈尔滨,双城', '3', '0451', '150100', 'Shuangcheng', 'SC', 'S', '126.308784', '45.377942'),
('691', '230123', '230100', '依兰县', '中国,黑龙江省,哈尔滨市,依兰县', '依兰', '中国,黑龙江,哈尔滨,依兰', '3', '0451', '154800', 'Yilan', 'YL', 'Y', '129.56817', '46.3247'),
('692', '230124', '230100', '方正县', '中国,黑龙江省,哈尔滨市,方正县', '方正', '中国,黑龙江,哈尔滨,方正', '3', '0451', '150800', 'Fangzheng', 'FZ', 'F', '128.82952', '45.85162'),
('693', '230125', '230100', '宾县', '中国,黑龙江省,哈尔滨市,宾县', '宾县', '中国,黑龙江,哈尔滨,宾县', '3', '0451', '150400', 'Binxian', 'BX', 'B', '127.48675', '45.75504'),
('694', '230126', '230100', '巴彦县', '中国,黑龙江省,哈尔滨市,巴彦县', '巴彦', '中国,黑龙江,哈尔滨,巴彦', '3', '0451', '151800', 'Bayan', 'BY', 'B', '127.40799', '46.08148'),
('695', '230127', '230100', '木兰县', '中国,黑龙江省,哈尔滨市,木兰县', '木兰', '中国,黑龙江,哈尔滨,木兰', '3', '0451', '151900', 'Mulan', 'ML', 'M', '128.0448', '45.94944'),
('696', '230128', '230100', '通河县', '中国,黑龙江省,哈尔滨市,通河县', '通河', '中国,黑龙江,哈尔滨,通河', '3', '0451', '150900', 'Tonghe', 'TH', 'T', '128.74603', '45.99007'),
('697', '230129', '230100', '延寿县', '中国,黑龙江省,哈尔滨市,延寿县', '延寿', '中国,黑龙江,哈尔滨,延寿', '3', '0451', '150700', 'Yanshou', 'YS', 'Y', '128.33419', '45.4554'),
('698', '230183', '230100', '尚志市', '中国,黑龙江省,哈尔滨市,尚志市', '尚志', '中国,黑龙江,哈尔滨,尚志', '3', '0451', '150600', 'Shangzhi', 'SZ', 'S', '127.96191', '45.21736'),
('699', '230184', '230100', '五常市', '中国,黑龙江省,哈尔滨市,五常市', '五常', '中国,黑龙江,哈尔滨,五常', '3', '0451', '150200', 'Wuchang', 'WC', 'W', '127.16751', '44.93184'),
('700', '230185', '230100', '哈尔滨新区', '中国,黑龙江省,哈尔滨市,哈尔滨新区', '哈尔滨新区', '中国,黑龙江,哈尔滨,哈尔滨新区', '3', '0451', '150000', 'HarbinXinQu', 'HEBXQ', 'H', '126.642464', '45.756967'),
('701', '230186', '230100', '高新区', '中国,黑龙江省,哈尔滨市,高新区', '高新区', '中国,黑龙江,哈尔滨,高新区', '3', '0451', '150000', 'GaoXinQu', 'GXQ', 'G', '126.498275', '45.791498'),
('702', '230200', '230000', '齐齐哈尔市', '中国,黑龙江省,齐齐哈尔市', '齐齐哈尔', '中国,黑龙江,齐齐哈尔', '2', '0452', '161000', 'Qiqihar', 'QQHE', 'Q', '123.953486', '47.348079'),
('703', '230202', '230200', '龙沙区', '中国,黑龙江省,齐齐哈尔市,龙沙区', '龙沙', '中国,黑龙江,齐齐哈尔,龙沙', '3', '0452', '161000', 'Longsha', 'LS', 'L', '123.95752', '47.31776'),
('704', '230203', '230200', '建华区', '中国,黑龙江省,齐齐哈尔市,建华区', '建华', '中国,黑龙江,齐齐哈尔,建华', '3', '0452', '161000', 'Jianhua', 'JH', 'J', '124.0133', '47.36718'),
('705', '230204', '230200', '铁锋区', '中国,黑龙江省,齐齐哈尔市,铁锋区', '铁锋', '中国,黑龙江,齐齐哈尔,铁锋', '3', '0452', '161000', 'Tiefeng', 'TF', 'T', '123.97821', '47.34075'),
('706', '230205', '230200', '昂昂溪区', '中国,黑龙江省,齐齐哈尔市,昂昂溪区', '昂昂溪', '中国,黑龙江,齐齐哈尔,昂昂溪', '3', '0452', '161000', 'Angangxi', 'AAX', 'A', '123.82229', '47.15513'),
('707', '230206', '230200', '富拉尔基区', '中国,黑龙江省,齐齐哈尔市,富拉尔基区', '富拉尔基', '中国,黑龙江,齐齐哈尔,富拉尔基', '3', '0452', '161000', 'Fulaerji', 'FLEJ', 'F', '123.62918', '47.20884'),
('708', '230207', '230200', '碾子山区', '中国,黑龙江省,齐齐哈尔市,碾子山区', '碾子山', '中国,黑龙江,齐齐哈尔,碾子山', '3', '0452', '161000', 'Nianzishan', 'NZS', 'N', '122.88183', '47.51662'),
('709', '230208', '230200', '梅里斯达斡尔族区', '中国,黑龙江省,齐齐哈尔市,梅里斯达斡尔族区', '梅里斯', '中国,黑龙江,齐齐哈尔,梅里斯', '3', '0452', '161000', 'Meilisi', 'MLS', 'M', '123.75274', '47.30946'),
('710', '230221', '230200', '龙江县', '中国,黑龙江省,齐齐哈尔市,龙江县', '龙江', '中国,黑龙江,齐齐哈尔,龙江', '3', '0452', '161100', 'Longjiang', 'LJ', 'L', '123.20532', '47.33868'),
('711', '230223', '230200', '依安县', '中国,黑龙江省,齐齐哈尔市,依安县', '依安', '中国,黑龙江,齐齐哈尔,依安', '3', '0452', '161500', 'Yi\'an', 'YA', 'Y', '125.30896', '47.8931'),
('712', '230224', '230200', '泰来县', '中国,黑龙江省,齐齐哈尔市,泰来县', '泰来', '中国,黑龙江,齐齐哈尔,泰来', '3', '0452', '162400', 'Tailai', 'TL', 'T', '123.42285', '46.39386'),
('713', '230225', '230200', '甘南县', '中国,黑龙江省,齐齐哈尔市,甘南县', '甘南', '中国,黑龙江,齐齐哈尔,甘南', '3', '0452', '162100', 'Gannan', 'GN', 'G', '123.50317', '47.92437'),
('714', '230227', '230200', '富裕县', '中国,黑龙江省,齐齐哈尔市,富裕县', '富裕', '中国,黑龙江,齐齐哈尔,富裕', '3', '0452', '161200', 'Fuyu', 'FY', 'F', '124.47457', '47.77431'),
('715', '230229', '230200', '克山县', '中国,黑龙江省,齐齐哈尔市,克山县', '克山', '中国,黑龙江,齐齐哈尔,克山', '3', '0452', '161600', 'Keshan', 'KS', 'K', '125.87396', '48.03265'),
('716', '230230', '230200', '克东县', '中国,黑龙江省,齐齐哈尔市,克东县', '克东', '中国,黑龙江,齐齐哈尔,克东', '3', '0452', '164800', 'Kedong', 'KD', 'K', '126.24917', '48.03828'),
('717', '230231', '230200', '拜泉县', '中国,黑龙江省,齐齐哈尔市,拜泉县', '拜泉', '中国,黑龙江,齐齐哈尔,拜泉', '3', '0452', '164700', 'Baiquan', 'BQ', 'B', '126.09167', '47.60817'),
('718', '230281', '230200', '讷河市', '中国,黑龙江省,齐齐哈尔市,讷河市', '讷河', '中国,黑龙江,齐齐哈尔,讷河', '3', '0452', '161300', 'Nehe', 'NH', 'N', '124.87713', '48.48388'),
('719', '230282', '230200', '高新区', '中国,黑龙江省,齐齐哈尔市,高新区', '高新区', '中国,黑龙江,齐齐哈尔,高新区', '3', '0452', '161000', 'GaoXinQu', 'GXQ', 'G', '123.9552', '47.308258'),
('720', '230300', '230000', '鸡西市', '中国,黑龙江省,鸡西市', '鸡西', '中国,黑龙江,鸡西', '2', '0467', '158100', 'Jixi', 'JX', 'J', '130.975966', '45.300046'),
('721', '230302', '230300', '鸡冠区', '中国,黑龙江省,鸡西市,鸡冠区', '鸡冠', '中国,黑龙江,鸡西,鸡冠', '3', '0467', '158100', 'Jiguan', 'JG', 'J', '130.98139', '45.30396'),
('722', '230303', '230300', '恒山区', '中国,黑龙江省,鸡西市,恒山区', '恒山', '中国,黑龙江,鸡西,恒山', '3', '0467', '158100', 'Hengshan', 'HS', 'H', '130.90493', '45.21071'),
('723', '230304', '230300', '滴道区', '中国,黑龙江省,鸡西市,滴道区', '滴道', '中国,黑龙江,鸡西,滴道', '3', '0467', '158100', 'Didao', 'DD', 'D', '130.84841', '45.35109'),
('724', '230305', '230300', '梨树区', '中国,黑龙江省,鸡西市,梨树区', '梨树', '中国,黑龙江,鸡西,梨树', '3', '0467', '158100', 'Lishu', 'LS', 'L', '130.69848', '45.09037'),
('725', '230306', '230300', '城子河区', '中国,黑龙江省,鸡西市,城子河区', '城子河', '中国,黑龙江,鸡西,城子河', '3', '0467', '158100', 'Chengzihe', 'CZH', 'C', '131.01132', '45.33689'),
('726', '230307', '230300', '麻山区', '中国,黑龙江省,鸡西市,麻山区', '麻山', '中国,黑龙江,鸡西,麻山', '3', '0467', '158100', 'Mashan', 'MS', 'M', '130.47811', '45.21209'),
('727', '230321', '230300', '鸡东县', '中国,黑龙江省,鸡西市,鸡东县', '鸡东', '中国,黑龙江,鸡西,鸡东', '3', '0467', '158200', 'Jidong', 'JD', 'J', '131.12423', '45.26025'),
('728', '230381', '230300', '虎林市', '中国,黑龙江省,鸡西市,虎林市', '虎林', '中国,黑龙江,鸡西,虎林', '3', '0467', '158400', 'Hulin', 'HL', 'H', '132.93679', '45.76291'),
('729', '230382', '230300', '密山市', '中国,黑龙江省,鸡西市,密山市', '密山', '中国,黑龙江,鸡西,密山', '3', '0467', '158300', 'Mishan', 'MS', 'M', '131.84625', '45.5297'),
('730', '230400', '230000', '鹤岗市', '中国,黑龙江省,鹤岗市', '鹤岗', '中国,黑龙江,鹤岗', '2', '0468', '154100', 'Hegang', 'HG', 'H', '130.277487', '47.332085'),
('731', '230402', '230400', '向阳区', '中国,黑龙江省,鹤岗市,向阳区', '向阳', '中国,黑龙江,鹤岗,向阳', '3', '0468', '154100', 'Xiangyang', 'XY', 'X', '130.2943', '47.34247'),
('732', '230403', '230400', '工农区', '中国,黑龙江省,鹤岗市,工农区', '工农', '中国,黑龙江,鹤岗,工农', '3', '0468', '154100', 'Gongnong', 'GN', 'G', '130.27468', '47.31869'),
('733', '230404', '230400', '南山区', '中国,黑龙江省,鹤岗市,南山区', '南山', '中国,黑龙江,鹤岗,南山', '3', '0468', '154100', 'Nanshan', 'NS', 'N', '130.27676', '47.31404'),
('734', '230405', '230400', '兴安区', '中国,黑龙江省,鹤岗市,兴安区', '兴安', '中国,黑龙江,鹤岗,兴安', '3', '0468', '154100', 'Xing\'an', 'XA', 'X', '130.23965', '47.2526'),
('735', '230406', '230400', '东山区', '中国,黑龙江省,鹤岗市,东山区', '东山', '中国,黑龙江,鹤岗,东山', '3', '0468', '154100', 'Dongshan', 'DS', 'D', '130.31706', '47.33853'),
('736', '230407', '230400', '兴山区', '中国,黑龙江省,鹤岗市,兴山区', '兴山', '中国,黑龙江,鹤岗,兴山', '3', '0468', '154100', 'Xingshan', 'XS', 'X', '130.29271', '47.35776'),
('737', '230421', '230400', '萝北县', '中国,黑龙江省,鹤岗市,萝北县', '萝北', '中国,黑龙江,鹤岗,萝北', '3', '0468', '154200', 'Luobei', 'LB', 'L', '130.83346', '47.57959'),
('738', '230422', '230400', '绥滨县', '中国,黑龙江省,鹤岗市,绥滨县', '绥滨', '中国,黑龙江,鹤岗,绥滨', '3', '0468', '156200', 'Suibin', 'SB', 'S', '131.86029', '47.2903'),
('739', '230500', '230000', '双鸭山市', '中国,黑龙江省,双鸭山市', '双鸭山', '中国,黑龙江,双鸭山', '2', '0469', '155100', 'Shuangyashan', 'SYS', 'S', '131.157304', '46.643442'),
('740', '230502', '230500', '尖山区', '中国,黑龙江省,双鸭山市,尖山区', '尖山', '中国,黑龙江,双鸭山,尖山', '3', '0469', '155100', 'Jianshan', 'JS', 'J', '131.15841', '46.64635'),
('741', '230503', '230500', '岭东区', '中国,黑龙江省,双鸭山市,岭东区', '岭东', '中国,黑龙江,双鸭山,岭东', '3', '0469', '155100', 'Lingdong', 'LD', 'L', '131.16473', '46.59043'),
('742', '230505', '230500', '四方台区', '中国,黑龙江省,双鸭山市,四方台区', '四方台', '中国,黑龙江,双鸭山,四方台', '3', '0469', '155100', 'Sifangtai', 'SFT', 'S', '131.33593', '46.59499'),
('743', '230506', '230500', '宝山区', '中国,黑龙江省,双鸭山市,宝山区', '宝山', '中国,黑龙江,双鸭山,宝山', '3', '0469', '155100', 'Baoshan', 'BS', 'B', '131.4016', '46.57718'),
('744', '230521', '230500', '集贤县', '中国,黑龙江省,双鸭山市,集贤县', '集贤', '中国,黑龙江,双鸭山,集贤', '3', '0469', '155900', 'Jixian', 'JX', 'J', '131.14053', '46.72678'),
('745', '230522', '230500', '友谊县', '中国,黑龙江省,双鸭山市,友谊县', '友谊', '中国,黑龙江,双鸭山,友谊', '3', '0469', '155800', 'Youyi', 'YY', 'Y', '131.80789', '46.76739'),
('746', '230523', '230500', '宝清县', '中国,黑龙江省,双鸭山市,宝清县', '宝清', '中国,黑龙江,双鸭山,宝清', '3', '0469', '155600', 'Baoqing', 'BQ', 'B', '132.19695', '46.32716'),
('747', '230524', '230500', '饶河县', '中国,黑龙江省,双鸭山市,饶河县', '饶河', '中国,黑龙江,双鸭山,饶河', '3', '0469', '155700', 'Raohe', 'RH', 'R', '134.01986', '46.79899'),
('748', '230600', '230000', '大庆市', '中国,黑龙江省,大庆市', '大庆', '中国,黑龙江,大庆', '2', '0459', '163000', 'Daqing', 'DQ', 'D', '125.11272', '46.590734'),
('749', '230602', '230600', '萨尔图区', '中国,黑龙江省,大庆市,萨尔图区', '萨尔图', '中国,黑龙江,大庆,萨尔图', '3', '0459', '163000', 'Saertu', 'SET', 'S', '125.08792', '46.59359'),
('750', '230603', '230600', '龙凤区', '中国,黑龙江省,大庆市,龙凤区', '龙凤', '中国,黑龙江,大庆,龙凤', '3', '0459', '163000', 'Longfeng', 'LF', 'L', '125.11657', '46.53273'),
('751', '230604', '230600', '让胡路区', '中国,黑龙江省,大庆市,让胡路区', '让胡路', '中国,黑龙江,大庆,让胡路', '3', '0459', '163000', 'Ranghulu', 'RHL', 'R', '124.87075', '46.6522'),
('752', '230605', '230600', '红岗区', '中国,黑龙江省,大庆市,红岗区', '红岗', '中国,黑龙江,大庆,红岗', '3', '0459', '163000', 'Honggang', 'HG', 'H', '124.89248', '46.40128'),
('753', '230606', '230600', '大同区', '中国,黑龙江省,大庆市,大同区', '大同', '中国,黑龙江,大庆,大同', '3', '0459', '163000', 'Datong', 'DT', 'D', '124.81591', '46.03295'),
('754', '230621', '230600', '肇州县', '中国,黑龙江省,大庆市,肇州县', '肇州', '中国,黑龙江,大庆,肇州', '3', '0459', '166400', 'Zhaozhou', 'ZZ', 'Z', '125.27059', '45.70414'),
('755', '230622', '230600', '肇源县', '中国,黑龙江省,大庆市,肇源县', '肇源', '中国,黑龙江,大庆,肇源', '3', '0459', '166500', 'Zhaoyuan', 'ZY', 'Z', '125.08456', '45.52032'),
('756', '230623', '230600', '林甸县', '中国,黑龙江省,大庆市,林甸县', '林甸', '中国,黑龙江,大庆,林甸', '3', '0459', '166300', 'Lindian', 'LD', 'L', '124.87564', '47.18601'),
('757', '230624', '230600', '杜尔伯特蒙古族自治县', '中国,黑龙江省,大庆市,杜尔伯特蒙古族自治县', '杜尔伯特', '中国,黑龙江,大庆,杜尔伯特', '3', '0459', '166200', 'Duerbote', 'DEBT', 'D', '124.44937', '46.86507'),
('758', '230625', '230600', '高新区', '中国,黑龙江省,大庆市,高新区', '高新区', '中国,黑龙江,大庆,高新区', '3', '0459', '163000', 'Gaoxinqu', 'GXQ', 'G', '125.15074', '46.58099'),
('759', '230700', '230000', '伊春市', '中国,黑龙江省,伊春市', '伊春', '中国,黑龙江,伊春', '2', '0458', '153000', 'Yichun', 'YC', 'Y', '128.899396', '47.724775'),
('760', '230702', '230700', '伊春区', '中国,黑龙江省,伊春市,伊春区', '伊春', '中国,黑龙江,伊春,伊春', '3', '0458', '153000', 'Yichun', 'YC', 'Y', '128.90752', '47.728'),
('761', '230703', '230700', '南岔区', '中国,黑龙江省,伊春市,南岔区', '南岔', '中国,黑龙江,伊春,南岔', '3', '0458', '153000', 'Nancha', 'NC', 'N', '129.28362', '47.13897'),
('762', '230704', '230700', '友好区', '中国,黑龙江省,伊春市,友好区', '友好', '中国,黑龙江,伊春,友好', '3', '0458', '153000', 'Youhao', 'YH', 'Y', '128.84039', '47.85371'),
('763', '230705', '230700', '西林区', '中国,黑龙江省,伊春市,西林区', '西林', '中国,黑龙江,伊春,西林', '3', '0458', '153000', 'Xilin', 'XL', 'X', '129.31201', '47.48103'),
('764', '230706', '230700', '翠峦区', '中国,黑龙江省,伊春市,翠峦区', '翠峦', '中国,黑龙江,伊春,翠峦', '3', '0458', '153000', 'Cuiluan', 'CL', 'C', '128.66729', '47.72503'),
('765', '230707', '230700', '新青区', '中国,黑龙江省,伊春市,新青区', '新青', '中国,黑龙江,伊春,新青', '3', '0458', '153000', 'Xinqing', 'XQ', 'X', '129.53653', '48.29067'),
('766', '230708', '230700', '美溪区', '中国,黑龙江省,伊春市,美溪区', '美溪', '中国,黑龙江,伊春,美溪', '3', '0458', '153000', 'Meixi', 'MX', 'M', '129.13708', '47.63513'),
('767', '230709', '230700', '金山屯区', '中国,黑龙江省,伊春市,金山屯区', '金山屯', '中国,黑龙江,伊春,金山屯', '3', '0458', '153000', 'Jinshantun', 'JST', 'J', '129.43768', '47.41349'),
('768', '230710', '230700', '五营区', '中国,黑龙江省,伊春市,五营区', '五营', '中国,黑龙江,伊春,五营', '3', '0458', '153000', 'Wuying', 'WY', 'W', '129.24545', '48.10791'),
('769', '230711', '230700', '乌马河区', '中国,黑龙江省,伊春市,乌马河区', '乌马河', '中国,黑龙江,伊春,乌马河', '3', '0458', '153000', 'Wumahe', 'WMH', 'W', '128.79672', '47.728'),
('770', '230712', '230700', '汤旺河区', '中国,黑龙江省,伊春市,汤旺河区', '汤旺河', '中国,黑龙江,伊春,汤旺河', '3', '0458', '153000', 'Tangwanghe', 'TWH', 'T', '129.57226', '48.45182'),
('771', '230713', '230700', '带岭区', '中国,黑龙江省,伊春市,带岭区', '带岭', '中国,黑龙江,伊春,带岭', '3', '0458', '153000', 'Dailing', 'DL', 'D', '129.02352', '47.02553'),
('772', '230714', '230700', '乌伊岭区', '中国,黑龙江省,伊春市,乌伊岭区', '乌伊岭', '中国,黑龙江,伊春,乌伊岭', '3', '0458', '153000', 'Wuyiling', 'WYL', 'W', '129.43981', '48.59602'),
('773', '230715', '230700', '红星区', '中国,黑龙江省,伊春市,红星区', '红星', '中国,黑龙江,伊春,红星', '3', '0458', '153000', 'Hongxing', 'HX', 'H', '129.3887', '48.23944'),
('774', '230716', '230700', '上甘岭区', '中国,黑龙江省,伊春市,上甘岭区', '上甘岭', '中国,黑龙江,伊春,上甘岭', '3', '0458', '153000', 'Shangganling', 'SGL', 'S', '129.02447', '47.97522'),
('775', '230722', '230700', '嘉荫县', '中国,黑龙江省,伊春市,嘉荫县', '嘉荫', '中国,黑龙江,伊春,嘉荫', '3', '0458', '153200', 'Jiayin', 'JY', 'J', '130.39825', '48.8917'),
('776', '230781', '230700', '铁力市', '中国,黑龙江省,伊春市,铁力市', '铁力', '中国,黑龙江,伊春,铁力', '3', '0458', '152500', 'Tieli', 'TL', 'T', '128.0317', '46.98571'),
('777', '230800', '230000', '佳木斯市', '中国,黑龙江省,佳木斯市', '佳木斯', '中国,黑龙江,佳木斯', '2', '0454', '154000', 'Jiamusi', 'JMS', 'J', '130.361634', '46.809606'),
('778', '230803', '230800', '向阳区', '中国,黑龙江省,佳木斯市,向阳区', '向阳', '中国,黑龙江,佳木斯,向阳', '3', '0454', '154000', 'Xiangyang', 'XY', 'X', '130.36519', '46.80778'),
('779', '230804', '230800', '前进区', '中国,黑龙江省,佳木斯市,前进区', '前进', '中国,黑龙江,佳木斯,前进', '3', '0454', '154000', 'Qianjin', 'QJ', 'Q', '130.37497', '46.81401'),
('780', '230805', '230800', '东风区', '中国,黑龙江省,佳木斯市,东风区', '东风', '中国,黑龙江,佳木斯,东风', '3', '0454', '154000', 'Dongfeng', 'DF', 'D', '130.40366', '46.82257'),
('781', '230811', '230800', '郊区', '中国,黑龙江省,佳木斯市,郊区', '郊区', '中国,黑龙江,佳木斯,郊区', '3', '0454', '154000', 'Jiaoqu', 'JQ', 'J', '130.32731', '46.80958'),
('782', '230822', '230800', '桦南县', '中国,黑龙江省,佳木斯市,桦南县', '桦南', '中国,黑龙江,佳木斯,桦南', '3', '0454', '154400', 'Huanan', 'HN', 'H', '130.55361', '46.23921'),
('783', '230826', '230800', '桦川县', '中国,黑龙江省,佳木斯市,桦川县', '桦川', '中国,黑龙江,佳木斯,桦川', '3', '0454', '154300', 'Huachuan', 'HC', 'H', '130.71893', '47.02297'),
('784', '230828', '230800', '汤原县', '中国,黑龙江省,佳木斯市,汤原县', '汤原', '中国,黑龙江,佳木斯,汤原', '3', '0454', '154700', 'Tangyuan', 'TY', 'T', '129.90966', '46.72755'),
('785', '230881', '230800', '同江市', '中国,黑龙江省,佳木斯市,同江市', '同江', '中国,黑龙江,佳木斯,同江', '3', '0454', '156400', 'Tongjiang', 'TJ', 'T', '132.51095', '47.64211'),
('786', '230882', '230800', '富锦市', '中国,黑龙江省,佳木斯市,富锦市', '富锦', '中国,黑龙江,佳木斯,富锦', '3', '0454', '156100', 'Fujin', 'FJ', 'F', '132.03707', '47.25132'),
('787', '230883', '230800', '抚远市', '中国,黑龙江省,佳木斯市,抚远市', '抚远', '中国,黑龙江,佳木斯,抚远', '3', '0454', '156500', 'Fuyuan', 'FY', 'F', '134.29595', '48.36794'),
('788', '230900', '230000', '七台河市', '中国,黑龙江省,七台河市', '七台河', '中国,黑龙江,七台河', '2', '0464', '154600', 'Qitaihe', 'QTH', 'Q', '131.015584', '45.771266'),
('789', '230902', '230900', '新兴区', '中国,黑龙江省,七台河市,新兴区', '新兴', '中国,黑龙江,七台河,新兴', '3', '0464', '154600', 'Xinxing', 'XX', 'X', '130.93212', '45.81624'),
('790', '230903', '230900', '桃山区', '中国,黑龙江省,七台河市,桃山区', '桃山', '中国,黑龙江,七台河,桃山', '3', '0464', '154600', 'Taoshan', 'TS', 'T', '131.01786', '45.76782'),
('791', '230904', '230900', '茄子河区', '中国,黑龙江省,七台河市,茄子河区', '茄子河', '中国,黑龙江,七台河,茄子河', '3', '0464', '154600', 'Qiezihe', 'QZH', 'Q', '131.06807', '45.78519'),
('792', '230921', '230900', '勃利县', '中国,黑龙江省,七台河市,勃利县', '勃利', '中国,黑龙江,七台河,勃利', '3', '0464', '154500', 'Boli', 'BL', 'B', '130.59179', '45.755'),
('793', '231000', '230000', '牡丹江市', '中国,黑龙江省,牡丹江市', '牡丹江', '中国,黑龙江,牡丹江', '2', '0453', '157000', 'Mudanjiang', 'MDJ', 'M', '129.618602', '44.582962'),
('794', '231002', '231000', '东安区', '中国,黑龙江省,牡丹江市,东安区', '东安', '中国,黑龙江,牡丹江,东安', '3', '0453', '157000', 'Dong\'an', 'DA', 'D', '129.62665', '44.58133'),
('795', '231003', '231000', '阳明区', '中国,黑龙江省,牡丹江市,阳明区', '阳明', '中国,黑龙江,牡丹江,阳明', '3', '0453', '157000', 'Yangming', 'YM', 'Y', '129.63547', '44.59603'),
('796', '231004', '231000', '爱民区', '中国,黑龙江省,牡丹江市,爱民区', '爱民', '中国,黑龙江,牡丹江,爱民', '3', '0453', '157000', 'Aimin', 'AM', 'A', '129.59077', '44.59648'),
('797', '231005', '231000', '西安区', '中国,黑龙江省,牡丹江市,西安区', '西安', '中国,黑龙江,牡丹江,西安', '3', '0453', '157000', 'Xi\'an', 'XA', 'X', '129.61616', '44.57766'),
('798', '231025', '231000', '林口县', '中国,黑龙江省,牡丹江市,林口县', '林口', '中国,黑龙江,牡丹江,林口', '3', '0453', '157600', 'Linkou', 'LK', 'L', '130.28393', '45.27809'),
('799', '231081', '231000', '绥芬河市', '中国,黑龙江省,牡丹江市,绥芬河市', '绥芬河', '中国,黑龙江,牡丹江,绥芬河', '3', '0453', '157300', 'Suifenhe', 'SFH', 'S', '131.15139', '44.41249'),
('800', '231083', '231000', '海林市', '中国,黑龙江省,牡丹江市,海林市', '海林', '中国,黑龙江,牡丹江,海林', '3', '0453', '157100', 'Hailin', 'HL', 'H', '129.38156', '44.59'),
('801', '231084', '231000', '宁安市', '中国,黑龙江省,牡丹江市,宁安市', '宁安', '中国,黑龙江,牡丹江,宁安', '3', '0453', '157400', 'Ning\'an', 'NA', 'N', '129.48303', '44.34016'),
('802', '231085', '231000', '穆棱市', '中国,黑龙江省,牡丹江市,穆棱市', '穆棱', '中国,黑龙江,牡丹江,穆棱', '3', '0453', '157500', 'Muling', 'ML', 'M', '130.52465', '44.919'),
('803', '231086', '231000', '东宁市', '中国,黑龙江省,牡丹江市,东宁市', '东宁', '中国,黑龙江,牡丹江,东宁', '3', '0453', '157200', 'Dongning', 'DN', 'D', '131.12793', '44.0661'),
('804', '231100', '230000', '黑河市', '中国,黑龙江省,黑河市', '黑河', '中国,黑龙江,黑河', '2', '0456', '164300', 'Heihe', 'HH', 'H', '127.499023', '50.249585'),
('805', '231102', '231100', '爱辉区', '中国,黑龙江省,黑河市,爱辉区', '爱辉', '中国,黑龙江,黑河,爱辉', '3', '0456', '164300', 'Aihui', 'AH', 'A', '127.50074', '50.25202'),
('806', '231121', '231100', '嫩江县', '中国,黑龙江省,黑河市,嫩江县', '嫩江', '中国,黑龙江,黑河,嫩江', '3', '0456', '161400', 'Nenjiang', 'NJ', 'N', '125.22607', '49.17844'),
('807', '231123', '231100', '逊克县', '中国,黑龙江省,黑河市,逊克县', '逊克', '中国,黑龙江,黑河,逊克', '3', '0456', '164400', 'Xunke', 'XK', 'X', '128.47882', '49.57983'),
('808', '231124', '231100', '孙吴县', '中国,黑龙江省,黑河市,孙吴县', '孙吴', '中国,黑龙江,黑河,孙吴', '3', '0456', '164200', 'Sunwu', 'SW', 'S', '127.33599', '49.42539'),
('809', '231181', '231100', '北安市', '中国,黑龙江省,黑河市,北安市', '北安', '中国,黑龙江,黑河,北安', '3', '0456', '164000', 'Bei\'an', 'BA', 'B', '126.48193', '48.23872'),
('810', '231182', '231100', '五大连池市', '中国,黑龙江省,黑河市,五大连池市', '五大连池', '中国,黑龙江,黑河,五大连池', '3', '0456', '164100', 'Wudalianchi', 'WDLC', 'W', '126.20294', '48.51507'),
('811', '231200', '230000', '绥化市', '中国,黑龙江省,绥化市', '绥化', '中国,黑龙江,绥化', '2', '0455', '152000', 'Suihua', 'SH', 'S', '126.99293', '46.637393'),
('812', '231202', '231200', '北林区', '中国,黑龙江省,绥化市,北林区', '北林', '中国,黑龙江,绥化,北林', '3', '0455', '152000', 'Beilin', 'BL', 'B', '126.98564', '46.63735'),
('813', '231221', '231200', '望奎县', '中国,黑龙江省,绥化市,望奎县', '望奎', '中国,黑龙江,绥化,望奎', '3', '0455', '152100', 'Wangkui', 'WK', 'W', '126.48187', '46.83079'),
('814', '231222', '231200', '兰西县', '中国,黑龙江省,绥化市,兰西县', '兰西', '中国,黑龙江,绥化,兰西', '3', '0455', '151500', 'Lanxi', 'LX', 'L', '126.28994', '46.2525'),
('815', '231223', '231200', '青冈县', '中国,黑龙江省,绥化市,青冈县', '青冈', '中国,黑龙江,绥化,青冈', '3', '0455', '151600', 'Qinggang', 'QG', 'Q', '126.11325', '46.68534'),
('816', '231224', '231200', '庆安县', '中国,黑龙江省,绥化市,庆安县', '庆安', '中国,黑龙江,绥化,庆安', '3', '0455', '152400', 'Qing\'an', 'QA', 'Q', '127.50753', '46.88016'),
('817', '231225', '231200', '明水县', '中国,黑龙江省,绥化市,明水县', '明水', '中国,黑龙江,绥化,明水', '3', '0455', '151700', 'Mingshui', 'MS', 'M', '125.90594', '47.17327'),
('818', '231226', '231200', '绥棱县', '中国,黑龙江省,绥化市,绥棱县', '绥棱', '中国,黑龙江,绥化,绥棱', '3', '0455', '152200', 'Suileng', 'SL', 'S', '127.11584', '47.24267'),
('819', '231281', '231200', '安达市', '中国,黑龙江省,绥化市,安达市', '安达', '中国,黑龙江,绥化,安达', '3', '0455', '151400', 'Anda', 'AD', 'A', '125.34375', '46.4177'),
('820', '231282', '231200', '肇东市', '中国,黑龙江省,绥化市,肇东市', '肇东', '中国,黑龙江,绥化,肇东', '3', '0455', '151100', 'Zhaodong', 'ZD', 'Z', '125.96243', '46.05131'),
('821', '231283', '231200', '海伦市', '中国,黑龙江省,绥化市,海伦市', '海伦', '中国,黑龙江,绥化,海伦', '3', '0455', '152300', 'Hailun', 'HL', 'H', '126.9682', '47.46093'),
('822', '232700', '230000', '大兴安岭地区', '中国,黑龙江省,大兴安岭地区', '大兴安岭', '中国,黑龙江,大兴安岭', '2', '0457', '165000', 'DaXingAnLing', 'DXAL', 'D', '124.711526', '52.335262'),
('823', '232701', '232700', '加格达奇区', '中国,黑龙江省,大兴安岭地区,加格达奇区', '加格达奇', '中国,黑龙江,大兴安岭,加格达奇', '3', '0457', '165000', 'Jiagedaqi', 'JGDQ', 'J', '124.30954', '51.98144'),
('824', '232702', '232700', '新林区', '中国,黑龙江省,大兴安岭地区,新林区', '新林', '中国,黑龙江,大兴安岭,新林', '3', '0457', '165010', 'Xinlin', 'XL', 'X', '124.397983', '51.67341'),
('825', '232703', '232700', '松岭区', '中国,黑龙江省,大兴安岭地区,松岭区', '松岭', '中国,黑龙江,大兴安岭,松岭', '3', '0457', '165020', 'Songling', 'SL', 'S', '124.189713', '51.985453'),
('826', '232704', '232700', '呼中区', '中国,黑龙江省,大兴安岭地区,呼中区', '呼中', '中国,黑龙江,大兴安岭,呼中', '3', '0457', '165030', 'Huzhong', 'HZ', 'H', '123.60009', '52.03346'),
('827', '232721', '232700', '呼玛县', '中国,黑龙江省,大兴安岭地区,呼玛县', '呼玛', '中国,黑龙江,大兴安岭,呼玛', '3', '0457', '165100', 'Huma', 'HM', 'H', '126.66174', '51.73112'),
('828', '232722', '232700', '塔河县', '中国,黑龙江省,大兴安岭地区,塔河县', '塔河', '中国,黑龙江,大兴安岭,塔河', '3', '0457', '165200', 'Tahe', 'TH', 'T', '124.70999', '52.33431'),
('829', '232723', '232700', '漠河县', '中国,黑龙江省,大兴安岭地区,漠河县', '漠河', '中国,黑龙江,大兴安岭,漠河', '3', '0457', '165300', 'Mohe', 'MH', 'M', '122.53759', '52.97003'),
('830', '310000', '100000', '上海', '中国,上海', '上海', '中国,上海', '1', '', '', 'Shanghai', 'SH', 'S', '121.472644', '31.231706'),
('831', '310100', '310000', '上海市', '中国,上海,上海市', '上海', '中国,上海,上海', '2', '021', '200000', 'Shanghai', 'SH', 'S', '121.472644', '31.231706'),
('832', '310101', '310100', '黄浦区', '中国,上海,上海市,黄浦区', '黄浦', '中国,上海,上海,黄浦', '3', '021', '200001', 'Huangpu', 'HP', 'H', '121.49295', '31.22337'),
('833', '310104', '310100', '徐汇区', '中国,上海,上海市,徐汇区', '徐汇', '中国,上海,上海,徐汇', '3', '021', '200030', 'Xuhui', 'XH', 'X', '121.43676', '31.18831'),
('834', '310105', '310100', '长宁区', '中国,上海,上海市,长宁区', '长宁', '中国,上海,上海,长宁', '3', '021', '200050', 'Changning', 'CN', 'C', '121.42462', '31.22036'),
('835', '310106', '310100', '静安区', '中国,上海,上海市,静安区', '静安', '中国,上海,上海,静安', '3', '021', '200040', 'Jing\'an', 'JA', 'J', '121.4444', '31.22884'),
('836', '310107', '310100', '普陀区', '中国,上海,上海市,普陀区', '普陀', '中国,上海,上海,普陀', '3', '021', '200333', 'Putuo', 'PT', 'P', '121.39703', '31.24951'),
('837', '310109', '310100', '虹口区', '中国,上海,上海市,虹口区', '虹口', '中国,上海,上海,虹口', '3', '021', '200080', 'Hongkou', 'HK', 'H', '121.48162', '31.27788'),
('838', '310110', '310100', '杨浦区', '中国,上海,上海市,杨浦区', '杨浦', '中国,上海,上海,杨浦', '3', '021', '200082', 'Yangpu', 'YP', 'Y', '121.526', '31.2595'),
('839', '310112', '310100', '闵行区', '中国,上海,上海市,闵行区', '闵行', '中国,上海,上海,闵行', '3', '021', '201100', 'Minhang', 'MH', 'M', '121.38162', '31.11246'),
('840', '310113', '310100', '宝山区', '中国,上海,上海市,宝山区', '宝山', '中国,上海,上海,宝山', '3', '021', '201900', 'Baoshan', 'BS', 'B', '121.4891', '31.4045'),
('841', '310114', '310100', '嘉定区', '中国,上海,上海市,嘉定区', '嘉定', '中国,上海,上海,嘉定', '3', '021', '201800', 'Jiading', 'JD', 'J', '121.2655', '31.37473'),
('842', '310115', '310100', '浦东新区', '中国,上海,上海市,浦东新区', '浦东', '中国,上海,上海,浦东', '3', '021', '200120', 'Pudong', 'PD', 'P', '121.5447', '31.22249'),
('843', '310116', '310100', '金山区', '中国,上海,上海市,金山区', '金山', '中国,上海,上海,金山', '3', '021', '201500', 'Jinshan', 'JS', 'J', '121.34164', '30.74163'),
('844', '310117', '310100', '松江区', '中国,上海,上海市,松江区', '松江', '中国,上海,上海,松江', '3', '021', '201600', 'Songjiang', 'SJ', 'S', '121.22879', '31.03222'),
('845', '310118', '310100', '青浦区', '中国,上海,上海市,青浦区', '青浦', '中国,上海,上海,青浦', '3', '021', '201700', 'Qingpu', 'QP', 'Q', '121.12417', '31.14974'),
('846', '310120', '310100', '奉贤区', '中国,上海,上海市,奉贤区', '奉贤', '中国,上海,上海,奉贤', '3', '021', '201400', 'Fengxian', 'FX', 'F', '121.47412', '30.9179'),
('847', '310151', '310100', '崇明区', '中国,上海,上海市,崇明区', '崇明', '中国,上海,上海,崇明', '3', '021', '202150', 'Chongming', 'CM', 'C', '121.39758', '31.62278'),
('848', '310231', '310100', '张江高新区', '中国,上海,上海市,张江高新区', '张江高新区', '中国,上海,上海,张江高新区', '3', '021', '201203', 'Zhangjiang', 'ZJ', 'Z', '121.635208', '31.219398'),
('849', '310232', '310100', '紫竹高新区', '中国,上海,上海市,紫竹高新区', '紫竹高新区', '中国,上海,上海,紫竹高新区', '3', '021', '200336', 'Zizhu', 'ZZ', 'Z', '121.448504', '31.021826'),
('850', '310233', '310100', '漕河泾开发区', '中国,上海,上海市,漕河泾开发区', '漕河泾开发区', '中国,上海,上海,漕河泾开发区', '3', '021', '200233', 'Caohejing', 'CHJ', 'C', '121.397823', '31.170624'),
('851', '320000', '100000', '江苏省', '中国,江苏省', '江苏', '中国,江苏', '1', '', '', 'Jiangsu', 'JS', 'J', '118.767413', '32.041544'),
('852', '320100', '320000', '南京市', '中国,江苏省,南京市', '南京', '中国,江苏,南京', '2', '025', '210000', 'Nanjing', 'NJ', 'N', '118.767413', '32.041544'),
('853', '320102', '320100', '玄武区', '中国,江苏省,南京市,玄武区', '玄武', '中国,江苏,南京,玄武', '3', '025', '210000', 'Xuanwu', 'XW', 'X', '118.79772', '32.04856'),
('854', '320104', '320100', '秦淮区', '中国,江苏省,南京市,秦淮区', '秦淮', '中国,江苏,南京,秦淮', '3', '025', '210000', 'Qinhuai', 'QH', 'Q', '118.79815', '32.01112'),
('855', '320105', '320100', '建邺区', '中国,江苏省,南京市,建邺区', '建邺', '中国,江苏,南京,建邺', '3', '025', '210000', 'Jianye', 'JY', 'J', '118.76641', '32.03096'),
('856', '320106', '320100', '鼓楼区', '中国,江苏省,南京市,鼓楼区', '鼓楼', '中国,江苏,南京,鼓楼', '3', '025', '210000', 'Gulou', 'GL', 'G', '118.76974', '32.06632'),
('857', '320111', '320100', '浦口区', '中国,江苏省,南京市,浦口区', '浦口', '中国,江苏,南京,浦口', '3', '025', '210000', 'Pukou', 'PK', 'P', '118.62802', '32.05881'),
('858', '320113', '320100', '栖霞区', '中国,江苏省,南京市,栖霞区', '栖霞', '中国,江苏,南京,栖霞', '3', '025', '210000', 'Qixia', 'QX', 'Q', '118.88064', '32.11352'),
('859', '320114', '320100', '雨花台区', '中国,江苏省,南京市,雨花台区', '雨花台', '中国,江苏,南京,雨花台', '3', '025', '210000', 'Yuhuatai', 'YHT', 'Y', '118.7799', '31.99202'),
('860', '320115', '320100', '江宁区', '中国,江苏省,南京市,江宁区', '江宁', '中国,江苏,南京,江宁', '3', '025', '211100', 'Jiangning', 'JN', 'J', '118.8399', '31.95263'),
('861', '320116', '320100', '六合区', '中国,江苏省,南京市,六合区', '六合', '中国,江苏,南京,六合', '3', '025', '211500', 'Luhe', 'LH', 'L', '118.8413', '32.34222'),
('862', '320117', '320100', '溧水区', '中国,江苏省,南京市,溧水区', '溧水', '中国,江苏,南京,溧水', '3', '025', '211200', 'Lishui', 'LS', 'L', '119.028732', '31.653061'),
('863', '320118', '320100', '高淳区', '中国,江苏省,南京市,高淳区', '高淳', '中国,江苏,南京,高淳', '3', '025', '211300', 'Gaochun', 'GC', 'G', '118.87589', '31.327132'),
('864', '320119', '320100', '江北新区', '中国,江苏省,南京市,江北新区', '江北新区', '中国,江苏,南京,江北新区', '3', '025', '211500', 'JiangbeiXinqu', 'JBXQ', 'J', '118.808899', '32.169219'),
('865', '320120', '320100', '高新区', '中国,江苏省,南京市,高新区', '高新区', '中国,江苏,南京,高新区', '3', '025', '210000', 'GaoXinQu', 'GXQ', 'G', '118.712815', '32.171025'),
('866', '320200', '320000', '无锡市', '中国,江苏省,无锡市', '无锡', '中国,江苏,无锡', '2', '0510', '214000', 'Wuxi', 'WX', 'W', '120.301663', '31.574729'),
('867', '320205', '320200', '锡山区', '中国,江苏省,无锡市,锡山区', '锡山', '中国,江苏,无锡,锡山', '3', '0510', '214000', 'Xishan', 'XS', 'X', '120.35699', '31.5886'),
('868', '320206', '320200', '惠山区', '中国,江苏省,无锡市,惠山区', '惠山', '中国,江苏,无锡,惠山', '3', '0510', '214000', 'Huishan', 'HS', 'H', '120.29849', '31.68088'),
('869', '320211', '320200', '滨湖区', '中国,江苏省,无锡市,滨湖区', '滨湖', '中国,江苏,无锡,滨湖', '3', '0510', '214123', 'Binhu', 'BH', 'B', '120.29461', '31.52162'),
('870', '320213', '320200', '梁溪区', '中国,江苏省,无锡市,梁溪区', '梁溪', '中国,江苏,无锡,梁溪', '3', '0510', '214002', 'LiangXi', 'LX', 'L', '120.29975', '31.58002'),
('871', '320214', '320200', '新吴区', '中国,江苏省,无锡市,新吴区', '新吴', '中国,江苏,无锡,新吴', '3', '0510', '214028', 'XinWu', 'XW', 'X', '120.363601', '31.490762'),
('872', '320281', '320200', '江阴市', '中国,江苏省,无锡市,江阴市', '江阴', '中国,江苏,无锡,江阴', '3', '0510', '214400', 'Jiangyin', 'JY', 'J', '120.2853', '31.91996'),
('873', '320282', '320200', '宜兴市', '中国,江苏省,无锡市,宜兴市', '宜兴', '中国,江苏,无锡,宜兴', '3', '0510', '214200', 'Yixing', 'YX', 'Y', '119.82357', '31.33978'),
('874', '320300', '320000', '徐州市', '中国,江苏省,徐州市', '徐州', '中国,江苏,徐州', '2', '0516', '221000', 'Xuzhou', 'XZ', 'X', '117.184811', '34.261792'),
('875', '320302', '320300', '鼓楼区', '中国,江苏省,徐州市,鼓楼区', '鼓楼', '中国,江苏,徐州,鼓楼', '3', '0516', '221005', 'Gulou', 'GL', 'G', '117.18559', '34.28851'),
('876', '320303', '320300', '云龙区', '中国,江苏省,徐州市,云龙区', '云龙', '中国,江苏,徐州,云龙', '3', '0516', '221007', 'Yunlong', 'YL', 'Y', '117.23053', '34.24895'),
('877', '320305', '320300', '贾汪区', '中国,江苏省,徐州市,贾汪区', '贾汪', '中国,江苏,徐州,贾汪', '3', '0516', '221003', 'Jiawang', 'JW', 'J', '117.45346', '34.44264'),
('878', '320311', '320300', '泉山区', '中国,江苏省,徐州市,泉山区', '泉山', '中国,江苏,徐州,泉山', '3', '0516', '221006', 'Quanshan', 'QS', 'Q', '117.19378', '34.24418'),
('879', '320312', '320300', '铜山区', '中国,江苏省,徐州市,铜山区', '铜山', '中国,江苏,徐州,铜山', '3', '0516', '221106', 'Tongshan', 'TS', 'T', '117.183894', '34.19288'),
('880', '320321', '320300', '丰县', '中国,江苏省,徐州市,丰县', '丰县', '中国,江苏,徐州,丰县', '3', '0516', '221700', 'Fengxian', 'FX', 'F', '116.59957', '34.69972'),
('881', '320322', '320300', '沛县', '中国,江苏省,徐州市,沛县', '沛县', '中国,江苏,徐州,沛县', '3', '0516', '221600', 'Peixian', 'PX', 'P', '116.93743', '34.72163'),
('882', '320324', '320300', '睢宁县', '中国,江苏省,徐州市,睢宁县', '睢宁', '中国,江苏,徐州,睢宁', '3', '0516', '221200', 'Suining', 'SN', 'S', '117.94104', '33.91269'),
('883', '320381', '320300', '新沂市', '中国,江苏省,徐州市,新沂市', '新沂', '中国,江苏,徐州,新沂', '3', '0516', '221400', 'Xinyi', 'XY', 'X', '118.35452', '34.36942'),
('884', '320382', '320300', '邳州市', '中国,江苏省,徐州市,邳州市', '邳州', '中国,江苏,徐州,邳州', '3', '0516', '221300', 'Pizhou', 'PZ', 'P', '117.95858', '34.33329'),
('885', '320383', '320300', '经济技术开发区', '中国,江苏省,徐州市,经济技术开发区', '经济技术开发区', '中国,江苏,徐州,经济技术开发区', '3', '0516', '221000', 'Jingjikaifaqu', 'JJ', 'J', '117.278725', '34.265385'),
('886', '320384', '320300', '高新技术产业开发区', '中国,江苏省,徐州市,高新技术产业开发区', '高新区', '中国,江苏,徐州,高新区', '3', '0516', '221100', 'Gaoxinqu', 'GXQ', 'G', '117.180932', '34.162716'),
('887', '320385', '320300', '软件园', '中国,江苏省,徐州市,软件园', '软件园', '中国,江苏,徐州,软件园', '3', '0516', '221100', 'Ruanjianyuan', 'RJY', 'R', '117.202215', '34.226085'),
('888', '320400', '320000', '常州市', '中国,江苏省,常州市', '常州', '中国,江苏,常州', '2', '0519', '213000', 'Changzhou', 'CZ', 'C', '119.946973', '31.772752'),
('889', '320402', '320400', '天宁区', '中国,江苏省,常州市,天宁区', '天宁', '中国,江苏,常州,天宁', '3', '0519', '213000', 'Tianning', 'TN', 'T', '119.95132', '31.75211'),
('890', '320404', '320400', '钟楼区', '中国,江苏省,常州市,钟楼区', '钟楼', '中国,江苏,常州,钟楼', '3', '0519', '213000', 'Zhonglou', 'ZL', 'Z', '119.90178', '31.80221'),
('891', '320411', '320400', '新北区', '中国,江苏省,常州市,新北区', '新北', '中国,江苏,常州,新北', '3', '0519', '213022', 'Xinbei', 'XB', 'X', '119.97131', '31.83046'),
('892', '320412', '320400', '武进区', '中国,江苏省,常州市,武进区', '武进', '中国,江苏,常州,武进', '3', '0519', '213100', 'Wujin', 'WJ', 'W', '119.94244', '31.70086'),
('893', '320413', '320400', '金坛区', '中国,江苏省,常州市,金坛区', '金坛', '中国,江苏,常州,金坛', '3', '0519', '213200', 'Jintan', 'JT', 'J', '119.57757', '31.74043'),
('894', '320481', '320400', '溧阳市', '中国,江苏省,常州市,溧阳市', '溧阳', '中国,江苏,常州,溧阳', '3', '0519', '213300', 'Liyang', 'LY', 'L', '119.4837', '31.41538'),
('895', '320482', '320400', '高新区', '中国,江苏省,常州市,高新区', '高新区', '中国,江苏,常州,高新区', '3', '0519', '213000', 'Gaoxinqu', 'GXQ', 'G', '119.973139', '31.82896'),
('896', '320500', '320000', '苏州市', '中国,江苏省,苏州市', '苏州', '中国,江苏,苏州', '2', '0512', '215000', 'Suzhou', 'SZ', 'S', '120.619585', '31.299379'),
('897', '320505', '320500', '虎丘区', '中国,江苏省,苏州市,虎丘区', '虎丘', '中国,江苏,苏州,虎丘', '3', '0512', '215000', 'Huqiu', 'HQ', 'H', '120.57345', '31.2953'),
('898', '320506', '320500', '吴中区', '中国,江苏省,苏州市,吴中区', '吴中', '中国,江苏,苏州,吴中', '3', '0512', '215100', 'Wuzhong', 'WZ', 'W', '120.63211', '31.26226'),
('899', '320507', '320500', '相城区', '中国,江苏省,苏州市,相城区', '相城', '中国,江苏,苏州,相城', '3', '0512', '215100', 'Xiangcheng', 'XC', 'X', '120.64239', '31.36889'),
('900', '320508', '320500', '姑苏区', '中国,江苏省,苏州市,姑苏区', '姑苏', '中国,江苏,苏州,姑苏', '3', '0512', '215031', 'Gusu', 'GS', 'G', '120.619585', '31.299379'),
('901', '320509', '320500', '吴江区', '中国,江苏省,苏州市,吴江区', '吴江', '中国,江苏,苏州,吴江', '3', '0512', '215200', 'Wujiang', 'WJ', 'W', '120.638317', '31.159815'),
('902', '320581', '320500', '常熟市', '中国,江苏省,苏州市,常熟市', '常熟', '中国,江苏,苏州,常熟', '3', '0512', '215500', 'Changshu', 'CS', 'C', '120.75225', '31.65374'),
('903', '320582', '320500', '张家港市', '中国,江苏省,苏州市,张家港市', '张家港', '中国,江苏,苏州,张家港', '3', '0512', '215600', 'Zhangjiagang', 'ZJG', 'Z', '120.55538', '31.87532'),
('904', '320583', '320500', '昆山市', '中国,江苏省,苏州市,昆山市', '昆山', '中国,江苏,苏州,昆山', '3', '0512', '215300', 'Kunshan', 'KS', 'K', '120.98074', '31.38464'),
('905', '320585', '320500', '太仓市', '中国,江苏省,苏州市,太仓市', '太仓', '中国,江苏,苏州,太仓', '3', '0512', '215400', 'Taicang', 'TC', 'T', '121.10891', '31.4497'),
('906', '320586', '320500', '苏州新区', '中国,江苏省,苏州市,苏州新区', '苏州新区', '中国,江苏,苏州,苏州新区', '3', '0512', '215010', 'Suzhouxinqu', 'SZXQ', 'S', '120.524139', '31.372399'),
('907', '320587', '320500', '工业园区', '中国,江苏省,苏州市,工业园区', '工业园区', '中国,江苏,苏州,工业园区', '3', '0512', '215000', 'GongyeYuanqu', 'GYYQ', 'G', '120.671629', '31.317985'),
('908', '320588', '320500', '高新区', '中国,江苏省,苏州市,高新区', '高新区', '中国,江苏,苏州,高新区', '3', '0512', '215010', 'Gaoxinqu', 'GXQ', 'G', '120.434418', '31.327552'),
('909', '320600', '320000', '南通市', '中国,江苏省,南通市', '南通', '中国,江苏,南通', '2', '0513', '226000', 'Nantong', 'NT', 'N', '120.864608', '32.016212'),
('910', '320602', '320600', '崇川区', '中国,江苏省,南通市,崇川区', '崇川', '中国,江苏,南通,崇川', '3', '0513', '226000', 'Chongchuan', 'CC', 'C', '120.8573', '32.0098'),
('911', '320611', '320600', '港闸区', '中国,江苏省,南通市,港闸区', '港闸', '中国,江苏,南通,港闸', '3', '0513', '226000', 'Gangzha', 'GZ', 'G', '120.81778', '32.03163'),
('912', '320612', '320600', '通州区', '中国,江苏省,南通市,通州区', '通州', '中国,江苏,南通,通州', '3', '0513', '226300', 'Tongzhou', 'TZ', 'T', '121.07293', '32.0676'),
('913', '320621', '320600', '海安县', '中国,江苏省,南通市,海安县', '海安', '中国,江苏,南通,海安', '3', '0513', '226600', 'Hai\'an', 'HA', 'H', '120.45852', '32.54514'),
('914', '320623', '320600', '如东县', '中国,江苏省,南通市,如东县', '如东', '中国,江苏,南通,如东', '3', '0513', '226400', 'Rudong', 'RD', 'R', '121.18942', '32.31439'),
('915', '320681', '320600', '启东市', '中国,江苏省,南通市,启东市', '启东', '中国,江苏,南通,启东', '3', '0513', '226200', 'Qidong', 'QD', 'Q', '121.65985', '31.81083'),
('916', '320682', '320600', '如皋市', '中国,江苏省,南通市,如皋市', '如皋', '中国,江苏,南通,如皋', '3', '0513', '226500', 'Rugao', 'RG', 'R', '120.55969', '32.37597'),
('917', '320684', '320600', '海门市', '中国,江苏省,南通市,海门市', '海门', '中国,江苏,南通,海门', '3', '0513', '226100', 'Haimen', 'HM', 'H', '121.16995', '31.89422'),
('918', '320685', '320600', '经济技术开发区', '中国,江苏省,南通市,经济技术开发区', '经济技术开发区', '中国,江苏,南通,经济技术开发区', '3', '0513', '226000', 'Jingjikaifaqu', 'JJKFQ', 'J', '120.957568', '31.924721'),
('919', '320700', '320000', '连云港市', '中国,江苏省,连云港市', '连云港', '中国,江苏,连云港', '2', '0518', '222000', 'Lianyungang', 'LYG', 'L', '119.178821', '34.600018'),
('920', '320703', '320700', '连云区', '中国,江苏省,连云港市,连云区', '连云', '中国,江苏,连云港,连云', '3', '0518', '222000', 'Lianyun', 'LY', 'L', '119.37304', '34.75293'),
('921', '320706', '320700', '海州区', '中国,江苏省,连云港市,海州区', '海州', '中国,江苏,连云港,海州', '3', '0518', '222000', 'Haizhou', 'HZ', 'H', '119.13128', '34.56986'),
('922', '320707', '320700', '赣榆区', '中国,江苏省,连云港市,赣榆区', '赣榆', '中国,江苏,连云港,赣榆', '3', '0518', '222100', 'Ganyu', 'GY', 'G', '119.128774', '34.839154'),
('923', '320722', '320700', '东海县', '中国,江苏省,连云港市,东海县', '东海', '中国,江苏,连云港,东海', '3', '0518', '222300', 'Donghai', 'DH', 'D', '118.77145', '34.54215'),
('924', '320723', '320700', '灌云县', '中国,江苏省,连云港市,灌云县', '灌云', '中国,江苏,连云港,灌云', '3', '0518', '222200', 'Guanyun', 'GY', 'G', '119.23925', '34.28391'),
('925', '320724', '320700', '灌南县', '中国,江苏省,连云港市,灌南县', '灌南', '中国,江苏,连云港,灌南', '3', '0518', '223500', 'Guannan', 'GN', 'G', '119.35632', '34.09'),
('926', '320725', '320700', '新海新区', '中国,江苏省,连云港市,新海新区', '新海新区', '中国,江苏,连云港,新海新区', '3', '0518', '222006', 'XinhaiXinqu', 'XHXQ', 'X', '119.23295', '34.637054'),
('927', '320726', '320700', '连云新城', '中国,江苏省,连云港市,连云新城', '连云新城', '中国,江苏,连云港,连云新城', '3', '0518', '222000', 'LianyunXincheng', 'LYXC', 'L', '119.343458', '34.755267'),
('928', '320727', '320700', '徐圩新区', '中国,江苏省,连云港市,徐圩新区', '徐圩新区', '中国,江苏,连云港,徐圩新区', '3', '0518', '222000', 'XuWeiXinqu', 'XWXQ', 'X', '119.52352', '34.619731'),
('929', '320728', '320700', '济技术开发区', '中国,江苏省,连云港市,济技术开发区', '济技术开发区', '中国,江苏,连云港,济技术开发区', '3', '0518', '222000', 'Jingjikaifaqu', 'JJKFQ', 'J', '119.344495', '34.694934'),
('930', '320800', '320000', '淮安市', '中国,江苏省,淮安市', '淮安', '中国,江苏,淮安', '2', '0517', '223000', 'Huai\'an', 'HA', 'H', '119.021265', '33.597506'),
('931', '320803', '320800', '淮安区', '中国,江苏省,淮安市,淮安区', '淮安', '中国,江苏,淮安,淮安', '3', '0517', '223200', 'Huai\'an', 'HA', 'H', '119.021265', '33.597506'),
('932', '320804', '320800', '淮阴区', '中国,江苏省,淮安市,淮阴区', '淮阴', '中国,江苏,淮安,淮阴', '3', '0517', '223300', 'Huaiyin', 'HY', 'H', '119.03485', '33.63171'),
('933', '320812', '320800', '清江浦区', '中国,江苏省,淮安市,清江浦区', '清江浦', '中国,江苏,淮安,清江浦', '3', '0517', '223001', 'Qingjiangpu', 'QJP', 'Q', '119.00778', '33.59949'),
('934', '320813', '320800', '洪泽区', '中国,江苏省,淮安市,洪泽区', '洪泽', '中国,江苏,淮安,洪泽', '3', '0517', '223100', 'Hongze', 'HZ', 'H', '118.87344', '33.29429'),
('935', '320826', '320800', '涟水县', '中国,江苏省,淮安市,涟水县', '涟水', '中国,江苏,淮安,涟水', '3', '0517', '223400', 'Lianshui', 'LS', 'L', '119.26083', '33.78094'),
('936', '320830', '320800', '盱眙县', '中国,江苏省,淮安市,盱眙县', '盱眙', '中国,江苏,淮安,盱眙', '3', '0517', '211700', 'Xuyi', 'XY', 'X', '118.54495', '33.01086'),
('937', '320831', '320800', '金湖县', '中国,江苏省,淮安市,金湖县', '金湖', '中国,江苏,淮安,金湖', '3', '0517', '211600', 'Jinhu', 'JH', 'J', '119.02307', '33.02219'),
('938', '320832', '320800', '经济开发区', '中国,江苏省,淮安市,经济开发区', '经济开发区', '中国,江苏,淮安,经济开发区', '3', '0517', '223005', 'Jingjikaifaqu', 'JJKFQ', 'J', '119.02307', '33.02219'),
('939', '320900', '320000', '盐城市', '中国,江苏省,盐城市', '盐城', '中国,江苏,盐城', '2', '0515', '224000', 'Yancheng', 'YC', 'Y', '120.139998', '33.377631'),
('940', '320902', '320900', '亭湖区', '中国,江苏省,盐城市,亭湖区', '亭湖', '中国,江苏,盐城,亭湖', '3', '0515', '224005', 'Tinghu', 'TH', 'T', '120.16583', '33.37825'),
('941', '320903', '320900', '盐都区', '中国,江苏省,盐城市,盐都区', '盐都', '中国,江苏,盐城,盐都', '3', '0515', '224000', 'Yandu', 'YD', 'Y', '120.15441', '33.3373'),
('942', '320904', '320900', '大丰区', '中国,江苏省,盐城市,大丰区', '大丰', '中国,江苏,盐城,大丰', '3', '0515', '224100', 'Dafeng', 'DF', 'D', '120.46594', '33.19893'),
('943', '320921', '320900', '响水县', '中国,江苏省,盐城市,响水县', '响水', '中国,江苏,盐城,响水', '3', '0515', '224600', 'Xiangshui', 'XS', 'X', '119.56985', '34.20513'),
('944', '320922', '320900', '滨海县', '中国,江苏省,盐城市,滨海县', '滨海', '中国,江苏,盐城,滨海', '3', '0515', '224500', 'Binhai', 'BH', 'B', '119.82058', '33.98972'),
('945', '320923', '320900', '阜宁县', '中国,江苏省,盐城市,阜宁县', '阜宁', '中国,江苏,盐城,阜宁', '3', '0515', '224400', 'Funing', 'FN', 'F', '119.80175', '33.78228'),
('946', '320924', '320900', '射阳县', '中国,江苏省,盐城市,射阳县', '射阳', '中国,江苏,盐城,射阳', '3', '0515', '224300', 'Sheyang', 'SY', 'S', '120.26043', '33.77636'),
('947', '320925', '320900', '建湖县', '中国,江苏省,盐城市,建湖县', '建湖', '中国,江苏,盐城,建湖', '3', '0515', '224700', 'Jianhu', 'JH', 'J', '119.79852', '33.47241'),
('948', '320981', '320900', '东台市', '中国,江苏省,盐城市,东台市', '东台', '中国,江苏,盐城,东台', '3', '0515', '224200', 'Dongtai', 'DT', 'D', '120.32376', '32.85078'),
('949', '321000', '320000', '扬州市', '中国,江苏省,扬州市', '扬州', '中国,江苏,扬州', '2', '0514', '225000', 'Yangzhou', 'YZ', 'Y', '119.421003', '32.393159'),
('950', '321002', '321000', '广陵区', '中国,江苏省,扬州市,广陵区', '广陵', '中国,江苏,扬州,广陵', '3', '0514', '225000', 'Guangling', 'GL', 'G', '119.43186', '32.39472'),
('951', '321003', '321000', '邗江区', '中国,江苏省,扬州市,邗江区', '邗江', '中国,江苏,扬州,邗江', '3', '0514', '225100', 'Hanjiang', 'HJ', 'H', '119.39816', '32.3765'),
('952', '321012', '321000', '江都区', '中国,江苏省,扬州市,江都区', '江都', '中国,江苏,扬州,江都', '3', '0514', '225200', 'Jiangdu', 'JD', 'J', '119.567481', '32.426564'),
('953', '321023', '321000', '宝应县', '中国,江苏省,扬州市,宝应县', '宝应', '中国,江苏,扬州,宝应', '3', '0514', '225800', 'Baoying', 'BY', 'B', '119.31213', '33.23549'),
('954', '321081', '321000', '仪征市', '中国,江苏省,扬州市,仪征市', '仪征', '中国,江苏,扬州,仪征', '3', '0514', '211400', 'Yizheng', 'YZ', 'Y', '119.18432', '32.27197'),
('955', '321084', '321000', '高邮市', '中国,江苏省,扬州市,高邮市', '高邮', '中国,江苏,扬州,高邮', '3', '0514', '225600', 'Gaoyou', 'GY', 'G', '119.45965', '32.78135'),
('956', '321100', '320000', '镇江市', '中国,江苏省,镇江市', '镇江', '中国,江苏,镇江', '2', '0511', '212000', 'Zhenjiang', 'ZJ', 'Z', '119.452753', '32.204402'),
('957', '321102', '321100', '京口区', '中国,江苏省,镇江市,京口区', '京口', '中国,江苏,镇江,京口', '3', '0511', '212000', 'Jingkou', 'JK', 'J', '119.46947', '32.19809'),
('958', '321111', '321100', '润州区', '中国,江苏省,镇江市,润州区', '润州', '中国,江苏,镇江,润州', '3', '0511', '212000', 'Runzhou', 'RZ', 'R', '119.41134', '32.19523'),
('959', '321112', '321100', '丹徒区', '中国,江苏省,镇江市,丹徒区', '丹徒', '中国,江苏,镇江,丹徒', '3', '0511', '212100', 'Dantu', 'DT', 'D', '119.43383', '32.13183'),
('960', '321181', '321100', '丹阳市', '中国,江苏省,镇江市,丹阳市', '丹阳', '中国,江苏,镇江,丹阳', '3', '0511', '212300', 'Danyang', 'DY', 'D', '119.57525', '31.99121'),
('961', '321182', '321100', '扬中市', '中国,江苏省,镇江市,扬中市', '扬中', '中国,江苏,镇江,扬中', '3', '0511', '212200', 'Yangzhong', 'YZ', 'Y', '119.79718', '32.2363'),
('962', '321183', '321100', '句容市', '中国,江苏省,镇江市,句容市', '句容', '中国,江苏,镇江,句容', '3', '0511', '212400', 'Jurong', 'JR', 'J', '119.16482', '31.95591'),
('963', '321184', '321100', '镇江新区', '中国,江苏省,镇江市,镇江新区', '镇江新区', '中国,江苏,镇江,镇江新区', '3', '0511', '212000', 'Zhenjiangxinqu', 'ZJXQ', 'Z', '119.674455', '32.167915'),
('964', '321185', '321100', '镇江新区', '中国,江苏省,镇江市,丹徒新区', '丹徒新区', '中国,江苏,镇江,丹徒新区', '3', '0511', '212000', 'Dantuxinqu', 'DTXQ', 'D', '119.434367', '32.127634'),
('965', '321186', '321100', '经济开发区', '中国,江苏省,镇江市,经济开发区', '经济开发区', '中国,江苏,镇江,经济开发区', '3', '0511', '212000', 'Jingjikaifaqu', 'JJKFQ', 'J', '119.665126', '32.16869'),
('966', '321200', '320000', '泰州市', '中国,江苏省,泰州市', '泰州', '中国,江苏,泰州', '2', '0523', '225300', 'Taizhou', 'TZ', 'T', '119.915176', '32.484882'),
('967', '321202', '321200', '海陵区', '中国,江苏省,泰州市,海陵区', '海陵', '中国,江苏,泰州,海陵', '3', '0523', '225300', 'Hailing', 'HL', 'H', '119.91942', '32.49101'),
('968', '321203', '321200', '高港区', '中国,江苏省,泰州市,高港区', '高港', '中国,江苏,泰州,高港', '3', '0523', '225300', 'Gaogang', 'GG', 'G', '119.88089', '32.31833'),
('969', '321204', '321200', '姜堰区', '中国,江苏省,泰州市,姜堰区', '姜堰', '中国,江苏,泰州,姜堰', '3', '0523', '225500', 'Jiangyan', 'JY', 'J', '120.148208', '32.508483'),
('970', '321281', '321200', '兴化市', '中国,江苏省,泰州市,兴化市', '兴化', '中国,江苏,泰州,兴化', '3', '0523', '225700', 'Xinghua', 'XH', 'X', '119.85238', '32.90944'),
('971', '321282', '321200', '靖江市', '中国,江苏省,泰州市,靖江市', '靖江', '中国,江苏,泰州,靖江', '3', '0523', '214500', 'Jingjiang', 'JJ', 'J', '120.27291', '32.01595'),
('972', '321283', '321200', '泰兴市', '中国,江苏省,泰州市,泰兴市', '泰兴', '中国,江苏,泰州,泰兴', '3', '0523', '225400', 'Taixing', 'TX', 'T', '120.05194', '32.17187'),
('973', '321300', '320000', '宿迁市', '中国,江苏省,宿迁市', '宿迁', '中国,江苏,宿迁', '2', '0527', '223800', 'Suqian', 'SQ', 'S', '118.293328', '33.945154'),
('974', '321302', '321300', '宿城区', '中国,江苏省,宿迁市,宿城区', '宿城', '中国,江苏,宿迁,宿城', '3', '0527', '223800', 'Sucheng', 'SC', 'S', '118.29141', '33.94219'),
('975', '321311', '321300', '宿豫区', '中国,江苏省,宿迁市,宿豫区', '宿豫', '中国,江苏,宿迁,宿豫', '3', '0527', '223800', 'Suyu', 'SY', 'S', '118.32922', '33.94673'),
('976', '321322', '321300', '沭阳县', '中国,江苏省,宿迁市,沭阳县', '沭阳', '中国,江苏,宿迁,沭阳', '3', '0527', '223600', 'Shuyang', 'SY', 'S', '118.76873', '34.11446'),
('977', '321323', '321300', '泗阳县', '中国,江苏省,宿迁市,泗阳县', '泗阳', '中国,江苏,宿迁,泗阳', '3', '0527', '223700', 'Siyang', 'SY', 'S', '118.7033', '33.72096'),
('978', '321324', '321300', '泗洪县', '中国,江苏省,宿迁市,泗洪县', '泗洪', '中国,江苏,宿迁,泗洪', '3', '0527', '223900', 'Sihong', 'SH', 'S', '118.21716', '33.45996'),
('979', '321325', '321300', '高新区', '中国,江苏省,宿迁市,高新区', '高新区', '中国,江苏,宿迁,高新区', '3', '0527', '223800', 'Gaoxinqu', 'GXQ', 'G', '118.267378', '33.892135'),
('980', '330000', '100000', '浙江省', '中国,浙江省', '浙江', '中国,浙江', '1', '', '', 'Zhejiang', 'ZJ', 'Z', '120.153576', '30.287459'),
('981', '330100', '330000', '杭州市', '中国,浙江省,杭州市', '杭州', '中国,浙江,杭州', '2', '0571', '310000', 'Hangzhou', 'HZ', 'H', '120.153576', '30.287459'),
('982', '330102', '330100', '上城区', '中国,浙江省,杭州市,上城区', '上城', '中国,浙江,杭州,上城', '3', '0571', '310000', 'Shangcheng', 'SC', 'S', '120.16922', '30.24255'),
('983', '330103', '330100', '下城区', '中国,浙江省,杭州市,下城区', '下城', '中国,浙江,杭州,下城', '3', '0571', '310000', 'Xiacheng', 'XC', 'X', '120.18096', '30.28153'),
('984', '330104', '330100', '江干区', '中国,浙江省,杭州市,江干区', '江干', '中国,浙江,杭州,江干', '3', '0571', '310000', 'Jianggan', 'JG', 'J', '120.20517', '30.2572'),
('985', '330105', '330100', '拱墅区', '中国,浙江省,杭州市,拱墅区', '拱墅', '中国,浙江,杭州,拱墅', '3', '0571', '310000', 'Gongshu', 'GS', 'G', '120.14209', '30.31968'),
('986', '330106', '330100', '西湖区', '中国,浙江省,杭州市,西湖区', '西湖', '中国,浙江,杭州,西湖', '3', '0571', '310000', 'Xihu', 'XH', 'X', '120.12979', '30.25949'),
('987', '330108', '330100', '滨江区', '中国,浙江省,杭州市,滨江区', '滨江', '中国,浙江,杭州,滨江', '3', '0571', '310000', 'Binjiang', 'BJ', 'B', '120.21194', '30.20835'),
('988', '330109', '330100', '萧山区', '中国,浙江省,杭州市,萧山区', '萧山', '中国,浙江,杭州,萧山', '3', '0571', '311200', 'Xiaoshan', 'XS', 'X', '120.26452', '30.18505'),
('989', '330110', '330100', '余杭区', '中国,浙江省,杭州市,余杭区', '余杭', '中国,浙江,杭州,余杭', '3', '0571', '311100', 'Yuhang', 'YH', 'Y', '120.29986', '30.41829'),
('990', '330111', '330100', '富阳区', '中国,浙江省,杭州市,富阳区', '富阳', '中国,浙江,杭州,富阳', '3', '0571', '311400', 'Fuyang', 'FY', 'F', '119.96041', '30.04878'),
('991', '330122', '330100', '桐庐县', '中国,浙江省,杭州市,桐庐县', '桐庐', '中国,浙江,杭州,桐庐', '3', '0571', '311500', 'Tonglu', 'TL', 'T', '119.68853', '29.79779'),
('992', '330127', '330100', '淳安县', '中国,浙江省,杭州市,淳安县', '淳安', '中国,浙江,杭州,淳安', '3', '0571', '311700', 'Chun\'an', 'CA', 'C', '119.04257', '29.60988'),
('993', '330182', '330100', '建德市', '中国,浙江省,杭州市,建德市', '建德', '中国,浙江,杭州,建德', '3', '0571', '311600', 'Jiande', 'JD', 'J', '119.28158', '29.47603'),
('994', '330185', '330100', '临安市', '中国,浙江省,杭州市,临安市', '临安', '中国,浙江,杭州,临安', '3', '0571', '311300', 'Lin\'an', 'LA', 'L', '119.72473', '30.23447'),
('995', '330186', '330100', '高新区', '中国,浙江省,杭州市,高新区', '高新区', '中国,浙江,杭州,高新区', '3', '0571', '310000', 'Gaoxinqu', 'GXQ', 'G', '120.20424', '30.187273'),
('996', '330200', '330000', '宁波市', '中国,浙江省,宁波市', '宁波', '中国,浙江,宁波', '2', '0574', '315000', 'Ningbo', 'NB', 'N', '121.549792', '29.868388'),
('997', '330203', '330200', '海曙区', '中国,浙江省,宁波市,海曙区', '海曙', '中国,浙江,宁波,海曙', '3', '0574', '315000', 'Haishu', 'HS', 'H', '121.55106', '29.85977'),
('998', '330205', '330200', '江北区', '中国,浙江省,宁波市,江北区', '江北', '中国,浙江,宁波,江北', '3', '0574', '315000', 'Jiangbei', 'JB', 'J', '121.55681', '29.88776'),
('999', '330206', '330200', '北仑区', '中国,浙江省,宁波市,北仑区', '北仑', '中国,浙江,宁波,北仑', '3', '0574', '315800', 'Beilun', 'BL', 'B', '121.84408', '29.90069'),
('1000', '330211', '330200', '镇海区', '中国,浙江省,宁波市,镇海区', '镇海', '中国,浙江,宁波,镇海', '3', '0574', '315200', 'Zhenhai', 'ZH', 'Z', '121.71615', '29.94893'),
('1001', '330212', '330200', '鄞州区', '中国,浙江省,宁波市,鄞州区', '鄞州', '中国,浙江,宁波,鄞州', '3', '0574', '315100', 'Yinzhou', 'YZ', 'Y', '121.54754', '29.81614'),
('1002', '330213', '330200', '奉化区', '中国,浙江省,宁波市,奉化区', '奉化', '中国,浙江,宁波,奉化', '3', '0574', '315500', 'Fenghua', 'FH', 'F', '121.41003', '29.65537'),
('1003', '330225', '330200', '象山县', '中国,浙江省,宁波市,象山县', '象山', '中国,浙江,宁波,象山', '3', '0574', '315700', 'Xiangshan', 'XS', 'X', '121.86917', '29.47758'),
('1004', '330226', '330200', '宁海县', '中国,浙江省,宁波市,宁海县', '宁海', '中国,浙江,宁波,宁海', '3', '0574', '315600', 'Ninghai', 'NH', 'N', '121.43072', '29.2889'),
('1005', '330281', '330200', '余姚市', '中国,浙江省,宁波市,余姚市', '余姚', '中国,浙江,宁波,余姚', '3', '0574', '315400', 'Yuyao', 'YY', 'Y', '121.15341', '30.03867'),
('1006', '330282', '330200', '慈溪市', '中国,浙江省,宁波市,慈溪市', '慈溪', '中国,浙江,宁波,慈溪', '3', '0574', '315300', 'Cixi', 'CX', 'C', '121.26641', '30.16959'),
('1007', '330284', '330200', '杭州湾新区', '中国,浙江省,宁波市,杭州湾新区', '杭州湾新区', '中国,浙江,宁波,杭州湾新区', '3', '0574', '315336', 'Hangzhouwanxinqu', 'HZWXQ', 'H', '121.314262', '30.291298'),
('1008', '330285', '330200', '高新区', '中国,浙江省,宁波市,高新区', '高新区', '中国,浙江,宁波,高新区', '3', '0574', '315000', 'Gaoxinqu', 'GXQ', 'G', '121.659487', '29.889258'),
('1009', '330300', '330000', '温州市', '中国,浙江省,温州市', '温州', '中国,浙江,温州', '2', '0577', '325000', 'Wenzhou', 'WZ', 'W', '120.672111', '28.000575'),
('1010', '330302', '330300', '鹿城区', '中国,浙江省,温州市,鹿城区', '鹿城', '中国,浙江,温州,鹿城', '3', '0577', '325000', 'Lucheng', 'LC', 'L', '120.65505', '28.01489'),
('1011', '330303', '330300', '龙湾区', '中国,浙江省,温州市,龙湾区', '龙湾', '中国,浙江,温州,龙湾', '3', '0577', '325000', 'Longwan', 'LW', 'L', '120.83053', '27.91284'),
('1012', '330304', '330300', '瓯海区', '中国,浙江省,温州市,瓯海区', '瓯海', '中国,浙江,温州,瓯海', '3', '0577', '325000', 'Ouhai', 'OH', 'O', '120.63751', '28.00714'),
('1013', '330305', '330300', '洞头区', '中国,浙江省,温州市,洞头区', '洞头', '中国,浙江,温州,洞头', '3', '0577', '325700', 'Dongtou', 'DT', 'D', '121.15606', '27.83634'),
('1014', '330324', '330300', '永嘉县', '中国,浙江省,温州市,永嘉县', '永嘉', '中国,浙江,温州,永嘉', '3', '0577', '325100', 'Yongjia', 'YJ', 'Y', '120.69317', '28.15456'),
('1015', '330326', '330300', '平阳县', '中国,浙江省,温州市,平阳县', '平阳', '中国,浙江,温州,平阳', '3', '0577', '325400', 'Pingyang', 'PY', 'P', '120.56506', '27.66245'),
('1016', '330327', '330300', '苍南县', '中国,浙江省,温州市,苍南县', '苍南', '中国,浙江,温州,苍南', '3', '0577', '325800', 'Cangnan', 'CN', 'C', '120.42608', '27.51739'),
('1017', '330328', '330300', '文成县', '中国,浙江省,温州市,文成县', '文成', '中国,浙江,温州,文成', '3', '0577', '325300', 'Wencheng', 'WC', 'W', '120.09063', '27.78678'),
('1018', '330329', '330300', '泰顺县', '中国,浙江省,温州市,泰顺县', '泰顺', '中国,浙江,温州,泰顺', '3', '0577', '325500', 'Taishun', 'TS', 'T', '119.7182', '27.55694'),
('1019', '330381', '330300', '瑞安市', '中国,浙江省,温州市,瑞安市', '瑞安', '中国,浙江,温州,瑞安', '3', '0577', '325200', 'Rui\'an', 'RA', 'R', '120.65466', '27.78041'),
('1020', '330382', '330300', '乐清市', '中国,浙江省,温州市,乐清市', '乐清', '中国,浙江,温州,乐清', '3', '0577', '325600', 'Yueqing', 'YQ', 'Y', '120.9617', '28.12404'),
('1021', '330400', '330000', '嘉兴市', '中国,浙江省,嘉兴市', '嘉兴', '中国,浙江,嘉兴', '2', '0573', '314000', 'Jiaxing', 'JX', 'J', '120.750865', '30.762653'),
('1022', '330402', '330400', '南湖区', '中国,浙江省,嘉兴市,南湖区', '南湖', '中国,浙江,嘉兴,南湖', '3', '0573', '314051', 'Nanhu', 'NH', 'N', '120.78524', '30.74865'),
('1023', '330411', '330400', '秀洲区', '中国,浙江省,嘉兴市,秀洲区', '秀洲', '中国,浙江,嘉兴,秀洲', '3', '0573', '314031', 'Xiuzhou', 'XZ', 'X', '120.70867', '30.76454'),
('1024', '330421', '330400', '嘉善县', '中国,浙江省,嘉兴市,嘉善县', '嘉善', '中国,浙江,嘉兴,嘉善', '3', '0573', '314100', 'Jiashan', 'JS', 'J', '120.92559', '30.82993'),
('1025', '330424', '330400', '海盐县', '中国,浙江省,嘉兴市,海盐县', '海盐', '中国,浙江,嘉兴,海盐', '3', '0573', '314300', 'Haiyan', 'HY', 'H', '120.9457', '30.52547'),
('1026', '330481', '330400', '海宁市', '中国,浙江省,嘉兴市,海宁市', '海宁', '中国,浙江,嘉兴,海宁', '3', '0573', '314400', 'Haining', 'HN', 'H', '120.6813', '30.5097'),
('1027', '330482', '330400', '平湖市', '中国,浙江省,嘉兴市,平湖市', '平湖', '中国,浙江,嘉兴,平湖', '3', '0573', '314200', 'Pinghu', 'PH', 'P', '121.02166', '30.69618'),
('1028', '330483', '330400', '桐乡市', '中国,浙江省,嘉兴市,桐乡市', '桐乡', '中国,浙江,嘉兴,桐乡', '3', '0573', '314500', 'Tongxiang', 'TX', 'T', '120.56485', '30.6302'),
('1029', '330500', '330000', '湖州市', '中国,浙江省,湖州市', '湖州', '中国,浙江,湖州', '2', '0572', '313000', 'Huzhou', 'HZ', 'H', '120.102398', '30.867198'),
('1030', '330502', '330500', '吴兴区', '中国,浙江省,湖州市,吴兴区', '吴兴', '中国,浙江,湖州,吴兴', '3', '0572', '313000', 'Wuxing', 'WX', 'W', '120.12548', '30.85752'),
('1031', '330503', '330500', '南浔区', '中国,浙江省,湖州市,南浔区', '南浔', '中国,浙江,湖州,南浔', '3', '0572', '313000', 'Nanxun', 'NX', 'N', '120.42038', '30.86686'),
('1032', '330521', '330500', '德清县', '中国,浙江省,湖州市,德清县', '德清', '中国,浙江,湖州,德清', '3', '0572', '313200', 'Deqing', 'DQ', 'D', '119.97836', '30.53369'),
('1033', '330522', '330500', '长兴县', '中国,浙江省,湖州市,长兴县', '长兴', '中国,浙江,湖州,长兴', '3', '0572', '313100', 'Changxing', 'CX', 'C', '119.90783', '31.00606'),
('1034', '330523', '330500', '安吉县', '中国,浙江省,湖州市,安吉县', '安吉', '中国,浙江,湖州,安吉', '3', '0572', '313300', 'Anji', 'AJ', 'A', '119.68158', '30.63798'),
('1035', '330600', '330000', '绍兴市', '中国,浙江省,绍兴市', '绍兴', '中国,浙江,绍兴', '2', '0575', '312000', 'Shaoxing', 'SX', 'S', '120.582112', '29.997117'),
('1036', '330602', '330600', '越城区', '中国,浙江省,绍兴市,越城区', '越城', '中国,浙江,绍兴,越城', '3', '0575', '312000', 'Yuecheng', 'YC', 'Y', '120.5819', '29.98895'),
('1037', '330603', '330600', '柯桥区', '中国,浙江省,绍兴市,柯桥区', '柯桥', '中国,浙江,绍兴,柯桥', '3', '0575', '312030', 'Keqiao ', 'KQ', 'K', '120.492736', '30.08763'),
('1038', '330604', '330600', '上虞区', '中国,浙江省,绍兴市,上虞区', '上虞', '中国,浙江,绍兴,上虞', '3', '0575', '312300', 'Shangyu', 'SY', 'S', '120.476075', '30.078038'),
('1039', '330624', '330600', '新昌县', '中国,浙江省,绍兴市,新昌县', '新昌', '中国,浙江,绍兴,新昌', '3', '0575', '312500', 'Xinchang', 'XC', 'X', '120.90435', '29.49991'),
('1040', '330681', '330600', '诸暨市', '中国,浙江省,绍兴市,诸暨市', '诸暨', '中国,浙江,绍兴,诸暨', '3', '0575', '311800', 'Zhuji', 'ZJ', 'Z', '120.23629', '29.71358'),
('1041', '330683', '330600', '嵊州市', '中国,浙江省,绍兴市,嵊州市', '嵊州', '中国,浙江,绍兴,嵊州', '3', '0575', '312400', 'Shengzhou', 'SZ', 'S', '120.82174', '29.58854'),
('1042', '330700', '330000', '金华市', '中国,浙江省,金华市', '金华', '中国,浙江,金华', '2', '0579', '321000', 'Jinhua', 'JH', 'J', '119.649506', '29.089524'),
('1043', '330702', '330700', '婺城区', '中国,浙江省,金华市,婺城区', '婺城', '中国,浙江,金华,婺城', '3', '0579', '321000', 'Wucheng', 'WC', 'W', '119.57135', '29.09521'),
('1044', '330703', '330700', '金东区', '中国,浙江省,金华市,金东区', '金东', '中国,浙江,金华,金东', '3', '0579', '321000', 'Jindong', 'JD', 'J', '119.69302', '29.0991'),
('1045', '330723', '330700', '武义县', '中国,浙江省,金华市,武义县', '武义', '中国,浙江,金华,武义', '3', '0579', '321200', 'Wuyi', 'WY', 'W', '119.8164', '28.89331'),
('1046', '330726', '330700', '浦江县', '中国,浙江省,金华市,浦江县', '浦江', '中国,浙江,金华,浦江', '3', '0579', '322200', 'Pujiang', 'PJ', 'P', '119.89181', '29.45353'),
('1047', '330727', '330700', '磐安县', '中国,浙江省,金华市,磐安县', '磐安', '中国,浙江,金华,磐安', '3', '0579', '322300', 'Pan\'an', 'PA', 'P', '120.45022', '29.05733'),
('1048', '330781', '330700', '兰溪市', '中国,浙江省,金华市,兰溪市', '兰溪', '中国,浙江,金华,兰溪', '3', '0579', '321100', 'Lanxi', 'LX', 'L', '119.45965', '29.20841'),
('1049', '330782', '330700', '义乌市', '中国,浙江省,金华市,义乌市', '义乌', '中国,浙江,金华,义乌', '3', '0579', '322000', 'Yiwu', 'YW', 'Y', '120.0744', '29.30558'),
('1050', '330783', '330700', '东阳市', '中国,浙江省,金华市,东阳市', '东阳', '中国,浙江,金华,东阳', '3', '0579', '322100', 'Dongyang', 'DY', 'D', '120.24185', '29.28942'),
('1051', '330784', '330700', '永康市', '中国,浙江省,金华市,永康市', '永康', '中国,浙江,金华,永康', '3', '0579', '321300', 'Yongkang', 'YK', 'Y', '120.04727', '28.88844'),
('1052', '330800', '330000', '衢州市', '中国,浙江省,衢州市', '衢州', '中国,浙江,衢州', '2', '0570', '324000', 'Quzhou', 'QZ', 'Q', '118.87263', '28.941708'),
('1053', '330802', '330800', '柯城区', '中国,浙江省,衢州市,柯城区', '柯城', '中国,浙江,衢州,柯城', '3', '0570', '324000', 'Kecheng', 'KC', 'K', '118.87109', '28.96858'),
('1054', '330803', '330800', '衢江区', '中国,浙江省,衢州市,衢江区', '衢江', '中国,浙江,衢州,衢江', '3', '0570', '324000', 'Qujiang', 'QJ', 'Q', '118.9598', '28.97977'),
('1055', '330822', '330800', '常山县', '中国,浙江省,衢州市,常山县', '常山', '中国,浙江,衢州,常山', '3', '0570', '324200', 'Changshan', 'CS', 'C', '118.51025', '28.90191'),
('1056', '330824', '330800', '开化县', '中国,浙江省,衢州市,开化县', '开化', '中国,浙江,衢州,开化', '3', '0570', '324300', 'Kaihua', 'KH', 'K', '118.41616', '29.13785'),
('1057', '330825', '330800', '龙游县', '中国,浙江省,衢州市,龙游县', '龙游', '中国,浙江,衢州,龙游', '3', '0570', '324400', 'Longyou', 'LY', 'L', '119.17221', '29.02823'),
('1058', '330881', '330800', '江山市', '中国,浙江省,衢州市,江山市', '江山', '中国,浙江,衢州,江山', '3', '0570', '324100', 'Jiangshan', 'JS', 'J', '118.62674', '28.7386'),
('1059', '330900', '330000', '舟山市', '中国,浙江省,舟山市', '舟山', '中国,浙江,舟山', '2', '0580', '316000', 'Zhoushan', 'ZS', 'Z', '122.106863', '30.016028'),
('1060', '330902', '330900', '定海区', '中国,浙江省,舟山市,定海区', '定海', '中国,浙江,舟山,定海', '3', '0580', '316000', 'Dinghai', 'DH', 'D', '122.10677', '30.01985'),
('1061', '330903', '330900', '普陀区', '中国,浙江省,舟山市,普陀区', '普陀', '中国,浙江,舟山,普陀', '3', '0580', '316100', 'Putuo', 'PT', 'P', '122.30278', '29.94908'),
('1062', '330921', '330900', '岱山县', '中国,浙江省,舟山市,岱山县', '岱山', '中国,浙江,舟山,岱山', '3', '0580', '316200', 'Daishan', 'DS', 'D', '122.20486', '30.24385'),
('1063', '330922', '330900', '嵊泗县', '中国,浙江省,舟山市,嵊泗县', '嵊泗', '中国,浙江,舟山,嵊泗', '3', '0580', '202450', 'Shengsi', 'SS', 'S', '122.45129', '30.72678'),
('1064', '331000', '330000', '台州市', '中国,浙江省,台州市', '台州', '中国,浙江,台州', '2', '0576', '318000', 'Taizhou', 'TZ', 'T', '121.428599', '28.661378'),
('1065', '331002', '331000', '椒江区', '中国,浙江省,台州市,椒江区', '椒江', '中国,浙江,台州,椒江', '3', '0576', '317700', 'Jiaojiang', 'JJ', 'J', '121.44287', '28.67301'),
('1066', '331003', '331000', '黄岩区', '中国,浙江省,台州市,黄岩区', '黄岩', '中国,浙江,台州,黄岩', '3', '0576', '318020', 'Huangyan', 'HY', 'H', '121.25891', '28.65077'),
('1067', '331004', '331000', '路桥区', '中国,浙江省,台州市,路桥区', '路桥', '中国,浙江,台州,路桥', '3', '0576', '318000', 'Luqiao', 'LQ', 'L', '121.37381', '28.58016'),
('1068', '331021', '331000', '玉环市', '中国,浙江省,台州市,玉环市', '玉环', '中国,浙江,台州,玉环', '3', '0576', '317600', 'Yuhuan', 'YH', 'Y', '121.23242', '28.13637'),
('1069', '331022', '331000', '三门县', '中国,浙江省,台州市,三门县', '三门', '中国,浙江,台州,三门', '3', '0576', '317100', 'Sanmen', 'SM', 'S', '121.3937', '29.1051'),
('1070', '331023', '331000', '天台县', '中国,浙江省,台州市,天台县', '天台', '中国,浙江,台州,天台', '3', '0576', '317200', 'Tiantai', 'TT', 'T', '121.00848', '29.1429'),
('1071', '331024', '331000', '仙居县', '中国,浙江省,台州市,仙居县', '仙居', '中国,浙江,台州,仙居', '3', '0576', '317300', 'Xianju', 'XJ', 'X', '120.72872', '28.84672'),
('1072', '331081', '331000', '温岭市', '中国,浙江省,台州市,温岭市', '温岭', '中国,浙江,台州,温岭', '3', '0576', '317500', 'Wenling', 'WL', 'W', '121.38595', '28.37176'),
('1073', '331082', '331000', '临海市', '中国,浙江省,台州市,临海市', '临海', '中国,浙江,台州,临海', '3', '0576', '317000', 'Linhai', 'LH', 'L', '121.13885', '28.85603'),
('1074', '331100', '330000', '丽水市', '中国,浙江省,丽水市', '丽水', '中国,浙江,丽水', '2', '0578', '323000', 'Lishui', 'LS', 'L', '119.921786', '28.451993'),
('1075', '331102', '331100', '莲都区', '中国,浙江省,丽水市,莲都区', '莲都', '中国,浙江,丽水,莲都', '3', '0578', '323000', 'Liandu', 'LD', 'L', '119.9127', '28.44583'),
('1076', '331121', '331100', '青田县', '中国,浙江省,丽水市,青田县', '青田', '中国,浙江,丽水,青田', '3', '0578', '323900', 'Qingtian', 'QT', 'Q', '120.29028', '28.13897'),
('1077', '331122', '331100', '缙云县', '中国,浙江省,丽水市,缙云县', '缙云', '中国,浙江,丽水,缙云', '3', '0578', '321400', 'Jinyun', 'JY', 'J', '120.09036', '28.65944'),
('1078', '331123', '331100', '遂昌县', '中国,浙江省,丽水市,遂昌县', '遂昌', '中国,浙江,丽水,遂昌', '3', '0578', '323300', 'Suichang', 'SC', 'S', '119.27606', '28.59291'),
('1079', '331124', '331100', '松阳县', '中国,浙江省,丽水市,松阳县', '松阳', '中国,浙江,丽水,松阳', '3', '0578', '323400', 'Songyang', 'SY', 'S', '119.48199', '28.4494'),
('1080', '331125', '331100', '云和县', '中国,浙江省,丽水市,云和县', '云和', '中国,浙江,丽水,云和', '3', '0578', '323600', 'Yunhe', 'YH', 'Y', '119.57287', '28.11643'),
('1081', '331126', '331100', '庆元县', '中国,浙江省,丽水市,庆元县', '庆元', '中国,浙江,丽水,庆元', '3', '0578', '323800', 'Qingyuan', 'QY', 'Q', '119.06256', '27.61842'),
('1082', '331127', '331100', '景宁畲族自治县', '中国,浙江省,丽水市,景宁畲族自治县', '景宁', '中国,浙江,丽水,景宁', '3', '0578', '323500', 'Jingning', 'JN', 'J', '119.63839', '27.97393'),
('1083', '331181', '331100', '龙泉市', '中国,浙江省,丽水市,龙泉市', '龙泉', '中国,浙江,丽水,龙泉', '3', '0578', '323700', 'Longquan', 'LQ', 'L', '119.14163', '28.0743'),
('1084', '331200', '330000', '舟山群岛新区', '中国,浙江省,舟山群岛新区', '舟山新区', '中国,浙江,舟山新区', '2', '0580', '316000', 'Zhoushan', 'ZS', 'Z', '122.317657', '29.813242'),
('1085', '331201', '331200', '金塘岛', '中国,浙江省,舟山群岛新区,金塘岛', '金塘', '中国,浙江,舟山新区,金塘', '3', '0580', '316000', 'Jintang', 'JT', 'J', '121.893373', '30.040641'),
('1086', '331202', '331200', '六横岛', '中国,浙江省,舟山群岛新区,六横岛', '六横', '中国,浙江,舟山新区,六横', '3', '0580', '316000', 'Liuheng', 'LH', 'L', '122.14265', '29.662938'),
('1087', '331203', '331200', '衢山岛', '中国,浙江省,舟山群岛新区,衢山岛', '衢山', '中国,浙江,舟山新区,衢山', '3', '0580', '316000', 'Qushan', 'QS', 'Q', '122.358425', '30.442642'),
('1088', '331204', '331200', '舟山本岛西北部', '中国,浙江省,舟山群岛新区,舟山本岛西北部', '舟山', '中国,浙江,舟山新区,舟山', '3', '0580', '316000', 'Zhoushan', 'ZS', 'Z', '122.03064', '30.140377'),
('1089', '331205', '331200', '岱山岛西南部', '中国,浙江省,舟山群岛新区,岱山岛西南部', '岱山', '中国,浙江,舟山新区,岱山', '3', '0580', '316000', 'Daishan', 'DS', 'D', '122.180123', '30.277269'),
('1090', '331206', '331200', '泗礁岛', '中国,浙江省,舟山群岛新区,泗礁岛', '泗礁', '中国,浙江,舟山新区,泗礁', '3', '0580', '316000', 'Sijiao', 'SJ', 'S', '122.45803', '30.725112'),
('1091', '331207', '331200', '朱家尖岛', '中国,浙江省,舟山群岛新区,朱家尖岛', '朱家尖', '中国,浙江,舟山新区,朱家尖', '3', '0580', '316000', 'Zhujiajian', 'ZJJ', 'Z', '122.390636', '29.916303'),
('1092', '331208', '331200', '洋山岛', '中国,浙江省,舟山群岛新区,洋山岛', '洋山', '中国,浙江,舟山新区,洋山', '3', '0580', '316000', 'Yangshan', 'YS', 'Y', '121.995891', '30.094637'),
('1093', '331209', '331200', '长涂岛', '中国,浙江省,舟山群岛新区,长涂岛', '长涂', '中国,浙江,舟山新区,长涂', '3', '0580', '316000', 'Changtu', 'CT', 'C', '122.284681', '30.24888'),
('1094', '331210', '331200', '虾峙岛', '中国,浙江省,舟山群岛新区,虾峙岛', '虾峙', '中国,浙江,舟山新区,虾峙', '3', '0580', '316000', 'Xiazhi', 'XZ', 'X', '122.244686', '29.752941'),
('1095', '340000', '100000', '安徽省', '中国,安徽省', '安徽', '中国,安徽', '1', '', '', 'Anhui', 'AH', 'A', '117.283042', '31.86119'),
('1096', '340100', '340000', '合肥市', '中国,安徽省,合肥市', '合肥', '中国,安徽,合肥', '2', '0551', '230000', 'Hefei', 'HF', 'H', '117.283042', '31.86119'),
('1097', '340102', '340100', '瑶海区', '中国,安徽省,合肥市,瑶海区', '瑶海', '中国,安徽,合肥,瑶海', '3', '0551', '230000', 'Yaohai', 'YH', 'Y', '117.30947', '31.85809'),
('1098', '340103', '340100', '庐阳区', '中国,安徽省,合肥市,庐阳区', '庐阳', '中国,安徽,合肥,庐阳', '3', '0551', '230000', 'Luyang', 'LY', 'L', '117.26452', '31.87874'),
('1099', '340104', '340100', '蜀山区', '中国,安徽省,合肥市,蜀山区', '蜀山', '中国,安徽,合肥,蜀山', '3', '0551', '230000', 'Shushan', 'SS', 'S', '117.26104', '31.85117'),
('1100', '340111', '340100', '包河区', '中国,安徽省,合肥市,包河区', '包河', '中国,安徽,合肥,包河', '3', '0551', '230000', 'Baohe', 'BH', 'B', '117.30984', '31.79502'),
('1101', '340121', '340100', '长丰县', '中国,安徽省,合肥市,长丰县', '长丰', '中国,安徽,合肥,长丰', '3', '0551', '231100', 'Changfeng', 'CF', 'C', '117.16549', '32.47959'),
('1102', '340122', '340100', '肥东县', '中国,安徽省,合肥市,肥东县', '肥东', '中国,安徽,合肥,肥东', '3', '0551', '230000', 'Feidong', 'FD', 'F', '117.47128', '31.88525'),
('1103', '340123', '340100', '肥西县', '中国,安徽省,合肥市,肥西县', '肥西', '中国,安徽,合肥,肥西', '3', '0551', '231200', 'Feixi', 'FX', 'F', '117.16845', '31.72143'),
('1104', '340124', '340100', '庐江县', '中国,安徽省,合肥市,庐江县', '庐江', '中国,安徽,合肥,庐江', '3', '0551', '231500', 'Lujiang', 'LJ', 'L', '117.289844', '31.251488'),
('1105', '340181', '340100', '巢湖市', '中国,安徽省,合肥市,巢湖市', '巢湖', '中国,安徽,合肥,巢湖', '3', '0551', '238000', 'Chaohu', 'CH', 'C', '117.874155', '31.600518'),
('1106', '340184', '340100', '经济技术开发区', '中国,安徽省,合肥市,经济技术开发区', '经济技术开发区', '中国,安徽,合肥,经济技术开发区', '3', '0551', '230000', 'Jingjikaifaqu', 'JJ', 'J', '117.208822', '31.778893'),
('1107', '340185', '340100', '高新技术开发区', '中国,安徽省,合肥市,高新技术开发区', '高新技术开发区', '中国,安徽,合肥,高新技术开发区', '3', '0551', '230000', 'Gaoxinkaifaqu', 'GX', 'G', '117.13579', '31.83128'),
('1108', '340186', '340100', '北城新区', '中国,安徽省,合肥市,北城新区', '北城新区', '中国,安徽,合肥,北城新区', '3', '0551', '230000', 'Beicheng', 'BC', 'B', '117.247121', '32.030226'),
('1109', '340187', '340100', '滨湖新区', '中国,安徽省,合肥市,滨湖新区', '滨湖新区', '中国,安徽,合肥,滨湖新区', '3', '0551', '230000', 'Binhu', 'BH', 'B', '117.287986', '31.732268'),
('1110', '340188', '340100', '政务文化新区', '中国,安徽省,合肥市,政务文化新区', '政务文化新区', '中国,安徽,合肥,政务文化新区', '3', '0551', '230000', 'Zhengwuwenhua', 'ZW', 'Z', '117.212659', '31.80348'),
('1111', '340189', '340100', '新站综合开发试验区', '中国,安徽省,合肥市,新站综合开发试验区', '新站综合开发试验区', '中国,安徽,合肥,新站综合开发试验区', '3', '0551', '230000', 'Xinzhan', 'XZ', 'X', '117.378188', '31.939811'),
('1112', '340200', '340000', '芜湖市', '中国,安徽省,芜湖市', '芜湖', '中国,安徽,芜湖', '2', '0553', '241000', 'Wuhu', 'WH', 'W', '118.376451', '31.326319'),
('1113', '340202', '340200', '镜湖区', '中国,安徽省,芜湖市,镜湖区', '镜湖', '中国,安徽,芜湖,镜湖', '3', '0553', '241000', 'Jinghu', 'JH', 'J', '118.38525', '31.34038'),
('1114', '340203', '340200', '弋江区', '中国,安徽省,芜湖市,弋江区', '弋江', '中国,安徽,芜湖,弋江', '3', '0553', '241000', 'Yijiang', 'YJ', 'Y', '118.37265', '31.31178'),
('1115', '340207', '340200', '鸠江区', '中国,安徽省,芜湖市,鸠江区', '鸠江', '中国,安徽,芜湖,鸠江', '3', '0553', '241000', 'Jiujiang', 'JJ', 'J', '118.39215', '31.36928'),
('1116', '340208', '340200', '三山区', '中国,安徽省,芜湖市,三山区', '三山', '中国,安徽,芜湖,三山', '3', '0553', '241000', 'Sanshan', 'SS', 'S', '118.22509', '31.20703'),
('1117', '340221', '340200', '芜湖县', '中国,安徽省,芜湖市,芜湖县', '芜湖', '中国,安徽,芜湖,芜湖', '3', '0553', '241100', 'Wuhu', 'WH', 'W', '118.57525', '31.13476'),
('1118', '340222', '340200', '繁昌县', '中国,安徽省,芜湖市,繁昌县', '繁昌', '中国,安徽,芜湖,繁昌', '3', '0553', '241200', 'Fanchang', 'FC', 'F', '118.19982', '31.08319'),
('1119', '340223', '340200', '南陵县', '中国,安徽省,芜湖市,南陵县', '南陵', '中国,安徽,芜湖,南陵', '3', '0553', '242400', 'Nanling', 'NL', 'N', '118.33688', '30.91969'),
('1120', '340225', '340200', '无为县', '中国,安徽省,芜湖市,无为县', '无为', '中国,安徽,芜湖,无为', '3', '0553', '238300', 'Wuwei', 'WW', 'W', '117.911432', '31.303075'),
('1121', '340226', '340200', '经济技术开发区', '中国,安徽省,芜湖市,经济技术开发区', '经济技术开发区', '中国,安徽,芜湖,经济技术开发区', '3', '0553', '241000', 'Jingjikaifaqu', 'JJ', 'J', '118.373839', '31.408279'),
('1122', '340300', '340000', '蚌埠市', '中国,安徽省,蚌埠市', '蚌埠', '中国,安徽,蚌埠', '2', '0552', '233000', 'Bengbu', 'BB', 'B', '117.36237', '32.934037'),
('1123', '340302', '340300', '龙子湖区', '中国,安徽省,蚌埠市,龙子湖区', '龙子湖', '中国,安徽,蚌埠,龙子湖', '3', '0552', '233000', 'Longzihu', 'LZH', 'L', '117.39379', '32.94301'),
('1124', '340303', '340300', '蚌山区', '中国,安徽省,蚌埠市,蚌山区', '蚌山', '中国,安徽,蚌埠,蚌山', '3', '0552', '233000', 'Bengshan', 'BS', 'B', '117.36767', '32.94411'),
('1125', '340304', '340300', '禹会区', '中国,安徽省,蚌埠市,禹会区', '禹会', '中国,安徽,蚌埠,禹会', '3', '0552', '233000', 'Yuhui', 'YH', 'Y', '117.35315', '32.93336'),
('1126', '340311', '340300', '淮上区', '中国,安徽省,蚌埠市,淮上区', '淮上', '中国,安徽,蚌埠,淮上', '3', '0552', '233000', 'Huaishang', 'HS', 'H', '117.35983', '32.96423'),
('1127', '340321', '340300', '怀远县', '中国,安徽省,蚌埠市,怀远县', '怀远', '中国,安徽,蚌埠,怀远', '3', '0552', '233400', 'Huaiyuan', 'HY', 'H', '117.20507', '32.97007'),
('1128', '340322', '340300', '五河县', '中国,安徽省,蚌埠市,五河县', '五河', '中国,安徽,蚌埠,五河', '3', '0552', '233300', 'Wuhe', 'WH', 'W', '117.89144', '33.14457'),
('1129', '340323', '340300', '固镇县', '中国,安徽省,蚌埠市,固镇县', '固镇', '中国,安徽,蚌埠,固镇', '3', '0552', '233700', 'Guzhen', 'GZ', 'G', '117.31558', '33.31803'),
('1130', '340324', '340300', '高新区', '中国,安徽省,蚌埠市,高新区', '高新区', '中国,安徽,蚌埠,高新区', '3', '0552', '233000', 'Gaoxinqu', 'GXQ', 'G', '117.320938', '32.908142'),
('1131', '340400', '340000', '淮南市', '中国,安徽省,淮南市', '淮南', '中国,安徽,淮南', '2', '0554', '232000', 'Huainan', 'HN', 'H', '117.025449', '32.645947'),
('1132', '340402', '340400', '大通区', '中国,安徽省,淮南市,大通区', '大通', '中国,安徽,淮南,大通', '3', '0554', '232000', 'Datong', 'DT', 'D', '117.05255', '32.63265'),
('1133', '340403', '340400', '田家庵区', '中国,安徽省,淮南市,田家庵区', '田家庵', '中国,安徽,淮南,田家庵', '3', '0554', '232000', 'Tianjiaan', 'TJA', 'T', '117.01739', '32.64697'),
('1134', '340404', '340400', '谢家集区', '中国,安徽省,淮南市,谢家集区', '谢家集', '中国,安徽,淮南,谢家集', '3', '0554', '232000', 'Xiejiaji', 'XJJ', 'X', '116.86377', '32.59818'),
('1135', '340405', '340400', '八公山区', '中国,安徽省,淮南市,八公山区', '八公山', '中国,安徽,淮南,八公山', '3', '0554', '232000', 'Bagongshan', 'BGS', 'B', '116.83694', '32.62941'),
('1136', '340406', '340400', '潘集区', '中国,安徽省,淮南市,潘集区', '潘集', '中国,安徽,淮南,潘集', '3', '0554', '232000', 'Panji', 'PJ', 'P', '116.81622', '32.78287'),
('1137', '340421', '340400', '凤台县', '中国,安徽省,淮南市,凤台县', '凤台', '中国,安徽,淮南,凤台', '3', '0554', '232100', 'Fengtai', 'FT', 'F', '116.71569', '32.70752'),
('1138', '340422', '340400', '寿县', '中国,安徽省,淮南市,寿县', '寿县', '中国,安徽,淮南,寿县', '3', '0554', '232200', 'Shouxian', 'SX', 'S', '116.78466', '32.57653'),
('1139', '340423', '340400', '山南新区', '中国,安徽省,淮南市,山南新区', '山南新区', '中国,安徽,淮南,山南新区', '3', '0554', '232000', 'Shannan', 'SN', 'S', '117.025449', '32.645947'),
('1140', '340424', '340400', '毛集实验区', '中国,安徽省,淮南市,毛集实验区', '毛集实验区', '中国,安徽,淮南,毛集实验区', '3', '0554', '232180', 'Maoji', 'MJ', 'M', '116.63552', '32.65471'),
('1141', '340425', '340400', '经济开发区', '中国,安徽省,淮南市,经济开发区', '经济开发区', '中国,安徽,淮南,经济开发区', '3', '0554', '232000', 'Jingjikaifaqu', 'JJ', 'J', '117.063845', '32.660271'),
('1142', '340500', '340000', '马鞍山市', '中国,安徽省,马鞍山市', '马鞍山', '中国,安徽,马鞍山', '2', '0555', '243000', 'Ma\'anshan', 'MAS', 'M', '118.507906', '31.689362'),
('1143', '340503', '340500', '花山区', '中国,安徽省,马鞍山市,花山区', '花山', '中国,安徽,马鞍山,花山', '3', '0555', '243000', 'Huashan', 'HS', 'H', '118.51231', '31.7001'),
('1144', '340504', '340500', '雨山区', '中国,安徽省,马鞍山市,雨山区', '雨山', '中国,安徽,马鞍山,雨山', '3', '0555', '243000', 'Yushan', 'YS', 'Y', '118.49869', '31.68219'),
('1145', '340506', '340500', '博望区', '中国,安徽省,马鞍山市,博望区', '博望', '中国,安徽,马鞍山,博望', '3', '0555', '243131', 'Bowang', 'BW', 'B', '118.844387', '31.561871'),
('1146', '340521', '340500', '当涂县', '中国,安徽省,马鞍山市,当涂县', '当涂', '中国,安徽,马鞍山,当涂', '3', '0555', '243100', 'Dangtu', 'DT', 'D', '118.49786', '31.57098'),
('1147', '340522', '340500', '含山县', '中国,安徽省,马鞍山市,含山县', '含山', '中国,安徽,马鞍山,含山', '3', '0555', '238100', 'Hanshan ', 'HS', 'H', '118.105545', '31.727758'),
('1148', '340523', '340500', '和县', '中国,安徽省,马鞍山市,和县', '和县', '中国,安徽,马鞍山,和县', '3', '0555', '238200', 'Hexian', 'HX', 'H', '118.351405', '31.741794'),
('1149', '340600', '340000', '淮北市', '中国,安徽省,淮北市', '淮北', '中国,安徽,淮北', '2', '0561', '235000', 'Huaibei', 'HB', 'H', '116.794664', '33.971707'),
('1150', '340602', '340600', '杜集区', '中国,安徽省,淮北市,杜集区', '杜集', '中国,安徽,淮北,杜集', '3', '0561', '235000', 'Duji', 'DJ', 'D', '116.82998', '33.99363'),
('1151', '340603', '340600', '相山区', '中国,安徽省,淮北市,相山区', '相山', '中国,安徽,淮北,相山', '3', '0561', '235000', 'Xiangshan', 'XS', 'X', '116.79464', '33.95979'),
('1152', '340604', '340600', '烈山区', '中国,安徽省,淮北市,烈山区', '烈山', '中国,安徽,淮北,烈山', '3', '0561', '235000', 'Lieshan', 'LS', 'L', '116.81448', '33.89355'),
('1153', '340621', '340600', '濉溪县', '中国,安徽省,淮北市,濉溪县', '濉溪', '中国,安徽,淮北,濉溪', '3', '0561', '235100', 'Suixi', 'SX', 'S', '116.76785', '33.91455'),
('1154', '340700', '340000', '铜陵市', '中国,安徽省,铜陵市', '铜陵', '中国,安徽,铜陵', '2', '0562', '244000', 'Tongling', 'TL', 'T', '117.816576', '30.929935'),
('1155', '340705', '340700', '铜官区', '中国,安徽省,铜陵市,铜官区', '铜官', '中国,安徽,铜陵,铜官', '3', '0562', '244000', 'Tongguan', 'TG', 'T', '117.81525', '30.93423'),
('1156', '340706', '340700', '义安区', '中国,安徽省,铜陵市,义安区', '义安', '中国,安徽,铜陵,义安', '3', '0562', '244100', 'Yi\'an', 'YA', 'Y', '117.79113', '30.95365'),
('1157', '340711', '340700', '郊区', '中国,安徽省,铜陵市,郊区', '郊区', '中国,安徽,铜陵,郊区', '3', '0562', '244000', 'Jiaoqu', 'JQ', 'J', '117.80868', '30.91976'),
('1158', '340722', '340700', '枞阳县', '中国,安徽省,铜陵市,枞阳县', '枞阳', '中国,安徽,铜陵,枞阳', '3', '0562', '246700', 'Zongyang', 'ZY', 'Z', '117.22015', '30.69956'),
('1159', '340800', '340000', '安庆市', '中国,安徽省,安庆市', '安庆', '中国,安徽,安庆', '2', '0556', '246000', 'Anqing', 'AQ', 'A', '117.053571', '30.524816'),
('1160', '340802', '340800', '迎江区', '中国,安徽省,安庆市,迎江区', '迎江', '中国,安徽,安庆,迎江', '3', '0556', '246001', 'Yingjiang', 'YJ', 'Y', '117.0493', '30.50421'),
('1161', '340803', '340800', '大观区', '中国,安徽省,安庆市,大观区', '大观', '中国,安徽,安庆,大观', '3', '0556', '246002', 'Daguan', 'DG', 'D', '117.03426', '30.51216'),
('1162', '340811', '340800', '宜秀区', '中国,安徽省,安庆市,宜秀区', '宜秀', '中国,安徽,安庆,宜秀', '3', '0556', '246003', 'Yixiu', 'YX', 'Y', '117.06127', '30.50783'),
('1163', '340822', '340800', '怀宁县', '中国,安徽省,安庆市,怀宁县', '怀宁', '中国,安徽,安庆,怀宁', '3', '0556', '246100', 'Huaining', 'HN', 'H', '116.82968', '30.73376'),
('1164', '340824', '340800', '潜山县', '中国,安徽省,安庆市,潜山县', '潜山', '中国,安徽,安庆,潜山', '3', '0556', '246300', 'Qianshan', 'QS', 'Q', '116.57574', '30.63037'),
('1165', '340825', '340800', '太湖县', '中国,安徽省,安庆市,太湖县', '太湖', '中国,安徽,安庆,太湖', '3', '0556', '246400', 'Taihu', 'TH', 'T', '116.3088', '30.4541'),
('1166', '340826', '340800', '宿松县', '中国,安徽省,安庆市,宿松县', '宿松', '中国,安徽,安庆,宿松', '3', '0556', '246500', 'Susong', 'SS', 'S', '116.12915', '30.1536'),
('1167', '340827', '340800', '望江县', '中国,安徽省,安庆市,望江县', '望江', '中国,安徽,安庆,望江', '3', '0556', '246200', 'Wangjiang', 'WJ', 'W', '116.68814', '30.12585'),
('1168', '340828', '340800', '岳西县', '中国,安徽省,安庆市,岳西县', '岳西', '中国,安徽,安庆,岳西', '3', '0556', '246600', 'Yuexi', 'YX', 'Y', '116.35995', '30.84983'),
('1169', '340881', '340800', '桐城市', '中国,安徽省,安庆市,桐城市', '桐城', '中国,安徽,安庆,桐城', '3', '0556', '231400', 'Tongcheng', 'TC', 'T', '116.95071', '31.05216'),
('1170', '341000', '340000', '黄山市', '中国,安徽省,黄山市', '黄山', '中国,安徽,黄山', '2', '0559', '242700', 'Huangshan', 'HS', 'H', '118.317325', '29.709239'),
('1171', '341002', '341000', '屯溪区', '中国,安徽省,黄山市,屯溪区', '屯溪', '中国,安徽,黄山,屯溪', '3', '0559', '245000', 'Tunxi', 'TX', 'T', '118.33368', '29.71138'),
('1172', '341003', '341000', '黄山区', '中国,安徽省,黄山市,黄山区', '黄山', '中国,安徽,黄山,黄山', '3', '0559', '242700', 'Huangshan', 'HS', 'H', '118.1416', '30.2729'),
('1173', '341004', '341000', '徽州区', '中国,安徽省,黄山市,徽州区', '徽州', '中国,安徽,黄山,徽州', '3', '0559', '245061', 'Huizhou', 'HZ', 'H', '118.33654', '29.82784'),
('1174', '341021', '341000', '歙县', '中国,安徽省,黄山市,歙县', '歙县', '中国,安徽,黄山,歙县', '3', '0559', '245200', 'Shexian', 'SX', 'S', '118.43676', '29.86745'),
('1175', '341022', '341000', '休宁县', '中国,安徽省,黄山市,休宁县', '休宁', '中国,安徽,黄山,休宁', '3', '0559', '245400', 'Xiuning', 'XN', 'X', '118.18136', '29.78607'),
('1176', '341023', '341000', '黟县', '中国,安徽省,黄山市,黟县', '黟县', '中国,安徽,黄山,黟县', '3', '0559', '245500', 'Yixian', 'YX', 'Y', '117.94137', '29.92588'),
('1177', '341024', '341000', '祁门县', '中国,安徽省,黄山市,祁门县', '祁门', '中国,安徽,黄山,祁门', '3', '0559', '245600', 'Qimen', 'QM', 'Q', '117.71847', '29.85723'),
('1178', '341100', '340000', '滁州市', '中国,安徽省,滁州市', '滁州', '中国,安徽,滁州', '2', '0550', '239000', 'Chuzhou', 'CZ', 'C', '118.316264', '32.303627'),
('1179', '341102', '341100', '琅琊区', '中国,安徽省,滁州市,琅琊区', '琅琊', '中国,安徽,滁州,琅琊', '3', '0550', '239000', 'Langya', 'LY', 'L', '118.30538', '32.29521'),
('1180', '341103', '341100', '南谯区', '中国,安徽省,滁州市,南谯区', '南谯', '中国,安徽,滁州,南谯', '3', '0550', '239000', 'Nanqiao', 'NQ', 'N', '118.31222', '32.31861'),
('1181', '341122', '341100', '来安县', '中国,安徽省,滁州市,来安县', '来安', '中国,安徽,滁州,来安', '3', '0550', '239200', 'Lai\'an', 'LA', 'L', '118.43438', '32.45176'),
('1182', '341124', '341100', '全椒县', '中国,安徽省,滁州市,全椒县', '全椒', '中国,安徽,滁州,全椒', '3', '0550', '239500', 'Quanjiao', 'QJ', 'Q', '118.27291', '32.08524'),
('1183', '341125', '341100', '定远县', '中国,安徽省,滁州市,定远县', '定远', '中国,安徽,滁州,定远', '3', '0550', '233200', 'Dingyuan', 'DY', 'D', '117.68035', '32.52488'),
('1184', '341126', '341100', '凤阳县', '中国,安徽省,滁州市,凤阳县', '凤阳', '中国,安徽,滁州,凤阳', '3', '0550', '233100', 'Fengyang', 'FY', 'F', '117.56454', '32.86507'),
('1185', '341181', '341100', '天长市', '中国,安徽省,滁州市,天长市', '天长', '中国,安徽,滁州,天长', '3', '0550', '239300', 'Tianchang', 'TC', 'T', '118.99868', '32.69124'),
('1186', '341182', '341100', '明光市', '中国,安徽省,滁州市,明光市', '明光', '中国,安徽,滁州,明光', '3', '0550', '239400', 'Mingguang', 'MG', 'M', '117.99093', '32.77819'),
('1187', '341200', '340000', '阜阳市', '中国,安徽省,阜阳市', '阜阳', '中国,安徽,阜阳', '2', '0558', '236000', 'Fuyang', 'FY', 'F', '115.819729', '32.896969'),
('1188', '341202', '341200', '颍州区', '中国,安徽省,阜阳市,颍州区', '颍州', '中国,安徽,阜阳,颍州', '3', '0558', '236000', 'Yingzhou', 'YZ', 'Y', '115.80694', '32.88346'),
('1189', '341203', '341200', '颍东区', '中国,安徽省,阜阳市,颍东区', '颍东', '中国,安徽,阜阳,颍东', '3', '0558', '236000', 'Yingdong', 'YD', 'Y', '115.85659', '32.91296'),
('1190', '341204', '341200', '颍泉区', '中国,安徽省,阜阳市,颍泉区', '颍泉', '中国,安徽,阜阳,颍泉', '3', '0558', '236000', 'Yingquan', 'YQ', 'Y', '115.80712', '32.9249'),
('1191', '341221', '341200', '临泉县', '中国,安徽省,阜阳市,临泉县', '临泉', '中国,安徽,阜阳,临泉', '3', '0558', '236400', 'Linquan', 'LQ', 'L', '115.26232', '33.06758'),
('1192', '341222', '341200', '太和县', '中国,安徽省,阜阳市,太和县', '太和', '中国,安徽,阜阳,太和', '3', '0558', '236600', 'Taihe', 'TH', 'T', '115.62191', '33.16025'),
('1193', '341225', '341200', '阜南县', '中国,安徽省,阜阳市,阜南县', '阜南', '中国,安徽,阜阳,阜南', '3', '0558', '236300', 'Funan', 'FN', 'F', '115.58563', '32.63551'),
('1194', '341226', '341200', '颍上县', '中国,安徽省,阜阳市,颍上县', '颍上', '中国,安徽,阜阳,颍上', '3', '0558', '236200', 'Yingshang', 'YS', 'Y', '116.26458', '32.62998'),
('1195', '341282', '341200', '界首市', '中国,安徽省,阜阳市,界首市', '界首', '中国,安徽,阜阳,界首', '3', '0558', '236500', 'Jieshou', 'JS', 'J', '115.37445', '33.25714'),
('1196', '341283', '341200', '经济开发区', '中国,安徽省,阜阳市,经济开发区', '经济开发区', '中国,安徽,阜阳,经济开发区', '3', '0558', '236000', 'Jingjikaifaqu', 'JJKFQ', 'J', '115.857371', '32.865142'),
('1197', '341300', '340000', '宿州市', '中国,安徽省,宿州市', '宿州', '中国,安徽,宿州', '2', '0557', '234000', 'Suzhou', 'SZ', 'S', '116.984084', '33.633891'),
('1198', '341302', '341300', '埇桥区', '中国,安徽省,宿州市,埇桥区', '埇桥', '中国,安徽,宿州,埇桥', '3', '0557', '234000', 'Yongqiao', 'YQ', 'Y', '116.97731', '33.64058'),
('1199', '341321', '341300', '砀山县', '中国,安徽省,宿州市,砀山县', '砀山', '中国,安徽,宿州,砀山', '3', '0557', '235300', 'Dangshan', 'DS', 'D', '116.35363', '34.42356'),
('1200', '341322', '341300', '萧县', '中国,安徽省,宿州市,萧县', '萧县', '中国,安徽,宿州,萧县', '3', '0557', '235200', 'Xiaoxian', 'XX', 'X', '116.94546', '34.1879'),
('1201', '341323', '341300', '灵璧县', '中国,安徽省,宿州市,灵璧县', '灵璧', '中国,安徽,宿州,灵璧', '3', '0557', '234200', 'Lingbi', 'LB', 'L', '117.55813', '33.54339'),
('1202', '341324', '341300', '泗县', '中国,安徽省,宿州市,泗县', '泗县', '中国,安徽,宿州,泗县', '3', '0557', '234300', 'Sixian', 'SX', 'S', '117.91033', '33.48295'),
('1203', '341325', '341300', '经济开发区', '中国,安徽省,宿州市,经济开发区', '经济开发区', '中国,安徽,宿州,经济开发区', '3', '0557', '234000', 'Jingjikaifaqu', 'JJKFQ', 'J', '117.010215', '33.59863'),
('1204', '341500', '340000', '六安市', '中国,安徽省,六安市', '六安', '中国,安徽,六安', '2', '0564', '237000', 'Lu\'an', 'LA', 'L', '116.507676', '31.752889'),
('1205', '341502', '341500', '金安区', '中国,安徽省,六安市,金安区', '金安', '中国,安徽,六安,金安', '3', '0564', '237000', 'Jin\'an', 'JA', 'J', '116.50912', '31.75573'),
('1206', '341503', '341500', '裕安区', '中国,安徽省,六安市,裕安区', '裕安', '中国,安徽,六安,裕安', '3', '0564', '237000', 'Yu\'an', 'YA', 'Y', '116.47985', '31.73787'),
('1207', '341504', '341500', '叶集区', '中国,安徽省,六安市,叶集区', '叶集', '中国,安徽,六安,叶集', '3', '0564', '237431', 'Yeji', 'YJ', 'Y', '115.94795', '31.863'),
('1208', '341522', '341500', '霍邱县', '中国,安徽省,六安市,霍邱县', '霍邱', '中国,安徽,六安,霍邱', '3', '0564', '237400', 'Huoqiu', 'HQ', 'H', '116.27795', '32.353'),
('1209', '341523', '341500', '舒城县', '中国,安徽省,六安市,舒城县', '舒城', '中国,安徽,六安,舒城', '3', '0564', '231300', 'Shucheng', 'SC', 'S', '116.94491', '31.46413'),
('1210', '341524', '341500', '金寨县', '中国,安徽省,六安市,金寨县', '金寨', '中国,安徽,六安,金寨', '3', '0564', '237300', 'Jinzhai', 'JZ', 'J', '115.93463', '31.7351'),
('1211', '341525', '341500', '霍山县', '中国,安徽省,六安市,霍山县', '霍山', '中国,安徽,六安,霍山', '3', '0564', '237200', 'Huoshan', 'HS', 'H', '116.33291', '31.3929'),
('1212', '341600', '340000', '亳州市', '中国,安徽省,亳州市', '亳州', '中国,安徽,亳州', '2', '0558', '236000', 'Bozhou', 'BZ', 'B', '115.782939', '33.869338'),
('1213', '341602', '341600', '谯城区', '中国,安徽省,亳州市,谯城区', '谯城', '中国,安徽,亳州,谯城', '3', '0558', '236800', 'Qiaocheng', 'QC', 'Q', '115.77941', '33.87532'),
('1214', '341621', '341600', '涡阳县', '中国,安徽省,亳州市,涡阳县', '涡阳', '中国,安徽,亳州,涡阳', '3', '0558', '233600', 'Guoyang', 'GY', 'G', '116.21682', '33.50911'),
('1215', '341622', '341600', '蒙城县', '中国,安徽省,亳州市,蒙城县', '蒙城', '中国,安徽,亳州,蒙城', '3', '0558', '233500', 'Mengcheng', 'MC', 'M', '116.5646', '33.26477'),
('1216', '341623', '341600', '利辛县', '中国,安徽省,亳州市,利辛县', '利辛', '中国,安徽,亳州,利辛', '3', '0558', '236700', 'Lixin', 'LX', 'L', '116.208', '33.14198'),
('1217', '341700', '340000', '池州市', '中国,安徽省,池州市', '池州', '中国,安徽,池州', '2', '0566', '247100', 'Chizhou', 'CZ', 'C', '117.489157', '30.656037'),
('1218', '341702', '341700', '贵池区', '中国,安徽省,池州市,贵池区', '贵池', '中国,安徽,池州,贵池', '3', '0566', '247100', 'Guichi', 'GC', 'G', '117.48722', '30.65283'),
('1219', '341721', '341700', '东至县', '中国,安徽省,池州市,东至县', '东至', '中国,安徽,池州,东至', '3', '0566', '247200', 'Dongzhi', 'DZ', 'D', '117.02719', '30.0969'),
('1220', '341722', '341700', '石台县', '中国,安徽省,池州市,石台县', '石台', '中国,安徽,池州,石台', '3', '0566', '245100', 'Shitai', 'ST', 'S', '117.48666', '30.21042'),
('1221', '341723', '341700', '青阳县', '中国,安徽省,池州市,青阳县', '青阳', '中国,安徽,池州,青阳', '3', '0566', '242800', 'Qingyang', 'QY', 'Q', '117.84744', '30.63932'),
('1222', '341800', '340000', '宣城市', '中国,安徽省,宣城市', '宣城', '中国,安徽,宣城', '2', '0563', '242000', 'Xuancheng', 'XC', 'X', '118.757995', '30.945667'),
('1223', '341802', '341800', '宣州区', '中国,安徽省,宣城市,宣州区', '宣州', '中国,安徽,宣城,宣州', '3', '0563', '242000', 'Xuanzhou', 'XZ', 'X', '118.75462', '30.94439'),
('1224', '341821', '341800', '郎溪县', '中国,安徽省,宣城市,郎溪县', '郎溪', '中国,安徽,宣城,郎溪', '3', '0563', '242100', 'Langxi', 'LX', 'L', '119.17923', '31.12599'),
('1225', '341822', '341800', '广德县', '中国,安徽省,宣城市,广德县', '广德', '中国,安徽,宣城,广德', '3', '0563', '242200', 'Guangde', 'GD', 'G', '119.41769', '30.89371'),
('1226', '341823', '341800', '泾县', '中国,安徽省,宣城市,泾县', '泾县', '中国,安徽,宣城,泾县', '3', '0563', '242500', 'Jingxian', 'JX', 'J', '118.41964', '30.69498'),
('1227', '341824', '341800', '绩溪县', '中国,安徽省,宣城市,绩溪县', '绩溪', '中国,安徽,宣城,绩溪', '3', '0563', '245300', 'Jixi', 'JX', 'J', '118.59765', '30.07069'),
('1228', '341825', '341800', '旌德县', '中国,安徽省,宣城市,旌德县', '旌德', '中国,安徽,宣城,旌德', '3', '0563', '242600', 'Jingde', 'JD', 'J', '118.54299', '30.28898'),
('1229', '341881', '341800', '宁国市', '中国,安徽省,宣城市,宁国市', '宁国', '中国,安徽,宣城,宁国', '3', '0563', '242300', 'Ningguo', 'NG', 'N', '118.98349', '30.6238'),
('1230', '350000', '100000', '福建省', '中国,福建省', '福建', '中国,福建', '1', '', '', 'Fujian', 'FJ', 'F', '119.306239', '26.075302'),
('1231', '350100', '350000', '福州市', '中国,福建省,福州市', '福州', '中国,福建,福州', '2', '0591', '350000', 'Fuzhou', 'FZ', 'F', '119.306239', '26.075302'),
('1232', '350102', '350100', '鼓楼区', '中国,福建省,福州市,鼓楼区', '鼓楼', '中国,福建,福州,鼓楼', '3', '0591', '350000', 'Gulou', 'GL', 'G', '119.30384', '26.08225'),
('1233', '350103', '350100', '台江区', '中国,福建省,福州市,台江区', '台江', '中国,福建,福州,台江', '3', '0591', '350000', 'Taijiang', 'TJ', 'T', '119.30899', '26.06204'),
('1234', '350104', '350100', '仓山区', '中国,福建省,福州市,仓山区', '仓山', '中国,福建,福州,仓山', '3', '0591', '350000', 'Cangshan', 'CS', 'C', '119.31543', '26.04335'),
('1235', '350105', '350100', '马尾区', '中国,福建省,福州市,马尾区', '马尾', '中国,福建,福州,马尾', '3', '0591', '350000', 'Mawei', 'MW', 'M', '119.4555', '25.98942'),
('1236', '350111', '350100', '晋安区', '中国,福建省,福州市,晋安区', '晋安', '中国,福建,福州,晋安', '3', '0591', '350000', 'Jin\'an', 'JA', 'J', '119.32828', '26.0818'),
('1237', '350121', '350100', '闽侯县', '中国,福建省,福州市,闽侯县', '闽侯', '中国,福建,福州,闽侯', '3', '0591', '350100', 'Minhou', 'MH', 'M', '119.13388', '26.15014'),
('1238', '350122', '350100', '连江县', '中国,福建省,福州市,连江县', '连江', '中国,福建,福州,连江', '3', '0591', '350500', 'Lianjiang', 'LJ', 'L', '119.53433', '26.19466'),
('1239', '350123', '350100', '罗源县', '中国,福建省,福州市,罗源县', '罗源', '中国,福建,福州,罗源', '3', '0591', '350600', 'Luoyuan', 'LY', 'L', '119.5509', '26.48752'),
('1240', '350124', '350100', '闽清县', '中国,福建省,福州市,闽清县', '闽清', '中国,福建,福州,闽清', '3', '0591', '350800', 'Minqing', 'MQ', 'M', '118.8623', '26.21901'),
('1241', '350125', '350100', '永泰县', '中国,福建省,福州市,永泰县', '永泰', '中国,福建,福州,永泰', '3', '0591', '350700', 'Yongtai', 'YT', 'Y', '118.936', '25.86816'),
('1242', '350128', '350100', '平潭县', '中国,福建省,福州市,平潭县', '平潭', '中国,福建,福州,平潭', '3', '0591', '350400', 'Pingtan', 'PT', 'P', '119.791197', '25.503672'),
('1243', '350181', '350100', '福清市', '中国,福建省,福州市,福清市', '福清', '中国,福建,福州,福清', '3', '0591', '350300', 'Fuqing', 'FQ', 'F', '119.38507', '25.72086'),
('1244', '350182', '350100', '长乐市', '中国,福建省,福州市,长乐市', '长乐', '中国,福建,福州,长乐', '3', '0591', '350200', 'Changle', 'CL', 'C', '119.52313', '25.96276'),
('1245', '350183', '350100', '福州新区', '中国,福建省,福州市,福州新区', '福州新区', '中国,福建,福州,福州新区', '3', '0591', '350000', 'FuzhouXinqu', 'FZXQ', 'F', '119.266079', '26.050692'),
('1246', '350200', '350000', '厦门市', '中国,福建省,厦门市', '厦门', '中国,福建,厦门', '2', '0592', '361000', 'Xiamen', 'XM', 'X', '118.11022', '24.490474'),
('1247', '350203', '350200', '思明区', '中国,福建省,厦门市,思明区', '思明', '中国,福建,厦门,思明', '3', '0592', '361000', 'Siming', 'SM', 'S', '118.08233', '24.44543'),
('1248', '350205', '350200', '海沧区', '中国,福建省,厦门市,海沧区', '海沧', '中国,福建,厦门,海沧', '3', '0592', '361000', 'Haicang', 'HC', 'H', '118.03289', '24.48461'),
('1249', '350206', '350200', '湖里区', '中国,福建省,厦门市,湖里区', '湖里', '中国,福建,厦门,湖里', '3', '0592', '361000', 'Huli', 'HL', 'H', '118.14621', '24.51253'),
('1250', '350211', '350200', '集美区', '中国,福建省,厦门市,集美区', '集美', '中国,福建,厦门,集美', '3', '0592', '361000', 'Jimei', 'JM', 'J', '118.09719', '24.57584'),
('1251', '350212', '350200', '同安区', '中国,福建省,厦门市,同安区', '同安', '中国,福建,厦门,同安', '3', '0592', '361100', 'Tong\'an', 'TA', 'T', '118.15197', '24.72308'),
('1252', '350213', '350200', '翔安区', '中国,福建省,厦门市,翔安区', '翔安', '中国,福建,厦门,翔安', '3', '0592', '361100', 'Xiang\'an', 'XA', 'X', '118.24783', '24.61863'),
('1253', '350300', '350000', '莆田市', '中国,福建省,莆田市', '莆田', '中国,福建,莆田', '2', '0594', '351100', 'Putian', 'PT', 'P', '119.007558', '25.431011'),
('1254', '350302', '350300', '城厢区', '中国,福建省,莆田市,城厢区', '城厢', '中国,福建,莆田,城厢', '3', '0594', '351100', 'Chengxiang', 'CX', 'C', '118.99462', '25.41872'),
('1255', '350303', '350300', '涵江区', '中国,福建省,莆田市,涵江区', '涵江', '中国,福建,莆田,涵江', '3', '0594', '351100', 'Hanjiang', 'HJ', 'H', '119.11621', '25.45876'),
('1256', '350304', '350300', '荔城区', '中国,福建省,莆田市,荔城区', '荔城', '中国,福建,莆田,荔城', '3', '0594', '351100', 'Licheng', 'LC', 'L', '119.01339', '25.43369'),
('1257', '350305', '350300', '秀屿区', '中国,福建省,莆田市,秀屿区', '秀屿', '中国,福建,莆田,秀屿', '3', '0594', '351100', 'Xiuyu', 'XY', 'X', '119.10553', '25.31831'),
('1258', '350322', '350300', '仙游县', '中国,福建省,莆田市,仙游县', '仙游', '中国,福建,莆田,仙游', '3', '0594', '351200', 'Xianyou', 'XY', 'X', '118.69177', '25.36214'),
('1259', '350400', '350000', '三明市', '中国,福建省,三明市', '三明', '中国,福建,三明', '2', '0598', '365000', 'Sanming', 'SM', 'S', '117.635001', '26.265444'),
('1260', '350402', '350400', '梅列区', '中国,福建省,三明市,梅列区', '梅列', '中国,福建,三明,梅列', '3', '0598', '365000', 'Meilie', 'ML', 'M', '117.64585', '26.27171'),
('1261', '350403', '350400', '三元区', '中国,福建省,三明市,三元区', '三元', '中国,福建,三明,三元', '3', '0598', '365000', 'Sanyuan', 'SY', 'S', '117.60788', '26.23372'),
('1262', '350421', '350400', '明溪县', '中国,福建省,三明市,明溪县', '明溪', '中国,福建,三明,明溪', '3', '0598', '365200', 'Mingxi', 'MX', 'M', '117.20498', '26.35294'),
('1263', '350423', '350400', '清流县', '中国,福建省,三明市,清流县', '清流', '中国,福建,三明,清流', '3', '0598', '365300', 'Qingliu', 'QL', 'Q', '116.8146', '26.17144'),
('1264', '350424', '350400', '宁化县', '中国,福建省,三明市,宁化县', '宁化', '中国,福建,三明,宁化', '3', '0598', '365400', 'Ninghua', 'NH', 'N', '116.66101', '26.25874'),
('1265', '350425', '350400', '大田县', '中国,福建省,三明市,大田县', '大田', '中国,福建,三明,大田', '3', '0598', '366100', 'Datian', 'DT', 'D', '117.8471', '25.6926'),
('1266', '350426', '350400', '尤溪县', '中国,福建省,三明市,尤溪县', '尤溪', '中国,福建,三明,尤溪', '3', '0598', '365100', 'Youxi', 'YX', 'Y', '118.19049', '26.17002'),
('1267', '350427', '350400', '沙县', '中国,福建省,三明市,沙县', '沙县', '中国,福建,三明,沙县', '3', '0598', '365500', 'Shaxian', 'SX', 'S', '117.79266', '26.39615'),
('1268', '350428', '350400', '将乐县', '中国,福建省,三明市,将乐县', '将乐', '中国,福建,三明,将乐', '3', '0598', '353300', 'Jiangle', 'JL', 'J', '117.47317', '26.72837'),
('1269', '350429', '350400', '泰宁县', '中国,福建省,三明市,泰宁县', '泰宁', '中国,福建,三明,泰宁', '3', '0598', '354400', 'Taining', 'TN', 'T', '117.17578', '26.9001'),
('1270', '350430', '350400', '建宁县', '中国,福建省,三明市,建宁县', '建宁', '中国,福建,三明,建宁', '3', '0598', '354500', 'Jianning', 'JN', 'J', '116.84603', '26.83091'),
('1271', '350481', '350400', '永安市', '中国,福建省,三明市,永安市', '永安', '中国,福建,三明,永安', '3', '0598', '366000', 'Yong\'an', 'YA', 'Y', '117.36517', '25.94136'),
('1272', '350500', '350000', '泉州市', '中国,福建省,泉州市', '泉州', '中国,福建,泉州', '2', '0595', '362000', 'Quanzhou', 'QZ', 'Q', '118.589421', '24.908853'),
('1273', '350502', '350500', '鲤城区', '中国,福建省,泉州市,鲤城区', '鲤城', '中国,福建,泉州,鲤城', '3', '0595', '362000', 'Licheng', 'LC', 'L', '118.56591', '24.88741'),
('1274', '350503', '350500', '丰泽区', '中国,福建省,泉州市,丰泽区', '丰泽', '中国,福建,泉州,丰泽', '3', '0595', '362000', 'Fengze', 'FZ', 'F', '118.61328', '24.89119'),
('1275', '350504', '350500', '洛江区', '中国,福建省,泉州市,洛江区', '洛江', '中国,福建,泉州,洛江', '3', '0595', '362000', 'Luojiang', 'LJ', 'L', '118.67111', '24.93984'),
('1276', '350505', '350500', '泉港区', '中国,福建省,泉州市,泉港区', '泉港', '中国,福建,泉州,泉港', '3', '0595', '362800', 'Quangang', 'QG', 'Q', '118.91586', '25.12005'),
('1277', '350521', '350500', '惠安县', '中国,福建省,泉州市,惠安县', '惠安', '中国,福建,泉州,惠安', '3', '0595', '362100', 'Hui\'an', 'HA', 'H', '118.79687', '25.03059'),
('1278', '350524', '350500', '安溪县', '中国,福建省,泉州市,安溪县', '安溪', '中国,福建,泉州,安溪', '3', '0595', '362400', 'Anxi', 'AX', 'A', '118.18719', '25.05627'),
('1279', '350525', '350500', '永春县', '中国,福建省,泉州市,永春县', '永春', '中国,福建,泉州,永春', '3', '0595', '362600', 'Yongchun', 'YC', 'Y', '118.29437', '25.32183'),
('1280', '350526', '350500', '德化县', '中国,福建省,泉州市,德化县', '德化', '中国,福建,泉州,德化', '3', '0595', '362500', 'Dehua', 'DH', 'D', '118.24176', '25.49224'),
('1281', '350527', '350500', '金门县', '中国,福建省,泉州市,金门县', '金门', '中国,福建,泉州,金门', '3', '0595', '362000', 'Jinmen', 'JM', 'J', '118.32263', '24.42922'),
('1282', '350581', '350500', '石狮市', '中国,福建省,泉州市,石狮市', '石狮', '中国,福建,泉州,石狮', '3', '0595', '362700', 'Shishi', 'SS', 'S', '118.64779', '24.73242'),
('1283', '350582', '350500', '晋江市', '中国,福建省,泉州市,晋江市', '晋江', '中国,福建,泉州,晋江', '3', '0595', '362200', 'Jinjiang', 'JJ', 'J', '118.55194', '24.78141'),
('1284', '350583', '350500', '南安市', '中国,福建省,泉州市,南安市', '南安', '中国,福建,泉州,南安', '3', '0595', '362300', 'Nan\'an', 'NA', 'N', '118.38589', '24.96055'),
('1285', '350584', '350500', '台商投资区', '中国,福建省,泉州市,台商投资区', '台商投资区', '中国,福建,泉州,台商投资区', '3', '0595', '362000', 'Taishangtouziqu', 'TS', 'T', '118.756072', '24.885957'),
('1286', '350585', '350500', '经济技术开发区', '中国,福建省,泉州市,经济技术开发区', '经济技术开发区', '中国,福建,泉州,经济技术开发区', '3', '0595', '362000', 'JingJi', 'JJ', 'J', '118.548114', '24.865094'),
('1287', '350586', '350500', '高新技术开发区', '中国,福建省,泉州市,高新技术开发区', '高新技术开发区', '中国,福建,泉州,高新技术开发区', '3', '0595', '362000', 'Gaoxin', 'GX', 'G', '118.545131', '24.904931'),
('1288', '350587', '350500', '综合保税区', '中国,福建省,泉州市,综合保税区', '综合保税区', '中国,福建,泉州,综合保税区', '3', '0595', '362000', 'Baoshuiqu', 'BS', 'B', '118.680447', '24.887735'),
('1289', '350600', '350000', '漳州市', '中国,福建省,漳州市', '漳州', '中国,福建,漳州', '2', '0596', '363000', 'Zhangzhou', 'ZZ', 'Z', '117.661801', '24.510897'),
('1290', '350602', '350600', '芗城区', '中国,福建省,漳州市,芗城区', '芗城', '中国,福建,漳州,芗城', '3', '0596', '363000', 'Xiangcheng', 'XC', 'X', '117.65402', '24.51081'),
('1291', '350603', '350600', '龙文区', '中国,福建省,漳州市,龙文区', '龙文', '中国,福建,漳州,龙文', '3', '0596', '363000', 'Longwen', 'LW', 'L', '117.70971', '24.50323'),
('1292', '350622', '350600', '云霄县', '中国,福建省,漳州市,云霄县', '云霄', '中国,福建,漳州,云霄', '3', '0596', '363300', 'Yunxiao', 'YX', 'Y', '117.34051', '23.95534'),
('1293', '350623', '350600', '漳浦县', '中国,福建省,漳州市,漳浦县', '漳浦', '中国,福建,漳州,漳浦', '3', '0596', '363200', 'Zhangpu', 'ZP', 'Z', '117.61367', '24.11706'),
('1294', '350624', '350600', '诏安县', '中国,福建省,漳州市,诏安县', '诏安', '中国,福建,漳州,诏安', '3', '0596', '363500', 'Zhao\'an', 'ZA', 'Z', '117.17501', '23.71148'),
('1295', '350625', '350600', '长泰县', '中国,福建省,漳州市,长泰县', '长泰', '中国,福建,漳州,长泰', '3', '0596', '363900', 'Changtai', 'CT', 'C', '117.75924', '24.62526'),
('1296', '350626', '350600', '东山县', '中国,福建省,漳州市,东山县', '东山', '中国,福建,漳州,东山', '3', '0596', '363400', 'Dongshan', 'DS', 'D', '117.42822', '23.70109'),
('1297', '350627', '350600', '南靖县', '中国,福建省,漳州市,南靖县', '南靖', '中国,福建,漳州,南靖', '3', '0596', '363600', 'Nanjing', 'NJ', 'N', '117.35736', '24.51448'),
('1298', '350628', '350600', '平和县', '中国,福建省,漳州市,平和县', '平和', '中国,福建,漳州,平和', '3', '0596', '363700', 'Pinghe', 'PH', 'P', '117.3124', '24.36395'),
('1299', '350629', '350600', '华安县', '中国,福建省,漳州市,华安县', '华安', '中国,福建,漳州,华安', '3', '0596', '363800', 'Hua\'an', 'HA', 'H', '117.54077', '25.00563'),
('1300', '350681', '350600', '龙海市', '中国,福建省,漳州市,龙海市', '龙海', '中国,福建,漳州,龙海', '3', '0596', '363100', 'Longhai', 'LH', 'L', '117.81802', '24.44655'),
('1301', '350700', '350000', '南平市', '中国,福建省,南平市', '南平', '中国,福建,南平', '2', '0599', '353000', 'Nanping', 'NP', 'N', '118.178459', '26.635627'),
('1302', '350702', '350700', '延平区', '中国,福建省,南平市,延平区', '延平', '中国,福建,南平,延平', '3', '0599', '353000', 'Yanping', 'YP', 'Y', '118.18189', '26.63745'),
('1303', '350703', '350700', '建阳区', '中国,福建省,南平市,建阳区', '建阳', '中国,福建,南平,建阳', '3', '0599', '354200', 'Jianyang', 'JY', 'J', '118.12267', '27.332067'),
('1304', '350721', '350700', '顺昌县', '中国,福建省,南平市,顺昌县', '顺昌', '中国,福建,南平,顺昌', '3', '0599', '353200', 'Shunchang', 'SC', 'S', '117.8103', '26.79298'),
('1305', '350722', '350700', '浦城县', '中国,福建省,南平市,浦城县', '浦城', '中国,福建,南平,浦城', '3', '0599', '353400', 'Pucheng', 'PC', 'P', '118.54007', '27.91888'),
('1306', '350723', '350700', '光泽县', '中国,福建省,南平市,光泽县', '光泽', '中国,福建,南平,光泽', '3', '0599', '354100', 'Guangze', 'GZ', 'G', '117.33346', '27.54231'),
('1307', '350724', '350700', '松溪县', '中国,福建省,南平市,松溪县', '松溪', '中国,福建,南平,松溪', '3', '0599', '353500', 'Songxi', 'SX', 'S', '118.78533', '27.52624'),
('1308', '350725', '350700', '政和县', '中国,福建省,南平市,政和县', '政和', '中国,福建,南平,政和', '3', '0599', '353600', 'Zhenghe', 'ZH', 'Z', '118.85571', '27.36769'),
('1309', '350781', '350700', '邵武市', '中国,福建省,南平市,邵武市', '邵武', '中国,福建,南平,邵武', '3', '0599', '354000', 'Shaowu', 'SW', 'S', '117.4924', '27.34033'),
('1310', '350782', '350700', '武夷山市', '中国,福建省,南平市,武夷山市', '武夷山', '中国,福建,南平,武夷山', '3', '0599', '354300', 'Wuyishan', 'WYS', 'W', '118.03665', '27.75543'),
('1311', '350783', '350700', '建瓯市', '中国,福建省,南平市,建瓯市', '建瓯', '中国,福建,南平,建瓯', '3', '0599', '353100', 'Jianou', 'JO', 'J', '118.29766', '27.02301'),
('1312', '350800', '350000', '龙岩市', '中国,福建省,龙岩市', '龙岩', '中国,福建,龙岩', '2', '0597', '364000', 'Longyan', 'LY', 'L', '117.02978', '25.091603'),
('1313', '350802', '350800', '新罗区', '中国,福建省,龙岩市,新罗区', '新罗', '中国,福建,龙岩,新罗', '3', '0597', '364000', 'Xinluo', 'XL', 'X', '117.03693', '25.09834'),
('1314', '350803', '350800', '永定区', '中国,福建省,龙岩市,永定区', '永定', '中国,福建,龙岩,永定', '3', '0597', '364100', 'Yongding', 'YD', 'Y', '116.73199', '24.72302'),
('1315', '350821', '350800', '长汀县', '中国,福建省,龙岩市,长汀县', '长汀', '中国,福建,龙岩,长汀', '3', '0597', '366300', 'Changting', 'CT', 'C', '116.35888', '25.82773'),
('1316', '350823', '350800', '上杭县', '中国,福建省,龙岩市,上杭县', '上杭', '中国,福建,龙岩,上杭', '3', '0597', '364200', 'Shanghang', 'SH', 'S', '116.42022', '25.04943'),
('1317', '350824', '350800', '武平县', '中国,福建省,龙岩市,武平县', '武平', '中国,福建,龙岩,武平', '3', '0597', '364300', 'Wuping', 'WP', 'W', '116.10229', '25.09244'),
('1318', '350825', '350800', '连城县', '中国,福建省,龙岩市,连城县', '连城', '中国,福建,龙岩,连城', '3', '0597', '366200', 'Liancheng', 'LC', 'L', '116.75454', '25.7103'),
('1319', '350881', '350800', '漳平市', '中国,福建省,龙岩市,漳平市', '漳平', '中国,福建,龙岩,漳平', '3', '0597', '364400', 'Zhangping', 'ZP', 'Z', '117.41992', '25.29109'),
('1320', '350900', '350000', '宁德市', '中国,福建省,宁德市', '宁德', '中国,福建,宁德', '2', '0593', '352000', 'Ningde', 'ND', 'N', '119.527082', '26.65924'),
('1321', '350902', '350900', '蕉城区', '中国,福建省,宁德市,蕉城区', '蕉城', '中国,福建,宁德,蕉城', '3', '0593', '352000', 'Jiaocheng', 'JC', 'J', '119.52643', '26.66048'),
('1322', '350921', '350900', '霞浦县', '中国,福建省,宁德市,霞浦县', '霞浦', '中国,福建,宁德,霞浦', '3', '0593', '355100', 'Xiapu', 'XP', 'X', '119.99893', '26.88578'),
('1323', '350922', '350900', '古田县', '中国,福建省,宁德市,古田县', '古田', '中国,福建,宁德,古田', '3', '0593', '352200', 'Gutian', 'GT', 'G', '118.74688', '26.57682'),
('1324', '350923', '350900', '屏南县', '中国,福建省,宁德市,屏南县', '屏南', '中国,福建,宁德,屏南', '3', '0593', '352300', 'Pingnan', 'PN', 'P', '118.98861', '26.91099'),
('1325', '350924', '350900', '寿宁县', '中国,福建省,宁德市,寿宁县', '寿宁', '中国,福建,宁德,寿宁', '3', '0593', '355500', 'Shouning', 'SN', 'S', '119.5039', '27.45996'),
('1326', '350925', '350900', '周宁县', '中国,福建省,宁德市,周宁县', '周宁', '中国,福建,宁德,周宁', '3', '0593', '355400', 'Zhouning', 'ZN', 'Z', '119.33837', '27.10664'),
('1327', '350926', '350900', '柘荣县', '中国,福建省,宁德市,柘荣县', '柘荣', '中国,福建,宁德,柘荣', '3', '0593', '355300', 'Zherong', 'ZR', 'Z', '119.89971', '27.23543'),
('1328', '350981', '350900', '福安市', '中国,福建省,宁德市,福安市', '福安', '中国,福建,宁德,福安', '3', '0593', '355000', 'Fu\'an', 'FA', 'F', '119.6495', '27.08673'),
('1329', '350982', '350900', '福鼎市', '中国,福建省,宁德市,福鼎市', '福鼎', '中国,福建,宁德,福鼎', '3', '0593', '355200', 'Fuding', 'FD', 'F', '120.21664', '27.3243'),
('1330', '350983', '350900', '东侨开发区', '中国,福建省,宁德市,东侨开发区', '东侨开发区', '中国,福建,宁德,东侨开发区', '3', '0593', '352000', 'Dongqiaokaifaqu', 'DQKFQ', 'D', '119.541299', '26.65991'),
('1331', '360000', '100000', '江西省', '中国,江西省', '江西', '中国,江西', '1', '', '', 'Jiangxi', 'JX', 'J', '115.892151', '28.676493'),
('1332', '360100', '360000', '南昌市', '中国,江西省,南昌市', '南昌', '中国,江西,南昌', '2', '0791', '330000', 'Nanchang', 'NC', 'N', '115.892151', '28.676493'),
('1333', '360102', '360100', '东湖区', '中国,江西省,南昌市,东湖区', '东湖', '中国,江西,南昌,东湖', '3', '0791', '330000', 'Donghu', 'DH', 'D', '115.8988', '28.68505'),
('1334', '360103', '360100', '西湖区', '中国,江西省,南昌市,西湖区', '西湖', '中国,江西,南昌,西湖', '3', '0791', '330000', 'Xihu', 'XH', 'X', '115.87728', '28.65688'),
('1335', '360104', '360100', '青云谱区', '中国,江西省,南昌市,青云谱区', '青云谱', '中国,江西,南昌,青云谱', '3', '0791', '330000', 'Qingyunpu', 'QYP', 'Q', '115.915', '28.63199'),
('1336', '360105', '360100', '湾里区', '中国,江西省,南昌市,湾里区', '湾里', '中国,江西,南昌,湾里', '3', '0791', '330000', 'Wanli', 'WL', 'W', '115.73104', '28.71529'),
('1337', '360111', '360100', '青山湖区', '中国,江西省,南昌市,青山湖区', '青山湖', '中国,江西,南昌,青山湖', '3', '0791', '330029', 'Qingshanhu', 'QSH', 'Q', '115.9617', '28.68206'),
('1338', '360112', '360100', '新建区', '中国,江西省,南昌市,新建区', '新建', '中国,江西,南昌,新建', '3', '0791', '330100', 'Xinjian', 'XJ', 'X', '115.81546', '28.69248'),
('1339', '360121', '360100', '南昌县', '中国,江西省,南昌市,南昌县', '南昌', '中国,江西,南昌,南昌', '3', '0791', '330200', 'Nanchang', 'NC', 'N', '115.94393', '28.54559'),
('1340', '360123', '360100', '安义县', '中国,江西省,南昌市,安义县', '安义', '中国,江西,南昌,安义', '3', '0791', '330500', 'Anyi', 'AY', 'A', '115.54879', '28.84602'),
('1341', '360124', '360100', '进贤县', '中国,江西省,南昌市,进贤县', '进贤', '中国,江西,南昌,进贤', '3', '0791', '331700', 'Jinxian', 'JX', 'J', '116.24087', '28.37679'),
('1342', '360125', '360100', '红谷滩新区', '中国,江西省,南昌市,红谷滩新区', '红谷滩新区', '中国,江西,南昌,红谷滩新区', '3', '0791', '330038', 'Honggutanxinqu', 'HGTXQ', 'H', '115.858055', '28.698165'),
('1343', '360126', '360100', '高新区', '中国,江西省,南昌市,高新区', '高新区', '中国,江西,南昌,高新区', '3', '0791', '330000', 'Gaoxinqu', 'GXQ', 'G', '115.959991', '28.682062'),
('1344', '360127', '360100', '经济开发区', '中国,江西省,南昌市,经济开发区', '经济开发区', '中国,江西,南昌,经济开发区', '3', '0791', '330000', 'Jingjikaifaqu', 'JJKFQ', 'J', '115.840792', '28.734856'),
('1345', '360128', '360100', '小蓝开发区', '中国,江西省,南昌市,小蓝开发区', '小蓝开发区', '中国,江西,南昌,小蓝开发区', '3', '0791', '330052', 'Xiaolankaifaqu', 'XLKFQ', 'X', '115.891874', '28.540863'),
('1346', '360129', '360100', '桑海开发区', '中国,江西省,南昌市,桑海开发区', '桑海开发区', '中国,江西,南昌,桑海开发区', '3', '0791', '330115', 'Shanhaikaifaqu', 'SHKFQ', 'S', '115.836578', '28.912213'),
('1347', '360130', '360100', '望城新区', '中国,江西省,南昌市,望城新区', '望城新区', '中国,江西,南昌,望城新区', '3', '0791', '330103', 'Wangchengxinqu', 'WCXQ', 'W', '115.734924', '28.631338'),
('1348', '360131', '360100', '赣江新区', '中国,江西省,南昌市,赣江新区', '赣江新区', '中国,江西,南昌,赣江新区', '3', '0791', '330029', 'Ganjiangxinqu', 'GJXQ', 'G', '115.897644', '28.716998'),
('1349', '360200', '360000', '景德镇市', '中国,江西省,景德镇市', '景德镇', '中国,江西,景德镇', '2', '0798', '333000', 'Jingdezhen', 'JDZ', 'J', '117.214664', '29.29256'),
('1350', '360202', '360200', '昌江区', '中国,江西省,景德镇市,昌江区', '昌江', '中国,江西,景德镇,昌江', '3', '0798', '333000', 'Changjiang', 'CJ', 'C', '117.18359', '29.27321'),
('1351', '360203', '360200', '珠山区', '中国,江西省,景德镇市,珠山区', '珠山', '中国,江西,景德镇,珠山', '3', '0798', '333000', 'Zhushan', 'ZS', 'Z', '117.20233', '29.30127'),
('1352', '360222', '360200', '浮梁县', '中国,江西省,景德镇市,浮梁县', '浮梁', '中国,江西,景德镇,浮梁', '3', '0798', '333400', 'Fuliang', 'FL', 'F', '117.21517', '29.35156'),
('1353', '360281', '360200', '乐平市', '中国,江西省,景德镇市,乐平市', '乐平', '中国,江西,景德镇,乐平', '3', '0798', '333300', 'Leping', 'LP', 'L', '117.12887', '28.96295'),
('1354', '360300', '360000', '萍乡市', '中国,江西省,萍乡市', '萍乡', '中国,江西,萍乡', '2', '0799', '337000', 'Pingxiang', 'PX', 'P', '113.852186', '27.622946'),
('1355', '360302', '360300', '安源区', '中国,江西省,萍乡市,安源区', '安源', '中国,江西,萍乡,安源', '3', '0799', '337000', 'Anyuan', 'AY', 'A', '113.89135', '27.61653'),
('1356', '360313', '360300', '湘东区', '中国,江西省,萍乡市,湘东区', '湘东', '中国,江西,萍乡,湘东', '3', '0799', '337000', 'Xiangdong', 'XD', 'X', '113.73294', '27.64007'),
('1357', '360321', '360300', '莲花县', '中国,江西省,萍乡市,莲花县', '莲花', '中国,江西,萍乡,莲花', '3', '0799', '337100', 'Lianhua', 'LH', 'L', '113.96142', '27.12866'),
('1358', '360322', '360300', '上栗县', '中国,江西省,萍乡市,上栗县', '上栗', '中国,江西,萍乡,上栗', '3', '0799', '337000', 'Shangli', 'SL', 'S', '113.79403', '27.87467'),
('1359', '360323', '360300', '芦溪县', '中国,江西省,萍乡市,芦溪县', '芦溪', '中国,江西,萍乡,芦溪', '3', '0799', '337000', 'Luxi', 'LX', 'L', '114.02951', '27.63063'),
('1360', '360400', '360000', '九江市', '中国,江西省,九江市', '九江', '中国,江西,九江', '2', '0792', '332000', 'Jiujiang', 'JJ', 'J', '115.992811', '29.712034'),
('1361', '360402', '360400', '濂溪区', '中国,江西省,九江市,濂溪区', '濂溪', '中国,江西,九江,濂溪', '3', '0792', '332900', 'LianXi', 'LX', 'L', '115.98904', '29.67177'),
('1362', '360403', '360400', '浔阳区', '中国,江西省,九江市,浔阳区', '浔阳', '中国,江西,九江,浔阳', '3', '0792', '332000', 'Xunyang', 'XY', 'X', '115.98986', '29.72786'),
('1363', '360421', '360400', '九江县', '中国,江西省,九江市,九江县', '九江', '中国,江西,九江,九江', '3', '0792', '332100', 'Jiujiang', 'JJ', 'J', '115.91128', '29.60852'),
('1364', '360423', '360400', '武宁县', '中国,江西省,九江市,武宁县', '武宁', '中国,江西,九江,武宁', '3', '0792', '332300', 'Wuning', 'WN', 'W', '115.10061', '29.2584'),
('1365', '360424', '360400', '修水县', '中国,江西省,九江市,修水县', '修水', '中国,江西,九江,修水', '3', '0792', '332400', 'Xiushui', 'XS', 'X', '114.54684', '29.02539'),
('1366', '360425', '360400', '永修县', '中国,江西省,九江市,永修县', '永修', '中国,江西,九江,永修', '3', '0792', '330300', 'Yongxiu', 'YX', 'Y', '115.80911', '29.02093'),
('1367', '360426', '360400', '德安县', '中国,江西省,九江市,德安县', '德安', '中国,江西,九江,德安', '3', '0792', '330400', 'De\'an', 'DA', 'D', '115.75601', '29.31341'),
('1368', '360428', '360400', '都昌县', '中国,江西省,九江市,都昌县', '都昌', '中国,江西,九江,都昌', '3', '0792', '332600', 'Duchang', 'DC', 'D', '116.20401', '29.27327'),
('1369', '360429', '360400', '湖口县', '中国,江西省,九江市,湖口县', '湖口', '中国,江西,九江,湖口', '3', '0792', '332500', 'Hukou', 'HK', 'H', '116.21853', '29.73818'),
('1370', '360430', '360400', '彭泽县', '中国,江西省,九江市,彭泽县', '彭泽', '中国,江西,九江,彭泽', '3', '0792', '332700', 'Pengze', 'PZ', 'P', '116.55011', '29.89589'),
('1371', '360481', '360400', '瑞昌市', '中国,江西省,九江市,瑞昌市', '瑞昌', '中国,江西,九江,瑞昌', '3', '0792', '332200', 'Ruichang', 'RC', 'R', '115.66705', '29.67183'),
('1372', '360482', '360400', '共青城市', '中国,江西省,九江市,共青城市', '共青城', '中国,江西,九江,共青城', '3', '0792', '332020', 'Gongqingcheng', 'GQC', 'G', '115.801939', '29.238785'),
('1373', '360483', '360400', '庐山市', '中国,江西省,九江市,庐山市', '庐山', '中国,江西,九江,庐山', '3', '0792', '332800', 'Lushan', 'LS', 'L', '116.04492', '29.44608'),
('1374', '360484', '360400', '经济技术开发区', '中国,江西省,九江市,经济技术开发区', '经济开发区', '中国,江西,九江,经济开发区', '3', '0792', '332000', 'Jingjikaifaqu', 'JJKFQ', 'J', '115.952224', '29.702546'),
('1375', '360485', '360400', '八里湖新区', '中国,江西省,九江市,八里湖新区', '八里湖新区', '中国,江西,九江,八里湖新区', '3', '0792', '332000', 'Balihuxinqu', 'BLHXQ', 'B', '115.953481', '29.671747'),
('1376', '360486', '360400', '庐山风景名胜区', '中国,江西省,九江市,庐山风景名胜区', '庐山风景名胜区', '中国,江西,九江,庐山风景名胜区', '3', '0792', '332800', 'Lushanfengjingqu', 'LSFJQ', 'L', '115.994609', '29.555576'),
('1377', '360500', '360000', '新余市', '中国,江西省,新余市', '新余', '中国,江西,新余', '2', '0790', '338000', 'Xinyu', 'XY', 'X', '114.930835', '27.810834'),
('1378', '360502', '360500', '渝水区', '中国,江西省,新余市,渝水区', '渝水', '中国,江西,新余,渝水', '3', '0790', '338000', 'Yushui', 'YS', 'Y', '114.944', '27.80098'),
('1379', '360521', '360500', '分宜县', '中国,江西省,新余市,分宜县', '分宜', '中国,江西,新余,分宜', '3', '0790', '336600', 'Fenyi', 'FY', 'F', '114.69189', '27.81475'),
('1380', '360600', '360000', '鹰潭市', '中国,江西省,鹰潭市', '鹰潭', '中国,江西,鹰潭', '2', '0701', '335000', 'Yingtan', 'YT', 'Y', '117.033838', '28.238638'),
('1381', '360602', '360600', '月湖区', '中国,江西省,鹰潭市,月湖区', '月湖', '中国,江西,鹰潭,月湖', '3', '0701', '335000', 'Yuehu', 'YH', 'Y', '117.03732', '28.23913'),
('1382', '360622', '360600', '余江县', '中国,江西省,鹰潭市,余江县', '余江', '中国,江西,鹰潭,余江', '3', '0701', '335200', 'Yujiang', 'YJ', 'Y', '116.81851', '28.21034'),
('1383', '360681', '360600', '贵溪市', '中国,江西省,鹰潭市,贵溪市', '贵溪', '中国,江西,鹰潭,贵溪', '3', '0701', '335400', 'Guixi', 'GX', 'G', '117.24246', '28.2926'),
('1384', '360682', '360600', '高新区', '中国,江西省,鹰潭市,高新区', '高新区', '中国,江西,鹰潭,高新区', '3', '0701', '338000', 'Gaoxinqu', 'GXQ', 'G', '117.000029', '28.21554'),
('1385', '360700', '360000', '赣州市', '中国,江西省,赣州市', '赣州', '中国,江西,赣州', '2', '0797', '341000', 'Ganzhou', 'GZ', 'G', '114.940278', '25.85097'),
('1386', '360702', '360700', '章贡区', '中国,江西省,赣州市,章贡区', '章贡', '中国,江西,赣州,章贡', '3', '0797', '341000', 'Zhanggong', 'ZG', 'Z', '114.94284', '25.8624'),
('1387', '360703', '360700', '南康区', '中国,江西省,赣州市,南康区', '南康', '中国,江西,赣州,南康', '3', '0797', '341400', 'Nankang', 'NK', 'N', '114.756933', '25.661721'),
('1388', '360704', '360700', '赣县区', '中国,江西省,赣州市,赣县区', '赣县', '中国,江西,赣州,赣县', '3', '0797', '341100', 'Ganxian', 'GX', 'G', '115.01171', '25.86149'),
('1389', '360722', '360700', '信丰县', '中国,江西省,赣州市,信丰县', '信丰', '中国,江西,赣州,信丰', '3', '0797', '341600', 'Xinfeng', 'XF', 'X', '114.92279', '25.38612'),
('1390', '360723', '360700', '大余县', '中国,江西省,赣州市,大余县', '大余', '中国,江西,赣州,大余', '3', '0797', '341500', 'Dayu', 'DY', 'D', '114.35757', '25.39561'),
('1391', '360724', '360700', '上犹县', '中国,江西省,赣州市,上犹县', '上犹', '中国,江西,赣州,上犹', '3', '0797', '341200', 'Shangyou', 'SY', 'S', '114.54138', '25.79567'),
('1392', '360725', '360700', '崇义县', '中国,江西省,赣州市,崇义县', '崇义', '中国,江西,赣州,崇义', '3', '0797', '341300', 'Chongyi', 'CY', 'C', '114.30835', '25.68186'),
('1393', '360726', '360700', '安远县', '中国,江西省,赣州市,安远县', '安远', '中国,江西,赣州,安远', '3', '0797', '342100', 'Anyuan', 'AY', 'A', '115.39483', '25.1371'),
('1394', '360727', '360700', '龙南县', '中国,江西省,赣州市,龙南县', '龙南', '中国,江西,赣州,龙南', '3', '0797', '341700', 'Longnan', 'LN', 'L', '114.78994', '24.91086'),
('1395', '360728', '360700', '定南县', '中国,江西省,赣州市,定南县', '定南', '中国,江西,赣州,定南', '3', '0797', '341900', 'Dingnan', 'DN', 'D', '115.02713', '24.78395'),
('1396', '360729', '360700', '全南县', '中国,江西省,赣州市,全南县', '全南', '中国,江西,赣州,全南', '3', '0797', '341800', 'Quannan', 'QN', 'Q', '114.5292', '24.74324'),
('1397', '360730', '360700', '宁都县', '中国,江西省,赣州市,宁都县', '宁都', '中国,江西,赣州,宁都', '3', '0797', '342800', 'Ningdu', 'ND', 'N', '116.01565', '26.47227'),
('1398', '360731', '360700', '于都县', '中国,江西省,赣州市,于都县', '于都', '中国,江西,赣州,于都', '3', '0797', '342300', 'Yudu', 'YD', 'Y', '115.41415', '25.95257'),
('1399', '360732', '360700', '兴国县', '中国,江西省,赣州市,兴国县', '兴国', '中国,江西,赣州,兴国', '3', '0797', '342400', 'Xingguo', 'XG', 'X', '115.36309', '26.33776'),
('1400', '360733', '360700', '会昌县', '中国,江西省,赣州市,会昌县', '会昌', '中国,江西,赣州,会昌', '3', '0797', '342600', 'Huichang', 'HC', 'H', '115.78555', '25.60068'),
('1401', '360734', '360700', '寻乌县', '中国,江西省,赣州市,寻乌县', '寻乌', '中国,江西,赣州,寻乌', '3', '0797', '342200', 'Xunwu', 'XW', 'X', '115.64852', '24.95513'),
('1402', '360735', '360700', '石城县', '中国,江西省,赣州市,石城县', '石城', '中国,江西,赣州,石城', '3', '0797', '342700', 'Shicheng', 'SC', 'S', '116.3442', '26.32617'),
('1403', '360781', '360700', '瑞金市', '中国,江西省,赣州市,瑞金市', '瑞金', '中国,江西,赣州,瑞金', '3', '0797', '342500', 'Ruijin', 'RJ', 'R', '116.02703', '25.88557'),
('1404', '360782', '360700', '章康新区', '中国,江西省,赣州市,章康新区', '章康新区', '中国,江西,赣州,章康新区', '3', '0797', '341000', 'Zhangkangxinqu', 'ZKXQ', 'Z', '114.93503', '25.831829'),
('1405', '360800', '360000', '吉安市', '中国,江西省,吉安市', '吉安', '中国,江西,吉安', '2', '0796', '343000', 'Ji\'an', 'JA', 'J', '114.986373', '27.111699'),
('1406', '360802', '360800', '吉州区', '中国,江西省,吉安市,吉州区', '吉州', '中国,江西,吉安,吉州', '3', '0796', '343000', 'Jizhou', 'JZ', 'J', '114.97598', '27.10669'),
('1407', '360803', '360800', '青原区', '中国,江西省,吉安市,青原区', '青原', '中国,江西,吉安,青原', '3', '0796', '343000', 'Qingyuan', 'QY', 'Q', '115.01747', '27.10577'),
('1408', '360821', '360800', '吉安县', '中国,江西省,吉安市,吉安县', '吉安', '中国,江西,吉安,吉安', '3', '0796', '343100', 'Ji\'an', 'JA', 'J', '114.90695', '27.04048'),
('1409', '360822', '360800', '吉水县', '中国,江西省,吉安市,吉水县', '吉水', '中国,江西,吉安,吉水', '3', '0796', '331600', 'Jishui', 'JS', 'J', '115.1343', '27.21071'),
('1410', '360823', '360800', '峡江县', '中国,江西省,吉安市,峡江县', '峡江', '中国,江西,吉安,峡江', '3', '0796', '331400', 'Xiajiang', 'XJ', 'X', '115.31723', '27.576'),
('1411', '360824', '360800', '新干县', '中国,江西省,吉安市,新干县', '新干', '中国,江西,吉安,新干', '3', '0796', '331300', 'Xingan', 'XG', 'X', '115.39306', '27.74092'),
('1412', '360825', '360800', '永丰县', '中国,江西省,吉安市,永丰县', '永丰', '中国,江西,吉安,永丰', '3', '0796', '331500', 'Yongfeng', 'YF', 'Y', '115.44238', '27.31785'),
('1413', '360826', '360800', '泰和县', '中国,江西省,吉安市,泰和县', '泰和', '中国,江西,吉安,泰和', '3', '0796', '343700', 'Taihe', 'TH', 'T', '114.90789', '26.79113'),
('1414', '360827', '360800', '遂川县', '中国,江西省,吉安市,遂川县', '遂川', '中国,江西,吉安,遂川', '3', '0796', '343900', 'Suichuan', 'SC', 'S', '114.51629', '26.32598'),
('1415', '360828', '360800', '万安县', '中国,江西省,吉安市,万安县', '万安', '中国,江西,吉安,万安', '3', '0796', '343800', 'Wan\'an', 'WA', 'W', '114.78659', '26.45931'),
('1416', '360829', '360800', '安福县', '中国,江西省,吉安市,安福县', '安福', '中国,江西,吉安,安福', '3', '0796', '343200', 'Anfu', 'AF', 'A', '114.61956', '27.39276'),
('1417', '360830', '360800', '永新县', '中国,江西省,吉安市,永新县', '永新', '中国,江西,吉安,永新', '3', '0796', '343400', 'Yongxin', 'YX', 'Y', '114.24246', '26.94488'),
('1418', '360881', '360800', '井冈山市', '中国,江西省,吉安市,井冈山市', '井冈山', '中国,江西,吉安,井冈山', '3', '0796', '343600', 'Jinggangshan', 'JGS', 'J', '114.28949', '26.74804'),
('1419', '360900', '360000', '宜春市', '中国,江西省,宜春市', '宜春', '中国,江西,宜春', '2', '0795', '336000', 'Yichun', 'YC', 'Y', '114.391136', '27.8043'),
('1420', '360902', '360900', '袁州区', '中国,江西省,宜春市,袁州区', '袁州', '中国,江西,宜春,袁州', '3', '0795', '336000', 'Yuanzhou', 'YZ', 'Y', '114.38246', '27.79649'),
('1421', '360921', '360900', '奉新县', '中国,江西省,宜春市,奉新县', '奉新', '中国,江西,宜春,奉新', '3', '0795', '330700', 'Fengxin', 'FX', 'F', '115.40036', '28.6879'),
('1422', '360922', '360900', '万载县', '中国,江西省,宜春市,万载县', '万载', '中国,江西,宜春,万载', '3', '0795', '336100', 'Wanzai', 'WZ', 'W', '114.4458', '28.10656'),
('1423', '360923', '360900', '上高县', '中国,江西省,宜春市,上高县', '上高', '中国,江西,宜春,上高', '3', '0795', '336400', 'Shanggao', 'SG', 'S', '114.92459', '28.23423'),
('1424', '360924', '360900', '宜丰县', '中国,江西省,宜春市,宜丰县', '宜丰', '中国,江西,宜春,宜丰', '3', '0795', '336300', 'Yifeng', 'YF', 'Y', '114.7803', '28.38555'),
('1425', '360925', '360900', '靖安县', '中国,江西省,宜春市,靖安县', '靖安', '中国,江西,宜春,靖安', '3', '0795', '330600', 'Jing\'an', 'JA', 'J', '115.36279', '28.86167'),
('1426', '360926', '360900', '铜鼓县', '中国,江西省,宜春市,铜鼓县', '铜鼓', '中国,江西,宜春,铜鼓', '3', '0795', '336200', 'Tonggu', 'TG', 'T', '114.37036', '28.52311'),
('1427', '360981', '360900', '丰城市', '中国,江西省,宜春市,丰城市', '丰城', '中国,江西,宜春,丰城', '3', '0795', '331100', 'Fengcheng', 'FC', 'F', '115.77114', '28.15918'),
('1428', '360982', '360900', '樟树市', '中国,江西省,宜春市,樟树市', '樟树', '中国,江西,宜春,樟树', '3', '0795', '331200', 'Zhangshu', 'ZS', 'Z', '115.5465', '28.05332'),
('1429', '360983', '360900', '高安市', '中国,江西省,宜春市,高安市', '高安', '中国,江西,宜春,高安', '3', '0795', '330800', 'Gao\'an', 'GA', 'G', '115.3753', '28.4178'),
('1430', '361000', '360000', '抚州市', '中国,江西省,抚州市', '抚州', '中国,江西,抚州', '2', '0794', '344000', 'Fuzhou', 'FZ', 'F', '116.358351', '27.98385'),
('1431', '361002', '361000', '临川区', '中国,江西省,抚州市,临川区', '临川', '中国,江西,抚州,临川', '3', '0794', '344100', 'Linchuan', 'LC', 'L', '116.35919', '27.97721'),
('1432', '361003', '361000', '东乡区', '中国,江西省,抚州市,东乡区', '东乡', '中国,江西,抚州,东乡', '3', '0794', '331800', 'Dongxiang', 'DX', 'D', '116.59039', '28.23614'),
('1433', '361021', '361000', '南城县', '中国,江西省,抚州市,南城县', '南城', '中国,江西,抚州,南城', '3', '0794', '344700', 'Nancheng', 'NC', 'N', '116.64419', '27.55381'),
('1434', '361022', '361000', '黎川县', '中国,江西省,抚州市,黎川县', '黎川', '中国,江西,抚州,黎川', '3', '0794', '344600', 'Lichuan', 'LC', 'L', '116.90771', '27.28232'),
('1435', '361023', '361000', '南丰县', '中国,江西省,抚州市,南丰县', '南丰', '中国,江西,抚州,南丰', '3', '0794', '344500', 'Nanfeng', 'NF', 'N', '116.5256', '27.21842'),
('1436', '361024', '361000', '崇仁县', '中国,江西省,抚州市,崇仁县', '崇仁', '中国,江西,抚州,崇仁', '3', '0794', '344200', 'Chongren', 'CR', 'C', '116.06021', '27.75962'),
('1437', '361025', '361000', '乐安县', '中国,江西省,抚州市,乐安县', '乐安', '中国,江西,抚州,乐安', '3', '0794', '344300', 'Le\'an', 'LA', 'L', '115.83108', '27.42812'),
('1438', '361026', '361000', '宜黄县', '中国,江西省,抚州市,宜黄县', '宜黄', '中国,江西,抚州,宜黄', '3', '0794', '344400', 'Yihuang', 'YH', 'Y', '116.23626', '27.55487'),
('1439', '361027', '361000', '金溪县', '中国,江西省,抚州市,金溪县', '金溪', '中国,江西,抚州,金溪', '3', '0794', '344800', 'Jinxi', 'JX', 'J', '116.77392', '27.90753'),
('1440', '361028', '361000', '资溪县', '中国,江西省,抚州市,资溪县', '资溪', '中国,江西,抚州,资溪', '3', '0794', '335300', 'Zixi', 'ZX', 'Z', '117.06939', '27.70493'),
('1441', '361030', '361000', '广昌县', '中国,江西省,抚州市,广昌县', '广昌', '中国,江西,抚州,广昌', '3', '0794', '344900', 'Guangchang', 'GC', 'G', '116.32547', '26.8341'),
('1442', '361100', '360000', '上饶市', '中国,江西省,上饶市', '上饶', '中国,江西,上饶', '2', '0793', '334000', 'Shangrao', 'SR', 'S', '117.971185', '28.44442'),
('1443', '361102', '361100', '信州区', '中国,江西省,上饶市,信州区', '信州', '中国,江西,上饶,信州', '3', '0793', '334000', 'Xinzhou', 'XZ', 'X', '117.96682', '28.43121'),
('1444', '361103', '361100', '广丰区', '中国,江西省,上饶市,广丰区', '广丰', '中国,江西,上饶,广丰', '3', '0793', '334600', 'Guangfeng', 'GF', 'G', '118.19158', '28.43766'),
('1445', '361121', '361100', '上饶县', '中国,江西省,上饶市,上饶县', '上饶', '中国,江西,上饶,上饶', '3', '0793', '334100', 'Shangrao', 'SR', 'S', '117.90884', '28.44856'),
('1446', '361123', '361100', '玉山县', '中国,江西省,上饶市,玉山县', '玉山', '中国,江西,上饶,玉山', '3', '0793', '334700', 'Yushan', 'YS', 'Y', '118.24462', '28.6818'),
('1447', '361124', '361100', '铅山县', '中国,江西省,上饶市,铅山县', '铅山', '中国,江西,上饶,铅山', '3', '0793', '334500', 'Yanshan', 'YS', 'Y', '117.70996', '28.31549'),
('1448', '361125', '361100', '横峰县', '中国,江西省,上饶市,横峰县', '横峰', '中国,江西,上饶,横峰', '3', '0793', '334300', 'Hengfeng', 'HF', 'H', '117.5964', '28.40716'),
('1449', '361126', '361100', '弋阳县', '中国,江西省,上饶市,弋阳县', '弋阳', '中国,江西,上饶,弋阳', '3', '0793', '334400', 'Yiyang', 'YY', 'Y', '117.45929', '28.37451'),
('1450', '361127', '361100', '余干县', '中国,江西省,上饶市,余干县', '余干', '中国,江西,上饶,余干', '3', '0793', '335100', 'Yugan', 'YG', 'Y', '116.69555', '28.70206'),
('1451', '361128', '361100', '鄱阳县', '中国,江西省,上饶市,鄱阳县', '鄱阳', '中国,江西,上饶,鄱阳', '3', '0793', '333100', 'Poyang', 'PY', 'P', '116.69967', '29.0118'),
('1452', '361129', '361100', '万年县', '中国,江西省,上饶市,万年县', '万年', '中国,江西,上饶,万年', '3', '0793', '335500', 'Wannian', 'WN', 'W', '117.06884', '28.69537'),
('1453', '361130', '361100', '婺源县', '中国,江西省,上饶市,婺源县', '婺源', '中国,江西,上饶,婺源', '3', '0793', '333200', 'Wuyuan', 'WY', 'W', '117.86105', '29.24841'),
('1454', '361181', '361100', '德兴市', '中国,江西省,上饶市,德兴市', '德兴', '中国,江西,上饶,德兴', '3', '0793', '334200', 'Dexing', 'DX', 'D', '117.57919', '28.94736'),
('1455', '370000', '100000', '山东省', '中国,山东省', '山东', '中国,山东', '1', '', '', 'Shandong', 'SD', 'S', '117.000923', '36.675807'),
('1456', '370100', '370000', '济南市', '中国,山东省,济南市', '济南', '中国,山东,济南', '2', '0531', '250000', 'Jinan', 'JN', 'J', '117.000923', '36.675807'),
('1457', '370102', '370100', '历下区', '中国,山东省,济南市,历下区', '历下', '中国,山东,济南,历下', '3', '0531', '250014', 'Lixia', 'LX', 'L', '117.0768', '36.66661'),
('1458', '370103', '370100', '市中区', '中国,山东省,济南市,市中区', '市中', '中国,山东,济南,市中', '3', '0531', '250001', 'Shizhong', 'SZ', 'S', '116.99741', '36.65101'),
('1459', '370104', '370100', '槐荫区', '中国,山东省,济南市,槐荫区', '槐荫', '中国,山东,济南,槐荫', '3', '0531', '250117', 'Huaiyin', 'HY', 'H', '116.90075', '36.65136'),
('1460', '370105', '370100', '天桥区', '中国,山东省,济南市,天桥区', '天桥', '中国,山东,济南,天桥', '3', '0531', '250031', 'Tianqiao', 'TQ', 'T', '116.98749', '36.67801'),
('1461', '370112', '370100', '历城区', '中国,山东省,济南市,历城区', '历城', '中国,山东,济南,历城', '3', '0531', '250100', 'Licheng', 'LC', 'L', '117.06509', '36.67995'),
('1462', '370113', '370100', '长清区', '中国,山东省,济南市,长清区', '长清', '中国,山东,济南,长清', '3', '0531', '250300', 'Changqing', 'CQ', 'C', '116.75192', '36.55352'),
('1463', '370114', '370100', '章丘区', '中国,山东省,济南市,章丘区', '章丘', '中国,山东,济南,章丘', '3', '0531', '250200', 'Zhangqiu', 'ZQ', 'Z', '117.53677', '36.71392'),
('1464', '370124', '370100', '平阴县', '中国,山东省,济南市,平阴县', '平阴', '中国,山东,济南,平阴', '3', '0531', '250400', 'Pingyin', 'PY', 'P', '116.45587', '36.28955'),
('1465', '370125', '370100', '济阳县', '中国,山东省,济南市,济阳县', '济阳', '中国,山东,济南,济阳', '3', '0531', '251400', 'Jiyang', 'JY', 'J', '117.17327', '36.97845'),
('1466', '370126', '370100', '商河县', '中国,山东省,济南市,商河县', '商河', '中国,山东,济南,商河', '3', '0531', '251600', 'Shanghe', 'SH', 'S', '117.15722', '37.31119'),
('1467', '370182', '370100', '高新区', '中国,山东省,济南市,高新区', '高新区', '中国,山东,济南,高新区', '3', '0531', '250000', 'Gaoxinqu', 'GXQ', 'G', '117.128033', '36.681553'),
('1468', '370200', '370000', '青岛市', '中国,山东省,青岛市', '青岛', '中国,山东,青岛', '2', '0532', '266000', 'Qingdao', 'QD', 'Q', '120.369557', '36.094406'),
('1469', '370202', '370200', '市南区', '中国,山东省,青岛市,市南区', '市南', '中国,山东,青岛,市南', '3', '0532', '266000', 'Shinan', 'SN', 'S', '120.38773', '36.06671'),
('1470', '370203', '370200', '市北区', '中国,山东省,青岛市,市北区', '市北', '中国,山东,青岛,市北', '3', '0532', '266000', 'Shibei', 'SB', 'S', '120.37469', '36.08734'),
('1471', '370211', '370200', '黄岛区', '中国,山东省,青岛市,黄岛区', '黄岛', '中国,山东,青岛,黄岛', '3', '0532', '266000', 'Huangdao', 'HD', 'H', '120.19775', '35.96065'),
('1472', '370212', '370200', '崂山区', '中国,山东省,青岛市,崂山区', '崂山', '中国,山东,青岛,崂山', '3', '0532', '266100', 'Laoshan', 'LS', 'L', '120.46923', '36.10717'),
('1473', '370213', '370200', '李沧区', '中国,山东省,青岛市,李沧区', '李沧', '中国,山东,青岛,李沧', '3', '0532', '266000', 'Licang', 'LC', 'L', '120.43286', '36.14502'),
('1474', '370214', '370200', '城阳区', '中国,山东省,青岛市,城阳区', '城阳', '中国,山东,青岛,城阳', '3', '0532', '266000', 'Chengyang', 'CY', 'C', '120.39621', '36.30735'),
('1475', '370281', '370200', '胶州市', '中国,山东省,青岛市,胶州市', '胶州', '中国,山东,青岛,胶州', '3', '0532', '266300', 'Jiaozhou', 'JZ', 'J', '120.0335', '36.26442'),
('1476', '370282', '370200', '即墨市', '中国,山东省,青岛市,即墨市', '即墨', '中国,山东,青岛,即墨', '3', '0532', '266200', 'Jimo', 'JM', 'J', '120.44699', '36.38907'),
('1477', '370283', '370200', '平度市', '中国,山东省,青岛市,平度市', '平度', '中国,山东,青岛,平度', '3', '0532', '266700', 'Pingdu', 'PD', 'P', '119.95996', '36.78688'),
('1478', '370285', '370200', '莱西市', '中国,山东省,青岛市,莱西市', '莱西', '中国,山东,青岛,莱西', '3', '0532', '266600', 'Laixi', 'LX', 'L', '120.51773', '36.88804'),
('1479', '370286', '370200', '西海岸新区', '中国,山东省,青岛市,西海岸新区', '西海岸新区', '中国,山东,青岛,西海岸新区', '3', '0532', '266500', 'Xihai\'anXinQu', 'XHAXQ', 'X', '120.19775', '35.96065'),
('1480', '370287', '370200', '高新区', '中国,山东省,青岛市,高新区', '高新区', '中国,山东,青岛,高新区', '3', '0532', '266000', 'Gaoxinqu', 'GXQ', 'G', '120.459095', '36.136192'),
('1481', '370300', '370000', '淄博市', '中国,山东省,淄博市', '淄博', '中国,山东,淄博', '2', '0533', '255000', 'Zibo', 'ZB', 'Z', '118.047648', '36.814939'),
('1482', '370302', '370300', '淄川区', '中国,山东省,淄博市,淄川区', '淄川', '中国,山东,淄博,淄川', '3', '0533', '255100', 'Zichuan', 'ZC', 'Z', '117.96655', '36.64339'),
('1483', '370303', '370300', '张店区', '中国,山东省,淄博市,张店区', '张店', '中国,山东,淄博,张店', '3', '0533', '255000', 'Zhangdian', 'ZD', 'Z', '118.01788', '36.80676'),
('1484', '370304', '370300', '博山区', '中国,山东省,淄博市,博山区', '博山', '中国,山东,淄博,博山', '3', '0533', '255200', 'Boshan', 'BS', 'B', '117.86166', '36.49469'),
('1485', '370305', '370300', '临淄区', '中国,山东省,淄博市,临淄区', '临淄', '中国,山东,淄博,临淄', '3', '0533', '255400', 'Linzi', 'LZ', 'L', '118.30966', '36.8259'),
('1486', '370306', '370300', '周村区', '中国,山东省,淄博市,周村区', '周村', '中国,山东,淄博,周村', '3', '0533', '255300', 'Zhoucun', 'ZC', 'Z', '117.86969', '36.80322'),
('1487', '370321', '370300', '桓台县', '中国,山东省,淄博市,桓台县', '桓台', '中国,山东,淄博,桓台', '3', '0533', '256400', 'Huantai', 'HT', 'H', '118.09698', '36.96036'),
('1488', '370322', '370300', '高青县', '中国,山东省,淄博市,高青县', '高青', '中国,山东,淄博,高青', '3', '0533', '256300', 'Gaoqing', 'GQ', 'G', '117.82708', '37.17197'),
('1489', '370323', '370300', '沂源县', '中国,山东省,淄博市,沂源县', '沂源', '中国,山东,淄博,沂源', '3', '0533', '256100', 'Yiyuan', 'YY', 'Y', '118.17105', '36.18536'),
('1490', '370324', '370300', '高新区', '中国,山东省,淄博市,高新区', '高新区', '中国,山东,淄博,高新区', '3', '0533', '255000', 'Gaoxinqu', 'GXQ', 'G', '118.050957', '36.840995'),
('1491', '370400', '370000', '枣庄市', '中国,山东省,枣庄市', '枣庄', '中国,山东,枣庄', '2', '0632', '277000', 'Zaozhuang', 'ZZ', 'Z', '117.557964', '34.856424'),
('1492', '370402', '370400', '市中区', '中国,山东省,枣庄市,市中区', '市中', '中国,山东,枣庄,市中', '3', '0632', '277000', 'Shizhong', 'SZ', 'S', '117.55603', '34.86391'),
('1493', '370403', '370400', '薛城区', '中国,山东省,枣庄市,薛城区', '薛城', '中国,山东,枣庄,薛城', '3', '0632', '277000', 'Xuecheng', 'XC', 'X', '117.26318', '34.79498'),
('1494', '370404', '370400', '峄城区', '中国,山东省,枣庄市,峄城区', '峄城', '中国,山东,枣庄,峄城', '3', '0632', '277300', 'Yicheng', 'YC', 'Y', '117.59057', '34.77225'),
('1495', '370405', '370400', '台儿庄区', '中国,山东省,枣庄市,台儿庄区', '台儿庄', '中国,山东,枣庄,台儿庄', '3', '0632', '277400', 'Taierzhuang', 'TEZ', 'T', '117.73452', '34.56363'),
('1496', '370406', '370400', '山亭区', '中国,山东省,枣庄市,山亭区', '山亭', '中国,山东,枣庄,山亭', '3', '0632', '277200', 'Shanting', 'ST', 'S', '117.4663', '35.09541'),
('1497', '370481', '370400', '滕州市', '中国,山东省,枣庄市,滕州市', '滕州', '中国,山东,枣庄,滕州', '3', '0632', '277500', 'Tengzhou', 'TZ', 'T', '117.165', '35.10534'),
('1498', '370482', '370400', '高新区', '中国,山东省,枣庄市,高新区', '高新区', '中国,山东,枣庄,高新区', '3', '0632', '277800', 'Gaoxinqu', 'GXQ', 'G', '117.27369', '34.809098'),
('1499', '370500', '370000', '东营市', '中国,山东省,东营市', '东营', '中国,山东,东营', '2', '0546', '257000', 'Dongying', 'DY', 'D', '118.4963', '37.461266'),
('1500', '370502', '370500', '东营区', '中国,山东省,东营市,东营区', '东营', '中国,山东,东营,东营', '3', '0546', '257100', 'Dongying', 'DY', 'D', '118.5816', '37.44875'),
('1501', '370503', '370500', '河口区', '中国,山东省,东营市,河口区', '河口', '中国,山东,东营,河口', '3', '0546', '257200', 'Hekou', 'HK', 'H', '118.5249', '37.88541'),
('1502', '370505', '370500', '垦利区', '中国,山东省,东营市,垦利区', '垦利', '中国,山东,东营,垦利', '3', '0546', '257500', 'Kenli', 'KL', 'K', '118.54815', '37.58825'),
('1503', '370522', '370500', '利津县', '中国,山东省,东营市,利津县', '利津', '中国,山东,东营,利津', '3', '0546', '257400', 'Lijin', 'LJ', 'L', '118.25637', '37.49157'),
('1504', '370523', '370500', '广饶县', '中国,山东省,东营市,广饶县', '广饶', '中国,山东,东营,广饶', '3', '0546', '257300', 'Guangrao', 'GR', 'G', '118.40704', '37.05381'),
('1505', '370600', '370000', '烟台市', '中国,山东省,烟台市', '烟台', '中国,山东,烟台', '2', '0535', '264000', 'Yantai', 'YT', 'Y', '121.391382', '37.539297'),
('1506', '370602', '370600', '芝罘区', '中国,山东省,烟台市,芝罘区', '芝罘', '中国,山东,烟台,芝罘', '3', '0535', '264000', 'Zhifu', 'ZF', 'Z', '121.40023', '37.54064'),
('1507', '370611', '370600', '福山区', '中国,山东省,烟台市,福山区', '福山', '中国,山东,烟台,福山', '3', '0535', '265500', 'Fushan', 'FS', 'F', '121.26812', '37.49841'),
('1508', '370612', '370600', '牟平区', '中国,山东省,烟台市,牟平区', '牟平', '中国,山东,烟台,牟平', '3', '0535', '264100', 'Muping', 'MP', 'M', '121.60067', '37.38846'),
('1509', '370613', '370600', '莱山区', '中国,山东省,烟台市,莱山区', '莱山', '中国,山东,烟台,莱山', '3', '0535', '264000', 'Laishan', 'LS', 'L', '121.44512', '37.51165'),
('1510', '370634', '370600', '长岛县', '中国,山东省,烟台市,长岛县', '长岛', '中国,山东,烟台,长岛', '3', '0535', '265800', 'Changdao', 'CD', 'C', '120.738', '37.91754'),
('1511', '370681', '370600', '龙口市', '中国,山东省,烟台市,龙口市', '龙口', '中国,山东,烟台,龙口', '3', '0535', '265700', 'Longkou', 'LK', 'L', '120.50634', '37.64064'),
('1512', '370682', '370600', '莱阳市', '中国,山东省,烟台市,莱阳市', '莱阳', '中国,山东,烟台,莱阳', '3', '0535', '265200', 'Laiyang', 'LY', 'L', '120.71066', '36.98012'),
('1513', '370683', '370600', '莱州市', '中国,山东省,烟台市,莱州市', '莱州', '中国,山东,烟台,莱州', '3', '0535', '261400', 'Laizhou', 'LZ', 'L', '119.94137', '37.17806'),
('1514', '370684', '370600', '蓬莱市', '中国,山东省,烟台市,蓬莱市', '蓬莱', '中国,山东,烟台,蓬莱', '3', '0535', '265600', 'Penglai', 'PL', 'P', '120.75988', '37.81119'),
('1515', '370685', '370600', '招远市', '中国,山东省,烟台市,招远市', '招远', '中国,山东,烟台,招远', '3', '0535', '265400', 'Zhaoyuan', 'ZY', 'Z', '120.40481', '37.36269'),
('1516', '370686', '370600', '栖霞市', '中国,山东省,烟台市,栖霞市', '栖霞', '中国,山东,烟台,栖霞', '3', '0535', '265300', 'Qixia', 'QX', 'Q', '120.85025', '37.33571'),
('1517', '370687', '370600', '海阳市', '中国,山东省,烟台市,海阳市', '海阳', '中国,山东,烟台,海阳', '3', '0535', '265100', 'Haiyang', 'HY', 'H', '121.15976', '36.77622'),
('1518', '370688', '370600', '高新区', '中国,山东省,烟台市,高新区', '高新区', '中国,山东,烟台,高新区', '3', '0535', '264003', 'Gaoxinqu', 'GXQ', 'G', '121.484124', '37.448924'),
('1519', '370689', '370600', '经济开发区', '中国,山东省,烟台市,经济开发区', '经济开发区', '中国,山东,烟台,经济开发区', '3', '0535', '264003', 'Jingjikaifaqu', 'JJKFQ', 'J', '121.253459', '37.563178'),
('1520', '370700', '370000', '潍坊市', '中国,山东省,潍坊市', '潍坊', '中国,山东,潍坊', '2', '0536', '261000', 'Weifang', 'WF', 'W', '119.107078', '36.70925'),
('1521', '370702', '370700', '潍城区', '中国,山东省,潍坊市,潍城区', '潍城', '中国,山东,潍坊,潍城', '3', '0536', '261000', 'Weicheng', 'WC', 'W', '119.10582', '36.7139'),
('1522', '370703', '370700', '寒亭区', '中国,山东省,潍坊市,寒亭区', '寒亭', '中国,山东,潍坊,寒亭', '3', '0536', '261100', 'Hanting', 'HT', 'H', '119.21832', '36.77504'),
('1523', '370704', '370700', '坊子区', '中国,山东省,潍坊市,坊子区', '坊子', '中国,山东,潍坊,坊子', '3', '0536', '261200', 'Fangzi', 'FZ', 'F', '119.16476', '36.65218'),
('1524', '370705', '370700', '奎文区', '中国,山东省,潍坊市,奎文区', '奎文', '中国,山东,潍坊,奎文', '3', '0536', '261000', 'Kuiwen', 'KW', 'K', '119.12532', '36.70723'),
('1525', '370724', '370700', '临朐县', '中国,山东省,潍坊市,临朐县', '临朐', '中国,山东,潍坊,临朐', '3', '0536', '262600', 'Linqu', 'LQ', 'L', '118.544', '36.51216'),
('1526', '370725', '370700', '昌乐县', '中国,山东省,潍坊市,昌乐县', '昌乐', '中国,山东,潍坊,昌乐', '3', '0536', '262400', 'Changle', 'CL', 'C', '118.83017', '36.7078'),
('1527', '370781', '370700', '青州市', '中国,山东省,潍坊市,青州市', '青州', '中国,山东,潍坊,青州', '3', '0536', '262500', 'Qingzhou', 'QZ', 'Q', '118.47915', '36.68505'),
('1528', '370782', '370700', '诸城市', '中国,山东省,潍坊市,诸城市', '诸城', '中国,山东,潍坊,诸城', '3', '0536', '262200', 'Zhucheng', 'ZC', 'Z', '119.40988', '35.99662'),
('1529', '370783', '370700', '寿光市', '中国,山东省,潍坊市,寿光市', '寿光', '中国,山东,潍坊,寿光', '3', '0536', '262700', 'Shouguang', 'SG', 'S', '118.74047', '36.88128'),
('1530', '370784', '370700', '安丘市', '中国,山东省,潍坊市,安丘市', '安丘', '中国,山东,潍坊,安丘', '3', '0536', '262100', 'Anqiu', 'AQ', 'A', '119.2189', '36.47847'),
('1531', '370785', '370700', '高密市', '中国,山东省,潍坊市,高密市', '高密', '中国,山东,潍坊,高密', '3', '0536', '261500', 'Gaomi', 'GM', 'G', '119.75701', '36.38397'),
('1532', '370786', '370700', '昌邑市', '中国,山东省,潍坊市,昌邑市', '昌邑', '中国,山东,潍坊,昌邑', '3', '0536', '261300', 'Changyi', 'CY', 'C', '119.39767', '36.86008'),
('1533', '370787', '370700', '高新区', '中国,山东省,潍坊市,高新区', '高新区', '中国,山东,潍坊,高新区', '3', '0536', '261000', 'Gaoxinqu', 'GXQ', 'G', '119.21585', '36.71218'),
('1534', '370800', '370000', '济宁市', '中国,山东省,济宁市', '济宁', '中国,山东,济宁', '2', '0537', '272000', 'Jining', 'JN', 'J', '116.587245', '35.415393'),
('1535', '370811', '370800', '任城区', '中国,山东省,济宁市,任城区', '任城', '中国,山东,济宁,任城', '3', '0537', '272000', 'Rencheng', 'RC', 'R', '116.59504', '35.40659'),
('1536', '370812', '370800', '兖州区', '中国,山东省,济宁市,兖州区', '兖州', '中国,山东,济宁,兖州', '3', '0537', '272000', 'Yanzhou ', 'YZ', 'Y', '116.826546', '35.552305'),
('1537', '370826', '370800', '微山县', '中国,山东省,济宁市,微山县', '微山', '中国,山东,济宁,微山', '3', '0537', '277600', 'Weishan', 'WS', 'W', '117.12875', '34.80712'),
('1538', '370827', '370800', '鱼台县', '中国,山东省,济宁市,鱼台县', '鱼台', '中国,山东,济宁,鱼台', '3', '0537', '272300', 'Yutai', 'YT', 'Y', '116.64761', '34.99674'),
('1539', '370828', '370800', '金乡县', '中国,山东省,济宁市,金乡县', '金乡', '中国,山东,济宁,金乡', '3', '0537', '272200', 'Jinxiang', 'JX', 'J', '116.31146', '35.065'),
('1540', '370829', '370800', '嘉祥县', '中国,山东省,济宁市,嘉祥县', '嘉祥', '中国,山东,济宁,嘉祥', '3', '0537', '272400', 'Jiaxiang', 'JX', 'J', '116.34249', '35.40836'),
('1541', '370830', '370800', '汶上县', '中国,山东省,济宁市,汶上县', '汶上', '中国,山东,济宁,汶上', '3', '0537', '272500', 'Wenshang', 'WS', 'W', '116.48742', '35.73295'),
('1542', '370831', '370800', '泗水县', '中国,山东省,济宁市,泗水县', '泗水', '中国,山东,济宁,泗水', '3', '0537', '273200', 'Sishui', 'SS', 'S', '117.27948', '35.66113'),
('1543', '370832', '370800', '梁山县', '中国,山东省,济宁市,梁山县', '梁山', '中国,山东,济宁,梁山', '3', '0537', '272600', 'Liangshan', 'LS', 'L', '116.09683', '35.80322'),
('1544', '370881', '370800', '曲阜市', '中国,山东省,济宁市,曲阜市', '曲阜', '中国,山东,济宁,曲阜', '3', '0537', '273100', 'Qufu', 'QF', 'Q', '116.98645', '35.58091'),
('1545', '370883', '370800', '邹城市', '中国,山东省,济宁市,邹城市', '邹城', '中国,山东,济宁,邹城', '3', '0537', '273500', 'Zoucheng', 'ZC', 'Z', '116.97335', '35.40531'),
('1546', '370884', '370800', '高新区', '中国,山东省,济宁市,高新区', '高新区', '中国,山东,济宁,高新区', '3', '0537', '272000', 'Gaoxinqu', 'GXQ', 'G', '116.629511', '35.429776'),
('1547', '370900', '370000', '泰安市', '中国,山东省,泰安市', '泰安', '中国,山东,泰安', '2', '0538', '271000', 'Tai\'an', 'TA', 'T', '117.129063', '36.194968'),
('1548', '370902', '370900', '泰山区', '中国,山东省,泰安市,泰山区', '泰山', '中国,山东,泰安,泰山', '3', '0538', '271000', 'Taishan', 'TS', 'T', '117.13446', '36.19411'),
('1549', '370911', '370900', '岱岳区', '中国,山东省,泰安市,岱岳区', '岱岳', '中国,山东,泰安,岱岳', '3', '0538', '271000', 'Daiyue', 'DY', 'D', '117.04174', '36.1875'),
('1550', '370921', '370900', '宁阳县', '中国,山东省,泰安市,宁阳县', '宁阳', '中国,山东,泰安,宁阳', '3', '0538', '271400', 'Ningyang', 'NY', 'N', '116.80542', '35.7599'),
('1551', '370923', '370900', '东平县', '中国,山东省,泰安市,东平县', '东平', '中国,山东,泰安,东平', '3', '0538', '271500', 'Dongping', 'DP', 'D', '116.47113', '35.93792'),
('1552', '370982', '370900', '新泰市', '中国,山东省,泰安市,新泰市', '新泰', '中国,山东,泰安,新泰', '3', '0538', '271200', 'Xintai', 'XT', 'X', '117.76959', '35.90887'),
('1553', '370983', '370900', '肥城市', '中国,山东省,泰安市,肥城市', '肥城', '中国,山东,泰安,肥城', '3', '0538', '271600', 'Feicheng', 'FC', 'F', '116.76815', '36.18247'),
('1554', '371000', '370000', '威海市', '中国,山东省,威海市', '威海', '中国,山东,威海', '2', '0631', '264200', 'Weihai', 'WH', 'W', '122.116394', '37.509691'),
('1555', '371002', '371000', '环翠区', '中国,山东省,威海市,环翠区', '环翠', '中国,山东,威海,环翠', '3', '0631', '264200', 'Huancui', 'HC', 'H', '122.12344', '37.50199'),
('1556', '371003', '371000', '文登区', '中国,山东省,威海市,文登区', '文登', '中国,山东,威海,文登', '3', '0631', '266440', 'Wendeng', 'WD', 'W', '122.057139', '37.196211'),
('1557', '371082', '371000', '荣成市', '中国,山东省,威海市,荣成市', '荣成', '中国,山东,威海,荣成', '3', '0631', '264300', 'Rongcheng', 'RC', 'R', '122.48773', '37.1652'),
('1558', '371083', '371000', '乳山市', '中国,山东省,威海市,乳山市', '乳山', '中国,山东,威海,乳山', '3', '0631', '264500', 'Rushan', 'RS', 'R', '121.53814', '36.91918'),
('1559', '371100', '370000', '日照市', '中国,山东省,日照市', '日照', '中国,山东,日照', '2', '0633', '276800', 'Rizhao', 'RZ', 'R', '119.461208', '35.428588'),
('1560', '371102', '371100', '东港区', '中国,山东省,日照市,东港区', '东港', '中国,山东,日照,东港', '3', '0633', '276800', 'Donggang', 'DG', 'D', '119.46237', '35.42541'),
('1561', '371103', '371100', '岚山区', '中国,山东省,日照市,岚山区', '岚山', '中国,山东,日照,岚山', '3', '0633', '276800', 'Lanshan', 'LS', 'L', '119.31884', '35.12203'),
('1562', '371121', '371100', '五莲县', '中国,山东省,日照市,五莲县', '五莲', '中国,山东,日照,五莲', '3', '0633', '262300', 'Wulian', 'WL', 'W', '119.207', '35.75004'),
('1563', '371122', '371100', '莒县', '中国,山东省,日照市,莒县', '莒县', '中国,山东,日照,莒县', '3', '0633', '276500', 'Juxian', 'JX', 'J', '118.83789', '35.58054'),
('1564', '371200', '370000', '莱芜市', '中国,山东省,莱芜市', '莱芜', '中国,山东,莱芜', '2', '0634', '271100', 'Laiwu', 'LW', 'L', '117.677736', '36.214397'),
('1565', '371202', '371200', '莱城区', '中国,山东省,莱芜市,莱城区', '莱城', '中国,山东,莱芜,莱城', '3', '0634', '271100', 'Laicheng', 'LC', 'L', '117.65986', '36.2032'),
('1566', '371203', '371200', '钢城区', '中国,山东省,莱芜市,钢城区', '钢城', '中国,山东,莱芜,钢城', '3', '0634', '271100', 'Gangcheng', 'GC', 'G', '117.8049', '36.06319'),
('1567', '371300', '370000', '临沂市', '中国,山东省,临沂市', '临沂', '中国,山东,临沂', '2', '0539', '276000', 'Linyi', 'LY', 'L', '118.326443', '35.065282'),
('1568', '371302', '371300', '兰山区', '中国,山东省,临沂市,兰山区', '兰山', '中国,山东,临沂,兰山', '3', '0539', '276000', 'Lanshan', 'LS', 'L', '118.34817', '35.06872'),
('1569', '371311', '371300', '罗庄区', '中国,山东省,临沂市,罗庄区', '罗庄', '中国,山东,临沂,罗庄', '3', '0539', '276000', 'Luozhuang', 'LZ', 'L', '118.28466', '34.99627'),
('1570', '371312', '371300', '河东区', '中国,山东省,临沂市,河东区', '河东', '中国,山东,临沂,河东', '3', '0539', '276000', 'Hedong', 'HD', 'H', '118.41055', '35.08803'),
('1571', '371321', '371300', '沂南县', '中国,山东省,临沂市,沂南县', '沂南', '中国,山东,临沂,沂南', '3', '0539', '276300', 'Yinan', 'YN', 'Y', '118.47061', '35.55131'),
('1572', '371322', '371300', '郯城县', '中国,山东省,临沂市,郯城县', '郯城', '中国,山东,临沂,郯城', '3', '0539', '276100', 'Tancheng', 'TC', 'T', '118.36712', '34.61354'),
('1573', '371323', '371300', '沂水县', '中国,山东省,临沂市,沂水县', '沂水', '中国,山东,临沂,沂水', '3', '0539', '276400', 'Yishui', 'YS', 'Y', '118.63009', '35.78731'),
('1574', '371324', '371300', '兰陵县', '中国,山东省,临沂市,兰陵县', '兰陵', '中国,山东,临沂,兰陵', '3', '0539', '277700', 'Lanling', 'LL', 'L', '117.856592', '34.738315'),
('1575', '371325', '371300', '费县', '中国,山东省,临沂市,费县', '费县', '中国,山东,临沂,费县', '3', '0539', '273400', 'Feixian', 'FX', 'F', '117.97836', '35.26562'),
('1576', '371326', '371300', '平邑县', '中国,山东省,临沂市,平邑县', '平邑', '中国,山东,临沂,平邑', '3', '0539', '273300', 'Pingyi', 'PY', 'P', '117.63867', '35.50573'),
('1577', '371327', '371300', '莒南县', '中国,山东省,临沂市,莒南县', '莒南', '中国,山东,临沂,莒南', '3', '0539', '276600', 'Junan', 'JN', 'J', '118.83227', '35.17539'),
('1578', '371328', '371300', '蒙阴县', '中国,山东省,临沂市,蒙阴县', '蒙阴', '中国,山东,临沂,蒙阴', '3', '0539', '276200', 'Mengyin', 'MY', 'M', '117.94592', '35.70996'),
('1579', '371329', '371300', '临沭县', '中国,山东省,临沂市,临沭县', '临沭', '中国,山东,临沂,临沭', '3', '0539', '276700', 'Linshu', 'LS', 'L', '118.65267', '34.92091'),
('1580', '371400', '370000', '德州市', '中国,山东省,德州市', '德州', '中国,山东,德州', '2', '0534', '253000', 'Dezhou', 'DZ', 'D', '116.307428', '37.453968'),
('1581', '371402', '371400', '德城区', '中国,山东省,德州市,德城区', '德城', '中国,山东,德州,德城', '3', '0534', '253000', 'Decheng', 'DC', 'D', '116.29943', '37.45126'),
('1582', '371403', '371400', '陵城区', '中国,山东省,德州市,陵城区', '陵城', '中国,山东,德州,陵城', '3', '0534', '253500', 'Lingcheng', 'LC', 'L', '116.57601', '37.33571'),
('1583', '371422', '371400', '宁津县', '中国,山东省,德州市,宁津县', '宁津', '中国,山东,德州,宁津', '3', '0534', '253400', 'Ningjin', 'NJ', 'N', '116.79702', '37.65301'),
('1584', '371423', '371400', '庆云县', '中国,山东省,德州市,庆云县', '庆云', '中国,山东,德州,庆云', '3', '0534', '253700', 'Qingyun', 'QY', 'Q', '117.38635', '37.77616'),
('1585', '371424', '371400', '临邑县', '中国,山东省,德州市,临邑县', '临邑', '中国,山东,德州,临邑', '3', '0534', '251500', 'Linyi', 'LY', 'L', '116.86547', '37.19053'),
('1586', '371425', '371400', '齐河县', '中国,山东省,德州市,齐河县', '齐河', '中国,山东,德州,齐河', '3', '0534', '251100', 'Qihe', 'QH', 'Q', '116.75515', '36.79532'),
('1587', '371426', '371400', '平原县', '中国,山东省,德州市,平原县', '平原', '中国,山东,德州,平原', '3', '0534', '253100', 'Pingyuan', 'PY', 'P', '116.43432', '37.16632'),
('1588', '371427', '371400', '夏津县', '中国,山东省,德州市,夏津县', '夏津', '中国,山东,德州,夏津', '3', '0534', '253200', 'Xiajin', 'XJ', 'X', '116.0017', '36.94852'),
('1589', '371428', '371400', '武城县', '中国,山东省,德州市,武城县', '武城', '中国,山东,德州,武城', '3', '0534', '253300', 'Wucheng', 'WC', 'W', '116.07009', '37.21403'),
('1590', '371481', '371400', '乐陵市', '中国,山东省,德州市,乐陵市', '乐陵', '中国,山东,德州,乐陵', '3', '0534', '253600', 'Leling', 'LL', 'L', '117.23141', '37.73164'),
('1591', '371482', '371400', '禹城市', '中国,山东省,德州市,禹城市', '禹城', '中国,山东,德州,禹城', '3', '0534', '251200', 'Yucheng', 'YC', 'Y', '116.64309', '36.93444'),
('1592', '371500', '370000', '聊城市', '中国,山东省,聊城市', '聊城', '中国,山东,聊城', '2', '0635', '252000', 'Liaocheng', 'LC', 'L', '115.980367', '36.456013'),
('1593', '371502', '371500', '东昌府区', '中国,山东省,聊城市,东昌府区', '东昌府', '中国,山东,聊城,东昌府', '3', '0635', '252000', 'Dongchangfu', 'DCF', 'D', '115.97383', '36.44458'),
('1594', '371521', '371500', '阳谷县', '中国,山东省,聊城市,阳谷县', '阳谷', '中国,山东,聊城,阳谷', '3', '0635', '252300', 'Yanggu', 'YG', 'Y', '115.79126', '36.11444'),
('1595', '371522', '371500', '莘县', '中国,山东省,聊城市,莘县', '莘县', '中国,山东,聊城,莘县', '3', '0635', '252400', 'Shenxian', 'SX', 'S', '115.6697', '36.23423'),
('1596', '371523', '371500', '茌平县', '中国,山东省,聊城市,茌平县', '茌平', '中国,山东,聊城,茌平', '3', '0635', '252100', 'Chiping', 'CP', 'C', '116.25491', '36.57969'),
('1597', '371524', '371500', '东阿县', '中国,山东省,聊城市,东阿县', '东阿', '中国,山东,聊城,东阿', '3', '0635', '252200', 'Dong\'e', 'DE', 'D', '116.25012', '36.33209'),
('1598', '371525', '371500', '冠县', '中国,山东省,聊城市,冠县', '冠县', '中国,山东,聊城,冠县', '3', '0635', '252500', 'Guanxian', 'GX', 'G', '115.44195', '36.48429'),
('1599', '371526', '371500', '高唐县', '中国,山东省,聊城市,高唐县', '高唐', '中国,山东,聊城,高唐', '3', '0635', '252800', 'Gaotang', 'GT', 'G', '116.23172', '36.86535'),
('1600', '371581', '371500', '临清市', '中国,山东省,聊城市,临清市', '临清', '中国,山东,聊城,临清', '3', '0635', '252600', 'Linqing', 'LQ', 'L', '115.70629', '36.83945'),
('1601', '371600', '370000', '滨州市', '中国,山东省,滨州市', '滨州', '中国,山东,滨州', '2', '0543', '256600', 'Binzhou', 'BZ', 'B', '118.016974', '37.383542'),
('1602', '371602', '371600', '滨城区', '中国,山东省,滨州市,滨城区', '滨城', '中国,山东,滨州,滨城', '3', '0543', '256600', 'Bincheng', 'BC', 'B', '118.02026', '37.38524'),
('1603', '371603', '371600', '沾化区', '中国,山东省,滨州市,沾化区', '沾化', '中国,山东,滨州,沾化', '3', '0543', '256800', 'Zhanhua', 'ZH', 'Z', '118.13214', '37.69832'),
('1604', '371621', '371600', '惠民县', '中国,山东省,滨州市,惠民县', '惠民', '中国,山东,滨州,惠民', '3', '0543', '251700', 'Huimin', 'HM', 'H', '117.51113', '37.49013'),
('1605', '371622', '371600', '阳信县', '中国,山东省,滨州市,阳信县', '阳信', '中国,山东,滨州,阳信', '3', '0543', '251800', 'Yangxin', 'YX', 'Y', '117.58139', '37.64198'),
('1606', '371623', '371600', '无棣县', '中国,山东省,滨州市,无棣县', '无棣', '中国,山东,滨州,无棣', '3', '0543', '251900', 'Wudi', 'WD', 'W', '117.61395', '37.74009'),
('1607', '371625', '371600', '博兴县', '中国,山东省,滨州市,博兴县', '博兴', '中国,山东,滨州,博兴', '3', '0543', '256500', 'Boxing', 'BX', 'B', '118.1336', '37.14316'),
('1608', '371626', '371600', '邹平县', '中国,山东省,滨州市,邹平县', '邹平', '中国,山东,滨州,邹平', '3', '0543', '256200', 'Zouping', 'ZP', 'Z', '117.74307', '36.86295'),
('1609', '371627', '371600', '北海新区', '中国,山东省,滨州市,北海新区', '北海新区', '中国,山东,滨州,北海新区', '3', '0543', '256200', 'Beihaixinqu', 'BHXQ', 'B', '118.016974', '37.383542'),
('1610', '371700', '370000', '菏泽市', '中国,山东省,菏泽市', '菏泽', '中国,山东,菏泽', '2', '0530', '274000', 'Heze', 'HZ', 'H', '115.469381', '35.246531'),
('1611', '371702', '371700', '牡丹区', '中国,山东省,菏泽市,牡丹区', '牡丹', '中国,山东,菏泽,牡丹', '3', '0530', '274000', 'Mudan', 'MD', 'M', '115.41662', '35.25091'),
('1612', '371703', '371700', '定陶区', '中国,山东省,菏泽市,定陶区', '定陶', '中国,山东,菏泽,定陶', '3', '0530', '274100', 'Dingtao', 'DT', 'D', '115.57287', '35.07118'),
('1613', '371721', '371700', '曹县', '中国,山东省,菏泽市,曹县', '曹县', '中国,山东,菏泽,曹县', '3', '0530', '274400', 'Caoxian', 'CX', 'C', '115.54226', '34.82659'),
('1614', '371722', '371700', '单县', '中国,山东省,菏泽市,单县', '单县', '中国,山东,菏泽,单县', '3', '0530', '274300', 'Shanxian', 'SX', 'S', '116.08703', '34.79514'),
('1615', '371723', '371700', '成武县', '中国,山东省,菏泽市,成武县', '成武', '中国,山东,菏泽,成武', '3', '0530', '274200', 'Chengwu', 'CW', 'C', '115.8897', '34.95332'),
('1616', '371724', '371700', '巨野县', '中国,山东省,菏泽市,巨野县', '巨野', '中国,山东,菏泽,巨野', '3', '0530', '274900', 'Juye', 'JY', 'J', '116.09497', '35.39788'),
('1617', '371725', '371700', '郓城县', '中国,山东省,菏泽市,郓城县', '郓城', '中国,山东,菏泽,郓城', '3', '0530', '274700', 'Yuncheng', 'YC', 'Y', '115.94439', '35.60044'),
('1618', '371726', '371700', '鄄城县', '中国,山东省,菏泽市,鄄城县', '鄄城', '中国,山东,菏泽,鄄城', '3', '0530', '274600', 'Juancheng', 'JC', 'J', '115.50997', '35.56412'),
('1619', '371728', '371700', '东明县', '中国,山东省,菏泽市,东明县', '东明', '中国,山东,菏泽,东明', '3', '0530', '274500', 'Dongming', 'DM', 'D', '115.09079', '35.28906'),
('1620', '410000', '100000', '河南省', '中国,河南省', '河南', '中国,河南', '1', '', '', 'Henan', 'HA', 'H', '113.665412', '34.757975'),
('1621', '410100', '410000', '郑州市', '中国,河南省,郑州市', '郑州', '中国,河南,郑州', '2', '0371', '450000', 'Zhengzhou', 'ZZ', 'Z', '113.665412', '34.757975'),
('1622', '410102', '410100', '中原区', '中国,河南省,郑州市,中原区', '中原', '中国,河南,郑州,中原', '3', '0371', '450000', 'Zhongyuan', 'ZY', 'Z', '113.61333', '34.74827'),
('1623', '410103', '410100', '二七区', '中国,河南省,郑州市,二七区', '二七', '中国,河南,郑州,二七', '3', '0371', '450000', 'Erqi', 'EQ', 'E', '113.63931', '34.72336'),
('1624', '410104', '410100', '管城回族区', '中国,河南省,郑州市,管城回族区', '管城', '中国,河南,郑州,管城', '3', '0371', '450000', 'Guancheng', 'GC', 'G', '113.67734', '34.75383'),
('1625', '410105', '410100', '金水区', '中国,河南省,郑州市,金水区', '金水', '中国,河南,郑州,金水', '3', '0371', '450000', 'Jinshui', 'JS', 'J', '113.66057', '34.80028'),
('1626', '410106', '410100', '上街区', '中国,河南省,郑州市,上街区', '上街', '中国,河南,郑州,上街', '3', '0371', '450041', 'Shangjie', 'SJ', 'S', '113.30897', '34.80276'),
('1627', '410108', '410100', '惠济区', '中国,河南省,郑州市,惠济区', '惠济', '中国,河南,郑州,惠济', '3', '0371', '450053', 'Huiji', 'HJ', 'H', '113.61688', '34.86735'),
('1628', '410122', '410100', '中牟县', '中国,河南省,郑州市,中牟县', '中牟', '中国,河南,郑州,中牟', '3', '0371', '451450', 'Zhongmu', 'ZM', 'Z', '113.97619', '34.71899'),
('1629', '410181', '410100', '巩义市', '中国,河南省,郑州市,巩义市', '巩义', '中国,河南,郑州,巩义', '3', '0371', '451200', 'Gongyi', 'GY', 'G', '113.022', '34.74794'),
('1630', '410182', '410100', '荥阳市', '中国,河南省,郑州市,荥阳市', '荥阳', '中国,河南,郑州,荥阳', '3', '0371', '450100', 'Xingyang', 'XY', 'X', '113.38345', '34.78759'),
('1631', '410183', '410100', '新密市', '中国,河南省,郑州市,新密市', '新密', '中国,河南,郑州,新密', '3', '0371', '452370', 'Xinmi', 'XM', 'X', '113.3869', '34.53704'),
('1632', '410184', '410100', '新郑市', '中国,河南省,郑州市,新郑市', '新郑', '中国,河南,郑州,新郑', '3', '0371', '451100', 'Xinzheng', 'XZ', 'X', '113.73645', '34.3955'),
('1633', '410185', '410100', '登封市', '中国,河南省,郑州市,登封市', '登封', '中国,河南,郑州,登封', '3', '0371', '452470', 'Dengfeng', 'DF', 'D', '113.05023', '34.45345'),
('1634', '410186', '410100', '郑东新区', '中国,河南省,郑州市,郑东新区', '郑东新区', '中国,河南,郑州,郑东新区', '3', '0371', '450018', 'Zhengdongxinqu', 'ZDXQ', 'Z', '113.728667', '34.769628'),
('1635', '410187', '410100', '郑汴新区', '中国,河南省,郑州市,郑汴新区', '郑汴新区', '中国,河南,郑州,郑汴新区', '3', '0371', '451100', 'Zhengbianxinqu', 'ZBXQ', 'Z', '113.692694', '34.747034'),
('1636', '410188', '410100', '高新开发区', '中国,河南省,郑州市,高新开发区', '高新开发区', '中国,河南,郑州,高新开发区', '3', '0371', '450000', 'Gaoxinqu', 'GXQ', 'G', '113.567406', '34.81207'),
('1637', '410189', '410100', '经济开发区', '中国,河南省,郑州市,经济开发区', '经济开发区', '中国,河南,郑州,经济开发区', '3', '0371', '450000', 'Jingjiqu', 'JJQ', 'J', '113.743089', '34.72195'),
('1638', '410200', '410000', '开封市', '中国,河南省,开封市', '开封', '中国,河南,开封', '2', '0371', '475000', 'Kaifeng', 'KF', 'K', '114.341447', '34.797049'),
('1639', '410202', '410200', '龙亭区', '中国,河南省,开封市,龙亭区', '龙亭', '中国,河南,开封,龙亭', '3', '0371', '475000', 'Longting', 'LT', 'L', '114.35484', '34.79995'),
('1640', '410203', '410200', '顺河回族区', '中国,河南省,开封市,顺河回族区', '顺河', '中国,河南,开封,顺河', '3', '0371', '475000', 'Shunhe', 'SH', 'S', '114.36123', '34.79586'),
('1641', '410204', '410200', '鼓楼区', '中国,河南省,开封市,鼓楼区', '鼓楼', '中国,河南,开封,鼓楼', '3', '0371', '475000', 'Gulou', 'GL', 'G', '114.35559', '34.79517'),
('1642', '410205', '410200', '禹王台区', '中国,河南省,开封市,禹王台区', '禹王台', '中国,河南,开封,禹王台', '3', '0371', '475000', 'Yuwangtai', 'YWT', 'Y', '114.34787', '34.77693'),
('1643', '410212', '410200', '祥符区', '中国,河南省,开封市,祥符区', '祥符', '中国,河南,开封,祥符', '3', '0371', '475100', 'Xiangfu', 'XF', 'X', '114.43859', '34.75874'),
('1644', '410221', '410200', '杞县', '中国,河南省,开封市,杞县', '杞县', '中国,河南,开封,杞县', '3', '0371', '475200', 'Qixian', 'QX', 'Q', '114.7828', '34.55033'),
('1645', '410222', '410200', '通许县', '中国,河南省,开封市,通许县', '通许', '中国,河南,开封,通许', '3', '0371', '475400', 'Tongxu', 'TX', 'T', '114.46716', '34.47522'),
('1646', '410223', '410200', '尉氏县', '中国,河南省,开封市,尉氏县', '尉氏', '中国,河南,开封,尉氏', '3', '0371', '475500', 'Weishi', 'WS', 'W', '114.19284', '34.41223'),
('1647', '410225', '410200', '兰考县', '中国,河南省,开封市,兰考县', '兰考', '中国,河南,开封,兰考', '3', '0371', '475300', 'Lankao', 'LK', 'L', '114.81961', '34.8235'),
('1648', '410226', '410200', '经济技术开发区', '中国,河南省,开封市,经济技术开发区', '兰考', '中国,河南,开封,经济技术开发区', '3', '0371', '475000', 'JingJiKaiFaQu', 'JJKFQ', 'J', '114.292303', '34.786812'),
('1649', '410300', '410000', '洛阳市', '中国,河南省,洛阳市', '洛阳', '中国,河南,洛阳', '2', '0379', '471000', 'Luoyang', 'LY', 'L', '112.434468', '34.663041'),
('1650', '410302', '410300', '老城区', '中国,河南省,洛阳市,老城区', '老城', '中国,河南,洛阳,老城', '3', '0379', '471000', 'Laocheng', 'LC', 'L', '112.46902', '34.68364'),
('1651', '410303', '410300', '西工区', '中国,河南省,洛阳市,西工区', '西工', '中国,河南,洛阳,西工', '3', '0379', '471000', 'Xigong', 'XG', 'X', '112.4371', '34.67'),
('1652', '410304', '410300', '瀍河回族区', '中国,河南省,洛阳市,瀍河回族区', '瀍河', '中国,河南,洛阳,瀍河', '3', '0379', '471002', 'Chanhe', 'CH', 'C', '112.50018', '34.67985'),
('1653', '410305', '410300', '涧西区', '中国,河南省,洛阳市,涧西区', '涧西', '中国,河南,洛阳,涧西', '3', '0379', '471000', 'Jianxi', 'JX', 'J', '112.39588', '34.65823'),
('1654', '410306', '410300', '吉利区', '中国,河南省,洛阳市,吉利区', '吉利', '中国,河南,洛阳,吉利', '3', '0379', '471000', 'Jili', 'JL', 'J', '112.58905', '34.90088'),
('1655', '410311', '410300', '洛龙区', '中国,河南省,洛阳市,洛龙区', '洛龙', '中国,河南,洛阳,洛龙', '3', '0379', '471000', 'Luolong', 'LL', 'L', '112.46412', '34.61866'),
('1656', '410322', '410300', '孟津县', '中国,河南省,洛阳市,孟津县', '孟津', '中国,河南,洛阳,孟津', '3', '0379', '471100', 'Mengjin', 'MJ', 'M', '112.44351', '34.826'),
('1657', '410323', '410300', '新安县', '中国,河南省,洛阳市,新安县', '新安', '中国,河南,洛阳,新安', '3', '0379', '471800', 'Xin\'an', 'XA', 'X', '112.13238', '34.72814'),
('1658', '410324', '410300', '栾川县', '中国,河南省,洛阳市,栾川县', '栾川', '中国,河南,洛阳,栾川', '3', '0379', '471500', 'Luanchuan', 'LC', 'L', '111.61779', '33.78576'),
('1659', '410325', '410300', '嵩县', '中国,河南省,洛阳市,嵩县', '嵩县', '中国,河南,洛阳,嵩县', '3', '0379', '471400', 'Songxian', 'SX', 'S', '112.08526', '34.13466'),
('1660', '410326', '410300', '汝阳县', '中国,河南省,洛阳市,汝阳县', '汝阳', '中国,河南,洛阳,汝阳', '3', '0379', '471200', 'Ruyang', 'RY', 'R', '112.47314', '34.15387'),
('1661', '410327', '410300', '宜阳县', '中国,河南省,洛阳市,宜阳县', '宜阳', '中国,河南,洛阳,宜阳', '3', '0379', '471600', 'Yiyang', 'YY', 'Y', '112.17907', '34.51523'),
('1662', '410328', '410300', '洛宁县', '中国,河南省,洛阳市,洛宁县', '洛宁', '中国,河南,洛阳,洛宁', '3', '0379', '471700', 'Luoning', 'LN', 'L', '111.65087', '34.38913'),
('1663', '410329', '410300', '伊川县', '中国,河南省,洛阳市,伊川县', '伊川', '中国,河南,洛阳,伊川', '3', '0379', '471300', 'Yichuan', 'YC', 'Y', '112.42947', '34.42205'),
('1664', '410381', '410300', '偃师市', '中国,河南省,洛阳市,偃师市', '偃师', '中国,河南,洛阳,偃师', '3', '0379', '471900', 'Yanshi', 'YS', 'Y', '112.7922', '34.7281'),
('1665', '410382', '410300', '洛阳新区', '中国,河南省,洛阳市,洛阳新区', '洛阳新区', '中国,河南,洛阳,洛阳新区', '3', '0379', '471000', 'Luoyangxinqu', 'LYXQ', 'L', '112.48219', '34.609795'),
('1666', '410383', '410300', '高新区', '中国,河南省,洛阳市,高新区', '高新区', '中国,河南,洛阳,高新区', '3', '0379', '471000', 'Gaoxinqu', 'GXQ', 'G', '112.371729', '34.64226'),
('1667', '410400', '410000', '平顶山市', '中国,河南省,平顶山市', '平顶山', '中国,河南,平顶山', '2', '0375', '467000', 'Pingdingshan', 'PDS', 'P', '113.307718', '33.735241'),
('1668', '410402', '410400', '新华区', '中国,河南省,平顶山市,新华区', '新华', '中国,河南,平顶山,新华', '3', '0375', '467000', 'Xinhua', 'XH', 'X', '113.29402', '33.7373'),
('1669', '410403', '410400', '卫东区', '中国,河南省,平顶山市,卫东区', '卫东', '中国,河南,平顶山,卫东', '3', '0375', '467000', 'Weidong', 'WD', 'W', '113.33511', '33.73472'),
('1670', '410404', '410400', '石龙区', '中国,河南省,平顶山市,石龙区', '石龙', '中国,河南,平顶山,石龙', '3', '0375', '467000', 'Shilong', 'SL', 'S', '112.89879', '33.89878'),
('1671', '410411', '410400', '湛河区', '中国,河南省,平顶山市,湛河区', '湛河', '中国,河南,平顶山,湛河', '3', '0375', '467000', 'Zhanhe', 'ZH', 'Z', '113.29252', '33.7362'),
('1672', '410421', '410400', '宝丰县', '中国,河南省,平顶山市,宝丰县', '宝丰', '中国,河南,平顶山,宝丰', '3', '0375', '467400', 'Baofeng', 'BF', 'B', '113.05493', '33.86916'),
('1673', '410422', '410400', '叶县', '中国,河南省,平顶山市,叶县', '叶县', '中国,河南,平顶山,叶县', '3', '0375', '467200', 'Yexian', 'YX', 'Y', '113.35104', '33.62225'),
('1674', '410423', '410400', '鲁山县', '中国,河南省,平顶山市,鲁山县', '鲁山', '中国,河南,平顶山,鲁山', '3', '0375', '467300', 'Lushan', 'LS', 'L', '112.9057', '33.73879'),
('1675', '410425', '410400', '郏县', '中国,河南省,平顶山市,郏县', '郏县', '中国,河南,平顶山,郏县', '3', '0375', '467100', 'Jiaxian', 'JX', 'J', '113.21588', '33.97072'),
('1676', '410481', '410400', '舞钢市', '中国,河南省,平顶山市,舞钢市', '舞钢', '中国,河南,平顶山,舞钢', '3', '0375', '462500', 'Wugang', 'WG', 'W', '113.52417', '33.2938'),
('1677', '410482', '410400', '汝州市', '中国,河南省,平顶山市,汝州市', '汝州', '中国,河南,平顶山,汝州', '3', '0375', '467500', 'Ruzhou', 'RZ', 'R', '112.84301', '34.16135'),
('1678', '410500', '410000', '安阳市', '中国,河南省,安阳市', '安阳', '中国,河南,安阳', '2', '0372', '455000', 'Anyang', 'AY', 'A', '114.352482', '36.103442'),
('1679', '410502', '410500', '文峰区', '中国,河南省,安阳市,文峰区', '文峰', '中国,河南,安阳,文峰', '3', '0372', '455000', 'Wenfeng', 'WF', 'W', '114.35708', '36.09046'),
('1680', '410503', '410500', '北关区', '中国,河南省,安阳市,北关区', '北关', '中国,河南,安阳,北关', '3', '0372', '455000', 'Beiguan', 'BG', 'B', '114.35735', '36.11872'),
('1681', '410505', '410500', '殷都区', '中国,河南省,安阳市,殷都区', '殷都', '中国,河南,安阳,殷都', '3', '0372', '455000', 'Yindu', 'YD', 'Y', '114.3034', '36.1099'),
('1682', '410506', '410500', '龙安区', '中国,河南省,安阳市,龙安区', '龙安', '中国,河南,安阳,龙安', '3', '0372', '455000', 'Long\'an', 'LA', 'L', '114.34814', '36.11904'),
('1683', '410522', '410500', '安阳县', '中国,河南省,安阳市,安阳县', '安阳', '中国,河南,安阳,安阳', '3', '0372', '455100', 'Anyang', 'AY', 'A', '114.36605', '36.06695'),
('1684', '410523', '410500', '汤阴县', '中国,河南省,安阳市,汤阴县', '汤阴', '中国,河南,安阳,汤阴', '3', '0372', '456150', 'Tangyin', 'TY', 'T', '114.35839', '35.92152'),
('1685', '410526', '410500', '滑县', '中国,河南省,安阳市,滑县', '滑县', '中国,河南,安阳,滑县', '3', '0372', '456400', 'Huaxian', 'HX', 'H', '114.52066', '35.5807'),
('1686', '410527', '410500', '内黄县', '中国,河南省,安阳市,内黄县', '内黄', '中国,河南,安阳,内黄', '3', '0372', '456300', 'Neihuang', 'NH', 'N', '114.90673', '35.95269'),
('1687', '410581', '410500', '林州市', '中国,河南省,安阳市,林州市', '林州', '中国,河南,安阳,林州', '3', '0372', '456500', 'Linzhou', 'LZ', 'L', '113.81558', '36.07804'),
('1688', '410582', '410500', '安阳新区', '中国,河南省,安阳市,安阳新区', '安阳新区', '中国,河南,安阳,安阳新区', '3', '0372', '456500', 'Anyangxinqu', 'AYXQ', 'A', '114.45282', '36.083928'),
('1689', '410600', '410000', '鹤壁市', '中国,河南省,鹤壁市', '鹤壁', '中国,河南,鹤壁', '2', '0392', '458000', 'Hebi', 'HB', 'H', '114.295444', '35.748236'),
('1690', '410602', '410600', '鹤山区', '中国,河南省,鹤壁市,鹤山区', '鹤山', '中国,河南,鹤壁,鹤山', '3', '0392', '458000', 'Heshan', 'HS', 'H', '114.16336', '35.95458'),
('1691', '410603', '410600', '山城区', '中国,河南省,鹤壁市,山城区', '山城', '中国,河南,鹤壁,山城', '3', '0392', '458000', 'Shancheng', 'SC', 'S', '114.18443', '35.89773'),
('1692', '410611', '410600', '淇滨区', '中国,河南省,鹤壁市,淇滨区', '淇滨', '中国,河南,鹤壁,淇滨', '3', '0392', '458000', 'Qibin', 'QB', 'Q', '114.29867', '35.74127'),
('1693', '410621', '410600', '浚县', '中国,河南省,鹤壁市,浚县', '浚县', '中国,河南,鹤壁,浚县', '3', '0392', '456250', 'Xunxian', 'XX', 'X', '114.54879', '35.67085'),
('1694', '410622', '410600', '淇县', '中国,河南省,鹤壁市,淇县', '淇县', '中国,河南,鹤壁,淇县', '3', '0392', '456750', 'Qixian', 'QX', 'Q', '114.1976', '35.60782'),
('1695', '410700', '410000', '新乡市', '中国,河南省,新乡市', '新乡', '中国,河南,新乡', '2', '0373', '453000', 'Xinxiang', 'XX', 'X', '113.883991', '35.302616'),
('1696', '410702', '410700', '红旗区', '中国,河南省,新乡市,红旗区', '红旗', '中国,河南,新乡,红旗', '3', '0373', '453000', 'Hongqi', 'HQ', 'H', '113.87523', '35.30367'),
('1697', '410703', '410700', '卫滨区', '中国,河南省,新乡市,卫滨区', '卫滨', '中国,河南,新乡,卫滨', '3', '0373', '453000', 'Weibin', 'WB', 'W', '113.86578', '35.30211'),
('1698', '410704', '410700', '凤泉区', '中国,河南省,新乡市,凤泉区', '凤泉', '中国,河南,新乡,凤泉', '3', '0373', '453011', 'Fengquan', 'FQ', 'F', '113.91507', '35.38399'),
('1699', '410711', '410700', '牧野区', '中国,河南省,新乡市,牧野区', '牧野', '中国,河南,新乡,牧野', '3', '0373', '453002', 'Muye', 'MY', 'M', '113.9086', '35.3149'),
('1700', '410721', '410700', '新乡县', '中国,河南省,新乡市,新乡县', '新乡', '中国,河南,新乡,新乡', '3', '0373', '453700', 'Xinxiang', 'XX', 'X', '113.80511', '35.19075'),
('1701', '410724', '410700', '获嘉县', '中国,河南省,新乡市,获嘉县', '获嘉', '中国,河南,新乡,获嘉', '3', '0373', '453800', 'Huojia', 'HJ', 'H', '113.66159', '35.26521'),
('1702', '410725', '410700', '原阳县', '中国,河南省,新乡市,原阳县', '原阳', '中国,河南,新乡,原阳', '3', '0373', '453500', 'Yuanyang', 'YY', 'Y', '113.93994', '35.06565'),
('1703', '410726', '410700', '延津县', '中国,河南省,新乡市,延津县', '延津', '中国,河南,新乡,延津', '3', '0373', '453200', 'Yanjin', 'YJ', 'Y', '114.20266', '35.14327'),
('1704', '410727', '410700', '封丘县', '中国,河南省,新乡市,封丘县', '封丘', '中国,河南,新乡,封丘', '3', '0373', '453300', 'Fengqiu', 'FQ', 'F', '114.41915', '35.04166'),
('1705', '410728', '410700', '长垣县', '中国,河南省,新乡市,长垣县', '长垣', '中国,河南,新乡,长垣', '3', '0373', '453400', 'Changyuan', 'CY', 'C', '114.66882', '35.20046'),
('1706', '410781', '410700', '卫辉市', '中国,河南省,新乡市,卫辉市', '卫辉', '中国,河南,新乡,卫辉', '3', '0373', '453100', 'Weihui', 'WH', 'W', '114.06454', '35.39843'),
('1707', '410782', '410700', '辉县市', '中国,河南省,新乡市,辉县市', '辉县', '中国,河南,新乡,辉县', '3', '0373', '453600', 'Huixian', 'HX', 'H', '113.8067', '35.46307'),
('1708', '410800', '410000', '焦作市', '中国,河南省,焦作市', '焦作', '中国,河南,焦作', '2', '0391', '454000', 'Jiaozuo', 'JZ', 'J', '113.238266', '35.23904'),
('1709', '410802', '410800', '解放区', '中国,河南省,焦作市,解放区', '解放', '中国,河南,焦作,解放', '3', '0391', '454150', 'Jiefang', 'JF', 'J', '113.22933', '35.24023'),
('1710', '410803', '410800', '中站区', '中国,河南省,焦作市,中站区', '中站', '中国,河南,焦作,中站', '3', '0391', '454150', 'Zhongzhan', 'ZZ', 'Z', '113.18315', '35.23665'),
('1711', '410804', '410800', '马村区', '中国,河南省,焦作市,马村区', '马村', '中国,河南,焦作,马村', '3', '0391', '454150', 'Macun', 'MC', 'M', '113.3187', '35.26908'),
('1712', '410811', '410800', '山阳区', '中国,河南省,焦作市,山阳区', '山阳', '中国,河南,焦作,山阳', '3', '0391', '454150', 'Shanyang', 'SY', 'S', '113.25464', '35.21436'),
('1713', '410821', '410800', '修武县', '中国,河南省,焦作市,修武县', '修武', '中国,河南,焦作,修武', '3', '0391', '454350', 'Xiuwu', 'XW', 'X', '113.44775', '35.22357'),
('1714', '410822', '410800', '博爱县', '中国,河南省,焦作市,博爱县', '博爱', '中国,河南,焦作,博爱', '3', '0391', '454450', 'Boai', 'BA', 'B', '113.06698', '35.16943'),
('1715', '410823', '410800', '武陟县', '中国,河南省,焦作市,武陟县', '武陟', '中国,河南,焦作,武陟', '3', '0391', '454950', 'Wuzhi', 'WZ', 'W', '113.39718', '35.09505'),
('1716', '410825', '410800', '温县', '中国,河南省,焦作市,温县', '温县', '中国,河南,焦作,温县', '3', '0391', '454850', 'Wenxian', 'WX', 'W', '113.08065', '34.94022'),
('1717', '410882', '410800', '沁阳市', '中国,河南省,焦作市,沁阳市', '沁阳', '中国,河南,焦作,沁阳', '3', '0391', '454550', 'Qinyang', 'QY', 'Q', '112.94494', '35.08935'),
('1718', '410883', '410800', '孟州市', '中国,河南省,焦作市,孟州市', '孟州', '中国,河南,焦作,孟州', '3', '0391', '454750', 'Mengzhou', 'MZ', 'M', '112.79138', '34.9071'),
('1719', '410900', '410000', '濮阳市', '中国,河南省,濮阳市', '濮阳', '中国,河南,濮阳', '2', '0393', '457000', 'Puyang', 'PY', 'P', '115.041299', '35.768234'),
('1720', '410902', '410900', '华龙区', '中国,河南省,濮阳市,华龙区', '华龙', '中国,河南,濮阳,华龙', '3', '0393', '457001', 'Hualong', 'HL', 'H', '115.07446', '35.77736'),
('1721', '410922', '410900', '清丰县', '中国,河南省,濮阳市,清丰县', '清丰', '中国,河南,濮阳,清丰', '3', '0393', '457300', 'Qingfeng', 'QF', 'Q', '115.10415', '35.88507'),
('1722', '410923', '410900', '南乐县', '中国,河南省,濮阳市,南乐县', '南乐', '中国,河南,濮阳,南乐', '3', '0393', '457400', 'Nanle', 'NL', 'N', '115.20639', '36.07686'),
('1723', '410926', '410900', '范县', '中国,河南省,濮阳市,范县', '范县', '中国,河南,濮阳,范县', '3', '0393', '457500', 'Fanxian', 'FX', 'F', '115.50405', '35.85178'),
('1724', '410927', '410900', '台前县', '中国,河南省,濮阳市,台前县', '台前', '中国,河南,濮阳,台前', '3', '0393', '457600', 'Taiqian', 'TQ', 'T', '115.87158', '35.96923'),
('1725', '410928', '410900', '濮阳县', '中国,河南省,濮阳市,濮阳县', '濮阳', '中国,河南,濮阳,濮阳', '3', '0393', '457100', 'Puyang', 'PY', 'P', '115.03057', '35.70745'),
('1726', '411000', '410000', '许昌市', '中国,河南省,许昌市', '许昌', '中国,河南,许昌', '2', '0374', '461000', 'Xuchang', 'XC', 'X', '113.826063', '34.022956'),
('1727', '411002', '411000', '魏都区', '中国,河南省,许昌市,魏都区', '魏都', '中国,河南,许昌,魏都', '3', '0374', '461000', 'Weidu', 'WD', 'W', '113.8227', '34.02544'),
('1728', '411003', '411000', '建安区', '中国,河南省,许昌市,建安区', '建安', '中国,河南,许昌,建安', '3', '0374', '461100', 'Jian\'an', 'JA', 'J', '113.84707', '34.00406'),
('1729', '411024', '411000', '鄢陵县', '中国,河南省,许昌市,鄢陵县', '鄢陵', '中国,河南,许昌,鄢陵', '3', '0374', '461200', 'Yanling', 'YL', 'Y', '114.18795', '34.10317'),
('1730', '411025', '411000', '襄城县', '中国,河南省,许昌市,襄城县', '襄城', '中国,河南,许昌,襄城', '3', '0374', '452670', 'Xiangcheng', 'XC', 'X', '113.48196', '33.84928'),
('1731', '411081', '411000', '禹州市', '中国,河南省,许昌市,禹州市', '禹州', '中国,河南,许昌,禹州', '3', '0374', '461670', 'Yuzhou', 'YZ', 'Y', '113.48803', '34.14054'),
('1732', '411082', '411000', '长葛市', '中国,河南省,许昌市,长葛市', '长葛', '中国,河南,许昌,长葛', '3', '0374', '461500', 'Changge', 'CG', 'C', '113.77328', '34.21846'),
('1733', '411100', '410000', '漯河市', '中国,河南省,漯河市', '漯河', '中国,河南,漯河', '2', '0395', '462000', 'Luohe', 'LH', 'L', '114.026405', '33.575855'),
('1734', '411102', '411100', '源汇区', '中国,河南省,漯河市,源汇区', '源汇', '中国,河南,漯河,源汇', '3', '0395', '462000', 'Yuanhui', 'YH', 'Y', '114.00647', '33.55627'),
('1735', '411103', '411100', '郾城区', '中国,河南省,漯河市,郾城区', '郾城', '中国,河南,漯河,郾城', '3', '0395', '462300', 'Yancheng', 'YC', 'Y', '114.00694', '33.58723'),
('1736', '411104', '411100', '召陵区', '中国,河南省,漯河市,召陵区', '召陵', '中国,河南,漯河,召陵', '3', '0395', '462300', 'Zhaoling', 'ZL', 'Z', '114.09399', '33.58601'),
('1737', '411121', '411100', '舞阳县', '中国,河南省,漯河市,舞阳县', '舞阳', '中国,河南,漯河,舞阳', '3', '0395', '462400', 'Wuyang', 'WY', 'W', '113.59848', '33.43243'),
('1738', '411122', '411100', '临颍县', '中国,河南省,漯河市,临颍县', '临颍', '中国,河南,漯河,临颍', '3', '0395', '462600', 'Linying', 'LY', 'L', '113.93661', '33.81123'),
('1739', '411200', '410000', '三门峡市', '中国,河南省,三门峡市', '三门峡', '中国,河南,三门峡', '2', '0398', '472000', 'Sanmenxia', 'SMX', 'S', '111.194099', '34.777338'),
('1740', '411202', '411200', '湖滨区', '中国,河南省,三门峡市,湖滨区', '湖滨', '中国,河南,三门峡,湖滨', '3', '0398', '472000', 'Hubin', 'HB', 'H', '111.20006', '34.77872'),
('1741', '411203', '411200', '陕州区', '中国,河南省,三门峡市,陕州区', '陕州', '中国,河南,三门峡,陕州', '3', '0398', '472100', 'Shanzhou', 'SZ', 'S', '111.10333', '34.72052'),
('1742', '411221', '411200', '渑池县', '中国,河南省,三门峡市,渑池县', '渑池', '中国,河南,三门峡,渑池', '3', '0398', '472400', 'Mianchi', 'MC', 'M', '111.76184', '34.76725'),
('1743', '411224', '411200', '卢氏县', '中国,河南省,三门峡市,卢氏县', '卢氏', '中国,河南,三门峡,卢氏', '3', '0398', '472200', 'Lushi', 'LS', 'L', '111.04782', '34.05436'),
('1744', '411281', '411200', '义马市', '中国,河南省,三门峡市,义马市', '义马', '中国,河南,三门峡,义马', '3', '0398', '472300', 'Yima', 'YM', 'Y', '111.87445', '34.74721'),
('1745', '411282', '411200', '灵宝市', '中国,河南省,三门峡市,灵宝市', '灵宝', '中国,河南,三门峡,灵宝', '3', '0398', '472500', 'Lingbao', 'LB', 'L', '110.8945', '34.51682'),
('1746', '411300', '410000', '南阳市', '中国,河南省,南阳市', '南阳', '中国,河南,南阳', '2', '0377', '473000', 'Nanyang', 'NY', 'N', '112.540918', '32.999082'),
('1747', '411302', '411300', '宛城区', '中国,河南省,南阳市,宛城区', '宛城', '中国,河南,南阳,宛城', '3', '0377', '473000', 'Wancheng', 'WC', 'W', '112.53955', '33.00378'),
('1748', '411303', '411300', '卧龙区', '中国,河南省,南阳市,卧龙区', '卧龙', '中国,河南,南阳,卧龙', '3', '0377', '473000', 'Wolong', 'WL', 'W', '112.53479', '32.98615'),
('1749', '411321', '411300', '南召县', '中国,河南省,南阳市,南召县', '南召', '中国,河南,南阳,南召', '3', '0377', '474650', 'Nanzhao', 'NZ', 'N', '112.43194', '33.49098'),
('1750', '411322', '411300', '方城县', '中国,河南省,南阳市,方城县', '方城', '中国,河南,南阳,方城', '3', '0377', '473200', 'Fangcheng', 'FC', 'F', '113.01269', '33.25453'),
('1751', '411323', '411300', '西峡县', '中国,河南省,南阳市,西峡县', '西峡', '中国,河南,南阳,西峡', '3', '0377', '474550', 'Xixia', 'XX', 'X', '111.48187', '33.29772'),
('1752', '411324', '411300', '镇平县', '中国,河南省,南阳市,镇平县', '镇平', '中国,河南,南阳,镇平', '3', '0377', '474250', 'Zhenping', 'ZP', 'Z', '112.2398', '33.03629'),
('1753', '411325', '411300', '内乡县', '中国,河南省,南阳市,内乡县', '内乡', '中国,河南,南阳,内乡', '3', '0377', '474350', 'Neixiang', 'NX', 'N', '111.84957', '33.04671'),
('1754', '411326', '411300', '淅川县', '中国,河南省,南阳市,淅川县', '淅川', '中国,河南,南阳,淅川', '3', '0377', '474450', 'Xichuan', 'XC', 'X', '111.48663', '33.13708'),
('1755', '411327', '411300', '社旗县', '中国,河南省,南阳市,社旗县', '社旗', '中国,河南,南阳,社旗', '3', '0377', '473300', 'Sheqi', 'SQ', 'S', '112.94656', '33.05503'),
('1756', '411328', '411300', '唐河县', '中国,河南省,南阳市,唐河县', '唐河', '中国,河南,南阳,唐河', '3', '0377', '473400', 'Tanghe', 'TH', 'T', '112.83609', '32.69453'),
('1757', '411329', '411300', '新野县', '中国,河南省,南阳市,新野县', '新野', '中国,河南,南阳,新野', '3', '0377', '473500', 'Xinye', 'XY', 'X', '112.36151', '32.51698'),
('1758', '411330', '411300', '桐柏县', '中国,河南省,南阳市,桐柏县', '桐柏', '中国,河南,南阳,桐柏', '3', '0377', '474750', 'Tongbai', 'TB', 'T', '113.42886', '32.37917'),
('1759', '411381', '411300', '邓州市', '中国,河南省,南阳市,邓州市', '邓州', '中国,河南,南阳,邓州', '3', '0377', '474150', 'Dengzhou', 'DZ', 'D', '112.0896', '32.68577'),
('1760', '411400', '410000', '商丘市', '中国,河南省,商丘市', '商丘', '中国,河南,商丘', '2', '0370', '476000', 'Shangqiu', 'SQ', 'S', '115.650497', '34.437054'),
('1761', '411402', '411400', '梁园区', '中国,河南省,商丘市,梁园区', '梁园', '中国,河南,商丘,梁园', '3', '0370', '476000', 'Liangyuan', 'LY', 'L', '115.64487', '34.44341'),
('1762', '411403', '411400', '睢阳区', '中国,河南省,商丘市,睢阳区', '睢阳', '中国,河南,商丘,睢阳', '3', '0370', '476000', 'Suiyang', 'SY', 'S', '115.65338', '34.38804'),
('1763', '411421', '411400', '民权县', '中国,河南省,商丘市,民权县', '民权', '中国,河南,商丘,民权', '3', '0370', '476800', 'Minquan', 'MQ', 'M', '115.14621', '34.64931'),
('1764', '411422', '411400', '睢县', '中国,河南省,商丘市,睢县', '睢县', '中国,河南,商丘,睢县', '3', '0370', '476900', 'Suixian', 'SX', 'S', '115.07168', '34.44539'),
('1765', '411423', '411400', '宁陵县', '中国,河南省,商丘市,宁陵县', '宁陵', '中国,河南,商丘,宁陵', '3', '0370', '476700', 'Ningling', 'NL', 'N', '115.30511', '34.45463'),
('1766', '411424', '411400', '柘城县', '中国,河南省,商丘市,柘城县', '柘城', '中国,河南,商丘,柘城', '3', '0370', '476200', 'Zhecheng', 'ZC', 'Z', '115.30538', '34.0911'),
('1767', '411425', '411400', '虞城县', '中国,河南省,商丘市,虞城县', '虞城', '中国,河南,商丘,虞城', '3', '0370', '476300', 'Yucheng', 'YC', 'Y', '115.86337', '34.40189'),
('1768', '411426', '411400', '夏邑县', '中国,河南省,商丘市,夏邑县', '夏邑', '中国,河南,商丘,夏邑', '3', '0370', '476400', 'Xiayi', 'XY', 'X', '116.13348', '34.23242'),
('1769', '411481', '411400', '永城市', '中国,河南省,商丘市,永城市', '永城', '中国,河南,商丘,永城', '3', '0370', '476600', 'Yongcheng', 'YC', 'Y', '116.44943', '33.92911'),
('1770', '411500', '410000', '信阳市', '中国,河南省,信阳市', '信阳', '中国,河南,信阳', '2', '0376', '464000', 'Xinyang', 'XY', 'X', '114.075031', '32.123274'),
('1771', '411502', '411500', '浉河区', '中国,河南省,信阳市,浉河区', '浉河', '中国,河南,信阳,浉河', '3', '0376', '464000', 'Shihe', 'SH', 'S', '114.05871', '32.1168'),
('1772', '411503', '411500', '平桥区', '中国,河南省,信阳市,平桥区', '平桥', '中国,河南,信阳,平桥', '3', '0376', '464000', 'Pingqiao', 'PQ', 'P', '114.12435', '32.10095'),
('1773', '411521', '411500', '罗山县', '中国,河南省,信阳市,罗山县', '罗山', '中国,河南,信阳,罗山', '3', '0376', '464200', 'Luoshan', 'LS', 'L', '114.5314', '32.20277'),
('1774', '411522', '411500', '光山县', '中国,河南省,信阳市,光山县', '光山', '中国,河南,信阳,光山', '3', '0376', '465450', 'Guangshan', 'GS', 'G', '114.91873', '32.00992'),
('1775', '411523', '411500', '新县', '中国,河南省,信阳市,新县', '新县', '中国,河南,信阳,新县', '3', '0376', '465550', 'Xinxian', 'XX', 'X', '114.87924', '31.64386'),
('1776', '411524', '411500', '商城县', '中国,河南省,信阳市,商城县', '商城', '中国,河南,信阳,商城', '3', '0376', '465350', 'Shangcheng', 'SC', 'S', '115.40856', '31.79986'),
('1777', '411525', '411500', '固始县', '中国,河南省,信阳市,固始县', '固始', '中国,河南,信阳,固始', '3', '0376', '465200', 'Gushi', 'GS', 'G', '115.68298', '32.18011'),
('1778', '411526', '411500', '潢川县', '中国,河南省,信阳市,潢川县', '潢川', '中国,河南,信阳,潢川', '3', '0376', '465150', 'Huangchuan', 'HC', 'H', '115.04696', '32.13763'),
('1779', '411527', '411500', '淮滨县', '中国,河南省,信阳市,淮滨县', '淮滨', '中国,河南,信阳,淮滨', '3', '0376', '464400', 'Huaibin', 'HB', 'H', '115.4205', '32.46614'),
('1780', '411528', '411500', '息县', '中国,河南省,信阳市,息县', '息县', '中国,河南,信阳,息县', '3', '0376', '464300', 'Xixian', 'XX', 'X', '114.7402', '32.34279'),
('1781', '411600', '410000', '周口市', '中国,河南省,周口市', '周口', '中国,河南,周口', '2', '0394', '466000', 'Zhoukou', 'ZK', 'Z', '114.649653', '33.620357'),
('1782', '411602', '411600', '川汇区', '中国,河南省,周口市,川汇区', '川汇', '中国,河南,周口,川汇', '3', '0394', '466000', 'Chuanhui', 'CH', 'C', '114.64202', '33.6256'),
('1783', '411621', '411600', '扶沟县', '中国,河南省,周口市,扶沟县', '扶沟', '中国,河南,周口,扶沟', '3', '0394', '461300', 'Fugou', 'FG', 'F', '114.39477', '34.05999'),
('1784', '411622', '411600', '西华县', '中国,河南省,周口市,西华县', '西华', '中国,河南,周口,西华', '3', '0394', '466600', 'Xihua', 'XH', 'X', '114.52279', '33.78548'),
('1785', '411623', '411600', '商水县', '中国,河南省,周口市,商水县', '商水', '中国,河南,周口,商水', '3', '0394', '466100', 'Shangshui', 'SS', 'S', '114.60604', '33.53912'),
('1786', '411624', '411600', '沈丘县', '中国,河南省,周口市,沈丘县', '沈丘', '中国,河南,周口,沈丘', '3', '0394', '466300', 'Shenqiu', 'SQ', 'S', '115.09851', '33.40936'),
('1787', '411625', '411600', '郸城县', '中国,河南省,周口市,郸城县', '郸城', '中国,河南,周口,郸城', '3', '0394', '477150', 'Dancheng', 'DC', 'D', '115.17715', '33.64485'),
('1788', '411626', '411600', '淮阳县', '中国,河南省,周口市,淮阳县', '淮阳', '中国,河南,周口,淮阳', '3', '0394', '466700', 'Huaiyang', 'HY', 'H', '114.88848', '33.73211'),
('1789', '411627', '411600', '太康县', '中国,河南省,周口市,太康县', '太康', '中国,河南,周口,太康', '3', '0394', '475400', 'Taikang', 'TK', 'T', '114.83773', '34.06376'),
('1790', '411628', '411600', '鹿邑县', '中国,河南省,周口市,鹿邑县', '鹿邑', '中国,河南,周口,鹿邑', '3', '0394', '477200', 'Luyi', 'LY', 'L', '115.48553', '33.85931'),
('1791', '411681', '411600', '项城市', '中国,河南省,周口市,项城市', '项城', '中国,河南,周口,项城', '3', '0394', '466200', 'Xiangcheng', 'XC', 'X', '114.87558', '33.4672'),
('1792', '411682', '411600', '东新区', '中国,河南省,周口市,东新区', '东新区', '中国,河南,周口,东新区', '3', '0394', '466000', 'Dongxinqu', 'DXQ', 'D', '114.707844', '33.65107'),
('1793', '411683', '411600', '经济开发区', '中国,河南省,周口市,经济开发区', '经济开发区', '中国,河南,周口,经济开发区', '3', '0394', '466000', 'Jingjikaifaqu', 'JJQ', 'J', '114.677582', '33.58664'),
('1794', '411700', '410000', '驻马店市', '中国,河南省,驻马店市', '驻马店', '中国,河南,驻马店', '2', '0396', '463000', 'Zhumadian', 'ZMD', 'Z', '114.024736', '32.980169'),
('1795', '411702', '411700', '驿城区', '中国,河南省,驻马店市,驿城区', '驿城', '中国,河南,驻马店,驿城', '3', '0396', '463000', 'Yicheng', 'YC', 'Y', '113.99377', '32.97316'),
('1796', '411721', '411700', '西平县', '中国,河南省,驻马店市,西平县', '西平', '中国,河南,驻马店,西平', '3', '0396', '463900', 'Xiping', 'XP', 'X', '114.02322', '33.3845'),
('1797', '411722', '411700', '上蔡县', '中国,河南省,驻马店市,上蔡县', '上蔡', '中国,河南,驻马店,上蔡', '3', '0396', '463800', 'Shangcai', 'SC', 'S', '114.26825', '33.26825'),
('1798', '411723', '411700', '平舆县', '中国,河南省,驻马店市,平舆县', '平舆', '中国,河南,驻马店,平舆', '3', '0396', '463400', 'Pingyu', 'PY', 'P', '114.63552', '32.95727'),
('1799', '411724', '411700', '正阳县', '中国,河南省,驻马店市,正阳县', '正阳', '中国,河南,驻马店,正阳', '3', '0396', '463600', 'Zhengyang', 'ZY', 'Z', '114.38952', '32.6039'),
('1800', '411725', '411700', '确山县', '中国,河南省,驻马店市,确山县', '确山', '中国,河南,驻马店,确山', '3', '0396', '463200', 'Queshan', 'QS', 'Q', '114.02917', '32.80281'),
('1801', '411726', '411700', '泌阳县', '中国,河南省,驻马店市,泌阳县', '泌阳', '中国,河南,驻马店,泌阳', '3', '0396', '463700', 'Biyang', 'BY', 'B', '113.32681', '32.71781'),
('1802', '411727', '411700', '汝南县', '中国,河南省,驻马店市,汝南县', '汝南', '中国,河南,驻马店,汝南', '3', '0396', '463300', 'Runan', 'RN', 'R', '114.36138', '33.00461'),
('1803', '411728', '411700', '遂平县', '中国,河南省,驻马店市,遂平县', '遂平', '中国,河南,驻马店,遂平', '3', '0396', '463100', 'Suiping', 'SP', 'S', '114.01297', '33.14571'),
('1804', '411729', '411700', '新蔡县', '中国,河南省,驻马店市,新蔡县', '新蔡', '中国,河南,驻马店,新蔡', '3', '0396', '463500', 'Xincai', 'XC', 'X', '114.98199', '32.7502'),
('1805', '419001', '410000', '济源市', '中国,河南省,济源市', '济源', '中国,河南,济源', '2', '0391', '454650', 'Jiyuan', 'JY', 'J', '112.590047', '35.090378'),
('1806', '419011', '419001', '沁园街道', '中国,河南省,济源市,沁园街道', '沁园街道', '中国,河南,济源,沁园街道', '3', '0391', '459000', 'Qinyuan', 'QY', 'Q', '112.57206', '35.08187'),
('1807', '419012', '419001', '济水街道', '中国,河南省,济源市,济水街道', '济水街道', '中国,河南,济源,济水街道', '3', '0391', '459001', 'Jishui', 'JS', 'J', '112.588928', '35.091632'),
('1808', '419013', '419001', '北海街道', '中国,河南省,济源市,北海街道', '北海街道', '中国,河南,济源,北海街道', '3', '0391', '459003', 'Beihai', 'BH', 'B', '112.593234', '35.097409'),
('1809', '419014', '419001', '天坛街道', '中国,河南省,济源市,天坛街道', '天坛街道', '中国,河南,济源,天坛街道', '3', '0391', '459004', 'Tiantan', 'TT', 'T', '112.565589', '35.094593'),
('1810', '419015', '419001', '玉泉街道', '中国,河南省,济源市,玉泉街道', '玉泉街道', '中国,河南,济源,玉泉街道', '3', '0391', '459002', 'Yuquan', 'YQ', 'Y', '112.615469', '35.092599'),
('1811', '419016', '419001', '克井镇', '中国,河南省,济源市,克井镇', '克井镇', '中国,河南,济源,克井镇', '3', '0391', '459010', 'Kejing', 'KJ', 'K', '112.543031', '35.1603'),
('1812', '419017', '419001', '五龙口镇', '中国,河南省,济源市,五龙口镇', '五龙口镇', '中国,河南,济源,五龙口镇', '3', '0391', '459011', 'Wulongkao', 'WLK', 'W', '112.68937', '35.135041'),
('1813', '419018', '419001', '梨林镇', '中国,河南省,济源市,梨林镇', '梨林镇', '中国,河南,济源,梨林镇', '3', '0391', '459018', 'Lilin', 'LL', 'L', '112.713501', '35.07768'),
('1814', '419019', '419001', '轵城镇', '中国,河南省,济源市,轵城镇', '轵城镇', '中国,河南,济源,轵城镇', '3', '0391', '459005', 'ZhiCheng', 'ZC', 'Z', '112.60099', '35.046131'),
('1815', '419020', '419001', '承留镇', '中国,河南省,济源市,承留镇', '承留镇', '中国,河南,济源,承留镇', '3', '0391', '459007', 'ChengLiu', 'CL', 'C', '112.499271', '35.077716'),
('1816', '419021', '419001', '坡头镇', '中国,河南省,济源市,坡头镇', '坡头镇', '中国,河南,济源,坡头镇', '3', '0391', '459016', 'Potou', 'PT', 'P', '112.524757', '34.928431'),
('1817', '419022', '419001', '大峪镇', '中国,河南省,济源市,大峪镇', '大峪镇', '中国,河南,济源,大峪镇', '3', '0391', '459017', 'Dayu', 'DY', 'D', '112.396154', '34.937623'),
('1818', '419023', '419001', '邵原镇', '中国,河南省,济源市,邵原镇', '邵原镇', '中国,河南,济源,邵原镇', '3', '0391', '459014', 'ShaoYuan', 'SY', 'S', '112.133535', '35.160964'),
('1819', '419024', '419001', '思礼镇', '中国,河南省,济源市,思礼镇', '思礼镇', '中国,河南,济源,思礼镇', '3', '0391', '459006', 'Sili', 'SL', 'S', '112.507286', '35.101133'),
('1820', '419025', '419001', '王屋镇', '中国,河南省,济源市,王屋镇', '王屋镇', '中国,河南,济源,王屋镇', '3', '0391', '459013', 'WangWu', 'WW', 'W', '112.272475', '35.113912'),
('1821', '419026', '419001', '下冶镇', '中国,河南省,济源市,下冶镇', '下冶镇', '中国,河南,济源,下冶镇', '3', '0391', '459015', 'XiaYe', 'XY', 'X', '112.20023', '35.032836'),
('1822', '420000', '100000', '湖北省', '中国,湖北省', '湖北', '中国,湖北', '1', '', '', 'Hubei', 'HB', 'H', '114.298572', '30.584355'),
('1823', '420100', '420000', '武汉市', '中国,湖北省,武汉市', '武汉', '中国,湖北,武汉', '2', '027', '430000', 'Wuhan', 'WH', 'W', '114.298572', '30.584355'),
('1824', '420102', '420100', '江岸区', '中国,湖北省,武汉市,江岸区', '江岸', '中国,湖北,武汉,江岸', '3', '027', '430014', 'Jiang\'an', 'JA', 'J', '114.30943', '30.59982'),
('1825', '420103', '420100', '江汉区', '中国,湖北省,武汉市,江汉区', '江汉', '中国,湖北,武汉,江汉', '3', '027', '430000', 'Jianghan', 'JH', 'J', '114.27093', '30.60146'),
('1826', '420104', '420100', '硚口区', '中国,湖北省,武汉市,硚口区', '硚口', '中国,湖北,武汉,硚口', '3', '027', '430000', 'Qiaokou', 'QK', 'Q', '114.26422', '30.56945'),
('1827', '420105', '420100', '汉阳区', '中国,湖北省,武汉市,汉阳区', '汉阳', '中国,湖北,武汉,汉阳', '3', '027', '430050', 'Hanyang', 'HY', 'H', '114.27478', '30.54915'),
('1828', '420106', '420100', '武昌区', '中国,湖北省,武汉市,武昌区', '武昌', '中国,湖北,武汉,武昌', '3', '027', '430000', 'Wuchang', 'WC', 'W', '114.31589', '30.55389'),
('1829', '420107', '420100', '青山区', '中国,湖北省,武汉市,青山区', '青山', '中国,湖北,武汉,青山', '3', '027', '430080', 'Qingshan', 'QS', 'Q', '114.39117', '30.63427'),
('1830', '420111', '420100', '洪山区', '中国,湖北省,武汉市,洪山区', '洪山', '中国,湖北,武汉,洪山', '3', '027', '430070', 'Hongshan', 'HS', 'H', '114.34375', '30.49989'),
('1831', '420112', '420100', '东西湖区', '中国,湖北省,武汉市,东西湖区', '东西湖', '中国,湖北,武汉,东西湖', '3', '027', '430040', 'Dongxihu', 'DXH', 'D', '114.13708', '30.61989'),
('1832', '420113', '420100', '汉南区', '中国,湖北省,武汉市,汉南区', '汉南', '中国,湖北,武汉,汉南', '3', '027', '430090', 'Hannan', 'HN', 'H', '114.08462', '30.30879'),
('1833', '420114', '420100', '蔡甸区', '中国,湖北省,武汉市,蔡甸区', '蔡甸', '中国,湖北,武汉,蔡甸', '3', '027', '430100', 'Caidian', 'CD', 'C', '114.02929', '30.58197'),
('1834', '420115', '420100', '江夏区', '中国,湖北省,武汉市,江夏区', '江夏', '中国,湖北,武汉,江夏', '3', '027', '430200', 'Jiangxia', 'JX', 'J', '114.31301', '30.34653'),
('1835', '420116', '420100', '黄陂区', '中国,湖北省,武汉市,黄陂区', '黄陂', '中国,湖北,武汉,黄陂', '3', '027', '432200', 'Huangpi', 'HP', 'H', '114.37512', '30.88151'),
('1836', '420117', '420100', '新洲区', '中国,湖北省,武汉市,新洲区', '新洲', '中国,湖北,武汉,新洲', '3', '027', '431400', 'Xinzhou', 'XZ', 'X', '114.80136', '30.84145'),
('1837', '420118', '420100', '经济技术开发区', '中国,湖北省,武汉市,经济技术开发区', '经济技术开发区', '中国,湖北,武汉,经济技术开发区', '3', '027', '430056', 'Jingjikaifaqu', 'JJ', 'J', '114.159156', '30.488929'),
('1838', '420200', '420000', '黄石市', '中国,湖北省,黄石市', '黄石', '中国,湖北,黄石', '2', '0714', '435000', 'Huangshi', 'HS', 'H', '115.077048', '30.220074'),
('1839', '420202', '420200', '黄石港区', '中国,湖北省,黄石市,黄石港区', '黄石港', '中国,湖北,黄石,黄石港', '3', '0714', '435000', 'Huangshigang', 'HSG', 'H', '115.06604', '30.22279'),
('1840', '420203', '420200', '西塞山区', '中国,湖北省,黄石市,西塞山区', '西塞山', '中国,湖北,黄石,西塞山', '3', '0714', '435001', 'Xisaishan', 'XSS', 'X', '115.11016', '30.20487'),
('1841', '420204', '420200', '下陆区', '中国,湖北省,黄石市,下陆区', '下陆', '中国,湖北,黄石,下陆', '3', '0714', '435000', 'Xialu', 'XL', 'X', '114.96112', '30.17368'),
('1842', '420205', '420200', '铁山区', '中国,湖北省,黄石市,铁山区', '铁山', '中国,湖北,黄石,铁山', '3', '0714', '435000', 'Tieshan', 'TS', 'T', '114.90109', '30.20678'),
('1843', '420222', '420200', '阳新县', '中国,湖北省,黄石市,阳新县', '阳新', '中国,湖北,黄石,阳新', '3', '0714', '435200', 'Yangxin', 'YX', 'Y', '115.21527', '29.83038'),
('1844', '420281', '420200', '大冶市', '中国,湖北省,黄石市,大冶市', '大冶', '中国,湖北,黄石,大冶', '3', '0714', '435100', 'Daye', 'DY', 'D', '114.97174', '30.09438'),
('1845', '420282', '420200', '经济开发区', '中国,湖北省,黄石市,经济开发区', '经济开发区', '中国,湖北,黄石,经济开发区', '3', '0714', '435003', 'Jingjikaifaqu', 'JJ', 'J', '115.029566', '30.135938'),
('1846', '420300', '420000', '十堰市', '中国,湖北省,十堰市', '十堰', '中国,湖北,十堰', '2', '0719', '442000', 'Shiyan', 'SY', 'S', '110.785239', '32.647017'),
('1847', '420302', '420300', '茅箭区', '中国,湖北省,十堰市,茅箭区', '茅箭', '中国,湖北,十堰,茅箭', '3', '0719', '442000', 'Maojian', 'MJ', 'M', '110.81341', '32.59153'),
('1848', '420303', '420300', '张湾区', '中国,湖北省,十堰市,张湾区', '张湾', '中国,湖北,十堰,张湾', '3', '0719', '442000', 'Zhangwan', 'ZW', 'Z', '110.77067', '32.65195'),
('1849', '420304', '420300', '郧阳区', '中国,湖北省,十堰市,郧阳区', '郧阳', '中国,湖北,十堰,郧阳', '3', '0719', '442500', 'Yunyang', 'YY', 'Y', '110.81854', '32.83593'),
('1850', '420322', '420300', '郧西县', '中国,湖北省,十堰市,郧西县', '郧西', '中国,湖北,十堰,郧西', '3', '0719', '442600', 'Yunxi', 'YX', 'Y', '110.42556', '32.99349'),
('1851', '420323', '420300', '竹山县', '中国,湖北省,十堰市,竹山县', '竹山', '中国,湖北,十堰,竹山', '3', '0719', '442200', 'Zhushan', 'ZS', 'Z', '110.23071', '32.22536'),
('1852', '420324', '420300', '竹溪县', '中国,湖北省,十堰市,竹溪县', '竹溪', '中国,湖北,十堰,竹溪', '3', '0719', '442300', 'Zhuxi', 'ZX', 'Z', '109.71798', '32.31901'),
('1853', '420325', '420300', '房县', '中国,湖北省,十堰市,房县', '房县', '中国,湖北,十堰,房县', '3', '0719', '442100', 'Fangxian', 'FX', 'F', '110.74386', '32.05794'),
('1854', '420381', '420300', '丹江口市', '中国,湖北省,十堰市,丹江口市', '丹江口', '中国,湖北,十堰,丹江口', '3', '0719', '442700', 'Danjiangkou', 'DJK', 'D', '111.51525', '32.54085'),
('1855', '420500', '420000', '宜昌市', '中国,湖北省,宜昌市', '宜昌', '中国,湖北,宜昌', '2', '0717', '443000', 'Yichang', 'YC', 'Y', '111.290843', '30.702636'),
('1856', '420502', '420500', '西陵区', '中国,湖北省,宜昌市,西陵区', '西陵', '中国,湖北,宜昌,西陵', '3', '0717', '443000', 'Xiling', 'XL', 'X', '111.28573', '30.71077'),
('1857', '420503', '420500', '伍家岗区', '中国,湖北省,宜昌市,伍家岗区', '伍家岗', '中国,湖北,宜昌,伍家岗', '3', '0717', '443000', 'Wujiagang', 'WJG', 'W', '111.3609', '30.64434'),
('1858', '420504', '420500', '点军区', '中国,湖北省,宜昌市,点军区', '点军', '中国,湖北,宜昌,点军', '3', '0717', '443000', 'Dianjun', 'DJ', 'D', '111.26828', '30.6934'),
('1859', '420505', '420500', '猇亭区', '中国,湖北省,宜昌市,猇亭区', '猇亭', '中国,湖北,宜昌,猇亭', '3', '0717', '443000', 'Xiaoting', 'XT', 'X', '111.44079', '30.52663'),
('1860', '420506', '420500', '夷陵区', '中国,湖北省,宜昌市,夷陵区', '夷陵', '中国,湖北,宜昌,夷陵', '3', '0717', '443100', 'Yiling', 'YL', 'Y', '111.3262', '30.76881'),
('1861', '420525', '420500', '远安县', '中国,湖北省,宜昌市,远安县', '远安', '中国,湖北,宜昌,远安', '3', '0717', '444200', 'Yuan\'an', 'YA', 'Y', '111.6416', '31.05989'),
('1862', '420526', '420500', '兴山县', '中国,湖北省,宜昌市,兴山县', '兴山', '中国,湖北,宜昌,兴山', '3', '0717', '443700', 'Xingshan', 'XS', 'X', '110.74951', '31.34686'),
('1863', '420527', '420500', '秭归县', '中国,湖北省,宜昌市,秭归县', '秭归', '中国,湖北,宜昌,秭归', '3', '0717', '443600', 'Zigui', 'ZG', 'Z', '110.98156', '30.82702'),
('1864', '420528', '420500', '长阳土家族自治县', '中国,湖北省,宜昌市,长阳土家族自治县', '长阳', '中国,湖北,宜昌,长阳', '3', '0717', '443500', 'Changyang', 'CY', 'C', '111.20105', '30.47052'),
('1865', '420529', '420500', '五峰土家族自治县', '中国,湖北省,宜昌市,五峰土家族自治县', '五峰', '中国,湖北,宜昌,五峰', '3', '0717', '443400', 'Wufeng', 'WF', 'W', '110.6748', '30.19856'),
('1866', '420581', '420500', '宜都市', '中国,湖北省,宜昌市,宜都市', '宜都', '中国,湖北,宜昌,宜都', '3', '0717', '443000', 'Yidu', 'YD', 'Y', '111.45025', '30.37807'),
('1867', '420582', '420500', '当阳市', '中国,湖北省,宜昌市,当阳市', '当阳', '中国,湖北,宜昌,当阳', '3', '0717', '444100', 'Dangyang', 'DY', 'D', '111.78912', '30.8208'),
('1868', '420583', '420500', '枝江市', '中国,湖北省,宜昌市,枝江市', '枝江', '中国,湖北,宜昌,枝江', '3', '0717', '443200', 'Zhijiang', 'ZJ', 'Z', '111.76855', '30.42612'),
('1869', '420584', '420500', '宜昌新区', '中国,湖北省,宜昌市,宜昌新区', '宜昌新区', '中国,湖北,宜昌,宜昌新区', '3', '0717', '443000', 'Yichangxinqu', 'YCXQ', 'Y', '111.406174', '30.711733'),
('1870', '420600', '420000', '襄阳市', '中国,湖北省,襄阳市', '襄阳', '中国,湖北,襄阳', '2', '0710', '441000', 'Xiangyang', 'XY', 'X', '112.144146', '32.042426'),
('1871', '420602', '420600', '襄城区', '中国,湖北省,襄阳市,襄城区', '襄城', '中国,湖北,襄阳,襄城', '3', '0710', '441000', 'Xiangcheng', 'XC', 'X', '112.13372', '32.01017'),
('1872', '420606', '420600', '樊城区', '中国,湖北省,襄阳市,樊城区', '樊城', '中国,湖北,襄阳,樊城', '3', '0710', '441000', 'Fancheng', 'FC', 'F', '112.13546', '32.04482'),
('1873', '420607', '420600', '襄州区', '中国,湖北省,襄阳市,襄州区', '襄州', '中国,湖北,襄阳,襄州', '3', '0710', '441100', 'Xiangzhou', 'XZ', 'X', '112.150327', '32.015088'),
('1874', '420624', '420600', '南漳县', '中国,湖北省,襄阳市,南漳县', '南漳', '中国,湖北,襄阳,南漳', '3', '0710', '441500', 'Nanzhang', 'NZ', 'N', '111.84603', '31.77653'),
('1875', '420625', '420600', '谷城县', '中国,湖北省,襄阳市,谷城县', '谷城', '中国,湖北,襄阳,谷城', '3', '0710', '441700', 'Gucheng', 'GC', 'G', '111.65267', '32.26377'),
('1876', '420626', '420600', '保康县', '中国,湖北省,襄阳市,保康县', '保康', '中国,湖北,襄阳,保康', '3', '0710', '441600', 'Baokang', 'BK', 'B', '111.26138', '31.87874'),
('1877', '420682', '420600', '老河口市', '中国,湖北省,襄阳市,老河口市', '老河口', '中国,湖北,襄阳,老河口', '3', '0710', '441800', 'Laohekou', 'LHK', 'L', '111.67117', '32.38476'),
('1878', '420683', '420600', '枣阳市', '中国,湖北省,襄阳市,枣阳市', '枣阳', '中国,湖北,襄阳,枣阳', '3', '0710', '441200', 'Zaoyang', 'ZY', 'Z', '112.77444', '32.13142'),
('1879', '420684', '420600', '宜城市', '中国,湖北省,襄阳市,宜城市', '宜城', '中国,湖北,襄阳,宜城', '3', '0710', '441400', 'Yicheng', 'YC', 'Y', '112.25772', '31.71972'),
('1880', '420685', '420600', '高新区', '中国,湖北省,襄阳市,高新区', '高新区', '中国,湖北,襄阳,高新区', '3', '0710', '441000', 'Gaoxinqu', 'GXQ', 'G', '112.121736', '32.080276'),
('1881', '420686', '420600', '经济开发区', '中国,湖北省,襄阳市,经济开发区', '经济开发区', '中国,湖北,襄阳,经济开发区', '3', '0710', '441000', 'Jingjikaifaqu', 'JJ', 'J', '112.260933', '32.132094'),
('1882', '420700', '420000', '鄂州市', '中国,湖北省,鄂州市', '鄂州', '中国,湖北,鄂州', '2', '0711', '436000', 'Ezhou', 'EZ', 'E', '114.890593', '30.396536'),
('1883', '420702', '420700', '梁子湖区', '中国,湖北省,鄂州市,梁子湖区', '梁子湖', '中国,湖北,鄂州,梁子湖', '3', '0711', '436000', 'Liangzihu', 'LZH', 'L', '114.68463', '30.10003'),
('1884', '420703', '420700', '华容区', '中国,湖北省,鄂州市,华容区', '华容', '中国,湖北,鄂州,华容', '3', '0711', '436000', 'Huarong', 'HR', 'H', '114.73568', '30.53328'),
('1885', '420704', '420700', '鄂城区', '中国,湖北省,鄂州市,鄂城区', '鄂城', '中国,湖北,鄂州,鄂城', '3', '0711', '436000', 'Echeng', 'EC', 'E', '114.89158', '30.40024'),
('1886', '420800', '420000', '荆门市', '中国,湖北省,荆门市', '荆门', '中国,湖北,荆门', '2', '0724', '448000', 'Jingmen', 'JM', 'J', '112.204251', '31.03542'),
('1887', '420802', '420800', '东宝区', '中国,湖北省,荆门市,东宝区', '东宝', '中国,湖北,荆门,东宝', '3', '0724', '448000', 'Dongbao', 'DB', 'D', '112.20147', '31.05192'),
('1888', '420804', '420800', '掇刀区', '中国,湖北省,荆门市,掇刀区', '掇刀', '中国,湖北,荆门,掇刀', '3', '0724', '448000', 'Duodao', 'DD', 'D', '112.208', '30.97316'),
('1889', '420821', '420800', '京山县', '中国,湖北省,荆门市,京山县', '京山', '中国,湖北,荆门,京山', '3', '0724', '431800', 'Jingshan', 'JS', 'J', '113.11074', '31.0224'),
('1890', '420822', '420800', '沙洋县', '中国,湖北省,荆门市,沙洋县', '沙洋', '中国,湖北,荆门,沙洋', '3', '0724', '448200', 'Shayang', 'SY', 'S', '112.58853', '30.70916'),
('1891', '420881', '420800', '钟祥市', '中国,湖北省,荆门市,钟祥市', '钟祥', '中国,湖北,荆门,钟祥', '3', '0724', '431900', 'Zhongxiang', 'ZX', 'Z', '112.58932', '31.1678'),
('1892', '420900', '420000', '孝感市', '中国,湖北省,孝感市', '孝感', '中国,湖北,孝感', '2', '0712', '432000', 'Xiaogan', 'XG', 'X', '113.926655', '30.926423'),
('1893', '420902', '420900', '孝南区', '中国,湖北省,孝感市,孝南区', '孝南', '中国,湖北,孝感,孝南', '3', '0712', '432100', 'Xiaonan', 'XN', 'X', '113.91111', '30.9168'),
('1894', '420921', '420900', '孝昌县', '中国,湖北省,孝感市,孝昌县', '孝昌', '中国,湖北,孝感,孝昌', '3', '0712', '432900', 'Xiaochang', 'XC', 'X', '113.99795', '31.25799'),
('1895', '420922', '420900', '大悟县', '中国,湖北省,孝感市,大悟县', '大悟', '中国,湖北,孝感,大悟', '3', '0712', '432800', 'Dawu', 'DW', 'D', '114.12564', '31.56176'),
('1896', '420923', '420900', '云梦县', '中国,湖北省,孝感市,云梦县', '云梦', '中国,湖北,孝感,云梦', '3', '0712', '432500', 'Yunmeng', 'YM', 'Y', '113.75289', '31.02093'),
('1897', '420981', '420900', '应城市', '中国,湖北省,孝感市,应城市', '应城', '中国,湖北,孝感,应城', '3', '0712', '432400', 'Yingcheng', 'YC', 'Y', '113.57287', '30.92834'),
('1898', '420982', '420900', '安陆市', '中国,湖北省,孝感市,安陆市', '安陆', '中国,湖北,孝感,安陆', '3', '0712', '432600', 'Anlu', 'AL', 'A', '113.68557', '31.25693'),
('1899', '420984', '420900', '汉川市', '中国,湖北省,孝感市,汉川市', '汉川', '中国,湖北,孝感,汉川', '3', '0712', '432300', 'Hanchuan', 'HC', 'H', '113.83898', '30.66117'),
('1900', '421000', '420000', '荆州市', '中国,湖北省,荆州市', '荆州', '中国,湖北,荆州', '2', '0716', '434000', 'Jingzhou', 'JZ', 'J', '112.23813', '30.326857'),
('1901', '421002', '421000', '沙市区', '中国,湖北省,荆州市,沙市区', '沙市', '中国,湖北,荆州,沙市', '3', '0716', '434000', 'Shashi', 'SS', 'S', '112.25543', '30.31107'),
('1902', '421003', '421000', '荆州区', '中国,湖北省,荆州市,荆州区', '荆州', '中国,湖北,荆州,荆州', '3', '0716', '434020', 'Jingzhou', 'JZ', 'J', '112.19006', '30.35264'),
('1903', '421022', '421000', '公安县', '中国,湖北省,荆州市,公安县', '公安', '中国,湖北,荆州,公安', '3', '0716', '434300', 'Gong\'an', 'GA', 'G', '112.23242', '30.05902'),
('1904', '421023', '421000', '监利县', '中国,湖北省,荆州市,监利县', '监利', '中国,湖北,荆州,监利', '3', '0716', '433300', 'Jianli', 'JL', 'J', '112.89462', '29.81494'),
('1905', '421024', '421000', '江陵县', '中国,湖北省,荆州市,江陵县', '江陵', '中国,湖北,荆州,江陵', '3', '0716', '434100', 'Jiangling', 'JL', 'J', '112.42468', '30.04174'),
('1906', '421081', '421000', '石首市', '中国,湖北省,荆州市,石首市', '石首', '中国,湖北,荆州,石首', '3', '0716', '434400', 'Shishou', 'SS', 'S', '112.42636', '29.72127'),
('1907', '421083', '421000', '洪湖市', '中国,湖北省,荆州市,洪湖市', '洪湖', '中国,湖北,荆州,洪湖', '3', '0716', '433200', 'Honghu', 'HH', 'H', '113.47598', '29.827'),
('1908', '421087', '421000', '松滋市', '中国,湖北省,荆州市,松滋市', '松滋', '中国,湖北,荆州,松滋', '3', '0716', '434200', 'Songzi', 'SZ', 'S', '111.76739', '30.16965'),
('1909', '421100', '420000', '黄冈市', '中国,湖北省,黄冈市', '黄冈', '中国,湖北,黄冈', '2', '0713', '438000', 'Huanggang', 'HG', 'H', '114.879365', '30.447711'),
('1910', '421102', '421100', '黄州区', '中国,湖北省,黄冈市,黄州区', '黄州', '中国,湖北,黄冈,黄州', '3', '0713', '438000', 'Huangzhou', 'HZ', 'H', '114.88008', '30.43436'),
('1911', '421121', '421100', '团风县', '中国,湖北省,黄冈市,团风县', '团风', '中国,湖北,黄冈,团风', '3', '0713', '438000', 'Tuanfeng', 'TF', 'T', '114.87228', '30.64359'),
('1912', '421122', '421100', '红安县', '中国,湖北省,黄冈市,红安县', '红安', '中国,湖北,黄冈,红安', '3', '0713', '438400', 'Hong\'an', 'HA', 'H', '114.6224', '31.28668'),
('1913', '421123', '421100', '罗田县', '中国,湖北省,黄冈市,罗田县', '罗田', '中国,湖北,黄冈,罗田', '3', '0713', '438600', 'Luotian', 'LT', 'L', '115.39971', '30.78255'),
('1914', '421124', '421100', '英山县', '中国,湖北省,黄冈市,英山县', '英山', '中国,湖北,黄冈,英山', '3', '0713', '438700', 'Yingshan', 'YS', 'Y', '115.68142', '30.73516'),
('1915', '421125', '421100', '浠水县', '中国,湖北省,黄冈市,浠水县', '浠水', '中国,湖北,黄冈,浠水', '3', '0713', '438200', 'Xishui', 'XS', 'X', '115.26913', '30.45265'),
('1916', '421126', '421100', '蕲春县', '中国,湖北省,黄冈市,蕲春县', '蕲春', '中国,湖北,黄冈,蕲春', '3', '0713', '435300', 'Qichun', 'QC', 'Q', '115.43615', '30.22613'),
('1917', '421127', '421100', '黄梅县', '中国,湖北省,黄冈市,黄梅县', '黄梅', '中国,湖北,黄冈,黄梅', '3', '0713', '435500', 'Huangmei', 'HM', 'H', '115.94427', '30.07033'),
('1918', '421181', '421100', '麻城市', '中国,湖北省,黄冈市,麻城市', '麻城', '中国,湖北,黄冈,麻城', '3', '0713', '438300', 'Macheng', 'MC', 'M', '115.00988', '31.17228'),
('1919', '421182', '421100', '武穴市', '中国,湖北省,黄冈市,武穴市', '武穴', '中国,湖北,黄冈,武穴', '3', '0713', '435400', 'Wuxue', 'WX', 'W', '115.55975', '29.84446'),
('1920', '421200', '420000', '咸宁市', '中国,湖北省,咸宁市', '咸宁', '中国,湖北,咸宁', '2', '0715', '437000', 'Xianning', 'XN', 'X', '114.328963', '29.832798'),
('1921', '421202', '421200', '咸安区', '中国,湖北省,咸宁市,咸安区', '咸安', '中国,湖北,咸宁,咸安', '3', '0715', '437000', 'Xian\'an', 'XA', 'X', '114.29872', '29.8529'),
('1922', '421221', '421200', '嘉鱼县', '中国,湖北省,咸宁市,嘉鱼县', '嘉鱼', '中国,湖北,咸宁,嘉鱼', '3', '0715', '437200', 'Jiayu', 'JY', 'J', '113.93927', '29.97054'),
('1923', '421222', '421200', '通城县', '中国,湖北省,咸宁市,通城县', '通城', '中国,湖北,咸宁,通城', '3', '0715', '437400', 'Tongcheng', 'TC', 'T', '113.81582', '29.24568'),
('1924', '421223', '421200', '崇阳县', '中国,湖北省,咸宁市,崇阳县', '崇阳', '中国,湖北,咸宁,崇阳', '3', '0715', '437500', 'Chongyang', 'CY', 'C', '114.03982', '29.55564'),
('1925', '421224', '421200', '通山县', '中国,湖北省,咸宁市,通山县', '通山', '中国,湖北,咸宁,通山', '3', '0715', '437600', 'Tongshan', 'TS', 'T', '114.48239', '29.6063'),
('1926', '421281', '421200', '赤壁市', '中国,湖北省,咸宁市,赤壁市', '赤壁', '中国,湖北,咸宁,赤壁', '3', '0715', '437300', 'Chibi', 'CB', 'C', '113.90039', '29.72454'),
('1927', '421300', '420000', '随州市', '中国,湖北省,随州市', '随州', '中国,湖北,随州', '2', '0722', '441300', 'Suizhou', 'SZ', 'S', '113.37377', '31.717497'),
('1928', '421303', '421300', '曾都区', '中国,湖北省,随州市,曾都区', '曾都', '中国,湖北,随州,曾都', '3', '0722', '441300', 'Zengdu', 'ZD', 'Z', '113.37128', '31.71614'),
('1929', '421321', '421300', '随县', '中国,湖北省,随州市,随县', '随县', '中国,湖北,随州,随县', '3', '0722', '441309', 'Suixian', 'SX', 'S', '113.82663', '31.6179'),
('1930', '421381', '421300', '广水市', '中国,湖北省,随州市,广水市', '广水', '中国,湖北,随州,广水', '3', '0722', '432700', 'Guangshui', 'GS', 'G', '113.82663', '31.6179'),
('1931', '422800', '420000', '恩施土家族苗族自治州', '中国,湖北省,恩施土家族苗族自治州', '恩施', '中国,湖北,恩施', '2', '0718', '445000', 'Enshi', 'ES', 'E', '109.48699', '30.283114'),
('1932', '422801', '422800', '恩施市', '中国,湖北省,恩施土家族苗族自治州,恩施市', '恩施', '中国,湖北,恩施,恩施', '3', '0718', '445000', 'Enshi', 'ES', 'E', '109.47942', '30.29502'),
('1933', '422802', '422800', '利川市', '中国,湖北省,恩施土家族苗族自治州,利川市', '利川', '中国,湖北,恩施,利川', '3', '0718', '445400', 'Lichuan', 'LC', 'L', '108.93591', '30.29117'),
('1934', '422822', '422800', '建始县', '中国,湖北省,恩施土家族苗族自治州,建始县', '建始', '中国,湖北,恩施,建始', '3', '0718', '445300', 'Jianshi', 'JS', 'J', '109.72207', '30.60209'),
('1935', '422823', '422800', '巴东县', '中国,湖北省,恩施土家族苗族自治州,巴东县', '巴东', '中国,湖北,恩施,巴东', '3', '0718', '444300', 'Badong', 'BD', 'B', '110.34066', '31.04233'),
('1936', '422825', '422800', '宣恩县', '中国,湖北省,恩施土家族苗族自治州,宣恩县', '宣恩', '中国,湖北,恩施,宣恩', '3', '0718', '445500', 'Xuanen', 'XE', 'X', '109.49179', '29.98714'),
('1937', '422826', '422800', '咸丰县', '中国,湖北省,恩施土家族苗族自治州,咸丰县', '咸丰', '中国,湖北,恩施,咸丰', '3', '0718', '445600', 'Xianfeng', 'XF', 'X', '109.152', '29.67983'),
('1938', '422827', '422800', '来凤县', '中国,湖北省,恩施土家族苗族自治州,来凤县', '来凤', '中国,湖北,恩施,来凤', '3', '0718', '445700', 'Laifeng', 'LF', 'L', '109.40716', '29.49373'),
('1939', '422828', '422800', '鹤峰县', '中国,湖北省,恩施土家族苗族自治州,鹤峰县', '鹤峰', '中国,湖北,恩施,鹤峰', '3', '0718', '445800', 'Hefeng', 'HF', 'H', '110.03091', '29.89072'),
('1940', '429004', '420000', '仙桃市', '中国,湖北省,仙桃市', '仙桃', '中国,湖北,仙桃', '2', '0728', '433000', 'Xiantao', 'XT', 'X', '113.453974', '30.364953'),
('1941', '429005', '420000', '潜江市', '中国,湖北省,潜江市', '潜江', '中国,湖北,潜江', '2', '0728', '433100', 'Qianjiang', 'QJ', 'Q', '112.896866', '30.421215'),
('1942', '429006', '420000', '天门市', '中国,湖北省,天门市', '天门', '中国,湖北,天门', '2', '0728', '431700', 'Tianmen', 'TM', 'T', '113.165862', '30.653061'),
('1943', '429021', '420000', '神农架林区', '中国,湖北省,神农架林区', '神农架', '中国,湖北,神农架', '2', '0719', '442400', 'Shennongjia', 'SNJ', 'S', '110.671525', '31.744449'),
('1944', '429022', '429021', '松柏镇', '中国,湖北省,神农架林区,松柏镇', '松柏镇', '中国,湖北,神农架,松柏镇', '3', '0719', '442499', 'Songbai', 'SB', 'S', '110.661631', '31.746937'),
('1945', '429023', '429021', '阳日镇', '中国,湖北省,神农架林区,阳日镇', '阳日镇', '中国,湖北,神农架,阳日镇', '3', '0719', '442411', 'Yangri', 'YR', 'Y', '110.81953', '31.737393'),
('1946', '429024', '429021', '木鱼镇', '中国,湖北省,神农架林区,木鱼镇', '木鱼镇', '中国,湖北,神农架,木鱼镇', '3', '0719', '442421', 'Muyu', 'MY', 'M', '110.482912', '31.401419'),
('1947', '429025', '429021', '红坪镇', '中国,湖北省,神农架林区,红坪镇', '红坪镇', '中国,湖北,神农架,红坪镇', '3', '0719', '442416', 'Hongping', 'HP', 'H', '110.429295', '31.672835'),
('1948', '429026', '429021', '新华镇', '中国,湖北省,神农架林区,新华镇', '新华镇', '中国,湖北,神农架,新华镇', '3', '0719', '442412', 'Xinhua', 'XH', 'X', '110.891543', '31.592996'),
('1949', '429027', '429021', '大九湖', '中国,湖北省,神农架林区,大九湖', '大九湖', '中国,湖北,神农架,大九湖', '3', '0719', '442417', 'DaJiuHu', 'DJH', 'D', '110.054278', '31.472687'),
('1950', '429028', '429021', '宋洛', '中国,湖北省,神农架林区,宋洛', '宋洛', '中国,湖北,神农架,宋洛', '3', '0719', '442414', 'SongLuo', 'SL', 'S', '110.607962', '31.660861'),
('1951', '429029', '429021', '下谷坪乡', '中国,湖北省,神农架林区,下谷坪乡', '下谷坪乡', '中国,湖北,神农架,下谷坪乡', '3', '0719', '442417', 'Xiaguping', 'XLP', 'X', '110.244807', '31.366248'),
('1952', '429401', '429004', '沙嘴街道', '中国,湖北省,仙桃市,沙嘴街道', '沙嘴街道', '中国,湖北,仙桃,沙嘴街道', '3', '0728', '433099', 'Shazui', 'SZ', 'S', '113.450661', '30.357622'),
('1953', '429402', '429004', '干河街道', '中国,湖北省,仙桃市,干河街道', '干河街道', '中国,湖北,仙桃,干河街道', '3', '0728', '433000', 'Ganhe', 'GH', 'G', '113.434771', '30.377964'),
('1954', '429403', '429004', '龙华山街道', '中国,湖北省,仙桃市,龙华山街道', '龙华山街道', '中国,湖北,仙桃,龙华山街道', '3', '0728', '433099', 'Longhuashan', 'LHS', 'L', '113.461332', '30.36951'),
('1955', '429404', '429004', '郑场镇', '中国,湖北省,仙桃市,郑场镇', '郑场镇', '中国,湖北,仙桃,郑场镇', '3', '0728', '433009', 'Zhengchang', 'ZC', 'Z', '113.033681', '30.487221'),
('1956', '429405', '429004', '毛嘴镇', '中国,湖北省,仙桃市,毛嘴镇', '毛嘴镇', '中国,湖北,仙桃,毛嘴镇', '3', '0728', '433008', 'Maozui', 'MZ', 'M', '113.03534', '30.417311'),
('1957', '429406', '429004', '豆河镇', '中国,湖北省,仙桃市,豆河镇', '豆河镇', '中国,湖北,仙桃,豆河镇', '3', '0728', '433006', 'Douhe', 'DH', 'D', '113.086727', '30.342125'),
('1958', '429407', '429004', '三伏潭镇', '中国,湖北省,仙桃市,三伏潭镇', '三伏潭镇', '中国,湖北,仙桃,三伏潭镇', '3', '0728', '433005', 'Sanfutan', 'SFT', 'S', '113.201557', '30.375224'),
('1959', '429408', '429004', '胡场镇', '中国,湖北省,仙桃市,胡场镇', '胡场镇', '中国,湖北,仙桃,胡场镇', '3', '0728', '433004', 'Huchang', 'HC', 'H', '113.308093', '30.377514'),
('1960', '429409', '429004', '长埫口镇', '中国,湖北省,仙桃市,长埫口镇', '长埫口镇', '中国,湖北,仙桃,长埫口镇', '3', '0728', '433000', 'Changchongkou', 'CCK', 'C', '113.56446', '30.400802'),
('1961', '429410', '429004', '西流河镇', '中国,湖北省,仙桃市,西流河镇', '西流河镇', '中国,湖北,仙桃,西流河镇', '3', '0728', '433023', 'Xiliuhe', 'XLH', 'X', '113.677657', '30.314503'),
('1962', '429411', '429004', '沙湖镇', '中国,湖北省,仙桃市,沙湖镇', '沙湖镇', '中国,湖北,仙桃,沙湖镇', '3', '0728', '433019', 'Shahu', 'SH', 'S', '113.675143', '30.169342'),
('1963', '429412', '429004', '杨林尾镇', '中国,湖北省,仙桃市,杨林尾镇', '杨林尾镇', '中国,湖北,仙桃,杨林尾镇', '3', '0728', '433021', 'Yanglinwei', 'YLW', 'Y', '113.509285', '30.137065'),
('1964', '429413', '429004', '彭场镇', '中国,湖北省,仙桃市,彭场镇', '彭场镇', '中国,湖北,仙桃,彭场镇', '3', '0728', '433018', 'Pengchang', 'PC', 'P', '113.506437', '30.263346'),
('1965', '429414', '429004', '张沟镇', '中国,湖北省,仙桃市,张沟镇', '张沟镇', '中国,湖北,仙桃,张沟镇', '3', '0728', '433012', 'Zhanggou', 'ZG', 'Z', '113.380563', '30.250125'),
('1966', '429415', '429004', '郭河镇', '中国,湖北省,仙桃市,郭河镇', '郭河镇', '中国,湖北,仙桃,郭河镇', '3', '0728', '433013', 'Guohe', 'GH', 'G', '113.293617', '30.240358'),
('1967', '429416', '429004', '沔城镇', '中国,湖北省,仙桃市,沔城镇', '沔城镇', '中国,湖北,仙桃,沔城镇', '3', '0728', '433014', 'Miancheng', 'MC', 'M', '113.230883', '30.19298'),
('1968', '429417', '429004', '通海口镇', '中国,湖北省,仙桃市,通海口镇', '通海口镇', '中国,湖北,仙桃,通海口镇', '3', '0728', '433015', 'Tonghaikou', 'THK', 'T', '113.153304', '30.198232'),
('1969', '429418', '429004', '陈场镇', '中国,湖北省,仙桃市,陈场镇', '陈场镇', '中国,湖北,仙桃,陈场镇', '3', '0728', '433016', 'Chenchang', 'CC', 'C', '113.087722', '30.235173'),
('1970', '429419', '429004', '高新区', '中国,湖北省,仙桃市,高新区', '高新区', '中国,湖北,仙桃,高新区', '3', '0728', '433000', 'GaoXinQu', 'GXQ', 'G', '113.461174', '30.368949'),
('1971', '429420', '429004', '经济开发区', '中国,湖北省,仙桃市,经济开发区', '经济开发区', '中国,湖北,仙桃,经济开发区', '3', '0728', '433000', 'Xiantao', 'XT', 'X', '113.482741', '30.364754'),
('1972', '429421', '429004', '工业园区', '中国,湖北省,仙桃市,工业园区', '工业园区', '中国,湖北,仙桃,工业园区', '3', '0728', '433001', 'Gongyeyuanqu', 'GYYQ', 'G', '113.413655', '30.337322'),
('1973', '429422', '429004', '九合垸原种场', '中国,湖北省,仙桃市,九合垸原种场', '九合垸原种场', '中国,湖北,仙桃,九合垸原种场', '3', '0728', '433032', 'Jiuheyuan', 'JHY', 'J', '113.01493', '30.253517'),
('1974', '429423', '429004', '沙湖原种场', '中国,湖北省,仙桃市,沙湖原种场', '沙湖原种场', '中国,湖北,仙桃,沙湖原种场', '3', '0728', '433019', 'ShaYuan', 'SY', 'S', '113.662273', '30.158672'),
('1975', '429424', '429004', '排湖渔场', '中国,湖北省,仙桃市,排湖渔场', '排湖渔场', '中国,湖北,仙桃,排湖渔场', '3', '0728', '433025', 'PaiHu', 'PH', 'P', '113.239273', '30.293313'),
('1976', '429425', '429004', '五湖渔场', '中国,湖北省,仙桃市,五湖渔场', '五湖渔场', '中国,湖北,仙桃,五湖渔场', '3', '0728', '433019', 'Wuhu', 'WH', 'W', '113.790478', '30.200202'),
('1977', '429426', '429004', '赵西垸林场', '中国,湖北省,仙桃市,赵西垸林场', '赵西垸林场', '中国,湖北,仙桃,赵西垸林场', '3', '0728', '433000', 'Zhaoxiyuan', 'ZXY', 'Z', '113.017429', '30.294689'),
('1978', '429427', '429004', '刘家垸林场', '中国,湖北省,仙桃市,刘家垸林场', '刘家垸林场', '中国,湖北,仙桃,刘家垸林场', '3', '0728', '433029', 'LiuJiaYuan', 'LJY', 'L', '113.568221', '30.212263'),
('1979', '429428', '429004', '畜禽良种场', '中国,湖北省,仙桃市,畜禽良种场', '畜禽良种场', '中国,湖北,仙桃,畜禽良种场', '3', '0728', '433019', 'ChuQin', 'CQ', 'C', '113.768018', '30.180304'),
('1980', '429501', '429005', '园林', '中国,湖北省,潜江市,园林', '园林', '中国,湖北,潜江,园林', '3', '0728', '433199', 'Yuanlin', 'YL', 'Y', '112.766411', '30.402724'),
('1981', '429502', '429005', '广华', '中国,湖北省,潜江市,广华', '广华', '中国,湖北,潜江,广华', '3', '0728', '433124', 'Guanghua', 'GH', 'G', '112.70196', '30.43727'),
('1982', '429503', '429005', '杨市', '中国,湖北省,潜江市,杨市', '杨市', '中国,湖北,潜江,杨市', '3', '0728', '433133', 'Yangshi', 'YS', 'Y', '112.909641', '30.368142'),
('1983', '429504', '429005', '周矶', '中国,湖北省,潜江市,周矶', '周矶', '中国,湖北,潜江,周矶', '3', '0728', '433114', 'Zhouji', 'ZJ', 'Z', '112.802664', '30.412837'),
('1984', '429505', '429005', '泽口', '中国,湖北省,潜江市,泽口', '泽口', '中国,湖北,潜江,泽口', '3', '0728', '433132', 'Zekou', 'ZK', 'Z', '112.877209', '30.476885'),
('1985', '429506', '429005', '泰丰', '中国,湖北省,潜江市,泰丰', '泰丰', '中国,湖北,潜江,泰丰', '3', '0728', '433199', 'Taifeng', 'TF', 'T', '112.90701', '30.406384'),
('1986', '429507', '429005', '高场', '中国,湖北省,潜江市,高场', '高场', '中国,湖北,潜江,高场', '3', '0728', '433115', 'Gaochang', 'GC', 'G', '112.732892', '30.404486'),
('1987', '429508', '429005', '熊口镇', '中国,湖北省,潜江市,熊口镇', '熊口镇', '中国,湖北,潜江,熊口镇', '3', '0728', '433136', 'Xiongkou', 'XK', 'X', '112.781307', '30.306017'),
('1988', '429509', '429005', '竹根滩镇', '中国,湖北省,潜江市,竹根滩镇', '竹根滩镇', '中国,湖北,潜江,竹根滩镇', '3', '0728', '433131', 'Zhugentan', 'ZGT', 'Z', '112.906645', '30.493506'),
('1989', '429510', '429005', '高石碑镇', '中国,湖北省,潜江市,高石碑镇', '高石碑镇', '中国,湖北,潜江,高石碑镇', '3', '0728', '433126', 'Gaoshibei', 'GSB', 'G', '112.673268', '30.547511'),
('1990', '429511', '429005', '老新镇', '中国,湖北省,潜江市,老新镇', '老新镇', '中国,湖北,潜江,老新镇', '3', '0728', '433111', 'Laoxin', 'LX', 'L', '112.85718', '30.188775'),
('1991', '429512', '429005', '王场镇', '中国,湖北省,潜江市,王场镇', '王场镇', '中国,湖北,潜江,王场镇', '3', '0728', '433122', 'Wangchang', 'WC', 'W', '112.774326', '30.504891'),
('1992', '429513', '429005', '渔洋镇', '中国,湖北省,潜江市,渔洋镇', '渔洋镇', '中国,湖北,潜江,渔洋镇', '3', '0728', '433138', 'Yuyang', 'YY', 'Y', '112.908916', '30.172422'),
('1993', '429514', '429005', '龙湾镇', '中国,湖北省,潜江市,龙湾镇', '龙湾镇', '中国,湖北,潜江,龙湾镇', '3', '0728', '433139', 'Longwan', 'LW', 'L', '112.716927', '30.229398'),
('1994', '429515', '429005', '浩口镇', '中国,湖北省,潜江市,浩口镇', '浩口镇', '中国,湖北,潜江,浩口镇', '3', '0728', '433116', 'Haokou', 'HK', 'H', '112.649998', '30.378737'),
('1995', '429516', '429005', '积玉口镇', '中国,湖北省,潜江市,积玉口镇', '积玉口镇', '中国,湖北,潜江,积玉口镇', '3', '0728', '433128', 'Jiyukou', 'JYK', 'J', '112.602848', '30.445452'),
('1996', '429517', '429005', '张金镇', '中国,湖北省,潜江市,张金镇', '张金镇', '中国,湖北,潜江,张金镇', '3', '0728', '433140', 'Zhangjin', 'ZJ', 'Z', '112.596542', '30.191927'),
('1997', '429518', '429005', '白鹭湖管理区', '中国,湖北省,潜江市,白鹭湖管理区', '白鹭湖管理区', '中国,湖北,潜江,白鹭湖管理区', '3', '0728', '433100', 'Bailuhu', 'BLH', 'B', '112.76611', '30.107631'),
('1998', '429519', '429005', '总口管理区', '中国,湖北省,潜江市,总口管理区', '总口管理区', '中国,湖北,潜江,总口管理区', '3', '0728', '433100', 'ZongKou', 'ZK', 'Z', '112.920845', '30.288729'),
('1999', '429520', '429005', '熊口农场管理区', '中国,湖北省,潜江市,熊口农场管理区', '熊口农场管理区', '中国,湖北,潜江,熊口农场管理区', '3', '0728', '433100', 'Xiongkou', 'XK', 'X', '112.775832', '30.29538'),
('2000', '429521', '429005', '运粮湖管理区', '中国,湖北省,潜江市,运粮湖管理区', '运粮湖管理区', '中国,湖北,潜江,运粮湖管理区', '3', '0728', '433100', 'Yunlianghu', 'YLH', 'Y', '112.5278', '30.28441'),
('2001', '429522', '429005', '后湖管理区', '中国,湖北省,潜江市,后湖管理区', '后湖管理区', '中国,湖北,潜江,后湖管理区', '3', '0728', '433100', 'Houhu', 'HH', 'H', '112.725628', '30.397626'),
('2002', '429523', '429005', '周矶管理区', '中国,湖北省,潜江市,周矶管理区', '周矶管理区', '中国,湖北,潜江,周矶管理区', '3', '0728', '433100', 'Zhouji', 'ZJ', 'Z', '112.789016', '30.457597'),
('2003', '429524', '429005', '经济开发区', '中国,湖北省,潜江市,经济开发区', '经济开发区', '中国,湖北,潜江,经济开发区', '3', '0728', '433100', 'Kaifaqu', 'KF', 'K', '112.873731', '30.465897'),
('2004', '429601', '429006', '竟陵街道', '中国,湖北省,天门市,竟陵街道', '竟陵街道', '中国,湖北,天门,竟陵街道', '3', '0728', '431700', 'Jingling', 'JL', 'J', '113.16709', '30.64568'),
('2005', '429602', '429006', '杨林街道', '中国,湖北省,天门市,杨林街道', '杨林街道', '中国,湖北,天门,杨林街道', '3', '0728', '431732', 'Yanglin', 'YL', 'Y', '113.194881', '30.639918'),
('2006', '429603', '429006', '佛子山镇', '中国,湖北省,天门市,佛子山镇', '佛子山镇', '中国,湖北,天门,佛子山镇', '3', '0728', '431707', 'Fozishan', 'FZS', 'F', '113.011283', '30.752449'),
('2007', '429604', '429006', '多宝镇', '中国,湖北省,天门市,多宝镇', '多宝镇', '中国,湖北,天门,多宝镇', '3', '0728', '431722', 'Duobao', 'DB', 'D', '112.697685', '30.668136'),
('2008', '429605', '429006', '拖市镇', '中国,湖北省,天门市,拖市镇', '拖市镇', '中国,湖北,天门,拖市镇', '3', '0728', '43171', 'Tuoshi', 'TS', 'T', '112.839027', '30.727011'),
('2009', '429606', '429006', '张港镇', '中国,湖北省,天门市,张港镇', '张港镇', '中国,湖北,天门,张港镇', '3', '0728', '431726', 'Zhanggang', 'ZG', 'Z', '112.881476', '30.567657'),
('2010', '429607', '429006', '蒋场镇', '中国,湖北省,天门市,蒋场镇', '蒋场镇', '中国,湖北,天门,蒋场镇', '3', '0728', '431716', 'Jiangchang', 'JC', 'J', '112.945059', '30.605891'),
('2011', '429608', '429006', '汪场镇', '中国,湖北省,天门市,汪场镇', '汪场镇', '中国,湖北,天门,汪场镇', '3', '0728', '431717', 'Wangchang', 'WC', 'W', '113.041017', '30.613778'),
('2012', '429609', '429006', '渔薪镇', '中国,湖北省,天门市,渔薪镇', '渔薪镇', '中国,湖北,天门,渔薪镇', '3', '0728', '431709', 'Yuxin', 'YX', 'Y', '112.990563', '30.675337'),
('2013', '429610', '429006', '黄潭镇', '中国,湖北省,天门市,黄潭镇', '黄潭镇', '中国,湖北,天门,黄潭镇', '3', '0728', '431708', 'Huangtan', 'HT', 'H', '113.090808', '30.659423'),
('2014', '429611', '429006', '岳口镇', '中国,湖北省,天门市,岳口镇', '岳口镇', '中国,湖北,天门,岳口镇', '3', '0728', '431702', 'Yuekou', 'YK', 'Y', '113.093583', '30.507149'),
('2015', '429612', '429006', '横林镇', '中国,湖北省,天门市,横林镇', '横林镇', '中国,湖北,天门,横林镇', '3', '0728', '431720', 'Henglin', 'HL', 'H', '113.188342', '30.536738'),
('2016', '429613', '429006', '彭市镇', '中国,湖北省,天门市,彭市镇', '彭市镇', '中国,湖北,天门,彭市镇', '3', '0728', '431718', 'Pengshi', 'PS', 'P', '113.187885', '30.456585'),
('2017', '429614', '429006', '麻洋镇', '中国,湖北省,天门市,麻洋镇', '麻洋镇', '中国,湖北,天门,麻洋镇', '3', '0728', '431727', 'Mayang', 'MY', 'M', '113.267874', '30.435305'),
('2018', '429615', '429006', '多祥镇', '中国,湖北省,天门市,多祥镇', '多祥镇', '中国,湖北,天门,多祥镇', '3', '0728', '431728', 'Duoxiang', 'DX', 'D', '113.36706', '30.427416'),
('2019', '429616', '429006', '干驿镇', '中国,湖北省,天门市,干驿镇', '干驿镇', '中国,湖北,天门,干驿镇', '3', '0728', '431714', 'Ganyi', 'GY', 'G', '113.386194', '30.543403'),
('2020', '429617', '429006', '马湾镇', '中国,湖北省,天门市,马湾镇', '马湾镇', '中国,湖北,天门,马湾镇', '3', '0728', '431715', 'Mawan', 'MW', 'M', '113.334657', '30.573308'),
('2021', '429618', '429006', '卢市镇', '中国,湖北省,天门市,卢市镇', '卢市镇', '中国,湖北,天门,卢市镇', '3', '0728', '431729', 'Lushi', 'LS', 'L', '113.33228', '30.668136'),
('2022', '429619', '429006', '小板镇', '中国,湖北省,天门市,小板镇', '小板镇', '中国,湖北,天门,小板镇', '3', '0728', '431731', 'Xiaoban', 'XB', 'X', '113.224974', '30.608035'),
('2023', '429620', '429006', '九真镇', '中国,湖北省,天门市,九真镇', '九真镇', '中国,湖北,天门,九真镇', '3', '0728', '431705', 'Jiuzhen', 'JZ', 'J', '113.220015', '30.741711'),
('2024', '429621', '429006', '皂市镇', '中国,湖北省,天门市,皂市镇', '皂市镇', '中国,湖北,天门,皂市镇', '3', '0728', '431703', 'Zaoshi', 'ZS', 'Z', '113.349357', '30.856178'),
('2025', '429622', '429006', '胡市镇', '中国,湖北省,天门市,胡市镇', '胡市镇', '中国,湖北,天门,胡市镇', '3', '0728', '431713', 'Hushi', 'HS', 'H', '113.389702', '30.779672'),
('2026', '429623', '429006', '石河镇', '中国,湖北省,天门市,石河镇', '石河镇', '中国,湖北,天门,石河镇', '3', '0728', '431706', 'Shihe', 'SH', 'S', '113.086015', '30.75847'),
('2027', '429624', '429006', '净潭乡', '中国,湖北省,天门市,净潭乡', '净潭乡', '中国,湖北,天门,净潭乡', '3', '0728', '431730', 'Jingtanxiang', 'JTX', 'J', '113.400548', '30.655127'),
('2028', '429625', '429006', '蒋湖农场', '中国,湖北省,天门市,蒋湖农场', '蒋湖农场', '中国,湖北,天门,蒋湖农场', '3', '0728', '431725', 'Jianghu', 'JH', 'J', '112.85411', '30.636486'),
('2029', '429626', '429006', '白茅湖农场', '中国,湖北省,天门市,白茅湖农场', '白茅湖农场', '中国,湖北,天门,白茅湖农场', '3', '0728', '431719', 'Baimaohu', 'BMH', 'B', '113.101479', '30.60746'),
('2030', '429627', '429006', '沉湖林业科技示范区', '中国,湖北省,天门市,沉湖林业科技示范区', '沉湖林业科技示范区', '中国,湖北,天门,沉湖林业科技示范区', '3', '0728', '431700', 'Chenhu', 'CH', 'C', '113.165862', '30.653061'),
('2031', '429628', '429006', '天门工业园', '中国,湖北省,天门市,天门工业园', '天门工业园', '中国,湖北,天门,天门工业园', '3', '0728', '431700', 'Gongyeyuan', 'GYY', 'G', '113.446422', '30.402596'),
('2032', '429629', '429006', '侨乡街道开发区', '中国,湖北省,天门市,侨乡街道开发区', '侨乡街道开发区', '中国,湖北,天门,侨乡街道开发区', '3', '0728', '431700', 'Qiaoxiang', 'QX', 'Q', '113.15723', '30.634943'),
('2033', '430000', '100000', '湖南省', '中国,湖南省', '湖南', '中国,湖南', '1', '', '', 'Hunan', 'HN', 'H', '112.982279', '28.19409'),
('2034', '430100', '430000', '长沙市', '中国,湖南省,长沙市', '长沙', '中国,湖南,长沙', '2', '0731', '410000', 'Changsha', 'CS', 'C', '112.982279', '28.19409'),
('2035', '430102', '430100', '芙蓉区', '中国,湖南省,长沙市,芙蓉区', '芙蓉', '中国,湖南,长沙,芙蓉', '3', '0731', '410000', 'Furong', 'FR', 'F', '113.03176', '28.1844'),
('2036', '430103', '430100', '天心区', '中国,湖南省,长沙市,天心区', '天心', '中国,湖南,长沙,天心', '3', '0731', '410000', 'Tianxin', 'TX', 'T', '112.98991', '28.1127'),
('2037', '430104', '430100', '岳麓区', '中国,湖南省,长沙市,岳麓区', '岳麓', '中国,湖南,长沙,岳麓', '3', '0731', '410000', 'Yuelu', 'YL', 'Y', '112.93133', '28.2351'),
('2038', '430105', '430100', '开福区', '中国,湖南省,长沙市,开福区', '开福', '中国,湖南,长沙,开福', '3', '0731', '410000', 'Kaifu', 'KF', 'K', '112.98623', '28.25585'),
('2039', '430111', '430100', '雨花区', '中国,湖南省,长沙市,雨花区', '雨花', '中国,湖南,长沙,雨花', '3', '0731', '410000', 'Yuhua', 'YH', 'Y', '113.03567', '28.13541'),
('2040', '430112', '430100', '望城区', '中国,湖南省,长沙市,望城区', '望城', '中国,湖南,长沙,望城', '3', '0731', '410200', 'Wangcheng', 'WC', 'W', '112.819549', '28.347458'),
('2041', '430121', '430100', '长沙县', '中国,湖南省,长沙市,长沙县', '长沙', '中国,湖南,长沙,长沙', '3', '0731', '410100', 'Changsha', 'CS', 'C', '113.08071', '28.24595'),
('2042', '430124', '430100', '宁乡市', '中国,湖南省,长沙市,宁乡市', '宁乡', '中国,湖南,长沙,宁乡', '3', '0731', '410600', 'Ningxiang', 'NX', 'N', '112.55749', '28.25358'),
('2043', '430181', '430100', '浏阳市', '中国,湖南省,长沙市,浏阳市', '浏阳', '中国,湖南,长沙,浏阳', '3', '0731', '410300', 'Liuyang', 'LY', 'L', '113.64312', '28.16375'),
('2044', '430182', '430100', '湘江新区', '中国,湖南省,长沙市,湘江新区', '湘江新区', '中国,湖南,长沙,湘江新区', '3', '0731', '410005', 'XiangJiangXiQu', 'XJXQ', 'X', '112.93769', '28.140266'),
('2045', '430200', '430000', '株洲市', '中国,湖南省,株洲市', '株洲', '中国,湖南,株洲', '2', '0731', '412000', 'Zhuzhou', 'ZZ', 'Z', '113.151737', '27.835806'),
('2046', '430202', '430200', '荷塘区', '中国,湖南省,株洲市,荷塘区', '荷塘', '中国,湖南,株洲,荷塘', '3', '0731', '412000', 'Hetang', 'HT', 'H', '113.17315', '27.85569'),
('2047', '430203', '430200', '芦淞区', '中国,湖南省,株洲市,芦淞区', '芦淞', '中国,湖南,株洲,芦淞', '3', '0731', '412000', 'Lusong', 'LS', 'L', '113.15562', '27.78525'),
('2048', '430204', '430200', '石峰区', '中国,湖南省,株洲市,石峰区', '石峰', '中国,湖南,株洲,石峰', '3', '0731', '412000', 'Shifeng', 'SF', 'S', '113.11776', '27.87552'),
('2049', '430211', '430200', '天元区', '中国,湖南省,株洲市,天元区', '天元', '中国,湖南,株洲,天元', '3', '0731', '412000', 'Tianyuan', 'TY', 'T', '113.12335', '27.83103'),
('2050', '430221', '430200', '株洲县', '中国,湖南省,株洲市,株洲县', '株洲', '中国,湖南,株洲,株洲', '3', '0731', '412000', 'Zhuzhou', 'ZZ', 'Z', '113.14428', '27.69826'),
('2051', '430223', '430200', '攸县', '中国,湖南省,株洲市,攸县', '攸县', '中国,湖南,株洲,攸县', '3', '0731', '412300', 'Youxian', 'YX', 'Y', '113.34365', '27.00352'),
('2052', '430224', '430200', '茶陵县', '中国,湖南省,株洲市,茶陵县', '茶陵', '中国,湖南,株洲,茶陵', '3', '0731', '412400', 'Chaling', 'CL', 'C', '113.54364', '26.7915'),
('2053', '430225', '430200', '炎陵县', '中国,湖南省,株洲市,炎陵县', '炎陵', '中国,湖南,株洲,炎陵', '3', '0731', '412500', 'Yanling', 'YL', 'Y', '113.77163', '26.48818'),
('2054', '430281', '430200', '醴陵市', '中国,湖南省,株洲市,醴陵市', '醴陵', '中国,湖南,株洲,醴陵', '3', '0731', '412200', 'Liling', 'LL', 'L', '113.49704', '27.64615'),
('2055', '430300', '430000', '湘潭市', '中国,湖南省,湘潭市', '湘潭', '中国,湖南,湘潭', '2', '0731', '411100', 'Xiangtan', 'XT', 'X', '112.925083', '27.846725'),
('2056', '430302', '430300', '雨湖区', '中国,湖南省,湘潭市,雨湖区', '雨湖', '中国,湖南,湘潭,雨湖', '3', '0731', '411100', 'Yuhu', 'YH', 'Y', '112.90399', '27.86859'),
('2057', '430304', '430300', '岳塘区', '中国,湖南省,湘潭市,岳塘区', '岳塘', '中国,湖南,湘潭,岳塘', '3', '0731', '411100', 'Yuetang', 'YT', 'Y', '112.9606', '27.85784'),
('2058', '430321', '430300', '湘潭县', '中国,湖南省,湘潭市,湘潭县', '湘潭', '中国,湖南,湘潭,湘潭', '3', '0731', '411200', 'Xiangtan', 'XT', 'X', '112.9508', '27.77893'),
('2059', '430381', '430300', '湘乡市', '中国,湖南省,湘潭市,湘乡市', '湘乡', '中国,湖南,湘潭,湘乡', '3', '0731', '411400', 'Xiangxiang', 'XX', 'X', '112.53512', '27.73543'),
('2060', '430382', '430300', '韶山市', '中国,湖南省,湘潭市,韶山市', '韶山', '中国,湖南,湘潭,韶山', '3', '0731', '411300', 'Shaoshan', 'SS', 'S', '112.52655', '27.91503'),
('2061', '430383', '430300', '高新区', '中国,湖南省,湘潭市,高新区', '高新区', '中国,湖南,湘潭,高新区', '3', '0731', '411100', 'Gaoxinqu', 'GXQ', 'G', '112.939865', '27.822804'),
('2062', '430400', '430000', '衡阳市', '中国,湖南省,衡阳市', '衡阳', '中国,湖南,衡阳', '2', '0734', '421000', 'Hengyang', 'HY', 'H', '112.607693', '26.900358'),
('2063', '430405', '430400', '珠晖区', '中国,湖南省,衡阳市,珠晖区', '珠晖', '中国,湖南,衡阳,珠晖', '3', '0734', '421000', 'Zhuhui', 'ZH', 'Z', '112.62054', '26.89361'),
('2064', '430406', '430400', '雁峰区', '中国,湖南省,衡阳市,雁峰区', '雁峰', '中国,湖南,衡阳,雁峰', '3', '0734', '421000', 'Yanfeng', 'YF', 'Y', '112.61654', '26.88866'),
('2065', '430407', '430400', '石鼓区', '中国,湖南省,衡阳市,石鼓区', '石鼓', '中国,湖南,衡阳,石鼓', '3', '0734', '421000', 'Shigu', 'SG', 'S', '112.61069', '26.90232'),
('2066', '430408', '430400', '蒸湘区', '中国,湖南省,衡阳市,蒸湘区', '蒸湘', '中国,湖南,衡阳,蒸湘', '3', '0734', '421000', 'Zhengxiang', 'ZX', 'Z', '112.6033', '26.89651'),
('2067', '430412', '430400', '南岳区', '中国,湖南省,衡阳市,南岳区', '南岳', '中国,湖南,衡阳,南岳', '3', '0734', '421000', 'Nanyue', 'NY', 'N', '112.7384', '27.23262'),
('2068', '430421', '430400', '衡阳县', '中国,湖南省,衡阳市,衡阳县', '衡阳', '中国,湖南,衡阳,衡阳', '3', '0734', '421200', 'Hengyang', 'HY', 'H', '112.37088', '26.9706'),
('2069', '430422', '430400', '衡南县', '中国,湖南省,衡阳市,衡南县', '衡南', '中国,湖南,衡阳,衡南', '3', '0734', '421100', 'Hengnan', 'HN', 'H', '112.67788', '26.73828'),
('2070', '430423', '430400', '衡山县', '中国,湖南省,衡阳市,衡山县', '衡山', '中国,湖南,衡阳,衡山', '3', '0734', '421300', 'Hengshan', 'HS', 'H', '112.86776', '27.23134'),
('2071', '430424', '430400', '衡东县', '中国,湖南省,衡阳市,衡东县', '衡东', '中国,湖南,衡阳,衡东', '3', '0734', '421400', 'Hengdong', 'HD', 'H', '112.94833', '27.08093'),
('2072', '430426', '430400', '祁东县', '中国,湖南省,衡阳市,祁东县', '祁东', '中国,湖南,衡阳,祁东', '3', '0734', '421600', 'Qidong', 'QD', 'Q', '112.09039', '26.79964'),
('2073', '430481', '430400', '耒阳市', '中国,湖南省,衡阳市,耒阳市', '耒阳', '中国,湖南,衡阳,耒阳', '3', '0734', '421800', 'Leiyang', 'LY', 'L', '112.85998', '26.42132'),
('2074', '430482', '430400', '常宁市', '中国,湖南省,衡阳市,常宁市', '常宁', '中国,湖南,衡阳,常宁', '3', '0734', '421500', 'Changning', 'CN', 'C', '112.4009', '26.40692'),
('2075', '430483', '430400', '高新区', '中国,湖南省,衡阳市,高新区', '高新区', '中国,湖南,衡阳,高新区', '3', '0734', '421000', 'Gaoxinqu', 'GXQ', 'G', '112.572172', '26.892209'),
('2076', '430500', '430000', '邵阳市', '中国,湖南省,邵阳市', '邵阳', '中国,湖南,邵阳', '2', '0739', '422000', 'Shaoyang', 'SY', 'S', '111.46923', '27.237842'),
('2077', '430502', '430500', '双清区', '中国,湖南省,邵阳市,双清区', '双清', '中国,湖南,邵阳,双清', '3', '0739', '422000', 'Shuangqing', 'SQ', 'S', '111.49715', '27.23291'),
('2078', '430503', '430500', '大祥区', '中国,湖南省,邵阳市,大祥区', '大祥', '中国,湖南,邵阳,大祥', '3', '0739', '422000', 'Daxiang', 'DX', 'D', '111.45412', '27.23332'),
('2079', '430511', '430500', '北塔区', '中国,湖南省,邵阳市,北塔区', '北塔', '中国,湖南,邵阳,北塔', '3', '0739', '422000', 'Beita', 'BT', 'B', '111.45219', '27.24648'),
('2080', '430521', '430500', '邵东县', '中国,湖南省,邵阳市,邵东县', '邵东', '中国,湖南,邵阳,邵东', '3', '0739', '422800', 'Shaodong', 'SD', 'S', '111.74441', '27.2584'),
('2081', '430522', '430500', '新邵县', '中国,湖南省,邵阳市,新邵县', '新邵', '中国,湖南,邵阳,新邵', '3', '0739', '422900', 'Xinshao', 'XS', 'X', '111.46066', '27.32169'),
('2082', '430523', '430500', '邵阳县', '中国,湖南省,邵阳市,邵阳县', '邵阳', '中国,湖南,邵阳,邵阳', '3', '0739', '422100', 'Shaoyang', 'SY', 'S', '111.27459', '26.99143'),
('2083', '430524', '430500', '隆回县', '中国,湖南省,邵阳市,隆回县', '隆回', '中国,湖南,邵阳,隆回', '3', '0739', '422200', 'Longhui', 'LH', 'L', '111.03216', '27.10937'),
('2084', '430525', '430500', '洞口县', '中国,湖南省,邵阳市,洞口县', '洞口', '中国,湖南,邵阳,洞口', '3', '0739', '422300', 'Dongkou', 'DK', 'D', '110.57388', '27.05462'),
('2085', '430527', '430500', '绥宁县', '中国,湖南省,邵阳市,绥宁县', '绥宁', '中国,湖南,邵阳,绥宁', '3', '0739', '422600', 'Suining', 'SN', 'S', '110.15576', '26.58636'),
('2086', '430528', '430500', '新宁县', '中国,湖南省,邵阳市,新宁县', '新宁', '中国,湖南,邵阳,新宁', '3', '0739', '422700', 'Xinning', 'XN', 'X', '110.85131', '26.42936'),
('2087', '430529', '430500', '城步苗族自治县', '中国,湖南省,邵阳市,城步苗族自治县', '城步', '中国,湖南,邵阳,城步', '3', '0739', '422500', 'Chengbu', 'CB', 'C', '110.3222', '26.39048'),
('2088', '430581', '430500', '武冈市', '中国,湖南省,邵阳市,武冈市', '武冈', '中国,湖南,邵阳,武冈', '3', '0739', '422400', 'Wugang', 'WG', 'W', '110.63281', '26.72817'),
('2089', '430600', '430000', '岳阳市', '中国,湖南省,岳阳市', '岳阳', '中国,湖南,岳阳', '2', '0730', '414000', 'Yueyang', 'YY', 'Y', '113.132855', '29.37029'),
('2090', '430602', '430600', '岳阳楼区', '中国,湖南省,岳阳市,岳阳楼区', '岳阳楼', '中国,湖南,岳阳,岳阳楼', '3', '0730', '414000', 'Yueyanglou', 'YYL', 'Y', '113.12942', '29.3719'),
('2091', '430603', '430600', '云溪区', '中国,湖南省,岳阳市,云溪区', '云溪', '中国,湖南,岳阳,云溪', '3', '0730', '414000', 'Yunxi', 'YX', 'Y', '113.27713', '29.47357'),
('2092', '430611', '430600', '君山区', '中国,湖南省,岳阳市,君山区', '君山', '中国,湖南,岳阳,君山', '3', '0730', '414000', 'Junshan', 'JS', 'J', '113.00439', '29.45941'),
('2093', '430621', '430600', '岳阳县', '中国,湖南省,岳阳市,岳阳县', '岳阳', '中国,湖南,岳阳,岳阳', '3', '0730', '414100', 'Yueyang', 'YY', 'Y', '113.11987', '29.14314'),
('2094', '430623', '430600', '华容县', '中国,湖南省,岳阳市,华容县', '华容', '中国,湖南,岳阳,华容', '3', '0730', '414200', 'Huarong', 'HR', 'H', '112.54089', '29.53019'),
('2095', '430624', '430600', '湘阴县', '中国,湖南省,岳阳市,湘阴县', '湘阴', '中国,湖南,岳阳,湘阴', '3', '0730', '410500', 'Xiangyin', 'XY', 'X', '112.90911', '28.68922'),
('2096', '430626', '430600', '平江县', '中国,湖南省,岳阳市,平江县', '平江', '中国,湖南,岳阳,平江', '3', '0730', '410400', 'Pingjiang', 'PJ', 'P', '113.58105', '28.70664'),
('2097', '430681', '430600', '汨罗市', '中国,湖南省,岳阳市,汨罗市', '汨罗', '中国,湖南,岳阳,汨罗', '3', '0730', '414400', 'Miluo', 'ML', 'M', '113.06707', '28.80631'),
('2098', '430682', '430600', '临湘市', '中国,湖南省,岳阳市,临湘市', '临湘', '中国,湖南,岳阳,临湘', '3', '0730', '414300', 'Linxiang', 'LX', 'L', '113.4501', '29.47701'),
('2099', '430700', '430000', '常德市', '中国,湖南省,常德市', '常德', '中国,湖南,常德', '2', '0736', '415000', 'Changde', 'CD', 'C', '111.691347', '29.040225'),
('2100', '430702', '430700', '武陵区', '中国,湖南省,常德市,武陵区', '武陵', '中国,湖南,常德,武陵', '3', '0736', '415000', 'Wuling', 'WL', 'W', '111.69791', '29.02876'),
('2101', '430703', '430700', '鼎城区', '中国,湖南省,常德市,鼎城区', '鼎城', '中国,湖南,常德,鼎城', '3', '0736', '415100', 'Dingcheng', 'DC', 'D', '111.68078', '29.01859'),
('2102', '430721', '430700', '安乡县', '中国,湖南省,常德市,安乡县', '安乡', '中国,湖南,常德,安乡', '3', '0736', '415600', 'Anxiang', 'AX', 'A', '112.16732', '29.41326'),
('2103', '430722', '430700', '汉寿县', '中国,湖南省,常德市,汉寿县', '汉寿', '中国,湖南,常德,汉寿', '3', '0736', '415900', 'Hanshou', 'HS', 'H', '111.96691', '28.90299'),
('2104', '430723', '430700', '澧县', '中国,湖南省,常德市,澧县', '澧县', '中国,湖南,常德,澧县', '3', '0736', '415500', 'Lixian', 'LX', 'L', '111.75866', '29.63317'),
('2105', '430724', '430700', '临澧县', '中国,湖南省,常德市,临澧县', '临澧', '中国,湖南,常德,临澧', '3', '0736', '415200', 'Linli', 'LL', 'L', '111.65161', '29.44163'),
('2106', '430725', '430700', '桃源县', '中国,湖南省,常德市,桃源县', '桃源', '中国,湖南,常德,桃源', '3', '0736', '415700', 'Taoyuan', 'TY', 'T', '111.48892', '28.90474'),
('2107', '430726', '430700', '石门县', '中国,湖南省,常德市,石门县', '石门', '中国,湖南,常德,石门', '3', '0736', '415300', 'Shimen', 'SM', 'S', '111.37966', '29.58424'),
('2108', '430781', '430700', '津市市', '中国,湖南省,常德市,津市市', '津市', '中国,湖南,常德,津市', '3', '0736', '415400', 'Jinshi', 'JS', 'J', '111.87756', '29.60563'),
('2109', '430800', '430000', '张家界市', '中国,湖南省,张家界市', '张家界', '中国,湖南,张家界', '2', '0744', '427000', 'Zhangjiajie', 'ZJJ', 'Z', '110.479921', '29.127401'),
('2110', '430802', '430800', '永定区', '中国,湖南省,张家界市,永定区', '永定', '中国,湖南,张家界,永定', '3', '0744', '427000', 'Yongding', 'YD', 'Y', '110.47464', '29.13387'),
('2111', '430811', '430800', '武陵源区', '中国,湖南省,张家界市,武陵源区', '武陵源', '中国,湖南,张家界,武陵源', '3', '0744', '427400', 'Wulingyuan', 'WLY', 'W', '110.55026', '29.34574'),
('2112', '430821', '430800', '慈利县', '中国,湖南省,张家界市,慈利县', '慈利', '中国,湖南,张家界,慈利', '3', '0744', '427200', 'Cili', 'CL', 'C', '111.13946', '29.42989'),
('2113', '430822', '430800', '桑植县', '中国,湖南省,张家界市,桑植县', '桑植', '中国,湖南,张家界,桑植', '3', '0744', '427100', 'Sangzhi', 'SZ', 'S', '110.16308', '29.39815'),
('2114', '430900', '430000', '益阳市', '中国,湖南省,益阳市', '益阳', '中国,湖南,益阳', '2', '0737', '413000', 'Yiyang', 'YY', 'Y', '112.355042', '28.570066'),
('2115', '430902', '430900', '资阳区', '中国,湖南省,益阳市,资阳区', '资阳', '中国,湖南,益阳,资阳', '3', '0737', '413000', 'Ziyang', 'ZY', 'Z', '112.32447', '28.59095'),
('2116', '430903', '430900', '赫山区', '中国,湖南省,益阳市,赫山区', '赫山', '中国,湖南,益阳,赫山', '3', '0737', '413000', 'Heshan', 'HS', 'H', '112.37265', '28.57425'),
('2117', '430921', '430900', '南县', '中国,湖南省,益阳市,南县', '南县', '中国,湖南,益阳,南县', '3', '0737', '413200', 'Nanxian', 'NX', 'N', '112.3963', '29.36159'),
('2118', '430922', '430900', '桃江县', '中国,湖南省,益阳市,桃江县', '桃江', '中国,湖南,益阳,桃江', '3', '0737', '413400', 'Taojiang', 'TJ', 'T', '112.1557', '28.51814'),
('2119', '430923', '430900', '安化县', '中国,湖南省,益阳市,安化县', '安化', '中国,湖南,益阳,安化', '3', '0737', '413500', 'Anhua', 'AH', 'A', '111.21298', '28.37424'),
('2120', '430981', '430900', '沅江市', '中国,湖南省,益阳市,沅江市', '沅江', '中国,湖南,益阳,沅江', '3', '0737', '413100', 'Yuanjiang', 'YJ', 'Y', '112.35427', '28.84403'),
('2121', '431000', '430000', '郴州市', '中国,湖南省,郴州市', '郴州', '中国,湖南,郴州', '2', '0735', '423000', 'Chenzhou', 'CZ', 'C', '113.032067', '25.793589'),
('2122', '431002', '431000', '北湖区', '中国,湖南省,郴州市,北湖区', '北湖', '中国,湖南,郴州,北湖', '3', '0735', '423000', 'Beihu', 'BH', 'B', '113.01103', '25.78405'),
('2123', '431003', '431000', '苏仙区', '中国,湖南省,郴州市,苏仙区', '苏仙', '中国,湖南,郴州,苏仙', '3', '0735', '423000', 'Suxian', 'SX', 'S', '113.04226', '25.80045'),
('2124', '431021', '431000', '桂阳县', '中国,湖南省,郴州市,桂阳县', '桂阳', '中国,湖南,郴州,桂阳', '3', '0735', '424400', 'Guiyang', 'GY', 'G', '112.73364', '25.75406'),
('2125', '431022', '431000', '宜章县', '中国,湖南省,郴州市,宜章县', '宜章', '中国,湖南,郴州,宜章', '3', '0735', '424200', 'Yizhang', 'YZ', 'Y', '112.95147', '25.39931'),
('2126', '431023', '431000', '永兴县', '中国,湖南省,郴州市,永兴县', '永兴', '中国,湖南,郴州,永兴', '3', '0735', '423300', 'Yongxing', 'YX', 'Y', '113.11242', '26.12646'),
('2127', '431024', '431000', '嘉禾县', '中国,湖南省,郴州市,嘉禾县', '嘉禾', '中国,湖南,郴州,嘉禾', '3', '0735', '424500', 'Jiahe', 'JH', 'J', '112.36935', '25.58795'),
('2128', '431025', '431000', '临武县', '中国,湖南省,郴州市,临武县', '临武', '中国,湖南,郴州,临武', '3', '0735', '424300', 'Linwu', 'LW', 'L', '112.56369', '25.27602'),
('2129', '431026', '431000', '汝城县', '中国,湖南省,郴州市,汝城县', '汝城', '中国,湖南,郴州,汝城', '3', '0735', '424100', 'Rucheng', 'RC', 'R', '113.68582', '25.55204'),
('2130', '431027', '431000', '桂东县', '中国,湖南省,郴州市,桂东县', '桂东', '中国,湖南,郴州,桂东', '3', '0735', '423500', 'Guidong', 'GD', 'G', '113.9468', '26.07987'),
('2131', '431028', '431000', '安仁县', '中国,湖南省,郴州市,安仁县', '安仁', '中国,湖南,郴州,安仁', '3', '0735', '423600', 'Anren', 'AR', 'A', '113.26944', '26.70931'),
('2132', '431081', '431000', '资兴市', '中国,湖南省,郴州市,资兴市', '资兴', '中国,湖南,郴州,资兴', '3', '0735', '423400', 'Zixing', 'ZX', 'Z', '113.23724', '25.97668'),
('2133', '431100', '430000', '永州市', '中国,湖南省,永州市', '永州', '中国,湖南,永州', '2', '0746', '425000', 'Yongzhou', 'YZ', 'Y', '111.608019', '26.434516'),
('2134', '431102', '431100', '零陵区', '中国,湖南省,永州市,零陵区', '零陵', '中国,湖南,永州,零陵', '3', '0746', '425000', 'Lingling', 'LL', 'L', '111.62103', '26.22109'),
('2135', '431103', '431100', '冷水滩区', '中国,湖南省,永州市,冷水滩区', '冷水滩', '中国,湖南,永州,冷水滩', '3', '0746', '425000', 'Lengshuitan', 'LST', 'L', '111.59214', '26.46107'),
('2136', '431121', '431100', '祁阳县', '中国,湖南省,永州市,祁阳县', '祁阳', '中国,湖南,永州,祁阳', '3', '0746', '426100', 'Qiyang', 'QY', 'Q', '111.84011', '26.58009'),
('2137', '431122', '431100', '东安县', '中国,湖南省,永州市,东安县', '东安', '中国,湖南,永州,东安', '3', '0746', '425900', 'Dong\'an', 'DA', 'D', '111.3164', '26.39202'),
('2138', '431123', '431100', '双牌县', '中国,湖南省,永州市,双牌县', '双牌', '中国,湖南,永州,双牌', '3', '0746', '425200', 'Shuangpai', 'SP', 'S', '111.65927', '25.95988'),
('2139', '431124', '431100', '道县', '中国,湖南省,永州市,道县', '道县', '中国,湖南,永州,道县', '3', '0746', '425300', 'Daoxian', 'DX', 'D', '111.60195', '25.52766'),
('2140', '431125', '431100', '江永县', '中国,湖南省,永州市,江永县', '江永', '中国,湖南,永州,江永', '3', '0746', '425400', 'Jiangyong', 'JY', 'J', '111.34082', '25.27233'),
('2141', '431126', '431100', '宁远县', '中国,湖南省,永州市,宁远县', '宁远', '中国,湖南,永州,宁远', '3', '0746', '425600', 'Ningyuan', 'NY', 'N', '111.94625', '25.56913'),
('2142', '431127', '431100', '蓝山县', '中国,湖南省,永州市,蓝山县', '蓝山', '中国,湖南,永州,蓝山', '3', '0746', '425800', 'Lanshan', 'LS', 'L', '112.19363', '25.36794'),
('2143', '431128', '431100', '新田县', '中国,湖南省,永州市,新田县', '新田', '中国,湖南,永州,新田', '3', '0746', '425700', 'Xintian', 'XT', 'X', '112.22103', '25.9095'),
('2144', '431129', '431100', '江华瑶族自治县', '中国,湖南省,永州市,江华瑶族自治县', '江华', '中国,湖南,永州,江华', '3', '0746', '425500', 'Jianghua', 'JH', 'J', '111.58847', '25.1845'),
('2145', '431200', '430000', '怀化市', '中国,湖南省,怀化市', '怀化', '中国,湖南,怀化', '2', '0745', '418000', 'Huaihua', 'HH', 'H', '109.97824', '27.550082'),
('2146', '431202', '431200', '鹤城区', '中国,湖南省,怀化市,鹤城区', '鹤城', '中国,湖南,怀化,鹤城', '3', '0745', '418000', 'Hecheng', 'HC', 'H', '109.96509', '27.54942'),
('2147', '431221', '431200', '中方县', '中国,湖南省,怀化市,中方县', '中方', '中国,湖南,怀化,中方', '3', '0745', '418000', 'Zhongfang', 'ZF', 'Z', '109.94497', '27.43988'),
('2148', '431222', '431200', '沅陵县', '中国,湖南省,怀化市,沅陵县', '沅陵', '中国,湖南,怀化,沅陵', '3', '0745', '419600', 'Yuanling', 'YL', 'Y', '110.39633', '28.45548'),
('2149', '431223', '431200', '辰溪县', '中国,湖南省,怀化市,辰溪县', '辰溪', '中国,湖南,怀化,辰溪', '3', '0745', '419500', 'Chenxi', 'CX', 'C', '110.18942', '28.00406'),
('2150', '431224', '431200', '溆浦县', '中国,湖南省,怀化市,溆浦县', '溆浦', '中国,湖南,怀化,溆浦', '3', '0745', '419300', 'Xupu', 'XP', 'X', '110.59384', '27.90836'),
('2151', '431225', '431200', '会同县', '中国,湖南省,怀化市,会同县', '会同', '中国,湖南,怀化,会同', '3', '0745', '418300', 'Huitong', 'HT', 'H', '109.73568', '26.88716'),
('2152', '431226', '431200', '麻阳苗族自治县', '中国,湖南省,怀化市,麻阳苗族自治县', '麻阳', '中国,湖南,怀化,麻阳', '3', '0745', '419400', 'Mayang', 'MY', 'M', '109.80194', '27.866'),
('2153', '431227', '431200', '新晃侗族自治县', '中国,湖南省,怀化市,新晃侗族自治县', '新晃', '中国,湖南,怀化,新晃', '3', '0745', '419200', 'Xinhuang', 'XH', 'X', '109.17166', '27.35937'),
('2154', '431228', '431200', '芷江侗族自治县', '中国,湖南省,怀化市,芷江侗族自治县', '芷江', '中国,湖南,怀化,芷江', '3', '0745', '419100', 'Zhijiang', 'ZJ', 'Z', '109.6849', '27.44297'),
('2155', '431229', '431200', '靖州苗族侗族自治县', '中国,湖南省,怀化市,靖州苗族侗族自治县', '靖州', '中国,湖南,怀化,靖州', '3', '0745', '418400', 'Jingzhou', 'JZ', 'J', '109.69821', '26.57651'),
('2156', '431230', '431200', '通道侗族自治县', '中国,湖南省,怀化市,通道侗族自治县', '通道', '中国,湖南,怀化,通道', '3', '0745', '418500', 'Tongdao', 'TD', 'T', '109.78515', '26.1571'),
('2157', '431281', '431200', '洪江市', '中国,湖南省,怀化市,洪江市', '洪江', '中国,湖南,怀化,洪江', '3', '0745', '418200', 'Hongjiang', 'HJ', 'H', '109.83651', '27.20922'),
('2158', '431300', '430000', '娄底市', '中国,湖南省,娄底市', '娄底', '中国,湖南,娄底', '2', '0738', '417000', 'Loudi', 'LD', 'L', '112.008497', '27.728136'),
('2159', '431302', '431300', '娄星区', '中国,湖南省,娄底市,娄星区', '娄星', '中国,湖南,娄底,娄星', '3', '0738', '417000', 'Louxing', 'LX', 'L', '112.00193', '27.72992'),
('2160', '431321', '431300', '双峰县', '中国,湖南省,娄底市,双峰县', '双峰', '中国,湖南,娄底,双峰', '3', '0738', '417700', 'Shuangfeng', 'SF', 'S', '112.19921', '27.45418'),
('2161', '431322', '431300', '新化县', '中国,湖南省,娄底市,新化县', '新化', '中国,湖南,娄底,新化', '3', '0738', '417600', 'Xinhua', 'XH', 'X', '111.32739', '27.7266'),
('2162', '431381', '431300', '冷水江市', '中国,湖南省,娄底市,冷水江市', '冷水江', '中国,湖南,娄底,冷水江', '3', '0738', '417500', 'Lengshuijiang', 'LSJ', 'L', '111.43554', '27.68147'),
('2163', '431382', '431300', '涟源市', '中国,湖南省,娄底市,涟源市', '涟源', '中国,湖南,娄底,涟源', '3', '0738', '417100', 'Lianyuan', 'LY', 'L', '111.67233', '27.68831'),
('2164', '433100', '430000', '湘西土家族苗族自治州', '中国,湖南省,湘西土家族苗族自治州', '湘西', '中国,湖南,湘西', '2', '0743', '416000', 'Xiangxi', 'XX', 'X', '109.739735', '28.314296'),
('2165', '433101', '433100', '吉首市', '中国,湖南省,湘西土家族苗族自治州,吉首市', '吉首', '中国,湖南,湘西,吉首', '3', '0743', '416000', 'Jishou', 'JS', 'J', '109.69799', '28.26247'),
('2166', '433122', '433100', '泸溪县', '中国,湖南省,湘西土家族苗族自治州,泸溪县', '泸溪', '中国,湖南,湘西,泸溪', '3', '0743', '416100', 'Luxi', 'LX', 'L', '110.21682', '28.2205'),
('2167', '433123', '433100', '凤凰县', '中国,湖南省,湘西土家族苗族自治州,凤凰县', '凤凰', '中国,湖南,湘西,凤凰', '3', '0743', '416200', 'Fenghuang', 'FH', 'F', '109.60156', '27.94822'),
('2168', '433124', '433100', '花垣县', '中国,湖南省,湘西土家族苗族自治州,花垣县', '花垣', '中国,湖南,湘西,花垣', '3', '0743', '416400', 'Huayuan', 'HY', 'H', '109.48217', '28.5721'),
('2169', '433125', '433100', '保靖县', '中国,湖南省,湘西土家族苗族自治州,保靖县', '保靖', '中国,湖南,湘西,保靖', '3', '0743', '416500', 'Baojing', 'BJ', 'B', '109.66049', '28.69997'),
('2170', '433126', '433100', '古丈县', '中国,湖南省,湘西土家族苗族自治州,古丈县', '古丈', '中国,湖南,湘西,古丈', '3', '0743', '416300', 'Guzhang', 'GZ', 'G', '109.94812', '28.61944'),
('2171', '433127', '433100', '永顺县', '中国,湖南省,湘西土家族苗族自治州,永顺县', '永顺', '中国,湖南,湘西,永顺', '3', '0743', '416700', 'Yongshun', 'YS', 'Y', '109.85266', '29.00103'),
('2172', '433130', '433100', '龙山县', '中国,湖南省,湘西土家族苗族自治州,龙山县', '龙山', '中国,湖南,湘西,龙山', '3', '0743', '416800', 'Longshan', 'LS', 'L', '109.4432', '29.45693'),
('2173', '440000', '100000', '广东省', '中国,广东省', '广东', '中国,广东', '1', '', '', 'Guangdong', 'GD', 'G', '113.280637', '23.125178'),
('2174', '440100', '440000', '广州市', '中国,广东省,广州市', '广州', '中国,广东,广州', '2', '020', '510000', 'Guangzhou', 'GZ', 'G', '113.280637', '23.125178'),
('2175', '440103', '440100', '荔湾区', '中国,广东省,广州市,荔湾区', '荔湾', '中国,广东,广州,荔湾', '3', '020', '510000', 'Liwan', 'LW', 'L', '113.2442', '23.12592'),
('2176', '440104', '440100', '越秀区', '中国,广东省,广州市,越秀区', '越秀', '中国,广东,广州,越秀', '3', '020', '510000', 'Yuexiu', 'YX', 'Y', '113.26683', '23.12897'),
('2177', '440105', '440100', '海珠区', '中国,广东省,广州市,海珠区', '海珠', '中国,广东,广州,海珠', '3', '020', '510000', 'Haizhu', 'HZ', 'H', '113.26197', '23.10379'),
('2178', '440106', '440100', '天河区', '中国,广东省,广州市,天河区', '天河', '中国,广东,广州,天河', '3', '020', '510000', 'Tianhe', 'TH', 'T', '113.36112', '23.12467'),
('2179', '440111', '440100', '白云区', '中国,广东省,广州市,白云区', '白云', '中国,广东,广州,白云', '3', '020', '510000', 'Baiyun', 'BY', 'B', '113.27307', '23.15787'),
('2180', '440112', '440100', '黄埔区', '中国,广东省,广州市,黄埔区', '黄埔', '中国,广东,广州,黄埔', '3', '020', '510700', 'Huangpu', 'HP', 'H', '113.45895', '23.10642'),
('2181', '440113', '440100', '番禺区', '中国,广东省,广州市,番禺区', '番禺', '中国,广东,广州,番禺', '3', '020', '511400', 'Panyu', 'PY', 'P', '113.38397', '22.93599'),
('2182', '440114', '440100', '花都区', '中国,广东省,广州市,花都区', '花都', '中国,广东,广州,花都', '3', '020', '510800', 'Huadu', 'HD', 'H', '113.22033', '23.40358'),
('2183', '440115', '440100', '南沙新区', '中国,广东省,广州市,南沙新区', '南沙', '中国,广东,广州,南沙', '3', '020', '511458', 'Nansha', 'NS', 'N', '113.60845', '22.77144'),
('2184', '440117', '440100', '从化区', '中国,广东省,广州市,从化区', '从化', '中国,广东,广州,从化', '3', '020', '510900', 'Conghua', 'CH', 'C', '113.587386', '23.545283'),
('2185', '440118', '440100', '增城区', '中国,广东省,广州市,增城区', '增城', '中国,广东,广州,增城', '3', '020', '511300', 'Zengcheng', 'ZC', 'Z', '113.829579', '23.290497'),
('2186', '440200', '440000', '韶关市', '中国,广东省,韶关市', '韶关', '中国,广东,韶关', '2', '0751', '512000', 'Shaoguan', 'SG', 'S', '113.591544', '24.801322'),
('2187', '440203', '440200', '武江区', '中国,广东省,韶关市,武江区', '武江', '中国,广东,韶关,武江', '3', '0751', '512000', 'Wujiang', 'WJ', 'W', '113.58767', '24.79264'),
('2188', '440204', '440200', '浈江区', '中国,广东省,韶关市,浈江区', '浈江', '中国,广东,韶关,浈江', '3', '0751', '512000', 'Zhenjiang', 'ZJ', 'Z', '113.61109', '24.80438'),
('2189', '440205', '440200', '曲江区', '中国,广东省,韶关市,曲江区', '曲江', '中国,广东,韶关,曲江', '3', '0751', '512100', 'Qujiang', 'QJ', 'Q', '113.60165', '24.67915'),
('2190', '440222', '440200', '始兴县', '中国,广东省,韶关市,始兴县', '始兴', '中国,广东,韶关,始兴', '3', '0751', '512500', 'Shixing', 'SX', 'S', '114.06799', '24.94759'),
('2191', '440224', '440200', '仁化县', '中国,广东省,韶关市,仁化县', '仁化', '中国,广东,韶关,仁化', '3', '0751', '512300', 'Renhua', 'RH', 'R', '113.74737', '25.08742'),
('2192', '440229', '440200', '翁源县', '中国,广东省,韶关市,翁源县', '翁源', '中国,广东,韶关,翁源', '3', '0751', '512600', 'Wengyuan', 'WY', 'W', '114.13385', '24.3495'),
('2193', '440232', '440200', '乳源瑶族自治县', '中国,广东省,韶关市,乳源瑶族自治县', '乳源', '中国,广东,韶关,乳源', '3', '0751', '512600', 'Ruyuan', 'RY', 'R', '113.27734', '24.77803'),
('2194', '440233', '440200', '新丰县', '中国,广东省,韶关市,新丰县', '新丰', '中国,广东,韶关,新丰', '3', '0751', '511100', 'Xinfeng', 'XF', 'X', '114.20788', '24.05924'),
('2195', '440281', '440200', '乐昌市', '中国,广东省,韶关市,乐昌市', '乐昌', '中国,广东,韶关,乐昌', '3', '0751', '512200', 'Lechang', 'LC', 'L', '113.35653', '25.12799'),
('2196', '440282', '440200', '南雄市', '中国,广东省,韶关市,南雄市', '南雄', '中国,广东,韶关,南雄', '3', '0751', '512400', 'Nanxiong', 'NX', 'N', '114.30966', '25.11706'),
('2197', '440300', '440000', '深圳市', '中国,广东省,深圳市', '深圳', '中国,广东,深圳', '2', '0755', '518000', 'Shenzhen', 'SZ', 'S', '114.085947', '22.547'),
('2198', '440303', '440300', '罗湖区', '中国,广东省,深圳市,罗湖区', '罗湖', '中国,广东,深圳,罗湖', '3', '0755', '518000', 'Luohu', 'LH', 'L', '114.13116', '22.54836'),
('2199', '440304', '440300', '福田区', '中国,广东省,深圳市,福田区', '福田', '中国,广东,深圳,福田', '3', '0755', '518000', 'Futian', 'FT', 'F', '114.05571', '22.52245'),
('2200', '440305', '440300', '南山区', '中国,广东省,深圳市,南山区', '南山', '中国,广东,深圳,南山', '3', '0755', '518000', 'Nanshan', 'NS', 'N', '113.93029', '22.53291'),
('2201', '440306', '440300', '宝安区', '中国,广东省,深圳市,宝安区', '宝安', '中国,广东,深圳,宝安', '3', '0755', '518100', 'Bao\'an', 'BA', 'B', '113.88311', '22.55371'),
('2202', '440307', '440300', '龙岗区', '中国,广东省,深圳市,龙岗区', '龙岗', '中国,广东,深圳,龙岗', '3', '0755', '518116', 'Longgang', 'LG', 'L', '114.24771', '22.71986'),
('2203', '440308', '440300', '盐田区', '中国,广东省,深圳市,盐田区', '盐田', '中国,广东,深圳,盐田', '3', '0755', '518000', 'Yantian', 'YT', 'Y', '114.23733', '22.5578'),
('2204', '440309', '440300', '龙华区', '中国,广东省,深圳市,龙华区', '龙华', '中国,广东,深圳,龙华', '3', '0755', '518109', 'Longhua', 'LH', 'L', '114.036585', '22.68695'),
('2205', '440310', '440300', '坪山区', '中国,广东省,深圳市,坪山区', '坪山', '中国,广东,深圳,坪山', '3', '0755', '518118', 'Pingshan', 'PS', 'P', '114.34637', '22.690529'),
('2206', '440311', '440300', '光明新区', '中国,广东省,深圳市,光明新区', '光明新区', '中国,广东,深圳,光明新区', '3', '0755', '518107', 'Guangmingxinqu', 'GMXQ', 'G', '113.896026', '22.777292'),
('2207', '440312', '440300', '大鹏新区', '中国,广东省,深圳市,大鹏新区', '大鹏新区', '中国,广东,深圳,大鹏新区', '3', '0755', '518116', 'Dapengxinqu', 'DPXQ', 'D', '114.479901', '22.587862'),
('2208', '440400', '440000', '珠海市', '中国,广东省,珠海市', '珠海', '中国,广东,珠海', '2', '0756', '519000', 'Zhuhai', 'ZH', 'Z', '113.552724', '22.255899'),
('2209', '440402', '440400', '香洲区', '中国,广东省,珠海市,香洲区', '香洲', '中国,广东,珠海,香洲', '3', '0756', '519000', 'Xiangzhou', 'XZ', 'X', '113.5435', '22.26654'),
('2210', '440403', '440400', '斗门区', '中国,广东省,珠海市,斗门区', '斗门', '中国,广东,珠海,斗门', '3', '0756', '519100', 'Doumen', 'DM', 'D', '113.29644', '22.20898'),
('2211', '440404', '440400', '金湾区', '中国,广东省,珠海市,金湾区', '金湾', '中国,广东,珠海,金湾', '3', '0756', '519090', 'Jinwan', 'JW', 'J', '113.36361', '22.14691'),
('2212', '440405', '440400', '横琴新区', '中国,广东省,珠海市,横琴新区', '横琴新区', '中国,广东,珠海,横琴新区', '3', '0756', '519000', 'Hengqinxinqu', 'HQXQ', 'H', '113.531427', '22.095899'),
('2213', '440406', '440400', '经济开发区', '中国,广东省,珠海市,经济开发区', '经济开发区', '中国,广东,珠海,经济开发区', '3', '0756', '519000', 'Jingjikaifaqu', 'JJKFQ', 'J', '113.223872', '21.915144'),
('2214', '440500', '440000', '汕头市', '中国,广东省,汕头市', '汕头', '中国,广东,汕头', '2', '0754', '515000', 'Shantou', 'ST', 'S', '116.708463', '23.37102'),
('2215', '440507', '440500', '龙湖区', '中国,广东省,汕头市,龙湖区', '龙湖', '中国,广东,汕头,龙湖', '3', '0754', '515000', 'Longhu', 'LH', 'L', '116.71641', '23.37166'),
('2216', '440511', '440500', '金平区', '中国,广东省,汕头市,金平区', '金平', '中国,广东,汕头,金平', '3', '0754', '515000', 'Jinping', 'JP', 'J', '116.70364', '23.36637'),
('2217', '440512', '440500', '濠江区', '中国,广东省,汕头市,濠江区', '濠江', '中国,广东,汕头,濠江', '3', '0754', '515000', 'Haojiang', 'HJ', 'H', '116.72659', '23.28588'),
('2218', '440513', '440500', '潮阳区', '中国,广东省,汕头市,潮阳区', '潮阳', '中国,广东,汕头,潮阳', '3', '0754', '515100', 'Chaoyang', 'CY', 'C', '116.6015', '23.26485'),
('2219', '440514', '440500', '潮南区', '中国,广东省,汕头市,潮南区', '潮南', '中国,广东,汕头,潮南', '3', '0754', '515100', 'Chaonan', 'CN', 'C', '116.43188', '23.25'),
('2220', '440515', '440500', '澄海区', '中国,广东省,汕头市,澄海区', '澄海', '中国,广东,汕头,澄海', '3', '0754', '515800', 'Chenghai', 'CH', 'C', '116.75589', '23.46728'),
('2221', '440523', '440500', '南澳县', '中国,广东省,汕头市,南澳县', '南澳', '中国,广东,汕头,南澳', '3', '0754', '515900', 'Nanao', 'NA', 'N', '117.01889', '23.4223'),
('2222', '440600', '440000', '佛山市', '中国,广东省,佛山市', '佛山', '中国,广东,佛山', '2', '0757', '528000', 'Foshan', 'FS', 'F', '113.122717', '23.028762'),
('2223', '440604', '440600', '禅城区', '中国,广东省,佛山市,禅城区', '禅城', '中国,广东,佛山,禅城', '3', '0757', '528000', 'Chancheng', 'CC', 'C', '113.1228', '23.00842'),
('2224', '440605', '440600', '南海区', '中国,广东省,佛山市,南海区', '南海', '中国,广东,佛山,南海', '3', '0757', '528200', 'Nanhai', 'NH', 'N', '113.14299', '23.02877'),
('2225', '440606', '440600', '顺德区', '中国,广东省,佛山市,顺德区', '顺德', '中国,广东,佛山,顺德', '3', '0757', '528300', 'Shunde', 'SD', 'S', '113.29394', '22.80452'),
('2226', '440607', '440600', '三水区', '中国,广东省,佛山市,三水区', '三水', '中国,广东,佛山,三水', '3', '0757', '528100', 'Sanshui', 'SS', 'S', '112.89703', '23.15564'),
('2227', '440608', '440600', '高明区', '中国,广东省,佛山市,高明区', '高明', '中国,广东,佛山,高明', '3', '0757', '528500', 'Gaoming', 'GM', 'G', '112.89254', '22.90022'),
('2228', '440700', '440000', '江门市', '中国,广东省,江门市', '江门', '中国,广东,江门', '2', '0750', '529000', 'Jiangmen', 'JM', 'J', '113.094942', '22.590431'),
('2229', '440703', '440700', '蓬江区', '中国,广东省,江门市,蓬江区', '蓬江', '中国,广东,江门,蓬江', '3', '0750', '529000', 'Pengjiang', 'PJ', 'P', '113.07849', '22.59515'),
('2230', '440704', '440700', '江海区', '中国,广东省,江门市,江海区', '江海', '中国,广东,江门,江海', '3', '0750', '529000', 'Jianghai', 'JH', 'J', '113.11099', '22.56024'),
('2231', '440705', '440700', '新会区', '中国,广东省,江门市,新会区', '新会', '中国,广东,江门,新会', '3', '0750', '529100', 'Xinhui', 'XH', 'X', '113.03225', '22.45876'),
('2232', '440781', '440700', '台山市', '中国,广东省,江门市,台山市', '台山', '中国,广东,江门,台山', '3', '0750', '529200', 'Taishan', 'TS', 'T', '112.79382', '22.2515'),
('2233', '440783', '440700', '开平市', '中国,广东省,江门市,开平市', '开平', '中国,广东,江门,开平', '3', '0750', '529300', 'Kaiping', 'KP', 'K', '112.69842', '22.37622'),
('2234', '440784', '440700', '鹤山市', '中国,广东省,江门市,鹤山市', '鹤山', '中国,广东,江门,鹤山', '3', '0750', '529700', 'Heshan', 'HS', 'H', '112.96429', '22.76523'),
('2235', '440785', '440700', '恩平市', '中国,广东省,江门市,恩平市', '恩平', '中国,广东,江门,恩平', '3', '0750', '529400', 'Enping', 'EP', 'E', '112.30496', '22.18288'),
('2236', '440800', '440000', '湛江市', '中国,广东省,湛江市', '湛江', '中国,广东,湛江', '2', '0759', '524000', 'Zhanjiang', 'ZJ', 'Z', '110.405529', '21.195338'),
('2237', '440802', '440800', '赤坎区', '中国,广东省,湛江市,赤坎区', '赤坎', '中国,广东,湛江,赤坎', '3', '0759', '524000', 'Chikan', 'CK', 'C', '110.36592', '21.26606'),
('2238', '440803', '440800', '霞山区', '中国,广东省,湛江市,霞山区', '霞山', '中国,广东,湛江,霞山', '3', '0759', '524000', 'Xiashan', 'XS', 'X', '110.39822', '21.19181'),
('2239', '440804', '440800', '坡头区', '中国,广东省,湛江市,坡头区', '坡头', '中国,广东,湛江,坡头', '3', '0759', '524000', 'Potou', 'PT', 'P', '110.45533', '21.24472'),
('2240', '440811', '440800', '麻章区', '中国,广东省,湛江市,麻章区', '麻章', '中国,广东,湛江,麻章', '3', '0759', '524000', 'Mazhang', 'MZ', 'M', '110.3342', '21.26333'),
('2241', '440823', '440800', '遂溪县', '中国,广东省,湛江市,遂溪县', '遂溪', '中国,广东,湛江,遂溪', '3', '0759', '524300', 'Suixi', 'SX', 'S', '110.25003', '21.37721'),
('2242', '440825', '440800', '徐闻县', '中国,广东省,湛江市,徐闻县', '徐闻', '中国,广东,湛江,徐闻', '3', '0759', '524100', 'Xuwen', 'XW', 'X', '110.17379', '20.32812'),
('2243', '440881', '440800', '廉江市', '中国,广东省,湛江市,廉江市', '廉江', '中国,广东,湛江,廉江', '3', '0759', '524400', 'Lianjiang', 'LJ', 'L', '110.28442', '21.60917'),
('2244', '440882', '440800', '雷州市', '中国,广东省,湛江市,雷州市', '雷州', '中国,广东,湛江,雷州', '3', '0759', '524200', 'Leizhou', 'LZ', 'L', '110.10092', '20.91428'),
('2245', '440883', '440800', '吴川市', '中国,广东省,湛江市,吴川市', '吴川', '中国,广东,湛江,吴川', '3', '0759', '524500', 'Wuchuan', 'WC', 'W', '110.77703', '21.44584'),
('2246', '440884', '440800', '经济开发区', '中国,广东省,湛江市,经济开发区', '经济开发区', '中国,广东,湛江,经济开发区', '3', '0759', '524001', 'Jingjikaifaqu', 'JJKFQ', 'J', '110.325459', '21.012065'),
('2247', '440900', '440000', '茂名市', '中国,广东省,茂名市', '茂名', '中国,广东,茂名', '2', '0668', '525000', 'Maoming', 'MM', 'M', '110.919229', '21.659751'),
('2248', '440902', '440900', '茂南区', '中国,广东省,茂名市,茂南区', '茂南', '中国,广东,茂名,茂南', '3', '0668', '525000', 'Maonan', 'MN', 'M', '110.9187', '21.64103'),
('2249', '440904', '440900', '电白区', '中国,广东省,茂名市,电白区', '电白', '中国,广东,茂名,电白', '3', '0668', '525400', 'Dianbai', 'DB', 'D', '111.007264', '21.507219'),
('2250', '440981', '440900', '高州市', '中国,广东省,茂名市,高州市', '高州', '中国,广东,茂名,高州', '3', '0668', '525200', 'Gaozhou', 'GZ', 'G', '110.85519', '21.92057'),
('2251', '440982', '440900', '化州市', '中国,广东省,茂名市,化州市', '化州', '中国,广东,茂名,化州', '3', '0668', '525100', 'Huazhou', 'HZ', 'H', '110.63949', '21.66394'),
('2252', '440983', '440900', '信宜市', '中国,广东省,茂名市,信宜市', '信宜', '中国,广东,茂名,信宜', '3', '0668', '525300', 'Xinyi', 'XY', 'X', '110.94647', '22.35351'),
('2253', '441200', '440000', '肇庆市', '中国,广东省,肇庆市', '肇庆', '中国,广东,肇庆', '2', '0758', '526000', 'Zhaoqing', 'ZQ', 'Z', '112.472529', '23.051546'),
('2254', '441202', '441200', '端州区', '中国,广东省,肇庆市,端州区', '端州', '中国,广东,肇庆,端州', '3', '0758', '526000', 'Duanzhou', 'DZ', 'D', '112.48495', '23.0519'),
('2255', '441203', '441200', '鼎湖区', '中国,广东省,肇庆市,鼎湖区', '鼎湖', '中国,广东,肇庆,鼎湖', '3', '0758', '526000', 'Dinghu', 'DH', 'D', '112.56643', '23.15846'),
('2256', '441204', '441200', '高要区', '中国,广东省,肇庆市,高要区', '高要', '中国,广东,肇庆,高要', '3', '0758', '526100', 'Gaoyao', 'GY', 'G', '112.45834', '23.02577'),
('2257', '441223', '441200', '广宁县', '中国,广东省,肇庆市,广宁县', '广宁', '中国,广东,肇庆,广宁', '3', '0758', '526300', 'Guangning', 'GN', 'G', '112.44064', '23.6346'),
('2258', '441224', '441200', '怀集县', '中国,广东省,肇庆市,怀集县', '怀集', '中国,广东,肇庆,怀集', '3', '0758', '526400', 'Huaiji', 'HJ', 'H', '112.18396', '23.90918'),
('2259', '441225', '441200', '封开县', '中国,广东省,肇庆市,封开县', '封开', '中国,广东,肇庆,封开', '3', '0758', '526500', 'Fengkai', 'FK', 'F', '111.50332', '23.43571'),
('2260', '441226', '441200', '德庆县', '中国,广东省,肇庆市,德庆县', '德庆', '中国,广东,肇庆,德庆', '3', '0758', '526600', 'Deqing', 'DQ', 'D', '111.78555', '23.14371'),
('2261', '441284', '441200', '四会市', '中国,广东省,肇庆市,四会市', '四会', '中国,广东,肇庆,四会', '3', '0758', '526200', 'Sihui', 'SH', 'S', '112.73416', '23.32686'),
('2262', '441300', '440000', '惠州市', '中国,广东省,惠州市', '惠州', '中国,广东,惠州', '2', '0752', '516000', 'Huizhou', 'HZ', 'H', '114.412599', '23.079404'),
('2263', '441302', '441300', '惠城区', '中国,广东省,惠州市,惠城区', '惠城', '中国,广东,惠州,惠城', '3', '0752', '516000', 'Huicheng', 'HC', 'H', '114.3828', '23.08377'),
('2264', '441303', '441300', '惠阳区', '中国,广东省,惠州市,惠阳区', '惠阳', '中国,广东,惠州,惠阳', '3', '0752', '516200', 'Huiyang', 'HY', 'H', '114.45639', '22.78845'),
('2265', '441322', '441300', '博罗县', '中国,广东省,惠州市,博罗县', '博罗', '中国,广东,惠州,博罗', '3', '0752', '516100', 'Boluo', 'BL', 'B', '114.28964', '23.17307'),
('2266', '441323', '441300', '惠东县', '中国,广东省,惠州市,惠东县', '惠东', '中国,广东,惠州,惠东', '3', '0752', '516300', 'Huidong', 'HD', 'H', '114.72009', '22.98484'),
('2267', '441324', '441300', '龙门县', '中国,广东省,惠州市,龙门县', '龙门', '中国,广东,惠州,龙门', '3', '0752', '516800', 'Longmen', 'LM', 'L', '114.25479', '23.72758'),
('2268', '441325', '441300', '大亚湾区', '中国,广东省,惠州市,大亚湾区', '大亚湾', '中国,广东,惠州,大亚湾', '3', '0752', '516000', 'Dayawan', 'DYW', 'D', '114.537616', '22.739381'),
('2269', '441400', '440000', '梅州市', '中国,广东省,梅州市', '梅州', '中国,广东,梅州', '2', '0753', '514000', 'Meizhou', 'MZ', 'M', '116.117582', '24.299112'),
('2270', '441402', '441400', '梅江区', '中国,广东省,梅州市,梅江区', '梅江', '中国,广东,梅州,梅江', '3', '0753', '514000', 'Meijiang', 'MJ', 'M', '116.11663', '24.31062'),
('2271', '441403', '441400', '梅县区', '中国,广东省,梅州市,梅县区', '梅县', '中国,广东,梅州,梅县', '3', '0753', '514787', 'Meixian', 'MX', 'M', '116.097753', '24.286739'),
('2272', '441422', '441400', '大埔县', '中国,广东省,梅州市,大埔县', '大埔', '中国,广东,梅州,大埔', '3', '0753', '514200', 'Dabu', 'DB', 'D', '116.69662', '24.35325'),
('2273', '441423', '441400', '丰顺县', '中国,广东省,梅州市,丰顺县', '丰顺', '中国,广东,梅州,丰顺', '3', '0753', '514300', 'Fengshun', 'FS', 'F', '116.18219', '23.74094'),
('2274', '441424', '441400', '五华县', '中国,广东省,梅州市,五华县', '五华', '中国,广东,梅州,五华', '3', '0753', '514400', 'Wuhua', 'WH', 'W', '115.77893', '23.92417'),
('2275', '441426', '441400', '平远县', '中国,广东省,梅州市,平远县', '平远', '中国,广东,梅州,平远', '3', '0753', '514600', 'Pingyuan', 'PY', 'P', '115.89556', '24.57116'),
('2276', '441427', '441400', '蕉岭县', '中国,广东省,梅州市,蕉岭县', '蕉岭', '中国,广东,梅州,蕉岭', '3', '0753', '514100', 'Jiaoling', 'JL', 'J', '116.17089', '24.65732'),
('2277', '441481', '441400', '兴宁市', '中国,广东省,梅州市,兴宁市', '兴宁', '中国,广东,梅州,兴宁', '3', '0753', '514500', 'Xingning', 'XN', 'X', '115.73141', '24.14001'),
('2278', '441500', '440000', '汕尾市', '中国,广东省,汕尾市', '汕尾', '中国,广东,汕尾', '2', '0660', '516600', 'Shanwei', 'SW', 'S', '115.364238', '22.774485'),
('2279', '441502', '441500', '城区', '中国,广东省,汕尾市,城区', '城区', '中国,广东,汕尾,城区', '3', '0660', '516600', 'Chengqu', 'CQ', 'C', '115.36503', '22.7789'),
('2280', '441521', '441500', '海丰县', '中国,广东省,汕尾市,海丰县', '海丰', '中国,广东,汕尾,海丰', '3', '0660', '516400', 'Haifeng', 'HF', 'H', '115.32336', '22.96653'),
('2281', '441523', '441500', '陆河县', '中国,广东省,汕尾市,陆河县', '陆河', '中国,广东,汕尾,陆河', '3', '0660', '516700', 'Luhe', 'LH', 'L', '115.65597', '23.30365'),
('2282', '441581', '441500', '陆丰市', '中国,广东省,汕尾市,陆丰市', '陆丰', '中国,广东,汕尾,陆丰', '3', '0660', '516500', 'Lufeng', 'LF', 'L', '115.64813', '22.94335'),
('2283', '441600', '440000', '河源市', '中国,广东省,河源市', '河源', '中国,广东,河源', '2', '0762', '517000', 'Heyuan', 'HY', 'H', '114.697802', '23.746266'),
('2284', '441602', '441600', '源城区', '中国,广东省,河源市,源城区', '源城', '中国,广东,河源,源城', '3', '0762', '517000', 'Yuancheng', 'YC', 'Y', '114.70242', '23.7341'),
('2285', '441621', '441600', '紫金县', '中国,广东省,河源市,紫金县', '紫金', '中国,广东,河源,紫金', '3', '0762', '517400', 'Zijin', 'ZJ', 'Z', '115.18365', '23.63867'),
('2286', '441622', '441600', '龙川县', '中国,广东省,河源市,龙川县', '龙川', '中国,广东,河源,龙川', '3', '0762', '517300', 'Longchuan', 'LC', 'L', '115.26025', '24.10142'),
('2287', '441623', '441600', '连平县', '中国,广东省,河源市,连平县', '连平', '中国,广东,河源,连平', '3', '0762', '517100', 'Lianping', 'LP', 'L', '114.49026', '24.37156'),
('2288', '441624', '441600', '和平县', '中国,广东省,河源市,和平县', '和平', '中国,广东,河源,和平', '3', '0762', '517200', 'Heping', 'HP', 'H', '114.93841', '24.44319'),
('2289', '441625', '441600', '东源县', '中国,广东省,河源市,东源县', '东源', '中国,广东,河源,东源', '3', '0762', '517500', 'Dongyuan', 'DY', 'D', '114.74633', '23.78835'),
('2290', '441700', '440000', '阳江市', '中国,广东省,阳江市', '阳江', '中国,广东,阳江', '2', '0662', '529500', 'Yangjiang', 'YJ', 'Y', '111.975107', '21.859222'),
('2291', '441702', '441700', '江城区', '中国,广东省,阳江市,江城区', '江城', '中国,广东,阳江,江城', '3', '0662', '529500', 'Jiangcheng', 'JC', 'J', '111.95488', '21.86193'),
('2292', '441704', '441700', '阳东区', '中国,广东省,阳江市,阳东区', '阳东', '中国,广东,阳江,阳东', '3', '0662', '529900', 'Yangdong', 'YD', 'Y', '112.01467', '21.87398'),
('2293', '441721', '441700', '阳西县', '中国,广东省,阳江市,阳西县', '阳西', '中国,广东,阳江,阳西', '3', '0662', '529800', 'Yangxi', 'YX', 'Y', '111.61785', '21.75234'),
('2294', '441781', '441700', '阳春市', '中国,广东省,阳江市,阳春市', '阳春', '中国,广东,阳江,阳春', '3', '0662', '529600', 'Yangchun', 'YC', 'Y', '111.78854', '22.17232'),
('2295', '441800', '440000', '清远市', '中国,广东省,清远市', '清远', '中国,广东,清远', '2', '0763', '511500', 'Qingyuan', 'QY', 'Q', '113.036779', '23.704188'),
('2296', '441802', '441800', '清城区', '中国,广东省,清远市,清城区', '清城', '中国,广东,清远,清城', '3', '0763', '511500', 'Qingcheng', 'QC', 'Q', '113.06265', '23.69784'),
('2297', '441803', '441800', '清新区', '中国,广东省,清远市,清新区', '清新', '中国,广东,清远,清新', '3', '0763', '511810', 'Qingxin', 'QX', 'Q', '113.015203', '23.736949'),
('2298', '441821', '441800', '佛冈县', '中国,广东省,清远市,佛冈县', '佛冈', '中国,广东,清远,佛冈', '3', '0763', '511600', 'Fogang', 'FG', 'F', '113.53286', '23.87231'),
('2299', '441823', '441800', '阳山县', '中国,广东省,清远市,阳山县', '阳山', '中国,广东,清远,阳山', '3', '0763', '513100', 'Yangshan', 'YS', 'Y', '112.64129', '24.46516'),
('2300', '441825', '441800', '连山壮族瑶族自治县', '中国,广东省,清远市,连山壮族瑶族自治县', '连山', '中国,广东,清远,连山', '3', '0763', '513200', 'Lianshan', 'LS', 'L', '112.0802', '24.56807'),
('2301', '441826', '441800', '连南瑶族自治县', '中国,广东省,清远市,连南瑶族自治县', '连南', '中国,广东,清远,连南', '3', '0763', '513300', 'Liannan', 'LN', 'L', '112.28842', '24.71726'),
('2302', '441881', '441800', '英德市', '中国,广东省,清远市,英德市', '英德', '中国,广东,清远,英德', '3', '0763', '513000', 'Yingde', 'YD', 'Y', '113.415', '24.18571'),
('2303', '441882', '441800', '连州市', '中国,广东省,清远市,连州市', '连州', '中国,广东,清远,连州', '3', '0763', '513400', 'Lianzhou', 'LZ', 'L', '112.38153', '24.77913'),
('2304', '441900', '440000', '东莞市', '中国,广东省,东莞市', '东莞', '中国,广东,东莞', '2', '0769', '523000', 'Dongguan', 'DG', 'D', '113.760234', '23.048884'),
('2305', '441901', '441900', '莞城区', '中国,广东省,东莞市,莞城区', '莞城', '中国,广东,东莞,莞城', '3', '0769', '523128', 'Guancheng', 'GC', 'G', '113.751043', '23.053412'),
('2306', '441902', '441900', '南城区', '中国,广东省,东莞市,南城区', '南城', '中国,广东,东莞,南城', '3', '0769', '523617', 'Nancheng', 'NC', 'N', '113.752125', '23.02018'),
('2307', '441903', '441900', '东城区', '中国,广东省,东莞市,东城区', '东城', '中国,广东,东莞,东城', '3', '0769', '402560', 'Dongcheng', 'DC', 'D', '113.772563', '22.981492'),
('2308', '441904', '441900', '万江区', '中国,广东省,东莞市,万江区', '万江', '中国,广东,东莞,万江', '3', '0769', '523039', 'Wanjiang', 'WJ', 'W', '113.739053', '23.043842'),
('2309', '441905', '441900', '石碣镇', '中国,广东省,东莞市,石碣镇', '石碣', '中国,广东,东莞,石碣', '3', '0769', '523290', 'Shijie', 'SJ', 'S', '113.80217', '23.09899'),
('2310', '441906', '441900', '石龙镇', '中国,广东省,东莞市,石龙镇', '石龙', '中国,广东,东莞,石龙', '3', '0769', '523326', 'Shilong', 'SL', 'S', '113.876381', '23.107444'),
('2311', '441907', '441900', '茶山镇', '中国,广东省,东莞市,茶山镇', '茶山', '中国,广东,东莞,茶山', '3', '0769', '523380', 'Chashan', 'CS', 'C', '113.883526', '23.062375'),
('2312', '441908', '441900', '石排镇', '中国,广东省,东莞市,石排镇', '石排', '中国,广东,东莞,石排', '3', '0769', '523346', 'Shipai', 'SP', 'S', '113.919859', '23.0863'),
('2313', '441909', '441900', '企石镇', '中国,广东省,东莞市,企石镇', '企石', '中国,广东,东莞,企石', '3', '0769', '523507', 'Qishi', 'QS', 'Q', '114.013233', '23.066044'),
('2314', '441910', '441900', '横沥镇', '中国,广东省,东莞市,横沥镇', '横沥', '中国,广东,东莞,横沥', '3', '0769', '523471', 'Hengli', 'HL', 'H', '113.957436', '23.025732'),
('2315', '441911', '441900', '桥头镇', '中国,广东省,东莞市,桥头镇', '桥头', '中国,广东,东莞,桥头', '3', '0769', '523520', 'Qiaotou', 'QT', 'Q', '114.01385', '22.939727'),
('2316', '441912', '441900', '谢岗镇', '中国,广东省,东莞市,谢岗镇', '谢岗', '中国,广东,东莞,谢岗', '3', '0769', '523592', 'Xiegang', 'XG', 'X', '114.141396', '22.959664'),
('2317', '441913', '441900', '东坑镇', '中国,广东省,东莞市,东坑镇', '东坑', '中国,广东,东莞,东坑', '3', '0769', '523451', 'Dongkeng', 'DK', 'D', '113.939835', '22.992804'),
('2318', '441914', '441900', '常平镇', '中国,广东省,东莞市,常平镇', '常平', '中国,广东,东莞,常平', '3', '0769', '523560', 'Changping', 'CP', 'C', '114.029627', '23.016116'),
('2319', '441915', '441900', '寮步镇', '中国,广东省,东莞市,寮步镇', '寮步', '中国,广东,东莞,寮步', '3', '0769', '523411', 'Liaobu', 'LB', 'L', '113.884745', '22.991738'),
('2320', '441916', '441900', '大朗镇', '中国,广东省,东莞市,大朗镇', '大朗', '中国,广东,东莞,大朗', '3', '0769', '523770', 'Dalang', 'DL', 'D', '113.9271', '22.965748'),
('2321', '441917', '441900', '麻涌镇', '中国,广东省,东莞市,麻涌镇', '麻涌', '中国,广东,东莞,麻涌', '3', '0769', '523143', 'Machong', 'MC', 'M', '113.546177', '23.045315'),
('2322', '441918', '441900', '中堂镇', '中国,广东省,东莞市,中堂镇', '中堂', '中国,广东,东莞,中堂', '3', '0769', '523233', 'Zhongtang', 'ZT', 'Z', '113.654422', '23.090164'),
('2323', '441919', '441900', '高埗镇', '中国,广东省,东莞市,高埗镇', '高埗', '中国,广东,东莞,高埗', '3', '0769', '523282', 'Gaobu', 'GB', 'G', '113.735917', '23.068415'),
('2324', '441920', '441900', '樟木头镇', '中国,广东省,东莞市,樟木头镇', '樟木头', '中国,广东,东莞,樟木头', '3', '0769', '523619', 'Zhangmutou', 'ZMT', 'Z', '114.066298', '22.956682'),
('2325', '441921', '441900', '大岭山镇', '中国,广东省,东莞市,大岭山镇', '大岭山', '中国,广东,东莞,大岭山', '3', '0769', '523835', 'Dalingshan', 'DLS', 'D', '113.782955', '22.885366'),
('2326', '441922', '441900', '望牛墩镇', '中国,广东省,东莞市,望牛墩镇', '望牛墩', '中国,广东,东莞,望牛墩', '3', '0769', '523203', 'Wangniudun', 'WND', 'W', '113.658847', '23.055018'),
('2327', '441923', '441900', '黄江镇', '中国,广东省,东莞市,黄江镇', '黄江', '中国,广东,东莞,黄江', '3', '0769', '523755', 'Huangjiang', 'HJ', 'H', '113.992635', '22.877536'),
('2328', '441924', '441900', '洪梅镇', '中国,广东省,东莞市,洪梅镇', '洪梅', '中国,广东,东莞,洪梅', '3', '0769', '523163', 'Hongmei', 'HM', 'H', '113.613081', '22.992675'),
('2329', '441925', '441900', '清溪镇', '中国,广东省,东莞市,清溪镇', '清溪', '中国,广东,东莞,清溪', '3', '0769', '523660', 'Qingxi', 'QX', 'Q', '114.155796', '22.844456'),
('2330', '441926', '441900', '沙田镇', '中国,广东省,东莞市,沙田镇', '沙田', '中国,广东,东莞,沙田', '3', '0769', '523988', 'Shatian', 'ST', 'S', '113.760234', '23.048884'),
('2331', '441927', '441900', '道滘镇', '中国,广东省,东莞市,道滘镇', '道滘', '中国,广东,东莞,道滘', '3', '0769', '523171', 'Daojiao', 'DJ', 'D', '113.760234', '23.048884'),
('2332', '441928', '441900', '塘厦镇', '中国,广东省,东莞市,塘厦镇', '塘厦', '中国,广东,东莞,塘厦', '3', '0769', '523713', 'Tangxia', 'TX', 'T', '114.10765', '22.822862'),
('2333', '441929', '441900', '虎门镇', '中国,广东省,东莞市,虎门镇', '虎门', '中国,广东,东莞,虎门', '3', '0769', '523932', 'Humen', 'HM', 'H', '113.71118', '22.82615'),
('2334', '441930', '441900', '厚街镇', '中国,广东省,东莞市,厚街镇', '厚街', '中国,广东,东莞,厚街', '3', '0769', '523960', 'Houjie', 'HJ', 'H', '113.67301', '22.940815'),
('2335', '441931', '441900', '凤岗镇', '中国,广东省,东莞市,凤岗镇', '凤岗', '中国,广东,东莞,凤岗', '3', '0769', '523690', 'Fenggang', 'FG', 'F', '114.141194', '22.744598'),
('2336', '441932', '441900', '长安镇', '中国,广东省,东莞市,长安镇', '长安', '中国,广东,东莞,长安', '3', '0769', '523850', 'Chang\'an', 'CA', 'C', '113.803939', '22.816644'),
('2337', '441933', '441900', '松山湖高新区', '中国,广东省,东莞市,松山湖高新区', '松山湖高新区', '中国,广东,东莞,松山湖高新区', '3', '0769', '523808', 'Songshanhu', 'SSH', 'S', '113.882849', '22.927549'),
('2338', '442000', '440000', '中山市', '中国,广东省,中山市', '中山', '中国,广东,中山', '2', '0760', '528400', 'Zhongshan', 'ZS', 'Z', '113.382391', '22.521113'),
('2339', '442001', '442000', '石岐区', '中国,广东省,中山市,石岐区', '石岐', '中国,广东,中山,石岐', '3', '0760', '528400', 'Shiqi', 'SQ', 'S', '113.378835', '22.52522'),
('2340', '442002', '442000', '东区', '中国,广东省,中山市,东区', '东区', '中国,广东,中山,东区', '3', '0760', '528403', 'Dongqu', 'DQ', 'D', '113.416716', '22.509903'),
('2341', '442003', '442000', '西区', '中国,广东省,中山市,西区', '东区', '中国,广东,中山,西区', '3', '0760', '528400', 'Xiqu', 'XQ', 'X', '113.342281', '22.553845'),
('2342', '442004', '442000', '南区', '中国,广东省,中山市,南区', '南区', '中国,广东,中山,南区', '3', '0760', '528400', 'Nanqu', 'NQ', 'N', '113.355896', '22.486568'),
('2343', '442005', '442000', '五桂山区', '中国,广东省,中山市,五桂山区', '五桂山', '中国,广东,中山,五桂山', '3', '0760', '528458', 'Wuguishan', 'WGS', 'W', '113.41079', '22.51968'),
('2344', '442006', '442000', '火炬开发区', '中国,广东省,中山市,火炬开发区', '火炬', '中国,广东,中山,火炬', '3', '0760', '528437', 'Huoju', 'HJ', 'H', '113.480523', '22.566082'),
('2345', '442007', '442000', '黄圃镇', '中国,广东省,中山市,黄圃镇', '黄圃', '中国,广东,中山,黄圃', '3', '0760', '528429', 'Huangpu', 'HP', 'H', '113.342359', '22.715116'),
('2346', '442008', '442000', '南头镇', '中国,广东省,中山市,南头镇', '南头', '中国,广东,中山,南头', '3', '0760', '528421', 'Nantou', 'NT', 'N', '113.296358', '22.713907'),
('2347', '442009', '442000', '东凤镇', '中国,广东省,中山市,东凤镇', '东凤', '中国,广东,中山,东凤', '3', '0760', '528425', 'Dongfeng', 'DF', 'D', '113.26114', '22.68775'),
('2348', '442010', '442000', '阜沙镇', '中国,广东省,中山市,阜沙镇', '阜沙', '中国,广东,中山,阜沙', '3', '0760', '528434', 'Fusha', 'FS', 'F', '113.353024', '22.666364'),
('2349', '442011', '442000', '小榄镇', '中国,广东省,中山市,小榄镇', '小榄', '中国,广东,中山,小榄', '3', '0760', '528415', 'Xiaolan', 'XL', 'X', '113.244235', '22.666951'),
('2350', '442012', '442000', '东升镇', '中国,广东省,中山市,东升镇', '东升', '中国,广东,中山,东升', '3', '0760', '528400', 'Dongsheng', 'DS', 'D', '113.296298', '22.614003'),
('2351', '442013', '442000', '古镇镇', '中国,广东省,中山市,古镇镇', '古镇', '中国,广东,中山,古镇', '3', '0760', '528422', 'Guzhen', 'GZ', 'G', '113.179745', '22.611019'),
('2352', '442014', '442000', '横栏镇', '中国,广东省,中山市,横栏镇', '横栏', '中国,广东,中山,横栏', '3', '0760', '528478', 'Henglan', 'HL', 'H', '113.265845', '22.523202'),
('2353', '442015', '442000', '三角镇', '中国,广东省,中山市,三角镇', '三角', '中国,广东,中山,三角', '3', '0760', '528422', 'Sanjiao', 'SJ', 'S', '113.423624', '22.677033'),
('2354', '442016', '442000', '民众镇', '中国,广东省,中山市,民众镇', '民众', '中国,广东,中山,民众', '3', '0760', '528441', 'Minzhong', 'MZ', 'M', '113.486025', '22.623468'),
('2355', '442017', '442000', '南朗镇', '中国,广东省,中山市,南朗镇', '南朗', '中国,广东,中山,南朗', '3', '0760', '528454', 'Nanlang', 'NL', 'N', '113.533939', '22.492378'),
('2356', '442018', '442000', '港口镇', '中国,广东省,中山市,港口镇', '港口', '中国,广东,中山,港口', '3', '0760', '528447', 'Gangkou', 'GK', 'G', '113.382391', '22.521113'),
('2357', '442019', '442000', '大涌镇', '中国,广东省,中山市,大涌镇', '大涌', '中国,广东,中山,大涌', '3', '0760', '528476', 'Dayong', 'DY', 'D', '113.291708', '22.467712'),
('2358', '442020', '442000', '沙溪镇', '中国,广东省,中山市,沙溪镇', '沙溪', '中国,广东,中山,沙溪', '3', '0760', '528471', 'Shaxi', 'SX', 'S', '113.328369', '22.526325'),
('2359', '442021', '442000', '三乡镇', '中国,广东省,中山市,三乡镇', '三乡', '中国,广东,中山,三乡', '3', '0760', '528463', 'Sanxiang', 'SX', 'S', '113.4334', '22.352494'),
('2360', '442022', '442000', '板芙镇', '中国,广东省,中山市,板芙镇', '板芙', '中国,广东,中山,板芙', '3', '0760', '528459', 'Banfu', 'BF', 'B', '113.320346', '22.415674'),
('2361', '442023', '442000', '神湾镇', '中国,广东省,中山市,神湾镇', '神湾', '中国,广东,中山,神湾', '3', '0760', '528462', 'Shenwan', 'SW', 'S', '113.359387', '22.312476'),
('2362', '442024', '442000', '坦洲镇', '中国,广东省,中山市,坦洲镇', '坦洲', '中国,广东,中山,坦洲', '3', '0760', '528467', 'Tanzhou', 'TZ', 'T', '113.485677', '22.261269'),
('2363', '445100', '440000', '潮州市', '中国,广东省,潮州市', '潮州', '中国,广东,潮州', '2', '0768', '521000', 'Chaozhou', 'CZ', 'C', '116.632301', '23.661701'),
('2364', '445102', '445100', '湘桥区', '中国,广东省,潮州市,湘桥区', '湘桥', '中国,广东,潮州,湘桥', '3', '0768', '521000', 'Xiangqiao', 'XQ', 'X', '116.62805', '23.67451'),
('2365', '445103', '445100', '潮安区', '中国,广东省,潮州市,潮安区', '潮安', '中国,广东,潮州,潮安', '3', '0768', '515638', 'Chao\'an', 'CA', 'C', '116.592895', '23.643656'),
('2366', '445122', '445100', '饶平县', '中国,广东省,潮州市,饶平县', '饶平', '中国,广东,潮州,饶平', '3', '0768', '515700', 'Raoping', 'RP', 'R', '117.00692', '23.66994'),
('2367', '445200', '440000', '揭阳市', '中国,广东省,揭阳市', '揭阳', '中国,广东,揭阳', '2', '0663', '522000', 'Jieyang', 'JY', 'J', '116.355733', '23.543778'),
('2368', '445202', '445200', '榕城区', '中国,广东省,揭阳市,榕城区', '榕城', '中国,广东,揭阳,榕城', '3', '0663', '522000', 'Rongcheng', 'RC', 'R', '116.3671', '23.52508'),
('2369', '445203', '445200', '揭东区', '中国,广东省,揭阳市,揭东区', '揭东', '中国,广东,揭阳,揭东', '3', '0663', '515500', 'Jiedong', 'JD', 'J', '116.412947', '23.569887'),
('2370', '445222', '445200', '揭西县', '中国,广东省,揭阳市,揭西县', '揭西', '中国,广东,揭阳,揭西', '3', '0663', '515400', 'Jiexi', 'JX', 'J', '115.83883', '23.42714'),
('2371', '445224', '445200', '惠来县', '中国,广东省,揭阳市,惠来县', '惠来', '中国,广东,揭阳,惠来', '3', '0663', '515200', 'Huilai', 'HL', 'H', '116.29599', '23.03289'),
('2372', '445281', '445200', '普宁市', '中国,广东省,揭阳市,普宁市', '普宁', '中国,广东,揭阳,普宁', '3', '0663', '515300', 'Puning', 'PN', 'P', '116.16564', '23.29732'),
('2373', '445300', '440000', '云浮市', '中国,广东省,云浮市', '云浮', '中国,广东,云浮', '2', '0766', '527300', 'Yunfu', 'YF', 'Y', '112.044439', '22.929801'),
('2374', '445302', '445300', '云城区', '中国,广东省,云浮市,云城区', '云城', '中国,广东,云浮,云城', '3', '0766', '527300', 'Yuncheng', 'YC', 'Y', '112.03908', '22.92996'),
('2375', '445303', '445300', '云安区', '中国,广东省,云浮市,云安区', '云安', '中国,广东,云浮,云安', '3', '0766', '527500', 'Yun\'an', 'YA', 'Y', '112.00936', '23.07779'),
('2376', '445321', '445300', '新兴县', '中国,广东省,云浮市,新兴县', '新兴', '中国,广东,云浮,新兴', '3', '0766', '527400', 'Xinxing', 'XX', 'X', '112.23019', '22.69734'),
('2377', '445322', '445300', '郁南县', '中国,广东省,云浮市,郁南县', '郁南', '中国,广东,云浮,郁南', '3', '0766', '527100', 'Yunan', 'YN', 'Y', '111.53387', '23.23307'),
('2378', '445381', '445300', '罗定市', '中国,广东省,云浮市,罗定市', '罗定', '中国,广东,云浮,罗定', '3', '0766', '527200', 'Luoding', 'LD', 'L', '111.56979', '22.76967'),
('2379', '450000', '100000', '广西壮族自治区', '中国,广西壮族自治区', '广西', '中国,广西', '1', '', '', 'Guangxi', 'GX', 'G', '108.320004', '22.82402'),
('2380', '450100', '450000', '南宁市', '中国,广西壮族自治区,南宁市', '南宁', '中国,广西,南宁', '2', '0771', '530000', 'Nanning', 'NN', 'N', '108.320004', '22.82402'),
('2381', '450102', '450100', '兴宁区', '中国,广西壮族自治区,南宁市,兴宁区', '兴宁', '中国,广西,南宁,兴宁', '3', '0771', '530000', 'Xingning', 'XN', 'X', '108.36694', '22.85355'),
('2382', '450103', '450100', '青秀区', '中国,广西壮族自治区,南宁市,青秀区', '青秀', '中国,广西,南宁,青秀', '3', '0771', '530000', 'Qingxiu', 'QX', 'Q', '108.49545', '22.78511'),
('2383', '450105', '450100', '江南区', '中国,广西壮族自治区,南宁市,江南区', '江南', '中国,广西,南宁,江南', '3', '0771', '530000', 'Jiangnan', 'JN', 'J', '108.27325', '22.78127'),
('2384', '450107', '450100', '西乡塘区', '中国,广西壮族自治区,南宁市,西乡塘区', '西乡塘', '中国,广西,南宁,西乡塘', '3', '0771', '530000', 'Xixiangtang', 'XXT', 'X', '108.31347', '22.83386'),
('2385', '450108', '450100', '良庆区', '中国,广西壮族自治区,南宁市,良庆区', '良庆', '中国,广西,南宁,良庆', '3', '0771', '530200', 'Liangqing', 'LQ', 'L', '108.41284', '22.74914'),
('2386', '450109', '450100', '邕宁区', '中国,广西壮族自治区,南宁市,邕宁区', '邕宁', '中国,广西,南宁,邕宁', '3', '0771', '530200', 'Yongning', 'YN', 'Y', '108.48684', '22.75628'),
('2387', '450110', '450100', '武鸣区', '中国,广西壮族自治区,南宁市,武鸣区', '武鸣', '中国,广西,南宁,武鸣', '3', '0771', '530100', 'Wuming', 'WM', 'W', '108.27719', '23.15643'),
('2388', '450123', '450100', '隆安县', '中国,广西壮族自治区,南宁市,隆安县', '隆安', '中国,广西,南宁,隆安', '3', '0771', '532700', 'Long\'an', 'LA', 'L', '107.69192', '23.17336'),
('2389', '450124', '450100', '马山县', '中国,广西壮族自治区,南宁市,马山县', '马山', '中国,广西,南宁,马山', '3', '0771', '530600', 'Mashan', 'MS', 'M', '108.17697', '23.70931'),
('2390', '450125', '450100', '上林县', '中国,广西壮族自治区,南宁市,上林县', '上林', '中国,广西,南宁,上林', '3', '0771', '530500', 'Shanglin', 'SL', 'S', '108.60522', '23.432'),
('2391', '450126', '450100', '宾阳县', '中国,广西壮族自治区,南宁市,宾阳县', '宾阳', '中国,广西,南宁,宾阳', '3', '0771', '530400', 'Binyang', 'BY', 'B', '108.81185', '23.2196'),
('2392', '450127', '450100', '横县', '中国,广西壮族自治区,南宁市,横县', '横县', '中国,广西,南宁,横县', '3', '0771', '530300', 'Hengxian', 'HX', 'H', '109.26608', '22.68448'),
('2393', '450128', '450100', '埌东新区', '中国,广西壮族自治区,南宁市,埌东新区', '埌东', '中国,广西,南宁,埌东', '3', '0771', '530000', 'Langdong', 'LD', 'L', '108.419094', '22.812976'),
('2394', '450200', '450000', '柳州市', '中国,广西壮族自治区,柳州市', '柳州', '中国,广西,柳州', '2', '0772', '545000', 'Liuzhou', 'LZ', 'L', '109.411703', '24.314617'),
('2395', '450202', '450200', '城中区', '中国,广西壮族自治区,柳州市,城中区', '城中', '中国,广西,柳州,城中', '3', '0772', '545000', 'Chengzhong', 'CZ', 'C', '109.41082', '24.31543'),
('2396', '450203', '450200', '鱼峰区', '中国,广西壮族自治区,柳州市,鱼峰区', '鱼峰', '中国,广西,柳州,鱼峰', '3', '0772', '545000', 'Yufeng', 'YF', 'Y', '109.4533', '24.31868'),
('2397', '450204', '450200', '柳南区', '中国,广西壮族自治区,柳州市,柳南区', '柳南', '中国,广西,柳州,柳南', '3', '0772', '545000', 'Liunan', 'LN', 'L', '109.38548', '24.33599'),
('2398', '450205', '450200', '柳北区', '中国,广西壮族自治区,柳州市,柳北区', '柳北', '中国,广西,柳州,柳北', '3', '0772', '545000', 'Liubei', 'LB', 'L', '109.40202', '24.36267'),
('2399', '450206', '450200', '柳江区', '中国,广西壮族自治区,柳州市,柳江区', '柳江', '中国,广西,柳州,柳江', '3', '0772', '545100', 'Liujiang', 'LJ', 'L', '109.33273', '24.25596'),
('2400', '450222', '450200', '柳城县', '中国,广西壮族自治区,柳州市,柳城县', '柳城', '中国,广西,柳州,柳城', '3', '0772', '545200', 'Liucheng', 'LC', 'L', '109.23877', '24.64951'),
('2401', '450223', '450200', '鹿寨县', '中国,广西壮族自治区,柳州市,鹿寨县', '鹿寨', '中国,广西,柳州,鹿寨', '3', '0772', '545600', 'Luzhai', 'LZ', 'L', '109.75177', '24.47306'),
('2402', '450224', '450200', '融安县', '中国,广西壮族自治区,柳州市,融安县', '融安', '中国,广西,柳州,融安', '3', '0772', '545400', 'Rong\'an', 'RA', 'R', '109.39761', '25.22465'),
('2403', '450225', '450200', '融水苗族自治县', '中国,广西壮族自治区,柳州市,融水苗族自治县', '融水', '中国,广西,柳州,融水', '3', '0772', '545300', 'Rongshui', 'RS', 'R', '109.25634', '25.06628'),
('2404', '450226', '450200', '三江侗族自治县', '中国,广西壮族自治区,柳州市,三江侗族自治县', '三江', '中国,广西,柳州,三江', '3', '0772', '545500', 'Sanjiang', 'SJ', 'S', '109.60446', '25.78428'),
('2405', '450227', '450200', '柳东新区', '中国,广西壮族自治区,柳州市,柳东新区', '柳东', '中国,广西,柳州,柳东', '3', '0772', '545000', 'Liudong', 'LD', 'L', '109.437053', '24.329204'),
('2406', '450300', '450000', '桂林市', '中国,广西壮族自治区,桂林市', '桂林', '中国,广西,桂林', '2', '0773', '541000', 'Guilin', 'GL', 'G', '110.299121', '25.274215'),
('2407', '450302', '450300', '秀峰区', '中国,广西壮族自治区,桂林市,秀峰区', '秀峰', '中国,广西,桂林,秀峰', '3', '0773', '541000', 'Xiufeng', 'XF', 'X', '110.28915', '25.28249'),
('2408', '450303', '450300', '叠彩区', '中国,广西壮族自治区,桂林市,叠彩区', '叠彩', '中国,广西,桂林,叠彩', '3', '0773', '541000', 'Diecai', 'DC', 'D', '110.30195', '25.31381'),
('2409', '450304', '450300', '象山区', '中国,广西壮族自治区,桂林市,象山区', '象山', '中国,广西,桂林,象山', '3', '0773', '541000', 'Xiangshan', 'XS', 'X', '110.28108', '25.26168'),
('2410', '450305', '450300', '七星区', '中国,广西壮族自治区,桂林市,七星区', '七星', '中国,广西,桂林,七星', '3', '0773', '541000', 'Qixing', 'QX', 'Q', '110.31793', '25.2525'),
('2411', '450311', '450300', '雁山区', '中国,广西壮族自治区,桂林市,雁山区', '雁山', '中国,广西,桂林,雁山', '3', '0773', '541000', 'Yanshan', 'YS', 'Y', '110.30911', '25.06038'),
('2412', '450312', '450300', '临桂区', '中国,广西壮族自治区,桂林市,临桂区', '临桂', '中国,广西,桂林,临桂', '3', '0773', '541100', 'Lingui', 'LG', 'L', '110.205487', '25.246257'),
('2413', '450321', '450300', '阳朔县', '中国,广西壮族自治区,桂林市,阳朔县', '阳朔', '中国,广西,桂林,阳朔', '3', '0773', '541900', 'Yangshuo', 'YS', 'Y', '110.49475', '24.77579'),
('2414', '450323', '450300', '灵川县', '中国,广西壮族自治区,桂林市,灵川县', '灵川', '中国,广西,桂林,灵川', '3', '0773', '541200', 'Lingchuan', 'LC', 'L', '110.32949', '25.41292'),
('2415', '450324', '450300', '全州县', '中国,广西壮族自治区,桂林市,全州县', '全州', '中国,广西,桂林,全州', '3', '0773', '541500', 'Quanzhou', 'QZ', 'Q', '111.07211', '25.92799'),
('2416', '450325', '450300', '兴安县', '中国,广西壮族自治区,桂林市,兴安县', '兴安', '中国,广西,桂林,兴安', '3', '0773', '541300', 'Xing\'an', 'XA', 'X', '110.67144', '25.61167'),
('2417', '450326', '450300', '永福县', '中国,广西壮族自治区,桂林市,永福县', '永福', '中国,广西,桂林,永福', '3', '0773', '541800', 'Yongfu', 'YF', 'Y', '109.98333', '24.98004'),
('2418', '450327', '450300', '灌阳县', '中国,广西壮族自治区,桂林市,灌阳县', '灌阳', '中国,广西,桂林,灌阳', '3', '0773', '541600', 'Guanyang', 'GY', 'G', '111.15954', '25.48803'),
('2419', '450328', '450300', '龙胜各族自治县', '中国,广西壮族自治区,桂林市,龙胜各族自治县', '龙胜', '中国,广西,桂林,龙胜', '3', '0773', '541700', 'Longsheng', 'LS', 'L', '110.01226', '25.79614'),
('2420', '450329', '450300', '资源县', '中国,广西壮族自治区,桂林市,资源县', '资源', '中国,广西,桂林,资源', '3', '0773', '541400', 'Ziyuan', 'ZY', 'Z', '110.65255', '26.04237'),
('2421', '450330', '450300', '平乐县', '中国,广西壮族自治区,桂林市,平乐县', '平乐', '中国,广西,桂林,平乐', '3', '0773', '542400', 'Pingle', 'PL', 'P', '110.64175', '24.63242'),
('2422', '450331', '450300', '荔浦县', '中国,广西壮族自治区,桂林市,荔浦县', '荔浦', '中国,广西,桂林,荔浦', '3', '0773', '546600', 'Lipu', 'LP', 'L', '110.3971', '24.49589'),
('2423', '450332', '450300', '恭城瑶族自治县', '中国,广西壮族自治区,桂林市,恭城瑶族自治县', '恭城', '中国,广西,桂林,恭城', '3', '0773', '542500', 'Gongcheng', 'GC', 'G', '110.83035', '24.83286'),
('2424', '450400', '450000', '梧州市', '中国,广西壮族自治区,梧州市', '梧州', '中国,广西,梧州', '2', '0774', '543000', 'Wuzhou', 'WZ', 'W', '111.316229', '23.472309'),
('2425', '450403', '450400', '万秀区', '中国,广西壮族自治区,梧州市,万秀区', '万秀', '中国,广西,梧州,万秀', '3', '0774', '543000', 'Wanxiu', 'WX', 'W', '111.32052', '23.47298'),
('2426', '450405', '450400', '长洲区', '中国,广西壮族自治区,梧州市,长洲区', '长洲', '中国,广西,梧州,长洲', '3', '0774', '543000', 'Changzhou', 'CZ', 'C', '111.27494', '23.48573'),
('2427', '450406', '450400', '龙圩区', '中国,广西壮族自治区,梧州市,龙圩区', '龙圩', '中国,广西,梧州,龙圩', '3', '0774', '543002', 'Longxu', 'LX', 'L', '111.316229', '23.472309'),
('2428', '450421', '450400', '苍梧县', '中国,广西壮族自治区,梧州市,苍梧县', '苍梧', '中国,广西,梧州,苍梧', '3', '0774', '543100', 'Cangwu', 'CW', 'C', '111.24533', '23.42049'),
('2429', '450422', '450400', '藤县', '中国,广西壮族自治区,梧州市,藤县', '藤县', '中国,广西,梧州,藤县', '3', '0774', '543300', 'Tengxian', 'TX', 'T', '110.91418', '23.37605'),
('2430', '450423', '450400', '蒙山县', '中国,广西壮族自治区,梧州市,蒙山县', '蒙山', '中国,广西,梧州,蒙山', '3', '0774', '546700', 'Mengshan', 'MS', 'M', '110.52221', '24.20168'),
('2431', '450481', '450400', '岑溪市', '中国,广西壮族自治区,梧州市,岑溪市', '岑溪', '中国,广西,梧州,岑溪', '3', '0774', '543200', 'Cenxi', 'CX', 'C', '110.99594', '22.9191'),
('2432', '450500', '450000', '北海市', '中国,广西壮族自治区,北海市', '北海', '中国,广西,北海', '2', '0779', '536000', 'Beihai', 'BH', 'B', '109.119254', '21.473343'),
('2433', '450502', '450500', '海城区', '中国,广西壮族自治区,北海市,海城区', '海城', '中国,广西,北海,海城', '3', '0779', '536000', 'Haicheng', 'HC', 'H', '109.11744', '21.47501'),
('2434', '450503', '450500', '银海区', '中国,广西壮族自治区,北海市,银海区', '银海', '中国,广西,北海,银海', '3', '0779', '536000', 'Yinhai', 'YH', 'Y', '109.13029', '21.4783'),
('2435', '450512', '450500', '铁山港区', '中国,广西壮族自治区,北海市,铁山港区', '铁山港', '中国,广西,北海,铁山港', '3', '0779', '536000', 'Tieshangang', 'TSG', 'T', '109.45578', '21.59661'),
('2436', '450521', '450500', '合浦县', '中国,广西壮族自治区,北海市,合浦县', '合浦', '中国,广西,北海,合浦', '3', '0779', '536100', 'Hepu', 'HP', 'H', '109.20068', '21.66601'),
('2437', '450600', '450000', '防城港市', '中国,广西壮族自治区,防城港市', '防城港', '中国,广西,防城港', '2', '0770', '538000', 'Fangchenggang', 'FCG', 'F', '108.345478', '21.614631'),
('2438', '450602', '450600', '港口区', '中国,广西壮族自治区,防城港市,港口区', '港口', '中国,广西,防城港,港口', '3', '0770', '538000', 'Gangkou', 'GK', 'G', '108.38022', '21.64342'),
('2439', '450603', '450600', '防城区', '中国,广西壮族自治区,防城港市,防城区', '防城', '中国,广西,防城港,防城', '3', '0770', '538000', 'Fangcheng', 'FC', 'F', '108.35726', '21.76464'),
('2440', '450621', '450600', '上思县', '中国,广西壮族自治区,防城港市,上思县', '上思', '中国,广西,防城港,上思', '3', '0770', '535500', 'Shangsi', 'SS', 'S', '107.9823', '22.14957'),
('2441', '450681', '450600', '东兴市', '中国,广西壮族自治区,防城港市,东兴市', '东兴', '中国,广西,防城港,东兴', '3', '0770', '538100', 'Dongxing', 'DX', 'D', '107.97204', '21.54713'),
('2442', '450700', '450000', '钦州市', '中国,广西壮族自治区,钦州市', '钦州', '中国,广西,钦州', '2', '0777', '535000', 'Qinzhou', 'QZ', 'Q', '108.624175', '21.967127'),
('2443', '450702', '450700', '钦南区', '中国,广西壮族自治区,钦州市,钦南区', '钦南', '中国,广西,钦州,钦南', '3', '0777', '535000', 'Qinnan', 'QN', 'Q', '108.61775', '21.95137'),
('2444', '450703', '450700', '钦北区', '中国,广西壮族自治区,钦州市,钦北区', '钦北', '中国,广西,钦州,钦北', '3', '0777', '535000', 'Qinbei', 'QB', 'Q', '108.63037', '21.95127'),
('2445', '450721', '450700', '灵山县', '中国,广西壮族自治区,钦州市,灵山县', '灵山', '中国,广西,钦州,灵山', '3', '0777', '535400', 'Lingshan', 'LS', 'L', '109.29153', '22.4165'),
('2446', '450722', '450700', '浦北县', '中国,广西壮族自治区,钦州市,浦北县', '浦北', '中国,广西,钦州,浦北', '3', '0777', '535300', 'Pubei', 'PB', 'P', '109.55572', '22.26888'),
('2447', '450800', '450000', '贵港市', '中国,广西壮族自治区,贵港市', '贵港', '中国,广西,贵港', '2', '0775', '537100', 'Guigang', 'GG', 'G', '109.602146', '23.0936'),
('2448', '450802', '450800', '港北区', '中国,广西壮族自治区,贵港市,港北区', '港北', '中国,广西,贵港,港北', '3', '0775', '537100', 'Gangbei', 'GB', 'G', '109.57224', '23.11153'),
('2449', '450803', '450800', '港南区', '中国,广西壮族自治区,贵港市,港南区', '港南', '中国,广西,贵港,港南', '3', '0775', '537100', 'Gangnan', 'GN', 'G', '109.60617', '23.07226'),
('2450', '450804', '450800', '覃塘区', '中国,广西壮族自治区,贵港市,覃塘区', '覃塘', '中国,广西,贵港,覃塘', '3', '0775', '537100', 'Qintang', 'QT', 'Q', '109.44293', '23.12677'),
('2451', '450821', '450800', '平南县', '中国,广西壮族自治区,贵港市,平南县', '平南', '中国,广西,贵港,平南', '3', '0775', '537300', 'Pingnan', 'PN', 'P', '110.39062', '23.54201'),
('2452', '450881', '450800', '桂平市', '中国,广西壮族自治区,贵港市,桂平市', '桂平', '中国,广西,贵港,桂平', '3', '0775', '537200', 'Guiping', 'GP', 'G', '110.08105', '23.39339'),
('2453', '450900', '450000', '玉林市', '中国,广西壮族自治区,玉林市', '玉林', '中国,广西,玉林', '2', '0775', '537000', 'Yulin', 'YL', 'Y', '110.154393', '22.63136'),
('2454', '450902', '450900', '玉州区', '中国,广西壮族自治区,玉林市,玉州区', '玉州', '中国,广西,玉林,玉州', '3', '0775', '537000', 'Yuzhou', 'YZ', 'Y', '110.15114', '22.6281'),
('2455', '450903', '450900', '福绵区', '中国,广西壮族自治区,玉林市,福绵区', '福绵', '中国,广西,玉林,福绵', '3', '0775', '537023', 'Fumian', 'FM', 'F', '110.064816', '22.583057'),
('2456', '450904', '450900', '玉东新区', '中国,广西壮族自治区,玉林市,玉东新区', '玉东', '中国,广西,玉林,玉东', '3', '0775', '537000', 'Yudong', 'YD', 'Y', '110.154393', '22.63136'),
('2457', '450921', '450900', '容县', '中国,广西壮族自治区,玉林市,容县', '容县', '中国,广西,玉林,容县', '3', '0775', '537500', 'Rongxian', 'RX', 'R', '110.55593', '22.85701'),
('2458', '450922', '450900', '陆川县', '中国,广西壮族自治区,玉林市,陆川县', '陆川', '中国,广西,玉林,陆川', '3', '0775', '537700', 'Luchuan', 'LC', 'L', '110.26413', '22.32454'),
('2459', '450923', '450900', '博白县', '中国,广西壮族自治区,玉林市,博白县', '博白', '中国,广西,玉林,博白', '3', '0775', '537600', 'Bobai', 'BB', 'B', '109.97744', '22.27286'),
('2460', '450924', '450900', '兴业县', '中国,广西壮族自治区,玉林市,兴业县', '兴业', '中国,广西,玉林,兴业', '3', '0775', '537800', 'Xingye', 'XY', 'X', '109.87612', '22.74237'),
('2461', '450981', '450900', '北流市', '中国,广西壮族自治区,玉林市,北流市', '北流', '中国,广西,玉林,北流', '3', '0775', '537400', 'Beiliu', 'BL', 'B', '110.35302', '22.70817'),
('2462', '451000', '450000', '百色市', '中国,广西壮族自治区,百色市', '百色', '中国,广西,百色', '2', '0776', '533000', 'Baise', 'BS', 'B', '106.616285', '23.897742'),
('2463', '451002', '451000', '右江区', '中国,广西壮族自治区,百色市,右江区', '右江', '中国,广西,百色,右江', '3', '0776', '533000', 'Youjiang', 'YJ', 'Y', '106.61764', '23.9009'),
('2464', '451021', '451000', '田阳县', '中国,广西壮族自治区,百色市,田阳县', '田阳', '中国,广西,百色,田阳', '3', '0776', '533600', 'Tianyang', 'TY', 'T', '106.91558', '23.73535'),
('2465', '451022', '451000', '田东县', '中国,广西壮族自治区,百色市,田东县', '田东', '中国,广西,百色,田东', '3', '0776', '531500', 'Tiandong', 'TD', 'T', '107.12432', '23.60003'),
('2466', '451023', '451000', '平果县', '中国,广西壮族自治区,百色市,平果县', '平果', '中国,广西,百色,平果', '3', '0776', '531400', 'Pingguo', 'PG', 'P', '107.59045', '23.32969'),
('2467', '451024', '451000', '德保县', '中国,广西壮族自治区,百色市,德保县', '德保', '中国,广西,百色,德保', '3', '0776', '533700', 'Debao', 'DB', 'D', '106.61917', '23.32515'),
('2468', '451026', '451000', '那坡县', '中国,广西壮族自治区,百色市,那坡县', '那坡', '中国,广西,百色,那坡', '3', '0776', '533900', 'Napo', 'NP', 'N', '105.84191', '23.40649'),
('2469', '451027', '451000', '凌云县', '中国,广西壮族自治区,百色市,凌云县', '凌云', '中国,广西,百色,凌云', '3', '0776', '533100', 'Lingyun', 'LY', 'L', '106.56155', '24.34747'),
('2470', '451028', '451000', '乐业县', '中国,广西壮族自治区,百色市,乐业县', '乐业', '中国,广西,百色,乐业', '3', '0776', '533200', 'Leye', 'LY', 'L', '106.56124', '24.78295'),
('2471', '451029', '451000', '田林县', '中国,广西壮族自治区,百色市,田林县', '田林', '中国,广西,百色,田林', '3', '0776', '533300', 'Tianlin', 'TL', 'T', '106.22882', '24.29207'),
('2472', '451030', '451000', '西林县', '中国,广西壮族自治区,百色市,西林县', '西林', '中国,广西,百色,西林', '3', '0776', '533500', 'Xilin', 'XL', 'X', '105.09722', '24.48966'),
('2473', '451031', '451000', '隆林各族自治县', '中国,广西壮族自治区,百色市,隆林各族自治县', '隆林', '中国,广西,百色,隆林', '3', '0776', '533400', 'Longlin', 'LL', 'L', '105.34295', '24.77036'),
('2474', '451081', '451000', '靖西市', '中国,广西壮族自治区,百色市,靖西市', '靖西', '中国,广西,百色,靖西', '3', '0776', '533800', 'Jingxi', 'JX', 'J', '106.41766', '23.13425'),
('2475', '451100', '450000', '贺州市', '中国,广西壮族自治区,贺州市', '贺州', '中国,广西,贺州', '2', '0774', '542800', 'Hezhou', 'HZ', 'H', '111.552056', '24.414141'),
('2476', '451102', '451100', '八步区', '中国,广西壮族自治区,贺州市,八步区', '八步', '中国,广西,贺州,八步', '3', '0774', '542800', 'Babu', 'BB', 'B', '111.55225', '24.41179'),
('2477', '451103', '451100', '平桂区', '中国,广西壮族自治区,贺州市,平桂区', '平桂', '中国,广西,贺州,平桂', '3', '0774', '542800', 'Pingui', 'PG', 'P', '111.485651', '24.458041'),
('2478', '451121', '451100', '昭平县', '中国,广西壮族自治区,贺州市,昭平县', '昭平', '中国,广西,贺州,昭平', '3', '0774', '546800', 'Zhaoping', 'ZP', 'Z', '110.81082', '24.1701'),
('2479', '451122', '451100', '钟山县', '中国,广西壮族自治区,贺州市,钟山县', '钟山', '中国,广西,贺州,钟山', '3', '0774', '542600', 'Zhongshan', 'ZS', 'Z', '111.30459', '24.52482'),
('2480', '451123', '451100', '富川瑶族自治县', '中国,广西壮族自治区,贺州市,富川瑶族自治县', '富川', '中国,广西,贺州,富川', '3', '0774', '542700', 'Fuchuan', 'FC', 'F', '111.27767', '24.81431'),
('2481', '451200', '450000', '河池市', '中国,广西壮族自治区,河池市', '河池', '中国,广西,河池', '2', '0778', '547000', 'Hechi', 'HC', 'H', '108.062105', '24.695899'),
('2482', '451202', '451200', '金城江区', '中国,广西壮族自治区,河池市,金城江区', '金城江', '中国,广西,河池,金城江', '3', '0778', '547000', 'Jinchengjiang', 'JCJ', 'J', '108.03727', '24.6897'),
('2483', '451203', '451200', '宜州区', '中国,广西壮族自治区,河池市,宜州区', '宜州', '中国,广西,河池,宜州', '3', '0778', '546300', 'Yizhou', 'YZ', 'Y', '108.65304', '24.49391'),
('2484', '451221', '451200', '南丹县', '中国,广西壮族自治区,河池市,南丹县', '南丹', '中国,广西,河池,南丹', '3', '0778', '547200', 'Nandan', 'ND', 'N', '107.54562', '24.9776'),
('2485', '451222', '451200', '天峨县', '中国,广西壮族自治区,河池市,天峨县', '天峨', '中国,广西,河池,天峨', '3', '0778', '547300', 'Tiane', 'TE', 'T', '107.17205', '24.99593'),
('2486', '451223', '451200', '凤山县', '中国,广西壮族自治区,河池市,凤山县', '凤山', '中国,广西,河池,凤山', '3', '0778', '547600', 'Fengshan', 'FS', 'F', '107.04892', '24.54215'),
('2487', '451224', '451200', '东兰县', '中国,广西壮族自治区,河池市,东兰县', '东兰', '中国,广西,河池,东兰', '3', '0778', '547400', 'Donglan', 'DL', 'D', '107.37527', '24.51053'),
('2488', '451225', '451200', '罗城仫佬族自治县', '中国,广西壮族自治区,河池市,罗城仫佬族自治县', '罗城', '中国,广西,河池,罗城', '3', '0778', '546499', 'Luocheng', 'LC', 'L', '108.90777', '24.77923'),
('2489', '451226', '451200', '环江毛南族自治县', '中国,广西壮族自治区,河池市,环江毛南族自治县', '环江', '中国,广西,河池,环江', '3', '0778', '547100', 'Huanjiang', 'HJ', 'H', '108.26055', '24.82916'),
('2490', '451227', '451200', '巴马瑶族自治县', '中国,广西壮族自治区,河池市,巴马瑶族自治县', '巴马', '中国,广西,河池,巴马', '3', '0778', '547500', 'Bama', 'BM', 'B', '107.25308', '24.14135'),
('2491', '451228', '451200', '都安瑶族自治县', '中国,广西壮族自治区,河池市,都安瑶族自治县', '都安', '中国,广西,河池,都安', '3', '0778', '530700', 'Du\'an', 'DA', 'D', '108.10116', '23.93245'),
('2492', '451229', '451200', '大化瑶族自治县', '中国,广西壮族自治区,河池市,大化瑶族自治县', '大化', '中国,广西,河池,大化', '3', '0778', '530800', 'Dahua', 'DH', 'D', '107.9985', '23.74487'),
('2493', '451300', '450000', '来宾市', '中国,广西壮族自治区,来宾市', '来宾', '中国,广西,来宾', '2', '0772', '546100', 'Laibin', 'LB', 'L', '109.229772', '23.733766'),
('2494', '451302', '451300', '兴宾区', '中国,广西壮族自治区,来宾市,兴宾区', '兴宾', '中国,广西,来宾,兴宾', '3', '0772', '546100', 'Xingbin', 'XB', 'X', '109.23471', '23.72731'),
('2495', '451321', '451300', '忻城县', '中国,广西壮族自治区,来宾市,忻城县', '忻城', '中国,广西,来宾,忻城', '3', '0772', '546200', 'Xincheng', 'XC', 'X', '108.66357', '24.06862'),
('2496', '451322', '451300', '象州县', '中国,广西壮族自治区,来宾市,象州县', '象州', '中国,广西,来宾,象州', '3', '0772', '545800', 'Xiangzhou', 'XZ', 'X', '109.6994', '23.97355'),
('2497', '451323', '451300', '武宣县', '中国,广西壮族自治区,来宾市,武宣县', '武宣', '中国,广西,来宾,武宣', '3', '0772', '545900', 'Wuxuan', 'WX', 'W', '109.66284', '23.59474'),
('2498', '451324', '451300', '金秀瑶族自治县', '中国,广西壮族自治区,来宾市,金秀瑶族自治县', '金秀', '中国,广西,来宾,金秀', '3', '0772', '545700', 'Jinxiu', 'JX', 'J', '110.19079', '24.12929'),
('2499', '451381', '451300', '合山市', '中国,广西壮族自治区,来宾市,合山市', '合山', '中国,广西,来宾,合山', '3', '0772', '546500', 'Heshan', 'HS', 'H', '108.88586', '23.80619'),
('2500', '451400', '450000', '崇左市', '中国,广西壮族自治区,崇左市', '崇左', '中国,广西,崇左', '2', '0771', '532200', 'Chongzuo', 'CZ', 'C', '107.353926', '22.404108'),
('2501', '451402', '451400', '江州区', '中国,广西壮族自治区,崇左市,江州区', '江州', '中国,广西,崇左,江州', '3', '0771', '532299', 'Jiangzhou', 'JZ', 'J', '107.34747', '22.41135'),
('2502', '451421', '451400', '扶绥县', '中国,广西壮族自治区,崇左市,扶绥县', '扶绥', '中国,广西,崇左,扶绥', '3', '0771', '532100', 'Fusui', 'FS', 'F', '107.90405', '22.63413'),
('2503', '451422', '451400', '宁明县', '中国,广西壮族自治区,崇左市,宁明县', '宁明', '中国,广西,崇左,宁明', '3', '0771', '532500', 'Ningming', 'NM', 'N', '107.07299', '22.13655'),
('2504', '451423', '451400', '龙州县', '中国,广西壮族自治区,崇左市,龙州县', '龙州', '中国,广西,崇左,龙州', '3', '0771', '532400', 'Longzhou', 'LZ', 'L', '106.85415', '22.33937'),
('2505', '451424', '451400', '大新县', '中国,广西壮族自治区,崇左市,大新县', '大新', '中国,广西,崇左,大新', '3', '0771', '532300', 'Daxin', 'DX', 'D', '107.19821', '22.83412'),
('2506', '451425', '451400', '天等县', '中国,广西壮族自治区,崇左市,天等县', '天等', '中国,广西,崇左,天等', '3', '0771', '532800', 'Tiandeng', 'TD', 'T', '107.13998', '23.077'),
('2507', '451481', '451400', '凭祥市', '中国,广西壮族自治区,崇左市,凭祥市', '凭祥', '中国,广西,崇左,凭祥', '3', '0771', '532600', 'Pingxiang', 'PX', 'P', '106.75534', '22.10573'),
('2508', '460000', '100000', '海南省', '中国,海南省', '海南', '中国,海南', '1', '', '', 'Hainan', 'HI', 'H', '110.33119', '20.031971'),
('2509', '460100', '460000', '海口市', '中国,海南省,海口市', '海口', '中国,海南,海口', '2', '0898', '570000', 'Haikou', 'HK', 'H', '110.33119', '20.031971'),
('2510', '460105', '460100', '秀英区', '中国,海南省,海口市,秀英区', '秀英', '中国,海南,海口,秀英', '3', '0898', '570300', 'Xiuying', 'XY', 'X', '110.29345', '20.00752'),
('2511', '460106', '460100', '龙华区', '中国,海南省,海口市,龙华区', '龙华', '中国,海南,海口,龙华', '3', '0898', '570100', 'Longhua', 'LH', 'L', '110.30194', '20.02866'),
('2512', '460107', '460100', '琼山区', '中国,海南省,海口市,琼山区', '琼山', '中国,海南,海口,琼山', '3', '0898', '571199', 'Qiongshan', 'QS', 'Q', '110.35418', '20.00321'),
('2513', '460108', '460100', '美兰区', '中国,海南省,海口市,美兰区', '美兰', '中国,海南,海口,美兰', '3', '0898', '570203', 'Meilan', 'ML', 'M', '110.36908', '20.02864'),
('2514', '460200', '460000', '三亚市', '中国,海南省,三亚市', '三亚', '中国,海南,三亚', '2', '0898', '572000', 'Sanya', 'SY', 'S', '109.508268', '18.247872'),
('2515', '460202', '460200', '海棠区', '中国,海南省,三亚市,海棠区', '海棠', '中国,海南,三亚,海棠', '3', '0898', '572000', 'Haitang', 'HT', 'H', '109.508268', '18.247872'),
('2516', '460203', '460200', '吉阳区', '中国,海南省,三亚市,吉阳区', '吉阳', '中国,海南,三亚,吉阳', '3', '0898', '572000', 'Jiyang', 'JY', 'J', '109.508268', '18.247872'),
('2517', '460204', '460200', '天涯区', '中国,海南省,三亚市,天涯区', '天涯', '中国,海南,三亚,天涯', '3', '0898', '572000', 'Tianya', 'TY', 'T', '109.508268', '18.247872'),
('2518', '460205', '460200', '崖州区', '中国,海南省,三亚市,崖州区', '崖州', '中国,海南,三亚,崖州', '3', '0898', '572000', 'Yazhou', 'YZ', 'Y', '109.508268', '18.247872'),
('2519', '460300', '460000', '三沙市', '中国,海南省,三沙市', '三沙', '中国,海南,三沙', '2', '0898', '573199', 'Sansha', 'SS', 'S', '112.34882', '16.831039'),
('2520', '460321', '460300', '西沙群岛', '中国,海南省,三沙市,西沙群岛', '西沙', '中国,海南,三沙,西沙', '3', '0898', '573199', 'Xisha Islands', 'XS', 'X', '112.025528', '16.331342'),
('2521', '460322', '460300', '南沙群岛', '中国,海南省,三沙市,南沙群岛', '南沙', '中国,海南,三沙,南沙', '3', '0898', '573199', 'Nansha Islands', 'NS', 'N', '116.749998', '11.471888'),
('2522', '460323', '460300', '中沙群岛', '中国,海南省,三沙市,中沙群岛', '中沙', '中国,海南,三沙,中沙', '3', '0898', '573199', 'Zhongsha Islands', 'ZS', 'Z', '117.740071', '15.112856'),
('2523', '460400', '460000', '儋州市', '中国,海南省,儋州市', '儋州', '中国,海南,儋州', '2', '0898', '571700', 'Danzhou', 'DZ', 'D', '109.576782', '19.517486'),
('2524', '460401', '460400', '洋浦经济开发区', '中国,海南省,儋州市,洋浦经济开发区', '洋浦', '中国,海南,儋州,洋浦', '3', '0898', '578001', 'Yangpu', 'YP', 'Y', '109.199224', '19.734908'),
('2525', '460402', '460400', '那大镇', '中国,海南省,儋州市,那大镇', '那大', '中国,海南,儋州,那大', '3', '0898', '571799', 'Nada', 'ND', 'N', '109.546409', '19.514878'),
('2526', '460403', '460400', '南丰镇', '中国,海南省,儋州市,南丰镇', '南丰', '中国,海南,儋州,南丰', '3', '0898', '571724', 'Nanfeng', 'NF', 'N', '109.555934', '19.409409'),
('2527', '460404', '460400', '雅星镇', '中国,海南省,儋州市,雅星镇', '雅星', '中国,海南,儋州,雅星', '3', '0898', '571729', 'Yaxing', 'YX', 'Y', '109.269116', '19.444077'),
('2528', '460405', '460400', '和庆镇', '中国,海南省,儋州市,和庆镇', '和庆', '中国,海南,儋州,和庆', '3', '0898', '571721', 'Heqing', 'HQ', 'H', '109.640856', '19.525399'),
('2529', '460406', '460400', '大成镇', '中国,海南省,儋州市,大成镇', '大成', '中国,海南,儋州,大成', '3', '0898', '571736', 'Dacheng', 'DC', 'D', '109.399019', '19.508129'),
('2530', '460407', '460400', '新州镇', '中国,海南省,儋州市,新州镇', '新州', '中国,海南,儋州,新州', '3', '0898', '571749', 'Xinzhou', 'XZ', 'X', '109.316166', '19.71431'),
('2531', '460408', '460400', '光村镇', '中国,海南省,儋州市,光村镇', '光村', '中国,海南,儋州,光村', '3', '0898', '571752', 'Guangcun', 'GC', 'G', '109.486552', '19.813635'),
('2532', '460409', '460400', '东成镇', '中国,海南省,儋州市,东成镇', '东成', '中国,海南,儋州,东成', '3', '0898', '571763', 'Dongcheng', 'DC', 'D', '109.461304', '19.703738'),
('2533', '460410', '460400', '中和镇', '中国,海南省,儋州市,中和镇', '中和', '中国,海南,儋州,中和', '3', '0898', '571747', 'Zhonghe', 'ZH', 'Z', '109.354985', '19.739661'),
('2534', '460411', '460400', '峨蔓镇', '中国,海南省,儋州市,峨蔓镇', '峨蔓', '中国,海南,儋州,峨蔓', '3', '0898', '571745', 'E\'man', 'EM', 'E', '109.266778', '19.854774'),
('2535', '460412', '460400', '兰洋镇', '中国,海南省,儋州市,兰洋镇', '兰洋', '中国,海南,儋州,兰洋', '3', '0898', '571722', 'Lanyang', 'LY', 'L', '109.664392', '19.460397'),
('2536', '460413', '460400', '王五镇', '中国,海南省,儋州市,王五镇', '王五', '中国,海南,儋州,王五', '3', '0898', '571739', 'Wangwu', 'WW', 'W', '109.299551', '19.652264'),
('2537', '460414', '460400', '排浦镇', '中国,海南省,儋州市,排浦镇', '排浦', '中国,海南,儋州,排浦', '3', '0898', '571741', 'Paipu', 'PP', 'P', '109.163254', '19.63881'),
('2538', '460415', '460400', '海头镇', '中国,海南省,儋州市,海头镇', '海头', '中国,海南,儋州,海头', '3', '0898', '571732', 'Haitou', 'HT', 'H', '108.953393', '19.503315'),
('2539', '460416', '460400', '木棠镇', '中国,海南省,儋州市,木棠镇', '木棠', '中国,海南,儋州,木棠', '3', '0898', '571746', 'Mutang', 'MT', 'M', '109.350255', '19.803926'),
('2540', '460417', '460400', '白马井镇', '中国,海南省,儋州市,白马井镇', '白马井', '中国,海南,儋州,白马井', '3', '0898', '571742', 'Baima', 'BMJ', 'B', '109.218735', '19.696407'),
('2541', '460418', '460400', '三都镇', '中国,海南省,儋州市,三都镇', '三都', '中国,海南,儋州,三都', '3', '0898', '571744', 'Sandu', 'SD', 'S', '109.223369', '19.785945'),
('2542', '460419', '460400', '西培农场', '中国,海南省,儋州市,西培农场', '西培农场', '中国,海南,儋州,西培农场', '3', '0898', '571725', 'Xipei', 'XP', 'X', '109.455554', '19.476422'),
('2543', '460420', '460400', '西联农场', '中国,海南省,儋州市,西联农场', '西联农场', '中国,海南,儋州,西联农场', '3', '0898', '571756', 'Xilian', 'XL', 'X', '109.561827', '19.568148'),
('2544', '460421', '460400', '蓝洋农场', '中国,海南省,儋州市,蓝洋农场', '蓝洋农场', '中国,海南,儋州,蓝洋农场', '3', '0898', '571756', 'Lanyang', 'LY', 'L', '109.670722', '19.458983'),
('2545', '460422', '460400', '八一农场', '中国,海南省,儋州市,八一农场', '八一农场', '中国,海南,儋州,八一农场', '3', '0898', '571727', 'Bayi', 'BY', 'B', '109.294493', '19.433909'),
('2546', '460423', '460400', '西华农场', '中国,海南省,儋州市,西华农场', '西华农场', '中国,海南,儋州,西华农场', '3', '0898', '571735', 'Xihua', 'XH', 'X', '109.339232', '19.522628'),
('2547', '460424', '460400', '西庆农场', '中国,海南省,儋州市,西庆农场', '西庆农场', '中国,海南,儋州,西庆农场', '3', '0898', '571738', 'Xiqing', 'XQ', 'X', '109.466186', '19.534709'),
('2548', '460425', '460400', '西流农场', '中国,海南省,儋州市,西流农场', '西流农场', '中国,海南,儋州,西流农场', '3', '0898', '571758', 'Xiliu', 'XL', 'X', '109.640914', '19.565919'),
('2549', '460426', '460400', '新盈农场', '中国,海南省,儋州市,新盈农场', '新盈农场', '中国,海南,儋州,新盈农场', '3', '0898', '571753', 'Xinying', 'XY', 'X', '109.561667', '19.774501'),
('2550', '460427', '460400', '龙山农场', '中国,海南省,儋州市,龙山农场', '龙山农场', '中国,海南,儋州,龙山农场', '3', '0898', '571757', 'Longshan', 'LS', 'L', '109.180752', '19.601523'),
('2551', '460428', '460400', '红岭农场', '中国,海南省,儋州市,红岭农场', '红岭农场', '中国,海南,儋州,红岭农场', '3', '0898', '571733', 'Hongling', 'HL', 'H', '109.098479', '19.49571'),
('2552', '469001', '460000', '五指山市', '中国,海南省,五指山市', '五指山', '中国,海南,五指山', '2', '0898', '572200', 'Wuzhishan', 'WZS', 'W', '109.516662', '18.776921'),
('2553', '469002', '460000', '琼海市', '中国,海南省,琼海市', '琼海', '中国,海南,琼海', '2', '0898', '571400', 'Qionghai', 'QH', 'Q', '110.466785', '19.246011'),
('2554', '469005', '460000', '文昌市', '中国,海南省,文昌市', '文昌', '中国,海南,文昌', '2', '0898', '571300', 'Wenchang', 'WC', 'W', '110.753975', '19.612986'),
('2555', '469006', '460000', '万宁市', '中国,海南省,万宁市', '万宁', '中国,海南,万宁', '2', '0898', '571500', 'Wanning', 'WN', 'W', '110.388793', '18.796216'),
('2556', '469007', '460000', '东方市', '中国,海南省,东方市', '东方', '中国,海南,东方', '2', '0898', '572600', 'Dongfang', 'DF', 'D', '108.653789', '19.10198'),
('2557', '469021', '460000', '定安县', '中国,海南省,定安县', '定安', '中国,海南,定安', '2', '0898', '571200', 'Ding\'an', 'DA', 'D', '110.323959', '19.699211'),
('2558', '469022', '460000', '屯昌县', '中国,海南省,屯昌县', '屯昌', '中国,海南,屯昌', '2', '0898', '571600', 'Tunchang', 'TC', 'T', '110.102773', '19.362916'),
('2559', '469023', '460000', '澄迈县', '中国,海南省,澄迈县', '澄迈', '中国,海南,澄迈', '2', '0898', '571900', 'Chengmai', 'CM', 'C', '110.007147', '19.737095'),
('2560', '469024', '460000', '临高县', '中国,海南省,临高县', '临高', '中国,海南,临高', '2', '0898', '571800', 'Lingao', 'LG', 'L', '109.687697', '19.908293'),
('2561', '469025', '460000', '白沙黎族自治县', '中国,海南省,白沙黎族自治县', '白沙', '中国,海南,白沙', '2', '0898', '572800', 'Baisha', 'BS', 'B', '109.452606', '19.224584'),
('2562', '469026', '460000', '昌江黎族自治县', '中国,海南省,昌江黎族自治县', '昌江', '中国,海南,昌江', '2', '0898', '572700', 'Changjiang', 'CJ', 'C', '109.053351', '19.260968'),
('2563', '469027', '460000', '乐东黎族自治县', '中国,海南省,乐东黎族自治县', '乐东', '中国,海南,乐东', '2', '0898', '572500', 'Ledong', 'LD', 'L', '109.175444', '18.74758'),
('2564', '469028', '460000', '陵水黎族自治县', '中国,海南省,陵水黎族自治县', '陵水', '中国,海南,陵水', '2', '0898', '572400', 'Lingshui', 'LS', 'L', '110.037218', '18.505006'),
('2565', '469029', '460000', '保亭黎族苗族自治县', '中国,海南省,保亭黎族苗族自治县', '保亭', '中国,海南,保亭', '2', '0898', '572300', 'Baoting', 'BT', 'B', '109.70245', '18.636371'),
('2566', '469030', '460000', '琼中黎族苗族自治县', '中国,海南省,琼中黎族苗族自治县', '琼中', '中国,海南,琼中', '2', '0898', '572900', 'Qiongzhong', 'QZ', 'Q', '109.839996', '19.03557'),
('2567', '469101', '469001', '通什镇', '中国,海南省,五指山市,通什镇', '通什镇', '中国,海南,五指山,通什镇', '3', '0898', '572299', 'Tongshi', 'TS', 'T', '109.517189', '18.780692'),
('2568', '469102', '469001', '南圣镇', '中国,海南省,五指山市,南圣镇', '南圣镇', '中国,海南,五指山,南圣镇', '3', '0898', '572219', 'Nansheng', 'NS', 'N', '109.592708', '18.738438'),
('2569', '469103', '469001', '毛阳镇', '中国,海南省,五指山市,毛阳镇', '毛阳镇', '中国,海南,五指山,毛阳镇', '3', '0898', '572214', 'Maoyang', 'MY', 'M', '109.50804', '18.936964'),
('2570', '469104', '469001', '番阳镇', '中国,海南省,五指山市,番阳镇', '番阳镇', '中国,海南,五指山,番阳镇', '3', '0898', '572213', 'Panyang', 'PY', 'P', '109.39785', '18.874421'),
('2571', '469105', '469001', '畅好乡', '中国,海南省,五指山市,畅好乡', '畅好乡', '中国,海南,五指山,畅好乡', '3', '0898', '572218', 'Changhao', 'CH', 'C', '109.487407', '18.733684'),
('2572', '469106', '469001', '毛道乡', '中国,海南省,五指山市,毛道乡', '毛道乡', '中国,海南,五指山,毛道乡', '3', '0898', '572217', 'Maodao', 'MD', 'M', '109.410699', '18.791255'),
('2573', '469107', '469001', '水满乡', '中国,海南省,五指山市,水满乡', '水满乡', '中国,海南,五指山,水满乡', '3', '0898', '572215', 'Shuiman', 'SM', 'S', '109.667011', '18.881007'),
('2574', '469201', '469002', '嘉积镇', '中国,海南省,琼海市,嘉积镇', '嘉积镇', '中国,海南,琼海,嘉积镇', '3', '0898', '571400', 'Jiaji', 'JJ', 'J', '110.485992', '19.242966'),
('2575', '469202', '469002', '万泉镇', '中国,海南省,琼海市,万泉镇', '万泉镇', '中国,海南,琼海,万泉镇', '3', '0898', '571421', 'Wanqua', 'WQ', 'W', '110.409471', '19.243028'),
('2576', '469203', '469002', '石壁镇', '中国,海南省,琼海市,石壁镇', '石壁镇', '中国,海南,琼海,石壁镇', '3', '0898', '571400', 'Shibi', 'SB', 'S', '110.308525', '19.163306'),
('2577', '469204', '469002', '中原镇', '中国,海南省,琼海市,中原镇', '中原镇', '中国,海南,琼海,中原镇', '3', '0898', '571447', 'Zhongyuan', 'ZY', 'Z', '110.468484', '19.151827'),
('2578', '469205', '469002', '博鳌镇', '中国,海南省,琼海市,博鳌镇', '博鳌镇', '中国,海南,琼海,博鳌镇', '3', '0898', '571434', 'Bo\'ao', 'BA', 'B', '110.586613', '19.159782'),
('2579', '469206', '469002', '阳江镇', '中国,海南省,琼海市,阳江镇', '阳江镇', '中国,海南,琼海,阳江镇', '3', '0898', '571441', 'Yangjiang', 'YJ', 'Y', '110.352241', '19.096958'),
('2580', '469207', '469002', '龙江镇', '中国,海南省,琼海市,龙江镇', '龙江镇', '中国,海南,琼海,龙江镇', '3', '0898', '571446', 'Longjiang', 'LJ', 'L', '110.324964', '19.146787'),
('2581', '469208', '469002', '潭门镇', '中国,海南省,琼海市,潭门镇', '潭门镇', '中国,海南,琼海,潭门镇', '3', '0898', '571431', 'Tanmen', 'TM', 'T', '110.616959', '19.241715'),
('2582', '469209', '469002', '塔洋镇', '中国,海南省,琼海市,塔洋镇', '塔洋镇', '中国,海南,琼海,塔洋镇', '3', '0898', '571427', 'Tayang', 'TY', 'T', '110.508274', '19.288197'),
('2583', '469210', '469002', '长坡镇', '中国,海南省,琼海市,长坡镇', '长坡镇', '中国,海南,琼海,长坡镇', '3', '0898', '571429', 'Changpo', 'CP', 'C', '110.606779', '19.335589'),
('2584', '469211', '469002', '大路镇', '中国,海南省,琼海市,大路镇', '大路镇', '中国,海南,琼海,大路镇', '3', '0898', '571425', 'Dalu', 'DL', 'D', '110.476469', '19.381023'),
('2585', '469212', '469002', '会山镇', '中国,海南省,琼海市,会山镇', '会山镇', '中国,海南,琼海,会山镇', '3', '0898', '571444', 'Huishan', 'HS', 'H', '110.269379', '19.067147'),
('2586', '469213', '469002', '东太农场', '中国,海南省,琼海市,东太农场', '东太农场', '中国,海南,琼海,东太农场', '3', '0898', '571445', 'Dongtai', 'DT', 'D', '110.219783', '19.122067'),
('2587', '469214', '469002', '东红农场', '中国,海南省,琼海市,东红农场', '东红农场', '中国,海南,琼海,东红农场', '3', '0898', '571445', 'Donghong', 'DH', 'D', '110.448472', '19.400257'),
('2588', '469215', '469002', '东升农场', '中国,海南省,琼海市,东升农场', '东升农场', '中国,海南,琼海,东升农场', '3', '0898', '571422', 'Dongsheng', 'DS', 'D', '110.408028', '19.297574'),
('2589', '469216', '469002', '南俸农场', '中国,海南省,琼海市,南俸农场', '南俸农场', '中国,海南,琼海,南俸农场', '3', '0898', '571448', 'Nanfeng', 'NF', 'N', '110.22289', '19.173099'),
('2590', '469217', '469002', '彬村山华侨农场', '中国,海南省,琼海市,彬村山华侨农场', '彬村山华侨农场', '中国,海南,琼海,彬村山华侨农场', '3', '0898', '571400', 'Huaqiao', 'HQ', 'H', '110.571208', '19.309458'),
('2591', '469501', '469005', '文城镇', '中国,海南省,文昌市,文城镇', '文城镇', '中国,海南,文昌,文城镇', '3', '0898', '571399', 'Wencheng', 'WC', 'W', '110.753868', '19.613781'),
('2592', '469502', '469005', '重兴镇', '中国,海南省,文昌市,重兴镇', '重兴镇', '中国,海南,文昌,重兴镇', '3', '0898', '571344', 'Zhongxing', 'ZX', 'Z', '110.59676', '19.411623'),
('2593', '469503', '469005', '蓬莱镇', '中国,海南省,文昌市,蓬莱镇', '蓬莱镇', '中国,海南,文昌,蓬莱镇', '3', '0898', '571345', 'Penglai', 'PL', 'P', '110.541209', '19.536766'),
('2594', '469504', '469005', '会文镇', '中国,海南省,文昌市,会文镇', '会文镇', '中国,海南,文昌,会文镇', '3', '0898', '571343', 'Huiwen', 'HW', 'H', '110.731756', '19.462573'),
('2595', '469505', '469005', '东路镇', '中国,海南省,文昌市,东路镇', '东路镇', '中国,海南,文昌,东路镇', '3', '0898', '571348', 'Donglu', 'DL', 'D', '110.706497', '19.791949'),
('2596', '469506', '469005', '潭牛镇', '中国,海南省,文昌市,潭牛镇', '潭牛镇', '中国,海南,文昌,潭牛镇', '3', '0898', '571349', 'Tanniu', 'TN', 'T', '110.735357', '19.710084'),
('2597', '469507', '469005', '东阁镇', '中国,海南省,文昌市,东阁镇', '东阁镇', '中国,海南,文昌,东阁镇', '3', '0898', '571336', 'Dongge', 'DG', 'D', '110.849212', '19.656873'),
('2598', '469508', '469005', '文教镇', '中国,海南省,文昌市,文教镇', '文教镇', '中国,海南,文昌,文教镇', '3', '0898', '571335', 'Wenjiao', 'WJ', 'W', '110.914491', '19.66635'),
('2599', '469509', '469005', '东郊镇', '中国,海南省,文昌市,东郊镇', '东郊镇', '中国,海南,文昌,东郊镇', '3', '0898', '571334', 'Dongjiao', 'DJ', 'D', '110.851718', '19.573169'),
('2600', '469510', '469005', '龙楼镇', '中国,海南省,文昌市,龙楼镇', '龙楼镇', '中国,海南,文昌,龙楼镇', '3', '0898', '571333', 'Longlou', 'LL', 'L', '110.968601', '19.653286'),
('2601', '469511', '469005', '昌洒镇', '中国,海南省,文昌市,昌洒镇', '昌洒镇', '中国,海南,文昌,昌洒镇', '3', '0898', '571332', 'Changsa', 'CS', 'C', '110.900496', '19.76179'),
('2602', '469512', '469005', '翁田镇', '中国,海南省,文昌市,翁田镇', '翁田镇', '中国,海南,文昌,翁田镇', '3', '0898', '571328', 'Wengtian', 'WT', 'W', '110.880134', '19.931466'),
('2603', '469513', '469005', '抱罗镇', '中国,海南省,文昌市,抱罗镇', '抱罗镇', '中国,海南,文昌,抱罗镇', '3', '0898', '571326', 'Baoluo', 'BL', 'B', '110.748934', '19.843477'),
('2604', '469514', '469005', '冯坡镇', '中国,海南省,文昌市,冯坡镇', '冯坡镇', '中国,海南,文昌,冯坡镇', '3', '0898', '571327', 'Fengpo', 'FP', 'F', '110.783952', '19.96465'),
('2605', '469515', '469005', '锦山镇', '中国,海南省,文昌市,锦山镇', '锦山镇', '中国,海南,文昌,锦山镇', '3', '0898', '571323', 'Jinshan', 'JS', 'J', '110.69774', '19.995546'),
('2606', '469516', '469005', '铺前镇', '中国,海南省,文昌市,铺前镇', '铺前镇', '中国,海南,文昌,铺前镇', '3', '0898', '571322', 'Putian', 'PT', 'P', '110.580003', '20.023731'),
('2607', '469517', '469005', '公坡镇', '中国,海南省,文昌市,公坡镇', '公坡镇', '中国,海南,文昌,公坡镇', '3', '0898', '571331', 'Gongpo', 'GP', 'G', '110.808679', '19.789147'),
('2608', '469518', '469005', '迈号镇', '中国,海南省,文昌市,迈号镇', '迈号镇', '中国,海南,文昌,迈号镇', '3', '0898', '571341', 'Maihao', 'MH', 'M', '110.750639', '19.536094'),
('2609', '469519', '469005', '清谰镇', '中国,海南省,文昌市,清谰镇', '清谰镇', '中国,海南,文昌,清谰镇', '3', '0898', '571300', 'Qinglan', 'QL', 'Q', '110.819324', '19.562616'),
('2610', '469520', '469005', '南阳镇', '中国,海南省,文昌市,南阳镇', '南阳镇', '中国,海南,文昌,南阳镇', '3', '0898', '571300', 'Nanyang', 'NY', 'N', '110.651269', '19.58414'),
('2611', '469521', '469005', '新桥镇', '中国,海南省,文昌市,新桥镇', '新桥镇', '中国,海南,文昌,新桥镇', '3', '0898', '571347', 'Xinqiao', 'XQ', 'X', '110.685505', '19.654715'),
('2612', '469522', '469005', '头苑镇', '中国,海南省,文昌市,头苑镇', '头苑镇', '中国,海南,文昌,头苑镇', '3', '0898', '571338', 'Touyuan', 'TY', 'T', '110.78784', '19.63677'),
('2613', '469523', '469005', '宝芳乡', '中国,海南省,文昌市,宝芳乡', '宝芳乡', '中国,海南,文昌,宝芳乡', '3', '0898', '571337', 'Baofang', 'BF', 'B', '110.82032', '19.71475'),
('2614', '469524', '469005', '龙马乡', '中国,海南省,文昌市,龙马乡', '龙马乡', '中国,海南,文昌,龙马乡', '3', '0898', '571329', 'Longma', 'LM', 'L', '110.955913', '19.867553'),
('2615', '469525', '469005', '湖山乡', '中国,海南省,文昌市,湖山乡', '湖山乡', '中国,海南,文昌,湖山乡', '3', '0898', '571325', 'Hushan', 'HS', 'H', '110.7073', '19.92081'),
('2616', '469526', '469005', '东路农场', '中国,海南省,文昌市,东路农场', '东路农场', '中国,海南,文昌,东路农场', '3', '0898', '571300', 'Donglu', 'DL', 'D', '110.680672', '19.735311'),
('2617', '469527', '469005', '南阳农场', '中国,海南省,文昌市,南阳农场', '南阳农场', '中国,海南,文昌,南阳农场', '3', '0898', '571300', 'Nanyang', 'NY', 'N', '110.653592', '19.564403'),
('2618', '469528', '469005', '罗豆农场', '中国,海南省,文昌市,罗豆农场', '罗豆农场', '中国,海南,文昌,罗豆农场', '3', '0898', '571300', 'Luodou', 'LD', 'L', '110.641659', '19.972489'),
('2619', '469601', '469006', '万城镇', '中国,海南省,万宁市,万城镇', '万城镇', '中国,海南,万宁,万城镇', '3', '0898', '571599', 'Wancheng', 'WC', 'W', '110.397028', '18.794435'),
('2620', '469602', '469006', '龙滚镇', '中国,海南省,万宁市,龙滚镇', '龙滚镇', '中国,海南,万宁,龙滚镇', '3', '0898', '571521', 'Longgun', 'LG', 'L', '110.519208', '19.058665'),
('2621', '469603', '469006', '和乐镇', '中国,海南省,万宁市,和乐镇', '和乐镇', '中国,海南,万宁,和乐镇', '3', '0898', '571523', 'Hele', 'HL', 'H', '110.477642', '18.903521'),
('2622', '469604', '469006', '后安镇', '中国,海南省,万宁市,后安镇', '后安镇', '中国,海南,万宁,后安镇', '3', '0898', '571525', 'Hou\'an', 'HA', 'H', '110.4437', '18.868013'),
('2623', '469605', '469006', '大茂镇', '中国,海南省,万宁市,大茂镇', '大茂镇', '中国,海南,万宁,大茂镇', '3', '0898', '571541', 'Damao', 'DM', 'D', '110.39644', '18.848965'),
('2624', '469606', '469006', '东澳镇', '中国,海南省,万宁市,东澳镇', '东澳镇', '中国,海南,万宁,东澳镇', '3', '0898', '571528', 'Dong\'ao', 'DA', 'D', '110.40168', '18.714824'),
('2625', '469607', '469006', '礼纪镇', '中国,海南省,万宁市,礼纪镇', '礼纪镇', '中国,海南,万宁,礼纪镇', '3', '0898', '571529', 'Liji', 'LJ', 'L', '110.317591', '18.750439'),
('2626', '469608', '469006', '长丰镇', '中国,海南省,万宁市,长丰镇', '长丰镇', '中国,海南,万宁,长丰镇', '3', '0898', '571535', 'Changfeng', 'CF', 'C', '110.331725', '18.799832'),
('2627', '469609', '469006', '山根镇', '中国,海南省,万宁市,山根镇', '山根镇', '中国,海南,万宁,山根镇', '3', '0898', '571522', 'Shangen', 'SG', 'S', '110.483442', '18.963334'),
('2628', '469610', '469006', '北大镇', '中国,海南省,万宁市,北大镇', '北大镇', '中国,海南,万宁,北大镇', '3', '0898', '571539', 'Beida', 'BD', 'B', '110.33561', '18.917983'),
('2629', '469611', '469006', '南桥镇', '中国,海南省,万宁市,南桥镇', '南桥镇', '中国,海南,万宁,南桥镇', '3', '0898', '571532', 'Nanqiao', 'NQ', 'N', '110.15279', '18.680257'),
('2630', '469612', '469006', '三更罗镇', '中国,海南省,万宁市,三更罗镇', '三更罗镇', '中国,海南,万宁,三更罗镇', '3', '0898', '571536', 'Sangengluo', 'SGL', 'S', '110.18663', '18.8612'),
('2631', '469613', '469006', '东岭农场', '中国,海南省,万宁市,东岭农场', '东岭农场', '中国,海南,万宁,东岭农场', '3', '0898', '571544', 'Dongning', 'DN', 'D', '110.382013', '18.979333'),
('2632', '469614', '469006', '南林农场', '中国,海南省,万宁市,南林农场', '南林农场', '中国,海南,万宁,南林农场', '3', '0898', '571532', 'Nanlin', 'NL', 'N', '110.158181', '18.682322'),
('2633', '469615', '469006', '东兴农场', '中国,海南省,万宁市,东兴农场', '东兴农场', '中国,海南,万宁,东兴农场', '3', '0898', '571539', 'Dongxing', 'DX', 'D', '110.33422', '18.928903'),
('2634', '469616', '469006', '东和农场', '中国,海南省,万宁市,东和农场', '东和农场', '中国,海南,万宁,东和农场', '3', '0898', '571534', 'Donghe', 'DH', 'D', '110.261509', '18.780627'),
('2635', '469617', '469006', '新中农场', '中国,海南省,万宁市,新中农场', '新中农场', '中国,海南,万宁,新中农场', '3', '0898', '571536', 'Xinzhong', 'XZ', 'X', '110.194651', '18.853817'),
('2636', '469618', '469006', '兴隆华侨农场', '中国,海南省,万宁市,兴隆华侨农场', '兴隆华侨农场', '中国,海南,万宁,兴隆华侨农场', '3', '0898', '571533', 'Xinglong', 'XL', 'X', '110.211072', '18.74816'),
('2637', '469701', '469007', '八所镇', '中国,海南省,东方市,八所镇', '八所镇', '中国,海南,东方,八所镇', '3', '0898', '572699', 'Basuo', 'BS', 'B', '108.670604', '19.092246'),
('2638', '469702', '469007', '东河镇', '中国,海南省,东方市,东河镇', '东河镇', '中国,海南,东方,东河镇', '3', '0898', '572626', 'Donghe', 'DH', 'D', '108.938223', '19.04824'),
('2639', '469703', '469007', '大田镇', '中国,海南省,东方市,大田镇', '大田镇', '中国,海南,东方,大田镇', '3', '0898', '572624', 'Datian', 'DT', 'D', '108.874903', '19.16498'),
('2640', '469704', '469007', '感城镇', '中国,海南省,东方市,感城镇', '感城镇', '中国,海南,东方,感城镇', '3', '0898', '572633', 'Gancheng', 'GC', 'G', '108.647432', '18.847602'),
('2641', '469705', '469007', '板桥镇', '中国,海南省,东方市,板桥镇', '板桥镇', '中国,海南,东方,板桥镇', '3', '0898', '572634', 'Banqiao', 'BQ', 'B', '108.688613', '18.794859'),
('2642', '469706', '469007', '三家镇', '中国,海南省,东方市,三家镇', '三家镇', '中国,海南,东方,三家镇', '3', '0898', '572623', 'Sanjia', 'SJ', 'S', '108.758458', '19.24124'),
('2643', '469707', '469007', '四更镇', '中国,海南省,东方市,四更镇', '四更镇', '中国,海南,东方,四更镇', '3', '0898', '572622', 'Sigeng', 'SG', 'S', '108.682123', '19.225622'),
('2644', '469708', '469007', '新龙镇', '中国,海南省,东方市,新龙镇', '新龙镇', '中国,海南,东方,新龙镇', '3', '0898', '572632', 'Xinlong', 'XL', 'X', '108.684546', '18.952031'),
('2645', '469709', '469007', '天安乡', '中国,海南省,东方市,天安乡', '天安乡', '中国,海南,东方,天安乡', '3', '0898', '572626', 'Tian\'an', 'TA', 'T', '108.912755', '19.017447'),
('2646', '469710', '469007', '江边乡', '中国,海南省,东方市,江边乡', '江边乡', '中国,海南,东方,江边乡', '3', '0898', '572628', 'Jiangbian', 'JB', 'J', '108.977135', '18.894377'),
('2647', '469711', '469007', '广坝农场', '中国,海南省,东方市,广坝农场', '广坝农场', '中国,海南,东方,广坝农场', '3', '0898', '572626', 'Guangba', 'GB', 'G', '108.945609', '19.043627'),
('2648', '469712', '469007', '东方华侨农场', '中国,海南省,东方市,东方华侨农场', '东方华侨农场', '中国,海南,东方,东方华侨农场', '3', '0898', '572632', 'Dongfang', 'DF', 'D', '108.690697', '18.982529'),
('2649', '469801', '469021', '定城镇', '中国,海南省,定安县,定城镇', '定城镇', '中国,海南,定安,定城镇', '3', '0898', '571299', 'Dingcheng', 'DC', 'D', '110.368429', '19.680305'),
('2650', '469802', '469021', '新竹镇', '中国,海南省,定安县,新竹镇', '新竹镇', '中国,海南,定安,新竹镇', '3', '0898', '571236', 'Xinzhu', 'XZ', 'X', '110.208963', '19.617783'),
('2651', '469803', '469021', '龙湖镇', '中国,海南省,定安县,龙湖镇', '龙湖镇', '中国,海南,定安,龙湖镇', '3', '0898', '571222', 'Longhu', 'LH', 'L', '110.401099', '19.573183'),
('2652', '469804', '469021', '雷鸣镇', '中国,海南省,定安县,雷鸣镇', '雷鸣镇', '中国,海南,定安,雷鸣镇', '3', '0898', '571225', 'Leiming', 'LM', 'L', '110.326015', '19.553654'),
('2653', '469805', '469021', '龙门镇', '中国,海南省,定安县,龙门镇', '龙门镇', '中国,海南,定安,龙门镇', '3', '0898', '571226', 'Longmen', 'LM', 'L', '110.328023', '19.43916'),
('2654', '469806', '469021', '龙河镇', '中国,海南省,定安县,龙河镇', '龙河镇', '中国,海南,定安,龙河镇', '3', '0898', '571231', 'Longhe', 'LH', 'L', '110.219091', '19.381868'),
('2655', '469807', '469021', '岭口镇', '中国,海南省,定安县,岭口镇', '岭口镇', '中国,海南,定安,岭口镇', '3', '0898', '571227', 'Lingkou', 'LK', 'L', '110.308233', '19.342639'),
('2656', '469808', '469021', '翰林镇', '中国,海南省,定安县,翰林镇', '翰林镇', '中国,海南,定安,翰林镇', '3', '0898', '571228', 'Hanlin', 'HL', 'H', '110.264731', '19.333758'),
('2657', '469809', '469021', '富文镇', '中国,海南省,定安县,富文镇', '富文镇', '中国,海南,定安,富文镇', '3', '0898', '571234', 'Fuwen', 'FW', 'F', '110.2625', '19.551144'),
('2658', '469810', '469021', '黄竹镇', '中国,海南省,定安县,黄竹镇', '黄竹镇', '中国,海南,定安,黄竹镇', '3', '0898', '571224', 'Huangzhu', 'HZ', 'H', '110.446722', '19.472782'),
('2659', '469811', '469021', '金鸡岭农场', '中国,海南省,定安县,金鸡岭农场', '金鸡岭农场', '中国,海南,定安,金鸡岭农场', '3', '0898', '571200', 'Jinjiling', 'JJL', 'J', '110.260688', '19.593328'),
('2660', '469812', '469021', '中瑞农场', '中国,海南省,定安县,中瑞农场', '中瑞农场', '中国,海南,定安,中瑞农场', '3', '0898', '571200', 'Zhongrui', 'ZR', 'Z', '110.307332', '19.250472'),
('2661', '469813', '469021', '南海农场', '中国,海南省,定安县,南海农场', '南海农场', '中国,海南,定安,南海农场', '3', '0898', '571200', 'Nanhai', 'NH', 'N', '110.446606', '19.471486'),
('2662', '469814', '469021', '城区', '中国,海南省,定安县,城区', '城区', '中国,海南,定安,城区', '3', '0898', '571200', 'Chengqu', 'CQ', 'C', '110.358891', '19.681434'),
('2663', '469821', '469022', '屯城镇', '中国,海南省,屯昌县,屯城镇', '屯城镇', '中国,海南,屯昌,屯城镇', '3', '0898', '571699', 'Tuncheng', 'TC', 'T', '110.104886', '19.371561'),
('2664', '469822', '469022', '新兴镇', '中国,海南省,屯昌县,新兴镇', '新兴镇', '中国,海南,屯昌,新兴镇', '3', '0898', '571621', 'Xinxing', 'XX', 'X', '110.182802', '19.506743'),
('2665', '469823', '469022', '枫木镇', '中国,海南省,屯昌县,枫木镇', '枫木镇', '中国,海南,屯昌,枫木镇', '3', '0898', '571627', 'Fengmu', 'FM', 'F', '110.019489', '19.214453'),
('2666', '469824', '469022', '乌坡镇', '中国,海南省,屯昌县,乌坡镇', '乌坡镇', '中国,海南,屯昌,乌坡镇', '3', '0898', '571626', 'Wupo', 'WP', 'W', '110.075965', '19.180023'),
('2667', '469825', '469022', '南吕镇', '中国,海南省,屯昌县,南吕镇', '南吕镇', '中国,海南,屯昌,南吕镇', '3', '0898', '571625', 'Nanlv', 'NL', 'N', '110.08538', '19.247382'),
('2668', '469826', '469022', '南坤镇', '中国,海南省,屯昌县,南坤镇', '南坤镇', '中国,海南,屯昌,南坤镇', '3', '0898', '571632', 'Nankun', 'NK', 'N', '109.948276', '19.318366'),
('2669', '469827', '469022', '坡心镇', '中国,海南省,屯昌县,坡心镇', '坡心镇', '中国,海南,屯昌,坡心镇', '3', '0898', '571629', 'Poxin', 'PX', 'P', '110.08987', '19.30706'),
('2670', '469828', '469022', '西昌镇', '中国,海南省,屯昌县,西昌镇', '西昌镇', '中国,海南,屯昌,西昌镇', '3', '0898', '571636', 'Xichang', 'XC', 'X', '110.0455', '19.433661'),
('2671', '469829', '469022', '中建农场', '中国,海南省,屯昌县,中建农场', '中建农场', '中国,海南,屯昌,中建农场', '3', '0898', '571624', 'Zhongjian', 'ZJ', 'Z', '110.170496', '19.284146'),
('2672', '469830', '469022', '中坤农场', '中国,海南省,屯昌县,中坤农场', '中坤农场', '中国,海南,屯昌,中坤农场', '3', '0898', '571600', 'Zhongkun', 'ZK', 'Z', '109.950409', '19.316375'),
('2673', '469831', '469022', '县城内', '中国,海南省,屯昌县,县城内', '县城内', '中国,海南,屯昌,县城内', '3', '0898', '571600', 'Xiancheng', 'XC', 'X', '110.10296', '19.360948'),
('2674', '469841', '469023', '金江镇', '中国,海南省,澄迈县,金江镇', '金江镇', '中国,海南,澄迈,金江镇', '3', '0898', '571999', 'Jinjiang', 'JJ', 'J', '110.010211', '19.736761'),
('2675', '469842', '469023', '老城镇', '中国,海南省,澄迈县,老城镇', '老城镇', '中国,海南,澄迈,老城镇', '3', '0898', '571924', 'Laocheng', 'LC', 'L', '110.125883', '19.961858'),
('2676', '469843', '469023', '瑞溪镇', '中国,海南省,澄迈县,瑞溪镇', '瑞溪镇', '中国,海南,澄迈,瑞溪镇', '3', '0898', '571933', 'Ruixi', 'RX', 'R', '110.134169', '19.731674'),
('2677', '469844', '469023', '永发镇', '中国,海南省,澄迈县,永发镇', '永发镇', '中国,海南,澄迈,永发镇', '3', '0898', '571929', 'Yongfa', 'YF', 'Y', '110.19532', '19.747359'),
('2678', '469845', '469023', '加乐镇', '中国,海南省,澄迈县,加乐镇', '加乐镇', '中国,海南,澄迈,加乐镇', '3', '0898', '571938', 'Jiale', 'JL', 'J', '110.001059', '19.584998'),
('2679', '469846', '469023', '文儒镇', '中国,海南省,澄迈县,文儒镇', '文儒镇', '中国,海南,澄迈,文儒镇', '3', '0898', '571937', 'Wenru', 'WR', 'W', '110.053169', '19.53708'),
('2680', '469847', '469023', '中兴镇', '中国,海南省,澄迈县,中兴镇', '中兴镇', '中国,海南,澄迈,中兴镇', '3', '0898', '571944', 'Zhongxing', 'ZX', 'Z', '109.861281', '19.564772'),
('2681', '469848', '469023', '仁兴镇', '中国,海南省,澄迈县,仁兴镇', '仁兴镇', '中国,海南,澄迈,仁兴镇', '3', '0898', '571941', 'Renxing', 'RX', 'R', '109.882645', '19.492122'),
('2682', '469849', '469023', '福山镇', '中国,海南省,澄迈县,福山镇', '福山镇', '中国,海南,澄迈,福山镇', '3', '0898', '571921', 'Fushan', 'FS', 'F', '109.930084', '19.830219'),
('2683', '469850', '469023', '桥头镇', '中国,海南省,澄迈县,桥头镇', '桥头镇', '中国,海南,澄迈,桥头镇', '3', '0898', '571922', 'Qiaotou', 'QT', 'Q', '109.933531', '19.954138'),
('2684', '469851', '469023', '大丰镇', '中国,海南省,澄迈县,大丰镇', '大丰镇', '中国,海南,澄迈,大丰镇', '3', '0898', '571926', 'Dafeng', 'DF', 'D', '110.038147', '19.855536'),
('2685', '469852', '469023', '红光农场', '中国,海南省,澄迈县,红光农场', '红光农场', '中国,海南,澄迈,红光农场', '3', '0898', '571921', 'Hongguang', 'HG', 'H', '109.920632', '19.837845'),
('2686', '469853', '469023', '西达农场', '中国,海南省,澄迈县,西达农场', '西达农场', '中国,海南,澄迈,西达农场', '3', '0898', '571941', 'Xida', 'XD', 'X', '109.88532', '19.483568'),
('2687', '469854', '469023', '金安农场', '中国,海南省,澄迈县,金安农场', '金安农场', '中国,海南,澄迈,金安农场', '3', '0898', '571932', 'Jin\'an', 'JA', 'J', '110.108668', '19.766615'),
('2688', '469855', '469023', '城区', '中国,海南省,澄迈县,城区', '城区', '中国,海南,澄迈,城区', '3', '0898', '571900', 'Chengqu', 'CQ', 'C', '110.006755', '19.738521'),
('2689', '469861', '469024', '临城镇', '中国,海南省,临高县,临城镇', '临城镇', '中国,海南,临高,临城镇', '3', '0898', '571899', 'Lincheng', 'LC', 'L', '109.696445', '19.896521'),
('2690', '469862', '469024', '波莲镇', '中国,海南省,临高县,波莲镇', '波莲镇', '中国,海南,临高,波莲镇', '3', '0898', '571834', 'Bolian', 'BL', 'B', '109.648489', '19.868223'),
('2691', '469863', '469024', '东英镇', '中国,海南省,临高县,东英镇', '东英镇', '中国,海南,临高,东英镇', '3', '0898', '571837', 'Dongying', 'DY', 'D', '109.648634', '19.959022'),
('2692', '469864', '469024', '博厚镇', '中国,海南省,临高县,博厚镇', '博厚镇', '中国,海南,临高,博厚镇', '3', '0898', '571821', 'Bohou', 'BH', 'B', '109.745591', '19.88374'),
('2693', '469865', '469024', '皇桐镇', '中国,海南省,临高县,皇桐镇', '皇桐镇', '中国,海南,临高,皇桐镇', '3', '0898', '571823', 'Huangtong', 'HT', 'H', '109.849709', '19.832893'),
('2694', '469866', '469024', '多文镇', '中国,海南省,临高县,多文镇', '多文镇', '中国,海南,临高,多文镇', '3', '0898', '571825', 'Duowen', 'DW', 'D', '109.771058', '19.773837'),
('2695', '469867', '469024', '和舍镇', '中国,海南省,临高县,和舍镇', '和舍镇', '中国,海南,临高,和舍镇', '3', '0898', '571831', 'Heshe', 'HS', 'H', '109.727556', '19.596267'),
('2696', '469868', '469024', '南宝镇', '中国,海南省,临高县,南宝镇', '南宝镇', '中国,海南,临高,南宝镇', '3', '0898', '571832', 'Nanbao', 'NB', 'N', '109.599542', '19.680483'),
('2697', '469869', '469024', '新盈镇', '中国,海南省,临高县,新盈镇', '新盈镇', '中国,海南,临高,新盈镇', '3', '0898', '571835', 'Xinying', 'XY', 'X', '109.536349', '19.89513'),
('2698', '469870', '469024', '调楼镇', '中国,海南省,临高县,调楼镇', '调楼镇', '中国,海南,临高,调楼镇', '3', '0898', '571836', 'Tiaolou', 'TL', 'T', '109.544414', '19.935063'),
('2699', '469871', '469024', '加来镇', '中国,海南省,临高县,加来镇', '加来镇', '中国,海南,临高,加来镇', '3', '0898', '571833', 'Jialai', 'JL', 'J', '109.699213', '19.709921'),
('2700', '469872', '469024', '红华农场', '中国,海南省,临高县,红华农场', '红华农场', '中国,海南,临高,红华农场', '3', '0898', '571825', 'Honghua', 'HH', 'H', '109.774558', '19.774451'),
('2701', '469873', '469024', '加来农场', '中国,海南省,临高县,加来农场', '加来农场', '中国,海南,临高,加来农场', '3', '0898', '571833', 'Jialai', 'JL', 'J', '109.700217', '19.705881'),
('2702', '469874', '469024', '城区', '中国,海南省,临高县,城区', '城区', '中国,海南,临高,城区', '3', '0898', '571800', 'Chengqu', 'CQ', 'C', '109.690508', '19.912026'),
('2703', '469881', '469025', '牙叉镇', '中国,海南省,白沙黎族自治县,牙叉镇', '牙叉镇', '中国,海南,白沙,牙叉镇', '3', '0898', '572800', 'Yacha', 'YC', 'Y', '109.445361', '19.212209'),
('2704', '469882', '469025', '七坊镇', '中国,海南省,白沙黎族自治县,七坊镇', '七坊镇', '中国,海南,白沙,七坊镇', '3', '0898', '572818', 'Qifang', 'QF', 'Q', '109.244488', '19.295347'),
('2705', '469883', '469025', '邦溪镇', '中国,海南省,白沙黎族自治县,邦溪镇', '邦溪镇', '中国,海南,白沙,邦溪镇', '3', '0898', '572821', 'Bangxi', 'BX', 'B', '109.103545', '19.369532'),
('2706', '469884', '469025', '打安镇', '中国,海南省,白沙黎族自治县,打安镇', '打安镇', '中国,海南,白沙,打安镇', '3', '0898', '572828', 'Da\'an', 'DA', 'D', '109.373669', '19.283888'),
('2707', '469885', '469025', '细水乡', '中国,海南省,白沙黎族自治县,细水乡', '细水乡', '中国,海南,白沙,细水乡', '3', '0898', '572811', 'Xishui', 'XS', 'X', '109.568501', '19.206414'),
('2708', '469886', '469025', '元门乡', '中国,海南省,白沙黎族自治县,元门乡', '元门乡', '中国,海南,白沙,元门乡', '3', '0898', '572813', 'Yuanmen', 'YM', 'Y', '109.486456', '19.158071'),
('2709', '469887', '469025', '南开乡', '中国,海南省,白沙黎族自治县,南开乡', '南开乡', '中国,海南,白沙,南开乡', '3', '0898', '572814', 'Nankai', 'NK', 'N', '109.418377', '19.076943'),
('2710', '469888', '469025', '阜龙乡', '中国,海南省,白沙黎族自治县,阜龙乡', '阜龙乡', '中国,海南,白沙,阜龙乡', '3', '0898', '572829', 'Fulong', 'FL', 'F', '109.460783', '19.322591'),
('2711', '469889', '469025', '青松乡', '中国,海南省,白沙黎族自治县,青松乡', '青松乡', '中国,海南,白沙,青松乡', '3', '0898', '572816', 'Qingsong', 'QS', 'Q', '109.277198', '19.154446'),
('2712', '469890', '469025', '金波乡', '中国,海南省,白沙黎族自治县,金波乡', '金波乡', '中国,海南,白沙,金波乡', '3', '0898', '572817', 'Jinbo', 'JB', 'J', '109.178467', '19.236611'),
('2713', '469891', '469025', '荣邦乡', '中国,海南省,白沙黎族自治县,荣邦乡', '荣邦乡', '中国,海南,白沙,荣邦乡', '3', '0898', '572823', 'Rongbang', 'RB', 'R', '109.170729', '19.413953'),
('2714', '469892', '469025', '白沙农场', '中国,海南省,白沙黎族自治县,白沙农场', '白沙农场', '中国,海南,白沙,白沙农场', '3', '0898', '572899', 'Baisha', 'BS', 'B', '109.475216', '19.195136'),
('2715', '469893', '469025', '龙江农场', '中国,海南省,白沙黎族自治县,龙江农场', '龙江农场', '中国,海南,白沙,龙江农场', '3', '0898', '572818', 'Longjiang', 'LJ', 'L', '109.2424', '19.299394'),
('2716', '469894', '469025', '邦溪农场', '中国,海南省,白沙黎族自治县,邦溪农场', '邦溪农场', '中国,海南,白沙,邦溪农场', '3', '0898', '572821', 'Bangxi', 'BX', 'B', '109.127062', '19.403143'),
('2717', '469895', '469025', '城区', '中国,海南省,白沙黎族自治县,城区', '城区', '中国,海南,白沙,城区', '3', '0898', '572800', 'Chengqu', 'CQ', 'C', '109.451484', '19.224823'),
('2718', '469901', '469026', '石碌镇', '中国,海南省,昌江黎族自治县,石碌镇', '石碌镇', '中国,海南,昌江,石碌镇', '3', '0898', '572799', 'Shilu', 'SL', 'S', '109.055817', '19.277558'),
('2719', '469902', '469026', '叉河镇', '中国,海南省,昌江黎族自治县,叉河镇', '叉河镇', '中国,海南,昌江,叉河镇', '3', '0898', '572724', 'Chahe', 'CH', 'C', '108.957345', '19.238934'),
('2720', '469903', '469026', '十月田镇', '中国,海南省,昌江黎族自治县,十月田镇', '十月田镇', '中国,海南,昌江,十月田镇', '3', '0898', '572726', 'Shiyuetian', 'SYT', 'S', '108.95223', '19.328115'),
('2721', '469904', '469026', '乌烈镇', '中国,海南省,昌江黎族自治县,乌烈镇', '乌烈镇', '中国,海南,昌江,乌烈镇', '3', '0898', '572728', 'Wulie', 'WL', 'W', '108.792331', '19.288583'),
('2722', '469905', '469026', '海尾镇', '中国,海南省,昌江黎族自治县,海尾镇', '海尾镇', '中国,海南,昌江,海尾镇', '3', '0898', '572732', 'Haiwei', 'HW', 'H', '108.820294', '19.42556'),
('2723', '469906', '469026', '南罗镇', '中国,海南省,昌江黎族自治县,南罗镇', '南罗镇', '中国,海南,昌江,南罗镇', '3', '0898', '572733', 'Nanluo', 'NL', 'N', '108.95497', '19.4647'),
('2724', '469907', '469026', '太坡镇', '中国,海南省,昌江黎族自治县,太坡镇', '太坡镇', '中国,海南,昌江,太坡镇', '3', '0898', '572799', 'Taipo', 'TP', 'T', '109.040275', '19.405817'),
('2725', '469908', '469026', '昌化镇', '中国,海南省,昌江黎族自治县,昌化镇', '昌化镇', '中国,海南,昌江,昌化镇', '3', '0898', '572731', 'Changhua', 'CH', 'C', '108.685077', '19.32924'),
('2726', '469909', '469026', '七叉镇', '中国,海南省,昌江黎族自治县,七叉镇', '七叉镇', '中国,海南,昌江,七叉镇', '3', '0898', '572722', 'Qicha', 'QC', 'Q', '109.051284', '19.114807'),
('2727', '469910', '469026', '保平乡', '中国,海南省,昌江黎族自治县,保平乡', '保平乡', '中国,海南,昌江,保平乡', '3', '0898', '572726', 'BaoPing', 'BP', 'B', '108.889608', '19.299437'),
('2728', '469911', '469026', '昌城乡', '中国,海南省,昌江黎族自治县,昌城乡', '昌城乡', '中国,海南,昌江,昌城乡', '3', '0898', '572731', 'Changcheng', 'CC', 'C', '108.700489', '19.309607'),
('2729', '469912', '469026', '王下乡', '中国,海南省,昌江黎族自治县,王下乡', '王下乡', '中国,海南,昌江,王下乡', '3', '0898', '572722', 'Wangxia', 'WX', 'W', '109.150473', '19.003399'),
('2730', '469913', '469026', '霸王岭林场', '中国,海南省,昌江黎族自治县,霸王岭林场', '霸王岭林场', '中国,海南,昌江,霸王岭林场', '3', '0898', '572722', 'Bawangling', 'BWL', 'B', '109.146257', '19.069097'),
('2731', '469914', '469026', '红林农场', '中国,海南省,昌江黎族自治县,红林农场', '红林农场', '中国,海南,昌江,红林农场', '3', '0898', '572724', 'Honglin', 'HL', 'H', '109.057676', '19.262656'),
('2732', '469915', '469026', '城区', '中国,海南省,昌江黎族自治县,城区', '城区', '中国,海南,昌江,城区', '3', '0898', '572700', 'Chengqu', 'CQ', 'C', '109.054957', '19.260863'),
('2733', '469920', '469027', '抱由镇', '中国,海南省,乐东黎族自治县,抱由镇', '抱由镇', '中国,海南,乐东,抱由镇', '3', '0898', '572599', 'Baoyou', 'BY', 'B', '109.179537', '18.744783'),
('2734', '469921', '469027', '万冲镇', '中国,海南省,乐东黎族自治县,万冲镇', '万冲镇', '中国,海南,乐东,万冲镇', '3', '0898', '572521', 'Wanchong', 'WC', 'W', '109.322862', '18.844718'),
('2735', '469922', '469027', '大安镇', '中国,海南省,乐东黎族自治县,大安镇', '大安镇', '中国,海南,乐东,大安镇', '3', '0898', '572523', 'Da\'an', 'DA', 'D', '109.214756', '18.691329'),
('2736', '469923', '469027', '志仲镇', '中国,海南省,乐东黎族自治县,志仲镇', '志仲镇', '中国,海南,乐东,志仲镇', '3', '0898', '572524', 'Zhizhong', 'ZZ', 'Z', '109.265118', '18.629242'),
('2737', '469924', '469027', '千家镇', '中国,海南省,乐东黎族自治县,千家镇', '千家镇', '中国,海南,乐东,千家镇', '3', '0898', '572531', 'Qianjia', 'QJ', 'Q', '109.086133', '18.565576'),
('2738', '469925', '469027', '九所镇', '中国,海南省,乐东黎族自治县,九所镇', '九所镇', '中国,海南,乐东,九所镇', '3', '0898', '572533', 'Jiusuo', 'JS', 'J', '108.953917', '18.453489'),
('2739', '469926', '469027', '利国镇', '中国,海南省,乐东黎族自治县,利国镇', '利国镇', '中国,海南,乐东,利国镇', '3', '0898', '572534', 'Liguo', 'LG', 'L', '108.897393', '18.472259'),
('2740', '469927', '469027', '黄流镇', '中国,海南省,乐东黎族自治县,黄流镇', '黄流镇', '中国,海南,乐东,黄流镇', '3', '0898', '572536', 'Huangliu', 'HL', 'H', '108.793131', '18.504591'),
('2741', '469928', '469027', '佛罗镇', '中国,海南省,乐东黎族自治县,佛罗镇', '佛罗镇', '中国,海南,乐东,佛罗镇', '3', '0898', '572541', 'Foluo', 'FL', 'F', '108.736289', '18.578496'),
('2742', '469929', '469027', '尖峰镇', '中国,海南省,乐东黎族自治县,尖峰镇', '尖峰镇', '中国,海南,乐东,尖峰镇', '3', '0898', '572542', 'Jianfeng', 'JF', 'J', '108.792492', '18.690418'),
('2743', '469930', '469027', '莺歌海镇', '中国,海南省,乐东黎族自治县,莺歌海镇', '莺歌海镇', '中国,海南,乐东,莺歌海镇', '3', '0898', '572539', 'Yinggehai', 'YGH', 'Y', '108.697351', '18.510156'),
('2744', '469931', '469027', '乐中农场', '中国,海南省,乐东黎族自治县,乐中农场', '乐中农场', '中国,海南,乐东,乐中农场', '3', '0898', '572522', 'Lezhong', 'LZ', 'L', '109.324863', '18.848094'),
('2745', '469932', '469027', '山荣农场', '中国,海南省,乐东黎族自治县,山荣农场', '山荣农场', '中国,海南,乐东,山荣农场', '3', '0898', '572501', 'Shanrong', 'SR', 'S', '109.16416', '18.769503'),
('2746', '469933', '469027', '乐光农场', '中国,海南省,乐东黎族自治县,乐光农场', '乐光农场', '中国,海南,乐东,乐光农场', '3', '0898', '572529', 'Leguang', 'LG', 'L', '109.115741', '18.624696'),
('2747', '469934', '469027', '报伦农场', '中国,海南省,乐东黎族自治县,报伦农场', '报伦农场', '中国,海南,乐东,报伦农场', '3', '0898', '572543', 'Baolun', 'BL', 'B', '109.050489', '18.591113'),
('2748', '469935', '469027', '福报农场', '中国,海南省,乐东黎族自治县,福报农场', '福报农场', '中国,海南,乐东,福报农场', '3', '0898', '572532', 'Fubao', 'FB', 'F', '109.071416', '18.528763'),
('2749', '469936', '469027', '保国农场', '中国,海南省,乐东黎族自治县,保国农场', '保国农场', '中国,海南,乐东,保国农场', '3', '0898', '572525', 'Baoguo', 'BG', 'B', '109.290247', '18.571222'),
('2750', '469937', '469027', '保显农场', '中国,海南省,乐东黎族自治县,保显农场', '保显农场', '中国,海南,乐东,保显农场', '3', '0898', '572526', 'Baoxian', 'BX', 'B', '109.317726', '18.616826'),
('2751', '469938', '469027', '尖峰岭林业', '中国,海南省,乐东黎族自治县,尖峰岭林业', '尖峰岭林业', '中国,海南,乐东,尖峰岭林业', '3', '0898', '572500', 'Jianfengling', 'JFL', 'J', '108.814636', '18.697903'),
('2752', '469939', '469027', '莺歌海盐场', '中国,海南省,乐东黎族自治县,莺歌海盐场', '莺歌海盐场', '中国,海南,乐东,莺歌海盐场', '3', '0898', '572500', 'Yinggehai', 'YGH', 'Y', '108.740872', '18.497727'),
('2753', '469940', '469027', '城区', '中国,海南省,乐东黎族自治县,城区', '城区', '中国,海南,乐东,城区', '3', '0898', '572500', 'Chengqu', 'CQ', 'C', '109.177751', '18.74347'),
('2754', '469941', '469028', '椰林镇', '中国,海南省,陵水黎族自治县,椰林镇', '椰林镇', '中国,海南,陵水,椰林镇', '3', '0898', '572499', 'Yelin', 'YL', 'Y', '110.036325', '18.506138'),
('2755', '469942', '469028', '光坡镇', '中国,海南省,陵水黎族自治县,光坡镇', '光坡镇', '中国,海南,陵水,光坡镇', '3', '0898', '572422', 'Guangpo', 'GP', 'G', '110.048782', '18.543389'),
('2756', '469943', '469028', '三才镇', '中国,海南省,陵水黎族自治县,三才镇', '三才镇', '中国,海南,陵水,三才镇', '3', '0898', '572424', 'Sancai', 'SC', 'S', '110.003298', '18.474577'),
('2757', '469944', '469028', '英州镇', '中国,海南省,陵水黎族自治县,英州镇', '英州镇', '中国,海南,陵水,英州镇', '3', '0898', '572427', 'Yingzhou', 'YZ', 'Y', '109.859217', '18.427082'),
('2758', '469945', '469028', '隆广镇', '中国,海南省,陵水黎族自治县,隆广镇', '隆广镇', '中国,海南,陵水,隆广镇', '3', '0898', '572429', 'Longguang', 'LG', 'L', '109.907152', '18.501217'),
('2759', '469946', '469028', '文罗镇', '中国,海南省,陵水黎族自治县,文罗镇', '文罗镇', '中国,海南,陵水,文罗镇', '3', '0898', '572429', 'Wenluo', 'WL', 'W', '109.962258', '18.514064'),
('2760', '469947', '469028', '本号镇', '中国,海南省,陵水黎族自治县,本号镇', '本号镇', '中国,海南,陵水,本号镇', '3', '0898', '572434', 'Benhao', 'BH', 'B', '109.966776', '18.608373'),
('2761', '469948', '469028', '新村镇', '中国,海南省,陵水黎族自治县,新村镇', '新村镇', '中国,海南,陵水,新村镇', '3', '0898', '572426', 'Xincun', 'XC', 'X', '109.971328', '18.412445'),
('2762', '469949', '469028', '黎安镇', '中国,海南省,陵水黎族自治县,黎安镇', '黎安镇', '中国,海南,陵水,黎安镇', '3', '0898', '572423', 'Li\'an', 'LA', 'L', '110.069642', '18.437315'),
('2763', '469950', '469028', '提蒙乡', '中国,海南省,陵水黎族自治县,提蒙乡', '提蒙乡', '中国,海南,陵水,提蒙乡', '3', '0898', '572435', 'Timeng', 'TM', 'T', '110.013019', '18.564801'),
('2764', '469951', '469028', '群英乡', '中国,海南省,陵水黎族自治县,群英乡', '群英乡', '中国,海南,陵水,群英乡', '3', '0898', '572431', 'Qunying', 'QY', 'Q', '109.880073', '18.581531'),
('2765', '469952', '469028', '岭门农场', '中国,海南省,陵水黎族自治县,岭门农场', '岭门农场', '中国,海南,陵水,岭门农场', '3', '0898', '572421', 'Lingmeng', 'LS', 'L', '110.03747', '18.601755'),
('2766', '469953', '469028', '南平农场', '中国,海南省,陵水黎族自治县,南平农场', '南平农场', '中国,海南,陵水,南平农场', '3', '0898', '572431', 'Nanping', 'NP', 'N', '109.926915', '18.607889'),
('2767', '469954', '469028', '城区', '中国,海南省,陵水黎族自治县,城区', '城区', '中国,海南,陵水,城区', '3', '0898', '572400', 'Chengqu', 'CQ', 'C', '110.036263', '18.507074'),
('2768', '469961', '469029', '保城镇', '中国,海南省,保亭黎族苗族自治县,保城镇', '保城镇', '中国,海南,保亭,保城镇', '3', '0898', '572399', 'Baocheng', 'BC', 'B', '109.703534', '18.641564'),
('2769', '469962', '469029', '什玲镇', '中国,海南省,保亭黎族苗族自治县,什玲镇', '什玲镇', '中国,海南,保亭,什玲镇', '3', '0898', '572312', 'Shiling', 'SL', 'S', '109.787782', '18.661954'),
('2770', '469963', '469029', '加茂镇', '中国,海南省,保亭黎族苗族自治县,加茂镇', '加茂镇', '中国,海南,保亭,加茂镇', '3', '0898', '572313', 'Jiamao', 'JM', 'J', '109.707284', '18.55223'),
('2771', '469964', '469029', '响水镇', '中国,海南省,保亭黎族苗族自治县,响水镇', '响水镇', '中国,海南,保亭,响水镇', '3', '0898', '572319', 'Xiangshui', 'XS', 'X', '109.61628', '18.592227'),
('2772', '469965', '469029', '新政镇', '中国,海南省,保亭黎族苗族自治县,新政镇', '新政镇', '中国,海南,保亭,新政镇', '3', '0898', '572318', 'Xinzheng', 'XZ', 'X', '109.628923', '18.541697'),
('2773', '469966', '469029', '三道镇', '中国,海南省,保亭黎族苗族自治县,三道镇', '三道镇', '中国,海南,保亭,三道镇', '3', '0898', '572316', 'Sandao', 'SD', 'S', '109.668671', '18.464575'),
('2774', '469967', '469029', '六弓乡', '中国,海南省,保亭黎族苗族自治县,六弓乡', '六弓乡', '中国,海南,保亭,六弓乡', '3', '0898', '572314', 'Liugong', 'LG', 'L', '109.790856', '18.539494'),
('2775', '469968', '469029', '南林乡', '中国,海南省,保亭黎族苗族自治县,南林乡', '南林乡', '中国,海南,保亭,南林乡', '3', '0898', '572317', 'Nanlin', 'NL', 'N', '109.617456', '18.405242'),
('2776', '469969', '469029', '毛感乡', '中国,海南省,保亭黎族苗族自治县,毛感乡', '毛感乡', '中国,海南,保亭,毛感乡', '3', '0898', '572322', 'Maogan', 'MG', 'M', '109.512415', '18.611051'),
('2777', '469970', '469029', '新星农场', '中国,海南省,保亭黎族苗族自治县,新星农场', '新星农场', '中国,海南,保亭,新星农场', '3', '0898', '572399', 'Xinxing', 'XX', 'X', '109.695472', '18.653413'),
('2778', '469971', '469029', '金江农场', '中国,海南省,保亭黎族苗族自治县,金江农场', '金江农场', '中国,海南,保亭,金江农场', '3', '0898', '572319', 'Jinjiang', 'JJ', 'J', '109.603133', '18.561246'),
('2779', '469972', '469029', '三道农场', '中国,海南省,保亭黎族苗族自治县,三道农场', '三道农场', '中国,海南,保亭,三道农场', '3', '0898', '572316', 'Sandao', 'SD', 'S', '109.683653', '18.440149'),
('2780', '469981', '469030', '营根镇', '中国,海南省,琼中黎族苗族自治县,营根镇', '营根镇', '中国,海南,琼中,营根镇', '3', '0898', '572999', 'Yinggen', 'YG', 'Y', '109.831638', '19.036091'),
('2781', '469982', '469030', '湾岭镇', '中国,海南省,琼中黎族苗族自治县,湾岭镇', '湾岭镇', '中国,海南,琼中,湾岭镇', '3', '0898', '572912', 'Wanling', 'WL', 'W', '109.900496', '19.141306'),
('2782', '469983', '469030', '黎母山镇', '中国,海南省,琼中黎族苗族自治县,黎母山镇', '黎母山镇', '中国,海南,琼中,黎母山镇', '3', '0898', '572929', 'Limushan', 'LMS', 'L', '109.792761', '19.266226'),
('2783', '469984', '469030', '和平镇', '中国,海南省,琼中黎族苗族自治县,和平镇', '和平镇', '中国,海南,琼中,和平镇', '3', '0898', '572918', 'Heping', 'HP', 'H', '110.029212', '18.897423'),
('2784', '469985', '469030', '长征镇', '中国,海南省,琼中黎族苗族自治县,长征镇', '长征镇', '中国,海南,琼中,长征镇', '3', '0898', '572917', 'Changzheng', 'CZ', 'C', '109.878212', '18.958753'),
('2785', '469986', '469030', '红毛镇', '中国,海南省,琼中黎族苗族自治县,红毛镇', '红毛镇', '中国,海南,琼中,红毛镇', '3', '0898', '572933', 'Hongmao', 'HM', 'H', '109.689915', '19.030678'),
('2786', '469987', '469030', '中平镇', '中国,海南省,琼中黎族苗族自治县,中平镇', '中平镇', '中国,海南,琼中,中平镇', '3', '0898', '572915', 'Zhongping', 'ZH', 'Z', '110.062058', '19.058532'),
('2787', '469988', '469030', '上安乡', '中国,海南省,琼中黎族苗族自治县,上安乡', '上安乡', '中国,海南,琼中,上安乡', '3', '0898', '572919', 'Shang\'an', 'SA', 'S', '109.83728', '18.876827'),
('2788', '469989', '469030', '什运乡', '中国,海南省,琼中黎族苗族自治县,什运乡', '什运乡', '中国,海南,琼中,什运乡', '3', '0898', '572923', 'Shiyun', 'SY', 'S', '109.607968', '18.992016'),
('2789', '469990', '469030', '吊罗山乡', '中国,海南省,琼中黎族苗族自治县,吊罗山乡', '吊罗山乡', '中国,海南,琼中,吊罗山乡', '3', '0898', '572921', 'Diaoluoshan', 'DLS', 'D', '109.878143', '18.792633'),
('2790', '469991', '469030', '阳江农场', '中国,海南省,琼中黎族苗族自治县,阳江农场', '阳江农场', '中国,海南,琼中,阳江农场', '3', '0898', '572928', 'Yangjiang', 'YJ', 'Y', '109.720008', '19.345067'),
('2791', '469992', '469030', '乌石农场', '中国,海南省,琼中黎族苗族自治县,乌石农场', '乌石农场', '中国,海南,琼中,乌石农场', '3', '0898', '572911', 'Wushi', 'WS', 'W', '109.86306', '19.124241'),
('2792', '469993', '469030', '加钗农场', '中国,海南省,琼中黎族苗族自治县,加钗农场', '加钗农场', '中国,海南,琼中,加钗农场', '3', '0898', '572925', 'Jiacha', 'JC', 'J', '109.779899', '19.038332'),
('2793', '469994', '469030', '长征农场', '中国,海南省,琼中黎族苗族自治县,长征农场', '长征农场', '中国,海南,琼中,长征农场', '3', '0898', '572917', 'Changzheng', 'CZ', 'C', '109.880077', '18.959379'),
('2794', '469995', '469030', '城区', '中国,海南省,琼中黎族苗族自治县,城区', '城区', '中国,海南,琼中,城区', '3', '0898', '572900', 'Chengqu', 'CQ', 'C', '109.854855', '19.037385'),
('2795', '500000', '100000', '重庆', '中国,重庆', '重庆', '中国,重庆', '1', '', '', 'Chongqing', 'CQ', 'C', '106.504962', '29.533155'),
('2796', '500100', '500000', '重庆市', '中国,重庆,重庆市', '重庆', '中国,重庆,重庆', '2', '023', '400000', 'Chongqing', 'CQ', 'C', '106.504962', '29.533155'),
('2797', '500101', '500100', '万州区', '中国,重庆,重庆市,万州区', '万州', '中国,重庆,重庆,万州', '3', '023', '404100', 'Wanzhou', 'WZ', 'W', '108.40869', '30.80788'),
('2798', '500102', '500100', '涪陵区', '中国,重庆,重庆市,涪陵区', '涪陵', '中国,重庆,重庆,涪陵', '3', '023', '408000', 'Fuling', 'FL', 'F', '107.39007', '29.70292'),
('2799', '500103', '500100', '渝中区', '中国,重庆,重庆市,渝中区', '渝中', '中国,重庆,重庆,渝中', '3', '023', '400000', 'Yuzhong', 'YZ', 'Y', '106.56901', '29.55279'),
('2800', '500104', '500100', '大渡口区', '中国,重庆,重庆市,大渡口区', '大渡口', '中国,重庆,重庆,大渡口', '3', '023', '400000', 'Dadukou', 'DDK', 'D', '106.48262', '29.48447'),
('2801', '500105', '500100', '江北区', '中国,重庆,重庆市,江北区', '江北', '中国,重庆,重庆,江北', '3', '023', '400000', 'Jiangbei', 'JB', 'J', '106.57434', '29.60658'),
('2802', '500106', '500100', '沙坪坝区', '中国,重庆,重庆市,沙坪坝区', '沙坪坝', '中国,重庆,重庆,沙坪坝', '3', '023', '400000', 'Shapingba', 'SPB', 'S', '106.45752', '29.54113'),
('2803', '500107', '500100', '九龙坡区', '中国,重庆,重庆市,九龙坡区', '九龙坡', '中国,重庆,重庆,九龙坡', '3', '023', '400000', 'Jiulongpo', 'JLP', 'J', '106.51107', '29.50197'),
('2804', '500108', '500100', '南岸区', '中国,重庆,重庆市,南岸区', '南岸', '中国,重庆,重庆,南岸', '3', '023', '400000', 'Nan\'an', 'NA', 'N', '106.56347', '29.52311'),
('2805', '500109', '500100', '北碚区', '中国,重庆,重庆市,北碚区', '北碚', '中国,重庆,重庆,北碚', '3', '023', '400700', 'Beibei', 'BB', 'B', '106.39614', '29.80574'),
('2806', '500110', '500100', '綦江区', '中国,重庆,重庆市,綦江区', '綦江', '中国,重庆,重庆,綦江', '3', '023', '400800', 'Qijiang', 'QJ', 'Q', '106.926779', '28.960656'),
('2807', '500111', '500100', '大足区', '中国,重庆,重庆市,大足区', '大足', '中国,重庆,重庆,大足', '3', '023', '400900', 'Dazu', 'DZ', 'D', '105.768121', '29.484025'),
('2808', '500112', '500100', '渝北区', '中国,重庆,重庆市,渝北区', '渝北', '中国,重庆,重庆,渝北', '3', '023', '401120', 'Yubei', 'YB', 'Y', '106.6307', '29.7182'),
('2809', '500113', '500100', '巴南区', '中国,重庆,重庆市,巴南区', '巴南', '中国,重庆,重庆,巴南', '3', '023', '401320', 'Banan', 'BN', 'B', '106.52365', '29.38311'),
('2810', '500114', '500100', '黔江区', '中国,重庆,重庆市,黔江区', '黔江', '中国,重庆,重庆,黔江', '3', '023', '409000', 'Qianjiang', 'QJ', 'Q', '108.7709', '29.5332'),
('2811', '500115', '500100', '长寿区', '中国,重庆,重庆市,长寿区', '长寿', '中国,重庆,重庆,长寿', '3', '023', '401220', 'Changshou', 'CS', 'C', '107.08166', '29.85359'),
('2812', '500116', '500100', '江津区', '中国,重庆,重庆市,江津区', '江津', '中国,重庆,重庆,江津', '3', '023', '402260', 'Jiangjin', 'JJ', 'J', '106.25912', '29.29008'),
('2813', '500117', '500100', '合川区', '中国,重庆,重庆市,合川区', '合川', '中国,重庆,重庆,合川', '3', '023', '401520', 'Hechuan', 'HC', 'H', '106.27633', '29.97227'),
('2814', '500118', '500100', '永川区', '中国,重庆,重庆市,永川区', '永川', '中国,重庆,重庆,永川', '3', '023', '402160', 'Yongchuan', 'YC', 'Y', '105.927', '29.35593'),
('2815', '500119', '500100', '南川区', '中国,重庆,重庆市,南川区', '南川', '中国,重庆,重庆,南川', '3', '023', '408400', 'Nanchuan', 'NC', 'N', '107.09936', '29.15751'),
('2816', '500120', '500100', '璧山区', '中国,重庆,重庆市,璧山区', '璧山', '中国,重庆,重庆,璧山', '3', '023', '402760', 'Bishan', 'BS', 'B', '106.231126', '29.593581'),
('2817', '500151', '500100', '铜梁区', '中国,重庆,重庆市,铜梁区', '铜梁', '中国,重庆,重庆,铜梁', '3', '023', '402560', 'Tongliang', 'TL', 'T', '106.054948', '29.839944'),
('2818', '500152', '500100', '潼南区', '中国,重庆,重庆市,潼南区', '潼南', '中国,重庆,重庆,潼南', '3', '023', '402660', 'Tongnan', 'TN', 'T', '105.84005', '30.1912'),
('2819', '500153', '500100', '荣昌区', '中国,重庆,重庆市,荣昌区', '荣昌', '中国,重庆,重庆,荣昌', '3', '023', '402460', 'Rongchang', 'RC', 'R', '105.59442', '29.40488'),
('2820', '500154', '500100', '开州区', '中国,重庆,重庆市,开州区', '开州', '中国,重庆,重庆,开州', '3', '023', '405400', 'KaiZhou', 'KZ', 'K', '108.39306', '31.16095'),
('2821', '500155', '500100', '梁平区', '中国,重庆,重庆市,梁平区', '梁平', '中国,重庆,重庆,梁平', '3', '023', '405200', 'Liangping', 'LP', 'L', '107.79998', '30.67545'),
('2822', '500156', '500100', '武隆区', '中国,重庆,重庆市,武隆区', '武隆', '中国,重庆,重庆,武隆', '3', '023', '408500', 'Wulong', 'WL', 'W', '107.7601', '29.32548'),
('2823', '500229', '500100', '城口县', '中国,重庆,重庆市,城口县', '城口', '中国,重庆,重庆,城口', '3', '023', '405900', 'Chengkou', 'CK', 'C', '108.66513', '31.94801'),
('2824', '500230', '500100', '丰都县', '中国,重庆,重庆市,丰都县', '丰都', '中国,重庆,重庆,丰都', '3', '023', '408200', 'Fengdu', 'FD', 'F', '107.73098', '29.86348'),
('2825', '500231', '500100', '垫江县', '中国,重庆,重庆市,垫江县', '垫江', '中国,重庆,重庆,垫江', '3', '023', '408300', 'Dianjiang', 'DJ', 'D', '107.35446', '30.33359'),
('2826', '500233', '500100', '忠县', '中国,重庆,重庆市,忠县', '忠县', '中国,重庆,重庆,忠县', '3', '023', '404300', 'Zhongxian', 'ZX', 'Z', '108.03689', '30.28898'),
('2827', '500235', '500100', '云阳县', '中国,重庆,重庆市,云阳县', '云阳', '中国,重庆,重庆,云阳', '3', '023', '404500', 'Yunyang', 'YY', 'Y', '108.69726', '30.93062'),
('2828', '500236', '500100', '奉节县', '中国,重庆,重庆市,奉节县', '奉节', '中国,重庆,重庆,奉节', '3', '023', '404600', 'Fengjie', 'FJ', 'F', '109.46478', '31.01825'),
('2829', '500237', '500100', '巫山县', '中国,重庆,重庆市,巫山县', '巫山', '中国,重庆,重庆,巫山', '3', '023', '404700', 'Wushan', 'WS', 'W', '109.87814', '31.07458'),
('2830', '500238', '500100', '巫溪县', '中国,重庆,重庆市,巫溪县', '巫溪', '中国,重庆,重庆,巫溪', '3', '023', '405800', 'Wuxi', 'WX', 'W', '109.63128', '31.39756'),
('2831', '500240', '500100', '石柱土家族自治县', '中国,重庆,重庆市,石柱土家族自治县', '石柱', '中国,重庆,重庆,石柱', '3', '023', '409100', 'Shizhu', 'SZ', 'S', '108.11389', '30.00054'),
('2832', '500241', '500100', '秀山土家族苗族自治县', '中国,重庆,重庆市,秀山土家族苗族自治县', '秀山', '中国,重庆,重庆,秀山', '3', '023', '409900', 'Xiushan', 'XS', 'X', '108.98861', '28.45062'),
('2833', '500242', '500100', '酉阳土家族苗族自治县', '中国,重庆,重庆市,酉阳土家族苗族自治县', '酉阳', '中国,重庆,重庆,酉阳', '3', '023', '409800', 'Youyang', 'YY', 'Y', '108.77212', '28.8446'),
('2834', '500243', '500100', '彭水苗族土家族自治县', '中国,重庆,重庆市,彭水苗族土家族自治县', '彭水', '中国,重庆,重庆,彭水', '3', '023', '409600', 'Pengshui', 'PS', 'P', '108.16638', '29.29516'),
('2835', '500300', '500100', '两江新区', '中国,重庆,重庆市,两江新区', '两江新区', '中国,重庆,重庆,两江新区', '3', '023', '401147', 'Liangjiangxinqu', 'LJXQ', 'L', '106.463344', '29.729153'),
('2836', '500301', '500100', '高新区', '中国,重庆,重庆市,高新区', '高新区', '中国,重庆,重庆,高新区', '3', '023', '400039', 'Gaoxinqu', 'GXQ', 'G', '106.480492', '29.524916'),
('2837', '500302', '500100', '璧山高新区', '中国,重庆,重庆市,璧山高新区', '璧山高新区', '中国,重庆,重庆,璧山高新区', '3', '023', '402760', 'BishanGaoxinqu', 'BSGXQ', 'B', '106.214128', '29.574009'),
('2838', '510000', '100000', '四川省', '中国,四川省', '四川', '中国,四川', '1', '', '', 'Sichuan', 'SC', 'S', '104.065735', '30.659462'),
('2839', '510100', '510000', '成都市', '中国,四川省,成都市', '成都', '中国,四川,成都', '2', '028', '610000', 'Chengdu', 'CD', 'C', '104.065735', '30.659462'),
('2840', '510104', '510100', '锦江区', '中国,四川省,成都市,锦江区', '锦江', '中国,四川,成都,锦江', '3', '028', '610000', 'Jinjiang', 'JJ', 'J', '104.08347', '30.65614'),
('2841', '510105', '510100', '青羊区', '中国,四川省,成都市,青羊区', '青羊', '中国,四川,成都,青羊', '3', '028', '610000', 'Qingyang', 'QY', 'Q', '104.06151', '30.67387'),
('2842', '510106', '510100', '金牛区', '中国,四川省,成都市,金牛区', '金牛', '中国,四川,成都,金牛', '3', '028', '610000', 'Jinniu', 'JN', 'J', '104.05114', '30.69126'),
('2843', '510107', '510100', '武侯区', '中国,四川省,成都市,武侯区', '武侯', '中国,四川,成都,武侯', '3', '028', '610000', 'Wuhou', 'WH', 'W', '104.04303', '30.64235'),
('2844', '510108', '510100', '成华区', '中国,四川省,成都市,成华区', '成华', '中国,四川,成都,成华', '3', '028', '610000', 'Chenghua', 'CH', 'C', '104.10193', '30.65993'),
('2845', '510112', '510100', '龙泉驿区', '中国,四川省,成都市,龙泉驿区', '龙泉驿', '中国,四川,成都,龙泉驿', '3', '028', '610100', 'Longquanyi', 'LQY', 'L', '104.27462', '30.55658'),
('2846', '510113', '510100', '青白江区', '中国,四川省,成都市,青白江区', '青白江', '中国,四川,成都,青白江', '3', '028', '610300', 'Qingbaijiang', 'QBJ', 'Q', '104.251', '30.87841'),
('2847', '510114', '510100', '新都区', '中国,四川省,成都市,新都区', '新都', '中国,四川,成都,新都', '3', '028', '610500', 'Xindu', 'XD', 'X', '104.15921', '30.82314'),
('2848', '510115', '510100', '温江区', '中国,四川省,成都市,温江区', '温江', '中国,四川,成都,温江', '3', '028', '611130', 'Wenjiang', 'WJ', 'W', '103.84881', '30.68444'),
('2849', '510116', '510100', '双流区', '中国,四川省,成都市,双流区', '双流', '中国,四川,成都,双流', '3', '028', '610200', 'Shuangliu', 'SL', 'S', '103.92373', '30.57444'),
('2850', '510117', '510100', '郫都区', '中国,四川省,成都市,郫都区', '郫都', '中国,四川,成都,郫都', '3', '028', '611730', 'Pidu', 'PD', 'P', '103.88717', '30.81054'),
('2851', '510121', '510100', '金堂县', '中国,四川省,成都市,金堂县', '金堂', '中国,四川,成都,金堂', '3', '028', '610400', 'Jintang', 'JT', 'J', '104.41195', '30.86195'),
('2852', '510129', '510100', '大邑县', '中国,四川省,成都市,大邑县', '大邑', '中国,四川,成都,大邑', '3', '028', '611300', 'Dayi', 'DY', 'D', '103.52075', '30.58738'),
('2853', '510131', '510100', '蒲江县', '中国,四川省,成都市,蒲江县', '蒲江', '中国,四川,成都,蒲江', '3', '028', '611600', 'Pujiang', 'PJ', 'P', '103.50616', '30.19667'),
('2854', '510132', '510100', '新津县', '中国,四川省,成都市,新津县', '新津', '中国,四川,成都,新津', '3', '028', '611400', 'Xinjin', 'XJ', 'X', '103.8114', '30.40983'),
('2855', '510181', '510100', '都江堰市', '中国,四川省,成都市,都江堰市', '都江堰', '中国,四川,成都,都江堰', '3', '028', '611800', 'Dujiangyan', 'DJY', 'D', '103.61941', '30.99825'),
('2856', '510182', '510100', '彭州市', '中国,四川省,成都市,彭州市', '彭州', '中国,四川,成都,彭州', '3', '028', '611900', 'Pengzhou', 'PZ', 'P', '103.958', '30.99011'),
('2857', '510183', '510100', '邛崃市', '中国,四川省,成都市,邛崃市', '邛崃', '中国,四川,成都,邛崃', '3', '028', '611500', 'Qionglai', 'QL', 'Q', '103.46283', '30.41489'),
('2858', '510184', '510100', '崇州市', '中国,四川省,成都市,崇州市', '崇州', '中国,四川,成都,崇州', '3', '028', '611200', 'Chongzhou', 'CZ', 'C', '103.67285', '30.63014'),
('2859', '510185', '510100', '简阳市', '中国,四川省,成都市,简阳市', '简阳', '中国,四川,成都,简阳', '3', '028', '641400', 'Jianyang', 'JY', 'J', '104.54864', '30.3904'),
('2860', '510186', '510100', '天府新区', '中国,四川省,成都市,天府新区', '天府新区', '中国,四川,成都,天府新区', '3', '028', '610000', 'TianfuXinqu', 'TFXQ', 'T', '104.069469', '30.523771'),
('2861', '510187', '510100', '高新南区', '中国,四川省,成都市,高新南区', '高新南区', '中国,四川,成都,高新南区', '3', '028', '610041', 'GaoxinNanqu', 'GXNQ', 'G', '104.067125', '30.595067'),
('2862', '510188', '510100', '高新西区', '中国,四川省,成都市,高新西区', '高新西区', '中国,四川,成都,高新西区', '3', '028', '611731', 'GaoxinXiqu', 'GXXQ', 'G', '103.930357', '30.766486'),
('2863', '510300', '510000', '自贡市', '中国,四川省,自贡市', '自贡', '中国,四川,自贡', '2', '0813', '643000', 'Zigong', 'ZG', 'Z', '104.773447', '29.352765'),
('2864', '510302', '510300', '自流井区', '中国,四川省,自贡市,自流井区', '自流井', '中国,四川,自贡,自流井', '3', '0813', '643000', 'Ziliujing', 'ZLJ', 'Z', '104.77719', '29.33745'),
('2865', '510303', '510300', '贡井区', '中国,四川省,自贡市,贡井区', '贡井', '中国,四川,自贡,贡井', '3', '0813', '643020', 'Gongjing', 'GJ', 'G', '104.71536', '29.34576'),
('2866', '510304', '510300', '大安区', '中国,四川省,自贡市,大安区', '大安', '中国,四川,自贡,大安', '3', '0813', '643010', 'Da\'an', 'DA', 'D', '104.77383', '29.36364'),
('2867', '510311', '510300', '沿滩区', '中国,四川省,自贡市,沿滩区', '沿滩', '中国,四川,自贡,沿滩', '3', '0813', '643030', 'Yantan', 'YT', 'Y', '104.88012', '29.26611'),
('2868', '510321', '510300', '荣县', '中国,四川省,自贡市,荣县', '荣县', '中国,四川,自贡,荣县', '3', '0813', '643100', 'Rongxian', 'RX', 'R', '104.4176', '29.44445'),
('2869', '510322', '510300', '富顺县', '中国,四川省,自贡市,富顺县', '富顺', '中国,四川,自贡,富顺', '3', '0813', '643200', 'Fushun', 'FS', 'F', '104.97491', '29.18123'),
('2870', '510323', '510300', '高新区', '中国,四川省,自贡市,高新区', '高新区', '中国,四川,自贡,高新区', '3', '0813', '643000', 'Gaoxinqu', 'GXQ', 'G', '104.777732', '29.338939'),
('2871', '510400', '510000', '攀枝花市', '中国,四川省,攀枝花市', '攀枝花', '中国,四川,攀枝花', '2', '0812', '617000', 'Panzhihua', 'PZH', 'P', '101.716007', '26.580446'),
('2872', '510402', '510400', '东区', '中国,四川省,攀枝花市,东区', '东区', '中国,四川,攀枝花,东区', '3', '0812', '617000', 'Dongqu', 'DQ', 'D', '101.7052', '26.54677'),
('2873', '510403', '510400', '西区', '中国,四川省,攀枝花市,西区', '西区', '中国,四川,攀枝花,西区', '3', '0812', '617000', 'Xiqu', 'XQ', 'X', '101.63058', '26.59753'),
('2874', '510411', '510400', '仁和区', '中国,四川省,攀枝花市,仁和区', '仁和', '中国,四川,攀枝花,仁和', '3', '0812', '617000', 'Renhe', 'RH', 'R', '101.73812', '26.49841'),
('2875', '510421', '510400', '米易县', '中国,四川省,攀枝花市,米易县', '米易', '中国,四川,攀枝花,米易', '3', '0812', '617200', 'Miyi', 'MY', 'M', '102.11132', '26.88718'),
('2876', '510422', '510400', '盐边县', '中国,四川省,攀枝花市,盐边县', '盐边', '中国,四川,攀枝花,盐边', '3', '0812', '617100', 'Yanbian', 'YB', 'Y', '101.85446', '26.68847'),
('2877', '510500', '510000', '泸州市', '中国,四川省,泸州市', '泸州', '中国,四川,泸州', '2', '0830', '646000', 'Luzhou', 'LZ', 'L', '105.443348', '28.889138'),
('2878', '510502', '510500', '江阳区', '中国,四川省,泸州市,江阳区', '江阳', '中国,四川,泸州,江阳', '3', '0830', '646000', 'Jiangyang', 'JY', 'J', '105.45336', '28.88934'),
('2879', '510503', '510500', '纳溪区', '中国,四川省,泸州市,纳溪区', '纳溪', '中国,四川,泸州,纳溪', '3', '0830', '646300', 'Naxi', 'NX', 'N', '105.37255', '28.77343'),
('2880', '510504', '510500', '龙马潭区', '中国,四川省,泸州市,龙马潭区', '龙马潭', '中国,四川,泸州,龙马潭', '3', '0830', '646000', 'Longmatan', 'LMT', 'L', '105.43774', '28.91308'),
('2881', '510521', '510500', '泸县', '中国,四川省,泸州市,泸县', '泸县', '中国,四川,泸州,泸县', '3', '0830', '646100', 'Luxian', 'LX', 'L', '105.38192', '29.15041'),
('2882', '510522', '510500', '合江县', '中国,四川省,泸州市,合江县', '合江', '中国,四川,泸州,合江', '3', '0830', '646200', 'Hejiang', 'HJ', 'H', '105.8352', '28.81005'),
('2883', '510524', '510500', '叙永县', '中国,四川省,泸州市,叙永县', '叙永', '中国,四川,泸州,叙永', '3', '0830', '646400', 'Xuyong', 'XY', 'X', '105.44473', '28.15586'),
('2884', '510525', '510500', '古蔺县', '中国,四川省,泸州市,古蔺县', '古蔺', '中国,四川,泸州,古蔺', '3', '0830', '646500', 'Gulin', 'GL', 'G', '105.81347', '28.03867'),
('2885', '510600', '510000', '德阳市', '中国,四川省,德阳市', '德阳', '中国,四川,德阳', '2', '0838', '618000', 'Deyang', 'DY', 'D', '104.398651', '31.127991'),
('2886', '510603', '510600', '旌阳区', '中国,四川省,德阳市,旌阳区', '旌阳', '中国,四川,德阳,旌阳', '3', '0838', '618000', 'Jingyang', 'JY', 'J', '104.39367', '31.13906'),
('2887', '510623', '510600', '中江县', '中国,四川省,德阳市,中江县', '中江', '中国,四川,德阳,中江', '3', '0838', '618100', 'Zhongjiang', 'ZJ', 'Z', '104.67861', '31.03297'),
('2888', '510626', '510600', '罗江县', '中国,四川省,德阳市,罗江县', '罗江', '中国,四川,德阳,罗江', '3', '0838', '618500', 'Luojiang', 'LJ', 'L', '104.51025', '31.31665'),
('2889', '510681', '510600', '广汉市', '中国,四川省,德阳市,广汉市', '广汉', '中国,四川,德阳,广汉', '3', '0838', '618300', 'Guanghan', 'GH', 'G', '104.28234', '30.97686'),
('2890', '510682', '510600', '什邡市', '中国,四川省,德阳市,什邡市', '什邡', '中国,四川,德阳,什邡', '3', '0838', '618400', 'Shifang', 'SF', 'S', '104.16754', '31.1264'),
('2891', '510683', '510600', '绵竹市', '中国,四川省,德阳市,绵竹市', '绵竹', '中国,四川,德阳,绵竹', '3', '0838', '618200', 'Mianzhu', 'MZ', 'M', '104.22076', '31.33772'),
('2892', '510700', '510000', '绵阳市', '中国,四川省,绵阳市', '绵阳', '中国,四川,绵阳', '2', '0816', '621000', 'Mianyang', 'MY', 'M', '104.741722', '31.46402'),
('2893', '510703', '510700', '涪城区', '中国,四川省,绵阳市,涪城区', '涪城', '中国,四川,绵阳,涪城', '3', '0816', '621000', 'Fucheng', 'FC', 'F', '104.75719', '31.45522'),
('2894', '510704', '510700', '游仙区', '中国,四川省,绵阳市,游仙区', '游仙', '中国,四川,绵阳,游仙', '3', '0816', '621000', 'Youxian', 'YX', 'Y', '104.77092', '31.46574'),
('2895', '510705', '510700', '安州区', '中国,四川省,绵阳市,安州区', '安州', '中国,四川,绵阳,安州', '3', '0816', '622650', 'Anzhou', 'AZ', 'A', '104.56738', '31.53487'),
('2896', '510722', '510700', '三台县', '中国,四川省,绵阳市,三台县', '三台', '中国,四川,绵阳,三台', '3', '0816', '621100', 'Santai', 'ST', 'S', '105.09079', '31.09179'),
('2897', '510723', '510700', '盐亭县', '中国,四川省,绵阳市,盐亭县', '盐亭', '中国,四川,绵阳,盐亭', '3', '0816', '621600', 'Yanting', 'YT', 'Y', '105.3898', '31.22176'),
('2898', '510725', '510700', '梓潼县', '中国,四川省,绵阳市,梓潼县', '梓潼', '中国,四川,绵阳,梓潼', '3', '0816', '622150', 'Zitong', 'ZT', 'Z', '105.16183', '31.6359'),
('2899', '510726', '510700', '北川羌族自治县', '中国,四川省,绵阳市,北川羌族自治县', '北川', '中国,四川,绵阳,北川', '3', '0816', '622750', 'Beichuan', 'BC', 'B', '104.46408', '31.83286'),
('2900', '510727', '510700', '平武县', '中国,四川省,绵阳市,平武县', '平武', '中国,四川,绵阳,平武', '3', '0816', '622550', 'Pingwu', 'PW', 'P', '104.52862', '32.40791'),
('2901', '510781', '510700', '江油市', '中国,四川省,绵阳市,江油市', '江油', '中国,四川,绵阳,江油', '3', '0816', '621700', 'Jiangyou', 'JY', 'J', '104.74539', '31.77775'),
('2902', '510782', '510700', '高新区', '中国,四川省,绵阳市,高新区', '高新区', '中国,四川,绵阳,高新区', '3', '0816', '621000', 'Gaoxinqu', 'GXQ', 'G', '104.663939', '31.466187'),
('2903', '510783', '510700', '经开区', '中国,四川省,绵阳市,经开区', '经开区', '中国,四川,绵阳,经开区', '3', '0816', '621000', 'Jingkaiqu', 'JKQ', 'J', '104.78534', '31.415073'),
('2904', '510800', '510000', '广元市', '中国,四川省,广元市', '广元', '中国,四川,广元', '2', '0839', '628000', 'Guangyuan', 'GY', 'G', '105.829757', '32.433668'),
('2905', '510802', '510800', '利州区', '中国,四川省,广元市,利州区', '利州', '中国,四川,广元,利州', '3', '0839', '628017', 'Lizhou', 'LZ', 'L', '105.826194', '32.432276'),
('2906', '510811', '510800', '昭化区', '中国,四川省,广元市,昭化区', '昭化', '中国,四川,广元,昭化', '3', '0839', '628017', 'Zhaohua', 'ZHU', 'Z', '105.640491', '32.386518'),
('2907', '510812', '510800', '朝天区', '中国,四川省,广元市,朝天区', '朝天', '中国,四川,广元,朝天', '3', '0839', '628000', 'Chaotian', 'CT', 'C', '105.89273', '32.64398'),
('2908', '510821', '510800', '旺苍县', '中国,四川省,广元市,旺苍县', '旺苍', '中国,四川,广元,旺苍', '3', '0839', '628200', 'Wangcang', 'WC', 'W', '106.29022', '32.22845'),
('2909', '510822', '510800', '青川县', '中国,四川省,广元市,青川县', '青川', '中国,四川,广元,青川', '3', '0839', '628100', 'Qingchuan', 'QC', 'Q', '105.2391', '32.58563'),
('2910', '510823', '510800', '剑阁县', '中国,四川省,广元市,剑阁县', '剑阁', '中国,四川,广元,剑阁', '3', '0839', '628300', 'Jiange', 'JG', 'J', '105.5252', '32.28845'),
('2911', '510824', '510800', '苍溪县', '中国,四川省,广元市,苍溪县', '苍溪', '中国,四川,广元,苍溪', '3', '0839', '628400', 'Cangxi', 'CX', 'C', '105.936', '31.73209'),
('2912', '510900', '510000', '遂宁市', '中国,四川省,遂宁市', '遂宁', '中国,四川,遂宁', '2', '0825', '629000', 'Suining', 'SN', 'S', '105.571331', '30.513311'),
('2913', '510903', '510900', '船山区', '中国,四川省,遂宁市,船山区', '船山', '中国,四川,遂宁,船山', '3', '0825', '629000', 'Chuanshan', 'CS', 'C', '105.5809', '30.49991'),
('2914', '510904', '510900', '安居区', '中国,四川省,遂宁市,安居区', '安居', '中国,四川,遂宁,安居', '3', '0825', '629000', 'Anju', 'AJ', 'A', '105.46402', '30.35778'),
('2915', '510921', '510900', '蓬溪县', '中国,四川省,遂宁市,蓬溪县', '蓬溪', '中国,四川,遂宁,蓬溪', '3', '0825', '629100', 'Pengxi', 'PX', 'P', '105.70752', '30.75775'),
('2916', '510922', '510900', '射洪县', '中国,四川省,遂宁市,射洪县', '射洪', '中国,四川,遂宁,射洪', '3', '0825', '629200', 'Shehong', 'SH', 'S', '105.38922', '30.87203'),
('2917', '510923', '510900', '大英县', '中国,四川省,遂宁市,大英县', '大英', '中国,四川,遂宁,大英', '3', '0825', '629300', 'Daying', 'DY', 'D', '105.24346', '30.59434'),
('2918', '510924', '510900', '经济技术开发区', '中国,四川省,遂宁市,经济技术开发区', '经济技术开发区', '中国,四川,遂宁,经济技术开发区', '3', '0825', '629000', 'JingJiKaiFaQu', 'JJKFQ', 'J', '105.565462', '30.543002'),
('2919', '511000', '510000', '内江市', '中国,四川省,内江市', '内江', '中国,四川,内江', '2', '0832', '641000', 'Neijiang', 'NJ', 'N', '105.066138', '29.58708'),
('2920', '511002', '511000', '市中区', '中国,四川省,内江市,市中区', '市中', '中国,四川,内江,市中', '3', '0832', '641000', 'Shizhong', 'SZ', 'S', '105.0679', '29.58709'),
('2921', '511011', '511000', '东兴区', '中国,四川省,内江市,东兴区', '东兴', '中国,四川,内江,东兴', '3', '0832', '641100', 'Dongxing', 'DX', 'D', '105.07554', '29.59278'),
('2922', '511024', '511000', '威远县', '中国,四川省,内江市,威远县', '威远', '中国,四川,内江,威远', '3', '0832', '642450', 'Weiyuan', 'WY', 'W', '104.66955', '29.52823'),
('2923', '511025', '511000', '资中县', '中国,四川省,内江市,资中县', '资中', '中国,四川,内江,资中', '3', '0832', '641200', 'Zizhong', 'ZZ', 'Z', '104.85205', '29.76409'),
('2924', '511028', '511000', '隆昌市', '中国,四川省,内江市,隆昌市', '隆昌', '中国,四川,内江,隆昌', '3', '0832', '642150', 'Longchang', 'LC', 'L', '105.28738', '29.33937'),
('2925', '511100', '510000', '乐山市', '中国,四川省,乐山市', '乐山', '中国,四川,乐山', '2', '0833', '614000', 'Leshan', 'LS', 'L', '103.761263', '29.582024'),
('2926', '511102', '511100', '市中区', '中国,四川省,乐山市,市中区', '市中', '中国,四川,乐山,市中', '3', '0833', '614000', 'Shizhong', 'SZ', 'S', '103.76159', '29.55543'),
('2927', '511111', '511100', '沙湾区', '中国,四川省,乐山市,沙湾区', '沙湾', '中国,四川,乐山,沙湾', '3', '0833', '614900', 'Shawan', 'SW', 'S', '103.54873', '29.41194'),
('2928', '511112', '511100', '五通桥区', '中国,四川省,乐山市,五通桥区', '五通桥', '中国,四川,乐山,五通桥', '3', '0833', '614800', 'Wutongqiao', 'WTQ', 'W', '103.82345', '29.40344'),
('2929', '511113', '511100', '金口河区', '中国,四川省,乐山市,金口河区', '金口河', '中国,四川,乐山,金口河', '3', '0833', '614700', 'Jinkouhe', 'JKH', 'J', '103.07858', '29.24578'),
('2930', '511123', '511100', '犍为县', '中国,四川省,乐山市,犍为县', '犍为', '中国,四川,乐山,犍为', '3', '0833', '614400', 'Qianwei', 'QW', 'Q', '103.94989', '29.20973'),
('2931', '511124', '511100', '井研县', '中国,四川省,乐山市,井研县', '井研', '中国,四川,乐山,井研', '3', '0833', '613100', 'Jingyan', 'JY', 'J', '104.07019', '29.65228'),
('2932', '511126', '511100', '夹江县', '中国,四川省,乐山市,夹江县', '夹江', '中国,四川,乐山,夹江', '3', '0833', '614100', 'Jiajiang', 'JJ', 'J', '103.57199', '29.73868'),
('2933', '511129', '511100', '沐川县', '中国,四川省,乐山市,沐川县', '沐川', '中国,四川,乐山,沐川', '3', '0833', '614500', 'Muchuan', 'MC', 'M', '103.90353', '28.95646'),
('2934', '511132', '511100', '峨边彝族自治县', '中国,四川省,乐山市,峨边彝族自治县', '峨边', '中国,四川,乐山,峨边', '3', '0833', '614300', 'Ebian', 'EB', 'E', '103.26339', '29.23004'),
('2935', '511133', '511100', '马边彝族自治县', '中国,四川省,乐山市,马边彝族自治县', '马边', '中国,四川,乐山,马边', '3', '0833', '614600', 'Mabian', 'MB', 'M', '103.54617', '28.83593'),
('2936', '511181', '511100', '峨眉山市', '中国,四川省,乐山市,峨眉山市', '峨眉山', '中国,四川,乐山,峨眉山', '3', '0833', '614200', 'Emeishan', 'EMS', 'E', '103.4844', '29.60117'),
('2937', '511300', '510000', '南充市', '中国,四川省,南充市', '南充', '中国,四川,南充', '2', '0817', '637000', 'Nanchong', 'NC', 'N', '106.082974', '30.795281'),
('2938', '511302', '511300', '顺庆区', '中国,四川省,南充市,顺庆区', '顺庆', '中国,四川,南充,顺庆', '3', '0817', '637000', 'Shunqing', 'SQ', 'S', '106.09216', '30.79642'),
('2939', '511303', '511300', '高坪区', '中国,四川省,南充市,高坪区', '高坪', '中国,四川,南充,高坪', '3', '0817', '637100', 'Gaoping', 'GP', 'G', '106.11894', '30.78162'),
('2940', '511304', '511300', '嘉陵区', '中国,四川省,南充市,嘉陵区', '嘉陵', '中国,四川,南充,嘉陵', '3', '0817', '637500', 'Jialing', 'JL', 'J', '106.07141', '30.75848'),
('2941', '511321', '511300', '南部县', '中国,四川省,南充市,南部县', '南部', '中国,四川,南充,南部', '3', '0817', '637300', 'Nanbu', 'NB', 'N', '106.06738', '31.35451'),
('2942', '511322', '511300', '营山县', '中国,四川省,南充市,营山县', '营山', '中国,四川,南充,营山', '3', '0817', '637700', 'Yingshan', 'YS', 'Y', '106.56637', '31.07747'),
('2943', '511323', '511300', '蓬安县', '中国,四川省,南充市,蓬安县', '蓬安', '中国,四川,南充,蓬安', '3', '0817', '637800', 'Peng\'an', 'PA', 'P', '106.41262', '31.02964'),
('2944', '511324', '511300', '仪陇县', '中国,四川省,南充市,仪陇县', '仪陇', '中国,四川,南充,仪陇', '3', '0817', '637600', 'Yilong', 'YL', 'Y', '106.29974', '31.27628'),
('2945', '511325', '511300', '西充县', '中国,四川省,南充市,西充县', '西充', '中国,四川,南充,西充', '3', '0817', '637200', 'Xichong', 'XC', 'X', '105.89996', '30.9969'),
('2946', '511381', '511300', '阆中市', '中国,四川省,南充市,阆中市', '阆中', '中国,四川,南充,阆中', '3', '0817', '637400', 'Langzhong', 'LZ', 'L', '106.00494', '31.55832'),
('2947', '511400', '510000', '眉山市', '中国,四川省,眉山市', '眉山', '中国,四川,眉山', '2', '028', '620000', 'Meishan', 'MS', 'M', '103.831788', '30.048318'),
('2948', '511402', '511400', '东坡区', '中国,四川省,眉山市,东坡区', '东坡', '中国,四川,眉山,东坡', '3', '028', '620000', 'Dongpo', 'DP', 'D', '103.832', '30.04219'),
('2949', '511403', '511400', '彭山区', '中国,四川省,眉山市,彭山区', '彭山', '中国,四川,眉山,彭山', '3', '028', '620860', 'Pengshan', 'PS', 'P', '103.87268', '30.19283'),
('2950', '511421', '511400', '仁寿县', '中国,四川省,眉山市,仁寿县', '仁寿', '中国,四川,眉山,仁寿', '3', '028', '620500', 'Renshou', 'RS', 'R', '104.13412', '29.99599'),
('2951', '511423', '511400', '洪雅县', '中国,四川省,眉山市,洪雅县', '洪雅', '中国,四川,眉山,洪雅', '3', '028', '620300', 'Hongya', 'HY', 'H', '103.37313', '29.90661'),
('2952', '511424', '511400', '丹棱县', '中国,四川省,眉山市,丹棱县', '丹棱', '中国,四川,眉山,丹棱', '3', '028', '620200', 'Danling', 'DL', 'D', '103.51339', '30.01562'),
('2953', '511425', '511400', '青神县', '中国,四川省,眉山市,青神县', '青神', '中国,四川,眉山,青神', '3', '028', '620400', 'Qingshen', 'QS', 'Q', '103.84771', '29.83235'),
('2954', '511500', '510000', '宜宾市', '中国,四川省,宜宾市', '宜宾', '中国,四川,宜宾', '2', '0831', '644000', 'Yibin', 'YB', 'Y', '104.630825', '28.760189'),
('2955', '511502', '511500', '翠屏区', '中国,四川省,宜宾市,翠屏区', '翠屏', '中国,四川,宜宾,翠屏', '3', '0831', '644000', 'Cuiping', 'CP', 'C', '104.61978', '28.76566'),
('2956', '511503', '511500', '南溪区', '中国,四川省,宜宾市,南溪区', '南溪', '中国,四川,宜宾,南溪', '3', '0831', '644100', 'Nanxi', 'NX', 'N', '104.981133', '28.839806'),
('2957', '511521', '511500', '宜宾县', '中国,四川省,宜宾市,宜宾县', '宜宾', '中国,四川,宜宾,宜宾', '3', '0831', '644600', 'Yibin', 'YB', 'Y', '104.53314', '28.68996'),
('2958', '511523', '511500', '江安县', '中国,四川省,宜宾市,江安县', '江安', '中国,四川,宜宾,江安', '3', '0831', '644200', 'Jiang\'an', 'JA', 'J', '105.06683', '28.72385'),
('2959', '511524', '511500', '长宁县', '中国,四川省,宜宾市,长宁县', '长宁', '中国,四川,宜宾,长宁', '3', '0831', '644300', 'Changning', 'CN', 'C', '104.9252', '28.57777'),
('2960', '511525', '511500', '高县', '中国,四川省,宜宾市,高县', '高县', '中国,四川,宜宾,高县', '3', '0831', '645150', 'Gaoxian', 'GX', 'G', '104.51754', '28.43619'),
('2961', '511526', '511500', '珙县', '中国,四川省,宜宾市,珙县', '珙县', '中国,四川,宜宾,珙县', '3', '0831', '644500', 'Gongxian', 'GX', 'G', '104.71398', '28.44512'),
('2962', '511527', '511500', '筠连县', '中国,四川省,宜宾市,筠连县', '筠连', '中国,四川,宜宾,筠连', '3', '0831', '645250', 'Junlian', 'JL', 'J', '104.51217', '28.16495'),
('2963', '511528', '511500', '兴文县', '中国,四川省,宜宾市,兴文县', '兴文', '中国,四川,宜宾,兴文', '3', '0831', '644400', 'Xingwen', 'XW', 'X', '105.23675', '28.3044'),
('2964', '511529', '511500', '屏山县', '中国,四川省,宜宾市,屏山县', '屏山', '中国,四川,宜宾,屏山', '3', '0831', '645350', 'Pingshan', 'PS', 'P', '104.16293', '28.64369'),
('2965', '511600', '510000', '广安市', '中国,四川省,广安市', '广安', '中国,四川,广安', '2', '0826', '638000', 'Guang\'an', 'GA', 'G', '106.633369', '30.456398'),
('2966', '511602', '511600', '广安区', '中国,四川省,广安市,广安区', '广安', '中国,四川,广安,广安', '3', '0826', '638550', 'Guang\'an', 'GA', 'G', '106.64163', '30.47389'),
('2967', '511603', '511600', '前锋区', '中国,四川省,广安市,前锋区', '前锋', '中国,四川,广安,前锋', '3', '0826', '638019', 'Qianfeng', 'QF', 'Q', '106.893537', '30.494572'),
('2968', '511621', '511600', '岳池县', '中国,四川省,广安市,岳池县', '岳池', '中国,四川,广安,岳池', '3', '0826', '638300', 'Yuechi', 'YC', 'Y', '106.44079', '30.53918'),
('2969', '511622', '511600', '武胜县', '中国,四川省,广安市,武胜县', '武胜', '中国,四川,广安,武胜', '3', '0826', '638400', 'Wusheng', 'WS', 'W', '106.29592', '30.34932'),
('2970', '511623', '511600', '邻水县', '中国,四川省,广安市,邻水县', '邻水', '中国,四川,广安,邻水', '3', '0826', '638500', 'Linshui', 'LS', 'L', '106.92968', '30.33449'),
('2971', '511681', '511600', '华蓥市', '中国,四川省,广安市,华蓥市', '华蓥', '中国,四川,广安,华蓥', '3', '0826', '409800', 'Huaying', 'HY', 'H', '106.78466', '30.39007'),
('2972', '511700', '510000', '达州市', '中国,四川省,达州市', '达州', '中国,四川,达州', '2', '0818', '635000', 'Dazhou', 'DZ', 'D', '107.502262', '31.209484'),
('2973', '511702', '511700', '通川区', '中国,四川省,达州市,通川区', '通川', '中国,四川,达州,通川', '3', '0818', '635000', 'Tongchuan', 'TC', 'T', '107.50456', '31.21469'),
('2974', '511703', '511700', '达川区', '中国,四川省,达州市,达川区', '达川', '中国,四川,达州,达川', '3', '0818', '635000', 'Dachuan', 'DC', 'D', '107.502262', '31.209484'),
('2975', '511722', '511700', '宣汉县', '中国,四川省,达州市,宣汉县', '宣汉', '中国,四川,达州,宣汉', '3', '0818', '636150', 'Xuanhan', 'XH', 'X', '107.72775', '31.35516'),
('2976', '511723', '511700', '开江县', '中国,四川省,达州市,开江县', '开江', '中国,四川,达州,开江', '3', '0818', '636250', 'Kaijiang', 'KJ', 'K', '107.86889', '31.0841'),
('2977', '511724', '511700', '大竹县', '中国,四川省,达州市,大竹县', '大竹', '中国,四川,达州,大竹', '3', '0818', '635100', 'Dazhu', 'DZ', 'D', '107.20855', '30.74147'),
('2978', '511725', '511700', '渠县', '中国,四川省,达州市,渠县', '渠县', '中国,四川,达州,渠县', '3', '0818', '635200', 'Quxian', 'QX', 'Q', '106.97381', '30.8376'),
('2979', '511781', '511700', '万源市', '中国,四川省,达州市,万源市', '万源', '中国,四川,达州,万源', '3', '0818', '636350', 'Wanyuan', 'WY', 'W', '108.03598', '32.08091'),
('2980', '511800', '510000', '雅安市', '中国,四川省,雅安市', '雅安', '中国,四川,雅安', '2', '0835', '625000', 'Ya\'an', 'YA', 'Y', '103.001033', '29.987722'),
('2981', '511802', '511800', '雨城区', '中国,四川省,雅安市,雨城区', '雨城', '中国,四川,雅安,雨城', '3', '0835', '625000', 'Yucheng', 'YC', 'Y', '103.03305', '30.00531'),
('2982', '511803', '511800', '名山区', '中国,四川省,雅安市,名山区', '名山', '中国,四川,雅安,名山', '3', '0835', '625100', 'Mingshan', 'MS', 'M', '103.112214', '30.084718'),
('2983', '511822', '511800', '荥经县', '中国,四川省,雅安市,荥经县', '荥经', '中国,四川,雅安,荥经', '3', '0835', '625200', 'Yingjing', 'YJ', 'Y', '102.84652', '29.79402'),
('2984', '511823', '511800', '汉源县', '中国,四川省,雅安市,汉源县', '汉源', '中国,四川,雅安,汉源', '3', '0835', '625300', 'Hanyuan', 'HY', 'H', '102.6784', '29.35168'),
('2985', '511824', '511800', '石棉县', '中国,四川省,雅安市,石棉县', '石棉', '中国,四川,雅安,石棉', '3', '0835', '625400', 'Shimian', 'SM', 'S', '102.35943', '29.22796'),
('2986', '511825', '511800', '天全县', '中国,四川省,雅安市,天全县', '天全', '中国,四川,雅安,天全', '3', '0835', '625500', 'Tianquan', 'TQ', 'T', '102.75906', '30.06754'),
('2987', '511826', '511800', '芦山县', '中国,四川省,雅安市,芦山县', '芦山', '中国,四川,雅安,芦山', '3', '0835', '625600', 'Lushan', 'LS', 'L', '102.92791', '30.14369'),
('2988', '511827', '511800', '宝兴县', '中国,四川省,雅安市,宝兴县', '宝兴', '中国,四川,雅安,宝兴', '3', '0835', '625700', 'Baoxing', 'BX', 'B', '102.81555', '30.36836'),
('2989', '511900', '510000', '巴中市', '中国,四川省,巴中市', '巴中', '中国,四川,巴中', '2', '0827', '636600', 'Bazhong', 'BZ', 'B', '106.753669', '31.858809'),
('2990', '511902', '511900', '巴州区', '中国,四川省,巴中市,巴州区', '巴州', '中国,四川,巴中,巴州', '3', '0827', '636600', 'Bazhou', 'BZ', 'B', '106.76889', '31.85125'),
('2991', '511903', '511900', '恩阳区', '中国,四川省,巴中市,恩阳区', '恩阳', '中国,四川,巴中,恩阳', '3', '0827', '636064', 'Enyang', 'EY', 'E', '106.753669', '31.858809'),
('2992', '511921', '511900', '通江县', '中国,四川省,巴中市,通江县', '通江', '中国,四川,巴中,通江', '3', '0827', '636700', 'Tongjiang', 'TJ', 'T', '107.24398', '31.91294'),
('2993', '511922', '511900', '南江县', '中国,四川省,巴中市,南江县', '南江', '中国,四川,巴中,南江', '3', '0827', '635600', 'Nanjiang', 'NJ', 'N', '106.84164', '32.35335'),
('2994', '511923', '511900', '平昌县', '中国,四川省,巴中市,平昌县', '平昌', '中国,四川,巴中,平昌', '3', '0827', '636400', 'Pingchang', 'PC', 'P', '107.10424', '31.5594'),
('2995', '512000', '510000', '资阳市', '中国,四川省,资阳市', '资阳', '中国,四川,资阳', '2', '028', '641300', 'Ziyang', 'ZY', 'Z', '104.641917', '30.122211'),
('2996', '512002', '512000', '雁江区', '中国,四川省,资阳市,雁江区', '雁江', '中国,四川,资阳,雁江', '3', '028', '641300', 'Yanjiang', 'YJ', 'Y', '104.65216', '30.11525'),
('2997', '512021', '512000', '安岳县', '中国,四川省,资阳市,安岳县', '安岳', '中国,四川,资阳,安岳', '3', '028', '642350', 'Anyue', 'AY', 'A', '105.3363', '30.09786'),
('2998', '512022', '512000', '乐至县', '中国,四川省,资阳市,乐至县', '乐至', '中国,四川,资阳,乐至', '3', '028', '641500', 'Lezhi', 'LZ', 'L', '105.03207', '30.27227'),
('2999', '513200', '510000', '阿坝藏族羌族自治州', '中国,四川省,阿坝藏族羌族自治州', '阿坝', '中国,四川,阿坝', '2', '0837', '624000', 'Aba', 'AB', 'A', '102.221374', '31.899792'),
('3000', '513201', '513200', '马尔康市', '中国,四川省,阿坝藏族羌族自治州,马尔康市', '马尔康', '中国,四川,阿坝,马尔康', '3', '0837', '624000', 'Maerkang', 'MEK', 'M', '102.20625', '31.90584'),
('3001', '513221', '513200', '汶川县', '中国,四川省,阿坝藏族羌族自治州,汶川县', '汶川', '中国,四川,阿坝,汶川', '3', '0837', '623000', 'Wenchuan', 'WC', 'W', '103.59079', '31.47326'),
('3002', '513222', '513200', '理县', '中国,四川省,阿坝藏族羌族自治州,理县', '理县', '中国,四川,阿坝,理县', '3', '0837', '623100', 'Lixian', 'LX', 'L', '103.17175', '31.43603'),
('3003', '513223', '513200', '茂县', '中国,四川省,阿坝藏族羌族自治州,茂县', '茂县', '中国,四川,阿坝,茂县', '3', '0837', '623200', 'Maoxian', 'MX', 'M', '103.85372', '31.682'),
('3004', '513224', '513200', '松潘县', '中国,四川省,阿坝藏族羌族自治州,松潘县', '松潘', '中国,四川,阿坝,松潘', '3', '0837', '623300', 'Songpan', 'SP', 'S', '103.59924', '32.63871'),
('3005', '513225', '513200', '九寨沟县', '中国,四川省,阿坝藏族羌族自治州,九寨沟县', '九寨沟', '中国,四川,阿坝,九寨沟', '3', '0837', '623400', 'Jiuzhaigou', 'JZG', 'J', '104.23672', '33.26318'),
('3006', '513226', '513200', '金川县', '中国,四川省,阿坝藏族羌族自治州,金川县', '金川', '中国,四川,阿坝,金川', '3', '0837', '624100', 'Jinchuan', 'JC', 'J', '102.06555', '31.47623'),
('3007', '513227', '513200', '小金县', '中国,四川省,阿坝藏族羌族自治州,小金县', '小金', '中国,四川,阿坝,小金', '3', '0837', '624200', 'Xiaojin', 'XJ', 'X', '102.36499', '30.99934'),
('3008', '513228', '513200', '黑水县', '中国,四川省,阿坝藏族羌族自治州,黑水县', '黑水', '中国,四川,阿坝,黑水', '3', '0837', '623500', 'Heishui', 'HS', 'H', '102.99176', '32.06184'),
('3009', '513230', '513200', '壤塘县', '中国,四川省,阿坝藏族羌族自治州,壤塘县', '壤塘', '中国,四川,阿坝,壤塘', '3', '0837', '624300', 'Rangtang', 'RT', 'R', '100.9783', '32.26578'),
('3010', '513231', '513200', '阿坝县', '中国,四川省,阿坝藏族羌族自治州,阿坝县', '阿坝', '中国,四川,阿坝,阿坝', '3', '0837', '624600', 'Aba', 'AB', 'A', '101.70632', '32.90301'),
('3011', '513232', '513200', '若尔盖县', '中国,四川省,阿坝藏族羌族自治州,若尔盖县', '若尔盖', '中国,四川,阿坝,若尔盖', '3', '0837', '624500', 'Ruoergai', 'REG', 'R', '102.9598', '33.57432'),
('3012', '513233', '513200', '红原县', '中国,四川省,阿坝藏族羌族自治州,红原县', '红原', '中国,四川,阿坝,红原', '3', '0837', '624400', 'Hongyuan', 'HY', 'H', '102.54525', '32.78989'),
('3013', '513300', '510000', '甘孜藏族自治州', '中国,四川省,甘孜藏族自治州', '甘孜', '中国,四川,甘孜', '2', '0836', '626000', 'Garze', 'GZ', 'G', '101.963815', '30.050663'),
('3014', '513301', '513300', '康定市', '中国,四川省,甘孜藏族自治州,康定市', '康定', '中国,四川,甘孜,康定', '3', '0836', '626000', 'Kangding', 'KD', 'K', '101.96487', '30.05532'),
('3015', '513322', '513300', '泸定县', '中国,四川省,甘孜藏族自治州,泸定县', '泸定', '中国,四川,甘孜,泸定', '3', '0836', '626100', 'Luding', 'LD', 'L', '102.23507', '29.91475'),
('3016', '513323', '513300', '丹巴县', '中国,四川省,甘孜藏族自治州,丹巴县', '丹巴', '中国,四川,甘孜,丹巴', '3', '0836', '626300', 'Danba', 'DB', 'D', '101.88424', '30.87656'),
('3017', '513324', '513300', '九龙县', '中国,四川省,甘孜藏族自治州,九龙县', '九龙', '中国,四川,甘孜,九龙', '3', '0836', '616200', 'Jiulong', 'JL', 'J', '101.50848', '29.00091'),
('3018', '513325', '513300', '雅江县', '中国,四川省,甘孜藏族自治州,雅江县', '雅江', '中国,四川,甘孜,雅江', '3', '0836', '627450', 'Yajiang', 'YJ', 'Y', '101.01492', '30.03281'),
('3019', '513326', '513300', '道孚县', '中国,四川省,甘孜藏族自治州,道孚县', '道孚', '中国,四川,甘孜,道孚', '3', '0836', '626400', 'Daofu', 'DF', 'D', '101.12554', '30.98046'),
('3020', '513327', '513300', '炉霍县', '中国,四川省,甘孜藏族自治州,炉霍县', '炉霍', '中国,四川,甘孜,炉霍', '3', '0836', '626500', 'Luhuo', 'LH', 'L', '100.67681', '31.3917'),
('3021', '513328', '513300', '甘孜县', '中国,四川省,甘孜藏族自治州,甘孜县', '甘孜', '中国,四川,甘孜,甘孜', '3', '0836', '626700', 'Ganzi', 'GZ', 'G', '99.99307', '31.62672'),
('3022', '513329', '513300', '新龙县', '中国,四川省,甘孜藏族自治州,新龙县', '新龙', '中国,四川,甘孜,新龙', '3', '0836', '626800', 'Xinlong', 'XL', 'X', '100.3125', '30.94067'),
('3023', '513330', '513300', '德格县', '中国,四川省,甘孜藏族自治州,德格县', '德格', '中国,四川,甘孜,德格', '3', '0836', '627250', 'Dege', 'DG', 'D', '98.58078', '31.80615'),
('3024', '513331', '513300', '白玉县', '中国,四川省,甘孜藏族自治州,白玉县', '白玉', '中国,四川,甘孜,白玉', '3', '0836', '627150', 'Baiyu', 'BY', 'B', '98.82568', '31.20902'),
('3025', '513332', '513300', '石渠县', '中国,四川省,甘孜藏族自治州,石渠县', '石渠', '中国,四川,甘孜,石渠', '3', '0836', '627350', 'Shiqu', 'SQ', 'S', '98.10156', '32.97884'),
('3026', '513333', '513300', '色达县', '中国,四川省,甘孜藏族自治州,色达县', '色达', '中国,四川,甘孜,色达', '3', '0836', '626600', 'Seda', 'SD', 'S', '100.33224', '32.26839'),
('3027', '513334', '513300', '理塘县', '中国,四川省,甘孜藏族自治州,理塘县', '理塘', '中国,四川,甘孜,理塘', '3', '0836', '624300', 'Litang', 'LT', 'L', '100.27005', '29.99674'),
('3028', '513335', '513300', '巴塘县', '中国,四川省,甘孜藏族自治州,巴塘县', '巴塘', '中国,四川,甘孜,巴塘', '3', '0836', '627650', 'Batang', 'BT', 'B', '99.10409', '30.00423'),
('3029', '513336', '513300', '乡城县', '中国,四川省,甘孜藏族自治州,乡城县', '乡城', '中国,四川,甘孜,乡城', '3', '0836', '627850', 'Xiangcheng', 'XC', 'X', '99.79943', '28.93554'),
('3030', '513337', '513300', '稻城县', '中国,四川省,甘孜藏族自治州,稻城县', '稻城', '中国,四川,甘孜,稻城', '3', '0836', '627750', 'Daocheng', 'DC', 'D', '100.29809', '29.0379'),
('3031', '513338', '513300', '得荣县', '中国,四川省,甘孜藏族自治州,得荣县', '得荣', '中国,四川,甘孜,得荣', '3', '0836', '627950', 'Derong', 'DR', 'D', '99.28628', '28.71297'),
('3032', '513400', '510000', '凉山彝族自治州', '中国,四川省,凉山彝族自治州', '凉山', '中国,四川,凉山', '2', '0834', '615000', 'Liangshan', 'LS', 'L', '102.258746', '27.886762'),
('3033', '513401', '513400', '西昌市', '中国,四川省,凉山彝族自治州,西昌市', '西昌', '中国,四川,凉山,西昌', '3', '0834', '615000', 'Xichang', 'XC', 'X', '102.26413', '27.89524'),
('3034', '513422', '513400', '木里藏族自治县', '中国,四川省,凉山彝族自治州,木里藏族自治县', '木里', '中国,四川,凉山,木里', '3', '0834', '615800', 'Muli', 'ML', 'M', '101.2796', '27.92875'),
('3035', '513423', '513400', '盐源县', '中国,四川省,凉山彝族自治州,盐源县', '盐源', '中国,四川,凉山,盐源', '3', '0834', '615700', 'Yanyuan', 'YY', 'Y', '101.5097', '27.42177'),
('3036', '513424', '513400', '德昌县', '中国,四川省,凉山彝族自治州,德昌县', '德昌', '中国,四川,凉山,德昌', '3', '0834', '615500', 'Dechang', 'DC', 'D', '102.18017', '27.40482'),
('3037', '513425', '513400', '会理县', '中国,四川省,凉山彝族自治州,会理县', '会理', '中国,四川,凉山,会理', '3', '0834', '615100', 'Huili', 'HL', 'H', '102.24539', '26.65627'),
('3038', '513426', '513400', '会东县', '中国,四川省,凉山彝族自治州,会东县', '会东', '中国,四川,凉山,会东', '3', '0834', '615200', 'Huidong', 'HD', 'H', '102.57815', '26.63429'),
('3039', '513427', '513400', '宁南县', '中国,四川省,凉山彝族自治州,宁南县', '宁南', '中国,四川,凉山,宁南', '3', '0834', '615400', 'Ningnan', 'NN', 'N', '102.76116', '27.06567'),
('3040', '513428', '513400', '普格县', '中国,四川省,凉山彝族自治州,普格县', '普格', '中国,四川,凉山,普格', '3', '0834', '615300', 'Puge', 'PG', 'P', '102.54055', '27.37485'),
('3041', '513429', '513400', '布拖县', '中国,四川省,凉山彝族自治州,布拖县', '布拖', '中国,四川,凉山,布拖', '3', '0834', '615350', 'Butuo', 'BT', 'B', '102.81234', '27.7079'),
('3042', '513430', '513400', '金阳县', '中国,四川省,凉山彝族自治州,金阳县', '金阳', '中国,四川,凉山,金阳', '3', '0834', '616250', 'Jinyang', 'JY', 'J', '103.24774', '27.69698'),
('3043', '513431', '513400', '昭觉县', '中国,四川省,凉山彝族自治州,昭觉县', '昭觉', '中国,四川,凉山,昭觉', '3', '0834', '616150', 'Zhaojue', 'ZJ', 'Z', '102.84661', '28.01155'),
('3044', '513432', '513400', '喜德县', '中国,四川省,凉山彝族自治州,喜德县', '喜德', '中国,四川,凉山,喜德', '3', '0834', '616750', 'Xide', 'XD', 'X', '102.41336', '28.30739'),
('3045', '513433', '513400', '冕宁县', '中国,四川省,凉山彝族自治州,冕宁县', '冕宁', '中国,四川,凉山,冕宁', '3', '0834', '615600', 'Mianning', 'MN', 'M', '102.17108', '28.55161'),
('3046', '513434', '513400', '越西县', '中国,四川省,凉山彝族自治州,越西县', '越西', '中国,四川,凉山,越西', '3', '0834', '616650', 'Yuexi', 'YX', 'Y', '102.5079', '28.64133'),
('3047', '513435', '513400', '甘洛县', '中国,四川省,凉山彝族自治州,甘洛县', '甘洛', '中国,四川,凉山,甘洛', '3', '0834', '616850', 'Ganluo', 'GL', 'G', '102.77154', '28.96624'),
('3048', '513436', '513400', '美姑县', '中国,四川省,凉山彝族自治州,美姑县', '美姑', '中国,四川,凉山,美姑', '3', '0834', '616450', 'Meigu', 'MG', 'M', '103.13116', '28.32596'),
('3049', '513437', '513400', '雷波县', '中国,四川省,凉山彝族自治州,雷波县', '雷波', '中国,四川,凉山,雷波', '3', '0834', '616550', 'Leibo', 'LB', 'L', '103.57287', '28.26407'),
('3050', '520000', '100000', '贵州省', '中国,贵州省', '贵州', '中国,贵州', '1', '', '', 'Guizhou', 'GZ', 'G', '106.713478', '26.578343'),
('3051', '520100', '520000', '贵阳市', '中国,贵州省,贵阳市', '贵阳', '中国,贵州,贵阳', '2', '0851', '550000', 'Guiyang', 'GY', 'G', '106.713478', '26.578343'),
('3052', '520102', '520100', '南明区', '中国,贵州省,贵阳市,南明区', '南明', '中国,贵州,贵阳,南明', '3', '0851', '550000', 'Nanming', 'NM', 'N', '106.7145', '26.56819'),
('3053', '520103', '520100', '云岩区', '中国,贵州省,贵阳市,云岩区', '云岩', '中国,贵州,贵阳,云岩', '3', '0851', '550000', 'Yunyan', 'YY', 'Y', '106.72485', '26.60484'),
('3054', '520111', '520100', '花溪区', '中国,贵州省,贵阳市,花溪区', '花溪', '中国,贵州,贵阳,花溪', '3', '0851', '550000', 'Huaxi', 'HX', 'H', '106.67688', '26.43343'),
('3055', '520112', '520100', '乌当区', '中国,贵州省,贵阳市,乌当区', '乌当', '中国,贵州,贵阳,乌当', '3', '0851', '550000', 'Wudang', 'WD', 'W', '106.7521', '26.6302'),
('3056', '520113', '520100', '白云区', '中国,贵州省,贵阳市,白云区', '白云', '中国,贵州,贵阳,白云', '3', '0851', '550000', 'Baiyun', 'BY', 'B', '106.63088', '26.68284'),
('3057', '520115', '520100', '观山湖区', '中国,贵州省,贵阳市,观山湖区', '观山湖', '中国,贵州,贵阳,观山湖', '3', '0851', '550009', 'Guanshanhu', 'GSH', 'G', '106.625442', '26.618209'),
('3058', '520121', '520100', '开阳县', '中国,贵州省,贵阳市,开阳县', '开阳', '中国,贵州,贵阳,开阳', '3', '0851', '550300', 'Kaiyang', 'KY', 'K', '106.9692', '27.05533'),
('3059', '520122', '520100', '息烽县', '中国,贵州省,贵阳市,息烽县', '息烽', '中国,贵州,贵阳,息烽', '3', '0851', '551100', 'Xifeng', 'XF', 'X', '106.738', '27.09346'),
('3060', '520123', '520100', '修文县', '中国,贵州省,贵阳市,修文县', '修文', '中国,贵州,贵阳,修文', '3', '0851', '550200', 'Xiuwen', 'XW', 'X', '106.59487', '26.83783'),
('3061', '520181', '520100', '清镇市', '中国,贵州省,贵阳市,清镇市', '清镇', '中国,贵州,贵阳,清镇', '3', '0851', '551400', 'Qingzhen', 'QZ', 'Q', '106.46862', '26.55261'),
('3062', '520182', '520100', '贵安新区', '中国,贵州省,贵阳市,贵安新区', '贵安新区', '中国,贵州,贵阳,贵安新区', '3', '0851', '550003', 'GuiAnXinQu', 'GAXQ', 'G', '106.630154', '26.647661'),
('3063', '520183', '520100', '高新区', '中国,贵州省,贵阳市,高新区', '高新区', '中国,贵州,贵阳,高新区', '3', '0851', '550003', 'GaoXinQu', 'GXQ', 'G', '106.645697', '26.620716'),
('3064', '520200', '520000', '六盘水市', '中国,贵州省,六盘水市', '六盘水', '中国,贵州,六盘水', '2', '0858', '553000', 'Liupanshui', 'LPS', 'L', '104.846743', '26.584643'),
('3065', '520201', '520200', '钟山区', '中国,贵州省,六盘水市,钟山区', '钟山', '中国,贵州,六盘水,钟山', '3', '0858', '553000', 'Zhongshan', 'ZS', 'Z', '104.87848', '26.57699'),
('3066', '520203', '520200', '六枝特区', '中国,贵州省,六盘水市,六枝特区', '六枝', '中国,贵州,六盘水,六枝', '3', '0858', '553400', 'Liuzhi', 'LZ', 'L', '105.48062', '26.20117'),
('3067', '520221', '520200', '水城县', '中国,贵州省,六盘水市,水城县', '水城', '中国,贵州,六盘水,水城', '3', '0858', '553600', 'Shuicheng', 'SC', 'S', '104.95764', '26.54785'),
('3068', '520222', '520200', '盘州市', '中国,贵州省,六盘水市,盘州市', '盘州', '中国,贵州,六盘水,盘州', '3', '0858', '553500', 'Panzhou', 'PZ', 'P', '104.47061', '25.7136'),
('3069', '520300', '520000', '遵义市', '中国,贵州省,遵义市', '遵义', '中国,贵州,遵义', '2', '0851', '563000', 'Zunyi', 'ZY', 'Z', '106.937265', '27.706626'),
('3070', '520302', '520300', '红花岗区', '中国,贵州省,遵义市,红花岗区', '红花岗', '中国,贵州,遵义,红花岗', '3', '0851', '563000', 'Honghuagang', 'HHG', 'H', '106.89404', '27.64471'),
('3071', '520303', '520300', '汇川区', '中国,贵州省,遵义市,汇川区', '汇川', '中国,贵州,遵义,汇川', '3', '0851', '563000', 'Huichuan', 'HC', 'H', '106.9393', '27.70625'),
('3072', '520304', '520300', '播州区', '中国,贵州省,遵义市,播州区', '播州', '中国,贵州,遵义,播州', '3', '0851', '563100', 'BoZhou', 'BZ', 'B', '106.83331', '27.53772'),
('3073', '520322', '520300', '桐梓县', '中国,贵州省,遵义市,桐梓县', '桐梓', '中国,贵州,遵义,桐梓', '3', '0851', '563200', 'Tongzi', 'TZ', 'T', '106.82568', '28.13806'),
('3074', '520323', '520300', '绥阳县', '中国,贵州省,遵义市,绥阳县', '绥阳', '中国,贵州,遵义,绥阳', '3', '0851', '563300', 'Suiyang', 'SY', 'S', '107.19064', '27.94702'),
('3075', '520324', '520300', '正安县', '中国,贵州省,遵义市,正安县', '正安', '中国,贵州,遵义,正安', '3', '0851', '563400', 'Zheng\'an', 'ZA', 'Z', '107.44357', '28.5512'),
('3076', '520325', '520300', '道真仡佬族苗族自治县', '中国,贵州省,遵义市,道真仡佬族苗族自治县', '道真', '中国,贵州,遵义,道真', '3', '0851', '563500', 'Daozhen', 'DZ', 'D', '107.61152', '28.864'),
('3077', '520326', '520300', '务川仡佬族苗族自治县', '中国,贵州省,遵义市,务川仡佬族苗族自治县', '务川', '中国,贵州,遵义,务川', '3', '0851', '564300', 'Wuchuan', 'WC', 'W', '107.88935', '28.52227'),
('3078', '520327', '520300', '凤冈县', '中国,贵州省,遵义市,凤冈县', '凤冈', '中国,贵州,遵义,凤冈', '3', '0851', '564200', 'Fenggang', 'FG', 'F', '107.71682', '27.95461'),
('3079', '520328', '520300', '湄潭县', '中国,贵州省,遵义市,湄潭县', '湄潭', '中国,贵州,遵义,湄潭', '3', '0851', '564100', 'Meitan', 'MT', 'M', '107.48779', '27.76676'),
('3080', '520329', '520300', '余庆县', '中国,贵州省,遵义市,余庆县', '余庆', '中国,贵州,遵义,余庆', '3', '0851', '564400', 'Yuqing', 'YQ', 'Y', '107.88821', '27.22532'),
('3081', '520330', '520300', '习水县', '中国,贵州省,遵义市,习水县', '习水', '中国,贵州,遵义,习水', '3', '0851', '564600', 'Xishui', 'XS', 'X', '106.21267', '28.31976'),
('3082', '520381', '520300', '赤水市', '中国,贵州省,遵义市,赤水市', '赤水', '中国,贵州,遵义,赤水', '3', '0851', '564700', 'Chishui', 'CS', 'C', '105.69845', '28.58921'),
('3083', '520382', '520300', '仁怀市', '中国,贵州省,遵义市,仁怀市', '仁怀', '中国,贵州,遵义,仁怀', '3', '0851', '564500', 'Renhuai', 'RH', 'R', '106.40152', '27.79231'),
('3084', '520400', '520000', '安顺市', '中国,贵州省,安顺市', '安顺', '中国,贵州,安顺', '2', '0851', '561000', 'Anshun', 'AS', 'A', '105.932188', '26.245544'),
('3085', '520402', '520400', '西秀区', '中国,贵州省,安顺市,西秀区', '西秀', '中国,贵州,安顺,西秀', '3', '0851', '561000', 'Xixiu', 'XX', 'X', '105.96585', '26.24491'),
('3086', '520403', '520400', '平坝区', '中国,贵州省,安顺市,平坝区', '平坝', '中国,贵州,安顺,平坝', '3', '0851', '561100', 'Pingba', 'PB', 'P', '106.25683', '26.40543'),
('3087', '520422', '520400', '普定县', '中国,贵州省,安顺市,普定县', '普定', '中国,贵州,安顺,普定', '3', '0851', '562100', 'Puding', 'PD', 'P', '105.74285', '26.30141'),
('3088', '520423', '520400', '镇宁布依族苗族自治县', '中国,贵州省,安顺市,镇宁布依族苗族自治县', '镇宁', '中国,贵州,安顺,镇宁', '3', '0851', '561200', 'Zhenning', 'ZN', 'Z', '105.76513', '26.05533'),
('3089', '520424', '520400', '关岭布依族苗族自治县', '中国,贵州省,安顺市,关岭布依族苗族自治县', '关岭', '中国,贵州,安顺,关岭', '3', '0851', '561300', 'Guanling', 'GL', 'G', '105.61883', '25.94248'),
('3090', '520425', '520400', '紫云苗族布依族自治县', '中国,贵州省,安顺市,紫云苗族布依族自治县', '紫云', '中国,贵州,安顺,紫云', '3', '0851', '550800', 'Ziyun', 'ZY', 'Z', '106.08364', '25.75258'),
('3091', '520500', '520000', '毕节市', '中国,贵州省,毕节市', '毕节', '中国,贵州,毕节', '2', '0857', '551700', 'Bijie', 'BJ', 'B', '105.28501', '27.301693'),
('3092', '520502', '520500', '七星关区', '中国,贵州省,毕节市,七星关区', '七星关', '中国,贵州,毕节,七星关', '3', '0857', '551700', 'Qixingguan', 'QXG', 'Q', '104.9497', '27.153556'),
('3093', '520521', '520500', '大方县', '中国,贵州省,毕节市,大方县', '大方', '中国,贵州,毕节,大方', '3', '0857', '551600', 'Dafang', 'DF', 'D', '105.609254', '27.143521'),
('3094', '520522', '520500', '黔西县', '中国,贵州省,毕节市,黔西县', '黔西', '中国,贵州,毕节,黔西', '3', '0857', '551500', 'Qianxi', 'QX', 'Q', '106.038299', '27.024923'),
('3095', '520523', '520500', '金沙县', '中国,贵州省,毕节市,金沙县', '金沙', '中国,贵州,毕节,金沙', '3', '0857', '551800', 'Jinsha', 'JS', 'J', '106.222103', '27.459693'),
('3096', '520524', '520500', '织金县', '中国,贵州省,毕节市,织金县', '织金', '中国,贵州,毕节,织金', '3', '0857', '552100', 'Zhijin', 'ZJ', 'Z', '105.768997', '26.668497'),
('3097', '520525', '520500', '纳雍县', '中国,贵州省,毕节市,纳雍县', '纳雍', '中国,贵州,毕节,纳雍', '3', '0857', '553300', 'Nayong', 'NY', 'N', '105.375322', '26.769875'),
('3098', '520526', '520500', '威宁彝族回族苗族自治县', '中国,贵州省,毕节市,威宁彝族回族苗族自治县', '威宁', '中国,贵州,毕节,威宁', '3', '0857', '553100', 'Weining', 'WN', 'W', '104.286523', '26.859099'),
('3099', '520527', '520500', '赫章县', '中国,贵州省,毕节市,赫章县', '赫章', '中国,贵州,毕节,赫章', '3', '0857', '553200', 'Hezhang', 'HZ', 'H', '104.726438', '27.119243'),
('3100', '520600', '520000', '铜仁市', '中国,贵州省,铜仁市', '铜仁', '中国,贵州,铜仁', '2', '0856', '554300', 'Tongren', 'TR', 'T', '109.191555', '27.718346'),
('3101', '520602', '520600', '碧江区', '中国,贵州省,铜仁市,碧江区', '碧江', '中国,贵州,铜仁,碧江', '3', '0856', '554300', 'Bijiang', 'BJ', 'B', '109.191555', '27.718346'),
('3102', '520603', '520600', '万山区', '中国,贵州省,铜仁市,万山区', '万山', '中国,贵州,铜仁,万山', '3', '0856', '554200', 'Wanshan', 'WS', 'W', '109.21199', '27.51903'),
('3103', '520621', '520600', '江口县', '中国,贵州省,铜仁市,江口县', '江口', '中国,贵州,铜仁,江口', '3', '0856', '554400', 'Jiangkou', 'JK', 'J', '108.848427', '27.691904'),
('3104', '520622', '520600', '玉屏侗族自治县', '中国,贵州省,铜仁市,玉屏侗族自治县', '玉屏', '中国,贵州,铜仁,玉屏', '3', '0856', '554004', 'Yuping', 'YP', 'Y', '108.917882', '27.238024'),
('3105', '520623', '520600', '石阡县', '中国,贵州省,铜仁市,石阡县', '石阡', '中国,贵州,铜仁,石阡', '3', '0856', '555100', 'Shiqian', 'SQ', 'S', '108.229854', '27.519386'),
('3106', '520624', '520600', '思南县', '中国,贵州省,铜仁市,思南县', '思南', '中国,贵州,铜仁,思南', '3', '0856', '565100', 'Sinan', 'SN', 'S', '108.255827', '27.941331'),
('3107', '520625', '520600', '印江土家族苗族自治县', '中国,贵州省,铜仁市,印江土家族苗族自治县', '印江', '中国,贵州,铜仁,印江', '3', '0856', '555200', 'Yinjiang', 'YJ', 'Y', '108.405517', '27.997976'),
('3108', '520626', '520600', '德江县', '中国,贵州省,铜仁市,德江县', '德江', '中国,贵州,铜仁,德江', '3', '0856', '565200', 'Dejiang', 'DJ', 'D', '108.117317', '28.26094'),
('3109', '520627', '520600', '沿河土家族自治县', '中国,贵州省,铜仁市,沿河土家族自治县', '沿河', '中国,贵州,铜仁,沿河', '3', '0856', '565300', 'Yuanhe', 'YH', 'Y', '108.495746', '28.560487'),
('3110', '520628', '520600', '松桃苗族自治县', '中国,贵州省,铜仁市,松桃苗族自治县', '松桃', '中国,贵州,铜仁,松桃', '3', '0856', '554100', 'Songtao', 'ST', 'S', '109.202627', '28.165419'),
('3111', '522300', '520000', '黔西南布依族苗族自治州', '中国,贵州省,黔西南布依族苗族自治州', '黔西南', '中国,贵州,黔西南', '2', '0859', '562400', 'Qianxinan', 'QXN', 'Q', '104.897971', '25.08812'),
('3112', '522301', '522300', '兴义市 ', '中国,贵州省,黔西南布依族苗族自治州,兴义市 ', '兴义', '中国,贵州,黔西南,兴义', '3', '0859', '562400', 'Xingyi', 'XY', 'X', '104.89548', '25.09205'),
('3113', '522322', '522300', '兴仁县', '中国,贵州省,黔西南布依族苗族自治州,兴仁县', '兴仁', '中国,贵州,黔西南,兴仁', '3', '0859', '562300', 'Xingren', 'XR', 'X', '105.18652', '25.43282'),
('3114', '522323', '522300', '普安县', '中国,贵州省,黔西南布依族苗族自治州,普安县', '普安', '中国,贵州,黔西南,普安', '3', '0859', '561500', 'Pu\'an', 'PA', 'P', '104.95529', '25.78603'),
('3115', '522324', '522300', '晴隆县', '中国,贵州省,黔西南布依族苗族自治州,晴隆县', '晴隆', '中国,贵州,黔西南,晴隆', '3', '0859', '561400', 'Qinglong', 'QL', 'Q', '105.2192', '25.83522'),
('3116', '522325', '522300', '贞丰县', '中国,贵州省,黔西南布依族苗族自治州,贞丰县', '贞丰', '中国,贵州,黔西南,贞丰', '3', '0859', '562200', 'Zhenfeng', 'ZF', 'Z', '105.65454', '25.38464'),
('3117', '522326', '522300', '望谟县', '中国,贵州省,黔西南布依族苗族自治州,望谟县', '望谟', '中国,贵州,黔西南,望谟', '3', '0859', '552300', 'Wangmo', 'WM', 'W', '106.09957', '25.17822'),
('3118', '522327', '522300', '册亨县', '中国,贵州省,黔西南布依族苗族自治州,册亨县', '册亨', '中国,贵州,黔西南,册亨', '3', '0859', '552200', 'Ceheng', 'CH', 'C', '105.8124', '24.98376'),
('3119', '522328', '522300', '安龙县', '中国,贵州省,黔西南布依族苗族自治州,安龙县', '安龙', '中国,贵州,黔西南,安龙', '3', '0859', '552400', 'Anlong', 'AL', 'A', '105.44268', '25.09818'),
('3120', '522600', '520000', '黔东南苗族侗族自治州', '中国,贵州省,黔东南苗族侗族自治州', '黔东南', '中国,贵州,黔东南', '2', '0855', '556000', 'Qiandongnan', 'QDN', 'Q', '107.977488', '26.583352'),
('3121', '522601', '522600', '凯里市', '中国,贵州省,黔东南苗族侗族自治州,凯里市', '凯里', '中国,贵州,黔东南,凯里', '3', '0855', '556000', 'Kaili', 'KL', 'K', '107.98132', '26.56689'),
('3122', '522622', '522600', '黄平县', '中国,贵州省,黔东南苗族侗族自治州,黄平县', '黄平', '中国,贵州,黔东南,黄平', '3', '0855', '556100', 'Huangping', 'HP', 'H', '107.90179', '26.89573'),
('3123', '522623', '522600', '施秉县', '中国,贵州省,黔东南苗族侗族自治州,施秉县', '施秉', '中国,贵州,黔东南,施秉', '3', '0855', '556200', 'Shibing', 'SB', 'S', '108.12597', '27.03495'),
('3124', '522624', '522600', '三穗县', '中国,贵州省,黔东南苗族侗族自治州,三穗县', '三穗', '中国,贵州,黔东南,三穗', '3', '0855', '556500', 'Sansui', 'SS', 'S', '108.67132', '26.94765'),
('3125', '522625', '522600', '镇远县', '中国,贵州省,黔东南苗族侗族自治州,镇远县', '镇远', '中国,贵州,黔东南,镇远', '3', '0855', '557700', 'Zhenyuan', 'ZY', 'Z', '108.42721', '27.04933'),
('3126', '522626', '522600', '岑巩县', '中国,贵州省,黔东南苗族侗族自治州,岑巩县', '岑巩', '中国,贵州,黔东南,岑巩', '3', '0855', '557800', 'Cengong', 'CG', 'C', '108.81884', '27.17539'),
('3127', '522627', '522600', '天柱县', '中国,贵州省,黔东南苗族侗族自治州,天柱县', '天柱', '中国,贵州,黔东南,天柱', '3', '0855', '556600', 'Tianzhu', 'TZ', 'T', '109.20718', '26.90781'),
('3128', '522628', '522600', '锦屏县', '中国,贵州省,黔东南苗族侗族自治州,锦屏县', '锦屏', '中国,贵州,黔东南,锦屏', '3', '0855', '556700', 'Jinping', 'JP', 'J', '109.19982', '26.67635'),
('3129', '522629', '522600', '剑河县', '中国,贵州省,黔东南苗族侗族自治州,剑河县', '剑河', '中国,贵州,黔东南,剑河', '3', '0855', '556400', 'Jianhe', 'JH', 'J', '108.5913', '26.6525'),
('3130', '522630', '522600', '台江县', '中国,贵州省,黔东南苗族侗族自治州,台江县', '台江', '中国,贵州,黔东南,台江', '3', '0855', '556300', 'Taijiang', 'TJ', 'T', '108.31814', '26.66916'),
('3131', '522631', '522600', '黎平县', '中国,贵州省,黔东南苗族侗族自治州,黎平县', '黎平', '中国,贵州,黔东南,黎平', '3', '0855', '557300', 'Liping', 'LP', 'L', '109.13607', '26.23114'),
('3132', '522632', '522600', '榕江县', '中国,贵州省,黔东南苗族侗族自治州,榕江县', '榕江', '中国,贵州,黔东南,榕江', '3', '0855', '557200', 'Rongjiang', 'RJ', 'R', '108.52072', '25.92421'),
('3133', '522633', '522600', '从江县', '中国,贵州省,黔东南苗族侗族自治州,从江县', '从江', '中国,贵州,黔东南,从江', '3', '0855', '557400', 'Congjiang', 'CJ', 'C', '108.90527', '25.75415'),
('3134', '522634', '522600', '雷山县', '中国,贵州省,黔东南苗族侗族自治州,雷山县', '雷山', '中国,贵州,黔东南,雷山', '3', '0855', '557100', 'Leishan', 'LS', 'L', '108.07745', '26.38385'),
('3135', '522635', '522600', '麻江县', '中国,贵州省,黔东南苗族侗族自治州,麻江县', '麻江', '中国,贵州,黔东南,麻江', '3', '0855', '557600', 'Majiang', 'MJ', 'M', '107.59155', '26.49235'),
('3136', '522636', '522600', '丹寨县', '中国,贵州省,黔东南苗族侗族自治州,丹寨县', '丹寨', '中国,贵州,黔东南,丹寨', '3', '0855', '557500', 'Danzhai', 'DZ', 'D', '107.79718', '26.19816'),
('3137', '522700', '520000', '黔南布依族苗族自治州', '中国,贵州省,黔南布依族苗族自治州', '黔南', '中国,贵州,黔南', '2', '0854', '558000', 'Qiannan', 'QN', 'Q', '107.517156', '26.258219'),
('3138', '522701', '522700', '都匀市', '中国,贵州省,黔南布依族苗族自治州,都匀市', '都匀', '中国,贵州,黔南,都匀', '3', '0854', '558000', 'Duyun', 'DY', 'D', '107.51872', '26.2594'),
('3139', '522702', '522700', '福泉市', '中国,贵州省,黔南布依族苗族自治州,福泉市', '福泉', '中国,贵州,黔南,福泉', '3', '0854', '550500', 'Fuquan', 'FQ', 'F', '107.51715', '26.67989'),
('3140', '522722', '522700', '荔波县', '中国,贵州省,黔南布依族苗族自治州,荔波县', '荔波', '中国,贵州,黔南,荔波', '3', '0854', '558400', 'Libo', 'LB', 'L', '107.88592', '25.4139'),
('3141', '522723', '522700', '贵定县', '中国,贵州省,黔南布依族苗族自治州,贵定县', '贵定', '中国,贵州,黔南,贵定', '3', '0854', '551300', 'Guiding', 'GD', 'G', '107.23654', '26.57812'),
('3142', '522725', '522700', '瓮安县', '中国,贵州省,黔南布依族苗族自治州,瓮安县', '瓮安', '中国,贵州,黔南,瓮安', '3', '0854', '550400', 'Weng\'an', 'WA', 'W', '107.4757', '27.06813'),
('3143', '522726', '522700', '独山县', '中国,贵州省,黔南布依族苗族自治州,独山县', '独山', '中国,贵州,黔南,独山', '3', '0854', '558200', 'Dushan', 'DS', 'D', '107.54101', '25.8245'),
('3144', '522727', '522700', '平塘县', '中国,贵州省,黔南布依族苗族自治州,平塘县', '平塘', '中国,贵州,黔南,平塘', '3', '0854', '558300', 'Pingtang', 'PT', 'P', '107.32428', '25.83294'),
('3145', '522728', '522700', '罗甸县', '中国,贵州省,黔南布依族苗族自治州,罗甸县', '罗甸', '中国,贵州,黔南,罗甸', '3', '0854', '550100', 'Luodian', 'LD', 'L', '106.75186', '25.42586'),
('3146', '522729', '522700', '长顺县', '中国,贵州省,黔南布依族苗族自治州,长顺县', '长顺', '中国,贵州,黔南,长顺', '3', '0854', '550700', 'Changshun', 'CS', 'C', '106.45217', '26.02299'),
('3147', '522730', '522700', '龙里县', '中国,贵州省,黔南布依族苗族自治州,龙里县', '龙里', '中国,贵州,黔南,龙里', '3', '0854', '551200', 'Longli', 'LL', 'L', '106.97662', '26.45076'),
('3148', '522731', '522700', '惠水县', '中国,贵州省,黔南布依族苗族自治州,惠水县', '惠水', '中国,贵州,黔南,惠水', '3', '0854', '550600', 'Huishui', 'HS', 'H', '106.65911', '26.13389'),
('3149', '522732', '522700', '三都水族自治县', '中国,贵州省,黔南布依族苗族自治州,三都水族自治县', '三都', '中国,贵州,黔南,三都', '3', '0854', '558100', 'Sandu', 'SD', 'S', '107.87464', '25.98562'),
('3150', '530000', '100000', '云南省', '中国,云南省', '云南', '中国,云南', '1', '', '', 'Yunnan', 'YN', 'Y', '102.712251', '25.040609'),
('3151', '530100', '530000', '昆明市', '中国,云南省,昆明市', '昆明', '中国,云南,昆明', '2', '0871', '650000', 'Kunming', 'KM', 'K', '102.712251', '25.040609'),
('3152', '530102', '530100', '五华区', '中国,云南省,昆明市,五华区', '五华', '中国,云南,昆明,五华', '3', '0871', '650000', 'Wuhua', 'WH', 'W', '102.70786', '25.03521'),
('3153', '530103', '530100', '盘龙区', '中国,云南省,昆明市,盘龙区', '盘龙', '中国,云南,昆明,盘龙', '3', '0871', '650000', 'Panlong', 'PL', 'P', '102.71994', '25.04053'),
('3154', '530111', '530100', '官渡区', '中国,云南省,昆明市,官渡区', '官渡', '中国,云南,昆明,官渡', '3', '0871', '650200', 'Guandu', 'GD', 'G', '102.74362', '25.01497'),
('3155', '530112', '530100', '西山区', '中国,云南省,昆明市,西山区', '西山', '中国,云南,昆明,西山', '3', '0871', '650100', 'Xishan', 'XS', 'X', '102.66464', '25.03796'),
('3156', '530113', '530100', '东川区', '中国,云南省,昆明市,东川区', '东川', '中国,云南,昆明,东川', '3', '0871', '654100', 'Dongchuan', 'DC', 'D', '103.18832', '26.083'),
('3157', '530114', '530100', '呈贡区', '中国,云南省,昆明市,呈贡区', '呈贡', '中国,云南,昆明,呈贡', '3', '0871', '650500', 'Chenggong', 'CG', 'C', '102.801382', '24.889275'),
('3158', '530115', '530100', '晋宁区', '中国,云南省,昆明市,晋宁区', '晋宁', '中国,云南,昆明,晋宁', '3', '0871', '650600', 'Jinning', 'JN', 'J', '102.59393', '24.6665'),
('3159', '530124', '530100', '富民县', '中国,云南省,昆明市,富民县', '富民', '中国,云南,昆明,富民', '3', '0871', '650400', 'Fumin', 'FM', 'F', '102.4985', '25.22119'),
('3160', '530125', '530100', '宜良县', '中国,云南省,昆明市,宜良县', '宜良', '中国,云南,昆明,宜良', '3', '0871', '652100', 'Yiliang', 'YL', 'Y', '103.14117', '24.91705'),
('3161', '530126', '530100', '石林彝族自治县', '中国,云南省,昆明市,石林彝族自治县', '石林', '中国,云南,昆明,石林', '3', '0871', '652200', 'Shilin', 'SL', 'S', '103.27148', '24.75897'),
('3162', '530127', '530100', '嵩明县', '中国,云南省,昆明市,嵩明县', '嵩明', '中国,云南,昆明,嵩明', '3', '0871', '651700', 'Songming', 'SM', 'S', '103.03729', '25.33986'),
('3163', '530128', '530100', '禄劝彝族苗族自治县', '中国,云南省,昆明市,禄劝彝族苗族自治县', '禄劝', '中国,云南,昆明,禄劝', '3', '0871', '651500', 'Luquan', 'LQ', 'L', '102.4671', '25.55387'),
('3164', '530129', '530100', '寻甸回族彝族自治县 ', '中国,云南省,昆明市,寻甸回族彝族自治县 ', '寻甸', '中国,云南,昆明,寻甸', '3', '0871', '655200', 'Xundian', 'XD', 'X', '103.2557', '25.55961'),
('3165', '530181', '530100', '安宁市', '中国,云南省,昆明市,安宁市', '安宁', '中国,云南,昆明,安宁', '3', '0871', '650300', 'Anning', 'AN', 'A', '102.46972', '24.91652'),
('3166', '530182', '530100', '滇中新区', '中国,云南省,昆明市,滇中新区', '滇中新区', '中国,云南,昆明,滇中新区', '3', '0871', '650000', 'DianZhongXinQu', 'DZXQ', 'D', '102.712251', '25.040609'),
('3167', '530183', '530100', '高新区', '中国,云南省,昆明市,高新区', '高新区', '中国,云南,昆明,高新区', '3', '0871', '650000', 'Gaoxinqu', 'GXQ', 'G', '102.653544', '25.072794'),
('3168', '530300', '530000', '曲靖市', '中国,云南省,曲靖市', '曲靖', '中国,云南,曲靖', '2', '0874', '655000', 'Qujing', 'QJ', 'Q', '103.797851', '25.501557'),
('3169', '530302', '530300', '麒麟区', '中国,云南省,曲靖市,麒麟区', '麒麟', '中国,云南,曲靖,麒麟', '3', '0874', '655000', 'Qilin', 'QL', 'Q', '103.80504', '25.49515'),
('3170', '530303', '530300', '沾益区', '中国,云南省,曲靖市,沾益区', '沾益', '中国,云南,曲靖,沾益', '3', '0874', '655331', 'Zhanyi', 'ZY', 'Z', '103.82135', '25.60715'),
('3171', '530321', '530300', '马龙县', '中国,云南省,曲靖市,马龙县', '马龙', '中国,云南,曲靖,马龙', '3', '0874', '655100', 'Malong', 'ML', 'M', '103.57873', '25.42521'),
('3172', '530322', '530300', '陆良县', '中国,云南省,曲靖市,陆良县', '陆良', '中国,云南,曲靖,陆良', '3', '0874', '655600', 'Luliang', 'LL', 'L', '103.6665', '25.02335'),
('3173', '530323', '530300', '师宗县', '中国,云南省,曲靖市,师宗县', '师宗', '中国,云南,曲靖,师宗', '3', '0874', '655700', 'Shizong', 'SZ', 'S', '103.99084', '24.82822'),
('3174', '530324', '530300', '罗平县', '中国,云南省,曲靖市,罗平县', '罗平', '中国,云南,曲靖,罗平', '3', '0874', '655800', 'Luoping', 'LP', 'L', '104.30859', '24.88444'),
('3175', '530325', '530300', '富源县', '中国,云南省,曲靖市,富源县', '富源', '中国,云南,曲靖,富源', '3', '0874', '655500', 'Fuyuan', 'FY', 'F', '104.25387', '25.66587'),
('3176', '530326', '530300', '会泽县', '中国,云南省,曲靖市,会泽县', '会泽', '中国,云南,曲靖,会泽', '3', '0874', '654200', 'Huize', 'HZ', 'H', '103.30017', '26.41076'),
('3177', '530381', '530300', '宣威市', '中国,云南省,曲靖市,宣威市', '宣威', '中国,云南,曲靖,宣威', '3', '0874', '655400', 'Xuanwei', 'XW', 'X', '104.10409', '26.2173'),
('3178', '530400', '530000', '玉溪市', '中国,云南省,玉溪市', '玉溪', '中国,云南,玉溪', '2', '0877', '653100', 'Yuxi', 'YX', 'Y', '102.543907', '24.350461'),
('3179', '530402', '530400', '红塔区', '中国,云南省,玉溪市,红塔区', '红塔', '中国,云南,玉溪,红塔', '3', '0877', '653100', 'Hongta', 'HT', 'H', '102.5449', '24.35411'),
('3180', '530403', '530400', '江川区', '中国,云南省,玉溪市,江川区', '江川', '中国,云南,玉溪,江川', '3', '0877', '652600', 'Jiangchuan', 'JC', 'J', '102.75412', '24.28863'),
('3181', '530422', '530400', '澄江县', '中国,云南省,玉溪市,澄江县', '澄江', '中国,云南,玉溪,澄江', '3', '0877', '652500', 'Chengjiang', 'CJ', 'C', '102.90817', '24.67376'),
('3182', '530423', '530400', '通海县', '中国,云南省,玉溪市,通海县', '通海', '中国,云南,玉溪,通海', '3', '0877', '652700', 'Tonghai', 'TH', 'T', '102.76641', '24.11362'),
('3183', '530424', '530400', '华宁县', '中国,云南省,玉溪市,华宁县', '华宁', '中国,云南,玉溪,华宁', '3', '0877', '652800', 'Huaning', 'HN', 'H', '102.92831', '24.1926'),
('3184', '530425', '530400', '易门县', '中国,云南省,玉溪市,易门县', '易门', '中国,云南,玉溪,易门', '3', '0877', '651100', 'Yimen', 'YM', 'Y', '102.16354', '24.67122'),
('3185', '530426', '530400', '峨山彝族自治县', '中国,云南省,玉溪市,峨山彝族自治县', '峨山', '中国,云南,玉溪,峨山', '3', '0877', '653200', 'Eshan', 'ES', 'E', '102.40576', '24.16904'),
('3186', '530427', '530400', '新平彝族傣族自治县', '中国,云南省,玉溪市,新平彝族傣族自治县', '新平', '中国,云南,玉溪,新平', '3', '0877', '653400', 'Xinping', 'XP', 'X', '101.98895', '24.06886'),
('3187', '530428', '530400', '元江哈尼族彝族傣族自治县', '中国,云南省,玉溪市,元江哈尼族彝族傣族自治县', '元江', '中国,云南,玉溪,元江', '3', '0877', '653300', 'Yuanjiang', 'YJ', 'Y', '101.99812', '23.59655'),
('3188', '530500', '530000', '保山市', '中国,云南省,保山市', '保山', '中国,云南,保山', '2', '0875', '678000', 'Baoshan', 'BS', 'B', '99.167133', '25.111802'),
('3189', '530502', '530500', '隆阳区', '中国,云南省,保山市,隆阳区', '隆阳', '中国,云南,保山,隆阳', '3', '0875', '678000', 'Longyang', 'LY', 'L', '99.16334', '25.11163'),
('3190', '530521', '530500', '施甸县', '中国,云南省,保山市,施甸县', '施甸', '中国,云南,保山,施甸', '3', '0875', '678200', 'Shidian', 'SD', 'S', '99.18768', '24.72418'),
('3191', '530523', '530500', '龙陵县', '中国,云南省,保山市,龙陵县', '龙陵', '中国,云南,保山,龙陵', '3', '0875', '678300', 'Longling', 'LL', 'L', '98.69024', '24.58746'),
('3192', '530524', '530500', '昌宁县', '中国,云南省,保山市,昌宁县', '昌宁', '中国,云南,保山,昌宁', '3', '0875', '678100', 'Changning', 'CN', 'C', '99.6036', '24.82763'),
('3193', '530581', '530500', '腾冲市', '中国,云南省,保山市,腾冲市', '腾冲', '中国,云南,保山,腾冲', '3', '0875', '679100', 'Tengchong', 'TC', 'T', '98.49414', '25.02539'),
('3194', '530600', '530000', '昭通市', '中国,云南省,昭通市', '昭通', '中国,云南,昭通', '2', '0870', '657000', 'Zhaotong', 'ZT', 'Z', '103.717216', '27.336999'),
('3195', '530602', '530600', '昭阳区', '中国,云南省,昭通市,昭阳区', '昭阳', '中国,云南,昭通,昭阳', '3', '0870', '657000', 'Zhaoyang', 'ZY', 'Z', '103.70654', '27.31998'),
('3196', '530621', '530600', '鲁甸县', '中国,云南省,昭通市,鲁甸县', '鲁甸', '中国,云南,昭通,鲁甸', '3', '0870', '657100', 'Ludian', 'LD', 'L', '103.54721', '27.19238'),
('3197', '530622', '530600', '巧家县', '中国,云南省,昭通市,巧家县', '巧家', '中国,云南,昭通,巧家', '3', '0870', '654600', 'Qiaojia', 'QJ', 'Q', '102.92416', '26.91237'),
('3198', '530623', '530600', '盐津县', '中国,云南省,昭通市,盐津县', '盐津', '中国,云南,昭通,盐津', '3', '0870', '657500', 'Yanjin', 'YJ', 'Y', '104.23461', '28.10856'),
('3199', '530624', '530600', '大关县', '中国,云南省,昭通市,大关县', '大关', '中国,云南,昭通,大关', '3', '0870', '657400', 'Daguan', 'DG', 'D', '103.89254', '27.7488'),
('3200', '530625', '530600', '永善县', '中国,云南省,昭通市,永善县', '永善', '中国,云南,昭通,永善', '3', '0870', '657300', 'Yongshan', 'YS', 'Y', '103.63504', '28.2279'),
('3201', '530626', '530600', '绥江县', '中国,云南省,昭通市,绥江县', '绥江', '中国,云南,昭通,绥江', '3', '0870', '657700', 'Suijiang', 'SJ', 'S', '103.94937', '28.59661'),
('3202', '530627', '530600', '镇雄县', '中国,云南省,昭通市,镇雄县', '镇雄', '中国,云南,昭通,镇雄', '3', '0870', '657200', 'Zhenxiong', 'ZX', 'Z', '104.87258', '27.43981'),
('3203', '530628', '530600', '彝良县', '中国,云南省,昭通市,彝良县', '彝良', '中国,云南,昭通,彝良', '3', '0870', '657600', 'Yiliang', 'YL', 'Y', '104.04983', '27.62809'),
('3204', '530629', '530600', '威信县', '中国,云南省,昭通市,威信县', '威信', '中国,云南,昭通,威信', '3', '0870', '657900', 'Weixin', 'WX', 'W', '105.04754', '27.84065'),
('3205', '530630', '530600', '水富县', '中国,云南省,昭通市,水富县', '水富', '中国,云南,昭通,水富', '3', '0870', '657800', 'Shuifu', 'SF', 'S', '104.4158', '28.62986'),
('3206', '530700', '530000', '丽江市', '中国,云南省,丽江市', '丽江', '中国,云南,丽江', '2', '0888', '674100', 'Lijiang', 'LJ', 'L', '100.233026', '26.872108'),
('3207', '530702', '530700', '古城区', '中国,云南省,丽江市,古城区', '古城', '中国,云南,丽江,古城', '3', '0888', '674100', 'Gucheng', 'GC', 'G', '100.2257', '26.87697'),
('3208', '530721', '530700', '玉龙纳西族自治县', '中国,云南省,丽江市,玉龙纳西族自治县', '玉龙', '中国,云南,丽江,玉龙', '3', '0888', '674100', 'Yulong', 'YL', 'Y', '100.2369', '26.82149'),
('3209', '530722', '530700', '永胜县', '中国,云南省,丽江市,永胜县', '永胜', '中国,云南,丽江,永胜', '3', '0888', '674200', 'Yongsheng', 'YS', 'Y', '100.74667', '26.68591'),
('3210', '530723', '530700', '华坪县', '中国,云南省,丽江市,华坪县', '华坪', '中国,云南,丽江,华坪', '3', '0888', '674800', 'Huaping', 'HP', 'H', '101.26562', '26.62967'),
('3211', '530724', '530700', '宁蒗彝族自治县', '中国,云南省,丽江市,宁蒗彝族自治县', '宁蒗', '中国,云南,丽江,宁蒗', '3', '0888', '674300', 'Ninglang', 'NL', 'N', '100.8507', '27.28179'),
('3212', '530800', '530000', '普洱市', '中国,云南省,普洱市', '普洱', '中国,云南,普洱', '2', '0879', '665000', 'Pu\'er', 'PE', 'P', '100.972344', '22.777321'),
('3213', '530802', '530800', '思茅区', '中国,云南省,普洱市,思茅区', '思茅', '中国,云南,普洱,思茅', '3', '0879', '665000', 'Simao', 'SM', 'S', '100.97716', '22.78691'),
('3214', '530821', '530800', '宁洱哈尼族彝族自治县', '中国,云南省,普洱市,宁洱哈尼族彝族自治县', '宁洱', '中国,云南,普洱,宁洱', '3', '0879', '665100', 'Ninger', 'NE', 'N', '101.04653', '23.06341'),
('3215', '530822', '530800', '墨江哈尼族自治县', '中国,云南省,普洱市,墨江哈尼族自治县', '墨江', '中国,云南,普洱,墨江', '3', '0879', '654800', 'Mojiang', 'MJ', 'M', '101.69171', '23.43214'),
('3216', '530823', '530800', '景东彝族自治县', '中国,云南省,普洱市,景东彝族自治县', '景东', '中国,云南,普洱,景东', '3', '0879', '676200', 'Jingdong', 'JD', 'J', '100.83599', '24.44791'),
('3217', '530824', '530800', '景谷傣族彝族自治县', '中国,云南省,普洱市,景谷傣族彝族自治县', '景谷', '中国,云南,普洱,景谷', '3', '0879', '666400', 'Jinggu', 'JG', 'J', '100.70251', '23.49705'),
('3218', '530825', '530800', '镇沅彝族哈尼族拉祜族自治县', '中国,云南省,普洱市,镇沅彝族哈尼族拉祜族自治县', '镇沅', '中国,云南,普洱,镇沅', '3', '0879', '666500', 'Zhenyuan', 'ZY', 'Z', '101.10675', '24.00557'),
('3219', '530826', '530800', '江城哈尼族彝族自治县', '中国,云南省,普洱市,江城哈尼族彝族自治县', '江城', '中国,云南,普洱,江城', '3', '0879', '665900', 'Jiangcheng', 'JC', 'J', '101.85788', '22.58424'),
('3220', '530827', '530800', '孟连傣族拉祜族佤族自治县', '中国,云南省,普洱市,孟连傣族拉祜族佤族自治县', '孟连', '中国,云南,普洱,孟连', '3', '0879', '665800', 'Menglian', 'ML', 'M', '99.58424', '22.32922'),
('3221', '530828', '530800', '澜沧拉祜族自治县', '中国,云南省,普洱市,澜沧拉祜族自治县', '澜沧', '中国,云南,普洱,澜沧', '3', '0879', '665600', 'Lancang', 'LC', 'L', '99.93591', '22.55474'),
('3222', '530829', '530800', '西盟佤族自治县', '中国,云南省,普洱市,西盟佤族自治县', '西盟', '中国,云南,普洱,西盟', '3', '0879', '665700', 'Ximeng', 'XM', 'X', '99.59869', '22.64774'),
('3223', '530900', '530000', '临沧市', '中国,云南省,临沧市', '临沧', '中国,云南,临沧', '2', '0883', '677000', 'Lincang', 'LC', 'L', '100.08697', '23.886567'),
('3224', '530902', '530900', '临翔区', '中国,云南省,临沧市,临翔区', '临翔', '中国,云南,临沧,临翔', '3', '0883', '677000', 'Linxiang', 'LX', 'L', '100.08242', '23.89497'),
('3225', '530921', '530900', '凤庆县', '中国,云南省,临沧市,凤庆县', '凤庆', '中国,云南,临沧,凤庆', '3', '0883', '675900', 'Fengqing', 'FQ', 'F', '99.92837', '24.58034'),
('3226', '530922', '530900', '云县', '中国,云南省,临沧市,云县', '云县', '中国,云南,临沧,云县', '3', '0883', '675800', 'Yunxian', 'YX', 'Y', '100.12808', '24.44675'),
('3227', '530923', '530900', '永德县', '中国,云南省,临沧市,永德县', '永德', '中国,云南,临沧,永德', '3', '0883', '677600', 'Yongde', 'YD', 'Y', '99.25326', '24.0276'),
('3228', '530924', '530900', '镇康县', '中国,云南省,临沧市,镇康县', '镇康', '中国,云南,临沧,镇康', '3', '0883', '677700', 'Zhenkang', 'ZK', 'Z', '98.8255', '23.76241'),
('3229', '530925', '530900', '双江拉祜族佤族布朗族傣族自治县', '中国,云南省,临沧市,双江拉祜族佤族布朗族傣族自治县', '双江', '中国,云南,临沧,双江', '3', '0883', '677300', 'Shuangjiang', 'SJ', 'S', '99.82769', '23.47349'),
('3230', '530926', '530900', '耿马傣族佤族自治县', '中国,云南省,临沧市,耿马傣族佤族自治县', '耿马', '中国,云南,临沧,耿马', '3', '0883', '677500', 'Gengma', 'GM', 'G', '99.39785', '23.53776'),
('3231', '530927', '530900', '沧源佤族自治县', '中国,云南省,临沧市,沧源佤族自治县', '沧源', '中国,云南,临沧,沧源', '3', '0883', '677400', 'Cangyuan', 'CY', 'C', '99.24545', '23.14821'),
('3232', '532300', '530000', '楚雄彝族自治州', '中国,云南省,楚雄彝族自治州', '楚雄', '中国,云南,楚雄', '2', '0878', '675000', 'Chuxiong', 'CX', 'C', '101.546046', '25.041988'),
('3233', '532301', '532300', '楚雄市', '中国,云南省,楚雄彝族自治州,楚雄市', '楚雄', '中国,云南,楚雄,楚雄', '3', '0878', '675000', 'Chuxiong', 'CX', 'C', '101.54615', '25.0329'),
('3234', '532322', '532300', '双柏县', '中国,云南省,楚雄彝族自治州,双柏县', '双柏', '中国,云南,楚雄,双柏', '3', '0878', '675100', 'Shuangbai', 'SB', 'S', '101.64205', '24.68882'),
('3235', '532323', '532300', '牟定县', '中国,云南省,楚雄彝族自治州,牟定县', '牟定', '中国,云南,楚雄,牟定', '3', '0878', '675500', 'Mouding', 'MD', 'M', '101.54', '25.31551'),
('3236', '532324', '532300', '南华县', '中国,云南省,楚雄彝族自治州,南华县', '南华', '中国,云南,楚雄,南华', '3', '0878', '675200', 'Nanhua', 'NH', 'N', '101.27313', '25.19293'),
('3237', '532325', '532300', '姚安县', '中国,云南省,楚雄彝族自治州,姚安县', '姚安', '中国,云南,楚雄,姚安', '3', '0878', '675300', 'Yao\'an', 'YA', 'Y', '101.24279', '25.50467'),
('3238', '532326', '532300', '大姚县', '中国,云南省,楚雄彝族自治州,大姚县', '大姚', '中国,云南,楚雄,大姚', '3', '0878', '675400', 'Dayao', 'DY', 'D', '101.32397', '25.72218'),
('3239', '532327', '532300', '永仁县', '中国,云南省,楚雄彝族自治州,永仁县', '永仁', '中国,云南,楚雄,永仁', '3', '0878', '651400', 'Yongren', 'YR', 'Y', '101.6716', '26.05794'),
('3240', '532328', '532300', '元谋县', '中国,云南省,楚雄彝族自治州,元谋县', '元谋', '中国,云南,楚雄,元谋', '3', '0878', '651300', 'Yuanmou', 'YM', 'Y', '101.87728', '25.70438'),
('3241', '532329', '532300', '武定县', '中国,云南省,楚雄彝族自治州,武定县', '武定', '中国,云南,楚雄,武定', '3', '0878', '651600', 'Wuding', 'WD', 'W', '102.4038', '25.5295'),
('3242', '532331', '532300', '禄丰县', '中国,云南省,楚雄彝族自治州,禄丰县', '禄丰', '中国,云南,楚雄,禄丰', '3', '0878', '651200', 'Lufeng', 'LF', 'L', '102.07797', '25.14815'),
('3243', '532500', '530000', '红河哈尼族彝族自治州', '中国,云南省,红河哈尼族彝族自治州', '红河', '中国,云南,红河', '2', '0873', '661400', 'Honghe', 'HH', 'H', '103.384182', '23.366775'),
('3244', '532501', '532500', '个旧市', '中国,云南省,红河哈尼族彝族自治州,个旧市', '个旧', '中国,云南,红河,个旧', '3', '0873', '661000', 'Gejiu', 'GJ', 'G', '103.15966', '23.35894'),
('3245', '532502', '532500', '开远市', '中国,云南省,红河哈尼族彝族自治州,开远市', '开远', '中国,云南,红河,开远', '3', '0873', '661600', 'Kaiyuan', 'KY', 'K', '103.26986', '23.71012'),
('3246', '532503', '532500', '蒙自市', '中国,云南省,红河哈尼族彝族自治州,蒙自市', '蒙自', '中国,云南,红河,蒙自', '3', '0873', '661101', 'Mengzi', 'MZ', 'M', '103.385005', '23.366843'),
('3247', '532504', '532500', '弥勒市', '中国,云南省,红河哈尼族彝族自治州,弥勒市', '弥勒', '中国,云南,红河,弥勒', '3', '0873', '652300', 'Mile ', 'ML', 'M', '103.436988', '24.40837'),
('3248', '532523', '532500', '屏边苗族自治县', '中国,云南省,红河哈尼族彝族自治州,屏边苗族自治县', '屏边', '中国,云南,红河,屏边', '3', '0873', '661200', 'Pingbian', 'PB', 'P', '103.68554', '22.98425'),
('3249', '532524', '532500', '建水县', '中国,云南省,红河哈尼族彝族自治州,建水县', '建水', '中国,云南,红河,建水', '3', '0873', '654300', 'Jianshui', 'JS', 'J', '102.82656', '23.63472'),
('3250', '532525', '532500', '石屏县', '中国,云南省,红河哈尼族彝族自治州,石屏县', '石屏', '中国,云南,红河,石屏', '3', '0873', '662200', 'Shiping', 'SP', 'S', '102.49408', '23.71441'),
('3251', '532527', '532500', '泸西县', '中国,云南省,红河哈尼族彝族自治州,泸西县', '泸西', '中国,云南,红河,泸西', '3', '0873', '652400', 'Luxi', 'LX', 'L', '103.76373', '24.52854'),
('3252', '532528', '532500', '元阳县', '中国,云南省,红河哈尼族彝族自治州,元阳县', '元阳', '中国,云南,红河,元阳', '3', '0873', '662400', 'Yuanyang', 'YY', 'Y', '102.83261', '23.22281'),
('3253', '532529', '532500', '红河县', '中国,云南省,红河哈尼族彝族自治州,红河县', '红河县', '中国,云南,红河,红河县', '3', '0873', '654400', 'Honghexian', 'HHX', 'H', '102.42059', '23.36767'),
('3254', '532530', '532500', '金平苗族瑶族傣族自治县', '中国,云南省,红河哈尼族彝族自治州,金平苗族瑶族傣族自治县', '金平', '中国,云南,红河,金平', '3', '0873', '661500', 'Jinping', 'JP', 'J', '103.22651', '22.77959'),
('3255', '532531', '532500', '绿春县', '中国,云南省,红河哈尼族彝族自治州,绿春县', '绿春', '中国,云南,红河,绿春', '3', '0873', '662500', 'Lvchun', 'LC', 'L', '102.39672', '22.99371'),
('3256', '532532', '532500', '河口瑶族自治县', '中国,云南省,红河哈尼族彝族自治州,河口瑶族自治县', '河口', '中国,云南,红河,河口', '3', '0873', '661300', 'Hekou', 'HK', 'H', '103.93936', '22.52929'),
('3257', '532600', '530000', '文山壮族苗族自治州', '中国,云南省,文山壮族苗族自治州', '文山', '中国,云南,文山', '2', '0876', '663000', 'Wenshan', 'WS', 'W', '104.24401', '23.36951'),
('3258', '532601', '532600', '文山市', '中国,云南省,文山壮族苗族自治州,文山市', '文山', '中国,云南,文山,文山', '3', '0876', '663000', 'Wenshan', 'WS', 'W', '104.244277', '23.369216'),
('3259', '532622', '532600', '砚山县', '中国,云南省,文山壮族苗族自治州,砚山县', '砚山', '中国,云南,文山,砚山', '3', '0876', '663100', 'Yanshan', 'YS', 'Y', '104.33306', '23.60723'),
('3260', '532623', '532600', '西畴县', '中国,云南省,文山壮族苗族自治州,西畴县', '西畴', '中国,云南,文山,西畴', '3', '0876', '663500', 'Xichou', 'XC', 'X', '104.67419', '23.43941'),
('3261', '532624', '532600', '麻栗坡县', '中国,云南省,文山壮族苗族自治州,麻栗坡县', '麻栗坡', '中国,云南,文山,麻栗坡', '3', '0876', '663600', 'Malipo', 'MLP', 'M', '104.70132', '23.12028'),
('3262', '532625', '532600', '马关县', '中国,云南省,文山壮族苗族自治州,马关县', '马关', '中国,云南,文山,马关', '3', '0876', '663700', 'Maguan', 'MG', 'M', '104.39514', '23.01293'),
('3263', '532626', '532600', '丘北县', '中国,云南省,文山壮族苗族自治州,丘北县', '丘北', '中国,云南,文山,丘北', '3', '0876', '663200', 'Qiubei', 'QB', 'Q', '104.19256', '24.03957'),
('3264', '532627', '532600', '广南县', '中国,云南省,文山壮族苗族自治州,广南县', '广南', '中国,云南,文山,广南', '3', '0876', '663300', 'Guangnan', 'GN', 'G', '105.05511', '24.0464'),
('3265', '532628', '532600', '富宁县', '中国,云南省,文山壮族苗族自治州,富宁县', '富宁', '中国,云南,文山,富宁', '3', '0876', '663400', 'Funing', 'FN', 'F', '105.63085', '23.62536'),
('3266', '532800', '530000', '西双版纳傣族自治州', '中国,云南省,西双版纳傣族自治州', '西双版纳', '中国,云南,西双版纳', '2', '0691', '666100', 'Xishuangbanna', 'XSBN', 'X', '100.797941', '22.001724'),
('3267', '532801', '532800', '景洪市', '中国,云南省,西双版纳傣族自治州,景洪市', '景洪', '中国,云南,西双版纳,景洪', '3', '0691', '666100', 'Jinghong', 'JH', 'J', '100.79977', '22.01071'),
('3268', '532822', '532800', '勐海县', '中国,云南省,西双版纳傣族自治州,勐海县', '勐海', '中国,云南,西双版纳,勐海', '3', '0691', '666200', 'Menghai', 'MH', 'M', '100.44931', '21.96175'),
('3269', '532823', '532800', '勐腊县', '中国,云南省,西双版纳傣族自治州,勐腊县', '勐腊', '中国,云南,西双版纳,勐腊', '3', '0691', '666300', 'Mengla', 'ML', 'M', '101.56488', '21.48162'),
('3270', '532900', '530000', '大理白族自治州', '中国,云南省,大理白族自治州', '大理', '中国,云南,大理', '2', '0872', '671000', 'Dali', 'DL', 'D', '100.240037', '25.592765'),
('3271', '532901', '532900', '大理市', '中国,云南省,大理白族自治州,大理市', '大理', '中国,云南,大理,大理', '3', '0872', '671000', 'Dali', 'DL', 'D', '100.22998', '25.59157'),
('3272', '532922', '532900', '漾濞彝族自治县', '中国,云南省,大理白族自治州,漾濞彝族自治县', '漾濞', '中国,云南,大理,漾濞', '3', '0872', '672500', 'Yangbi', 'YB', 'Y', '99.95474', '25.6652'),
('3273', '532923', '532900', '祥云县', '中国,云南省,大理白族自治州,祥云县', '祥云', '中国,云南,大理,祥云', '3', '0872', '672100', 'Xiangyun', 'XY', 'X', '100.55761', '25.47342'),
('3274', '532924', '532900', '宾川县', '中国,云南省,大理白族自治州,宾川县', '宾川', '中国,云南,大理,宾川', '3', '0872', '671600', 'Binchuan', 'BC', 'B', '100.57666', '25.83144'),
('3275', '532925', '532900', '弥渡县', '中国,云南省,大理白族自治州,弥渡县', '弥渡', '中国,云南,大理,弥渡', '3', '0872', '675600', 'Midu', 'MD', 'M', '100.49075', '25.34179'),
('3276', '532926', '532900', '南涧彝族自治县', '中国,云南省,大理白族自治州,南涧彝族自治县', '南涧', '中国,云南,大理,南涧', '3', '0872', '675700', 'Nanjian', 'NJ', 'N', '100.50964', '25.04349'),
('3277', '532927', '532900', '巍山彝族回族自治县', '中国,云南省,大理白族自治州,巍山彝族回族自治县', '巍山', '中国,云南,大理,巍山', '3', '0872', '672400', 'Weishan', 'WS', 'W', '100.30612', '25.23197'),
('3278', '532928', '532900', '永平县', '中国,云南省,大理白族自治州,永平县', '永平', '中国,云南,大理,永平', '3', '0872', '672600', 'Yongping', 'YP', 'Y', '99.54095', '25.46451'),
('3279', '532929', '532900', '云龙县', '中国,云南省,大理白族自治州,云龙县', '云龙', '中国,云南,大理,云龙', '3', '0872', '672700', 'Yunlong', 'YL', 'Y', '99.37255', '25.88505'),
('3280', '532930', '532900', '洱源县', '中国,云南省,大理白族自治州,洱源县', '洱源', '中国,云南,大理,洱源', '3', '0872', '671200', 'Eryuan', 'EY', 'E', '99.94903', '26.10829'),
('3281', '532931', '532900', '剑川县', '中国,云南省,大理白族自治州,剑川县', '剑川', '中国,云南,大理,剑川', '3', '0872', '671300', 'Jianchuan', 'JC', 'J', '99.90545', '26.53688'),
('3282', '532932', '532900', '鹤庆县', '中国,云南省,大理白族自治州,鹤庆县', '鹤庆', '中国,云南,大理,鹤庆', '3', '0872', '671500', 'Heqing', 'HQ', 'H', '100.17697', '26.55798'),
('3283', '533100', '530000', '德宏傣族景颇族自治州', '中国,云南省,德宏傣族景颇族自治州', '德宏', '中国,云南,德宏', '2', '0692', '678400', 'Dehong', 'DH', 'D', '98.578363', '24.436694'),
('3284', '533102', '533100', '瑞丽市', '中国,云南省,德宏傣族景颇族自治州,瑞丽市', '瑞丽', '中国,云南,德宏,瑞丽', '3', '0692', '678600', 'Ruili', 'RL', 'R', '97.85183', '24.01277'),
('3285', '533103', '533100', '芒市', '中国,云南省,德宏傣族景颇族自治州,芒市', '芒市', '中国,云南,德宏,芒市', '3', '0692', '678400', 'Mangshi', 'MS', 'M', '98.588641', '24.433735'),
('3286', '533122', '533100', '梁河县', '中国,云南省,德宏傣族景颇族自治州,梁河县', '梁河', '中国,云南,德宏,梁河', '3', '0692', '679200', 'Lianghe', 'LH', 'L', '98.29705', '24.80658'),
('3287', '533123', '533100', '盈江县', '中国,云南省,德宏傣族景颇族自治州,盈江县', '盈江', '中国,云南,德宏,盈江', '3', '0692', '679300', 'Yingjiang', 'YJ', 'Y', '97.93179', '24.70579'),
('3288', '533124', '533100', '陇川县', '中国,云南省,德宏傣族景颇族自治州,陇川县', '陇川', '中国,云南,德宏,陇川', '3', '0692', '678700', 'Longchuan', 'LC', 'L', '97.79199', '24.18302'),
('3289', '533300', '530000', '怒江傈僳族自治州', '中国,云南省,怒江傈僳族自治州', '怒江', '中国,云南,怒江', '2', '0886', '673100', 'Nujiang', 'NJ', 'N', '98.854304', '25.850949'),
('3290', '533301', '533300', '泸水市', '中国,云南省,怒江傈僳族自治州,泸水市', '泸水', '中国,云南,怒江,泸水', '3', '0886', '673200', 'Lushui', 'LS', 'L', '98.85534', '25.83772'),
('3291', '533323', '533300', '福贡县', '中国,云南省,怒江傈僳族自治州,福贡县', '福贡', '中国,云南,怒江,福贡', '3', '0886', '673400', 'Fugong', 'FG', 'F', '98.86969', '26.90366'),
('3292', '533324', '533300', '贡山独龙族怒族自治县', '中国,云南省,怒江傈僳族自治州,贡山独龙族怒族自治县', '贡山', '中国,云南,怒江,贡山', '3', '0886', '673500', 'Gongshan', 'GS', 'G', '98.66583', '27.74088'),
('3293', '533325', '533300', '兰坪白族普米族自治县', '中国,云南省,怒江傈僳族自治州,兰坪白族普米族自治县', '兰坪', '中国,云南,怒江,兰坪', '3', '0886', '671400', 'Lanping', 'LP', 'L', '99.41891', '26.45251'),
('3294', '533400', '530000', '迪庆藏族自治州', '中国,云南省,迪庆藏族自治州', '迪庆', '中国,云南,迪庆', '2', '0887', '674400', 'Deqen', 'DQ', 'D', '99.706463', '27.826853'),
('3295', '533401', '533400', '香格里拉市', '中国,云南省,迪庆藏族自治州,香格里拉市', '香格里拉', '中国,云南,迪庆,香格里拉', '3', '0887', '674400', 'Xianggelila', 'XGLL', 'X', '99.70601', '27.82308'),
('3296', '533422', '533400', '德钦县', '中国,云南省,迪庆藏族自治州,德钦县', '德钦', '中国,云南,迪庆,德钦', '3', '0887', '674500', 'Deqin', 'DQ', 'D', '98.91082', '28.4863'),
('3297', '533423', '533400', '维西傈僳族自治县', '中国,云南省,迪庆藏族自治州,维西傈僳族自治县', '维西', '中国,云南,迪庆,维西', '3', '0887', '674600', 'Weixi', 'WX', 'W', '99.28402', '27.1793'),
('3298', '540000', '100000', '西藏自治区', '中国,西藏自治区', '西藏', '中国,西藏', '1', '', '', 'Tibet', 'XZ', 'T', '91.132212', '29.660361'),
('3299', '540100', '540000', '拉萨市', '中国,西藏自治区,拉萨市', '拉萨', '中国,西藏,拉萨', '2', '0891', '850000', 'Lhasa', 'LS', 'L', '91.132212', '29.660361'),
('3300', '540102', '540100', '城关区', '中国,西藏自治区,拉萨市,城关区', '城关', '中国,西藏,拉萨,城关', '3', '0891', '850000', 'Chengguan', 'CG', 'C', '91.13859', '29.65312'),
('3301', '540103', '540100', '堆龙德庆区', '中国,西藏自治区,拉萨市,堆龙德庆区', '堆龙德庆', '中国,西藏,拉萨,堆龙德庆', '3', '0891', '851400', 'Duilongdeqing', 'DLDQ', 'D', '91.00033', '29.65002'),
('3302', '540121', '540100', '林周县', '中国,西藏自治区,拉萨市,林周县', '林周', '中国,西藏,拉萨,林周', '3', '0891', '851600', 'Linzhou', 'LZ', 'L', '91.2586', '29.89445'),
('3303', '540122', '540100', '当雄县', '中国,西藏自治区,拉萨市,当雄县', '当雄', '中国,西藏,拉萨,当雄', '3', '0891', '851500', 'Dangxiong', 'DX', 'D', '91.10076', '30.48309'),
('3304', '540123', '540100', '尼木县', '中国,西藏自治区,拉萨市,尼木县', '尼木', '中国,西藏,拉萨,尼木', '3', '0891', '851600', 'Nimu', 'NM', 'N', '90.16378', '29.43353'),
('3305', '540124', '540100', '曲水县', '中国,西藏自治区,拉萨市,曲水县', '曲水', '中国,西藏,拉萨,曲水', '3', '0891', '850600', 'Qushui', 'QS', 'Q', '90.73187', '29.35636'),
('3306', '540126', '540100', '达孜县', '中国,西藏自治区,拉萨市,达孜县', '达孜', '中国,西藏,拉萨,达孜', '3', '0891', '850100', 'Dazi', 'DZ', 'D', '91.35757', '29.6722'),
('3307', '540127', '540100', '墨竹工卡县', '中国,西藏自治区,拉萨市,墨竹工卡县', '墨竹工卡', '中国,西藏,拉萨,墨竹工卡', '3', '0891', '850200', 'Mozhugongka', 'MZGK', 'M', '91.72814', '29.83614'),
('3308', '540200', '540000', '日喀则市', '中国,西藏自治区,日喀则市', '日喀则', '中国,西藏,日喀则', '2', '0892', '857000', 'Rikaze', 'RKZ', 'R', '88.884874', '29.263792'),
('3309', '540202', '540200', '桑珠孜区', '中国,西藏自治区,日喀则市,桑珠孜区', '桑珠孜', '中国,西藏,日喀则,桑珠孜', '3', '0892', '857000', 'Sangzhuzi', 'SZZ', 'S', '88.880367', '29.269565'),
('3310', '540221', '540200', '南木林县', '中国,西藏自治区,日喀则市,南木林县', '南木林', '中国,西藏,日喀则,南木林', '3', '0892', '857100', 'Nanmulin', 'NML', 'N', '89.09686', '29.68206'),
('3311', '540222', '540200', '江孜县', '中国,西藏自治区,日喀则市,江孜县', '江孜', '中国,西藏,日喀则,江孜', '3', '0892', '857400', 'Jiangzi', 'JZ', 'J', '89.60263', '28.91744'),
('3312', '540223', '540200', '定日县', '中国,西藏自治区,日喀则市,定日县', '定日', '中国,西藏,日喀则,定日', '3', '0892', '858200', 'Dingri', 'DR', 'D', '87.12176', '28.66129'),
('3313', '540224', '540200', '萨迦县', '中国,西藏自治区,日喀则市,萨迦县', '萨迦', '中国,西藏,日喀则,萨迦', '3', '0892', '857800', 'Sajia', 'SJ', 'S', '88.02191', '28.90299'),
('3314', '540225', '540200', '拉孜县', '中国,西藏自治区,日喀则市,拉孜县', '拉孜', '中国,西藏,日喀则,拉孜', '3', '0892', '858100', 'Lazi', 'LZ', 'L', '87.63412', '29.085'),
('3315', '540226', '540200', '昂仁县', '中国,西藏自治区,日喀则市,昂仁县', '昂仁', '中国,西藏,日喀则,昂仁', '3', '0892', '858500', 'Angren', 'AR', 'A', '87.23858', '29.29496'),
('3316', '540227', '540200', '谢通门县', '中国,西藏自治区,日喀则市,谢通门县', '谢通门', '中国,西藏,日喀则,谢通门', '3', '0892', '858900', 'Xietongmen', 'XTM', 'X', '88.26242', '29.43337'),
('3317', '540228', '540200', '白朗县', '中国,西藏自治区,日喀则市,白朗县', '白朗', '中国,西藏,日喀则,白朗', '3', '0892', '857300', 'Bailang', 'BL', 'B', '89.26205', '29.10553'),
('3318', '540229', '540200', '仁布县', '中国,西藏自治区,日喀则市,仁布县', '仁布', '中国,西藏,日喀则,仁布', '3', '0892', '857200', 'Renbu', 'RB', 'R', '89.84228', '29.2301'),
('3319', '540230', '540200', '康马县', '中国,西藏自治区,日喀则市,康马县', '康马', '中国,西藏,日喀则,康马', '3', '0892', '857500', 'Kangma', 'KM', 'K', '89.68527', '28.5567'),
('3320', '540231', '540200', '定结县', '中国,西藏自治区,日喀则市,定结县', '定结', '中国,西藏,日喀则,定结', '3', '0892', '857900', 'Dingjie', 'DJ', 'D', '87.77255', '28.36403'),
('3321', '540232', '540200', '仲巴县', '中国,西藏自治区,日喀则市,仲巴县', '仲巴', '中国,西藏,日喀则,仲巴', '3', '0892', '858800', 'Zhongba', 'ZB', 'Z', '84.02951', '29.76595'),
('3322', '540233', '540200', '亚东县', '中国,西藏自治区,日喀则市,亚东县', '亚东', '中国,西藏,日喀则,亚东', '3', '0892', '857600', 'Yadong', 'YD', 'Y', '88.90802', '27.4839'),
('3323', '540234', '540200', '吉隆县', '中国,西藏自治区,日喀则市,吉隆县', '吉隆', '中国,西藏,日喀则,吉隆', '3', '0892', '858700', 'Jilong', 'JL', 'J', '85.29846', '28.85382'),
('3324', '540235', '540200', '聂拉木县', '中国,西藏自治区,日喀则市,聂拉木县', '聂拉木', '中国,西藏,日喀则,聂拉木', '3', '0892', '858300', 'Nielamu', 'NLM', 'N', '85.97998', '28.15645'),
('3325', '540236', '540200', '萨嘎县', '中国,西藏自治区,日喀则市,萨嘎县', '萨嘎', '中国,西藏,日喀则,萨嘎', '3', '0892', '857800', 'Saga', 'SG', 'S', '85.23413', '29.32936'),
('3326', '540237', '540200', '岗巴县', '中国,西藏自治区,日喀则市,岗巴县', '岗巴', '中国,西藏,日喀则,岗巴', '3', '0892', '857700', 'Gangba', 'GB', 'G', '88.52069', '28.27504'),
('3327', '540300', '540000', '昌都市', '中国,西藏自治区,昌都市', '昌都', '中国,西藏,昌都', '2', '0895', '854000', 'Qamdo', 'CD', 'Q', '97.178452', '31.136875'),
('3328', '540302', '540300', '卡若区', '中国,西藏自治区,昌都市,卡若区', '卡若', '中国,西藏,昌都,卡若', '3', '0895', '854000', 'Karuo', 'KR', 'K', '97.18043', '31.1385'),
('3329', '540321', '540300', '江达县', '中国,西藏自治区,昌都市,江达县', '江达', '中国,西藏,昌都,江达', '3', '0895', '854100', 'Jiangda', 'JD', 'J', '98.21865', '31.50343'),
('3330', '540322', '540300', '贡觉县', '中国,西藏自治区,昌都市,贡觉县', '贡觉', '中国,西藏,昌都,贡觉', '3', '0895', '854200', 'Gongjue', 'GJ', 'G', '98.27163', '30.85941'),
('3331', '540323', '540300', '类乌齐县', '中国,西藏自治区,昌都市,类乌齐县', '类乌齐', '中国,西藏,昌都,类乌齐', '3', '0895', '855600', 'Leiwuqi', 'LWQ', 'L', '96.60015', '31.21207'),
('3332', '540324', '540300', '丁青县', '中国,西藏自治区,昌都市,丁青县', '丁青', '中国,西藏,昌都,丁青', '3', '0895', '855700', 'Dingqing', 'DQ', 'D', '95.59362', '31.41621'),
('3333', '540325', '540300', '察雅县', '中国,西藏自治区,昌都市,察雅县', '察雅', '中国,西藏,昌都,察雅', '3', '0895', '854300', 'Chaya', 'CY', 'C', '97.56521', '30.65336'),
('3334', '540326', '540300', '八宿县', '中国,西藏自治区,昌都市,八宿县', '八宿', '中国,西藏,昌都,八宿', '3', '0895', '854600', 'Basu', 'BS', 'B', '96.9176', '30.05346'),
('3335', '540327', '540300', '左贡县', '中国,西藏自治区,昌都市,左贡县', '左贡', '中国,西藏,昌都,左贡', '3', '0895', '854400', 'Zuogong', 'ZG', 'Z', '97.84429', '29.67108'),
('3336', '540328', '540300', '芒康县', '中国,西藏自治区,昌都市,芒康县', '芒康', '中国,西藏,昌都,芒康', '3', '0895', '854500', 'Mangkang', 'MK', 'M', '98.59378', '29.67946'),
('3337', '540329', '540300', '洛隆县', '中国,西藏自治区,昌都市,洛隆县', '洛隆', '中国,西藏,昌都,洛隆', '3', '0895', '855400', 'Luolong', 'LL', 'L', '95.82644', '30.74049'),
('3338', '540330', '540300', '边坝县', '中国,西藏自治区,昌都市,边坝县', '边坝', '中国,西藏,昌都,边坝', '3', '0895', '855500', 'Bianba', 'BB', 'B', '94.70687', '30.93434'),
('3339', '540400', '540000', '林芝市', '中国,西藏自治区,林芝市', '林芝', '中国,西藏,林芝', '2', '0894', '860000', 'Nyingchi', 'LZ', 'N', '94.362348', '29.654693'),
('3340', '540402', '540400', '巴宜区', '中国,西藏自治区,林芝市,巴宜区', '巴宜', '中国,西藏,林芝,巴宜', '3', '0894', '860100', 'BaYi', 'BY', 'B', '94.48391', '29.57562'),
('3341', '540421', '540400', '工布江达县', '中国,西藏自治区,林芝市,工布江达县', '工布江达', '中国,西藏,林芝,工布江达', '3', '0894', '860200', 'Gongbujiangda', 'GBJD', 'G', '93.2452', '29.88576'),
('3342', '540422', '540400', '米林县', '中国,西藏自治区,林芝市,米林县', '米林', '中国,西藏,林芝,米林', '3', '0894', '860500', 'Milin', 'ML', 'M', '94.21316', '29.21535'),
('3343', '540423', '540400', '墨脱县', '中国,西藏自治区,林芝市,墨脱县', '墨脱', '中国,西藏,林芝,墨脱', '3', '0894', '860700', 'Motuo', 'MT', 'M', '95.3316', '29.32698'),
('3344', '540424', '540400', '波密县', '中国,西藏自治区,林芝市,波密县', '波密', '中国,西藏,林芝,波密', '3', '0894', '860300', 'Bomi', 'BM', 'B', '95.77096', '29.85907'),
('3345', '540425', '540400', '察隅县', '中国,西藏自治区,林芝市,察隅县', '察隅', '中国,西藏,林芝,察隅', '3', '0894', '860600', 'Chayu', 'CY', 'C', '97.46679', '28.6618'),
('3346', '540426', '540400', '朗县', '中国,西藏自治区,林芝市,朗县', '朗县', '中国,西藏,林芝,朗县', '3', '0894', '860400', 'Langxian', 'LX', 'L', '93.0754', '29.04549'),
('3347', '540500', '540000', '山南市', '中国,西藏自治区,山南市', '山南', '中国,西藏,山南', '2', '0893', '856000', 'Shannan', 'SN', 'S', '91.766529', '29.236023'),
('3348', '540502', '540500', '乃东区', '中国,西藏自治区,山南市,乃东区', '乃东', '中国,西藏,山南,乃东', '3', '0893', '856100', 'Naidong', 'ND', 'N', '91.76153', '29.2249'),
('3349', '540521', '540500', '扎囊县', '中国,西藏自治区,山南市,扎囊县', '扎囊', '中国,西藏,山南,扎囊', '3', '0893', '850800', 'Zhanang', 'ZN', 'Z', '91.33288', '29.2399'),
('3350', '540522', '540500', '贡嘎县', '中国,西藏自治区,山南市,贡嘎县', '贡嘎', '中国,西藏,山南,贡嘎', '3', '0893', '850700', 'Gongga', 'GG', 'G', '90.98867', '29.29408'),
('3351', '540523', '540500', '桑日县', '中国,西藏自治区,山南市,桑日县', '桑日', '中国,西藏,山南,桑日', '3', '0893', '856200', 'Sangri', 'SR', 'S', '92.02005', '29.26643'),
('3352', '540524', '540500', '琼结县', '中国,西藏自治区,山南市,琼结县', '琼结', '中国,西藏,山南,琼结', '3', '0893', '856800', 'Qiongjie', 'QJ', 'Q', '91.68093', '29.02632'),
('3353', '540525', '540500', '曲松县', '中国,西藏自治区,山南市,曲松县', '曲松', '中国,西藏,山南,曲松', '3', '0893', '856300', 'Qusong', 'QS', 'Q', '92.20263', '29.06412'),
('3354', '540526', '540500', '措美县', '中国,西藏自治区,山南市,措美县', '措美', '中国,西藏,山南,措美', '3', '0893', '856900', 'Cuomei', 'CM', 'C', '91.43237', '28.43794'),
('3355', '540527', '540500', '洛扎县', '中国,西藏自治区,山南市,洛扎县', '洛扎', '中国,西藏,山南,洛扎', '3', '0893', '851200', 'Luozha', 'LZ', 'L', '90.86035', '28.3872'),
('3356', '540528', '540500', '加查县', '中国,西藏自治区,山南市,加查县', '加查', '中国,西藏,山南,加查', '3', '0893', '856400', 'Jiacha', 'JC', 'J', '92.57702', '29.13973'),
('3357', '540529', '540500', '隆子县', '中国,西藏自治区,山南市,隆子县', '隆子', '中国,西藏,山南,隆子', '3', '0893', '856600', 'Longzi', 'LZ', 'L', '92.46148', '28.40797'),
('3358', '540530', '540500', '错那县', '中国,西藏自治区,山南市,错那县', '错那', '中国,西藏,山南,错那', '3', '0893', '856700', 'Cuona', 'CN', 'C', '91.95752', '27.99224'),
('3359', '540531', '540500', '浪卡子县', '中国,西藏自治区,山南市,浪卡子县', '浪卡子', '中国,西藏,山南,浪卡子', '3', '0893', '851100', 'Langkazi', 'LKZ', 'L', '90.40002', '28.96948'),
('3360', '542400', '540000', '那曲地区', '中国,西藏自治区,那曲地区', '那曲', '中国,西藏,那曲', '2', '0896', '852000', 'Nagqu', 'NQ', 'N', '92.060214', '31.476004'),
('3361', '542421', '542400', '那曲县', '中国,西藏自治区,那曲地区,那曲县', '那曲', '中国,西藏,那曲,那曲', '3', '0896', '852000', 'Naqu', 'NQ', 'N', '92.0535', '31.46964'),
('3362', '542422', '542400', '嘉黎县', '中国,西藏自治区,那曲地区,嘉黎县', '嘉黎', '中国,西藏,那曲,嘉黎', '3', '0896', '852400', 'Jiali', 'JL', 'J', '93.24987', '30.64233'),
('3363', '542423', '542400', '比如县', '中国,西藏自治区,那曲地区,比如县', '比如', '中国,西藏,那曲,比如', '3', '0896', '852300', 'Biru', 'BR', 'B', '93.68685', '31.4779'),
('3364', '542424', '542400', '聂荣县', '中国,西藏自治区,那曲地区,聂荣县', '聂荣', '中国,西藏,那曲,聂荣', '3', '0896', '853500', 'Nierong', 'NR', 'N', '92.29574', '32.11193'),
('3365', '542425', '542400', '安多县', '中国,西藏自治区,那曲地区,安多县', '安多', '中国,西藏,那曲,安多', '3', '0896', '853400', 'Anduo', 'AD', 'A', '91.6795', '32.26125'),
('3366', '542426', '542400', '申扎县', '中国,西藏自治区,那曲地区,申扎县', '申扎', '中国,西藏,那曲,申扎', '3', '0896', '853100', 'Shenzha', 'SZ', 'S', '88.70776', '30.92995'),
('3367', '542427', '542400', '索县', '中国,西藏自治区,那曲地区,索县', '索县', '中国,西藏,那曲,索县', '3', '0896', '852200', 'Suoxian', 'SX', 'S', '93.78295', '31.88427'),
('3368', '542428', '542400', '班戈县', '中国,西藏自治区,那曲地区,班戈县', '班戈', '中国,西藏,那曲,班戈', '3', '0896', '852500', 'Bange', 'BG', 'B', '90.01907', '31.36149'),
('3369', '542429', '542400', '巴青县', '中国,西藏自治区,那曲地区,巴青县', '巴青', '中国,西藏,那曲,巴青', '3', '0896', '852100', 'Baqing', 'BQ', 'B', '94.05316', '31.91833'),
('3370', '542430', '542400', '尼玛县', '中国,西藏自治区,那曲地区,尼玛县', '尼玛', '中国,西藏,那曲,尼玛', '3', '0896', '853200', 'Nima', 'NM', 'N', '87.25256', '31.79654'),
('3371', '542431', '542400', '双湖县', '中国,西藏自治区,那曲地区,双湖县', '双湖', '中国,西藏,那曲,双湖', '3', '0896', '852600', 'Shuanghu', 'SH', 'S', '88.837776', '33.189032'),
('3372', '542500', '540000', '阿里地区', '中国,西藏自治区,阿里地区', '阿里', '中国,西藏,阿里', '2', '0897', '859000', 'Ngari', 'AL', 'N', '80.105498', '32.503187'),
('3373', '542521', '542500', '普兰县', '中国,西藏自治区,阿里地区,普兰县', '普兰', '中国,西藏,阿里,普兰', '3', '0897', '859500', 'Pulan', 'PL', 'P', '81.177', '30.30002'),
('3374', '542522', '542500', '札达县', '中国,西藏自治区,阿里地区,札达县', '札达', '中国,西藏,阿里,札达', '3', '0897', '859600', 'Zhada', 'ZD', 'Z', '79.80255', '31.48345'),
('3375', '542523', '542500', '噶尔县', '中国,西藏自治区,阿里地区,噶尔县', '噶尔', '中国,西藏,阿里,噶尔', '3', '0897', '859000', 'Gaer', 'GE', 'G', '80.09579', '32.50024'),
('3376', '542524', '542500', '日土县', '中国,西藏自治区,阿里地区,日土县', '日土', '中国,西藏,阿里,日土', '3', '0897', '859700', 'Ritu', 'RT', 'R', '79.7131', '33.38741'),
('3377', '542525', '542500', '革吉县', '中国,西藏自治区,阿里地区,革吉县', '革吉', '中国,西藏,阿里,革吉', '3', '0897', '859100', 'Geji', 'GJ', 'G', '81.151', '32.3964'),
('3378', '542526', '542500', '改则县', '中国,西藏自治区,阿里地区,改则县', '改则', '中国,西藏,阿里,改则', '3', '0897', '859200', 'Gaize', 'GZ', 'G', '84.06295', '32.30446'),
('3379', '542527', '542500', '措勤县', '中国,西藏自治区,阿里地区,措勤县', '措勤', '中国,西藏,阿里,措勤', '3', '0897', '859300', 'Cuoqin', 'CQ', 'C', '85.16616', '31.02095'),
('3380', '610000', '100000', '陕西省', '中国,陕西省', '陕西', '中国,陕西', '1', '', '', 'Shaanxi', 'SN', 'S', '108.948024', '34.263161'),
('3381', '610100', '610000', '西安市', '中国,陕西省,西安市', '西安', '中国,陕西,西安', '2', '029', '710000', 'Xi\'an', 'XA', 'X', '108.948024', '34.263161'),
('3382', '610102', '610100', '新城区', '中国,陕西省,西安市,新城区', '新城', '中国,陕西,西安,新城', '3', '029', '710000', 'Xincheng', 'XC', 'X', '108.9608', '34.26641'),
('3383', '610103', '610100', '碑林区', '中国,陕西省,西安市,碑林区', '碑林', '中国,陕西,西安,碑林', '3', '029', '710000', 'Beilin', 'BL', 'B', '108.93426', '34.2304'),
('3384', '610104', '610100', '莲湖区', '中国,陕西省,西安市,莲湖区', '莲湖', '中国,陕西,西安,莲湖', '3', '029', '710000', 'Lianhu', 'LH', 'L', '108.9401', '34.26709'),
('3385', '610111', '610100', '灞桥区', '中国,陕西省,西安市,灞桥区', '灞桥', '中国,陕西,西安,灞桥', '3', '029', '710000', 'Baqiao', 'BQ', 'B', '109.06451', '34.27264'),
('3386', '610112', '610100', '未央区', '中国,陕西省,西安市,未央区', '未央', '中国,陕西,西安,未央', '3', '029', '710000', 'Weiyang', 'WY', 'W', '108.94683', '34.29296'),
('3387', '610113', '610100', '雁塔区', '中国,陕西省,西安市,雁塔区', '雁塔', '中国,陕西,西安,雁塔', '3', '029', '710000', 'Yanta', 'YT', 'Y', '108.94866', '34.22245'),
('3388', '610114', '610100', '阎良区', '中国,陕西省,西安市,阎良区', '阎良', '中国,陕西,西安,阎良', '3', '029', '710000', 'Yanliang', 'YL', 'Y', '109.22616', '34.66221'),
('3389', '610115', '610100', '临潼区', '中国,陕西省,西安市,临潼区', '临潼', '中国,陕西,西安,临潼', '3', '029', '710600', 'Lintong', 'LT', 'L', '109.21417', '34.36665'),
('3390', '610116', '610100', '长安区', '中国,陕西省,西安市,长安区', '长安', '中国,陕西,西安,长安', '3', '029', '710100', 'Chang\'an', 'CA', 'C', '108.94586', '34.15559'),
('3391', '610117', '610100', '高陵区', '中国,陕西省,西安市,高陵区', '高陵', '中国,陕西,西安,高陵', '3', '029', '710200', 'Gaoling', 'GL', 'G', '109.08816', '34.53483'),
('3392', '610118', '610100', '鄠邑区', '中国,陕西省,西安市,鄠邑区', '鄠邑', '中国,陕西,西安,鄠邑', '3', '029', '710300', 'Huyi', 'HY', 'H', '108.60513', '34.10856'),
('3393', '610122', '610100', '蓝田县', '中国,陕西省,西安市,蓝田县', '蓝田', '中国,陕西,西安,蓝田', '3', '029', '710500', 'Lantian', 'LT', 'L', '109.32339', '34.15128'),
('3394', '610124', '610100', '周至县', '中国,陕西省,西安市,周至县', '周至', '中国,陕西,西安,周至', '3', '029', '710400', 'Zhouzhi', 'ZZ', 'Z', '108.22207', '34.16337'),
('3395', '610127', '610100', '曲江新区', '中国,陕西省,西安市,曲江新区', '曲江新区', '中国,陕西,西安,曲江新区', '3', '029', '710061', 'QujiangXinQu', 'QJXQ', 'Q', '108.773575', '34.187666'),
('3396', '610128', '610100', '高新区', '中国,陕西省,西安市,高新区', '高新区', '中国,陕西,西安,高新区', '3', '029', '710000', 'Gaoxinqu', 'GXQ', 'G', '108.890974', '34.193388'),
('3397', '610200', '610000', '铜川市', '中国,陕西省,铜川市', '铜川', '中国,陕西,铜川', '2', '0919', '727000', 'Tongchuan', 'TC', 'T', '108.963122', '34.90892'),
('3398', '610202', '610200', '王益区', '中国,陕西省,铜川市,王益区', '王益', '中国,陕西,铜川,王益', '3', '0919', '727000', 'Wangyi', 'WY', 'W', '109.07564', '35.06896'),
('3399', '610203', '610200', '印台区', '中国,陕西省,铜川市,印台区', '印台', '中国,陕西,铜川,印台', '3', '0919', '727007', 'Yintai', 'YT', 'Y', '109.10208', '35.1169'),
('3400', '610204', '610200', '耀州区', '中国,陕西省,铜川市,耀州区', '耀州', '中国,陕西,铜川,耀州', '3', '0919', '727100', 'Yaozhou', 'YZ', 'Y', '108.98556', '34.91308'),
('3401', '610222', '610200', '宜君县', '中国,陕西省,铜川市,宜君县', '宜君', '中国,陕西,铜川,宜君', '3', '0919', '727200', 'Yijun', 'YJ', 'Y', '109.11813', '35.40108'),
('3402', '610300', '610000', '宝鸡市', '中国,陕西省,宝鸡市', '宝鸡', '中国,陕西,宝鸡', '2', '0917', '721000', 'Baoji', 'BJ', 'B', '107.14487', '34.369315'),
('3403', '610302', '610300', '渭滨区', '中国,陕西省,宝鸡市,渭滨区', '渭滨', '中国,陕西,宝鸡,渭滨', '3', '0917', '721000', 'Weibin', 'WB', 'W', '107.14991', '34.37116'),
('3404', '610303', '610300', '金台区', '中国,陕西省,宝鸡市,金台区', '金台', '中国,陕西,宝鸡,金台', '3', '0917', '721000', 'Jintai', 'JT', 'J', '107.14691', '34.37612'),
('3405', '610304', '610300', '陈仓区', '中国,陕西省,宝鸡市,陈仓区', '陈仓', '中国,陕西,宝鸡,陈仓', '3', '0917', '721300', 'Chencang', 'CC', 'C', '107.38742', '34.35451'),
('3406', '610322', '610300', '凤翔县', '中国,陕西省,宝鸡市,凤翔县', '凤翔', '中国,陕西,宝鸡,凤翔', '3', '0917', '721400', 'Fengxiang', 'FX', 'F', '107.39645', '34.52321'),
('3407', '610323', '610300', '岐山县', '中国,陕西省,宝鸡市,岐山县', '岐山', '中国,陕西,宝鸡,岐山', '3', '0917', '722400', 'Qishan', 'QS', 'Q', '107.62173', '34.44378'),
('3408', '610324', '610300', '扶风县', '中国,陕西省,宝鸡市,扶风县', '扶风', '中国,陕西,宝鸡,扶风', '3', '0917', '722200', 'Fufeng', 'FF', 'F', '107.90017', '34.37524'),
('3409', '610326', '610300', '眉县', '中国,陕西省,宝鸡市,眉县', '眉县', '中国,陕西,宝鸡,眉县', '3', '0917', '722300', 'Meixian', 'MX', 'M', '107.75079', '34.27569'),
('3410', '610327', '610300', '陇县', '中国,陕西省,宝鸡市,陇县', '陇县', '中国,陕西,宝鸡,陇县', '3', '0917', '721200', 'Longxian', 'LX', 'L', '106.85946', '34.89404'),
('3411', '610328', '610300', '千阳县', '中国,陕西省,宝鸡市,千阳县', '千阳', '中国,陕西,宝鸡,千阳', '3', '0917', '721100', 'Qianyang', 'QY', 'Q', '107.13043', '34.64219'),
('3412', '610329', '610300', '麟游县', '中国,陕西省,宝鸡市,麟游县', '麟游', '中国,陕西,宝鸡,麟游', '3', '0917', '721500', 'Linyou', 'LY', 'L', '107.79623', '34.67844'),
('3413', '610330', '610300', '凤县', '中国,陕西省,宝鸡市,凤县', '凤县', '中国,陕西,宝鸡,凤县', '3', '0917', '721700', 'Fengxian', 'FX', 'F', '106.52356', '33.91172'),
('3414', '610331', '610300', '太白县', '中国,陕西省,宝鸡市,太白县', '太白', '中国,陕西,宝鸡,太白', '3', '0917', '721600', 'Taibai', 'TB', 'T', '107.31646', '34.06207'),
('3415', '610332', '610300', '高新区', '中国,陕西省,宝鸡市,高新区', '高新区', '中国,陕西,宝鸡,高新区', '3', '0917', '721013', 'Gaoxinqu', 'GXQ', 'G', '107.231584', '34.348635'),
('3416', '610400', '610000', '咸阳市', '中国,陕西省,咸阳市', '咸阳', '中国,陕西,咸阳', '2', '029', '712000', 'Xianyang', 'XY', 'X', '108.705117', '34.333439'),
('3417', '610402', '610400', '秦都区', '中国,陕西省,咸阳市,秦都区', '秦都', '中国,陕西,咸阳,秦都', '3', '029', '712000', 'Qindu', 'QD', 'Q', '108.71493', '34.33804'),
('3418', '610403', '610400', '杨陵区', '中国,陕西省,咸阳市,杨陵区', '杨陵', '中国,陕西,咸阳,杨陵', '3', '029', '712100', 'Yangling', 'YL', 'Y', '108.083481', '34.270434'),
('3419', '610404', '610400', '渭城区', '中国,陕西省,咸阳市,渭城区', '渭城', '中国,陕西,咸阳,渭城', '3', '029', '712000', 'Weicheng', 'WC', 'W', '108.72227', '34.33198'),
('3420', '610422', '610400', '三原县', '中国,陕西省,咸阳市,三原县', '三原', '中国,陕西,咸阳,三原', '3', '029', '713800', 'Sanyuan', 'SY', 'S', '108.93194', '34.61556'),
('3421', '610423', '610400', '泾阳县', '中国,陕西省,咸阳市,泾阳县', '泾阳', '中国,陕西,咸阳,泾阳', '3', '029', '713700', 'Jingyang', 'JY', 'J', '108.84259', '34.52705'),
('3422', '610424', '610400', '乾县', '中国,陕西省,咸阳市,乾县', '乾县', '中国,陕西,咸阳,乾县', '3', '029', '713300', 'Qianxian', 'QX', 'Q', '108.24231', '34.52946'),
('3423', '610425', '610400', '礼泉县', '中国,陕西省,咸阳市,礼泉县', '礼泉', '中国,陕西,咸阳,礼泉', '3', '029', '713200', 'Liquan', 'LQ', 'L', '108.4263', '34.48455'),
('3424', '610426', '610400', '永寿县', '中国,陕西省,咸阳市,永寿县', '永寿', '中国,陕西,咸阳,永寿', '3', '029', '713400', 'Yongshou', 'YS', 'Y', '108.14474', '34.69081'),
('3425', '610427', '610400', '彬县', '中国,陕西省,咸阳市,彬县', '彬县', '中国,陕西,咸阳,彬县', '3', '029', '713500', 'Binxian', 'BX', 'B', '108.08468', '35.0342'),
('3426', '610428', '610400', '长武县', '中国,陕西省,咸阳市,长武县', '长武', '中国,陕西,咸阳,长武', '3', '029', '713600', 'Changwu', 'CW', 'C', '107.7951', '35.2067'),
('3427', '610429', '610400', '旬邑县', '中国,陕西省,咸阳市,旬邑县', '旬邑', '中国,陕西,咸阳,旬邑', '3', '029', '711300', 'Xunyi', 'XY', 'X', '108.3341', '35.11338'),
('3428', '610430', '610400', '淳化县', '中国,陕西省,咸阳市,淳化县', '淳化', '中国,陕西,咸阳,淳化', '3', '029', '711200', 'Chunhua', 'CH', 'C', '108.58026', '34.79886'),
('3429', '610431', '610400', '武功县', '中国,陕西省,咸阳市,武功县', '武功', '中国,陕西,咸阳,武功', '3', '029', '712200', 'Wugong', 'WG', 'W', '108.20434', '34.26003'),
('3430', '610481', '610400', '兴平市', '中国,陕西省,咸阳市,兴平市', '兴平', '中国,陕西,咸阳,兴平', '3', '029', '713100', 'Xingping', 'XP', 'X', '108.49057', '34.29785'),
('3431', '610482', '610400', '高新区', '中国,陕西省,咸阳市,高新区', '高新区', '中国,陕西,咸阳,高新区', '3', '029', '712000', 'Gaoxinqu', 'GXQ', 'G', '108.664746', '34.319917'),
('3432', '610500', '610000', '渭南市', '中国,陕西省,渭南市', '渭南', '中国,陕西,渭南', '2', '0913', '714000', 'Weinan', 'WN', 'W', '109.502882', '34.499381'),
('3433', '610502', '610500', '临渭区', '中国,陕西省,渭南市,临渭区', '临渭', '中国,陕西,渭南,临渭', '3', '0913', '714000', 'Linwei', 'LW', 'L', '109.49296', '34.49822'),
('3434', '610503', '610500', '华州区', '中国,陕西省,渭南市,华州区', '华州', '中国,陕西,渭南,华州', '3', '0913', '714100', 'Huazhou', 'HZ', 'H', '109.77185', '34.51255'),
('3435', '610522', '610500', '潼关县', '中国,陕西省,渭南市,潼关县', '潼关', '中国,陕西,渭南,潼关', '3', '0913', '714300', 'Tongguan', 'TG', 'T', '110.24362', '34.54284'),
('3436', '610523', '610500', '大荔县', '中国,陕西省,渭南市,大荔县', '大荔', '中国,陕西,渭南,大荔', '3', '0913', '715100', 'Dali', 'DL', 'D', '109.94216', '34.79565'),
('3437', '610524', '610500', '合阳县', '中国,陕西省,渭南市,合阳县', '合阳', '中国,陕西,渭南,合阳', '3', '0913', '715300', 'Heyang', 'HY', 'H', '110.14862', '35.23805'),
('3438', '610525', '610500', '澄城县', '中国,陕西省,渭南市,澄城县', '澄城', '中国,陕西,渭南,澄城', '3', '0913', '715200', 'Chengcheng', 'CC', 'C', '109.93444', '35.18396'),
('3439', '610526', '610500', '蒲城县', '中国,陕西省,渭南市,蒲城县', '蒲城', '中国,陕西,渭南,蒲城', '3', '0913', '715500', 'Pucheng', 'PC', 'P', '109.5903', '34.9568'),
('3440', '610527', '610500', '白水县', '中国,陕西省,渭南市,白水县', '白水', '中国,陕西,渭南,白水', '3', '0913', '715600', 'Baishui', 'BS', 'B', '109.59286', '35.17863'),
('3441', '610528', '610500', '富平县', '中国,陕西省,渭南市,富平县', '富平', '中国,陕西,渭南,富平', '3', '0913', '711700', 'Fuping', 'FP', 'F', '109.1802', '34.75109'),
('3442', '610581', '610500', '韩城市', '中国,陕西省,渭南市,韩城市', '韩城', '中国,陕西,渭南,韩城', '3', '0913', '715400', 'Hancheng', 'HC', 'H', '110.44238', '35.47926'),
('3443', '610582', '610500', '华阴市', '中国,陕西省,渭南市,华阴市', '华阴', '中国,陕西,渭南,华阴', '3', '0913', '714200', 'Huayin', 'HY', 'H', '110.08752', '34.56608'),
('3444', '610600', '610000', '延安市', '中国,陕西省,延安市', '延安', '中国,陕西,延安', '2', '0911', '716000', 'Yan\'an', 'YA', 'Y', '109.49081', '36.596537'),
('3445', '610602', '610600', '宝塔区', '中国,陕西省,延安市,宝塔区', '宝塔', '中国,陕西,延安,宝塔', '3', '0911', '716000', 'Baota', 'BT', 'B', '109.49336', '36.59154'),
('3446', '610603', '610600', '安塞区', '中国,陕西省,延安市,安塞区', '安塞', '中国,陕西,延安,安塞', '3', '0911', '717400', 'Ansai', 'AS', 'A', '109.32708', '36.86507'),
('3447', '610621', '610600', '延长县', '中国,陕西省,延安市,延长县', '延长', '中国,陕西,延安,延长', '3', '0911', '717100', 'Yanchang', 'YC', 'Y', '110.01083', '36.57904'),
('3448', '610622', '610600', '延川县', '中国,陕西省,延安市,延川县', '延川', '中国,陕西,延安,延川', '3', '0911', '717200', 'Yanchuan', 'YC', 'Y', '110.19415', '36.87817'),
('3449', '610623', '610600', '子长县', '中国,陕西省,延安市,子长县', '子长', '中国,陕西,延安,子长', '3', '0911', '717300', 'Zichang', 'ZC', 'Z', '109.67532', '37.14253'),
('3450', '610625', '610600', '志丹县', '中国,陕西省,延安市,志丹县', '志丹', '中国,陕西,延安,志丹', '3', '0911', '717500', 'Zhidan', 'ZD', 'Z', '108.76815', '36.82177'),
('3451', '610626', '610600', '吴起县', '中国,陕西省,延安市,吴起县', '吴起', '中国,陕西,延安,吴起', '3', '0911', '717600', 'Wuqi', 'WQ', 'W', '108.17611', '36.92785'),
('3452', '610627', '610600', '甘泉县', '中国,陕西省,延安市,甘泉县', '甘泉', '中国,陕西,延安,甘泉', '3', '0911', '716100', 'Ganquan', 'GQ', 'G', '109.35012', '36.27754'),
('3453', '610628', '610600', '富县', '中国,陕西省,延安市,富县', '富县', '中国,陕西,延安,富县', '3', '0911', '727500', 'Fuxian', 'FX', 'F', '109.37927', '35.98803'),
('3454', '610629', '610600', '洛川县', '中国,陕西省,延安市,洛川县', '洛川', '中国,陕西,延安,洛川', '3', '0911', '727400', 'Luochuan', 'LC', 'L', '109.43286', '35.76076'),
('3455', '610630', '610600', '宜川县', '中国,陕西省,延安市,宜川县', '宜川', '中国,陕西,延安,宜川', '3', '0911', '716200', 'Yichuan', 'YC', 'Y', '110.17196', '36.04732'),
('3456', '610631', '610600', '黄龙县', '中国,陕西省,延安市,黄龙县', '黄龙', '中国,陕西,延安,黄龙', '3', '0911', '715700', 'Huanglong', 'HL', 'H', '109.84259', '35.58349'),
('3457', '610632', '610600', '黄陵县', '中国,陕西省,延安市,黄陵县', '黄陵', '中国,陕西,延安,黄陵', '3', '0911', '727300', 'Huangling', 'HL', 'H', '109.26333', '35.58357'),
('3458', '610700', '610000', '汉中市', '中国,陕西省,汉中市', '汉中', '中国,陕西,汉中', '2', '0916', '723000', 'Hanzhong', 'HZ', 'H', '107.028621', '33.077668'),
('3459', '610702', '610700', '汉台区', '中国,陕西省,汉中市,汉台区', '汉台', '中国,陕西,汉中,汉台', '3', '0916', '723000', 'Hantai', 'HT', 'H', '107.03187', '33.06774'),
('3460', '610721', '610700', '南郑县', '中国,陕西省,汉中市,南郑县', '南郑', '中国,陕西,汉中,南郑', '3', '0916', '723100', 'Nanzheng', 'NZ', 'N', '106.94024', '33.00299'),
('3461', '610722', '610700', '城固县', '中国,陕西省,汉中市,城固县', '城固', '中国,陕西,汉中,城固', '3', '0916', '723200', 'Chenggu', 'CG', 'C', '107.33367', '33.15661'),
('3462', '610723', '610700', '洋县', '中国,陕西省,汉中市,洋县', '洋县', '中国,陕西,汉中,洋县', '3', '0916', '723300', 'Yangxian', 'YX', 'Y', '107.54672', '33.22102'),
('3463', '610724', '610700', '西乡县', '中国,陕西省,汉中市,西乡县', '西乡', '中国,陕西,汉中,西乡', '3', '0916', '723500', 'Xixiang', 'XX', 'X', '107.76867', '32.98411'),
('3464', '610725', '610700', '勉县', '中国,陕西省,汉中市,勉县', '勉县', '中国,陕西,汉中,勉县', '3', '0916', '724200', 'Mianxian', 'MX', 'M', '106.67584', '33.15273'),
('3465', '610726', '610700', '宁强县', '中国,陕西省,汉中市,宁强县', '宁强', '中国,陕西,汉中,宁强', '3', '0916', '724400', 'Ningqiang', 'NQ', 'N', '106.25958', '32.82881'),
('3466', '610727', '610700', '略阳县', '中国,陕西省,汉中市,略阳县', '略阳', '中国,陕西,汉中,略阳', '3', '0916', '724300', 'Lueyang', 'LY', 'L', '106.15399', '33.33009'),
('3467', '610728', '610700', '镇巴县', '中国,陕西省,汉中市,镇巴县', '镇巴', '中国,陕西,汉中,镇巴', '3', '0916', '723600', 'Zhenba', 'ZB', 'Z', '107.89648', '32.53487'),
('3468', '610729', '610700', '留坝县', '中国,陕西省,汉中市,留坝县', '留坝', '中国,陕西,汉中,留坝', '3', '0916', '724100', 'Liuba', 'LB', 'L', '106.92233', '33.61606'),
('3469', '610730', '610700', '佛坪县', '中国,陕西省,汉中市,佛坪县', '佛坪', '中国,陕西,汉中,佛坪', '3', '0916', '723400', 'Foping', 'FP', 'F', '107.98974', '33.52496'),
('3470', '610800', '610000', '榆林市', '中国,陕西省,榆林市', '榆林', '中国,陕西,榆林', '2', '0912', '719000', 'Yulin', 'YL', 'Y', '109.741193', '38.290162'),
('3471', '610802', '610800', '榆阳区', '中国,陕西省,榆林市,榆阳区', '榆阳', '中国,陕西,榆林,榆阳', '3', '0912', '719000', 'Yuyang', 'YY', 'Y', '109.73473', '38.27843'),
('3472', '610803', '610800', '横山区', '中国,陕西省,榆林市,横山区', '横山', '中国,陕西,榆林,横山', '3', '0912', '719200', 'Hengshan', 'HS', 'H', '109.29568', '37.958'),
('3473', '610821', '610800', '神木市', '中国,陕西省,榆林市,神木市', '神木', '中国,陕西,榆林,神木', '3', '0912', '719300', 'Shenmu', 'SM', 'S', '110.4989', '38.84234'),
('3474', '610822', '610800', '府谷县', '中国,陕西省,榆林市,府谷县', '府谷', '中国,陕西,榆林,府谷', '3', '0912', '719400', 'Fugu', 'FG', 'F', '111.06723', '39.02805'),
('3475', '610824', '610800', '靖边县', '中国,陕西省,榆林市,靖边县', '靖边', '中国,陕西,榆林,靖边', '3', '0912', '718500', 'Jingbian', 'JB', 'J', '108.79412', '37.59938'),
('3476', '610825', '610800', '定边县', '中国,陕西省,榆林市,定边县', '定边', '中国,陕西,榆林,定边', '3', '0912', '718600', 'Dingbian', 'DB', 'D', '107.59793', '37.59037'),
('3477', '610826', '610800', '绥德县', '中国,陕西省,榆林市,绥德县', '绥德', '中国,陕西,榆林,绥德', '3', '0912', '718000', 'Suide', 'SD', 'S', '110.26126', '37.49778'),
('3478', '610827', '610800', '米脂县', '中国,陕西省,榆林市,米脂县', '米脂', '中国,陕西,榆林,米脂', '3', '0912', '718100', 'Mizhi', 'MZ', 'M', '110.18417', '37.75529'),
('3479', '610828', '610800', '佳县', '中国,陕西省,榆林市,佳县', '佳县', '中国,陕西,榆林,佳县', '3', '0912', '719200', 'Jiaxian', 'JX', 'J', '110.49362', '38.02248'),
('3480', '610829', '610800', '吴堡县', '中国,陕西省,榆林市,吴堡县', '吴堡', '中国,陕西,榆林,吴堡', '3', '0912', '718200', 'Wubu', 'WB', 'W', '110.74533', '37.45709'),
('3481', '610830', '610800', '清涧县', '中国,陕西省,榆林市,清涧县', '清涧', '中国,陕西,榆林,清涧', '3', '0912', '718300', 'Qingjian', 'QJ', 'Q', '110.12173', '37.08854'),
('3482', '610831', '610800', '子洲县', '中国,陕西省,榆林市,子洲县', '子洲', '中国,陕西,榆林,子洲', '3', '0912', '718400', 'Zizhou', 'ZZ', 'Z', '110.03488', '37.61238'),
('3483', '610900', '610000', '安康市', '中国,陕西省,安康市', '安康', '中国,陕西,安康', '2', '0915', '725000', 'Ankang', 'AK', 'A', '109.029273', '32.6903'),
('3484', '610902', '610900', '汉滨区', '中国,陕西省,安康市,汉滨区', '汉滨', '中国,陕西,安康,汉滨', '3', '0915', '725000', 'Hanbin', 'HB', 'H', '109.02683', '32.69517'),
('3485', '610921', '610900', '汉阴县', '中国,陕西省,安康市,汉阴县', '汉阴', '中国,陕西,安康,汉阴', '3', '0915', '725100', 'Hanyin', 'HY', 'H', '108.51098', '32.89129'),
('3486', '610922', '610900', '石泉县', '中国,陕西省,安康市,石泉县', '石泉', '中国,陕西,安康,石泉', '3', '0915', '725200', 'Shiquan', 'SQ', 'S', '108.24755', '33.03971'),
('3487', '610923', '610900', '宁陕县', '中国,陕西省,安康市,宁陕县', '宁陕', '中国,陕西,安康,宁陕', '3', '0915', '711600', 'Ningshan', 'NS', 'N', '108.31515', '33.31726'),
('3488', '610924', '610900', '紫阳县', '中国,陕西省,安康市,紫阳县', '紫阳', '中国,陕西,安康,紫阳', '3', '0915', '725300', 'Ziyang', 'ZY', 'Z', '108.5368', '32.52115'),
('3489', '610925', '610900', '岚皋县', '中国,陕西省,安康市,岚皋县', '岚皋', '中国,陕西,安康,岚皋', '3', '0915', '725400', 'Langao', 'LG', 'L', '108.90289', '32.30794'),
('3490', '610926', '610900', '平利县', '中国,陕西省,安康市,平利县', '平利', '中国,陕西,安康,平利', '3', '0915', '725500', 'Pingli', 'PL', 'P', '109.35775', '32.39111'),
('3491', '610927', '610900', '镇坪县', '中国,陕西省,安康市,镇坪县', '镇坪', '中国,陕西,安康,镇坪', '3', '0915', '725600', 'Zhenping', 'ZP', 'Z', '109.52456', '31.8833'),
('3492', '610928', '610900', '旬阳县', '中国,陕西省,安康市,旬阳县', '旬阳', '中国,陕西,安康,旬阳', '3', '0915', '725700', 'Xunyang', 'XY', 'X', '109.3619', '32.83207'),
('3493', '610929', '610900', '白河县', '中国,陕西省,安康市,白河县', '白河', '中国,陕西,安康,白河', '3', '0915', '725800', 'Baihe', 'BH', 'B', '110.11315', '32.80955'),
('3494', '611000', '610000', '商洛市', '中国,陕西省,商洛市', '商洛', '中国,陕西,商洛', '2', '0914', '726000', 'Shangluo', 'SL', 'S', '109.939776', '33.868319'),
('3495', '611002', '611000', '商州区', '中国,陕西省,商洛市,商州区', '商州', '中国,陕西,商洛,商州', '3', '0914', '726000', 'Shangzhou', 'SZ', 'S', '109.94126', '33.8627'),
('3496', '611021', '611000', '洛南县', '中国,陕西省,商洛市,洛南县', '洛南', '中国,陕西,商洛,洛南', '3', '0914', '726100', 'Luonan', 'LN', 'L', '110.14645', '34.08994'),
('3497', '611022', '611000', '丹凤县', '中国,陕西省,商洛市,丹凤县', '丹凤', '中国,陕西,商洛,丹凤', '3', '0914', '726200', 'Danfeng', 'DF', 'D', '110.33486', '33.69468'),
('3498', '611023', '611000', '商南县', '中国,陕西省,商洛市,商南县', '商南', '中国,陕西,商洛,商南', '3', '0914', '726300', 'Shangnan', 'SN', 'S', '110.88375', '33.52581'),
('3499', '611024', '611000', '山阳县', '中国,陕西省,商洛市,山阳县', '山阳', '中国,陕西,商洛,山阳', '3', '0914', '726400', 'Shanyang', 'SY', 'S', '109.88784', '33.52931'),
('3500', '611025', '611000', '镇安县', '中国,陕西省,商洛市,镇安县', '镇安', '中国,陕西,商洛,镇安', '3', '0914', '711500', 'Zhen\'an', 'ZA', 'Z', '109.15374', '33.42366'),
('3501', '611026', '611000', '柞水县', '中国,陕西省,商洛市,柞水县', '柞水', '中国,陕西,商洛,柞水', '3', '0914', '711400', 'Zhashui', 'ZS', 'Z', '109.11105', '33.6831'),
('3502', '611100', '610000', '西咸新区', '中国,陕西省,西咸新区', '西咸新区', '中国,陕西,西咸新区', '2', '029', '712000', 'XixianXinQu', 'XXXQ', 'X', '108.810654', '34.307144'),
('3503', '611101', '611100', '空港新城', '中国,陕西省,西咸新区,空港新城', '空港', '中国,陕西,西咸新区,空港', '3', '029', '461000', 'Konggang', 'KG', 'K', '108.760529', '34.440894'),
('3504', '611102', '611100', '沣东新城', '中国,陕西省,西咸新区,沣东新城', '沣东', '中国,陕西,西咸新区,沣东', '3', '029', '710000', 'Fengdong', 'FD', 'F', '108.82988', '34.267431'),
('3505', '611103', '611100', '秦汉新城', '中国,陕西省,西咸新区,秦汉新城', '秦汉', '中国,陕西,西咸新区,秦汉', '3', '029', '712000', 'Qinhan', 'QH', 'Q', '108.83812', '34.386513'),
('3506', '611104', '611100', '沣西新城', '中国,陕西省,西咸新区,沣西新城', '沣西', '中国,陕西,西咸新区,沣西', '3', '029', '710000', 'Fengxi', 'FX', 'F', '108.71215', '34.190453'),
('3507', '611105', '611100', '泾河新城', '中国,陕西省,西咸新区,泾河新城', '泾河', '中国,陕西,西咸新区,泾河', '3', '029', '713700', 'Jinghe', 'JH', 'J', '109.049603', '34.460587'),
('3508', '620000', '100000', '甘肃省', '中国,甘肃省', '甘肃', '中国,甘肃', '1', '', '', 'Gansu', 'GS', 'G', '103.823557', '36.058039'),
('3509', '620100', '620000', '兰州市', '中国,甘肃省,兰州市', '兰州', '中国,甘肃,兰州', '2', '0931', '730000', 'Lanzhou', 'LZ', 'L', '103.823557', '36.058039'),
('3510', '620102', '620100', '城关区', '中国,甘肃省,兰州市,城关区', '城关', '中国,甘肃,兰州,城关', '3', '0931', '730030', 'Chengguan', 'CG', 'C', '103.8252', '36.05725'),
('3511', '620103', '620100', '七里河区', '中国,甘肃省,兰州市,七里河区', '七里河', '中国,甘肃,兰州,七里河', '3', '0931', '730050', 'Qilihe', 'QLH', 'Q', '103.78564', '36.06585'),
('3512', '620104', '620100', '西固区', '中国,甘肃省,兰州市,西固区', '西固', '中国,甘肃,兰州,西固', '3', '0931', '730060', 'Xigu', 'XG', 'X', '103.62811', '36.08858'),
('3513', '620105', '620100', '安宁区', '中国,甘肃省,兰州市,安宁区', '安宁', '中国,甘肃,兰州,安宁', '3', '0931', '730070', 'Anning', 'AN', 'A', '103.7189', '36.10384'),
('3514', '620111', '620100', '红古区', '中国,甘肃省,兰州市,红古区', '红古', '中国,甘肃,兰州,红古', '3', '0931', '730080', 'Honggu', 'HG', 'H', '102.85955', '36.34537'),
('3515', '620121', '620100', '永登县', '中国,甘肃省,兰州市,永登县', '永登', '中国,甘肃,兰州,永登', '3', '0931', '730300', 'Yongdeng', 'YD', 'Y', '103.26055', '36.73522'),
('3516', '620122', '620100', '皋兰县', '中国,甘肃省,兰州市,皋兰县', '皋兰', '中国,甘肃,兰州,皋兰', '3', '0931', '730200', 'Gaolan', 'GL', 'G', '103.94506', '36.33215'),
('3517', '620123', '620100', '榆中县', '中国,甘肃省,兰州市,榆中县', '榆中', '中国,甘肃,兰州,榆中', '3', '0931', '730100', 'Yuzhong', 'YZ', 'Y', '104.1145', '35.84415'),
('3518', '620124', '620100', '兰州新区', '中国,甘肃省,兰州市,兰州新区', '兰州新区', '中国,甘肃,兰州,兰州新区', '3', '0931', '730000', 'LanzhouXinQu', 'LZXQ', 'L', '103.628725', '36.492791'),
('3519', '620125', '620100', '高新区', '中国,甘肃省,兰州市,高新区', '高新区', '中国,甘肃,兰州,高新区', '3', '0931', '730000', 'Gaoxinqu', 'GXQ', 'G', '103.893943', '36.053037'),
('3520', '620126', '620100', '经济开发区', '中国,甘肃省,兰州市,经济开发区', '经济开发区', '中国,甘肃,兰州,经济开发区', '3', '0931', '730000', 'Jingjikaifaqu', 'JJKFQ', 'J', '103.71609', '36.095017'),
('3521', '620200', '620000', '嘉峪关市', '中国,甘肃省,嘉峪关市', '嘉峪关', '中国,甘肃,嘉峪关', '2', '0937', '735100', 'Jiayuguan', 'JYG', 'J', '98.277304', '39.786529'),
('3522', '620201', '620200', '雄关区', '中国,甘肃省,嘉峪关市,雄关区', '雄关', '中国,甘肃,嘉峪关,雄关', '3', '0937', '735100', 'Xiongguan', 'XG', 'X', '98.277398', '39.77925'),
('3523', '620202', '620200', '长城区', '中国,甘肃省,嘉峪关市,长城区', '长城', '中国,甘肃,嘉峪关,长城', '3', '0937', '735106', 'Changcheng', 'CC', 'C', '98.273523', '39.787431'),
('3524', '620203', '620200', '镜铁区', '中国,甘肃省,嘉峪关市,镜铁区', '镜铁', '中国,甘肃,嘉峪关,镜铁', '3', '0937', '735100', 'Jingtie', 'JT', 'J', '98.277304', '39.786529'),
('3525', '620300', '620000', '金昌市', '中国,甘肃省,金昌市', '金昌', '中国,甘肃,金昌', '2', '0935', '737100', 'Jinchang', 'JC', 'J', '102.187888', '38.514238'),
('3526', '620302', '620300', '金川区', '中国,甘肃省,金昌市,金川区', '金川', '中国,甘肃,金昌,金川', '3', '0935', '737100', 'Jinchuan', 'JC', 'J', '102.19376', '38.52101'),
('3527', '620321', '620300', '永昌县', '中国,甘肃省,金昌市,永昌县', '永昌', '中国,甘肃,金昌,永昌', '3', '0935', '737200', 'Yongchang', 'YC', 'Y', '101.97222', '38.24711'),
('3528', '620400', '620000', '白银市', '中国,甘肃省,白银市', '白银', '中国,甘肃,白银', '2', '0943', '730900', 'Baiyin', 'BY', 'B', '104.173606', '36.54568'),
('3529', '620402', '620400', '白银区', '中国,甘肃省,白银市,白银区', '白银', '中国,甘肃,白银,白银', '3', '0943', '730900', 'Baiyin', 'BY', 'B', '104.17355', '36.54411'),
('3530', '620403', '620400', '平川区', '中国,甘肃省,白银市,平川区', '平川', '中国,甘肃,白银,平川', '3', '0943', '730913', 'Pingchuan', 'PC', 'P', '104.82498', '36.7277'),
('3531', '620421', '620400', '靖远县', '中国,甘肃省,白银市,靖远县', '靖远', '中国,甘肃,白银,靖远', '3', '0943', '730600', 'Jingyuan', 'JY', 'J', '104.68325', '36.56602'),
('3532', '620422', '620400', '会宁县', '中国,甘肃省,白银市,会宁县', '会宁', '中国,甘肃,白银,会宁', '3', '0943', '730700', 'Huining', 'HN', 'H', '105.05297', '35.69626'),
('3533', '620423', '620400', '景泰县', '中国,甘肃省,白银市,景泰县', '景泰', '中国,甘肃,白银,景泰', '3', '0943', '730400', 'Jingtai', 'JT', 'J', '104.06295', '37.18359'),
('3534', '620500', '620000', '天水市', '中国,甘肃省,天水市', '天水', '中国,甘肃,天水', '2', '0938', '741000', 'Tianshui', 'TS', 'T', '105.724998', '34.578529'),
('3535', '620502', '620500', '秦州区', '中国,甘肃省,天水市,秦州区', '秦州', '中国,甘肃,天水,秦州', '3', '0938', '741000', 'Qinzhou', 'QZ', 'Q', '105.72421', '34.58089'),
('3536', '620503', '620500', '麦积区', '中国,甘肃省,天水市,麦积区', '麦积', '中国,甘肃,天水,麦积', '3', '0938', '741020', 'Maiji', 'MJ', 'M', '105.89013', '34.57069'),
('3537', '620521', '620500', '清水县', '中国,甘肃省,天水市,清水县', '清水', '中国,甘肃,天水,清水', '3', '0938', '741400', 'Qingshui', 'QS', 'Q', '106.13671', '34.75032'),
('3538', '620522', '620500', '秦安县', '中国,甘肃省,天水市,秦安县', '秦安', '中国,甘肃,天水,秦安', '3', '0938', '741600', 'Qin\'an', 'QA', 'Q', '105.66955', '34.85894'),
('3539', '620523', '620500', '甘谷县', '中国,甘肃省,天水市,甘谷县', '甘谷', '中国,甘肃,天水,甘谷', '3', '0938', '741200', 'Gangu', 'GG', 'G', '105.33291', '34.73665'),
('3540', '620524', '620500', '武山县', '中国,甘肃省,天水市,武山县', '武山', '中国,甘肃,天水,武山', '3', '0938', '741300', 'Wushan', 'WS', 'W', '104.88382', '34.72123'),
('3541', '620525', '620500', '张家川回族自治县', '中国,甘肃省,天水市,张家川回族自治县', '张家川', '中国,甘肃,天水,张家川', '3', '0938', '741500', 'Zhangjiachuan', 'ZJC', 'Z', '106.21582', '34.99582'),
('3542', '620600', '620000', '武威市', '中国,甘肃省,武威市', '武威', '中国,甘肃,武威', '2', '0935', '733000', 'Wuwei', 'WW', 'W', '102.634697', '37.929996'),
('3543', '620602', '620600', '凉州区', '中国,甘肃省,武威市,凉州区', '凉州', '中国,甘肃,武威,凉州', '3', '0935', '733000', 'Liangzhou', 'LZ', 'L', '102.64203', '37.92832'),
('3544', '620621', '620600', '民勤县', '中国,甘肃省,武威市,民勤县', '民勤', '中国,甘肃,武威,民勤', '3', '0935', '733300', 'Minqin', 'MQ', 'M', '103.09011', '38.62487'),
('3545', '620622', '620600', '古浪县', '中国,甘肃省,武威市,古浪县', '古浪', '中国,甘肃,武威,古浪', '3', '0935', '733100', 'Gulang', 'GL', 'G', '102.89154', '37.46508'),
('3546', '620623', '620600', '天祝藏族自治县', '中国,甘肃省,武威市,天祝藏族自治县', '天祝', '中国,甘肃,武威,天祝', '3', '0935', '733200', 'Tianzhu', 'TZ', 'T', '103.1361', '36.97715'),
('3547', '620700', '620000', '张掖市', '中国,甘肃省,张掖市', '张掖', '中国,甘肃,张掖', '2', '0936', '734000', 'Zhangye', 'ZY', 'Z', '100.455472', '38.932897'),
('3548', '620702', '620700', '甘州区', '中国,甘肃省,张掖市,甘州区', '甘州', '中国,甘肃,张掖,甘州', '3', '0936', '734000', 'Ganzhou', 'GZ', 'G', '100.4527', '38.92947'),
('3549', '620721', '620700', '肃南裕固族自治县', '中国,甘肃省,张掖市,肃南裕固族自治县', '肃南', '中国,甘肃,张掖,肃南', '3', '0936', '734400', 'Sunan', 'SN', 'S', '99.61407', '38.83776'),
('3550', '620722', '620700', '民乐县', '中国,甘肃省,张掖市,民乐县', '民乐', '中国,甘肃,张掖,民乐', '3', '0936', '734500', 'Minle', 'ML', 'M', '100.81091', '38.43479'),
('3551', '620723', '620700', '临泽县', '中国,甘肃省,张掖市,临泽县', '临泽', '中国,甘肃,张掖,临泽', '3', '0936', '734200', 'Linze', 'LZ', 'L', '100.16445', '39.15252'),
('3552', '620724', '620700', '高台县', '中国,甘肃省,张掖市,高台县', '高台', '中国,甘肃,张掖,高台', '3', '0936', '734300', 'Gaotai', 'GT', 'G', '99.81918', '39.37829'),
('3553', '620725', '620700', '山丹县', '中国,甘肃省,张掖市,山丹县', '山丹', '中国,甘肃,张掖,山丹', '3', '0936', '734100', 'Shandan', 'SD', 'S', '101.09359', '38.78468'),
('3554', '620800', '620000', '平凉市', '中国,甘肃省,平凉市', '平凉', '中国,甘肃,平凉', '2', '0933', '744000', 'Pingliang', 'PL', 'P', '106.684691', '35.54279'),
('3555', '620802', '620800', '崆峒区', '中国,甘肃省,平凉市,崆峒区', '崆峒', '中国,甘肃,平凉,崆峒', '3', '0933', '744000', 'Kongtong', 'KT', 'K', '106.67483', '35.54262'),
('3556', '620821', '620800', '泾川县', '中国,甘肃省,平凉市,泾川县', '泾川', '中国,甘肃,平凉,泾川', '3', '0933', '744300', 'Jingchuan', 'JC', 'J', '107.36581', '35.33223'),
('3557', '620822', '620800', '灵台县', '中国,甘肃省,平凉市,灵台县', '灵台', '中国,甘肃,平凉,灵台', '3', '0933', '744400', 'Lingtai', 'LT', 'L', '107.6174', '35.06768'),
('3558', '620823', '620800', '崇信县', '中国,甘肃省,平凉市,崇信县', '崇信', '中国,甘肃,平凉,崇信', '3', '0933', '744200', 'Chongxin', 'CX', 'C', '107.03738', '35.30344'),
('3559', '620824', '620800', '华亭县', '中国,甘肃省,平凉市,华亭县', '华亭', '中国,甘肃,平凉,华亭', '3', '0933', '744100', 'Huating', 'HT', 'H', '106.65463', '35.2183'),
('3560', '620825', '620800', '庄浪县', '中国,甘肃省,平凉市,庄浪县', '庄浪', '中国,甘肃,平凉,庄浪', '3', '0933', '744600', 'Zhuanglang', 'ZL', 'Z', '106.03662', '35.20235'),
('3561', '620826', '620800', '静宁县', '中国,甘肃省,平凉市,静宁县', '静宁', '中国,甘肃,平凉,静宁', '3', '0933', '743400', 'Jingning', 'JN', 'J', '105.72723', '35.51991'),
('3562', '620900', '620000', '酒泉市', '中国,甘肃省,酒泉市', '酒泉', '中国,甘肃,酒泉', '2', '0937', '735000', 'Jiuquan', 'JQ', 'J', '98.510795', '39.744023'),
('3563', '620902', '620900', '肃州区', '中国,甘肃省,酒泉市,肃州区', '肃州', '中国,甘肃,酒泉,肃州', '3', '0937', '735000', 'Suzhou', 'SZ', 'S', '98.50775', '39.74506'),
('3564', '620921', '620900', '金塔县', '中国,甘肃省,酒泉市,金塔县', '金塔', '中国,甘肃,酒泉,金塔', '3', '0937', '735300', 'Jinta', 'JT', 'J', '98.90002', '39.97733'),
('3565', '620922', '620900', '瓜州县', '中国,甘肃省,酒泉市,瓜州县', '瓜州', '中国,甘肃,酒泉,瓜州', '3', '0937', '736100', 'Guazhou', 'GZ', 'G', '95.78271', '40.51548'),
('3566', '620923', '620900', '肃北蒙古族自治县', '中国,甘肃省,酒泉市,肃北蒙古族自治县', '肃北', '中国,甘肃,酒泉,肃北', '3', '0937', '736300', 'Subei', 'SB', 'S', '94.87649', '39.51214'),
('3567', '620924', '620900', '阿克塞哈萨克族自治县', '中国,甘肃省,酒泉市,阿克塞哈萨克族自治县', '阿克塞', '中国,甘肃,酒泉,阿克塞', '3', '0937', '736400', 'Akesai', 'AKS', 'A', '94.34097', '39.63435'),
('3568', '620981', '620900', '玉门市', '中国,甘肃省,酒泉市,玉门市', '玉门', '中国,甘肃,酒泉,玉门', '3', '0937', '735200', 'Yumen', 'YM', 'Y', '97.04538', '40.29172'),
('3569', '620982', '620900', '敦煌市', '中国,甘肃省,酒泉市,敦煌市', '敦煌', '中国,甘肃,酒泉,敦煌', '3', '0937', '736200', 'Dunhuang', 'DH', 'D', '94.66159', '40.14211'),
('3570', '621000', '620000', '庆阳市', '中国,甘肃省,庆阳市', '庆阳', '中国,甘肃,庆阳', '2', '0934', '745000', 'Qingyang', 'QY', 'Q', '107.638372', '35.734218'),
('3571', '621002', '621000', '西峰区', '中国,甘肃省,庆阳市,西峰区', '西峰', '中国,甘肃,庆阳,西峰', '3', '0934', '745000', 'Xifeng', 'XF', 'X', '107.65107', '35.73065'),
('3572', '621021', '621000', '庆城县', '中国,甘肃省,庆阳市,庆城县', '庆城', '中国,甘肃,庆阳,庆城', '3', '0934', '745100', 'Qingcheng', 'QC', 'Q', '107.88272', '36.01507'),
('3573', '621022', '621000', '环县', '中国,甘肃省,庆阳市,环县', '环县', '中国,甘肃,庆阳,环县', '3', '0934', '745700', 'Huanxian', 'HX', 'H', '107.30835', '36.56846'),
('3574', '621023', '621000', '华池县', '中国,甘肃省,庆阳市,华池县', '华池', '中国,甘肃,庆阳,华池', '3', '0934', '745600', 'Huachi', 'HC', 'H', '107.9891', '36.46108'),
('3575', '621024', '621000', '合水县', '中国,甘肃省,庆阳市,合水县', '合水', '中国,甘肃,庆阳,合水', '3', '0934', '745400', 'Heshui', 'HS', 'H', '108.02032', '35.81911'),
('3576', '621025', '621000', '正宁县', '中国,甘肃省,庆阳市,正宁县', '正宁', '中国,甘肃,庆阳,正宁', '3', '0934', '745300', 'Zhengning', 'ZN', 'Z', '108.36007', '35.49174'),
('3577', '621026', '621000', '宁县', '中国,甘肃省,庆阳市,宁县', '宁县', '中国,甘肃,庆阳,宁县', '3', '0934', '745200', 'Ningxian', 'NX', 'N', '107.92517', '35.50164'),
('3578', '621027', '621000', '镇原县', '中国,甘肃省,庆阳市,镇原县', '镇原', '中国,甘肃,庆阳,镇原', '3', '0934', '744500', 'Zhenyuan', 'ZY', 'Z', '107.199', '35.67712'),
('3579', '621100', '620000', '定西市', '中国,甘肃省,定西市', '定西', '中国,甘肃,定西', '2', '0932', '743000', 'Dingxi', 'DX', 'D', '104.626294', '35.579578'),
('3580', '621102', '621100', '安定区', '中国,甘肃省,定西市,安定区', '安定', '中国,甘肃,定西,安定', '3', '0932', '744300', 'Anding', 'AD', 'A', '104.6106', '35.58066'),
('3581', '621121', '621100', '通渭县', '中国,甘肃省,定西市,通渭县', '通渭', '中国,甘肃,定西,通渭', '3', '0932', '743300', 'Tongwei', 'TW', 'T', '105.24224', '35.21101'),
('3582', '621122', '621100', '陇西县', '中国,甘肃省,定西市,陇西县', '陇西', '中国,甘肃,定西,陇西', '3', '0932', '748100', 'Longxi', 'LX', 'L', '104.63446', '35.00238'),
('3583', '621123', '621100', '渭源县', '中国,甘肃省,定西市,渭源县', '渭源', '中国,甘肃,定西,渭源', '3', '0932', '748200', 'Weiyuan', 'WY', 'W', '104.21435', '35.13649'),
('3584', '621124', '621100', '临洮县', '中国,甘肃省,定西市,临洮县', '临洮', '中国,甘肃,定西,临洮', '3', '0932', '730500', 'Lintao', 'LT', 'L', '103.86196', '35.3751'),
('3585', '621125', '621100', '漳县', '中国,甘肃省,定西市,漳县', '漳县', '中国,甘肃,定西,漳县', '3', '0932', '748300', 'Zhangxian', 'ZX', 'Z', '104.46704', '34.84977'),
('3586', '621126', '621100', '岷县', '中国,甘肃省,定西市,岷县', '岷县', '中国,甘肃,定西,岷县', '3', '0932', '748400', 'Minxian', 'MX', 'M', '104.03772', '34.43444'),
('3587', '621200', '620000', '陇南市', '中国,甘肃省,陇南市', '陇南', '中国,甘肃,陇南', '2', '0939', '742500', 'Longnan', 'LN', 'L', '104.929379', '33.388598'),
('3588', '621202', '621200', '武都区', '中国,甘肃省,陇南市,武都区', '武都', '中国,甘肃,陇南,武都', '3', '0939', '746000', 'Wudu', 'WD', 'W', '104.92652', '33.39239'),
('3589', '621221', '621200', '成县', '中国,甘肃省,陇南市,成县', '成县', '中国,甘肃,陇南,成县', '3', '0939', '742500', 'Chengxian', 'CX', 'C', '105.72586', '33.73925'),
('3590', '621222', '621200', '文县', '中国,甘肃省,陇南市,文县', '文县', '中国,甘肃,陇南,文县', '3', '0939', '746400', 'Wenxian', 'WX', 'W', '104.68362', '32.94337'),
('3591', '621223', '621200', '宕昌县', '中国,甘肃省,陇南市,宕昌县', '宕昌', '中国,甘肃,陇南,宕昌', '3', '0939', '748500', 'Dangchang', 'DC', 'D', '104.39349', '34.04732'),
('3592', '621224', '621200', '康县', '中国,甘肃省,陇南市,康县', '康县', '中国,甘肃,陇南,康县', '3', '0939', '746500', 'Kangxian', 'KX', 'K', '105.60711', '33.32912'),
('3593', '621225', '621200', '西和县', '中国,甘肃省,陇南市,西和县', '西和', '中国,甘肃,陇南,西和', '3', '0939', '742100', 'Xihe', 'XH', 'X', '105.30099', '34.01432'),
('3594', '621226', '621200', '礼县', '中国,甘肃省,陇南市,礼县', '礼县', '中国,甘肃,陇南,礼县', '3', '0939', '742200', 'Lixian', 'LX', 'L', '105.17785', '34.18935'),
('3595', '621227', '621200', '徽县', '中国,甘肃省,陇南市,徽县', '徽县', '中国,甘肃,陇南,徽县', '3', '0939', '742300', 'Huixian', 'HX', 'H', '106.08529', '33.76898'),
('3596', '621228', '621200', '两当县', '中国,甘肃省,陇南市,两当县', '两当', '中国,甘肃,陇南,两当', '3', '0939', '742400', 'Liangdang', 'LD', 'L', '106.30484', '33.9096'),
('3597', '622900', '620000', '临夏回族自治州', '中国,甘肃省,临夏回族自治州', '临夏', '中国,甘肃,临夏', '2', '0930', '731100', 'Linxia', 'LX', 'L', '103.212006', '35.599446'),
('3598', '622901', '622900', '临夏市', '中国,甘肃省,临夏回族自治州,临夏市', '临夏', '中国,甘肃,临夏,临夏', '3', '0930', '731100', 'Linxia', 'LX', 'L', '103.21', '35.59916'),
('3599', '622921', '622900', '临夏县', '中国,甘肃省,临夏回族自治州,临夏县', '临夏', '中国,甘肃,临夏,临夏', '3', '0930', '731800', 'Linxia', 'LX', 'L', '102.9938', '35.49519'),
('3600', '622922', '622900', '康乐县', '中国,甘肃省,临夏回族自治州,康乐县', '康乐', '中国,甘肃,临夏,康乐', '3', '0930', '731500', 'Kangle', 'KL', 'K', '103.71093', '35.37219'),
('3601', '622923', '622900', '永靖县', '中国,甘肃省,临夏回族自治州,永靖县', '永靖', '中国,甘肃,临夏,永靖', '3', '0930', '731600', 'Yongjing', 'YJ', 'Y', '103.32043', '35.93835'),
('3602', '622924', '622900', '广河县', '中国,甘肃省,临夏回族自治州,广河县', '广河', '中国,甘肃,临夏,广河', '3', '0930', '731300', 'Guanghe', 'GH', 'G', '103.56933', '35.48097'),
('3603', '622925', '622900', '和政县', '中国,甘肃省,临夏回族自治州,和政县', '和政', '中国,甘肃,临夏,和政', '3', '0930', '731200', 'Hezheng', 'HZ', 'H', '103.34936', '35.42592'),
('3604', '622926', '622900', '东乡族自治县', '中国,甘肃省,临夏回族自治州,东乡族自治县', '东乡族', '中国,甘肃,临夏,东乡族', '3', '0930', '731400', 'Dongxiangzu', 'DXZ', 'D', '103.39477', '35.66471'),
('3605', '622927', '622900', '积石山保安族东乡族撒拉族自治县', '中国,甘肃省,临夏回族自治州,积石山保安族东乡族撒拉族自治县', '积石山', '中国,甘肃,临夏,积石山', '3', '0930', '731700', 'Jishishan', 'JSS', 'J', '102.87374', '35.7182'),
('3606', '623000', '620000', '甘南藏族自治州', '中国,甘肃省,甘南藏族自治州', '甘南', '中国,甘肃,甘南', '2', '0941', '747000', 'Gannan', 'GN', 'G', '102.911008', '34.986354'),
('3607', '623001', '623000', '合作市', '中国,甘肃省,甘南藏族自治州,合作市', '合作', '中国,甘肃,甘南,合作', '3', '0941', '747000', 'Hezuo', 'HZ', 'H', '102.91082', '35.00016'),
('3608', '623021', '623000', '临潭县', '中国,甘肃省,甘南藏族自治州,临潭县', '临潭', '中国,甘肃,甘南,临潭', '3', '0941', '747500', 'Lintan', 'LT', 'L', '103.35287', '34.69515'),
('3609', '623022', '623000', '卓尼县', '中国,甘肃省,甘南藏族自治州,卓尼县', '卓尼', '中国,甘肃,甘南,卓尼', '3', '0941', '747600', 'Zhuoni', 'ZN', 'Z', '103.50811', '34.58919'),
('3610', '623023', '623000', '舟曲县', '中国,甘肃省,甘南藏族自治州,舟曲县', '舟曲', '中国,甘肃,甘南,舟曲', '3', '0941', '746300', 'Zhouqu', 'ZQ', 'Z', '104.37155', '33.78468'),
('3611', '623024', '623000', '迭部县', '中国,甘肃省,甘南藏族自治州,迭部县', '迭部', '中国,甘肃,甘南,迭部', '3', '0941', '747400', 'Diebu', 'DB', 'D', '103.22274', '34.05623'),
('3612', '623025', '623000', '玛曲县', '中国,甘肃省,甘南藏族自治州,玛曲县', '玛曲', '中国,甘肃,甘南,玛曲', '3', '0941', '747300', 'Maqu', 'MQ', 'M', '102.0754', '33.997'),
('3613', '623026', '623000', '碌曲县', '中国,甘肃省,甘南藏族自治州,碌曲县', '碌曲', '中国,甘肃,甘南,碌曲', '3', '0941', '747200', 'Luqu', 'LQ', 'L', '102.49176', '34.58872'),
('3614', '623027', '623000', '夏河县', '中国,甘肃省,甘南藏族自治州,夏河县', '夏河', '中国,甘肃,甘南,夏河', '3', '0941', '747100', 'Xiahe', 'XH', 'X', '102.52215', '35.20487'),
('3615', '630000', '100000', '青海省', '中国,青海省', '青海', '中国,青海', '1', '', '', 'Qinghai', 'QH', 'Q', '101.778916', '36.623178'),
('3616', '630100', '630000', '西宁市', '中国,青海省,西宁市', '西宁', '中国,青海,西宁', '2', '0971', '810000', 'Xining', 'XN', 'X', '101.778916', '36.623178'),
('3617', '630102', '630100', '城东区', '中国,青海省,西宁市,城东区', '城东', '中国,青海,西宁,城东', '3', '0971', '810000', 'Chengdong', 'CD', 'C', '101.80373', '36.59969'),
('3618', '630103', '630100', '城中区', '中国,青海省,西宁市,城中区', '城中', '中国,青海,西宁,城中', '3', '0971', '810000', 'Chengzhong', 'CZ', 'C', '101.78394', '36.62279'),
('3619', '630104', '630100', '城西区', '中国,青海省,西宁市,城西区', '城西', '中国,青海,西宁,城西', '3', '0971', '810000', 'Chengxi', 'CX', 'C', '101.76588', '36.62828'),
('3620', '630105', '630100', '城北区', '中国,青海省,西宁市,城北区', '城北', '中国,青海,西宁,城北', '3', '0971', '810000', 'Chengbei', 'CB', 'C', '101.7662', '36.65014'),
('3621', '630121', '630100', '大通回族土族自治县', '中国,青海省,西宁市,大通回族土族自治县', '大通', '中国,青海,西宁,大通', '3', '0971', '810100', 'Datong', 'DT', 'D', '101.70236', '36.93489'),
('3622', '630122', '630100', '湟中县', '中国,青海省,西宁市,湟中县', '湟中', '中国,青海,西宁,湟中', '3', '0971', '811600', 'Huangzhong', 'HZ', 'H', '101.57159', '36.50083'),
('3623', '630123', '630100', '湟源县', '中国,青海省,西宁市,湟源县', '湟源', '中国,青海,西宁,湟源', '3', '0971', '812100', 'Huangyuan', 'HY', 'H', '101.25643', '36.68243'),
('3624', '630200', '630000', '海东市', '中国,青海省,海东市', '海东', '中国,青海,海东', '2', '0972', '810600', 'Haidong', 'HD', 'H', '102.10327', '36.502916'),
('3625', '630202', '630200', '乐都区', '中国,青海省,海东市,乐都区', '乐都', '中国,青海,海东,乐都', '3', '0972', '810700', 'Ledu', 'LD', 'L', '102.402431', '36.480291'),
('3626', '630203', '630200', '平安区', '中国,青海省,海东市,平安区', '平安', '中国,青海,海东,平安', '3', '0972', '810600', 'Ping\'an', 'PA', 'P', '102.104295', '36.502714'),
('3627', '630222', '630200', '民和回族土族自治县', '中国,青海省,海东市,民和回族土族自治县', '民和', '中国,青海,海东,民和', '3', '0972', '810800', 'Minhe', 'MH', 'M', '102.804209', '36.329451'),
('3628', '630223', '630200', '互助土族自治县', '中国,青海省,海东市,互助土族自治县', '互助', '中国,青海,海东,互助', '3', '0972', '810500', 'Huzhu', 'HZ', 'H', '101.956734', '36.83994'),
('3629', '630224', '630200', '化隆回族自治县', '中国,青海省,海东市,化隆回族自治县', '化隆', '中国,青海,海东,化隆', '3', '0972', '810900', 'Hualong', 'HL', 'H', '102.262329', '36.098322'),
('3630', '630225', '630200', '循化撒拉族自治县', '中国,青海省,海东市,循化撒拉族自治县', '循化', '中国,青海,海东,循化', '3', '0972', '811100', 'Xunhua', 'XH', 'X', '102.486534', '35.847247'),
('3631', '632200', '630000', '海北藏族自治州', '中国,青海省,海北藏族自治州', '海北', '中国,青海,海北', '2', '0970', '812200', 'Haibei', 'HB', 'H', '100.901059', '36.959435'),
('3632', '632221', '632200', '门源回族自治县', '中国,青海省,海北藏族自治州,门源回族自治县', '门源', '中国,青海,海北,门源', '3', '0970', '810300', 'Menyuan', 'MY', 'M', '101.62228', '37.37611'),
('3633', '632222', '632200', '祁连县', '中国,青海省,海北藏族自治州,祁连县', '祁连', '中国,青海,海北,祁连', '3', '0970', '810400', 'Qilian', 'QL', 'Q', '100.24618', '38.17901'),
('3634', '632223', '632200', '海晏县', '中国,青海省,海北藏族自治州,海晏县', '海晏', '中国,青海,海北,海晏', '3', '0970', '812200', 'Haiyan', 'HY', 'H', '100.9927', '36.89902'),
('3635', '632224', '632200', '刚察县', '中国,青海省,海北藏族自治州,刚察县', '刚察', '中国,青海,海北,刚察', '3', '0970', '812300', 'Gangcha', 'GC', 'G', '100.14675', '37.32161'),
('3636', '632300', '630000', '黄南藏族自治州', '中国,青海省,黄南藏族自治州', '黄南', '中国,青海,黄南', '2', '0973', '811300', 'Huangnan', 'HN', 'H', '102.019988', '35.517744'),
('3637', '632321', '632300', '同仁县', '中国,青海省,黄南藏族自治州,同仁县', '同仁', '中国,青海,黄南,同仁', '3', '0973', '811300', 'Tongren', 'TR', 'T', '102.0184', '35.51603'),
('3638', '632322', '632300', '尖扎县', '中国,青海省,黄南藏族自治州,尖扎县', '尖扎', '中国,青海,黄南,尖扎', '3', '0973', '811200', 'Jianzha', 'JZ', 'J', '102.03411', '35.93947'),
('3639', '632323', '632300', '泽库县', '中国,青海省,黄南藏族自治州,泽库县', '泽库', '中国,青海,黄南,泽库', '3', '0973', '811400', 'Zeku', 'ZK', 'Z', '101.46444', '35.03519'),
('3640', '632324', '632300', '河南蒙古族自治县', '中国,青海省,黄南藏族自治州,河南蒙古族自治县', '河南', '中国,青海,黄南,河南', '3', '0973', '811500', 'Henan', 'HN', 'H', '101.60864', '34.73476'),
('3641', '632500', '630000', '海南藏族自治州', '中国,青海省,海南藏族自治州', '海南', '中国,青海,海南', '2', '0974', '813000', 'Hainan', 'HN', 'H', '100.619542', '36.280353'),
('3642', '632521', '632500', '共和县', '中国,青海省,海南藏族自治州,共和县', '共和', '中国,青海,海南,共和', '3', '0974', '813000', 'Gonghe', 'GH', 'G', '100.62003', '36.2841'),
('3643', '632522', '632500', '同德县', '中国,青海省,海南藏族自治州,同德县', '同德', '中国,青海,海南,同德', '3', '0974', '813200', 'Tongde', 'TD', 'T', '100.57159', '35.25488'),
('3644', '632523', '632500', '贵德县', '中国,青海省,海南藏族自治州,贵德县', '贵德', '中国,青海,海南,贵德', '3', '0974', '811700', 'Guide', 'GD', 'G', '101.432', '36.044'),
('3645', '632524', '632500', '兴海县', '中国,青海省,海南藏族自治州,兴海县', '兴海', '中国,青海,海南,兴海', '3', '0974', '813300', 'Xinghai', 'XH', 'X', '99.98846', '35.59031'),
('3646', '632525', '632500', '贵南县', '中国,青海省,海南藏族自治州,贵南县', '贵南', '中国,青海,海南,贵南', '3', '0974', '813100', 'Guinan', 'GN', 'G', '100.74716', '35.58667'),
('3647', '632600', '630000', '果洛藏族自治州', '中国,青海省,果洛藏族自治州', '果洛', '中国,青海,果洛', '2', '0975', '814000', 'Golog', 'GL', 'G', '100.242143', '34.4736'),
('3648', '632621', '632600', '玛沁县', '中国,青海省,果洛藏族自治州,玛沁县', '玛沁', '中国,青海,果洛,玛沁', '3', '0975', '814000', 'Maqin', 'MQ', 'M', '100.23901', '34.47746'),
('3649', '632622', '632600', '班玛县', '中国,青海省,果洛藏族自治州,班玛县', '班玛', '中国,青海,果洛,班玛', '3', '0975', '814300', 'Banma', 'BM', 'B', '100.73745', '32.93253'),
('3650', '632623', '632600', '甘德县', '中国,青海省,果洛藏族自治州,甘德县', '甘德', '中国,青海,果洛,甘德', '3', '0975', '814100', 'Gande', 'GD', 'G', '99.90246', '33.96838'),
('3651', '632624', '632600', '达日县', '中国,青海省,果洛藏族自治州,达日县', '达日', '中国,青海,果洛,达日', '3', '0975', '814200', 'Dari', 'DR', 'D', '99.65179', '33.75193'),
('3652', '632625', '632600', '久治县', '中国,青海省,果洛藏族自治州,久治县', '久治', '中国,青海,果洛,久治', '3', '0975', '624700', 'Jiuzhi', 'JZ', 'J', '101.48342', '33.42989'),
('3653', '632626', '632600', '玛多县', '中国,青海省,果洛藏族自治州,玛多县', '玛多', '中国,青海,果洛,玛多', '3', '0975', '813500', 'Maduo', 'MD', 'M', '98.20996', '34.91567'),
('3654', '632700', '630000', '玉树藏族自治州', '中国,青海省,玉树藏族自治州', '玉树', '中国,青海,玉树', '2', '0976', '815000', 'Yushu', 'YS', 'Y', '97.008522', '33.004049'),
('3655', '632701', '632700', '玉树市', '中国,青海省,玉树藏族自治州,玉树市', '玉树', '中国,青海,玉树,玉树', '3', '0976', '815000', 'Yushu', 'YS', 'Y', '97.008762', '33.00393'),
('3656', '632722', '632700', '杂多县', '中国,青海省,玉树藏族自治州,杂多县', '杂多', '中国,青海,玉树,杂多', '3', '0976', '815300', 'Zaduo', 'ZD', 'Z', '95.29864', '32.89318'),
('3657', '632723', '632700', '称多县', '中国,青海省,玉树藏族自治州,称多县', '称多', '中国,青海,玉树,称多', '3', '0976', '815100', 'Chenduo', 'CD', 'C', '97.10788', '33.36899'),
('3658', '632724', '632700', '治多县', '中国,青海省,玉树藏族自治州,治多县', '治多', '中国,青海,玉树,治多', '3', '0976', '815400', 'Zhiduo', 'ZD', 'Z', '95.61572', '33.8528'),
('3659', '632725', '632700', '囊谦县', '中国,青海省,玉树藏族自治州,囊谦县', '囊谦', '中国,青海,玉树,囊谦', '3', '0976', '815200', 'Nangqian', 'NQ', 'N', '96.47753', '32.20359'),
('3660', '632726', '632700', '曲麻莱县', '中国,青海省,玉树藏族自治州,曲麻莱县', '曲麻莱', '中国,青海,玉树,曲麻莱', '3', '0976', '815500', 'Qumalai', 'QML', 'Q', '95.79757', '34.12609'),
('3661', '632800', '630000', '海西蒙古族藏族自治州', '中国,青海省,海西蒙古族藏族自治州', '海西', '中国,青海,海西', '2', '0977', '817000', 'Haixi', 'HX', 'H', '97.370785', '37.374663'),
('3662', '632801', '632800', '格尔木市', '中国,青海省,海西蒙古族藏族自治州,格尔木市', '格尔木', '中国,青海,海西,格尔木', '3', '0979', '816000', 'Geermu', 'GEM', 'G', '94.90329', '36.40236'),
('3663', '632802', '632800', '德令哈市', '中国,青海省,海西蒙古族藏族自治州,德令哈市', '德令哈', '中国,青海,海西,德令哈', '3', '0977', '817000', 'Delingha', 'DLH', 'D', '97.36084', '37.36946'),
('3664', '632821', '632800', '乌兰县', '中国,青海省,海西蒙古族藏族自治州,乌兰县', '乌兰', '中国,青海,海西,乌兰', '3', '0977', '817100', 'Wulan', 'WL', 'W', '98.48196', '36.93471'),
('3665', '632822', '632800', '都兰县', '中国,青海省,海西蒙古族藏族自治州,都兰县', '都兰', '中国,青海,海西,都兰', '3', '0977', '816100', 'Dulan', 'DL', 'D', '98.09228', '36.30135'),
('3666', '632823', '632800', '天峻县', '中国,青海省,海西蒙古族藏族自治州,天峻县', '天峻', '中国,青海,海西,天峻', '3', '0977', '817200', 'Tianjun', 'TJ', 'T', '99.02453', '37.30326'),
('3667', '640000', '100000', '宁夏回族自治区', '中国,宁夏回族自治区', '宁夏', '中国,宁夏', '1', '', '', 'Ningxia', 'NX', 'N', '106.278179', '38.46637'),
('3668', '640100', '640000', '银川市', '中国,宁夏回族自治区,银川市', '银川', '中国,宁夏,银川', '2', '0951', '750000', 'Yinchuan', 'YC', 'Y', '106.278179', '38.46637'),
('3669', '640104', '640100', '兴庆区', '中国,宁夏回族自治区,银川市,兴庆区', '兴庆', '中国,宁夏,银川,兴庆', '3', '0951', '750000', 'Xingqing', 'XQ', 'X', '106.28872', '38.47392'),
('3670', '640105', '640100', '西夏区', '中国,宁夏回族自治区,银川市,西夏区', '西夏', '中国,宁夏,银川,西夏', '3', '0951', '750000', 'Xixia', 'XX', 'X', '106.15023', '38.49137'),
('3671', '640106', '640100', '金凤区', '中国,宁夏回族自治区,银川市,金凤区', '金凤', '中国,宁夏,银川,金凤', '3', '0951', '750000', 'Jinfeng', 'JF', 'J', '106.24261', '38.47294'),
('3672', '640121', '640100', '永宁县', '中国,宁夏回族自治区,银川市,永宁县', '永宁', '中国,宁夏,银川,永宁', '3', '0951', '750100', 'Yongning', 'YN', 'Y', '106.2517', '38.27559'),
('3673', '640122', '640100', '贺兰县', '中国,宁夏回族自治区,银川市,贺兰县', '贺兰', '中国,宁夏,银川,贺兰', '3', '0951', '750200', 'Helan', 'HL', 'H', '106.34982', '38.5544'),
('3674', '640181', '640100', '灵武市', '中国,宁夏回族自治区,银川市,灵武市', '灵武', '中国,宁夏,银川,灵武', '3', '0951', '751400', 'Lingwu', 'LW', 'L', '106.33999', '38.10266'),
('3675', '640182', '640100', '经济开发区', '中国,宁夏回族自治区,银川市,经济开发区', '经济开发区', '中国,宁夏,银川,经济开发区', '3', '0951', '750000', 'Jingjikaifaqu', 'JJKFQ', 'J', '106.223199', '38.479626'),
('3676', '640200', '640000', '石嘴山市', '中国,宁夏回族自治区,石嘴山市', '石嘴山', '中国,宁夏,石嘴山', '2', '0952', '753000', 'Shizuishan', 'SZS', 'S', '106.376173', '39.01333'),
('3677', '640202', '640200', '大武口区', '中国,宁夏回族自治区,石嘴山市,大武口区', '大武口', '中国,宁夏,石嘴山,大武口', '3', '0952', '753000', 'Dawukou', 'DWK', 'D', '106.37717', '39.01226'),
('3678', '640205', '640200', '惠农区', '中国,宁夏回族自治区,石嘴山市,惠农区', '惠农', '中国,宁夏,石嘴山,惠农', '3', '0952', '753600', 'Huinong', 'HN', 'H', '106.71145', '39.13193'),
('3679', '640221', '640200', '平罗县', '中国,宁夏回族自治区,石嘴山市,平罗县', '平罗', '中国,宁夏,石嘴山,平罗', '3', '0952', '753400', 'Pingluo', 'PL', 'P', '106.54538', '38.90429'),
('3680', '640222', '640200', '经济开发区', '中国,宁夏回族自治区,石嘴山市,经济开发区', '经济开发区', '中国,宁夏,石嘴山,经济开发区', '3', '0952', '753000', 'Jingjikaifaqu', 'JJKFQ', 'J', '106.3157', '38.954601'),
('3681', '640300', '640000', '吴忠市', '中国,宁夏回族自治区,吴忠市', '吴忠', '中国,宁夏,吴忠', '2', '0953', '751100', 'Wuzhong', 'WZ', 'W', '106.199409', '37.986165'),
('3682', '640302', '640300', '利通区', '中国,宁夏回族自治区,吴忠市,利通区', '利通', '中国,宁夏,吴忠,利通', '3', '0953', '751100', 'Litong', 'LT', 'L', '106.20311', '37.98512'),
('3683', '640303', '640300', '红寺堡区', '中国,宁夏回族自治区,吴忠市,红寺堡区', '红寺堡', '中国,宁夏,吴忠,红寺堡', '3', '0953', '751900', 'Hongsibao', 'HSB', 'H', '106.19822', '37.99747'),
('3684', '640323', '640300', '盐池县', '中国,宁夏回族自治区,吴忠市,盐池县', '盐池', '中国,宁夏,吴忠,盐池', '3', '0953', '751500', 'Yanchi', 'YC', 'Y', '107.40707', '37.7833'),
('3685', '640324', '640300', '同心县', '中国,宁夏回族自治区,吴忠市,同心县', '同心', '中国,宁夏,吴忠,同心', '3', '0953', '751300', 'Tongxin', 'TX', 'T', '105.91418', '36.98116'),
('3686', '640381', '640300', '青铜峡市', '中国,宁夏回族自治区,吴忠市,青铜峡市', '青铜峡', '中国,宁夏,吴忠,青铜峡', '3', '0953', '751600', 'Qingtongxia', 'QTX', 'Q', '106.07489', '38.02004'),
('3687', '640400', '640000', '固原市', '中国,宁夏回族自治区,固原市', '固原', '中国,宁夏,固原', '2', '0954', '756000', 'Guyuan', 'GY', 'G', '106.285241', '36.004561'),
('3688', '640402', '640400', '原州区', '中国,宁夏回族自治区,固原市,原州区', '原州', '中国,宁夏,固原,原州', '3', '0954', '756000', 'Yuanzhou', 'YZ', 'Y', '106.28778', '36.00374'),
('3689', '640422', '640400', '西吉县', '中国,宁夏回族自治区,固原市,西吉县', '西吉', '中国,宁夏,固原,西吉', '3', '0954', '756200', 'Xiji', 'XJ', 'X', '105.73107', '35.96616'),
('3690', '640423', '640400', '隆德县', '中国,宁夏回族自治区,固原市,隆德县', '隆德', '中国,宁夏,固原,隆德', '3', '0954', '756300', 'Longde', 'LD', 'L', '106.12426', '35.61718'),
('3691', '640424', '640400', '泾源县', '中国,宁夏回族自治区,固原市,泾源县', '泾源', '中国,宁夏,固原,泾源', '3', '0954', '756400', 'Jingyuan', 'JY', 'J', '106.33902', '35.49072'),
('3692', '640425', '640400', '彭阳县', '中国,宁夏回族自治区,固原市,彭阳县', '彭阳', '中国,宁夏,固原,彭阳', '3', '0954', '756500', 'Pengyang', 'PY', 'P', '106.64462', '35.85076'),
('3693', '640500', '640000', '中卫市', '中国,宁夏回族自治区,中卫市', '中卫', '中国,宁夏,中卫', '2', '0955', '755000', 'Zhongwei', 'ZW', 'Z', '105.189568', '37.514951'),
('3694', '640502', '640500', '沙坡头区', '中国,宁夏回族自治区,中卫市,沙坡头区', '沙坡头', '中国,宁夏,中卫,沙坡头', '3', '0955', '755000', 'Shapotou', 'SPT', 'S', '105.18962', '37.51044'),
('3695', '640521', '640500', '中宁县', '中国,宁夏回族自治区,中卫市,中宁县', '中宁', '中国,宁夏,中卫,中宁', '3', '0955', '755100', 'Zhongning', 'ZN', 'Z', '105.68515', '37.49149'),
('3696', '640522', '640500', '海原县', '中国,宁夏回族自治区,中卫市,海原县', '海原', '中国,宁夏,中卫,海原', '3', '0955', '755200', 'Haiyuan', 'HY', 'H', '105.64712', '36.56498'),
('3697', '650000', '100000', '新疆维吾尔自治区', '中国,新疆维吾尔自治区', '新疆', '中国,新疆', '1', '', '', 'Xinjiang', 'XJ', 'X', '87.617733', '43.792818'),
('3698', '650100', '650000', '乌鲁木齐市', '中国,新疆维吾尔自治区,乌鲁木齐市', '乌鲁木齐', '中国,新疆,乌鲁木齐', '2', '0991', '830000', 'Urumqi', 'WLMQ', 'U', '87.617733', '43.792818'),
('3699', '650102', '650100', '天山区', '中国,新疆维吾尔自治区,乌鲁木齐市,天山区', '天山', '中国,新疆,乌鲁木齐,天山', '3', '0991', '830000', 'Tianshan', 'TS', 'T', '87.63167', '43.79439'),
('3700', '650103', '650100', '沙依巴克区', '中国,新疆维吾尔自治区,乌鲁木齐市,沙依巴克区', '沙依巴克', '中国,新疆,乌鲁木齐,沙依巴克', '3', '0991', '830000', 'Shayibake', 'SYBK', 'S', '87.59788', '43.80118'),
('3701', '650104', '650100', '新市区', '中国,新疆维吾尔自治区,乌鲁木齐市,新市区', '新市', '中国,新疆,乌鲁木齐,新市', '3', '0991', '830000', 'Xinshi', 'XS', 'X', '87.57406', '43.84348'),
('3702', '650105', '650100', '水磨沟区', '中国,新疆维吾尔自治区,乌鲁木齐市,水磨沟区', '水磨沟', '中国,新疆,乌鲁木齐,水磨沟', '3', '0991', '830000', 'Shuimogou', 'SMG', 'S', '87.64249', '43.83247'),
('3703', '650106', '650100', '头屯河区', '中国,新疆维吾尔自治区,乌鲁木齐市,头屯河区', '头屯河', '中国,新疆,乌鲁木齐,头屯河', '3', '0991', '830000', 'Toutunhe', 'TTH', 'T', '87.29138', '43.85487'),
('3704', '650107', '650100', '达坂城区', '中国,新疆维吾尔自治区,乌鲁木齐市,达坂城区', '达坂城', '中国,新疆,乌鲁木齐,达坂城', '3', '0991', '830039', 'Dabancheng', 'DBC', 'D', '88.30697', '43.35797'),
('3705', '650109', '650100', '米东区', '中国,新疆维吾尔自治区,乌鲁木齐市,米东区', '米东', '中国,新疆,乌鲁木齐,米东', '3', '0991', '830019', 'Midong', 'MD', 'M', '87.68583', '43.94739'),
('3706', '650121', '650100', '乌鲁木齐县', '中国,新疆维吾尔自治区,乌鲁木齐市,乌鲁木齐县', '乌鲁木齐', '中国,新疆,乌鲁木齐,乌鲁木齐', '3', '0991', '830000', 'Wulumuqi', 'WLMQ', 'W', '87.40939', '43.47125'),
('3707', '650200', '650000', '克拉玛依市', '中国,新疆维吾尔自治区,克拉玛依市', '克拉玛依', '中国,新疆,克拉玛依', '2', '0990', '834000', 'Karamay', 'KLMY', 'K', '84.873946', '45.595886'),
('3708', '650202', '650200', '独山子区', '中国,新疆维吾尔自治区,克拉玛依市,独山子区', '独山子', '中国,新疆,克拉玛依,独山子', '3', '0992', '833600', 'Dushanzi', 'DSZ', 'D', '84.88671', '44.32867'),
('3709', '650203', '650200', '克拉玛依区', '中国,新疆维吾尔自治区,克拉玛依市,克拉玛依区', '克拉玛依', '中国,新疆,克拉玛依,克拉玛依', '3', '0990', '834000', 'Kelamayi', 'KLMY', 'K', '84.86225', '45.59089'),
('3710', '650204', '650200', '白碱滩区', '中国,新疆维吾尔自治区,克拉玛依市,白碱滩区', '白碱滩', '中国,新疆,克拉玛依,白碱滩', '3', '0990', '834008', 'Baijiantan', 'BJT', 'B', '85.13244', '45.68768'),
('3711', '650205', '650200', '乌尔禾区', '中国,新疆维吾尔自治区,克拉玛依市,乌尔禾区', '乌尔禾', '中国,新疆,克拉玛依,乌尔禾', '3', '0990', '834014', 'Wuerhe', 'WEH', 'W', '85.69143', '46.09006'),
('3712', '650400', '650000', '吐鲁番市', '中国,新疆维吾尔自治区,吐鲁番市', '吐鲁番', '中国,新疆,吐鲁番', '2', '0995', '838000', 'Turpan', 'TLF', 'T', '89.184078', '42.947613'),
('3713', '650402', '650400', '高昌区', '中国,新疆维吾尔自治区,吐鲁番市,高昌区', '高昌', '中国,新疆,吐鲁番,高昌', '3', '0995', '838000', 'Gaochang', 'GC', 'G', '89.18579', '42.93505'),
('3714', '650421', '650400', '鄯善县', '中国,新疆维吾尔自治区,吐鲁番市,鄯善县', '鄯善', '中国,新疆,吐鲁番,鄯善', '3', '0995', '838200', 'Shanshan', 'SS', 'S', '90.21402', '42.8635'),
('3715', '650422', '650400', '托克逊县', '中国,新疆维吾尔自治区,吐鲁番市,托克逊县', '托克逊', '中国,新疆,吐鲁番,托克逊', '3', '0995', '838100', 'Tuokexun', 'TKX', 'T', '88.65823', '42.79231'),
('3716', '650500', '650000', '哈密市', '中国,新疆维吾尔自治区,哈密市', '哈密', '中国,新疆,哈密', '2', '0902', '839000', 'Hami', 'HM', 'H', '93.51316', '42.833248'),
('3717', '650502', '650500', '伊州区', '中国,新疆维吾尔自治区,哈密市,伊州区', '哈密', '中国,新疆,哈密,伊州', '3', '0902', '839000', 'Yizhou', 'YZ', 'Y', '93.514797', '42.827255'),
('3718', '650521', '650500', '巴里坤哈萨克自治县', '中国,新疆维吾尔自治区,哈密市,巴里坤哈萨克自治县', '巴里坤', '中国,新疆,哈密,巴里坤', '3', '0902', '839200', 'Balikun', 'BLK', 'B', '93.01236', '43.59993'),
('3719', '650522', '650500', '伊吾县', '中国,新疆维吾尔自治区,哈密市,伊吾县', '伊吾', '中国,新疆,哈密,伊吾', '3', '0902', '839300', 'Yiwu', 'YW', 'Y', '94.69403', '43.2537'),
('3720', '652300', '650000', '昌吉回族自治州', '中国,新疆维吾尔自治区,昌吉回族自治州', '昌吉', '中国,新疆,昌吉', '2', '0994', '831100', 'Changji', 'CJ', 'C', '87.304012', '44.014577'),
('3721', '652301', '652300', '昌吉市', '中国,新疆维吾尔自治区,昌吉回族自治州,昌吉市', '昌吉', '中国,新疆,昌吉,昌吉', '3', '0994', '831100', 'Changji', 'CJ', 'C', '87.30249', '44.01267'),
('3722', '652302', '652300', '阜康市', '中国,新疆维吾尔自治区,昌吉回族自治州,阜康市', '阜康', '中国,新疆,昌吉,阜康', '3', '0994', '831500', 'Fukang', 'FK', 'F', '87.98529', '44.1584'),
('3723', '652323', '652300', '呼图壁县', '中国,新疆维吾尔自治区,昌吉回族自治州,呼图壁县', '呼图壁', '中国,新疆,昌吉,呼图壁', '3', '0994', '831200', 'Hutubi', 'HTB', 'H', '86.89892', '44.18977'),
('3724', '652324', '652300', '玛纳斯县', '中国,新疆维吾尔自治区,昌吉回族自治州,玛纳斯县', '玛纳斯', '中国,新疆,昌吉,玛纳斯', '3', '0994', '832200', 'Manasi', 'MNS', 'M', '86.2145', '44.30438'),
('3725', '652325', '652300', '奇台县', '中国,新疆维吾尔自治区,昌吉回族自治州,奇台县', '奇台', '中国,新疆,昌吉,奇台', '3', '0994', '831800', 'Qitai', 'QT', 'Q', '89.5932', '44.02221'),
('3726', '652327', '652300', '吉木萨尔县', '中国,新疆维吾尔自治区,昌吉回族自治州,吉木萨尔县', '吉木萨尔', '中国,新疆,昌吉,吉木萨尔', '3', '0994', '831700', 'Jimusaer', 'JMSE', 'J', '89.18078', '44.00048'),
('3727', '652328', '652300', '木垒哈萨克自治县', '中国,新疆维吾尔自治区,昌吉回族自治州,木垒哈萨克自治县', '木垒', '中国,新疆,昌吉,木垒', '3', '0994', '831900', 'Mulei', 'ML', 'M', '90.28897', '43.83508'),
('3728', '652700', '650000', '博尔塔拉蒙古自治州', '中国,新疆维吾尔自治区,博尔塔拉蒙古自治州', '博尔塔拉', '中国,新疆,博尔塔拉', '2', '0909', '833400', 'Bortala', 'BETL', 'B', '82.074778', '44.903258'),
('3729', '652701', '652700', '博乐市', '中国,新疆维吾尔自治区,博尔塔拉蒙古自治州,博乐市', '博乐', '中国,新疆,博尔塔拉,博乐', '3', '0909', '833400', 'Bole', 'BL', 'B', '82.0713', '44.90052'),
('3730', '652702', '652700', '阿拉山口市', '中国,新疆维吾尔自治区,博尔塔拉蒙古自治州,阿拉山口市', '阿拉山口', '中国,新疆,博尔塔拉,阿拉山口', '3', '0909', '833400', 'Alashankou', 'ALSK', 'A', '82.567721', '45.170616'),
('3731', '652722', '652700', '精河县', '中国,新疆维吾尔自治区,博尔塔拉蒙古自治州,精河县', '精河', '中国,新疆,博尔塔拉,精河', '3', '0909', '833300', 'Jinghe', 'JH', 'J', '82.89419', '44.60774'),
('3732', '652723', '652700', '温泉县', '中国,新疆维吾尔自治区,博尔塔拉蒙古自治州,温泉县', '温泉', '中国,新疆,博尔塔拉,温泉', '3', '0909', '833500', 'Wenquan', 'WQ', 'W', '81.03134', '44.97373'),
('3733', '652800', '650000', '巴音郭楞蒙古自治州', '中国,新疆维吾尔自治区,巴音郭楞蒙古自治州', '巴音郭楞', '中国,新疆,巴音郭楞', '2', '0996', '841000', 'Bayingol', 'BYGL', 'B', '86.150969', '41.768552'),
('3734', '652801', '652800', '库尔勒市', '中国,新疆维吾尔自治区,巴音郭楞蒙古自治州,库尔勒市', '库尔勒', '中国,新疆,巴音郭楞,库尔勒', '3', '0996', '841000', 'Kuerle', 'KEL', 'K', '86.15528', '41.76602'),
('3735', '652822', '652800', '轮台县', '中国,新疆维吾尔自治区,巴音郭楞蒙古自治州,轮台县', '轮台', '中国,新疆,巴音郭楞,轮台', '3', '0996', '841600', 'Luntai', 'LT', 'L', '84.26101', '41.77642'),
('3736', '652823', '652800', '尉犁县', '中国,新疆维吾尔自治区,巴音郭楞蒙古自治州,尉犁县', '尉犁', '中国,新疆,巴音郭楞,尉犁', '3', '0996', '841500', 'Yuli', 'YL', 'Y', '86.25903', '41.33632'),
('3737', '652824', '652800', '若羌县', '中国,新疆维吾尔自治区,巴音郭楞蒙古自治州,若羌县', '若羌', '中国,新疆,巴音郭楞,若羌', '3', '0996', '841800', 'Ruoqiang', 'RQ', 'R', '88.16812', '39.0179'),
('3738', '652825', '652800', '且末县', '中国,新疆维吾尔自治区,巴音郭楞蒙古自治州,且末县', '且末', '中国,新疆,巴音郭楞,且末', '3', '0996', '841900', 'Qiemo', 'QM', 'Q', '85.52975', '38.14534'),
('3739', '652826', '652800', '焉耆回族自治县', '中国,新疆维吾尔自治区,巴音郭楞蒙古自治州,焉耆回族自治县', '焉耆', '中国,新疆,巴音郭楞,焉耆', '3', '0996', '841100', 'Yanqi', 'YQ', 'Y', '86.5744', '42.059'),
('3740', '652827', '652800', '和静县', '中国,新疆维吾尔自治区,巴音郭楞蒙古自治州,和静县', '和静', '中国,新疆,巴音郭楞,和静', '3', '0996', '841300', 'Hejing', 'HJ', 'H', '86.39611', '42.31838'),
('3741', '652828', '652800', '和硕县', '中国,新疆维吾尔自治区,巴音郭楞蒙古自治州,和硕县', '和硕', '中国,新疆,巴音郭楞,和硕', '3', '0996', '841200', 'Heshuo', 'HS', 'H', '86.86392', '42.26814'),
('3742', '652829', '652800', '博湖县', '中国,新疆维吾尔自治区,巴音郭楞蒙古自治州,博湖县', '博湖', '中国,新疆,巴音郭楞,博湖', '3', '0996', '841400', 'Bohu', 'BH', 'B', '86.63333', '41.98014'),
('3743', '652900', '650000', '阿克苏地区', '中国,新疆维吾尔自治区,阿克苏地区', '阿克苏', '中国,新疆,阿克苏', '2', '0997', '843000', 'Aksu', 'AKS', 'A', '80.265068', '41.170712'),
('3744', '652901', '652900', '阿克苏市', '中国,新疆维吾尔自治区,阿克苏地区,阿克苏市', '阿克苏', '中国,新疆,阿克苏,阿克苏', '3', '0997', '843000', 'Akesu', 'AKS', 'A', '80.26338', '41.16754'),
('3745', '652922', '652900', '温宿县', '中国,新疆维吾尔自治区,阿克苏地区,温宿县', '温宿', '中国,新疆,阿克苏,温宿', '3', '0997', '843100', 'Wensu', 'WS', 'W', '80.24173', '41.27679'),
('3746', '652923', '652900', '库车县', '中国,新疆维吾尔自治区,阿克苏地区,库车县', '库车', '中国,新疆,阿克苏,库车', '3', '0997', '842000', 'Kuche', 'KC', 'K', '82.96209', '41.71793'),
('3747', '652924', '652900', '沙雅县', '中国,新疆维吾尔自治区,阿克苏地区,沙雅县', '沙雅', '中国,新疆,阿克苏,沙雅', '3', '0997', '842200', 'Shaya', 'SY', 'S', '82.78131', '41.22497'),
('3748', '652925', '652900', '新和县', '中国,新疆维吾尔自治区,阿克苏地区,新和县', '新和', '中国,新疆,阿克苏,新和', '3', '0997', '842100', 'Xinhe', 'XH', 'X', '82.61053', '41.54964'),
('3749', '652926', '652900', '拜城县', '中国,新疆维吾尔自治区,阿克苏地区,拜城县', '拜城', '中国,新疆,阿克苏,拜城', '3', '0997', '842300', 'Baicheng', 'BC', 'B', '81.87564', '41.79801'),
('3750', '652927', '652900', '乌什县', '中国,新疆维吾尔自治区,阿克苏地区,乌什县', '乌什', '中国,新疆,阿克苏,乌什', '3', '0997', '843400', 'Wushi', 'WS', 'W', '79.22937', '41.21569'),
('3751', '652928', '652900', '阿瓦提县', '中国,新疆维吾尔自治区,阿克苏地区,阿瓦提县', '阿瓦提', '中国,新疆,阿克苏,阿瓦提', '3', '0997', '843200', 'Awati', 'AWT', 'A', '80.38336', '40.63926'),
('3752', '652929', '652900', '柯坪县', '中国,新疆维吾尔自治区,阿克苏地区,柯坪县', '柯坪', '中国,新疆,阿克苏,柯坪', '3', '0997', '843600', 'Keping', 'KP', 'K', '79.04751', '40.50585'),
('3753', '653000', '650000', '克孜勒苏柯尔克孜自治州', '中国,新疆维吾尔自治区,克孜勒苏柯尔克孜自治州', '克孜勒苏', '中国,新疆,克孜勒苏', '2', '0908', '845350', 'Kizilsu', 'KZLS', 'K', '76.172825', '39.713431'),
('3754', '653001', '653000', '阿图什市', '中国,新疆维吾尔自治区,克孜勒苏柯尔克孜自治州,阿图什市', '阿图什', '中国,新疆,克孜勒苏,阿图什', '3', '0908', '845350', 'Atushi', 'ATS', 'A', '76.16827', '39.71615'),
('3755', '653022', '653000', '阿克陶县', '中国,新疆维吾尔自治区,克孜勒苏柯尔克孜自治州,阿克陶县', '阿克陶', '中国,新疆,克孜勒苏,阿克陶', '3', '0908', '845550', 'Aketao', 'AKT', 'A', '75.94692', '39.14892'),
('3756', '653023', '653000', '阿合奇县', '中国,新疆维吾尔自治区,克孜勒苏柯尔克孜自治州,阿合奇县', '阿合奇', '中国,新疆,克孜勒苏,阿合奇', '3', '0997', '843500', 'Aheqi', 'AHQ', 'A', '78.44848', '40.93947'),
('3757', '653024', '653000', '乌恰县', '中国,新疆维吾尔自治区,克孜勒苏柯尔克孜自治州,乌恰县', '乌恰', '中国,新疆,克孜勒苏,乌恰', '3', '0908', '845450', 'Wuqia', 'WQ', 'W', '75.25839', '39.71984'),
('3758', '653100', '650000', '喀什地区', '中国,新疆维吾尔自治区,喀什地区', '喀什', '中国,新疆,喀什', '2', '0998', '844000', 'Kashgar', 'KS', 'K', '75.989138', '39.467664'),
('3759', '653101', '653100', '喀什市', '中国,新疆维吾尔自治区,喀什地区,喀什市', '喀什', '中国,新疆,喀什,喀什', '3', '0998', '844000', 'Kashi', 'KS', 'K', '75.99379', '39.46768'),
('3760', '653121', '653100', '疏附县', '中国,新疆维吾尔自治区,喀什地区,疏附县', '疏附', '中国,新疆,喀什,疏附', '3', '0998', '844100', 'Shufu', 'SF', 'S', '75.86029', '39.37534'),
('3761', '653122', '653100', '疏勒县', '中国,新疆维吾尔自治区,喀什地区,疏勒县', '疏勒', '中国,新疆,喀什,疏勒', '3', '0998', '844200', 'Shule', 'SL', 'S', '76.05398', '39.40625'),
('3762', '653123', '653100', '英吉沙县', '中国,新疆维吾尔自治区,喀什地区,英吉沙县', '英吉沙', '中国,新疆,喀什,英吉沙', '3', '0998', '844500', 'Yingjisha', 'YJS', 'Y', '76.17565', '38.92968'),
('3763', '653124', '653100', '泽普县', '中国,新疆维吾尔自治区,喀什地区,泽普县', '泽普', '中国,新疆,喀什,泽普', '3', '0998', '844800', 'Zepu', 'ZP', 'Z', '77.27145', '38.18935'),
('3764', '653125', '653100', '莎车县', '中国,新疆维吾尔自治区,喀什地区,莎车县', '莎车', '中国,新疆,喀什,莎车', '3', '0998', '844700', 'Shache', 'SC', 'S', '77.24316', '38.41601'),
('3765', '653126', '653100', '叶城县', '中国,新疆维吾尔自治区,喀什地区,叶城县', '叶城', '中国,新疆,喀什,叶城', '3', '0998', '844900', 'Yecheng', 'YC', 'Y', '77.41659', '37.88324'),
('3766', '653127', '653100', '麦盖提县', '中国,新疆维吾尔自治区,喀什地区,麦盖提县', '麦盖提', '中国,新疆,喀什,麦盖提', '3', '0998', '844600', 'Maigaiti', 'MGT', 'M', '77.64224', '38.89662'),
('3767', '653128', '653100', '岳普湖县', '中国,新疆维吾尔自治区,喀什地区,岳普湖县', '岳普湖', '中国,新疆,喀什,岳普湖', '3', '0998', '844400', 'Yuepuhu', 'YPH', 'Y', '76.77233', '39.23561'),
('3768', '653129', '653100', '伽师县', '中国,新疆维吾尔自治区,喀什地区,伽师县', '伽师', '中国,新疆,喀什,伽师', '3', '0998', '844300', 'Jiashi', 'JS', 'J', '76.72372', '39.48801'),
('3769', '653130', '653100', '巴楚县', '中国,新疆维吾尔自治区,喀什地区,巴楚县', '巴楚', '中国,新疆,喀什,巴楚', '3', '0998', '843800', 'Bachu', 'BC', 'B', '78.54888', '39.7855'),
('3770', '653131', '653100', '塔什库尔干塔吉克自治县', '中国,新疆维吾尔自治区,喀什地区,塔什库尔干塔吉克自治县', '塔什库尔干塔吉克', '中国,新疆,喀什,塔什库尔干塔吉克', '3', '0998', '845250', 'Tashikuergantajike', 'TSKEGTJK', 'T', '75.23196', '37.77893'),
('3771', '653200', '650000', '和田地区', '中国,新疆维吾尔自治区,和田地区', '和田', '中国,新疆,和田', '2', '0903', '848000', 'Hotan', 'HT', 'H', '79.92533', '37.110687'),
('3772', '653201', '653200', '和田市', '中国,新疆维吾尔自治区,和田地区,和田市', '和田市', '中国,新疆,和田,和田市', '3', '0903', '848000', 'Hetianshi', 'HTS', 'H', '79.91353', '37.11214'),
('3773', '653221', '653200', '和田县', '中国,新疆维吾尔自治区,和田地区,和田县', '和田县', '中国,新疆,和田,和田县', '3', '0903', '848000', 'Hetianxian', 'HTX', 'H', '79.82874', '37.08922'),
('3774', '653222', '653200', '墨玉县', '中国,新疆维吾尔自治区,和田地区,墨玉县', '墨玉', '中国,新疆,和田,墨玉', '3', '0903', '848100', 'Moyu', 'MY', 'M', '79.74035', '37.27248'),
('3775', '653223', '653200', '皮山县', '中国,新疆维吾尔自治区,和田地区,皮山县', '皮山', '中国,新疆,和田,皮山', '3', '0903', '845150', 'Pishan', 'PS', 'P', '78.28125', '37.62007'),
('3776', '653224', '653200', '洛浦县', '中国,新疆维吾尔自治区,和田地区,洛浦县', '洛浦', '中国,新疆,和田,洛浦', '3', '0903', '848200', 'Luopu', 'LP', 'L', '80.18536', '37.07364'),
('3777', '653225', '653200', '策勒县', '中国,新疆维吾尔自治区,和田地区,策勒县', '策勒', '中国,新疆,和田,策勒', '3', '0903', '848300', 'Cele', 'CL', 'C', '80.80999', '36.99843'),
('3778', '653226', '653200', '于田县', '中国,新疆维吾尔自治区,和田地区,于田县', '于田', '中国,新疆,和田,于田', '3', '0903', '848400', 'Yutian', 'YT', 'Y', '81.66717', '36.854'),
('3779', '653227', '653200', '民丰县', '中国,新疆维吾尔自治区,和田地区,民丰县', '民丰', '中国,新疆,和田,民丰', '3', '0903', '848500', 'Minfeng', 'MF', 'M', '82.68444', '37.06577'),
('3780', '654000', '650000', '伊犁哈萨克自治州', '中国,新疆维吾尔自治区,伊犁哈萨克自治州', '伊犁', '中国,新疆,伊犁', '2', '0999', '835000', 'Ili', 'YL', 'I', '81.317946', '43.92186'),
('3781', '654002', '654000', '伊宁市', '中国,新疆维吾尔自治区,伊犁哈萨克自治州,伊宁市', '伊宁', '中国,新疆,伊犁,伊宁', '3', '0999', '835000', 'Yining', 'YN', 'Y', '81.32932', '43.91294'),
('3782', '654003', '654000', '奎屯市', '中国,新疆维吾尔自治区,伊犁哈萨克自治州,奎屯市', '奎屯', '中国,新疆,伊犁,奎屯', '3', '0992', '833200', 'Kuitun', 'KT', 'K', '84.90228', '44.425'),
('3783', '654004', '654000', '霍尔果斯市', '中国,新疆维吾尔自治区,伊犁哈萨克自治州,霍尔果斯市', '霍尔果斯', '中国,新疆,伊犁,霍尔果斯', '3', '0999', '835221', 'Huoerguosi', 'HEGS', 'H', '80.418189', '44.205778'),
('3784', '654021', '654000', '伊宁县', '中国,新疆维吾尔自治区,伊犁哈萨克自治州,伊宁县', '伊宁', '中国,新疆,伊犁,伊宁', '3', '0999', '835100', 'Yining', 'YN', 'Y', '81.52764', '43.97863'),
('3785', '654022', '654000', '察布查尔锡伯自治县', '中国,新疆维吾尔自治区,伊犁哈萨克自治州,察布查尔锡伯自治县', '察布查尔锡伯', '中国,新疆,伊犁,察布查尔锡伯', '3', '0999', '835300', 'Chabuchaerxibo', 'CBCEXB', 'C', '81.14956', '43.84023'),
('3786', '654023', '654000', '霍城县', '中国,新疆维吾尔自治区,伊犁哈萨克自治州,霍城县', '霍城', '中国,新疆,伊犁,霍城', '3', '0999', '835200', 'Huocheng', 'HC', 'H', '80.87826', '44.0533'),
('3787', '654024', '654000', '巩留县', '中国,新疆维吾尔自治区,伊犁哈萨克自治州,巩留县', '巩留', '中国,新疆,伊犁,巩留', '3', '0999', '835400', 'Gongliu', 'GL', 'G', '82.22851', '43.48429'),
('3788', '654025', '654000', '新源县', '中国,新疆维吾尔自治区,伊犁哈萨克自治州,新源县', '新源', '中国,新疆,伊犁,新源', '3', '0999', '835800', 'Xinyuan', 'XY', 'X', '83.26095', '43.4284'),
('3789', '654026', '654000', '昭苏县', '中国,新疆维吾尔自治区,伊犁哈萨克自治州,昭苏县', '昭苏', '中国,新疆,伊犁,昭苏', '3', '0999', '835600', 'Zhaosu', 'ZS', 'Z', '81.1307', '43.15828'),
('3790', '654027', '654000', '特克斯县', '中国,新疆维吾尔自治区,伊犁哈萨克自治州,特克斯县', '特克斯', '中国,新疆,伊犁,特克斯', '3', '0999', '835500', 'Tekesi', 'TKS', 'T', '81.84005', '43.21938'),
('3791', '654028', '654000', '尼勒克县', '中国,新疆维吾尔自治区,伊犁哈萨克自治州,尼勒克县', '尼勒克', '中国,新疆,伊犁,尼勒克', '3', '0999', '835700', 'Nileke', 'NLK', 'N', '82.51184', '43.79901'),
('3792', '654200', '650000', '塔城地区', '中国,新疆维吾尔自治区,塔城地区', '塔城', '中国,新疆,塔城', '2', '0901', '834700', 'Qoqek', 'TC', 'Q', '82.985732', '46.746301'),
('3793', '654201', '654200', '塔城市', '中国,新疆维吾尔自治区,塔城地区,塔城市', '塔城', '中国,新疆,塔城,塔城', '3', '0901', '834700', 'Tacheng', 'TC', 'T', '82.97892', '46.74852'),
('3794', '654202', '654200', '乌苏市', '中国,新疆维吾尔自治区,塔城地区,乌苏市', '乌苏', '中国,新疆,塔城,乌苏', '3', '0992', '833000', 'Wusu', 'WS', 'W', '84.68258', '44.43729'),
('3795', '654221', '654200', '额敏县', '中国,新疆维吾尔自治区,塔城地区,额敏县', '额敏', '中国,新疆,塔城,额敏', '3', '0901', '834600', 'Emin', 'EM', 'E', '83.62872', '46.5284'),
('3796', '654223', '654200', '沙湾县', '中国,新疆维吾尔自治区,塔城地区,沙湾县', '沙湾', '中国,新疆,塔城,沙湾', '3', '0993', '832100', 'Shawan', 'SW', 'S', '85.61932', '44.33144'),
('3797', '654224', '654200', '托里县', '中国,新疆维吾尔自治区,塔城地区,托里县', '托里', '中国,新疆,塔城,托里', '3', '0901', '834500', 'Tuoli', 'TL', 'T', '83.60592', '45.93623'),
('3798', '654225', '654200', '裕民县', '中国,新疆维吾尔自治区,塔城地区,裕民县', '裕民', '中国,新疆,塔城,裕民', '3', '0901', '834800', 'Yumin', 'YM', 'Y', '82.99002', '46.20377'),
('3799', '654226', '654200', '和布克赛尔蒙古自治县', '中国,新疆维吾尔自治区,塔城地区,和布克赛尔蒙古自治县', '和布克赛尔', '中国,新疆,塔城,和布克赛尔', '3', '0990', '834400', 'Hebukesaier', 'HBKSE', 'H', '85.72662', '46.79362'),
('3800', '654300', '650000', '阿勒泰地区', '中国,新疆维吾尔自治区,阿勒泰地区', '阿勒泰', '中国,新疆,阿勒泰', '2', '0906', '836500', 'Altay', 'ALT', 'A', '88.13963', '47.848393'),
('3801', '654301', '654300', '阿勒泰市', '中国,新疆维吾尔自治区,阿勒泰地区,阿勒泰市', '阿勒泰', '中国,新疆,阿勒泰,阿勒泰', '3', '0906', '836500', 'Aletai', 'ALT', 'A', '88.13913', '47.8317'),
('3802', '654321', '654300', '布尔津县', '中国,新疆维吾尔自治区,阿勒泰地区,布尔津县', '布尔津', '中国,新疆,阿勒泰,布尔津', '3', '0906', '836600', 'Buerjin', 'BEJ', 'B', '86.85751', '47.70062'),
('3803', '654322', '654300', '富蕴县', '中国,新疆维吾尔自治区,阿勒泰地区,富蕴县', '富蕴', '中国,新疆,阿勒泰,富蕴', '3', '0906', '836100', 'Fuyun', 'FY', 'F', '89.52679', '46.99444'),
('3804', '654323', '654300', '福海县', '中国,新疆维吾尔自治区,阿勒泰地区,福海县', '福海', '中国,新疆,阿勒泰,福海', '3', '0906', '836400', 'Fuhai', 'FH', 'F', '87.49508', '47.11065'),
('3805', '654324', '654300', '哈巴河县', '中国,新疆维吾尔自治区,阿勒泰地区,哈巴河县', '哈巴河', '中国,新疆,阿勒泰,哈巴河', '3', '0906', '836700', 'Habahe', 'HBH', 'H', '86.42092', '48.06046'),
('3806', '654325', '654300', '青河县', '中国,新疆维吾尔自治区,阿勒泰地区,青河县', '青河', '中国,新疆,阿勒泰,青河', '3', '0906', '836200', 'Qinghe', 'QH', 'Q', '90.38305', '46.67382'),
('3807', '654326', '654300', '吉木乃县', '中国,新疆维吾尔自治区,阿勒泰地区,吉木乃县', '吉木乃', '中国,新疆,阿勒泰,吉木乃', '3', '0906', '836800', 'Jimunai', 'JMN', 'J', '85.87814', '47.43359'),
('3808', '659001', '650000', '石河子市', '中国,新疆维吾尔自治区,石河子市', '石河子', '中国,新疆,石河子', '2', '0993', '832000', 'Shihezi', 'SHZ', 'S', '86.041075', '44.305886'),
('3809', '659002', '650000', '阿拉尔市', '中国,新疆维吾尔自治区,阿拉尔市', '阿拉尔', '中国,新疆,阿拉尔', '2', '0997', '843300', 'Aral', 'ALE', 'A', '81.285884', '40.541914'),
('3810', '659003', '650000', '图木舒克市', '中国,新疆维吾尔自治区,图木舒克市', '图木舒克', '中国,新疆,图木舒克', '2', '0998', '843806', 'Tumxuk', 'TMSK', 'T', '79.077978', '39.867316'),
('3811', '659004', '650000', '五家渠市', '中国,新疆维吾尔自治区,五家渠市', '五家渠', '中国,新疆,五家渠', '2', '0994', '831300', 'Wujiaqu', 'WJQ', 'W', '87.526884', '44.167401'),
('3812', '659005', '650000', '北屯市', '中国,新疆维吾尔自治区,北屯市', '北屯', '中国,新疆,北屯', '2', '0906', '836000', 'Beitun', 'BT', 'B', '87.808456', '47.362308'),
('3813', '659006', '650000', '铁门关市', '中国,新疆维吾尔自治区,铁门关市', '铁门关', '中国,新疆,铁门关', '2', '0906', '836000', 'Tiemenguan', 'TMG', 'T', '86.194687', '41.811007'),
('3814', '659007', '650000', '双河市', '中国,新疆维吾尔自治区,双河市', '双河', '中国,新疆,双河', '2', '0909', '833408', 'Shuanghe', 'SH', 'S', '82.353656', '44.840524'),
('3815', '659008', '650000', '可克达拉市', '中国,新疆维吾尔自治区,可克达拉市', '可克达拉', '中国,新疆,可克达拉', '2', '0999', '835213', 'Kokdala', 'KKDL', 'K', '80.624235', '44.126966'),
('3816', '659009', '650000', '昆玉市', '中国,新疆维吾尔自治区,昆玉市', '昆玉', '中国,新疆,昆玉', '2', '0903', '848000', 'Kunyu', 'KY', 'K', '79.918141', '37.096411'),
('3817', '659101', '659001', '新城街道', '中国,新疆维吾尔自治区,石河子市,新城街道', '新城街道', '中国,新疆,石河子,新城街道', '3', '0993', '832000', 'Xincheng', 'XC', 'X', '86.027642', '44.29313'),
('3818', '659102', '659001', '向阳街道', '中国,新疆维吾尔自治区,石河子市,向阳街道', '向阳街道', '中国,新疆,石河子,向阳街道', '3', '0993', '832000', 'Xiangyang', 'XY', 'X', '86.054413', '44.307129'),
('3819', '659103', '659001', '红山街道', '中国,新疆维吾尔自治区,石河子市,红山街道', '红山街道', '中国,新疆,石河子,红山街道', '3', '0993', '832000', 'Hongshan', 'HS', 'H', '86.044978', '44.29856'),
('3820', '659104', '659001', '老街街道', '中国,新疆维吾尔自治区,石河子市,老街街道', '老街街道', '中国,新疆,石河子,老街街道', '3', '0993', '832000', 'Laojie', 'LJ', 'L', '86.039023', '44.316702'),
('3821', '659105', '659001', '东城街道', '中国,新疆维吾尔自治区,石河子市,东城街道', '东城街道', '中国,新疆,石河子,东城街道', '3', '0993', '832000', 'Dongcheng', 'DC', 'D', '86.09183', '44.310211'),
('3822', '659106', '659001', '北泉镇', '中国,新疆维吾尔自治区,石河子市,北泉镇', '北泉镇', '中国,新疆,石河子,北泉镇', '3', '0993', '832011', 'Beiquan', 'BQ', 'B', '86.016731', '44.333841'),
('3823', '659107', '659001', '石河子乡', '中国,新疆维吾尔自治区,石河子市,石河子乡', '石河子乡', '中国,新疆,石河子,石河子乡', '3', '0993', '832099', 'Shihezi', 'SHZ', 'S', '86.035442', '44.286537'),
('3824', '659108', '659001', '一五二团', '中国,新疆维吾尔自治区,石河子市,一五二团', '一五二团', '中国,新疆,石河子,一五二团', '3', '0993', '832099', 'Yishierwutuan', 'YSEWT', 'Y', '86.047665', '44.289252'),
('3825', '659201', '659002', '幸福路街道', '中国,新疆维吾尔自治区,阿拉尔市,幸福路街道', '阿拉尔', '中国,新疆,阿拉尔,幸福路街道', '3', '0997', '843302', 'XingFuLu', 'XFL', 'X', '81.285043', '40.544189'),
('3826', '659202', '659002', '金银川路街道', '中国,新疆维吾尔自治区,阿拉尔市,金银川路街道', '金银川路街道', '中国,新疆,阿拉尔,金银川路街道', '3', '0997', '843300', 'JinYinChuanLu', 'JYCL', 'J', '81.2758', '40.548049'),
('3827', '659203', '659002', '青松路街道', '中国,新疆维吾尔自治区,阿拉尔市,青松路街道', '青松路街道', '中国,新疆,阿拉尔,青松路街道', '3', '0997', '843300', 'QingSongLu', 'QSL', 'Q', '81.265448', '40.551269'),
('3828', '659204', '659002', '南口街道', '中国,新疆维吾尔自治区,阿拉尔市,南口街道', '南口街道', '中国,新疆,阿拉尔,南口街道', '3', '0997', '843301', 'Nankou', 'NK', 'N', '81.308342', '40.506414'),
('3829', '659205', '659002', '托喀依乡', '中国,新疆维吾尔自治区,阿拉尔市,托喀依乡', '托喀依乡', '中国,新疆,阿拉尔,托喀依乡', '3', '0997', '843300', 'Tuokayixiang', 'TKYX', 'T', '81.120055', '40.538677'),
('3830', '659206', '659002', '金银川镇', '中国,新疆维吾尔自治区,阿拉尔市,金银川镇', '阿拉尔', '中国,新疆,阿拉尔,金银川镇', '3', '0997', '843008', 'JinYinChuanZhen', 'JYCZ', 'J', '79.958753', '40.745954'),
('3831', '659301', '659003', '图木舒克市区', '中国,新疆维吾尔自治区,图木舒克市,图木舒克市区', '图木舒克市区', '中国,新疆,图木舒克,图木舒克市区', '3', '0998', '843806', 'Shiqu', 'SQ', 'S', '79.072007', '39.868673'),
('3832', '659302', '659003', '兵团四十四团', '中国,新疆维吾尔自治区,图木舒克市,兵团四十四团', '兵团四十四团', '中国,新疆,图木舒克,兵团四十四团', '3', '0998', '843806', 'SishisiTuan', 'SSST', 'S', '79.137663', '39.827301'),
('3833', '659303', '659003', '兵团四十九团', '中国,新疆维吾尔自治区,图木舒克市,兵团四十九团', '兵团四十九团', '中国,新疆,图木舒克,兵团四十九团', '3', '0998', '843806', 'SishijiuTuan', 'SSJT', 'S', '78.917058', '39.705154'),
('3834', '659304', '659003', '兵团五十团', '中国,新疆维吾尔自治区,图木舒克市,兵团五十团', '兵团五十团', '中国,新疆,图木舒克,兵团五十团', '3', '0998', '843806', 'WushiTuan', 'WST', 'W', '79.242827', '39.946008'),
('3835', '659305', '659003', '兵团五十一团', '中国,新疆维吾尔自治区,图木舒克市,兵团五十一团', '兵团五十一团', '中国,新疆,图木舒克,兵团五十一团', '3', '0998', '843806', 'WushiyiTuan', 'WSYT', 'W', '79.143243', '39.978939'),
('3836', '659306', '659003', '兵团五十二团', '中国,新疆维吾尔自治区,图木舒克市,兵团五十二团', '兵团五十二团', '中国,新疆,图木舒克,兵团五十二团', '3', '0998', '843806', 'WushierTuan', 'WSET', 'W', '79.10236', '39.879874'),
('3837', '659307', '659003', '兵团五十三团', '中国,新疆维吾尔自治区,图木舒克市,兵团五十三团', '兵团五十三团', '中国,新疆,图木舒克,兵团五十三团', '3', '0998', '843806', 'WushisanTuan', 'WSST', 'W', '79.420571', '40.030333'),
('3838', '659308', '659003', '喀拉拜勒镇', '中国,新疆维吾尔自治区,图木舒克市,喀拉拜勒镇', '喀拉拜勒', '中国,新疆,图木舒克,喀拉拜勒', '3', '0998', '843806', 'Kalabaile', 'KLBL', 'K', '78.8737', '39.68535'),
('3839', '659309', '659003', '永安坝', '中国,新疆维吾尔自治区,图木舒克市,永安坝', '永安坝', '中国,新疆,图木舒克,永安坝', '3', '0998', '843806', 'Yong\'anBa', 'YAB', 'Y', '79.021035', '39.846305'),
('3840', '659401', '659004', '城区', '中国,新疆维吾尔自治区,五家渠市,城区', '城区', '中国,新疆,五家渠,城区', '3', '0994', '831300', 'Chengqu', 'CQ', 'C', '87.53521', '44.129486'),
('3841', '659402', '659004', '一零一团', '中国,新疆维吾尔自治区,五家渠市,一零一团', '一零一团', '中国,新疆,五家渠,一零一团', '3', '0994', '831300', 'YilingyiTuan', 'YLYT', 'Y', '87.528515', '44.162252'),
('3842', '659403', '659004', '一零二团', '中国,新疆维吾尔自治区,五家渠市,一零二团', '一零二团', '中国,新疆,五家渠,一零二团', '3', '0994', '831300', 'YilingerTuan', 'YLET', 'Y', '87.648592', '44.266388'),
('3843', '659404', '659004', '一零三团', '中国,新疆维吾尔自治区,五家渠市,一零三团', '一零三团', '中国,新疆,五家渠,一零三团', '3', '0994', '831300', 'YilingsanTuan', 'YLST', 'Y', '87.528519', '44.527921'),
('3844', '659501', '659005', '新城区', '中国,新疆维吾尔自治区,北屯市,新城区', '新城', '中国,新疆,北屯,新城', '3', '0906', '836000', 'Xincheng', 'XC', 'X', '87.800338', '47.354533'),
('3845', '659502', '659005', '老城区', '中国,新疆维吾尔自治区,北屯市,老城区', '老城', '中国,新疆,北屯,老城', '3', '0906', '836000', 'Laocheng', 'LC', 'L', '87.808456', '47.362308'),
('3846', '659503', '659005', '工业园区', '中国,新疆维吾尔自治区,北屯市,工业园区', '工业园', '中国,新疆,北屯,工业园', '3', '0906', '836000', 'Gongyeyuan', 'GYY', 'G', '87.780612', '47.231226'),
('3847', '659504', '659005', '海川镇', '中国,新疆维吾尔自治区,北屯市,海川镇', '海川', '中国,新疆,北屯,海川', '3', '0906', '836000', 'Haichuan', 'HC', 'H', '87.790949', '47.343443'),
('3848', '659505', '659005', '丰庆镇', '中国,新疆维吾尔自治区,北屯市,丰庆镇', '丰庆', '中国,新疆,北屯,丰庆', '3', '0906', '836000', 'Fengqing', 'FQ', 'F', '87.992437', '47.273735'),
('3849', '659506', '659005', '锡伯渡镇', '中国,新疆维吾尔自治区,北屯市,锡伯渡镇', '锡伯渡', '中国,新疆,北屯,锡伯渡', '3', '0906', '836000', 'Xibodu', 'XBD', 'X', '88.095016', '47.260134'),
('3850', '659601', '659006', '二十九团场', '中国,新疆维吾尔自治区,铁门关市,二十九团场', '二十九团场', '中国,新疆,铁门关,二十九团场', '3', '0906', '836000', 'Ershijiutuan', 'ESJT', 'E', '85.659021', '41.819144'),
('3851', '659602', '659006', '库西经济工业园', '中国,新疆维吾尔自治区,铁门关市,库西经济工业园', '库西经济工业园', '中国,新疆,铁门关,库西经济工业园', '3', '0906', '836000', 'Kuxigongyeyuan', 'KXGYY', 'K', '85.712205', '41.854188'),
('3852', '659603', '659006', '博古其镇', '中国,新疆维吾尔自治区,铁门关市,博古其镇', '博古其镇', '中国,新疆,铁门关,博古其镇', '3', '0906', '836000', 'Boguqi', 'BGQ', 'B', '86.029663', '41.793293'),
('3853', '659604', '659006', '双丰镇', '中国,新疆维吾尔自治区,铁门关市,双丰镇', '双丰镇', '中国,新疆,铁门关,双丰镇', '3', '0906', '836000', 'Shuangfeng', 'SF', 'S', '85.501779', '41.826156'),
('3854', '659701', '659007', '八十一团', '中国,新疆维吾尔自治区,双河市,八十一团', '八十一团', '中国,新疆,双河,八十一团', '3', '0909', '833408', 'Bayituan', 'BSYT', 'B', '82.440002', '44.78414'),
('3855', '659702', '659007', '八十四团', '中国,新疆维吾尔自治区,双河市,八十四团', '八十四团', '中国,新疆,双河,八十四团', '3', '0909', '833408', 'Basituan', 'BSST', 'B', '82.06305', '44.995778'),
('3856', '659703', '659007', '八十五团', '中国,新疆维吾尔自治区,双河市,八十五团', '八十五团', '中国,新疆,双河,八十五团', '3', '0909', '833408', 'Bawutuan', 'BSWT', 'B', '82.14409', '44.830917'),
('3857', '659704', '659007', '八十六团', '中国,新疆维吾尔自治区,双河市,八十六团', '八十六团', '中国,新疆,双河,八十六团', '3', '0909', '833408', 'Baliutuan', 'BSLT', 'B', '82.193835', '44.888208'),
('3858', '659705', '659007', '八十九团', '中国,新疆维吾尔自治区,双河市,八十九团', '八十九团', '中国,新疆,双河,八十九团', '3', '0909', '833408', 'Bashijiutuan', 'BSJT', 'B', '82.44737', '44.849095'),
('3859', '659706', '659007', '九十团', '中国,新疆维吾尔自治区,双河市,九十团', '九十团', '中国,新疆,双河,九十团', '3', '0909', '833408', 'Jiushituan', 'JST', 'J', '82.595543', '44.822492'),
('3860', '659801', '659008', '63团', '中国,新疆维吾尔自治区,可克达拉市,63团', '63团', '中国,新疆,可克达拉,63团', '3', '0999', '835213', 'Liushisantuan', 'LSST', 'L', '80.544984', '43.968611'),
('3861', '659802', '659008', '64团', '中国,新疆维吾尔自治区,可克达拉市,64团', '64团', '中国,新疆,可克达拉,64团', '3', '0999', '835213', 'Liushisituan', 'LSST', 'L', '80.643848', '44.127782'),
('3862', '659803', '659008', '66团', '中国,新疆维吾尔自治区,可克达拉市,66团', '66团', '中国,新疆,可克达拉,66团', '3', '0999', '835213', 'LiushiliuTuan', 'LSLT', 'L', '81.043819', '43.943968'),
('3863', '659804', '659008', '67团', '中国,新疆维吾尔自治区,可克达拉市,67团', '67团', '中国,新疆,可克达拉,67团', '3', '0999', '835213', 'LiushiqiTuan', 'LSQT', 'L', '80.757732', '43.802029'),
('3864', '659805', '659008', '68团', '中国,新疆维吾尔自治区,可克达拉市,68团', '68团', '中国,新疆,可克达拉,68团', '3', '0999', '835213', 'LiushibaTuan', 'LSBT', 'L', '80.624235', '44.126966'),
('3865', '659901', '659009', '皮山农场', '中国,新疆维吾尔自治区,昆玉市,皮山农场', '皮山农场', '中国,新疆,昆玉,皮山农场', '3', '0903', '848000', 'Pishannongchang', 'PSNC', 'P', '77.794153', '37.226445'),
('3866', '659902', '659009', '二二四团', '中国,新疆维吾尔自治区,昆玉市,二二四团', '二二四团', '中国,新疆,昆玉,二二四团', '3', '0903', '848000', 'Erersituan', 'EEST', 'E', '79.290212', '37.211706'),
('3867', '659903', '659009', '四十七团', '中国,新疆维吾尔自治区,昆玉市,四十七团', '四十七团', '中国,新疆,昆玉,四十七团', '3', '0903', '848000', 'Sishiqituan', 'SSQT', 'S', '79.648416', '37.510671'),
('3868', '659904', '659009', '一牧场', '中国,新疆维吾尔自治区,昆玉市,一牧场', '一牧场', '中国,新疆,昆玉,一牧场', '3', '0903', '848000', 'Yimuchang', 'YMC', 'Y', '81.033128', '36.263196'),
('3869', '710000', '100000', '台湾', '中国,台湾', '台湾', '中国,台湾', '1', '', '', 'Taiwan', 'TW', 'T', '121.509062', '25.044332'),
('3870', '710100', '710000', '台北市', '中国,台湾,台北市', '台北', '中国,台湾,台北', '2', '02', '1', 'Taipei', 'TB', 'T', '121.565170', '25.037798'),
('3871', '710101', '710100', '松山区', '中国,台湾,台北市,松山区', '松山', '中国,台湾,台北,松山', '3', '02', '105', 'Songshan', 'SS', 'S', '121.577206', '25.049698'),
('3872', '710102', '710100', '信义区', '中国,台湾,台北市,信义区', '信义', '中国,台湾,台北,信义', '3', '02', '110', 'Xinyi', 'XY', 'X', '121.751381', '25.129407'),
('3873', '710103', '710100', '大安区', '中国,台湾,台北市,大安区', '大安', '中国,台湾,台北,大安', '3', '02', '106', 'Da\'an', 'DA', 'D', '121.534648', '25.026406'),
('3874', '710104', '710100', '中山区', '中国,台湾,台北市,中山区', '中山', '中国,台湾,台北,中山', '3', '02', '104', 'Zhongshan', 'ZS', 'Z', '121.533468', '25.064361'),
('3875', '710105', '710100', '中正区', '中国,台湾,台北市,中正区', '中正', '中国,台湾,台北,中正', '3', '02', '100', 'Zhongzheng', 'ZZ', 'Z', '121.518267', '25.032361'),
('3876', '710106', '710100', '大同区', '中国,台湾,台北市,大同区', '大同', '中国,台湾,台北,大同', '3', '02', '103', 'Datong', 'DT', 'D', '121.515514', '25.065986'),
('3877', '710107', '710100', '万华区', '中国,台湾,台北市,万华区', '万华', '中国,台湾,台北,万华', '3', '02', '108', 'Wanhua', 'WH', 'W', '121.499332', '25.031933'),
('3878', '710108', '710100', '文山区', '中国,台湾,台北市,文山区', '文山', '中国,台湾,台北,文山', '3', '02', '116', 'Wenshan', 'WS', 'W', '121.570458', '24.989786'),
('3879', '710109', '710100', '南港区', '中国,台湾,台北市,南港区', '南港', '中国,台湾,台北,南港', '3', '02', '115', 'Nangang', 'NG', 'N', '121.606858', '25.054656'),
('3880', '710110', '710100', '内湖区', '中国,台湾,台北市,内湖区', '内湖', '中国,台湾,台北,内湖', '3', '02', '114', 'Nahu', 'NH', 'N', '121.588998', '25.069664'),
('3881', '710111', '710100', '士林区', '中国,台湾,台北市,士林区', '士林', '中国,台湾,台北,士林', '3', '02', '111', 'Shilin', 'SL', 'S', '121.519874', '25.092822'),
('3882', '710112', '710100', '北投区', '中国,台湾,台北市,北投区', '北投', '中国,台湾,台北,北投', '3', '02', '112', 'Beitou', 'BT', 'B', '121.501379', '25.132419'),
('3883', '710200', '710000', '高雄市', '中国,台湾,高雄市', '高雄', '中国,台湾,高雄', '2', '07', '8', 'Kaohsiung', 'GX', 'K', '120.311922', '22.620856'),
('3884', '710201', '710200', '盐埕区', '中国,台湾,高雄市,盐埕区', '盐埕', '中国,台湾,高雄,盐埕', '3', '07', '803', 'Yancheng', 'YC', 'Y', '120.286795', '22.624666'),
('3885', '710202', '710200', '鼓山区', '中国,台湾,高雄市,鼓山区', '鼓山', '中国,台湾,高雄,鼓山', '3', '07', '804', 'Gushan', 'GS', 'G', '120.281154', '22.636797'),
('3886', '710203', '710200', '左营区', '中国,台湾,高雄市,左营区', '左营', '中国,台湾,高雄,左营', '3', '07', '813', 'Zuoying', 'ZY', 'Z', '120.294958', '22.690124'),
('3887', '710204', '710200', '楠梓区', '中国,台湾,高雄市,楠梓区', '楠梓', '中国,台湾,高雄,楠梓', '3', '07', '811', 'Nanzi', 'NZ', 'N', '120.326314', '22.728401'),
('3888', '710205', '710200', '三民区', '中国,台湾,高雄市,三民区', '三民', '中国,台湾,高雄,三民', '3', '07', '807', 'Sanmin', 'SM', 'S', '120.299622', '22.647695'),
('3889', '710206', '710200', '新兴区', '中国,台湾,高雄市,新兴区', '新兴', '中国,台湾,高雄,新兴', '3', '07', '800', 'Xinxing', 'XX', 'X', '120.309535', '22.631147'),
('3890', '710207', '710200', '前金区', '中国,台湾,高雄市,前金区', '前金', '中国,台湾,高雄,前金', '3', '07', '801', 'Qianjin', 'QJ', 'Q', '120.294159', '22.627421'),
('3891', '710208', '710200', '苓雅区', '中国,台湾,高雄市,苓雅区', '苓雅', '中国,台湾,高雄,苓雅', '3', '07', '802', 'Lingya', 'LY', 'L', '120.312347', '22.621770'),
('3892', '710209', '710200', '前镇区', '中国,台湾,高雄市,前镇区', '前镇', '中国,台湾,高雄,前镇', '3', '07', '806', 'Qianzhen', 'QZ', 'Q', '120.318583', '22.586425'),
('3893', '710210', '710200', '旗津区', '中国,台湾,高雄市,旗津区', '旗津', '中国,台湾,高雄,旗津', '3', '07', '805', 'Qijin', 'QJ', 'Q', '120.284429', '22.590565'),
('3894', '710211', '710200', '小港区', '中国,台湾,高雄市,小港区', '小港', '中国,台湾,高雄,小港', '3', '07', '812', 'Xiaogang', 'XG', 'X', '120.337970', '22.565354'),
('3895', '710212', '710200', '凤山区', '中国,台湾,高雄市,凤山区', '凤山', '中国,台湾,高雄,凤山', '3', '07', '830', 'Fengshan', 'FS', 'F', '120.356892', '22.626945'),
('3896', '710213', '710200', '林园区', '中国,台湾,高雄市,林园区', '林园', '中国,台湾,高雄,林园', '3', '07', '832', 'Linyuan', 'LY', 'L', '120.395977', '22.501490'),
('3897', '710214', '710200', '大寮区', '中国,台湾,高雄市,大寮区', '大寮', '中国,台湾,高雄,大寮', '3', '07', '831', 'Daliao', 'DL', 'D', '120.395422', '22.605386'),
('3898', '710215', '710200', '大树区', '中国,台湾,高雄市,大树区', '大树', '中国,台湾,高雄,大树', '3', '07', '840', 'Dashu', 'DS', 'D', '120.433095', '22.693394'),
('3899', '710216', '710200', '大社区', '中国,台湾,高雄市,大社区', '大社', '中国,台湾,高雄,大社', '3', '07', '815', 'Dashe', 'DS', 'D', '120.346635', '22.729966'),
('3900', '710217', '710200', '仁武区', '中国,台湾,高雄市,仁武区', '仁武', '中国,台湾,高雄,仁武', '3', '07', '814', 'Renwu', 'RW', 'R', '120.347779', '22.701901'),
('3901', '710218', '710200', '鸟松区', '中国,台湾,高雄市,鸟松区', '鸟松', '中国,台湾,高雄,鸟松', '3', '07', '833', 'Niaosong', 'NS', 'N', '120.364402', '22.659340'),
('3902', '710219', '710200', '冈山区', '中国,台湾,高雄市,冈山区', '冈山', '中国,台湾,高雄,冈山', '3', '07', '820', 'Gangshan', 'GS', 'G', '120.295820', '22.796762'),
('3903', '710220', '710200', '桥头区', '中国,台湾,高雄市,桥头区', '桥头', '中国,台湾,高雄,桥头', '3', '07', '825', 'Qiaotou', 'QT', 'Q', '120.305741', '22.757501'),
('3904', '710221', '710200', '燕巢区', '中国,台湾,高雄市,燕巢区', '燕巢', '中国,台湾,高雄,燕巢', '3', '07', '824', 'Yanchao', 'YC', 'Y', '120.361956', '22.793370'),
('3905', '710222', '710200', '田寮区', '中国,台湾,高雄市,田寮区', '田寮', '中国,台湾,高雄,田寮', '3', '07', '823', 'Tianliao', 'TL', 'T', '120.359636', '22.869307'),
('3906', '710223', '710200', '阿莲区', '中国,台湾,高雄市,阿莲区', '阿莲', '中国,台湾,高雄,阿莲', '3', '07', '822', 'Alian', 'AL', 'A', '120.327036', '22.883703'),
('3907', '710224', '710200', '路竹区', '中国,台湾,高雄市,路竹区', '路竹', '中国,台湾,高雄,路竹', '3', '07', '821', 'Luzhu', 'LZ', 'L', '120.261828', '22.856851'),
('3908', '710225', '710200', '湖内区', '中国,台湾,高雄市,湖内区', '湖内', '中国,台湾,高雄,湖内', '3', '07', '829', 'Huna', 'HN', 'H', '120.211530', '22.908188'),
('3909', '710226', '710200', '茄萣区', '中国,台湾,高雄市,茄萣区', '茄萣', '中国,台湾,高雄,茄萣', '3', '07', '852', 'Qieding', 'QD', 'Q', '120.182815', '22.906556'),
('3910', '710227', '710200', '永安区', '中国,台湾,高雄市,永安区', '永安', '中国,台湾,高雄,永安', '3', '07', '828', 'Yong\'an', 'YA', 'Y', '120.225308', '22.818580'),
('3911', '710228', '710200', '弥陀区', '中国,台湾,高雄市,弥陀区', '弥陀', '中国,台湾,高雄,弥陀', '3', '07', '827', 'Mituo', 'MT', 'M', '120.247344', '22.782879'),
('3912', '710229', '710200', '梓官区', '中国,台湾,高雄市,梓官区', '梓官', '中国,台湾,高雄,梓官', '3', '07', '826', 'Ziguan', 'ZG', 'Z', '120.267322', '22.760475'),
('3913', '710230', '710200', '旗山区', '中国,台湾,高雄市,旗山区', '旗山', '中国,台湾,高雄,旗山', '3', '07', '842', 'Qishan', 'QS', 'Q', '120.483550', '22.888491'),
('3914', '710231', '710200', '美浓区', '中国,台湾,高雄市,美浓区', '美浓', '中国,台湾,高雄,美浓', '3', '07', '843', 'Meinong', 'MN', 'M', '120.541530', '22.897880'),
('3915', '710232', '710200', '六龟区', '中国,台湾,高雄市,六龟区', '六龟', '中国,台湾,高雄,六龟', '3', '07', '844', 'Liugui', 'LG', 'L', '120.633418', '22.997914'),
('3916', '710233', '710200', '甲仙区', '中国,台湾,高雄市,甲仙区', '甲仙', '中国,台湾,高雄,甲仙', '3', '07', '847', 'Jiaxian', 'JX', 'J', '120.591185', '23.084688'),
('3917', '710234', '710200', '杉林区', '中国,台湾,高雄市,杉林区', '杉林', '中国,台湾,高雄,杉林', '3', '07', '846', 'Shanlin', 'SL', 'S', '120.538980', '22.970712'),
('3918', '710235', '710200', '内门区', '中国,台湾,高雄市,内门区', '内门', '中国,台湾,高雄,内门', '3', '07', '845', 'Namen', 'NM', 'N', '120.462351', '22.943437'),
('3919', '710236', '710200', '茂林区', '中国,台湾,高雄市,茂林区', '茂林', '中国,台湾,高雄,茂林', '3', '07', '851', 'Maolin', 'ML', 'M', '120.663217', '22.886248'),
('3920', '710237', '710200', '桃源区', '中国,台湾,高雄市,桃源区', '桃源', '中国,台湾,高雄,桃源', '3', '07', '848', 'Taoyuan', 'TY', 'T', '120.760049', '23.159137'),
('3921', '710238', '710200', '那玛夏区', '中国,台湾,高雄市,那玛夏区', '那玛夏', '中国,台湾,高雄,那玛夏', '3', '07', '849', 'Namaxia', 'NMX', 'N', '120.692799', '23.216964'),
('3922', '710300', '710000', '基隆市', '中国,台湾,基隆市', '基隆', '中国,台湾,基隆', '2', '02', '2', 'Keelung', 'JL', 'K', '121.746248', '25.130741'),
('3923', '710301', '710300', '中正区', '中国,台湾,基隆市,中正区', '中正', '中国,台湾,基隆,中正', '3', '02', '202', 'Zhongzheng', 'ZZ', 'Z', '121.518267', '25.032361'),
('3924', '710302', '710300', '七堵区', '中国,台湾,基隆市,七堵区', '七堵', '中国,台湾,基隆,七堵', '3', '02', '206', 'Qidu', 'QD', 'Q', '121.713032', '25.095739'),
('3925', '710303', '710300', '暖暖区', '中国,台湾,基隆市,暖暖区', '暖暖', '中国,台湾,基隆,暖暖', '3', '02', '205', 'Nuannuan', 'NN', 'N', '121.736102', '25.099777'),
('3926', '710304', '710300', '仁爱区', '中国,台湾,基隆市,仁爱区', '仁爱', '中国,台湾,基隆,仁爱', '3', '02', '200', 'Renai', 'RA', 'R', '121.740940', '25.127526'),
('3927', '710305', '710300', '中山区', '中国,台湾,基隆市,中山区', '中山', '中国,台湾,基隆,中山', '3', '02', '203', 'Zhongshan', 'ZS', 'Z', '121.739132', '25.133991'),
('3928', '710306', '710300', '安乐区', '中国,台湾,基隆市,安乐区', '安乐', '中国,台湾,基隆,安乐', '3', '02', '204', 'Anle', 'AL', 'A', '121.723203', '25.120910'),
('3929', '710307', '710300', '信义区', '中国,台湾,基隆市,信义区', '信义', '中国,台湾,基隆,信义', '3', '02', '201', 'Xinyi', 'XY', 'X', '121.751381', '25.129407'),
('3930', '710400', '710000', '台中市', '中国,台湾,台中市', '台中', '中国,台湾,台中', '2', '04', '4', 'Taichung', 'TZ', 'T', '120.679040', '24.138620'),
('3931', '710401', '710400', '中区', '中国,台湾,台中市,中区', '中区', '中国,台湾,台中,中区', '3', '04', '400', 'Zhongqu', 'ZQ', 'Z', '120.679510', '24.143830'),
('3932', '710402', '710400', '东区', '中国,台湾,台中市,东区', '东区', '中国,台湾,台中,东区', '3', '04', '401', 'Dongqu', 'DQ', 'D', '120.704', '24.13662'),
('3933', '710403', '710400', '南区', '中国,台湾,台中市,南区', '南区', '中国,台湾,台中,南区', '3', '04', '402', 'Nanqu', 'NQ', 'N', '120.188648', '22.960944'),
('3934', '710404', '710400', '西区', '中国,台湾,台中市,西区', '西区', '中国,台湾,台中,西区', '3', '04', '403', 'Xiqu', 'XQ', 'X', '120.67104', '24.14138'),
('3935', '710405', '710400', '北区', '中国,台湾,台中市,北区', '北区', '中国,台湾,台中,北区', '3', '04', '404', 'Beiqu', 'BQ', 'B', '120.682410', '24.166040'),
('3936', '710406', '710400', '西屯区', '中国,台湾,台中市,西屯区', '西屯', '中国,台湾,台中,西屯', '3', '04', '407', 'Xitun', 'XT', 'X', '120.639820', '24.181340'),
('3937', '710407', '710400', '南屯区', '中国,台湾,台中市,南屯区', '南屯', '中国,台湾,台中,南屯', '3', '04', '408', 'Nantun', 'NT', 'N', '120.643080', '24.138270'),
('3938', '710408', '710400', '北屯区', '中国,台湾,台中市,北屯区', '北屯', '中国,台湾,台中,北屯', '3', '04', '406', 'Beitun', 'BT', 'B', '120.686250', '24.182220'),
('3939', '710409', '710400', '丰原区', '中国,台湾,台中市,丰原区', '丰原', '中国,台湾,台中,丰原', '3', '04', '420', 'Fengyuan', 'FY', 'F', '120.718460', '24.242190'),
('3940', '710410', '710400', '东势区', '中国,台湾,台中市,东势区', '东势', '中国,台湾,台中,东势', '3', '04', '423', 'Dongshi', 'DS', 'D', '120.827770', '24.258610'),
('3941', '710411', '710400', '大甲区', '中国,台湾,台中市,大甲区', '大甲', '中国,台湾,台中,大甲', '3', '04', '437', 'Dajia', 'DJ', 'D', '120.622390', '24.348920'),
('3942', '710412', '710400', '清水区', '中国,台湾,台中市,清水区', '清水', '中国,台湾,台中,清水', '3', '04', '436', 'Qingshui', 'QS', 'Q', '120.559780', '24.268650'),
('3943', '710413', '710400', '沙鹿区', '中国,台湾,台中市,沙鹿区', '沙鹿', '中国,台湾,台中,沙鹿', '3', '04', '433', 'Shalu', 'SL', 'S', '120.565700', '24.233480'),
('3944', '710414', '710400', '梧栖区', '中国,台湾,台中市,梧栖区', '梧栖', '中国,台湾,台中,梧栖', '3', '04', '435', 'Wuqi', 'WQ', 'W', '120.531520', '24.254960'),
('3945', '710415', '710400', '后里区', '中国,台湾,台中市,后里区', '后里', '中国,台湾,台中,后里', '3', '04', '421', 'Houli', 'HL', 'H', '120.710710', '24.304910'),
('3946', '710416', '710400', '神冈区', '中国,台湾,台中市,神冈区', '神冈', '中国,台湾,台中,神冈', '3', '04', '429', 'Shengang', 'SG', 'S', '120.661550', '24.257770'),
('3947', '710417', '710400', '潭子区', '中国,台湾,台中市,潭子区', '潭子', '中国,台湾,台中,潭子', '3', '04', '427', 'Tanzi', 'TZ', 'T', '120.705160', '24.209530'),
('3948', '710418', '710400', '大雅区', '中国,台湾,台中市,大雅区', '大雅', '中国,台湾,台中,大雅', '3', '04', '428', 'Daya', 'DY', 'D', '120.647780', '24.229230'),
('3949', '710419', '710400', '新社区', '中国,台湾,台中市,新社区', '新社', '中国,台湾,台中,新社', '3', '04', '426', 'Xinshe', 'XS', 'X', '120.809500', '24.234140'),
('3950', '710420', '710400', '石冈区', '中国,台湾,台中市,石冈区', '石冈', '中国,台湾,台中,石冈', '3', '04', '422', 'Shigang', 'SG', 'S', '120.780410', '24.274980'),
('3951', '710421', '710400', '外埔区', '中国,台湾,台中市,外埔区', '外埔', '中国,台湾,台中,外埔', '3', '04', '438', 'Waipu', 'WP', 'W', '120.654370', '24.332010'),
('3952', '710422', '710400', '大安区', '中国,台湾,台中市,大安区', '大安', '中国,台湾,台中,大安', '3', '04', '439', 'Da\'an', 'DA', 'D', '120.58652', '24.34607'),
('3953', '710423', '710400', '乌日区', '中国,台湾,台中市,乌日区', '乌日', '中国,台湾,台中,乌日', '3', '04', '414', 'Wuri', 'WR', 'W', '120.623810', '24.104500'),
('3954', '710424', '710400', '大肚区', '中国,台湾,台中市,大肚区', '大肚', '中国,台湾,台中,大肚', '3', '04', '432', 'Dadu', 'DD', 'D', '120.540960', '24.153660'),
('3955', '710425', '710400', '龙井区', '中国,台湾,台中市,龙井区', '龙井', '中国,台湾,台中,龙井', '3', '04', '434', 'Longjing', 'LJ', 'L', '120.545940', '24.192710'),
('3956', '710426', '710400', '雾峰区', '中国,台湾,台中市,雾峰区', '雾峰', '中国,台湾,台中,雾峰', '3', '04', '413', 'Wufeng', 'WF', 'W', '120.700200', '24.061520'),
('3957', '710427', '710400', '太平区', '中国,台湾,台中市,太平区', '太平', '中国,台湾,台中,太平', '3', '04', '411', 'Taiping', 'TP', 'T', '120.718523', '24.126472'),
('3958', '710428', '710400', '大里区', '中国,台湾,台中市,大里区', '大里', '中国,台湾,台中,大里', '3', '04', '412', 'Dali', 'DL', 'D', '120.677860', '24.099390'),
('3959', '710429', '710400', '和平区', '中国,台湾,台中市,和平区', '和平', '中国,台湾,台中,和平', '3', '04', '424', 'Heping', 'HP', 'H', '120.88349', '24.17477'),
('3960', '710500', '710000', '台南市', '中国,台湾,台南市', '台南', '中国,台湾,台南', '2', '06', '7', 'Tainan', 'TN', 'T', '120.279363', '23.172478'),
('3961', '710501', '710500', '东区', '中国,台湾,台南市,东区', '东区', '中国,台湾,台南,东区', '3', '06', '701', 'Dongqu', 'DQ', 'D', '120.224198', '22.980072'),
('3962', '710502', '710500', '南区', '中国,台湾,台南市,南区', '南区', '中国,台湾,台南,南区', '3', '06', '702', 'Nanqu', 'NQ', 'N', '120.188648', '22.960944'),
('3963', '710504', '710500', '北区', '中国,台湾,台南市,北区', '北区', '中国,台湾,台南,北区', '3', '06', '704', 'Beiqu', 'BQ', 'B', '120.682410', '24.166040'),
('3964', '710506', '710500', '安南区', '中国,台湾,台南市,安南区', '安南', '中国,台湾,台南,安南', '3', '06', '709', 'Annan', 'AN', 'A', '120.184617', '23.047230'),
('3965', '710507', '710500', '安平区', '中国,台湾,台南市,安平区', '安平', '中国,台湾,台南,安平', '3', '06', '708', 'Anping', 'AP', 'A', '120.166810', '23.000763'),
('3966', '710508', '710500', '中西区', '中国,台湾,台南市,中西区', '中西', '中国,台湾,台南,中西', '3', '06', '700', 'Zhongxi', 'ZX', 'Z', '120.205957', '22.992152'),
('3967', '710509', '710500', '新营区', '中国,台湾,台南市,新营区', '新营', '中国,台湾,台南,新营', '3', '06', '730', 'Xinying', 'XY', 'X', '120.316698', '23.310274'),
('3968', '710510', '710500', '盐水区', '中国,台湾,台南市,盐水区', '盐水', '中国,台湾,台南,盐水', '3', '06', '737', 'Yanshui', 'YS', 'Y', '120.266398', '23.319828'),
('3969', '710511', '710500', '白河区', '中国,台湾,台南市,白河区', '白河', '中国,台湾,台南,白河', '3', '06', '732', 'Baihe', 'BH', 'B', '120.415810', '23.351221'),
('3970', '710512', '710500', '柳营区', '中国,台湾,台南市,柳营区', '柳营', '中国,台湾,台南,柳营', '3', '06', '736', 'Liuying', 'LY', 'L', '120.311286', '23.278133'),
('3971', '710513', '710500', '后壁区', '中国,台湾,台南市,后壁区', '后壁', '中国,台湾,台南,后壁', '3', '06', '731', 'Houbi', 'HB', 'H', '120.362726', '23.366721'),
('3972', '710514', '710500', '东山区', '中国,台湾,台南市,东山区', '东山', '中国,台湾,台南,东山', '3', '06', '733', 'Dongshan', 'DS', 'D', '120.403984', '23.326092'),
('3973', '710515', '710500', '麻豆区', '中国,台湾,台南市,麻豆区', '麻豆', '中国,台湾,台南,麻豆', '3', '06', '721', 'Madou', 'MD', 'M', '120.248179', '23.181680'),
('3974', '710516', '710500', '下营区', '中国,台湾,台南市,下营区', '下营', '中国,台湾,台南,下营', '3', '06', '735', 'Xiaying', 'XY', 'X', '120.264484', '23.235413'),
('3975', '710517', '710500', '六甲区', '中国,台湾,台南市,六甲区', '六甲', '中国,台湾,台南,六甲', '3', '06', '734', 'Liujia', 'LJ', 'L', '120.347600', '23.231931'),
('3976', '710518', '710500', '官田区', '中国,台湾,台南市,官田区', '官田', '中国,台湾,台南,官田', '3', '06', '720', 'Guantian', 'GT', 'G', '120.314374', '23.194652'),
('3977', '710519', '710500', '大内区', '中国,台湾,台南市,大内区', '大内', '中国,台湾,台南,大内', '3', '06', '742', 'Dana', 'DN', 'D', '120.348853', '23.119460'),
('3978', '710520', '710500', '佳里区', '中国,台湾,台南市,佳里区', '佳里', '中国,台湾,台南,佳里', '3', '06', '722', 'Jiali', 'JL', 'J', '120.177211', '23.165121'),
('3979', '710521', '710500', '学甲区', '中国,台湾,台南市,学甲区', '学甲', '中国,台湾,台南,学甲', '3', '06', '726', 'Xuejia', 'XJ', 'X', '120.180255', '23.232348'),
('3980', '710522', '710500', '西港区', '中国,台湾,台南市,西港区', '西港', '中国,台湾,台南,西港', '3', '06', '723', 'Xigang', 'XG', 'X', '120.203618', '23.123057'),
('3981', '710523', '710500', '七股区', '中国,台湾,台南市,七股区', '七股', '中国,台湾,台南,七股', '3', '06', '724', 'Qigu', 'QG', 'Q', '120.140003', '23.140545'),
('3982', '710524', '710500', '将军区', '中国,台湾,台南市,将军区', '将军', '中国,台湾,台南,将军', '3', '06', '725', 'Jiangjun', 'JJ', 'J', '120.156871', '23.199543'),
('3983', '710525', '710500', '北门区', '中国,台湾,台南市,北门区', '北门', '中国,台湾,台南,北门', '3', '06', '727', 'Beimen', 'BM', 'B', '120.125821', '23.267148'),
('3984', '710526', '710500', '新化区', '中国,台湾,台南市,新化区', '新化', '中国,台湾,台南,新化', '3', '06', '712', 'Xinhua', 'XH', 'X', '120.310807', '23.038533'),
('3985', '710527', '710500', '善化区', '中国,台湾,台南市,善化区', '善化', '中国,台湾,台南,善化', '3', '06', '741', 'Shanhua', 'SH', 'S', '120.296895', '23.132261'),
('3986', '710528', '710500', '新市区', '中国,台湾,台南市,新市区', '新市', '中国,台湾,台南,新市', '3', '06', '744', 'Xinshi', 'XS', 'X', '120.295138', '23.07897'),
('3987', '710529', '710500', '安定区', '中国,台湾,台南市,安定区', '安定', '中国,台湾,台南,安定', '3', '06', '745', 'Anding', 'AD', 'A', '120.237083', '23.121498'),
('3988', '710530', '710500', '山上区', '中国,台湾,台南市,山上区', '山上', '中国,台湾,台南,山上', '3', '06', '743', 'Shanshang', 'SS', 'S', '120.352908', '23.103223'),
('3989', '710531', '710500', '玉井区', '中国,台湾,台南市,玉井区', '玉井', '中国,台湾,台南,玉井', '3', '06', '714', 'Yujing', 'YJ', 'Y', '120.460110', '23.123859'),
('3990', '710532', '710500', '楠西区', '中国,台湾,台南市,楠西区', '楠西', '中国,台湾,台南,楠西', '3', '06', '715', 'Nanxi', 'NX', 'N', '120.485396', '23.173454'),
('3991', '710533', '710500', '南化区', '中国,台湾,台南市,南化区', '南化', '中国,台湾,台南,南化', '3', '06', '716', 'Nanhua', 'NH', 'N', '120.477116', '23.042614'),
('3992', '710534', '710500', '左镇区', '中国,台湾,台南市,左镇区', '左镇', '中国,台湾,台南,左镇', '3', '06', '713', 'Zuozhen', 'ZZ', 'Z', '120.407309', '23.057955'),
('3993', '710535', '710500', '仁德区', '中国,台湾,台南市,仁德区', '仁德', '中国,台湾,台南,仁德', '3', '06', '717', 'Rende', 'RD', 'R', '120.251520', '22.972212'),
('3994', '710536', '710500', '归仁区', '中国,台湾,台南市,归仁区', '归仁', '中国,台湾,台南,归仁', '3', '06', '711', 'Guiren', 'GR', 'G', '120.293791', '22.967081'),
('3995', '710537', '710500', '关庙区', '中国,台湾,台南市,关庙区', '关庙', '中国,台湾,台南,关庙', '3', '06', '718', 'Guanmiao', 'GM', 'G', '120.327689', '22.962949'),
('3996', '710538', '710500', '龙崎区', '中国,台湾,台南市,龙崎区', '龙崎', '中国,台湾,台南,龙崎', '3', '06', '719', 'Longqi', 'LQ', 'L', '120.360824', '22.965681'),
('3997', '710539', '710500', '永康区', '中国,台湾,台南市,永康区', '永康', '中国,台湾,台南,永康', '3', '06', '710', 'Yongkang', 'YK', 'Y', '120.257069', '23.026061'),
('3998', '710600', '710000', '新竹市', '中国,台湾,新竹市', '新竹', '中国,台湾,新竹', '2', '03', '3', 'Hsinchu', 'XZ', 'H', '120.968798', '24.806738'),
('3999', '710601', '710600', '东区', '中国,台湾,新竹市,东区', '东区', '中国,台湾,新竹,东区', '3', '03', '300', 'Dongqu', 'DQ', 'D', '120.970239', '24.801337'),
('4000', '710602', '710600', '北区', '中国,台湾,新竹市,北区', '北区', '中国,台湾,新竹,北区', '3', '03', '', 'Beiqu', 'BQ', 'B', '120.682410', '24.166040'),
('4001', '710603', '710600', '香山区', '中国,台湾,新竹市,香山区', '香山', '中国,台湾,新竹,香山', '3', '03', '', 'Xiangshan', 'XS', 'X', '120.956679', '24.768933'),
('4002', '710700', '710000', '嘉义市', '中国,台湾,嘉义市', '嘉义', '中国,台湾,嘉义', '2', '05', '6', 'Chiayi', 'JY', 'C', '120.452538', '23.481568'),
('4003', '710701', '710700', '东区', '中国,台湾,嘉义市,东区', '东区', '中国,台湾,嘉义,东区', '3', '05', '600', 'Dongqu', 'DQ', 'D', '120.458009', '23.486213'),
('4004', '710702', '710700', '西区', '中国,台湾,嘉义市,西区', '西区', '中国,台湾,嘉义,西区', '3', '05', '600', 'Xiqu', 'XQ', 'X', '120.437493', '23.473029'),
('4005', '710800', '710000', '新北市', '中国,台湾,新北市', '新北', '中国,台湾,新北', '2', '02', '2', 'New Taipei', 'XB', 'N', '121.465746', '25.012366'),
('4006', '710801', '710800', '板桥区', '中国,台湾,新北市,板桥区', '板桥', '中国,台湾,新北,板桥', '3', '02', '220', 'Banqiao', 'BQ', 'B', '121.459084', '25.009578'),
('4007', '710802', '710800', '三重区', '中国,台湾,新北市,三重区', '三重', '中国,台湾,新北,三重', '3', '02', '241', 'Sanzhong', 'SZ', 'S', '121.488102', '25.061486'),
('4008', '710803', '710800', '中和区', '中国,台湾,新北市,中和区', '中和', '中国,台湾,新北,中和', '3', '02', '235', 'Zhonghe', 'ZH', 'Z', '121.498980', '24.999397'),
('4009', '710804', '710800', '永和区', '中国,台湾,新北市,永和区', '永和', '中国,台湾,新北,永和', '3', '02', '234', 'Yonghe', 'YH', 'Y', '121.513660', '25.007802'),
('4010', '710805', '710800', '新庄区', '中国,台湾,新北市,新庄区', '新庄', '中国,台湾,新北,新庄', '3', '02', '242', 'Xinzhuang', 'XZ', 'X', '121.450413', '25.035947'),
('4011', '710806', '710800', '新店区', '中国,台湾,新北市,新店区', '新店', '中国,台湾,新北,新店', '3', '02', '231', 'Xindian', 'XD', 'X', '121.541750', '24.967558'),
('4012', '710807', '710800', '树林区', '中国,台湾,新北市,树林区', '树林', '中国,台湾,新北,树林', '3', '02', '238', 'Shulin', 'SL', 'S', '121.420533', '24.990706'),
('4013', '710808', '710800', '莺歌区', '中国,台湾,新北市,莺歌区', '莺歌', '中国,台湾,新北,莺歌', '3', '02', '239', 'Yingge', 'YG', 'Y', '121.354573', '24.955413'),
('4014', '710809', '710800', '三峡区', '中国,台湾,新北市,三峡区', '三峡', '中国,台湾,新北,三峡', '3', '02', '237', 'Sanxia', 'SX', 'S', '121.368905', '24.934339'),
('4015', '710810', '710800', '淡水区', '中国,台湾,新北市,淡水区', '淡水', '中国,台湾,新北,淡水', '3', '02', '251', 'Danshui', 'DS', 'D', '121.440915', '25.169452'),
('4016', '710811', '710800', '汐止区', '中国,台湾,新北市,汐止区', '汐止', '中国,台湾,新北,汐止', '3', '02', '221', 'Xizhi', 'XZ', 'X', '121.629470', '25.062999'),
('4017', '710812', '710800', '瑞芳区', '中国,台湾,新北市,瑞芳区', '瑞芳', '中国,台湾,新北,瑞芳', '3', '02', '224', 'Ruifang', 'RF', 'R', '121.810061', '25.108895'),
('4018', '710813', '710800', '土城区', '中国,台湾,新北市,土城区', '土城', '中国,台湾,新北,土城', '3', '02', '236', 'Tucheng', 'TC', 'T', '121.443348', '24.972201'),
('4019', '710814', '710800', '芦洲区', '中国,台湾,新北市,芦洲区', '芦洲', '中国,台湾,新北,芦洲', '3', '02', '247', 'Luzhou', 'LZ', 'L', '121.473700', '25.084923'),
('4020', '710815', '710800', '五股区', '中国,台湾,新北市,五股区', '五股', '中国,台湾,新北,五股', '3', '02', '248', 'Wugu', 'WG', 'W', '121.438156', '25.082743'),
('4021', '710816', '710800', '泰山区', '中国,台湾,新北市,泰山区', '泰山', '中国,台湾,新北,泰山', '3', '02', '243', 'Taishan', 'TS', 'T', '121.430811', '25.058864'),
('4022', '710817', '710800', '林口区', '中国,台湾,新北市,林口区', '林口', '中国,台湾,新北,林口', '3', '02', '244', 'Linkou', 'LK', 'L', '121.391602', '25.077531'),
('4023', '710818', '710800', '深坑区', '中国,台湾,新北市,深坑区', '深坑', '中国,台湾,新北,深坑', '3', '02', '222', 'Shenkeng', 'SK', 'S', '121.615670', '25.002329'),
('4024', '710819', '710800', '石碇区', '中国,台湾,新北市,石碇区', '石碇', '中国,台湾,新北,石碇', '3', '02', '223', 'Shiding', 'SD', 'S', '121.658567', '24.991679'),
('4025', '710820', '710800', '坪林区', '中国,台湾,新北市,坪林区', '坪林', '中国,台湾,新北,坪林', '3', '02', '232', 'Pinglin', 'PL', 'P', '121.711185', '24.937388'),
('4026', '710821', '710800', '三芝区', '中国,台湾,新北市,三芝区', '三芝', '中国,台湾,新北,三芝', '3', '02', '252', 'Sanzhi', 'SZ', 'S', '121.500866', '25.258047'),
('4027', '710822', '710800', '石门区', '中国,台湾,新北市,石门区', '石门', '中国,台湾,新北,石门', '3', '02', '253', 'Shimen', 'SM', 'S', '121.568491', '25.290412'),
('4028', '710823', '710800', '八里区', '中国,台湾,新北市,八里区', '八里', '中国,台湾,新北,八里', '3', '02', '249', 'Bali', 'BL', 'B', '121.398227', '25.146680'),
('4029', '710824', '710800', '平溪区', '中国,台湾,新北市,平溪区', '平溪', '中国,台湾,新北,平溪', '3', '02', '226', 'Pingxi', 'PX', 'P', '121.738255', '25.025725'),
('4030', '710825', '710800', '双溪区', '中国,台湾,新北市,双溪区', '双溪', '中国,台湾,新北,双溪', '3', '02', '227', 'Shuangxi', 'SX', 'S', '121.865676', '25.033409'),
('4031', '710826', '710800', '贡寮区', '中国,台湾,新北市,贡寮区', '贡寮', '中国,台湾,新北,贡寮', '3', '02', '228', 'Gongliao', 'GL', 'G', '121.908185', '25.022388'),
('4032', '710827', '710800', '金山区', '中国,台湾,新北市,金山区', '金山', '中国,台湾,新北,金山', '3', '02', '208', 'Jinshan', 'JS', 'J', '121.636427', '25.221883'),
('4033', '710828', '710800', '万里区', '中国,台湾,新北市,万里区', '万里', '中国,台湾,新北,万里', '3', '02', '207', 'Wanli', 'WL', 'W', '121.688687', '25.181234'),
('4034', '710829', '710800', '乌来区', '中国,台湾,新北市,乌来区', '乌来', '中国,台湾,新北,乌来', '3', '02', '233', 'Wulai', 'WL', 'W', '121.550531', '24.865296'),
('4035', '712200', '710000', '宜兰县', '中国,台湾,宜兰县', '宜兰', '中国,台湾,宜兰', '2', '03', '2', 'Yilan', 'YL', 'Y', '121.500000', '24.600000'),
('4036', '712201', '712200', '宜兰市', '中国,台湾,宜兰县,宜兰市', '宜兰', '中国,台湾,宜兰,宜兰', '3', '03', '260', 'Yilan', 'YL', 'Y', '121.753476', '24.751682'),
('4037', '712221', '712200', '罗东镇', '中国,台湾,宜兰县,罗东镇', '罗东', '中国,台湾,宜兰,罗东', '3', '03', '265', 'Luodong', 'LD', 'L', '121.766919', '24.677033'),
('4038', '712222', '712200', '苏澳镇', '中国,台湾,宜兰县,苏澳镇', '苏澳', '中国,台湾,宜兰,苏澳', '3', '03', '270', 'Suao', 'SA', 'S', '121.842656', '24.594622'),
('4039', '712223', '712200', '头城镇', '中国,台湾,宜兰县,头城镇', '头城', '中国,台湾,宜兰,头城', '3', '03', '261', 'Toucheng', 'TC', 'T', '121.823307', '24.859217'),
('4040', '712224', '712200', '礁溪乡', '中国,台湾,宜兰县,礁溪乡', '礁溪', '中国,台湾,宜兰,礁溪', '3', '03', '262', 'Jiaoxi', 'JX', 'J', '121.766680', '24.822345'),
('4041', '712225', '712200', '壮围乡', '中国,台湾,宜兰县,壮围乡', '壮围', '中国,台湾,宜兰,壮围', '3', '03', '263', 'Zhuangwei', 'ZW', 'Z', '121.781619', '24.744949'),
('4042', '712226', '712200', '员山乡', '中国,台湾,宜兰县,员山乡', '员山', '中国,台湾,宜兰,员山', '3', '03', '264', 'Yuanshan', 'YS', 'Y', '121.721733', '24.741771'),
('4043', '712227', '712200', '冬山乡', '中国,台湾,宜兰县,冬山乡', '冬山', '中国,台湾,宜兰,冬山', '3', '03', '269', 'Dongshan', 'DS', 'D', '121.792280', '24.634514'),
('4044', '712228', '712200', '五结乡', '中国,台湾,宜兰县,五结乡', '五结', '中国,台湾,宜兰,五结', '3', '03', '268', 'Wujie', 'WJ', 'W', '121.798297', '24.684640'),
('4045', '712229', '712200', '三星乡', '中国,台湾,宜兰县,三星乡', '三星', '中国,台湾,宜兰,三星', '3', '03', '266', 'Sanxing', 'SX', 'S', '121.003418', '23.775291'),
('4046', '712230', '712200', '大同乡', '中国,台湾,宜兰县,大同乡', '大同', '中国,台湾,宜兰,大同', '3', '03', '267', 'Datong', 'DT', 'D', '121.605557', '24.675997'),
('4047', '712231', '712200', '南澳乡', '中国,台湾,宜兰县,南澳乡', '南澳', '中国,台湾,宜兰,南澳', '3', '03', '272', 'Nanao', 'NA', 'N', '121.799810', '24.465393'),
('4048', '712300', '710000', '桃园市', '中国,台湾,桃园市', '桃园', '中国,台湾,桃园', '2', '03', '3', 'Taoyuan', 'TY', 'T', '121.083000', '25.000000'),
('4049', '712301', '712300', '桃园市', '中国,台湾,桃园市,桃园区', '桃园', '中国,台湾,桃园,桃园', '3', '03', '330', 'Taoyuan', 'TY', 'T', '121.301337', '24.993777'),
('4050', '712302', '712300', '中坜市', '中国,台湾,桃园市,中坜区', '中坜', '中国,台湾,桃园,中坜', '3', '03', '320', 'Zhongli', 'ZL', 'Z', '121.224926', '24.965353'),
('4051', '712303', '712300', '平镇市', '中国,台湾,桃园市,平镇区', '平镇', '中国,台湾,桃园,平镇', '3', '03', '324', 'Pingzhen', 'PZ', 'P', '121.218359', '24.945752'),
('4052', '712304', '712300', '八德市', '中国,台湾,桃园市,八德区', '八德', '中国,台湾,桃园,八德', '3', '03', '334', 'Bade', 'BD', 'B', '121.284655', '24.928651'),
('4053', '712305', '712300', '杨梅市', '中国,台湾,桃园市,杨梅区', '杨梅', '中国,台湾,桃园,杨梅', '3', '03', '326', 'Yangmei', 'YM', 'Y', '121.145873', '24.907575'),
('4054', '712306', '712300', '芦竹市', '中国,台湾,桃园市,芦竹区', '芦竹', '中国,台湾,桃园,芦竹', '3', '03', '338', 'Luzhu', 'LZ', 'L', '121.292064', '25.045392'),
('4055', '712321', '712300', '大溪镇', '中国,台湾,桃园市,大溪区', '大溪', '中国,台湾,桃园,大溪', '3', '03', '335', 'Daxi', 'DX', 'D', '121.286962', '24.880584'),
('4056', '712324', '712300', '大园乡', '中国,台湾,桃园市,大园区', '大园', '中国,台湾,桃园,大园', '3', '03', '337', 'Dayuan', 'DY', 'D', '121.196292', '25.064471'),
('4057', '712325', '712300', '龟山乡', '中国,台湾,桃园市,龟山区', '龟山', '中国,台湾,桃园,龟山', '3', '03', '333', 'Guishan', 'GS', 'G', '121.337767', '24.992517'),
('4058', '712327', '712300', '龙潭乡', '中国,台湾,桃园市,龙潭区', '龙潭', '中国,台湾,桃园,龙潭', '3', '03', '325', 'Longtan', 'LT', 'L', '121.216392', '24.863851'),
('4059', '712329', '712300', '新屋乡', '中国,台湾,桃园市,新屋区', '新屋', '中国,台湾,桃园,新屋', '3', '03', '327', 'Xinwu', 'XW', 'X', '121.105801', '24.972203'),
('4060', '712330', '712300', '观音乡', '中国,台湾,桃园市,观音区', '观音', '中国,台湾,桃园,观音', '3', '03', '328', 'Guanyin', 'GY', 'G', '121.077519', '25.033303'),
('4061', '712331', '712300', '复兴乡', '中国,台湾,桃园市,复兴区', '复兴', '中国,台湾,桃园,复兴', '3', '03', '336', 'Fuxing', 'FX', 'F', '121.352613', '24.820908'),
('4062', '712400', '710000', '新竹县', '中国,台湾,新竹县', '新竹', '中国,台湾,新竹', '2', '03', '3', 'Hsinchu', 'XZ', 'H', '121.160000', '24.600000'),
('4063', '712401', '712400', '竹北市', '中国,台湾,新竹县,竹北市', '竹北', '中国,台湾,新竹,竹北', '3', '03', '302', 'Zhubei', 'ZB', 'Z', '121.004317', '24.839652'),
('4064', '712421', '712400', '竹东镇', '中国,台湾,新竹县,竹东镇', '竹东', '中国,台湾,新竹,竹东', '3', '03', '310', 'Zhudong', 'ZD', 'Z', '121.086418', '24.733601'),
('4065', '712422', '712400', '新埔镇', '中国,台湾,新竹县,新埔镇', '新埔', '中国,台湾,新竹,新埔', '3', '03', '305', 'Xinpu', 'XP', 'X', '121.072804', '24.824820'),
('4066', '712423', '712400', '关西镇', '中国,台湾,新竹县,关西镇', '关西', '中国,台湾,新竹,关西', '3', '03', '306', 'Guanxi', 'GX', 'G', '121.177301', '24.788842'),
('4067', '712424', '712400', '湖口乡', '中国,台湾,新竹县,湖口乡', '湖口', '中国,台湾,新竹,湖口', '3', '03', '303', 'Hukou', 'HK', 'H', '121.043691', '24.903943'),
('4068', '712425', '712400', '新丰乡', '中国,台湾,新竹县,新丰乡', '新丰', '中国,台湾,新竹,新丰', '3', '03', '304', 'Xinfeng', 'XF', 'X', '120.983006', '24.899600'),
('4069', '712426', '712400', '芎林乡', '中国,台湾,新竹县,芎林乡', '芎林', '中国,台湾,新竹,芎林', '3', '03', '307', 'Xionglin', 'XL', 'X', '121.076924', '24.774436'),
('4070', '712427', '712400', '横山乡', '中国,台湾,新竹县,横山乡', '横山', '中国,台湾,新竹,横山', '3', '03', '312', 'Hengshan', 'HS', 'H', '121.116244', '24.720807'),
('4071', '712428', '712400', '北埔乡', '中国,台湾,新竹县,北埔乡', '北埔', '中国,台湾,新竹,北埔', '3', '03', '314', 'Beipu', 'BP', 'B', '121.053156', '24.697126'),
('4072', '712429', '712400', '宝山乡', '中国,台湾,新竹县,宝山乡', '宝山', '中国,台湾,新竹,宝山', '3', '03', '308', 'Baoshan', 'BS', 'B', '120.985752', '24.760975'),
('4073', '712430', '712400', '峨眉乡', '中国,台湾,新竹县,峨眉乡', '峨眉', '中国,台湾,新竹,峨眉', '3', '03', '315', 'Emei', 'EM', 'E', '121.015291', '24.686127'),
('4074', '712431', '712400', '尖石乡', '中国,台湾,新竹县,尖石乡', '尖石', '中国,台湾,新竹,尖石', '3', '03', '313', 'Jianshi', 'JS', 'J', '121.197802', '24.704360'),
('4075', '712432', '712400', '五峰乡', '中国,台湾,新竹县,五峰乡', '五峰', '中国,台湾,新竹,五峰', '3', '03', '311', 'Wufeng', 'WF', 'W', '121.003418', '23.775291'),
('4076', '712500', '710000', '苗栗县', '中国,台湾,苗栗县', '苗栗', '中国,台湾,苗栗', '2', '037', '3', 'Miaoli', 'ML', 'M', '120.750000', '24.500000'),
('4077', '712501', '712500', '苗栗市', '中国,台湾,苗栗县,苗栗市', '苗栗', '中国,台湾,苗栗,苗栗', '3', '037', '360', 'Miaoli', 'ML', 'M', '120.818869', '24.561472'),
('4078', '712521', '712500', '苑里镇', '中国,台湾,苗栗县,苑里镇', '苑里', '中国,台湾,苗栗,苑里', '3', '037', '358', 'Yuanli', 'YL', 'Y', '120.648907', '24.441750'),
('4079', '712522', '712500', '通霄镇', '中国,台湾,苗栗县,通霄镇', '通霄', '中国,台湾,苗栗,通霄', '3', '037', '357', 'Tongxiao', 'TX', 'T', '120.676700', '24.489087'),
('4080', '712523', '712500', '竹南镇', '中国,台湾,苗栗县,竹南镇', '竹南', '中国,台湾,苗栗,竹南', '3', '037', '350', 'Zhunan', 'ZN', 'Z', '120.872641', '24.685513'),
('4081', '712524', '712500', '头份市', '中国,台湾,苗栗县,头份市', '头份', '中国,台湾,苗栗,头份', '3', '037', '351', 'Toufen', 'TF', 'T', '120.895188', '24.687993'),
('4082', '712525', '712500', '后龙镇', '中国,台湾,苗栗县,后龙镇', '后龙', '中国,台湾,苗栗,后龙', '3', '037', '356', 'Houlong', 'HL', 'H', '120.786480', '24.612617'),
('4083', '712526', '712500', '卓兰镇', '中国,台湾,苗栗县,卓兰镇', '卓兰', '中国,台湾,苗栗,卓兰', '3', '037', '369', 'Zhuolan', 'ZL', 'Z', '120.823441', '24.309509'),
('4084', '712527', '712500', '大湖乡', '中国,台湾,苗栗县,大湖乡', '大湖', '中国,台湾,苗栗,大湖', '3', '037', '364', 'Dahu', 'DH', 'D', '120.863641', '24.422547'),
('4085', '712528', '712500', '公馆乡', '中国,台湾,苗栗县,公馆乡', '公馆', '中国,台湾,苗栗,公馆', '3', '037', '363', 'Gongguan', 'GG', 'G', '120.822983', '24.499108'),
('4086', '712529', '712500', '铜锣乡', '中国,台湾,苗栗县,铜锣乡', '铜锣', '中国,台湾,苗栗,铜锣', '3', '037', '366', 'Tongluo', 'TL', 'T', '121.003418', '23.775291'),
('4087', '712530', '712500', '南庄乡', '中国,台湾,苗栗县,南庄乡', '南庄', '中国,台湾,苗栗,南庄', '3', '037', '353', 'Nanzhuang', 'NZ', 'N', '120.994957', '24.596835'),
('4088', '712531', '712500', '头屋乡', '中国,台湾,苗栗县,头屋乡', '头屋', '中国,台湾,苗栗,头屋', '3', '037', '362', 'Touwu', 'TW', 'T', '120.846616', '24.574249'),
('4089', '712532', '712500', '三义乡', '中国,台湾,苗栗县,三义乡', '三义', '中国,台湾,苗栗,三义', '3', '037', '367', 'Sanyi', 'SY', 'S', '120.742340', '24.350270'),
('4090', '712533', '712500', '西湖乡', '中国,台湾,苗栗县,西湖乡', '西湖', '中国,台湾,苗栗,西湖', '3', '037', '368', 'Xihu', 'XH', 'X', '121.003418', '23.775291'),
('4091', '712534', '712500', '造桥乡', '中国,台湾,苗栗县,造桥乡', '造桥', '中国,台湾,苗栗,造桥', '3', '037', '361', 'Zaoqiao', 'ZQ', 'Z', '120.862399', '24.637537'),
('4092', '712535', '712500', '三湾乡', '中国,台湾,苗栗县,三湾乡', '三湾', '中国,台湾,苗栗,三湾', '3', '037', '352', 'Sanwan', 'SW', 'S', '120.951484', '24.651051'),
('4093', '712536', '712500', '狮潭乡', '中国,台湾,苗栗县,狮潭乡', '狮潭', '中国,台湾,苗栗,狮潭', '3', '037', '354', 'Shitan', 'ST', 'S', '120.918024', '24.540004'),
('4094', '712537', '712500', '泰安乡', '中国,台湾,苗栗县,泰安乡', '泰安', '中国,台湾,苗栗,泰安', '3', '037', '365', 'Tai\'an', 'TA', 'T', '120.904441', '24.442600'),
('4095', '712700', '710000', '彰化县', '中国,台湾,彰化县', '彰化', '中国,台湾,彰化', '2', '04', '5', 'Changhua', 'ZH', 'C', '120.416000', '24.000000'),
('4096', '712701', '712700', '彰化市', '中国,台湾,彰化县,彰化市', '彰化市', '中国,台湾,彰化,彰化市', '3', '04', '500', 'Zhanghuashi', 'ZHS', 'Z', '120.542294', '24.080911'),
('4097', '712721', '712700', '鹿港镇', '中国,台湾,彰化县,鹿港镇', '鹿港', '中国,台湾,彰化,鹿港', '3', '04', '505', 'Lugang', 'LG', 'L', '120.435392', '24.056937'),
('4098', '712722', '712700', '和美镇', '中国,台湾,彰化县,和美镇', '和美', '中国,台湾,彰化,和美', '3', '04', '508', 'Hemei', 'HM', 'H', '120.500265', '24.110904'),
('4099', '712723', '712700', '线西乡', '中国,台湾,彰化县,线西乡', '线西', '中国,台湾,彰化,线西', '3', '04', '507', 'Xianxi', 'XX', 'X', '120.465921', '24.128653'),
('4100', '712724', '712700', '伸港乡', '中国,台湾,彰化县,伸港乡', '伸港', '中国,台湾,彰化,伸港', '3', '04', '509', 'Shengang', 'SG', 'S', '120.484224', '24.146081'),
('4101', '712725', '712700', '福兴乡', '中国,台湾,彰化县,福兴乡', '福兴', '中国,台湾,彰化,福兴', '3', '04', '506', 'Fuxing', 'FX', 'F', '120.443682', '24.047883'),
('4102', '712726', '712700', '秀水乡', '中国,台湾,彰化县,秀水乡', '秀水', '中国,台湾,彰化,秀水', '3', '04', '504', 'Xiushui', 'XS', 'X', '120.502658', '24.035267'),
('4103', '712727', '712700', '花坛乡', '中国,台湾,彰化县,花坛乡', '花坛', '中国,台湾,彰化,花坛', '3', '04', '503', 'Huatan', 'HT', 'H', '120.538403', '24.029399'),
('4104', '712728', '712700', '芬园乡', '中国,台湾,彰化县,芬园乡', '芬园', '中国,台湾,彰化,芬园', '3', '04', '502', 'Fenyuan', 'FY', 'F', '120.629024', '24.013658'),
('4105', '712729', '712700', '员林市', '中国,台湾,彰化县,员林市', '员林', '中国,台湾,彰化,员林', '3', '04', '510', 'Yuanlin', 'YL', 'Y', '120.574625', '23.958999'),
('4106', '712730', '712700', '溪湖镇', '中国,台湾,彰化县,溪湖镇', '溪湖', '中国,台湾,彰化,溪湖', '3', '04', '514', 'Xihu', 'XH', 'X', '120.479144', '23.962315'),
('4107', '712731', '712700', '田中镇', '中国,台湾,彰化县,田中镇', '田中', '中国,台湾,彰化,田中', '3', '04', '520', 'Tianzhong', 'TZ', 'T', '120.580629', '23.861718'),
('4108', '712732', '712700', '大村乡', '中国,台湾,彰化县,大村乡', '大村', '中国,台湾,彰化,大村', '3', '04', '515', 'Dacun', 'DC', 'D', '120.540713', '23.993726'),
('4109', '712733', '712700', '埔盐乡', '中国,台湾,彰化县,埔盐乡', '埔盐', '中国,台湾,彰化,埔盐', '3', '04', '516', 'Puyan', 'PY', 'P', '120.464044', '24.000346'),
('4110', '712734', '712700', '埔心乡', '中国,台湾,彰化县,埔心乡', '埔心', '中国,台湾,彰化,埔心', '3', '04', '513', 'Puxin', 'PX', 'P', '120.543568', '23.953019'),
('4111', '712735', '712700', '永靖乡', '中国,台湾,彰化县,永靖乡', '永靖', '中国,台湾,彰化,永靖', '3', '04', '512', 'Yongjing', 'YJ', 'Y', '120.547775', '23.924703'),
('4112', '712736', '712700', '社头乡', '中国,台湾,彰化县,社头乡', '社头', '中国,台湾,彰化,社头', '3', '04', '511', 'Shetou', 'ST', 'S', '120.582681', '23.896686'),
('4113', '712737', '712700', '二水乡', '中国,台湾,彰化县,二水乡', '二水', '中国,台湾,彰化,二水', '3', '04', '530', 'Ershui', 'ES', 'E', '120.618788', '23.806995'),
('4114', '712738', '712700', '北斗镇', '中国,台湾,彰化县,北斗镇', '北斗', '中国,台湾,彰化,北斗', '3', '04', '521', 'Beidou', 'BD', 'B', '120.520449', '23.870911'),
('4115', '712739', '712700', '二林镇', '中国,台湾,彰化县,二林镇', '二林', '中国,台湾,彰化,二林', '3', '04', '526', 'Erlin', 'EL', 'E', '120.374468', '23.899751'),
('4116', '712740', '712700', '田尾乡', '中国,台湾,彰化县,田尾乡', '田尾', '中国,台湾,彰化,田尾', '3', '04', '522', 'Tianwei', 'TW', 'T', '120.524717', '23.890735'),
('4117', '712741', '712700', '埤头乡', '中国,台湾,彰化县,埤头乡', '埤头', '中国,台湾,彰化,埤头', '3', '04', '523', 'Pitou', 'PT', 'P', '120.462599', '23.891324'),
('4118', '712742', '712700', '芳苑乡', '中国,台湾,彰化县,芳苑乡', '芳苑', '中国,台湾,彰化,芳苑', '3', '04', '528', 'Fangyuan', 'FY', 'F', '120.320329', '23.924222'),
('4119', '712743', '712700', '大城乡', '中国,台湾,彰化县,大城乡', '大城', '中国,台湾,彰化,大城', '3', '04', '527', 'Dacheng', 'DC', 'D', '120.320934', '23.852382'),
('4120', '712744', '712700', '竹塘乡', '中国,台湾,彰化县,竹塘乡', '竹塘', '中国,台湾,彰化,竹塘', '3', '04', '525', 'Zhutang', 'ZT', 'Z', '120.427499', '23.860112'),
('4121', '712745', '712700', '溪州乡', '中国,台湾,彰化县,溪州乡', '溪州', '中国,台湾,彰化,溪州', '3', '04', '524', 'Xizhou', 'XZ', 'X', '120.498706', '23.851229'),
('4122', '712800', '710000', '南投县', '中国,台湾,南投县', '南投', '中国,台湾,南投', '2', '049', '5', 'Nantou', 'NT', 'N', '120.830000', '23.830000'),
('4123', '712801', '712800', '南投市', '中国,台湾,南投县,南投市', '南投市', '中国,台湾,南投,南投市', '3', '049', '540', 'Nantoushi', 'NTS', 'N', '120.683706', '23.909956'),
('4124', '712821', '712800', '埔里镇', '中国,台湾,南投县,埔里镇', '埔里', '中国,台湾,南投,埔里', '3', '049', '545', 'Puli', 'PL', 'P', '120.964648', '23.964789'),
('4125', '712822', '712800', '草屯镇', '中国,台湾,南投县,草屯镇', '草屯', '中国,台湾,南投,草屯', '3', '049', '542', 'Caotun', 'CT', 'C', '120.680343', '23.973947'),
('4126', '712823', '712800', '竹山镇', '中国,台湾,南投县,竹山镇', '竹山', '中国,台湾,南投,竹山', '3', '049', '557', 'Zhushan', 'ZS', 'Z', '120.672007', '23.757655'),
('4127', '712824', '712800', '集集镇', '中国,台湾,南投县,集集镇', '集集', '中国,台湾,南投,集集', '3', '049', '552', 'Jiji', 'JJ', 'J', '120.783673', '23.829013'),
('4128', '712825', '712800', '名间乡', '中国,台湾,南投县,名间乡', '名间', '中国,台湾,南投,名间', '3', '049', '551', 'Mingjian', 'MJ', 'M', '120.702797', '23.838427'),
('4129', '712826', '712800', '鹿谷乡', '中国,台湾,南投县,鹿谷乡', '鹿谷', '中国,台湾,南投,鹿谷', '3', '049', '558', 'Lugu', 'LG', 'L', '120.752796', '23.744471'),
('4130', '712827', '712800', '中寮乡', '中国,台湾,南投县,中寮乡', '中寮', '中国,台湾,南投,中寮', '3', '049', '541', 'Zhongliao', 'ZL', 'Z', '120.766654', '23.878935'),
('4131', '712828', '712800', '鱼池乡', '中国,台湾,南投县,鱼池乡', '鱼池', '中国,台湾,南投,鱼池', '3', '049', '555', 'Yuchi', 'YC', 'Y', '120.936060', '23.896356'),
('4132', '712829', '712800', '国姓乡', '中国,台湾,南投县,国姓乡', '国姓', '中国,台湾,南投,国姓', '3', '049', '544', 'Guoxing', 'GX', 'G', '120.858541', '24.042298'),
('4133', '712830', '712800', '水里乡', '中国,台湾,南投县,水里乡', '水里', '中国,台湾,南投,水里', '3', '049', '553', 'Shuili', 'SL', 'S', '120.855912', '23.812086'),
('4134', '712831', '712800', '信义乡', '中国,台湾,南投县,信义乡', '信义', '中国,台湾,南投,信义', '3', '049', '556', 'Xinyi', 'XY', 'X', '120.855257', '23.699922'),
('4135', '712832', '712800', '仁爱乡', '中国,台湾,南投县,仁爱乡', '仁爱', '中国,台湾,南投,仁爱', '3', '049', '546', 'Renai', 'RA', 'R', '121.133543', '24.024429'),
('4136', '712900', '710000', '云林县', '中国,台湾,云林县', '云林', '中国,台湾,云林', '2', '05', '6', 'Yunlin', 'YL', 'Y', '120.250000', '23.750000'),
('4137', '712901', '712900', '斗六市', '中国,台湾,云林县,斗六市', '斗六', '中国,台湾,云林,斗六', '3', '05', '640', 'Douliu', 'DL', 'D', '120.527360', '23.697651'),
('4138', '712921', '712900', '斗南镇', '中国,台湾,云林县,斗南镇', '斗南', '中国,台湾,云林,斗南', '3', '05', '630', 'Dounan', 'DN', 'D', '120.479075', '23.679731'),
('4139', '712922', '712900', '虎尾镇', '中国,台湾,云林县,虎尾镇', '虎尾', '中国,台湾,云林,虎尾', '3', '05', '632', 'Huwei', 'HW', 'H', '120.445339', '23.708182'),
('4140', '712923', '712900', '西螺镇', '中国,台湾,云林县,西螺镇', '西螺', '中国,台湾,云林,西螺', '3', '05', '648', 'Xiluo', 'XL', 'X', '120.466010', '23.797984'),
('4141', '712924', '712900', '土库镇', '中国,台湾,云林县,土库镇', '土库', '中国,台湾,云林,土库', '3', '05', '633', 'Tuku', 'TK', 'T', '120.392572', '23.677822'),
('4142', '712925', '712900', '北港镇', '中国,台湾,云林县,北港镇', '北港', '中国,台湾,云林,北港', '3', '05', '651', 'Beigang', 'BG', 'B', '120.302393', '23.575525'),
('4143', '712926', '712900', '古坑乡', '中国,台湾,云林县,古坑乡', '古坑', '中国,台湾,云林,古坑', '3', '05', '646', 'Gukeng', 'GK', 'G', '120.562043', '23.642568'),
('4144', '712927', '712900', '大埤乡', '中国,台湾,云林县,大埤乡', '大埤', '中国,台湾,云林,大埤', '3', '05', '631', 'Dapi', 'DP', 'D', '120.430516', '23.645908'),
('4145', '712928', '712900', '莿桐乡', '中国,台湾,云林县,莿桐乡', '莿桐', '中国,台湾,云林,莿桐', '3', '05', '647', 'Citong', 'CT', 'C', '120.502374', '23.760784'),
('4146', '712929', '712900', '林内乡', '中国,台湾,云林县,林内乡', '林内', '中国,台湾,云林,林内', '3', '05', '643', 'Linna', 'LN', 'L', '120.611365', '23.758712'),
('4147', '712930', '712900', '二仑乡', '中国,台湾,云林县,二仑乡', '二仑', '中国,台湾,云林,二仑', '3', '05', '649', 'Erlun', 'EL', 'E', '120.415077', '23.771273'),
('4148', '712931', '712900', '仑背乡', '中国,台湾,云林县,仑背乡', '仑背', '中国,台湾,云林,仑背', '3', '05', '637', 'Lunbei', 'LB', 'L', '120.353895', '23.758840'),
('4149', '712932', '712900', '麦寮乡', '中国,台湾,云林县,麦寮乡', '麦寮', '中国,台湾,云林,麦寮', '3', '05', '638', 'Mailiao', 'ML', 'M', '120.252043', '23.753841'),
('4150', '712933', '712900', '东势乡', '中国,台湾,云林县,东势乡', '东势', '中国,台湾,云林,东势', '3', '05', '635', 'Dongshi', 'DS', 'D', '120.252672', '23.674679'),
('4151', '712934', '712900', '褒忠乡', '中国,台湾,云林县,褒忠乡', '褒忠', '中国,台湾,云林,褒忠', '3', '05', '634', 'Baozhong', 'BZ', 'B', '120.310488', '23.694245'),
('4152', '712935', '712900', '台西乡', '中国,台湾,云林县,台西乡', '台西', '中国,台湾,云林,台西', '3', '05', '636', 'Taixi', 'TX', 'T', '120.196141', '23.702819'),
('4153', '712936', '712900', '元长乡', '中国,台湾,云林县,元长乡', '元长', '中国,台湾,云林,元长', '3', '05', '655', 'Yuanchang', 'YC', 'Y', '120.315124', '23.649458'),
('4154', '712937', '712900', '四湖乡', '中国,台湾,云林县,四湖乡', '四湖', '中国,台湾,云林,四湖', '3', '05', '654', 'Sihu', 'SH', 'S', '120.225741', '23.637740'),
('4155', '712938', '712900', '口湖乡', '中国,台湾,云林县,口湖乡', '口湖', '中国,台湾,云林,口湖', '3', '05', '653', 'Kouhu', 'KH', 'K', '120.185370', '23.582406'),
('4156', '712939', '712900', '水林乡', '中国,台湾,云林县,水林乡', '水林', '中国,台湾,云林,水林', '3', '05', '652', 'Shuilin', 'SL', 'S', '120.245948', '23.572634'),
('4157', '713000', '710000', '嘉义县', '中国,台湾,嘉义县', '嘉义', '中国,台湾,嘉义', '2', '05', '6', 'Chiayi', 'JY', 'C', '120.300000', '23.500000'),
('4158', '713001', '713000', '太保市', '中国,台湾,嘉义县,太保市', '太保', '中国,台湾,嘉义,太保', '3', '05', '612', 'Taibao', 'TB', 'T', '120.332876', '23.459647'),
('4159', '713002', '713000', '朴子市', '中国,台湾,嘉义县,朴子市', '朴子', '中国,台湾,嘉义,朴子', '3', '05', '613', 'Puzi', 'PZ', 'P', '120.247014', '23.464961'),
('4160', '713023', '713000', '布袋镇', '中国,台湾,嘉义县,布袋镇', '布袋', '中国,台湾,嘉义,布袋', '3', '05', '625', 'Budai', 'BD', 'B', '120.166936', '23.377979'),
('4161', '713024', '713000', '大林镇', '中国,台湾,嘉义县,大林镇', '大林', '中国,台湾,嘉义,大林', '3', '05', '622', 'Dalin', 'DL', 'D', '120.471336', '23.603815'),
('4162', '713025', '713000', '民雄乡', '中国,台湾,嘉义县,民雄乡', '民雄', '中国,台湾,嘉义,民雄', '3', '05', '621', 'Minxiong', 'MX', 'M', '120.428577', '23.551456'),
('4163', '713026', '713000', '溪口乡', '中国,台湾,嘉义县,溪口乡', '溪口', '中国,台湾,嘉义,溪口', '3', '05', '623', 'Xikou', 'XK', 'X', '120.393822', '23.602223'),
('4164', '713027', '713000', '新港乡', '中国,台湾,嘉义县,新港乡', '新港', '中国,台湾,嘉义,新港', '3', '05', '616', 'Xingang', 'XG', 'X', '120.347647', '23.551806'),
('4165', '713028', '713000', '六脚乡', '中国,台湾,嘉义县,六脚乡', '六脚', '中国,台湾,嘉义,六脚', '3', '05', '615', 'Liujiao', 'LJ', 'L', '120.291083', '23.493942'),
('4166', '713029', '713000', '东石乡', '中国,台湾,嘉义县,东石乡', '东石', '中国,台湾,嘉义,东石', '3', '05', '614', 'Dongshi', 'DS', 'D', '120.153822', '23.459235'),
('4167', '713030', '713000', '义竹乡', '中国,台湾,嘉义县,义竹乡', '义竹', '中国,台湾,嘉义,义竹', '3', '05', '624', 'Yizhu', 'YZ', 'Y', '120.243423', '23.336277'),
('4168', '713031', '713000', '鹿草乡', '中国,台湾,嘉义县,鹿草乡', '鹿草', '中国,台湾,嘉义,鹿草', '3', '05', '611', 'Lucao', 'LC', 'L', '120.308370', '23.410784'),
('4169', '713032', '713000', '水上乡', '中国,台湾,嘉义县,水上乡', '水上', '中国,台湾,嘉义,水上', '3', '05', '608', 'Shuishang', 'SS', 'S', '120.397936', '23.428104'),
('4170', '713033', '713000', '中埔乡', '中国,台湾,嘉义县,中埔乡', '中埔', '中国,台湾,嘉义,中埔', '3', '05', '606', 'Zhongpu', 'ZP', 'Z', '120.522948', '23.425148'),
('4171', '713034', '713000', '竹崎乡', '中国,台湾,嘉义县,竹崎乡', '竹崎', '中国,台湾,嘉义,竹崎', '3', '05', '604', 'Zhuqi', 'ZQ', 'Z', '120.551466', '23.523184'),
('4172', '713035', '713000', '梅山乡', '中国,台湾,嘉义县,梅山乡', '梅山', '中国,台湾,嘉义,梅山', '3', '05', '603', 'Meishan', 'MS', 'M', '120.557192', '23.584915'),
('4173', '713036', '713000', '番路乡', '中国,台湾,嘉义县,番路乡', '番路', '中国,台湾,嘉义,番路', '3', '05', '602', 'Fanlu', 'FL', 'F', '120.555043', '23.465222'),
('4174', '713037', '713000', '大埔乡', '中国,台湾,嘉义县,大埔乡', '大埔', '中国,台湾,嘉义,大埔', '3', '05', '607', 'Dapu', 'DP', 'D', '120.593795', '23.296715'),
('4175', '713038', '713000', '阿里山乡', '中国,台湾,嘉义县,阿里山乡', '阿里山', '中国,台湾,嘉义,阿里山', '3', '05', '605', 'Alishan', 'ALS', 'A', '120.732520', '23.467950'),
('4176', '713300', '710000', '屏东县', '中国,台湾,屏东县', '屏东', '中国,台湾,屏东', '2', '08', '9', 'Pingtung', 'PD', 'P', '120.487928', '22.682802'),
('4177', '713301', '713300', '屏东市', '中国,台湾,屏东县,屏东市', '屏东', '中国,台湾,屏东,屏东', '3', '08', '900', 'Pingdong', 'PD', 'P', '120.488465', '22.669723'),
('4178', '713321', '713300', '潮州镇', '中国,台湾,屏东县,潮州镇', '潮州', '中国,台湾,屏东,潮州', '3', '08', '920', 'Chaozhou', 'CZ', 'C', '120.542854', '22.550536'),
('4179', '713322', '713300', '东港镇', '中国,台湾,屏东县,东港镇', '东港', '中国,台湾,屏东,东港', '3', '08', '928', 'Donggang', 'DG', 'D', '120.454489', '22.466626'),
('4180', '713323', '713300', '恒春镇', '中国,台湾,屏东县,恒春镇', '恒春', '中国,台湾,屏东,恒春', '3', '08', '946', 'Hengchun', 'HC', 'H', '120.745451', '22.002373'),
('4181', '713324', '713300', '万丹乡', '中国,台湾,屏东县,万丹乡', '万丹', '中国,台湾,屏东,万丹', '3', '08', '913', 'Wandan', 'WD', 'W', '120.484533', '22.589839'),
('4182', '713325', '713300', '长治乡', '中国,台湾,屏东县,长治乡', '长治', '中国,台湾,屏东,长治', '3', '08', '908', 'Changzhi', 'CZ', 'C', '120.527614', '22.677062'),
('4183', '713326', '713300', '麟洛乡', '中国,台湾,屏东县,麟洛乡', '麟洛', '中国,台湾,屏东,麟洛', '3', '08', '909', 'Linluo', 'LL', 'L', '120.527283', '22.650604'),
('4184', '713327', '713300', '九如乡', '中国,台湾,屏东县,九如乡', '九如', '中国,台湾,屏东,九如', '3', '08', '904', 'Jiuru', 'JR', 'J', '120.490142', '22.739778'),
('4185', '713328', '713300', '里港乡', '中国,台湾,屏东县,里港乡', '里港', '中国,台湾,屏东,里港', '3', '08', '905', 'Ligang', 'LG', 'L', '120.494490', '22.779220'),
('4186', '713329', '713300', '盐埔乡', '中国,台湾,屏东县,盐埔乡', '盐埔', '中国,台湾,屏东,盐埔', '3', '08', '907', 'Yanpu', 'YP', 'Y', '120.572849', '22.754783'),
('4187', '713330', '713300', '高树乡', '中国,台湾,屏东县,高树乡', '高树', '中国,台湾,屏东,高树', '3', '08', '906', 'Gaoshu', 'GS', 'G', '120.600214', '22.826789'),
('4188', '713331', '713300', '万峦乡', '中国,台湾,屏东县,万峦乡', '万峦', '中国,台湾,屏东,万峦', '3', '08', '923', 'Wanluan', 'WL', 'W', '120.566477', '22.571965'),
('4189', '713332', '713300', '内埔乡', '中国,台湾,屏东县,内埔乡', '内埔', '中国,台湾,屏东,内埔', '3', '08', '912', 'Napu', 'NP', 'N', '120.566865', '22.611967'),
('4190', '713333', '713300', '竹田乡', '中国,台湾,屏东县,竹田乡', '竹田', '中国,台湾,屏东,竹田', '3', '08', '911', 'Zhutian', 'ZT', 'Z', '120.544038', '22.584678'),
('4191', '713334', '713300', '新埤乡', '中国,台湾,屏东县,新埤乡', '新埤', '中国,台湾,屏东,新埤', '3', '08', '925', 'Xinpi', 'XP', 'X', '120.549546', '22.469976'),
('4192', '713335', '713300', '枋寮乡', '中国,台湾,屏东县,枋寮乡', '枋寮', '中国,台湾,屏东,枋寮', '3', '08', '940', 'Fangliao', 'FL', 'F', '120.593438', '22.365560'),
('4193', '713336', '713300', '新园乡', '中国,台湾,屏东县,新园乡', '新园', '中国,台湾,屏东,新园', '3', '08', '932', 'Xinyuan', 'XY', 'X', '120.461739', '22.543952'),
('4194', '713337', '713300', '崁顶乡', '中国,台湾,屏东县,崁顶乡', '崁顶', '中国,台湾,屏东,崁顶', '3', '08', '924', 'Kanding', 'KD', 'K', '120.514571', '22.514795'),
('4195', '713338', '713300', '林边乡', '中国,台湾,屏东县,林边乡', '林边', '中国,台湾,屏东,林边', '3', '08', '927', 'Linbian', 'LB', 'L', '120.515091', '22.434015'),
('4196', '713339', '713300', '南州乡', '中国,台湾,屏东县,南州乡', '南州', '中国,台湾,屏东,南州', '3', '08', '926', 'Nanzhou', 'NZ', 'N', '120.509808', '22.490192'),
('4197', '713340', '713300', '佳冬乡', '中国,台湾,屏东县,佳冬乡', '佳冬', '中国,台湾,屏东,佳冬', '3', '08', '931', 'Jiadong', 'JD', 'J', '120.551544', '22.417653'),
('4198', '713341', '713300', '琉球乡', '中国,台湾,屏东县,琉球乡', '琉球', '中国,台湾,屏东,琉球', '3', '08', '929', 'Liuqiu', 'LQ', 'L', '120.369020', '22.342366'),
('4199', '713342', '713300', '车城乡', '中国,台湾,屏东县,车城乡', '车城', '中国,台湾,屏东,车城', '3', '08', '944', 'Checheng', 'CC', 'C', '120.710979', '22.072077'),
('4200', '713343', '713300', '满州乡', '中国,台湾,屏东县,满州乡', '满州', '中国,台湾,屏东,满州', '3', '08', '947', 'Manzhou', 'MZ', 'M', '120.838843', '22.020853'),
('4201', '713344', '713300', '枋山乡', '中国,台湾,屏东县,枋山乡', '枋山', '中国,台湾,屏东,枋山', '3', '08', '941', 'Fangshan', 'FS', 'F', '120.656356', '22.260338'),
('4202', '713345', '713300', '三地门乡', '中国,台湾,屏东县,三地门乡', '三地门', '中国,台湾,屏东,三地门', '3', '08', '901', 'Sandimen', 'SDM', 'S', '120.654486', '22.713877'),
('4203', '713346', '713300', '雾台乡', '中国,台湾,屏东县,雾台乡', '雾台', '中国,台湾,屏东,雾台', '3', '08', '902', 'Wutai', 'WT', 'W', '120.732318', '22.744877'),
('4204', '713347', '713300', '玛家乡', '中国,台湾,屏东县,玛家乡', '玛家', '中国,台湾,屏东,玛家', '3', '08', '903', 'Majia', 'MJ', 'M', '120.644130', '22.706718'),
('4205', '713348', '713300', '泰武乡', '中国,台湾,屏东县,泰武乡', '泰武', '中国,台湾,屏东,泰武', '3', '08', '921', 'Taiwu', 'TW', 'T', '120.632856', '22.591819'),
('4206', '713349', '713300', '来义乡', '中国,台湾,屏东县,来义乡', '来义', '中国,台湾,屏东,来义', '3', '08', '922', 'Laiyi', 'LY', 'L', '120.633601', '22.525866'),
('4207', '713350', '713300', '春日乡', '中国,台湾,屏东县,春日乡', '春日', '中国,台湾,屏东,春日', '3', '08', '942', 'Chunri', 'CR', 'C', '120.628793', '22.370672'),
('4208', '713351', '713300', '狮子乡', '中国,台湾,屏东县,狮子乡', '狮子', '中国,台湾,屏东,狮子', '3', '08', '943', 'Shizi', 'SZ', 'S', '120.704617', '22.201917'),
('4209', '713352', '713300', '牡丹乡', '中国,台湾,屏东县,牡丹乡', '牡丹', '中国,台湾,屏东,牡丹', '3', '08', '945', 'Mudan', 'MD', 'M', '120.770108', '22.125687'),
('4210', '713400', '710000', '台东县', '中国,台湾,台东县', '台东', '中国,台湾,台东', '2', '089', '9', 'Taitung', 'TD', 'T', '120.916000', '23.000000'),
('4211', '713401', '713400', '台东市', '中国,台湾,台东县,台东市', '台东', '中国,台湾,台东,台东', '3', '089', '950', 'Taidong', 'TD', 'T', '121.145654', '22.756045'),
('4212', '713421', '713400', '成功镇', '中国,台湾,台东县,成功镇', '成功', '中国,台湾,台东,成功', '3', '089', '961', 'Chenggong', 'CG', 'C', '121.379571', '23.100223'),
('4213', '713422', '713400', '关山镇', '中国,台湾,台东县,关山镇', '关山', '中国,台湾,台东,关山', '3', '089', '956', 'Guanshan', 'GS', 'G', '121.163134', '23.047450'),
('4214', '713423', '713400', '卑南乡', '中国,台湾,台东县,卑南乡', '卑南', '中国,台湾,台东,卑南', '3', '089', '954', 'Beinan', 'BN', 'B', '121.083503', '22.786039'),
('4215', '713424', '713400', '鹿野乡', '中国,台湾,台东县,鹿野乡', '鹿野', '中国,台湾,台东,鹿野', '3', '089', '955', 'Luye', 'LY', 'L', '121.135982', '22.913951'),
('4216', '713425', '713400', '池上乡', '中国,台湾,台东县,池上乡', '池上', '中国,台湾,台东,池上', '3', '089', '958', 'Chishang', 'CS', 'C', '121.215139', '23.122393'),
('4217', '713426', '713400', '东河乡', '中国,台湾,台东县,东河乡', '东河', '中国,台湾,台东,东河', '3', '089', '959', 'Donghe', 'DH', 'D', '121.300334', '22.969934'),
('4218', '713427', '713400', '长滨乡', '中国,台湾,台东县,长滨乡', '长滨', '中国,台湾,台东,长滨', '3', '089', '962', 'Changbin', 'CB', 'C', '121.451522', '23.315041'),
('4219', '713428', '713400', '太麻里乡', '中国,台湾,台东县,太麻里乡', '太麻里', '中国,台湾,台东,太麻里', '3', '089', '963', 'Taimali', 'TML', 'T', '121.007394', '22.615383'),
('4220', '713429', '713400', '大武乡', '中国,台湾,台东县,大武乡', '大武', '中国,台湾,台东,大武', '3', '089', '965', 'Dawu', 'DW', 'D', '120.889938', '22.339919'),
('4221', '713430', '713400', '绿岛乡', '中国,台湾,台东县,绿岛乡', '绿岛', '中国,台湾,台东,绿岛', '3', '089', '951', 'Lvdao', 'LD', 'L', '121.492596', '22.661676'),
('4222', '713431', '713400', '海端乡', '中国,台湾,台东县,海端乡', '海端', '中国,台湾,台东,海端', '3', '089', '957', 'Haiduan', 'HD', 'H', '121.172008', '23.101074'),
('4223', '713432', '713400', '延平乡', '中国,台湾,台东县,延平乡', '延平', '中国,台湾,台东,延平', '3', '089', '953', 'Yanping', 'YP', 'Y', '121.084499', '22.902358'),
('4224', '713433', '713400', '金峰乡', '中国,台湾,台东县,金峰乡', '金峰', '中国,台湾,台东,金峰', '3', '089', '964', 'Jinfeng', 'JF', 'J', '120.971292', '22.595511'),
('4225', '713434', '713400', '达仁乡', '中国,台湾,台东县,达仁乡', '达仁', '中国,台湾,台东,达仁', '3', '089', '966', 'Daren', 'DR', 'D', '120.884131', '22.294869'),
('4226', '713435', '713400', '兰屿乡', '中国,台湾,台东县,兰屿乡', '兰屿', '中国,台湾,台东,兰屿', '3', '089', '952', 'Lanyu', 'LY', 'L', '121.532473', '22.056736'),
('4227', '713500', '710000', '花莲县', '中国,台湾,花莲县', '花莲', '中国,台湾,花莲', '2', '03', '9', 'Hualien', 'HL', 'H', '121.300000', '23.830000'),
('4228', '713501', '713500', '花莲市', '中国,台湾,花莲县,花莲市', '花莲', '中国,台湾,花莲,花莲', '3', '03', '970', 'Hualian', 'HL', 'H', '121.606810', '23.982074'),
('4229', '713521', '713500', '凤林镇', '中国,台湾,花莲县,凤林镇', '凤林', '中国,台湾,花莲,凤林', '3', '03', '975', 'Fenglin', 'FL', 'F', '121.451687', '23.744648'),
('4230', '713522', '713500', '玉里镇', '中国,台湾,花莲县,玉里镇', '玉里', '中国,台湾,花莲,玉里', '3', '03', '981', 'Yuli', 'YL', 'Y', '121.316445', '23.336509'),
('4231', '713523', '713500', '新城乡', '中国,台湾,花莲县,新城乡', '新城', '中国,台湾,花莲,新城', '3', '03', '971', 'Xincheng', 'XC', 'X', '121.640512', '24.128133'),
('4232', '713524', '713500', '吉安乡', '中国,台湾,花莲县,吉安乡', '吉安', '中国,台湾,花莲,吉安', '3', '03', '973', 'Ji\'an', 'JA', 'J', '121.568005', '23.961635'),
('4233', '713525', '713500', '寿丰乡', '中国,台湾,花莲县,寿丰乡', '寿丰', '中国,台湾,花莲,寿丰', '3', '03', '974', 'Shoufeng', 'SF', 'S', '121.508955', '23.870680'),
('4234', '713526', '713500', '光复乡', '中国,台湾,花莲县,光复乡', '光复', '中国,台湾,花莲,光复', '3', '03', '976', 'Guangfu', 'GF', 'G', '121.423496', '23.669084'),
('4235', '713527', '713500', '丰滨乡', '中国,台湾,花莲县,丰滨乡', '丰滨', '中国,台湾,花莲,丰滨', '3', '03', '977', 'Fengbin', 'FB', 'F', '121.518639', '23.597080'),
('4236', '713528', '713500', '瑞穗乡', '中国,台湾,花莲县,瑞穗乡', '瑞穗', '中国,台湾,花莲,瑞穗', '3', '03', '978', 'Ruisui', 'RS', 'R', '121.375992', '23.496817'),
('4237', '713529', '713500', '富里乡', '中国,台湾,花莲县,富里乡', '富里', '中国,台湾,花莲,富里', '3', '03', '983', 'Fuli', 'FL', 'F', '121.250124', '23.179984'),
('4238', '713530', '713500', '秀林乡', '中国,台湾,花莲县,秀林乡', '秀林', '中国,台湾,花莲,秀林', '3', '03', '972', 'Xiulin', 'XL', 'X', '121.620381', '24.116642'),
('4239', '713531', '713500', '万荣乡', '中国,台湾,花莲县,万荣乡', '万荣', '中国,台湾,花莲,万荣', '3', '03', '979', 'Wanrong', 'WR', 'W', '121.407493', '23.715346'),
('4240', '713532', '713500', '卓溪乡', '中国,台湾,花莲县,卓溪乡', '卓溪', '中国,台湾,花莲,卓溪', '3', '03', '982', 'Zhuoxi', 'ZX', 'Z', '121.303422', '23.346369'),
('4241', '713600', '710000', '澎湖县', '中国,台湾,澎湖县', '澎湖', '中国,台湾,澎湖', '2', '06', '8', 'Penghu', 'PH', 'P', '119.566417', '23.569733'),
('4242', '713601', '713600', '马公市', '中国,台湾,澎湖县,马公市', '马公', '中国,台湾,澎湖,马公', '3', '06', '880', 'Magong', 'MG', 'M', '119.566499', '23.565845'),
('4243', '713621', '713600', '湖西乡', '中国,台湾,澎湖县,湖西乡', '湖西', '中国,台湾,澎湖,湖西', '3', '06', '885', 'Huxi', 'HX', 'H', '119.659666', '23.583358'),
('4244', '713622', '713600', '白沙乡', '中国,台湾,澎湖县,白沙乡', '白沙', '中国,台湾,澎湖,白沙', '3', '06', '884', 'Baisha', 'BS', 'B', '119.597919', '23.666060'),
('4245', '713623', '713600', '西屿乡', '中国,台湾,澎湖县,西屿乡', '西屿', '中国,台湾,澎湖,西屿', '3', '06', '881', 'Xiyu', 'XY', 'X', '119.506974', '23.600836'),
('4246', '713624', '713600', '望安乡', '中国,台湾,澎湖县,望安乡', '望安', '中国,台湾,澎湖,望安', '3', '06', '882', 'Wang\'an', 'WA', 'W', '119.500538', '23.357531'),
('4247', '713625', '713600', '七美乡', '中国,台湾,澎湖县,七美乡', '七美', '中国,台湾,澎湖,七美', '3', '06', '883', 'Qimei', 'QM', 'Q', '119.423929', '23.206018'),
('4248', '713700', '710000', '金门县', '中国,台湾,金门县', '金门', '中国,台湾,金门', '2', '082', '8', 'Jinmen', 'JM', 'J', '118.317089', '24.432706'),
('4249', '713701', '713700', '金城镇', '中国,台湾,金门县,金城镇', '金城', '中国,台湾,金门,金城', '3', '082', '893', 'Jincheng', 'JC', 'J', '118.316667', '24.416667'),
('4250', '713702', '713700', '金湖镇', '中国,台湾,金门县,金湖镇', '金湖', '中国,台湾,金门,金湖', '3', '082', '891', 'Jinhu', 'JH', 'J', '118.419743', '24.438633'),
('4251', '713703', '713700', '金沙镇', '中国,台湾,金门县,金沙镇', '金沙', '中国,台湾,金门,金沙', '3', '082', '890', 'Jinsha', 'JS', 'J', '118.427993', '24.481109'),
('4252', '713704', '713700', '金宁乡', '中国,台湾,金门县,金宁乡', '金宁', '中国,台湾,金门,金宁', '3', '082', '892', 'Jinning', 'JN', 'J', '118.334506', '24.45672'),
('4253', '713705', '713700', '烈屿乡', '中国,台湾,金门县,烈屿乡', '烈屿', '中国,台湾,金门,烈屿', '3', '082', '894', 'Lieyu', 'LY', 'L', '118.247255', '24.433102'),
('4254', '713706', '713700', '乌丘乡', '中国,台湾,金门县,乌丘乡', '乌丘', '中国,台湾,金门,乌丘', '3', '082', '896', 'Wuqiu', 'WQ', 'W', '118.319578', '24.435038'),
('4255', '713800', '710000', '连江县', '中国,台湾,连江县', '连江', '中国,台湾,连江', '2', '0836', '2', 'Lienchiang', 'LJ', 'L', '119.539704', '26.197364'),
('4256', '713801', '713800', '南竿乡', '中国,台湾,连江县,南竿乡', '南竿', '中国,台湾,连江,南竿', '3', '0836', '209', 'Nangan', 'NG', 'N', '119.944267', '26.144035'),
('4257', '713802', '713800', '北竿乡', '中国,台湾,连江县,北竿乡', '北竿', '中国,台湾,连江,北竿', '3', '0836', '210', 'Beigan', 'BG', 'B', '120.000572', '26.221983'),
('4258', '713803', '713800', '莒光乡', '中国,台湾,连江县,莒光乡', '莒光', '中国,台湾,连江,莒光', '3', '0836', '211', 'Juguang', 'JG', 'J', '119.940405', '25.976256'),
('4259', '713804', '713800', '东引乡', '中国,台湾,连江县,东引乡', '东引', '中国,台湾,连江,东引', '3', '0836', '212', 'Dongyin', 'DY', 'D', '120.493955', '26.366164'),
('4260', '810000', '100000', '香港特别行政区', '中国,香港特别行政区', '香港', '中国,香港', '1', '', '', 'Hong Kong', 'HK', 'H', '114.173355', '22.320048'),
('4261', '810100', '810000', '香港岛', '中国,香港特别行政区,香港岛', '香港岛', '中国,香港,香港岛', '2', '00852', '999077', 'Hong Kong Island', 'XGD', 'H', '114.177314', '22.266416'),
('4262', '810101', '810100', '中西区', '中国,香港特别行政区,香港岛,中西区', '中西区', '中国,香港,香港岛,中西区', '3', '00852', '999077', 'Central and Western District', 'ZXQ', 'C', '114.154374', '22.281981'),
('4263', '810102', '810100', '湾仔区', '中国,香港特别行政区,香港岛,湾仔区', '湾仔区', '中国,香港,香港岛,湾仔区', '3', '00852', '999077', 'Wan Chai District', 'WZQ', 'W', '114.182915', '22.276389'),
('4264', '810103', '810100', '东区', '中国,香港特别行政区,香港岛,东区', '东区', '中国,香港,香港岛,东区', '3', '00852', '999077', 'Eastern District', 'DQ', 'E', '114.255993', '22.262755'),
('4265', '810104', '810100', '南区', '中国,香港特别行政区,香港岛,南区', '南区', '中国,香港,香港岛,南区', '3', '00852', '999077', 'Southern District', 'NQ', 'S', '114.174134', '22.24676'),
('4266', '810200', '810000', '九龙', '中国,香港特别行政区,九龙', '九龙', '中国,香港,九龙', '2', '00852', '999077', 'Kowloon', 'JL', 'K', '114.17495', '22.327115'),
('4267', '810201', '810200', '油尖旺区', '中国,香港特别行政区,九龙,油尖旺区', '油尖旺', '中国,香港,九龙,油尖旺', '3', '00852', '999077', 'Yau Tsim Mong', 'YJW', 'Y', '114.173332', '22.311704'),
('4268', '810202', '810200', '深水埗区', '中国,香港特别行政区,九龙,深水埗区', '深水埗', '中国,香港,九龙,深水埗', '3', '00852', '999077', 'Sham Shui Po', 'SSB', 'S', '114.16721', '22.328171'),
('4269', '810203', '810200', '九龙城区', '中国,香港特别行政区,九龙,九龙城区', '九龙城', '中国,香港,九龙,九龙城', '3', '00852', '999077', 'Jiulongcheng', 'JLC', 'J', '114.195053', '22.32673'),
('4270', '810204', '810200', '黄大仙区', '中国,香港特别行政区,九龙,黄大仙区', '黄大仙', '中国,香港,九龙,黄大仙', '3', '00852', '999077', 'Wong Tai Sin', 'HDX', 'W', '114.19924', '22.336313'),
('4271', '810205', '810200', '观塘区', '中国,香港特别行政区,九龙,观塘区', '观塘', '中国,香港,九龙,观塘', '3', '00852', '999077', 'Kwun Tong', 'GT', 'K', '114.231268', '22.30943'),
('4272', '810300', '810000', '新界', '中国,香港特别行政区,新界', '新界', '中国,香港,新界', '2', '00852', '999077', 'New Territories', 'XJ', 'N', '114.202408', '22.341766'),
('4273', '810301', '810300', '荃湾区', '中国,香港特别行政区,新界,荃湾区', '荃湾', '中国,香港,新界,荃湾', '3', '00852', '999077', 'Tsuen Wan', 'QW', 'T', '114.122952', '22.370973'),
('4274', '810302', '810300', '屯门区', '中国,香港特别行政区,新界,屯门区', '屯门', '中国,香港,新界,屯门', '3', '00852', '999077', 'Tuen Mun', 'TM', 'T', '113.977416', '22.391047'),
('4275', '810303', '810300', '元朗区', '中国,香港特别行政区,新界,元朗区', '元朗', '中国,香港,新界,元朗', '3', '00852', '999077', 'Yuen Long', 'YL', 'Y', '114.039796', '22.443342'),
('4276', '810304', '810300', '北区', '中国,香港特别行政区,新界,北区', '北区', '中国,香港,新界,北区', '3', '00852', '999077', 'North District', 'BQ', 'N', '114.148959', '22.494086'),
('4277', '810305', '810300', '大埔区', '中国,香港特别行政区,新界,大埔区', '大埔', '中国,香港,新界,大埔', '3', '00852', '999077', 'Tai Po', 'DP', 'T', '114.171743', '22.445653'),
('4278', '810306', '810300', '西贡区', '中国,香港特别行政区,新界,西贡区', '西贡', '中国,香港,新界,西贡', '3', '00852', '999077', 'Sai Kung', 'XG', 'S', '114.27854', '22.37944'),
('4279', '810307', '810300', '沙田区', '中国,香港特别行政区,新界,沙田区', '沙田', '中国,香港,新界,沙田', '3', '00852', '999077', 'Sha Tin', 'ST', 'S', '114.191941', '22.379294'),
('4280', '810308', '810300', '葵青区', '中国,香港特别行政区,新界,葵青区', '葵青', '中国,香港,新界,葵青', '3', '00852', '999077', 'Kwai Tsing', 'KQ', 'K', '114.13932', '22.363877'),
('4281', '810309', '810300', '离岛区', '中国,香港特别行政区,新界,离岛区', '离岛', '中国,香港,新界,离岛', '3', '00852', '999077', 'Outlying Islands', 'LD', 'O', '113.945842', '22.281508'),
('4282', '820000', '100000', '澳门特别行政区', '中国,澳门特别行政区', '澳门', '中国,澳门', '1', '', '', 'Macau', 'MO', 'M', '113.54909', '22.198951'),
('4283', '820100', '820000', '澳门半岛', '中国,澳门特别行政区,澳门半岛', '澳门半岛', '中国,澳门,澳门半岛', '2', '00853', '999078', 'MacauPeninsula', 'AMBD', 'M', '113.549134', '22.198751'),
('4284', '820101', '820100', '花地玛堂区', '中国,澳门特别行政区,澳门半岛,花地玛堂区', '花地玛堂区', '中国,澳门,澳门半岛,花地玛堂区', '3', '00853', '999078', 'Nossa Senhora de Fatima', 'HDMTQ', 'N', '113.552284', '22.208067'),
('4285', '820102', '820100', '圣安多尼堂区', '中国,澳门特别行政区,澳门半岛,圣安多尼堂区', '圣安多尼堂区', '中国,澳门,澳门半岛,圣安多尼堂区', '3', '00853', '999078', 'Santo Antonio', 'SADNTQ', 'S', '113.564301', '22.12381'),
('4286', '820103', '820100', '大堂区', '中国,澳门特别行政区,澳门半岛,大堂区', '大堂', '中国,澳门,澳门半岛,大堂', '3', '00853', '999078', 'Sé', 'DT', 'S', '113.552971', '22.188359'),
('4287', '820104', '820100', '望德堂区', '中国,澳门特别行政区,澳门半岛,望德堂区', '望德堂区', '中国,澳门,澳门半岛,望德堂区', '3', '00853', '999078', 'Sao Lazaro', 'WDTQ', 'S', '113.550568', '22.194081'),
('4288', '820105', '820100', '风顺堂区', '中国,澳门特别行政区,澳门半岛,风顺堂区', '风顺堂区', '中国,澳门,澳门半岛,风顺堂区', '3', '00853', '999078', 'Sao Lourenco', 'FSTQ', 'S', '113.541928', '22.187368'),
('4289', '820200', '820000', '氹仔岛', '中国,澳门特别行政区,氹仔岛', '氹仔岛', '中国,澳门,氹仔岛', '2', '00853', '999078', 'Taipa', 'DZD', 'T', '113.577669', '22.156838'),
('4290', '820201', '820200', '嘉模堂区', '中国,澳门特别行政区,氹仔岛,嘉模堂区', '嘉模堂区', '中国,澳门,氹仔岛,嘉模堂区', '3', '00853', '999078', 'Our Lady Of Carmel\'s Parish', 'JMTQ', 'O', '113.565303', '22.149029'),
('4291', '820300', '820000', '路环岛', '中国,澳门特别行政区,路环岛', '路环岛', '中国,澳门,路环岛', '2', '00853', '999078', 'Coloane', 'LHD', 'C', '113.564857', '22.116226'),
('4292', '820301', '820300', '圣方济各堂区', '中国,澳门特别行政区,路环岛,圣方济各堂区', '圣方济各堂区', '中国,澳门,路环岛,圣方济各堂区', '3', '00853', '999078', 'St Francis Xavier\'s Parish', 'SFJGTQ', 'S', '113.559954', '22.123486'),
('10000', '140403', '140400', '潞州区', '中国,山西省,长治市,潞州区', '潞州', '中国,山西,长治,潞城,潞州', '3', '0355', '046000', 'Luzhou', 'LZ', 'L', '113.22888', '36.33414');

-- 物流公司字典表数据
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('1', '2019-01-22 14:05:17', '2019-01-22 14:05:20', NULL, '顺丰', '1', 'SFEXPRESS', NULL, '0', 'SF');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('2', '2019-01-22 14:07:38', '2019-01-22 14:07:40', NULL, '申通', '1', 'STO', NULL, '1', 'STO');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('3', '2019-01-22 14:07:48', '2019-01-22 14:07:50', NULL, '中通', '1', 'ZTO', NULL, '2', 'ZTO');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('4', '2019-01-22 14:07:48', '2019-01-22 14:07:50', NULL, '圆通', '1', 'YTO', NULL, '3', 'YTO');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('5', '2019-04-10 19:38:51', NULL, NULL, '京东快递', '1', 'JD', NULL, '4', 'jd');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('6', '2019-04-16 18:38:51', NULL, NULL, '汇通', '2', 'ht', NULL, '5', 'ht');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('7', '2019-04-22 23:55:47', NULL, NULL, '韵达', '1', 'YUNDA', NULL, '6', 'YUNDA');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('8', '2019-04-24 16:33:18', NULL, NULL, '天天', '1', 'TTKDEX', NULL, '7', 'TTKDEX');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('9', '2019-04-26 12:03:33', NULL, NULL, 'EMS', '1', 'EMS', NULL, '8', 'EMS');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('10', '2019-04-26 12:04:13', NULL, NULL, '全峰', '1', 'QFKD', NULL, '9', 'QFKD');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('11', '2019-04-26 12:54:51', NULL, NULL, '德邦', '1', 'DEPPON', NULL, '10', 'DBL');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('12', '2019-04-26 12:55:53', NULL, NULL, '国通', '1', 'GTO', NULL, '11', 'GTO');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('13', '2019-04-26 12:56:12', NULL, NULL, '如风达', '1', 'RFD', NULL, '12', 'RFDSM');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('14', '2019-04-26 12:57:55', NULL, NULL, '宅急送', '1', 'ZJS', NULL, '13', 'ZJS');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('15', '2019-04-26 12:57:58', NULL, NULL, 'EMS国际', '1', 'EMS', NULL, '14', 'emsg');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('16', '2019-04-26 12:58:02', NULL, NULL, 'Fedex国际', '1', 'FEDEXIN', NULL, '15', 'FEDEX');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('17', '2019-04-26 12:58:05', NULL, NULL, '邮政国内（挂号信', '1', 'CHINAPOST', NULL, '16', 'POST');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('18', '2019-04-26 12:58:07', NULL, NULL, 'UPS国际快递', '1', 'UPS', NULL, '17', 'ups');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('19', '2019-04-26 12:58:12', NULL, NULL, '中铁快运', '1', 'CRE', NULL, '18', 'CRE');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('20', '2019-04-26 13:01:49', NULL, NULL, '佳吉快运', '1', 'JIAJI', NULL, '19', 'CNEX');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('21', '2019-04-26 13:01:54', NULL, NULL, '速尔快递', '1', 'SURE', NULL, '20', 'suer');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('22', '2019-04-26 13:01:57', NULL, NULL, '信丰物流', '1', 'XFEXPRESS', NULL, '21', 'XFWL');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('23', '2019-04-26 13:02:01', NULL, NULL, '优速快递', '1', 'UC56', NULL, '22', 'UC');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('24', '2019-04-26 13:02:05', NULL, NULL, '中邮物流', '2', 'zhongyou', NULL, '23', 'zhongyou');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('25', '2019-04-26 13:02:07', NULL, NULL, '天地华宇', '1', 'HOAU', NULL, '24', 'tdhy');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('26', '2019-04-26 13:02:10', NULL, NULL, '安信达快递', '1', 'ANXINDA', NULL, '25', 'axd');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('27', '2019-04-26 13:02:13', NULL, NULL, '快捷速递', '2', 'kuaijie', NULL, '26', 'FAST');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('28', '2019-04-26 13:02:15', NULL, NULL, 'AAE全球专递', '1', 'AAEWEB', NULL, '27', 'aae');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('29', '2019-04-26 13:02:18', NULL, NULL, 'DHL', '1', 'DHL', NULL, '28', 'DHL');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('30', '2019-04-26 13:02:21', NULL, NULL, 'DPEX国际快递', '1', 'DPEX', NULL, '29', 'dpex');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('31', '2019-04-26 13:02:25', NULL, NULL, 'D速物流', '1', 'DEXP', NULL, '30', 'ds');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('32', '2019-04-26 13:02:27', NULL, NULL, 'FEDEX国内快递', '1', 'FEDEX', NULL, '31', 'FEDEXLY');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('33', '2019-04-26 13:02:32', NULL, NULL, 'OCS', '1', 'OCS', NULL, '32', 'ocs');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('34', '2019-04-26 13:02:35', NULL, NULL, 'TNT', '1', 'TNT', NULL, '33', 'tnt');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('35', '2019-04-26 13:08:16', NULL, NULL, '东方快递', '1', 'COE', NULL, '34', 'coe');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('36', '2019-04-26 13:08:20', NULL, NULL, '传喜物流', '2', 'cxwl', NULL, '35', 'cxwl');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('37', '2019-04-26 13:08:24', NULL, NULL, '城市100', '1', 'CITY100', NULL, '36', 'BJCS');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('38', '2019-04-26 13:08:27', NULL, NULL, '城市之星物流', '2', 'cszx', NULL, '37', 'cszx');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('39', '2019-04-26 13:08:30', NULL, NULL, '安捷快递', '2', 'aj', NULL, '38', 'aj');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('40', '2019-04-26 13:08:33', NULL, NULL, '百福东方', '1', 'EES', NULL, '39', 'bfdf');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('41', '2019-04-26 13:12:03', NULL, NULL, '程光快递', '1', 'FLYWAYEX', NULL, '40', 'chengguang');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('42', '2019-04-26 13:12:06', NULL, NULL, '递四方快递', '1', 'D4PX', NULL, '41', 'FPXSZ');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('43', '2019-04-26 13:12:09', NULL, NULL, '长通物流', '2', 'ctwl', NULL, '42', 'ctwl');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('44', '2019-04-26 13:12:13', NULL, NULL, '飞豹快递', '2', 'feibao', NULL, '43', 'feibao');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('45', '2019-04-26 13:12:16', NULL, NULL, '马来西亚（大包EMS）', '2', 'malaysiaems', NULL, '44', 'malaysiaems');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('46', '2019-04-26 13:12:22', NULL, NULL, '安能快递', '1', 'ANE', NULL, '45', 'ANE56');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('47', '2019-04-26 13:12:25', NULL, NULL, '中通快运', '1', 'ZTO56', NULL, '46', 'ZTOKY');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('48', '2019-04-26 13:12:27', NULL, NULL, '远成物流', '1', 'YCGWL', NULL, '47', 'YCGWL');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('49', '2019-04-26 13:12:30', NULL, NULL, '邮政快递', '1', 'CHINAPOST', NULL, '48', 'POSTB');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('50', '2019-04-26 13:12:32', NULL, NULL, '百世快运', '1', 'BSKY', NULL, '49', 'BEST');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('51', '2019-04-26 13:12:36', NULL, NULL, '苏宁快递', '1', 'SUNING', NULL, '50', 'SNWL');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('52', '2019-04-26 13:12:38', NULL, NULL, '安能物流', '1', 'ANE', NULL, '51', 'ANE56');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('53', '2021-04-30 09:58:21', NULL, NULL, '百世快递', '1', 'BSKY', NULL, '53', 'BEST');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('54', '2021-05-31 15:14:18', NULL, NULL, '日日顺', '1', 'RRSJK', NULL, '1', 'RRS');
INSERT INTO `ct_platform_express` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `logo`, `short_name`, `telephone`, `index`, `ali_code`) VALUES ('55', '2019-04-16 18:38:51', NULL, NULL, '极兔快递', '1', 'JTEXPRESS', NULL, '57', 'jitu');


-- 权限总表数据
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1', '0', '首页', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('2', '0', '商品审核', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('3', '0', '店铺审核', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('4', '0', '订单管理', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('5', '0', '营销管理', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('6', '0', '佣金管理', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('7', '0', '财务管理', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('8', '0', '配置管理', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('9', '0', '权限管理', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10', '0', '店铺管理', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11', '0', '商品管理', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('12', '0', '订单管理', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('13', '0', '营销管理', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('14', '0', '财务管理', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('15', '0', '权限管理', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('17', '0', '报表查询', '', 'MENU', 'OPE', '2020-11-25 17:24:32');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('18', '0', '数据管理', ' ', 'MENU', 'OPE', '2021-04-01 15:45:09');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('19', '0', '数据管理', ' ', 'MENU', 'SHOP', '2021-04-01 15:45:09');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('20', '0', '服务中心', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('21', '0', '服务中心', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('31', '0', '渠道管理', ' ', 'MENU', 'OPE', '2021-12-29 14:03:27');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('32', '0', '信审管理', ' ', 'MENU', 'OPE', '2021-12-29 14:03:27');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('100', '2', '商品审核', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('101', '3', '店铺审核', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('102', '4', '订单列表', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('103', '4', '订单关闭和退货', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('104', '4', '逾期订单', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('105', '4', '买断订单', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('106', '4', '续租订单', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('107', '4', '电审订单', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('108', '5', '模块消息', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('109', '5', '大礼包', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('110', '5', '优惠券', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('111', '5', '评论管理', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('113', '7', '佣金审批', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('114', '7', '财务结算', ' ', 'MENU', 'OPE', '2021-04-01 11:05:12');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('117', '8', 'Tab配置', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('120', '8', '分类配置', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('122', '9', '部门列表', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('123', '9', '成员管理', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('124', '10', '店铺信息', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('125', '11', '商品列表', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('126', '11', '归还地址', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('127', '11', '增值服务', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('128', '12', '订单列表', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('129', '12', '逾期订单', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('130', '12', '买断订单', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('131', '12', '续租订单', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('132', '13', '优惠券列表', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('133', '13', '大礼包', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('134', '13', '店铺营销图配置', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('135', '14', '财务结算', ' ', 'MENU', 'SHOP', '2021-04-01 11:17:07');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('137', '15', '部门列表', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('138', '15', '成员管理', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('141', '2', '租赁商品审核', '', 'MENU', 'OPE', '2020-09-05 16:46:31');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('145', '16', '点券商品列表', '', 'MENU', 'OPE', '2020-09-18 15:52:12');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('146', '16', '点券订单列表', '', 'MENU', 'OPE', '2020-09-21 19:51:42');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('147', '16', '数据看板', '', 'MENU', 'OPE', '2020-09-21 20:03:40');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('150', '9', '微信消息通知', '', 'MENU', 'SHOP', '2020-10-26 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('151', '4', '到期未归还订单', '', 'MENU', 'OPE', '2020-11-19 14:31:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('152', '12', '到期未归还订单', '', 'MENU', 'SHOP', '2020-11-19 14:31:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('153', '17', '报表查询', '', 'MENU', 'OPE', '2020-11-25 17:24:55');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('154', '18', '导出数据下载', '', 'MENU', 'OPE', '2021-04-01 15:47:29');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('155', '19', '导出数据下载', '', 'MENU', 'SHOP', '2021-04-01 15:47:29');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('156', '8', '素材中心', '', 'MENU', 'OPE', '2021-05-10 10:31:51');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('157', '8', '小程序配置', '', 'MENU', 'OPE', '2021-05-10 10:41:03');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('158', '6', '小程序佣金设置', '', 'MENU', 'OPE', '2021-05-24 16:00:23');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('159', '14', '结算明细查询', '', 'MENU', 'SHOP', '2021-07-30 11:48:15');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('160', '7', '结算明细查询', '', 'MENU', 'OPE', '2021-07-30 11:48:14');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('161', '14', '资金账户', '', 'MENU', 'SHOP', '2021-08-17 14:00:54');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('162', '7', '商家资金账户', '', 'MENU', 'OPE', '2021-08-22 19:30:39');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('163', '14', '费用结算明细', '', 'MENU', 'SHOP', '2021-08-23 17:00:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('165', '7', '费用结算明细', '', 'MENU', 'OPE', '2021-08-23 17:00:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('166', '8', '租物频道配置管理', '', 'MENU', 'OPE', '2021-09-07 11:32:09');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('167', '6', '抖音佣金设置', '', 'MENU', 'OPE', '2021-09-12 13:14:22');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('168', '2', '库存管理', '', 'MENU', 'OPE', '2021-11-16 17:57:36');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('172', '7', '渠道佣金审批', ' ', 'MENU', 'OPE', '2022-01-04 19:01:54');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('173', '7', '渠道佣金结算', ' ', 'MENU', 'OPE', '2022-01-04 19:03:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('174', '7', '渠道佣金结算明细', ' ', 'MENU', 'OPE', '2022-01-04 19:05:34');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('202', '31', '渠道分佣列表', ' ', 'MENU', 'OPE', '2021-12-29 14:27:02');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('203', '32', '信审管理', ' ', 'MENU', 'OPE', '2021-12-29 14:03:27');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('204', '206', '渠道资金账户', ' ', 'MENU', 'CHANNEL', '2023-02-22 18:43:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('205', '206', '渠道订单明细', ' ', 'MENU', 'CHANNEL', '2023-02-22 18:45:52');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('206', '0', '渠道管理', ' ', 'MENU', 'CHANNEL', '2023-02-22 18:47:51');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('207', '206', '渠道佣金结算明细', ' ', 'MENU', 'CHANNEL', '2023-02-22 18:49:24');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('208', '206', '佣金结算管理', ' ', 'MENU', 'CHANNEL', '2023-02-23 18:31:55');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('209', '31', '佣金结算管理', '', 'MENU', 'OPE', '2023-03-03 17:12:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('210', '31', '渠道订单明细', '', 'MENU', 'OPE', '2023-03-03 17:12:39');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('211', '7', '商家提现申请', '', 'MENU', 'OPE', '2023-03-29 16:36:42');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('212', '7', '商家发票申请', '', 'MENU', 'OPE', '2023-03-29 16:37:13');

INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1000', '1', '首页商品数据统计', 'home/selectProductCounts', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1001', '1', '首页订单数据统计', 'ope/order/opeOrderStatistics', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1002', '100', '获取商品审核列表', 'examineProduct/selectExaminePoroductList', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1003', '100', '商品审核确认', 'examineProduct/examineProductConfirm', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1004', '100', '商品审核详细', 'examineProduct/selectExamineProductDetail', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1005', '100', '查询商品编辑信息', 'examineProduct/selectExamineProductEdit', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1006', '100', '商品信息修改', 'examineProduct/updateExamineProduct', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1007', '101', '获取店铺审核列表', 'opeShop/toShopExamineList', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1008', '101', '店铺审核确认', 'opeShop/toShopExamineConform', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1009', '101', '查询店铺信息', 'opeShop/selectShopInfo', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1010', '101', '冻结(解冻)店铺信息', 'opeShop/lockedShop', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1011', '101', '导出店铺审核信息', 'opeShop/exportShop', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1012', '102', '获取订单列表', 'ope/order/queryOpeOrderByCondition', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1013', '102', '查看订单详细信息', 'ope/order/queryOpeUserOrderDetail', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1014', '102', '添加备注', 'ope/order/orderRemark', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1015', '102', '修改地址', 'ope/order/opeOrderAddressModify', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1016', '102', '订单导出', 'ope/order/exportOpeAllUserOrders', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1017', '103', '查询带关闭订单列表', 'ope/order/queryPendingOrderClosureList', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1018', '103', '关闭订单并退款', 'ope/order/closeUserOrderAndRefundPrice', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1019', '104', '查询逾期订单列表', 'ope/order/queryOpeOverDueOrdersByCondition', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1020', '105', '查询买断订单列表', 'ope/order/queryOpeBuyOutOrdersByCondition', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1022', '105', '买断订单导出', 'ope/order/exportOpeBuyOutUserOrders', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1023', '106', '查询续租订单列表', 'ope/order/queryReletOrderByCondition', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1024', '106', '续租订单导出', 'ope/order/exportReletOrders', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1025', '107', '查询电审订单列表', 'ope/order/queryTelephoneAuditOrder', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1026', '108', '获取模块消息列表', 'messageTem/getMessageTemplateByPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1027', '108', '运营模板消息删除', 'messageTem/delMessageTemplate', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1028', '108', '运营模板消息详情', 'messageTem/getMessageTemplateById', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1029', '108', '运营模板消息新增', 'messageTem/saveMessageTemplate', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1030', '108', '运营模板消息修改', 'messageTem/updateMessageTemplate', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1031', '109', '获取大礼包列表', 'couponPackage/queryPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1032', '109', '添加大礼包', 'couponPackage/add', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1033', '109', '编辑大礼包页面数据获取', 'couponPackage/getUpdatePageData', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1034', '109', '编辑大礼包', 'couponPackage/modify', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1035', '109', '删除大礼包', 'couponPackage/delete', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1036', '110', '获取优惠券列表', 'couponTemplate/queryPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1037', '110', '添加优惠券', 'couponTemplate/add', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1038', '110', '编辑优惠券页面数据获取', 'couponTemplate/getUpdatePageData', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1039', '110', '编辑优惠券', 'couponTemplate/modify', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1040', '110', '优惠券详情页面数据获取', 'couponTemplate/getDetail', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1041', '110', '删除优惠券', 'couponTemplate/delete', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1042', '110', '查看优惠券领取详情', 'userCoupon/queryPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1043', '111', '查询部落评论分页', 'tribeComment/queryTribeCommentPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1044', '111', '批量生效部落评论', 'tribeComment/batchEffectUserComment', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1045', '111', '部落评论客服回复确认', 'tribeComment/confirmTribeCommment', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1046', '111', '删除部落评论', 'tribeComment/deleteUserComment', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1047', '111', '下载部落评论模板', 'tribeComment/exportUserComment', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1048', '111', '导入部落评论', 'tribeComment/importUserComment', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1049', '111', '查询部落评论编辑信息', 'tribeComment/selectTribeCommentEdit', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1051', '111', '分页查询运营导入商品评论', 'productEvaluation/queryProductEvaluationPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1052', '111', '批量生效商品评论', 'productEvaluation/batchEffectProductEvaluation', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1053', '111', '商品评论客服回复确认', 'productEvaluation/confirmProductEvaluation', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1054', '111', '删除商品评论', 'productEvaluation/deleteProductEvaluation', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1055', '111', '导入商品评论', 'productEvaluation/importProductEvaluation', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1056', '113', '分页获取佣金设置列表', 'splitBillConfig/page', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1057', '113', '添加佣金设置', 'splitBillConfig/add', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1058', '113', '佣金设置审核', 'splitBillConfig/audit', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1059', '113', '佣金设置详情', 'splitBillConfig/detail', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1060', '113', '添加佣金设置页面获取店铺列表接口', 'splitBillConfig/getShopList', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1061', '113', '更新佣金设置', 'splitBillConfig/update', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1062', '114', '分页查询常规账单', 'splitBill/rentPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1063', '114', '查询租赁分账详细信息', 'splitBill/rentDetail', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1064', '114', '分页查询买断账单列表', 'splitBill/buyOutPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1065', '114', '查询买断分账详细信息', 'splitBill/buyOutDetail', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1066', '114', '分账代发', 'splitBill/paySplitBill', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1079', '117', '查询Tab列表', 'index/getTabList', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1080', '117', '新增Tab', 'index/saveIndexTab', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1081', '117', '更新tab', 'index/updateIndexTabById', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1082', '117', '删除tab', 'index/delIndexTab', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1083', '117', '删除tab下的产品', 'index/delIndexPrdouct', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1084', '117', 'tab下查询产品', 'index/getIndexProductListByPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1085', '117', '分页获得tab列表', 'index/getIndexTabListByPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1086', '117', 'tab下新增产品', 'index/saveIndexPrdouct', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1098', '120', '添加前台类目数据', 'category/addOperateCategory', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1099', '120', '获取待添加的分类商品', 'category/checkedProduct', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1100', '120', '删除类目下商品', 'category/deleteProduct', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1101', '120', '多商品批量添加', 'category/manyInsertCategoryProduct', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1102', '120', '二级分页类目下分页', 'category/queryOpeCategoryPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1103', '120', '删除前台类目', 'category/removeOperateCategory', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1104', '120', '查询前台分类数据(组装)', 'category/selectAll', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1105', '120', '查询前台类目商品', 'category/selectOperateCategoryProduct', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1106', '120', '运营查询一级类目查询前台分类数据', 'category/selectParentCategoryList', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1107', '120', '查询前台分类数据', 'category/selectReceptionCategoryList', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1108', '120', '修改前台类目', 'category/updateOperateCategory', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1121', '122', '获取部门列表', 'department/queryPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1122', '122', '添加部门', 'department/add', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1123', '122', '删除部门', 'department/delete', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1124', '122', '获取部门详细信息', 'department/queryOne', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1125', '122', '编辑部门', 'department/update', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1126', '122', '权限设置页面', 'department/authPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1127', '122', '提交权限更新', 'department/updateAuth', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1128', '122', '获取部门名称和列表', 'department/queryAll', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1129', '123', '获取成员列表', 'user/queryBackstageUserPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1130', '123', '添加成员', 'user/addBackstageUser', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1131', '123', '删除成员', 'user/delete', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1132', '123', '获取成员详细信息', 'user/queryBackstageUserDetail', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1133', '123', '编辑成员', 'user/modifyBackstageUser', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1134', '123', '权限设置页面', 'user/authPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1135', '123', '提交权限更新', 'user/updateAuth', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1136', '124', '修改/新增 商家店铺企业资质信息', 'busShop/updateShopAndEnterpriseInfo', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1137', '124', '根据店铺Id查询新增商品的规则模版和归还地址', 'busShop/selectShopRuleAndGiveBackByShopId', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1138', '124', '商家店铺详情', 'busShop/selectBusShopInfo', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1139', '124', '文件上传接口', 'busShop/doUpLoadwebp', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1140', '125', '查询商品列表', 'product/selectBusinessPrdouct', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1141', '125', '商家后台复制商品', 'product/busCopyProduct', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1142', '125', '商家后台新增商品', 'product/busInsertProduct', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1143', '125', '更改成下架状态', 'product/busUpdateProductByDown', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1144', '125', '商品后台删除', 'product/busUpdateProductByRecycleDel', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1145', '125', '更改上架状态', 'product/busUpdateProductByUp', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1146', '125', '商家后台根据条件导出查询产品', 'product/exportBusinessPrdouct', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1147', '125', '查询平台规格', 'product/selectAllPlatformSpec', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1148', '125', '商家后台根据条件查询tab数据统计', 'product/selectBusinessPrdouctCounts', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1149', '125', '商品审核结果详情', 'product/selectExamineProductAuditLog', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1150', '125', '商家后台编辑商品时查询', 'product/selectProductEdit', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1151', '125', '商家后台修改商品', 'product/updateBusProduct', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1152', '126', '商家查询归还地址', 'shopAddress/selectShopGiveBackAddressListByShopId', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1153', '126', '根据店铺Id查询新增商品的规则模版和归还地址', 'busShop/selectShopRuleAndGiveBackByShopId', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1154', '126', '商家后台新增归还地址', 'shopAddress/saveShopGiveBackAddress', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1155', '126', '商家后台删除租用归还地址', 'shopAddress/delShopGiveBackAddressById', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1156', '126', '更新归还地址', 'shopAddress/updateShopGiveBackAddressById', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1157', '127', '增值服务列表', 'shopAdditionalServices/selectShopAdditionalServicesList', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1158', '127', '删除增值服务', 'shopAdditionalServices/deletShopAdditionService', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1159', '127', '新增/修改商家增值服务', 'shopAdditionalServices/insertShopAdditionServices', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1160', '129', '查询逾期订单列表', 'business/order/queryOpeOverDueOrdersByCondition', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1161', '130', '查询买断订单列表', 'business/order/queryOpeBuyOutOrdersByCondition', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1162', '130', '查询买断订单详情', 'business/order/queryOpeBuyOutOrderDetail', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1163', '131', '查询续租订单', 'business/order/queryReletOrderByCondition', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1164', '131', '查询续租订单详情', 'business/order/queryUserReletOrderDetail', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1165', '132', '获取优惠券列表', 'couponTemplate/queryPage', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1166', '132', '添加优惠券', 'couponTemplate/add', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1167', '132', '编辑优惠券页面数据获取', 'couponTemplate/getUpdatePageData', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1168', '132', '编辑优惠券', 'couponTemplate/modify', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1169', '132', '优惠券详情页面数据获取', 'couponTemplate/getDetail', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1170', '132', '删除优惠券', 'couponTemplate/delete', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1171', '132', '查看优惠券领取详情', 'userCoupon/queryPage', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1172', '133', '获取大礼包列表', 'couponPackage/queryPage', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1173', '133', '添加大礼包', 'couponPackage/add', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1174', '133', '编辑大礼包页面数据获取', 'couponPackage/getUpdatePageData', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1175', '133', '编辑大礼包', 'couponPackage/modify', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1176', '133', '删除大礼包', 'couponPackage/delete', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1177', '135', '查看常规帐单列表', 'splitBill/rentPage', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1178', '135', '查询租赁分账详细信息', 'splitBill/rentDetail', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1179', '135', '查询买断账单列表', 'splitBill/buyOutPage', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1180', '135', '查询买断分账详细信息', 'splitBill/buyOutDetail', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1181', '137', '部门列表', 'department/queryPage', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1182', '137', '添加部门', 'department/add', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1183', '137', '删除部门', 'department/delete', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1184', '137', '获取部门详细信息', 'department/queryOne', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1185', '137', '编辑部门', 'department/update', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1186', '137', '获取部门名称和列表', 'department/queryAll', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1187', '138', '查看成员列表', 'user/queryBackstageUserPage', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1188', '138', '添加成员', 'user/addBackstageUser', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1189', '138', '删除成员', 'user/delete', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1190', '138', '获取成员详细信息', 'user/queryBackstageUserDetail', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1191', '138', '编辑成员', 'user/modifyBackstageUser', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1192', '100', '商品审核获取商品类目', 'examineProduct/findCategories', 'FUNCTION', 'OPE', '2020-08-28 02:07:44');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1193', '101', '查询店铺信息', 'busShop/selectBusShopInfo', 'FUNCTION', 'OPE', '2020-08-28 02:11:59');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1194', '102', '查询平台信息', 'platform/selectPlateformChannel', 'FUNCTION', 'OPE', '2020-08-28 02:14:14');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1195', '128', '查询订单列表', 'business/order/queryOrderByCondition', 'FUNCTION', 'SHOP', '2020-08-28 10:40:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1196', '1', '上传图片', 'busShop/doUpLoadwebp', 'FUNCTION', 'OPE', '2020-08-28 10:49:34');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1197', '12', '查询渠道信息', 'platform/selectPlateformChannel', 'FUNCTION', 'SHOP', '2020-08-28 10:49:54');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1198', '128', '查询逾期订单列表', 'business/order/queryOverDueOrdersByCondition', 'FUNCTION', 'SHOP', '2020-08-28 10:52:25');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1199', '117', 'tab下新增产品获取产品', 'index/ableProduct', 'FUNCTION', 'OPE', '2020-08-28 10:53:28');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1200', '128', '查询买断订单列表', 'business/order/queryBuyOutOrdersByCondition', 'FUNCTION', 'SHOP', '2020-08-28 10:55:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1201', '128', '查询续租订单列表', 'business/order/queryReletOrderByCondition', 'FUNCTION', 'SHOP', '2020-08-28 10:56:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1202', '128', '查询物流公司', 'platformExpress/selectExpressList', 'FUNCTION', 'SHOP', '2020-08-28 10:58:27');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1203', '128', '查询订单详情', 'business/order/queryBusinessUserOrderDetail', 'FUNCTION', 'SHOP', '2020-08-28 11:00:21');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1204', '128', '查询买断订单详情', 'business/order/queryBuyOutOrderDetail', 'FUNCTION', 'SHOP', '2020-08-28 11:02:12');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1205', '128', '商家备注', 'business/order/orderRemark', 'FUNCTION', 'SHOP', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1206', '128', '商家常规订单导出', 'business/order/exportOpeAllUserOrders', 'FUNCTION', 'SHOP', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1207', '128', '商家买断订单导出', 'business/order/exportOpeBuyOutUserOrders', 'FUNCTION', 'SHOP', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1208', '128', '商家逾期订单导出', 'business/order/exportOpeOverDueUserOrders', 'FUNCTION', 'SHOP', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1209', '128', '商家续租订单导出', 'business/order/exportReletOrders', 'FUNCTION', 'SHOP', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1210', '128', '查看信用报告分', 'business/order/querySiriusReportByUid', 'FUNCTION', 'SHOP', '2020-08-28 11:33:06');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1211', '128', '商家关单', 'business/order/businessClosePayedOrder', 'FUNCTION', 'SHOP', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1212', '128', '商家发货', 'business/order/orderDelivery', 'FUNCTION', 'SHOP', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1213', '128', '商家查看物流', 'business/order/queryExpressInfo', 'FUNCTION', 'SHOP', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1214', '128', '商家出具结算单', 'business/order/merchantsIssuedStatements', 'FUNCTION', 'SHOP', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1215', '1', '首页用户数据统计', 'user/getUserStatistics', 'FUNCTION', 'OPE', '2020-08-28 12:56:19');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1216', '1', '查询地址字典', 'district/selectDistrict', 'FUNCTION', 'OPE', '2020-08-28 13:34:27');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1217', '125', '查询地址字典', 'district/selectDistrict', 'FUNCTION', 'SHOP', '2020-08-28 13:44:34');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1224', '117', 'tab下新增产品获取产品V1', 'index/ableProductV1', 'FUNCTION', 'OPE', '2020-08-28 15:17:30');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1225', '102', '运营查询备注', 'ope/order/queryOrderRemark', 'FUNCTION', 'OPE', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1226', '128', '商家查询备注', 'business/order/queryOrderRemark', 'FUNCTION', 'SHOP', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1227', '114', '常规账单导出', 'splitBill/rentExport', 'FUNCTION', 'OPE', '2020-08-28 15:39:42');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1228', '114', '买断账单导出', 'splitBill/buyOutExport', 'FUNCTION', 'OPE', '2020-08-28 15:40:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1229', '135', '常规账单导出', 'splitBill/rentExport', 'FUNCTION', 'SHOP', '2020-08-28 15:43:22');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1230', '135', '买断账单导出', 'splitBill/buyOutExport', 'FUNCTION', 'SHOP', '2020-08-28 15:43:42');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1231', '102', '运营电审审核', 'ope/order/telephoneAuditOrder', 'FUNCTION', 'OPE', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1232', '102', '下载租赁协议', 'ope/order/contractReport', 'FUNCTION', 'OPE', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1233', '102', '查询物流信息', 'ope/order/queryExpressInfo', 'FUNCTION', 'OPE', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1234', '102', '运营查询风控报告', 'ope/order/querySiriusReportByUid', 'FUNCTION', 'OPE', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1235', '105', '运营查询买断详情', 'ope/order/queryOpeBuyOutOrderDetail', 'FUNCTION', 'OPE', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1236', '106', '查询续租订单详情', 'ope/order/queryUserReletOrderDetail', 'FUNCTION', 'OPE', '2020-08-28 21:44:07');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1237', '134', '店铺营销配置分页', 'shopbanner/queryOpeIndexShopBannerPage', 'FUNCTION', 'SHOP', '2020-08-29 13:30:51');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1238', '134', '新增商家详情轮播配置', 'shopbanner/addOpeIndexShopBanner', 'FUNCTION', 'SHOP', '2020-08-29 13:32:21');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1239', '134', '删除商家详情轮播配置', 'shopbanner/deleteOpeIndexShopBanner', 'FUNCTION', 'SHOP', '2020-08-29 13:33:47');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1240', '134', '更新商家详情轮播配置', 'shopbanner/modifyOpeIndexShopBanner', 'FUNCTION', 'SHOP', '2020-08-29 13:33:51');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1296', '2', '租赁商品审核', '', 'MENU', 'OPE', '2020-09-05 16:46:31');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1305', '2', '租赁商品审核', '', 'MENU', 'OPE', '2020-09-05 16:46:31');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1323', '125', '商品审核查看类目', 'examineProduct/findCategories', 'FUNCTION', 'SHOP', '2020-09-15 18:17:53');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1324', '137', '权限设置页面', 'department/authPage', 'FUNCTION', 'SHOP', '2020-09-24 11:55:07');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1325', '137', '提交权限更新', 'department/updateAuth', 'FUNCTION', 'SHOP', '2020-09-24 14:12:31');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1326', '138', '权限设置页面', 'user/authPage', 'FUNCTION', 'SHOP', '2020-09-24 14:14:35');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1327', '138', '提交权限更新', 'user/updateAuth', 'FUNCTION', 'SHOP', '2020-09-24 14:16:08');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1329', '146', '查询物流公司', 'platformExpress/selectExpressList', 'FUNCTION', 'OPE', '2020-09-23 10:54:54');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1330', '146', '获取兑换订单拒绝原因', 'exchangeOrder/refuseReason', 'FUNCTION', 'OPE', '2020-09-22 18:40:56');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1332', '147', '兑换订单数据看板', 'home/stepStatistics', 'FUNCTION', 'OPE', '2020-09-21 19:55:59');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1333', '146', '兑换订单拒绝', 'exchangeOrder/refuse', 'FUNCTION', 'OPE', '2020-09-21 19:54:35');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1334', '146', '兑换订单发货', 'exchangeOrder/delivery', 'FUNCTION', 'OPE', '2020-09-21 19:54:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1335', '146', '查询兑换订单详情', 'exchangeOrder/detail', 'FUNCTION', 'OPE', '2020-09-21 19:54:32');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1336', '146', '分页查询兑换订单', 'exchangeOrder/page', 'FUNCTION', 'OPE', '2020-09-21 19:54:31');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1342', '147', '兑换订单数据看板数据导出', 'home/stepStatisticsExport', 'FUNCTION', 'OPE', '2020-10-19 11:27:14');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1343', '132', 'tab下新增产品获取产品V1', 'index/ableProductV1', 'FUNCTION', 'SHOP', '2020-10-26 15:13:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1344', '110', '添加大礼包获取优惠券', 'couponTemplate/getAssignAbleTemplate', 'FUNCTION', 'OPE', '2020-10-27 11:38:38');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1345', '132', '添加大礼包获取优惠券', 'couponTemplate/getAssignAbleTemplate', 'FUNCTION', 'SHOP', '2020-10-27 11:38:40');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1346', '0', '订单列表订单统计', 'business/order/businessOrderStatistics', 'FUNCTION', 'SHOP', '2020-10-27 16:19:05');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1347', '128', '查询订单操作节点', 'business/order/queryOrderStatusTransfer', 'FUNCTION', 'SHOP', '2020-10-27 16:19:05');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1348', '102', '查询审批记录', 'ope/order/queryOrderAuditRecord', 'FUNCTION', 'OPE', '2020-10-27 16:19:05');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1349', '102', '查询订单操作节点', 'ope/order/queryOrderStatusTransfer', 'FUNCTION', 'OPE', '2020-10-27 16:19:05');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1350', '128', '查询审批记录', 'business/order/queryOrderAuditRecord', 'FUNCTION', 'SHOP', '2020-10-27 16:19:06');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1351', '111', '获取小程序评论分页', 'appletsComment/queryAppletsCommentPage', 'FUNCTION', 'OPE', '2020-10-28 15:00:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1352', '111', '录入小程序处理信息', 'appletsComment/modifyAppletsComment', 'FUNCTION', 'OPE', '2020-10-28 15:00:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1353', '111', '获取小程序订单投诉分页', 'orderComplaints/queryOrderComplaintsPage', 'FUNCTION', 'OPE', '2020-10-28 15:00:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1354', '111', '获取小程序订单投诉详情', 'orderComplaints/queryOrderComplaintsDetail', 'FUNCTION', 'OPE', '2020-10-28 15:00:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1355', '111', '录入小程序订单投诉处理结果', 'orderComplaints/modifyOrderComplaints', 'FUNCTION', 'OPE', '2020-10-28 15:00:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1356', '111', '获取订单投诉类型集合', 'orderComplaints/getOrderComplaintsTypes', 'FUNCTION', 'OPE', '2020-10-28 15:00:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1358', '150', '获取微信用户分页', 'weixin/queryUserWeixinPage', 'FUNCTION', 'SHOP', '2020-10-26 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1359', '150', '修改微信用户信息', 'weixin/modifyUserWeixin', 'FUNCTION', 'SHOP', '2020-10-26 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1360', '150', '删除微信用户信息', 'weixin/deleteUserWeixin', 'FUNCTION', 'SHOP', '2020-10-26 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1361', '1', '首页订单数据统计', 'ope/order/queryOrderReport', 'FUNCTION', 'OPE', '2020-10-30 15:30:01');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1362', '128', '商家电审审核', 'business/order/telephoneAuditOrder', 'FUNCTION', 'SHOP', '2020-10-30 17:21:12');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1363', '128', '商家修改发货信息', 'business/order/opeOrderAddressModify', 'FUNCTION', 'SHOP', '2020-10-30 17:21:12');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1364', '110', '导出优惠券券码', 'couponTemplate/exportEntityNum', 'FUNCTION', 'OPE', '2020-10-30 20:44:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1365', '111', '查看评论', 'productEvaluation/selectExamineProductEdit', 'FUNCTION', 'OPE', '2020-11-02 10:26:42');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1366', '8', '短信配置', '', 'MENU', 'OPE', '2020-10-26 00:00:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1367', '1366', '分页查询布云短信模板', 'buyunSmsTemplate/queryBuyunSmsTemplatePage', 'FUNCTION', 'OPE', '2020-10-26 00:00:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1368', '1366', '新增布云短信模板', 'buyunSmsTemplate/addBuyunSmsTemplate', 'FUNCTION', 'OPE', '2020-10-26 00:00:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1369', '1366', '删除布云短信模板', 'buyunSmsTemplate/deleteBuyunSmsTemplateById', 'FUNCTION', 'OPE', '2020-10-26 00:00:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1370', '1366', '更新布云短信模板', 'buyunSmsTemplate/modifyBuyunSmsTemplate', 'FUNCTION', 'OPE', '2020-10-26 00:00:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1371', '1366', '查询布云短信模板', 'buyunSmsTemplate/queryBuyunSmsTemplateDetail', 'FUNCTION', 'OPE', '2020-10-26 00:00:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1372', '110', '读取excel', 'couponTemplate/readPhoneFromExcel', 'FUNCTION', 'OPE', '2020-11-10 17:41:52');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1373', '132', '读取excel', 'couponTemplate/readPhoneFromExcel', 'FUNCTION', 'SHOP', '2020-11-10 17:42:03');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1374', '102', '查询账单信息', 'ope/order/queryOrderStagesDetail', 'FUNCTION', 'OPE', '2020-11-12 10:48:16');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1375', '128', '查询账单信息', 'business/order/queryOrderStagesDetail', 'FUNCTION', 'SHOP', '2020-11-12 10:50:15');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1376', '124', '签署店铺合同', 'busShop/signShopContract', 'FUNCTION', 'SHOP', '2020-11-18 15:33:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1377', '124', '获取协议内容', 'busShop/getShopContractData', 'FUNCTION', 'SHOP', '2020-11-18 15:33:28');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1378', '128', '签署订单合同', 'business/order/generateOrderContract', 'FUNCTION', 'SHOP', '2020-11-18 15:35:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1379', '128', '根据code获取配置', 'config/getByCode', 'FUNCTION', 'SHOP', '2020-11-18 15:36:47');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1380', '1394', '系统配置-更新', 'config/update', 'FUNCTION', 'OPE', '2020-11-18 15:39:36');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1381', '1394', '系统配置-查看', 'config/page', 'FUNCTION', 'OPE', '2020-11-18 15:39:35');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1382', '1394', '系统配置-获取', 'config/getByCode', 'FUNCTION', 'OPE', '2020-11-18 15:39:34');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1383', '102', '添加催收记录', 'ope/order/orderHasten', 'FUNCTION', 'OPE', '2020-11-18 15:57:04');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1384', '102', '查看催收记录', 'ope/order/queryOrderHasten', 'FUNCTION', 'OPE', '2020-11-18 15:57:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1385', '128', '查看催收记录', 'ope/order/queryOrderHasten', 'FUNCTION', 'SHOP', '2020-11-18 16:00:24');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1386', '128', '添加催收记录', 'ope/order/orderHasten', 'FUNCTION', 'SHOP', '2020-11-18 16:00:37');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1387', '151', '查询到期未归还订单', 'ope/order/queryWaitingGiveBackOrder', 'FUNCTION', 'OPE', '2020-11-19 09:50:49');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1388', '152', '查询到期未归还订单', 'business/order/queryWaitingGiveBackOrder', 'FUNCTION', 'SHOP', '2020-11-19 09:50:49');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1389', '151', '导出到期未归还订单', 'ope/order/exportWaitingGiveBack', 'FUNCTION', 'OPE', '2020-11-19 14:50:59');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1390', '152', '导出到期未归还订单', 'business/order/exportWaitingGiveBack', 'FUNCTION', 'SHOP', '2020-11-19 14:50:59');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1391', '8', '公告配置', '', 'MENU', 'OPE', '2020-11-23 11:07:04');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1392', '1391', '修改公告信息', 'notice/updateNotice', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1393', '1391', '查询公告信息', 'notice/selectNotice', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1394', '6', '费用设设置', '', 'MENU', 'OPE', '2020-11-23 15:46:09');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1396', '153', '报表查询', 'ope/reportForm/query', 'FUNCTION', 'OPE', '2020-11-25 17:26:02');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1397', '117', 'tab下修改产品排序', 'index/updateIndexPrdouctSort', 'FUNCTION', 'OPE', '2020-12-11 13:55:13');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1398', '8', '素材配置', ' ', 'MENU', 'OPE', '2020-12-24 10:16:42');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1399', '8', '活动配置', ' ', 'MENU', 'OPE', '2020-12-24 10:17:14');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1400', '1398', '新增素材', 'activityMaterial/addActivityConfigMaterial', 'FUNCTION', 'OPE', '2020-12-24 10:18:49');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1401', '1398', '更新素材', 'activityMaterial/modifyActivityConfigMaterial', 'FUNCTION', 'OPE', '2020-12-24 10:19:20');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1402', '1398', '查询一条活动素材', 'activityMaterial/queryActivityConfigMaterialDetail', 'FUNCTION', 'OPE', '2020-12-24 10:19:20');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1403', '1398', '分页查询活动素材', 'activityMaterial/queryActivityConfigMaterialPage', 'FUNCTION', 'OPE', '2020-12-24 10:19:20');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1404', '1399', '新增活动', 'activityConfig/addActivity', 'FUNCTION', 'OPE', '2020-12-24 10:19:20');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1405', '1399', '删除活动', 'activityConfig/deleteActivity', 'FUNCTION', 'OPE', '2020-12-24 10:19:20');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1406', '1399', '查询活动列表', 'activityConfig/listActivity', 'FUNCTION', 'OPE', '2020-12-24 10:19:20');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1407', '1399', '修改活动详情', 'activityConfig/modifyActivity', 'FUNCTION', 'OPE', '2020-12-24 10:19:20');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1408', '1399', '查询活动详情', 'activityConfig/queryActivityDetail', 'FUNCTION', 'OPE', '2020-12-24 10:19:20');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1409', '110', '添加券码券领取链接', 'couponTemplate/addBindUrl', 'FUNCTION', 'OPE', '2021-01-06 16:39:47');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1424', '102', '平台确认收货', 'ope/order/forceConfirmReceipt', 'FUNCTION', 'OPE', '2021-01-25 10:58:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1425', '1391', '查看小程序公告信息', 'notice/selectApiNotice', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1426', '1391', '修改小程序公告信息', 'notice/updateApiNotice', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1427', '104', '导出逾期订单列表', 'ope/order/exportOpeOverDueUserOrders', 'FUNCTION', 'OPE', '2021-03-02 09:39:32');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1428', '1195', '商家用户自提', 'business/order/orderPickUp', 'FUNCTION', 'SHOP', '2021-03-18 18:11:56');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1429', '1195', '商家确认归还', 'business/order/businessConfirmReturnOrder', 'FUNCTION', 'SHOP', '2021-03-18 19:10:14');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1430', '1195', '商家确认归还', 'business/order/businessConfirmReturnOrder', 'FUNCTION', 'SHOP', '2021-03-18 19:13:07');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1440', '20', '常见问题', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1441', '21', '常见问题', '', 'MENU', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('1442', '8', '常见问题配置', '', 'MENU', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10142', '114', '财务结算查看备注列表', 'accountPeriod/listRemark', 'FUNCTION', 'OPE', '2021-04-01 11:08:59');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10143', '114', '财务结算添加备注', 'accountPeriod/addRemark', 'FUNCTION', 'OPE', '2021-04-01 11:09:06');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10144', '114', '查看财务结算', 'accountPeriod/queryAccountPeriodDetail', 'FUNCTION', 'OPE', '2021-04-01 11:09:16');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10145', '114', '分页查询财务结算', 'accountPeriod/queryAccountPeriodPage', 'FUNCTION', 'OPE', '2021-04-01 11:09:25');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10146', '114', '手动提交支付', 'accountPeriod/submitPay', 'FUNCTION', 'OPE', '2021-04-01 11:09:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10147', '114', '提交审核', 'accountPeriod/submitAudit', 'FUNCTION', 'OPE', '2021-04-01 11:09:40');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10148', '114', '提交结算', 'accountPeriod/submitSettle', 'FUNCTION', 'OPE', '2021-04-01 11:09:48');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10150', '135', '分页查询财务结算', 'accountPeriod/queryAccountPeriodPage', 'FUNCTION', 'SHOP', '2021-04-01 11:23:20');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10151', '135', '查看财务结算', 'accountPeriod/queryAccountPeriodDetail', 'FUNCTION', 'SHOP', '2021-04-01 11:23:27');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10153', '114', '结算-常规订单导出', 'export/accountPeriodRent', 'FUNCTION', 'OPE', '2021-04-01 11:31:48');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10154', '114', '结算-买断订单导出', 'export/accountPeriodBuyOut', 'FUNCTION', 'OPE', '2021-04-01 11:31:53');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10156', '114', '查看支付凭证', 'accountPeriod/selectPayInfo', 'FUNCTION', 'OPE', '2021-04-01 11:34:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10157', '135', '查看支付凭证', 'accountPeriod/selectPayInfo', 'FUNCTION', 'SHOP', '2021-04-01 12:44:53');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10158', '135', '常规订单查看明细', 'accountPeriod/rent', 'FUNCTION', 'SHOP', '2021-04-01 12:47:39');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10159', '135', '买断订单查看明细', 'accountPeriod/buyOut', 'FUNCTION', 'SHOP', '2021-04-01 12:47:47');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10161', '135', '常规订单导出', 'export/accountPeriodRent', 'FUNCTION', 'SHOP', '2021-04-01 12:49:55');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10162', '135', '买断订单导出', 'export/accountPeriodBuyOut', 'FUNCTION', 'SHOP', '2021-04-01 12:50:01');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10164', '135', '常规订单查看单个明细', 'accountPeriod/rentDetail', 'FUNCTION', 'SHOP', '2021-04-01 12:53:22');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10165', '135', '买断订单查看单个明细', 'accountPeriod/buyOutDetail', 'FUNCTION', 'SHOP', '2021-04-01 12:53:30');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10167', '114', '常规订单查看明细', 'accountPeriod/rent', 'FUNCTION', 'OPE', '2021-04-01 12:47:39');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10168', '114', '买断订单查看明细', 'accountPeriod/buyOut', 'FUNCTION', 'OPE', '2021-04-01 12:47:47');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10170', '114', '常规订单查看单个明细', 'accountPeriod/rentDetail', 'FUNCTION', 'OPE', '2021-04-01 12:53:22');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10171', '114', '买断订单查看单个明细', 'accountPeriod/buyOutDetail', 'FUNCTION', 'OPE', '2021-04-01 12:53:30');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10175', '154', '查看导出历史', 'export/exportHistory', 'FUNCTION', 'OPE', '2021-04-01 15:49:47');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10176', '154', '导出订单', 'export/rentOrder', 'FUNCTION', 'OPE', '2021-04-01 15:49:45');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10177', '154', '导出逾期订单', 'export/overdueOrder', 'FUNCTION', 'OPE', '2021-04-01 15:49:42');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10178', '154', '到期未归还订单导出', 'export/notGiveBack', 'FUNCTION', 'OPE', '2021-04-01 15:49:40');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10179', '154', '买断订单导出', 'export/buyOut', 'FUNCTION', 'OPE', '2021-04-01 15:49:37');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10181', '155', '查看导出历史', 'export/exportHistory', 'FUNCTION', 'SHOP', '2021-04-01 15:49:47');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10182', '155', '导出订单', 'export/rentOrder', 'FUNCTION', 'SHOP', '2021-04-01 15:49:45');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10183', '155', '导出逾期订单', 'export/overdueOrder', 'FUNCTION', 'SHOP', '2021-04-01 15:49:42');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10184', '155', '到期未归还订单导出', 'export/notGiveBack', 'FUNCTION', 'SHOP', '2021-04-01 15:49:40');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10185', '155', '买断订单导出', 'export/buyOut', 'FUNCTION', 'SHOP', '2021-04-01 15:49:37');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10188', '156', '分页查询素材中心分类信息', 'materialCenter/pageCategory', 'FUNCTION', 'OPE', '2021-05-10 10:43:01');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10189', '156', '添加分类', 'materialCenter/addCategory', 'FUNCTION', 'OPE', '2021-05-10 10:43:02');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10190', '156', '删除分类', 'materialCenter/deleteCategory', 'FUNCTION', 'OPE', '2021-05-10 10:43:03');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10191', '156', '分页查询素材中心素材信息', 'materialCenter/pageItem', 'FUNCTION', 'OPE', '2021-05-10 10:43:05');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10192', '156', '添加素材', 'materialCenter/addItem', 'FUNCTION', 'OPE', '2021-05-10 10:43:06');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10193', '156', '删除素材', 'materialCenter/detailItem', 'FUNCTION', 'OPE', '2021-05-10 10:43:07');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10194', '156', '更新素材', 'materialCenter/updateItem', 'FUNCTION', 'OPE', '2021-05-10 10:43:07');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10195', '156', '删除素材', 'materialCenter/deleteItem', 'FUNCTION', 'OPE', '2021-05-10 10:43:08');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10196', '157', '添加', 'pageElementConfig/add', 'FUNCTION', 'OPE', '2021-05-10 10:46:15');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10197', '157', '删除', 'pageElementConfig/delete', 'FUNCTION', 'OPE', '2021-05-10 10:46:16');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10198', '157', '更新', 'pageElementConfig/update', 'FUNCTION', 'OPE', '2021-05-10 10:46:19');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10199', '157', '查询列表-首页', 'pageElementConfig/listIndex', 'FUNCTION', 'OPE', '2021-05-10 10:46:20');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10202', '117', '新增tab', 'tab/add', 'FUNCTION', 'OPE', '2021-05-11 17:52:25');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10203', '117', '修改tab', 'tab/update', 'FUNCTION', 'OPE', '2021-05-11 17:52:31');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10204', '117', '删除Tab', 'tab/delete', 'FUNCTION', 'OPE', '2021-05-11 17:52:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10205', '117', '获取tab列表', 'tab/list', 'FUNCTION', 'OPE', '2021-05-11 17:52:36');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10206', '117', '获取tab详细信息', 'tab/getById', 'FUNCTION', 'OPE', '2021-05-11 17:52:37');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10207', '117', '获取tab列表可以添加的商品列表', 'tab/getAvailableProduct', 'FUNCTION', 'OPE', '2021-05-11 17:52:39');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10208', '117', 'tab下添加商品', 'tab/addProduct', 'FUNCTION', 'OPE', '2021-05-11 17:52:43');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10209', '117', '查询tab下的商品', 'tab/listProduct', 'FUNCTION', 'OPE', '2021-05-11 17:52:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10210', '117', '更新tab下商品顺序', 'tab/updateProductSort', 'FUNCTION', 'OPE', '2021-05-11 17:52:44');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10211', '117', '删除tab下的商品', 'tab/deleteProduct', 'FUNCTION', 'OPE', '2021-05-11 17:54:04');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10212', '157', '热门板块分页查询', 'hotSearchConfig/list', 'FUNCTION', 'OPE', '2021-05-12 17:42:04');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10213', '157', '热门板块新增', 'hotSearchConfig/save', 'FUNCTION', 'OPE', '2021-05-12 17:42:05');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10217', '156', '查看素材分类详细信息', 'materialCenter/detailCategory', 'FUNCTION', 'OPE', '2021-05-14 13:41:07');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10218', '102', '查询lite版本小程序渠道信息', 'platform/listLitePlatformChannel', 'FUNCTION', 'OPE', '2021-05-14 16:18:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10219', '157', '查询列表-商品页', 'pageElementConfig/listProduct', 'FUNCTION', 'OPE', '2021-05-14 16:39:40');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10220', '157', '查询列表-我的页面', 'pageElementConfig/listMy', 'FUNCTION', 'OPE', '2021-05-14 16:39:42');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10221', '102', '刷新缓存', 'platform/updateCache', 'FUNCTION', 'OPE', '2021-05-17 11:11:45');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10222', '128', '校验订单是否用户认证', 'business/order/checkOrderIsAuth', 'FUNCTION', 'SHOP', '2021-05-19 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10224', '158', '分页获取佣金设置列表', 'splitBillConfigLite/page', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10225', '158', '添加佣金设置', 'splitBillConfigLite/add', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10226', '113', '佣金设置审核', 'splitBillConfigLite/audit', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10227', '158', '佣金设置详情', 'splitBillConfigLite/detail', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10228', '158', '添加佣金设置页面获取店铺列表接口', 'splitBillConfigLite/getShopList', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10229', '158', '更新佣金设置', 'splitBillConfigLite/update', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10230', '110', '获取优惠券列表', 'liteCouponTemplate/queryPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10231', '110', '添加优惠券', 'liteCouponTemplate/add', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10232', '110', '编辑优惠券页面数据获取', 'liteCouponTemplate/getUpdatePageData', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10233', '110', '编辑优惠券', 'liteCouponTemplate/modify', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10234', '110', '优惠券详情页面数据获取', 'liteCouponTemplate/getDetail', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10235', '110', '删除优惠券', 'liteCouponTemplate/delete', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10236', '110', '添加大礼包获取优惠券', 'liteCouponTemplate/getAssignAbleTemplate', 'FUNCTION', 'OPE', '2020-10-27 11:38:38');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10237', '110', '导出优惠券券码', 'liteCouponTemplate/exportEntityNum', 'FUNCTION', 'OPE', '2020-10-30 20:44:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10238', '110', '读取excel', 'liteCouponTemplate/readPhoneFromExcel', 'FUNCTION', 'OPE', '2020-11-10 17:41:52');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10239', '110', '添加券码券领取链接', 'liteCouponTemplate/addBindUrl', 'FUNCTION', 'OPE', '2021-01-06 16:39:47');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10240', '109', '获取大礼包列表', 'liteCouponPackage/queryPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10241', '109', '添加大礼包', 'liteCouponPackage/add', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10242', '109', '编辑大礼包页面数据获取', 'liteCouponPackage/getUpdatePageData', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10243', '109', '编辑大礼包', 'liteCouponPackage/modify', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10244', '109', '删除大礼包', 'liteCouponPackage/delete', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10245', '110', '查看优惠券领取详情', 'liteUserCoupon/queryPage', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10246', '102', '退押金', 'ope/order/forceDepositRefund', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10247', '102', '修改押金金额', 'ope/order/updatePayDepositAmount', 'FUNCTION', 'OPE', '2021-06-28 14:34:07');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10248', '102', '查询押金信息', 'ope/order/queryPayDepositLog', 'FUNCTION', 'OPE', '2021-06-28 14:34:08');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10249', '128', '修改押金金额', 'ope/order/updatePayDepositAmount', 'FUNCTION', 'SHOP', '2021-06-28 14:34:07');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10250', '128', '查询押金信息', 'ope/order/queryPayDepositLog', 'FUNCTION', 'SHOP', '2021-06-28 14:34:08');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10251', '159', '常规订单查看明细列表', 'accountPeriod/listRent', 'FUNCTION', 'SHOP', '2021-07-19 16:01:50');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10253', '159', '买断订单查看明细列表', 'accountPeriod/listBuyOut', 'FUNCTION', 'SHOP', '2021-07-19 16:02:06');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10254', '160', '常规订单查看明细列表', 'accountPeriod/listRent', 'FUNCTION', 'OPE', '2021-07-19 16:01:50');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10256', '160', '买断订单查看明细列表', 'accountPeriod/listBuyOut', 'FUNCTION', 'OPE', '2021-07-19 16:02:06');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10257', '154', '预审单-导出-运营', 'export/prequalificationSheet', 'FUNCTION', 'OPE', '2021-04-01 15:49:45');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10258', '155', '回执单-导出-商家', 'export/receiptConfirmationReceipt', 'FUNCTION', 'SHOP', '2021-04-01 15:49:45');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10261', '102', '运营查询反欺诈', 'antiFraud/queryAntiFraudByOrderId', 'FUNCTION', 'OPE', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10262', '10261', '商家查询反欺诈', 'antiFraud/queryAntiFraudByOrderId', 'FUNCTION', 'SHOP', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10264', '161', '账户信息', 'shopFund/pageShopFundFlow', 'FUNCTION', 'SHOP', '2021-08-17 14:02:09');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10265', '161', '资金余额', 'shopFund/getShopFundBalance', 'FUNCTION', 'SHOP', '2021-08-17 14:02:22');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10266', '161', '资金明细', 'shopFund/getShopAccountInfo', 'FUNCTION', 'SHOP', '2021-08-17 14:04:49');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10267', '1', '获取首页公告集合', 'noticeCenter/queryOpeNoticeDetailList', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10268', '1440', '菜单tab-常见问题集合', 'noticeCenter/queryOpeNoticeTabList', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10269', '1441', '菜单tab-常见问题集合', 'noticeCenter/queryOpeNoticeTabList', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10270', '1391', '商家中心通知分页', 'noticeCenter/queryOpeNoticePage', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10271', '1391', '添加商家中心通知', 'noticeCenter/addOpeNotice', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10272', '1391', '修改商家中心通知', 'noticeCenter/modifyOpeNotice', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10273', '1391', '删除商家中心通知', 'noticeCenter/deleteOpeNotice', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10274', '1442', '获取商家常见问题tab集合', 'noticeCenter/queryOpeNoticeTabDetailList', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10275', '1442', '添加商家常见问题tab', 'noticeCenter/addOpeNoticeTab', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10276', '1442', '修改商家常见问题tab', 'noticeCenter/modifyOpeNoticeTab', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10277', '1442', '删除商家常见问题tab', 'noticeCenter/deleteOpeNoticeTab', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10278', '1442', '商家常见问题tab下常见问题新增', 'noticeCenter/addOpeNoticeItem', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10279', '1442', '商家常见问题tab下常见问题修改', 'noticeCenter/modifyOpeNoticeItem', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10280', '1442', '商家常见问题tab下常见问题修改', 'noticeCenter/deleteOpeNoticeItem', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10281', '1442', '商家常见问题tab下常见问题分页', 'noticeCenter/queryOpeNoticeItemPage', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10282', '161', '资金账户-提现', 'shopFund/withDraw', 'FUNCTION', 'SHOP', '2021-08-17 17:35:49');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10283', '161', '资金账户-充值', 'shopFund/recharge', 'FUNCTION', 'SHOP', '2021-08-17 17:35:47');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10284', '1442', '商家常见问题tab下常见问题查看', 'noticeCenter/queryOpeNoticeItemDetail', 'FUNCTION', 'OPE', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10285', '161', '资金账户-查看凭证', 'shopFund/prof', 'FUNCTION', 'SHOP', '2021-08-18 16:14:44');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10286', '161', '资金账户-结算查看明细', 'shopFund/brokerageDetail', 'FUNCTION', 'SHOP', '2021-08-18 16:14:44');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10287', '1', '获取首页公告集合', 'noticeCenter/queryOpeNoticeDetailList', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10288', '1441', '商家常见问题tab下常见问题查看', 'noticeCenter/queryOpeNoticeItemDetail', 'FUNCTION', 'SHOP', '2020-11-05 13:50:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10289', '161', '查看费用明细', 'feeBill/page', 'FUNCTION', 'SHOP', '2021-08-22 18:24:15');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10290', '162', '商家资金账户', 'shopFund/pageShopFundBalance', 'FUNCTION', 'OPE', '2021-08-22 19:31:12');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10291', '162', '商家资金账户-明细页面', 'shopFund/pageShopFundFlow', 'FUNCTION', 'OPE', '2021-08-22 19:46:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10292', '162', '资金账户-结算查看明细', 'shopFund/brokerageDetail', 'FUNCTION', 'OPE', '2021-08-22 19:50:30');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10293', '162', '查看费用明细', 'feeBill/page', 'FUNCTION', 'OPE', '2021-08-22 20:09:35');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10294', '162', '资金账户-查看凭证', 'shopFund/prof', 'FUNCTION', 'OPE', '2021-08-18 16:14:44');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10298', '1', '获取首页公告集合', 'noticeCenter/queryOpeNoticeDetailList', 'FUNCTION', 'SHOP', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10300', '166', '租物频道配置-分页查询', 'productDistribute/page', 'FUNCTION', 'OPE', '2021-09-07 11:32:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10301', '166', '租物频道配置-根据虚拟商品ID获取详细信息', 'productDistribute/getByVirtualProductId', 'FUNCTION', 'OPE', '2021-09-07 11:32:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10302', '166', '租物频道配置-更新', 'productDistribute/update', 'FUNCTION', 'OPE', '2021-09-07 11:32:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10303', '166', '租物频道配置-新增', 'productDistribute/add', 'FUNCTION', 'OPE', '2021-09-07 11:32:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10304', '166', '租物频道配置-删除', 'productDistribute/delete', 'FUNCTION', 'OPE', '2021-09-07 11:32:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10305', '166', '租物频道配置-添加备注', 'productDistribute/updateRemark', 'FUNCTION', 'OPE', '2021-09-07 11:32:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10313', '166', '租物频道配置-导入', 'productDistribute/importAdd', 'FUNCTION', 'OPE', '2021-09-07 11:32:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10314', '167', '分页获取佣金设置列表', 'splitBillConfigToutiao/page', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10315', '167', '添加佣金设置', 'splitBillConfigToutiao/add', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10316', '167', '佣金设置审核', 'splitBillConfigToutiao/audit', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10317', '167', '佣金设置详情', 'splitBillConfigToutiao/detail', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10318', '167', '添加佣金设置页面获取店铺列表接口', 'splitBillConfigToutiao/getShopList', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10319', '167', '更新佣金设置', 'splitBillConfigToutiao/update', 'FUNCTION', 'OPE', '2020-08-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10321', '128', '获取合同下载地址', 'business/order/getOrderContractDownloadUrl', 'FUNCTION', 'SHOP', '2021-10-08 14:45:59');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10322', '102', '查询是否有风控报告', 'ope/order/queryWhetherRiskReport', 'FUNCTION', 'OPE', '2021-10-09 17:06:21');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10323', '128', '查询是否有风控报告', 'business/order/queryWhetherRiskReport', 'FUNCTION', 'SHOP', '2021-10-11 09:46:00');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10324', '102', '查询蚁盾分', 'order/antChain/queryShieldScore', 'FUNCTION', 'OPE', '2021-10-11 10:56:15');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10325', '102', '订单上链', 'order/antChain/syncToAntChain', 'FUNCTION', 'OPE', '2021-10-11 17:41:03');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10326', '128', '订单上链', 'order/antChain/syncToAntChain', 'FUNCTION', 'SHOP', '2021-10-11 17:41:03');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10327', '128', '查询蚁盾分', 'order/antChain/queryShieldScore', 'FUNCTION', 'SHOP', '2021-10-11 10:56:15');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10328', '128', '投保', 'order/antChain/insure', 'FUNCTION', 'SHOP', '2021-10-13 20:57:04');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10329', '102', '投保', 'order/antChain/insure', 'FUNCTION', 'OPE', '2021-10-13 20:57:14');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10338', '128', '退保', 'order/antChain/cancelInsurance', 'FUNCTION', 'SHOP', '2021-11-09 20:35:25');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10339', '102', '退保', 'order/antChain/cancelInsurance', 'FUNCTION', 'OPE', '2021-11-09 20:35:31');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10340', '102', '手动发起代扣', 'ope/order/orderByStagesWithhold', 'FUNCTION', 'OPE', '2021-11-11 14:24:26');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10341', '102', '手动平账', 'ope/order/stagesOfflinePay', 'FUNCTION', 'OPE', '2021-11-11 14:49:01');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10342', '1', '查询保险余额', 'order/antChain/getInsuranceBalance', 'FUNCTION', 'OPE', '2021-11-11 18:45:34');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10344', '168', '库存管理-查询', 'productStock/page', 'FUNCTION', 'OPE', '2021-11-16 17:58:50');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10345', '128', '更换设备', 'business/order/changeEquipment', 'FUNCTION', 'SHOP', '2021-11-17 18:41:58');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10346', '168', '库存管理-订单统计', 'productStock/stockOrderStatistics', 'FUNCTION', 'OPE', '2021-11-18 17:24:16');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10347', '168', '库存管理-导出', 'export/exportStock', 'FUNCTION', 'OPE', '2021-11-19 11:57:48');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10348', '0', '订单列表订单统计', 'business/order/businessOrderStatistics', 'FUNCTION', 'OPE', '2021-11-19 16:18:01');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10350', '102', '获取订单列表', 'ope/order/opeOrderList', 'FUNCTION', 'OPE', '2021-11-22 16:02:25');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10351', '128', '获取订单列表', 'ope/order/opeOrderList', 'FUNCTION', 'SHOP', '2021-11-25 10:39:14');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10382', '102', '运营配置转单', 'ope/order/transferOrder', 'FUNCTION', 'OPE', '2021-12-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10383', '102', '订单转单列表', 'ope/order/queryTransferOrderRecordPage', 'FUNCTION', 'OPE', '2021-12-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10384', '171', '转单列表', 'ope/order/queryTransferOrderRecordPage', 'FUNCTION', 'OPE', '2021-12-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10385', '102', '查询转单商品', 'opeShop/listAllShop', 'FUNCTION', 'OPE', '2021-12-28 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10386', '102', '订单状态数量统计', 'ope/reportForm/orderStatusCount', 'FUNCTION', 'OPE', '2022-06-16 20:51:08');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10387', '128', '订单状态数量统计', 'ope/reportForm/orderStatusCount', 'FUNCTION', 'SHOP', '2022-06-16 20:51:08');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10388', '102', '手动发起代扣', 'ope/order/stageOrderWithhold', 'FUNCTION', 'OPE', '2021-10-20 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10389', '128', '手动发起代扣', 'ope/order/stageOrderWithhold', 'FUNCTION', 'SHOP', '2021-10-20 01:44:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10392', '202', '查询渠道分佣列表', 'marketingChannel/page', 'FUNCTION', 'OPE', '2021-12-29 14:35:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10393', '202', '新增渠道账号', 'user/channelRegister', 'FUNCTION', 'OPE', '2021-12-29 16:44:20');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10394', '202', '修改渠道账号', 'marketingChannel/update', 'FUNCTION', 'OPE', '2021-12-29 16:53:11');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10395', '202', '查询渠道编码列表', 'marketingChannel/list', 'FUNCTION', 'OPE', '2021-12-29 16:57:17');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10396', '202', '新增渠道营销码', 'marketingChannel/add', 'FUNCTION', 'OPE', '2021-12-30 16:46:07');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10397', '202', '删除渠道营销码', 'marketingChannel/delete', 'FUNCTION', 'OPE', '2021-12-30 16:47:29');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10398', '202', '获取一条渠道信息', 'marketingChannel/getOne', 'FUNCTION', 'OPE', '2022-01-04 16:07:48');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10407', '202', '查询渠道资金账户', 'marketingChannel/pageChannelFundFlow', 'FUNCTION', 'OPE', '2022-01-05 09:58:52');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10410', '202', '分页查询渠道用户订单', 'ope/order/queryChannelUserOrdersPage', 'FUNCTION', 'OPE', '2022-01-06 10:10:35');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10411', '202', '财务渠道审核', 'splitBillConfig/channelAudit', 'FUNCTION', 'OPE', '2022-09-29 16:46:27');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10412', '202', '渠道佣金结算', 'channelAccountPeriod/queryChannelAccountPeriodPage', 'FUNCTION', 'OPE', '2022-01-04 18:53:43');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10413', '203', '分页查看信审人员信息', 'auditUser/page', 'FUNCTION', 'OPE', '2022-01-04 18:53:43');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10414', '203', '修改信审状态', 'auditUser/changeStatus', 'FUNCTION', 'OPE', '2022-01-04 18:53:43');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10415', '203', '添加信审人员', 'auditUser/add', 'FUNCTION', 'OPE', '2022-01-04 18:53:43');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10416', '173', '渠道佣金结算', 'channelAccountPeriod/queryChannelAccountPeriodPage', 'FUNCTION', 'OPE', '2022-01-04 18:53:43');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10417', '173', '渠道佣金结算获取详细信息', 'channelAccountPeriod/detail', 'FUNCTION', 'OPE', '2022-01-04 19:09:37');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10418', '173', '渠道佣金提交结算', 'channelAccountPeriod/submitSettle', 'FUNCTION', 'OPE', '2022-01-04 19:13:31');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10419', '173', '新增渠道佣金备注', 'channelAccountPeriod/add', 'FUNCTION', 'OPE', '2022-01-04 19:16:51');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10420', '173', '查询渠道佣金备注', 'channelAccountPeriod/listByAccountPeriodId', 'FUNCTION', 'OPE', '2022-01-04 19:18:23');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10421', '174', '渠道佣金结算明细', 'channelAccountPeriod/listRent', 'FUNCTION', 'OPE', '2022-01-04 19:25:42');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10422', '173', '渠道佣金提交审核', 'channelAccountPeriod/submitAudit', 'FUNCTION', 'OPE', '2022-01-14 15:41:28');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10429', '204', '获取一条渠道信息', 'marketingChannel/getOne', 'FUNCTION', 'CHANNEL', '2022-01-04 16:07:48');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10430', '204', '资金余额', 'shopFund/getShopFundBalance', 'FUNCTION', 'CHANNEL', '2021-08-17 14:02:22');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10431', '204', '查询渠道资金账户', 'marketingChannel/pageChannelFundFlow', 'FUNCTION', 'CHANNEL', '2022-01-05 09:58:52');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10432', '205', '分页查询渠道用户订单', 'ope/order/queryChannelUserOrdersPage', 'FUNCTION', 'CHANNEL', '2022-01-06 10:10:35');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10433', '205', '渠道导出渠道订单', 'ope/order/exportChannelUserOrdersPage', 'FUNCTION', 'CHANNEL', '2022-01-11 17:37:05');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10434', '206', '佣金结算管理', 'channelAccountPeriod/queryChannelAccountPeriodPage', 'FUNCTION', 'CHANNEL', '2023-02-23 18:34:10');

INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10439', '212', '查看发票列表', 'feeBillTicket/page', 'FUNCTION', 'OPE', '2023-03-29 16:20:02');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10440', '212', '发票审核', 'feeBillTicket/confirm', 'FUNCTION', 'OPE', '2023-03-29 16:20:07');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10442', '211', '查看商家提现申请', 'shopFund/withDrawApplyPage', 'FUNCTION', 'OPE', '2023-03-29 21:52:33');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10443', '211', '商家提现审批', 'shopFund/withDrawPass', 'FUNCTION', 'OPE', '2023-03-29 22:04:45');


INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10437', '161', '一键申请开票', 'feeBillTicket/getEnableApplyFeeBill', 'FUNCTION', 'SHOP', '2023-03-29 16:19:05');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10438', '161', '一键申请开票', 'feeBillTicket/apply', 'FUNCTION', 'SHOP', '2023-03-29 16:19:19');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('10441', '161', '查看提现记录', 'shopFund/withDrawApplyPage', 'FUNCTION', 'SHOP', '2023-03-29 21:52:26');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11001', '102', '查询用户是否查过征信报告', 'compoents/creditReport/confirmYxReport', 'FUNCTION', 'OPE', '2022-02-23 16:08:38');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11002', '102', '调用守卫接口查询征信报告', 'compoents/creditReport/queryCreditReport', 'FUNCTION', 'OPE', '2022-02-23 16:07:56');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11003', '102', '查询报告结果', 'compoents/creditReport/queryReport', 'FUNCTION', 'OPE', '2022-02-23 16:09:13');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11004', '102', '运营查询云信上报', 'compoents/creditReport/queryYxCreditAppearInfo', 'FUNCTION', 'OPE', '2020-08-28 11:04:46');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11005', '202', '导出渠道用户订单', 'export/channelRentOrder', 'FUNCTION', 'OPE', '2023-05-22 10:06:19');

INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11028', '161', '费用明细导出', 'export/feeBillDetail', 'FUNCTION', 'OPE', '2023-05-26 13:02:05');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11027', '157', '商品分类删除', 'cubesConfig/delete', 'FUNCTION', 'OPE', '2023-05-25 17:20:57');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11026', '157', '商品分类编辑', 'cubesConfig/update', 'FUNCTION', 'OPE', '2023-05-25 17:20:48');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11025', '157', '商品分类新增', 'cubesConfig/add', 'FUNCTION', 'OPE', '2023-05-25 17:20:43');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11024', '157', '商品分类详情', 'cubesConfig/detail', 'FUNCTION', 'OPE', '2023-05-25 17:20:38');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11023', '157', '商品分类列表', 'cubesConfig/list', 'FUNCTION', 'OPE', '2023-05-25 17:20:32');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11022', '157', '活动专区编辑', 'activityConfig/update', 'FUNCTION', 'OPE', '2023-05-25 17:13:47');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11021', '157', '活动专区详情', 'activityConfig/detail', 'FUNCTION', 'OPE', '2023-05-25 17:13:41');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11020', '157', '活动专区列表', 'activityConfig/list', 'FUNCTION', 'OPE', '2023-05-25 17:13:36');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11019', '157', '栏目编辑', 'columnConfig/update', 'FUNCTION', 'OPE', '2023-05-25 17:13:29');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11018', '157', '栏目详情', 'columnConfig/detail', 'FUNCTION', 'OPE', '2023-05-25 17:13:22');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11017', '157', '栏目列表', 'columnConfig/list', 'FUNCTION', 'OPE', '2023-05-25 17:13:15');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11016', '157', 'banner开关', 'bannerConfig/open', 'FUNCTION', 'OPE', '2023-05-25 17:12:18');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11015', '157', 'banner删除', 'bannerConfig/delete', 'FUNCTION', 'OPE', '2023-05-25 17:10:44');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11014', '157', 'banner编辑', 'bannerConfig/update', 'FUNCTION', 'OPE', '2023-05-25 17:10:38');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11013', '157', 'banner新增', 'bannerConfig/add', 'FUNCTION', 'OPE', '2023-05-25 17:10:32');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11012', '157', 'banner详情', 'bannerConfig/detail', 'FUNCTION', 'OPE', '2023-05-25 17:10:27');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11011', '157', 'banner列表', 'bannerConfig/list', 'FUNCTION', 'OPE', '2023-05-25 17:10:20');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11010', '157', 'icon删除', 'iconConfig/delete', 'FUNCTION', 'OPE', '2023-05-25 17:09:28');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11009', '157', 'icon编辑', 'iconConfig/update', 'FUNCTION', 'OPE', '2023-05-25 17:08:59');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11008', '157', 'icon新增', 'iconConfig/add', 'FUNCTION', 'OPE', '2023-05-25 17:08:16');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11007', '157', 'icon详情', 'iconConfig/detail', 'FUNCTION', 'OPE', '2023-05-25 16:26:59');
INSERT INTO `ct_backstage_function` (`id`, `parent_id`, `name`, `code`, `type`, `platform`, `create_time`) VALUES ('11006', '157', 'icon列表', 'iconConfig/list', 'FUNCTION', 'OPE', '2023-05-25 16:26:26');


--  给admin部门添加权限
DELETE from ct_backstage_department_function where department_id=1;
INSERT INTO `ct_backstage_department_function` (`department_id`,`function_id`,`create_time`) SELECT'1',id,'2021-11-22 16:03:20' FROM ct_backstage_function WHERE platform = "OPE";

--  给用户添加权限
DELETE from ct_backstage_user_function where backstage_user_id=1;
INSERT INTO `ct_backstage_user_function` (`backstage_user_id`,`function_id`,`source_type`,`source_value`,`create_time`)
(SELECT '1',id,'ADD','0','2021-11-22 16:03:20' FROM ct_backstage_function WHERE platform = "OPE");


INSERT INTO `ct_backstage_department_function` (`department_id`, `function_id`, `create_time`) VALUES ('3', '10', '2021-02-03 14:02:05');
INSERT INTO `ct_backstage_department_function` (`department_id`, `function_id`, `create_time`) VALUES ('3', '1136', '2021-02-03 14:02:05');
INSERT INTO `ct_backstage_department_function` (`department_id`, `function_id`, `create_time`) VALUES ('3', '1137', '2021-02-03 14:02:05');
INSERT INTO `ct_backstage_department_function` (`department_id`, `function_id`, `create_time`) VALUES ('3', '1138', '2021-02-03 14:02:05');
INSERT INTO `ct_backstage_department_function` (`department_id`, `function_id`, `create_time`) VALUES ('3', '1139', '2021-02-03 14:02:05');
INSERT INTO `ct_backstage_department_function` (`department_id`, `function_id`, `create_time`) VALUES ('3', '1376', '2021-02-03 14:02:05');
INSERT INTO `ct_backstage_department_function` (`department_id`, `function_id`, `create_time`) VALUES ('3', '1377', '2021-02-03 14:02:05');
INSERT INTO `ct_backstage_department_function` (`department_id`, `function_id`, `create_time`) VALUES ('3', '1346', '2021-02-03 14:02:05');
INSERT INTO `ct_backstage_department_function` (`department_id`, `function_id`, `create_time`) VALUES ('3', '10298', '2021-02-03 14:02:05');
INSERT INTO `ct_backstage_department_function` (`department_id`, `function_id`, `create_time`) VALUES ('3', '10265', '2021-02-03 14:02:05');




-- 给商家admin部门添加权限
DELETE from ct_backstage_department_function where department_id=2;
INSERT INTO `ct_backstage_department_function` (`department_id`,`function_id`,`create_time`) 
SELECT '2',id,'2021-11-22 16:03:20' FROM ct_backstage_function WHERE platform = "SHOP";


INSERT INTO `ct_backstage_department_function` (`department_id`,`function_id`,`create_time`) 
SELECT '4',id,'2021-11-22 16:03:20' FROM ct_backstage_function WHERE platform = "CHANNEL";


  -- 营销部分数据
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('57', '56', '待付款', 'https://oss.x-x-x-x-x-x-x.com/backstage/57.png', '2022-10-11 20:16:33', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('58', '56', '已逾期', 'https://oss.x-x-x-x-x-x-x.com/backstage/58.png', '2022-10-17 13:03:30', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('59', '56', '租用中', 'https://oss.x-x-x-x-x-x-x.com/backstage/59.png', '2022-10-17 13:04:05', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('60', '56', '待结算', 'https://oss.x-x-x-x-x-x-x.com/backstage/60.png', '2022-10-17 13:04:23', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('61', '56', '收藏夹', 'https://oss.x-x-x-x-x-x-x.com/backstage/61.png', '2022-10-17 13:04:38', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('62', '56', '待发货', 'https://oss.x-x-x-x-x-x-x.com/backstage/62.png', '2022-10-17 13:04:57', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('63', '56', '收货地址', 'https://oss.x-x-x-x-x-x-x.com/backstage/63.png', '2022-10-17 13:05:28', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('64', '56', '优惠券', 'https://oss.x-x-x-x-x-x-x.com/backstage/64.png', '2022-10-17 13:05:53', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('65', '56', '投诉建议', 'https://oss.x-x-x-x-x-x-x.com/backstage/65.png', '2022-10-17 13:06:11', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('66', '56', '联系客服', 'https://oss.x-x-x-x-x-x-x.com/backstage/66.png', '2022-10-17 13:06:29', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('67', '56', '押金充值', 'https://oss.x-x-x-x-x-x-x.com/backstage/67.png', '2022-10-17 13:06:47', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('68', '56', '待收货', 'https://oss.x-x-x-x-x-x-x.com/backstage/68.png', '2022-10-17 13:07:01', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('69', '58', '横幅1', 'https://oss.x-x-x-x-x-x-x.com/backstage/69.png', '2022-10-17 13:14:51', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('70', '60', '游戏电玩', 'https://oss.x-x-x-x-x-x-x.com/backstage/70.png', '2022-10-17 13:18:53', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('71', '60', '手机', 'https://oss.x-x-x-x-x-x-x.com/backstage/71.png', '2022-10-17 13:19:22', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('72', '60', '配件', 'https://oss.x-x-x-x-x-x-x.com/backstage/72.png', '2022-10-17 13:19:34', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('73', '60', '相机', 'https://oss.x-x-x-x-x-x-x.com/backstage/73.png', '2022-10-17 13:19:48', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('74', '60', '电脑平板', 'https://oss.x-x-x-x-x-x-x.com/backstage/74.png', '2022-10-17 13:20:06', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('75', '60', '健康', 'https://oss.x-x-x-x-x-x-x.com/backstage/75.png', '2022-10-17 13:20:22', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('76', '60', '苹果', 'https://oss.x-x-x-x-x-x-x.com/backstage/76.png', '2022-10-17 13:20:35', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('77', '60', '更多', 'https://oss.x-x-x-x-x-x-x.com/backstage/77.png', '2022-10-17 13:20:46', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('78', '57', '主图', 'https://oss.x-x-x-x-x-x-x.com/backstage/78.jpeg', '2022-10-17 13:27:38', NULL);
INSERT INTO `ct_material_center_item` (`id`, `category_id`, `name`, `file_url`, `create_time`, `delete_time`) VALUES ('79', '59', '副图1', 'https://oss.x-x-x-x-x-x-x.com/backstage/79.png', '2022-10-17 13:28:37', NULL);

INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('104', NULL, 'SPECIAL_TITLE_SUB', NULL, NULL, '限时抢租', NULL, '000', '2022-10-17 13:26:48', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('103', NULL, 'SPECIAL_TITLE_MAIN', NULL, NULL, '火热爆款', NULL, '000', '2022-10-17 13:26:42', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('83', '57', 'MY_ORDER', '#', '1', '待付款', 'ORDER_WAIT_PAY', '000', '2022-10-17 13:07:50', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('88', '58', 'MY_ORDER', '#', '6', '已逾期', 'ORDER_OVERDUE', '000', '2022-10-17 13:09:27', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('86', '59', 'MY_ORDER', '#', '4', '租用中', 'ORDER_RENTING', '000', '2022-10-17 13:08:47', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('87', '60', 'MY_ORDER', '#', '5', '待结算', 'ORDER_WAIT_SETTLE', '000', '2022-10-17 13:09:05', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('89', '61', 'MY_SERVICE', 'pages/collection/collection', '1', '收藏夹', NULL, '000', '2022-10-17 13:10:13', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('84', '62', 'MY_ORDER', '#', '2', '待发货', 'ORDER_WAIT_DELIVERY', '000', '2022-10-17 13:08:09', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('91', '63', 'MY_SERVICE', 'pages/address/address', '3', '收货地址', NULL, '000', '2022-10-17 13:11:19', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('90', '64', 'MY_SERVICE', 'pages/coupon/coupon', '2', '优惠券  ', NULL, '000', '2022-10-17 13:10:45', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('92', '65', 'MY_SERVICE', 'pages/complain/complain', '4', '投诉建议', NULL, '000', '2022-10-17 13:11:48', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('93', '67', 'MY_SERVICE', 'pages/depositRecharge/depositRecharge  ', '5', '押金管理', NULL, '000', '2022-10-17 13:12:17', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('85', '68', 'MY_ORDER', '#', '3', '待收货', 'ORDER_WAIT_RECEIVE', '000', '2022-10-17 13:08:29', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('109', '69', 'INDEX_BANNER', '/pages/product/product?productId=1665985652046', '1', '#364E7F', NULL, '000', '2022-10-17 13:53:52', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('95', '70', 'ICON', '/pages/goodsList/goodsList?content=游戏电玩&name=游戏电玩', '1', '游戏电玩', NULL, '000', '2022-10-17 13:22:06', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('96', '71', 'ICON', '/pages/goodsList/goodsList?content=手机&name=手机', '2', '手机', NULL, '000', '2022-10-17 13:22:52', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('97', '72', 'ICON', '/pages/goodsList/goodsList?content=配件&name=配件', '3', '配件', NULL, '000', '2022-10-17 13:23:35', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('98', '73', 'ICON', '/pages/goodsList/goodsList?content=相机&name=相机', '4', '相机', NULL, '000', '2022-10-17 13:24:09', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('99', '74', 'ICON', '/pages/goodsList/goodsList?content=电脑平板&name=电脑平板', '5', '电脑平板', NULL, '000', '2022-10-17 13:24:46', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('100', '75', 'ICON', '/pages/goodsList/goodsList?content=健康&name=健康', '6', '健康', NULL, '000', '2022-10-17 13:25:16', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('101', '76', 'ICON', '/pages/goodsList/goodsList?content=苹果&name=苹果', '7', '苹果', NULL, '000', '2022-10-17 13:25:42', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('102', '77', 'ICON', '/pages/classified/classified', '8', '更多', NULL, '000', '2022-10-17 13:26:19', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('108', '78', 'SPECIAL_AREA_MAIN', '/pages/product/product?productId=1665985652046', '1', NULL, NULL, '000', '2022-10-17 13:53:31', NULL);
INSERT INTO `ct_page_element_config` (`id`, `material_center_item_id`, `type`, `link`, `sort_num`, `describe_info`, `ext_code`, `channel_id`, `create_time`, `delete_time`) VALUES ('107', '79', 'SPECIAL_AREA_SUB', '/pages/product/product?productId=1665985652046', '1', NULL, NULL, '000', '2022-10-17 13:53:17', NULL);

INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1662', '2022-10-17 13:32:22', '2022-10-17 13:32:22', NULL, 'iPhone13Pro', NULL, NULL, '1660', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1663', '2022-10-17 13:32:38', '2022-10-17 13:32:38', NULL, 'iPhone13ProMax', NULL, NULL, '1660', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1664', '2022-10-17 13:32:56', '2022-10-17 13:32:56', NULL, 'iPhone14Pro', NULL, NULL, '1660', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1665', '2022-10-17 13:33:15', '2022-10-17 13:33:15', NULL, 'iPhone14ProMax', NULL, NULL, '1660', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1666', '2022-10-17 13:33:50', '2022-10-17 13:33:50', NULL, 'P40', NULL, NULL, '1661', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1667', '2022-10-17 13:33:59', '2022-10-17 13:33:59', NULL, 'Mate4', NULL, NULL, '1661', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1670', '2022-10-17 13:35:00', '2022-10-17 13:35:00', NULL, 'iPadMini', NULL, NULL, '1668', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1671', '2022-10-17 13:35:14', '2022-10-17 13:35:14', NULL, 'iPadPro', NULL, NULL, '1668', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1672', '2022-10-17 13:35:29', '2022-10-17 13:35:29', NULL, '华为matePad', NULL, NULL, '1669', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1673', '2022-10-21 10:09:06', '2022-10-21 10:09:06', NULL, '手机优选', '', '', '0', '1', '1', '3c_mobile', 'RENT_PHONE', '1');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1674', '2022-10-21 10:09:47', '2022-10-21 10:09:47', NULL, '苹果手机', '', '', '1673', '2', '1', '3c_mobile', 'RENT_PHONE', '2');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1676', '2022-10-21 10:14:45', '2022-10-21 10:14:45', NULL, 'OPPO手机', '', '', '1673', '3', '1', '3c_mobile', 'RENT_PHONE', '2');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1677', '2022-10-21 10:15:18', '2022-10-21 10:15:18', NULL, 'VIVO/IQOO手机', '', '', '1673', '4', '1', '3c_mobile', 'RENT_PHONE', '2');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1678', '2022-10-21 10:15:45', '2022-10-21 10:15:45', NULL, '小米手机', '', '', '1673', '5', '1', '3c_mobile', 'RENT_PHONE', '2');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1679', '2022-10-21 10:16:12', '2022-10-21 10:16:12', NULL, '荣耀手机', '', '', '1673', '6', '1', '3c_mobile', 'RENT_PHONE', '2');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1680', '2022-10-21 10:18:11', '2022-10-21 10:18:11', NULL, '华为Mate50(4G版)8G+128G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1681', '2022-10-21 10:19:10', '2022-10-21 10:19:10', NULL, '华为Mate50(4G版)8G+256G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1682', '2022-10-21 10:20:32', '2022-10-21 10:20:32', NULL, '华为Mate50(4G版）8G+512G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1683', '2022-10-21 10:21:50', '2022-10-21 10:21:50', NULL, '华为Mate50E(4G版）8G+128G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1684', '2022-10-21 10:22:05', '2022-10-21 10:22:05', NULL, '华为Mate50E(4G版）8G+256G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1685', '2022-10-21 10:22:45', '2022-10-21 10:22:45', NULL, '华为Mate50pro(4G版）8G+256G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1686', '2022-10-21 10:23:12', '2022-10-21 10:23:12', NULL, '华为Mate50pro(4G版）8G+512G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1687', '2022-10-21 10:26:23', '2022-10-21 10:26:23', NULL, '华为Mate40E(5G版）8G+128G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1688', '2022-10-21 10:27:20', '2022-10-21 10:27:20', NULL, '华为Mate40E Pro(5G版）8G+256G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1689', '2022-10-21 10:28:07', '2022-10-21 10:28:07', NULL, '华为NOVA10(4G版）8G+128G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1690', '2022-10-21 10:28:42', '2022-10-21 10:28:42', NULL, '华为NOVA10(4G版）8G+256G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1691', '2022-10-21 10:29:29', '2022-10-21 10:29:29', NULL, '华为NOVA10Pro(4G版）8G+128G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1692', '2022-10-21 10:29:44', '2022-10-21 10:29:44', NULL, '华为NOVA10Pro(4G版）8G+256G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1693', '2022-10-21 10:30:13', '2022-10-21 10:30:13', NULL, '华为NOVA10Z(4G版）8G+128G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1694', '2022-10-21 10:30:38', '2022-10-21 10:30:38', NULL, '华为NOVA10Z(4G版）8G+256G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1695', '2022-10-21 10:31:12', '2022-10-21 10:31:12', NULL, '华为NOVA9(4G版）8G+256G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1696', '2022-10-21 10:31:35', '2022-10-21 10:31:35', NULL, '华为NOVA9Pro(4G版）8G+256G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1697', '2022-10-21 10:32:43', '2022-10-21 10:32:43', NULL, '华为NOVA9SE(4G版）8G+128G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1698', '2022-10-21 10:32:58', '2022-10-21 10:32:58', NULL, '华为NOVA9SE(4G版）8G+256G', NULL, NULL, '1675', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1699', '2022-10-21 10:47:17', '2022-10-21 10:47:17', NULL, '苹果原装蓝牙耳机二代AirPods', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1700', '2022-10-21 10:48:30', '2022-10-21 10:48:30', NULL, '苹果原装蓝牙耳机三代无线AirPods', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1701', '2022-10-21 10:49:50', '2022-10-21 10:49:50', NULL, '苹果原装蓝牙耳机三代有线AirPods', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1702', '2022-10-21 10:50:40', '2022-10-21 10:50:40', NULL, '苹果无线蓝牙耳机AirPods PRO二代', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1703', '2022-10-21 10:52:03', '2022-10-21 10:52:03', NULL, '苹果原装蓝牙耳机AirPods Pro(配Magsafe无线充电盒）', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1704', '2022-10-21 10:53:10', '2022-10-21 10:53:10', NULL, '苹果14（A2884）-128G', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1705', '2022-10-21 10:53:21', '2022-10-21 10:53:21', NULL, '苹果14（A2884）-256G', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1706', '2022-10-21 10:53:33', '2022-10-21 10:53:33', NULL, '苹果14（A2884）-512G', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1707', '2022-10-21 10:54:17', '2022-10-21 10:54:17', NULL, '苹果14Pro（A2892）-128G', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1708', '2022-10-21 10:54:30', '2022-10-21 10:54:30', NULL, '苹果14Pro（A2892）-256G', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1709', '2022-10-21 10:54:43', '2022-10-21 10:54:43', NULL, '苹果14Pro（A2892）-512G', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1710', '2022-10-21 10:55:18', '2022-10-21 10:55:18', NULL, '苹果14Pro Max（A2896）-128G', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1711', '2022-10-21 10:55:31', '2022-10-21 10:55:31', NULL, '苹果14Pro Max（A2896）-256G', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1712', '2022-10-21 10:55:47', '2022-10-21 10:55:47', NULL, '苹果14Pro Max（A2896）-512G', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1713', '2022-10-21 10:56:15', '2022-10-21 10:56:15', NULL, '苹果14Pro Max（A2896）-1T', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');
INSERT INTO `ct_ope_category` (`id`, `create_time`, `update_time`, `delete_time`, `name`, `icon`, `banner_icon`, `parent_id`, `sort_rule`, `status`, `ant_chain_code`, `zfb_code`, `type`) VALUES ('1714', '2022-10-21 10:57:12', '2022-10-21 10:57:12', NULL, '苹果14PLus(A2888)-128G', NULL, NULL, '1674', '0', '1', '3c_mobile', 'RENT_PHONE', '3');

INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3267', '000', '6', '1673', '2022-10-21 10:09:06', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3268', '000', '6', '1674', '2022-10-21 10:09:47', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3269', '000', '6', '1675', '2022-10-21 10:11:16', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3270', '000', '6', '1676', '2022-10-21 10:14:45', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3271', '000', '6', '1677', '2022-10-21 10:15:18', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3272', '000', '6', '1678', '2022-10-21 10:15:45', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3273', '000', '6', '1679', '2022-10-21 10:16:12', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3275', '000', '6', '2026', '2022-10-25 14:50:15', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3276', '000', '6', '2027', '2022-10-25 14:51:04', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3277', '000', '6', '2028', '2022-10-25 14:51:25', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3278', '000', '6', '2029', '2022-10-25 14:51:42', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3279', '000', '6', '2030', '2022-10-25 14:52:03', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3280', '000', '6', '2025', '2022-10-25 14:56:13', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3281', '000', '6', '2082', '2022-10-25 15:24:59', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3282', '000', '6', '2083', '2022-10-25 15:25:14', NULL, NULL);
INSERT INTO `ct_ope_config` (`id`, `channel_id`, `index_type`, `config_id`, `create_time`, `update_time`, `delete_time`) VALUES ('3283', '000', '6', '2084', '2022-10-25 15:25:27', NULL, NULL);


INSERT INTO `ct_buyun_sms_template` (`id`, `tpl_id`, `autograph`, `content`, `use_case`, `status`, `create_time`, `update_time`, `delete_time`, `sms_type`) VALUES ('312', 'ORDER_BUY_OUT_SUCCESS', 'XX租赁', '您尾号为#orderId#的订单已买断支付成功,有问题请联系客服LINK_MOBILE,工作日9:30—18:00在线', NULL, '0', '2021-01-06 16:05:04', NULL, NULL, 'RENT');
INSERT INTO `ct_buyun_sms_template` (`id`, `tpl_id`, `autograph`, `content`, `use_case`, `status`, `create_time`, `update_time`, `delete_time`, `sms_type`) VALUES ('316', 'ORDER_PALT_CLOSE', 'XX租赁', '您尾号为#orderNo#的订单已取消退款并解冻押金,金额已原路退回请查收,有问题请拨打#serviceTel#,在线9:30-18:00', NULL, '0', '2021-01-10 16:05:04', NULL, NULL, 'RENT');
INSERT INTO `ct_buyun_sms_template` (`id`, `tpl_id`, `autograph`, `content`, `use_case`, `status`, `create_time`, `update_time`, `delete_time`, `sms_type`) VALUES ('317', 'BUSINESS_DELIVERY', 'XX租赁', '您租的：#productName# 已发货;发货物流公司(#logisticsName#);快递单号(#expressNo#)', NULL, '0', '2021-01-11 16:05:04', NULL, NULL, 'RENT');
INSERT INTO `ct_buyun_sms_template` (`id`, `tpl_id`, `autograph`, `content`, `use_case`, `status`, `create_time`, `update_time`, `delete_time`, `sms_type`) VALUES ('318', 'RISK_CLOSE', 'XX租赁', '您的尾号为为#orderId#的订单审核未通过，已取消退款并解冻押金,金额已原路退回请查收,有问题请拨打#serviceTel#', NULL, '0', '2021-01-12 16:05:04', NULL, NULL, 'RENT');
INSERT INTO `ct_buyun_sms_template` (`id`, `tpl_id`, `autograph`, `content`, `use_case`, `status`, `create_time`, `update_time`, `delete_time`, `sms_type`) VALUES ('319', 'ORDER_PAY_SUCCESS', 'XX租赁', '您的尾号为#orderId#的订单已支付成功, 商品已备货，请等待审核通过后发货。有问题请拨打#serviceTel#,工作日(9:30-18:00)在线。温馨提示：近期电信网络诈骗高发，凡是要求私下交易转账的都是诈骗，请勿轻易操作以防被骗。', NULL, '0', '2021-01-13 16:05:04', NULL, NULL, 'RENT');
INSERT INTO `ct_buyun_sms_template` (`id`, `tpl_id`, `autograph`, `content`, `use_case`, `status`, `create_time`, `update_time`, `delete_time`, `sms_type`) VALUES ('320', 'ORDER_PAY_FAIL_CLOSE', 'XX租赁', '您的尾号为#orderId#的订单扣款失败已关闭，请确保支付宝账户余额或余额宝金额大于首期实付款；在支付宝搜索XXXX，重新下单。', NULL, '0', '2021-01-14 16:05:04', NULL, NULL, 'RENT');
INSERT INTO `ct_buyun_sms_template` (`id`, `tpl_id`, `autograph`, `content`, `use_case`, `status`, `create_time`, `update_time`, `delete_time`, `sms_type`) VALUES ('322', 'REAL_NAME', 'XX租赁', '您正在进行身份认证，您的验证码是#code#。如非本人操作，请忽略本短信', NULL, '0', '2021-01-16 16:05:04', NULL, NULL, 'RENT');
INSERT INTO `ct_buyun_sms_template` (`id`, `tpl_id`, `autograph`, `content`, `use_case`, `status`, `create_time`, `update_time`, `delete_time`, `sms_type`) VALUES ('399', 'RELET_ORDER_PAY_SUCCESS', 'XX租赁', '您的【XX租赁】订单已续租成功。有问题请拨打#serviceTel#,工作日(9:30-18:00)在线', NULL, '0', '2021-08-02 09:40:51', NULL, NULL, 'RENT');
INSERT INTO `ct_buyun_sms_template` (`id`, `tpl_id`, `autograph`, `content`, `use_case`, `status`, `create_time`, `update_time`, `delete_time`, `sms_type`) VALUES ('400', 'RELET_TO_BUSINESS', 'XX租赁', '商家您好,用户:#userName#,订单号#OrderId#,已提交续租，续租订单号#ReletOrderId#，请登录XXXX商家管理后台查看。', NULL, '0', '2021-08-02 09:40:08', NULL, NULL, 'RENT');
INSERT INTO `ct_buyun_sms_template` (`id`, `tpl_id`, `autograph`, `content`, `use_case`, `status`, `create_time`, `update_time`, `delete_time`, `sms_type`) VALUES ('481', 'BUSINESS_UPDATE_PASSWORD', 'XX租赁', '正在找回密码，您的验证码是#code#', NULL, '0', '2022-06-22 16:08:12', NULL, NULL, 'RENT');


INSERT INTO `ct_config_column` (`id`, `channel_id`, `type`, `name`, `url`, `product_ids`, `paperwork`, `jump_url`, `create_time`, `update_time`) VALUES ('1', '000', 'COLUMN', '爆款产品', 'https://oss.x-x-x-x-x-x-x.com/backstage/a0.png', '1,2', '文案', NULL, '2023-05-26 14:22:32', '2023-06-07 11:19:39');
INSERT INTO `ct_config_column` (`id`, `channel_id`, `type`, `name`, `url`, `product_ids`, `paperwork`, `jump_url`, `create_time`, `update_time`) VALUES ('2', '000', 'COLUMN', '特惠租机', 'https://oss.x-x-x-x-x-x-x.com/backstage/a1.png', '1,2', '文案', NULL, '2023-05-26 14:22:32', '2023-06-07 11:19:51');
INSERT INTO `ct_config_column` (`id`, `channel_id`, `type`, `name`, `url`, `product_ids`, `paperwork`, `jump_url`, `create_time`, `update_time`) VALUES ('3', '000', 'ACTIVITY', '活动专区', 'https://oss.x-x-x-x-x-x-x.com/backstage/c0.png', NULL, NULL, 'pages/collection/collection  ', '2023-05-26 14:25:43', '2023-06-05 14:22:55');
INSERT INTO `ct_config_column` (`id`, `channel_id`, `type`, `name`, `url`, `product_ids`, `paperwork`, `jump_url`, `create_time`, `update_time`) VALUES ('4', '000', 'ACTIVITY', '活动专区', 'https://oss.x-x-x-x-x-x-x.com/backstage/c1.png', NULL, NULL, 'pages/collection/collection  ', '2023-05-26 14:25:43', '2023-05-26 16:14:39');
INSERT INTO `ct_config_column` (`id`, `channel_id`, `type`, `name`, `url`, `product_ids`, `paperwork`, `jump_url`, `create_time`, `update_time`) VALUES ('5', '000', 'ACTIVITY', '活动专区', 'https://oss.x-x-x-x-x-x-x.com/backstage/c2.png', NULL, NULL, 'pages/collection/collection  ', '2023-05-26 14:25:41', '2023-05-26 15:10:40');


INSERT INTO ct_config_icon (id, channel_id, url, title, jump_url, sort, create_time, update_time, delete_time, tag) VALUES(1, '000', 'https://oss.x-x-x-x-x-x-x.com/menu/1.gif', '苹果手机', '/pages/product/product?productId=1691720666670', 1, '2023-08-08 02:14:25', NULL, NULL, NULL);
INSERT INTO ct_config_icon (id, channel_id, url, title, jump_url, sort, create_time, update_time, delete_time, tag) VALUES(2, '000', 'https://oss.x-x-x-x-x-x-x.com/menu/2.png', '电脑设备', '/pages/product/product?productId=1691720666670', 2, '2023-08-08 02:14:25', NULL, NULL, NULL);
INSERT INTO ct_config_icon (id, channel_id, url, title, jump_url, sort, create_time, update_time, delete_time, tag) VALUES(3, '000', 'https://oss.x-x-x-x-x-x-x.com/menu/3.png', '平板iPad', '/pages/product/product?productId=1691720666670', 3, '2023-08-08 02:14:25', NULL, NULL, NULL);
INSERT INTO ct_config_icon (id, channel_id, url, title, jump_url, sort, create_time, update_time, delete_time, tag) VALUES(4, '000', 'https://oss.x-x-x-x-x-x-x.com/menu/4.png', '耳机', '/pages/product/product?productId=1691720666670', 4, '2023-08-08 02:14:25', NULL, NULL, NULL);
INSERT INTO ct_config_icon (id, channel_id, url, title, jump_url, sort, create_time, update_time, delete_time, tag) VALUES(5, '000', 'https://oss.x-x-x-x-x-x-x.com/menu/5.png', '手表', '/pages/product/product?productId=1691720666670', 5, '2023-08-08 02:14:25', NULL, NULL, NULL);
INSERT INTO ct_config_icon (id, channel_id, url, title, jump_url, sort, create_time, update_time, delete_time, tag) VALUES(6, '000', 'https://oss.x-x-x-x-x-x-x.com/menu/6.png', '摄影设备', '/pages/product/product?productId=1691720666670', 6, '2023-08-08 02:14:25', NULL, NULL, NULL);
INSERT INTO ct_config_icon (id, channel_id, url, title, jump_url, sort, create_time, update_time, delete_time, tag) VALUES(7, '000', 'https://oss.x-x-x-x-x-x-x.com/menu/7.gif', '华为', '/pages/product/product?productId=1691720666670', 7, '2023-08-08 02:14:25', NULL, NULL, NULL);
INSERT INTO ct_config_icon (id, channel_id, url, title, jump_url, sort, create_time, update_time, delete_time, tag) VALUES(8, '000', 'https://oss.x-x-x-x-x-x-x.com/menu/8.gif', '活动专区', '/pages/product/product?productId=1691720666670', 8, '2023-08-08 02:14:25', NULL, NULL, NULL);

INSERT INTO ct_config_banner (id, channel_id, url, jump_url, sort, begin_time, end_time, open_status, create_time, update_time, delete_time) VALUES(1, '000', 'https://oss.x-x-x-x-x-x-x.com/banner/1.jpg', 'pages/product/product?productId=1691720666670', 1, '2023-07-31 11:00:00', '2028-09-30 10:59:59', 1, '2023-08-08 02:02:36', '2023-08-08 02:02:51', NULL);
INSERT INTO ct_config_banner (id, channel_id, url, jump_url, sort, begin_time, end_time, open_status, create_time, update_time, delete_time) VALUES(2, '000', 'https://oss.x-x-x-x-x-x-x.com/banner/2.jpg', 'pages/product/product?productId=1691720666670', 2, '2023-07-31 11:00:00', '2028-09-30 10:59:59', 1, '2023-08-08 02:02:36', '2023-08-08 02:02:51', NULL);
INSERT INTO ct_config_banner (id, channel_id, url, jump_url, sort, begin_time, end_time, open_status, create_time, update_time, delete_time) VALUES(3, '000', 'https://oss.x-x-x-x-x-x-x.com/banner/3.jpg', 'pages/product/product?productId=1691720666670', 3, '2023-07-31 11:00:00', '2028-09-30 10:59:59', 1, '2023-08-08 02:02:36', '2023-08-08 02:02:51', NULL);
INSERT INTO ct_config_banner (id, channel_id, url, jump_url, sort, begin_time, end_time, open_status, create_time, update_time, delete_time) VALUES(4, '000', 'https://oss.x-x-x-x-x-x-x.com/banner/4.jpg', 'pages/product/product?productId=1691720666670', 4, '2023-07-31 11:00:00', '2028-09-30 10:59:59', 1, '2023-08-08 02:02:36', '2023-08-08 02:02:51', NULL);



INSERT INTO ct_config_cubes (id, channel_id, paperwork, paperwork_copy, product_ids, url, jump_url, create_time, update_time, delete_time) VALUES(2, '000', '摄影梦想家', '看镜头｜眼前就是诗和远方', '1691657769802,1691720666670', 'https://oss.x-x-x-x-x-x-x.com/backstage/d1.jpg', 'h', '2023-08-12 02:12:09', '2023-08-12 02:33:19', NULL);

INSERT INTO `ct_tab` VALUES (17,'2023-08-11 21:50:21',NULL,'2023-08-11 21:50:21',1,'全新苹果','#','000',NULL),
                            (18,'2023-08-12 10:50:39',NULL,'2023-08-11 21:50:39',2,'手机优选','#','000',NULL),
                            (19,'2023-08-11 21:50:35',NULL,'2023-08-11 21:50:35',3,'爆款热租','#','000',NULL),
                            (20,'2023-08-11 21:50:45',NULL,'2023-08-11 21:50:45',4,'爆款手机','#','000',NULL);

# 阿里云短信
CREATE TABLE `ct_aliyun_sms_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tpl_id` varchar(64) DEFAULT NULL COMMENT '短信模板Id',
  `template_code` varchar(16) NOT NULL COMMENT '阿里云短信的模板ID',
  `autograph` varchar(64) NOT NULL COMMENT '签名',
  `content` varchar(500) NOT NULL COMMENT '短信内容',
  `use_case` varchar(500) DEFAULT NULL COMMENT '使用场景',
  `status` bigint(20) DEFAULT NULL COMMENT '是否生效 0:有效,1:无效',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `sms_type` varchar(64) NOT NULL COMMENT '短信行业类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `all_idx` (`tpl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=482 DEFAULT CHARSET=utf8mb4 COMMENT='阿里云短信模板';

CREATE TABLE `ct_aliyun_sms_send_serial` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sms_code` varchar(64) NOT NULL COMMENT '短信编号',
  `template_code` varchar(16) DEFAULT NULL COMMENT '阿里云模板ID',
  `params` varchar(255) NOT NULL COMMENT '短信参数',
  `mobile` varchar(64) NOT NULL COMMENT '接收人手机号',
  `result` mediumtext COMMENT '发送结果',
  `status` varchar(32) DEFAULT NULL COMMENT '发送结果返回码',
  `balance` varchar(255) DEFAULT NULL COMMENT '当前账户余额，单位厘',
  `channel_id` varchar(10) NOT NULL COMMENT '渠道编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `sms_type` varchar(255) DEFAULT NULL COMMENT '短信行业类型',
  `bizId` varchar(32) DEFAULT NULL COMMENT '发送回执ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;


INSERT INTO ct_aliyun_sms_template
(id, tpl_id, template_code, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(312, 'ORDER_BUY_OUT_SUCCESS', 'SMS_462550290', 'XX租赁', '您尾号为${orderId}的订单已买断支付成功,有问题请联系客服,工作日9:30—18:00在线', NULL, 0, '2021-01-06 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_aliyun_sms_template
(id, tpl_id, template_code, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(316, 'ORDER_PALT_CLOSE', 'SMS_462610277', 'XX租赁', '您尾号为${orderNo}的订单已取消退款并解冻押金,金额已原路退回请查收,有问题请拨打客服热线,在线9:30-18:00', NULL, 0, '2021-01-10 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_aliyun_sms_template
(id, tpl_id, template_code, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(317, 'BUSINESS_DELIVERY', 'SMS_462645147', 'XX租赁', '您租的：${productName} 已发货;发货物流公司(${logisticsName});快递单号(${expressNo})', NULL, 0, '2021-01-11 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_aliyun_sms_template
(id, tpl_id, template_code, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(318, 'RISK_CLOSE', 'SMS_462555282', 'XX租赁', '您的尾号为为${orderId}的订单审核未通过，已取消退款并解冻押金,金额已原路退回请查收,有问题请拨打客服热线', NULL, 0, '2021-01-12 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_aliyun_sms_template
(id, tpl_id, template_code, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(319, 'ORDER_PAY_SUCCESS', 'SMS_462595264', 'XX租赁', '您的尾号为${orderId}的订单已支付成功, 商品已备货，请等待审核通过后发货。有问题请拨打客服热线,工作日(9:30-18:00)在线。温馨提示：近期电信网络诈骗高发，凡是要求私下交易转账的都是诈骗，请勿轻易操作以防被骗。', NULL, 0, '2021-01-13 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_aliyun_sms_template
(id, tpl_id, template_code, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(320, 'ORDER_PAY_FAIL_CLOSE', 'SMS_462620267', 'XX租赁', '您的尾号为${orderId}的订单扣款失败已关闭，请确保支付宝账户余额或余额宝金额大于首期实付款；在支付宝搜索XX租赁，重新下单。', NULL, 0, '2021-01-14 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_aliyun_sms_template
(id, tpl_id, template_code, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(322, 'REAL_NAME', 'SMS_462565130', 'XX租赁', '您正在进行身份认证，您的验证码是${code}。如非本人操作，请忽略本短信', NULL, 0, '2021-01-16 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_aliyun_sms_template
(id, tpl_id, template_code, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(399, 'RELET_ORDER_PAY_SUCCESS', 'SMS_462590273', 'XX租赁', '您的订单已续租成功。有问题请拨打客服热线,工作日(9:30-18:00)在线', NULL, 0, '2021-08-02 09:40:51', NULL, NULL, 'RENT');
INSERT INTO ct_aliyun_sms_template
(id, tpl_id, template_code, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(400, 'RELET_TO_BUSINESS', 'SMS_462650128', 'XX租赁', '商家您好,用户:${userName},订单号${OrderId},已提交续租，续租订单号${ReletOrderId}，请登录XX租赁商家管理后台查看。', NULL, 0, '2021-08-02 09:40:08', NULL, NULL, 'RENT');
INSERT INTO ct_aliyun_sms_template
(id, tpl_id, template_code, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(481, 'BUSINESS_UPDATE_PASSWORD', 'SMS_462605158', 'XX租赁', '正在找回密码，您的验证码是${code}', NULL, 0, '2022-06-22 16:08:12', NULL, NULL, 'RENT');


# 迅达短信模板
CREATE TABLE `ct_xunda_sms_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tpl_id` varchar(64) DEFAULT NULL COMMENT '短信模板Id',
  `autograph` varchar(64) NOT NULL COMMENT '签名',
  `content` varchar(500) NOT NULL COMMENT '短信内容',
  `use_case` varchar(500) DEFAULT NULL COMMENT '使用场景',
  `status` bigint(20) DEFAULT NULL COMMENT '是否生效 0:有效,1:无效',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `sms_type` varchar(64) NOT NULL COMMENT '短信行业类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `all_idx` (`tpl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=482 DEFAULT CHARSET=utf8mb4 COMMENT='讯达短信模板';

CREATE TABLE `ct_xunda_sms_send_serial` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='讯达短信发送记录';

INSERT INTO ct_xunda_sms_template
(id, tpl_id, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(312, 'ORDER_BUY_OUT_SUCCESS', 'XX租赁', '您尾号为#orderId#的订单已买断支付成功,有问题请联系客服LINK_MOBILE,工作日9:30—18:00在线', NULL, 0, '2021-01-06 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_xunda_sms_template
(id, tpl_id, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(316, 'ORDER_PALT_CLOSE', 'XX租赁', '您尾号为#orderNo#的订单已取消退款并解冻押金,金额已原路退回请查收,有问题请拨打#serviceTel#,在线9:30-18:00', NULL, 0, '2021-01-10 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_xunda_sms_template
(id, tpl_id, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(317, 'BUSINESS_DELIVERY', 'XX租赁', '您租的：#productName# 已发货;发货物流公司(#logisticsName#);快递单号(#expressNo#)', NULL, 0, '2021-01-11 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_xunda_sms_template
(id, tpl_id, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(318, 'RISK_CLOSE', 'XX租赁', '您的尾号为为#orderId#的订单审核未通过，已取消退款并解冻押金,金额已原路退回请查收,有问题请拨打#serviceTel#', NULL, 0, '2021-01-12 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_xunda_sms_template
(id, tpl_id, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(319, 'ORDER_PAY_SUCCESS', 'XX租赁', '您的尾号为#orderId#的订单已支付成功, 商品已备货，请等待审核通过后发货。有问题请拨打#serviceTel#,工作日(9:30-18:00)在线。温馨提示：近期电信网络诈骗高发，凡是要求私下交易转账的都是诈骗，请勿轻易操作以防被骗。', NULL, 0, '2021-01-13 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_xunda_sms_template
(id, tpl_id, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(320, 'ORDER_PAY_FAIL_CLOSE', 'XX租赁', '您的尾号为#orderId#的订单扣款失败已关闭，请确保支付宝账户余额或余额宝金额大于首期实付款；在支付宝搜索XX租赁，重新下单。', NULL, 0, '2021-01-14 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_xunda_sms_template
(id, tpl_id, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(322, 'REAL_NAME', 'XX租赁', '您正在进行身份认证，您的验证码是#code#。如非本人操作，请忽略本短信', NULL, 0, '2021-01-16 16:05:04', NULL, NULL, 'RENT');
INSERT INTO ct_xunda_sms_template
(id, tpl_id, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(399, 'RELET_ORDER_PAY_SUCCESS', 'XX租赁', '您的【XX租赁】订单已续租成功。有问题请拨打#serviceTel#,工作日(9:30-18:00)在线', NULL, 0, '2021-08-02 09:40:51', NULL, NULL, 'RENT');
INSERT INTO ct_xunda_sms_template
(id, tpl_id, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(400, 'RELET_TO_BUSINESS', 'XX租赁', '商家您好,用户:#userName#,订单号#OrderId#,已提交续租，续租订单号#ReletOrderId#，请登录XX租赁商家管理后台查看。', NULL, 0, '2021-08-02 09:40:08', NULL, NULL, 'RENT');
INSERT INTO ct_xunda_sms_template
(id, tpl_id, autograph, content, use_case, status, create_time, update_time, delete_time, sms_type)
VALUES(481, 'BUSINESS_UPDATE_PASSWORD', 'XX租赁', '正在找回密码，您的验证码是#code#', NULL, 0, '2022-06-22 16:08:12', NULL, NULL, 'RENT');
