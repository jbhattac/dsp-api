package com.vm.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bids
{
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 0, message = "")
    @Column(name = "PRICE")
    private Integer price;
    @NotNull(message = "")
    @Column(name = "TID")
    private String tid;
    @NotNull(message = "")
    @Column(name = "URL")
    private String url;


    public Bids(Integer price, String tid, String url)
    {
        super();
        this.price = price;
        this.tid = tid;
        this.url = url;
    }


    public Bids(Integer price, String tid)
    {
        super();
        this.price = price;
        this.tid = tid;
    }


    public Bids()
    {
        super();
    }

}
