-- noinspection SqlResolveForFile

insert into my_user (name, password, enabled, locked, role)
    values('luigi', '$2y$12$6qmXIRmg5yIFubma3QFkReJocbIzRFFYXbjWjqYvhGgjL.YWYjzny', true, false, 1);
insert into plan (name, user_id, start, end, allday) values('walk', 1, '2021-2-28 22:11:11', CURRENT_TIMESTAMP, false);
insert into plan (name, user_id, start, end, allDay) values('sleep', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
insert into plan (name, user_id, start, end, allDay) values('laugh',1 ,'2005-12-31 23:59:59','2005-12-31 23:59:59', false);
insert into task (name, user_id, duration, start, end) values('feed cat', 1, 3600, '2020-1-30 22:11:11', '2020-1-30 22:11:11');
insert into my_user (name, password, enabled, locked, role)
    values('oyu', '$2y$12$6INmJjaMYdSRcpdnugObke1mqVCKScs/T7Swxef9dd3RtaaW9GD8m', true, false, 1);
insert into plan (name, user_id, start, end, allDay) values('eat', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
insert into plan (name, user_id, start, end, allDay) values('sit', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
insert into plan (name, user_id, start, end, allDay) values('look',2 ,'2005-12-31 23:59:59','2005-12-31 23:59:59', false);
