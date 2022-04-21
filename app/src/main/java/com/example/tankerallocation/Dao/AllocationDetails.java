package com.example.tankerallocation.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AllocationDetails {
    @Id
    private Long id;
    @Property(nameInDb = "token_id")
    private String tokenid;
    @Property(nameInDb = "tanker_num")
    private String tankernum;
    @Property(nameInDb = "driver_mob_no")
    private String drivermobno;
    @Property(nameInDb = "cust_mob_no")
    private String custmobNo;
    @Property(nameInDb = "cust_add")
    private String custadd;
    @Property(nameInDb = "status")
    private String status;
    @Generated(hash = 1913422435)
    public AllocationDetails(Long id, String tokenid, String tankernum,
            String drivermobno, String custmobNo, String custadd, String status) {
        this.id = id;
        this.tokenid = tokenid;
        this.tankernum = tankernum;
        this.drivermobno = drivermobno;
        this.custmobNo = custmobNo;
        this.custadd = custadd;
        this.status = status;
    }
    @Generated(hash = 1767350816)
    public AllocationDetails() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTokenid() {
        return this.tokenid;
    }
    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }
    public String getTankernum() {
        return this.tankernum;
    }
    public void setTankernum(String tankernum) {
        this.tankernum = tankernum;
    }
    public String getDrivermobno() {
        return this.drivermobno;
    }
    public void setDrivermobno(String drivermobno) {
        this.drivermobno = drivermobno;
    }
    public String getCustmobNo() {
        return this.custmobNo;
    }
    public void setCustmobNo(String custmobNo) {
        this.custmobNo = custmobNo;
    }
    public String getCustadd() {
        return this.custadd;
    }
    public void setCustadd(String custadd) {
        this.custadd = custadd;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
