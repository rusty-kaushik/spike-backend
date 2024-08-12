package Like_Service.LikeEntity;

import jakarta.persistence.*;

@Entity
@Table(name="like_table")
public class Like {

    @Id
    @GeneratedValue(generator= "like_seq")
    @SequenceGenerator(name="like_seq",initialValue =1,  allocationSize=1)
    private Long id;
    private Long blogid;
    private String userid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getBlogid() {
        return blogid;
    }

    public void setBlogid(long blogid) {
        this.blogid = blogid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
