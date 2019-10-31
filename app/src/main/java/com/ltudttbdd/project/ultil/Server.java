package com.ltudttbdd.project.ultil;

public class Server {
    public static String localhost = "172.17.26.82:800";
//    public static String localhost = "192.168.1.19:800";

    public static String urlProductCategory = "http://" + localhost + "/ctxhstore/public/api/data/getProductCategory";
    public static String urlNewProduct = "http://" + localhost + "/ctxhstore/public/api/data/getNewProduct";
    public static String urlAds = "http://" + localhost + "/ctxhstore/public/api/data/getSlideShow";
    public static String urlProduct = "http://" + localhost + "/ctxhstore/public/api/data/getProductByCategoryId";
    public static String urlOrder = "http://" + localhost + "/ctxhstore/public/api/data/postInforUser";
    public static String urlOrderDetail = "http://" + localhost + "/ctxhstore/public/api/data/postOrderDetail";
}
