package com.test.initial;

import java.util.HashMap;

import org.eclipse.swt.widgets.Control;

public class YummyObjFactory {
	/**
	 * ¿Ø¼þ»º´æ
	 */
	private HashMap<String, Control> ControlMap = new HashMap<String, Control>();

	public void SaveControlMap(HashMap<String, Control> in_map) {
		ControlMap = in_map;
	}
}
