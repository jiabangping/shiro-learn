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
	// ���ظ�realmName
	public String getName() {
		return "myRealm1";
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		// ��֧��UsernamepasswordToken����token
		return token instanceof UsernamePasswordToken;
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		String username = token.getPrincipal().toString();// �õ��û���
		String password = new String((char[]) token.getCredentials());
		if (!"zhang".equals(username)) {
			throw new UnknownAccountException(); // ����û�������
		}
		if (!"123".equals(password)) {
			throw new IncorrectCredentialsException(); // ����������
		}

		// �����֤�ɹ�������һ�� AuthenticationInfo��ʵ��
		AuthenticationInfo info = new SimpleAuthenticationInfo(username,
				password, getName());
		
		return info;
	}

}
