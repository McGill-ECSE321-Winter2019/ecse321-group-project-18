package ca.mcgill.ecse321.academicmanager.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * helper methods used by service classes
 * 
 * @author Group-18
 * @version 2.0
 *
 */
public class Helper {
	/**
	 * converts any iterable to a Set
	 * 
	 * @param iterable iterable of any type
	 * @return Set result of iterable
	 */
	static <T> Set<T> toSet(Iterable<T> iterable) {
		Set<T> res = new HashSet<T>();
		for (T t : iterable) {
			res.add(t);
		}
		return res;
	}

	/**
	 * converts any iterable to a List
	 * 
	 * @param iterable iterable of any type
	 * @return List result of iterable
	 */
	static <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	/**
	 * Validated inputs of service methods, i.e. not null and not empty string
	 * 
	 * @param arg argument to be checked
	 * @return boolean indicating validity of arg
	 */
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
