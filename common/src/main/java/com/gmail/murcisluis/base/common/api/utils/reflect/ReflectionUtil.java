package com.gmail.murcisluis.base.common.api.utils.reflect;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class ReflectionUtil {

	private static String version;

	public static String getVersion() {
		if (version == null) {
			version = BaseAPIFactory.getPlugin().getVersionServer();
		}
		return version;
	}

	public static Class<?> getClass(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> getNMClass(String name) {
		try {
			return Class.forName("net.minecraft." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server." + getVersion() + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> getObcClass(String classname) {
		try {
			return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + classname);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> boolean setFieldValue(Object object, String fieldName, T value) {
		Class<?> clazz = object.getClass();
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(object, value);
			return true;
		} catch (NoSuchFieldException | IllegalAccessException ignored) {}
		return false;
	}

}