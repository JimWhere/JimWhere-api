package com.jimwhere.api.alarm.domain;

import com.jimwhere.api.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "alarm")
@SQLDelete(sql = "UPDATE alarm SET is_deleted = 'Y' WHERE alarm_id = ?")
@SQLRestriction("is_deleted = 'N'")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code", nullable = false)
    private User user;      /* N+1 문제 예상 되나 직접 문제 상황 마주 해보는 것도 괜찮아 보임 */

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 50, nullable = false)
    private AlarmType type;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "message", columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "link", length = 255)
    private String link;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_read", length = 2, nullable = false)
    private IsRead isRead;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_deleted", length = 2, nullable = false)
    private IsDelete isDeleted;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @PrePersist
    protected void onCreate() {
        if (this.isRead == null) {
            this.isRead = IsRead.N;
        }
        if (this.isDeleted == null) {
            this.isDeleted = IsDelete.N;
        }
    }

    public boolean isReadYn() {
        return this.isRead == IsRead.Y;
    }

    public boolean isDeletedYn() {
        return this.isDeleted == IsDelete.Y;
    }

    /* 알림 읽음 대비 */
    public void markAsRead() {
        if (!isReadYn()) {
            this.isRead = IsRead.Y;
            this.readAt = LocalDateTime.now();
        }
    }

    /* 소프트 삭제 */
    public void softDelete() {
        this.isDeleted = IsDelete.Y;
    }
}
