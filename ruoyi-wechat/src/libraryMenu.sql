-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('图片库', '3', '1', '/weixin/library', 'C', '0', 'weixin:library:view', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '图片库菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('图片库查询', @parentId, '1',  '#',  'F', '0', 'weixin:library:list',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('图片库新增', @parentId, '2',  '#',  'F', '0', 'weixin:library:add',          '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('图片库修改', @parentId, '3',  '#',  'F', '0', 'weixin:library:edit',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('图片库删除', @parentId, '4',  '#',  'F', '0', 'weixin:library:remove',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('图片库导出', @parentId, '5',  '#',  'F', '0', 'weixin:library:export',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');
