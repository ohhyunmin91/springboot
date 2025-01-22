package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

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
    @Column(length = 500, nullable = false)
    public String writer;
    public boolean finished;

    public void change(String title, Boolean finished){
        this.title = title;
        this.finished = finished;
    }
}
