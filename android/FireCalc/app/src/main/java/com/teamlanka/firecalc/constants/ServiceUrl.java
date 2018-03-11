package com.teamlanka.firecalc.constants;

import com.teamlanka.firecalc.Config;

/**
 * Created by Charmal on 1/14/2018.
 */

public class ServiceUrl {

    private static final String REST_SERVICE_URL = Config.DOMAIN + "api/";

    public static final String GET_CATEGORY_LIST = REST_SERVICE_URL + "category";

    public static final String FLASHOVER = REST_SERVICE_URL + "flashover";

    public static final String GASLAYER = REST_SERVICE_URL + "gaslayer";

    public static final String CONDUCTION = REST_SERVICE_URL + "conduction";

    public static final String Signin = REST_SERVICE_URL + "signin";

    public static final String Signup = REST_SERVICE_URL + "signup";

    public static final String Feedback = REST_SERVICE_URL + "feedback";

    public static final String IMAGE = REST_SERVICE_URL + "images";
}