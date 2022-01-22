package CompareAPI.myAPI.data.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import CompareAPI.myAPI.data.jpa.domain.Orders;

import java.util.List;


@Service
class OrdersServiceImpl implements OrdersService {

	private final OrdersRepository ordersRepository;

	@Autowired
	public OrdersServiceImpl(OrdersRepository ordersRepository) {
		this.ordersRepository = ordersRepository;
	}

	@Override
	public List<Orders> getOrders(String orderMethod, String pgCd, String createDt) {
		Assert.hasLength(orderMethod, "orderMethod must not be empty");
		Assert.hasLength(pgCd, "pgCd must not be empty");
		Assert.hasLength(createDt, "createDt must not be empty");
		return this.ordersRepository.findByOrderMethodAndPgCdAndCreateDt(orderMethod, pgCd, createDt);
	}

	@Override
	public Orders getOrderByTid(String tid) {
		Assert.hasLength(tid, "tid must not be empty");
		return this.ordersRepository.findByTid(tid);
	}



}
