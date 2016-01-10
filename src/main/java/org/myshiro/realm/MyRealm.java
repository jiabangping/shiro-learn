package org.myshiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.myshiro.authorizer.BitPermission;

public class MyRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		//�����Ҫͨ���Զ����Realm��ʵ����Ȩ(�����Ƶķ�ʽ)����������Ҫ�������ݿ�����ȡ
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//		authorizationInfo.addRole("role1");
//		authorizationInfo.addRole("role2");
		authorizationInfo.addObjectPermission(new BitPermission("+*+0+*"));
//		authorizationInfo.addObjectPermission(new BitPermission("+user1+10"));
//		authorizationInfo
//				.addObjectPermission(new WildcardPermission("user1:*"));
//		authorizationInfo.addStringPermission("+user2+10");
//		authorizationInfo.addStringPermission("user2:*");
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
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
