package Entity;// Entity.Group.java

import java.util.Date;

public class Group {
    private int groupID;
    private String groupName;
    private Account creator;
    private Date createDate;

    public Group() {
    }

    public Group(int groupID, String groupName, Account creator, Date createDate) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}