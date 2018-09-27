package cn.wow.support.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.wow.common.domain.Account;
import cn.wow.common.service.AccountService;
import cn.wow.common.service.OperationLogService;
import cn.wow.common.utils.Contants;
import cn.wow.common.utils.operationlog.ClientInfo;
import cn.wow.common.utils.operationlog.OperationType;
import cn.wow.common.utils.operationlog.ServiceType;

public class FormAuthenticationExtendFilter extends FormAuthenticationFilter {
	private static Logger logger = LoggerFactory.getLogger(FormAuthenticationExtendFilter.class);

	@Autowired
	private AccountService accountService;
	@Autowired
	private OperationLogService operationLogService;

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String successUrl = "/index";

		/* //成功登录后返回成功跳转页面，指定跳转地址 */
		WebUtils.issueRedirect(request, response, successUrl);

		// 成功登录后返回成功跳转页面
		// issueSuccessRedirect(request, response);

		String username = (String) SecurityUtils.getSubject().getPrincipal();
		if (StringUtils.isNotBlank(username)) {
			HttpSession session = httpServletRequest.getSession();

			Account account = accountService.selectByAccountName(username);
			session.setAttribute(Contants.CURRENT_ACCOUNT, account);

			// 判断用户客户端信息
			createOrUpdateClientInfo(username, request.getRemoteAddr(), httpServletRequest.getHeader("user-agent"));
			// 添加日志
			operationLogService.save(username, OperationType.LOGIN, ServiceType.ACCOUNT, "");
		}

		return false;
	}

	private void createOrUpdateClientInfo(String userName, String clientIp, String userAgent) {
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setClientIp(clientIp);
		clientInfo.setUserAgent(userAgent);
		operationLogService.createOrUpdateUserClientInfo(userName, clientInfo);
	}


}
