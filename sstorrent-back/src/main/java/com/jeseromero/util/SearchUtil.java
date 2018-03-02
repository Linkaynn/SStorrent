package com.jeseromero.util;

import java.util.*;
import java.util.stream.Collectors;

public class SearchUtil {

	private static final String SPECIAL_CHARACTER_REGEX = "[^\\w:',’()&]+";

	/**
	 * This splits a name in all posible combinatios, pe: The Good Burger
	 * The
	 * The Good
	 * Good
	 * The Good Burger
	 * Burger
	 * Good Burger
	 * @param name
	 * @return
	 */
	public static Collection<String> torrentNameDecompose(String name) {

		name = name.replaceAll(SPECIAL_CHARACTER_REGEX, " ");

		List<String> tokens = Arrays.asList(name.split(" "));

		tokens = tokens.stream().map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());

		Collection<String> result = new ArrayList<>();

		for (int i = 0; i < tokens.size(); i++) {
			result.addAll(splitInside(tokens, i));
		}

		result = result.stream().filter(s -> !s.isEmpty()).sorted((o1, o2) -> o1.length() <= o2.length() ? 1 : -1).collect(Collectors.toList());

		return result;
	}

	private static Collection<String> splitInside(List<String> tokens, int offset) {
		Collection<String> result = new ArrayList<>();

		int lap = 0;

		while (lap < tokens.size()) {
			String division = "";

			for (int i = offset; i < tokens.size() - lap; i++) {
				division += " " + tokens.get(i);
			}

			result.add(division.trim());

			lap++;
		}

		return result;
	}
}
