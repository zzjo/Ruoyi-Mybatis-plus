-- auto Generated on 2021-10-02
-- DROP TABLE IF EXISTS wx_picture_library;
CREATE TABLE wx_picture_library(
	create_by VARCHAR (50) NOT NULL COMMENT '创建者',
	create_time DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR (50) NOT NULL COMMENT '更新者',
	update_time TIMESTAMP NOT NULL COMMENT '更新时间',
	remark VARCHAR (50) NOT NULL COMMENT '备注',
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	holidays_menu_id INT (11) NOT NULL COMMENT '假日外键',
	picture_path VARCHAR (100) NOT NULL COMMENT '图片路径',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '图片库';
