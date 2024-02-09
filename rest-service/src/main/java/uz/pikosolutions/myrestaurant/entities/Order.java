package uz.pikosolutions.myrestaurant.entities;

import lombok.Data;
import org.hibernate.annotations.Formula;
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

    @Column(name = "persons_count")
    private Integer personsCount;

    @Column(name = "service")
    private Integer service;

    @Column(name = "cost")
    @Formula(value = "(SELECT SUM(d.cost * c.dish_count / 100 * o.service + d.cost * c.dish_count) FROM _order o " +
            "join ORDER_DISH c ON o.id = c.order_id " +
            "join _DISH d ON c.dish_id = d.id " +
            "WHERE c.order_id = o.id)")
    private Float cost;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_user_id", nullable = false)
    private User creator;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = {CascadeType.ALL})
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
