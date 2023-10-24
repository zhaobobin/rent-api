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