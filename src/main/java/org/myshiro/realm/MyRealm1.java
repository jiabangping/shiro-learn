package org.myshiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

public class MyRealm1 implements Realm {

	@Override
	// 返回该realmName
	public String getName() {
		return "myRealm1";
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		// 仅支持UsernamepasswordToken类型token
		return token instanceof UsernamePasswordToken;
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		String username = token.getPrincipal().toString();// 得到用户名
		String password = new String((char[]) token.getCredentials());
		if (!"zhang".equals(username)) {
			throw new UnknownAccountException(); // 如果用户名错误
		}
		if (!"123".equals(password)) {
			throw new IncorrectCredentialsException(); // 如果密码错误
		}

		// 如果认证成功，返回一个 AuthenticationInfo的实现
		AuthenticationInfo info = new SimpleAuthenticationInfo(username,
				password, getName());
		
		return info;
	}

}
