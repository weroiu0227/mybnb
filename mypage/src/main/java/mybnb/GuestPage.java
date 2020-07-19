package mybnb;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="GuestPage_table")
public class GuestPage {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private Long guestId;
        private Long bookId;
        private Long roomId;
        private Long price;
        private String status;


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
        public Long getBookId() {
            return bookId;
        }

        public void setBookId(Long bookId) {
            this.bookId = bookId;
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

}
