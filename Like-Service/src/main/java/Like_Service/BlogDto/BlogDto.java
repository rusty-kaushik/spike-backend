package Like_Service.BlogDto;

import java.util.UUID;

public class BlogDto {


    private String id;

    private String title;

    private long commentCount;

    private long likeCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public BlogDto(String id, String title, long commentCount) {
        super();
        this.id = id;
        this.title = title;
        this.commentCount = commentCount;
    }


}
