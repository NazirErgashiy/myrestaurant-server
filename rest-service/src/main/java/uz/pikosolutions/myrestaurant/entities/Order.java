package uz.pikosolutions.myrestaurant.entities;

import lombok.Data;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
import java.util.Map;

@Data
@Entity
@Table(name = "_order")
@NamedEntityGraph(name = "user_eg", attributeNodes = {@NamedAttributeNode("creator"), @NamedAttributeNode("dishes")})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer personsCount;

    private Integer service;

    @Transient
    @Formula("dishes.cost")
    private Float cost;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_user_id", nullable = false)
    private User creator;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "order_dishes",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "dish_id")})
    private List<Dish> dishes;

    @ElementCollection(targetClass = Float.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "dishes_count", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "dish_count", nullable = false)
    private List<Float> dishCount;

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
