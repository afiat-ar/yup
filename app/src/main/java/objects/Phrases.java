package objects;

public class Phrases {
    private String id;
    private String text;
    private String upld_user_id;
    private String simp_user_id;
    private String group_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUpld_user_id() {
        return upld_user_id;
    }

    public void setUpld_user_id(String upld_user_id) {
        this.upld_user_id = upld_user_id;
    }

    public String getSimp_user_id() {
        return simp_user_id;
    }

    public void setSimp_user_id(String simp_user_id) {
        this.simp_user_id = simp_user_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}

