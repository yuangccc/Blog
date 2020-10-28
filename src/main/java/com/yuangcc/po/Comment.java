package com.yuangcc.po;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatat;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @ManyToOne
    private Blog blogs;
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyCommnet = new ArrayList<>();
    @ManyToOne
    private Comment parentComment;

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatat='" + avatat + '\'' +
                ", createTime=" + createTime +
                ", blogs=" + blogs +
                ", replyCommnet=" + replyCommnet +
                ", parentComment=" + parentComment +
                '}';
    }

    public List<Comment> getReplyCommnet() {
        return replyCommnet;
    }

    public void setReplyCommnet(List<Comment> replyCommnet) {
        this.replyCommnet = replyCommnet;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Blog getBlogs() {
        return blogs;
    }

    public void setBlogs(Blog blogs) {
        this.blogs = blogs;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatat() {
        return avatat;
    }

    public void setAvatat(String avatat) {
        this.avatat = avatat;
    }

}
