package com.marathonfront.config;

import lombok.Getter;

@Getter
public class ApiConfig {

    public static String backendUrl = "http://localhost:8080/v1/";
    public static String postalCodeUrl = "http://kodpocztowy.intami.pl/api/";
    public static String weatherUrl1 = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/Częstochowa/";
    public static String weatherKey = "J3PJ8E5CUGC7FFP99AJED9DPD";


}
