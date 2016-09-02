package com.sl.test.pricing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sl.test.pricing.common.PricingFeedMapper;
import com.sl.test.pricing.domain.PricingFeed;

public class TestPricing {

	public static void main(String[] args) {

		if (!checkArgs(args)) {

			System.err.println(" Invalid TestPricing -  args :    \n   "
					+ Arrays.toString(args) + "   \n");
			System.exit(-1);

		}

		List<PricingFeed> pricingList = loadFeedFile(args[0], args[1]);

		Map<String, Double> result = check(pricingList);
		
		System.out.println(" result  1   :         " + result);

		Map<String, List<Double>> output =  check_1(pricingList);
		
		System.out.println(" result  1   :         " + output);

	}

	private static boolean checkArgsCount(String[] args) {

		if (args == null || args.length == 2) {

			return true;
		}

		return false;
	}

	private static boolean checkArgs(String[] args) {

		if (!checkArgsCount(args)) {

			System.err
					.println("Usage: TestPricing <feed-file-path> <separator> \n"
							+ " where   <feed-file-path> is the feed-file to use   \n"
							+ "  <separator> is the separator eg : | or ,   \n\n");
			return false;

		}

		String path = null;
		String sep = null;

		if (args != null) {

			path = args[0];
			sep = args[1];

			if (path == null || path.isEmpty()) {

				System.err.println(" TestPricing -   path is null   ");
				return false;

			}

			File feedFile = new File(path);

			if (feedFile == null || !feedFile.exists() || !feedFile.isFile()) {

				System.err
						.println(" TestPricing -  feedFilePath does not exists    "
								+ path);
				return false;

			}

			if (sep == null || sep.isEmpty()) {

				System.err.println(" TestPricing -   separator is null   ");
				// return false;
			}

		}

		return true;
	}

	private static Map<String, Double> check(List<PricingFeed> pricingList) {

		Map<String, Double> output = null;

		Map<String, Double> result = null;

		result =  pricingList.stream().collect(Collectors.groupingBy(
				PricingFeed::getItemName,
				Collectors.summingDouble(PricingFeed::getWeight)));

		// System.out.println(" result   :         " + result);

		output = result
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue())
				.collect(
						Collectors.toMap(Entry::getKey, Entry::getValue, (e1,e2) -> e1, LinkedHashMap<String, Double>::new));

	//	System.out.println(" result  1   :         " + output);

		return output;

	}

	private static Map<String,  List<Double>> check_1(List<PricingFeed> pricingList) {  

		Map<String, List<Double>> result = null;

		result =  pricingList.stream().collect(Collectors.groupingBy(
				PricingFeed::getItemName, Collectors.mapping(
						PricingFeed::getWeight, Collectors.collectingAndThen(
								Collectors.toList(), l -> l.stream().limit(3)
										.collect(Collectors.toList())))));

//		System.out.println(" result   :         " + result);
		
		return result;  

	}

	private static List<PricingFeed> loadFeedFile(String path, String separator) {

		Stream<PricingFeed> stream = null;

		List<PricingFeed> pricingList = null;

		try (InputStream is = new FileInputStream(new File(path));
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));) {

			stream = br.lines().map(PricingFeedMapper::apply);

			pricingList = stream.collect(Collectors
					.toCollection(ArrayList::new));

			// pricingList.forEach(System.out::println );

		} catch (Exception e) {

			System.err
					.println(" Exception occured in TestPricing.loadFeedFile()   :      \n   "
							+ e.getMessage() + "   \n");

			e.printStackTrace();

			System.exit(-1);
		}

		return pricingList;

	}

}
