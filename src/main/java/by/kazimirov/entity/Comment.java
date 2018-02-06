package by.kazimirov.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 */
public class Comment implements IDatabaseEntity {
    private int commentId;
    private int accountId;
    private int trackId;
    private LocalDateTime dateTime;
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentId == comment.commentId &&
                accountId == comment.accountId &&
                trackId == comment.trackId &&
                Objects.equals(dateTime, comment.dateTime) &&
                Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, accountId, trackId, dateTime, text);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("commentId=").append(commentId);
        sb.append(", accountId=").append(accountId);
        sb.append(", trackId=").append(trackId);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
