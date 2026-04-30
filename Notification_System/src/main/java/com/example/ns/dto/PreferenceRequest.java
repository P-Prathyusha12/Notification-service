package com.example.ns.dto;

import lombok.Data;

@Data
public class PreferenceRequest {
    private Boolean emailEnabled;
    private Boolean smsEnabled;
    private Boolean pushEnabled;
}