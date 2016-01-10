package org.myshiro.authorizer;

import java.util.Arrays;
import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

//用于根据 角色字符串来解析 得到权限集合
public class MyRolePermissionResolver implements RolePermissionResolver {

	@Override
	public Collection<Permission> resolvePermissionsInRole(String roleString) {
		if("role1".equals(roleString)) {
			return Arrays.asList((Permission)new WildcardPermission("menu:*"));
		}
		return null;
	}

}
