package be.fresher_manage.model.dto;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Center")
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "NameCenter")
    private String nameCenter;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCenterId")
    private Center parentCenter;

    @OneToMany(mappedBy = "parentCenter", cascade = CascadeType.ALL)
    private Set<Center> childCenter;

    public Center() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameCenter() {
        return nameCenter;
    }

    public void setNameCenter(String nameCenter) {
        this.nameCenter = nameCenter;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Center getParentCenter() {
        return parentCenter;
    }

    public void setParentCenter(Center parentCenter) {
        this.parentCenter = parentCenter;
    }

    public Set<Center> getChildCenter() {
        return childCenter;
    }

    public void setChildCenter(Set<Center> childCenter) {
        this.childCenter = childCenter;
    }
}
