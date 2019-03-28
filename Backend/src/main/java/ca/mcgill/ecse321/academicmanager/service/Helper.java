package ca.mcgill.ecse321.academicmanager.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Helper {
	static <T> Set<T> toSet(Iterable<T> iterable) {
		Set<T> res = new HashSet<T>();
		for (T t : iterable) {
			res.add(t);
		}
		return res;
	}

	static <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	static <T> boolean checkArg(T arg) {
		boolean legal = true;
		if (arg == null) {
			legal = false;
		} else if (arg instanceof String && ((String) arg).trim().length() == 0) {
			legal = false;
		}

		return legal;
	}
}
