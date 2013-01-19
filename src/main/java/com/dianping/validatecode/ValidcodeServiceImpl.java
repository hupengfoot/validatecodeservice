package com.dianping.validatecode;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dianping.alpaca.common.utils.IPUtils;
import com.dianping.avatar.cache.CacheKey;
import com.dianping.avatar.cache.CacheService;
import com.dianping.validatecode.config.CommonConfig;
import com.dianping.validatecode.config.ConfigurationWatch;
import com.dianping.validatecode.config.PolicyConfig;
import com.dianping.validatecode.utils.FirewallRequestUtils;
import com.dianping.validatecode.utils.IOUtils;
import com.dianping.validatecode.utils.MonitorUtils;
import com.dianping.validatecode.utils.RequestUtils;
import com.dianping.validatecode.utils.StringUtils;

public class ValidcodeServiceImpl extends HttpServlet {

	private volatile boolean running = false;
	private CacheService cacheService;
	private static final String cacheKeyCategory = "oValidateCode";

	public void init() throws ServletException {
		initConfigWatch();
		WebApplicationContext appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		cacheService = (CacheService) appContext.getBean("cacheService");
	}

	private boolean isMatchValidateCode(HttpServletRequest request, String validateCode) {
		if (StringUtils.isBlank(validateCode)) {
			return false;
		}
		// 获取sessionId
		// String sessionId = request.getSession().getId();
		String sessionId = StringUtils.emptyIfNull(RequestUtils.getRemoteIp(request));
		if (StringUtils.isBlank(sessionId)) {
			return false;
		}
		// 校验
		if (validateCode.toLowerCase().equals(cacheService.get(getValidateCodeCacheKey(sessionId)))) {
			return true;
		}
		return false;
	}

	private void releaseBlock(String releaseIp, String vid) {
		int retryTimes = 0;
		while (retryTimes++ < 3) {
			try {
				Map<String, String> httpParams = new HashMap<String, String>();
				httpParams.put("releaseIP", releaseIp);
				httpParams.put("clientIP", IPUtils.getFirstNoLoopbackIP4Address());
				if (!StringUtils.isBlank(vid)) {
					httpParams.put("vid", vid);
				}
				String responseMessage = FirewallRequestUtils.sendFirewallHttpRequest(httpParams, CommonConfig.getInstance()
						.getServerReleaseUrl(), CommonConfig.getInstance().getServerRoot(), IPUtils.getFirstNoLoopbackIP4Address());
				if ("ok".equals(responseMessage)) {
					return;
				}
			} catch (Exception e) {
				MonitorUtils.traceError("Release server block failed.", e);
			}
		}
		MonitorUtils.traceError("Release server block failed after retry 3 times.");
	}

	private void checkValidateCode(HttpServletRequest request, HttpServletResponse response) {
		String validateCode = request.getParameter("vode");
		String redirectUrl = request.getHeader("Referer");
		if (isMatchValidateCode(request, validateCode)) {
			PolicyConfig.getInstance().getDenyIPAddressRate().remove(StringUtils.emptyIfNull(RequestUtils.getRemoteIp(request)));

			try {
				releaseBlock(StringUtils.emptyIfNull(RequestUtils.getRemoteIp(request)), StringUtils.emptyIfNull(RequestUtils
						.escapeVid(getCookie(request, "_hc.v"))));
				if (StringUtils.isBlank(redirectUrl)) {
					redirectUrl = "www.dianping.com";
				}
				response.sendRedirect(redirectUrl);
			} catch (IOException e) {
				MonitorUtils.traceError("checkValidateCode failed.", e);
			}
		} else {
			try {
				response.sendRedirect(redirectUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "";
		}
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(key)) {
				String value = cookies[i].getValue();
				if (!StringUtils.isBlank(value)) {
					if (value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
						return value.substring(1, value.length() - 1);
					} else {
						return value;
					}
				} else {
					return "";
				}
			}
		}

		return "";
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (req.getMethod() == "GET") {
			URL url = new URL(CommonConfig.getInstance().getExternalValidateUrl()
					+ StringUtils.emptyIfNull(RequestUtils.getRemoteIp(req)));
			URLConnection rconn = url.openConnection();
			// 传递referer
			rconn.setRequestProperty("Referer", req.getHeader("Referer"));
			// 获取sessionId
			// String sessionId = req.getSession().getId();
			String sessionId = StringUtils.emptyIfNull(RequestUtils.getRemoteIp(req));
			if (StringUtils.isBlank(sessionId)) {
				return;
			}
			// 验证码
			String code = rconn.getHeaderField("Captcha");
			if (StringUtils.isBlank(code)) {
				return;
			}

			cacheService.add(getValidateCodeCacheKey(sessionId), code.trim());

			res.setHeader("Cache-Control", "must-revalidate, no-cache, private");
			res.setHeader("Pragma", "no-cache");
			res.setHeader("Expires", "-1");
			res.setContentType("image/png");
			// 验证码图片
			res.getOutputStream().write(IOUtils.toByteArray(rconn.getInputStream()));
		} else if (req.getMethod() == "POST") {
			checkValidateCode(req, res);
		} else {

		}
	}

	private CacheKey getValidateCodeCacheKey(String sessionId) {
		return new CacheKey(cacheKeyCategory, sessionId + "_alpaca");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// System.out.println(req.getContentLength());
		// byte[] data = new byte[req.getContentLength()];
		// req.getInputStream().read(data);
		// System.out.println(new String(data));
		doGet(req, res);
	}

	private void initConfigWatch() {
		ConfigurationWatch.getInstance().addWatchedConfig(PolicyConfig.getInstance());
		ConfigurationWatch.getInstance().addWatchedConfig(CommonConfig.getInstance());

		if (ConfigurationWatch.getInstance().init() && ConfigurationWatch.getInstance().start()) {
			running = true;
		} else {
			running = false;
		}
	}
}
