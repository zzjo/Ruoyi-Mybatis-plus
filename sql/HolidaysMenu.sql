-- auto Generated on 2021-10-02
-- DROP TABLE IF EXISTS wx_holidays_menu;
CREATE TABLE wx_holidays_menu(
	create_by VARCHAR (50) NOT NULL COMMENT '创建者',
	create_time DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR (50) NOT NULL COMMENT '更新者',
	update_time TIMESTAMP NOT NULL COMMENT '更新时间',
	remark VARCHAR (50) NOT NULL COMMENT '备注',
	id BIGINT (15) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`name` VARCHAR (50) NOT NULL COMMENT '假日名称',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'wx_holidays_menu';
