package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Getter
@Builder
public class Member extends BaseEntity{
    @Id
    @Column(length = 50, nullable = false)
    private String mid;
    @Column(length = 50, nullable = false)
    private String mpw;
    @Column(length = 100, nullable = false)
    private String mname;
    @Column(length = 50, nullable = true)
    private String uuid;

    public void change(String mpw, String mname){
        this.mpw = mpw;
        this.mname = mname;
    }
}
