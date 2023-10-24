package com.rent.common.dto.components.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: hzsx-rent-parent
 * @description:
 * @author: yr
 * @create: 2021-09-23 16:26
 **/
@Data
public class SxExpressResponse implements Serializable {

    private String resp_code;
    private String resp_msg;
    private String timestamp;
    private String resp_order;

    private RespData resp_data;

    @Data
    public class RespData implements Serializable{
        private int issign;
        private String number;
        private int deliverystatus;
        private String logo;
        private String type;
        private List<Node> list;
        private String typename;
    }

    @Data
    public class Node implements Serializable{
        private Date time;
        private String status;
    }
}
