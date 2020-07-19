package mybnb;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Room_table")
public class Room {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long hostId;
    private String name;
    private String price;
    private String address;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getHostId() {
        return hostId;
    }
    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @PostPersist
    public void onPostPersist(){
        RoomRegistered roomRegistered = new RoomRegistered();
        BeanUtils.copyProperties(this, roomRegistered);
        roomRegistered.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate(){
        RoomChanged roomChanged = new RoomChanged();
        BeanUtils.copyProperties(this, roomChanged);
        roomChanged.publishAfterCommit();
    }

    @PostRemove
    public void onPostRemove(){
        RoomDeleted roomDeleted = new RoomDeleted();
        BeanUtils.copyProperties(this, roomDeleted);
        roomDeleted.publishAfterCommit();
    }

}
