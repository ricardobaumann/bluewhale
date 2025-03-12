package com.github.ricbau.bluewhale.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    private UUID id;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //For future reuse, the below attributes can be added to an @Embedded JPA entity
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

}
