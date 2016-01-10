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
		// 1、获取SecurityManager工厂
		Factory<org.apache.shiro.mgt.SecurityManager> factory;
		factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		// 2、得到SecurityManager并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);

		// 3、得到Submect及创建用户名/密码 身份验证Token(用户/身份凭证)
		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			System.out.println("用户名错误");
		} catch (IncorrectCredentialsException e) {
			System.out.println("密码错误");
		} catch (AuthenticationException e) {
			System.out.println("其他身份验证失败错误");
		}

		Assert.assertEquals(true, subject.isAuthenticated());
		subject.logout();
	}

	@Test
	// 测试用户自己定义的Realm
	public void testCustomRealm() {
		// 1、获取SecurityManager工厂，此处使用shiro-realm.ini配置文件初始化
		SecurityManager securityManager = new IniSecurityManagerFactory(
				"classpath:shiro-realm.ini").getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			System.out.println("用户名错误");
		} catch (IncorrectCredentialsException e) {
			System.out.println("密码错误");
		} catch (AuthenticationException e) {
			System.out.println("其他身份验证失败错误");
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
			System.out.println("用户名错误");
		} catch (IncorrectCredentialsException e) {
			System.out.println("密码错误");
		} catch (AuthenticationException e) {
			System.out.println("其他身份验证失败错误");
		}
		Assert.assertEquals(true, subject.isAuthenticated());

		return subject;
	}

	@Test
	// 测试role
	public void testHasRole() {
		Subject subject = login2("classpath:shiro-role.ini", "zhang", "123");
		// 判断是否拥有角色 role1
		Assert.assertTrue(subject.hasRole("role1"));

		// 判断是否拥有 role1 and role2
		Assert.assertEquals(true,
				subject.hasAllRoles(Arrays.asList("role1", "role2")));

		boolean[] results = subject.hasRoles(Arrays.asList("role1", "role2",
				"role3"));
		System.out.println(Arrays.toString(results));

		subject.checkRole("role5");// 如果没有此角色，抛出UnauthorizedException
	}

	@Test
	public void testPermission() {
		Subject subject = login2("classpath:shiro-permission.ini", "zhang",
				"123");
		// 判断拥有权限 user:create
		// Assert.assertTrue(subject.isPermitted("user:create"));
		Assert.assertEquals(true, subject.isPermitted("user:create"));
		// 判断拥有权限 user:update and user:delete
		Assert.assertTrue(subject.isPermittedAll("user:update", "user:delete"));

		Assert.assertTrue(subject.isPermitted("user:view"));
	}

	@Test
	public void testJDBCRealm() {
		Subject subject = login2("classpath:shiro-jdbc-realm.ini", "zhang",
				"123");

		Assert.assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录

		// 6、退出
		subject.logout();
	}

	@Test
	public void testIsPermittedByJdbc() {
		Subject subject = login2("classpath:shiro-authorizer.ini", "zhang",
				"123");
		// 判断拥有权限：user:create
		Assert.assertEquals(true, subject.isPermitted("user1:update"));
		Assert.assertEquals(true, subject.isPermitted("user2:update"));
		// 通过二进制位的方式表示权限
		Assert.assertEquals(true, subject.isPermitted("+user+2"));// 新增权限
		Assert.assertEquals(true, subject.isPermitted("+user1+8"));// 查看权限
		Assert.assertEquals(true, subject.isPermitted("+user2+10"));// 新增及查看
		Assert.assertEquals(true, subject.isPermitted("+user1+4"));// 没有删除权限
		// Assert.assertTrue(subject.isPermitted("menu:view"));//
		// 通过MyRolePermissionResolver
		// 解析得到的权限
	}
}
