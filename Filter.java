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
		final String INPUT_FILENAME = "list.txt";
		final String OUTPUT_FILENAME = "list.txt";
		Filter myFilter = new Filter(INPUT_FILENAME);
		myFilter.process(true);
		myFilter.writeFile(OUTPUT_FILENAME);
	}

	private Filter() {
		input = new ArrayList<String>();
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
			System.err.println("Could not find file " + filename);
		}
	}

	public void writeFile(String filename) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF8"))) {
			for (String line : output) {
				writer.write(line);
				writer.write(System.lineSeparator());
			}
		} catch (IOException e) {
			System.err.println("Could not create or open file " + filename);
		}
	}

	public void process() {
		process(false);
	}

	public void process(boolean showStats) {
		Map<String, Integer> stats = new HashMap<String, Integer>();

		for (String s : input) {
			stats.put(s, stats.getOrDefault(s, 0) + 1);
		}
		output = new ArrayList<String>(stats.keySet());
		Collections.sort(output);

		if (showStats) {
			if (input.size() == output.size()) {
				System.out.println("No duplicated elements.");
			} else {
				String format = "  %-20s  %-6s  %n";
				System.out.printf(format, "Item", "Times");
				for (final Map.Entry<String, Integer> e : stats.entrySet()) {
					if (e.getValue() > 1) {
						System.out.printf(format, e.getKey(), e.getValue().toString());
					}
				}
			}
		}
	}

	public List<String> getResult() {
		return output;
	}

}
