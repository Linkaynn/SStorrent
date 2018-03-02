package com.jeseromero.util;

import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SearchUtilTest {

	@Test
	public void must_decompose() {
		Collection<String> strings = SearchUtil.torrentNameDecompose("DCs.Legends.of.Tomorrow.S03E12.HDTV.x264-SVA");


		assertThat(strings.size(), is(36));
	}
}
