package com.example.tankerallocation.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserDetails {
    @Id
    private Long id;
    @Property(nameInDb = "user_id")
    private String userid;
    @Property(nameInDb = "user_name")
    private String username;
    @Property(nameInDb = "Password")
    private String password;
    @Property(nameInDb = "zone_id")
    private String zone;
    @Property(nameInDb = "status")
    private String status;
    @Generated(hash = 965689104)
    public UserDetails(Long id, String userid, String username, String password,
            String zone, String status) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.zone = zone;
        this.status = status;
    }
    @Generated(hash = 64089743)
    public UserDetails() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserid() {
        return this.userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getZone() {
        return this.zone;
    }
    public void setZone(String zone) {
        this.zone = zone;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
   
}
