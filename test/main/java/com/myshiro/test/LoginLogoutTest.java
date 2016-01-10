package com.myshiro.test;

import java.util.Arrays;

import junit.framework.Assert;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class LoginLogoutTest {

	@Test
	public void testHelloworld() {
		// 1����ȡSecurityManager����
		Factory<org.apache.shiro.mgt.SecurityManager> factory;
		factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		// 2���õ�SecurityManager���󶨸�SecurityUtils
		org.apache.shiro.mgt.SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);

		// 3���õ�Submect�������û���/���� �����֤Token(�û�/���ƾ֤)
		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			System.out.println("�û�������");
		} catch (IncorrectCredentialsException e) {
			System.out.println("�������");
		} catch (AuthenticationException e) {
			System.out.println("���������֤ʧ�ܴ���");
		}

		Assert.assertEquals(true, subject.isAuthenticated());
		subject.logout();
	}

	@Test
	// �����û��Լ������Realm
	public void testCustomRealm() {
		// 1����ȡSecurityManager�������˴�ʹ��shiro-realm.ini�����ļ���ʼ��
		SecurityManager securityManager = new IniSecurityManagerFactory(
				"classpath:shiro-realm.ini").getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			System.out.println("�û�������");
		} catch (IncorrectCredentialsException e) {
			System.out.println("�������");
		} catch (AuthenticationException e) {
			System.out.println("���������֤ʧ�ܴ���");
		}
		Assert.assertEquals(true, subject.isAuthenticated());
		subject.logout();
	}

	private static Subject login(String ini, String username, String password) {
		SecurityManager securityManager = new IniSecurityManagerFactory(ini)
				.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		return subject;
	}

	private static Subject login2(String ini, String username, String password) {
		SecurityManager securityManager = new IniSecurityManagerFactory(ini)
				.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			System.out.println("�û�������");
		} catch (IncorrectCredentialsException e) {
			System.out.println("�������");
		} catch (AuthenticationException e) {
			System.out.println("���������֤ʧ�ܴ���");
		}
		Assert.assertEquals(true, subject.isAuthenticated());

		return subject;
	}

	@Test
	// ����role
	public void testHasRole() {
		Subject subject = login2("classpath:shiro-role.ini", "zhang", "123");
		// �ж��Ƿ�ӵ�н�ɫ role1
		Assert.assertTrue(subject.hasRole("role1"));

		// �ж��Ƿ�ӵ�� role1 and role2
		Assert.assertEquals(true,
				subject.hasAllRoles(Arrays.asList("role1", "role2")));

		boolean[] results = subject.hasRoles(Arrays.asList("role1", "role2",
				"role3"));
		System.out.println(Arrays.toString(results));

		subject.checkRole("role5");// ���û�д˽�ɫ���׳�UnauthorizedException
	}

	@Test
	public void testPermission() {
		Subject subject = login2("classpath:shiro-permission.ini", "zhang",
				"123");
		// �ж�ӵ��Ȩ�� user:create
		// Assert.assertTrue(subject.isPermitted("user:create"));
		Assert.assertEquals(true, subject.isPermitted("user:create"));
		// �ж�ӵ��Ȩ�� user:update and user:delete
		Assert.assertTrue(subject.isPermittedAll("user:update", "user:delete"));

		Assert.assertTrue(subject.isPermitted("user:view"));
	}

	@Test
	public void testJDBCRealm() {
		Subject subject = login2("classpath:shiro-jdbc-realm.ini", "zhang",
				"123");

		Assert.assertEquals(true, subject.isAuthenticated()); // �����û��Ѿ���¼

		// 6���˳�
		subject.logout();
	}

	@Test
	public void testIsPermittedByJdbc() {
		Subject subject = login2("classpath:shiro-authorizer.ini", "zhang",
				"123");
		// �ж�ӵ��Ȩ�ޣ�user:create
		Assert.assertEquals(true, subject.isPermitted("user1:update"));
		Assert.assertEquals(true, subject.isPermitted("user2:update"));
		// ͨ��������λ�ķ�ʽ��ʾȨ��
		Assert.assertEquals(true, subject.isPermitted("+user+2"));// ����Ȩ��
		Assert.assertEquals(true, subject.isPermitted("+user1+8"));// �鿴Ȩ��
		Assert.assertEquals(true, subject.isPermitted("+user2+10"));// �������鿴
		Assert.assertEquals(true, subject.isPermitted("+user1+4"));// û��ɾ��Ȩ��
		// Assert.assertTrue(subject.isPermitted("menu:view"));//
		// ͨ��MyRolePermissionResolver
		// �����õ���Ȩ��
	}
}
