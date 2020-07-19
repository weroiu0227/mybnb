package mybnb;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Booking_table")
public class Booking {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long guestId;
    private Long roomId;
    private Long price;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    @PostPersist
    public void onPostPersist(){
        // 예약시 결제까지 트랜잭션을 통합을 위해 결제 서비스 직접 호출
        {
            mybnb.external.Payment payment = new mybnb.external.Payment();
            payment.setBookId(getId());
            payment.setGuestId(getGuestId());
            payment.setRoomId(getRoomId());
            payment.setPrice(getPrice());
            payment.setStatus("PayApproved");
            // mappings goes here
            BookingApplication.applicationContext.getBean(mybnb.external.PaymentService.class)
                    .pay(payment);
        }
        
        // 결제까지 완료되면 최종적으로 예약 완료 이벤트 발생
        Booked booked = new Booked();
        BeanUtils.copyProperties(this, booked);
        booked.setStatus("Booked");
        booked.publishAfterCommit();
    }

    @PostRemove
    public void onPostRemove(){
        BookCanceled bookCanceled = new BookCanceled();
        BeanUtils.copyProperties(this, bookCanceled);
        bookCanceled.setStatus("BookCanceled");
        bookCanceled.publishAfterCommit();
    }

}
