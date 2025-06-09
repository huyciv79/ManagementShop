package com.example.managementbook.service;
import com.example.managementbook.model.dto.OrderDetailDTO;
import com.example.managementbook.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetailDTO> getAllOrderDetailsByOrder(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId).stream()
                .map(detail -> {
                    OrderDetailDTO dto = new OrderDetailDTO();
                    dto.setId(detail.getId());
                    dto.setQuantity(detail.getQuantity());
                    dto.setUnitPrice(detail.getUnitPrice());
                    dto.setProductId(detail.getProduct().getId());
                    dto.setOrderId(detail.getOrder().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}