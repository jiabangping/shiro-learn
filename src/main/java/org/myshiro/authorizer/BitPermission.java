package org.myshiro.authorizer;

import org.apache.shiro.authz.Permission;

public class BitPermission implements Permission {

	private String resourceIndentify;
	private int permissionBit;
	private String instanceId;

	public BitPermission(String permissionString) {
		String[] array = permissionString.split("\\+");
		if (array.length > 1) {
			resourceIndentify = array[1];
		}
		if (org.apache.commons.lang.StringUtils.isEmpty(resourceIndentify)) {
			resourceIndentify = "*";
		}
		if (array.length > 2) {
			permissionBit = Integer.valueOf(array[2]);
		}
		if (array.length > 3) {
			instanceId = array[3];
		}

		if (org.apache.commons.lang.StringUtils.isEmpty(instanceId)) {
			instanceId = "*";
		}
	}

	@Override
	public boolean implies(Permission p) {// 0��ʾ����Ȩ�� this.permissionBit
											// ��Realm�д����ݿ��ȡӵ�е�Ȩ��
		if (!(p instanceof BitPermission)) {
			return false;
		}
		BitPermission other = (BitPermission) p;
		// thisΪ���ݿ�ȡ�����ģ��������еģ�����Ҳ����ͬ������false
		if (!"*".equals(this.resourceIndentify)
				&& !this.resourceIndentify.equals(other.resourceIndentify)) {
			return false;
		}
		// �������еģ� ���� & =0 ����û�д�Ȩ��
		if (!(this.permissionBit == 0)
				&& (this.permissionBit & other.permissionBit) == 0) {
			return false;
		}
		if (!"*".equals(this.instanceId)
				&& !this.instanceId.equals(other.instanceId)) {
			return false;
		}
		return true;

	}

}
