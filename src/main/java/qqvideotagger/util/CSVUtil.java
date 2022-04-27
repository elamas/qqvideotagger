package qqvideotagger.util;

import java.util.List;
import java.util.Set;

public class CSVUtil {

	public static void loadCsvToSet(Set<String> set, String csv) {
		String[] csvSplitted = csv.split(",");
		set.addAll(List.of(csvSplitted));
	}
	
	public static String setToCSV(Set<String> set) {
		return String.join(",", set);
	}
}
