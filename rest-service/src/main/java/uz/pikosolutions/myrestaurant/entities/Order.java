package uz.pikosolutions.myrestaurant.entities;

import lombok.Data;
import uz.pikosolutions.myrestaurant.entities.auxiliary.OrderDish;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "_order")
@NamedEntityGraph(name = "user_eg", attributeNodes = {@NamedAttributeNode("creator"), @NamedAttributeNode("orderDishes")})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer personsCount;

    private Integer service;

    @Transient
    //@Formula("SELECT (o.service) FROM _order o" +
    //        "join ORDER_DISH_COUNT c ON o.id = c.order_id")
    private Float cost;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_user_id", nullable = false)
    private User creator;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order",cascade = {CascadeType.ALL})
    private List<OrderDish> orderDishes;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @PrePersist
    @PreUpdate
    public void prePersistOrUpdate() {
        updateDate = LocalDateTime.now();
        if (createDate == null) {
            createDate = updateDate;
        }
    }
}
