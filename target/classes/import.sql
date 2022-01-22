
insert into orders(order_no, order_amt, pg_cd, order_method, tid, create_dt, order_status) values ('ORDER_1', 1000, 'INICIS', 'VACCT',  'INICIS_VACCT_1', '20220120', 'DEPOSIT')
insert into orders(order_no, order_amt, pg_cd, order_method, tid, create_dt, order_status) values ('ORDER_2', 2000, 'INICIS', 'CARD',  'INICIS_CARD_2', '20220120', 'APPROVED')
insert into orders(order_no, order_amt, pg_cd, order_method, tid, create_dt, order_status) values ('ORDER_3', 3000, 'INICIS', 'VACCT',  'INICIS_VACCT_3', '20220120', 'DEPOSIT')
insert into orders(order_no, order_amt, pg_cd, order_method, tid, create_dt, order_status) values ('ORDER_4', 1000, 'INICIS', 'CARD',  'INICIS_CARD_4', '20220120', 'APPROVED')
insert into orders(order_no, order_amt, pg_cd, order_method, tid, create_dt, order_status) values ('ORDER_5', 5000, 'INICIS', 'VACCT',  'INICIS_VACCT_5', '20220120', 'DEPOSIT')
insert into orders(order_no, order_amt, pg_cd, order_method, tid, create_dt, order_status) values ('ORDER_6', 8000, 'INICIS', 'CARD',  'INICIS_CARD_6', '20220120', 'APPROVED')
insert into orders(order_no, order_amt, pg_cd, order_method, tid, create_dt, order_status) values ('ORDER_7', 9000, 'TOSS', 'VACCT',  'TOSS_VACCT_7', '20220120', 'DEPOSIT')
insert into orders(order_no, order_amt, pg_cd, order_method, tid, create_dt, order_status) values ('ORDER_8', 1100, 'TOSS', 'CARD',  'TOSS_CARD_8', '20220120', 'APPROVED')
insert into orders(order_no, order_amt, pg_cd, order_method, tid, create_dt, order_status) values ('ORDER_9', 3200, 'TOSS', 'VACCT',  'TOSS_VACCT_9', '20220120', 'DEPOSIT')
insert into orders(order_no, order_amt, pg_cd, order_method, tid, create_dt, order_status) values ('ORDER_10', 7800, 'TOSS', 'CARD',  'TOSS_CARD_10', '20220120', 'APPROVED')
insert into orders(order_no, order_amt, pg_cd, order_method, tid, create_dt, order_status) values ('ORDER_11', 5500, 'TOSS', 'VACCT',  'TOSS_VACCT_11', '20220120', 'DEPOSIT')
insert into orders(order_no, order_amt, pg_cd, order_method, tid, create_dt, order_status) values ('ORDER_12', 4300, 'TOSS', 'CARD',  'TOSS_CARD_12', '20220120', 'CANCELD')



-- 대사 내역이 한 건 적고, 금액이 다르고, 한 건 취소됨
insert into vacct(order_no, tid, pg_cd, deposit_dt, cancel_dt, deposit_amt, bank_cd, acct_no, deposit_nm, buyer, compare_status) values ('ORDER_1', 'INICIS_VACCT_1', 'INICIS', '20220120', '', 1000, '04', '222444333', 'Tom', 'Tom',  'MATCH_TARGET')
insert into vacct(order_no, tid, pg_cd, deposit_dt, cancel_dt, deposit_amt, bank_cd, acct_no, deposit_nm, buyer, compare_status) values ('ORDER_3', 'INICIS_VACCT_3', 'INICIS', '20220120', '', 2000, '02', '111222333', 'Sam', 'Sam',  'MATCH_TARGET')
insert into vacct(order_no, tid, pg_cd, deposit_dt, cancel_dt, deposit_amt, bank_cd, acct_no, deposit_nm, buyer, compare_status) values ('ORDER_5', 'INICIS_VACCT_5', 'INICIS', '20220120', '', 6000, '20', '555222333', 'Tom', 'Tom',  'MATCH_TARGET')
insert into vacct(order_no, tid, pg_cd, deposit_dt, cancel_dt, deposit_amt, bank_cd, acct_no, deposit_nm, buyer, compare_status) values ('ORDER_7', 'TOSS_VACCT_7', 'TOSS', '20220120', '', 9000, '11', '777222333', 'Mike', 'Mike',  'MATCH_TARGET')
insert into vacct(order_no, tid, pg_cd, deposit_dt, cancel_dt, deposit_amt, bank_cd, acct_no, deposit_nm, buyer, compare_status) values ('ORDER_9', 'TOSS_VACCT_9', 'TOSS', '20220120', '', 3200, '88', '999222333', 'Sally', 'Sally',  'MATCH_TARGET')
insert into vacct(order_no, tid, pg_cd, deposit_dt, cancel_dt, deposit_amt, bank_cd, acct_no, deposit_nm, buyer, compare_status) values ('ORDER_9', 'TOSS_VACCT_9', 'TOSS', '20220120', '20220120', 3200, '88', '999222333', 'Sally', 'Sally',  'MATCH_TARGET')

-- 대사 내역이 한 건 많음, 한 건 취소됨
insert into card(order_no, tid, pg_cd, approve_dt, cancel_dt, approve_amt, card_cd, approve_no, installment, approve_status, compare_status) values ('ORDER_2', 'INICIS_CARD_2', 'INICIS', '20220120', '', 2000, '04', '11111111', '00', 'APPROVED', 'MATCH_TARGET')
insert into card(order_no, tid, pg_cd, approve_dt, cancel_dt, approve_amt, card_cd, approve_no, installment, approve_status, compare_status) values ('ORDER_4', 'INICIS_CARD_4', 'INICIS', '20220120', '', 1000, '04', '22222222', '00', 'APPROVED', 'MATCH_TARGET')
insert into card(order_no, tid, pg_cd, approve_dt, cancel_dt, approve_amt, card_cd, approve_no, installment, approve_status, compare_status) values ('ORDER_6', 'INICIS_CARD_6', 'INICIS', '20220120', '', 8000, '04', '33333333', '00', 'APPROVED', 'MATCH_TARGET')
insert into card(order_no, tid, pg_cd, approve_dt, cancel_dt, approve_amt, card_cd, approve_no, installment, approve_status, compare_status) values ('ORDER_8', 'TOSS_CARD_8', 'TOSS', '20220120', '', 1100, '04', '44444444', '00', 'APPROVED', 'MATCH_TARGET')
insert into card(order_no, tid, pg_cd, approve_dt, cancel_dt, approve_amt, card_cd, approve_no, installment, approve_status, compare_status) values ('ORDER_10', 'TOSS_CARD_10', 'TOSS', '20220120', '', 7800, '04', '55555555', '00', 'APPROVED', 'MATCH_TARGET')
insert into card(order_no, tid, pg_cd, approve_dt, cancel_dt, approve_amt, card_cd, approve_no, installment, approve_status, compare_status) values ('ORDER_12', 'TOSS_CARD_12', 'TOSS', '20220120', '', 4300, '04', '66666666', '00', 'APPROVED', 'MATCH_TARGET')
insert into card(order_no, tid, pg_cd, approve_dt, cancel_dt, approve_amt, card_cd, approve_no, installment, approve_status, compare_status) values ('ORDER_14', 'TOSS_CARD_14', 'TOSS', '20220119', '20220120', 1000, '04', '77777777', '12', 'CANCELED', 'MATCH_TARGET')






