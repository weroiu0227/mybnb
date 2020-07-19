package mybnb;

import mybnb.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class GuestPageViewHandler {


    @Autowired
    private GuestPageRepository guestPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBooked_then_CREATE_1 (@Payload Booked booked) {
        try {
            if (booked.isMe()) {
                System.out.println("Booked " + booked.toJson());

                // view 객체 생성
                GuestPage guestPage = new GuestPage();
                // view 객체에 이벤트의 Value 를 set 함
                guestPage.setGuestId(booked.getGuestId());
                guestPage.setRoomId(booked.getRoomId());
                guestPage.setPrice(booked.get금액());
                guestPage.setStatus(booked.getStatus());
                guestPage.setBookId(booked.getId());
                // view 레파지 토리에 save
                guestPageRepository.save(guestPage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookCanceled_then_UPDATE_1(@Payload BookCanceled bookCanceled) {
        try {
            if (bookCanceled.isMe()) {
                // view 객체 조회
                List<GuestPage> guestPageList = guestPageRepository.findByBookId(bookCanceled.getId());
                for(GuestPage guestPage : guestPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    guestPage.setStatus(bookCanceled.getStatus());
                    // view 레파지 토리에 save
                    guestPageRepository.save(guestPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayCanceled_then_UPDATE_2(@Payload PayCanceled payCanceled) {
        try {
            if (payCanceled.isMe()) {
                // view 객체 조회
                List<GuestPage> guestPageList = guestPageRepository.findByBookId(payCanceled.getBookId());
                for(GuestPage guestPage : guestPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    guestPage.setStatus(payCanceled.getStatus());
                    // view 레파지 토리에 save
                    guestPageRepository.save(guestPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenReviewRegistered_then_UPDATE_3(@Payload ReviewRegistered reviewRegistered) {
        try {
            if (reviewRegistered.isMe()) {
                // view 객체 조회
                List<GuestPage> guestPageList = guestPageRepository.findByBookId(reviewRegistered.getBookId());
                for(GuestPage guestPage : guestPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    guestPage.setStatus(reviewRegistered.getStatus());
                    // view 레파지 토리에 save
                    guestPageRepository.save(guestPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}