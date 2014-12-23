package com.util.pub.color;

import org.eclipse.swt.graphics.RGB;

/**
 * 颜色相关工具类
 * @author hengzai 时间：2013-8-4
 */
public class ColorDealing {
	public static RGB getRGB(String value) {
		RGB rgb = null;
		String[] strs = value.split(",");
		int red = Integer.parseInt(strs[0]);
		int green = Integer.parseInt(strs[1]);
		int blue = Integer.parseInt(strs[2]);
		rgb = new RGB(red, green, blue);
		return rgb;
	}
}
