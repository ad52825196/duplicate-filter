import java.io.*;
import java.util.*;

/**
 * This class can filter out all duplicated elements in a List Collection. It
 * can read input from a file and write processed data into a file. It can take
 * a List Collection as input and return a processed ArrayList Collection. It
 * can print some useful statistics to the console if required.
 * 
 * @author Zhen Chen
 *
 */

public class Filter {
	private List<String> input, output;

	public static void main(String[] args) {
		final String inputFileName = "list.txt";
		final String outputFileName = "output.txt";
		Filter myFilter = new Filter(inputFileName);

		myFilter.process(true);
		myFilter.writeFile(outputFileName);
	}

	public Filter() {
		input = new ArrayList<String>();
		output = new ArrayList<String>();
	}

	public Filter(final List<Object> list) {
		this();
		for (final Object o : list) {
			input.add(o.toString());
		}
	}

	public Filter(String filename) {
		this();
		readFile(filename);
	}

	public void readFile(String filename) {
		File file = new File(filename);
		String line;

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				if (!line.equals("")) {
					input.add(line);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file " + filename);
		}
	}

	public void writeFile(String filename) {
		try (PrintWriter writer = new PrintWriter(filename)) {
			for (String line : output) {
				writer.println(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not create or open file " + filename);
		}
	}

	public void process() {
		process(false);
	}

	public void process(boolean showStats) {
		Map<String, Integer> stats = new HashMap<String, Integer>();
		Integer value;

		for (String s : input) {
			if (!output.contains(s)) {
				output.add(s);
			} else if (showStats) {
				value = stats.containsKey(s) ? (stats.get(s) + 1) : 2;
				stats.put(s, value);
			}
		}

		Collections.sort(output);

		if (showStats) {
			if (stats.isEmpty()) {
				System.out.println("No duplicated elements.");
			} else {
				String format = "  %-20s  %-6s  %n";
				System.out.printf(format, "Item", "Times");
				for (String s : stats.keySet()) {
					value = stats.get(s);
					System.out.printf(format, s, value.toString());
				}
			}
		}
	}

	public List<String> getResult() {
		return output;
	}

}
