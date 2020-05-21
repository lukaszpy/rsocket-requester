package com.biteit.rsocket.requester.cdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reading {
    private String meterId;
    private String worksId;
    private long value;
    private Date readingDate;
}
