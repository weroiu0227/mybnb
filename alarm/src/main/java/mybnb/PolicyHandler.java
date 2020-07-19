package mybnb;

import mybnb.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    private NotificationHistoryRepository notificationHistoryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayApproved_Notify(@Payload PayApproved payApproved){

        if(payApproved.isMe()){
            //System.out.println("##### listener Notify : " + payApproved.toJson());
            addNotificationHistory(payApproved.getGuestId(), "PayApproved");
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCanceled_Notify(@Payload PayCanceled payCanceled){

        if(payCanceled.isMe()){
            //System.out.println("##### listener Notify : " + payCanceled.toJson());
            addNotificationHistory(payCanceled.getGuestId(), "PayCanceled");
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBooked_Notify(@Payload Booked booked){

        if(booked.isMe()){
            //System.out.println("##### listener Notify : " + booked.toJson());
            addNotificationHistory(booked.getGuestId(), "Booked");
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookCanceled_Notify(@Payload BookCanceled bookCanceled){

        if(bookCanceled.isMe()){
            //System.out.println("##### listener Notify : " + bookCanceled.toJson());
            addNotificationHistory(bookCanceled.getGuestId(), "BookCanceled");
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReviewRegistered_Notify(@Payload ReviewRegistered reviewRegistered){

        if(reviewRegistered.isMe()){
            //System.out.println("##### listener Notify : " + reviewRegistered.toJson());
            addNotificationHistory(reviewRegistered.getGuestId(), "ReviewRegistered");
        }
    }

    private void addNotificationHistory(long receiver, String message) {
        NotificationHistory history = new NotificationHistory();
        history.setReceiver(String.valueOf(receiver));
        history.setMessage(message);
        notificationHistoryRepository.save(history);
    }

}
