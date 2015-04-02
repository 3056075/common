package com.zm.user.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zm.user.dao.ResourceDao;
import com.zm.user.entity.Resource;
import com.zm.user.entity.Role;



@Service("zmSecurityMetadataSource")
public class ZmSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {
	protected static Logger logger = LoggerFactory
			.getLogger(ZmSecurityMetadataSource.class);
	@Autowired
	private ZmSecurityMetadataSourceHelp zmSecurityMetadataSourceHelp;
	private Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		if (null == resourceMap) {
			resourceMap = zmSecurityMetadataSourceHelp.getResourceMap();
		}
		 HttpServletRequest request = ((FilterInvocation) object).getRequest();
		logger.debug("请求的url是:" + request.getRequestURI());
		for (String key : resourceMap.keySet()) {
			AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(key);
			if (requestMatcher.matches(request)) {
				return resourceMap.get(key);
			}
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}

@Service
class ZmSecurityMetadataSourceHelp {
	@Autowired
	private ResourceDao resourceDao;
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true,rollbackFor=Exception.class)
	public Map<String, Collection<ConfigAttribute>> getResourceMap() {
		Map<String, Collection<ConfigAttribute>> resourceMapTmp = new HashMap<String, Collection<ConfigAttribute>>();
		List<Resource> resources = resourceDao.findAll();
		for (Resource resource : resources) {
			Set<Role> roles = resource.getRoles();
			Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
			for (Role role : roles) {
				if (Role.STATUS_NORMAL.equals(role.getStatus())) {
					ConfigAttribute configAttribute = new SecurityConfig(
							"ROLE_" + role.getRoleId());
					configAttributes.add(configAttribute);
				}
			}
			resourceMapTmp.put(resource.getUrl(), configAttributes);
			resourceMapTmp.put(resource.getCode(), configAttributes);
		}
		return resourceMapTmp;
	}
}
