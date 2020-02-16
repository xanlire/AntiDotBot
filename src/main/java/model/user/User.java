package model.user;



public class User implements Comparable<User> {
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private Role role;
    private Integer messageCount;

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("`")
                .append(firstname)
                .append(" ")
                .append(firstname == null ? username : "")
                .append("\t\t")
                .append(messageCount)
                .append("`");
        return builder.toString();
    }

    public String toShortString(){
        StringBuilder builder = new StringBuilder();
        builder.append("`")
                .append(firstname)
                .append(" ")
                .append(lastname != null ? lastname : "")
                .append(" ")
                .append(firstname == null && lastname == null ? username : "")
                .append("`");
        return builder.toString();
    }

    @Override
    public int compareTo(User o) {
        return (o.getMessageCount() - this.messageCount != 0)
                ? o.getMessageCount() - this.messageCount
                : o.getFirstname().compareTo(this.getFirstname());
    }
}
