package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity // 해당 클래스는 JPA에서 관리하는 entity로 지정
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Getter
@Builder
public class Board extends BaseEntity{

    @Id // PK(primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 숫자를 증가시켜줌. Auto Inclement == Oracle
    private Long bno;   // 게시물 번호
    @Column(length = 500, nullable = false)
    private String title;   //게시물 제목
    @Column(length = 2000, nullable = false)
    private String content; //게시물 내용
    @Column(length = 50, nullable = false)
    private String writer;  //게시물 작성자

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }
}
