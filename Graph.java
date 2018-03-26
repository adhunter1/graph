package cs241Proj03;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Graph {
	ArrayList<City> cities;
	HashMap<String, Road> roads;
	HashMap<String, City> mappy;

	private Graph() {
		cities = new ArrayList<City>();
		roads = new HashMap<String, Road>();
		mappy = new HashMap<String, City>();
	}

	public City getCity(String name) {
		return mappy.get(name);
	}

	public void initializeMap(String cityFile, String roadFile) throws FileNotFoundException {
		Scanner file1 = new Scanner(new File(cityFile));
		City city;
		String name;
		String hold;

		while (file1.hasNext()) {
			city = new City(file1.nextInt(), file1.next(), file1.next());
			name = city.getFullName();
			while (true) {
				hold = file1.next();
				try {
					city.setPopulation(Long.parseLong(hold));
					break;
				} catch (NumberFormatException nE) {
					name += " " + hold;
				}
			}

			city.setFullName(name);
			city.setElevation(file1.nextInt());
			// puts city in part of map
			mappy.put(city.getCityCode(), city);
			cities.add(city);
		}

		file1.close();

		Scanner file2 = new Scanner(new File(roadFile));

		while (file2.hasNext()) {
			addRoad(file2.nextInt(), file2.nextInt(), file2.nextInt());
		}
		file2.close();
	}

	private void addRoad(int cityNum1, int cityNum2, int distance) {

		addRoad(cities.get(cityNum1 - 1), cities.get(cityNum2 - 1), distance);
	}

	private void addRoad(String beginning, String end, int distance) {
		Road road;
		City city1 = mappy.get(beginning);
		City city2 = mappy.get(end);

		if (city1 == null) {
			System.out.println(beginning + " doesn't exist");
			return;
		}
		if (city2 == null) {
			System.out.println(end + " doesn't exist");
			return;

		}
		if ((road = roads.get(beginning + end)) != null) {
			System.out.println("There is already a road with distance " + road.getDistance() + " between " + beginning
					+ " and " + end);
			return;
		}

		addRoad(city1, city2, distance);
		System.out.println("You have inserted a road from " + city1.getFullName() + " to " + city2.getFullName()
				+ " with a distance of " + distance);
	}

	private void addRoad(City city1, City city2, int distance) {

		Road road = new Road(city1, city2, distance);
		city1.addRoad(road);
		roads.put(city1.getCityCode() + city2.getCityCode(), road);
	}

	private void removeRoad(String beginning, String end) {

		City city1 = mappy.get(beginning);
		City city2 = mappy.get(end);
		Road road;

		if (city1 == null) {
			System.out.println(beginning + " doesn't exist");
			return;
		}
		if (city2 == null) {
			System.out.println(end + " doesn't exist");
			return;
		}

		if ((road = roads.get(beginning + end)) == null) {
			System.out
					.println("The road from " + city1.getFullName() + " to " + city2.getFullName() + " doesn't exist");
			return;
		}
		city1.removeRoad(road);
		roads.remove(beginning + end);
		System.out.println("You have removed the road from " + city1.getFullName() + " to " + city2.getFullName()
				+ " with a distance of " + road.getDistance());
	}

	// try to use Dijkstra
	public int getShortestDistance(String start, String goal) {
		City beginning = mappy.get(start);
		City end = mappy.get(goal);

		if (beginning == null) {
			System.out.println(start + " doesn't exist");
			return 0;
		}
		if (end == null) {
			System.out.println(goal + " doesn't exist");
			return 0;
		}

		HashMap<City, Integer> distance = new HashMap<City, Integer>();
		HashMap<City, City> lastD = new HashMap<City, City>();
		int newD;
		City first = null;
		City next = null;

		Integer min = null;
		Integer holdD;
		ArrayList<City> line = new ArrayList<City>();

		for (int i = 0; i < cities.size(); i++) {
			next = cities.get(i);
			line.add(next);
		}

		distance.put(beginning, 0);

		boolean found = false;

		while (!line.isEmpty() && !found) {

			min = null;

			for (int i = 0; i < line.size(); i++) {
				holdD = distance.get(next = line.get(i));
				if (holdD != null) {
					if (min == null || holdD < min) {
						min = holdD;
						first = next;
					}
				}
			}

			line.remove(first);

			for (Road r : first.roads) {

				newD = distance.get(first) + r.getDistance();
				next = r.getDistToCity();
				if (distance.get(next) == null || newD < distance.get(next)) {
					distance.put(next, newD);
					lastD.put(next, first);

				}
				if (next == end) {
					found = true;

					break;
				}

			}

		}

		if (found == true) {
			String track = next.getCityCode();

			while (next != beginning) {
				next = lastD.get(next);
				track = next.getCityCode() + " " + track;
			}

			System.out.println("The minimum distance between " + beginning.getFullName() + " and " + end.getFullName()
					+ " is " + distance.get(end) + " through the route : " + track);
			return distance.get(end);
		} else {
			System.out.println("Couldn't find path between " + beginning.getFullName() + " and " + end.getFullName());
			return -1;
		}
	}

	public static void main(String[] args) {
		//String cityFile = "c:\\users\\alex\\onedrive\\documents\\city.dat";
		//String roadFile = "c:\\Users\\Alex\\OneDrive\\Documents\\Road.dat";
		 String cityFile="c:\\city.dat";
		 String roadFile="c:road.dat";
		try {
			Graph graph = new Graph();
			graph.initializeMap(cityFile, roadFile);

			Scanner input = new Scanner(System.in);
			String userInput;
			City city;
			boolean exit = false;
			do {
				System.out.print("Command? ");
				userInput = input.next();
				userInput = userInput.trim();

				switch (userInput.charAt(0)) {
				case 'E':
					exit = true;
					break;
				case 'H':
					System.out.println("Q Query the city information by entering the city code.");
					System.out.println("D Find the minimum distance between two cities.");
					System.out.println("I Insert a road by entering two city codes and distance.");
					System.out.println("R Remove an existing road by entering two city codes.");
					System.out.println("H Display this message.");
					System.out.println("E Exit.");
					break;
				case 'Q':
					System.out.print("City code: ");
					userInput = input.next();
					city = graph.getCity(userInput);
					if (city == null) {
						System.out.println("City with code " + userInput + " does not exist in graph");
					} else
						System.out.println(city.getCityNum() + " " + city.getCityCode() + " " + city.getFullName() + " "
								+ city.getPopulation() + " " + city.getElevation());

					break;
				case 'I':
					System.out.print("City codes and distance :");
					graph.addRoad(input.next(), input.next(), input.nextInt());
					break;

				case 'D':
					System.out.print("City codes : ");
					graph.getShortestDistance(input.next(), input.next());
					break;
				case 'R':
					System.out.print("City codes : ");
					graph.removeRoad(input.next(), input.next());

					break;

				default:
					break;
				}

			} while (!exit);

			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
