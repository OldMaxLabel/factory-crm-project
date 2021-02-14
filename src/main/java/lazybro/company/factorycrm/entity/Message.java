package lazybro.company.factorycrm.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Message extends Identify {

    @NotBlank(message = "Обязательно нужно написать текст")
    @Size(max = 500, message = "Не более 500 символов")
    private String text;

    @NotBlank(message = "Обязательно нужно указать номер заказа")
    @Size(min = 3, max = 10, message = "Не менее 3 и не более 10 символов")
    @Column(nullable = false)
    private String tag;

    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Message() {
    }

    public Message(String text, String tag, User author) {
        this.text = text;
        this.tag = tag;
        this.author= author;
        this.localDateTime = LocalDateTime.now().withNano(0);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
