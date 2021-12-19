-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('wx_holidays_menu', '3', '1', '/weixin/menu', 'C', '0', 'weixin:menu:view', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', 'wx_holidays_menu菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('wx_holidays_menu查询', @parentId, '1',  '#',  'F', '0', 'weixin:menu:list',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('wx_holidays_menu新增', @parentId, '2',  '#',  'F', '0', 'weixin:menu:add',          '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('wx_holidays_menu修改', @parentId, '3',  '#',  'F', '0', 'weixin:menu:edit',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('wx_holidays_menu删除', @parentId, '4',  '#',  'F', '0', 'weixin:menu:remove',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('wx_holidays_menu导出', @parentId, '5',  '#',  'F', '0', 'weixin:menu:export',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');
