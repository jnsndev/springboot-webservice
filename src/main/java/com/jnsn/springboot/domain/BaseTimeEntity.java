package com.jnsn.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// MappedSuperclass: JPA entity 클래스들이 해당 클래스를 상속할 경우 필드들(createdDate, modifiedDate)도 column으로 인식하도록 해준다.
// EntityListeners: 해당 클래스에 Auditing 기능을 포함 시킨다.
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    // 모든 entity의 상위 클래스가 되어 entity들의 createdDate, modifiedDate를 자동으로 관리하는 역할

    // CreatedDate: entity가 생성되어 저장될 때 시간이 자동 저장
    @CreatedDate
    private LocalDateTime createdDate;

    // LastModifiedDate: 조회한 entity의 값을 변경할 때 시간이 자동 저장
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
