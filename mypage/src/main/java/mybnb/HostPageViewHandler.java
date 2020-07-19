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
public class HostPageViewHandler {


    @Autowired
    private HostPageRepository hostPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBooked_then_CREATE_1 (@Payload Booked booked) {
        try {
            if (booked.isMe()) {
                // view 객체 생성
                HostPage hostPage = new HostPage();
                // view 객체에 이벤트의 Value 를 set 함
                hostPage.setRoomId(booked.getRoomId());
                hostPage.setGuestId(booked.getGuestId());
                hostPage.setBookId(booked.getId());
                hostPage.setPrice(booked.get금액());
                hostPage.setStatus(booked.getStatus());
                // view 레파지 토리에 save
                hostPageRepository.save(hostPage);
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
                List<HostPage> hostPageList = hostPageRepository.findByBookId(bookCanceled.getId());
                for(HostPage hostPage : hostPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    hostPage.setStatus(bookCanceled.getStatus());
                    // view 레파지 토리에 save
                    hostPageRepository.save(hostPage);
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
                List<HostPage> hostPageList = hostPageRepository.findByBookId(payCanceled.getId());
                for(HostPage hostPage : hostPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    hostPage.setStatus(payCanceled.getStatus());
                    // view 레파지 토리에 save
                    hostPageRepository.save(hostPage);
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
                List<HostPage> hostPageList = hostPageRepository.findByBookId(reviewRegistered.getBookId());
                for(HostPage hostPage : hostPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    hostPage.setScore(reviewRegistered.getScore());
                    // view 레파지 토리에 save
                    hostPageRepository.save(hostPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}