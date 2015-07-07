package com.appnometry.appnomars.util;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ApiImplementation {

    //public static String baseURL = "http://192.168.1.12/appnometry_webapp/www/RESTApi/public/rest";
    public static String baseURL = "http://digitalsolutionhouse.com/RESTApi/public/rest";
    private final String route = "route=api/login";
    private final String menRoute = "route=api/category";
    private final String registerRoute = "users/registration";
    private final String forgotPasswordRoute = "route=api/forgotten";
    private static final String loginRoute="users/login";
    private final String newsfeedRoute="items/user";


    public String MenListURL(int path) {

        return baseURL + menRoute + "&path=" + path;

    }

    public String RegisterURL(String fastName, String lastName, String email, String telephone, String fax, String company,
                              String customer_groupid, String company_id, String address1, String address2, String city,
                              String postcode, String countryID, String zoneID, String password, String confirmPassword,
                              String newslatter, int terms) {

        return baseURL + registerRoute + "&firstname=" + fastName + "&lastname=" + lastName + "&email=" + email + "&telephone=" + telephone +
                "&fax=" + fax + "&company=" + company + "&customer_group_id=" + company_id + "&company_id=" + company_id + "&address_1=" + address1 +
                "&address_2=" + address2 + "&city=" + city + "&postcode=" + postcode + "&country_id=" + countryID + "&zone_id=" + zoneID +
                "&password=" + password + "&confirm=" + confirmPassword + "&newsletter=" + newslatter + "&agree=" + terms + "&fb_profile=" + "" + "&tax_id=" + "";

    }

    public String forgotPasswordURL(String email) {

        return baseURL + forgotPasswordRoute + "&email=" + email;

    }

    //generate url for save payment Method
    public String GenarateFullURLforSavePaymentMethod(String userId, String userMethod, String paymentMethodCode, String token, String secret) {

        return baseURL + "/users/save-payment-method/" + userId + "?user_method=" + userMethod + "&s-f-1-30_payment-method-code=" + paymentMethodCode +
                "&s-t-1-256_token=" + token + "&secret=" + secret;
    }

    //generate url for payment gateway name
    public String GenarateFullURLforPaymentGateway(String secret) {

        return baseURL + "/users/payment-gateway?secret=" + secret;
    }

    //generate url for payment method list
    public String GenarateFullURLforPaymentMethodList(String userId, String secret) {

        return baseURL + "/users/payment-methods/" + userId + "?secret=" + secret;
    }

    public String GenarateFullURLforLogin(String email, String password,
                                          String platform, String regid) {

        return baseURL + "/" + loginRoute + "?email=" + email
                + "&password=" + password + "&platform=" + platform + "&dtoken=" + regid + "&is_push_enable=" + "1";
    }


    public static HTTPPostHelper getLogin(String email, String password,
                                          String platform, String regid) {
        String join_BaseUrl = baseURL + "/" + loginRoute;
        final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("email", "" + email));
        nvps.add(new BasicNameValuePair("password", "" + password));
        nvps.add(new BasicNameValuePair("platform", "" + platform));
        nvps.add(new BasicNameValuePair("dtoken", "" + regid));
        nvps.add(new BasicNameValuePair("is_push_enable", "" + "1"));
        Log.i("Login URL Is ", "" + nvps);
        final HTTPPostHelper helper = new HTTPPostHelper(join_BaseUrl, nvps);
        Log.i("Login URL Is ", "" + helper);
        return helper;

    }

    //login by facebook
    public String GenarateFullURLforLoginByFacebook(String email, String platform, String regid, String fbsession) {

        return baseURL + "/users/" + "fbLogin" + "?email=" + email + "&platform=" + platform +
                "&dtoken=" + regid + "&is_push_enable=" + "1" + "&fbsession=" + fbsession;
    }

    //facebook connect url
    public String GenarateFullURLforFacebookConnect(String userId, String platform, String fbsession) {

        return baseURL + "/users/" + "fbConnect" + "?userId=" + userId + "&platform=" + platform + "&fbsession=" + fbsession;
    }

    //facebook disconnect url
    public String GenarateFullURLforFacebookDisconnect(String userId, String platform, String logoutUrl) {

        return baseURL + "/users/" + "fblogout" + "?user_id=" + userId + "&platform=" + platform + "&url=" + logoutUrl;
    }

    public String GenarateFullURLforLogout(String userid, String platform, String regid, String secret) {

        return baseURL + "/" + "users/logout/" + userid + "?platform=" + platform + "&dtoken=" + regid + "&secret=" + secret;
    }

    public String GenerateFullUrlforStream(String secret, String user_id) {

        return baseURL + "/" + newsfeedRoute + "/" + user_id + "?q=" + "&page=stream" + "&secret=" + secret;

    }

    //get user history url
    public String generateFullUrlforHistory(String user_id, String secret) {

        return baseURL + "/" + "items/user" + "/" + user_id + "?secret=" + secret + "&page=used";
    }

    public String GenarateFullURLforEvent(String secret, String city) {

        return baseURL + "/" + "events" + "?q=" + "&city=" + city + "&secret="
                + secret;
    }

    public String GenerateFullUrlforBenefitCupon(String secret, String user_id) {

        return baseURL + "/" + "items/user" + "/" + user_id + "?q="
                + "&page=benefit_coupon" + "&secret=" + secret;

    }

    public String GenerateFullUrlforPurchase(String secret, String user_id) {

        return baseURL + "/items/user/" + user_id + "?q=" + "&page=purchase"
                + "&secret=" + secret;

    }

    public String GenerateFullUrlforMembersDeal(String secret, String user_id) {

        return baseURL + "/" + "items/user" + "/" + user_id + "?q="
                + "&page=member_deal" + "&secret=" + secret;

    }

    public String GenerateFullUrlforShopView(String secret, String user_id) {

        return baseURL + "/" + "items/user" + "/" + user_id + "?q="
                + "&page=bought_coupon" + "&secret=" + secret;

    }

    public String GenerateURLforCheckout(String user_id, String tax_amount,
                                         String shipping_amount, String discounted_price,
                                         String total_amount, String item_id, String quantity, String payment_method, String payment_type, String secret) {

        return baseURL + "/" + "users/order" + "/" + user_id + "?&tax_amount="
                + tax_amount + "&shipping_amount=" + shipping_amount
                + "&discounted_price=" + discounted_price + "&total_amount="
                + total_amount + "&item_id=" + item_id + "&quantity="
                + quantity + "&payment_method=" + payment_method + "&payment_type=" + payment_type + "&secret=" + secret;

    }

    public String GenerateFullUrlforCountryList() {
        return baseURL + "/" + "nyxcountryinfo";

    }

    public String GenerateFullUrlforCityList() {
        return baseURL + "/" + "cityinfos";

    }

    public String GenerateFullUrlforVenueList() {
        return baseURL + "/" + "venuedetails";

    }

    //get url for course details
    public String generateFullUrlforCourseDetails(String user_id, String secret) {
        return baseURL + "/" + "venuedetails/" + user_id + "?secret=" + secret;

    }

    //get url for course details
    public String generateCorseListDetailUrl(String user_id, String vanueID, String item, String secret) {
        return baseURL + "/" + "venuedetails/items/" + user_id + "?venue_id=" + vanueID + "&item=" + item + "&secret=" + secret;

    }

    //full url for Venuedetails Subscription
    public String generateFullUrlforVenuedetailsSubscription(String venue_id,
                                                             String user_id, String subscription, String secret) {
        return baseURL + "/" + "venuedetails/subscription/" + venue_id + "?user_id=" + user_id +
                "&subscription=" + subscription + "&secret=" + secret;

    }

    public String GenerateFullUrlforMyProfile(String id, String secret) {
        return baseURL + "/" + "users/view" + "/" + id + "?secret=" + secret;

    }



    public String GenetareFullURLforRegistration(String fname, String lname,
                                                    String emails, String password, String phone, String pcode,
                                                    String street, String town, String country, String platform,
                                                    String sex, String dob, String venues) {
        return baseURL + "/" + registerRoute + "?email=" + emails
                + "&password=" + password + "&firstname=" + fname
                + "&lastname=" + lname + "&phone=" + phone + "&postcode="
                + pcode + "&street=" + street + "&town=" + town + "&country="
                + country + "&platform=" + platform + "&sex=" + sex + "&dob="
                + dob + "&venues=" + venues;

    }

    public String GenerateFullUrlforUpdate(String userid, String email,
                                           String password, String newpassword, String phone,
                                           String firstname, String lastname, String postcode, String street,
                                           String town, String country, String platform, String sex,
                                           String dob, String venues, String greenCard, String secret) {

        return baseURL + "/" + "users/update" + "/" + userid + "?email="
                + email + "&password=" + password + "&new_password="
                + newpassword + "&phone=" + phone + "&firstname=" + firstname
                + "&lastname=" + lastname + "&postcode=" + postcode
                + "&street=" + street + "&town=" + town + "&country=" + country
                + "&platform=" + platform + "&sex=" + sex + "&dob=" + dob
                + "&venues=" + venues + "&green_card=" + greenCard + "&secret=" + secret;

    }

    public String GenerateFullUrlforForgetPassword(String email) {

        return baseURL + "/" + "users/forget-password" + "?email=" + email;

    }

    public String GenerateFullUrlforCuponRip(String user_id, String cupon_id,
                                             String secret) {
        return baseURL + "/" + "items/use-item" + "/" + cupon_id + "?user_id="
                + user_id + "?&secret=" + secret;

    }

    // Search URLS

    public String GenerateFullUrlforShopViewSearch(String search_query,
                                                   String user_id, String secret) {

        return baseURL + "/" + "items/user" + "/" + user_id + "?q="
                + search_query + "&page=bought_coupon" + "&secret=" + secret;
    }

    public String GenerateFullUrlforPurchaseSearch(String search_query,
                                                   String user_id, String secret) {

        return baseURL + "/" + "items/user" + "/" + user_id + "?q="
                + search_query + "&page=purchase" + "&secret=" + secret;
    }

    public String GenarateFullURLforStreamSearch(String search_query,
                                                 String user_id, String secret) {

        return baseURL + "/" + "items/user" + "/" + user_id + "?q="
                + search_query + "&page=stream" + "&secret=" + secret;
    }

    public String GenarateFullURLforBenefitCuponSearch(String search_query,
                                                       String user_id, String secret) {
        return baseURL + "/" + "items/user" + "/" + user_id + "?q="
                + search_query + "&page=benefit_coupon" + "&secret=" + secret;
    }

    public String GenarateFullURLforMembersDealSearch(String search_query,
                                                      String user_id, String secret) {
        return baseURL + "/" + "items/user" + "/" + user_id + "?q="
                + search_query + "&page=member_deal" + "&secret=" + secret;
    }

    public String GenarateFullURLforEventSearch(String search_query,
                                                String secret, String city) {

        return baseURL + "/" + "events" + "?q=" + search_query + "&city="
                + city + "&secret=" + secret;
    }

}
