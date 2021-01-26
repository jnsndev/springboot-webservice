package com.jnsn.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Entity: 테이블과 링크될 클래스, 언더스코어 네이밍으로 테이블명을 매칭
// NoArgsConstructor: 기본 생성자 자동 추가, public Posts() {}와 같은 효과
// Getter: 클래스 내 모든 필드의 Getter 메소드를 자동생성
@Getter
@NoArgsConstructor
@Entity
public class Posts {
    // Posts 클래스는 실제 DB의 테이블과 매칭될 클래스이며 보통 Entity 클래스라고 한다.
    // JPA를 사용하면 DB 작업을 할 경우, 실제 쿼리를 날리기보다 Entity 클래스의 수정을 통해 작업한다.
    
    // Id: 해당 테이블의 PK 필드
    // GeneratedValue: PK의 생성 규칙, 스프링부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 된다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Column: 기본값 외에 추가로 변경이 필요한 옵션이 있을 때 사용
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    // Builder: 해당 클래스의 빌더 패턴 클래스를 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
