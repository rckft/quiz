package dev.rckft.notificationservice.notification.log;

import org.springframework.data.repository.CrudRepository;

public interface DeliveryLogRepository extends CrudRepository<DeliveryLog, Long> {

    DeliveryLog findBySourceRequestId(Long sourceRequestId);

}