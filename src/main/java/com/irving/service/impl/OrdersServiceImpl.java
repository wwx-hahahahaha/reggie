package com.irving.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.irving.commonly.R;
import com.irving.domain.*;
import com.irving.mapper.OrdersMapper;
import com.irving.service.AddressService;
import com.irving.service.OrderDetailService;
import com.irving.service.OrderService;
import com.irving.service.ShoppingCartService;
import com.irving.utlis.ThreadLocalUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrderService {

    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private ShoppingCartService shoppingCartService;

    @Resource
    private AddressService addressService;
    @Transactional
    @Override
    public R<?> submit(Orders orders) {
        User user = (User) ThreadLocalUtils.getObjects();
        LambdaQueryWrapper<ShoppingCart>slm=new LambdaQueryWrapper<>();
        slm.eq(ShoppingCart::getUserId,user.getId());

        //查询地址信息
        AddressBook addressBook = addressService.getById(orders.getAddressBookId());
        if (addressBook==null){
            throw new RuntimeException("当前用户不存在");
        }

        Long orderId= IdWorker.getId();
        AtomicInteger amount = new AtomicInteger(0);

        List<ShoppingCart> shoppingCartList = shoppingCartService.list(slm);
        List<OrderDetail> detailList = shoppingCartList.stream().map(item -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(user.getId());
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        //向订单表插入数据，一条数据
        this.save(orders);

        orderDetailService.saveBatch(detailList);

        return R.success("结算成功");
    }
}
