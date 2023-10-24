--  给admin部门添加权限
DELETE from ct_backstage_department_function where department_id=1;
INSERT INTO `ct_backstage_department_function` (
	`department_id`,
	`function_id`,
	`create_time`
) SELECT
	'1',
	id,
	'2021-11-22 16:03:20'
FROM
	ct_backstage_function
WHERE
	platform = "OPE";

--  给用户添加权限
DELETE from ct_backstage_user_function where backstage_user_id=1;
INSERT INTO `ct_backstage_user_function` (
	`backstage_user_id`,
	`function_id`,
	`source_type`,
	`source_value`,
	`create_time`
)(
	SELECT
		'1',
		id,
		'ADD',
		'0',
		'2021-11-22 16:03:20'
	FROM
		ct_backstage_function
	WHERE
		platform = "OPE"
);


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
INSERT INTO `ct_backstage_department_function` (
	`department_id`,
	`function_id`,
	`create_time`
) SELECT
	'2',
	id,
	'2021-11-22 16:03:20'
FROM
	ct_backstage_function
WHERE
	platform = "SHOP";


INSERT INTO `ct_backstage_department_function` (
	`department_id`,
	`function_id`,
	`create_time`
) SELECT
	'4',
	id,
	'2021-11-22 16:03:20'
FROM
	ct_backstage_function
WHERE
	platform = "CHANNEL";