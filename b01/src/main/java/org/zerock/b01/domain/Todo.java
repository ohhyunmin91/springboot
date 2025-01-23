package org.zerock.b01.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
@Builder
@ToString
public class Todo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long tno;
    @Column(length = 100, nullable = false)
    public String title;
    @Column(nullable = false)
    public LocalDate dueDate;
    @Column(length = 500, nullable = false)
    public String writer;
    @ColumnDefault("0")
    public boolean finished;

    public void change(String title, Boolean finished){
        this.title = title;
        this.finished = finished;
    }
}
