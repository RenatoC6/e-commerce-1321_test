package br.ada.ecommerce.application.usecases.impl.order;

import br.ada.ecommerce.application.model.Order;
import br.ada.ecommerce.application.model.OrderStatus;
import br.ada.ecommerce.application.usecases.exception.IllegalOrderStateException;
import br.ada.ecommerce.application.usecases.order.IOrderPlaceUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderPlaceUseCaseImpl implements IOrderPlaceUseCase {


    @Override
    public void placeOrder(Order order) {
        if (order.getStatus() != OrderStatus.OPEN) {
            throw new IllegalOrderStateException("Pedido com estado invalido");
        }
        var total = amount(order);
        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalOrderStateException("Pedido zerado, não é possível prosseguir");
        }
        order.setStatus(OrderStatus.PENDING_PAYMENT);
    }

    private BigDecimal amount(Order order) {
        return order.getItems()
                .stream()
                .map(item -> item.getSaleValue().multiply(BigDecimal.valueOf(item.getAmount())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

}
