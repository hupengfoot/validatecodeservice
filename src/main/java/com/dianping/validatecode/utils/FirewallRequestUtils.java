package com.dianping.validatecode.utils;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dianping.alpaca.common.utils.SecurityUtils;

public class FirewallRequestUtils {

    private static final String TOKEN_KEY = "alpaca-firewall-token";

    public static String sendFirewallHttpRequest(Map<String, String> httpParams, String uri, String root, String ip)
            throws Exception {
        try {
            final URL reqUrl = new URL(root + uri);
            HttpURLConnection conn = (HttpURLConnection) reqUrl.openConnection();

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(3000);
            conn.setDoOutput(true);

            PrintWriter out = new PrintWriter(conn.getOutputStream());
            boolean isFirst = true;

            if (httpParams == null || httpParams.size() == 0) {
                httpParams = new HashMap<String, String>();
            }

            httpParams.put(TOKEN_KEY, SecurityUtils.genToken(uri + "|" + ip));

            for (Map.Entry<String, String> entry : httpParams.entrySet()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    out.print("&");
                }

                out.print(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            out.close();

            InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream(), "UTF-8");

            return IOUtils.toString(inputStreamReader);
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean isFirewallRequest(HttpServletRequest request, String ip, String uri) {
        if (request != null && "POST".equalsIgnoreCase(request.getMethod())) {
            String token = request.getParameter(TOKEN_KEY);
            if (token != null && uri != null && ip != null) {
                return token.equals(SecurityUtils.genToken(uri + "|" + ip));
            }
        }
        return false;
    }
}