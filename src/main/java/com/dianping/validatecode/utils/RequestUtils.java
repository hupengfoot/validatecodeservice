package com.dianping.validatecode.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
	   private static final Map<String, String> escapeMapping = new HashMap<String, String>();

	   static {
	      escapeMapping.put("\\x22", "\"");
	      escapeMapping.put("\\x5C", "\\");
	   }

	   public static String getRemoteIp(HttpServletRequest request) {
	      String ip = filterXForwardedForIP(request.getHeader("x-forwarded-for"));

	      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	         ip = request.getHeader("Proxy-Client-IP");
	      }
	      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	         ip = request.getHeader("WL-Proxy-Client-IP");
	      }
	      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	         ip = request.getRemoteAddr();
	      }
	      return ip;
	   }

	   public static String filterXForwardedForIP(String ip) {
	      if (ip == null || ip.trim().length() == 0) {
	         return null;
	      } else {
	         if (ip.indexOf(',') == -1) {
	            return ip;
	         } else {
	            String[] subIps = ip.split(",");
	            // 从后往前取第一个非192.168.*，127.0.0.1，10.*的ip
	            for (int i = subIps.length - 1; i >= 0; i--) {
	               String subIp = subIps[i];
	               if (subIp == null || subIp.trim().length() == 0) {
	                  continue;
	               } else {
	                  subIp = subIp.trim();
	                  if (subIp.startsWith("192.168.") || subIp.startsWith("10.") || "127.0.0.1".equals(subIp)) {
	                     continue;
	                  } else {
	                     return subIp;
	                  }
	               }
	            }
	         }
	         return null;
	      }
	   }

	   public static String escapeVid(String vid) {
	      for (Map.Entry<String, String> entry : escapeMapping.entrySet()) {
	         vid = replaceAll(vid, entry.getKey(), entry.getValue());
	      }
	      return vid;
	   }

	   public static String replaceAll(String src, String pat, String replacement) {
	      if (src == null) {
	         return null;
	      }

	      if (pat == null || replacement == null) {
	         return src;
	      }

	      int pos = -1;
	      while ((pos = src.toLowerCase().indexOf(pat.toLowerCase())) >= 0) {
	         src = src.substring(0, pos) + replacement + src.substring(pos + pat.length());
	      }
	      return src;
	   }

	}