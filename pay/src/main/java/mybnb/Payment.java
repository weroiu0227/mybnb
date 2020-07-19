package mybnb;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Payment_table")
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long bookId;
    private Long guestId;
    private Long roomId;
    private Long price;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @PostPersist
    public void onPostPersist(){
        if("PayApproved".equals(getStatus())) {
            PayApproved payApproved = new PayApproved();
            BeanUtils.copyProperties(this, payApproved);
            payApproved.setStatus(getStatus());
            payApproved.publishAfterCommit();
        }
        else if("PayCanceled".equals(getStatus())) {
            PayCanceled payCanceled = new PayCanceled();
            BeanUtils.copyProperties(this, payCanceled);
            payCanceled.setStatus(getStatus());
            payCanceled.publishAfterCommit();
        }
    }

}
